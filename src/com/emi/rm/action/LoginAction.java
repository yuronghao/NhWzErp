package com.emi.rm.action;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;

import net.sf.json.JSONObject;

import com.emi.cache.service.CacheCtrlService;
import com.emi.common.action.BaseAction;
import com.emi.common.util.Base64;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.DBHelper;
import com.emi.common.util.PasswordUtil;
import com.emi.rm.bean.RM_Right;
import com.emi.rm.service.LoginService;
import com.emi.rm.service.RightService;
import com.emi.rm.service.UserRightService;
import com.emi.sys.core.format.EmiJsonObj;
import com.emi.wms.bean.AaPerson;
import com.emi.wms.bean.YmUser;

public class LoginAction extends BaseAction {
	private static final long serialVersionUID = -6535548050311457497L;
	private RightService rightService;
	private UserRightService userRightService;
	private LoginService loginService;
	private CacheCtrlService cacheCtrlService;
	static String sql = null;  
    static DBHelper db1 = null;  
    static ResultSet ret = null;
	
	public UserRightService getUserRightService() {
		return userRightService;
	}

	public void setUserRightService(UserRightService userRightService) {
		this.userRightService = userRightService;
	}

	public RightService getRightService() {
		return rightService;
	}

	public void setRightService(RightService rightService) {
		this.rightService = rightService;
	}

