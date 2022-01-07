package com.konantech.ksearch.web.service;

import com.konantech.ksearch.web.vo.KlaParamVo;
import com.konantech.ksearch.web.vo.LogflowParamVo;

public interface KlaService {

	//클릭로그 저장
	boolean saveLogflow( LogflowParamVo paramvo);

	//키워드별 클릭문서 docid
	String getKeywordDocidRank(KlaParamVo paramvo);

	//키워드별 클릭문서 field
	String getDocidRankContent(KlaParamVo paramvo);

	//급상승키워드 조회
	String getRankUpper(KlaParamVo paramvo);

}
