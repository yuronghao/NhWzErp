package com.emi.flow.form.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 流程-表单
 */
@EmiTable(name = "FLOW_Form")
public class FLOW_Form implements Serializable{
	private static final long serialVersionUID = 7401068829417111940L;
	
	@EmiColumn(name = "pk", increment = true)
	private Integer pk;					//自增长主键
	
	@EmiColumn(name = "gid", ID = true)
	private String gid;					//uuid
	
	@EmiColumn(name = "formName")
	private String formName;			//表单名称
	
	@EmiColumn(name = "tableId")
	private String tableId;				//数据表ID
	
	@EmiColumn(name = "childTableId")
	private String childTableId;		//数据表子表ID
	
	@EmiColumn(name = "titleCfg")
	private String titleCfg;			//标题配置情况
	
	@EmiColumn(name = "formHtml")
	private String formHtml;			//表单HTML内容
	
	@EmiColumn(name = "description")
	private String description;			//描述
	
	@EmiColumn(name = "createUserId")
	private String createUserId;		//创建人
	
	@EmiColumn(name = "createTime")
	private Timestamp createTime;		//创建时间
	
	@EmiColumn(name = "version")
	private Integer version;			//版本
	
	@EmiColumn(name = "scripts")
	private String scripts;			//js脚本
	
	@EmiColumn(name = "isPageForm")
	private Integer isPageForm;			//表单是否直接调用页面
	
	@EmiColumn(name = "pageUrl")
	private String pageUrl;			//表单调用的页面url
	
	@EmiColumn(name = "dataFromAction")
	private Integer dataFromAction;			//是否数据来源调用系统action 1：是
	
	@EmiColumn(name = "actionName")
	private String actionName;			//action名称（strutsAction名.方法名）
	
	@EmiColumn(name = "isSys")
	private Integer isSys;				//是否是系统预置表单 1：是

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

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getChildTableId() {
		return childTableId;
	}

	public void setChildTableId(String childTableId) {
		this.childTableId = childTableId;
	}


	public String getTitleCfg() {
		return titleCfg;
	}

	public void setTitleCfg(String titleCfg) {
		this.titleCfg = titleCfg;
	}

	public String getFormHtml() {
		return formHtml;
	}

	public void setFormHtml(String formHtml) {
		this.formHtml = formHtml;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getScripts() {
		return scripts;
	}

	public void setScripts(String scripts) {
		this.scripts = scripts;
	}
	public Integer getIsPageForm() {
		return isPageForm;
	}

	public void setIsPageForm(Integer isPageForm) {
		this.isPageForm = isPageForm;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public Integer getDataFromAction() {
		return dataFromAction;
	}

	public void setDataFromAction(Integer dataFromAction) {
		this.dataFromAction = dataFromAction;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public Integer getIsSys() {
		return isSys;
	}

	public void setIsSys(Integer isSys) {
		this.isSys = isSys;
	}
	
}
