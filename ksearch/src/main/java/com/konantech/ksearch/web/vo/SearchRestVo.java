package com.konantech.ksearch.web.vo;

import static com.konantech.ksearch.constants.SearchConstant.SEARCH_URL;
import static com.konantech.ksearch.constants.SearchConstant.CHARSET;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class SearchRestVo{

	// restUrl
	private String url=SEARCH_URL;
	// 조회할 필드명
	private String selectFields="";
	// 볼륨명
	private String from="";
	//조건절
	private String where="";
	//검색시작
	private int offset = 0;
	//결과개수
	private int pagelength = 10;
	//Top-N 쿼리 제약시 N값 지정. 상위 N개에 대해서만 검색/정렬 등 수행
	private int limit = 10;
	//kla custom 로그
	private String customLog="";
	//콜백함수
	private String callback = "";
	//중복레벨 설정
	private int distinct = 0;
	//필드에 대한 하일라이팅 설정
	private String hilightFields="";
	//하일라이팅할 키워드
	private String hilightKeywords="";
	// 적합도 점수
	private String score="";
	//디폴트 하일라이팅을 적용
	private String defaultHilite="on";
	//캐릭터 인코딩
	private String charset=CHARSET;
}
