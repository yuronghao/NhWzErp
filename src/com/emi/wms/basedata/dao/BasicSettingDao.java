package com.emi.wms.basedata.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.AaDepartment;
import com.emi.wms.bean.AaFreeSet;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.AaGoodsallocation;
import com.emi.wms.bean.AaOrg;
import com.emi.wms.bean.AaPerson;
import com.emi.wms.bean.AaProviderCustomer;
import com.emi.wms.bean.AaWarehouse;
import com.emi.wms.bean.Accountperiod;
import com.emi.wms.bean.Classify;
import com.emi.wms.bean.Unit;
import com.emi.wms.bean.Unitconversion;
import com.emi.wms.bean.YmUser;

public class BasicSettingDao extends BaseDao {
	
	
//////////////////////////////////////////////////////////////////////////////////以下是账期部分

	/**
	 * @category 查询账期list实体
	 *2015 2015年12月15日上午9:18:49
	 *String
	 *宋银海
	 */
	public List<Accountperiod> getAccountperiod(String condition){
		String sql="select "+CommonUtil.colsFromBean(Accountperiod.class)+" from AccountPeriod where 1=1 "+condition;
		return this.emiQueryList(sql, Accountperiod.class);
	}
	
	/**
	 * @category 查询账期listMap
	 *2015 2015年12月15日上午9:18:49
	 *String
	 *宋银海
	 */
	public List<Map> getAccountperiodListMap(String condition){
		String sql="select "+CommonUtil.colsFromBean(Accountperiod.class)+" from AccountPeriod where 1=1 "+condition;
		return this.queryForList(sql);
	}
	
	/**
	 * @category 查询唯一年度
	 *2015 2015年12月15日上午9:18:49
	 *String
	 *宋银海
	 */
	public List<Map> getDistinctYear(String condition){
		String sql="select DISTINCT apYear from AccountPeriod where 1=1 "+condition;
		return this.queryForList(sql);
	}
	
	
	
	/**
	 * @category 保存账期
	 *2015 2015年12月16日上午9:00:53
	 *boolean
	 *宋银海
	 */
	public boolean saveAccountperiod(List<Accountperiod> period){
		return this.emiInsert(period);
	}
	
	
	/**
	 * @category 修改账期
	 *2015 2015年12月16日上午9:00:53
	 *boolean
	 *宋银海
	 */
	public boolean uptAccountperiod(List<Accountperiod> period){
		return this.emiUpdate(period);
	}
	
	
	/**
	 * @category 删除账期
	 *2015 2015年12月16日上午9:00:53
	 *boolean
	 *宋银海
	 */
	public int deleteAccountperiod(String condition){
		String sql="delete from accountPeriod where 1=1 "+condition;
		return this.update(sql);
	}
	
	
//////////////////////////////////////////////////////////////////////////////以下是物料部分
	
	/**
	 * @category 添加物料
	 *2015 2015年12月18日下午4:20:27
	 *boolean
	 *宋银海
	 */
	public boolean addGoods(AaGoods aaGoods){
		return this.emiInsert(aaGoods);
	}
	
	/**
	 * @category 修改物料
	 *2015 2015年12月21日下午2:38:00
	 *boolean
	 *宋银海
	 */
	public boolean uptGoods(AaGoods aaGoods){
		return this.emiUpdate(aaGoods);
	}
	
	/**
	 * @category 根据条件查询物料
	 *2015 2015年12月21日上午10:31:36
	 *AaGoods
	 * 宋银海
	 */
	public AaGoods getGoodsEntity(String condition){
		String sql="select "+CommonUtil.colsFromBean(AaGoods.class)+"from AA_Goods where 1=1 "+condition;
		return (AaGoods)this.emiQuery(sql, AaGoods.class);
	}
	
