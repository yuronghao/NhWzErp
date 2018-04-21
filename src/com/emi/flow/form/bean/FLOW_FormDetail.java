package com.emi.flow.form.bean;

import java.io.Serializable;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 流程-表单详情
 */
@EmiTable(name = "FLOW_FormDetail")
public class FLOW_FormDetail implements Serializable{
	private static final long serialVersionUID = -7517230021524549142L;
	
	@EmiColumn(name = "pk", increment = true)
	private Integer pk;					//自增长主键
	
	@EmiColumn(name = "gid", ID = true)
	private String gid;					//uuid
	
	@EmiColumn(name = "formId")
	private String formId;				//表单主表id
	
	@EmiColumn(name = "elementName")
	private String elementName;			//表单元素名称
	
	@EmiColumn(name = "elementType")
	private String elementType;			//表单元素类型
	
	@EmiColumn(name = "columnName")
	private String columnName;			//字段名称
	
	@EmiColumn(name = "description")
	private String description;			//描述
	
	@EmiColumn(name = "defaultValue")
	private String defaultValue;		//默认值
	
	@EmiColumn(name = "defaultType")
	private Integer defaultType;			//默认值类型 0：无  1:手动填写 2:系统自动值 3：策略生成
	
	@EmiColumn(name = "enumId")
	private String enumId;				//引用枚举id
	
	@EmiColumn(name = "dataFormat")
	private String dataFormat;			//数据格式(时间格式：yyyy-MM-dd HH:mm:ss等；)
	
	@EmiColumn(name = "isRequired")
	private Integer isRequired;			//是否必填  0：不必填  1：必填
	
	@EmiColumn(name = "readOnly")
	private Integer readOnly;		//是否只读 0：否  1：只读
	
	@EmiColumn(name = "listShow")
	private Integer listShow;		//在查询列表上是否显示 0：不显示  1：显示
	
	@EmiColumn(name = "showIndex")
	private Integer showIndex;			//显示顺序
	
	@EmiColumn(name = "listenerName")
	private String listenerName;	//监听的事件名称
	
	@EmiColumn(name = "callback")
	private String callback;		//监听执行的方法
	

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

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getElementName() {
		return elementName;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Integer getDefaultType() {
		return defaultType;
	}

	public void setDefaultType(Integer defaultType) {
		this.defaultType = defaultType;
	}

	public String getEnumId() {
		return enumId;
	}

	public void setEnumId(String enumId) {
		this.enumId = enumId;
	}

	public String getDataFormat() {
		return dataFormat;
	}

	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}

	public Integer getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(Integer isRequired) {
		this.isRequired = isRequired;
	}

	public String getElementType() {
		return elementType;
	}

	public void setElementType(String elementType) {
		this.elementType = elementType;
	}

	public Integer getShowIndex() {
		return showIndex;
	}

	public void setShowIndex(Integer showIndex) {
		this.showIndex = showIndex;
	}

	public String getListenerName() {
		return listenerName;
	}

	public void setListenerName(String listenerName) {
		this.listenerName = listenerName;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public Integer getReadOnly() {
		return readOnly;
	}

	public void setReadOnly(Integer readOnly) {
		this.readOnly = readOnly;
	}

	public Integer getListShow() {
		return listShow;
	}

	public void setListShow(Integer listShow) {
		this.listShow = listShow;
	}
	
	
}
