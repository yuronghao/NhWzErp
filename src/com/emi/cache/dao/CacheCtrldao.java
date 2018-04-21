package com.emi.cache.dao;

import java.math.BigDecimal;
import java.util.List;

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
 * Title:缓存控制数据库
 * 
 * @Copyright: Copyright (c) v1.0
 * @Company: 江苏一米智能科技股份有限公司
 * @project name: WMS
 * @author: 李小伟
 * @version:v1.0
 * @time:2015-5-13
 */
public interface CacheCtrldao {
	

	/**
	 * Title:加入物料(批量加入缓存，在数据库中取出)
	 * 
	 * @author 李小伟
	 * @param 
	 * @return list
	 * 
	 **/
	public List<AaGoods> setGoods();

	/**
	 * Title:添加单个物料进入缓存
	 * 
	 * @author 李小伟
	 * @param AA_Goods
	 * @return boolean
	 * **/
	public boolean addGoods(AaGoods goods);

	/**
	 * Title:在缓存中删除单个物料
	 * 
	 * @author 李小伟
	 * @param goodsGid
	 * @return boolean
	 */
	public boolean delGoods(String goodsGid);

	/**
	 * Title:在缓存中查询单个物料
	 * 
	 * @author 李小伟
	 * @param goodsGid
	 */
	public AaGoods getGoods(String goodsGid);

	/**
	 * Title:在缓存中查询多个goods
	 * 
	 * @author 李小伟
	 * 
	 * */
	public List<AaGoods> getGoodsList(String[] goodsList);

	/**
	 * @title 修改单个物料信息
	 * @author 李小伟
	 */
	public List<AaGoods> setGoodsSortNameByUid(String goodsUid);
	
	
	
	/**
	 * @author 李小伟
	 * @Title 加入单个组织
	 * @return
	 */
	public  boolean addOrg(AaOrg org);
	
	/**
	 * @author 李小伟
	 * @Title 批量加入多个组织
	 * @return
	 */
	public List<AaOrg>  setOrgs();
	
	/**
	 * @author 李小伟
	 * @Title 删除组织
	 * @param orgId
	 * @return
	 */
	public boolean delOrg(String orgId);
	
	/**
	 * @author 李小伟
	 * @Title 获取单个组织
	 * @param orgId
	 * @return
	 */
	public AaOrg getOrgs(String orgId);
	
	
	/**
	 * Title:在缓存中查询多个组织
	 * 
	 * @author 李小伟
	 * 
	 * */
	public List<AaOrg> getOrgList(String[] orgId  );
	
	/**
	 * @author 李小伟
	 * @Title 删除现存量
	 * @param goodsGid
	 * @return
	 */
	public boolean delStock(String goodsGid);

	/**
	 * @author 李小伟
	 * @Title 查找单个物品现存量
	 * @param goodsGid
	 * @return
	 */
	public BigDecimal getStock(String goodsGid);


	


	/**
	 * Title:加入部门名称(批量加入缓存，在数据库中取出)
	 * 
	 * @author 李小伟
	 * @param 
	 * @return list
	 * 
	 **/
	public List<AaDepartment> setDepartments();
	
	/**
	 * Title:加入部门名称(批量加入缓存，在数据库中取出)
	 * 
	 * @author 李小伟
	 * @param 
	 * @return list
	 * 
	 **/
	public boolean setDepartmentList();

	/**
	 * Title:添加单个部门进入缓存
	 * 
	 * @author 李小伟
	 * @param department
	 * @return boolean
	 * **/
	public boolean addDepartment(AaDepartment department);

	/**
	 * Title:在缓存中删除单个部门
	 * 
	 * @author 李小伟
	 * @param departmentGid
	 * @return boolean
	 */
	public boolean delDepartment(String departmentGid);

	/**
	 * Title:在缓存中查询单个部门
	 * 
	 * @author 李小伟
	 * @param departmentGid
	 */
	public AaDepartment getDepartment(String departmentGid);

	/**
	 * Title:在缓存中查询多个部门
	 * 
	 * @author 李小伟
	 * 
	 * */
	public List<AaDepartment> getDepartmentList(String[] departmentList  );
	/**
	 * Title:在缓存中查询全部部门
	 * 
	 * @author 李小伟
	 * 
	 * */
	public List<AaDepartment> getDepartmentList();


