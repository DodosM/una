package com.konantech.ksearch.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import static com.konantech.ksearch.constants.SearchConstant.DEFAULT_PAGE_SIZE;
import static com.konantech.ksearch.constants.SearchConstant.TOTAL_PAGE_SIZE;
import com.konantech.ksearch.util.CommonUtil;
import com.konantech.ksearch.web.vo.SearchParamVo;
/**
 * Class Name : SearchController.java
 * Description : 통합검색 조회를 위한 컨트롤러
 *
 * Modification Information
 *
 * 수정일                        수정자           수정내용
 * --------------------  -----------  ---------------------------------------
 *                                 @author ksh  최초작성
 * konan
 */

@Controller
public class SearchController {

	@Autowired
	CommonUtil comUtil;

	private static final Logger logger = LoggerFactory.getLogger(SearchController.class);

	/*
	 * @RequestMapping(value = "/searchgo", method = RequestMethod.GET) public
	 * String searchGet(@ModelAttribute("params") SearchParamVo paramvo , Model
	 * model) {
	 * 
	 * //기본파라미터 설정 setDefaultParam(paramvo);
	 * logger.info(">>>>>>>>>>>>>> get params!! "+ paramvo); return "search"; }
	 */
	@RequestMapping(value = "/searchpo", method = {RequestMethod.GET, RequestMethod.POST})
	public String searchPost(@ModelAttribute("params") SearchParamVo paramvo , Model model) {
		//기본파라미터 설정
		setDefaultParam(paramvo);
		logger.info(">>>>>>>>>>>>>> post params!! "+ paramvo);
		return "search";
	}

	/**
	 * 조회기간을 이용하여 조회기간을 설정한다.
	 * @param paramvo
	 */
	private void setDefaultParam(SearchParamVo paramvo) {

		//페이지 사이즈
		if("total".equals(paramvo.getCategory())) {
			paramvo.setPageSize(TOTAL_PAGE_SIZE);
			paramvo.setOffset(0);
		}else {
			paramvo.setPageSize(DEFAULT_PAGE_SIZE);
			paramvo.setOffset( (paramvo.getPage()-1 )*paramvo.getPageSize() );
		}
/*
		//정렬
		if("r".equals(paramvo.getSort())) {
			paramvo.setSortNm("정확도순");
		}else if("c".equals(paramvo.getSort())) {
			paramvo.setSortNm("클릭순");
		}else {
			paramvo.setSortNm("최신순");
		}
*/
		if(null == paramvo.getDate() || "".equals(paramvo.getDate()) || "input".equals(paramvo.getDate())) {
			paramvo.setStartDate("");
			paramvo.setEndDate("");
			return;
		}

		int datenum =1;
		if("3m".equals(paramvo.getDate())) {
			datenum = 3;
		}

		paramvo.setStartDate(comUtil.getDateMinusFormat(paramvo.getDate(), "yyyyMMdd", datenum));
		paramvo.setEndDate(comUtil.getDateFormat("yyyyMMdd"));

	}
}
