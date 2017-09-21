package com.midian.base.afinal.db.table;

public class OneToMany extends Property{

	private Class<?> oneClass;
    //add by pwy for lazyload时实现排序
    private String orderColumn;
    private boolean isDesc;

	public Class<?> getOneClass() {
		return oneClass;
	}

	public void setOneClass(Class<?> oneClass) {
		this.oneClass = oneClass;
	}

    public String getOrderColumn(){
        return  orderColumn;
    }
    public void setOrderColumn(String column){
        orderColumn  = column;
    }


    public boolean isDesc() {
        return isDesc;
    }

    public void setDesc(boolean desc) {
        isDesc = desc;
    }

	
	
}
