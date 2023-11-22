package practiceWireMock;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.*;

public class ConditionalMocking {private static final String HOST="localhost";
private static final int PORT=8080;
private static final WireMockServer server=new WireMockServer(PORT);

@BeforeClass
public void setUp() {
	
	//Starting wire Mock Server
	server.start();
	WireMock.configureFor(HOST,PORT);
	
	//Stubbing Response
	ResponseDefinitionBuilder mockresponse=new ResponseDefinitionBuilder();
	
	mockresponse.withStatus(503);
	
	mockresponse.withHeader("Content-Type", "text/html");
	
	mockresponse.withBody("Service Unavaliable");
	
	
	ResponseDefinitionBuilder mockresponse1=new ResponseDefinitionBuilder();
	
	mockresponse1.withStatus(200);
	
	mockresponse1.withHeader("Content-Type", "application/JSON");
	
	mockresponse1.withHeader("Ticke-ID", "55555");
	
	mockresponse1.withBody("Enter Valid Ticket");
	
	ResponseDefinitionBuilder mockresponse3=new ResponseDefinitionBuilder();
	
	mockresponse3.withStatus(201);
	
	mockresponse3.withBodyFile("json/{}index.json");
	
	WireMock.stubFor(WireMock.get("/movie/1").withHeader("Content-Type", WireMock.matching("text/html")).willReturn(mockresponse));

	WireMock.stubFor(WireMock.get("/movie/1").withHeader("Ticke-ID", WireMock.matching("55555")).willReturn(mockresponse1));

	WireMock.stubFor(WireMock.post("/movie/3?search_term=WireMock").
			withHeader("Ticke-ID", WireMock.matching("55555"))
			.willReturn(mockresponse3));
	

}

@Test
public void testWithcode() {
	
	
	Response response=RestAssured.given().
	header(new Header("Ticke-ID", "55555")).
	queryParam("search_term","WireMock").
	post("http://localhost:8080/movie/3").
	then().
	extract().
	response();
	
	System.out.println(response.asPrettyString());
	
	System.out.println(response.statusCode());

	
	/*
	 * System.out.println(response.asPrettyString());
	 */		

	
}

@AfterClass
public void tearDown() {
	
	if(server.isRunning() && null!=server) {
		server.stop();
		
		System.out.println("The Mock server stopped");
		
	}
	
}}
