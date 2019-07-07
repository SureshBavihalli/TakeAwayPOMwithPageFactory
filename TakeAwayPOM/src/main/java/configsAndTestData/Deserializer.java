package configsAndTestData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

public class Deserializer {
	
	public static <T> ArrayList<T> getJSONModelDeserializer(String input, Class<T> typetest)
	{
		
		List<T> classArray = new ArrayList<T>();
		String trimmedInput = input.trim();
		Gson gson = new GsonBuilder().create();
		if(trimmedInput.startsWith("["))
		{
			JsonArray jsonarray = gson.fromJson(input, JsonArray.class);
			for(JsonElement jsonobject : jsonarray)
			{
				T classObject = gson.fromJson(jsonobject, typetest);
				 classArray.add(classObject);
			}
			 
		}
		else if(trimmedInput.startsWith("{"))
		{
			 T classObject = gson.fromJson(input, typetest);
			 classArray.add(classObject);
				
		}else
		{
			throw new IllegalArgumentException("Error: The input string is not a JSON, please provide a valid input for deserialization.");
		}
		return (ArrayList<T>) classArray;
	}
	
	public static <T> T getXMLModelDeserializer(String input, Class<T> typetest)
	{
		
		String trimedInput = input.trim();
		XmlMapper xmlmapper = new XmlMapper();
			T value = null;
			if(trimedInput.startsWith("<"))
			{
			try {
				value = xmlmapper.readValue(trimedInput,typetest);
			} catch (JsonParseException e) {
				throw new IllegalArgumentException("Error: The input XML format is invalid, please provide a valid input for deserialization.");
			} catch (JsonMappingException e) {
				e.printStackTrace();
				throw new IllegalArgumentException("Error: Unable to map the objects, please verify if the Model class is accurate for mapping with XML and then re-try.");
			} catch (IOException e) {
				e.printStackTrace();
			}
			}
			else
			{
				throw new IllegalArgumentException("Error: The input string is not an XML, please provide a valid input for deserialization.");
			}
		return value;
	}
}
