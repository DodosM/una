package com.konantech.ksearch.web.service.impl;

import static com.konantech.ksearch.constants.ExceptionConstant.KLA_EXCEPTION;
import static com.konantech.ksearch.constants.SearchConstant.KLA_API_JSP;
import static com.konantech.ksearch.constants.SearchConstant.KLA_URL;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.konantech.ksearch.util.HttpUtil;
import com.konantech.ksearch.web.service.KlaService;
import com.konantech.ksearch.web.vo.KlaParamVo;
import com.konantech.ksearch.web.vo.LogflowParamVo;

@Service("klaService")
public class KlaServiceImpl implements KlaService {

	private static final Logger logger = LoggerFactory.getLogger(KlaServiceImpl.class);

	@Autowired
	private HttpUtil httpUtil;


	//클릭로그 저장
	@Override
	public boolean saveLogflow( LogflowParamVo paramvo) {
		JsonObject json = new JsonObject();
		json.addProperty("section",httpUtil.urlEncode( paramvo.getSection()));
		json.addProperty("docid", paramvo.getDocid());
		json.addProperty("title", httpUtil.urlEncode(paramvo.getTitle()));
		json.addProperty("keyword", httpUtil.urlEncode(paramvo.getKeyword()));
		json.addProperty("targeturl", paramvo.getTargeturl());
		json.addProperty("user", paramvo.getUser());

		Gson gson = new Gson();


		StringBuffer sbUrl  = new StringBuffer();
		sbUrl.append(KLA_URL);
		sbUrl.append("/ksm/logflow/write");
		sbUrl.append("?type=").append(paramvo.getSite());
		sbUrl.append("&msg=").append( gson.toJson(json));

		logger.debug(sbUrl.toString());
		logger.debug(gson.toJson(json));

		return httpUtil.saveLogflowPost(sbUrl.toString());


	}

	//클릭로그 조회
	@Override
	public String getKeywordDocidRank(KlaParamVo paramvo) {
		//날짜 바인딩
		HttpUtil httpUtil = new HttpUtil();
		StringBuffer sbUrl  = new StringBuffer();
		sbUrl.append(KLA_URL);
		sbUrl.append(KLA_API_JSP);
		sbUrl.append("?target=keyword_docid_rank");
		sbUrl.append("&format=logflow");
		sbUrl.append("&output=json");
		sbUrl.append("&site=").append(paramvo.getSite());
		sbUrl.append("&category=").append(paramvo.getCategory());
		sbUrl.append("&keyword=").append(httpUtil.urlEncode(paramvo.getKeyword() ) );
		sbUrl.append("&fromDate=").append(paramvo.getFromDate());
		sbUrl.append("&toDate=").append( paramvo.getToDate());
		sbUrl.append("&count=").append(paramvo.getCount());


		//sbUrl.setLength(0);
		//sbUrl.append("http://127.0.0.1:7581/ksm/kla/api/data.jsp?output=json&format=logflow&target=keyword_docid_rank&site=sample_site&category=&fromDate=2019-05-13&toDate=2020-05-13&keyword=명사&count=10");
		logger.debug(sbUrl.toString());

		StringBuffer sbData = httpUtil.getUrlData(sbUrl.toString());

		try {
			Gson gson = new Gson();
			JsonObject json = gson.fromJson( sbData.toString(), JsonObject.class);
			JsonObject obj = json.get("result").getAsJsonObject();
			if(obj.get("list_count").getAsInt() > 0) {
				obj = obj.get("list").getAsJsonArray().get(0).getAsJsonObject();
				String docid = obj.get("docid_list").toString();

				docid =  docid.replaceAll("\\[", "");
				docid =  docid.replaceAll("\\]", "");

				logger.debug( docid );
				return docid;
			}else {
				return "";
			}

		}catch (Exception e) {
			logger.error(KLA_EXCEPTION, e);
			return "";
		}
	}
	//클릭한콘텐츠 조회
	@Override
	public String getDocidRankContent(KlaParamVo paramvo) {
		StringBuffer sbUrl  = new StringBuffer();
		sbUrl.append(KLA_URL);
		sbUrl.append(KLA_API_JSP);
		sbUrl.append("?target=docid_rank");
		sbUrl.append("&format=logflow");
		sbUrl.append("&output=json");
		sbUrl.append("&site=").append(paramvo.getSite());
		sbUrl.append("&category=").append(paramvo.getCategory());
		sbUrl.append("&keyword=").append(httpUtil.urlEncode(paramvo.getKeyword() ) );
		sbUrl.append("&fromDate=").append(paramvo.getFromDate());
		sbUrl.append("&toDate=").append( paramvo.getToDate());
		sbUrl.append("&count=").append(paramvo.getCount());
		sbUrl.append("&select_field=title,targeturl");

		logger.debug(sbUrl.toString());
		String result = getDataKla(sbUrl.toString());
		//result = result.replace("\\u003d", "=");
		//logger.debug(result);

		return result;
	}

	//급상승키워드 조회
	@Override
	public String getRankUpper(KlaParamVo paramvo) {
		StringBuffer sbUrl  = new StringBuffer();
		sbUrl.append(KLA_URL);
		sbUrl.append(KLA_API_JSP);
		sbUrl.append("?site=").append(paramvo.getSite());
		sbUrl.append("&target=rank_upper");
		sbUrl.append("&format=").append(paramvo.getFormat());
		sbUrl.append("&duration=").append(paramvo.getDuration());
		sbUrl.append("&category=").append(paramvo.getCategory());
		sbUrl.append("&output=").append(paramvo.getOutput());
		sbUrl.append("&fromDate=").append(paramvo.getFromDate());
		sbUrl.append("&toDate=").append( paramvo.getToDate());
		sbUrl.append("&count=").append(paramvo.getCount());
		logger.debug(sbUrl.toString());
		return getDataKla(sbUrl.toString());
	}


	/**
	 * kla api 이용하여 json 결과의 list항목의 Map 형태로 리턴한다.
	 * @param url
	 * @return
	 */
	private String getDataKla(String url) {

		StringBuffer sbData = httpUtil.getUrlData(url);
		try {
			Gson gson =new GsonBuilder().disableHtmlEscaping().create();
			JsonObject json = gson.fromJson( sbData.toString(), JsonObject.class);
			JsonObject obj = json.get("result").getAsJsonObject();
			if(obj.get("list_count").getAsInt() > 0) {
				JsonArray arr = obj.get("list").getAsJsonArray();
				List<Map<String, String>> list = gson.fromJson(arr, new TypeToken<List<Map<String, String>>>() {}.getType());
				return gson.toJson(list);
			}else {
				return "";
			}

		}catch (Exception e) {
			logger.error(KLA_EXCEPTION, e);
			return "";
		}

	}

}
