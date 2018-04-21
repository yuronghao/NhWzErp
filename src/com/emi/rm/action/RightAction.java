package com.emi.rm.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.util.TokenHelper;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.emi.common.action.BaseAction;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.Constants;
import com.emi.rm.bean.RM_MenuFavorite;
import com.emi.rm.bean.RM_Right;
import com.emi.rm.bean.RM_Settings;
import com.emi.rm.bean.RightSystem;
import com.emi.rm.service.RightService;
import com.emi.sys.core.format.EmiJsonArray;
@SuppressWarnings({ "unchecked", "rawtypes" })
public class RightAction extends BaseAction {

	private static final long serialVersionUID = 3106458076295899264L;
	private RightService rightService;
	private RM_Right right;		//权限实体

	public RM_Right getRight() {
		return right;
	}

	public void setRight(RM_Right right) {
		this.right = right;
	}

	public RightService getRightService() {
		return rightService;
	}

	public void setRightService(RightService rightService) {
		this.rightService = rightService;
	}

	/**
	 * @category 添加权限数据
	 * 2014年8月11日 下午1:19:30 
	 * @author 朱晓陈
	 * @return
	 */
	public void addRight(){
		try {
			//防止重复提交
//			if(!TokenHelper.validToken()){
//				getResponse().getWriter().write("success");
//			}
//			if(Integer.compare(1, right.getRightType())!=0){
				//判断权限代码是否已存在
				boolean rcExist = rightService.hasSameCode(right.getRightCode(),"");
				if(rcExist){
					getResponse().getWriter().write("sameCode");
					return;
				}	
//			}
			String superiorRightId = this.right.getSuperiorRightId(); //上一层权限id，空则表示是顶层
			if(CommonUtil.isNullString(superiorRightId)){
				superiorRightId = null;
				right.setSuperiorRightId(superiorRightId);
			}
			RM_Right p_right = rightService.findRight(superiorRightId);//上级权限
			int p_level = 0;
			boolean update_suc = true;
			boolean add_suc = false;
			if(update_suc){
				if(p_right!=null){
					p_level = p_right.getLevelNum()==null?1:p_right.getLevelNum();//上级权限level
				}
				//设置保存的权限层级
				this.right.setLevelNum(p_level+1);
				//设置保存的权限是否末级：是
				this.right.setIsLast(1);
//				this.right.setRightFlag(0);
				//【保存】
				add_suc = rightService.addRight(right);
			}
			if(add_suc && !CommonUtil.isNullString(superiorRightId)){
				//获取上级权限
				if(p_right != null){
					//设置上级的权限是否末级为：否
					p_right.setIsLast(0);
					//【更新】
					update_suc = rightService.updateRight(p_right,false);
				}
			}
			if(add_suc && update_suc){
				getResponse().getWriter().write("success");
			}else{
				getResponse().getWriter().write("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	
	/**
	 * @category 更新权限 
	 * 2014年8月11日 下午2:53:37 
	 * @author 朱晓陈
	 * @return
	 */
	public String updateRight(){
		try {
			boolean suc = false;
			boolean nullflag = false;
//			if (Integer.compare(1, right.getRightType()) != 0) {
				// 判断权限代码是否已存在
				boolean rcExist = rightService.hasSameCode(right.getRightCode(),right.getGid());
				if (rcExist) {
					getResponse().getWriter().write("sameCode");
					return null;
				}
//			}
			if (!CommonUtil.isNullString(getParameter("upgid"))) {
				String upgid = getParameter("upgid");
				RM_Right upright = new RM_Right();
				if (upgid.equals("null")) {
					upright.setGid(right.getGid());
					upright.setSuperiorRightId(null);
					nullflag = true;
					suc = rightService.updateRight(upright, nullflag);
				} else {
					upright.setGid(upgid);
					upright.setIsLast(0);
					suc = rightService.updateRight(upright, false);
					// 是否有子集
					List<RM_Right> subrightList = rightService.getRightList(
							right.getOwnerSys(), getParameter("superupgid"));// 权限列表
																				// Constants.RIGHT_WEB
					if (subrightList.size() <= 1) {
						RM_Right superupright = new RM_Right();
						superupright.setGid(getParameter("superupgid"));
						superupright.setIsLast(1);
						suc = rightService.updateRight(superupright, false);
					}
				}
			} else {
				if (CommonUtil.isNullString(right.getSuperiorRightId())) {
					right.setSuperiorRightId(null);
				}
				suc = rightService.updateRight(right, nullflag);
			}

			if (suc) {
				getResponse().getWriter().write("success");
			} else {
				getResponse().getWriter().write("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * @category 删除权限 
	 * 2014年8月15日 下午2:25:28 
	 * @author 朱晓陈
	 * @return
	 */
	public String deleteRight(){
		try {
			String rightId = getParameter("gid"); //上一层权限id，空则表示是顶层
			RM_Right right = rightService.findRight(rightId);//权限对象
			String superiorRightId = right.getSuperiorRightId();
			boolean update_suc = true;
			boolean del_suc = false;
			//【删除】
			del_suc = rightService.deleteRight(rightId);
			if(del_suc && !CommonUtil.isNullString(superiorRightId)){
				//查询父节点是否还有子节点
				boolean hashChildRight = rightService.hashChildRight(superiorRightId);
				//获取上级权限
				RM_Right p_right = rightService.findRight(superiorRightId);
				//上级的层级
				//设置上级的权限是否末级
				p_right.setIsLast(hashChildRight?0:1);
				//【更新】
				update_suc = rightService.updateRight(p_right,false);
			}
			if(del_suc && update_suc){
				getResponse().getWriter().write("success");
			}else{
				getResponse().getWriter().write("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return null;
	}
	/**
	 * @category 得到权限树
	 * 2014年8月7日 下午3:11:26 
	 * @author 朱晓陈
	 */
	public String getRightTree(){
		try {
			String sysName = getParameter("ownerSys");
			String nodeId = getParameter("gid");
			List<RM_Right> rightList = null;
			if("1".equals(getParameter("isChangeFather"))){
				//换父节点的树，选中的节点过滤掉
				String selectedId = getParameter("selectedId");
				String condition = " and gid!='"+selectedId+"'";
				rightList = rightService.getRightList(sysName,nodeId,condition);//权限列表 Constants.RIGHT_WEB
			}else{
				rightList = rightService.getRightList(sysName,nodeId);//权限列表 Constants.RIGHT_WEB
			}
			
			JSONArray array = EmiJsonArray.fromObject(rightList);
			/*String rootPath = getRequest().getSession().getServletContext().getContextPath();
			for(Object obj:array){
				JSONObject jo = (JSONObject) obj;
				if(jo.getInt("rightType")==1){
					jo.put("icon", rootPath+"/images/common/ui_buttons.png");
				}else{
					jo.put("icon", rootPath+"/images/common/item.png");
				}
			}*/
			String json = array.toString();
			getResponse().getWriter().write(json);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getRightTree_(){
		try {
			String sysName = getParameter("systemName");
			List<RM_Right> rightList = rightService.getRightList("wms");//权限列表 Constants.RIGHT_WEB
			
			String idProp = "gid";					//id属性名
			String pIdProp = "superiorRightId";		//父id属性名
			//得到分级后的权限
			List<Map<String,Object>> newSortList = CommonUtil.hierarchicalChild(rightList,idProp,pIdProp);
			String json = EmiJsonArray.fromObject(newSortList).toString();
			getResponse().getWriter().write(json);
//			getRequest().setAttribute("json", json);
		} catch (Exception e) {
			writeError();
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @category 权限树页面 
	 * 2014年8月14日 上午8:28:57 
	 * @author 朱晓陈
	 * @return
	 */
	public String toRightTree(){
		try {
			List<Map> maps = new ArrayList<Map>();
			List<RightSystem> systems = rightService.getSystems();
			for(RightSystem rs : systems){
				Map map = new HashMap();
				map.put("gid", "");
				map.put("rightName", rs.getFullName());
				map.put("isParent", true);
				map.put("ownerSys", rs.getShortName());
				map.put("isRoot", true);
				maps.add(map);
			}
//		String zNodes = "[{gid:null,rightName:'仓储管理系统',isParent:true,ownerSys:'wms',isRoot:true},"
//				+ "{gid:null,rightName:'生产管理系统',isParent:true,ownerSys:'mes',isRoot:true}]";
			getRequest().setAttribute("zNodes", JSONArray.fromObject(maps).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "rightTree";
	}
	
	/**
	 * @category 分配权限用的树 
	 * 2014年9月9日 下午6:01:52 
	 * @author 朱晓陈
	 * @return
	 */
	public String toRightTree4Auth(){
		try {
			List<Map> maps = new ArrayList<Map>();
			List<RightSystem> systems = rightService.getSystems();
			for(RightSystem rs : systems){
				Map map = new HashMap();
				map.put("gid", "");
				map.put("rightName", rs.getFullName());
				map.put("isParent", true);
				map.put("ownerSys", rs.getShortName());
				map.put("isRoot", true);
				map.put("isShow", 1);
				maps.add(map);
			}
			getRequest().setAttribute("zNodes", JSONArray.fromObject(maps).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "rightTree4Auth";
	}
	
	/**
	 * @category 权限表单 
	 * 2014年8月14日 上午8:29:19 
	 * @author 朱晓陈
	 * @return
	 */
	public String toRightForm(){
		try {
			String rightId = getParameter("gId");
			String ownerSys = getParameter("ownerSys");
			String isRoot = getParameter("isRoot");
			RM_Right right = new RM_Right();
			if("true".equals(isRoot)){
				//根目录（wms,mes等）
				RM_Settings setting = rightService.getSettings(Constants.SET_SYSTEMS);//系统设置-权限管理的系统
				if(setting != null){
					String value = setting.getParamValue();
					String[] systems = value.split(";");
					for(String str : systems){
						String[] sys = str.split(",");
						if(sys[0].equals(ownerSys)){
							right.setOwnerSys(sys[0]);
							right.setRightName(sys[1]);
							right.setRightCode(sys[0]);
							right.setRightUrl("/");
						}
					}
				}
			}else{
				if(rightId!=null){
					right = rightService.findRight(rightId);
				}
			}
			
			RM_Right p_right = rightService.findSuperiorRight(rightId);//有上级权限
			if(p_right!=null){
				String superiorRightName = p_right.getRightName();
				String superiorRightId = p_right.getGid();
				getRequest().setAttribute("superiorRightName", superiorRightName);
				getRequest().setAttribute("superiorRightId", superiorRightId);
			}
			List<RightSystem> systems = rightService.getSystems();//系统列表
			//查询权限的控制范围 'on':控制菜单和增删改操作，'off':只控制菜单
			String rangeSwitch = rightService.getRightRangeSwitch();
			
			getRequest().setAttribute("rangeSwitch", rangeSwitch);
			getRequest().setAttribute("systems", systems);
			getRequest().setAttribute("right", right);
			getRequest().setAttribute("isRoot", isRoot);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if("1".equals(getParameter("rightType"))){
			//按钮编辑页面
			List<Map> cssList = rightService.getBtnCssTemplate();
			getRequest().setAttribute("cssList", cssList);
			return "rightFormButton";
		}
		//菜单编辑页面
		return "rightForm";
	}
	
	//添加权限
	public String toAddRight(){
		try {
			String rightId = getParameter("gId");			//父权限id
			String ownerSys = getParameter("ownerSys");		//所属系统
			
			RM_Right right = rightService.findRight(rightId);//有上级权限
			if(right!=null){
				String superiorRightName = right.getRightName();
				getRequest().setAttribute("superiorRightName", superiorRightName);
				getRequest().setAttribute("superiorRightId", rightId);
				getRequest().setAttribute("superiorRightCode", right.getRightCode());
			}
			List<RightSystem> systems = rightService.getSystems();//系统列表
			//查询权限的控制范围 'on':控制菜单和增删改操作，'off':只控制菜单
			String rangeSwitch = rightService.getRightRangeSwitch();
			
			getRequest().setAttribute("rangeSwitch", rangeSwitch);
			getRequest().setAttribute("systems", systems);
			getRequest().setAttribute("ownerSys", ownerSys);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "rightAdd";
	}
	
	//添加权限
	public String toAddRightButton(){
		try {
			String rightId = getParameter("gId");			//父权限id
			String ownerSys = getParameter("ownerSys");		//所属系统
			
			RM_Right right = rightService.findRight(rightId);//有上级权限
			if(right!=null){
				String superiorRightName = right.getRightName();
				getRequest().setAttribute("superiorRightName", superiorRightName);
				getRequest().setAttribute("superiorRightId", rightId);
				getRequest().setAttribute("superiorRightCode", right.getRightCode());
			}
			List<RightSystem> systems = rightService.getSystems();//系统列表
			//查询权限的控制范围 'on':控制菜单和增删改操作，'off':只控制菜单
			String rangeSwitch = rightService.getRightRangeSwitch();
			
			getRequest().setAttribute("rangeSwitch", rangeSwitch);
			getRequest().setAttribute("systems", systems);
			getRequest().setAttribute("ownerSys", ownerSys);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "rightAddButton";
	}
	
	//添加权限按钮
	public String toAddRightBtn(){
		try {
			String rightId = getParameter("gId");			//父权限id
			String ownerSys = getParameter("ownerSys");		//所属系统
			
			RM_Right right = rightService.findRight(rightId);//有上级权限
			if(right!=null){
				String superiorRightName = right.getRightName();
				getRequest().setAttribute("superiorRightName", superiorRightName);
				getRequest().setAttribute("superiorRightId", rightId);
			}
			List<RightSystem> systems = rightService.getSystems();//系统列表
			//查询权限的控制范围 'on':控制菜单和增删改操作，'off':只控制菜单
			String rangeSwitch = rightService.getRightRangeSwitch();
			
			getRequest().setAttribute("rangeSwitch", rangeSwitch);
			getRequest().setAttribute("systems", systems);
			getRequest().setAttribute("ownerSys", ownerSys);
			
			//按钮编辑页面
			List<Map> cssList = rightService.getBtnCssTemplate();
			getRequest().setAttribute("cssList", cssList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "buttonAdd";
	}
	
	//权限frame
	public String toRightFrame(){
		return "right";
	}
	public String updateUpRight(){
		try {
			List<Map> maps = new ArrayList<Map>();
			List<RightSystem> systems = rightService.getSystems();
			for(RightSystem rs : systems){
				if(rs.getShortName().equals(getParameter("sys"))){
				Map map = new HashMap();
				map.put("gid", null);
				map.put("rightName", rs.getFullName());
				map.put("isParent", true);
				map.put("ownerSys", rs.getShortName());
				map.put("isRoot", true);
				maps.add(map);
				}
			}
//		String zNodes = "[{gid:null,rightName:'仓储管理系统',isParent:true,ownerSys:'wms',isRoot:true},"
//				+ "{gid:null,rightName:'生产管理系统',isParent:true,ownerSys:'mes',isRoot:true}]";
			getRequest().setAttribute("zNodes", JSONArray.fromObject(maps).toString());
			getRequest().setAttribute("selectedId", getParameter("gid"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "updateUpRight";
	}
	/**
	 * @category 权限选择界面 
	 * 2014年9月10日 上午8:39:24 
	 * @author 朱晓陈
	 * @return
	 */
	public String toRightAuth(){
		try {
			String rightId = getParameter("gId");
			String ownerSys = getParameter("ownerSys");
			String isRoot = getParameter("isRoot");
			 
			List<RM_Right> childRights = rightService.getRightList(ownerSys, rightId);
			getRequest().setAttribute("childRights", childRights);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "rightAuth";
	}
	
	/**
	 * @category 演示系统页面的跳转 
	 * 2014年10月9日 下午5:10:21 
	 * @author 朱晓陈
	 * @return
	 */
	public String pageJump(){
		String rightId = getParameter("rightId");
		String ownerSys = getParameter("ownerSys");
		String url = getParameter("url");
		
		String condition = " and rightType=1";
		List<RM_Right> btnList = rightService.getRightList(ownerSys, rightId,condition);
		getRequest().setAttribute("btnList", btnList);
		getRequest().setAttribute("url", url);
		return "pageJump";
	}
	
	/**
	 * @category 更新收藏 
	 * 2014年10月10日 下午5:10:54 
	 * @author 朱晓陈
	 */
	public void updateFavorite(){
		try {
			String gid = getParameter("gid");
			String menuName = getParameter("menuName");
			
			RM_MenuFavorite mf = new RM_MenuFavorite();
			mf.setGid(gid);
			mf.setMenuName(menuName);
			boolean suc = rightService.updateFavorite(mf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @category 删除收藏 
	 * 2014年10月10日 下午5:11:04 
	 * @author 朱晓陈
	 */
	public void deleteFavorite(){
		try {
			String gid = getParameter("gid");
			boolean suc = rightService.deleteFavorite(gid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @category 添加到我的收藏 
	 * 2014年10月11日 下午3:39:37 
	 * @author 朱晓陈
	 */
	public void addToMyFavorite(){
		try {
			String rightId = getParameter("rightId");
			String rightName = getParameter("rightName");
			String userId = getSession().get("UserId").toString();
			RM_MenuFavorite mf = new RM_MenuFavorite();
			mf.setMenuId(rightId);
			mf.setMenuName(rightName);
			mf.setMenuIndex(1);
			mf.setUserId(userId);
			boolean suc = rightService.addFavorite(mf);
			Map map = new HashMap();
			if(suc){
				map.put("success", "1");
				map.put("favId", mf.getGid());
				getResponse().getWriter().write(JSONObject.fromObject(map).toString());
			}else{
				map.put("success", "0");
				getResponse().getWriter().write(JSONObject.fromObject(map).toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
