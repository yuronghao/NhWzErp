package com.emi.rm.action;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.emi.common.action.BaseAction;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.Constants;
import com.emi.rm.service.UserRightService;
import com.emi.sys.core.format.EmiJsonArray;
import com.emi.sys.init.Config;

public class UserRightAction extends BaseAction {

	private static final long serialVersionUID = -2100832030798307514L;
	private UserRightService userRightService;

	public UserRightService getUserRightService() {
		return userRightService;
	}

	public void setUserRightService(UserRightService userRightService) {
		this.userRightService = userRightService;
	}

	
	/**
	 * @category 保存用户单独分配的权限 
	 * 2014年8月14日 下午3:32:48 
	 * @author 朱晓陈
	 */
	public void saveUserRight(){
		try {
			String userId = getParameter("userId");						//角色id
			String rightIds_add = getParameter("rightIds_add");			//权限id，多个用逗号隔开
			String rightIds_del = getParameter("rightIds_del");
			String enterpriseId = getParameter("enterpriseId");			//所在企业id
			boolean suc = userRightService.saveUserRight(userId,rightIds_add,rightIds_del,enterpriseId);
			if (suc) {
				getResponse().getWriter().write("{\"success\":\"1\"}");
			} else {
				getResponse().getWriter().write("{\"success\":\"0\"}");
			}
		} catch (Exception e) {
			writeError();
			e.printStackTrace();
		}
	}
	
	/**
	 * @category 得到人员的所有权限id 
	 * 2014年8月14日 下午5:10:18 
	 * @author 朱晓陈
	 */
	public void getRights4User(){
		try {
			String userId = getParameter("userId");
			String enterpriseId = getParameter("enterpriseId");
			String rightIds = userRightService.getRightIds4User(userId,enterpriseId);
			getResponse().getWriter().write("{\"success\":\"1\",\"rightIds\":"+rightIds+"}");
		} catch (Exception e) {
			this.writeError();
			e.printStackTrace();
		}
	}
	
}
