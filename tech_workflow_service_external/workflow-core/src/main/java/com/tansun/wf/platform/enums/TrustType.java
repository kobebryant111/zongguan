package com.tansun.wf.platform.enums;

public enum TrustType {
    
	AGENT("代理","0"), REPLACE("代办","1"),AGREE("协办","2");
	public  String val;
	
	public  String  name;
	
	private  TrustType(String name,String  val) {
		this.val = val;
		this.name  = name;
	}
	
	
	
}
