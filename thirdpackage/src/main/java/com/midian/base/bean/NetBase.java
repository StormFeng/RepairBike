package com.midian.base.bean;

import java.io.Serializable;

/**
 * 实体类
 */
public abstract class NetBase implements Serializable {

	private static final long serialVersionUID = 3755992750229256985L;
	protected int itemViewType;
	protected String cacheKey = "";

	public String getCacheKey() {
		return cacheKey;
	}

	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}

	public int getItemViewType() {
		return itemViewType;
	}

	public void setItemViewType(int itemViewType) {
		this.itemViewType = itemViewType;
	}

}
