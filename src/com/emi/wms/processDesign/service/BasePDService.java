package com.emi.wms.processDesign.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.AaDepartment;
import com.emi.wms.bean.AaFreeSet;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.MesAaWorkcenter;
import com.emi.wms.bean.MesWmProcessRouteCPre;
import com.emi.wms.bean.MesWmProcessroute;
import com.emi.wms.bean.MesWmProcessroutec;
import com.emi.wms.bean.MesWmStandardprocess;

public interface BasePDService {

	/**
	 * @category 标准工艺路线列表
	 * 2015年5月25日 下午4:52:15 
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
	 * 2015年5月25日 下午5:27:31 
	 * @author 朱晓陈
	 * @param routeId
	 * @return
	 */
	public MesWmProcessroute findBaseRoute(String routeId);

	/**
	 * @category 更新工艺路线
	 * 2015年5月26日 上午8:40:44 
	 * @author 朱晓陈
	 * @param route
	 */
	public void updateBaseRoute(MesWmProcessroute route);

	/**
	 * @category 新增工艺路线
	 * 2015年5月26日 上午8:40:55 
	 * @author 朱晓陈
	 * @param route
	 */
	public void insertBaseRoute(MesWmProcessroute route);

	/**
	 * @category 工艺路线子表列表
	 * 2015年5月26日 下午5:06:34 
	 * @author 朱晓陈
	 * @param increamentId 主键
	 * @return
	 */
	public List<MesWmProcessroutec> getRouteCList( int increamentId);
	public List<MesWmProcessroutec> getRouteCList(String route_id);
	public List<MesWmProcessroutec> getRouteCListIn(String route_id);
	/**
	 * @category 工艺路线子表
	 * 2015年5月27日 上午11:16:39 
	 * @author 朱晓陈
	 * @param routecId
	 * @return
	 */
	public MesWmProcessroutec finBaseRouteC(String routecId);

	/**
	 * @category 根据工序得到上道工序转入
	 * 2015年5月27日 下午1:38:03 
	 * @author 朱晓陈
	 * @param routecId
	 * @return
	 */
	public List<MesWmProcessRouteCPre> getPreProcessList(String routecId);

	/**
	 * @category 保存标准工艺路线设计
	 * 2015年6月2日 下午3:40:14 
	 * @author 朱晓陈
	 * @param route_id
	 * @param process_info
	 * @param arr_updProcess
	 * @param arr_addProcess
	 * @param arr_delProcess
	 * @param process_objs
	 * @param process_codeJson
	 */
	public void saveProcessData(String productId,List<MesWmProcessroutec> processList,String route_id, String process_info,
			String arr_updProcess, String arr_addProcess,
			String arr_delProcess, String process_objs, String process_codeJson,String userid);

	public String getDesignJsonStr(String routeId ,JSONObject info_json,JSONObject objs_json);
	/**
	 * @category 删除标准路线
	 * 2015年12月21日 下午5:23:35 
	 * @author 朱晓陈
	 * @param routeId
	 */
	public void deleteBaseRoute(String routeId);
	
	public void deleteStandardProcess(String processId);
	
	public void deleteWorkCenter(String processId);

	/**
	 * @category 获取工艺路线的json
	 * 2016年4月26日 下午2:57:21 
	 * @author zhuxiaochen
	 * @param routeId
	 * @param product 
	 * @return
	 */
	public JSONObject getProcessJson(String routeId, AaGoods product);

	/**
	 * @category 根据查出来的工序节点生成设计字符串
	 * 2016年4月27日 下午5:41:17 
	 * @author zhuxiaochen
	 * @param process_objs
	 * @return
	 */
	public String getInitDesignJson(JSONObject process_objs,String routeId);
	public PageBean getStandardProcessList(int pageIndex,int pageSize,String condition);
	
	public PageBean getWorkCenterList(int pageIndex,int pageSize,String condition);
	
	public boolean saveStandardProcess(MesWmStandardprocess standardprocess);
	
	public boolean saveWorkCenter(MesAaWorkcenter workcenter);
	
	public MesWmStandardprocess findStandardProcess(String gid);
	
	public MesAaWorkcenter findWorkCenter(String gid);
	
	public AaDepartment findDepartment(String gid);
	
	public boolean updateStandardProcess(MesWmStandardprocess standardprocess);
	
	public boolean updateWorkCenter(MesAaWorkcenter workcenter);
	
	public List getDepList(String condtion);
	
	public List getCustomerList(String condtion);

	/**
	 * @category 得到标准工艺路线的整体数据（包括子表及相关属性）
	 * 2016年4月29日 上午10:39:19 
	 * @author zhuxiaochen
	 * @param goodsId
	 * @param free1 
	 * @return
	 */
	public Map getBaseProcessRouteOverall(String goodsId, String free1);

	/**
	 * @category 保存BOM到U8
	 * 2016年5月18日 上午9:13:31 
	 * @author zhuxiaochen
	 * @param process_objs
	 * @param process_info
	 * @param productCode
	 */
	public void saveBomToU8(String process_objs, String process_info,
			String productCode,List<String> msg, MesWmProcessroute route);

	/**
	 * @category 复制工艺路线
	 * 2016年5月27日 下午5:33:12 
	 * @author zhuxiaochen
	 * @param routeId
	 * @param copyNum
	 */
	public void copyRoute(String routeId, int copyNum);

	/**
	 * @category 导入文件
	 * 2016年5月31日 上午10:38:31 
	 * @author zhuxiaochen
	 * @param file
	 * @param type 
	 */
	public void importRoute(File file, int type) throws Exception;

	/**
	 * @category 获取物料的自由项
	 * 2016年7月4日 上午10:54:05 
	 * @author zhuxiaochen
	 * @return
	 */
	public List<AaFreeSet> getGoodsFreeset();

	/**
	 * @category 审核工艺路线
	 * 2016年7月7日 上午10:34:18 
	 * @author zhuxiaochen
	 * @param routeId
	 * @param authUserId 
	 */
	public List<String> auditRoute(String routeId, String authUserId);

	/**
	 * @category 弃审工艺路线
	 * 2016年7月7日 下午5:25:53 
	 * @author zhuxiaochen
	 * @param routeId
	 */
	public void cancelAuditRoute(String routeId);
	
}
