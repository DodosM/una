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

import com.konantech.ksearch.web.service.KsfService;
import com.konantech.ksearch.web.vo.KsfParamVo;

/**
 * Class Name : KsfController.java
 * Description : KSF를 통해 조회하기 위한 컨트롤러
 *
 * Modification Information
 *
 * 수정일                        수정자           수정내용
 * --------------------  -----------  ---------------------------------------
 *                                 @author ksh  최초작성
 * konan
 */

@RestController
public class KsfController {

	private static final Logger logger = LoggerFactory.getLogger(KsfController.class);

	@Autowired
	private KsfService ksfService;

	/**
	 * ksf_자동완성
	 * @param paramvo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ksf/akc", produces=CONTENT_TYPE_APPLICATION_JSON, method = RequestMethod.GET)
	public String getCompleteKwd(@ModelAttribute KsfParamVo paramvo , Model model) {

		String result = null;

		if( !paramvo.getTerm().isEmpty()) {
			result = ksfService.getAutocomplete(paramvo);
		}

		return result;
	}

	/**
	 * ksf_인기검색어
	 * @param paramvo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ksf/ppk", produces=CONTENT_TYPE_APPLICATION_JSON, method = RequestMethod.GET)
	public String getPopularKwd(@ModelAttribute KsfParamVo paramvo , Model model) {

		String result = null;
		result = ksfService.getPopularKwd(paramvo);

		return result;
}

	/**
	 * ksf_추천검색어
	 * @param paramvo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/ksf/kre", produces=CONTENT_TYPE_APPLICATION_JSON, method = RequestMethod.GET)
	public String getRelatedKwd(@ModelAttribute KsfParamVo paramvo , Model model) {

		String result = null;

		if( !paramvo.getTerm().isEmpty()) {
			result = ksfService.getRelatedKwd(paramvo);
		}

		return result;
	}
}
