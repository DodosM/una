package com.konantech.ksearch.web.service.impl;

import static com.konantech.ksearch.constants.SearchConstant.KONANBOT_MESSAGE;
import static com.konantech.ksearch.constants.SearchConstant.KONANBOT_OPEN;
import static com.konantech.ksearch.constants.SearchConstant.KONANBOT_URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.konantech.ksearch.util.HttpUtil;
import com.konantech.ksearch.web.service.KonanbotService;
import com.konantech.ksearch.web.vo.KonanbotParamVo;

@Service("KonanbotService")
public class KonanbotServiceImpl implements KonanbotService {

	private static final Logger logger = LoggerFactory.getLogger(KonanbotServiceImpl.class);

	@Autowired
	private HttpUtil httpUtil;

	@Override
	public String getOpen(KonanbotParamVo param) {
		StringBuffer sbUrl  = new StringBuffer();
		sbUrl.append(KONANBOT_URL);
		sbUrl.append(KONANBOT_OPEN);
		sbUrl.append("?domain=").append(param.getDomain());
		sbUrl.append("&user=").append(param.getUser());


		logger.debug(sbUrl.toString() );

		return httpUtil.getUrlDataPost(sbUrl.toString()).toString();
	}

	@Override
	public String getMessage(KonanbotParamVo param) {
		StringBuffer sbUrl  = new StringBuffer();
		sbUrl.append(KONANBOT_URL);
		sbUrl.append(KONANBOT_MESSAGE);
		sbUrl.append("?domain=").append(param.getDomain());
		sbUrl.append("&user=").append(param.getUser());
		sbUrl.append("&text=").append(httpUtil.urlEncode(param.getText()) );
		logger.debug(sbUrl.toString() );

		return httpUtil.getUrlDataPost(sbUrl.toString()).toString();
	}



}
