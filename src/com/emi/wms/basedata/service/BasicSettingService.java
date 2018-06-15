package com.emi.wms.basedata.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.emi.android.bean.DepartmentPersonRsp;
import com.emi.android.bean.DepartmentRsp;
import com.emi.android.bean.LoginDetailRsp;
import com.emi.cache.service.CacheCtrlService;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.PasswordUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.basedata.dao.BasicSettingDao;
import com.emi.wms.bean.AaDepartment;
import com.emi.wms.bean.AaFreeSet;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.AaGoodsallocation;
import com.emi.wms.bean.AaPerson;
import com.emi.wms.bean.AaWarehouse;
import com.emi.wms.bean.Accountperiod;
import com.emi.wms.bean.Classify;
import com.emi.wms.bean.Unit;
import com.emi.wms.bean.Unitconversion;
import com.emi.wms.bean.WmAllocationstock;
import com.emi.wms.bean.YmUser;
import com.emi.wms.servicedata.dao.WareHouseDao;

public class BasicSettingService {

	
	private CacheCtrlService cacheCtrlService;
	private BasicSettingDao basicSettingDao;
	private WareHouseDao wareHouseDao;

	public void setBasicSettingDao(BasicSettingDao basicSettingDao) {
		this.basicSettingDao = basicSettingDao;
	}

