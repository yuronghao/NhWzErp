package com.emi.wms.processDesign.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.MesWmProduceProcessRouteCDispatching;
import com.emi.wms.bean.MesWmProduceProcessRouteCEquipment;
import com.emi.wms.bean.MesWmProduceProcessRouteCMould;
import com.emi.wms.bean.MesWmProduceProcessRouteCPre;
import com.emi.wms.bean.MesWmProduceProcessroute;
import com.emi.wms.bean.MesWmProduceProcessroutec;
import com.emi.wms.bean.MesWmProduceProcessroutecGoods;
import com.emi.wms.bean.MesWmStandardprocess;
import com.emi.wms.bean.WmProduceorderC;
import com.emi.wms.bean.WmProduceorderC2;
import com.emi.wms.bean.WmTask;

public interface OrderPDDao {

	/**
	 * @category 订单字表id
	 * 2016年4月29日 上午8:54:57 
	 * @author zhuxiaochen
	 * @param routeId
	 * @return
	 */
	public List<WmProduceorderC> getProduceOrderCList(String orderId);
	
	
	/**
	 * @category 根据订单gid 获得订单产品表跟标准工艺路线主表关联
	 *2016 2016年9月14日上午9:43:55
	 *List<Map>
	 *宋银海
	 */
	public List<Map> getProduceOrderCProcessRoute(String orderGid);

	/**
	 * @category 保存拷贝的数据
	 * 2016年4月29日 下午3:06:28 
	 * @author zhuxiaochen
	 * @param p_route
	 * @param p_cList
	 * @param p_preList
	 * @param p_goodsList
	 * @param p_dispatchingList
	 * @param p_equipmentList 
	 * @param p_mouldList 
	 */
	public void saveCopyedProduceRoute(List<MesWmProduceProcessroute> p_routeList,
			List<MesWmProduceProcessroutec> p_cList,
			List<MesWmProduceProcessRouteCPre> p_preList,
			List<MesWmProduceProcessroutecGoods> p_goodsList,
			List<MesWmProduceProcessRouteCDispatching> p_dispatchingList, List<MesWmProduceProcessRouteCEquipment> p_equipmentList, List<MesWmProduceProcessRouteCMould> p_mouldList);

	/**
	 * @category 检验是否有订单工艺路线数据
	 * 2016年4月29日 下午3:25:25 
	 * @author zhuxiaochen
	 * @param orderId
	 * @return
	 */
	public boolean checkHasRoute(String orderId);

	/**
	 * @category 删除原有的订单工艺路线
	 * 2016年4月29日 下午4:49:49 
	 * @author zhuxiaochen
	 * @param orderId
	 */
	public void deleteOldData(String orderId);

	/**
	 * @category 根据订单查询其工艺路线
	 * 2016年5月3日 上午11:01:08 
	 * @author zhuxiaochen
	 * @param orderId
	 * @param orderCid
	 * @return
	 */
	public MesWmProduceProcessroute queryProduceRoute(String orderId,
			String orderCid);

	/**
	 * @category 订单工艺路线字表列表
	 * 2016年5月3日 上午11:25:59 
	 * @author zhuxiaochen
	 * @param routeId
	 * @return
	 */
	public List<MesWmProduceProcessroutec> getRouteCList(String routeId);

	/**
	 * @category 上道工序转入
	 * 2016年5月3日 上午11:30:48 
	 * @author zhuxiaochen
	 * @param routeCids
	 * @return
	 */
	public List<MesWmProduceProcessRouteCPre> getProcessRouteCPreList(
			String routeCids);

	public List<MesWmProduceProcessroutecGoods> getProcessRouteCGoodsList(
			String routeCids);

	public List<MesWmProduceProcessRouteCDispatching> getProcessRouteCDispatchingList(
			String routeCids);

	public void updateOrderRoute(MesWmProduceProcessroute route);

	public void addProcessRouteC(List<MesWmProduceProcessroutec> addList);

