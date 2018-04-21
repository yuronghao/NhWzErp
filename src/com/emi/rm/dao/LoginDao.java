package com.emi.rm.dao;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.wms.bean.AaPerson;
import com.emi.wms.bean.YmUser;
@SuppressWarnings({ "rawtypes", "unchecked" })
public class LoginDao extends BaseDao {

	/**
	 * @category 获取人员信息
	 * 2015年12月9日 上午9:39:12 
	 * @author 朱晓陈
	 * @param userId
	 * @return
	 */
	public AaPerson personInfo(String userId) {
		String sql = "select "+CommonUtil.colsFromBean(AaPerson.class)+" from AA_Person where useruid='"+userId+"' and (isDel=0 or isDel is null)";
		return (AaPerson) this.emiQuery(sql, AaPerson.class);
	}

	/**
	 * @category 根据用户名获取用户
	 * 2015年12月9日 上午9:38:54 
	 * @author 朱晓陈
	 * @param userName
	 * @return
	 */
	public YmUser getUserByName(String userName) {
		Map match = new HashMap();
		match.put("orgName", "orgName");
//		match.put("sobName", "sobName");
		String sql = "select "+CommonUtil.colsFromBean(YmUser.class,"u")+",o.orgName from YM_User u "
				+ "left join AA_Org o on u.orggid=o.gid "
//				+ "left join AccountPeriod p on u.sobgid=p.gid "
				+ "where u.userCode='"+userName+"' and (u.isDelete=0 or u.isDelete is null)";
		return (YmUser) this.emiQuery(sql, YmUser.class,match);
	}

	public Object updateUser(YmUser user) {
		return this.emiUpdate(user);
	}

}
