package com.emi.common.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import net.sf.json.JSONObject;

import com.emi.common.service.CommonFileUploadService;
import com.emi.common.util.Constants;
import com.emi.common.util.DateUtil;
import com.emi.common.util.FileUploadUtils;
import com.emi.sys.core.format.EmiJsonObj;
import com.emi.sys.file.EmiFileOption;
import com.emi.sys.file.bean.Record_doc;


/**
 * 文件上传插件
 * 2015年6月24日11:06:30
 */
public class CommonFileUploadAction extends BaseAction {
	private static final long serialVersionUID = 5873260984216825353L;
	private CommonFileUploadService commonFileUploadService;
	private File uploadify;
	private String uploadifyFileName;
	
	private Boolean showFolder;	//是否显示左侧文件夹
	private Boolean editable;	//是否可编辑
	private String buttonText;	//按钮文字
	private String fileTypeExts;//可上传的文件类型
	private String targetId;	//页面目标元素id
	
	private String tableName;	//数据表名
	private String idColumnName;	//id字段名  
	private String recordId;	//数据记录id
	private String folderId;	//文件夹id
	private String folderName;	//文件夹名称
	
	public void setCommonFileUploadService(
			CommonFileUploadService commonFileUploadService) {this.commonFileUploadService = commonFileUploadService;}
	public File getUploadify() {return uploadify;}
	public void setUploadify(File uploadify) {this.uploadify = uploadify;}
	public String getUploadifyFileName() {return uploadifyFileName;}
	public void setUploadifyFileName(String uploadifyFileName) {this.uploadifyFileName = uploadifyFileName;}
	public String getTableName() {return tableName;}
	public void setTableName(String tableName) {this.tableName = tableName;}
	public String getRecordId() {return recordId;}
	public void setRecordId(String recordId) {this.recordId = recordId;}
	public String getButtonText() {return buttonText;}
	public void setButtonText(String buttonText) {this.buttonText = buttonText;}
	public String getFolderId() {return folderId;}
	public void setFolderId(String folderId) {this.folderId = folderId;}
	public Boolean getShowFolder() {return showFolder;}
	public void setShowFolder(Boolean showFolder) {this.showFolder = showFolder;}
	public Boolean getEditable() {return editable;}
	public void setEditable(Boolean editable) {this.editable = editable;}
	public String getFolderName() {return folderName;}
	public void setFolderName(String folderName) {this.folderName = folderName;}
	public String getIdColumnName() {return idColumnName;}
	public void setIdColumnName(String idColumnName) {this.idColumnName = idColumnName;}
	public String getFileTypeExts() {return fileTypeExts;}
	public void setFileTypeExts(String fileTypeExts) {this.fileTypeExts = fileTypeExts;}
	public String getTargetId() {return targetId;}
	public void setTargetId(String targetId) {this.targetId = targetId;}
	/**
	 * 上传附件
	 * 2015年6月24日 下午5:08:18
	 */
	public void upload(){
		try {
			String suffix = FileUploadUtils.getExtension(uploadifyFileName);	//后缀名
			String realfileName = UUID.randomUUID().toString()+"."+suffix;		//生成随机文件名
			String uploadPath = EmiFileOption.createBaseFolder(EmiFileOption.getBasePath());	//文件所在文件夹
			String realpath = uploadPath + realfileName;	//真实文件路径
			File realFile = new File(realpath);
			if(!realFile.exists()){
				realFile.createNewFile();
			}
			//保存到磁盘
			boolean suc = FileUploadUtils.upload(realfileName, uploadPath, uploadify);
			
			//将结果返回
			JSONObject json = new JSONObject();
			if(suc){
				String fileId = UUID.randomUUID().toString();
				//创建一个新的文件夹对象
				Record_doc doc = new Record_doc();
				doc.setDocid(fileId);
				doc.setDocname(uploadifyFileName);
				doc.setFolderid(folderId);
				doc.setRecordid(recordId);
				doc.setTablename(tableName);
				doc.setUrl(realpath);
				commonFileUploadService.addFile(doc, tableName, recordId,idColumnName);
				json.put("success", "success");
				json.put("file", EmiJsonObj.fromObject(doc));
			}else{
				json.put("success", "error");
				json.put("msg", uploadifyFileName+"上传失败");
			}
			
			responseWrite(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * @category 上传图片
	 *2016 2016年5月6日下午4:18:45
	 *void
	 *宋银海
	 */
	public void uploadPicture(){
		try {
			
			String filepath=getRequest().getSession().getServletContext().getRealPath("");
			
			Date nowDate = new Date();
			String year  = DateUtil.dateToString(nowDate, "yyyy");
			String month = DateUtil.dateToString(nowDate, "MM");
			String folder = filepath+"/notOkPic/" + year+"/"+month+"/";
			
			String fileTrueName = UUID.randomUUID().toString()+".png";
			String imageUrl = folder+fileTrueName;//图片保存路径
			
			InputStream br = getRequest().getInputStream();
			FileInputStream fs=(FileInputStream)br;
			
			FileUploadUtils.uploadForName(fileTrueName, imageUrl, fs);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		String str="E:\\入职合同\\一米科技Java源码规范.doc";
		File imgFolder = new File(str);
		imgFolder.createNewFile();
		
		FileInputStream fis=new FileInputStream(str);
//		int i=0;
//		while((i=fis.read())!=-1){
//			System.out.println(i);
//		}
		
		char a='A';
		System.out.println(a);
		byte[] bs=new byte[20*1024];
		
		int j=0;
		while((j=fis.read(bs, 0, bs.length))!=-1){
			System.out.println(j);
			System.out.println(bs);
		};
		
	}
	
	
	/**
	 * @category 安卓上传图片通用接口
	 *2016 2016年5月6日下午5:26:21
	 *void
	 *宋银海
	 */
	public void uploadPictureAndroid(){
		
		try {
			String realfileName = UUID.randomUUID().toString()+".jpg";		//生成随机文件名
			String uploadPath = EmiFileOption.createBaseFolder(EmiFileOption.getBasePath());	//文件所在文件夹
			String realpath = uploadPath + realfileName;	//真实文件路径
			
			InputStream br = getRequest().getInputStream();
			FileInputStream fs=(FileInputStream)br;
			
			//保存到磁盘
			FileUploadUtils.uploadForName(realfileName, uploadPath, fs);
			
			//将结果返回
			JSONObject json = new JSONObject();
			
//			if(suc){
//				String fileId = UUID.randomUUID().toString();
//				//创建一个新的文件夹对象
//				Record_doc doc = new Record_doc();
//				doc.setDocid(fileId);
//				doc.setDocname(uploadifyFileName);
//				doc.setFolderid(folderId);
//				doc.setRecordid(recordId);
//				doc.setTablename(tableName);
//				doc.setUrl(realpath);
//				commonFileUploadService.addFile(doc, tableName, recordId,idColumnName);
//				json.put("success", "success");
//				json.put("file", EmiJsonObj.fromObject(doc));
//			}else{
//				json.put("success", "error");
//				json.put("msg", uploadifyFileName+"上传失败");
//			}
			
			responseWrite(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * 上传的frame窗口
	 * 2015年6月24日 下午5:08:30
	 */
	public String showFrame(){
		try {
			this.setRequstAttribute("height", getParameter("height"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "uploadFrame";
	}
	
	/**
	 * 文件上传界面
	 * 2015年6月26日 上午10:31:28
	 * @return
	 */
	public String toUploadFile(){
		try {
//			tableName = URLDecoder.decode(getParameter("tableName")) ;
			buttonText = URLDecoder.decode(buttonText,"UTF-8");
			
			//已有的附件列表
//			List<Record_doc> files = commonFileUploadService.getFilesInFolder(folderId);
			
//			setRequstAttribute("files", files);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "uploadFile";
	}
	
	/**
	 * 删除文件
	 * 2015年6月30日 下午8:11:42
	 */
	public void deleteFile(){
		try {
			String fileId = getParameter("fileId");	//所选文件id
			String tableName = getParameter("tableName");	
			String recordId = getParameter("recordId");	
			String idColumnName = getParameter("idColumnName");	
			//删除
			commonFileUploadService.deleteFile(fileId,recordId,tableName,idColumnName);
			
			//将结果返回
			JSONObject json = new JSONObject();
			json.put("success", "success");
			responseWrite(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @category 下载 
	 * 2015年12月4日 上午9:27:13 
	 * @author 朱晓陈
	 */
	public void download(){
		InputStream in = null;
		try {
			String fileId = getParameter("fileId");	//所选文件id
			Record_doc doc = commonFileUploadService.findDoc(fileId);
			File file = new File(doc.getUrl());
			if(!file.exists()){
				getResponse().setContentType("text/html;charset=UTF-8");
				getResponse().getWriter().write("<font color='red'>文件不存在</font><br><a href='javascript:history.go(-1)'>返回</a>");
				return;
			} 
            // 一次读多个字节
            byte[] tempbytes = new byte[1024*1024];
            int byteread = 0;
            in = new FileInputStream(doc.getUrl());
            // 读入多个字节到字节数组中，byteread为一次读入的字节数
            getResponse().addHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(doc.getDocname(), "UTF-8").replace("+","%20")+"\"" );
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
            } catch (IOException e1) {
            }
        }
	}
	
	/**
	 * 获取excel模板方法（模板以文件形式存储于templete文件夹当中）
	 * 2016年9月1日 上午11:23:12
	 * @author 崔旭宁
	 * 传入相应的xlsx文件名并传出对应xlsx文件，文件名变量为fileName
	 */
	public void getExcelFile(){
		String fileName=this.getParameter("fileName");
		InputStream in = null;
		try {
			fileName = URLDecoder.decode(fileName,"utf8");
			String filepath = getRequest().getSession().getServletContext().getRealPath("")+"/templete/"+fileName+".xlsx";
			uploadify = new File(filepath);
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
	
}
