package com.emi.cache.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.emi.cache.dao.CacheCtrldao;
import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.Constants;
import com.emi.wms.bean.AaDepartment;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.AaGoodsallocation;
import com.emi.wms.bean.AaGroup;
import com.emi.wms.bean.AaOrg;
import com.emi.wms.bean.AaPerson;
import com.emi.wms.bean.AaProviderCustomer;
import com.emi.wms.bean.AaWarehouse;
import com.emi.wms.bean.Classify;
import com.emi.wms.bean.Equipment;
import com.emi.wms.bean.MesAaStation;
import com.emi.wms.bean.MesAaWorkcenter;
import com.emi.wms.bean.MesWmStandardprocess;
import com.emi.wms.bean.Mould;
import com.emi.wms.bean.Unit;
import com.emi.wms.bean.WmAllocationstock;
import com.emi.wms.bean.WmNowstock;
import com.emi.wms.bean.YmUser;

/**
 * 缓存中的基础数据仅仅只是基础数据，现存量是另外的缓存
 * **/
@SuppressWarnings({ "rawtypes", "unchecked" })
public class CacheCtrldaoImpl extends BaseDao implements CacheCtrldao {

	@Override
	public List<AaGoods> setGoods() {
		Map match = new HashMap();
		match.put("cassComUnitName", "AaGoods.cassComUnitName");
		match.put("cstComUnitName", "AaGoods.cstComUnitName");
//		match.put("csaComUnitName", "AaGoods.csaComUnitName");
//		match.put("cpuComUnitName", "AaGoods.cpuComUnitName");
//		match.put("ccaComUnitName", "AaGoods.ccaComUnitName");
		match.put("classificationName", "AaGoods.classificationName");
		match.put("unitName", "AaGoods.unitName");
		match.put("unitGroupName", "AaGoods.unitGroupName");
		
		String sql = "select cu.UnitName AS cassComUnitName ,"
				+ "cst.UnitName AS cstComUnitName "
				+ ",uos.unitGroupName,tt.*  from  (select"
				+ CommonUtil.colsFromBean(AaGoods.class, "goods")
				+ ",goodsSort.classificationName as classificationName,cunit.UnitName as unitName from AA_Goods as goods with (nolock), "
				+ "	classify AS goodsSort with (nolock) ,unit AS cunit with (nolock) "
				+ "where goods.GoodsSortUid=goodsSort.gId and goods.GoodsUnit=cunit.gid) as tt "
				+ "left join unit AS cu ON tt.cassComUnitCode = cu.gid "
				+ "left join unit as cst on tt.cstcomunitcode=cst.gid "
				+ "left join UnitOfMeasurement as uos on tt.unitGroupGid=uos.gid ";
		return this.emiQueryList(sql, AaGoods.class, match, true,
				Constants.CACHE_GOODS);
	}

	// /////////////////////////////////////
	@Override
	public List<AaGoods> setGoodsSortNameByUid(String goodsUid) {
		Map match = new HashMap();
		match.put("cGoodsSortName", "AA_Goods.cGoodsSortName");
		// match.put("别名", "bean的属性");
		match.put("cComUnitName", "AA_Goods.comUnitName");
		match.put("ichangRate", "AA_Goods.ichangRate");
		match.put("cassChangRate", "AA_Goods.cassChangRate");
		match.put("cassComUnitName", "AA_Goods.cassComUnitName");
		// "g" AA_Goods的别名
		String sql = "select tt.* ,cu.cComUnitName AS cassComUnitName ,"
				+ "cu.iChangRate as cassChangRate from  (select"
				+ CommonUtil.colsFromBean(AaGoods.class, "goods")
				+ ",goodsSort.cGoodsSortName,cunit.cComUnitName,cunit.iChangRate from AA_Goods as goods, "
				+ "AA_GoodsSort AS goodsSort,AA_ComputationUnit AS cunit "
				+ "where goods.gGoodsSortUid=goodsSort.gId and goods.cGoodsUnit=cunit.gid and goods.gid='"
				+ goodsUid
				+ "') as tt "
				+ "left join  AA_ComputationUnit  AS cu ON tt.cassComUnitGid=cu.gid";
		return this.emiQueryList(sql, AaGoods.class, match, true,
				Constants.CACHE_GOODS);
	}

