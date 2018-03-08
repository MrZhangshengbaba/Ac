package com.ac.MyProcess.testStart;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

/*
*2018年3月8日
*Administrator
*下午5:15:00
*/
public class TestStart {
	ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * 部署流程定义
	 */
	@Test
	public void deplayMentProcessDefinition_InputStream() {
		InputStream inputStreamBpmn = this.getClass() // 获取当前class对象
				.getResourceAsStream("tesStart.bpmn"); // 获取指定文件资源流
		InputStream inputStreamPng = this.getClass() // 获取当前class对象
				.getResourceAsStream("tesStart.png"); // 获取指定文件资源流

		Deployment deploy = defaultProcessEngine.getRepositoryService() // 与流程定义和对象部署相关的service
				.createDeployment() // 创建一个部署对象
				.name("开始活动") // 添加部署的名称
				.addInputStream("tesStart.bpmn", inputStreamBpmn).addInputStream("v.png", inputStreamPng).deploy(); // 完成部署

		System.out.println("部署id:" + deploy.getId());
		System.out.println("部署名称:" + deploy.getName());

	}

	/**
	 * 启动流程实例
	 */
	@Test
	public void startProcessInstsnce() {
		String key = "testStart";
		ProcessInstance startProcessInstanceByKey = defaultProcessEngine.getRuntimeService()// 与正在执行的流程实例和执行对象相关的service
				.startProcessInstanceByKey(key);
		System.out.println("流程实例id：" + startProcessInstanceByKey.getId());// 流程实例id
		System.out.println("流程定义id：" + startProcessInstanceByKey.getProcessDefinitionId());// 流程定义id
		System.out.println("流程启动###################");
		//查询正在执行的流程实例(根据流程实例id查询)
		ProcessInstance singleResult = defaultProcessEngine.getRuntimeService().createProcessInstanceQuery()
				.processInstanceId(startProcessInstanceByKey.getId()).singleResult();
		System.out.println(singleResult);
		if (singleResult == null) {//无正在执行的流程实例
			//查询历史表
			HistoricProcessInstance singleResult2 = defaultProcessEngine.getHistoryService()
			.createHistoricProcessInstanceQuery()
			.processInstanceId(startProcessInstanceByKey.getId()).singleResult();
			System.out.println(singleResult2.getId()+  "开始时间"  +singleResult2.getStartTime());
		}
	}

}
