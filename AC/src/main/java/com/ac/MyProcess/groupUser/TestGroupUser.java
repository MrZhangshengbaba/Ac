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
import org.activiti.engine.task.IdentityLink;
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

	//查询组任务列表
		@Test
		public void findGroupList(){
			String userId = "张大大";//小张，小李可以查询结果，小王不可以，因为他不是部门经理
			List<Task> list = defaultProcessEngine.getTaskService()//
			                .createTaskQuery()//
			                .taskCandidateUser(userId)//指定组任务查询
			                .list();
			for(Task task:list ){
				System.out.println("id="+task.getId());
				System.out.println("name="+task.getName());
				System.out.println("assinee="+task.getAssignee());
				System.out.println("assinee="+task.getCreateTime());
				System.out.println("executionId="+task.getExecutionId());
				System.out.println("##################################");
				
			}
		}
		
		//查询组任务成员列表
		@Test
		public void findGroupUser(){
			String taskId = "4408";
			List<IdentityLink> list = defaultProcessEngine.getTaskService()//
			                .getIdentityLinksForTask(taskId);
			for(IdentityLink identityLink:list ){
				System.out.println("userId="+identityLink.getUserId());
				System.out.println("taskId="+identityLink.getTaskId());
				System.out.println("piId="+identityLink.getProcessInstanceId());
				System.out.println("######################");
			}
		}
		
		//完成任务
		@Test
		public void completeTask(){
			String taskId = "5108";
			defaultProcessEngine.getTaskService()//
						.complete(taskId);//
			System.out.println("完成任务");
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
