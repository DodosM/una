package com.konantech.ksearch.web.service;

import com.konantech.ksearch.web.vo.KonanbotParamVo;

public interface KonanbotService {

	//open
	String getOpen(KonanbotParamVo param);

	//message
	String getMessage(KonanbotParamVo param);
}
