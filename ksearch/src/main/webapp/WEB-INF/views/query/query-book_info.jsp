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
    String listName = "book_infoList";
    String totalName="book_infoTotal";

    SearchParamVo paramvo = (SearchParamVo)request.getAttribute("params");
    String kwd = paramvo.getKwd();

     //검색어 없을 경우
     if(kwd.isEmpty()){
    	 request.setAttribute(totalName, 0 );
    	 System.out.println("==== book_info 검색어 없을 경우====");
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
    	 sbquery.append(" = '").append(kwd).append("' allword "); 
     }
	 System.out.println("1> 여기까지 생성된 쿼리  : "+ sbquery.toString());
     
     //결과내재검색
    if(paramvo.isResrch()){
    	if(sbquery.length() > 0) sbquery.append(" and ");
    	sbquery.append(  comUtil.makeReSearchQuery(paramvo, strNmFd));
    }
   
    //범위검색
    /* 
    if( !paramvo.getStartDate().isEmpty()){
        sbquery.append(" and created_time >= '").append(paramvo.getStartDate()).append("' ");
    }
    if( !paramvo.getEndDate().isEmpty()){
        sbquery.append(" and created_time <= '").append(paramvo.getEndDate()).append("' ");
    }
 */

    //정렬조건 d:최신순, r: 정확도순, c: 클릭순
    /* 
    switch(paramvo.getSort()){
    case "d":
    	sbquery.append(" order by created_time desc ");
    	break;
    case "r":
        sbquery.append(" order by $relevance desc ");
        break;
    case "c":
        String docids = module.getKeywordDocidRank(kwd, "book_info", "w");
        if(!docids.isEmpty()){
            sbquery.append(" order by idx (").append(docids).append(") ");
        }
        break;
    default:
    	sbquery.append(" order by created_time desc ");
        break;
    } */
    
 
    System.out.println("====paramvo 가져오는 것 ==== : "+paramvo);
    
    
     restvo.setSelectFields("idx,bk_nm,bk_writer,content,file_nm,flpth,bk_reference,since,detail_txt,detail_html");
     restvo.setFrom("book_info.book_info");
     restvo.setWhere( sbquery.toString() );
     restvo.setOffset( paramvo.getOffset() );
     restvo.setPagelength(paramvo.getPageSize() );
     restvo.setHilightFields("{'bk_nm':{'length':250,'begin':'<b>','end':'</b>'}},{'detail_txt':{'length':200,'begin':'<b>','end':'</b>'}}");
     restvo.setCustomLog(comUtil.getCustomLog(paramvo)  );
     
     System.out.println("====restvo 가져오는 것 ==== : "+restvo.toString());

     
     System.out.println("여기까지 됩니다");
     
     //url생성
     
     logger.debug(restvo.getSelectFields());
     System.out.println("필드 " + restvo.getSelectFields());
     logger.debug(restvo.getUrl());
     System.out.println("필드 " + restvo.getUrl());
     logger.debug(restvo.getFrom());
     System.out.println("필드 " + restvo.getFrom());
     
     RestResultVo resultvo = module.restSearchPost(restvo);
     System.out.println("module에서 가져오는 값 : "+resultvo);


     logger.debug(">>>>>>>>>>>>>  query-book_info "+paramvo);
     logger.debug(">>>>>>>>>>>>>  resultvo list "+resultvo.getResult());
     logger.debug(">>>>>>>>>>>>>  resultvo total "+resultvo.getTotal());

     request.setAttribute(listName, resultvo.getResult() );
     request.setAttribute(totalName,resultvo.getTotal() );

 }catch (Exception e){
	 e.printStackTrace();
	 logger.error(" book_info error - "+e.toString());
	 request.setAttribute("error", SearchConstant.MSG_SEARCH_ERROR);
 }

 %>
