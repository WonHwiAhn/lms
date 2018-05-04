<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>LMS</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath }/assets/css/main.css" rel="stylesheet" type="text/css">
<script>
	var result = '${msg}';
	
	if(result == 'fail'){
		alert('로그인이 필요한 작업입니다.\n로그인 페이지로 이동합니다.');
		self.location = "user/login";
	}
	
	if(result == 'failToGetData'){
		alert('해당 페이지는 존재하지 않습니다.\n첫 번째 페이지로 이동합니다.');
		self.location = "/lms/1";
	}
	
	if(result == 'noData'){
		alert('해당 조건에 해당하는 데이터가 존재하지않습니다.\n첫 번째 페이지로 이동합니다.');
		self.location = "/lms/1";
	}
	
	if(result == 'exist'){
		alert('해당 아이디로 이미 예약하셨기 때문에 예약이 안됩니다.');
		self.location = "/lms/1";
	}
</script>
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header_jstl.jsp" />
		<div id="content">
			<div id="board">
				<form id="search_form" action="${pageContext.request.contextPath }" method="get">
					전체 아이템 수 : ${list.totalCounts} 
					<input type="text" id="searchString" name="searchString" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>타이틀</th>
						<th>카테고리</th>
						<th>&nbsp;</th>
					</tr>
				<c:forEach items="${list.list }" var="vo" varStatus="status">
					<tr>
						<td>
							${vo.no}
						</td>
						<td>${vo.name }</td>
						<td>${vo.category.name }</td>
						<td>
							<c:if test="${vo.status eq 0}">
								<a href="${pageContext.servletContext.contextPath }/rent/${list.nowPage }?itemNo=${vo.no}" class="btn">대여</a>
							</c:if>
							<c:if test="${vo.status eq 1 }">
								<a href="${pageContext.servletContext.contextPath }/reserve/${list.nowPage }?itemNo=${vo.no}" class="btn">예약</a>
							</c:if>
						</td>
					</tr>
				</c:forEach>
				</table>
				<div class="pager">
					<ul>
						<%-- <li><a href="${pageContext.servletContext.contextPath }/rent">◀</a></li>
						<li><a href="${pageContext.servletContext.contextPath }/rent">6</a></li>
						<li><a href="${pageContext.servletContext.contextPath }/rent">7</a></li>
						<li class="selected">8</li>
						<li>9</li>
						<li>10</li>
						<li>▶</li>--%>
						<c:if test="${list.previous eq true }">
							<li><a href="${pageContext.servletContext.contextPath }/${list.nowPage-1}">◀</a></li>
						</c:if>
						<c:forEach begin="${list.startPage}" end="${list.startPage + 4 }" step="1" var="page" varStatus="status">
							<c:choose>
								<c:when test="${page eq list.nowPage }">
									<li class="selected">${page}</li>
								</c:when>
								<c:when test="${page gt list.totalPages }">
									<li><font style="color:#CDCDCD">${page}</font></li>
								</c:when>
								<c:otherwise>
									<li><a href="${pageContext.servletContext.contextPath }/${page}">${page}</a></li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<c:if test="${list.nextPage eq true}">
							<li><a href="${pageContext.servletContext.contextPath }/${list.nowPage+1}">▶</a></li>
						</c:if>
					</ul>
				</div>				
				<div class="bottom">
				</div>
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>