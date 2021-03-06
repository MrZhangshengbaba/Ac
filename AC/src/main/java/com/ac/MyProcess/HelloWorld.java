package com.ac.MyProcess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

/*
*2018年2月28日
*Administrator
*下午2:03:19
*/
public class HelloWorld {

	ProcessEngine defaultProcessEngine = ProcessEngines.getDefaultProcessEngine();

	/**
	 * 部署流程定义
	 */
	@Test
	public void deplayMentProcessDefinition() {
		// zipInputStream = new ZipInputStream(new
		// FileInputStream("/diagrams/HelloWorld.zip"));
		InputStream inputStream = this.getClass() // 获取当前class对象
				.getClassLoader() // 获取类加载器
				.getResourceAsStream("diagrams/HelloWorld.zip"); // 获取指定文件资源流
		ZipInputStream zipInputStream = new ZipInputStream(inputStream); // 实例化zip输入流对象
		Deployment deploy = defaultProcessEngine.getRepositoryService() // 与流程定义和对象部署相关的service
				.createDeployment() // 创建一个部署对象
				.name("测试从zip读取文件") // 添加部署的名称
			//	.addInputStream(resourceName, zipInputStream)
				.addZipInputStream(zipInputStream)
				/*
				 * .addClasspathResource("diagrams/MyProcess1.bpmn")// 从classpath中加载，一次只能加载一个文件
				 * .addClasspathResource("diagrams/MyProcess1.png")// 从classpath中加载，一次只能加载一个文件
				 */ .deploy(); // 完成部署

		System.out.println("部署id" + deploy.getId());
		System.out.println("部署名称" + deploy.getName());

	}

	/**
	 * 启动流程实例
	 */
	@Test
	public void startProcessInstsnce() {
		String key = "myProcess1";
		ProcessInstance startProcessInstanceByKey = defaultProcessEngine.getRuntimeService()// 与正在执行的流程实例和执行对象相关的service
				.startProcessInstanceByKey(key);
		System.out.println("流程实例id" + startProcessInstanceByKey.getId());// 流程实例id
		System.out.println("流程定义id" + startProcessInstanceByKey.getProcessDefinitionId());// 流程定义id
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
