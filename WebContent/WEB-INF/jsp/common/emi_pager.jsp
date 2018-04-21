<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- JSTL 标签 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="emi_page">
	<%-- <input type="hidden" name="pno" value="${data.pageIndex }"> --%>
	<div id="kkpager" ></div>
</div>
<script type="text/javascript">
	var formIndex=0;	//本页面form的index,默认0
   	var pageIndex = '${data.pageIndex}'-0;	 //当前页码
   	var pageCount = '${data.pageCount}'-0;	 //总页数
   	var totalRecords = '${data.totalCount}'-0;	 //总条数
</script>
<script type="text/javascript" src="${ctx }/scripts/plugins/kkpager/src/kkpager.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx }/scripts/plugins/kkpager/src/kkpager_blue.css" />
<script type="text/javascript" src="${ctx }/scripts/plugins/kkpager/src/page.js"></script>