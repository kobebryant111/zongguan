package com.camunda.demo;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.Random;

/**
 * @author lijiang
 * @create 2021-03-18 11:23
 */
public class CheckWeatherDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Random random = new Random();

        execution.setVariable("name","Jack");
        execution.setVariable("weatherOk",random.nextBoolean());
    }
}