	@Override
	public boolean addGoods(AaGoods goods) {
		return this.emi_core_cache(goods, Constants.CACHE_GOODS, 0, null);
	}

	@Override
	public boolean delGoods(String goodsGid) {
		return this.emi_core_cache(null, Constants.CACHE_GOODS, 1, goodsGid);
	}

	@Override
	public AaGoods getGoods(String goodsGid) {
		String key = Constants.CACHE_GOODS + "_" + goodsGid;
		return (AaGoods) this.emi_core_cache_get(key);
	}

	@Override
	public List<AaGoods> getGoodsList(String[] goodsList) {
		List obj = this.emi_core_cache_getList(goodsList);
		return (List<AaGoods>) obj;
	}

	@Override
	public List<WmNowstock> setStocks() {
		String sql = "SELECT sum(availableQuantity) AS number , gGoodsUid FROM WM_AllocationStock"
				+ "  group BY gGoodsUid";
		return this.emiQueryList(sql, WmNowstock.class, null, true,
				Constants.CACHE_STOCK);
	}

	@Override
	public List<WmNowstock> setStocksById(String goodsUid) {
		String sql = "SELECT sum(availableQuantity) AS number , gGoodsUid FROM WM_AllocationStock"
				+ "  where gGoodsUid='" + goodsUid + "'";
		return this.emiQueryList(sql, WmNowstock.class, null, true,
				Constants.CACHE_STOCK);
	}

	@Override
	public boolean setStock(WmNowstock allocationStock) {
		return this.emi_core_cache(allocationStock, Constants.CACHE_STOCK, 0,
				null);
	}

	@Override
	public boolean delStock(String goodsGid) {
		return this.emi_core_cache(null, Constants.CACHE_STOCK, 1, goodsGid);
	}

	@Override
	public BigDecimal getStock(String goodsGid) {
		String key = Constants.CACHE_STOCK + "_" + goodsGid;
		WmNowstock stock = (WmNowstock) this.emi_core_cache_get(key);
		return (BigDecimal) stock.getAvailable();
	}

	@Override
	public List<WmNowstock> getStockList(String[] goodsGids) {
		List obj = this.emi_core_cache_getList(goodsGids);
		return (List<WmNowstock>) obj;
	}

	@Override
	public List<AaDepartment> setDepartments() {
		String sql = "select "
				+ CommonUtil.colsFromBean(AaDepartment.class, "department")
				+ " from AA_Department as department";
		return this.emiQueryList(sql, AaDepartment.class, null, true,
				Constants.CACHE_DEPARTMENT);
	}

	@Override
	public boolean addDepartment(AaDepartment department) {
		return this.emi_core_cache(department, Constants.CACHE_DEPARTMENT, 0,
				null);
	}

	@Override
	public boolean delDepartment(String departmentGid) {
		return this.emi_core_cache(null, Constants.CACHE_DEPARTMENT, 1,
				departmentGid);
	}

	@Override
	public AaDepartment getDepartment(String departmentGid) {
		String key = Constants.CACHE_DEPARTMENT + "_" + departmentGid;
		return (AaDepartment) this.emi_core_cache_get(key);
	}

	@Override
	public List<AaDepartment> getDepartmentList(String[] departmentList) {
		List obj = this.emi_core_cache_getList(departmentList);
		return (List<AaDepartment>) obj;
	}

	@Override
	public boolean setDepartmentList() {
		this.init();
		String sql = "select "
				+ CommonUtil.colsFromBean(AaDepartment.class, "department")
				+ " from AA_Department as department";
		List<AaDepartment> departmentList = this.emiQueryList(sql,
				AaDepartment.class);
		return getMemCache().set(Constants.CACHE_DEPARTMENT, departmentList);
	}

	@Override
	public List<AaDepartment> getDepartmentList() {
		return (List<AaDepartment>) this
				.emi_core_cache_get(Constants.CACHE_DEPARTMENT);
	}

	@Override
	public List<AaPerson> setPersons() {
		String sql = "select"
				+ CommonUtil.colsFromBean(AaPerson.class, "person")
				+ "FROM AA_Person as person";
		return this.emiQueryList(sql, AaPerson.class, null, true,
				Constants.CACHE_PERSON);
	}

	@Override
	public boolean addPerson(AaPerson person) {
		return this.emi_core_cache(person, Constants.CACHE_PERSON, 0, null);
	}

