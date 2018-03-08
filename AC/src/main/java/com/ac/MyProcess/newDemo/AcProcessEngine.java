package com.ac.MyProcess.newDemo;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

/*
*2018年3月6日
*Administrator
*下午5:55:20
*/
public class AcProcessEngine {
	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	RuntimeService runtimeService = processEngine.getRuntimeService();
	/*查询引擎已知的部署和过程定义。
	暂停并激活部署作为一个整体或特定的过程定义。悬浮意味着不能对它们进行进一步的操作，而激活则是相反的操作。
	检索各种资源，如在部署过程中包含的文件或由引擎自动生成的过程图。
	检索流程定义的POJO版本，可以使用Java而不是xml对流程进行内省*/
	RepositoryService repositoryService = processEngine.getRepositoryService();
	/*查询分配给用户或组的任务。
	创造新的独立任务。这些任务与流程实例无关。
	处理分配给哪个用户的任务，或者哪些用户在某种程度上参与了任务。
	声称并完成一项任务。声明意味着某人决定成为任务的受让人，这意味着该用户将完成任务。完成意味着完成任务的工作。通常这是一种形式的填充。*/
	TaskService taskService = processEngine.getTaskService();
	
	ManagementService managementService = processEngine.getManagementService();
	
	/*标识服务非常简单。它允许管理(创建、更新、删除、查询、…)的组和用户。重要的是要理解，
	 * Activiti实际上并没有在运行时对用户进行任何检查。例如，任务可以分配给任何用户，
	 * 但是引擎不验证该用户是否知道系统。这是因为Activiti引擎还可以与诸如LDAP、Active Directory等服务一起使用。*/
	IdentityService identityService = processEngine.getIdentityService();
	/*历史服务公开了Activiti引擎收集的所有历史数据。在执行过程中,大量的数据可以保存等引擎
	 * (这是可配置的)流程实例启动时间,谁做了哪些任务,用了多长时间来完成任务,在每个流程实
	 * 例路径之后,等。此服务公开主要查询功能来访问这些数据。
	 * */
	HistoryService historyService = processEngine.getHistoryService();
	/*FormService是一个可选的服务。也就是说，如果没有它，Activiti可以完美地使用，
	 * 不需要牺牲任何功能。该服务引入了start表单和任务表单的概念。start表单是在流程实例启动之前向用户显示的表单，
	 * 而任务表单是用户希望完成表单时显示的表单。Activiti允许在BPMN 2.0过程定义中定义这些表单。
	 * 该服务以一种简单的方式公开这些数据。但是，这是可选的，因为表单不需要嵌入到流程定义中*/
	FormService formService = processEngine.getFormService();
	//	DynamicBpmnService dynamicBpmnService = processEngine.getDynamicBpmnService();
	
	public void repositoryServiceAPI() {
		 Deployment deploy = repositoryService   //与流程定义和对象部署相关的service
				 .createDeployment() //创建一个部署对象
				 .name("入门程序") //添加部署的名称
				 .addClasspathResource("diagrams/MyProcess1.bpmn")// 从classpath中加载，一次只能加载一个文件
				 .addClasspathResource("diagrams/MyProcess1.png")// 从classpath中加载，一次只能加载一个文件
				 .deploy(); // 完成部署
		 String processDefinitionId = "myProcess1:2:504";
		 repositoryService.activateProcessDefinitionById(processDefinitionId);
	}
	public void taskServiceAPI() {
		String taskId = "";
		String groupId = "";
		taskService.addCandidateGroup(taskId, groupId);
	}

	public void identityServiceAPI() {
		
	}
	@Test
	public void formServiceAPI() {
		String processDefinitionId = "myProcess1:2:504";
		Object renderedStartForm = formService.getRenderedStartForm(processDefinitionId);
		System.out.println("###################"+renderedStartForm);
	}
	public void runtimeServiceAPI() {
		
	}
	
	
}
