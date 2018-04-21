package com.emi.common.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.emi.common.dao.BaseDao;
import com.emi.common.util.CSVUtils;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.ExcelUtil;
import com.emi.rm.bean.RM_Right;
import com.emi.sys.core.format.EmiJsonObj;
import com.emi.sys.file.EmiFileOption;
import com.emi.sys.init.Config;
import com.emi.wms.bean.AaGoods;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("rawtypes")
public class BaseAction extends ActionSupport implements SessionAware{
	private static final long serialVersionUID = -5300158034452776588L;
	private Map<String,Object> session;
	private BaseDao baseDao;
	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	public void setSession(Map<String, Object> session) {
		this.session=session;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public HttpServletRequest getRequest(){
		HttpServletRequest request = ServletActionContext.getRequest();
		return request;
	}
	
	public HttpServletResponse getResponse(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		return response;
	}
	
	public JSONObject getJsonObject(){
		return getJsonObject(true);
	}
	
	public JSONArray getJsonArray(){
		return getJsonArray(true);
	}
	
	/**
	 * @category 流转jsonObject
	 * @author zhuxiaochen
	 * @param printJson
	 * @return
	 */
	public JSONObject getJsonObject(boolean printJson){
		StringBuffer sb = new StringBuffer();
		String result = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getRequest().getInputStream(),"utf-8"));
			String temp;
			while((temp=br.readLine())!=null){
				sb.append(temp);
			}
			br.close();
			result = sb.toString();
			if(printJson){
				System.out.println(result);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(result);
		return jsonObject;
	}
	
