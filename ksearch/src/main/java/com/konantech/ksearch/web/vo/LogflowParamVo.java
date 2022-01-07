package com.konantech.ksearch.web.vo;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import lombok.EqualsAndHashCode;
import static com.konantech.ksearch.constants.SearchConstant.SITE;



@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class LogflowParamVo{
	//private static final long serialVersionUID =1L;

	//사이트
	private String site = SITE;

	//키워드
	private String keyword = "";

	//사용자
	private String user ="";

	//문서id
	private String docid ="";

	//상세url
	private String targeturl ="";

	//제목
	private String title ="";

	//클릭한섹션명: category
	private String section ="";

}
