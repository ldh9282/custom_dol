<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>해당 페이지 없음</title>
</head>
<body>

<c:choose>
	<c:when test="${not empty response.header}">
	<h1>${response.header.status}</h1>
	</c:when>
	<c:otherwise>
	<h1>404</h1>
	</c:otherwise>
</c:choose>
<c:choose>
	<c:when test="${not empty response.header}">
	<h1>${response.header.errorMsg}</h1>
	</c:when>
	<c:otherwise>
	<h1>해당 페이지가 없습니다</h1>
	</c:otherwise>
</c:choose>

</body>
</html>