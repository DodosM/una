<%@page import="com.konantech.ksearch.constants.SearchConstant"%>
<%@page import="com.konantech.ksearch.util.CommonUtil"%>
<%@page import="com.konantech.ksearch.web.vo.SearchParamVo"%>
<%@page import="com.konantech.ksearch.web.vo.SearchRestVo"%>
<%@page import="com.konantech.ksearch.web.vo.RestResultVo"%>
<%@page import="com.konantech.ksearch.module.RestModule"%>
<%@page import="org.slf4j.Logger"%>
<%@page import="org.slf4j.LoggerFactory"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%
   Logger logger = LoggerFactory.getLogger(this.getClass().getName());
try{
    String listName = "sampleList";
    String totalName="sampleTotal";

    SearchParamVo paramvo = (SearchParamVo)request.getAttribute("params");
    String kwd = paramvo.getKwd();

     //검색어 없을 경우
     if(kwd.isEmpty()){
    	 request.setAttribute(totalName, 0 );
    	 return;
     }

     //검색어 있을 경우
    SearchRestVo restvo = new SearchRestVo();
    RestModule module = new RestModule();

     CommonUtil comUtil = new CommonUtil();
     StringBuffer sbquery = new StringBuffer();
     StringBuffer sbcustom = new StringBuffer();
     String strNmFd = paramvo.getFields().isEmpty()? "text_idx": paramvo.getFields();
     //상세검색
     if(paramvo.isDetail() ){
    	 sbquery.append(	 comUtil.makeDetailQuery(paramvo, strNmFd));
     }else{  //일반검색
    	 sbquery.append(strNmFd);
    	 sbquery.append(" = '").append(kwd).append("' allword synonym ");
     }

     //결과내재검색
    if(paramvo.isResrch()){
    	if(sbquery.length() > 0) sbquery.append(" and ");
    	sbquery.append(  comUtil.makeReSearchQuery(paramvo, strNmFd));
    }

    //범위검색
    if( !paramvo.getStartDate().isEmpty()){
        sbquery.append(" and created_ymd >= '").append(paramvo.getStartDate()).append("' ");
    }
    if( !paramvo.getEndDate().isEmpty()){
        sbquery.append(" and created_ymd <= '").append(paramvo.getEndDate()).append("' ");
    }


    //정렬조건 d:최신순, r: 정확도순, c: 클릭순
    switch(paramvo.getSort()){
    case "d":
    	sbquery.append(" order by created_ymd desc ");
    	break;
    case "r":
        sbquery.append(" order by $relevance desc ");
        break;
    case "c":
        String docids = module.getKeywordDocidRank(kwd, "sample", "w");
        if(!docids.isEmpty()){
            sbquery.append(" order by post_id (").append(docids).append(") ");
        }
        break;
    default:
    	sbquery.append(" order by created_ymd desc ");
        break;
    }

     restvo.setSelectFields("post_id,user_id,post_url,created_ymd,category,title,message");
     restvo.setFrom("sample.post");
     restvo.setWhere( sbquery.toString() );
     restvo.setOffset( paramvo.getOffset() );
     restvo.setPagelength(paramvo.getPageSize() );
     restvo.setHilightFields("{'title':{'length':250,'begin':'<strong>','end':'</strong>'}},{'message':{'length':200,'begin':'<strong>','end':'</strong>'}}");
     restvo.setCustomLog(comUtil.getCustomLog(paramvo)  );

     RestResultVo resultvo = module.restSearchPost(restvo);



     logger.debug(">>>>>>>>>>>>>  query-sample "+paramvo);
     logger.debug(">>>>>>>>>>>>>  resultvo list "+resultvo.getResult() );
     logger.debug(">>>>>>>>>>>>>  resultvo total "+resultvo.getTotal());

     request.setAttribute(listName, resultvo.getResult() );
     request.setAttribute(totalName,resultvo.getTotal() );

 }catch (Exception e){
	 e.printStackTrace();
	 logger.error(" sample error - "+e.toString());
	 request.setAttribute("error", SearchConstant.MSG_SEARCH_ERROR);
 }

 %>
