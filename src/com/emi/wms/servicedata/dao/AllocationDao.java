package com.emi.wms.servicedata.dao;

import java.util.List;
import java.util.Map;

import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.AaOrg;
import com.emi.wms.bean.AaReason;
import com.emi.wms.bean.QMCheckCReasonBill;
import com.emi.wms.bean.YmUser;
import com.emi.wms.bean.wmCall;

public class AllocationDao extends BaseDao {

	public List getcolumns() {
		String sql = "select * from AA_freeSet where projectName is not null and projectName !='' ";
		return this.queryForList(sql);
	}
	
	public PageBean getallocationlist(int pageIndex,int pageSize,String condition) {
		String sql = "select wmcall.gid,wmcall.pk,wmcall.billCode,wmcall.billDate,wmcall.outWhUid,wmcall.inWhUid,wmcallc.goodsUid,wmcallc.number,wmcallc.outgoodsAllocationUid,wmcallc.ingoodsAllocationUid,wmcallc.outnumber,wmcallc.outassistNumber,wmcallc.cfree1,wmcallc.cfree2 from WM_Call wmcall left join WM_Call_C wmcallc on wmcallc.callUid = wmcall.gid where 1=1 ";
		if(!CommonUtil.isNullString(condition)){
			sql += condition;
		}
		return this.emiQueryList(sql, pageIndex, pageSize, "");
	}
}
