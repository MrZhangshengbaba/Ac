package com.ac.MyProcess.personTask.a;
/*
*2018年3月9日
*Administrator
*下午3:00:38
*/

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

public class TestPersonTask {

	ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * 部署流程定义
	 */
	@Test
	public void deplayMentProcessDefinition_InputStream() {
		InputStream inputStreamBpmn = this.getClass() // 获取当前class对象
				.getResourceAsStream("personTask.bpmn"); // 获取指定文件资源流
		InputStream inputStreamPng = this.getClass() // 获取当前class对象
				.getResourceAsStream("personTask.png"); // 获取指定文件资源流

		Deployment deploy = defaultProcessEngine.getRepositoryService() // 与流程定义和对象部署相关的service
				.createDeployment() // 创建一个部署对象
				.name("个人任务personTask") // 添加部署的名称
				.addInputStream("personTask.bpmn", inputStreamBpmn).addInputStream("personTask.png", inputStreamPng)
				.deploy(); // 完成部署

		System.out.println("部署id:" + deploy.getId());
		System.out.println("部署名称:" + deploy.getName());
	}

	/**
	 * 启动流程实例
	 */
	@Test
	public void startProcessInstsnce() {
		String key = "task";
		
		//启动流程实例，同时设置流程变量，用来指定组任务的办理人
				Map<String, Object> variables = new HashMap<String, Object>();
				variables.put("userIDs", "大大,小小,中中");
		ProcessInstance startProcessInstanceByKey = defaultProcessEngine.getRuntimeService()// 与正在执行的流程实例和执行对象相关的service
				.startProcessInstanceByKey(key,variables);
		
		
		System.out.println("流程实例id：" + startProcessInstanceByKey.getId());// 流程实例id
		System.out.println("流程定义id：" + startProcessInstanceByKey.getProcessDefinitionId());// 流程定义id

		System.out.println("流程启动###################");

	}
	/**
	 * 查询当前人的个人任务
	 * 
	 */

	@Test
	public void findMyPersontask() {
		String assignee = "张三丰";
		List<Task> list = defaultProcessEngine.getTaskService()// 与正在执行的任务相关的 表act_ru_task
				.createTaskQuery() // 创建任务查询对象
				.taskAssignee(assignee) // 指定个人任务查询，指定班里人
				.list();
		if (list != null && list.size() > 0) {
			for (Task task : list) {
				System.out.println("任务id:" + task.getId());
				System.out.println("任务名称:" + task.getName());
				System.out.println("任务的创建时间:" + task.getCreateTime());
				System.out.println("任务办理人:" + task.getAssignee());
				System.out.println("流程实例id:" + task.getProcessInstanceId());
				System.out.println("执行对象id:" + task.getExecutionId());
				System.out.println("流程定义id:" + task.getProcessDefinitionId());
				System.out.println("##########################################################");
			}
		}
	}
	/**
	 * 启动流程实例
	 */
	@Test
	public void compeleteProcessInstsnce() {
		String taskId = "";
		defaultProcessEngine.getTaskService().complete(taskId);
		System.out.println("完成任务的ID：" + taskId);
		;
	}

}
