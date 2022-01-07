package com.konantech.ksearch.config;

import java.lang.reflect.Field;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.konantech.ksearch.constants.SearchConstant;


public class SearchConstConfigurer {
	private static final Logger logger = LoggerFactory.getLogger(SearchConstConfigurer.class);

	/** context-bean.xml 에서 설정 */
	@Autowired
	private Properties		searchConfig;

	/**
	 * 생성자.
	 */
	private SearchConstConfigurer()
	{
	}

	/**
	 * 상수값 할당.
	 *
	 * @throws	Exception	예외
	 */
	public void init () throws Exception
	{
		logger.info	(">>>>>>>>>>>>>>>>>>>>>>>>> begin ...");
		//------------
		// 필수
		//------------
		SearchConstant.SITE				= this.searchConfig.getProperty("SITE");
		SearchConstant.CHARSET		= this.searchConfig.getProperty("CHARSET");
		SearchConstant.CREDENTIAL		= this.searchConfig.getProperty("CREDENTIAL");
		SearchConstant.SEARCH_URL		= this.searchConfig.getProperty("SEARCH_URL");
		SearchConstant.KLA_URL		= this.searchConfig.getProperty("KLA_URL");
		SearchConstant.KSF_URL		= this.searchConfig.getProperty("KSF_URL");
		SearchConstant.KONANBOT_URL		= this.searchConfig.getProperty("KONANBOT_URL");
		SearchConstant.KONANBOT_DEFAULT_DOMAIN		= this.searchConfig.getProperty("KONANBOT_DEFAULT_DOMAIN");
		SearchConstant.KONANBOT_DEFAULT_USER		= this.searchConfig.getProperty("KONANBOT_DEFAULT_USER");
		SearchConstant.TIMEOUT		=  Integer.parseInt(this.searchConfig.getProperty("TIMEOUT") );
	}

	/**
	 * 할당된 상수값 확인.
	 */
	public void print ()
	{
		logger.info	("---------------------------------------------- begin ...");

		try {

			Class<?> clazz =  Class.forName(new SearchConstant().getClass().getName() );
		//	logger.info("Class: " + clazz);
			 Field[] fields = clazz.getDeclaredFields();
			 int fieldCount = fields==null ? 0 : fields.length;
			 String fieldNm, value;
			 Field field;
			 for (int i=0;  i<fieldCount;  i++) {
				 fieldNm = fields[i].getName();
				 field = clazz.getDeclaredField(fieldNm);
				 value =  field.get(fieldNm).toString();
					logger.info(fieldNm+":" + value);
			}


		}catch (Exception  e) {
			e.printStackTrace();
			logger.error ("print", e);
		}

		 logger.info	("---------------------------------------------- end ...");
	}
}
