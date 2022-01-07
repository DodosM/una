 <%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
                <div class="no-result">
                    <div class="result-title">
                        &quot;<strong><c:out value="${params.preKwds}" /></strong>&quot; 에 대한 검색결과가 없습니다.
                    </div>
                    <div class="result-infor">
                        &middot; 단어의 철자가 정확한지 확인해 보세요.<br />
                        &middot; 검색어의 단어 수를 줄이거나, 보다 일반적인 검색어로 다시 검색해 보세요.<br />
                        &middot; 두 단어 이상의 검색어인 경우, 띄어쓰기를 확인해 보세요.<br />
                    </div>
                    <div style="border-bottom:1px solid #eee; margin-left:10px;margin-right:10px;"></div>
                    <br />
                    <div class="result-infor">
                        <span style="font-weight:bold;font-size:15px;color:#333;">검색도움말</span><br />
                              &middot; 예1) 코난 +솔루션: 코난과 솔루션이 모두 존재하는 문서 검색<br />
                              &middot; 예2) 코난 -솔루션: 코난으로 검색된 문서중 솔루션이 존재하는 문서 제외<br /><br />
                    </div>
                </div>