package com.midian.base.afinal.db.table;

import java.text.SimpleDateFormat;

public class KeyValue {
	private String key;
	private Object value;
	
	public KeyValue(String key, Object value) {
		this.key = key;
		this.value = value;
	}
	
	
	public KeyValue() {}


	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public Object getValue() {
		if(value instanceof java.util.Date || value instanceof java.sql.Date){
			return sdf.format(value);
		}
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
	
}
