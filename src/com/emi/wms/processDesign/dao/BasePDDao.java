package com.emi.wms.processDesign.dao;

import java.util.List;

import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.AaDepartment;
import com.emi.wms.bean.AaFreeSet;
import com.emi.wms.bean.MesAaWorkcenter;
import com.emi.wms.bean.MesWmProcessRouteCDispatching;
import com.emi.wms.bean.MesWmProcessRouteCEquipment;
import com.emi.wms.bean.MesWmProcessRouteCMould;
import com.emi.wms.bean.MesWmProcessRouteCPre;
import com.emi.wms.bean.MesWmProcessRoutecGoods;
import com.emi.wms.bean.MesWmProcessroute;
import com.emi.wms.bean.MesWmProcessroutec;
import com.emi.wms.bean.MesWmStandardprocess;

public interface BasePDDao {

	/**
	 * @category 标准工艺路线列表
	 * 2015年5月25日 下午4:53:27 
	 * @author 朱晓陈
	 * @param condition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageBean getBaseProcessRouteList(String condition, int pageIndex,
			int pageSize);

	/**
	 * @category 标准工艺路线基本信息
	 * 2015年5月25日 下午5:28:18 
	 * @author 朱晓陈
	 * @param routeId
	 * @return
	 */
	public MesWmProcessroute findBaseRoute(String routeId);

	/**
	 * @category 更新工艺路线
	 * 2015年5月26日 上午8:42:18 
	 * @author 朱晓陈
	 * @param route
	 */
	public void updateBaseRoute(MesWmProcessroute route);

	/**
	 * @category 新增工艺路线
	 * 2015年5月26日 上午8:42:42 
	 * @author 朱晓陈
	 * @param route
	 */
	public void insertBaseRoute(MesWmProcessroute route);

	/**
	 * @category 工艺路线子表列表
	 * 2015年5月26日 下午5:08:20 
	 * @author 朱晓陈
	 * @param increamentId 主键
	 * @return
	 */
	public List<MesWmProcessroutec> getRouteCList( int increamentId);

	public MesWmProcessroutec finBaseRouteC(String routecId);

	/**
	 * @category 上道工序转入
	 * 2015年5月27日 下午1:39:05 
	 * @author 朱晓陈
	 * @param routecId
	 * @return
	 */
	public List<MesWmProcessRouteCPre> getPreProcessList(String routecId);

	public List<MesWmProcessroutec> getRouteCList(String route_id);
	
	public List<MesWmProcessroutec> getRouteCListIn(String route_id);

	/**
	 * @category 保存工艺路线设计图
	 * 2015年6月6日 下午3:08:07 
	 * @author 朱晓陈
	 * @param route_id
	 * @param string
	 */
	public void updateProcessInfo(String route_id, String string);

	/**
	 * @category 更新对象获对象列表
	 * 2015年6月6日 下午3:44:02 
	 * @author 朱晓陈
	 * @param processList
	 */
	public void updateObject(Object obj);
	/**
	 * @category 新增对象获对象列表
	 * 2015年6月6日 下午3:44:02 
	 * @author 朱晓陈
	 * @param processList
	 */
	public void addObject(Object obj);

	/**
	 * @category 删除标准工艺路线（假删除）
	 * 2015年12月21日 下午5:29:46 
	 * @author 朱晓陈
	 * @param routeId
	 */
	public void deleteBaseRoute(String routeId);
	
	public void deleteStandardProcess(String processId);
	
	public void deleteWorkCenter(String processId);

	/**
	 * @category 添加工序及子表
	 * 2016年4月25日 下午2:53:02 
	 * @author zhuxiaochen
	 * @param addList
	 */
	public void addProcessRouteC(List<MesWmProcessroutec> addList);

	/**
	 * @category 更新工序及子表
	 * 2016年4月25日 下午2:53:25 
	 * @author zhuxiaochen
	 * @param updateList
	 */
	public void updateProcessRouteC(List<MesWmProcessroutec> updateList);

	/**
	 * @category 删除工序属性的设置
	 * 2016年4月25日 下午3:44:26 
	 * @author zhuxiaochen
	 * @param updateIds
	 */
	public void deleteRoutecAttributes(String updateIds);

	/**
	 * @category 重新添加工序的属性设置
	 * 2016年4月25日 下午3:56:31 
	 * @author zhuxiaochen
	 * @param updateList
	 * @param addList
	 */
	public void insertRoutecAttributes(List<MesWmProcessroutec> updateList,
			List<MesWmProcessroutec> addList);

	/**
	 * @category 根据工艺路线子表id得到上道工序列表
	 * 2016年4月26日 下午3:13:24 
	 * @author zhuxiaochen
	 * @param routeCids
	 * @return
	 */
	public List<MesWmProcessRouteCPre> getProcessRouteCPreList(String routeCids);

