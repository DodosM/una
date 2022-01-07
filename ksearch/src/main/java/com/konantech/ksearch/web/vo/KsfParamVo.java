package com.konantech.ksearch.web.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class KsfParamVo{

	//도메인번호
	private int domain_no = 0;

	//최대개수
	private int  max_count = 10;

	//검색어힌드
	private String term="";

	//추천방식(s:첫단어,e:끝단어, c:가운데단어, t:단어기반)
	private String mod = "s";

}
