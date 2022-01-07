<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:forTokens var="target" items="sample,board" delims=",">
    <c:if test="${params['category'] == target || params['category'] == 'total'}">
        <jsp:include page="/WEB-INF/views/query/query-${target}.jsp"/>
    </c:if>
</c:forTokens>
<!DOCTYPE html>
<html lang="ko">
<head>
	<title> : 통합검색</title>
	<meta charset="utf-8">
	<meta name="referrer" content="always">
	<meta property="og:type" content="website">
	<meta property="og:title" content="통합검색">
	<meta property="og:description" content="''의 통합검색 결과입니다.">
	<link type="text/css" rel="stylesheet"  href="resources/css/reset.css">
	<link type="text/css" rel="stylesheet"  href="resources/css/main.css">
	<link type="text/css" rel="stylesheet"  href="resources/css/search.css">
	<link type="text/css" rel="stylesheet"  href="resources/js/jquery-ui-1.12.1.custom/jquery-ui.css">
<!--     <link type="text/css" rel="stylesheet"  href="resources/js/jquery/pagination.css"> -->
    <script type="text/javascript" src="resources/js/jquery-ui-1.12.1.custom/external/jquery/jquery-3.4.1.js"></script>
    <script type="text/javascript" src="resources/js/jquery-ui-1.12.1.custom/jquery-ui.js"></script><!--jQuery UI -->
    <script type="text/javascript" src="resources/js/jquery/jquery.deserialize.min.js"></script>
     <script type="text/javascript" src="resources/js/jquery/jquery.cookie.js"></script>
    <script type="text/javascript" src="resources/js/jquery/jquery.konan.sf-ko.js"></script><!-- 자동완성: 텍스트  -->
    <script type="text/javascript" src="resources/js/jquery/jquery.konan.sf.js"></script><!-- 자동완성,최근검색어  -->
    <script type="text/javascript" src="resources/js/jquery/jquery.pagination.js"></script>
    <script type="text/javascript" src="resources/js/common.js"></script><!-- Events -->
    <script type="text/javascript" src="resources/js/rolling.js"></script><!-- Events -->
    <script type="text/javascript" src="resources/js/search.js"></script><!-- Events -->
    <script type="text/javascript" src="resources/js/search.ksf.js"></script>
    <script type="text/javascript" src="resources/js/search.kla.js"></script><!-- Events -->
    <script type="text/javascript" src="resources/js/rolling.js"></script><!-- Events -->
    <!-- 엑셀저장 -->
    <script type="text/javascript" src="resources/js/sheetjs/xlsx.full.min.js"></script><!-- Events -->
    <script type="text/javascript" src="resources/js/filesaver/FileSaver.min.js"></script><!-- Events -->
    <script type="text/javascript" src="resources/js/search.excel.js"></script><!-- Events -->
	<script>
	 $( function() {
        $( "#sdate, #edate" ).datepicker({ dateFormat:"yymmdd" });


        document.body.onclick=function(e){
             $(".auto-search").css('display', 'none');
             $(".auto-recent").css('display', 'none');
        };

        //메타태그 수정
        $("meta[property='og\\:title']").attr("content", '<c:out value="${params.kwd}" />:통합검색' );
        $("meta[property='og\\:description']").attr("content", '<c:out value="${params.kwd}" />의 통합검색 결과입니다' );
        document.title = '<c:out value="${params.kwd}" />:통합검색';
	 });

	</script>
