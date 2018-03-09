package com.ac.MyProcess.groupUser;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

/*
*2018年3月9日
*Administrator
*下午4:48:07
*/
public class TestGroupUser {
	ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * 部署流程定义
	 */
	@Test
	public void deplayMentProcessDefinition() {
		InputStream inputStreamBpmn = this.getClass() // 获取当前class对象
				.getResourceAsStream("groupUser.bpmn"); // 获取指定文件资源流
		InputStream inputStreamPng = this.getClass() // 获取当前class对象
				.getResourceAsStream("groupUser.png"); // 获取指定文件资源流

		Deployment deploy = defaultProcessEngine.getRepositoryService() // 与流程定义和对象部署相关的service
				.createDeployment() // 创建一个部署对象
				.name("组任务任务groupUser") // 添加部署的名称
				.addInputStream("groupUser.bpmn", inputStreamBpmn).addInputStream("groupUser.png", inputStreamPng)
				.deploy(); // 完成部署

		System.out.println("部署id:" + deploy.getId());
		System.out.println("部署名称:" + deploy.getName());
		/* 添加用户组 */
		IdentityService identityService = defaultProcessEngine.getIdentityService();
		identityService.saveGroup(new GroupEntity("部门经理"));
		identityService.saveGroup(new GroupEntity("总经理"));
		/* 添加用户*/
		identityService.saveUser(new UserEntity("张三"));
		identityService.saveUser(new UserEntity("张大大"));
		identityService.saveUser(new UserEntity("王八"));
		
		
		identityService.createMembership("张大大","部门经理");
		identityService.createMembership("张三","总经理");
		identityService.createMembership("王八", "部门经理");
		System.out.println("添加部门组织机构成功");
	}

	/**
	 * 启动流程实例
	 */
	@Test
	public void startProcessInstsnce() {
		String key = "groupUserTest";
		ProcessInstance startProcessInstanceByKey = defaultProcessEngine.getRuntimeService()// 与正在执行的流程实例和执行对象相关的service
				.startProcessInstanceByKey(key);
		System.out.println("流程实例id:" + startProcessInstanceByKey.getId());// 流程实例id
		System.out.println("流程定义id:" + startProcessInstanceByKey.getProcessDefinitionId());// 流程定义id
	}

	/**
	 * 查询当前人的个人任务
	 * 
	 */

	@Test
	public void findMyPersontask() {
		String assignee = "王五";
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
		String taskId = "302";
		defaultProcessEngine.getTaskService().complete(taskId);
		System.out.println("完成任务的ID：" + taskId);
		;

	}
}
