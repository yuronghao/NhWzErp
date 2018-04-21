package com.emi.wms.servicedata.dao;

import java.util.List;
import java.util.Map;

import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.AaOrg;
import com.emi.wms.bean.AaReason;
import com.emi.wms.bean.MesWmProduceProcessroutec;
import com.emi.wms.bean.QMCheckBill;
import com.emi.wms.bean.QMCheckCReasonBill;
import com.emi.wms.bean.YmUser;

public class CheckBillDao extends BaseDao {

	public List getClassifyList(String conditionSql) {
		String sql = "select gid as id,parentid as pId,orgName as name from AA_Org where 1=1 and isDel='0'";
		if(!CommonUtil.isNullString(conditionSql)){
			sql += conditionSql;
		}
		return this.queryForList(sql);
	}

	public Map getorganizeInfo(String conditionSql) {
		String sql = "select * FROM AA_Org where 1=1 and isDel=0";
		if(!CommonUtil.isNullString(conditionSql)){
			sql += conditionSql;
		}
		return this.queryForMap(sql);
	}
	
	public List getCategoryList(String conditionSql) {
		String sql = "select * from AA_Category where 1=1 ";
		if(!CommonUtil.isNullString(conditionSql)){
			sql += conditionSql;
		}
		return this.queryForList(sql);
	}
	
	public boolean deletebooks(String exhTypeId) {
		try {
			String sql = "delete from WM_Purchasecheckbill_C where purchasecheckbillUid='"+exhTypeId+"'";
			this.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean addorg(AaOrg aaorg) {
		return this.emiInsert(aaorg);
	}
	public boolean addcheckbill(QMCheckBill qmcheckbill) {
		return this.emiInsert(qmcheckbill);
	}
	public boolean addcheckbillreason(QMCheckCReasonBill reason) {
		return this.emiInsert(reason);
	}
	public boolean addprocusbook(List list) {
		return this.emiInsert(list);
	}
	public Map findorg(String exhTypeId) {
		String sql="select * from AA_Org where gid='"+exhTypeId+"'";
		return  this.queryForMap(sql);
	}
	
	public boolean updateorg(AaOrg aaorg) {
		return this.emiUpdate(aaorg);
	}
	
	public boolean updatecheckbill(QMCheckBill qmcheckbill) {
		return this.emiUpdate(qmcheckbill);
	}

	public boolean updateymuser(YmUser YmUser) {
		return this.emiUpdate(YmUser);
	}
	
	public boolean deletecheckbill(String orgclassid) {
		try {
				String sql = "update AA_Org set isDel='1' where gid='"+orgclassid+ "'";
			this.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	public List getcheckbillbookList(String exhTypeId) {
		String sql="select * from AA_Provider_Customer_AddBook where pcGid='"+exhTypeId+"'";
		return  this.queryForList(sql);
	}
	public boolean findorgchild(String orgclassid){
		String sql = "select count(*) from AA_Org where parentid = '"+orgclassid+"'";
		int count = this.queryForInt(sql);
		if(count!=0){
			return true;
		}else{
			return false;
		}
	}
	
	public Map findcheckbill(String checkbillgid,String orgId,String sobId) {
		String sql="";
		if(CommonUtil.isNullString(checkbillgid)){
			sql="SELECT TOP 1 qmcheckbill.gid checkbillgid,* FROM QM_CheckBill qmcheckbill left join AA_Person person on person.gid = qmcheckbill.checkPersonGid left join AA_Department department on department.gid = qmcheckbill.checkDptGid where   qmcheckbill.sobGid='"+sobId+"' and qmcheckbill.orgGid='"+orgId+"' ORDER BY qmcheckbill.pk DESC";
		}else{
			sql="SELECT qmcheckbill.gid checkbillgid,* FROM QM_CheckBill qmcheckbill left join AA_Person person on person.gid = qmcheckbill.checkPersonGid left join AA_Department department on department.gid = qmcheckbill.checkDptGid where qmcheckbill.gid = '"+checkbillgid+"' and qmcheckbill.sobGid='"+sobId+"' and qmcheckbill.orgGid='"+orgId+"' order by qmcheckbill.pk desc";
		}
		return  this.queryForMap(sql);
	}
	
	public void setcheckbillEnable(int enable, String id) {
		String sql = "update MES_WM_AccountingInform set state="+enable+" where gid='"+id+"'";
		this.update(sql);
	}
	
	public boolean addcheckbillc(List list) {
		return this.emiInsert(list);
	}
	public boolean updatecheckbillc(List list) {
		return this.emiUpdate(list);
	}
	public List getcheckbillclist(String purchasecheckbillUid) {
		String sql = "select * from QM_CheckCbill where checkGid='"+purchasecheckbillUid+"'";
		return this.queryForList(sql);
	}
	public PageBean getreasonlist(int pageIndex,int pageSize,String gid) {
		String sql = "select QM_CheckCReasonbill.pk,QM_CheckCReasonbill.num,AA_Reason.reasonname,AA_Reason.reasoncode,AA_Reason.reasontype from QM_CheckCReasonbill QM_CheckCReasonbill left join AA_Reason AA_Reason on AA_Reason.gid=QM_CheckCReasonbill.reasonGid where checkcGid='"+gid+"'";
		return this.emiQueryList(sql, pageIndex, pageSize, "");
	}
	public List getaareasonlist() {
		String sql = "select * from AA_Reason order by reasoncode";
		return this.queryForList(sql);
	}
	public PageBean getcheckbilllist(int pageIndex,int pageSize,String condition) {
		String sql = "select * from QM_CheckBill  where 1=1 ";
		if(!CommonUtil.isNullString(condition)){
			sql += condition;
		}
		
		return this.emiQueryList(sql, pageIndex, pageSize, " checkDate desc ");
	}
	
	/**
	 * @category 获得生产订单工艺路线子表
	 *2016 2016年4月12日下午2:23:33
	 *MesWmProduceProcessroutec
	 *宋银海
	 */
	public MesWmProduceProcessroutec getMesWmProduceProcessroutec(String condition){
		String sql="select "+CommonUtil.colsFromBean(MesWmProduceProcessroutec.class)+" from MES_WM_ProduceProcessRouteC where "+condition;
		return (MesWmProduceProcessroutec) this.emiQuery(sql, MesWmProduceProcessroutec.class);
	}
	
}
