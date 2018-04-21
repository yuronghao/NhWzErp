package com.emi.flow.main.util;

public class FlowNode {
	/**
	 * 默认节点名称
	 */
	public static final String DEFAULT_NAME = "新建步骤";
	
	/**
	 * 默认节点类型 0：普通节点 
	 */
	public static final int DEFAULT_TYPE = 0;		
	
	/**
	 * 默认路由类型 0：并行
	 */
	public static final int DEFAULT_ROUTE = 0;
	
	/**
	 * 默认节点的id
	 */
	public static final String DEFAULT_NODEID = "0";
	
	/**
	 * 默认执行人类型  0：无  1:单人 2：角色 3：组
	 */
	public static final int DEFAULT_DOTYPE = 0;
		
	/**
	 * 默认图标
	 */
	public static final String DEFAULT_ICON = "icon-user";
	
	/**
	 * 图标-开始节点
	 */
	public static final String ICON_START = "icon-play";
	
	/**
	 * 默认样式
	 */
	public static final String DEFAULT_STYLE = "min-width:120px;height:28px;line-height:28px;color:#0e76a8;";
	
	/**
	 * 默认样式-宽度
	 */
	public static final int STYLE_WIDTH = 120;
	
	/**
	 * 默认样式-高度
	 */
	public static final int STYLE_HEIGHT = 28;
	
	/**
	 * 自动接收人类型-无
	 */
	public static final int DOTYPE_NONE = 0;
	
	/**
	 * 自动接收人类型-指定人
	 */
	public static final int DOTYPE_PERSON = 1;
	
	/**
	 * 自动接收人类型-指定角色
	 */
	public static final int DOTYPE_ROLE = 2;
	
	/**
	 * 自动接收人类型-指定组
	 */
	public static final int DOTYPE_GROUP = 3;
	
	/**
	 * 自动接收人类型-指定部门
	 */
	public static final int DOTYPE_DEPT = 4;
	
	/**
	 * 自动发送方式-根据项目自动选取项目经理
	 */
	public static final int SMARTTYPE_PM = 1;
	
	/**
	 * 自动发送方式-发送对象只有一个人时，自动发送
	 */
	public static final int SMARTTYPE_SINGLE = 2;
	
	public static String generateStyle(int left, int top ,int width, int height){
		String style = "min-width:"+width+"px;height:"+height+"px;line-height:"+height+"px;color:#0e76a8;";
		style += "left:"+left+"px;top:"+top+"px;";
		return style;
	}
	
	public static String generateStyle(int left , int top){
		return generateStyle(left, top, STYLE_WIDTH, STYLE_HEIGHT);
	}
}
