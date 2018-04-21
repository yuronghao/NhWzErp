package com.emi.sys.file.bean;

import java.io.Serializable;

import javax.persistence.GenerationType;

import com.emi.common.util.Constants;
import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name = "YM_Record_Doc" )
public class Record_doc implements Serializable{
	private static final long serialVersionUID = -1784055787937594862L;
	@EmiColumn(name = "id", increment = true)
	private Integer id;					//自增长主键
	
	@EmiColumn(ID = true, name = "docid")
	private String docid;             //附件编号
	
	@EmiColumn(name = "folderid")
	private String folderid;          //文件夹编码
	
	@EmiColumn(name = "docname")
	private String docname;          //附件名称
	
	@EmiColumn(name = "tablename")
	private String tablename;          //记录表名
	
	@EmiColumn(name = "recordid")
	private String recordid;     //记录编号
	
	@EmiColumn(name = "url")
	private String url;     //链接（或地址）

	public String getDocid() {
		return docid;
	}

	public void setDocid(String docid) {
		this.docid = docid;
	}

	public String getFolderid() {
		return folderid;
	}

	public void setFolderid(String folderid) {
		this.folderid = folderid;
	}

	public String getDocname() {
		return docname;
	}

	public void setDocname(String docname) {
		this.docname = docname;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public String getRecordid() {
		return recordid;
	}

	public void setRecordid(String recordid) {
		this.recordid = recordid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
