package com.emi.wms.basedata.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.AAPriorAttribute;
import com.emi.wms.bean.MESWMCostItem;
import com.emi.wms.bean.MESWMCostItemAllotRate;
import com.emi.wms.bean.MESWMCostItemSourceSet;
import com.emi.wms.bean.YmRdStyle;

public class CostItemDao extends BaseDao {

	public PageBean getcostItemList(int pageIndex, int pageSize,String conditionSql) {
		Map match = new HashMap();
		match.put("priorname", "MESWMCostItem.priorname");
		match.put("allotRateName", "MESWMCostItem.allotRateName");
		String sql = "select "+CommonUtil.colsFromBean(MESWMCostItem.class,"CostItem")+",prior.name priorname,PriorAttribute.name allotRateName FROM MES_WM_CostItem CostItem left join AA_PriorAttribute prior on prior.gid = CostItem.sourceGid LEFT JOIN AA_PriorAttribute PriorAttribute on PriorAttribute.gid=CostItem.allotRateGid where 1=1 and isDelete=0";
		if(!CommonUtil.isNullString(conditionSql)){
			sql += conditionSql;
		}
		return (PageBean) this.emiQueryList(sql, pageIndex, pageSize, MESWMCostItem.class,match,"");
	}
	
	public boolean addcostItem(MESWMCostItem costItem) {
		return this.emiInsert(costItem);
	}
	
	public MESWMCostItem findcostItem(String gid) {
		return  (MESWMCostItem)this.emiFind(gid, MESWMCostItem.class);
	}
	
	public AAPriorAttribute findprior(String gid) {
		return  (AAPriorAttribute)this.emiFind(gid, AAPriorAttribute.class);
	}
	
	public boolean updatecostItem(MESWMCostItem costItem) {
		return this.emiUpdate(costItem);
	}

	public void deletecostItem(String processId) {
		processId = processId.replaceAll(",", "','"); 
		String sql = "UPDATE  MES_WM_CostItem SET isDelete=1 WHERE gid in ('"+processId+"')";
		this.update(sql);
	}
	
	
	/**
	 * 
	* @Title: 添加/修改/删除成本分配率
	* @author zxl 2017年6月8日 上午8:55:48
	* @return PageBean
	 */
	public PageBean costItemAllotRateList(int pageIndex, int pageSize,String conditionSql) {
		Map match = new HashMap();
		match.put("costItemName", "MESWMCostItemAllotRate.costItemName");
		String sql = "select "+CommonUtil.colsFromBean(MESWMCostItemAllotRate.class,"CostItemAllotRate")+",c.name costItemName from MES_WM_CostItemAllotRate CostItemAllotRate LEFT JOIN MES_WM_CostItem c on c.gid=CostItemAllotRate.costItemGid  WHERE CostItemAllotRate.isDelete=0";
		if(!CommonUtil.isNullString(conditionSql)){
			sql += conditionSql;
		}
		return (PageBean) this.emiQueryList(sql, pageIndex, pageSize,MESWMCostItemAllotRate.class,match,"");
	}
	
	public boolean addcostItemAllotRate(MESWMCostItemAllotRate costItemAllotRate) {
		return this.emiInsert(costItemAllotRate);
	}
	
	public boolean addcostItemAllotRateList(List<MESWMCostItemAllotRate> costItemAllotRate) {
		return this.emiInsert(costItemAllotRate);
	}
	
	
	public int uptcostItemAllotRate() {
		String sql="update MES_WM_CostItemAllotRate set costItemGid=ci.gid, goodsGid=gs.gid, depGid=dt.gid "+
					"from MES_WM_CostItemAllotRate ar "+
					"LEFT JOIN AA_Goods gs on ar.goodsCodeImport=gs.goodsCode "+
					"LEFT JOIN AA_Department dt on ar.depCodeImport=dt.depCode "+
					"LEFT JOIN MES_WM_CostItem ci on ar.costItemCodeImport=ci.code "+
					"where (isnull(costItemGid,'')='' "+
					"or isnull(goodsGid,'')='' "+
					"or isnull(depGid,'')='') and isnull(depCodeImport,'')<>'' ";
		return this.update(sql);
	}
	
	public MESWMCostItemAllotRate findcostItemAllotRate(String gid) {
		return  (MESWMCostItemAllotRate)this.emiFind(gid, MESWMCostItemAllotRate.class);
	}
	
	public boolean UpdatecostItemAllotRate(MESWMCostItemAllotRate costItemAllotRate) {
		return this.emiUpdate(costItemAllotRate);
	}
	
