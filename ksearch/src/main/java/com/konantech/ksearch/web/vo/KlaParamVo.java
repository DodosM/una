package com.konantech.ksearch.web.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import static com.konantech.ksearch.constants.SearchConstant.SITE;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class KlaParamVo{

	//리포트데이터형태
	private String format = "1";

	//리포트형태
	private String target="rank_upper";

	//사이트
	private String site = SITE;

	//카테고리명
	private String category="";

	//조회시작날짜
	private String fromDate = "";

	//조회종료날짜
	private String toDate = "";

	//선택한 기간
	private String duration = "daily";

	//최대개수
	private int  count = 10;

	// 이전날짜 조회
	private int  datenum = 1;

	//검색어
	private String keyword="";

	//데이터형태
	private String output="json";

	private String credential ="0baea2f0ae20150d";

	//표제어존재여부
	private boolean hasHeadword=true;

	//연관어 최대 개수
	private int relatedWordCount= 10;

	//format이 logflow 인경우 사용 추가로가져올 필드명, 여러필드인 경우 ,로 구분
	private String select_field="";

	//두 기간의 차이 설정
	private int rank_gap = 1;

}