</head>
<body>
    <form id="historyForm" name="historyForm" action="searchpo.do" method="post">
        <input type="hidden" id="category" name="category" value="<c:out value="${params.category}" />">
        <input type="hidden" id="kwd" name="kwd" value="<c:out value="${params.kwd}" />">
        <input type="hidden" id="preKwds" name="preKwds" value="<c:out value="${params.preKwds}" />">
        <input type="hidden" id="date" name="date" value="<c:out value="${params.date}" />">
        <input type="hidden" id="startDate" name="startDate" value="<c:out value="${params.startDate}" />">
        <input type="hidden" id="endDate" name="endDate" value="<c:out value="${params.endDate}" />">
        <input type="hidden" id="page" name="page" value="<c:out value="${params.page}" />">
         <input type="hidden" id="pageSize" name="pageSize" value="<c:out value="${params.pageSize}" />">
        <input type="hidden" id="offset" name="offset" value="<c:out value="${params.offset}" />">
        <input type="hidden" id="resrch" name="resrch" value="<c:out value="${params.resrch}" />">
        <input type="hidden" id="sort" name="sort" value="<c:out value="${params.sort}" />">
        <input type="hidden" id="sortNm" name="sortNm" value="<c:out value="${params.sortNm}" />">
        <input type="hidden" id="fields" name="fields" value="<c:out value="${params.fields}" />">
        <input type="hidden" id="detail" name="detail" value="<c:out value="${params.detail}" />">
        <input type="hidden" id="basickwd" name="basickwd" value="<c:out value="${params.basickwd}" />">
        <input type="hidden" id="exactkwd" name="exactkwd" value="<c:out value="${params.exactkwd}" />">
        <input type="hidden" id="inkwd" name="inkwd" value="<c:out value="${params.inkwd}" />">
        <input type="hidden" id="notkwd" name="notkwd" value="<c:out value="${params.notkwd}" />">
        <input type="hidden" id="autospc" name="autospc" value="">
        <input type="hidden" id="tr_code" name="tr_code" value="<c:out value="${params.tr_code}" />">
        <input type="hidden" id="srch_opt" name="srch_opt" value="<c:out value="${params.srch_opt}" />">
    </form>
     <form id="clickForm" name="clickForm" method="post">
        <input type="hidden" id="docid" name="docid">
        <input type="hidden" id="targeturl" name="targeturl">
        <input type="hidden" id="title" name="title">
        <input type="hidden" id="section" name="section">
        <input type="hidden" id="keyword" name="keyword" value="<c:out value="${params.kwd}" />">
     </form>

	<div id="wrap" class="sub">
		<header id="header">

			<!-- 검색창 -->
            <jsp:include page="include/topSearch.jsp"/>
			<!-- end.상세검색 -->
		</header>

		<section id="container">
			<section id="column-left" tabindex="0">
			    <div class="result-text detail" style="display:none;border-bottom:1px solid #dddddd">
			    </div>
			    <!-- s.연관검색어 -->
			    <dl id="related"  class="search-group dn">
                    <dt>연관검색어</dt>
                    <dd>
                    </dd>
			    </dl>
			    <div class="ly_option" style="display: none;" id="sugmore">
                    <a href="javascript:void(0);" class="suggest_more" style="display:none;">
                        <span id="suggestmore">더보기</span><span class="ico-set up"></span></a>
			    </div>
			    <!-- e.연관검색어 -->

                <!-- 검색결과 -->
                <jsp:include page="result/total.jsp"/>
			</section>

			<section id="column-right">
				  <!-- 인기검색어 -->
				 <div class="keyword-wrap">
				     <h3>인기검색어</h3>
				     <div id="rankings" class="box-content rankings">

				     </div>
				 </div>
				 <!-- 검색어별 많이 조회한 콘텐츠 -->
				 <div class="keyword-wrap">
				     <h3>검색어별 많이 조회한 콘텐츠</h3>
				     <div id="docidranks">
				     </div>
				 </div>

				<!-- 내가 찾은 검색어 -->
				<div class="keyword-wrap new">
				         <h3>내가 찾은 검색어</h3>
				         <div id="recent" class="box-content" data-trcode="mine">
				             <ul class="konan-recent lst-keyword mysearch">
				                 <li><a href="javascript:void(0);">검색</a><span class="close-btn"></span></li>
				             </ul>
				             <div class="konan-box-msg" style="display: none;">최근검색어 내역이 없습니다.</div>
				          </div>
				 </div>

			</section>
		</section>
        <!-- footer -->
        <footer>
            <section class="botsearch">
                <div class="search-wrap">
                    <div class="rankupp-wrap">
                    <!-- 급상승키워드 -->
                        <div id="rankupp" class="rankupp-wrap">
                        </div>
                        <!-- 검색폼 -->
                        <fieldset>
                            <legend>통합검색</legend>
                            <form name="searchForm">
                                <div class="search-box">
                                    <div class="search-box-inner">
                                        <label for="footer-search-text" class="blind">검색어를 입력하세요</label>
                                        <input type="text" name="kwd" value="" id="footer-search-text" title="통합검색어 입력" placeholder="검색어를 입력하세요">
                                    </div>
                                    <button type="submit" title="검색"><span class="blind">검색</span></button>
                                </div>
                            </form>
                        </fieldset>
                    </div>
                </div>
                <div class="row">
                        <div class="address">
                            <p class="city">(주)코난테크놀로지 서울특별시 서초구 서초동 강남대로 327 </p>
                            <p class="copy">© Konan all rights reserved</p>
                        </div>
                </div>

            </section>
        </footer>
	</div>
	<!-- 챗봇 아이콘 추가 -->
	<div class="ico-bot">
      <a type="button" class="btn" data-toggle="modal" data-target="#chatLayer"></a>
    </div>
    <jsp:include page="./konanbot/konanbot.jsp"></jsp:include>

</body>
</html>