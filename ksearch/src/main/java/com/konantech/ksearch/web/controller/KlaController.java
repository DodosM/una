package com.konantech.ksearch.web.controller;

import static com.konantech.ksearch.constants.SearchConstant.CONTENT_TYPE_APPLICATION_JSON;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.konantech.ksearch.util.CommonUtil;
import com.konantech.ksearch.web.service.KlaService;
import com.konantech.ksearch.web.vo.KlaParamVo;
import com.konantech.ksearch.web.vo.KsfParamVo;
import com.konantech.ksearch.web.vo.LogflowParamVo;

/**
 * Class Name : KlaController.java
 * Description : 로그분석기 API를 조회하기 위한 컨트롤러
 *
 * Modification Information
 *
 * 수정일                        수정자           수정내용
 * --------------------  -----------  ---------------------------------------
 *                                 @author ksh  최초작성
 * konan
 */

@RestController
public class KlaController {

	private static final Logger logger = LoggerFactory.getLogger(KlaController.class);

	@Autowired
	private KlaService klaService;

	@Autowired
	private CommonUtil comUtil;

	/**
	 * kla 클릭로그 저장
	 * @param paramvo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/kla/click", produces=CONTENT_TYPE_APPLICATION_JSON, method = RequestMethod.POST)
	public void saveClick(@ModelAttribute LogflowParamVo paramvo , Model model) {
		logger.info(">>>>>>>>>>>>>> get params!! "+ paramvo);
		klaService.saveLogflow(paramvo);
	}

	/**
	 * kla 급상승키워드 조회
	 * @param paramvo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/kla/rankup", produces=CONTENT_TYPE_APPLICATION_JSON, method = RequestMethod.GET)
	public String getRankUpper(@ModelAttribute KlaParamVo paramvo , Model model) {
		//날짜입력
		//paramvo.setDuration("weekly");
		//paramvo.setDatenum(1);
		this.setDate(paramvo);
		String result = klaService.getRankUpper(paramvo);

		return result;
	}

	/**
	 * kla 급상승키워드 조회
	 * @param paramvo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/kla/idrank", produces=CONTENT_TYPE_APPLICATION_JSON, method = RequestMethod.GET)
	public String getDocidRank(@ModelAttribute KlaParamVo paramvo , Model model) {
		//날짜입력
		//paramvo.setDuration("weekly");
		//paramvo.setDatenum(6);
		this.setDate(paramvo);
		String result = klaService.getDocidRankContent(paramvo);

		return result;
	}




	/**
	 * 조회기간을 이용하여 조회기간을 설정한다.
	 * @param paramvo
	 */
	private void setDate(KlaParamVo paramvo) {
		paramvo.setFromDate(comUtil.getDateMinusFormat(paramvo.getDuration(), "yyyy-MM-dd", paramvo.getDatenum()));
		paramvo.setToDate(comUtil.getDateFormat("yyyy-MM-dd"));

	}


}
