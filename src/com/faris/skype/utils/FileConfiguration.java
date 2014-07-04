package com.faris.skype.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class FileConfiguration {
	private File configFile = null;
	private Properties configProperties = null;

	private FileConfiguration(File configFile) {
		this.configFile = configFile;
		this.configProperties = new Properties();
		if (this.configFile != null) {
			try {
				this.configProperties.load(new FileInputStream(this.configFile));
			} catch (Exception ex) {
				System.out.println("Could not load the configuration.");
			}
		}
	}

	public Object get(String key) {
		return this.configProperties.get(key);
	}

	public Object get(String key, Object defaultValue) {
		Object obj = this.get(key);
		return obj == null ? defaultValue : obj;
	}

	@SuppressWarnings({ "unchecked" })
	public <T> T get(String key, Class<T> unused) {
		try {
			T value = (T) this.configProperties.get(key);
			return value != null ? (unused == Long.class ? (T) ((Long) Long.parseLong(value.toString())) : (unused == Integer.class ? (T) ((Integer) Integer.parseInt(value.toString())) : (unused == Short.class ? (T) ((Short) Short.parseShort(value.toString())) : value))) : null;
		} catch (ClassCastException ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public <T> T get(String key, T defaultValue, Class<T> unused) {
		T obj = this.get(key, unused);
		return obj == null ? defaultValue : obj;
	}

	public List<String> getKeys() {
		List<Object> keyList = new ArrayList<Object>(this.configProperties.keySet());
		List<String> strKeyList = new ArrayList<String>();
		for (Object key : keyList)
			strKeyList.add(key.toString());
		return strKeyList;
	}

	public List<Object> getValues() {
		return new ArrayList<Object>(this.configProperties.values());
	}

	public HashMap<String, Object> getAsMap() {
		HashMap<String, Object> configMap = new HashMap<String, Object>();
		List<String> keys = this.getKeys();
		List<Object> values = this.getValues();
		while (keys.size() < values.size())
			keys.add("");
		while (keys.size() > values.size())
			values.add("");
		for (int index = 0; index < keys.size(); index++) {
			configMap.put(keys.get(index), values.get(index));
		}
		return configMap;
	}

	public void set(String key, Object value) {
		this.configProperties.setProperty(key, value.toString());
	}

	public void save() {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(this.configFile);
			fileOutputStream.write(new String().getBytes());
			this.configProperties.store(fileOutputStream, null);
			fileOutputStream.close();
		} catch (Exception ex) {
			System.out.println("Could not save the configuration.");
		}
	}

	public static FileConfiguration loadConfiguration(File configFile) {
		return new FileConfiguration(configFile);
	}
}
