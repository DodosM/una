package com.konantech.ksearch.util;

import static com.konantech.ksearch.constants.SearchConstant.CHARSET_UTF8;
import static com.konantech.ksearch.constants.SearchConstant.CONTENT_TYPE_FORM_POST;
import static com.konantech.ksearch.constants.SearchConstant.CONTENT_TYPE_APPLICATION_JSON;
import static com.konantech.ksearch.constants.SearchConstant.HTTP_CONTENT_LENGTH;
import static com.konantech.ksearch.constants.SearchConstant.HTTP_CONTENT_TYPE;
import static com.konantech.ksearch.constants.SearchConstant.HTTP_METHOD_POST;
import static com.konantech.ksearch.constants.SearchConstant.URL_SEPARATOR;
import static com.konantech.ksearch.constants.ExceptionConstant.KLA_EXCEPTION;
import static com.konantech.ksearch.constants.ExceptionConstant.SEARCH5_EXCEPTION;
import static com.konantech.ksearch.constants.ExceptionConstant.KONAN_EXCEPTION;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.konantech.ksearch.web.vo.SearchRestVo;

@Component
public class HttpUtil {

	private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);


	/**
	 *지정된 파라미터 값으로 데이터 호출 시 사용
	 * @param urlinfo
	 * @return
	 */
	public StringBuffer getUrlData(String urlinfo) {
		return getUrlData(urlinfo, CHARSET_UTF8);
	}

	/**
	 *지정된 파라미터 값으로 데이터 호출 시 사용
	 * @param urlinfo url 주소
	 * @param charset 인코딩
	 * @return
	 */
	public StringBuffer getUrlData(String urlinfo, String charset) {
        URL url =null;
		HttpURLConnection conn = null;
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();

		// 조회
		try{
			url = new URL( urlinfo);
			conn = (HttpURLConnection) url.openConnection();
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName(charset)));


			for(String line = br.readLine(); line != null ; line = br.readLine()){
				sb.append(line);
			}

			return sb;

		} catch (IOException ie) {
			logger.error(SEARCH5_EXCEPTION, ie);
			return  new StringBuffer();

		} finally {
			try{
				if( br != null) {
					br.close();
				}
			}catch(IOException ie){
				logger.error(SEARCH5_EXCEPTION, ie);
			}
			if( conn != null){
				conn.disconnect();
			}

			if( url != null){
				url = null;
			}
		}

	}

	/**
	 *지정된 파라미터 값으로 데이터 호출 시 사용
	 * @param urlinfo
	 * @return
	 */
	public StringBuffer getUrlDataPost(String urlinfo, String postData) {
		return getUrlDataPost(urlinfo, postData, CHARSET_UTF8);
	}


	/**
	 * 지정된 파라미터 값으로 데이터 호출 시 사용
	 * @param urlinfo
	 * @param postData
	 * @param charset
	 * @return
	 */
	public StringBuffer getUrlDataPost(String urlinfo, String postData, String charset) {
        URL url =null;
		HttpURLConnection conn = null;
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();

		// 조회
		try{
			url = new URL( urlinfo);
			conn = (HttpURLConnection) url.openConnection();
			byte[] postDataBytes = postData.toString().getBytes(CHARSET_UTF8);

			conn.setRequestMethod(HTTP_METHOD_POST);
	        conn.setRequestProperty(HTTP_CONTENT_TYPE, CONTENT_TYPE_FORM_POST);
	        conn.setRequestProperty(HTTP_CONTENT_LENGTH, String.valueOf(postDataBytes.length));
	        conn.setDoOutput(true);
	        conn.getOutputStream().write(postDataBytes); // POST 호출


			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName(charset)));


			for(String line = br.readLine(); line != null ; line = br.readLine()){
				sb.append(line);
			}

			return sb;

		} catch (IOException ie) {
			logger.error(SEARCH5_EXCEPTION, ie);
			return  new StringBuffer();
		} finally {
			try{
				if( br != null) {
					br.close();
				}
			}catch(IOException ie){
				logger.error(SEARCH5_EXCEPTION, ie);
			}
			if( conn != null){
				conn.disconnect();
			}

			if( url != null){
				url = null;
			}
		}

	}

	/**
	 * 지정된 파라미터 값으로 데이터 호출 시 사용
	 * @param urlinfo
	 * @param postData
	 * @param charset
	 * @return
	 */
	public StringBuffer getUrlDataPost(SearchRestVo restVo) {

		logger.debug(restVo.toString() );
        URL url =null;
		HttpURLConnection conn = null;
		OutputStreamWriter os = null;
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();
		StringBuffer sbData = new StringBuffer();

		try {
			sbData.append("select=").append(restVo.getSelectFields());
			sbData.append("&from=").append(restVo.getFrom());
			sbData.append("&where=").append(URLEncoder.encode(restVo.getWhere(),CHARSET_UTF8));
			sbData.append("&offset=").append(restVo.getOffset());
			sbData.append("&pagelength=").append(restVo.getPagelength());
			sbData.append("&hilite-fields=").append(URLEncoder.encode(restVo.getHilightFields(),CHARSET_UTF8));
			sbData.append("&custom=").append(URLEncoder.encode(restVo.getCustomLog(),CHARSET_UTF8) );
			sbData.append("&charset=").append(restVo.getCharset());
			System.out.println("쿼리문 " + sbData.toString());
		}catch (Exception e) {
			logger.error(SEARCH5_EXCEPTION, e);
			return  new StringBuffer();
		}
		logger.debug(sbData.toString());
		// 조회
		try{
			url = new URL( restVo.getUrl());
			conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod(HTTP_METHOD_POST);
	        conn.setRequestProperty(HTTP_CONTENT_TYPE, CONTENT_TYPE_FORM_POST);
	        conn.setDoOutput(true);

	        os = new OutputStreamWriter(conn.getOutputStream());
	        os.write(sbData.toString());
	        os.flush();

			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName(CHARSET_UTF8)));


			for(String line = br.readLine(); line != null ; line = br.readLine()){
				sb.append(line);
			}

			return sb;

		} catch (IOException ie) {
			logger.error(SEARCH5_EXCEPTION, ie);
			return  new StringBuffer();
		} finally {
			try{
				if( br != null) {
					br.close();
				}
				if( os != null) {
					os.close();
				}
			}catch(IOException ie){
				logger.error(SEARCH5_EXCEPTION, ie);
			}
			if( conn != null){
				conn.disconnect();
			}

			if( url != null){
				url = null;
			}
		}

	}



	public StringBuffer getUrlDataPost (String urlinfo) {
        URL url =null;
		HttpURLConnection conn = null;
		BufferedReader br = null;
		OutputStreamWriter os = null;
		StringBuffer sb = new StringBuffer();
		String[] str;

		try {
			str = urlinfo.split(URL_SEPARATOR);
		}catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}

		try{
			url = new URL(str[0] );
			conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod(HTTP_METHOD_POST);
	        conn.setRequestProperty(HTTP_CONTENT_TYPE, CONTENT_TYPE_FORM_POST);
	        conn.setDoOutput(true);

	        os = new OutputStreamWriter(conn.getOutputStream());
	        os.write(str[1]);
	        os.flush();

			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName(CHARSET_UTF8)));


			for(String line = br.readLine(); line != null ; line = br.readLine()){
				sb.append(line);
			}

			return sb;

		} catch (IOException ie) {
			logger.error(KONAN_EXCEPTION, ie);
			return  new StringBuffer();
		} finally {
			try{
				if( br != null) {
					br.close();
				}
				if( os != null) {
					os.close();
				}
			}catch(IOException ie){
				logger.error(SEARCH5_EXCEPTION, ie);
			}
			if( conn != null){
				conn.disconnect();
			}

			if( url != null){
				url = null;
			}
		}

	}

	/**
	 * Method Name : urlEncode
	 * Description : 통합검색 엔진에 호출하기 위한 URL 정보를 인코딩하여 반환한다.
	 *
	 * @param strUrl 검색 URL
	 * @param enc 인코딩
	 * @return String URL 인코딩처리하여 반환
	 */
	public String urlEncode(String strUrl) {
		String tmpUrl;
		try{
			tmpUrl = URLEncoder.encode(strUrl, CHARSET_UTF8);
			return tmpUrl;
		}catch(UnsupportedEncodingException us) {
			return "";
		}
	}

	/**
	 * Method Name : urlEncode
	 * Description : 통합검색 엔진에 호출하기 위한 URL 정보를 인코딩하여 반환한다.
	 *
	 * @param strUrl 검색 URL
	 * @param enc 인코딩
	 * @return String URL 인코딩처리하여 반환
	 */
	public String urlEncode(String strUrl, String enc) {
		String tmpUrl;
		try{
			if(null == enc ){
				tmpUrl = URLEncoder.encode(strUrl, CHARSET_UTF8);
			}else {
				tmpUrl = URLEncoder.encode(strUrl, enc);
			}
			return tmpUrl;
		}catch(UnsupportedEncodingException us) {
			return "";
		}
	}


	/**
	 * 로그분석기를 이용하여 클릭 이벤트 로그를 저장한다.
	 * @param urlinfo
	 * @return
	 */
	public boolean saveLogflowPost(String urlinfo) {
        URL url =null;
		HttpURLConnection conn = null;
		BufferedReader br = null;
		StringBuffer sb = new StringBuffer();

		try{
			url = new URL( urlinfo);
			conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod(HTTP_METHOD_POST);
	        conn.setRequestProperty(HTTP_CONTENT_TYPE, CONTENT_TYPE_FORM_POST);
	        conn.setDoOutput(true);
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), Charset.forName(CHARSET_UTF8)));


			for(String line = br.readLine(); line != null ; line = br.readLine()){
				sb.append(line);
			}

			return true;

		} catch (IOException ie) {
			logger.error(KLA_EXCEPTION, ie);
			return false;
		} finally {
			try{
				if( br != null) {
					br.close();
				}
			}catch(IOException ie){
				logger.error(KLA_EXCEPTION, ie);
			}
			if( conn != null){
				conn.disconnect();
			}

			if( url != null){
				url = null;
			}
		}

	}

}