	@Override
	public boolean delPerson(String personGid) {
		return this.emi_core_cache(null, Constants.CACHE_PERSON, 1, personGid);
	}

	@Override
	public AaPerson getPerson(String personGid) {
		String key = Constants.CACHE_PERSON + "_" + personGid;
		return (AaPerson) this.emi_core_cache_get(key);
	}

	@Override
	public List<AaPerson> getPersonList(String[] personList) {
		List obj = this.emi_core_cache_getList(personList);
		return (List<AaPerson>) obj;
	}

	// ////////////////////////////////////////////
	@Override
	public List<AaWarehouse> setWareHouses() {
		String sql = "select"
				+ CommonUtil.colsFromBean(AaWarehouse.class, "warehouse")
				+ " FROM AA_WareHouse as warehouse";
		return this.emiQueryList(sql, AaWarehouse.class, null, true,
				Constants.CACHE_WAREHOUSE);
	}

	@Override
	public List<AaWarehouse> setWareHousesByWhUid(String whUid) {
		String sql = "select"
				+ CommonUtil.colsFromBean(AaWarehouse.class, "warehouse")
				+ " FROM AA_WareHouse as warehouse where warehouse.gId='"
				+ whUid + "'";
		return this.emiQueryList(sql, AaWarehouse.class, null, true,
				Constants.CACHE_WAREHOUSE);
	}

	@Override
	public boolean addWareHouse(AaWarehouse house) {
		return this.emi_core_cache(house, Constants.CACHE_WAREHOUSE, 0, null);
	}

	@Override
	public boolean delWareHouse(String wareHouseGid) {
		return this.emi_core_cache(null, Constants.CACHE_WAREHOUSE, 1,
				wareHouseGid);
	}

	@Override
	public AaWarehouse getWareHouse(String wareHouseGid) {
		String key = Constants.CACHE_WAREHOUSE + "_" + wareHouseGid;
		return (AaWarehouse) this.emi_core_cache_get(key);
	}

	@Override
	public List<AaWarehouse> getWareHouseList(String[] wareHouseList) {
		List obj = this.emi_core_cache_getList(wareHouseList);
		return (List<AaWarehouse>) obj;
	}

	@Override
	public List<AaGoodsallocation> setGoodsAllocation() {
		Map match = new HashMap();
		// 实体点属性
		match.put("whName", "whName");
		match.put("whcode", "whcode");
		String sql = "select"
				+ CommonUtil.colsFromBean(AaGoodsallocation.class, "allocation")
				+ ",warehouse.whName,warehouse.whcode FROM dbo.AA_GoodsAllocation allocation with (nolock) "
				+ "LEFT JOIN AA_WareHouse warehouse  with (nolock) ON allocation.WhUid=warehouse.gId";
		return this.emiQueryList(sql, AaGoodsallocation.class, match, true,
				Constants.CACHE_GOODSALLOCATION);
	}

	@Override
	public boolean addGoodsAllocation(AaGoodsallocation goodsAllocation) {
		return this.emi_core_cache(goodsAllocation,
				Constants.CACHE_GOODSALLOCATION, 0, null);
	}

	@Override
	public boolean delGoodsAllocation(String goodsAllocationGid) {
		return this.emi_core_cache(null, Constants.CACHE_GOODSALLOCATION, 1,
				goodsAllocationGid);
	}

	@Override
	public AaGoodsallocation getGoodsAllocation(String goodsAllocationGid) {
		String key = Constants.CACHE_GOODSALLOCATION + "_" + goodsAllocationGid;
		return (AaGoodsallocation) this.emi_core_cache_get(key);
	}

	@Override
	public List<AaGoodsallocation> getGoodsAllocationList(
			String[] goodsAllocationList) {
		List obj = this.emi_core_cache_getList(goodsAllocationList);
		return (List<AaGoodsallocation>) obj;
	}

	// //////////////////////////////////////////////////////////////
	@Override
	public List<AaProviderCustomer> setProviderCustomer() {
		String sql = "SELECT pk,gid,pcCode,pcName,addr,soulationId,beginTimes,endTimes,sobId,orgId,customerId,providerId,isDel"
				+ " FROM dbo.AA_Provider_Customer";
		return this.emiQueryList(sql, AaProviderCustomer.class, null, true,
				Constants.CACHE_PROVIDERCUSTOMER);
	}

