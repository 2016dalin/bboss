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
package com.frameworkset.common.poolman;

/**
 * 
 * <p>Title: CUDResult.java</p>
 *
 * <p>Description: �������ݿ���ɾ�ĵĽ����Ϣ��GetCUDResult�����Ժ������£�
	 * result������������������ԴautoprimarykeyΪtrue��������tableinfo���б����˱��������ϢresultΪ��������������֮resultΪ���µļ�¼��
	 * updateCount:���µļ�¼��
	 * keys:�Զ����������������ֻ��һ����¼��Ϊ��ͨ��������ж�����¼��ΪList<Object>����</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 * @Date 2012-11-15 ����2:23:17
 * @author biaoping.yin
 * @version 1.0
 */
public class GetCUDResult {
	private Object result;
	private Object updatecount;
	private Object keys;

	public GetCUDResult(Object result,Object updatecount,Object keys) {
		super();
		this.result = result;
		this.updatecount = updatecount;
		this.keys = keys;
	}
	

	public Object getResult() {
		return result;
	}

	public Object getUpdatecount() {
		return updatecount;
	}
	public Object getKeys() {
		return this.keys;
	}
	

}
