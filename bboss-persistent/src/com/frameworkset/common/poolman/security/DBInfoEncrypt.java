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

/**
 * 
 * <p>Title: DBInfoEncrypt.java</p>
 *
 * <p>Description: ���������ݿ���Ϣ���м��ܵĳ����࣬Ӧ��ϵͳ����ͨ���ò�������ݿ��ַ���˺ţ�������Ϣ���м���
 * </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 * @Date 2012-3-14 ����3:08:09
 * @author biaoping.yin
 * @version 1.0
 */
public interface DBInfoEncrypt {
	/**
	 * �������ݽӿ�
	 * @param data
	 * @return
	 */
	public abstract String encrypt(String data);
	/**
	 * �������ݽӿ�
	 * @param data
	 * @return
	 */
	public abstract String decrypt(String data);
	
	/**
	 * �������ݽӿ�
	 * @param data
	 * @return
	 */
	public abstract String encryptDBUrl(String url);
	/**
	 * �������ݽӿ�
	 * @param data
	 * @return
	 */
	public abstract String decryptDBUrl(String url);
	
	/**
	 * �������ݽӿ�
	 * @param data
	 * @return
	 */
	public abstract String encryptDBPassword(String password);
	/**
	 * �������ݽӿ�
	 * @param data
	 * @return
	 */
	public abstract String decryptDBPassword(String password);
	
	/**
	 * �������ݽӿ�
	 * @param data
	 * @return
	 */
	public abstract String encryptDBUser(String user);
	/**
	 * �������ݽӿ�
	 * @param data
	 * @return
	 */
	public abstract String decryptDBUser(String user);
	
	

}
