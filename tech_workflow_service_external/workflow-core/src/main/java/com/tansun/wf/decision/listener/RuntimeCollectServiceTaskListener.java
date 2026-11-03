//package com.tansun.wf.decision.listener;
//
// camunda-TODO
//
//import org.camunda.bpm.engine.RuntimeService;
//import org.camunda.bpm.engine.delegate.event.ActivitiEvent;
//import org.camunda.bpm.engine.delegate.event.ActivitiEventListener;
//import org.camunda.bpm.engine.delegate.event.ActivitiEventType;
//
//public class RuntimeCollectServiceTaskListener implements ActivitiEventListener{
//
//	@Override
//	public void onEvent(ActivitiEvent event) {
//		RuntimeService runtimeService = event.getEngineServices().getRuntimeService();
//		if(event.getType() == ActivitiEventType.ACTIVITY_STARTED){
//			event.getEngineServices().getRuntimeService().signal(event.getExecutionId());
//		}
//
//	}
//
//	@Override
//	public boolean isFailOnException() {
//		return false;
//	}
//
//}
