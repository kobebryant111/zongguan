package com.tansun.wf.activiti.util.dbtool.exception;

public class DbToolException extends RuntimeException {

    private Integer code;

    public DbToolException(){
        super(DbToolEnum.ERROR.getMsg());
        this.code = DbToolEnum.ERROR.getCode();
    }

	public DbToolException(String s){
		super(s);
        this.code = DbToolEnum.ERROR.getCode();
    }

    public DbToolException(DbToolEnum e){
	    super(e.getMsg());
	    this.code = e.getCode();
    }
}
