package restAPIDemo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;


public class AddBook {

	Properties prop = new Properties();
	
	@BeforeTest
	public void setUp() throws IOException
	{
		FileInputStream fis = new FileInputStream("D:\\Sapna_Workplace\\restAPIDemo\\src\\Resources\\env.properties");
		prop.load(fis);
	}
	@Test(dataProvider="testData")
	public void addBookID(String isbn,String aisle) 
	{
		//add the book
		RestAssured.baseURI = prop.getProperty("bookURI");
		Response res = given().
			body(PayLoad.addBookBody(isbn,aisle)).
		when().
			post("Library/Addbook.php").
			then().statusCode(200).and().
		extract().response();
		
		JsonPath js = ReusableMethods.getJsonString(res);
		String id = js.get("ID");
		System.out.println(id);
		
		//delete the book
		given().
			body("{\r\n\"ID\" : \""+id+"\"\r\n} \r\n").
		when().
			post("/Library/DeleteBook.php").
		then().assertThat().statusCode(200);
			
	}
	
	@DataProvider
	public Object[][] testData()
	{
		return new Object[][]{{"manu","15"},{"sapna","10"}};
		
	}
	@AfterTest
	public void endTest()
	{
		System.out.println("book is added");
	}

}
