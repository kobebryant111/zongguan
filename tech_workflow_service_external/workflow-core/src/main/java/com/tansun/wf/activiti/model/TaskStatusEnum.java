package com.tansun.wf.activiti.model;

public enum TaskStatusEnum {
	   CLAIM("1","待签收"),HANDLE("2","待处理"),COMPLETED("3","");
	   
	   private   String  index;
	   
	   private   String  name;
	   
	   private  TaskStatusEnum(String index,String name){
		    this.index = index;
		    this.name  = name;
	   }

	   public  static String    getIndexName(String index){
		   
		       for (TaskStatusEnum   idx :TaskStatusEnum.values()){
		    	   if (idx.index.equals(index)){
		    		   return   idx.name;
		    	   }
		    	   
		       }
		       return  null;
	   }

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	   
}
