package com.midian.base.app;


import com.midian.base.bean.NetResult;

public class CityData extends NetResult {

	public CityData() {
		super();
	}

	public CityData(String id, String name, int index) {
		super();
		this.id = id;
		this.name = name;
		this.index = index;
	}

	public String id;
	public String name;
	public int index;
	public String type;

}
