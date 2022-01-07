package com.konantech.ksearch.web.vo;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RestResultVo {

	//조회 상태
	private String status;
	//전체건수
	private long total;
	//데이터
	private int rows;
	//데이터리스트
	private List<Map<String, String>> result;

}
