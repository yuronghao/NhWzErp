package com.emi.sys.file.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 附件
 */
@EmiTable(name = "YM_File")
public class YM_File implements Serializable{
	private static final long serialVersionUID = -2067855570343778252L;

	@EmiColumn(name = "pk", increment = true)
	private Integer pk;					//自增长主键
	
	@EmiColumn(name = "gid", ID = true)
	private String gid;					//uuid
	
	@EmiColumn(name = "fileSize")
	private Long fileSize;			//文件大小
	
	@EmiColumn(name = "fileName")
	private String fileName;			//文件名称
	
	@EmiColumn(name = "fileType")
	private String fileType;			//文件类型
	
	@EmiColumn(name = "filePath")
	private String filePath;			//路径
	
	@EmiColumn(name = "uploadTime")
	private Timestamp uploadTime;		//上传时间
	
	
	public YM_File() {
		// TODO Auto-generated constructor stub
	}
	
	
	public YM_File(Long fileSize, String fileName, String fileType,
			String filePath) {
		super();
		this.fileSize = fileSize;
		this.fileName = fileName;
		this.fileType = fileType;
		this.filePath = filePath;
		this.uploadTime = new Timestamp(System.currentTimeMillis());
	}


	public Integer getPk() {
		return pk;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}


	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public String getFileType() {
		return fileType;
	}


	public void setFileType(String fileType) {
		this.fileType = fileType;
	}


	public String getFilePath() {
		return filePath;
	}


	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


	public Timestamp getUploadTime() {
		return uploadTime;
	}


	public void setUploadTime(Timestamp uploadTime) {
		this.uploadTime = uploadTime;
	}


}