	@Override
	public boolean addProviderCustomer(AaProviderCustomer providerCustomer) {
		return this.emi_core_cache(providerCustomer,
				Constants.CACHE_PROVIDERCUSTOMER, 0, null);
	}

	@Override
	public boolean delProviderCustomer(String providerCustomerGid) {
		return this.emi_core_cache(null, Constants.CACHE_PROVIDERCUSTOMER, 1,
				providerCustomerGid);
	}

	@Override
	public AaProviderCustomer getProviderCustomer(String providerCustomerGid) {
		String key = Constants.CACHE_PROVIDERCUSTOMER + "_"
				+ providerCustomerGid;
		return (AaProviderCustomer) this.emi_core_cache_get(key);
	}

	@Override
	public List<AaProviderCustomer> getProviderCustomerList(
			String[] providerCustomerList) {
		List obj = this.emi_core_cache_getList(providerCustomerList);
		return (List<AaProviderCustomer>) obj;
	}

	// //////////////////////////////////////////
	@Override
	public List<MesAaWorkcenter> setMESWorkCenter() {
		Map match = new HashMap();
		match.put("depCode", "depCode");
		match.put("depName", "depName");
		String sql = "SELECT "+CommonUtil.colsFromBean(MesAaWorkcenter.class,"wc")+",d.depCode,d.depName FROM MES_AA_WorkCenter wc left join AA_Department d on wc.depUid=d.gid ";
		return this.emiQueryList(sql, MesAaWorkcenter.class, match, true,
				Constants.CACHE_MESWORKCENTER);
	}

	@Override
	public boolean addWorkCenter(MesAaWorkcenter workCenter) {
		return this.emi_core_cache(workCenter, Constants.CACHE_MESWORKCENTER,
				0, null);
	}

	@Override
	public boolean delMES_WorkCenter(String workCenterGid) {
		return this.emi_core_cache(null, Constants.CACHE_MESWORKCENTER, 1,
				workCenterGid);
	}

	@Override
	public MesAaWorkcenter getworkCenter(String workCenterGid) {
		String key = Constants.CACHE_MESWORKCENTER + "_" + workCenterGid;
		return (MesAaWorkcenter) this.emi_core_cache_get(key);
	}
	
	@Override
	public MesAaStation getMesAaStation(String mesAaStationGid) {
		String key = Constants.CACHE_MESWORKSTATION + "_" + mesAaStationGid;
		return (MesAaStation) this.emi_core_cache_get(key);
	}
	
	@Override
	public List<AaGroup> setAaGroup() {
		String sql = "select"+ CommonUtil.colsFromBean(AaGroup.class, "g")+ " FROM AA_Group as g";
		return this.emiQueryList(sql, AaGroup.class, null, true,Constants.CACHE_GROUP);
	}
	
	@Override
	public List<Classify> setClassify(){
		String sql = "select"+ CommonUtil.colsFromBean(Classify.class, "c")+ " FROM classify as c ";
		return this.emiQueryList(sql, Classify.class, null, true,Constants.CACHE_CLASSIFY);
	}
	
	@Override
	public List<Unit> setUnit() {
		String sql = "select"+ CommonUtil.colsFromBean(Unit.class, "u")+ " FROM unit as u ";
		return this.emiQueryList(sql, Unit.class, null, true,Constants.CACHE_UNIT);
	}
	
	/**
	 * @category 获得类别
	 *2016 2016年4月20日下午3:21:02
	 *MesAaStation
	 *宋银海
	 */
	public Classify getClassify(String classifyGid) {
		String key = Constants.CACHE_CLASSIFY + "_" + classifyGid;
		return (Classify) this.emi_core_cache_get(key);
	}
	
	/**
	 * @category 获得计量单位
	 *2016 2016年4月20日下午3:21:02
	 *MesAaStation
	 *宋银海
	 */
	public Unit getUnit(String unitGid) {
		String key = Constants.CACHE_UNIT + "_" + unitGid;
		return (Unit) this.emi_core_cache_get(key);
	}
	
	
	@Override
	public AaGroup getAaGroup(String groupGid) {
		String key = Constants.CACHE_GROUP + "_" + groupGid;
		return (AaGroup) this.emi_core_cache_get(key);
	}
	

