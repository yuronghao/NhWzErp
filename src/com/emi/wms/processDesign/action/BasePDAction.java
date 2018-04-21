package com.emi.wms.processDesign.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.emi.android.action.Submit;
import com.emi.cache.service.CacheCtrlService;
import com.emi.common.action.BaseAction;
import com.emi.common.util.Base64;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.Constants;
import com.emi.common.util.ExcelUtil;
import com.emi.common.util.HttpRequester;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.core.format.EmiJsonArray;
import com.emi.sys.core.format.EmiJsonObj;
import com.emi.sys.file.EmiFileOption;
import com.emi.sys.init.Config;
import com.emi.sys.util.SysPropertites;
import com.emi.wms.bean.AaDepartment;
import com.emi.wms.bean.AaFreeSet;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.MesAaWorkcenter;
import com.emi.wms.bean.MesWmProcessroute;
import com.emi.wms.bean.MesWmProcessroutec;
import com.emi.wms.bean.MesWmStandardprocess;
import com.emi.wms.bean.YmUser;
import com.emi.wms.processDesign.bean.BasPart;
import com.emi.wms.processDesign.bean.BomBomReq;
import com.emi.wms.processDesign.bean.StockRouteC;
import com.emi.wms.processDesign.service.BasePDService;
import com.emi.wms.processDesign.util.BasePDUtil;

/*
 * 基本工艺路线设计
 */
@SuppressWarnings({"unchecked","rawtypes"})	
public class BasePDAction extends BaseAction{
	private static final long serialVersionUID = 4920894976410036457L;
	private BasePDService basePDService;
	private MesWmProcessroute route;
	private CacheCtrlService cacheCtrlService;
	private File file;
	private String fileFileName;	//文件名
	private String fileContentType;	//文件类型
	