	public void deletecostItemAllotRate(String processId) {
		processId = processId.replaceAll(",", "','"); 
		String sql = "UPDATE  MES_WM_CostItemAllotRate SET isDelete=1 WHERE gid in ('"+processId+"')";
		this.update(sql);
	}
	
	
	/**
	 * 
	* @Title: 添加/修改/删除成本取数 
	* @author zxl 2017年6月8日 上午8:56:43
	* @return PageBean
	 */
	public PageBean costItemSourceSetList(int pageIndex, int pageSize,String conditionSql) {
		Map match = new HashMap();
		match.put("costItemName", "MESWMCostItemSourceSet.costItemName");
		match.put("rdStyleName", "MESWMCostItemSourceSet.rdStyleName");
		match.put("apName", "MESWMCostItemSourceSet.apName");
		String sql = "select "+CommonUtil.colsFromBean(MESWMCostItemSourceSet.class,"costItemSourceSet")+",c.name costItemName,yr.crdName rdStyleName,ap.name apName from MES_WM_CostItemSourceSet costItemSourceSet "
				+ "LEFT JOIN MES_WM_CostItem c on c.gid=costItemSourceSet.costItemGid LEFT JOIN YM_RdStyle yr on yr.gid=costItemSourceSet.rdStyleGid LEFT JOIN AA_PriorAttribute ap on ap.gid=costItemSourceSet.sourceMode "
				+ "WHERE costItemSourceSet.isDelete=0";
		if(!CommonUtil.isNullString(conditionSql)){
			sql += conditionSql;
		}
		return (PageBean) this.emiQueryList(sql, pageIndex, pageSize,MESWMCostItemSourceSet.class,match,"");
	}
	
	public boolean addcostItemSourceSet(MESWMCostItemSourceSet costItemSourceSet) {
		return this.emiInsert(costItemSourceSet);
	}
	
	public MESWMCostItemSourceSet findcostItemSourceSet(String gid) {
		return  (MESWMCostItemSourceSet)this.emiFind(gid, MESWMCostItemSourceSet.class);
	}
	
	public boolean updatecostItemSourceSet(MESWMCostItemSourceSet costItemSourceSet) {
		return this.emiUpdate(costItemSourceSet);
	}
	
	public void deletecostItemSourceSet(String processId) {
		processId = processId.replaceAll(",", "','"); 
		String sql = "UPDATE  MES_WM_CostItemSourceSet SET isDelete=1 WHERE gid in ('"+processId+"')";
		this.update(sql);
	}
	
	public List<MESWMCostItem> selectcostItems(){
		String sql = "select "+CommonUtil.colsFromBean(MESWMCostItem.class,"CostItem")+" from MES_WM_CostItem CostItem "
				+ "WHERE CostItem.isDelete=0";
		return this.emiQueryList(sql, MESWMCostItem.class);
	}
	
	public List<YmRdStyle> selectRdStyles(){
		String sql = "select "+CommonUtil.colsFromBean(YmRdStyle.class,"RdStyle")+" from YM_RdStyle RdStyle ";
		return this.emiQueryList(sql, YmRdStyle.class);
	}
	
	public List<AAPriorAttribute> selectPriorAttribute(){
		String sql = "select "+CommonUtil.colsFromBean(AAPriorAttribute.class,"PriorAttribute")+" from AA_PriorAttribute PriorAttribute WHERE type='csource'";
		return this.emiQueryList(sql, AAPriorAttribute.class);
	}
	
	public MESWMCostItemAllotRate selectcostItem(String goodsGid,String cfree1){
		String sql = "select "+CommonUtil.colsFromBean(MESWMCostItemAllotRate.class,"CostItemAllotRate")+" from MES_WM_CostItemAllotRate CostItemAllotRate WHERE goodsGid='"+goodsGid+"' and cfree1='"+cfree1+"' and isDelete=0  ";
		return (MESWMCostItemAllotRate)this.emiQuery(sql, MESWMCostItemAllotRate.class);
	}
	
	public MESWMCostItemAllotRate selectcostItemss(String goodsGid,String cfree1,String gid){
		String sql = "select "+CommonUtil.colsFromBean(MESWMCostItemAllotRate.class,"CostItemAllotRate")+" from MES_WM_CostItemAllotRate CostItemAllotRate WHERE goodsGid='"+goodsGid+"' and cfree1='"+cfree1+"' and gid='"+gid+"' and isDelete=0  ";
		return (MESWMCostItemAllotRate)this.emiQuery(sql, MESWMCostItemAllotRate.class);
	}
	
	public YmRdStyle selectRdStylesByGid(String goodsGid){
		String sql = "select "+CommonUtil.colsFromBean(YmRdStyle.class,"RdStyle")+" from YM_RdStyle RdStyle WHERE gid='"+goodsGid+"'";
		return (YmRdStyle)this.emiQuery(sql, YmRdStyle.class);
	}
}
