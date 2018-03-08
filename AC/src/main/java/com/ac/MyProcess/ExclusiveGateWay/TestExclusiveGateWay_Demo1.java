package com.ac.MyProcess.ExclusiveGateWay;
/*
*2018年3月8日
*Administrator
*下午1:50:15
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

//排他网关
public class TestExclusiveGateWay_Demo1 {
	ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * 部署流程定义
	 */
	@Test
	public void deplayMentProcessDefinition_InputStream() {
		InputStream inputStreamBpmn = this.getClass() // 获取当前class对象
				.getResourceAsStream("exclusiveGateWay.bpmn"); // 获取指定文件资源流
		InputStream inputStreamPng = this.getClass() // 获取当前class对象
				.getResourceAsStream("exclusiveGateWay.png"); // 获取指定文件资源流
		
		Deployment deploy = defaultProcessEngine.getRepositoryService() // 与流程定义和对象部署相关的service
				.createDeployment() // 创建一个部署对象
				.name("排他网关1") // 添加部署的名称
				.addInputStream("exclusiveGateWay.bpmn", inputStreamBpmn)
				.addInputStream("exclusiveGateWay.png", inputStreamPng)
				.deploy(); // 完成部署

		System.out.println("部署id:" + deploy.getId());
		System.out.println("部署名称:" + deploy.getName());
		

	}
	/**
	 * 部署流程定义
	 */
	@Test
	public void deleteMentProcessDefinition_InputStream() {
		String id = "3501";
		defaultProcessEngine.getRepositoryService() // 与流程定义和对象部署相关的service
				.deleteDeployment(id);
		System.out.println("删除流程实例id:"+id);
	}

	/**
	 * 启动流程实例
	 */
	@Test
	public void startProcessInstsnce() {
		String key = "exclusiveGateWay";
		ProcessInstance startProcessInstanceByKey = defaultProcessEngine.getRuntimeService()// 与正在执行的流程实例和执行对象相关的service
				.startProcessInstanceByKey(key);
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
		String assignee = "赵晓柳";//默认财务 赵晓柳  申请人张爸爸
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
	 * 完成我的任务
	 * 
	 */

	@Test
	public void completeMyTask() {
		String taskId = "3604";
		//完成任务的同时，使用流程变量，使用流程变量来指定完成任务后，下一个连线
		
		
		Map<String, Object> variables = new HashMap<>();
		variables.put("money", 500);
		defaultProcessEngine.getTaskService().complete(taskId, variables);;
		System.out.println("完成任务的ID：" + taskId);
		;

	}
	@Test
	public void completeMyTask1() {
		String taskId = "3804";
		//完成任务的同时，使用流程变量，使用流程变量来指定完成任务后，下一个连线
		defaultProcessEngine.getTaskService().complete(taskId);;
		System.out.println("完成任务的ID：" + taskId);
		;

	}
}
