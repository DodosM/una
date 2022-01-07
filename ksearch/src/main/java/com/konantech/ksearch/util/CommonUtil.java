package com.konantech.ksearch.util;

import static com.konantech.ksearch.constants.SearchConstant.SITE;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.konantech.ksearch.web.service.impl.KlaServiceImpl;
import com.konantech.ksearch.web.vo.SearchParamVo;
import com.sun.xml.internal.ws.util.StringUtils;
import static com.konantech.ksearch.constants.SearchConstant.KWD_DELIMITER;

@Component
public class CommonUtil {

	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	/**
	 * 현재날짜 조회
	 * @param format
	 * @return
	 */
    public String getDateFormat( String format) {
    	LocalDateTime ldt = LocalDateTime.now();
    	DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
    	return ldt.toString(fmt);
    }

	/**
	 * 현재날짜를 기준으로 이전날짜를 조회한다.
	 * @param iDay
     * @param format
	 * @return
	 */
    public String getDateFormat(int iDay, String format) {
    	LocalDateTime ldt = LocalDateTime.now().minusDays(iDay);
    	DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
    	return ldt.toString(fmt);
    }

    /**
     * 현재날짜를 기준으로 이전날짜를 조회한다.
     * @param duration
     * @param format
     * @return
     */
    public String getDateMinusFormat(String duration,  String format) {
    	return getDateMinusFormat(duration, format,  1);
    }

	/**
	 * 현재날짜를 기준으로 이전날짜를 조회한다.
	 * @param duration
	 * @param format
	 * @param num
	 * @return
	 */
    public String getDateMinusFormat(String duration,  String format, int num) {
    	LocalDateTime ldt;
    	switch (duration) {
		case "daily":
		case "d":
			ldt = LocalDateTime.now().minusDays(num);
			break;
		case "weekly":
		case "w":
			ldt = LocalDateTime.now().minusWeeks(num);
			break;
		case "monthly":
		case "m":
		case "3m":
			ldt = LocalDateTime.now().minusMonths(num);
			break;
		case "yearly":
		case "y":
			ldt = LocalDateTime.now().minusYears(num);
			break;
		default:
			ldt = LocalDateTime.now().minusDays(num);
			break;
		}

    	DateTimeFormatter fmt = DateTimeFormat.forPattern(format);
    	return ldt.toString(fmt);
    }

    /**
     * 검색엔진 검색로그 로그포맷
     * ;; site, category, userid,gender,keyword_type, search_type, pagenum, sort, keyword, pre_keyword
     * userdef_pattern = (1)@(2)+(3)$(4)|(5)|(6)|(7)^(8)]##(9)
     * 							(site)@(category)+(userid)$(gender)|(search_type)|(pagenum)|(sort)^(keyword)]##(pre_keyword)
     *                    			KONANSEARCH@+user1$1|첫검색|0|정확도^추천]##
     * @param paramvo
     * @return
     */
    public String getCustomLog(SearchParamVo paramvo) {
    	StringBuffer sb = new StringBuffer();

    	sb.append(SITE);
    	sb.append("@");
    	sb.append(paramvo.getCategory());
    	sb.append("+");
    	sb.append(paramvo.getUserid());
    	sb.append("$");
    	sb.append(paramvo.getGender());
    	sb.append("|");
    	sb.append(!paramvo.isResrch() ? "첫검색":"재검색");
    	sb.append("|");
    	sb.append(paramvo.getPage());
    	sb.append("|");
    	sb.append(paramvo.getSortNm());
    	sb.append("^");
    	sb.append(paramvo.getKwd());
    	sb.append("]##");
    	if (paramvo.isResrch() ) {
    		String[] prekwds =  paramvo.getPreKwds().split(",");
    		int idx = prekwds.length;
    		sb.append(prekwds[idx - 2]);
    	}
    	logger.debug(sb.toString());
    	return sb.toString();
    }


    /**
     * 검색엔진 쿼리
     * 상세검색에서 필수, 포함, 제외검색 상세 검색 쿼리
     * @param paramvo
     * @param strNmFd
     * @return
     */
    public String makeDetailQuery(SearchParamVo paramvo, String strNmFd) {
    	StringBuffer sb = new StringBuffer();

		String[] kwds = null;

		if(!paramvo.getBasickwd().isEmpty()) {
			sb.append(strNmFd);
			sb.append("='");
			sb.append(paramvo.getBasickwd().replaceAll(",", " "));
			sb.append("' ").append("allword synonym ");
		}

		if(!paramvo.getExactkwd().isEmpty()) {
			if(paramvo.getExactkwd().contains(KWD_DELIMITER)) {
				kwds = paramvo.getExactkwd().split(KWD_DELIMITER);
			}
			kwds[0] = paramvo.getExactkwd();
			for(String s: kwds ) {
				if(sb.length() > 0) {
					sb.append(" AND ");
				}
				sb.append(strNmFd);
				sb.append("='");
				sb.append(s);
				sb.append("' ").append("allorder ");
			}
		}

		if(!paramvo.getInkwd().isEmpty()) {
			if(paramvo.getInkwd().contains(KWD_DELIMITER)) {
				kwds = paramvo.getInkwd().split(KWD_DELIMITER);
			}
			kwds[0] = paramvo.getInkwd();
			for(String s: kwds ) {
				if(sb.length() > 0) {
					sb.append(" AND ");
				}
				sb.append(strNmFd);
				sb.append("='");
				sb.append(s);
				sb.append("' ").append("allword ");
			}
		}

		if(!paramvo.getNotkwd().isEmpty()) {
			if(paramvo.getNotkwd().contains(KWD_DELIMITER)) {
				kwds = paramvo.getNotkwd().split(KWD_DELIMITER);
			}
			kwds[0] = paramvo.getNotkwd();
			for(String s: kwds ) {
				if(sb.length() > 0) {
					sb.append(" ANDNOT ");
				}
				sb.append(strNmFd);
				sb.append("='");
				sb.append(s);
				sb.append("' ").append("allword ");
			}

		}

    	logger.debug(sb.toString());
    	return sb.toString();
    }


    /**
     * 결과내재검색을 위한 쿼리
     * @param paramvo
     * @param strNmFd
     * @return
     */
    public String makeReSearchQuery(SearchParamVo paramvo, String strNmFd) {
    	StringBuffer sb = new StringBuffer();
		String[] kwds = null;
		if(!paramvo.getPreKwds().isEmpty()) {
			if(paramvo.getPreKwds().contains(KWD_DELIMITER)) {
				kwds = paramvo.getPreKwds().split(KWD_DELIMITER);
			}else {
				kwds = new String[0];
				kwds[0]= paramvo.getPreKwds();
			}
			for(String s: kwds ) {
				if(s.equals(paramvo.getKwd())) continue;
				if(sb.length() > 0) {
					sb.append(" AND ");
				}
				sb.append(strNmFd);
				sb.append("='");
				sb.append(s);
				sb.append("' ").append("allword synonym ");
			}
		}

    	logger.debug(sb.toString());
    	return sb.toString();
    }


}
