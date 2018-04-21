package com.emi.rm.bean;

import java.io.Serializable;
import java.util.List;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 菜单收藏
 */
@EmiTable(name = "RM_MenuFavorite")
public class RM_MenuFavorite implements Serializable{
	private static final long serialVersionUID = 7522065510148954409L;

	@EmiColumn(name = "gid", ID = true)
	private String gid;
	
	@EmiColumn(name = "userId")
	private String userId;			//用户id
	
	@EmiColumn(name = "menuId")
	private String menuId;			//关联菜单id
	
	@EmiColumn(name = "menuName")
	private String menuName;		//菜单名称
	
	@EmiColumn(name = "menuIndex")
	private Integer menuIndex;		//菜单排序

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public Integer getMenuIndex() {
		return menuIndex;
	}

	public void setMenuIndex(Integer menuIndex) {
		this.menuIndex = menuIndex;
	}
	
	
}
