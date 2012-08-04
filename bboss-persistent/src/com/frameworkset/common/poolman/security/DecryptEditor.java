/*
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
package com.frameworkset.common.poolman.security;

import com.frameworkset.util.EditorInf;

/**
 * 
 * <p>Title: DecryptEditor.java</p>
 *
 * <p>Description: ����Ϣ���н��ܵ����Ա༭������Ҫ�û��������ӳ��˺���Ϣ���м��ܵ���ز���</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 * @Date 2012-7-31 ����11:15:40
 * @author biaoping.yin
 * @version 1.0
 */
public class DecryptEditor implements EditorInf {

	public Object getValueFromObject(Object fromValue) {
		return getValueFromString((String )fromValue) ;
	}

	public Object getValueFromString(String fromValue) {
		try {
			return new DESCipher().decrypt((String)fromValue);
		} catch (Exception e) {
			return fromValue;
		}
	}

}
