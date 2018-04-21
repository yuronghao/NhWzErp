package com.emi.cache.service;

import java.math.BigDecimal;
import java.util.List;

import com.emi.wms.bean.AaDepartment;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.AaGoodsallocation;
import com.emi.wms.bean.AaGroup;
import com.emi.wms.bean.AaPerson;
import com.emi.wms.bean.AaProviderCustomer;
import com.emi.wms.bean.AaWarehouse;
import com.emi.wms.bean.Classify;
import com.emi.wms.bean.Equipment;
import com.emi.wms.bean.MESWMCostItem;
import com.emi.wms.bean.MesAaStation;
import com.emi.wms.bean.MesAaWorkcenter;
import com.emi.wms.bean.MesWmStandardprocess;
import com.emi.wms.bean.Mould;
import com.emi.wms.bean.Unit;
import com.emi.wms.bean.WmAllocationstock;
import com.emi.wms.bean.YmUser;


/**
 * 
 * @author 李小伟
 * @Title 缓存操作
 * 
 */
public interface CacheCtrlService {
	/**
	 * @category 加入物品属性名称(批量加入缓存，在数据库中取出)
	 * @author 李小伟
	 * @param
	 * @return list
	 * 
	 **/
	public List<AaGoods> setGoods();
	

	/**
	 * @category 添加单个物料进入缓存
	 * 
	 * @author 李小伟
	 * @param AA_Goods
	 * @return boolean
	 * **/
	public boolean addGoods(AaGoods goods);

	/**
	 * @category 在缓存中删除单个物料
	 * 
	 * @author 李小伟
	 * @param goodsGid
	 * @return boolean
	 */
	public boolean delGoods(String goodsGid);

	/**
	 * @category 在缓存中查询单个物料
	 * 
	 * @author 李小伟
	 * @param goodsGid
	 */
	public AaGoods getGoods(String goodsGid);

	/**
	 * @category 在缓存中查询多个goods
	 * 
	 * @author 李小伟
	 * 
	 * */
	public List<AaGoods> getGoodsList(String[] goodsList);
/**
	 * @category 维护goods的缓存
	 * @param i
	 * @param goodsUid
	 * @return
	 */
	
	public void maintenanceGoods(int i,String goodsUid);




	/**
	 * @author 李小伟
	 * @category 删除现存量
	 * @param goodsGid
	 * @return
	 */
	public boolean delStock(String goodsGid);

	/**
	 * @author 李小伟
	 * @category 查找单个物品现存量
	 * @param goodsGid
	 * @return
	 */
	public BigDecimal getStock(String goodsGid);



	/**
	 * @category 加入部门名称(批量加入缓存，在数据库中取出)
	 * 
	 * @author 李小伟
	 * @param
	 * @return list
	 * 
	 **/
	public List<AaDepartment> setDepartments();

	/**
	 * @category 添加单个部门进入缓存
	 * 
	 * @author 李小伟
	 * @param department
	 * @return boolean
	 * **/
	public boolean addDepartment(AaDepartment department);

	/**
	 * @category 在缓存中删除单个部门
	 * 
	 * @author 李小伟
	 * @param departmentGid
	 * @return boolean
	 */
	public boolean delDepartment(String departmentGid);

	/**
	 * @category 在缓存中查询单个部门
	 * 
	 * @author 李小伟
	 * @param departmentGid
	 */
	public AaDepartment getDepartment(String departmentGid);

	/**
	 * @category 在缓存中查询多个部门
	 * 
	 * @author 李小伟
	 * 
	 * */
	public List<AaDepartment> getDepartmentList(String[] departmentList);

	/**
	 * @category 加入部门名称(批量加入缓存，在数据库中取出)
	 * 
	 * @author 李小伟
	 * @param
	 * @return list
	 * 
	 **/
	public boolean setDepartmentList();

	/**
	 * @category 在缓存中查询全部部门
	 * 
	 * @author 李小伟
	 * 
	 * */
	public List<AaDepartment> getDepartmentList();

