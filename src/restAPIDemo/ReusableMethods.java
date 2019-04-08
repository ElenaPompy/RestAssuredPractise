package restAPIDemo;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ReusableMethods 
{
	public static JsonPath getJsonString(Response response)
	{
		String rawRespone =response.asString();		
		return new JsonPath(rawRespone);
	}
	
	
}
