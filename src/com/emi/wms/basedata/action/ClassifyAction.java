package com.emi.wms.basedata.action;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import com.emi.common.action.BaseAction;
import com.emi.common.util.CommonUtil;
import com.emi.rm.bean.RM_Settings;
import com.emi.sys.core.format.EmiJsonArray;
import com.emi.wms.basedata.service.ClassifyService;
import com.emi.wms.bean.Classify;

/**
 * 类别统一管理
 * @author 朱晓陈
 *
 */
@SuppressWarnings("serial")
public class ClassifyAction extends BaseAction {
	private ClassifyService classifyService;
	private Classify classify;
	
	public ClassifyService getClassifyService() {
		return classifyService;
	}

	public void setClassifyService(ClassifyService classifyService) {
		this.classifyService = classifyService;
	}
	
	public Classify getClassify() {
		return classify;
	}

	public void setClassify(Classify classify) {
		this.classify = classify;
	}

	/**
	 * @category frame页面
	 * 2015年12月10日 下午5:32:27 
	 * @author 朱晓陈
	 * @return
	 */
	public String toClassifyFrame(){
		
		return "classifyFrame";
	}
	
	/**
	 * @category 类别树页面
	 * 2015年12月10日 下午5:32:44 
	 * @author 朱晓陈
	 * @return
	 */
	public String toClassifyTree(){
		try {
			/*String sobId = getSession().get("SobId").toString();//账套id
			String orgId = getSession().get("OrgId").toString();//组织id
			String typeCode = "";*/
			//先查询出所有类别
			List<Classify> classifyList = new ArrayList<Classify>();;
//			List<Classify> classifyList = classifyService.getClassifyList(typeCode,sobId,orgId);
			List<RM_Settings> typeList = classifyService.getTypeList();
			//将每个列表的第一层数据的pid改成typecode
			for(RM_Settings setting : typeList){
				//改成异步展示树，以下代码注释
				/*for(Classify classify : classifyList){
					//如果找到该类别的数据，且pid是空或者0，则修改成编号
					if(setting.getParamValue().equals(classify.getStylegid())
							&& (CommonUtil.isNullString(classify.getParentid()) || classify.getParentid().equals("0"))){
						classify.setParentid(setting.getParamValue());
						break;
					}
				}*/
				Classify root = new Classify();
				root.setGid(setting.getParamValue());
				root.setClassificationname(setting.getNotes());
				root.setStylegid(setting.getParamValue());
				root.setIsRoot(true);
				root.setIsParent(true);
				classifyList.add(root);
			}
			setRequstAttribute("classifyList", EmiJsonArray.fromObject(classifyList));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "classifyTree";
	}
	
	/*
	 * 树的子节点
	 */
	public void getClassifyChildren(){
		try {
			String gid = getParameter("gid");
			String stylegid = getParameter("stylegid");
			String isRoot = getParameter("isRoot");
			if("true".equals(isRoot)){
				gid = "0";
			}
			String sobId = getSession().get("SobId").toString();//账套id
			String orgId = getSession().get("OrgId").toString();//组织id
			List<Classify> child_classify = classifyService.getChildClassify(gid,sobId,orgId,stylegid);
			responseWrite(JSONArray.fromObject(child_classify).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @category 类别信息
	 * 2015年12月10日 下午5:32:59 
	 * @author 朱晓陈
	 * @return
	 */
	public String toClassifyForm(){
		String gid = getParameter("gid");//父级id
		String isRoot = getParameter("isRoot");
		String stylegid = getParameter("stylegid");//类别编码
		Classify classify = new Classify();
		Classify f_classify =  new Classify();
		//如果是根节点，不需要查库
		if("true".equals(isRoot)){
			classify.setStylegid(stylegid);
			//根据style获取名称
			List<RM_Settings> set_list = classifyService.getTypeList();
			for(RM_Settings set : set_list){
				if(set.getParamValue().equals(stylegid)){
					classify.setParentName(set.getNotes());
					break;
				}
			}
		}else{
			classify = classifyService.findClassify(gid);
			f_classify = classifyService.findParentClassify(gid);
		}
		
		setRequstAttribute("classify", classify);
		setRequstAttribute("f_classify", f_classify);
		setRequstAttribute("isRoot", isRoot);
		return "classifyForm";
	}
	
	/**
	 * @category 添加类别页面
	 * 2015年12月11日 下午3:26:12 
	 * @author 朱晓陈
	 * @return
	 */
	public String toAddClassify(){
		String parentid = getParameter("parentid");//父级id
		String stylegid = getParameter("stylegid");//类别编码
		String isRoot = getParameter("isRoot");
		Classify f_classify =  new Classify();
		//如果是根节点，不需要查库
		if("true".equals(isRoot)){
			f_classify.setStylegid(stylegid);
			f_classify.setGid("0");
			//根据style获取名称
			List<RM_Settings> set_list = classifyService.getTypeList();
			for(RM_Settings set : set_list){
				if(set.getParamValue().equals(stylegid)){
					f_classify.setParentName(set.getNotes());
					break;
				}
			}
		}else{
			f_classify = classifyService.findClassify(parentid);
		}
		setRequstAttribute("f_classify", f_classify);
		return "classifyAdd";
	}
	
	/**
	 * @category 保存添加的类别
	 * 2015年12月11日 下午3:27:02 
	 * @author 朱晓陈
	 */
	public void addClassify(){
		try {
			String sobId = getSession().get("SobId").toString();//账套id
			String orgId = getSession().get("OrgId").toString();//组织id
			
			classify.setSobid(sobId);
			classify.setOrgid(orgId);
			classifyService.addClassify(classify);
			responseWrite("success");
		} catch (Exception e) {
			e.printStackTrace();
			this.writeError();
		}
	}
	
	/**
	 * @category 更新类别
	 * 2015年12月11日 下午3:27:37 
	 * @author 朱晓陈
	 */
	public void updateClassify(){
		try {
			classifyService.updateClassify(classify);
			responseWrite("success");
		} catch (Exception e) {
			e.printStackTrace();
			this.writeError();
		}
	}
	
	/**
	 * @category 删除分类
	 * 2015年12月11日 下午3:28:10 
	 * @author 朱晓陈
	 */
	public void deleteClassify(){
		try {
			String gid = getParameter("gid");
			classifyService.deleteClassify(gid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