	/**
	 * @category 加入人员(批量加入缓存，在数据库中取出)
	 * 
	 * @author 李小伟
	 * @param
	 * @return list
	 * 
	 **/
	public List<AaPerson> setPersons();

	/**
	 * @category 添加单个人员进入缓存
	 * 
	 * @author 李小伟
	 * @param person
	 * @return boolean
	 * **/
	public boolean addPerson(AaPerson person);

	/**
	 * @category 在缓存中删除单个人员
	 * 
	 * @author 李小伟
	 * @param personGid
	 * @return boolean
	 */
	public boolean delPerson(String personGid);

	/**
	 * @category 在缓存中查询单个人员
	 * 
	 * @author 李小伟
	 * @param personGid
	 */
	public AaPerson getPerson(String personGid);

	/**
	 * @category 在缓存中查询多个人员
	 * 
	 * @author 李小伟
	 * 
	 * */
	public List<AaPerson> getPersonList(String[] personList);

	/**
	 * @category 加入仓库(批量加入缓存，在数据库中取出)
	 * 
	 * @author 李小伟
	 * @return list
	 * 
	 **/
	public List<AaWarehouse> setWareHouses();

	/**
	 * @category 添加单个仓库进入缓存
	 * 
	 * @author 李小伟
	 * @param house
	 * @return boolean
	 * **/
	public boolean addWareHouse(AaWarehouse house);

	/**
	 * @category 在缓存中删除单个仓库
	 * 
	 * @author 李小伟
	 * @param wareHouseGid
	 * @return boolean
	 */
	public boolean delWareHouse(String wareHouseGid);

	/**
	 * @category 在缓存中查询单个仓库
	 * 
	 * @author 李小伟
	 * @param wareHouseGid
	 */
	public AaWarehouse getWareHouse(String wareHouseGid);

	/**
	 * @category 在缓存中查询多个仓库
	 * 
	 * @author 李小伟
	 * 
	 * */
	public List<AaWarehouse> getWareHouseList(String[] wareHouseList);

	// ///////////////////////////////////////////////////////////////////////////
	/**
	 * @category 加入货位(批量加入缓存，在数据库中取出)
	 * 
	 * @author 李小伟
	 * @return list
	 * 
	 **/
	public List<AaGoodsallocation> setGoodsAllocation();

	/**
	 * @category 添加单个货位进入缓存
	 * 
	 * @author 李小伟
	 * @param goodsAllocation
	 * @return boolean
	 * **/
	public boolean addGoodsAllocation(AaGoodsallocation goodsAllocation);

	/**
	 * @category 在缓存中删除单个货位
	 * 
	 * @author 李小伟
	 * @param goodsAllocationGid
	 * @return boolean
	 */
	public boolean delGoodsAllocation(String goodsAllocationGid);

	/**
	 * @category 在缓存中查询单个货位
	 * 
	 * @author 李小伟
	 * @param goodsAllocationGid
	 */
	public AaGoodsallocation getGoodsAllocation(String goodsAllocationGid);

	/**
	 * @category 在缓存中查询多个货位
	 * 
	 * @author 李小伟
	 * 
	 * */
	public List<AaGoodsallocation> getGoodsAllocationList(
			String[] goodsAllocationList);

	/**
	 * @category 加入客商档案(批量加入缓存，在数据库中取出)
	 * 
	 * @author 李小伟
	 * @return list
	 * 
	 **/
	public List<AaProviderCustomer> setProviderCustomer();

	/**
	 * @category 添加单个货位进入缓存
	 * 
	 * @author 李小伟
	 * @param providerCustomer
	 * @return boolean
	 * **/
	public boolean addProviderCustomer(AaProviderCustomer providerCustomer);

	/**
	 * @category 在缓存中删除单个货位
	 * 
	 * @author 李小伟
	 * @param providerCustomerGid
	 * @return boolean
	 */
	public boolean delProviderCustomer(String providerCustomerGid);

