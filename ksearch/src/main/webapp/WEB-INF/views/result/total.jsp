 <%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:choose>
<c:when test="${sampleTotal == 0 }">
<jsp:include page="noresult.jsp"/> 
</c:when>
<c:otherwise>
<jsp:include page="sample.jsp" />
<jsp:include page="book_info.jsp"/>
</c:otherwise>
</c:choose>