	/**
	 * Title:加入人员(批量加入缓存，在数据库中取出)
	 * 
	 * @author 李小伟
	 * @param 
	 * @return list
	 * 
	 **/
	public List<AaPerson> setPersons();

	/**
	 * Title:添加单个人员进入缓存
	 * 
	 * @author 李小伟
	 * @param person
	 * @return boolean
	 * **/
	public boolean addPerson(AaPerson person);

	/**
	 * Title:在缓存中删除单个人员
	 * 
	 * @author 李小伟
	 * @param personGid
	 * @return boolean
	 */
	public boolean delPerson(String personGid);

	/**
	 * Title:在缓存中查询单个人员
	 * 
	 * @author 李小伟
	 * @param personGid
	 */
	public AaPerson getPerson(String personGid);

	/**
	 * Title:在缓存中查询多个人员
	 * 
	 * @author 李小伟
	 * 
	 * */
	public List<AaPerson> getPersonList(String[] personList  );

	/**
	 * Title:加入仓库(批量加入缓存，在数据库中取出)
	 * 
	 * @author 李小伟
	 * @return list
	 * 
	 **/
	public List<AaWarehouse> setWareHouses();
	
	/**
	 * Title:插入单条仓库数据
	 * 
	 * @author 李小伟
	 * @return list
	 * 
	 **/
	public List<AaWarehouse> setWareHousesByWhUid(String whUid); 
	/**
	 * Title:添加单个仓库进入缓存
	 * 
	 * @author 李小伟
	 * @param house
	 * @return boolean
	 * **/
	public boolean addWareHouse(AaWarehouse house);

	/**
	 * Title:在缓存中删除单个仓库
	 * 
	 * @author 李小伟
	 * @param wareHouseGid
	 * @return boolean
	 */
	public boolean delWareHouse(String wareHouseGid);

	/**
	 * Title:在缓存中查询单个仓库
	 * 
	 * @author 李小伟
	 * @param wareHouseGid
	 */
	public AaWarehouse getWareHouse(String wareHouseGid);

	/**
	 * Title:在缓存中查询多个仓库
	 * 
	 * @author 李小伟
	 * 
	 * */
	public List<AaWarehouse> getWareHouseList(String[] wareHouseList  );

	// ///////////////////////////////////////////////////////////////////////////
	/**
	 * Title:加入货位(批量加入缓存，在数据库中取出)
	 * 
	 * @author 李小伟
	 * @return list
	 * 
	 **/
	public List<AaGoodsallocation> setGoodsAllocation();
	

	/**
	 * Title:添加单个货位进入缓存
	 * 
	 * @author 李小伟
	 * @param goodsAllocation
	 * @return boolean
	 * **/
	public boolean addGoodsAllocation(AaGoodsallocation goodsAllocation);

	/**
	 * Title:在缓存中删除单个货位
	 * 
	 * @author 李小伟
	 * @param goodsAllocationGid
	 * @return boolean
	 */
	public boolean delGoodsAllocation(String goodsAllocationGid);

	/**
	 * Title:在缓存中查询单个货位
	 * 
	 * @author 李小伟
	 * @param goodsAllocationGid
	 */
	public AaGoodsallocation getGoodsAllocation(String goodsAllocationGid);

	/**
	 * Title:在缓存中查询多个货位
	 * 
	 * @author 李小伟
	 * 
	 * */
	public List<AaGoodsallocation> getGoodsAllocationList(
			String[] goodsAllocationList  );
	
	/////////////////////////////////////////////////////////////

	/**
	 * Title:加入客商档案(批量加入缓存，在数据库中取出)
	 * 
	 * @author 李小伟
	 * @return list
	 * 
	 **/
	public List<AaProviderCustomer> setProviderCustomer();

	/**
	 * Title:添加客商档案进入缓存
	 * 
	 * @author 李小伟
	 * @param providerCustomer
	 * @return boolean
	 * **/
	public boolean addProviderCustomer(AaProviderCustomer providerCustomer);

	/**
	 * Title:在缓存中删除单个客商档案
	 * 
	 * @author 李小伟
	 * @param providerCustomerGid
	 * @return boolean
	 */
	public boolean delProviderCustomer(String providerCustomerGid);

	/**
	 * Title:在缓存中查询单个客商档案
	 * 
	 * @author 李小伟
	 * @param providerCustomerGid
	 */
	public AaProviderCustomer getProviderCustomer(String providerCustomerGid);

