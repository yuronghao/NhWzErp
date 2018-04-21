package com.emi.wms.basedata.service;

import java.util.List;
import java.util.Map;

import com.emi.sys.core.bean.PageBean;
import com.emi.wms.basedata.dao.AttributeDao;
import com.emi.wms.bean.AaBaseAttr;
import com.emi.wms.bean.AaBaseAttrDetail;
import com.emi.wms.bean.AaCategory;
import com.emi.wms.bean.AaSoulation;
import com.emi.wms.bean.AaSoulationdetail;
import com.emi.wms.bean.AaSoulationlist;

/**
 * 属性方案，基本属性，属性类别
 * @author Administrator
 *
 */
public class AttributeService {
	public AttributeDao attributeDao;

	public AttributeDao getAttributeDao() {
		return attributeDao;
	}

	public void setAttributeDao(AttributeDao attributeDao) {
		this.attributeDao = attributeDao;
	}

	// ......................................................................属性方案....................................................................................//
	//属性方案列表
	public PageBean getAA_SoulationList(int pageIndex, int pageSize,
			String conditionSql) {
		return attributeDao.getAA_SoulationList(pageIndex, pageSize,
				conditionSql);

	}
	//根据gid查询属性方案
	public AaSoulation findAaSoulation(String gid) {
		return attributeDao.findAaSoulation(gid);
	}
	//根据GID查询属性方案中属性的集合
	public List<AaSoulationlist> findAaSoulationlist(String gid) {
		return attributeDao.findAaSoulationlist(gid);
	}
	//根据GID查询属性方案中属性相关联的属性值的集合
	public List<AaSoulationdetail> findAaSoulationdetail(String gid) {
		return attributeDao.findAaSoulationdetail(gid);
	}
    //根据条件查询基本属性集合
	public List<AaBaseAttr> getAaBaseAttr(String gid, String consql) {
		return attributeDao.getAaBaseAttr(gid, consql);
	}
    //根据条件查询基本属值集合
	public List<AaBaseAttrDetail> getAaBaseAttrDetail(String gid, String consql) {
		return attributeDao.getAaBaseAttrDetail(gid, consql);
	}
	//查询属性类别集合
	public List<AaCategory> getAaCategory() {
		return attributeDao.getAaCategory();
	}
	//添加属性方案
	public boolean addList(AaSoulation sou, List<AaSoulationlist> alist,
			List<AaSoulationdetail> dlist) {
		boolean suc = attributeDao.addAaSoulation(sou);
		if (alist.size() > 0) {
			if (suc) {
				suc = attributeDao.addAaSoulationlist(alist);
			}
		}
		if (dlist.size() > 0) {
			if (suc) {
				suc = attributeDao.addAaSoulationdetail(dlist);
			}
		}
		return suc;
	}
	//修改属性方案
	public boolean updateList(AaSoulation sou, List<AaSoulationlist> alist,
			List<AaSoulationdetail> dlist, String gid) {
		List<AaSoulationlist> solist = attributeDao.findAaSoulationlist(gid);
		String aas = "";
		for (AaSoulationlist a : solist) {
			aas += a.getGid() + ",";
		}
		boolean suc = attributeDao.updateAaSoulation(sou);
		if (suc) {
			suc = attributeDao.deleteAaSoulationlist(gid);
		}
		if (aas.length() > 0) {
			if(suc){
			suc = attributeDao.deleteAaSoulationdetail(aas.split(","));
			}
		}
		if (alist.size() > 0) {
			if (suc) {
				suc = attributeDao.addAaSoulationlist(alist);
			}
		}
		if (dlist.size() > 0) {
			if (suc) {
				suc = attributeDao.addAaSoulationdetail(dlist);
			}
		}


		return suc;
	}
	//删除属性方案
	public boolean deleteAaSoulation(String[] psList) {
	  return attributeDao.deleteAaSoulation(psList);
	}
	// ......................................................................属性....................................................................................//
	//基本属性列表	
	public PageBean getAA_BaseAttrList(int pageIndex, int pageSize,
			String conditionSql) {
		return attributeDao.getAA_BaseAttrList(pageIndex, pageSize,
				conditionSql);
	}
	//根据GID查询基本属性
	public AaBaseAttr findAaBaseAttr(String gid) {
		// TODO Auto-generated method stub
		return attributeDao.findAaBaseAttr(gid);
	}
	//添加基本属性
	public boolean addAaBaseAttr(AaBaseAttr aaBaseAttr) {
		return attributeDao.addAaBaseAttr(aaBaseAttr);
	}
	//修改基本属性
	public boolean updateAaBaseAttr(AaBaseAttr aaBaseAttr) {
		return attributeDao.updateAaBaseAttr(aaBaseAttr);
	}
	//删除基本属性
	public boolean deleteAaBaseAttr(String[] psList) {
		return attributeDao.deleteAaBaseAttr(psList);
	}

	//基本属性值列表
	public PageBean getAA_BaseAttrValList(int pageIndex, int pageSize,
			String conditionSql) {
		return attributeDao.getAA_BaseAttrValList(pageIndex, pageSize,
				conditionSql);
	}
	//根据GID查询基本属性值
	public AaBaseAttrDetail findAaBaseValAttr(String gid) {
		// TODO Auto-generated method stub
		return attributeDao.findAaBaseValAttr(gid);
	}
	//添加基本属性值
	public boolean addAaBaseValAttr(AaBaseAttrDetail aaBaseAttr) {
		return attributeDao.addAaBaseValAttr(aaBaseAttr);
	}
	//修改基本属性值
	public boolean updateAaBaseValAttr(AaBaseAttrDetail aaBaseAttr) {
		return attributeDao.updateAaBaseValAttr(aaBaseAttr);
	}
	//删除基本属性值
	public boolean deleteAaBaseValAttr(String[] psList) {
		return attributeDao.deleteAaBaseValAttr(psList);
	}

	// ......................................................................属性类别....................................................................................//
	//属性方案类别列表	
	public PageBean getAA_CategoryList(int pageIndex, int pageSize,
			String conditionSql) {
		return attributeDao.getAA_CategoryList(pageIndex, pageSize,
				conditionSql);
	}
	//根据GID查询属性方案类别
	public AaCategory findAA_Category(String gid) {
		return attributeDao.findAA_Category(gid);
	}
	//添加属性方案类别
	public boolean addAaCategory(AaCategory aaCategory) {
		return attributeDao.addAaCategory(aaCategory);
	}
	//修改属性方案类别
	public boolean updateAaCategory(AaCategory aaCategory) {
		return attributeDao.updateAaCategory(aaCategory);
	}
	//删除属性方案类别
	public boolean deleteAaCategory(String[] psList) {
		return attributeDao.deleteAaCategory(psList);
	}
}
