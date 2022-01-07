<%@page import="com.konantech.ksearch.web.vo.SearchParamVo"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%
    SearchParamVo paramvo = (SearchParamVo)request.getAttribute("paramvo");

     StringBuffer sbquery = new StringBuffer();
     StringBuffer sbcustom = new StringBuffer();

     sbquery.append("");

     System.out.println(">>>>>>>>>>>>>  query-sample "+paramvo);
 %>