	public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
		this.cacheCtrlService = cacheCtrlService;
	}

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}

	public void getcookie() {
		try {
			Cookie cookies[] = getRequest().getCookies();
			Cookie sCookie = null;
			String svalue = null;
			String sname = null;
			String outname = null;
			String outpsw = null;
			for (int i = 0; i < cookies.length; i++) {
				sCookie = cookies[i];
				sname = sCookie.getName();
				svalue = sCookie.getValue();
				if (sname.equals("username")) {
					outname = svalue;
				}
				if (sname.equals("password")) {
					outpsw = svalue;
				}
			}
			Map outmap = new HashMap();
			outmap.put("username", outname);
			outmap.put("password", Base64.getFromBase64(outpsw));
			getResponse().getWriter().write(
					JSONObject.fromObject(outmap).toString());
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	public void checkLogin_(){
		try {
			getResponse().getWriter().write("login");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void checkLogin() {
		PrintWriter out = null;
		try {
			String userName = getRequest().getParameter("userName");
			String passWord = getRequest().getParameter("password");
			String pswcheck = getRequest().getParameter("pswcheck");
			YmUser user = loginService.getUserByName(userName);
			String reqString = "";
			if (user == null) {
				reqString = "你输入的账号不正确！";
			} else {
				String pswd = CommonUtil.Obj2String(user.getPassWord());
				if (!pswd.equals(
						PasswordUtil.generatePassword(passWord))) {
	
					reqString = "您输入的密码错误！";
				} else {
					//登录成功后，更新最后一次登录时间
					Date loginTime = new Date();
					user.setLastLoginTime(loginTime);
					loginService.updateUser(user);
					
					//cookie中保存用户名和密码
					Cookie cookie_name =new Cookie("username", URLEncoder.encode(userName,"UTF-8"));
					Cookie cookie_psw =new Cookie("password",passWord);
					Base64.getBase64(passWord);
					cookie_psw =new Cookie("password",Base64.getBase64(passWord));
					if(pswcheck!=null&&pswcheck.equals("on")){
					cookie_name.setMaxAge(365*24*60*60);
					cookie_psw.setMaxAge(365*24*60*60);
					getResponse().addCookie(cookie_name);
					getResponse().addCookie(cookie_psw);
					}else{
						cookie_name.setMaxAge(0);
						cookie_psw.setMaxAge(0);
						getResponse().addCookie(cookie_name);
						getResponse().addCookie(cookie_psw);   
	
					}
					reqString = "login";
					// 在session中加入内容
					String userId = CommonUtil.Obj2String(user.getGid());
					AaPerson person = loginService.personInfo(userId);
					getSession().put("UserId", userId);
					getSession().put("UserName", user.getUserName());
					getSession().put("Person", person);
					getSession().put("User", user);
					getSession().put("isAdmin", user.getIsAdmin());//是否是管理员  0否  1超级管理员 2:帐套管理员
					getSession().put("OrgId", user.getOrggid());//组织id
					getSession().put("SobId", user.getSobgid());//账套id
					getSession().put("OrgName", user.getOrgName());//组织名称
					getSession().put("SobName", user.getSobName());//账套id
					
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					getSession().put("LoginTime", sdf.format(loginTime));
					
//					cacheCtrlService.setSession(userId);
				}
			}
			out = getResponse().getWriter();
			out.write(reqString);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			this.responseWrite("系统异常");
		} finally {
			if (out != null) {
				out.close();
			}
		}

	}
	
	/**
	 * @category 跳转到首页 
	 * 2014年8月22日 下午3:38:35 
	 * @author 朱晓陈
	 * @return
	 */
	public String toIndex(){
		//获取菜单权限
		List<RM_Right> rights = null;
		YmUser userMap = (YmUser) getSession().get("User");
		String sysName = getParameter("sysName");
		String userId = CommonUtil.Obj2String(getSession().get("UserId"));
		
		//得到账号绑定的系统
//		sysName = styleService.getBindSys(userId);
		Map rightFuns = new HashMap();
		Map<String,List<RM_Right>> funMap = new HashMap<String,List<RM_Right>>();
//		rights = rightService.getRightList(sysName);
		if(userMap!=null && userMap.getIsAdmin()!=null && userMap.getIsAdmin()==1){
			rights = rightService.getRightList(sysName,false);
		}else{
			rights = userRightService.getRights4User(userId, sysName,false);
			//获取功能操作权限
//			rightFuns = userRightService.getRightFuns(userId,sysName);
			funMap = userRightService.getFunRight(userId, sysName,rights);
			
		}
		List list  = new ArrayList();
		if(userMap!=null && userMap.getIsAdmin()!=null && userMap.getIsAdmin()==1){
			sql="SELECT rightCode FROM RM_Right rmright WHERE rmright.rightType =1 and rmright.isShow=1 ";
		}else{
			sql = "SELECT rightCode FROM RM_RoleRight roleright  LEFT JOIN RM_Right rmright on roleright.rightId = rmright.gid WHERE roleright.roleId IN (SELECT roleId FROM RM_RoleUser WHERE userId = '"+userId+"') and roleright.useFuns > 0 and rmright.rightType =1 and rmright.isShow=1 ";
		}
		try {
			db1 = new DBHelper(sql);
			ret = db1.pst.executeQuery();
			while (ret.next()) {  
				RM_Right permiss = new RM_Right(ret.getString(1));
				list.add(permiss);
			}
			ret.close();  
			db1.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//菜单权限json
		String json = userRightService.rightsToJson(rights);
//		List<Map<String,Object>> tree = userRightService.rightsToTree(rights);
		//(新)功能权限的json
//		String funJson = 
		//收藏的菜单
		List<Map> mfs = rightService.getMenuFavorites(userId);
		getRequest().setAttribute("favorites", mfs);
		getRequest().setAttribute("favoritesSize", mfs.size());
		
		getSession().put("privileges", list);
		getSession().put("rightFuns", JSONObject.fromObject(rightFuns).toString());
		getSession().put("isAdmin", userMap.getIsAdmin());
		getSession().put("rightFunMap", funMap);
		getSession().put("rightFunJson", EmiJsonObj.fromObject(funMap).toString());
		getRequest().setAttribute("rights", json);
		return "index";
	}

	/**
	 * @category 显示左侧菜单 
	 * 2014年8月15日 上午9:33:32 
	 * @author 朱晓陈
	 * @return
	 */
	public String showLeft(){
		String userId = CommonUtil.Obj2String(getSession().get("UserId"));
		
		//获取菜单权限
		List<RM_Right> rights = null;
		Map userMap = (Map) getSession().get("User");
		String sysName = getParameter("sysName");
		
		//得到账号绑定的系统
//				sysName = styleService.getBindSys(userId);
		Map rightFuns = new HashMap();
		Map<String,List<RM_Right>> funMap = new HashMap<String,List<RM_Right>>();
//				rights = rightService.getRightList(sysName);
		if(userMap!=null && userMap.get("isAdmin")!=null && Integer.parseInt(userMap.get("isAdmin").toString())==1){
			rights = rightService.getRightList(sysName,false);
		}else{
			rights = userRightService.getRights4User(userId, sysName,false);
			//获取功能操作权限
//					rightFuns = userRightService.getRightFuns(userId,sysName);
			funMap = userRightService.getFunRight(userId, sysName,rights);
			
		}
		
		//菜单权限json
		String json = userRightService.rightsToJson(rights);
		//(新)功能权限的json
//				String funJson = 
		
		getSession().put("rightFuns", JSONObject.fromObject(rightFuns).toString());
		getSession().put("rightFunMap", funMap);
		getSession().put("rightFunJson", EmiJsonObj.fromObject(funMap).toString());
		getRequest().setAttribute("rights", json);
		//收藏的菜单
		List<Map> mfs = rightService.getMenuFavorites(userId);
		getRequest().setAttribute("favorites", mfs);
		return "left";
	}
	
	
	/**
	 * @category 退出系统 
	 * 2014年8月26日 下午1:27:46 
	 * @author 朱晓陈
	 * @return
	 */
	public String logout(){
		getRequest().getSession().invalidate();
		return "login";
	}
	/**
	 * 密码修改界面
	 * @category 
	 * 2014年12月22日 上午9:14:47 
	 * @author 朱晓陈
	 * @return
	 */
	public String toModifyPassword(){
		return "password";
	}
	
	/**
	 * @category 修改密码 
	 * 2014年8月26日 下午4:32:18 
	 * @author 朱晓陈
	 */
	public void modifyPassword(){
		try {
			String userId = CommonUtil.Obj2String(getSession().get("UserId"));
			String oldPassword = getParameter("oldPassword");
			String newPassword = getParameter("newPassword");
			int status = 0;  	//0失败，1成功，2原始密码不对
			boolean check = userRightService.checkPassword(userId,oldPassword);
			if(check){
				String newSecPwd = PasswordUtil.generatePassword(newPassword);
				YmUser user = new YmUser();
				user.setGid(userId);
				user.setPassWord(newSecPwd);
				boolean suc = userRightService.updateUser(user);
				if(suc){
					status = 1;
				}else{
					status = 0;
				}
			}else{
				status = 2;
			}
			getResponse().getWriter().write("{\"success\":\""+status+"\"}");
		} catch (Exception e) {
			this.writeError();
			e.printStackTrace();
		}
	}
}