	/**
	 * @category 根据条件查询物料 返回Map
	 *2015 2015年12月25日上午11:23:53
	 *Map
	 *宋银海
	 */
	public Map getGoodsMap(String condition){
		
//		String sql = "select cu.UnitName AS cassComUnitName ,"
//				+ "csa.UnitName as csaComUnitName,cpu.UnitName AS cpuComUnitName,"
//				+ "cst.UnitName AS cstComUnitName,	cca.UnitName AS ccaComUnitName"
//				+ ",uc.unitGroupName,tt.*  from  (select"
//				+ CommonUtil.colsFromBean(AaGoods.class, "goods")
//				+ ",goodsSort.classificationName as classificationName,cunit.UnitName as unitName from AA_Goods as goods, "
//				+ "	classify AS goodsSort,unit AS cunit "
//				+ "where goods.GoodsSortUid=goodsSort.gId and goods.GoodsUnit=cunit.gid  "+condition+" ) as tt "
//				+ "left join unit AS cu ON tt.cassComUnitCode = cu.gid "
//				+ "left join unit as csa on tt.csacomunitcode=csa.gid "
//				+ "left join unit as cpu  on tt.cpucomunitcode=cpu.gid "
//				+ "left join unit as cst on tt.cstcomunitcode=cst.gid "
//				+ "left join unit as cca on tt.ccacomunitcode=cca.gid " 
//				+ "left join unitConversion as uc on tt.unitGroupGid=uc.gid ";
		
		String sql="select "+CommonUtil.colsFromBean(AaGoods.class, "goods")+" from AA_Goods as goods where "+condition;
		return this.queryForMap(sql);
	}
	
	
	/**
	 * @category 物品查询页
	 *2015 2015年12月25日下午1:54:43
	 *PageBean
	 *宋银海
	 */
	public PageBean getGoodsPageBean(int pageIndex, int pageSize,String condition) {
		Map match = new HashMap();
//		match.put("nowsum", "AaGoods.nowsum");
		String sql = "select "+CommonUtil.colsFromBean(AaGoods.class,"gs")+" FROM AA_Goods gs "
				+ "  where 1=1 "+condition;
		return (PageBean) this.emiQueryList(sql, pageIndex, pageSize, AaGoods.class,match,"goodscode");
	}
	public PageBean getGoodsAllocationPageBean(int pageIndex, int pageSize,String condition) {
		String sql = "select "+CommonUtil.colsFromBean(AaGoodsallocation.class,"gs")+" FROM AA_GoodsAllocation gs "
				+ "  where 1=1 and (gs.isDel=0 or gs.isDel is null) "+condition;
		return (PageBean) this.emiQueryList(sql, pageIndex, pageSize, AaGoodsallocation.class,"pk");
	}
	
	/**
	 * @category 返回满足条件的商品列表
	 *2015 2015年12月25日下午1:54:43
	 *PageBean
	 *宋银海
	 */
	public List<Map> getGoodsListMap(String condition){
		String sql="select gid,goodsCode,goodsName,goodsStandard from aa_goods where "+condition;
		return this.queryForList(sql);
	}
	
	////////////////////////////////////////////////////////////////////////////////////以下是单位部分
	
	/**
	 * @category 查询计量单位
	 *2015 2015年12月22日下午3:46:57
	 *List<Unit>
	 *宋银海
	 */
	public List<Unit> getUnit(String condition){
		String sql="select "+CommonUtil.colsFromBean(Unit.class)+" from unit where 1=1 "+condition;
		return this.emiQueryList(sql, Unit.class);
	}
	
	
	/**
	 * @category 根据gid 查询计量单位
	 *2015 2015年12月22日下午3:46:57
	 *List<Unit>
	 *宋银海
	 */
	public Unit getUnitEntity(String condition){
		String sql="select "+CommonUtil.colsFromBean(Unit.class)+" from unit where 1=1 "+condition;
		return (Unit)this.emiQuery(sql, Unit.class);
	}
	
	/**
	 * @category 添加计量单位
	 *2015 2015年12月22日下午3:15:18
	 *boolean
	 *宋银海
	 */
	public boolean addUnit(Unit unit){
		return this.emiInsert(unit);
	}
	
	/**
	 * @category 修改计量单位
	 *2015 2015年12月23日上午8:43:37
	 *boolean
	 *宋银海
	 */
	public boolean uptUnit(Unit unit){
		return this.emiUpdate(unit);
	}
	
