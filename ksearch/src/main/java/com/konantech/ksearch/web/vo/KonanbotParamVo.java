package com.konantech.ksearch.web.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import static com.konantech.ksearch.constants.SearchConstant.KONANBOT_DEFAULT_DOMAIN;
import static com.konantech.ksearch.constants.SearchConstant.KONANBOT_DEFAULT_USER;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class KonanbotParamVo{

	//도메인
	private String domain = KONANBOT_DEFAULT_DOMAIN;

	//사용자
	private String  user =KONANBOT_DEFAULT_USER;

	//질의문
	private String text;

}
