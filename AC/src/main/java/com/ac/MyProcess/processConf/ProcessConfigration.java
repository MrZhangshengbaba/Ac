package com.ac.MyProcess.processConf;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;

/*
*2018年3月6日
*Administrator
*上午11:28:36
*/
public class ProcessConfigration {
	
	ProcessEngine processEngine = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration()
			  .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_FALSE)
			  .setJdbcUrl("jdbc:h2:mem:my-own-db;DB_CLOSE_DELAY=1000")
			  .setJobExecutorActivate(false)
			  .buildProcessEngine();
}
