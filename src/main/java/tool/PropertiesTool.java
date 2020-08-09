package tool;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesTool {

	private static Properties properties = new Properties();
	
	static {
		InputStream inputStream = PropertiesTool.class.getClassLoader().getResourceAsStream("conf.properties");//将db.properties变为javaIO流对象
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getValue(String key) {
		return properties.getProperty(key);
	}
}
