package com.konantech.ksearch.web.service;

import com.konantech.ksearch.web.vo.KsfParamVo;

public interface KsfService {

	//자동완성
	String getAutocomplete(KsfParamVo param);

	//인기검색어
	String getPopularKwd(KsfParamVo param);

	//추천검색어
	String getRelatedKwd(KsfParamVo paramvo);

	//오타체크
	String getSpellChek(String term);
}
