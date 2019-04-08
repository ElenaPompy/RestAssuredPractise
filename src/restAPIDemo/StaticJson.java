package restAPIDemo;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class StaticJson {

	public static void main(String[] args) throws IOException 
	{
		RestAssured.baseURI = "http://216.10.245.166";
		Response res = given().
			body(GenerateStringFromJson("D:\\Sapna_Workplace\\restAPIDemo\\src\\Resources\\bookTestData.json")).
		when().
			post("Library/Addbook.php").
			then().statusCode(200).and().
		extract().response();
		
		JsonPath js = ReusableMethods.getJsonString(res);
		String id = js.get("ID");
		System.out.println("ID of the book created is : "+id);
		
		//delete the book
		given().
			body("{\r\n\"ID\" : \""+id+"\"\r\n} \r\n").
		when().
			post("/Library/DeleteBook.php").
		then().assertThat().statusCode(200);
		
		System.out.println("book is deleted");

	}
	
	public static String GenerateStringFromJson(String path) throws IOException 
	{
		
		return new String(Files.readAllBytes(Paths.get(path)));
		
	}
	

}