	@Override
	public List<MesAaWorkcenter> getworkCenterList(String[] workCenterList) {
		List obj = this.emi_core_cache_getList(workCenterList);
		return (List<MesAaWorkcenter>) obj;
	}

	// /
	@Override
	public List<Equipment> setMESEquipment() {
		String sql = "select"
				+ CommonUtil.colsFromBean(Equipment.class, "e")
				+ " FROM equipment as e";
		return this.emiQueryList(sql, Equipment.class, null, true,
				Constants.CACHE_MESEQUIPMENT);
	}
	
	@Override
	public List<Mould> setMould() {
		
		Map match = new HashMap();
		match.put("mouldStyleName", "Mould.mouldStyleName");
		match.put("depName", "Mould.depName");
		match.put("customer", "Mould.customer");
		match.put("provider", "Mould.provider");
		
		String sql = "select"
				+ CommonUtil.colsFromBean(Mould.class, "m")
				+ ",cf.classificationName mouldStyleName,d.depName,c.pcName customer,p.pcName provider "
				+ " FROM mould as m with (nolock) left join classify cf on m.mouldStyle=cf.gid"
				+ " left join AA_Department d on m.currentDeptGid=d.gid"
				+ " left join AA_Provider_Customer c on m.cutomerGid=c.gid"
				+ " left join AA_Provider_Customer p on m.providerGid=p.gid where m.isDelete=0";
		return this.emiQueryList(sql, Mould.class, match, true,
				Constants.CACHE_MOULD);
	}

	@Override
	public boolean addEquipment(Equipment equipment) {
		return this.emi_core_cache(equipment, Constants.CACHE_MESEQUIPMENT, 0,
				null);
	}

	@Override
	public boolean delMES_Equipment(String equipmentGid) {
		return this.emi_core_cache(null, Constants.CACHE_MESEQUIPMENT, 1,
				equipmentGid);
	}

	@Override
	public Equipment getMESEquipment(String equipmentGid) {
		String key = Constants.CACHE_MESEQUIPMENT + "_" + equipmentGid;
		return (Equipment) this.emi_core_cache_get(key);
	}
	
	@Override
	public Mould getMould(String mouldGid) {
		String key = Constants.CACHE_MOULD + "_" + mouldGid;
		return (Mould) this.emi_core_cache_get(key);
	}

	@Override
	public List<Equipment> getEquipmentList(String[] equipmentList) {
		List obj = this.emi_core_cache_getList(equipmentList);
		return (List<Equipment>) obj;
	}

	// /////////////////////

	@Override
	public List<MesWmStandardprocess> setStandardProcess() {
		// 实体点属性
//		Map match = new HashMap();
//		match.put("wcname", "MES_WM_StandardProcess.wcname");
		String sql = "select "+ CommonUtil.colsFromBean(MesWmStandardprocess.class, "mwsp")
				+ " FROM dbo.MES_WM_StandardProcess mwsp ";
		return this.emiQueryList(sql, MesWmStandardprocess.class, null, true,Constants.CACHE_MESSTANDARDPROCESS);
	}

	@Override
	public boolean addStandardProcess(MesWmStandardprocess standardProcess) {
		return this.emi_core_cache(standardProcess,
				Constants.CACHE_MESSTANDARDPROCESS, 0, null);
	}

	@Override
	public boolean delMES_WM_StandardProcess(String standardProcessGid) {
		return this.emi_core_cache(null, Constants.CACHE_MESSTANDARDPROCESS, 1,
				standardProcessGid);
	}

	@Override
	public MesWmStandardprocess getMESStandardProcess(String standardProcessGid) {
		String key = Constants.CACHE_MESSTANDARDPROCESS + "_"
				+ standardProcessGid;
		return (MesWmStandardprocess) this.emi_core_cache_get(key);
	}

	@Override
	public List<MesWmStandardprocess> getMESStandardProcessList(
			String[] standardProcessList) {
		List obj = this.emi_core_cache_getList(standardProcessList);
		return (List<MesWmStandardprocess>) obj;
	}

