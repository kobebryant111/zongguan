package com.tansun.wf.camunda.bpmn.instance;

public class DefineEvent {
	
    public  static final String  TASK_EVENT_CREATE="taskCreate";
    public  static final String  TASK_EVENT_COMPLETE="taskComplete";
    public  static final String  EXECTION_EVENT_START="exectionStart";
    public  static final String  EXECTION_EVENT_END="exectionEnd";
    
    
    public  static  String  getEventType(String  event) {
    	if (event != null) {
    		if (event.equals("7")) {
    			return DefineEvent.TASK_EVENT_CREATE;
    		}else if (event.equals("10")) {
    			return DefineEvent.TASK_EVENT_COMPLETE;
    		}else if ("1".equals(event)) {
    			return DefineEvent.EXECTION_EVENT_START;
    		}else if ("4".equals(event)) {
    			return DefineEvent.EXECTION_EVENT_END;
    		}
    		
    	}
    	return  null;
    }

}