	/**
	 * Title:在缓存中查询多个客商档案
	 * @param providerCustomer
	 * @author 李小伟
	 * 
	 * */
	public List<AaProviderCustomer> getProviderCustomerList(
			String[] providerCustomerList  );
	
	///////////////////////////////////////////////
	//工作中心基础信息
	/**
	 * Title:加入工作中心(批量加入缓存，在数据库中取出)
	 * 
	 * @author 李小伟
	 * @return list
	 * 
	 **/
	public List<MesAaWorkcenter> setMESWorkCenter();

	/**
	 * Title:添加单个工作中心进入缓存
	 * 
	 * @author 李小伟
	 * @param workCenter
	 * @return boolean
	 * **/
	public boolean addWorkCenter(MesAaWorkcenter workCenter);

	/**
	 * Title:在缓存中删除单个工作中心
	 * 
	 * @author 李小伟
	 * @param workCenterGid
	 * @return boolean
	 */
	public boolean delMES_WorkCenter(String workCenterGid);

	/**
	 * Title:在缓存中查询单个工作中心
	 * 
	 * @author 李小伟
	 * @param workCenterGid
	 */
	public MesAaWorkcenter getworkCenter(String workCenterGid);

	/**
	 * Title:在缓存中查询多个工作中心
	 * @param workCenterList
	 * @author 李小伟
	 *
	 * */
	public List<MesAaWorkcenter> getworkCenterList(
			String[] workCenterList  );
	
	
	/**
	 * @category 获得工位
	 *2016 2016年4月20日下午3:21:02
	 *MesAaStation
	 *宋银海
	 */
	public MesAaStation getMesAaStation(String mesAaStationGid);
	
	
	/**
	 * @category 获得工作组
	 *2016 2016年5月4日下午3:16:18
	 *List<AaGroup>
	 *宋银海
	 */
	public List<AaGroup> setAaGroup();
	
	
	/**
	 * @category 获得类别
	 *2016 2016年5月4日下午3:16:18
	 *List<AaGroup>
	 *宋银海
	 */
	public List<Classify> setClassify();
	
	
	/**
	 * @category 获得计量单位
	 *2016 2016年5月4日下午3:16:18
	 *List<AaGroup>
	 *宋银海
	 */
	public List<Unit> setUnit();
	
	/**
	 * @category 获得类别
	 *2016 2016年4月20日下午3:21:02
	 *MesAaStation
	 *宋银海
	 */
	public Classify getClassify(String classifyGid);
	
	/**
	 * @category 获得计量单位
	 *2016 2016年4月20日下午3:21:02
	 *MesAaStation
	 *宋银海
	 */
	public Unit getUnit(String unitGid);
	
	/**
	 * @category 获得工作组
	 *2016 2016年4月20日下午3:21:02
	 *MesAaStation
	 *宋银海
	 */
	public AaGroup getAaGroup(String groupGid);
	
	
	//设备基础信息
	
	/**
	 * Title:加入设备基础信息(批量加入缓存，在数据库中取出)
	 * 
	 * @author 李小伟
	 * @return list
	 * 
	 **/
	public List<Equipment> setMESEquipment();

	/**
	 * Title:添加单个设备基础信息进入缓存
	 * 
	 * @author 李小伟
	 * @param equipment
	 * @return boolean
	 * **/
	public boolean addEquipment(Equipment equipment);

	/**
	 * Title:在缓存中删除单个设备基础信息
	 * 
	 * @author 李小伟
	 * @param equipmentGid
	 * @return boolean
	 */
	public boolean delMES_Equipment(String equipmentGid);

	/**
	 * Title:在缓存中查询单个设备基础信息
	 * 
	 * @author 李小伟
	 * @param equipmentGid
	 */
	public Equipment getMESEquipment(String equipmentGid);
	
	public Mould getMould(String mouldGid);

	/**
	 * Title:在缓存中查询多个设备基础信息
	 * @param equipmentList
	 * @author 李小伟
	 * 
	 * */
	public List<Equipment> getEquipmentList(
			String[] equipmentList  );
	//工作中心所属的设备信息//有需要时写
	
	////工序信息
	/**
	 * Title:加入工序基础信息(批量加入缓存，在数据库中取出)
	 * 
	 * @author 李小伟
	 * @return list
	 * 
	 **/
	public List<MesWmStandardprocess> setStandardProcess();

