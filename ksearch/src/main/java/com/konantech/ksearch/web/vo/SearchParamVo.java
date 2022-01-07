package com.konantech.ksearch.web.vo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import static com.konantech.ksearch.constants.SearchConstant.TOTAL_PAGE_SIZE;

import lombok.Data;
import lombok.EqualsAndHashCode;



@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SearchParamVo{
	//private static final long serialVersionUID =1L;

	//카테고리
	private String category = "total";
	//페이징
	private int page = 1;
	//페이징건수
	private int  pageSize = TOTAL_PAGE_SIZE;
	//
	private int offset=0;
	//정렬
	private String sort="d";
	//키워드
	private String kwd = "";
	//검색기간
	private String date="";
	//검색기간-시작일
	private String startDate="";
	//검색기간-종료일
	private String endDate="";
	 //이전키워드
	private String preKwds="";
	//결과내재검색
	private boolean resrch=false;
	//검색필드 또는 인덱스명
	private String fields="";
	//
	private String group="";
	//
	private boolean detail=false;
	//키워드 검색 상세
	private String basickwd="";
	private String exactkwd="";
	private String inkwd="";
	private String notkwd="";
	//오타교정
	private String autospc="";
	private String tr_code="";
	//검색옵션
	private String srch_opt="";
	//오늘일자
    private String todayDate="";

    //customlog
    private String sortNm="최신순";
    private String userid="";
    private String gender="";
}
