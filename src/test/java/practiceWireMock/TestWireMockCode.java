package practiceWireMock;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;

import io.restassured.RestAssured;

import io.restassured.response.*;

public class TestWireMockCode {
	
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
		
		mockresponse.withStatus(200);
		
		mockresponse.withStatusMessage("Mocking through code");
		
		mockresponse.withHeader("Content-Type", "text/json");
		
		mockresponse.withHeader("Token", "1111");
		
		mockresponse.withBody("text to put in boday");
		
		//Stubbing the request
		WireMock.stubFor(WireMock.get("/emps/1").willReturn(mockresponse));

	}
	
	@Test
	public void testWithcode() {
		
		
		Response response=RestAssured.given().
		get("http://localhost:8080/emps/1").
		then().
		extract().
		response();
		
		System.out.println(response.asPrettyString());
		
	}
	
	@AfterClass
	public void tearDown() {
		
		if(server.isRunning() && null!=server) {
			server.stop();
			
			System.out.println("The Mock server stopped");
			
		}
		
	}

}
