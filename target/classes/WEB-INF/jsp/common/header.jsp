<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- JSTL 标签 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.privilege.cn/privilege" prefix="privilege"%>
<!-- struts2 标签 -->
<%@ taglib prefix="s" uri="/struts-tags" %>
<%String contextPath = request.getContextPath();%> 

<!-- 导入常量包 -->
<%@ page import="com.emi.common.util.Constants"%>
<!-- 简化书写 -->
<c:set var="dateFormatDisplay">yyyy.MM.dd</c:set>
<c:set var="timeFormatDisplay">yyyy-MM-dd HH:mm:ss</c:set>
<c:set var="ctx" value="<%=contextPath %>"/>
<c:if test="${lhg_self == null}"><c:set var="lhg_self" value="true"/> </c:if>
<script type="text/javascript" src="<%=contextPath %>/scripts/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="<%=contextPath %>/scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=contextPath %>/scripts/lhgdialog/lhgdialog.js?self=${lhg_self }"></script>

<!-- 初始化变量 -->
<script type="text/javascript">
	var context = "<%=contextPath %>";
	var sys_isAdmin = "${sessionScope.isAdmin}"; //是否是管理员
	var req_menuCode = "${requestScope.menuCode}";		//菜单id(如果有)
	
	var rightFunJson = '${sessionScope.rightFunJson}'==''?null: eval('(${sessionScope.rightFunJson})');//功能权限
</script>
<script type="text/javascript" src="${ctx}/scripts/common/common.js"></script>
<script type="text/javascript" src="${ctx}/scripts/common/emi_export.js"></script>
<script type="text/javascript" src="${ctx}/scripts/plugins/uploadify/emi_fileupload.js"></script>
<%-- <script type="text/javascript" src="${ctx}/scripts/emiwms.js"></script> --%>

