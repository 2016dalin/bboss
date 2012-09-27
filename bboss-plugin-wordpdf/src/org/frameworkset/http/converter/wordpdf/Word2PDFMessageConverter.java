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
package org.frameworkset.http.converter.wordpdf;

import java.io.IOException;
import java.util.List;

import org.frameworkset.http.HttpInputMessage;
import org.frameworkset.http.HttpOutputMessage;
import org.frameworkset.http.MediaType;
import org.frameworkset.http.converter.HttpMessageConverter;
import org.frameworkset.http.converter.HttpMessageNotReadableException;
import org.frameworkset.http.converter.HttpMessageNotWritableException;



/**
 * <p> Word2PDFMessageConverter.java</p>
 * <p> Description: 
 * 1.wordתpdf�����ṩ��������ع���
 * 2.wordģ�������ݣ���תpdf�����ṩ��������ع���
 * 3.wordתflash��ʽ�ļ�
 * </p>
 * <p> bboss workgroup </p>
 * <p> Copyright (c) 2009 </p>
 * 
 * @Date 2012-9-17 ����8:29:53
 * @author biaoping.yin
 * @version 1.0
 */
public class Word2PDFMessageConverter <T> implements HttpMessageConverter<T>{
	/**
	 * ָ��ȫ����ʱ�ļ�Ŀ¼
	 */
	private String tempDir;
	private boolean openoffice = false;

	@Override
	public boolean canRead(Class<?> clazz, MediaType mediaType) {
		// TODO Auto-generated method stub
		return WordResponse.class.isAssignableFrom(clazz);
	}

	@Override
	public boolean canWrite(Class<?> clazz, MediaType mediaType) {
		// TODO Auto-generated method stub
		return WordResponse.class.isAssignableFrom(clazz);
	}

	@Override
	public List<MediaType> getSupportedMediaTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T read(Class<? extends T> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void write(T t, MediaType contentType,
			HttpOutputMessage outputMessage, HttpInputMessage inputMessage,
			boolean usecustomMediaTypeByMethod) throws IOException,
			HttpMessageNotWritableException {
		// TODO Auto-generated method stub
		if(t instanceof WordResponse)
		{
			WordResponse tmp = (WordResponse)t ;
			tmp.setConvter(this);
			if(tmp.getTempdir() == null)
			{
				if(this.tempDir != null)
				{
					tmp.setTempdir(this.tempDir);
				}
			}
			tmp._resonse(outputMessage, inputMessage);
			
		}
	}

	public String getTempDir() {
		return tempDir;
	}

	public void setTempDir(String tempDir) {
		this.tempDir = tempDir;
	}

	public boolean isOpenoffice() {
		return openoffice;
	}

	public void setOpenoffice(boolean openoffice) {
		this.openoffice = openoffice;
	}
	
	

}
