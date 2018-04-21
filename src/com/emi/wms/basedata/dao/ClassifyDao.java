package com.emi.wms.basedata.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.rm.bean.RM_Settings;
import com.emi.wms.bean.Classify;

/**
 *	类别统一管理
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class ClassifyDao extends BaseDao {

	/**
	 * @category 类别数据列表
	 * 2015年12月11日 上午8:52:59 
	 * @author 朱晓陈
	 * @param typeCode
	 * @param orgId 
	 * @param sobId 
	 * @return
	 */
	public List<Classify> getClassifyList(String typeCode, String sobId, String orgId) {
		typeCode = typeCode.replaceAll(",", "','");
		String sql = "select "+CommonUtil.colsFromBean(Classify.class)+" from classify where (isDelete=0 or isDelete is null) ";
		if(CommonUtil.notNullString(typeCode)){
			sql += " and styleGid in ('"+typeCode+"') ";
		}
		if(CommonUtil.notNullString(sobId)){
			sql += " and sobid='"+sobId+"' ";
		}
		if(CommonUtil.notNullString(orgId)){
			sql += " and orgid='"+orgId+"' ";
		}
		return this.emiQueryList(sql, Classify.class);
	}

	/**
	 * @category 类别类型列表
	 * 2015年12月11日 上午9:04:50 
	 * @author 朱晓陈
	 * @return
	 */
	public List<RM_Settings> getTypeList() {
		String sql = "select "+CommonUtil.colsFromBean(RM_Settings.class)+" from RM_Settings where setName='classifyType' order by paramValue";
		return this.emiQueryList(sql, RM_Settings.class);
	}

	/**
	 * @category 获取单个分类信息
	 * 2015年12月11日 下午3:54:15 
	 * @author 朱晓陈
	 * @param gid
	 * @return
	 */
	public Classify findClassify(String gid) {
		return (Classify) this.emiFind(gid, Classify.class);
	}

	/**
	 * @category 根据id获取父类别
	 * 2015年12月11日 下午4:05:55 
	 * @author 朱晓陈
	 * @param gid
	 * @return
	 */
	public Classify findParentClassify(String gid) {
		String sql = "select "+CommonUtil.colsFromBean(Classify.class)+" from classify where gid=(select parentid from classify where gid='"+gid+"') ";
		return (Classify) this.emiQuery(sql, Classify.class);
	}

	/**
	 * @category 更新分类
	 * 2015年12月11日 下午4:26:08 
	 * @author 朱晓陈
	 * @param classify
	 */
	public void updateClassify(Classify classify) {
		this.emiUpdate(classify);
	}

	/**
	 * @category 添加分类
	 * 2015年12月11日 下午4:35:12 
	 * @author 朱晓陈
	 * @param classify
	 */
	public void addClassify(Classify classify) {
		this.emiInsert(classify);
	}

	/**
	 * @category 删除分类
	 * 2015年12月11日 下午5:03:25 
	 * @author 朱晓陈
	 * @param gid
	 */
	public void deleteClassify(String gid) {
//		String sql = "delete from classify where gid='"+gid+"'";
		String sql = "update classify set isDelete=1 where gid='"+gid+"'";
		this.update(sql);
	}

	/**
	 * @category 子分类
	 * 2015年12月14日 上午9:00:01 
	 * @author 朱晓陈
	 * @param stylegid 
	 * @return
	 */
	public List<Classify> getChildClassify(String gid, String sobId,
			String orgId, String stylegid) {
		Map match = new HashMap();
		match.put("childrenNum", "Classify.childrenNum");
		String sql = "select "+CommonUtil.colsFromBean(Classify.class,"c")+",(select count(1) from classify c1 where c1.parentid=c.gid) childrenNum from classify c where (isDelete=0 or isDelete is null) ";
		if(CommonUtil.notNullString(gid)){
			sql += " and parentid ='"+gid+"' ";
		}
		if(CommonUtil.notNullString(stylegid)){
			sql += " and stylegid ='"+stylegid+"' ";
		}
		sql += " and sobid='"+sobId+"' ";
		sql += " and orgid='"+orgId+"' ";
		return this.emiQueryList(sql, Classify.class,match);
	}
	
}