	public void updateProcessRouteC(List<MesWmProduceProcessroutec> updateList);

	public void deleteProcessRouteC(String deleteIds);

	public void deleteRoutecAttributes(String updateIds);

	public void insertRoutecAttributes(
			List<MesWmProduceProcessroutec> updateList,
			List<MesWmProduceProcessroutec> addList);

	public List<MesWmProduceProcessRouteCEquipment> getProduceProcessRouteCEquipmentList(
			String routeCids);

	/**
	 * @category 根据订单获取需要开工的第一道工序
	 * 2016年5月11日 上午10:09:19 
	 * @author zhuxiaochen
	 * @param orderId
	 * @return
	 */
	public List<MesWmProduceProcessroutec> getProduceFirstProcess(String orderId);

	/**
	 * @category 检测是否有正在进行的任务
	 * 2016年5月26日 下午5:17:25 
	 * @author zhuxiaochen
	 * @param orderId
	 * @param type 来源类型 0：生成订单工艺路线  1：派发任务
	 * @return
	 */
	public boolean checkDoingTask(String orderId,String ordercId,int type);

	/**
	 * @category 删除原有订单任务
	 * 2016年6月6日 上午9:17:11 
	 * @author zhuxiaochen
	 * @param orderId
	 */
	public void deleteOldProduceTask(String ordercId);
	
	public void deleteOldOrderTask(String orderId);

	/**
	 * @category 获取可作为改制订单来源的生产订单
	 * 2016年6月14日 下午5:21:26 
	 * @author zhuxiaochen
	 * @param orgId
	 * @param sobId
	 * @param pageSize 
	 * @param pageIndex 
	 * @param condition 
	 * @param filter 
	 * @return
	 */
	public PageBean getEnabledChangeOrder(String orgId, String sobId, int pageIndex, int pageSize, String condition, boolean filter);

	/**
	 * @category 订单的所有产品工艺路线子表
	 * 2016年6月16日 下午2:13:26 
	 * @author zhuxiaochen
	 * @param orderId
	 * @return
	 */
	public List<MesWmProduceProcessroutec> getProduceRouteCList(String orderId);

	/**
	 * 根据订单获取可用于改制的工序
	 * @category 改制工序
	 * 2016年6月21日 上午10:22:26 
	 * @author zhuxiaochen
	 * @param orderId
	 * @return
	 */
	public List<Map> getChangeProcessSrc(String ordercId,String condition);
	public List<Map> getChangeProcessSrcInfo(String ordercId,String condition);

	/**
	 * @category 检测是否有派工任务
	 * 2016年6月22日 下午4:16:58 
	 * @author zhuxiaochen
	 * @param routeCid
	 * @return
	 */
	public boolean checkDispatchTask(String routeCid);

	/**
	 * @category 检测是否有领料任务
	 * 2016年6月22日 下午4:17:13 
	 * @author zhuxiaochen
	 * @param routeCid
	 * @return
	 */
	public boolean checkGoodsTask(String routeCid);

	/**
	 * @category 删除工序的领料任务
	 * 2016年6月24日 上午11:08:23 
	 * @author zhuxiaochen
	 * @param oldProcessList
	 */
	public void deleteProcessGoodsTask(List<String> oldProcessList);

	/**
	 * @category 生成领料任务
	 * 2016年6月24日 上午11:08:53 
	 * @author zhuxiaochen
	 * @param newNextProcess
	 */
	public void reGenProcessGoodsTask(List<String> newNextProcess);

	/**
	 * @category (还未领的所有的任务)
	 * 2016年6月28日 上午11:08:53 
	 * @author zhuxiaochen
	 * @param route_id
	 * @return
	 */
	public List<WmTask> getIdleTask(String route_id);

	/**
	 * @category 获取首道闲置工序，且有料可以领
	 * 2016年6月28日 上午11:09:09 
	 * @author zhuxiaochen
	 * @param route_id
	 * @return
	 */
	public List<MesWmProduceProcessroutec> getIdleStartRoutec(String route_id);