	public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
		this.cacheCtrlService = cacheCtrlService;
	}

	public void setBasePDService(BasePDService basePDService) {
		this.basePDService = basePDService;
	}
	

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	public MesWmProcessroute getRoute() {
		return route;
	}

	public void setRoute(MesWmProcessroute route) {
		this.route = route;
	}

	/**
	 * @category 标准工艺路线列表
	 * 2015年5月26日 上午8:22:47 
	 * @author 朱晓陈
	 * @return
	 */
	public String toBaseRouteList(){
		try {
			String keyWord = this.getParameter("keyWord");					//关键字
			String status = getParameter("status");	//审核状态
			int pageIndex = getPageIndex();								//页码，从1开始
			int pageSize = Config.PAGESIZE_WEB;							//每页总条数
			
			String columns = "r.routname,r.routcode,g.goodscode,g.goodsname,g.goodsstandard";
			String condition = CommonUtil.combQuerySql(columns, keyWord);	//过滤条件
//			if(CommonUtil.notNullString(keyWord)){
//				condition += " or goodsUid in (select gid from AA_Goods where goodscode like '%"+keyWord+"%' or goodsname like '%"+keyWord+"%' or goodsstandard like '%"+keyWord+"%') ";
//			}
			if(CommonUtil.notNullString(status)){
				if("0".equals(status)){
					condition += " and (state=0 or state is null)";
				}else{
					condition += " and state="+status;
				}
			}
			PageBean pageBean = basePDService.getBaseProcessRouteList(condition,pageIndex,pageSize);
			String[] goodsIds = new String[pageBean.getList().size()];
			StringBuffer sb=new StringBuffer();
			for(int i = 0;i<pageBean.getList().size();i++){
				MesWmProcessroute p = (MesWmProcessroute) pageBean.getList().get(i);
				goodsIds[i] = Constants.CACHE_GOODS+"_"+p.getGoodsUid();
				
				sb.append("'"+p.getGid()+"',");
				
			}
			
			String sbStr=sb.toString();
			if(sbStr.length()>0){
				sbStr="("+sbStr.substring(0, sbStr.length()-1)+")";
			}
			
			//查询标准工艺路线子表
			List<MesWmProcessroutec>  mwpc=basePDService.getRouteCListIn(sbStr);
			
			//从缓存中取物料
			List<AaGoods> goodsList = cacheCtrlService.getGoodsList(goodsIds);
			//设置到对应的列表数据中
			for(Object po : pageBean.getList()){
				MesWmProcessroute p = (MesWmProcessroute) po;
				
				for(MesWmProcessroutec wc:mwpc){
					if(p.getGid().equalsIgnoreCase(wc.getRoutGid())){
						MesWmStandardprocess ms=cacheCtrlService.getMESStandardProcess(wc.getOpGid());
						p.setEndFreeName(ms.getOpname());
					}
				}
				
				
				for(AaGoods g : goodsList){
					if(g!=null && g.getGid().equals(p.getGoodsUid())){
						p.setGoodsCode(g.getGoodscode());
						p.setGoodsName(g.getGoodsname());
						p.setGoodsStandard(g.getGoodsstandard());
						p.setGoodsUnit(g.getUnitName());
					}
				}
				YmUser createUser =	cacheCtrlService.getUser(p.getCreateUser());
				YmUser modifyUser =	cacheCtrlService.getUser(p.getModifyUser());
				YmUser authUser =	cacheCtrlService.getUser(p.getAuthUser());
				if(createUser!=null){
					p.setCreateUserName(createUser.getUserName());	
				}
				if(modifyUser!=null){
					p.setModifyUserName(modifyUser.getUserName());			
				}
				if(authUser!=null){
					p.setAuthUserName(authUser.getUserName());
				}
			}
			
			this.setRequstAttribute("data", pageBean);
			this.setRequstAttribute("keyWord", keyWord);
			this.setRequstAttribute("status", status);
			
			setRequstAttribute("lhg_self", "false");//lhgdialog参数，使之基于整个浏览器弹出
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "basepd_list";
	}
	
	/**
	 * 获取excel模板方法
	 * 2016年9月1日 上午11:23:12
	 * @author 崔旭宁
	 * 传入相应的文件名并传出数据，文件名变量为fileName
	 */
	public void getExcelFile(){
		String fileName=this.getParameter("fileName");
		InputStream in = null;
		try {
			fileName = URLDecoder.decode(fileName,"utf8");
			String filepath = getRequest().getSession().getServletContext().getRealPath("")+"/templete/"+fileName+".xlsx";
			file = new File(filepath);
			/*
			 * 下载
			 */
            // 一次读多个字节
            byte[] tempbytes = new byte[1024*1024];
            int byteread = 0;
            in = new FileInputStream(filepath);
            String file=fileName+".xlsx";//为文件加上文件名后缀
            file = new String(file.getBytes(), "ISO8859-1");//兼容火狐浏览器转码为中文文件名
            // 读入多个字节到字节数组中，byteread为一次读入的字节数
            getResponse().addHeader("Content-Disposition", "attachment;filename=\"" + file);//.replace("+","%20")+"\"" );
//            getResponse().addHeader("Content-Length", "" + file.length());
            getResponse().setContentType("application/octet-stream");

            while ((byteread = in.read(tempbytes)) != -1) {
            	getResponse().getOutputStream().write(tempbytes, 0, byteread);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (in != null) {
            try {
                in.close();
                //file.delete();
            } catch (IOException e1) {
            }
        }
		
	}
	
	/**
	 * @category 跳转新增、编辑页面
	 * 2015年5月26日 上午8:29:56 
	 * @author 朱晓陈
	 * @return
	 */
	public String toEditBaseRoute(){
		try {
			String routeId = getParameter("routeId");
			if(!CommonUtil.isNullString(routeId)){
				//如果有id，说明是编辑，查出工艺路线对象
				MesWmProcessroute route = basePDService.findBaseRoute(routeId);
				if(CommonUtil.notNullString(route.getGoodsUid())){
					AaGoods product = cacheCtrlService.getGoods(route.getGoodsUid());
					setRequstAttribute("product", product);
				}
				setRequstAttribute("route", route);
			}
			return "basepd_info";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	/**
	 * @category 保存工艺路线
	 * 2015年5月26日 上午8:28:57 
	 * @author 朱晓陈
	 */
	public void saveBaseRoute(){
		try {
			if(route !=null && !CommonUtil.isNullString(route.getGid()) ){
				//如果有id，说明是编辑
				route.setModifyUser(CommonUtil.Obj2String(getSession().get("UserId")));
				route.setModifyDate(new Date());
				basePDService.updateBaseRoute(route);
			}else{
				//新增
				route.setCreateUser(CommonUtil.Obj2String(getSession().get("UserId")));
				basePDService.insertBaseRoute(route);
			}
			responseWrite("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @category 标准工艺路线设计界面
	 * 2015年5月26日 下午3:16:39 
	 * @author 朱晓陈
	 * @return
	 */
	public String toDesignBaseRoutePage(){
		try {
			String routeId = getParameter("routeId");
			//工艺路线信息
			MesWmProcessroute route = basePDService.findBaseRoute(routeId);
			//工序属性详情 
//			String process_json = "[]";
			if(route!=null){
				AaGoods product = cacheCtrlService.getGoods(route.getGoodsUid());
				if(product!=null){
					//获取节点及属性信息，转成json
					JSONObject process_objs = basePDService.getProcessJson(routeId,product);
					
					if(CommonUtil.isNullString(route.getDesignJson())){
						// 首先查是否有子表数据，有就自动生成json，没有则输出空array
						if(process_objs.isNullObject() || process_objs.isEmpty()){
							route.setDesignJson("[]");//输出空array
						}else{
							String designJson = basePDService.getInitDesignJson(process_objs,routeId);
							route.setDesignJson(designJson);
						}
					}
					setRequstAttribute("process_objs", process_objs.toString());
				}
				setRequstAttribute("product", product);
				
			}else{
				route = new MesWmProcessroute();
				route.setDesignJson("[]");
			}
			int nodeTotal = JSONArray.fromObject(route.getDesignJson()).size();
			
			setRequstAttribute("nodeTotal", nodeTotal);
			setRequstAttribute("route", route);
			setRequstAttribute("routeName", route.getRoutname());
			setRequstAttribute("routeReadOnly", route.getState());
//			setRequstAttribute("process_json", process_json);
			return "basepd_design";
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}
	
	/**
	 * @category 工艺路线节点属性界面
	 * 2015年5月26日 下午3:16:52 
	 * @author 朱晓陈
	 * @return
	 */
	public String attributePage(){
		try {
			String routecId = this.getParameter("routecId");	//工艺路线子表(工序)id
			String process = getParameter("process");			//是否页面已有工序对象存在
			String processCode = getParameter("processCode");	//工序编号
			String noEdit = getParameter("noEdit");	//是否禁止修改，1：禁止
			/*if(!"1".equals(process)){//如果没有，从数据库读取
				
				 * 工序信息
				 
				MesWmProcessroutec routec = basePDService.finBaseRouteC(routecId);
				if(routec != null){
//					MesWmStandardprocess sp = cacheCtrlService.getMESStandardProcess(routec.getOpGid());
//					setRequstAttribute("standardProcess", sp);
				}
				setRequstAttribute("routec", routec);
				
				
				 * 上道工序转入
				 
				List<MesWmProcessRouteCPre> preList = basePDService.getPreProcessList(routecId);
				setRequstAttribute("preList", preList);
				
				 * 物料领用
				 
				
				
				setRequstAttribute("isQuery", "1");
			}*/
			//获取物料自由项
			List<AaFreeSet> freeset = basePDService.getGoodsFreeset();
			setRequstAttribute("freesetJson", JSONArray.fromObject(freeset).toString());
			setRequstAttribute("freeset", freeset);
			setRequstAttribute("routecId", routecId);
			setRequstAttribute("processCode", processCode);
			setRequstAttribute("isOrder", getParameter("isOrder"));
			setRequstAttribute("noEdit", noEdit);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "basepd_attribute";
	}
	
	/**
	 * @category 保存工艺路线的节点数据
	 * 2015年5月28日 下午1:43:57 
	 * @author 朱晓陈
	 */
	public void saveProcessData(){
		try {
 			String route_id = getParameter("flow_id");//工艺路线id
			String process_info = getParameter("process_info");//工艺路线设计信息
			String arr_updProcess = getParameter("arr_updProcess");//更新的工序，存id
			String arr_addProcess = getParameter("arr_addProcess");//新增的工序，存节点对象
			String arr_delProcess = getParameter("arr_delProcess");//删除的工序，存id
			String process_objs = getParameter("process_objs");//工序属性详情 数组
			String process_codeJson = getParameter("process_codeJson");//工序属性详情 数组
			String productId = getParameter("productId");//产成品id
			
			//现有的工序
			List<MesWmProcessroutec> processList = new ArrayList<MesWmProcessroutec>();//basePDService.getRouteCList(route_id);
			/*String[] spIds = new String[processList.size()];
			for(int i=0;i<processList.size();i++){
				spIds[i] = Constants.CACHE_MESSTANDARDPROCESS+"_"+processList.get(i).getProcessId();
			}
			List<MES_WM_StandardProcess> spList = cacheCtrlService.getMESStandardProcessList(spIds);*/
			
			String userid=CommonUtil.Obj2String(getSession().get("UserId"));
			//保存数据
			basePDService.saveProcessData(productId,processList,route_id,process_info,arr_updProcess,arr_addProcess,
					arr_delProcess,process_objs,process_codeJson,userid);
	        
			responseWrite("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @category 复制工艺路线
	 * 2016年5月27日 下午5:27:27 
	 * @author zhuxiaochen
	 */
	public void copyRoute(){
		try {
			String copyNumber = getParameter("copyNumber");//复制份数
			String routeId = getParameter("routeId");//工艺路线id
			int copyNum = CommonUtil.isNullString(copyNumber)?1:Integer.parseInt(copyNumber);
			basePDService.copyRoute(routeId,copyNum);
			responseWrite("success");
		} catch (Exception e) {
			e.printStackTrace();
			this.writeError();
		}
	}
	
	/**
	 * @category 保存BOM数据到U8
	 * 2016年5月18日 上午9:14:18 
	 * @author zhuxiaochen
	 */
	public void saveBomToU8(){
		try {
			if(Constants.INTERFACE_U890.equalsIgnoreCase(Config.INTERFACETYPE)){
				String process_objs = getParameter("process_objs");//工序属性详情 数组
				String process_info = getParameter("process_info");//工序属性详情 数组
				String productCode = getParameter("productCode");//
				List<String> msg = new ArrayList<String>();
				//保存BOM到用友U890,对需要入库的分别生成bom
//				basePDService.saveBomToU8(process_objs,process_info,productCode,msg);
			}
			
			responseWrite("success");
		} catch (Exception e) {
			e.printStackTrace();
			writeError();
		}
	}
	
	/**
	 * @category 删除标准工艺路线
	 * 2015年12月21日 下午5:18:17 
	 * @author 朱晓陈
	 */
	public void deleteBaseRoute(){
		try {
			String routeId = getParameter("routeId");
			//删除
			basePDService.deleteBaseRoute(routeId);
			responseWrite("success");
		} catch (Exception e) {
			e.printStackTrace();
			responseWrite("error");
		}
	}
	
	public void deleteStandardProcess(){
		try {
			String processId = getParameter("processId");
			//删除
			basePDService.deleteStandardProcess(processId);
			responseWrite("success");
		} catch (Exception e) {
			e.printStackTrace();
			responseWrite("error");
		}
	}
	
	public void deleteWorkCenter(){
		try {
			String workCenterId = getParameter("workCenterId");
			//删除
			basePDService.deleteWorkCenter(workCenterId);
			responseWrite("success");
		} catch (Exception e) {
			e.printStackTrace();
			responseWrite("error");
		}
	}
	
	/**
	 * @category 标准工序列表
	 * 2015年12月22日 上午10:15:34 
	 * @author 朱晓陈
	 * @return
	 */
	public String toStandardProcessList(){
		int pageIndex = getPageIndex();
		int pageSize = getPageSize();
		String keyWord = getParameter("keyWord");//搜索关键字
		String condition = CommonUtil.combQuerySql("MesWmStandardprocess.opcode,MesWmStandardprocess.opname", keyWord);
		setRequstAttribute("keyWord",keyWord);
		String orgId=getSession().get("OrgId").toString();
		String sobId=getSession().get("SobId").toString();
		condition+=" and MesWmStandardprocess.sobGid='"+sobId+"' and MesWmStandardprocess.orgGid='"+orgId+"'";
		PageBean StandardProcessList = basePDService.getStandardProcessList(pageIndex,pageSize,condition);
		setRequstAttribute("data", StandardProcessList);
		return "standardProcess_list";
	}
	
	/**
	 * @category 工作中心列表
	 * 2015年12月22日 上午10:15:34 
	 * @author 朱晓陈
	 * @return
	 */
	public String toWorkCenterList(){
		int pageIndex = getPageIndex();
		int pageSize = getPageSize();
		String keyWord = getParameter("keyWord");//搜索关键字
		String condition = CommonUtil.combQuerySql("MesAaWorkcenter.wccode,MesAaWorkcenter.wcname", keyWord);
		setRequstAttribute("keyWord",keyWord);
		String orgId=getSession().get("OrgId").toString();
		String sobId=getSession().get("SobId").toString();
		condition+=" and MesAaWorkcenter.sobGid='"+sobId+"' and MesAaWorkcenter.orgGid='"+orgId+"'";
		PageBean StandardProcessList = basePDService.getWorkCenterList(pageIndex,pageSize,condition);
		setRequstAttribute("data", StandardProcessList);
		return "workcenterlist";
	}
	
	/**
	 * @category 跳转至新增标准工序页面
	 * 2015年12月22日 上午10:20:24 
	 * @author 朱晓陈
	 * @return
	 */
	public String toAddStandardProcess(){
		String pageIndex = getParameter("pno");
		
		setRequstAttribute("pno", pageIndex);
		return "standardProcessadd";
	}
	
    public String toAddWorkcenter(){
		
		return "workcenteradd";
	}
	
	/**
	 * @category 跳转至修改标准工序页面
	 * 2015年12月22日 上午10:20:24 
	 * @author 朱晓陈
	 * @return
	 */
	public String toUpdateStandardProcess(){
		MesWmStandardprocess standardprocess = basePDService.findStandardProcess(getParameter("processId"));
		setRequstAttribute("standardprocess", standardprocess);
		setRequstAttribute("pno", getParameter("pno"));
		return "standardProcessedit";
	}
	
    public String toUpdateWorkcenter(){
		MesAaWorkcenter workcenter = basePDService.findWorkCenter(getParameter("workCenterId"));
		AaDepartment department = basePDService.findDepartment(workcenter.getDepUid());
		setRequstAttribute("workcenter", workcenter);
		setRequstAttribute("department", department);
		return "workcenteredit";
	}
	
	/**
	 * @category 保存标准工序
	 * 2015年12月22日 上午10:27:23 
	 * @author 朱晓陈
	 */
	public void saveStandardProcess(){
		try {
			MesWmStandardprocess standardprocess = new MesWmStandardprocess();
			standardprocess.setOpcode(getParameter("opcode"));
			standardprocess.setOpname(getParameter("opname"));
			standardprocess.setOrgGId(getSession().get("OrgId").toString());
			standardprocess.setSobGId(getSession().get("SobId").toString());
			standardprocess.setIsCheck(CommonUtil.isNullString(getParameter("isCheck"))?null:new Integer(getParameter("isCheck")));
			standardprocess.setStandardPrice(CommonUtil.isNullString(getParameter("standardPrice"))?null: new BigDecimal(getParameter("standardPrice")));
			standardprocess.setCheckRate(CommonUtil.isNullString(getParameter("checkRate"))?null:new BigDecimal(getParameter("checkRate")));
			standardprocess.setIsStock(CommonUtil.isNullString(getParameter("isStock"))?null:new Integer(getParameter("isStock")));
			standardprocess.setIsDelete(0);
			standardprocess.setIsMustScanMould(CommonUtil.isNullString(getParameter("isMustScanMould"))?null:new Integer(getParameter("isMustScanMould")));
			boolean suc = basePDService.saveStandardProcess(standardprocess);
			if(suc){
				getResponse().getWriter().write("success");
			}else{
				getResponse().getWriter().write("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void saveWorkCenter(){
		try {
			MesAaWorkcenter workcenter = new MesAaWorkcenter();
			workcenter.setWccode(getParameter("wccode"));
			workcenter.setWcname(getParameter("wcname"));
			workcenter.setDepUid(getParameter("depUid"));
			workcenter.setSobgid(getSession().get("SobId").toString());
			workcenter.setOrggid(getSession().get("OrgId").toString());
			workcenter.setIsDelete(0);
			boolean suc = basePDService.saveWorkCenter(workcenter);
			if(suc){
				getResponse().getWriter().write("success");
			}else{
				getResponse().getWriter().write("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void updateStandardProcess(){
		try {
			MesWmStandardprocess standardprocess = new MesWmStandardprocess();
			standardprocess.setGid(getParameter("processid"));
			standardprocess.setOpcode(getParameter("opcode"));
			standardprocess.setOpname(getParameter("opname"));
			standardprocess.setOrgGId(getSession().get("OrgId").toString());
			standardprocess.setSobGId(getSession().get("SobId").toString());
			standardprocess.setIsCheck(CommonUtil.isNullString(getParameter("isCheck"))?null:new Integer(getParameter("isCheck")));
			standardprocess.setStandardPrice(CommonUtil.isNullString(getParameter("standardPrice"))?null: new BigDecimal(getParameter("standardPrice")));
			standardprocess.setCheckRate(CommonUtil.isNullString(getParameter("checkRate"))?null:new BigDecimal(getParameter("checkRate")));
			standardprocess.setIsStock(CommonUtil.isNullString(getParameter("isStock"))?null:new Integer(getParameter("isStock")));
			standardprocess.setIsMustScanMould(CommonUtil.isNullString(getParameter("isMustScanMould"))?null:new Integer(getParameter("isMustScanMould")));
			boolean suc = basePDService.updateStandardProcess(standardprocess);
			if(suc){
				getResponse().getWriter().write("success");
			}else{
				getResponse().getWriter().write("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void updateWorkCenter(){
		try {
			MesAaWorkcenter workcenter = new MesAaWorkcenter();
			workcenter.setGid(getParameter("workcenterid"));
			workcenter.setWccode(getParameter("wccode"));
			workcenter.setWcname(getParameter("wcname"));
			workcenter.setDepUid(getParameter("depUid"));
			workcenter.setSobgid(getSession().get("SobId").toString());
			workcenter.setOrggid(getSession().get("OrgId").toString());
			workcenter.setIsDelete(0);
			boolean suc = basePDService.updateWorkCenter(workcenter);
			if(suc){
				getResponse().getWriter().write("success");
			}else{
				getResponse().getWriter().write("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @category 跳转到设置工艺路线
	 * 2016年4月15日 下午4:20:14 
	 * @author zhuxiaochen
	 */
	public String toSetRouteInfo(){
		try {
			String routeId = getParameter("routeId");
			MesWmProcessroute baseRoute = basePDService.findBaseRoute(routeId);
			AaGoods product = null ;
			if(CommonUtil.notNullString(baseRoute.getGoodsUid())){
				product = cacheCtrlService.getGoods(baseRoute.getGoodsUid());
			}
			setRequstAttribute("product", product);
			setRequstAttribute("baseRoute", baseRoute);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "basepd_setRoute";
	}
	
	public String getselectdep(){
		try {
			String condition = "";	
			List bean = basePDService.getDepList(condition);
			Map m=new HashMap();
			m.put("gid", null);
			m.put("depParentUid", "-1");
			m.put("depName", "部门树");
			bean.add(m);
			getRequest().setAttribute("depTree", JSONArray.fromObject(bean).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "selectdepTree";
	}
	
	/**
	 * @category 跳转到导入工艺路线页面
	 * 2016年5月30日 下午4:40:12 
	 * @author zhuxiaochen
	 * @return
	 */
	public String toImportRoutePage(){
		return "basepd_importRoute";
	}
	
	/**
	 * @category 导入工艺
	 * 2016年5月31日 上午9:09:19 
	 * @author zhuxiaochen
	 */
	public void importRoute(){ 
		try {
			if(file!=null){
				String type = getParameter("type");
				basePDService.importRoute(file,Integer.parseInt(type));
				responseWrite("success",true);
			}else{
				responseWrite("empty",true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseWrite("error",true);
		}
	}
	
	/**
	 * @category 获取u8的bas_part
	 * 2016年7月7日 上午8:55:49 
	 * @author zhuxiaochen
	 */
	public void getGoodsBasPart(){
		try {
			String invCode = getParameter("goodsCode");
			String elementId = getParameter("elementId");
			String free1 = getParameter("free1");
			Map rspMap = new HashMap();
			rspMap.put("elementId", elementId);
			rspMap.put("free1",  URLDecoder.decode(URLDecoder.decode(free1,"UTF-8"),"UTF-8"));
			if(Constants.INTERFACE_U890.equalsIgnoreCase(Config.INTERFACETYPE)){
				String bas_part_str =new String(Submit.sendPostRequest("invCodes="+Base64.getBase64(invCode), "http://"+Config.INTERFACEADDRESS+"/u890/wareHouse_getBasPartList.emi")) ;//请求并得到返回值
				List<BasPart> basPartList = new ArrayList<BasPart>();
				JSONObject rsp = EmiJsonObj.fromObject(bas_part_str);
				if("1".equals(rsp.getString("success"))){
					basPartList = (List<BasPart>) EmiJsonArray.toCollection(rsp.getJSONArray("data"),BasPart.class);
				}
				rspMap.put("success", "1");
				rspMap.put("data", basPartList);
			}else{
				rspMap.put("success", "2");//不是u890就不处理
			}
			
			responseWrite(EmiJsonObj.fromObject(rspMap).toString());
		} catch (Exception e) {
			e.printStackTrace();
			writeError();
		}
	}
	
	/**
	 * @category 审核工艺路线
	 * 2016年7月7日 上午8:56:31 
	 * @author zhuxiaochen
	 */
	public void auditRoute(){
		try {
			String routeId = getParameter("routeId");
			String authUserId = CommonUtil.Obj2String(getSession().get("UserId"));
			List<String> msg = basePDService.auditRoute(routeId,authUserId);
			Map res = new HashMap();
			res.put("success", msg.size()>0?"0":"1");
			res.put("msg", msg);
			responseWrite(JSONObject.fromObject(res).toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.writeError();
		}
	}
	
	public void cancelAuditRoute(){
		try {
			String routeId = getParameter("routeId");
			basePDService.cancelAuditRoute(routeId);
			responseWrite("success");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
