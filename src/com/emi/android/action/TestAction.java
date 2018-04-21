package com.emi.android.action;

import com.emi.common.action.BaseAction;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.servicedata.service.TaskService;

public class TestAction extends BaseAction{
	
	
	private TaskService taskService;
	
	
	
	public TaskService getTaskService() {
		return taskService;
	}


	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}


	public void test (){
		
//		PageBean pg=taskService.getTaskList(0, 0, null);
//		System.out.println(pg);
		
	}

	
	
	

}
