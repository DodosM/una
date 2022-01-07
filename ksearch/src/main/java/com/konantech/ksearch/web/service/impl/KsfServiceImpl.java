package com.konantech.ksearch.web.service.impl;

import static com.konantech.ksearch.constants.SearchConstant.CHARSET;
import static com.konantech.ksearch.constants.SearchConstant.KSF_URL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.konantech.ksearch.util.HttpUtil;
import com.konantech.ksearch.web.service.KsfService;
import com.konantech.ksearch.web.vo.KsfParamVo;

@Service("ksfService")
public class KsfServiceImpl implements KsfService {

	private static final Logger logger = LoggerFactory.getLogger(KsfServiceImpl.class);

	@Autowired
	private HttpUtil httpUtil;

	//자동완성
	@Override
	public String getAutocomplete(KsfParamVo paramvo) {

		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append(KSF_URL);
		sbUrl.append("suggest");
		sbUrl.append("?target=complete");
		sbUrl.append("&term=").append(httpUtil.urlEncode(paramvo.getTerm(), CHARSET) );
		sbUrl.append("&mode=").append(paramvo.getMod());
		sbUrl.append("&domain_no=").append( paramvo.getDomain_no());
		sbUrl.append("&max_count=").append(paramvo.getMax_count());


		logger.debug(sbUrl.toString());
		StringBuffer sb = httpUtil.getUrlData(sbUrl.toString());
		logger.debug(sb.toString());
		return sb.toString();

	}


	@Override
	public String getPopularKwd(KsfParamVo paramvo) {
		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append(KSF_URL);
		sbUrl.append("rankings");
		sbUrl.append("?domain_no=").append(paramvo.getDomain_no());
		sbUrl.append("&max_count=").append( paramvo.getMax_count());
		logger.debug(sbUrl.toString());
		StringBuffer sb = httpUtil.getUrlData(sbUrl.toString());
		try{
			List<Map<String, String>> list = new ArrayList<> ();
			Map<String, String> map;

			Gson gson = new Gson();
			List<String[]> ppkjson = gson.fromJson(sb.toString(), new TypeToken<List<String[]>>() {}.getType());

			for(String[] arr : ppkjson ) {
				map = new HashMap<>();
				map.put("ppk",  arr[0]);
				map.put("meta", arr[1]);
				list.add(map);
				map = null;
			}

			return gson.toJson(list);

		} catch (Exception e){
			return "";
		}

	}

	@Override
	public String getRelatedKwd(KsfParamVo paramvo) {
		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append(KSF_URL);
		sbUrl.append("suggest");
		sbUrl.append("?target=related");
		sbUrl.append("&term=" ).append( httpUtil.urlEncode(paramvo.getTerm(), CHARSET) );
		sbUrl.append("&domain_no=").append(paramvo.getDomain_no());
		sbUrl.append("&max_count=").append(paramvo.getMax_count());
		logger.debug(sbUrl.toString());
		StringBuffer sb = httpUtil.getUrlData(sbUrl.toString());

		return sb.toString();

	}

	@Override
	public String getSpellChek(String term) {
		StringBuilder sbUrl = new StringBuilder();
		sbUrl.append(KSF_URL);
		sbUrl.append("suggest");
		sbUrl.append("?target=spell");
		sbUrl.append("&term=").append(httpUtil.urlEncode(term) );
		logger.debug(sbUrl.toString());
		StringBuffer sb = httpUtil.getUrlData(sbUrl.toString());

		return sb.toString();
	}

}
