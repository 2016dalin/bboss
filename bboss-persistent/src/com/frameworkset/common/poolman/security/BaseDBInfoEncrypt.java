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
 * 4
 * <p>Title: BaseDBInfoEncrypt.java</p>
 *
 * <p>Description: Ĭ��db���ܲ��ʵ���࣬�����ݲ����ܣ�Ӧ��ϵͳ���Լ̳и���
 * ʵ������ļӽ��ܽӿڣ�ʵ��decrypt��encrypt����
 * </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 * @Date 2012-3-14 ����3:12:16
 * @author biaoping.yin
 * @version 1.0
 */
public abstract class BaseDBInfoEncrypt implements DBInfoEncrypt{

	public String encrypt(String data) {
		
		try {
			return new DESCipher().encrypt(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	public String decrypt(String data){
		
		try {
			return new DESCipher().decrypt(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	public String encryptDBUrl(String url) {
		// TODO Auto-generated method stub
		return url;
	}

	public String decryptDBUrl(String url) {
		// TODO Auto-generated method stub
		return url;
	}

	public String encryptDBPassword(String password) {
		// TODO Auto-generated method stub
		return password;
	}

	public String decryptDBPassword(String password) {
		// TODO Auto-generated method stub
		return password;
	}

	public String encryptDBUser(String user) {
		// TODO Auto-generated method stub
		return user;
	}

	public String decryptDBUser(String user) {
		// TODO Auto-generated method stub
		return user;
	}

}
