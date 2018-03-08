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
		String id = "3501";
		defaultProcessEngine.getRepositoryService() // 与流程定义和对象部署相关的service
				.deleteDeployment(id);
		System.out.println("删除流程实例id:" + id);
	}

	/**
	 * 启动流程实例
	 */
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
		.activityId("servicetask1")
		.singleResult();
		/*使用流程变量设置当日销售额，用来传递业务参数*/
		defaultProcessEngine.getRuntimeService().setVariable(execution.getId(), "汇总当日销售额",21000);
		/*向后执行一步*/
		defaultProcessEngine.getRuntimeService().signal(execution.getId());
		
		
		Execution execution1 = defaultProcessEngine.getRuntimeService()
				.createExecutionQuery()
				.processInstanceId(processinstance.getId())
				.activityId("servicetask1")
				.singleResult();
		/*使用流程变量设置当日销售额，用来传递业务参数*/
		defaultProcessEngine.getRuntimeService().setVariable(execution1.getId(), "给老板发送短信","已汇总");
		/*向后执行一步*/
		defaultProcessEngine.getRuntimeService().signal(execution1.getId());
				
		
	}
}
