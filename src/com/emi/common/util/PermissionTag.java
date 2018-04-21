package com.emi.common.util;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts2.ServletActionContext;


import com.emi.rm.bean.RM_Right;
import com.emi.rm.bean.RM_Role;
import com.opensymphony.xwork2.ActionContext;

public class PermissionTag extends TagSupport {
	private String rightCode;
	
	
	public String getRightCode() {
		return rightCode;
	}

	public void setRightCode(String rightCode) {
		this.rightCode = rightCode;
	}

	@Override
	public int doStartTag() throws JspException {
		boolean result = false;
		List privileges = (List) ((HttpServletRequest)pageContext.getRequest()).getSession().getAttribute("privileges");
		RM_Right privilege = new RM_Right(this.rightCode);
				if(privileges.contains(privilege)){
					result = true;
				} 
		return result? EVAL_BODY_INCLUDE : SKIP_BODY;

	}
	
}
