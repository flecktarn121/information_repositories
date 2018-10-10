package uo.ri.configuration;

import java.io.IOException;
import java.util.Properties;

public class Configuration {
	private Properties properties;
	private static Configuration instance = null;
	private static final String CONFIG_FILE = "configuration.properties";
	
	private Configuration() {
		this.properties = new Properties();
		try {
			properties.load(Configuration.class.getClassLoader().getResourceAsStream(CONFIG_FILE));
		} catch (IOException e) {
			throw new RuntimeException("There has been a problem with the config file", e);
		}
	}
	
	public static Configuration getInstance() {
		if(instance == null) {
			instance = new Configuration();
		}
		return instance;
	}
	
	public String getProperty(String key) {
		String prop = properties.getProperty(key);
		if(prop == null) {
			throw new RuntimeException("Property not found.");
		}
		return prop;
	}
}
