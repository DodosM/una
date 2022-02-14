<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
            <!-- 검색창 -->
            <div class="search-line">
                <div class="inner">
                    <h1 class="pc-view">
                        <a href=""><span>KONAN</span></a>
                    </h1>

                    <fieldset>
                        <legend>통합검색</legend>
                        <form name="search-form" autocomplete="off">
                            <!-- 검색창 -->
                             <div class="search">
                                <label for="kwd" class="sr-only">검색어를 입력해 주세요</label>
                                <input type="text" value="<c:out value='${params.kwd}'/>" id="search-text" class="konan-autocomplete-input"
                                            name="kwd" placeholder="검색어를 입력해 주세요" style="ime-mode:active;"
                                            title="검색창" autocomplete="off">
                                    <button type="submit" class="btn btn-search" title="검색">
                                    <span class="sr-only">검색하기</span>
                                 </button>
                             </div>
                            <div class="search-check-box">
                                <input type="checkbox" name="resrch" id="search_re">
                                <label for="search_re"><strong>결과내 재검색</strong></label>
                            </div>
                        </form>
                    </fieldset>
                </div>
            </div>

            <!-- navi bar -->
            <div class="searchnav">
                <nav>
                    <ul class="gnb">
                        <li class="ctgr">
                            <a href="javascript:void(0);" data-target="total" data-trcode="menu01">통합검색<span class="gnb-bar"></span></a>
                        </li>
                        <li class="ctgr">
                            <a href="javascript:void(0);" data-target="sample" data-trcode="menu02">top_news<span class="gnb-bar"></span></a>
                        </li>
                        <li class="ctgr">
                            <a href="javascript:void(0);" data-target="book_info" data-trcode="menu03">책 정보<span class="gnb-bar"></span></a>
                        </li>
                    </ul>
                    <div class="search-option">
                        <a href="javascript:void(0);" data-trcode="menu09">검색옵션<span class=""></span></a>
                    </div>
                </nav>
            </div>

            <!-- 상세검색 -->
            <div id="filtersearh" class="search-option-inner">
                <ul class="search-option-menu">
                    <li>
                        <a href="javascript:void(0);">최신순<span class="ico-set up"></span></a>
                        <div class="more-layer" data-param="sort">
                            <ul class="more-menu">
                                <li><a href="javascript:void(0);" data-cd="d" data-trcode="opt01_02" class="active">최신순</a></li>
                                <li><a href="javascript:void(0);" data-cd="r" data-trcode="opt01_01">정확도순</a></li>
                                <li><a href="javascript:void(0);" data-cd="c" data-trcode="opt01_03">클릭순</a></li>
                            </ul>
                        </div>
                    </li>
                    <li>
                        <a href="javascript:void(0);">기간<span class="ico-set up"></span></a>
                        <div class="more-layer" data-param="date">
                            <ul class="more-menu">
                                <li><a href="javascript:void(0);" data-cd="" data-trcode="opt02_01" class="active">전체</a></li>
                                <li><a href="javascript:void(0);" data-cd="d" data-trcode="opt02_02">1일</a></li>
                                <li><a href="javascript:void(0);" data-cd="w" data-trcode="opt02_03">1주</a></li>
                                <li><a href="javascript:void(0);" data-cd="m" data-trcode="opt02_04">1개월</a></li>
                                <li><a href="javascript:void(0);" data-cd="3m" data-trcode="opt02_05">3개월</a></li>
                                <li><a href="javascript:void(0);" data-cd="y" data-trcode="opt02_06">1년</a></li>
                                <li>
                                    <div class="more-input" data-cd="input" data-trcode="opt02_07">
                                        <p>직접입력</p>
                                        <input type="text" name="sdate" id="sdate" title="시작일" >
                                        <label for="sdate" class="blind">시작일</label>
                                        <input type="text" name="edate" id="edate" title="종료일" >
                                        <label for="edate" class="blind">종료일</label>
                                        <button>적용</button>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </li>
                    <li>
                        <a href="javascript:void(0);">영역<span class="ico-set up"></span></a>
                        <div class="more-layer" data-param="fields">
                            <ul class="more-menu">
                                <li><a href="javascript:void(0);" data-cd="" data-trcode="opt03_01" class="active">전체</a></li>
                                <li><a href="javascript:void(0);" data-cd="title" data-trcode="opt03_02">제목</a></li>
                            </ul>
                        </div>
                    </li>
                    <li>
                        <a href="javascript:void(0);">상세검색<span class="ico-set up"></span></a>
                        <div class="more-layer" data-param="detail" data-trcode="opt04_01">
                            <div class="detail-box">
                                <p class="detail-input">
                                    <input type="text" name="detailkwd" id="detailkwd" title="상세검색어" disabled="" />
                                    <label for="detailkwd" class="blind">상세검색어</label>
                                </p>
                                <dl>
                                    <dt><label for="s1">기본검색어</label></dt>
                                    <dd><div><input type="text" name="s1" id="s1"></div></dd>
                                </dl>
                                <dl>
                                    <dt><label for="s2">정확히 일치하는 단어/문장 (””)</label></dt>
                                    <dd><div><input type="text" name="s2" id="s2"></div></dd>
                                </dl>
                                <dl>
                                    <dt><label for="s3">반드시 포함하는 단어 (+)</label></dt>
                                    <dd><div><input type="text" name="s3" id="s3"></div></dd>
                                </dl>
                                <dl>
                                    <dt><label for="s4">제외하는 단어 (-)</label></dt>
                                    <dd><div><input type="text" name="s4" id="s4"></div></dd>
                                </dl>
                                <p class="detail-info">* 여러 개의 단어를 입력할 때는 쉽표(,)로 구분해서 입력하세요</p>
                                <div class="detail-btn-set">
                                    <button class="blue srch">검색</button>
                                    <button class="gray exit">닫기</button>
                                </div>
                            </div>
                        </div>
                    </li>
                </ul>
            </div>
            <!-- end.상세검색 -->