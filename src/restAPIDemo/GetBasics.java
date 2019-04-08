package restAPIDemo;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;



public class GetBasics {

	public static void main(String[] args) 
	{
		RestAssured.baseURI = "https://maps.googleapis.com";
		given().
			param("location","-33.8670522,151.1957362").
			param("radius","1500").
			param("key","AIzaSyCQ1r9RDWCzYX_IXTwZpBe4S66RShYTdMY").
			
		when().
			get("/maps/api/place/nearbysearch/json").
			
		then().assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
		body("results[0].place_id", equalTo("ChIJP3Sa8ziYEmsRUKgyFmh9AQM")).and().
		header("Content-Type", "application/json; charset=UTF-8");
		System.out.println("all good");

	}

}
