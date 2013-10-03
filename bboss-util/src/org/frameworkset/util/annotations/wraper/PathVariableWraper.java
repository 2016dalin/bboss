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
package org.frameworkset.util.annotations.wraper;

import org.frameworkset.util.annotations.AnnotationUtils;
import org.frameworkset.util.annotations.PathVariable;

/**
 * <p>PathVariableWraper.java</p>
 * <p> Description: </p>
 * <p> bboss workgroup </p>
 * <p> Copyright (c) 2005-2013 </p>
 * 
 * @Date 2013��10��3��
 * @author biaoping.yin
 * @version 1.0
 */
public class PathVariableWraper {
	private String value;
	private String editor;
	private String defaultvalue;
	/**
	 * �����ַ���
	 * @return
	 */
	private String decodeCharset;
	
	/**
	 * ����ԭʼ�ַ���
	 * @return
	 */
	private String charset;
	
	/**
	 * ����ת���ַ���
	 * @return
	 */
	private String convertcharset;
	/**
	 * ָ�����ڸ�ʽ
	 * @return
	 */
	private String dateformat;
	public PathVariableWraper(PathVariable pv) {
		 value = pv.value();
		 editor = pv.editor();
		 defaultvalue = AnnotationUtils.converDefaultValue(pv.defaultvalue());
		/**
		 * �����ַ���
		 * @return
		 */
		 decodeCharset = AnnotationUtils.converDefaultValue(pv.decodeCharset());
		
		/**
		 * ����ԭʼ�ַ���
		 * @return
		 */
		 charset = AnnotationUtils.converDefaultValue(pv.charset());
		
		/**
		 * ����ת���ַ���
		 * @return
		 */
		 convertcharset = AnnotationUtils.converDefaultValue(pv.convertcharset());
		/**
		 * ָ�����ڸ�ʽ
		 * @return
		 */
		 dateformat = AnnotationUtils.converDefaultValue(pv.dateformat());
	}
	
	public String value(){
		return this.value;
	}
	public String editor(){
		return this.editor;
	}
	public String defaultvalue(){
		return this.defaultvalue;
	}
	/**
	 * �����ַ���
	 * @return
	 */
	public String decodeCharset(){
		return this.decodeCharset;
	}
	
	/**
	 * ����ԭʼ�ַ���
	 * @return
	 */
	public String charset(){
		return this.charset;
	}
	
	/**
	 * ����ת���ַ���
	 * @return
	 */
	public String convertcharset(){
		return this.convertcharset;
	}
	/**
	 * ָ�����ڸ�ʽ
	 * @return
	 */
	public String dateformat(){
		return this.dateformat;
	}

}
