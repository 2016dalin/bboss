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
package org.frameworkset.event;

/**
 * ������
 * 
 * @author biaoping.yin
 * @version 1.0
 */
public interface Listener<T>  {

	/**
	 * Description:����������⵽��Ӧ���¼�ʱ�����ñ�����
	 * ���¼�������Ӧ����
	 * @param e
	 * void
	 */
	public void handle(Event<T> e);	
	/**
	 * �������ͼ�����
	 */
	public static final int LOCAL = 0;
	/**
	 * Զ���¼�������
	 */
	public static final int REMOTE = 1;
	
	/**
	 * ���غ�Զ���¼�������
	 */
	public static final int LOCAL_REMOTE = 2;
	
	
}
