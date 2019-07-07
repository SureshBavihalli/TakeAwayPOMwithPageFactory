package configsAndTestData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;


public class ConfigLoader {
	// public Environment envConfig;
	// public ConfigMain commonConfig;
	
	StringBuilder sb;
	public Object load(String dataXml) {
	
	try{
			 File mainConfig = new File("src/main/java/configsAndTestData/"+dataXml+".xml");
		     Reader fileReader = new FileReader(mainConfig);
		     BufferedReader bufReader = new BufferedReader(fileReader);
		     sb = new StringBuilder();
		     String line = bufReader.readLine();
		     while( line != null){
		         sb.append(line).append("\n");
		         line = bufReader.readLine();
		     }
	}catch (Exception e) {
		// TODO: handle exception
	}
	if(dataXml.equalsIgnoreCase("ConfigMain"))
		return Deserializer.getXMLModelDeserializer(sb.toString(),ConfigMain.class);
	else if(dataXml.equalsIgnoreCase("DE")||dataXml.equalsIgnoreCase("LD9PT")||dataXml.equalsIgnoreCase("QA")
			||dataXml.equalsIgnoreCase("Chicago")||dataXml.equalsIgnoreCase("UK"))
		return Deserializer.getXMLModelDeserializer(sb.toString(),Environment.class);
	else if(dataXml.equalsIgnoreCase("TestData"))
		return Deserializer.getXMLModelDeserializer(sb.toString(),TestData.class);
	return null;
}
	
	
}
