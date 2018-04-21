package com.emi.wms.basedata.action;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.struts2.util.TokenHelper;

import net.sf.json.JSONArray;

import com.emi.common.action.BaseAction;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.DateUtil;
import com.emi.rm.bean.RM_Role;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.core.format.EmiJsonArray;
import com.emi.sys.init.Config;
import com.emi.wms.basedata.service.GroupService;
import com.emi.wms.bean.AaGroup;
import com.emi.wms.bean.AaProviderCustomer;
import com.emi.wms.bean.AaProviderCustomerAddbook;
import com.emi.wms.bean.MesWmStandardprocess;
import com.emi.wms.bean.GroupPerson;

public class GroupAction extends BaseAction {
private GroupService groupService;

public GroupService getGroupService() {
	return groupService;
}
public void setGroupService(GroupService groupService) {
	this.groupService = groupService;
}
	
	/**
	 * 
	 * @category 列表
	 * 2015年3月22日 上午8:17:57
	 * @author 杨峥铖
	 * @return
	 */
		public String getgroupList(){
			try {
				int pageIndex = getPageIndex();								//页码，从1开始
				int pageSize = getPageSize();
				String orgId=getSession().get("OrgId").toString();
				String sobId=getSession().get("SobId").toString();
				String condition=" and AaGroup.sobGid='"+sobId+"' and AaGroup.orgGid='"+orgId+"'";
				PageBean grouplist = groupService.getgroupList(pageIndex, pageSize,condition);
				setRequstAttribute("data", grouplist);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "groupList";
		}
		/**
		 * 
		 * @category 添加页面
		 * 2016年5月6日 下午2:09:26
		 * @author 杨峥铖
		 * @return
		 */
		public String toAddgroup(){
			return "groupAdd";
			}
		
		/**
		 * 
		 * @category 修改页面
		 * 2016年5月6日 下午2:09:40
		 * @author 杨峥铖
		 * @return
		 */
		public String toUpdategroup(){
			Map aagroup = groupService.findgroup(getParameter("groupid"));
			Map usersMap = groupService.getRoleUsersMap(getParameter("groupid"));	//查找已有的用户id和用户名
			getRequest().setAttribute("userIds", usersMap.get("ids"));
			getRequest().setAttribute("userNames", usersMap.get("names"));
			getRequest().setAttribute("gids", usersMap.get("gids"));
			setRequstAttribute("aagroup", aagroup);
			return "groupEdit";
		}
		
		/**
		 * 
		 * @category 添加
		 * 2016年5月6日 下午2:10:20
		 * @author 杨峥铖
		 */
		public void addgroup(){
			try {
				//防止重复提交
				if(!TokenHelper.validToken()){
					getResponse().getWriter().write("success");
					return;
				}
				AaGroup aagroup = new AaGroup();
				aagroup.setGid(UUID.randomUUID().toString());
				aagroup.setBarcode(getParameter("barcode"));
				aagroup.setCode(getParameter("code"));
				aagroup.setOrgGid(getSession().get("OrgId").toString());
				aagroup.setSobGid(getSession().get("SobId").toString());
				aagroup.setGroupname(getParameter("groupname"));
				aagroup.setWorkcenterId(getParameter("workCenterId"));
				aagroup.setIsDelete(0);
				List<GroupPerson> grouppersonlist = new ArrayList<GroupPerson>();
				String userIds = getParameter("userIds");
				String [] userId = userIds.split(",");
				for(int i=0;i<userId.length;i++){
					GroupPerson groupperson = new GroupPerson();
					groupperson.setPersongid(userId[i]);
					groupperson.setGroupgid(aagroup.getGid());;
					grouppersonlist.add(groupperson);
				}
				boolean suc = groupService.addgroup(aagroup);
				boolean suc1 = groupService.addgrouplist(grouppersonlist);
				if(suc&&suc1){
					getResponse().getWriter().write("success");
				}else{
					getResponse().getWriter().write("error");
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	
	/**
	 * 
	 * @category 修改
	 * 2015年3月22日 上午8:18:58
	 * @author 杨峥铖
	 */
		public void updategroup(){
			try {
				String msg = "";
				boolean pass = true;
				if(pass){
					AaGroup aagroup = new AaGroup();
					aagroup.setGid(getParameter("gid"));
					aagroup.setBarcode(getParameter("barcode"));
					aagroup.setCode(getParameter("code"));
					aagroup.setOrgGid(getSession().get("OrgId").toString());
					aagroup.setSobGid(getSession().get("SobId").toString());
					aagroup.setGroupname(getParameter("groupname"));
					aagroup.setWorkcenterId(getParameter("workCenterId"));
					aagroup.setIsDelete(0);
					List<GroupPerson> grouppersonlist = new ArrayList<GroupPerson>();
					String userIds = getParameter("userIds");
					String [] userId = userIds.split(",");
					for(int i=0;i<userId.length;i++){
						GroupPerson groupperson = new GroupPerson();
						groupperson.setPersongid(userId[i]);
						groupperson.setGroupgid(aagroup.getGid());;
						grouppersonlist.add(groupperson);
					}
					groupService.deletegroupperson(aagroup.getGid());
					boolean suc = groupService.updategroup(aagroup);
					boolean suc1 = groupService.addgrouplist(grouppersonlist);
					if(suc&&suc1){
						getResponse().getWriter().write("success");
					}else{
						getResponse().getWriter().write("保存失败");
					}
				}else{
					getResponse().getWriter().write(msg);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	/**
	 * 
	 * @category 删除
	 * 2015年12月10日 下午5:19:10
	 * @author 杨峥铖
	 */
		public void deletegroup(){
			try {
				String gid = getParameter("gid");
				groupService.deletegroup(gid);
				responseWrite("success");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * @category  选择用户界面
		 * 2014年9月18日 上午10:40:03 
		 * @author 朱晓陈
		 * @return
		 */
		public String toSelectUser(){
			String selectedUserIds = getParameter("selectedUserIds");
			String keyWord=getParameter("keyWord");
			String condition="";
			if(!CommonUtil.isNullObject(keyWord)){
				condition=" and (perCode like '%"+keyWord+"%' or perName like '%"+keyWord+"%')";
			}
			List list = groupService.getUserList(-1, -1,condition);//用户列表
			getRequest().setAttribute("userList", list);
			getRequest().setAttribute("selectedUserIds", selectedUserIds);
			return "selectUser";
		}

}