	/**
	 * @category 在缓存中查询单个客商
	 * 
	 * @author 李小伟
	 * @param providerCustomerGid
	 */
	public AaProviderCustomer getProviderCustomer(String providerCustomerGid);

	/**
	 * @category 在缓存中查询多个客商
	 * 
	 * @param providerCustomer
	 * @author 李小伟
	 * 
	 * */
	public List<AaProviderCustomer> getProviderCustomerList(
			String[] providerCustomerList);

	// /////////////////////////////////////////////
	// 工作中心基础信息
	/**
	 * @category 加入工作中心(批量加入缓存，在数据库中取出)
	 * 
	 * @author 李小伟
	 * @return list
	 * 
	 **/
	public List<MesAaWorkcenter> setMESWorkCenter();

	/**
	 * @category 添加单个工作中心进入缓存
	 * 
	 * @author 李小伟
	 * @param workCenter
	 * @return boolean
	 * **/
	public boolean addWorkCenter(MesAaWorkcenter workCenter);

	/**
	 * @category 在缓存中删除单个工作中心
	 * 
	 * @author 李小伟
	 * @param workCenterGid
	 * @return boolean
	 */
	public boolean delMES_WorkCenter(String workCenterGid);

/**
	 * @category 在缓存中查询单个工作中心
	 * 
	 * @author 李小伟
	 * @param workCenterGid
	 */
	public MesAaWorkcenter getworkCenter(String workCenterGid);

/**
	 * @category 在缓存中查询多个工作中心
	 * 
	 * @param workCenterList
	 * @author 李小伟
	 * 
	 * */
	public List<MesAaWorkcenter> getworkCenterList(String[] workCenterList);
	
	
	
	/**
	 * @category 获得工位
	 *2016 2016年4月20日下午3:21:02
	 *MesAaStation
	 *宋银海
	 */
	public MesAaStation getMesAaStation(String mesAaStationGid);
	
	/**
	 * @category 获得工作组
	 *2016 2016年4月20日下午3:21:02
	 *MesAaStation
	 *宋银海
	 */
	public AaGroup getAaGroup(String aaGroupGid);
	
	/**
	 * @category 获得类别
	 *2016 2016年4月20日下午3:21:02
	 *MesAaStation
	 *宋银海
	 */
	public Classify getClassify(String classifyGid);
	
	/**
	 * @category 获得单位
	 *2016 2016年4月20日下午3:21:02
	 *MesAaStation
	 *宋银海
	 */
	public Unit getUnit(String unitGid);
	

	// 设备基础信息

	/**
	 * @category 加入设备基础信息(批量加入缓存，在数据库中取出)
	 * 
	 * @author 李小伟
	 * @return list
	 * 
	 **/
	public List<Equipment> setEquipment();
	
	/**
	 * @category 加入模具基础信息(批量加入缓存，在数据库中取出)
	 **/
	public List<Equipment> setMould();

	/**
	 * @category 添加单个设备基础信息进入缓存
	 * 
	 * @author 李小伟
	 * @param equipment
	 * @return boolean
	 * **/
	public boolean addEquipment(Equipment equipment);

	/**
	 * @category 在缓存中删除单个设备基础信息
	 * 
	 * @author 李小伟
	 * @param equipmentGid
	 * @return boolean
	 */
	public boolean delEquipment(String equipmentGid);

	/**
	 * @category 在缓存中查询单个设备基础信息
	 * 
	 * @author 李小伟
	 * @param equipmentGid
	 */
	public Equipment getMESEquipment(String equipmentGid);
	
	public Mould getMould(String mouldGid);
	
	//public MESWMCostItem getMESWMCostItem(String mESWMCostItemid);

	/**
	 * @category 在缓存中查询多个设备基础信息
	 * 
	 * @param equipmentList
	 * @author 李小伟
	 * 
	 * */
	public List<Equipment> getEquipmentList(String[] equipmentList);

