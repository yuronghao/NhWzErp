package com.emi.wms.processDesign.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.MesWmProduceProcessroute;
import com.emi.wms.bean.MesWmProduceProcessroutec;
import com.emi.wms.bean.WmProduceorderC;
import com.emi.wms.processDesign.util.NotEnoughException;

public interface OrderPDService {

	/**
	 * @category 生成订单工艺路线
	 * 2016年4月28日 下午5:15:41 
	 * @author zhuxiaochen
	 * @param routeId
	 */
	public JSONObject genOrderRoute(String orderId,boolean reGen);
	public void genProductRoute(String orderId, String ordercId);
	/**
	 * @category 校验是否已有订单路线
	 * 2016年4月29日 下午3:40:13 
	 * @author zhuxiaochen
	 * @param orderId
	 * @return
	 */
	boolean checkHasRoute(String orderId);

	/**
	 * @category 查询订单工艺路线
	 * 2016年5月3日 上午9:28:01 
	 * @author zhuxiaochen
	 * @param orderId
	 * @param orderCid
	 * @return
	 */
	MesWmProduceProcessroute queryProduceRoute(String orderId, String orderCid);

	JSONObject getProcessJson(String gid);

	/**
	 * @category 保存工艺路线数据
	 * 2016年5月3日 下午3:47:43 
	 * @author zhuxiaochen
	 * @param productId
	 * @param isAlter 
	 * @param processList
	 * @param route_id
	 * @param process_info
	 * @param arr_updProcess
	 * @param arr_addProcess
	 * @param arr_delProcess
	 * @param process_objs
	 * @param process_codeJson
	 * @param number 
	 * @param arr_taskNextProcess 
	 * @param arr_taskProcess 
	 * @param changeOrder 
	 * @param changeSrcJson 
	 */
	void saveProcessData(String isAlter,String productId,
			 List<MesWmProduceProcessroutec> processList, String route_id,
			String process_info, String arr_updProcess, String arr_addProcess,
			String arr_delProcess, String process_objs, String process_codeJson, String number, String arr_taskProcess, String arr_taskNextProcess, String changeOrder, String changeSrcJson)throws NotEnoughException;

	
	/**
	 * @category 调整单价
	 *2017 2017年1月15日下午5:04:57
	 *void
	 *宋银海
	 */
	void changeRealPrice(String routeCid, String realPrice)throws NotEnoughException;

	
	
	/**
	 * @category 创建任务
	 * 2016年5月11日 上午9:53:28 
	 * @author zhuxiaochen
	 * @param orderId
	 */
	void createTask(String orderId);

	/**
	 * @category 检测是否已有进行的任务
	 * 2016年5月26日 下午5:14:29 
	 * @author zhuxiaochen
	 * @param orderId
	 * @param type 来源类型 0：生成订单工艺路线  1：派发任务
	 * @return
	 */
	boolean checkDoingTask(String orderId,int type);

	/**
	 * @category 删除订单工艺路线
	 * 2016年6月6日 上午8:52:54 
	 * @author zhuxiaochen
	 * @param orderId
	 * @param orderCid
	 */
	void deleteProductRoute(String ordercId);
	
	public void deleteOrderRoute(String orderId);

	/**
	 * @category 可作为改制订单来源的生产订单
	 * 2016年6月14日 下午5:17:33 
	 * @author zhuxiaochen
	 * @param orgId
	 * @param sobId
	 * @param pageSize 
	 * @param pageIndex 
	 * @param condition 
	 * @return
	 */
	PageBean getEnabledChangeOrder(String orgId, String sobId, int pageIndex, int pageSize, String condition,boolean filter);
	PageBean getEnabledChangeOrder(String orgId, String sobId, int pageIndex, int pageSize, String condition);

	/**
	 * @category 检测数据
	 * 2016年6月16日 下午1:38:29 
	 * @author zhuxiaochen
	 * @param orderId
	 * @return
	 */
	String checkData(String orderId);

	/**
	 * @category 改制订单的工序来源
	 * 2016年6月21日 上午10:15:29 
	 * @author zhuxiaochen
	 * @param condition 
	 * @param orderId
	 * @return
	 */
	List<Map> getChangeProcessSrc(String ordercId, String condition,boolean ignoreNum);

	/**
	 * @category 检测工序是否有任务正在进行
	 * 2016年6月22日 下午3:44:35 
	 * @author zhuxiaochen
	 * @param routeCid
	 * @return "none":无任务 ， "has":有任务   ，"error"：异常
	 */
	public boolean checkProcessTask(String routeCid);

	void dealRouteTask(String route_id);

	public void inertObject(Object obj);

	public WmProduceorderC findProduceOrderc(String orderCid);

	public boolean checkDoingTaskByOrderc(String ordercId, int parseInt);

	/**
	 * @category 清除改制来源
	 * 2016年7月15日 下午2:03:39 
	 * @author zhuxiaochen
	 * @param ordercId
	 * @param routecId
	 * @param thisRouteId 
	 */
	public void clearChangeSrc(String ordercId, String routecId, String thisRouteId);
	public MesWmProduceProcessroute findProduceRouteByOrderC(String thisOrdercId);
	/**
	 * @category 根据产品分别生成领料任务
	 * 2016年8月22日 下午3:08:36 
	 * @author zhuxiaochen
	 * @param orderId
	 * @return int 0:所有领料都任务已开始  1：成功
	 */
	public int createTask4Produce(String orderId);


}