	// /////////////
	@Override
	public List<WmAllocationstock> setAllocationStockBygoodsUid(String goodsUid) {
		Map match = new HashMap();
		match.put("cGoodsName", "WM_AllocationStock.cGoodsName");
		match.put("cWhName", "WM_AllocationStock.cWhName");
		match.put("cName", "WM_AllocationStock.cName");
		String sql = "select"
				+ CommonUtil.colsFromBean(WmAllocationstock.class, "stock")
				+ ",goods.cGoodsName,wh.cWhName,ga.cName from WM_AllocationStock AS stock "
				+ "LEFT JOIN AA_Goods AS goods ON stock.gGoodsUid=goods.gId "
				+ "LEFT JOIN AA_WareHouse AS wh ON stock.gWhUid=wh.gId "
				+ "LEFT JOIN AA_GoodsAllocation AS ga ON stock.gGoodsAllocationUid=ga.gId where stock.ggoodsUid='"
				+ goodsUid + "'";
		return this.emiQueryList(sql, WmAllocationstock.class, match, true,
				null);
	}

	@Override
	public void setAllocationStockS() {
		String sql = "select"
				+ CommonUtil.colsFromBean(WmAllocationstock.class, "stock")
				+ ",goods.cGoodsName,wh.cWhName,ga.cName from WM_AllocationStock AS stock "
				+ "LEFT JOIN AA_Goods AS goods ON stock.gGoodsUid=goods.gId "
				+ "LEFT JOIN AA_WareHouse AS wh ON stock.gWhUid=wh.gId "
				+ "LEFT JOIN AA_GoodsAllocation AS ga ON stock.gGoodsAllocationUid=ga.gId";
		setAllocationStockSql(sql);
	}

	@Override
	public void setAllocationStockByWhUid(String whUid) {
		String sql = "select"
				+ CommonUtil.colsFromBean(WmAllocationstock.class, "stock")
				+ ",goods.cGoodsName,wh.cWhName,ga.cName from WM_AllocationStock AS stock "
				+ "LEFT JOIN AA_Goods AS goods ON stock.gGoodsUid=goods.gId "
				+ "LEFT JOIN AA_WareHouse AS wh ON stock.gWhUid=wh.gId "
				+ "LEFT JOIN AA_GoodsAllocation AS ga ON stock.gGoodsAllocationUid=ga.gId "
				+ "where stock.ggoodsUid in (SELECT gGoodsUid FROM WM_AllocationStock "
				+ "WHERE gWhUid='" + whUid + "')";
		setAllocationStockSql(sql);
	}

	public void setAllocationStockSql(String sql) {
		Map match = new HashMap();
		match.put("cGoodsName", "WM_AllocationStock.cGoodsName");
		match.put("cWhName", "WM_AllocationStock.cWhName");
		match.put("cName", "WM_AllocationStock.cName");

		List<WmAllocationstock> asList = this.emiQueryList(sql,
				WmAllocationstock.class, match, false, null);
		String att = Constants.CACHE_ALLOCATIONSTOCKLIST;
		HashMap<String, List<WmAllocationstock>> asMap = new HashMap<String, List<WmAllocationstock>>();
		List<WmAllocationstock> wmasList = null;
		for (WmAllocationstock as : asList) {
			wmasList = new ArrayList<WmAllocationstock>();
			String goodsUid = as.getGoodsuid();
			// 如果goodsUid在hashmap中有值则修改，没有则添加
			if (asMap.containsKey(goodsUid)) {
				wmasList = asMap.get(goodsUid);
				wmasList.add(as);
				asMap.put(goodsUid, wmasList);
			} else {
				wmasList.add(as);
				asMap.put(goodsUid, wmasList);
			}
		}

		Iterator ite = asMap.keySet().iterator();
		while (ite.hasNext()) {
			String key = (String) ite.next();
			getMemCache().set(att + "_" + key, asMap.get(key));
		}
	}

	@Override
	public boolean addAllocationStock(String goodsUid) {
		Map match = new HashMap();
		match.put("cGoodsName", "WM_AllocationStock.cGoodsName");
		match.put("cWhName", "WM_AllocationStock.cWhName");
		match.put("cName", "WM_AllocationStock.cName");
		String sql = "select"
				+ CommonUtil.colsFromBean(WmAllocationstock.class, "stock")
				+ ",goods.cGoodsName,wh.cWhName,ga.cName from WM_AllocationStock AS stock "
				+ "LEFT JOIN AA_Goods AS goods ON stock.gGoodsUid=goods.gId "
				+ "LEFT JOIN AA_WareHouse AS wh ON stock.gWhUid=wh.gId "
				+ "LEFT JOIN AA_GoodsAllocation AS ga ON stock.gGoodsAllocationUid=ga.gId where stock.GoodsUid='"
				+ goodsUid + "'";
		List<WmAllocationstock> asList = this.emiQueryList(sql,
				WmAllocationstock.class, match, false, null);
		String att = Constants.CACHE_ALLOCATIONSTOCKLIST;
		String key = att + "_" + goodsUid;
		return getMemCache().set(key, asList);
	}

