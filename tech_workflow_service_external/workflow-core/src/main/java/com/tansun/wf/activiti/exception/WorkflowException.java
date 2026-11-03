package com.tansun.wf.activiti.exception;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
/**
 * 异常处理
 * @author admin
 *
 */
public class WorkflowException extends Exception {

	
	private static final long serialVersionUID = 581432832305538682L;
	
	
	private   String   errorCode;
	
	private  static  Properties  prop =  null;
	
	private  static   final  String   errorFile ="/message.properties";
	
	
	public    WorkflowException(String  errorCode){
		super(errorCode);
		  if (WorkflowException.prop == null ){
			 Resource  resource = new ClassPathResource(errorFile);
			 try {
				 WorkflowException.prop =PropertiesLoaderUtils.loadProperties(resource);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		  }
		  this.errorCode  = errorCode;
	}
	
	
	public   String   getMessage(String  errorCode){
		  if (prop != null){
			  return   prop.getProperty(errorCode);
		  }
		  return  null;
	}
	
	public  String   getMessage(){
		  if (prop != null){
			  String errorMsg = prop.getProperty(this.errorCode);
			  if (StringUtils.isNotBlank(errorMsg)) {
				  return errorMsg;
			  }else {
				  return this.errorCode;
			  }
		  }
			return this.errorCode;
	}
	public static void main(String[] args) throws WorkflowException {

		try {
			try {

				if (true){
					a();
				}

			} catch (Exception e) {
				throw new WorkflowException(e.getMessage());
			}
		} catch (WorkflowException e) {
			System.out.println(e.getMessage());
		}

	}

	public static void  a(){
		throw new RuntimeException("abc");
	}
}
