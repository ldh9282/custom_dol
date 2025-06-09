<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>시스템 내부서버 오류</title>
</head>
<body>

<c:choose>
	<c:when test="${not empty response.header}">
	<h1>${response.header.status}</h1>
	</c:when>
	<c:otherwise>
	<h1>500</h1>
	</c:otherwise>
</c:choose>
<c:choose>
	<c:when test="${not empty response.header}">
	<h1>${response.header.errorMsg}</h1>
	</c:when>
	<c:otherwise>
	<h1>시스템 내부 처리중 오류가 발생했습니다 (잠시후 시도해주세요)</h1>
	</c:otherwise>
</c:choose>

</body>
</html>