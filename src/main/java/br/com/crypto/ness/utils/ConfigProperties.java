package br.com.crypto.ness.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import br.com.crypto.ness.entities.Config;

public class ConfigProperties {

	private static ConfigProperties configProperties;
	private static Config config;

	private ConfigProperties() {
		Map<String, String> propertiesRead = readProperties();
		config = new Config();

		mapToObject(propertiesRead);
	}

	private void mapToObject(Map<String, String> propertiesRead) {
		if (propertiesRead.containsKey("salvarKey"))
			config.setSalvarKey(Boolean.valueOf((String) propertiesRead.get("salvarKey")));

		if (propertiesRead.containsKey("salvarIV"))
			config.setSalvarIV(Boolean.valueOf((String) propertiesRead.get("salvarIV")));

		if (propertiesRead.containsKey("key"))
			config.setKey((String) propertiesRead.get("key"));

		if (propertiesRead.containsKey("iv"))
			config.setIV((String) propertiesRead.get("iv"));

		if (propertiesRead.containsKey("tempoFecharJanela"))
			config.setTempoApagarMensagem((String) propertiesRead.get("tempoFecharJanela"));
	}

	public static ConfigProperties getInstance() {
		if (configProperties == null)
			configProperties = new ConfigProperties();

		return configProperties;
	}

	public static Map<String, String> readProperties() {
		Properties prop = new Properties();
		InputStream input = null;

		Map toReturn = new HashMap();
		try {
			input = new FileInputStream("config.properties");
			prop.load(input);

			Set<Object> keys = prop.keySet();
			for (Object key : keys) {
				toReturn.put(key, prop.get(key));
			}

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return toReturn;
	}

	public static void saveProperties() {
		Properties prop = new Properties();
		OutputStream output = null;

		try {
			output = new FileOutputStream("config.properties");

			prop.put("salvarKey", String.valueOf(config.isSalvarKey()));
			prop.put("salvarIV", String.valueOf(config.isSalvarIV()));
			prop.put("key", config.getKey());
			prop.put("iv", config.getIV());
			prop.put("tempoFecharJanela", config.getTempoApagarMensagem());

			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public static void main(String[] args) {
		System.out.println(ConfigProperties.getInstance().getConfig().getKey());

	}
}
