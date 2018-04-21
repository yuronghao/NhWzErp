package com.emi.sys.core.bean;

import java.util.List;

/**
 * 带分页的实体类
 * @author 朱晓陈
 * 2014年8月5日 上午11:24:01
 */
public class PageBean {
	/**
	 * 页码
	 */
	private int pageIndex;
	
	/**
	 * 每页条数
	 */
	private int pageSize;
	
	/**
	 * 总页数
	 */
	private int pageCount;
	
	/**
	 * 数据总条数
	 */
	private int totalCount;
	
	/**
	 * 数据列表
	 */
	private List list;

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}
	
}