	/**
	 * Title:添加单个工序基础信息进入缓存
	 * 
	 * @author 李小伟
	 * @param standardProcess
	 * @return boolean
	 * **/
	public boolean addStandardProcess(MesWmStandardprocess standardProcess);

	/**
	 * Title:在缓存中删除单个工序基础信息
	 * 
	 * @author 李小伟
	 * @param standardProcessGid
	 * @return boolean
	 */
	public boolean delMESWMStandardProcess(String standardProcessGid);

	/**
	 * Title:在缓存中查询单个工序基础信息
	 * 
	 * @author 李小伟
	 * @param standardProcessGid
	 */
	public MesWmStandardprocess getMESStandardProcess(String standardProcessGid);

	/**
	 * Title:在缓存中查询多个工序基础信息
	 * @param standardProcessList
	 * @author 李小伟
	 * 
	 * */
	public List<MesWmStandardprocess> getMESStandardProcessList(
			String[] standardProcessList  );
	
	
	///////////////货位现存量/////////////////////////
	/**
	 * Title:加入货位现存量信息(批量加入缓存，在数据库中取出)
	 * 货位现存量是一个特殊的插入方式，目前的架构不满足只能特殊处理
	 * @author 李小伟
	 * @return list
	 * 
	 **/
	public void setAllocationStockS();

	/**
	 * Title:添加/修改单个货位现存量信息进入缓存
	 * 
	 * @author 李小伟
	 * @param standardProcess
	 * @return boolean
	 * **/
	public boolean addAllocationStock(String goodsUid);

	/**
	 * Title:在缓存中删除单个货位现存量
	 * 
	 * @author 李小伟
	 * @param standardProcessGid
	 * @return boolean
	 */
	public boolean delAllocationStock(String goodsUid);

	/**
	 * Title:在缓存中查询单个货位现存量基础信息
	 * 
	 * @author 李小伟
	 * @param standardProcessGid
	 */
	public List<WmAllocationstock> getAllocationStock(String goodsUid);

	/**
	 * Title:在缓存中查询多个货位现存量
	 * @param standardProcessList
	 * @author 李小伟
	 * 
	 * */
	public List<WmAllocationstock> getAllocationStockList(
			String[] goodsUid  );
	
	/***
	 * @title 插入单条货位现存量缓存
	 * @param goodsUid
	 * @return
	 */
	public List<WmAllocationstock> setAllocationStockBygoodsUid(String goodsUid);
	
	/**
	 * 
	 * @param whUid
	 */
	public void setAllocationStockByWhUid(String whUid);

	
	/**
	 * @title添加货位现存量的缓存
	 * @param goodsUid
	 * @param wmas
	 * @return
	 */
	public boolean setAllocationStock(String goodsUid,List<WmAllocationstock> wmas);
	/**
	 * Title:加入用户(批量加入缓存，在数据库中取出)
	 * 
	 * @author 李小伟
	 * @param 
	 * @return list
	 * 
	 **/
	public List<YmUser> setUser();

	/**
	 * Title:添加单个物料进入缓存
	 * 
	 * @author 李小伟
	 * @param AA_Goods
	 * @return boolean
	 * **/
	public boolean addUser(YmUser user);

	/**
	 * Title:在缓存中删除单个用户
	 * 
	 * @author 李小伟
	 * @param goodsGid
	 * @return boolean
	 */
	public boolean delUser(String userId);

	/**
	 * Title:在缓存中查询单个用户
	 * 
	 * @author 李小伟
	 * @param goodsGid
	 */
	public YmUser getUser(String userId);

	/**
	 * Title:在缓存中查询多个goods
	 * 
	 * @author 李小伟
	 * 
	 * */
	public List<YmUser> getUserList(String[] userList);

	
	/**
	 * @title 获取用户信息和person信息
	 * @param userID
	 * @return
	 */
	public List<YmUser> getU(String userID);

	/**
	 * @author 李小伟
	 * @title 现存量
	 * @param goodsUid
	 * @return
	 */
	public List<WmNowstock> setStocksById(String goodsUid);

	boolean setStock(WmNowstock allocationStock);

	boolean delMES_WM_StandardProcess(String standardProcessGid);

	List<WmNowstock> getStockList(String[] goodsGids  );

	List<WmNowstock> setStocks();

	List<Mould> setMould();
	


	//新建销售订单插入缓存
	
	//关闭销售订单清除缓存
	
	//发货完成清除缓存

}