	/**
	 * @category 根据工艺路线子表id得到物料领用列表
	 * 2016年4月26日 下午3:13:24 
	 * @author zhuxiaochen
	 * @param routeCids
	 * @return
	 */
	public List<MesWmProcessRoutecGoods> getProcessRouteCGoodsList(
			String routeCids);

	/**
	 * @category 根据工艺路线子表id得到派工对象（工作中心、人员等）列表
	 * 2016年4月26日 下午3:13:24 
	 * @author zhuxiaochen
	 * @param routeCids
	 * @return
	 */
	public List<MesWmProcessRouteCDispatching> getProcessRouteCDispatchingList(
			String routeCids);

	/**
	 * @category 删除工序
	 * 2016年4月27日 上午9:23:57 
	 * @author zhuxiaochen
	 * @param deleteIds
	 */
	public void deleteProcessRouteC(String deleteIds);
	
	public PageBean getStandardProcessList(int pageIndex,int pageSize,String condition);
	
	public PageBean getWorkCenterList(int pageIndex,int pageSize,String condition);
	
	public boolean saveStandardProcess(MesWmStandardprocess standardprocess);
	
	public boolean saveWorkCenter(MesAaWorkcenter workcenter);
	
	public MesWmStandardprocess findStandardProcess(String gid);
	
	public MesAaWorkcenter findWorkCenter(String gid);
	
	public AaDepartment findDepartment(String gid);
	
	public boolean updateStandardProcess(MesWmStandardprocess standardprocess);
	
	public boolean updateWorkCenter(MesAaWorkcenter workcenter);
	
	public List getDepList(String condition);
	
	public List getCustomerList(String condition);

	/**
	 * @category 根据物品得到其工艺路线
	 * 2016年4月29日 上午10:41:58 
	 * @author zhuxiaochen
	 * @param goodsId
	 * @return
	 */
	public MesWmProcessroute getBaseRouteByGoods(String goodsId);
	
	public MesWmProcessroute getBaseRouteByGoodsCfree(String goodsId,String cfree);

	/**
	 * @category 工序的设备
	 * 2016年5月5日 下午3:40:46 
	 * @author zhuxiaochen
	 * @param routeCids
	 * @return
	 */
	public List<MesWmProcessRouteCEquipment> getProcessRouteCEquipmentList(
			String routeCids);
	/**
	 * @category 工序的设备
	 * 2016年5月5日 下午3:40:46 
	 * @author zhuxiaochen
	 * @param routeCids
	 * @return
	 */
	public List<MesWmProcessRouteCMould> getProcessRouteCMouldList(
			String routeCids);

	/**
	 * @category 批量添加工艺路线主表
	 * 2016年5月31日 下午2:44:30 
	 * @author zhuxiaochen
	 * @param routeList
	 */
	public void addProcessRoute(List<MesWmProcessroute> routeList);

	public void addImportProcessRoute(List<MesWmProcessroute> routeList);

	public void addImportProcessRouteC(List<MesWmProcessroutec> routeClist) throws Exception;

	public void addImportProcessRouteCGoods(
			List<MesWmProcessRoutecGoods> goodsList) throws Exception ;

	/**
	 * @category 插入复制的工艺路线
	 * 2016年6月6日 上午10:27:45 
	 * @author zhuxiaochen
	 * @param route
	 * @param routec
	 * @param pre
	 * @param goods
	 * @param dispatching
	 * @param equ
	 */
	public void insertCopyedRoute(MesWmProcessroute route,
			List<MesWmProcessroutec> routec, List<MesWmProcessRouteCPre> pre,
			List<MesWmProcessRoutecGoods> goods,
			List<MesWmProcessRouteCDispatching> dispatching,
			List<MesWmProcessRouteCEquipment> equ,List<MesWmProcessRouteCMould> mould);

	/**
	 * @category 获取物料自由项
	 * 2016年7月4日 上午10:54:41 
	 * @author zhuxiaochen
	 * @return
	 */
	public List<AaFreeSet> getGoodsFreeset();

	/**
	 * @category 弃审工艺路线
	 * 2016年7月7日 下午5:26:32 
	 * @author zhuxiaochen
	 * @param routeId
	 */
	public void cancelAuditRoute(String routeId);

	/**
	 * @category 有自由项过滤的工艺路线子表列表
	 * 2016年8月4日 上午11:32:59 
	 * @author zhuxiaochen
	 * @param gid
	 * @param free1
	 * @return
	 */
//	public List<MesWmProcessroutec> getRouteCListByFree(String routeId, String free1);

	public List<MesWmProcessroutec> getRouteCListByIds(String routecids_free);


}
