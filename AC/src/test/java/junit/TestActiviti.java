package junit;
/*
*2018年2月28日
*Administrator
*上午10:27:44
*/


import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;

public class TestActiviti {
	/**
	 * 使用代码创建工作流所需23张表
	 * 
	 * */
	@Test
	public void createTable() {
		
	ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
	configuration.setJdbcDriver("com.mysql.jdbc.Driver");
	configuration.setJdbcUrl("jdbc:mysql://localhost:3306/activiti?createDatabaseIfNotExist=true&characterEncoding=utf-8");
	configuration.setJdbcUsername("root");
	configuration.setJdbcPassword("root");
	configuration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
	
	ProcessEngine processEngine = configuration.buildProcessEngine();
	System.out.println(processEngine);
	}
	/**
	 * 使用配置文件创建工作流所需23张表
	 * 
	 * */
	@Test
	public void createTable_2() {
	ProcessEngineConfiguration configuration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
	ProcessEngine processEngine = configuration.buildProcessEngine();
	System.out.println(processEngine);
	}
	
	
}