	/**
	 * @category 流转jsonArray
	 * @author zhuxiaochen
	 * @param printJson
	 * @return
	 */
	public JSONArray getJsonArray(boolean printJson){
		StringBuffer sb = new StringBuffer();
		String result = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getRequest().getInputStream(),"utf-8"));
			String temp;
			while((temp=br.readLine())!=null){
				sb.append(temp);
			}
			br.close();
			result = sb.toString();
			if(printJson){
				System.out.println(result);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONArray jsonA = JSONArray.fromObject(result);
		return jsonA;
	}
	
	/*
	 * 获取request参数
	 */
	public String getParameter(String key){
		String param = getRequest().getParameter(key);
		if (param != null) {
			param = param.trim();
			param = param.replaceAll("<", "&lt;");
			param = param.replaceAll(">", "&gt;");
		}
		return param;
	}
	
	/*
	 * 设置request参数
	 */
	public void setRequstAttribute(String key,Object value){
		getRequest().setAttribute(key, value);
	}
	
	public int getPageIndex(){
		int pageIndex = 1;
		String page = getRequest().getParameter("pno");
		if (!CommonUtil.isNullString(page)) {
			pageIndex = Integer.parseInt(page);
		}
		return pageIndex < 0 ? 1 : pageIndex;
	}
	
	public int getPageSize() {
		int size = Config.PAGESIZE_WEB;
		try {
			String s = getRequest().getParameter("pageSize");
			if (!CommonUtil.isNullString(s)) {
				//如果有值，存入cookie中
				size = Integer.parseInt(s);
			}else{
				
			}
			/*setRequstAttribute("pageSize", size);*/
		} catch (Exception e) {
			e.printStackTrace();
			/*setRequstAttribute("pageSize", size);*/
			return size;
		}
		return getPageSize(size);
	}
	public int getPageSize(int size) {
		return size < 1 ? 1 : size;
	}
	
	public void writeError(){
		try {
			getResponse().getWriter().write("{\"success\":\"0\"}");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	
	public void writeErrorOrSuccess(int flag,String failInfor){
		try {
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("success", flag);
			jsonObject.put("failInfor", failInfor);
			getResponse().getWriter().write(jsonObject.toString());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	
	public void writeSuccess(){
		try {
			getResponse().getWriter().write("{\"success\":\"1\"}");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void responseWrite(String str){
		responseWrite(str, false);
	}
	
	public void responseWrite(String str,boolean fmt){
		try {
			if(fmt){
				getResponse().setContentType("text/html; charset=utf-8");
			}
			getResponse().getWriter().write(str);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * @category 下载文件
	 * @param fileName
	 * @param filePath
	 * @param inStream
	 * @param sos
	 * @throws IOException
	 */
	public void outputFile(String fileName,String filePath,InputStream inStream,ServletOutputStream sos) throws IOException{
		sos = getResponse().getOutputStream();
		String filename = URLEncoder.encode(fileName, "UTF-8");
		getResponse().setCharacterEncoding("UTF-8");
		
//		if (online) { // 在线打开方式
//            URL u = new URL("file:///" + filePath);
//            getResponse().setContentType(u.openConnection().getContentType());
//            getResponse().setHeader("Content-Disposition", "inline; filename=" + fileName);
//            // 文件名应该编码成UTF-8
//        } else { // 纯下载方式
//        	getResponse().setContentType("application/x-msdownload");
        	getResponse().setContentType("application/octet-stream");
        	getResponse().setHeader("Content-Disposition","attachment;filename=" + filename);
//        }
		// 循环取出流中的数据
		byte[] b = new byte[1024 * 8];
		int len;

		while ((len = inStream.read(b)) > 0)
			sos.write(b, 0, len);
	}
	
	public void forbideMsg(){
		try {
			getResponse().getWriter().write("document.write('<script>alert(\"没有权限\");</script>')");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 校验功能权限
	 * 2015年5月20日 上午10:59:28 
	 * @param funCode 功能编码
	 * @param f_menuCode 所属模块(菜单)编码
	 */
	public boolean checkFunRight(String funCode,String menuCode){
		boolean flag = false;
		try {
			if("1".equals(getSession().get("isAdmin").toString())){
				flag = true;//是管理员 跳过
			}else{
				if(CommonUtil.notNullString(funCode)){
					//有权限的功能
					Map<String,List<RM_Right>> funMap = (Map<String,List<RM_Right>>) getSession().get("rightFunMap");
					if(CommonUtil.notNullString(menuCode)){
						//如果传了模块代码，则在指定模块中搜索
						List<RM_Right> rights = funMap.get(menuCode);
						if(rights!=null){
							for(RM_Right r : rights){
								if(funCode.equals(r.getRightCode())){
									//匹配到权限
									flag = true;
								}
							}
						}
					}else{
						//未传模块代码，全部搜索
						for (String key : funMap.keySet()) {  
							List<RM_Right> rights = funMap.get(key);
							for(RM_Right r : rights){
								if(funCode.equals(r.getRightCode())){
									//匹配到权限
									flag = true;
								}
							}
				        }   
					}
				}
				if(!flag){
					//没有权限，限制其操作
//					getResponse().getWriter().write("没有权限");
//					throw new Exception("没有权限");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * 分页上的导出数据 csv格式，逗号隔开
	 * column_cfg  格式：{"name:姓名","address:联系地址"}
	 * 2015年8月20日 下午5:14:06
	 * @param 数据
	 * @param 导出的文件名
	 * @return
	 */
	public String exportData(List<Object> dataList,String exportName){
		InputStream in = null;
		File file = null;
		exportName += ".csv";
		try {
			String column_str = getParameter("emi_export_column");
			JSONObject column_cfg_json = JSONObject.fromObject(column_str);
			
			List<String> stringList = new ArrayList<String>();
			List<String> col_code = new ArrayList<String>();//列的英文字段名
			String col_name = "";//列中文描述,用逗号隔开（即表头）
			
			Iterator it = column_cfg_json.keys();  
			while (it.hasNext()) {  
                String key = (String) it.next();  
                String value = column_cfg_json.getString(key);
                col_code.add(key);
                col_name += value+",";
            }  
			col_name = CommonUtil.cutLastString(col_name, ",");
			//将List<Object>转成List<String>
			//1、先加表头
			stringList.add(col_name);
			//2、数据
			JSONObject json = null;
			for(Object obj : dataList){
	//			if(obj instanceof )
				json = EmiJsonObj.fromObject(obj);
				String data = "";
				for(String code : col_code){
					data += CommonUtil.Obj2String("\""+json.get(code))+"\",";
				}
				data = CommonUtil.cutLastString(data, ",");
				stringList.add(data);
			}
			String filepath = EmiFileOption.getBasePath()+UUID.randomUUID().toString()+".csv";
			/*
			 * 导出到磁盘
			 */
			CSVUtils.exportCsv(new File(filepath), stringList);
			/*
			 * 下载
			 */
			file = new File(filepath);
            // 一次读多个字节
            byte[] tempbytes = new byte[1024*1024];
            int byteread = 0;
            in = new FileInputStream(filepath);
            // 读入多个字节到字节数组中，byteread为一次读入的字节数
            getResponse().addHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(exportName, "UTF-8").replace("+","%20")+"\"" );
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
                file.delete();
            } catch (IOException e1) {
            }
        }
		return null;
	}
	
	/**
	 * 导出数据 excel格式
	 * @param column_cfg  格式：{"name:姓名","address:联系地址"}
	 * @param dataList 数据
	 * @param exportName 导出的文件名
	 * @param title 数据标题
	 * @param column_ext_type 自定义数据类型（默认字符串）"{'disNum':"+ExcelUtil.DATA_TYPE_NUMERIC+",'reportOkNum':"+ExcelUtil.DATA_TYPE_NUMERIC+"}"
	 * @return
	 */
	public String exportExcel(String column_str,List<Object> dataList,String exportName,String title){
		return exportExcel(column_str, dataList, exportName, title,null);
	}
	public String exportExcel(String column_str,List<Object> dataList,String exportName,String title,String column_ext_type){
		InputStream in = null;
		File file = null;
		exportName += ".xls";
		try {
			JSONObject column_cfg_json = JSONObject.fromObject(column_str);
			JSONObject column_type_json = JSONObject.fromObject(column_ext_type);
			
			List<Object[]> exportList = new ArrayList<Object[]>();
			List<String> col_code = new ArrayList<String>();//列的英文字段名
			List<String> col_name = new ArrayList<String>();//列中文描述（即表头）
			
			Iterator it = column_cfg_json.keys();  
			while (it.hasNext()) {  
                String key = (String) it.next();  
                String value = column_cfg_json.getString(key);
                col_code.add(key);
                col_name.add(value);
            }  
			//将List<Object>转成List<String>
			//2、数据
			JSONObject json = null;
			for(Object obj : dataList){
	//			if(obj instanceof )
				json = EmiJsonObj.fromObject(obj);
				Object[] o_list = new Object[col_code.size()];
				int i = 0;
				for(String code : col_code){
					Object data = json.get(code);
					if(data==null || (data+"").equals("null")){
						o_list[i] = "";
					}else{
						o_list[i] = data;
					}
					
					i++;
				}
				exportList.add(o_list);
			}
			//3、单元格类型
			int[] types = new int[col_code.size()];
			int i = 0;
			for(String code : col_code){
				if(column_type_json.get(code)!=null){
					types[i] = Integer.parseInt(column_type_json.get(code).toString());
				}else{
					types[i] = HSSFCell.CELL_TYPE_STRING;
				}
				i++;
			}
			String filepath = EmiFileOption.getBasePath()+UUID.randomUUID().toString()+".xls";
			file = new File(filepath);
			/*
			 * 导出到磁盘
			 */
			ExcelUtil.export(file, title, col_name, exportList,types);
			/*
			 * 下载
			 */
            // 一次读多个字节
            byte[] tempbytes = new byte[1024*1024];
            int byteread = 0;
            in = new FileInputStream(filepath);
            // 读入多个字节到字节数组中，byteread为一次读入的字节数
            getResponse().addHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(exportName, "UTF-8").replace("+","%20")+"\"" );
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
                file.delete();
            } catch (IOException e1) {
            }
        }
		return null;
	}
	
	public boolean isEmiExport(){
		if("1".equals(getParameter("exportData"))){
			return true;
		}
		return false;
	}
	
	/**
	 * 获取下一个序列的值，序列的值也随之改变成该值 2015年6月29日 下午5:19:20
	 *
	 * @return
	 */
	public String nextSequenceVal(String seq_name) {
		return baseDao.nextSeqVal(seq_name);
	}
	
	
}