	public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
		this.cacheCtrlService = cacheCtrlService;
	}

	public CacheCtrlService getCacheCtrlService() {
		return cacheCtrlService;
	}

	public BasicSettingDao getBasicSettingDao() {
		return basicSettingDao;
	}

	public WareHouseDao getWareHouseDao() {
		return wareHouseDao;
	}

	public void setWareHouseDao(WareHouseDao wareHouseDao) {
		this.wareHouseDao = wareHouseDao;
	}

	////////////////////////////////////////////////////////////////////////////////// 以下是账期部分
	/**
	 * @category 查询账期树
	 *2015 2015年12月15日上午9:18:49
	 *String
	 *宋银海
	 */
	public List<Map> getAccountPeriodTree(Map map){
		
		String sobid=map.get("sobId").toString();//帐套id
		String orgId=map.get("orgId").toString();//组织id
		
		String condition=" and sobId='"+sobid+"' and orgId='"+orgId+"'";
		List<Map> maps=basicSettingDao.getDistinctYear(condition);
		
		for(Map m :maps){
			m.put("id", m.get("apYear"));
			m.put("pId", 0);
			m.put("name", m.get("apYear"));
		}
		
		Map mapFirst=new HashMap();
		mapFirst.put("id", 0);
		mapFirst.put("pId", -1);
		mapFirst.put("name", "帐期年度");
		
		maps.add(mapFirst);
		return maps;
	}
	
	
	/**
	 * @category 查询当前帐套最大年度
	 *2015 2015年12月15日上午9:18:49
	 *String
	 *宋银海
	 */
	public int getMaxAccountPeriodYear(Map map){
		
		String sobid=map.get("sobId").toString();//帐套id
		String orgId=map.get("orgId").toString();//组织id
		
		String condition=" and sobId='"+sobid+"' and orgId='"+orgId+"' order by apYear desc";
		List<Map> maps=basicSettingDao.getDistinctYear(condition);
		int i=0;
		if(maps.size()>0){
			i=Integer.parseInt(maps.get(0).get("apYear").toString());
		}
		
		return i;
	}
	
	
	
	
	/**
	 * @category 查询账期list实体
	 *2015 2015年12月15日上午9:18:49
	 *String
	 *宋银海
	 */
	public List<Accountperiod> getAccountperiod(String condition){
		return basicSettingDao.getAccountperiod(condition);
	}
	

	/**
	 * @category 保存账期
	 *2015 2015年12月16日上午9:00:53
	 *boolean
	 *宋银海
	 */
	public boolean saveAccountperiod(List<Accountperiod> period){
		return basicSettingDao.saveAccountperiod(period);
	}
	
	
	/**
	 * @category 修改账期
	 *2015 2015年12月16日上午9:00:53
	 *boolean
	 *宋银海
	 */
	public boolean uptAccountperiod(List<Accountperiod> period){
		return basicSettingDao.uptAccountperiod(period);
	}
	
	
	/**
	 * @category 删除账期
	 *2015 2015年12月16日上午9:00:53
	 *boolean
	 *宋银海
	 */
	public boolean deleteAccountperiod(Map values){
		
		String sobid=values.get("sobId").toString();//帐套id
		String orgId=values.get("orgId").toString();//组织id
		String year=values.get("year").toString();//组织id
		
		String condition=" and sobId='"+sobid+"' and orgId='"+orgId+"' and apYear='"+year+"'";
		
		basicSettingDao.deleteAccountperiod(condition);
		
		return true;
	}
	
	//////////////////////////////////////////////////////////////////////////////以下是物料部分
	
	/**
	 * @category 添加物料
	 *2015 2015年12月18日下午4:20:27
	 *boolean
	 *宋银海
	 */
	public boolean addGoods(AaGoods aaGoods){
		
		Classify classify=cacheCtrlService.getClassify(aaGoods.getGoodssortuid());
		Unit mainUnit=cacheCtrlService.getUnit(aaGoods.getGoodsunit());
		
		aaGoods.setClassificationName(classify.getClassificationname());
		aaGoods.setUnitName(mainUnit.getUnitname());
		
		if(!CommonUtil.isNullObject(aaGoods.getCstcomunitcode())){
			Unit cstUnit=cacheCtrlService.getUnit(aaGoods.getCstcomunitcode());
			aaGoods.setCstComUnitName(cstUnit.getUnitname());
			aaGoods.setCassComUnitName(cstUnit.getUnitname());
		}
		
		
		cacheCtrlService.addGoods(aaGoods);
		return basicSettingDao.addGoods(aaGoods);
	}
	
	
	/**
	 * @category 修改物料
	 *2015 2015年12月18日下午4:20:27
	 *boolean
	 *宋银海
	 */
	public JSONObject uptGoods(AaGoods aaGoods){
		
		JSONObject jobj=new JSONObject();
		
		Classify classify=cacheCtrlService.getClassify(aaGoods.getGoodssortuid());
		Unit mainUnit=cacheCtrlService.getUnit(aaGoods.getGoodsunit());
		
		aaGoods.setClassificationName(classify.getClassificationname());
		aaGoods.setUnitName(mainUnit.getUnitname());
		
		String condition=" and gid='"+aaGoods.getGid()+"'";
		AaGoods oldGoods=basicSettingDao.getGoodsEntity(condition);
		
		if((CommonUtil.isNullObject(aaGoods.getBinvbach())?0:aaGoods.getBinvbach().intValue())!=(CommonUtil.isNullObject(oldGoods.getBinvbach())?0:oldGoods.getBinvbach().intValue())){
			condition=" goodsUid='"+aaGoods.getGid()+"'";
			int oi=wareHouseDao.getOtherWarehouseCount(condition);//查询其他入库子表
			if(oi>0){
				jobj.put("success", 0);
				jobj.put("failInfor", "存在其它入库单，不允许修改！");
				return jobj;
			}
			int pw=wareHouseDao.getProduceWarehousecCount(condition);//查询生产入库子表
			if(pw>0){
				jobj.put("success", 0);
				jobj.put("failInfor", "存在生产入库单，不允许修改！");
				return jobj;
			}
			int so=wareHouseDao.getSaleOutWarehousecCount(condition);//查询销售出库子表
			if(so>0){
				jobj.put("success", 0);
				jobj.put("failInfor", "存在销售出库单，不允许修改！");
				return jobj;
			}
			
		}
		
		if(!CommonUtil.isNullObject(aaGoods.getCstcomunitcode())){
			Unit cstUnit=cacheCtrlService.getUnit(aaGoods.getCstcomunitcode());
			if(!CommonUtil.isNullObject(cstUnit)){
				aaGoods.setCstComUnitName(cstUnit.getUnitname());
				aaGoods.setCassComUnitName(cstUnit.getUnitname());
			}
		}
		cacheCtrlService.addGoods(aaGoods);
		
		 basicSettingDao.uptGoods(aaGoods);
		 
		jobj.put("success", 1);
		jobj.put("failInfor", "");
		 
		 return jobj;
	}
	
	
	
	/**
	 * @category 分页查询物品
	 *2015 2015年12月21日上午9:50:31
	 *PageBean
	 *宋银海
	 */
	public PageBean getGoodsPageBean(int pageIndex, int pageSize, String classifyGid, String orgId, String sobId, String ext_cond, String conplus){
		StringBuffer sbf=new StringBuffer();
		
		if(!CommonUtil.isNullObject(classifyGid) && classifyGid.equalsIgnoreCase("0")){//最顶级
			sbf.append(" and gs.orgGid='"+orgId+"' and gs.sobGid='"+sobId+"'");
		}else if(!CommonUtil.isNullObject(classifyGid) && !classifyGid.equalsIgnoreCase("0")){
			sbf.append(" and gs.goodsSortUid='"+classifyGid+"' and gs.orgGid='"+orgId+"' and gs.sobGid='"+sobId+"'");
		}
		
		
		if(CommonUtil.notNullString(ext_cond)){
			sbf.append(ext_cond);
		}
		
		String condition=sbf.toString();
		
		PageBean pageBean=basicSettingDao.getGoodsPageBean(pageIndex, pageSize, condition);
		if(conplus != null && !"".equals(conplus)){
			pageBean = basicSettingDao.getGoodsPageBeanBySearchStock(pageIndex, pageSize, condition,conplus);
		}
		List<AaGoods> list=pageBean.getList();
		
		StringBuffer sbfGoods = new StringBuffer();
		for(AaGoods gs:list){
			AaGoods gscache=cacheCtrlService.getGoods(gs.getGid());
			
			gs.setUnitName(gscache.getUnitName());//主单位名称
			gs.setClassificationName(gscache.getClassificationName());//类别名称
			gs.setCstComUnitName(gscache.getCstComUnitName());//辅单位gid
			
			sbfGoods.append("'" + gs.getGid() + "',");
		}
		
		condition = sbfGoods.toString();
		if (condition.length() > 0) {
			condition = condition.substring(0, condition.length() - 1);
			condition = " and goodsUid in (" + condition + ")";
		}
		
		// 查询
		List<WmAllocationstock> currentStock = wareHouseDao.getAllocationStock(condition,conplus);
		
		for(AaGoods gs:list){
			for(WmAllocationstock wa:currentStock){
				if(gs.getGid().equalsIgnoreCase(wa.getGoodsuid()) ){
					gs.setNowsum(CommonUtil.bigDecimal2BigDecimal(gs.getNowsum()).add(wa.getNumber()));
				}
			}
		}
		return pageBean;
	}
	
	/**
	 * @category 返回满足条件的商品列表
	 *2015 2015年12月25日下午1:54:43
	 *PageBean
	 *宋银海
	 */
	public List<Map> getGoodsListMap(String condition){
		return basicSettingDao.getGoodsListMap(condition);
	}
	
	
	/**
	 * @category 根据物料gid查询物料
	 *2015 2015年12月18日下午5:19:55
	 *AaGoods
	 *宋银海
	 */
	public Map getGoodsByGid(String gid,String orgId,String sobId){
		
		String condition=" goods.gid='"+gid+"' and goods.orgGid='"+orgId+"' and goods.sobGid='"+sobId+"'";
		Map goodsMap=basicSettingDao.getGoodsMap(condition);
		AaGoods goodsEntity=cacheCtrlService.getGoods(gid);
		
		AaWarehouse aaWarehouse=cacheCtrlService.getWareHouse(goodsEntity.getCdefwarehouse());
		
		Map resultMap=new HashMap();
		resultMap.put("goodsMap", goodsMap);
		resultMap.put("goodsEntity", goodsEntity);
		resultMap.put("aaWarehouse", aaWarehouse);
		
		return resultMap;
	} 
	
	//////////////////////////////////////////////////////////////////////////////////////////////以下是计量单位部分
	
	/**
	 * @category 查询计量单位
	 *2015 2015年12月22日下午3:46:57
	 *List<Unit>
	 *宋银海
	 */
	public List<Unit> getUnit(String orgId,String sobId){
		String condition=" and sobId='"+sobId+"' and orgId='"+orgId+"' and (isDel=0 or isDel is null)";
		return basicSettingDao.getUnit(condition);
	}
	
	
	/**
	 * @category 根据gid 查询计量单位
	 *2015 2015年12月22日下午3:46:57
	 *List<Unit>
	 *宋银海
	 */
	public Unit getUnit(String orgId,String sobId,String gid){
		String condition=" and sobId='"+sobId+"' and orgId='"+orgId+"' and gid='"+gid+"'";
		return basicSettingDao.getUnitEntity(condition);
	}
	
	
	/**
	 * @category 添加计量单位
	 *2015 2015年12月22日下午3:15:18
	 *boolean
	 *宋银海
	 */
	public boolean addUnit(Unit unit){
		return basicSettingDao.addUnit(unit);
	}
	
	/**
	 * @category 修改计量单位
	 *2015 2015年12月23日上午8:40:50
	 *boolean
	 *宋银海
	 */
	public boolean uptUnit(Unit unit){
		return basicSettingDao.uptUnit(unit);
	}
	
	
	/**
	 * @category 假删计量单位
	 *2015 2015年12月23日上午9:43:57
	 *boolean
	 *宋银海
	 */
	public boolean deleteUnit(String[] unitGids){
		return basicSettingDao.deleteUnit(unitGids);
	}
	
	
	/**
	 * @category 添加计量单位组
	 *2015 2015年12月23日下午2:15:20
	 *String
	 *宋银海
	 */
	public boolean addUnitConversion(Unitconversion unitconversion){
		return basicSettingDao.addUnitConversion(unitconversion);
	}
	
	
	/**
	 * @category 修改计量单位组
	 *2015 2015年12月23日下午2:15:20
	 *String
	 *宋银海
	 */
	public boolean uptUnitConversion(Unitconversion unitconversion){
		return basicSettingDao.uptUnitConversion(unitconversion);
	}
	
	
	/**
	 * @category 查询计量单位组list
	 *2015 2015年12月23日下午2:51:16
	 *List<Map>
	 *宋银海
	 */
	public List<Map> getUnitConversion(String orgId,String sobId){
		
		String condition=" and uc.orgId='"+orgId+"' and uc.sobId='"+sobId+"' and (uc.isDel=0 or uc.isDel is null)";
		return basicSettingDao.getUnitConversionList(condition);
	}

	
	/**
	 * @category 查询计量单位组map
	 *2015 2015年12月23日下午4:28:04
	 *Map
	 *宋银海
	 */
	public Map getUnitConversion(String orgId,String sobId,String gid){
		String condition=" and uc.orgId='"+orgId+"' and uc.sobId='"+sobId+"' and uc.gid='"+gid+"'";
		return basicSettingDao.getUnitConversionMap(condition);
	}
	
	/**
	 * @category 假删计量单位组
	 *2015 2015年12月23日上午9:43:57
	 *boolean
	 *宋银海
	 */
	public boolean deleteUnitConversion(String[] unitGids){
		return basicSettingDao.deleteUnitConversion(unitGids);
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////以下是仓库部分
	
	/**
	 * @category 查询仓库实体列表
	 *2015 2015年12月28日上午9:49:38
	 *List<AaWarehouse>
	 *宋银海
	 */
	public PageBean getWarehouse(int pageIndex,int pageSize,String orgId,String sobId){
		String condition=" and orggId='"+orgId+"' and sobgId='"+sobId+"' and (isDel=0 or isDel is null) ";
		return basicSettingDao.getWarehouse(pageIndex,pageSize,condition);
	}
	
	public List<AaWarehouse> getWarehouse(String orgId,String sobId){
		String condition=" and orggId='"+orgId+"' and sobgId='"+sobId+"' and (isDel=0 or isDel is null) ";
		return basicSettingDao.getWarehouse(condition);
	}
	
	/**
	 * @category 查询仓库实体
	 *2015 2015年12月28日上午10:52:38
	 *AaWarehouse
	 *宋银海
	 */
	public AaWarehouse getWarehouse(String orgId,String sobId,String gid){
		String condition=" and sobgId='"+sobId+"' and orggId='"+orgId+"' and gid='"+gid+"'";
		return basicSettingDao.getWarehouseEntity(condition);
	}
	
	/**
	 * @category 添加仓库
	 *2015 2015年12月28日上午8:56:45
	 *boolean
	 *宋银海
	 */
	public boolean addWarehouse(AaWarehouse aaWarehouse){
		return basicSettingDao.addWarehouse(aaWarehouse);
	}
	
	/**
	 * @category 修改仓库
	 *2015 2015年12月28日上午8:56:45
	 *boolean
	 *宋银海
	 */
	public boolean uptWarehouse(AaWarehouse aaWarehouse){
		return basicSettingDao.uptWarehouse(aaWarehouse);
	}
	
	/**
	 * @category 删除仓库
	 *2015 2015年12月28日上午11:25:36
	 *boolean
	 *宋银海
	 */
	public boolean deleteWarehouse(String[] unitGids){
		return basicSettingDao.deleteWarehouse(unitGids);
	}
	
	/**
	 * @category 仓库树
	 *2015 2015年12月28日下午1:33:33
	 *List<Map>
	 *宋银海
	 */
	public List<Map> getWarehouseTree(String orgId,String sobId){
		String condition=" and sobgId='"+sobId+"' and orggId='"+orgId+"' and (isDel=0 or isDel is null) ";
		List<Map> warehouse=basicSettingDao.getWarehouseList(condition);
		
		condition=" and sobgId='"+sobId+"' and orggId='"+orgId+"' and (isDel=0 or isDel is null)  ";
		List<AaGoodsallocation> goodsAllocation=basicSettingDao.getAaGoodsallocationList(condition+" and posEnd=1 ");
		
		for(Map map :warehouse){
			map.put("id", map.get("gid").toString());
			map.put("pId", 0);
			map.put("name","("+map.get("whcode").toString()+")"+ map.get("whname").toString());
		}
		
		for(AaGoodsallocation ga:goodsAllocation){
			Map map=new HashMap();
			if(CommonUtil.isNullObject(ga.getWhuid())){
				map.put("id", ga.getGid());
				map.put("pId", 0);
				map.put("name","("+ga.getCode()+")"+ ga.getName());
			}else{
				map.put("id", ga.getGid());
				map.put("pId", ga.getWhuid());
				map.put("name", "("+ga.getCode()+")"+ga.getName());
			}
			
			warehouse.add(map);
		}
		
		Map m=new HashMap();
		m.put("id", 0);
		m.put("pId",-1);
		m.put("name", "全部");
		
		warehouse.add(m);
		
		return warehouse;
	}	
	
	
	/**
	 * @category 分页查询货位
	 *2015 2015年12月28日下午1:57:25
	 *PageBean
	 *宋银海
	 */
	public AaGoodsallocation getGoodsAllocation(String goodsAllocationGid,String orgId,String sobId){

		String condition=" and gid='"+goodsAllocationGid+"' and sobgId='"+sobId+"' and orggId='"+orgId+"'";
		AaGoodsallocation aaGoodsallocation=basicSettingDao.getGoodsAllocation(condition);
		return aaGoodsallocation;
	}
	
	
	/**
	 * @category 添加货位
	 *2015 2015年12月28日下午5:21:22
	 *boolean
	 *宋银海
	 */
	public boolean addGoodsAllocation(AaGoodsallocation aaGoodsallocation){
		return basicSettingDao.addGoodsAllocation(aaGoodsallocation);
	}
	
	/**
	 * @category 修改货位
	 *2015 2015年12月29日下午3:12:12
	 *boolean
	 *宋银海
	 */
	public boolean uptGoodsAllocation(AaGoodsallocation aaGoodsallocation){
		return basicSettingDao.uptGoodsAllocation(aaGoodsallocation);
	}
	
	
	/**
	 * @category 删除货位
	 *2015 2015年12月29日下午3:35:21
	 *boolean
	 *宋银海
	 */
	public boolean deleteWarehouse(String gid){
		basicSettingDao.deleteGoodsAllocation(gid);
		return true;
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////以下是组织（部门和人员）部分
	
	
	/**
	 * @category 开工获得部门列表
	 *2016 2016年6月14日上午10:50:24
	 *List<DepartmentRsp>
	 *宋银海
	 */
	public List<DepartmentRsp> getDepartmentLast(String orgId,String sobId,String billType){
		List<DepartmentRsp> drs=new ArrayList<DepartmentRsp>();
		
		String condition=" and sobgId='"+sobId+"' and orggId='"+orgId+"' and isnull(isdel,0)=0 and last=1 ";
//		if(billType.equalsIgnoreCase(Constants.TASKTYPE_CLCK) || billType.equalsIgnoreCase(Constants.TASKTYPE_GXLL) || billType.equalsIgnoreCase(Constants.TASKTYPE_GXTL)
//			|| billType.equalsIgnoreCase(Constants.TASKTYPE_CPRK) ){//限制所选择部门，目前暂时用isWorkshop，isWorkshop=1时出入库可选择
//			condition+=" and isWorkshop=1 ";
//		}
		
		condition+=" order by depCode ";
		
		List<AaDepartment> dpts=basicSettingDao.getDepartmentListEntity(condition);
		for(AaDepartment d:dpts){
			DepartmentRsp dt=new DepartmentRsp();
			dt.setGid(d.getGid());
			dt.setDepCode(d.getDepcode());
			dt.setDepName(d.getDepname());
			drs.add(dt);
		}
		
		return drs;
	}
	
	
	
	/**
	 * @category 部门树
	 *2015 2015年12月30日上午8:50:49
	 *List<Map>
	 *宋银海
	 */
	public List<Map> getDepartmentTree(String orgId,String sobId){
		String condition=" and sobgId='"+sobId+"' and orggId='"+orgId+"' and isnull(isdel,0)=0 ";
		List<AaDepartment> dpts=basicSettingDao.getDepartmentListEntity(condition);
		
		List<Map> maps=new ArrayList<Map>();
		
		for(AaDepartment dpt:dpts){
			Map map=new HashMap();
			if(CommonUtil.isNullObject(dpt.getDepparentuid())){
				map.put("id", dpt.getGid());
				map.put("pId",-1);
				map.put("name", dpt.getDepname());
			}else{
				map.put("id", dpt.getGid());
				map.put("pId",dpt.getDepparentuid());
				map.put("name", dpt.getDepname());
			}
			
			maps.add(map);
		}
		
		return maps;
	}
	
	
	/**
	 * @category 增加部门
	 *2015 2015年12月30日上午11:09:13
	 *boolean
	 *宋银海
	 */
	public boolean addDepartment(AaDepartment aaDepartment){
		return basicSettingDao.addDepartment(aaDepartment);
	}
	
	/**
	 * @category 获得部门详情
	 *2015 2015年12月30日上午11:27:20
	 *List<AaDepartment>
	 *宋银海
	 */
	public AaDepartment getDepartmentDetail(String orgId,String sobId,String gid){
		String condition=" and sobgId='"+sobId+"' and orggId='"+orgId+"' and gid='"+gid+"'";
		
		AaDepartment aaDepartment=basicSettingDao.getDepartmentDetail(condition);
		
		AaDepartment departmentParent=null;
		if(!CommonUtil.isNullObject(aaDepartment.getDepparentuid())){
			condition=" and sobgId='"+sobId+"' and orggId='"+orgId+"' and gid='"+aaDepartment.getDepparentuid()+"'";
			departmentParent=basicSettingDao.getDepartmentDetail(condition);
		}
		
		if(!CommonUtil.isNullObject(departmentParent)){
			aaDepartment.setDepparentname(departmentParent.getDepname());
		}
		
		return aaDepartment;
	}
	
	
	/**
	 * @category 修改部门
	 *2015 2015年12月30日下午2:30:54
	 *boolean
	 *宋银海
	 */
	public boolean editDepartment(AaDepartment aaDepartment){
		return basicSettingDao.editDepartment(aaDepartment);
	}
	
	/**
	 * @category 删除部门
	 *2015 2015年12月30日下午3:19:49
	 *boolean
	 *宋银海
	 */
	public boolean deleteDepartment(String gid){
		basicSettingDao.deleteDepartment(gid);
		return true;
	}
	
	
	/**
	 * @category 增加人员
	 *2015 2015年12月31日上午10:00:43
	 *boolean
	 *宋银海
	 */
	public boolean addPerson(AaPerson aaPerson){
		return basicSettingDao.addPerson(aaPerson);
	}
	
	
	/**
	 * @category 开工获得人员列表
	 *2016 2016年6月14日上午10:50:24
	 *List<DepartmentRsp>
	 *宋银海
	 */
	public List<DepartmentPersonRsp> getDepartmentPersonList(String orgId,String sobId,String dptGid){
		List<DepartmentPersonRsp> dpr=new ArrayList<DepartmentPersonRsp>();
		
		String condition=" and sobgId='"+sobId+"' and orggId='"+orgId+"' and depGid='"+dptGid+"'";
		List<AaPerson> aps=basicSettingDao.getPersonList(condition);
		
		for(AaPerson p:aps){
			DepartmentPersonRsp dp=new DepartmentPersonRsp();
			dp.setGid(p.getGid());
			dp.setPersonCode(p.getPercode());
			dp.setPersonName(p.getPername());
			dpr.add(dp);
		}
		
		return dpr;
	}
	
	
	/**
	 * @category 分页查询人员
	 *2015 2015年12月31日下午1:28:11
	 *PageBean
	 *宋银海
	 */
	public PageBean getPersonPageBean(int pageIndex,int pageSize,String deptGid,String orgId,String sobId){
		
		StringBuffer sbf=new StringBuffer();
		sbf.append(" and ps.orgGid='"+orgId+"' and ps.sobGid='"+sobId+"' and ps.isDel=0 ");
		
		if(!CommonUtil.isNullObject(deptGid)){
			sbf.append(" and depGid='"+deptGid+"'");
		}
		
		String condition=sbf.toString();
		
		PageBean pageBean=basicSettingDao.getPersonPageBean(pageIndex, pageSize, condition);
		return pageBean;
	}
	
	
	/**
	 * @category 查询单个人员
	 *2015 2015年12月31日下午5:04:13
	 *Map
	 *宋银海
	 */
	public Map getPersonMap(String gid,String orgId,String sobId){
		
		StringBuffer sbf=new StringBuffer();
		sbf.append(" and ps.gid='"+gid+"' and ps.orgGid='"+orgId+"' and ps.sobGid='"+sobId+"'");
		String condition=sbf.toString();
		
		return basicSettingDao.getPersonMap(condition);
	}
	
	
	/**
	 * @category 修改人员
	 *2015 2015年12月31日下午5:13:30
	 *boolean
	 *宋银海
	 */
	public boolean uptPerson(AaPerson aaPerson){
		return basicSettingDao.uptPerson(aaPerson);
	}
	
	
	/**
	 * @category 删除人员
	 *2016 2016年1月4日上午10:40:28
	 *boolean
	 *宋银海
	 */
	public boolean deletePerson(String gid){
		basicSettingDao.deletePerson(gid);
		return true;
	}
	//根据仓库号查询货位
	public PageBean getGoodsAllocationPageBean(int pageIndex,int pageSize,String condition){
		

		
		return basicSettingDao.getGoodsAllocationPageBean(pageIndex, pageSize, condition);
	}
	//////////////////////////////////////////////////////////////////////////////////////////////以下是用户部分
	
	
	/**
	 * @param condition2 
	 * @category 用户列表
	 *2016 2016年1月4日下午2:21:22
	 *List<YmUser>
	 *宋银海
	 */
	public PageBean getYmUser(int pageIndex,int pageSize,String orgId,String sobId, String condition2){
		
		StringBuffer sbf=new StringBuffer();
//		sbf.append(" and us.orgGid='"+orgId+"' and us.sobGid='"+sobId+"' and (us.isDelete=0 or us.isDelete is null) ");
		sbf.append(" and us.orgGid='"+orgId+"' and us.sobGid='"+sobId+"'  ");
		if(CommonUtil.notNullString(condition2)){
			sbf.append(condition2);
		}
		String condition=sbf.toString();
		
		PageBean pageBean=basicSettingDao.getYmUser(pageIndex, pageSize, condition);
		return pageBean;
	}
	
	
	/**
	 * @category 增加用户
	 *2016 2016年1月4日下午1:46:08
	 *boolean
	 *宋银海
	 */
	public boolean addUser(YmUser ymUser){
		return basicSettingDao.addUser(ymUser);
	}
	
	
	/**
	 * @category 根据用户编码查询用户信息
	 *2016 2016年4月6日下午4:07:44
	 *boolean 
	 *宋银海
	 */
	public YmUser getUser(String userCode) {
		String condition=" userCode='"+userCode+"'";
		return basicSettingDao.getYmUser(condition);
	}
	
	
	/**
	 * @throws InterruptedException 
	 * @category 安卓登录
	 *2016 2016年4月7日下午2:10:52
	 *LoginDetailRsp
	 *宋银海
	 */
	public LoginDetailRsp androidLogin(String userCode,String passWord) throws InterruptedException{
		
		String condition=" userCode='"+userCode+"'";
		YmUser ymUser=basicSettingDao.getYmUser(condition);//查询用户 
		
		int success=0;
		String failInfor="";
		LoginDetailRsp loginDetailRsp=new LoginDetailRsp();
		StringBuffer sbf=new StringBuffer();
		String taskTypeCode="";
		if(CommonUtil.isNullObject(ymUser)){
			failInfor="该用户不存在！";
		}else{
			String md5_pass = PasswordUtil.generatePassword(passWord);
			if(md5_pass.equalsIgnoreCase(ymUser.getPassWord())){
				success=1;
				loginDetailRsp.setUerUid(ymUser.getGid());
				loginDetailRsp.setUserName(ymUser.getUserName());
				loginDetailRsp.setOrggid(ymUser.getOrggid());
				loginDetailRsp.setSobgid(ymUser.getSobgid());
				
				condition=" ru.userId='"+ymUser.getGid()+"' and rr.useFuns=1 ";
				List<Map> rightMaps=basicSettingDao.getAndroidLoginRight(condition);//查询权限
				
				HashSet<String> hs=new HashSet<String>();
				
				for(Map map :rightMaps){
					if(!CommonUtil.isNullObject(map.get("taskTypeCode"))){
						hs.add(map.get("taskTypeCode").toString());
					}
				}
				
				Iterator it=hs.iterator();
				while(it.hasNext()){
					sbf.append(it.next().toString()+",");
				}
				
				taskTypeCode=sbf.toString();
				if(!CommonUtil.isNullObject(taskTypeCode)){
					taskTypeCode=taskTypeCode.substring(0, taskTypeCode.length()-1);
				}
				
			}else{
				failInfor="密码错误！";
			}
		}
		
		loginDetailRsp.setSuccess(Integer.valueOf(success));
		loginDetailRsp.setFailInfor(failInfor);
		loginDetailRsp.setTaskType(taskTypeCode);
		
		return loginDetailRsp;
	}

	public YmUser findUser(String gid) {
		return basicSettingDao.findUser(gid);
	}

	public boolean editUser(YmUser ymUser) {
		return basicSettingDao.editUser(ymUser);
	}
	
	
	public List<AaFreeSet> getFreeSet() {
		return basicSettingDao.getFreeSet();
	}

	public List<Map> getWarehouseTreeOnly(String orgId, String sobId) {
		String condition=" and sobgId='"+sobId+"' and orggId='"+orgId+"' and (isDel=0 or isDel is null) ";
		List<Map> warehouse=basicSettingDao.getWarehouseList(condition);
		
		for(Map map :warehouse){
			map.put("id", map.get("gid").toString());
			map.put("pId", 0);
			map.put("isParent", true);
			map.put("children", new ArrayList());
			map.put("name","("+map.get("whcode").toString()+")"+ map.get("whname").toString());
		}
		
		Map m=new HashMap();
		m.put("id", 0);
		m.put("pId",-1);
		m.put("open", true);
		m.put("name", "全部");
		
		warehouse.add(m);
		
		return warehouse;
	}
	
	public List<Map> getAllocationTreeOnly(String orgId,String sobId,String warehouseId){
		List<Map> allocation=new ArrayList<Map>();
		
		String condition=" and sobgId='"+sobId+"' and orggId='"+orgId+"' and (isDel=0 or isDel is null) and whUid='"+warehouseId+"' ";
		List<AaGoodsallocation> goodsAllocation=basicSettingDao.getAaGoodsallocationList(condition+" and posEnd=1 ");
		
		for(AaGoodsallocation ga:goodsAllocation){
			Map map=new HashMap();
			if(CommonUtil.isNullObject(ga.getWhuid())){
				map.put("id", ga.getGid());
				map.put("pId", 0);
				map.put("name","("+ga.getCode()+")"+ ga.getName());
			}else{
				map.put("id", ga.getGid());
				map.put("pId", ga.getWhuid());
				map.put("name", "("+ga.getCode()+")"+ga.getName());
			}
			
			allocation.add(map);
		}
		return allocation;
	}

	public AaGoodsallocation getGoodsAllocationForGolBal(String condition) {
			return basicSettingDao.getGoodsAllocationForGolBal(condition);

	}
}
