package com.konantech.ksearch.constants;

public class SearchConstant {

	//#########################################################################
	// 설정파일 ksearch_context 설정
	//#########################################################################
	public static String	SITE;
	//charset
	public static String   CHARSET;
	//Security
	public static String	CREDENTIAL;
	//검색엔진 URL
	public static String	SEARCH_URL;
	//KSF URL
	public static String   KSF_URL;
	//KLA URL
	public static String   KLA_URL;
	//챗봇 URL
	public static String   KONANBOT_URL;
	//챗봇 Domain
	public static String   KONANBOT_DEFAULT_DOMAIN;
	//챗봇 사용자
	public static String   KONANBOT_DEFAULT_USER;
	/** 검색서버 연결 대기 시간 */
	public static int TIMEOUT;

	//#########################################################################
	// 설정파일
	//#########################################################################
	public static final int DEFAULT_PAGE_SIZE = 10;

	public static final int TOTAL_PAGE_SIZE = 3;

	public static final String CHARSET_UTF8                     = "utf-8";

	public static final String CONTENT_TYPE_APPLICATION_JSON    = "application/json;charset=utf-8";

	public static final String CONTENT_TYPE_FORM_POST                 = "application/x-www-form-urlencoded; charset=UTF-8";

	public static final String HTTP_METHOD_POST                = "POST";

	public static final String HTTP_METHOD_GET                = "GET";

	public static final String HTTP_CONTENT_TYPE                = "Content-Type";

	public static final String HTTP_CONTENT_LENGTH               = "Content-Length";

	public static final String SEARCH_WARNING                = "\\(WARNING: EVALUATION COPY\\[SEARCH\\]\\)";

	public static final int HTTP_OK                             = 200;

	public static final String KLA_API_JSP  							="/ksm/kla/api/data.jsp";

	public static final String KWD_DELIMITER                     = ",";

	public static final String URL_SEPARATOR                    = "\\?";

	//#########################################################################
	// 챗봇
	//#########################################################################
	public static final String KONANBOT_OPEN  							="chat/open";
	public static final String KONANBOT_MESSAGE 						="chat/message";

	//#########################################################################
	// 통합검색 메시지 상수
	//#########################################################################
	public static String MSG_SEARCH_ERROR	= "조회 시 오류가 발생하였습니다.";

    /**
	 * 생성자.
	 */
	public SearchConstant()
	{
	}

}
