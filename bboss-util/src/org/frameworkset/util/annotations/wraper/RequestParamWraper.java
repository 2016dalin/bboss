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
import org.frameworkset.util.annotations.RequestParam;

/**
 * <p>RequestParamWraper.java</p>
 * <p> Description: </p>
 * <p> bboss workgroup </p>
 * <p> Copyright (c) 2005-2013 </p>
 * 
 * @Date 2013��10��3��
 * @author biaoping.yin
 * @version 1.0
 */
public class RequestParamWraper {
	/**
	 * �������ƣ�Ĭ��Ϊ""
	 * @return
	 */
	private String name;
	
	private boolean required;
	private String editor;
	/**
     * ���ݸ�ʽ
     */
	private String dataformat;
	/**
	 * ���ڸ�ʽ
     */
	private String dateformat;
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
	public RequestParamWraper(RequestParam param) {
		/**
		 * �������ƣ�Ĭ��Ϊ""
		 * @return
		 */
		 name = param.name();
		
		 required = param.required();
		 editor = param.editor();
		/**
	     * ���ݸ�ʽ
	     */
		 dataformat = param.dataformat();
		/**
		 * ���ڸ�ʽ
	     */
		 dateformat = param.dateformat();
		 defaultvalue = AnnotationUtils.converDefaultValue(param.defaultvalue());
		/**
		 * �����ַ���
		 * @return
		 */
		 decodeCharset = AnnotationUtils.converDefaultValue(param.decodeCharset());
		
		/**
		 * ����ԭʼ�ַ���
		 * @return
		 */
		 charset = AnnotationUtils.converDefaultValue(param.charset());
		
		/**
		 * ����ת���ַ���
		 * @return
		 */
		 convertcharset = AnnotationUtils.converDefaultValue(param.convertcharset());
	}
	/**
	 * �������ƣ�Ĭ��Ϊ""
	 * @return
	 */
	public String name(){
		return this.name;
	}
	
	public boolean required(){
		return this.required;
	}
	public String editor(){
		return this.editor;
	}
	/**
     * ���ݸ�ʽ
     */
	public String dataformat(){
		return this.dataformat;
	}
	/**
	 * ���ڸ�ʽ
     */
	public String dateformat(){
		return this.dateformat;
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

}
