package com.emi.wms.basedata.service;

import java.util.List;

import com.emi.rm.bean.RM_Settings;
import com.emi.wms.basedata.dao.ClassifyDao;
import com.emi.wms.bean.Classify;

/**
 * 属性方案，基本属性，属性类别
 * @author Administrator
 *
 */
public class ClassifyService {
	private ClassifyDao classifyDao;

	public ClassifyDao getClassifyDao() {
		return classifyDao;
	}

	public void setClassifyDao(ClassifyDao classifyDao) {
		this.classifyDao = classifyDao;
	}

	/**
	 * @category 获取类别列表数据
	 * 2015年12月11日 上午8:52:15 
	 * @author 朱晓陈
	 * @param typeCode 类别类型码
	 * @param orgId 
	 * @param sobId 
	 * @return
	 */
	public List<Classify> getClassifyList(String typeCode, String sobId, String orgId) {
		return classifyDao.getClassifyList(typeCode,sobId,orgId);
	}

	/**
	 * @category 类别类型列表
	 * 2015年12月11日 上午9:04:03 
	 * @author 朱晓陈
	 * @return
	 */
	public List<RM_Settings> getTypeList() {
		return classifyDao.getTypeList();
	}

	public Classify findClassify(String gid) {
		return classifyDao.findClassify(gid);
	}

	/**
	 * @category 根据id找到父类别
	 * 2015年12月11日 下午4:05:24 
	 * @author 朱晓陈
	 * @param gid
	 * @return
	 */
	public Classify findParentClassify(String gid) {
		return classifyDao.findParentClassify(gid);
	}

	/**
	 * @category 更新分类
	 * 2015年12月11日 下午4:25:46 
	 * @author 朱晓陈
	 * @param classify
	 */
	public void updateClassify(Classify classify) {
		classifyDao.updateClassify(classify);
	}

	/**
	 * @category 添加分类
	 * 2015年12月11日 下午4:34:48 
	 * @author 朱晓陈
	 * @param classify
	 */
	public void addClassify(Classify classify) {
		classifyDao.addClassify(classify);
	}

	public void deleteClassify(String gid) {
		classifyDao.deleteClassify(gid);
	}

	/**
	 * @category 子分类
	 * 2015年12月14日 上午8:56:35 
	 * @author 朱晓陈
	 * @param gid
	 * @param sobId
	 * @param orgId
	 * @param stylegid 
	 * @return
	 */
	public List<Classify> getChildClassify(String gid, String sobId,
			String orgId, String stylegid) {
		return classifyDao.getChildClassify(gid,sobId,orgId,stylegid);
	}

	
}
