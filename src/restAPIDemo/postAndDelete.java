package restAPIDemo;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class postAndDelete {

	public static void main(String[] args) 
	{
		String b = "{\r\n" + 
				"    \"location\":{\r\n" + 
				"        \"lat\" : -38.383494,\r\n" + 
				"        \"lng\" : 33.427362\r\n" + 
				"    },\r\n" + 
				"    \"accuracy\":50,\r\n" + 
				"    \"name\":\"Frontline house\",\r\n" + 
				"    \"phone_number\":\"(+91) 983 893 3937\",\r\n" + 
				"    \"address\" : \"29, side layout, cohen 09\",\r\n" + 
				"    \"types\": [\"shoe park\",\"shop\"],\r\n" + 
				"    \"website\" : \"http://google.com\",\r\n" + 
				"    \"language\" : \"French-IN\"\r\n" + 
				"}";
		RestAssured.baseURI="http://216.10.245.166";
		Response res = given().
		queryParam("key", "qaclick123").
		body(b).
		when().
			post("/maps/api/place/add/json").
		then().assertThat().statusCode(200).and().body("status", equalTo("OK")).
		extract().response();
				
		String rawRespone =res.asString();		
		System.out.println(rawRespone);
		
		JsonPath js = new JsonPath(rawRespone);
		String placeID = js.get("place_id");
		
		System.out.println("place is added with "+placeID);
		
		//delete the place using place id
		given().
			queryParam("key", "qaclick123").
			body("{\"place_id\":\""+placeID+"\" }").
		when().
			post("/maps/api/place/delete/json").
			then().assertThat().statusCode(200).and().
			body("status", equalTo("OK"));
		
		System.out.println("place is deleted");
			
			
		
		
		
		
		
		
	}

}