	// 工作中心所属的设备信息//有需要时写

	// //工序信息
	/**
	 * @category 加入工序基础信息(批量加入缓存，在数据库中取出)
	 * 
	 * @author 李小伟
	 * @return list
	 * 
	 **/
	public List<MesWmStandardprocess> setStandardProcess();

	/**
	 * @category 添加单个工序基础信息进入缓存
	 * 
	 * @author 李小伟
	 * @param standardProcess
	 * @return boolean
	 * **/
	public boolean addStandardProcess(MesWmStandardprocess standardProcess);

	/**
	 * @category 在缓存中删除单个工序基础信息
	 * 
	 * @author 李小伟
	 * @param standardProcessGid
	 * @return boolean
	 */
	public boolean delMES_WM_StandardProcess(String standardProcessGid);

	/**
	 * @category 在缓存中查询单个工序基础信息
	 * 
	 * @author 李小伟
	 * @param standardProcessGid
	 */
	public MesWmStandardprocess getMESStandardProcess(
			String standardProcessGid);

	/**
	 * @category 在缓存中查询多个工序基础信息
	 * 
	 * @param standardProcessList
	 * @author 李小伟
	 * 
	 * */
	public List<MesWmStandardprocess> getMESStandardProcessList(
			String[] standardProcessList);

	/**
	 * @category 加入用户(批量加入缓存，在数据库中取出)
	 * 
	 * @author 李小伟
	 * @param
	 * @return list
	 * 
	 **/
	public List<YmUser> setUser();

	/**
	 * @category 添加单个物料进入缓存
	 * 
	 * @author 李小伟
	 * @param AA_Goods
	 * @return boolean
	 * **/
	public boolean addUser(YmUser user);

	/**
	 * @category 在缓存中删除单个用户
	 * 
	 * @author 李小伟
	 * @param goodsGid
	 * @return boolean
	 */
	public boolean delUser(String userId);

	/**
	 * @category 在缓存中查询单个用户
	 * 
	 * @author 李小伟
	 * @param goodsGid
	 */
	public YmUser getUser(String userId);

	/**
	 * @category 在缓存中查询多个用户
	 * 
	 * @author 李小伟
	 * 
	 * */
	public List<YmUser> getUserList(String[] userList);
	
	/**
	 * @category 加入货位现存量信息(批量加入缓存，在数据库中取出)
	 * 货位现存量是一个特殊的插入方式，目前的架构不满足只能特殊处理
	 * @author 李小伟
	 * @return list
	 * 
	 **/
	public void setAllocationStockS();

	/**
	 * @category 添加/修改单个货位现存量信息进入缓存
	 * 
	 * @author 李小伟
	 * @param goodsUid
	 * @return boolean
	 * **/
	public boolean addAllocationStock(String goodsUid);
	public boolean setAllocationStock(String goodsUid,List<WmAllocationstock> wmas);
	/**
	 * @category 在缓存中删除单个货位现存量
	 * 
	 * @author 李小伟
	 * @param goodsUid
	 * @return boolean
	 */
	public boolean delAllocationStock(String goodsUid);

	/**
	 * @category 在缓存中查询单个货位现存量基础信息
	 * 
	 * @author 李小伟
	 * @param goodsUid
	 */
	public List<WmAllocationstock> getAllocationStock(String goodsUid);

	/**
	 * @category 在缓存中查询多个货位现存量
	 * @param goodsUid
	 * @author 李小伟
	 * 
	 * */
	public List<WmAllocationstock> getAllocationStockList(
			String[] goodsUid);
	
	/**
	 * @category 缓存数据维护现存量
	 * @param i
	 * @author 李小伟
	 * 
	 */
	public void maintenanceStock(int i,String goodsUid);
	

	/**
	 * @category 缓存维护仓库
	 * @param i
	 * @return boolean 
	 * @author 李小伟
	 */
	public void maintenanceWareHouse(int i,String wareHouseGid);
	
	
}
