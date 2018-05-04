<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>lms</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/admin/rent.css" rel="stylesheet" type="text/css">
<script>
	var result = '${msg}';
	
	if(result == 'fail'){
		alert('로그인이 필요한 작업입니다.\n로그인 페이지로 이동합니다.');
		self.location = "user/login";
	}
	
	if(result == 'failToGetData'){
		alert('해당 페이지는 존재하지 않습니다.\n첫 번째 페이지로 이동합니다.');
		self.location = "/lms/admin/reserve/1";
	}
	
	if(result == 'noData'){
		alert('해당 조건에 해당하는 데이터가 존재하지않습니다.\n첫 번째 페이지로 이동합니다.');
		self.location = "/lms/admin/reserve/1";
	}
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/admin/include/header.jsp" />
		<div id="wrapper">
			<div id="content">
				<table class="tbl-ex">
					<tr>
						<th>번호 </th>
						<th>타이틀</th>
						<th>카테고리</th>
						<th>대여예정일</th>
						<th>반납예정일</th>
						<th>&nbsp;</th>
					</tr>
					<c:forEach items="${list.list}" var="vo" varStatus="status">
						<tr>
							<td>${vo.no}</td>
							<td>${vo.rent.item.name }</td>
							<td>${vo.rent.item.category.name }</td>
							<td>${vo.planRentDate}</td>
							<td>${vo.planReturnDate }</td>
						</tr>
					</c:forEach>
				</table>
				<div class="pager">
					<ul>
						<c:if test="${list.previous eq true }">
							<li><a href="${pageContext.servletContext.contextPath }/admin/reserve/${list.nowPage-1}">◀</a></li>
						</c:if>
						<c:forEach begin="${list.startPage}" end="${list.startPage + 4 }" step="1" var="page" varStatus="status">
							<c:choose>
								<c:when test="${page eq list.nowPage }">
									<li class="selected"><%-- ${list.startPage + status.index-1 } --%>${page}</li>
								</c:when>
								<c:when test="${page gt list.totalPages }">
									<li><font style="color:#CDCDCD">${page}</font></li>
								</c:when>
								<c:otherwise>
									<li><a href="${pageContext.servletContext.contextPath }/admin/reserve/${page}<%-- ${list.startPage + status.index-1  } --%>">${page}<%-- ${list.startPage + status.index-1 } --%></a></li>
								</c:otherwise>
							</c:choose>
							<%-- <c:if test="${page eq list.nowPage }">
								<li class="selected">${list.startPage + status.index-1 }${page}</li>
							</c:if>
							<c:if test="${page ne list.nowPage }">
								<li><a href="${pageContext.servletContext.contextPath }/admin/reserve/${page}${list.startPage + status.index-1  }">${page}${list.startPage + status.index-1 }</a></li>
							</c:if> --%>
						</c:forEach>
						<c:if test="${list.nextPage eq true}">
							<li><a href="${pageContext.servletContext.contextPath }/admin/reserve/${list.nowPage+1}">▶</a></li>
						</c:if>
					</ul>
				</div>
			</div>
			<c:import url="/WEB-INF/views/admin/include/navigation.jsp">
				<c:param name="menu" value="reserve"/>
			</c:import>
		</div>
	</div>
</body>
</html>