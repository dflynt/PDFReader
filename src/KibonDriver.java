import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class KibonDriver {

	public static void main(String[] args) throws IOException, ParseException, java.text.ParseException {
		JSONParser parser = new JSONParser();

		String filePath = "C:/users/danielf/Documents/KIBON_images/KIBON_IMAGE2JSON.json";
		//this type of input will change once we have a selector on the form
		try {
			Object obj = parser.parse(new FileReader(filePath));
			JSONObject jsonObject = (JSONObject) obj;
			JSONArray jArray = (JSONArray)jsonObject.get("textAnnotations");
			
			Object[] words = jArray.toArray();
			JSONObject description = (JSONObject) words[0];
			String[] file = description.get("description").toString().split("\n");
			
			KibonTemplate kibon = new KibonTemplate(file);
			kibon.printInfo();
			
			
			//the template choice  on the form determines the object created
			
			
			/*
			 * Below is an example of what would be used once we have the selector made
			 * 
			 * int choice = {form selector input}
			 *
			 * switch(choice) {
			 * 		case(1):
			 * 			SeekWayTemplate SWTemplate = new SeekWayTemplate(file);
			 *			SWTemplate.printInfo();
			 *			break;
			 *		case(2):
			 *			AnotherTemplate anotherTemplate = new AnotherTemplate(file);
			 *			anotherTemplate.printInfo();
			 *			break;
			 */

			
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}	}

}
