package practiceWireMock;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;

import io.restassured.RestAssured;

import io.restassured.response.*;

public class TestWireMockCodeFile {
	
	private static final String HOST="localhost";
	private static final int PORT=8080;
	private static final WireMockServer server=new WireMockServer(PORT);
	
	@BeforeClass
	public void setUp() {
		
		//Starting wire Mock Server
		server.start();
		WireMock.configureFor(HOST,PORT);
		
		//Stubbing Response
		ResponseDefinitionBuilder mockresponse=new ResponseDefinitionBuilder();
		
		mockresponse.withStatus(201);
		
		mockresponse.withBodyFile("json/{}index.json");
		
		WireMock.stubFor(WireMock.get("/emps/2").willReturn(mockresponse));

	}
	
	@Test
	public void testWithcode() {
		
		
		Response response=RestAssured.given().
		get("http://localhost:8080/emps/2").
		then().
		extract().
		response();
		
		System.out.println(response.asPrettyString());
		
	String s=	response.jsonPath().get("glossary.GlossDiv.title");
	
	System.out.println("************"+s);
		
	}
	
	@AfterClass
	public void tearDown() {
		
		if(server.isRunning() && null!=server) {
			server.stop();
			
			System.out.println("The Mock server stopped");
			
		}
		
	}

}