	@Override
	public boolean delAllocationStock(String goodsUid) {
		String key = Constants.CACHE_ALLOCATIONSTOCKLIST + "_" + goodsUid;
		return getMemCache().delete(key);
	}

	@Override
	public List<WmAllocationstock> getAllocationStock(String goodsUid) {
		String key = Constants.CACHE_ALLOCATIONSTOCKLIST + "_" + goodsUid;
		return (List<WmAllocationstock>) this.emi_core_cache_get(key);
	}

	@Override
	public List<WmAllocationstock> getAllocationStockList(String[] goodsUidList) {
		List obj = this.emi_core_cache_getList(goodsUidList);
		return (List<WmAllocationstock>) obj;
	}

	@Override
	public boolean setAllocationStock(String goodsUid,
			List<WmAllocationstock> wmas) {
		String key = Constants.CACHE_ALLOCATIONSTOCKLIST + "_" + goodsUid;
		return getMemCache().set(key, wmas);
	}

	// ///////////////////
	@Override
	public List<YmUser> setUser() {
		Map match = new HashMap();
		match.put("sobName", "YmUser.sobName");
		match.put("orgName", "YmUser.orgName");
		String sql = "select mwa.sobName,ao.orgName ,"
				+ CommonUtil.colsFromBean(YmUser.class, "yu")
				+ " FROM YM_User yu left join AA_Org as ao "
				+ "ON yu.orggId=ao.gid left join MES_WM_AccountingInform as mwa"
				+ " on yu.sobgId=mwa.gid";
		return this.emiQueryList(sql, YmUser.class, match, true,
				Constants.CACHE_USER);
	}

	@Override
	public boolean addUser(YmUser user) {
		return this.emi_core_cache(user, Constants.CACHE_USER, 0, null);
	}

	@Override
	public boolean delUser(String userId) {
		return this.emi_core_cache(null, Constants.CACHE_USER, 1, userId);
	}

	@Override
	public YmUser getUser(String userId) {
		String key = Constants.CACHE_USER + "_" + userId;
		return (YmUser) this.emi_core_cache_get(key);
	}

	@Override
	public List<YmUser> getUserList(String[] userList) {
		List obj = this.emi_core_cache_getList(userList);
		return (List<YmUser>) obj;
	}

	@Override
	public List<YmUser> getU(String userID) {
		Map match = new HashMap();
		match.put("gid", "YM_User.personGid");
		match.put("cperCode", "YM_User.cperCode");
		match.put("cperName", "YM_User.cperName");
		match.put("bperSex", "YM_User.bperSex");
		match.put("dperBirthday", "YM_User.dperBirthday");
		match.put("dbeginDate", "YM_User.dbeginDate");
		match.put("dendDate", "YM_User.dendDate");
		match.put("istate", "YM_User.istate");
		match.put("barcode", "YM_User.barcode");

		String sql = "select"
				+ CommonUtil.colsFromBean(YmUser.class, "yu")
				+ ",ap.gid,ap.cperCode, ap.cperName, ap.bperSex, ap.dperBirthday,"
				+ " ap.dbeginDate, ap.dendDate, ap.istate, ap.barcode from YM_User AS yu "
				+ " LEFT JOIN AA_Person AS ap ON yu.gId=ap.gUserUid"
				+ " WHERE yu.gId='" + userID + "'";
		return this.emiQueryList(sql, YmUser.class, match, true,
				Constants.CACHE_SESSION);
	}

	// ////////////////////////////////////////////////////////////
	@Override
	public boolean addOrg(AaOrg org) {
		return this.emi_core_cache(org, Constants.CACHE_ORG, 0, null);
	}

	@Override
	public List<AaOrg> setOrgs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delOrg(String orgId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AaOrg getOrgs(String orgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AaOrg> getOrgList(String[] orgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delMESWMStandardProcess(String standardProcessGid) {
		// TODO Auto-generated method stub
		return false;
	}

}