	/**
	 * @category 删除计量单位
	 *2015 2015年12月23日上午8:43:37
	 *boolean
	 *宋银海
	 */
	public boolean deleteUnit(String[] unitGids){
		
		try {
			String[] sqls = new String[unitGids.length];
			for (int i = 0; i < unitGids.length; i++) {
				String sql = "update unit set isDel='1' where gid='"+ unitGids[i] + "'";
				sqls[i] = sql;
			}
			this.batchUpdate(sqls);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	
	/**
	 * @category 添加计量单位组
	 *2015 2015年12月23日下午2:15:20
	 *String
	 *宋银海
	 */
	public boolean addUnitConversion(Unitconversion unitconversion){
		return this.emiInsert(unitconversion);
	}
	
	/**
	 * @category 修改计量单位组
	 *2015 2015年12月23日下午2:15:20
	 *String
	 *宋银海
	 */
	public boolean uptUnitConversion(Unitconversion unitconversion){
		return this.emiUpdate(unitconversion);
	}
	
	
	/**
	 * @category 查询计量单位组
	 *2015 2015年12月23日下午2:51:16
	 *List<Map>
	 *宋银海
	 */
	public List<Map> getUnitConversionList(String condition){
		String sql="select uc.gid,uc.unitGroupName,uc.UnitOfMeasurement,uc.mainUnit,mu.unitName muname,uc.auxiliaryUnit,fu.unitName funame,uc.auxiliaryQuantity from unitConversion uc "+
					"LEFT JOIN unit mu on uc.mainUnit=mu.gid "+
					"LEFT JOIN unit fu on uc.auxiliaryUnit=fu.gid where 1=1 "+condition;
		return this.queryForList(sql);
	}
	
	/**
	 * @category 查询计量单位组
	 *2015 2015年12月23日下午2:51:16
	 *List<Map>
	 *宋银海
	 */
	public Map getUnitConversionMap(String condition){
		String sql="select uc.gid,uc.unitGroupName,uc.UnitOfMeasurement,uc.mainUnit,mu.unitName muname,uc.auxiliaryUnit,fu.unitName funame,uc.auxiliaryQuantity from unitConversion uc "+
					"LEFT JOIN unit mu on uc.mainUnit=mu.gid "+
					"LEFT JOIN unit fu on uc.auxiliaryUnit=fu.gid where 1=1 "+condition;
		return this.queryForMap(sql);
	}
	
	
	/**
	 * @category 假删计量单位组
	 *2015 2015年12月23日上午9:43:57
	 *boolean
	 *宋银海
	 */
	public boolean deleteUnitConversion(String[] unitGids){
		
		try {
			String[] sqls = new String[unitGids.length];
			for (int i = 0; i < unitGids.length; i++) {
				String sql = "update unitConversion set isDel='1' where gid='"+ unitGids[i] + "'";
				sqls[i] = sql;
			}
			this.batchUpdate(sqls);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////以下是仓库部分
	
	/**
	 * @category 查询仓库实体列表
	 *2015 2015年12月28日上午9:49:38
	 *List<AaWarehouse>
	 *宋银海
	 */
	public PageBean getWarehouse(int pageIndex,int pageSize,String condition){
		String sql="select "+CommonUtil.colsFromBean(AaWarehouse.class)+" from AA_WareHouse where 1=1 "+condition;
		return this.emiQueryList(sql, pageIndex, pageSize, AaWarehouse.class, "");
	}
	
	public List<AaWarehouse> getWarehouse(String condition){
		String sql="select "+CommonUtil.colsFromBean(AaWarehouse.class)+" from AA_WareHouse where 1=1 "+condition;
		return this.emiQueryList(sql,AaWarehouse.class);
	}
	
	/**
	 * @category 查询仓库实体
	 *2015 2015年12月28日上午10:52:38
	 *AaWarehouse
	 *宋银海
	 */
	public AaWarehouse getWarehouseEntity(String condition){
		String sql="select "+CommonUtil.colsFromBean(AaWarehouse.class)+" from AA_WareHouse where 1=1 "+condition;
		return (AaWarehouse)this.emiQuery(sql, AaWarehouse.class);
	}
	
	/**
	 * @category 添加仓库
	 *2015 2015年12月28日上午8:56:45
	 *boolean
	 *宋银海
	 */
	public boolean addWarehouse(AaWarehouse aaWarehouse){
		return this.emiInsert(aaWarehouse);
	}
	
	/**
	 * @category 修改仓库
	 *2015 2015年12月28日上午8:56:45
	 *boolean
	 *宋银海
	 */
	public boolean uptWarehouse(AaWarehouse aaWarehouse){
		return this.emiUpdate(aaWarehouse);
	}
	
	
	/**
	 * @category 删除仓库
	 *2015 2015年12月28日上午11:25:36
	 *boolean
	 *宋银海
	 */
	public boolean deleteWarehouse(String[] unitGids){
		
		try {
			String[] sqls = new String[unitGids.length];
			for (int i = 0; i < unitGids.length; i++) {
				String sql = "update AA_WareHouse set isDel='1' where gid='"+ unitGids[i] + "'";
				sqls[i] = sql;
			}
			this.batchUpdate(sqls);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	/**
	 * @category 仓库树
	 *2015 2015年12月28日下午1:33:33
	 *List<Map>
	 *宋银海
	 */
	public List<Map> getWarehouseList(String condition){
		String sql="select "+CommonUtil.colsFromBean(AaWarehouse.class)+" from AA_WareHouse where 1=1 "+condition;
		return this.queryForList(sql);
	}	
	
	
	/**
	 * @category 查询货位列表
	 *2015 2015年12月28日下午2:01:12
	 *PageBean
	 *宋银海
	 */
	public AaGoodsallocation getGoodsAllocation(String condition) {
		String sql = "select "+CommonUtil.colsFromBean(AaGoodsallocation.class)+" FROM AA_GoodsAllocation where 1=1 "+condition;
		return (AaGoodsallocation)this.emiQuery(sql, AaGoodsallocation.class);
	}
	
	
	/**
	 * @category 查询货位列表
	 *2015 2015年12月29日上午9:37:09
	 *List<AaGoodsallocation>
	 *宋银海
	 */
	public List<AaGoodsallocation> getAaGoodsallocationList(String condition){
		String sql = "select "+CommonUtil.colsFromBean(AaGoodsallocation.class)+" FROM AA_GoodsAllocation where 1=1 "+condition;
		return this.emiQueryList(sql, AaGoodsallocation.class);
	}
	
	
	
	/**
	 * @category 添加货位
	 *2015 2015年12月28日下午5:21:22
	 *boolean
	 *宋银海
	 */
	public boolean addGoodsAllocation(AaGoodsallocation aaGoodsallocation){
		return this.emiInsert(aaGoodsallocation);
	}
	
	/**
	 * @category 修改货位
	 *2015 2015年12月29日下午3:12:57
	 *boolean
	 *宋银海
	 */
	public boolean uptGoodsAllocation(AaGoodsallocation aaGoodsallocation){
		return this.emiUpdate(aaGoodsallocation);
	}
	
	
	/**
	 * @category 删除货位
	 *2015 2015年12月29日下午3:35:21
	 *boolean
	 *宋银海
	 */
	public int deleteGoodsAllocation(String gid){
		String sql="update AA_GoodsAllocation set isDel=1 where gid='"+gid+"'";
		return this.update(sql);
	}
	
	
//////////////////////////////////////////////////////////////////////////////////////////////以下是组织（部门和人员）部分
	
	/**
	 * @category 查询部门
	 *2015 2015年12月30日上午8:54:39
	 *List<AaDepartment>
	 *宋银海
	 */
	public List<AaDepartment> getDepartmentListEntity(String condition){
		String sql="select "+CommonUtil.colsFromBean(AaDepartment.class)+" from AA_Department where 1=1 "+condition;
		return this.emiQueryList(sql, AaDepartment.class);
	}
	
	/**
	 * @category 增加部门
	 *2015 2015年12月30日上午11:13:25
	 *boolean
	 *宋银海
	 */
	public boolean addDepartment(AaDepartment aaDepartment){
		return this.emiInsert(aaDepartment);
	}
	
	/**
	 * @category 获得部门详情
	 *2015 2015年12月30日上午11:28:17
	 *AaDepartment
	 *宋银海
	 */
	public AaDepartment getDepartmentDetail(String condition){
		String sql="select "+CommonUtil.colsFromBean(AaDepartment.class)+" from AA_Department where 1=1 "+condition;
		return (AaDepartment)this.emiQuery(sql, AaDepartment.class);
	}
	
	
	/**
	 * @category 修改部门
	 *2015 2015年12月30日下午2:31:48
	 *boolean
	 *宋银海
	 */
	public boolean editDepartment(AaDepartment aaDepartment){
		return this.emiUpdate(aaDepartment);
	}
	
	/**
	 * @category 删除部门
	 *2015 2015年12月30日下午3:20:29
	 *int
	 *宋银海
	 */
	public int deleteDepartment(String gid){
		String sql="update AA_Department set isDel=1 where gid='"+gid+"'";
		return this.update(sql);
	}
	
	
	/**
	 * @category 增加人员
	 *2015 2015年12月31日上午10:00:43
	 *boolean
	 *宋银海
	 */
	public boolean addPerson(AaPerson aaPerson){
		return this.emiInsert(aaPerson);
	}
	
	/**
	 * @category 查询人员
	 *2015 2015年12月31日下午1:31:27
	 *PageBean
	 *宋银海
	 */
	public PageBean getPersonPageBean(int pageIndex, int pageSize,String condition) {
		String sql = " select "+CommonUtil.colsFromBean(AaPerson.class,"ps")+",dpt.depName FROM AA_Person ps "
				+ " left join AA_Department dpt on ps.depGid=dpt.gid  where 1=1 "+condition;
		return (PageBean)this.emiQueryList(sql, pageIndex, pageSize, ""); 
	}
	
	
	/**
	 * @category 查询人员
	 *2015 2015年12月30日上午8:54:39
	 *List<AaDepartment>
	 *宋银海
	 */
	public List<AaPerson> getPersonList(String condition){
		String sql="select "+CommonUtil.colsFromBean(AaPerson.class)+" from AA_Person where 1=1 "+condition;
		return this.emiQueryList(sql, AaPerson.class);
	}
	
	/**
	 * @category 查询人员Map 
	 *2015 2015年12月31日下午4:37:31
	 *Map
	 *宋银海
	 */
	public Map getPersonMap(String condition){
		String sql = " select "+CommonUtil.colsFromBean(AaPerson.class,"ps")+",dpt.depName FROM AA_Person ps "
				+ " left join AA_Department dpt on ps.depGid=dpt.gid  where 1=1 "+condition;
		return this.queryForMap(sql);
	}
	
	
	/**
	 * @category 修改人员
	 *2015 2015年12月31日下午5:13:30
	 *boolean
	 *宋银海
	 */
	public boolean uptPerson(AaPerson aaPerson){
		return this.emiUpdate(aaPerson);
	}
	
	
	/**
	 * @category 删除人员
	 *2016 2016年1月4日上午10:41:16
	 *int
	 *宋银海
	 */
	public int deletePerson(String gid){
		String sql="update aa_person set isDel=1 where gid='"+gid+"'";
		return this.update(sql);
	}
	
	
//////////////////////////////////////////////////////////////////////////////////////////////以下是用户部分
	
	/**
	 * @category 用户列表
	 *2016 2016年1月4日下午2:21:22
	 *List<YmUser>
	 *宋银海
	 */
	public PageBean getYmUser(int pageIndex,int pageSize,String condition){
		String sql = "select "+CommonUtil.colsFromBean(YmUser.class,"us")+" from YM_User us where 1=1 "+condition;
		return (PageBean)this.emiQueryList(sql, pageIndex, pageSize, ""); 
	}
	
	
	
	/**
	 * @category 增加用户
	 *2016 2016年1月4日下午1:47:36
	 *boolean
	 *宋银海
	 */
	public boolean addUser(YmUser ymUser){
		return this.emiInsert(ymUser);
	}
	
	/**
	 * @category 根据条件获得用户
	 *2016 2016年4月6日下午4:14:15
	 *YmUser
	 *宋银海
	 */
	public YmUser getYmUser(String condition){
		String sql="select "+CommonUtil.colsFromBean(YmUser.class)+" from YM_User where "+condition;
		return (YmUser) this.emiQuery(sql, YmUser.class);
	}
	
	/**
	 * @category 查询权限
	 *2016 2016年4月7日下午2:28:53
	 *List<Map>
	 *宋银海
	 */
	public List<Map> getAndroidLoginRight(String condition){
		String sql="SELECT ru.roleId,ru.userId,rr.rightId,rrt.rightCode,rrt.rightName,rrt.taskTypeCode from RM_RoleUser ru "+
					"LEFT JOIN RM_RoleRight rr on ru.roleId=rr.roleId "+
					"LEFT JOIN RM_Right rrt on rr.rightId=rrt.gid where "+condition;
		return this.queryForList(sql);
	}
	

	public YmUser findUser(String gid) {
		return (YmUser) this.emiFind(gid, YmUser.class);
	}

	public boolean editUser(YmUser ymUser) {
		return this.emiUpdate(ymUser);
	}

	/**
	 * @category 得到自由项的定义
	 * 2016年5月23日 下午4:23:30 
	 * @author zhuxiaochen
	 * @return
	 */
	public List<AaFreeSet> getFreeSet() {
		String sql = "select "+CommonUtil.colsFromBean(AaFreeSet.class)+" from AA_freeSet where projectName is not null";
		return this.emiQueryList(sql, AaFreeSet.class);
	}


    public PageBean getGoodsPageBeanBySearchStock(int pageIndex, int pageSize, String condition, String conplus) {
		Map match = new HashMap();
		String sql = "select "+CommonUtil.colsFromBean(AaGoods.class,"gs")+" FROM AA_Goods gs " +
				" inner  JOIN WM_AllocationStock wa on wa.goodsUid = gs.gid " +
				 "  where 1=1 "+condition+conplus;
		return (PageBean) this.emiQueryList(sql, pageIndex, pageSize, AaGoods.class,match,"goodscode");


    }
}
