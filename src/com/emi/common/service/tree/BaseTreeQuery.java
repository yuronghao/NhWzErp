package com.emi.common.service.tree;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.emi.cache.service.CacheCtrlService;
import com.emi.common.dao.EmiPluginDao;
import com.emi.common.util.CommonUtil;
import com.emi.sys.init.Config;
/**
 * @Title:树查询基础类
 * @Copyright: Copyright (c) v1.0
 * @Company: 江苏一米智能科技股份有限公司
 * @project name: LugErp
 * @author: zhuxiaochen
 * @version: V1.0
 * @time: 2016年4月22日 上午9:51:14
 */
public class BaseTreeQuery {
	protected EmiPluginDao emiPluginDao;
	protected int pageIndex=1;
	protected int pageSize;
	protected String rootName = "全部";
	protected HttpServletRequest request;
	protected String sobId;//账套id
	protected String orgId;//组织id
	private CacheCtrlService cacheCtrlService;
	
	
	public BaseTreeQuery(HttpServletRequest request) {
		this.request = request;
		
		/*
		 * 分页参数初始化
		 */
		String page = request.getParameter("pno");
		if (!CommonUtil.isNullString(page)) {
			pageIndex = Integer.parseInt(page);
		}
		pageIndex = pageIndex < 0 ? 1 : pageIndex;
		pageSize = Config.PAGESIZE_WEB;
		
		ServletContext sc = request.getServletContext();
		ApplicationContext ac = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
		emiPluginDao =  (EmiPluginDao) ac.getBean("emiPluginDao");
		cacheCtrlService =(CacheCtrlService)ac.getBean("cacheCtrlService");
		
		sobId = CommonUtil.Obj2String(request.getSession().getAttribute("SobId"));
		orgId = CommonUtil.Obj2String(request.getSession().getAttribute("OrgId"));
	}

	public EmiPluginDao getEmiPluginDao() {
		return emiPluginDao;
	}

	public void setEmiPluginDao(EmiPluginDao emiPluginDao) {
		this.emiPluginDao = emiPluginDao;
	}

	public String getRootName() {
		return rootName;
	}

	public void setRootName(String rootName) {
		this.rootName = rootName;
	}

	public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
		this.cacheCtrlService = cacheCtrlService;
	}

	public CacheCtrlService getCacheCtrlService() {
		return cacheCtrlService;
	}
	
	
}
