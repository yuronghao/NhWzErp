package com.emi.wms.servicedata.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.*;

public class PurchaseArrivalDao extends BaseDao {
	
	public PageBean getpurchasearrivallist(int pageIndex,int pageSize,String condition){
		String sql ="select "+CommonUtil.colsFromBean(WmProcurearrival.class,"WmProcurearrival")+" from WM_ProcureArrival WmProcurearrival where 1=1 ";
		if(!CommonUtil.isNullString(condition)){
			sql += condition;
		}
		return this.emiQueryList(sql, pageIndex, pageSize, WmProcurearrival.class, "");
	}

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
			String sql = "delete from WM_purchaseArrivalpurchaseArrival_C where purchaseArrivalpurchaseArrivalUid='"+exhTypeId+"'";
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
	public boolean addpurchaseArrival(WmProcureorder WmProcureorder) {
		return this.emiInsert(WmProcureorder);
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
	
	public boolean updatepurchaseArrival(WmProcureorder WmProcureorder) {
		return this.emiUpdate(WmProcureorder);
	}

	public boolean updateymuser(YmUser YmUser) {
		return this.emiUpdate(YmUser);
	}
	
	public boolean deletepurchaseArrival(String orgclassid) {
		try {
				String sql = "update AA_Org set isDel='1' where gid='"+orgclassid+ "'";
			this.update(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	public List getpurchaseArrivalbookList(String exhTypeId) {
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
	
	public Map findpurchaseArrival(String purchaseArrivalgid,String orgId,String sobId) {
		String sql="";
		if(CommonUtil.isNullString(purchaseArrivalgid)){
			sql="SELECT TOP 1 purchaseArrival.gid purchaseArrivalgid,ymuser.userName recordpersonName,ymuser1.userName auditpersonName ,* FROM WM_ProcureArrival purchaseArrival LEFT JOIN AA_Person person ON person.gid = purchaseArrival.salesmanUid LEFT JOIN AA_Department department ON department.gid = purchaseArrival.departmentUid LEFT JOIN YM_User ymuser ON ymuser.gid = purchaseArrival.recordPersonUid LEFT JOIN YM_User ymuser1 ON ymuser1.gid = purchaseArrival.auditPersonUid left join AA_Provider_Customer procus on procus.gid=purchaseArrival.supplierUid where 1=1 and purchaseArrival.sobGid='"+sobId+"' and purchaseArrival.orgGid='"+orgId+"' ORDER BY purchaseArrival.pk DESC";
		}else{
			sql="SELECT purchaseArrival.gid purchaseArrivalgid,ymuser.userName recordpersonName,ymuser1.userName auditpersonName ,* FROM WM_ProcureArrival purchaseArrival LEFT JOIN AA_Person person ON person.gid = purchaseArrival.salesmanUid LEFT JOIN AA_Department department ON department.gid = purchaseArrival.departmentUid LEFT JOIN YM_User ymuser ON ymuser.gid = purchaseArrival.recordPersonUid LEFT JOIN YM_User ymuser1 ON ymuser1.gid = purchaseArrival.auditPersonUid left join AA_Provider_Customer procus on procus.gid=purchaseArrival.supplierUid where purchaseArrival.gid = '"+purchaseArrivalgid+"'  and purchaseArrival.sobGid='"+sobId+"' and purchaseArrival.orgGid='"+orgId+"' order by purchaseArrival.pk desc";
		}
		
		return  this.queryForMap(sql);
	}
	
	public void setpurchaseArrivalEnable(int enable, String id) {
		String sql = "update MES_WM_AccountingInform set state="+enable+" where gid='"+id+"'";
		this.update(sql);
	}
	
	public boolean addpurchaseArrivalc(List list) {
		return this.emiInsert(list);
	}
	public boolean updatepurchaseArrivalc(List list) {
		return this.emiUpdate(list);
	}
	public List getpurchaseArrivallist(String purchaseArrivalpurchaseArrivalUid,String condition) {
		String sql = "select abs(number) number,abs(putinNumber) putinNumber,abs(assistNumber) assistNumber,abs(putinAssistNumber) putinAssistNumber,"
				+ "goodsUid,batch,wmprocurearrivalc.gid,code, wmprocurearrivalc.cfree1,wmprocurearrivalc.cfree2,"
				+ " originalTaxPrice,originalTaxMoney,originalNotaxPrice,originalNotaxMoney,originalTax,"
				+ " localTaxPrice,localTaxMoney,localNotaxPrice,localNotaxMoney,localTax from WM_ProcureArrival_C wmprocurearrivalc WITH (NoLock) left join AA_UserDefine aauserdefine on aauserdefine.value = wmprocurearrivalc.cfree1 "
				+ "where procureArrivalUid='"+purchaseArrivalpurchaseArrivalUid+"' and abs(number) > abs(isnull(putinNumber,0)) "+condition;
		return this.queryForList(sql);
	}
	
	public List getpurchaseArrivallistcheck(String purchaseArrivalpurchaseArrivalUid,String condition) {
		String sql = "select number num,abs(number) number,abs(checkOkNumber) checkOkNumber,abs(checkNotOkNumber) checkNotOkNumber,"
				+ " abs(assistNumber) assistNumber,abs(checkOkAssistNumber) checkOkAssistNumber,abs(checkNotOkAssistNumber) checkNotOkAssistNumber,"
				+ "goodsUid,batch,gid  from WM_ProcureArrival_C WITH (NoLock) "
				+ " where procureArrivalUid='"+purchaseArrivalpurchaseArrivalUid+"' and abs(number) > abs(isnull(checkOkNumber,0))+abs(isnull(checkNotOkNumber,0)) "+condition;
		return this.queryForList(sql);
	}
	
	public List getpurchaseArrivallistcheck1(String purchaseArrivalpurchaseArrivalUid) {
		String sql = "select * from WM_ProcureArrival_C where procureArrivalUid='"+purchaseArrivalpurchaseArrivalUid+"' and number > (isnull(checkOkNumber,0)+isnull(checkNotOkNumber,0)) and needCheck=1 ";
		return this.queryForList(sql);
	}
	
	public WmProcurearrival getwmprocure(String billGid){
		String sql = "select "+CommonUtil.colsFromBean(WmProcurearrival.class,"WmProcurearrival")+" from WM_ProcureArrival WmProcurearrival WITH (NoLock) where gid='"+billGid+"'";
		return (WmProcurearrival)this.emiQuery(sql, WmProcurearrival.class);
	}
	
	public Map getymsetting(){
		String sql ="select paramValue from YM_Settings where setName='headCheckFlag'";
		return (Map)this.queryForMap(sql);
	}
	
	/**
	 * 
	 * @category
	 * 2016年4月25日 上午11:27:09
	 * @author 杨峥铖
	 * @param condition
	 * @return
	 */
	public List<Map> getProcureCbyCheck(String condition) {
		String sql="select cc.gid ccgid,abs(cc.okNum) okNum,abs(cc.notOkNum) notOkNum, abs(cc.assistOkNum) assistOkNum,"
				+ " abs(cc.assistNotOkNum) assistNotOkNum,abs(cc.putInNum) putInNum, abs(cc.putInAssistNum) putInAssistNum,cc.batch,cc.procureArrivalCuid,cc.goodsUid, "
				+ " c.checkCode,c.checkDate,c.gid,"
				+ " ss.supplierUid,"
				+ " ssc.cfree1,ssc.cfree2 from QM_CheckCbill cc WITH (NoLock) "
				+ " left join WM_ProcureArrival_C ssc WITH (NoLock) on cc.procureArrivalCuid=ssc.gid "
				+ " left join WM_ProcureArrival ss WITH (NoLock) on ssc.procureArrivalUid=ss.gid "
				+ " left join QM_CheckBill c WITH (NoLock) on cc.checkGid=c.gid where "+condition;
		return this.queryForList(sql);
	}
	
	
	
	/**
	 * @category 获得到货单子表gid
	 *2016 2016年4月23日下午3:49:45
	 *ProcurearrivalC
	 *宋银海
	 */
	public WmProcurearrivalC getProcurearrivalC(String condition){
		String sql="select "+CommonUtil.colsFromBean(WmProcurearrivalC.class)+" from WM_ProcureArrival_C where "+condition;
		return (WmProcurearrivalC)this.emiQuery(sql, WmProcurearrivalC.class);
	}
	
	
	/**
	 * @category 获得到货单子表列表
	 *2016 2016年4月23日下午3:49:45
	 *ProcurearrivalC
	 *宋银海
	 */
	public List<WmProcurearrivalC> getProcurearrivalCList(String condition){
		String sql="select "+CommonUtil.colsFromBean(WmProcurearrivalC.class)+" from WM_ProcureArrival_C where "+condition;
		return this.emiQueryList(sql, WmProcurearrivalC.class);
	}

	public List<OM_MOMaterials> getmOMaterialsGoods(String billGid) {
		
		String sql="select "+CommonUtil.colsFromBean(OM_MOMaterials.class)+" from OM_Materials  where moDetailsGid in(select gid from OM_Details where moMainGid='"+billGid+"')";
		return this.emiQueryList(sql, OM_MOMaterials.class);
	}

	public OM_MOMain getOmMainById(String billGid) {
		String sql="select "+CommonUtil.colsFromBean(OM_MOMain.class)+" from OM_Main where gid='"+billGid+"'";
		return (OM_MOMain) this.emiQuery(sql, OM_MOMain.class);
	}
	
	public Map getProcureArrivalByCgid(String cgid){
		String sql="select a.supplierUid from WM_ProcureArrival_C ac left join WM_ProcureArrival a on ac.procureArrivalUid=a.gid "
				+ " where ac.gid='"+cgid+"'";
		return this.queryForMap(sql);
	}


	public WmProcureorder getwmprocureorder(String billGid) {
		String sql = "select "+CommonUtil.colsFromBean(WmProcureorder.class,"WmProcureorder")+" from WM_ProcureOrder WmProcureorder WITH (NoLock) where gid='"+billGid+"'";
		return (WmProcureorder) this.emiQuery(sql, WmProcureorder.class);
	}

	public List getpurchaseOrderlist(String purchaseOrderUid, String condition) {
		String sql = "select abs(number) number,abs(putinNumber) putinNumber,"+
				"				 goodsUid,wmprocureorderc.gid,"+
				"				  originalTaxPrice,originalTaxMoney,originalNotaxPrice,originalNotaxMoney,originalTax,"+
				"				  localTaxPrice,localTaxMoney,localNotaxPrice,localNotaxMoney,localTax from WM_ProcureOrder_C wmprocureorderc WITH (NoLock) "+
				"				 where procureOrderUid='"+purchaseOrderUid+"' and abs(number) > abs(isnull(putinNumber,0)) " + condition;
		return this.queryForList(sql);
	}

    public List<WmProcureorderC> getProcureorderCList(String condition) {
		String sql="select "+CommonUtil.colsFromBean(WmProcureorderC.class)+" from WM_ProcureOrder_C where "+condition;
		return this.emiQueryList(sql, WmProcureorderC.class);


    }
}
