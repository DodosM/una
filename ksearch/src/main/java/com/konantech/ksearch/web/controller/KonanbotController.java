package com.konantech.ksearch.web.controller;

import static com.konantech.ksearch.constants.SearchConstant.CONTENT_TYPE_APPLICATION_JSON;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.konantech.ksearch.web.service.KonanbotService;
import com.konantech.ksearch.web.vo.KonanbotParamVo;

/**
 * Class Name : KonanbotController.java
 * Description : Konanbot 챗봇위한 컨트롤러
 *
 * Modification Information
 *
 * 수정일                        수정자           수정내용
 * --------------------  -----------  ---------------------------------------
 *                            @author ksh  최초작성
 * Copyright ⓒ KONAN TECHNOLOGY INC. All rights reserved.
 */

@RestController
public class KonanbotController {

	private static final Logger logger = LoggerFactory.getLogger(KonanbotController.class);

	@Autowired
	private KonanbotService konanbotService;

	/**
	 * konanbot_초기인사
	 * @param paramvo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/chat/open", produces=CONTENT_TYPE_APPLICATION_JSON)
	public String getOpen(@RequestBody KonanbotParamVo paramvo , Model model) {

		String result = null;
		result = konanbotService.getOpen(paramvo);
		return result;
	}

	/**
	 * konanbot_message
	 * @param paramvo
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/chat/message", produces=CONTENT_TYPE_APPLICATION_JSON)
	public String getMessage(@RequestBody KonanbotParamVo paramvo , Model model) {
		String result = null;
		result = konanbotService.getMessage(paramvo);

		return result;
	}
}