	/**
	 * @category 根据任务id删除任务
	 * 2016年6月28日 上午11:09:38 
	 * @author zhuxiaochen
	 * @param taskIds
	 */
	public void deleteTasks(String taskIds);

	/**
	 * @category 上道工序有开工了的工序
	 * 2016年6月28日 下午5:18:17 
	 * @author zhuxiaochen
	 * @param route_id
	 * @return
	 */
	public List<MesWmProduceProcessroutec> getPreDispatchedRoutec(
			String route_id);

	/**
	 * @category 根据单据id删除任务
	 * 2016年6月29日 下午2:38:23 
	 * @author zhuxiaochen
	 * @param deleteIds
	 */
	public void deleteTasksByBillId(String billIds);

	public List<MesWmProduceProcessRouteCMould> getProduceProcessRouteCMouldList(
			String routeCids);

	public void insertObject(Object obj);

	public MesWmProduceProcessroutec findProduceProcessroutec(
			String changeSrcRouteCid);

	/**
	 * @category 回填订单子表转出数量
	 * 2016年7月13日 下午2:08:27 
	 * @author zhuxiaochen
	 * @param changeSrcOrderCid
	 * @param realNumber
	 */
	public void setOrdercTurnoutNum(String changeSrcOrderCid,
			BigDecimal realNumber);

	public void updateObject(Object routec);

	/**
	 * @category 来源订单子表
	 * 2016年7月13日 下午3:15:03 
	 * @author zhuxiaochen
	 * @param changeSrcOrderCid
	 * @return
	 */
	public WmProduceorderC findProduceOrderc(String changeSrcOrderCid);

	/**
	 * @category 清除改制来源
	 * 2016年7月15日 下午2:07:52 
	 * @author zhuxiaochen
	 * @param ordercId
	 * @param routecId
	 * @param thisRouteId 
	 */
	public void clearChangeSrc(String ordercId, String routecId, String thisRouteId);

	public void deleteProductOldData(String ordercId);

	/**
	 * @category 根据id得到订单子表
	 * 2016年7月18日 上午8:47:38 
	 * @author zhuxiaochen
	 * @param ordercId
	 * @return
	 */
	public List<WmProduceorderC> getOrderCListById(String ordercId);

	public MesWmProduceProcessroute findProduceRouteByOrderC(String thisOrdercId);

	/**
	 * @category 根据订单id找到所有产品的工艺路线
	 * 2016年8月22日 下午3:19:31 
	 * @author zhuxiaochen
	 * @param orderId
	 * @return
	 */
	public List<MesWmProduceProcessroute> getProduceRouteByOrder(String orderId);

	/**
	 * @category 根据工艺路线检测是否已有领料任务在进行
	 * 2016年8月22日 下午4:02:48 
	 * @author zhuxiaochen
	 * @param gid
	 * @return
	 */
	public boolean checkGoodsTaskByRoute(String routeId);

	/**
	 * @category 根据订单子表id获取首道工序
	 * 2016年8月22日 下午4:55:04 
	 * @author zhuxiaochen
	 * @param produceCuid
	 * @return
	 */
	public List<MesWmProduceProcessroutec> getProduceFirstProcessByOrderc(
			String ordercId,int flag);

	/**
	 * @category 查询可以改制数量
	 * 2016年8月23日 上午11:21:00 
	 * @author zhuxiaochen
	 * @param srcRoutecId
	 * @return
	 */
	public BigDecimal getEnabledChangeNumber(String srcRoutecId);

	/**
	 * 修改实际工价
	 *2017 2017年1月15日下午5:21:47
	 *int[]
	 *宋银海
	 */
	public int[] changeRealPrice(List<Map> maps);
	
	//获得标准工序
	public MesWmStandardprocess getMesWmStandardprocess(String opname);
	
	//获得 订单产品表获得材料表
	public List<WmProduceorderC2> getWmProduceorderC2(String produceCuid);
}
