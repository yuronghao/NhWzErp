package com.emi.sys.init;

import java.io.File;

import javax.servlet.http.HttpServlet;

import com.emi.sys.util.SysPropertites;

public class InitApplication extends HttpServlet{
	public void init(){
		System.out.println("=========================================");
		System.out.println("开始初始化!");
		this.initConfig();		
	}

	private void initConfig(){
		System.out.println("开始读取配置信息......");
		Config.getInstance();
		//创建图片目录
		String dirPath = SysPropertites.get("file.root");
		File file = new File(dirPath);
		if(!file.exists()){
			file.mkdirs();
		}
		System.out.println("一米移动ERP  配置完毕!");		
	}

	public void destroy(){		
		System.out.println("系统 销毁!");		
		super.destroy();
	}
}
