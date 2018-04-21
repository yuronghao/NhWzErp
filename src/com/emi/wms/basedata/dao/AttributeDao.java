package com.emi.wms.basedata.dao;

import java.util.List;
import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.sys.core.bean.PageBean;
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
public class AttributeDao extends BaseDao {
	// ......................................................................属性方案....................................................................................//
	//属性方案列表	
	public PageBean getAA_SoulationList(int pageIndex, int pageSize,
			String conditionSql) {
		/* Map match = new HashMap(); */
		// match.put("descriptionparent", "Asset_change.descriptionparent");
		String sql = "select s.*,a.name from AA_Soulation s "
				+ " left join AA_Category a on a.gid=s.categorygid"
				+ " where 1=1 and (s.isdel=0 or s.isdel is null)";
		if (!CommonUtil.isNullString(conditionSql)) {
			sql += conditionSql;
		}
		return (PageBean) this.emiQueryList(sql, pageIndex, pageSize, " pk desc");
	}
	//根据GID获取基本属性
	public AaSoulation findAaSoulation(String gid) {
		// TODO Auto-generated method stub
		return (AaSoulation) this.emiFind(gid, AaSoulation.class);
	}
	//获取属性方案相关联的属性列表
	public List<AaSoulationlist> findAaSoulationlist(String gid) {
		String sql = "select s.* from AA_SoulationList s  where 1=1 and s.soulationgid='"
				+ gid + "'";
		return this.emiQueryList(sql, AaSoulationlist.class);
	}
	//或者属性方案中基本属性相关联的属性列表
	public List<AaSoulationdetail> findAaSoulationdetail(String gid) {
		String sql = "select s.* from AA_SoulationDetail s  where 1=1 and s.soulationListgid='"
				+ gid + "'";
		return this.emiQueryList(sql, AaSoulationdetail.class);
	}
	//获取基本属性列表
	public List<AaBaseAttr> getAaBaseAttr(String gid, String consql) {
		String sql = "select s.* from AA_BaseAttr s  where 1=1 and s.isdel=0";
		if (!CommonUtil.isNullString(consql)) {
			sql += consql;
		}
		return this.emiQueryList(sql, AaBaseAttr.class);
	}
	//基本属性值列表
	public List<AaBaseAttrDetail> getAaBaseAttrDetail(String gid, String consql) {
		String sql = "select s.* from AA_BaseAttrDetail s  where 1=1 and s.isdel=0";
		if (!CommonUtil.isNullString(consql)) {
			sql += consql;
		}
		return this.emiQueryList(sql, AaBaseAttrDetail.class);
	}
	//获取属性类别列表
	public List<AaCategory> getAaCategory() {
		String sql = "select s.* from AA_Category s  where 1=1 and s.isdel=0";
/*		if (!CommonUtil.isNullString(consql)) {
			sql += consql;
		}*/
		return this.emiQueryList(sql, AaCategory.class);
	}
	//添加属性方案
	public boolean addAaSoulation(AaSoulation aSoulation) {
		return this.emiInsert(aSoulation);
	}
	//修改属性方案
	public boolean updateAaSoulation(AaSoulation aSoulation) {
		return this.emiUpdate(aSoulation);
	}
	//添加基本属性方案中属性的列表
	public boolean addAaSoulationlist(List<AaSoulationlist> list) {
		return this.emiInsert(list);
	}
	//添加基本方案中属性值的列表
	public boolean addAaSoulationdetail(List<AaSoulationdetail> dlist) {
		return this.emiInsert(dlist);
	}
	//删除属性方案中相关联的属性
	public boolean deleteAaSoulationlist(String gid) {
		try {
			String sql = "delete from  AA_SoulationList  where soulationgid='"
					+ gid + "'";
			this.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	//删除属性方案中的属性相关联的属性值
	public boolean deleteAaSoulationdetail(String[] psList) {
		try {
			String[] sqls = new String[psList.length];
			for (int i = 0; i < psList.length; i++) {
				String sql = "delete from  AA_SoulationDetail where soulationListgid='"
						+ psList[i] + "'";
				sqls[i] = sql;
			}
			this.batchUpdate(sqls);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	//删除属性方案
	public boolean deleteAaSoulation(String[] psList) {
		try {
			String[] sqls = new String[psList.length];
			for (int i = 0; i < psList.length; i++) {
				String sql = "update  AA_Soulation set isdel=1 where gid='"
						+ psList[i] + "'";
				sqls[i] = sql;
			}
			this.batchUpdate(sqls);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	// ......................................................................属性....................................................................................//
	//基本属性列表
	public PageBean getAA_BaseAttrList(int pageIndex, int pageSize,
			String conditionSql) {
		String sql = "select c.* from AA_BaseAttr c"
				+ " where 1=1 and (c.isdel=0 or c.isdel is null)";

		if (!CommonUtil.isNullString(conditionSql)) {
			sql += conditionSql;
		}
		return (PageBean) this.emiQueryList(sql, pageIndex, pageSize, " pk desc");
	}
	//根据GID查询基本属性
	public AaBaseAttr findAaBaseAttr(String gid) {
		// TODO Auto-generated method stub
		return (AaBaseAttr) this.emiFind(gid, AaBaseAttr.class);
	}
	//添加基本属性
	public boolean addAaBaseAttr(AaBaseAttr aaBaseAttr) {
		return this.emiInsert(aaBaseAttr);
	}
	//修改基本属性
	public boolean updateAaBaseAttr(AaBaseAttr aaBaseAttr) {
		return this.emiUpdate(aaBaseAttr);
	}
	//删除基本属性
	public boolean deleteAaBaseAttr(String[] psList) {
		try {
			String[] sqls = new String[psList.length];
			for (int i = 0; i < psList.length; i++) {
				String sql = "update  AA_BaseAttr set isdel=1 where gid='"
						+ psList[i] + "'";
				sqls[i] = sql;
			}
			this.batchUpdate(sqls);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	//基本属性值列表
	public PageBean getAA_BaseAttrValList(int pageIndex, int pageSize,
			String conditionSql) {
		String sql = "select c.* from AA_BaseAttrDetail c"
				+ " where 1=1 and c.isdel=0 ";

		if (!CommonUtil.isNullString(conditionSql)) {
			sql += conditionSql;
		}
		return (PageBean) this.emiQueryList(sql, pageIndex, pageSize, "");
	}
	//根据GID查询基本属性值
	public AaBaseAttrDetail findAaBaseValAttr(String gid) {
		// TODO Auto-generated method stub
		return (AaBaseAttrDetail) this.emiFind(gid, AaBaseAttrDetail.class);
	}
	//添加基本属性值
	public boolean addAaBaseValAttr(AaBaseAttrDetail aaBaseAttr) {
		return this.emiInsert(aaBaseAttr);
	}
	//修改基本属性值
	public boolean updateAaBaseValAttr(AaBaseAttrDetail aaBaseAttr) {
		return this.emiUpdate(aaBaseAttr);
	}
	//删除基本属性值
	public boolean deleteAaBaseValAttr(String[] psList) {
		try {
			String[] sqls = new String[psList.length];
			for (int i = 0; i < psList.length; i++) {
				String sql = "update  AA_BaseAttrDetail set isdel=1 where gid='"
						+ psList[i] + "'";
				sqls[i] = sql;
			}
			this.batchUpdate(sqls);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	// ......................................................................属性类别....................................................................................//
	//属性方案类别列表
	public PageBean getAA_CategoryList(int pageIndex, int pageSize,
			String conditionSql) {
		String sql = "select c.* from AA_Category c" + " where 1=1 ";

		if (!CommonUtil.isNullString(conditionSql)) {
			sql += conditionSql;
		}
		return (PageBean) this.emiQueryList(sql, pageIndex, pageSize, " pk desc");
	}
	//根据GID查询属性方案类别
	public AaCategory findAA_Category(String gid) {
		// TODO Auto-generated method stub
		return (AaCategory) this.emiFind(gid, AaCategory.class);
	}
	//添加属性方案类别
	public boolean addAaCategory(AaCategory aaCategory) {
		return this.emiInsert(aaCategory);
	}
	//修改属性方案类别
	public boolean updateAaCategory(AaCategory aaCategory) {
		return this.emiUpdate(aaCategory);
	}
	//删除属性方案类别
	public boolean deleteAaCategory(String[] psList) {
		try {
			String[] sqls = new String[psList.length];
			for (int i = 0; i < psList.length; i++) {
				String sql = "update  AA_Category set isdel=1 where gid='"
						+ psList[i] + "'";
				sqls[i] = sql;
			}
			this.batchUpdate(sqls);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
