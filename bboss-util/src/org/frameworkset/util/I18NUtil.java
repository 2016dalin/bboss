/**
 *  Copyright 2008 biaoping.yin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.  
 */
package org.frameworkset.util;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.frameworkset.util.i18n.DefaultI18N;
import org.frameworkset.util.i18n.I18n;

/**
 * <p>I18NUtil.java</p>
 * <p> Description: </p>
 * <p> bboss workgroup </p>
 * <p> Copyright (c) 2005-2013 </p>
 * 
 * @Date 2013��10��23��
 * @author biaoping.yin
 * @version 1.0
 */
public class I18NUtil {
	private static Logger log = Logger.getLogger(I18NUtil.class);
	private static I18n i18n;
	static
	{
		try {
			i18n = (I18n) Class.forName("org.frameworkset.web.servlet.i18n.I18nImpl").newInstance();
		} catch (Exception e) {
			log.warn("class org.frameworkset.web.servlet.i18n.I18nImpl not found in classpath,use DefaultI18N. ");
			i18n = new DefaultI18N();
		}
	}
	public I18NUtil() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * ����code��mvc�Ĺ��ʻ������ļ��л�ȡ��Ӧ���ԵĴ���ֵ
	 * @param code
	 * @param request
	 * @return
	 */
	public static String getI18nMessage(String code,HttpServletRequest request)
	{
		return i18n.getI18nMessage(code,(String )null,request);
		
		
	}
	/**
	 * ����code��mvc�Ĺ��ʻ������ļ��л�ȡ��Ӧ���ԵĴ���ֵ,�������ֵΪ�գ��򷵻�defaultMessage
	 * @param code
	 * @param defaultMessage
	 * @param request
	 * @return
	 */
	public static String getI18nMessage(String code,String defaultMessage,HttpServletRequest request)
	{
		return i18n.getI18nMessage(code,defaultMessage,request);
		
		
	}

	public static Locale getRequestContextLocal(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return i18n.getRequestContextLocal(request);
	}

}
