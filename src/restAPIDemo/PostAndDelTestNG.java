package restAPIDemo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class PostAndDelTestNG 
{
	Properties prop = new Properties();
	
	@BeforeTest
	public void setUp() throws IOException
	{
		
		FileInputStream fis = new FileInputStream("D:\\Sapna_Workplace\\restAPIDemo\\src\\Resources\\env.properties");
		prop.load(fis);
	}
	
	@Test
	public void postAndDelete()
	{
		RestAssured.baseURI= prop.getProperty("URI");
		
		//to add the place in place API
		Response res = given().log().ifValidationFails().
			queryParam("key",prop.getProperty("key")).
			body(PayLoad.postData()).
		when().
			post("/maps/api/place/add/json").
		then().
			assertThat().statusCode(200).and().
			body("status", equalTo("OK")).
		extract().response();
			
		JsonPath js = ReusableMethods.getJsonString(res);
		String placeID = js.get("place_id");
				
		System.out.println("place is added with "+placeID);
		
		//delete the place using place id
		given().
			queryParam("key", prop.getProperty("key")).
			body("{\"place_id\":\""+placeID+"\" }").
		when().
			post("/maps/api/place/delete/json").
		then().
			assertThat().statusCode(200).and().
			body("status", equalTo("OK"));
		
		System.out.println("place is deleted");		
	}
	
	@AfterTest
	public void quitTest()
	{
		System.out.println("test is finished");
	}
}

