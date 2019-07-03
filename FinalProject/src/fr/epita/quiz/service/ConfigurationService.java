package fr.epita.quiz.service;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;


/**
 * <h2> The ConfigurationService class <h2>
 * <p> Contains the methods to get the properties </p> 
 * @author Durgi
 *
 */
public class ConfigurationService {
	
	private static ConfigurationService instance;
	
	
	Properties props = new Properties();
	boolean init = false;
	
	private ConfigurationService() {
		// properties loading
		try {
			File confFile = new File("conf.properties");
			FileInputStream ips = new FileInputStream(confFile);
			props.load(ips);
			init =true;
		} catch (Exception e) {
			init=false;
		}

	}
	
	/**
	 * <p> Method to create the configuration service instance <p>
	 * @return instance returns the instance of the configration service class
	 */
	public static ConfigurationService getInstance() {
		if (instance == null) {
			instance = new ConfigurationService();
		}
		return instance;
	}
	
	public boolean isInit() {
		return init;
	}
	
	/** 
	 * <p> Returns the property for a key </p>
	 * @param key The key that is searched
	 * @param defaultValue The value that is returned if the the property is not found
	 * @return returns the property corresponding to the key
	 */
	public String getConfigurationValue(String key, String defaultValue) {
		return props.getProperty(key, defaultValue);
	}
	/**
	 * <p> Returns the property for a key</p>
	 * @param key The key that is searched
	 * @param defaultValue 
	 * @return returns the property corresponding to the key
	 */
	public String getConfigurationValue(ConfigEntry key, String defaultValue) {
		return props.getProperty(key.getKey(), defaultValue);
	}

}
