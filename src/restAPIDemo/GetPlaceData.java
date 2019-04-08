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

public class GetPlaceData 
{
Properties prop = new Properties();
	
	@BeforeTest
	public void setUp() throws IOException
	{
		
		FileInputStream fis = new FileInputStream("D:\\Sapna_Workplace\\restAPIDemo\\src\\Resources\\env.properties");
		prop.load(fis);
	}
	
	@Test
	public void searchPlace()
	{
		RestAssured.baseURI= "https://maps.googleapis.com";
		
		//to search the place in place API
		Response res = given().
				param("location","-33.8670522,151.1957362").
				param("radius","1500").
				param("key",prop.getProperty("googleKey")).
		when().
			get("/maps/api/place/nearbysearch/json").
		then().
			assertThat().statusCode(200).and().
			body("status", equalTo("OK")).
		extract().response();
		
		String result = res.asString();
		JsonPath js = ReusableMethods.getJsonString(res);				
		int count = js.get("results.size()");
		System.out.println(count);
	}
	
	@AfterTest
	public void quitTest()
	{
		System.out.println("test is finished");
	}
}
