 <%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@page import="com.konantech.ksearch.web.vo.RestResultVo"%>
<%@page import="com.konantech.ksearch.module.RestModule"%>

<%

RestResultVo resultvo = new RestResultVo();
RestModule module = new RestModule();

System.out.println("순서4) [total.jsp] 진입 ");

%>
<!-- 
XMLHttpRequest 객체 
	- 웹 브라우저는 서버에 데이터를 요청하기 위한 XMLHttpRequest객체를 내장
	- 서버로부터 XML데이터를 전송 받아 처리하는데 사용
	- 이 객체를 사용하면 웹 페이지가 전부 로딩 된 후에도 서버에 데이터를 요청하거나 서버로부터 데이터를 전송 받을 수 있음
	- 즉, 웹 페이지 전체를 다시 로딩하지 않고 일부분만 갱신할 수 있게 됨
 -->
 
<%-- 통합 검색 창 --%>
<c:choose>
	<c:when test="${sampleTotal == 0 && book_infoTotal == 0}"> <!-- c:when test="${sampleTotal == 0}"  ${header['X-Requested-With'] == 'XMLHttpRequest'} -->
		<jsp:include page="noresult.jsp"/> 
	</c:when>
	<c:otherwise>
		<span style="float:right;position:relative;left:-30%;padding-bottom:5px;padding-top:5px;"><strong>"<c:out value="${params.kwd}"/>"</strong>에 대한 전체 <c:out value="${sampleTotal + book_infoTotal}" />건이 검색되었습니다</span>
		<jsp:include page="sample.jsp" />
		<jsp:include page="book_info.jsp" />
	</c:otherwise>
</c:choose>
<%-- 각 카테고리를 클릭 했을 때, 해당하는 값을 불러올려면 (*해당카테고리*.jsp 맨 상단에 c:if 붙이기) --%>




<%-- 다음은 내가 헷갈렸던 문제이다
	<c:choose>
		<c:when test="${sampleTotal > 0 && book_infoTotal > 0}">
			<jsp:include page="sample.jsp" />
			<jsp:include page="book_info.jsp" />
		</c:when>
		<c:otherwise>
			<c:if test="${sampleTotal == 0}">
				<jsp:include page="noresult.jsp"/>
			</c:if>
		</c:otherwise>	
	</c:choose>
 --%>