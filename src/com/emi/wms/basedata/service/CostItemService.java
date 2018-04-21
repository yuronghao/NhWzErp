package com.emi.wms.basedata.service;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.emi.android.bean.DepartmentRsp;
import com.emi.cache.service.CacheCtrlService;
import com.emi.common.util.CommonUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.basedata.dao.CostItemDao;
import com.emi.wms.bean.AAPriorAttribute;
import com.emi.wms.bean.AaDepartment;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.AaProviderCustomer;
import com.emi.wms.bean.MESWMCostItem;
import com.emi.wms.bean.MESWMCostItemAllotRate;
import com.emi.wms.bean.MESWMCostItemSourceSet;
import com.emi.wms.bean.YmRdStyle;

public class CostItemService {

	private CostItemDao costItemDao;
	
	private CacheCtrlService cacheCtrlService;

	public CostItemDao getCostItemDao() {
		return costItemDao;
	}

	public void setCostItemDao(CostItemDao costItemDao) {
		this.costItemDao = costItemDao;
	}

	public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
	this.cacheCtrlService = cacheCtrlService;
}

	
	public PageBean getcostItemList(int pageIndex, int pageSize,String conditionSql) {
		return costItemDao.getcostItemList(pageIndex, pageSize,conditionSql);
	}
	
	public boolean addcostItem(MESWMCostItem costItem) {
		return costItemDao.addcostItem(costItem);
	}
	public MESWMCostItem findcostItem(String gid) {
		return costItemDao.findcostItem(gid);
	}
	
	public AAPriorAttribute findprior(String gid) {
		return costItemDao.findprior(gid);
	}
	
	public boolean updatecostItem(MESWMCostItem costItem) {
		return costItemDao.updatecostItem(costItem);
	}
	public void deletecostItem(String gid) {
		//1、删除主表（假删除）
		costItemDao.deletecostItem(gid);
	}
	
	
	/**
	 * 
	* @Title: 添加/修改/删除  成本分配率 
	* @author zxl 2017年6月8日 上午8:53:32
	* @return PageBean
	 */
	public PageBean costItemAllotRateList(int pageIndex, int pageSize,String conditionSql) {
		PageBean costItemAllotRateList=	 costItemDao.costItemAllotRateList(pageIndex, pageSize,conditionSql);
		
		for(int i=0;i<costItemAllotRateList.getList().size();i++){
			MESWMCostItemAllotRate oo=(MESWMCostItemAllotRate)costItemAllotRateList.getList().get(i);
			AaGoods goods=cacheCtrlService.getGoods(oo.getGoodsGid());
			oo.setGoodsName(goods.getGoodsname());
			AaDepartment dRsp=cacheCtrlService.getDepartment(oo.getDepGid());
			oo.setDepName(dRsp.getDepname());
			oo.setGoodsCode(goods.getGoodscode());
			oo.setGoodsStandard(goods.getGoodsstandard());
			/*MESWMCostItem mCostItem=cacheCtrlService.getMESWMCostItem(oo.getCostItemGid());
			oo.setCostItemName(mCostItem.getName());*/
		}
		return costItemAllotRateList;
	}
	
	public boolean addcostItemAllotRate(MESWMCostItemAllotRate costItemAllotRate) {
		return costItemDao.addcostItemAllotRate(costItemAllotRate);
	}
	
	
	public void importRoute(File file,String orgGid,String sobGid) throws Exception {
		//excel所有数据
		List<String[]> list = CommonUtil.getAllExcelData(file);
		List<MESWMCostItemAllotRate> costItemAllotRates=new ArrayList<MESWMCostItemAllotRate>();
		
		for(String[] strs:list){
			MESWMCostItemAllotRate ar=new MESWMCostItemAllotRate();
			ar.setOrgGid(orgGid);
			ar.setSobGid(sobGid);
			ar.setCostItemCodeImport(strs[0]);
			ar.setDepCodeImport(CommonUtil.Obj2String(strs[1]));
			ar.setGoodsCodeImport(CommonUtil.Obj2String(strs[2]));
			ar.setCfree1(CommonUtil.Obj2String(strs[3]));
			
			if(CommonUtil.isNullObject(strs[4])){
				continue;
			}
			ar.setRatio(new BigDecimal(strs[4]) );
			costItemAllotRates.add(ar);
		}
		
		costItemDao.addcostItemAllotRateList(costItemAllotRates);
		
		costItemDao.uptcostItemAllotRate();
		
	}
	
	public MESWMCostItemAllotRate findcostItemAllotRate(String gid) {
		MESWMCostItemAllotRate mRate= costItemDao.findcostItemAllotRate(gid);
		AaDepartment dRsp=cacheCtrlService.getDepartment(mRate.getDepGid());
		AaGoods goods=cacheCtrlService.getGoods(mRate.getGoodsGid());
		mRate.setDepName(dRsp.getDepname());
		mRate.setGoodsCode(goods.getGoodscode());
		mRate.setGoodsName(goods.getGoodsname());
		return mRate;
	}
	
	public String UpdatecostItemAllotRate(MESWMCostItemAllotRate costItemAllotRate) {
		String type="";
		MESWMCostItemAllotRate mRate=costItemDao.selectcostItemss(costItemAllotRate.getGoodsGid(),costItemAllotRate.getCfree1(),costItemAllotRate.getGid());
		if(mRate==null){
			MESWMCostItemAllotRate mRates=costItemDao.selectcostItem(costItemAllotRate.getGoodsGid(),costItemAllotRate.getCfree1());
			if(mRates!=null){
				type="noNull";
				return type;
			}else {
				costItemDao.UpdatecostItemAllotRate(costItemAllotRate);
				type="Null";
			}
		}else{
			costItemDao.UpdatecostItemAllotRate(costItemAllotRate);
			type="Null";
		}
		return type;
	}
	
	public void deletecostItemAllotRate(String gid) {
		//1、删除主表（假删除）
		costItemDao.deletecostItemAllotRate(gid);
	}
	
	/**
	 * 
	* @Title: 添加/修改/删除成本取数设置
	* @author zxl 2017年6月8日 上午8:54:24
	* @return PageBean
	 */
	public PageBean costItemSourceSetList(int pageIndex, int pageSize,String conditionSql) {
		PageBean costItemSourceSetList=	 costItemDao.costItemSourceSetList(pageIndex, pageSize,conditionSql);
		
		for(int i=0;i<costItemSourceSetList.getList().size();i++){
			MESWMCostItemSourceSet oo=(MESWMCostItemSourceSet)costItemSourceSetList.getList().get(i);
			AaDepartment dRsp=cacheCtrlService.getDepartment(oo.getDepGid());
			if (dRsp!=null) {
				oo.setDepName(dRsp.getDepname());
			}
			if(oo.getRdStyleGid()!=null){
				String[] ary = oo.getRdStyleGid().split(",");
				String cdstyle="";
				for(int j=0;j<ary.length;j++){
					YmRdStyle rdStyle=costItemDao.selectRdStylesByGid(ary[j]);
					if (rdStyle!=null) {
						cdstyle+=rdStyle.getCrdName()+",";
					}
				}
				if (!cdstyle.equals("")) {
					cdstyle=cdstyle.substring(0,cdstyle.length()-1);
				}
				oo.setRdStyleName(cdstyle);
			}
			
		}
		return costItemSourceSetList;
	}
	
	public boolean addcostItemSourceSet(MESWMCostItemSourceSet costItemSourceSet) {
		return costItemDao.addcostItemSourceSet(costItemSourceSet);
	}
	
	public MESWMCostItemSourceSet findcostItemSourceSet(String gid) {
		MESWMCostItemSourceSet mSet= costItemDao.findcostItemSourceSet(gid);
		AaDepartment dRsp=cacheCtrlService.getDepartment(mSet.getDepGid());
		if (dRsp!=null) {
			mSet.setDepName(dRsp.getDepname());
		}
		if(mSet.getRdStyleGid()!=null){
			String[] ary = mSet.getRdStyleGid().split(",");
			String cdstyle="";
			for(int j=0;j<ary.length;j++){
				YmRdStyle rdStyle=costItemDao.selectRdStylesByGid(ary[j]);
				if (rdStyle!=null) {
					cdstyle+=rdStyle.getCrdName()+",";
				}
			}
			if (!cdstyle.equals("")) {
				cdstyle=cdstyle.substring(0,cdstyle.length()-1);
			}
			mSet.setRdStyleName(cdstyle);
		}
		return mSet;
	}
	
	public boolean updatecostItemSourceSet(MESWMCostItemSourceSet costItemSourceSet) {
		return costItemDao.updatecostItemSourceSet(costItemSourceSet);
	}
	
	public void deletecostItemSourceSet(String gid) {
		//1、删除主表（假删除）
		costItemDao.deletecostItemSourceSet(gid);
	}
	
	
	public MESWMCostItemAllotRate selectcostItem(String goodsGid,String cfree1){
		return	costItemDao.selectcostItem(goodsGid,cfree1);
	}
	
	public MESWMCostItemAllotRate selectcostItemss(String goodsGid,String cfree1,String gid){
		return	costItemDao.selectcostItemss(goodsGid,cfree1,gid);
	}
	
	public List<MESWMCostItem> selectcostItems(){
		return	costItemDao.selectcostItems();
	}
	
	public List<YmRdStyle> selectRdStyles(){
		return	costItemDao.selectRdStyles();
	}
	
	public List<AAPriorAttribute> selectPriorAttribute(){
		return	costItemDao.selectPriorAttribute();
	}
	
}
