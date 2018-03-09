package com.ac.MyProcess.receiveTask;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

/*
*2018年3月8日
*Administrator
*下午5:34:17
*/
public class TestReceiveTask {
	ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * 部署流程定义
	 */
	@Test
	public void deplayMentProcessDefinition_InputStream() {
		InputStream inputStreamBpmn = this.getClass() // 获取当前class对象
				.getResourceAsStream("receiveTask.bpmn"); // 获取指定文件资源流
		InputStream inputStreamPng = this.getClass() // 获取当前class对象
				.getResourceAsStream("receiveTask.png"); // 获取指定文件资源流

		Deployment deploy = defaultProcessEngine.getRepositoryService() // 与流程定义和对象部署相关的service
				.createDeployment() // 创建一个部署对象
				.name("接收活动任务receiveTask") // 添加部署的名称
				.addInputStream("receiveTask.bpmn", inputStreamBpmn)
				.addInputStream("receiveTask.png", inputStreamPng).deploy(); // 完成部署

		System.out.println("部署id:" + deploy.getId());
		System.out.println("部署名称:" + deploy.getName());

	}

	/**
	 * 删除流程定义
	 */
	@Test
	public void deleteMentProcessDefinition_InputStream() {
		String id = "4601";
		defaultProcessEngine.getRepositoryService() // 与流程定义和对象部署相关的service
				.deleteDeployment(id);
		System.out.println("删除流程实例id:" + id);
	}

	/**
	 * 启动流程实例
	 */
	@SuppressWarnings("unused")
	@Test
	public void startProcessInstsnce() {
		String key = "receiveTask";
		ProcessInstance processinstance = defaultProcessEngine.getRuntimeService()// 与正在执行的流程实例和执行对象相关的service
				.startProcessInstanceByKey(key);
		System.out.println("流程实例id：" + processinstance.getId());// 流程实例id
		System.out.println("流程定义id：" + processinstance.getProcessDefinitionId());// 流程定义id

		System.out.println("流程启动###################");
		Map<String, ? extends Object> variables;
		/* 查询执行对象*/
		Execution execution = defaultProcessEngine.getRuntimeService()
		.createExecutionQuery()
		.processInstanceId(processinstance.getId())
		.activityId("receivetask1")
		.singleResult();
		System.out.println("执行对象ID:"+execution.getId());
		/*使用流程变量设置当日销售额，用来传递业务参数*/
		defaultProcessEngine.getRuntimeService().setVariable(execution.getId(), "当日销售额",21000);
		/*向后执行一步*/
		defaultProcessEngine.getRuntimeService().signal(execution.getId());
		
/*		=================================================*/
		Execution execution1 = defaultProcessEngine.getRuntimeService()
				.createExecutionQuery()
				.processInstanceId(processinstance.getId())
				.activityId("receivetask2")
				.singleResult();
		/*使用流程变量设置当日销售额，用来传递业务参数*/
		Integer value = (Integer) defaultProcessEngine.getRuntimeService().getVariable(execution1.getId(), "当日销售额");
		/*向后执行一步*/
		defaultProcessEngine.getRuntimeService().signal(execution1.getId());
		 System.out.println("给老板发送短信：内容，当日销售额："+value);  
         
	     System.out.println("########################"+execution1.getId());
	     System.out.println("判断是流程是否结束——————————开始");
	        //9.判断流程是否结束  
	        ProcessInstance nowPi = defaultProcessEngine.getRuntimeService()  
	                .createProcessInstanceQuery()
	                
	                .processInstanceId(processinstance.getId()) 
	                
	                .singleResult();  
	        
	       // System.out.println(nowPi+""+nowPi.getId());
	        if(nowPi == null){  
	            System.out.println("流程结束");  
	        } 
	        
	        System.out.println("判断是流程是否结束——————————结束");
	    }  	
		
	
}
