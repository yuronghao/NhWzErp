package com.emi.rm.bean;

import java.io.Serializable;
import java.util.List;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

/*
 * 权限
 */
@EmiTable(name = "RM_Right")
public class RM_Right implements Serializable{
	private static final long serialVersionUID = -1428245099147868149L;
	public RM_Right(){};
	public RM_Right(String rightCode){this.rightCode = rightCode;};
	public RM_Right(String gid, String rightCode, String rightName,
			String rightUrl, String superiorRightId, String superiorRightCode,
			Integer isLast, Integer levelNum, String notes, Integer functions,
			Integer isSystem, Integer hasFast, Integer rightIndex,
			String ownerSys) {
		super();
		this.gid = gid;
		this.rightCode = rightCode;
		this.rightName = rightName;
		this.rightUrl = rightUrl;
		this.superiorRightId = superiorRightId;
		this.superiorRightCode = superiorRightCode;
		this.isLast = isLast;
		this.levelNum = levelNum;
		this.notes = notes;
		this.functions = functions;
		this.isSystem = isSystem;
		this.hasFast = hasFast;
		this.rightIndex = rightIndex;
		this.ownerSys = ownerSys;
	}
	@EmiColumn(name = "gid", ID = true)
	private String gid;
	
	@EmiColumn(name = "rightCode")
	private String rightCode;			//权限编码
	
	@EmiColumn(name = "rightName")
	private String rightName;			//权限名称
	
	@EmiColumn(name = "rightUrl")
	private String rightUrl;			//权限url
	
	@EmiColumn(name = "superiorRightId")
	private String superiorRightId;		//父权限id
	
	@EmiColumn(name = "superiorRightCode")
	private String superiorRightCode;	//父权限编码
	
	@EmiColumn(name = "isLast")
	private Integer isLast ;			//是否末级 0不是末级 1是末级
	
	@EmiColumn(name = "levelNum")
	private Integer levelNum;			//级次
	
	@EmiColumn(name = "notes")
	private String notes;				//备注
	
	@EmiColumn(name = "functions")
	private Integer functions;			//含有的功能 权值的和 1：查看 2：新增 4：修改 8：删除 (使用位运算进行判断含有哪些功能) 16:表单自定义
	
	@EmiColumn(name = "isSystem")
	private Integer isSystem;			//是否是系统权限 1系统权限 0不是系统权限(即企业权限) 2企业固定权限
	
	@EmiColumn(name = "hasFast")
	private Integer hasFast;			//是否有快捷菜单 0无 1有
	
	@EmiColumn(name = "rightIndex")
	private Integer rightIndex;			//排序
	
	@EmiColumn(name = "ownerSys")
	private String ownerSys;			//所属系统
	
	@EmiColumn(name = "isShow")
	private Integer isShow;				//是否显示
	
	@EmiColumn(name = "rightType")
	private Integer rightType;			//权限类型 0：菜单 1：按钮 （只针对于演示系统使用）
	
	@EmiColumn(name = "buttonCss")
	private String buttonCss;			//按钮样式
	
	@EmiColumn(name = "reqType")
	private Integer reqType;			//请求类型

	@EmiColumn(name = "imgUrl")
	private String imgUrl;				//logo图片地址
	
	@EmiColumn(name = "quoteRightId")
	private String quoteRightId;		//引用系统菜单的id
	
	@EmiColumn(name = "subGenerate")
	private Integer subGenerate;			//子菜单的生成方式  0：常规菜单   1：根据任务流动态生成
	
	@EmiColumn(name = "isDelete")
	private Integer isDelete;		//是否删除 0：否 1：是
	//用户或角色查出的权限对应的功能权值
	private Integer useFuns;	
	
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getRightCode() {
		return rightCode;
	}
	public void setRightCode(String rightCode) {
		this.rightCode = rightCode;
	}
	public String getSuperiorRightId() {
		return superiorRightId;
	}
	public void setSuperiorRightId(String superiorRightId) {
		this.superiorRightId = superiorRightId;
	}
	public String getSuperiorRightCode() {
		return superiorRightCode;
	}
	public void setSuperiorRightCode(String superiorRightCode) {
		this.superiorRightCode = superiorRightCode;
	}
	public Integer getIsLast() {
		return isLast;
	}
	public void setIsLast(Integer isLast) {
		this.isLast = isLast;
	}
	public Integer getLevelNum() {
		return levelNum;
	}
	public void setLevelNum(Integer levelNum) {
		this.levelNum = levelNum;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getRightName() {
		return rightName;
	}
	public void setRightName(String rightName) {
		this.rightName = rightName;
	}
	public String getRightUrl() {
		return rightUrl;
	}
	public void setRightUrl(String rightUrl) {
		this.rightUrl = rightUrl;
	}
	public Integer getFunctions() {
		return functions;
	}
	public void setFunctions(Integer functions) {
		this.functions = functions;
	}
	public Integer getIsSystem() {
		return isSystem;
	}
	public void setIsSystem(Integer isSystem) {
		this.isSystem = isSystem;
	}
	public Integer getHasFast() {
		return hasFast;
	}
	public void setHasFast(Integer hasFast) {
		this.hasFast = hasFast;
	}
	public Integer getRightIndex() {
		return rightIndex;
	}
	public void setRightIndex(Integer rightIndex) {
		this.rightIndex = rightIndex;
	}
	public String getOwnerSys() {
		return ownerSys;
	}
	public void setOwnerSys(String ownerSys) {
		this.ownerSys = ownerSys;
	}
	public Integer getUseFuns() {
		return useFuns;
	}
	public void setUseFuns(Integer useFuns) {
		this.useFuns = useFuns;
	}
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	public Integer getRightType() {
		return rightType;
	}
	public void setRightType(Integer rightType) {
		this.rightType = rightType;
	}
	public String getButtonCss() {
		return buttonCss;
	}
	public void setButtonCss(String buttonCss) {
		this.buttonCss = buttonCss;
	}
	public Integer getReqType() {
		return reqType;
	}
	public void setReqType(Integer reqType) {
		this.reqType = reqType;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getQuoteRightId() {
		return quoteRightId;
	}
	public void setQuoteRightId(String quoteRightId) {
		this.quoteRightId = quoteRightId;
	}
	public Integer getSubGenerate() {
		return subGenerate;
	}
	public void setSubGenerate(Integer subGenerate) {
		this.subGenerate = subGenerate;
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((rightCode == null) ? 0 : rightCode.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RM_Right other = (RM_Right) obj;
		if (rightCode == null) {
			if (other.rightCode != null)
				return false;
		} else if (!rightCode.equals(other.rightCode))
			return false;
		return true;
	}
	
}
