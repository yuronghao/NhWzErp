package com.emi.rm.service;

import java.util.Date;

import com.emi.rm.dao.LoginDao;
import com.emi.wms.bean.AaPerson;
import com.emi.wms.bean.YmUser;
@SuppressWarnings({"unchecked","rawtypes"})
public class LoginService{
	private LoginDao loginDao;

	public LoginDao getLoginDao() {
		return loginDao;
	}
	public void setLoginDao(LoginDao loginDao) {
		this.loginDao = loginDao;
	}
	public AaPerson personInfo(String userId) {
		return loginDao.personInfo(userId);
	}
	public YmUser getUserByName(String userName) {
		return loginDao.getUserByName(userName);
	}
	
	/**
	 * @category 更新用户
	 * 2015年12月9日 上午10:18:00 
	 * @author 朱晓陈
	 * @param user
	 */
	public void updateUser(YmUser user) {
		loginDao.updateUser(user);
	}
	


}
