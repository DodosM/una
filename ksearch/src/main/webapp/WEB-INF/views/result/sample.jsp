 <%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
                <!-- s.샘플 -->
                <div class="lst-type-wrap">
                    <h3 data-target="sample">top_news
                    <c:choose>
                        <c:when test="${not empty error}">
                            <span> -<c:out value="${error}"  escapeXml="false"/></span>
                        </c:when>
                        <c:otherwise>
                            <span> (<fmt:formatNumber value="${sampleTotal}" groupingUsed="true"/>건)</span>
                            <c:if test="${params.category ne 'total'}">
                            <span style="float:right"><img src="resources/images/ico-excel.png" alt="엑셀다운" style="cursor: pointer;" onclick="javascript:doit('searchSample','table-data-sample')" ></span>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                    </h3>

                    <c:if test="${sampleTotal> 0}">
                    <ul class="lst-type-thumb">
                    <c:forEach var="result" items="${sampleList}" varStatus="status">
                    <fmt:parseDate value="${result.created_time}" var="dateFmt" pattern="yyyyMMddHHmmss"/>
                        <li style="min-height:0px">
                            <input type="hidden" name="id" value="<c:out value="${result.idx}"  escapeXml="false"/>">
                            <dl>
                                <dt>
                                    <a class="title" href="<c:out value="${result.title}"/>" target="_blank"><c:out value="${result.title}"  escapeXml="false"/></a>
                                    <!-- <span><fmt:formatDate value="${dateFmt}"  pattern="yyyy-MM-dd"/></span>  -->
                                </dt>
                                <dd><c:out value="${result.message}"  escapeXml="false"/></dd>
                                <dd class="link-info"><c:out value="${result.chain}"  escapeXml="false"/></dd>
                            </dl>
                        </li>
                        </c:forEach>
                    </ul>
                    </c:if>


                    <c:if test="${params.category eq 'total' and sampleTotal > params.pageSize}">
                    <div class="section-btn more">
                        <a href="javascript:void(0);" data-target="sample" data-trcode="sweb_more">샘플 더 보기<span class="ico-set arrow-type-a"></span></a>
                    </div>
                    </c:if>

                </div>
                <!-- e.통합검색 -->

                <!-- 페이지 -->
               <div id="pagination" class="paging"></div>
<script type="text/javascript">

$(function() {
	 <c:if test="${params.category ne 'total'}">
    $('#pagination').pagination(<c:out value="${sampleTotal}"/>, {
        current_page: <fmt:formatNumber value="${params.page -1 }" minFractionDigits="0"/>,
        callback: gotopage
    });
    </c:if>
});
</script>

<!--엑셀 다운로드 테이블 만들기  -->
<!-- 
<table border = "1" id="table-data-sample" class="dn">
    <tbody>
        <tr>
            <td colspan="4">샘플 (<fmt:formatNumber value="${sampleTotal}" groupingUsed="true"/>건)</td>
        </tr>
        <tr>
            <td width="50px">카테고리</td>
            <td width="150px">제목</td>
            <td width="50px">등록일</td>
            <td width="100px">내용</td>
        </tr>
<c:forEach var="result" items="${sampleList}" varStatus="status">
<fmt:parseDate value="${result.created_ymd}" var="dateFmt" pattern="yyyyMMdd"/>
        <tr>
            <td><c:out value="${result.category}"  escapeXml="false"/></td>
            <td><c:out value="${result.title}"  escapeXml="false"/></td>
            <td><fmt:formatDate value="${dateFmt}"  pattern="yyyy-MM-dd"/></td>
            <td><c:out value="${result.message}"  escapeXml="false"/></td>
        </tr>
</c:forEach>
    </tbody>
</table>
 -->