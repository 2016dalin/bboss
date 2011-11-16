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

import java.util.List;


/**
 * �¼�������
 *
 * @author biaoping.yin
 * @version 1.0
 */
public interface Notifiable {
	/**
	 * Description ע�����������
	 * @param listener ��Ҫע��ļ�����
	 * void
	 */
	public void addListener(Listener listener);
	
	/**
	 * Description ע�����������
	 * @param listener ��Ҫע��ļ�����
	 * @remote ���������¼���Զ���¼���ʶ��Ϊtrueʱ����Զ���¼�
	 *         ���ϵͳ��û��������Ⱥ���ʵ������ʱ
	 * void
	 * @deprecated
	 * @see public void addListener(Listener listener,int listenerType);
	 */
	public void addListener(Listener listener,boolean remote);
	
	
	/**
	 * Description ע�����������
	 * @param listener ��Ҫע��ļ�����
	 * @param List<ResourceChangeEventType> ��������Ҫ��������Ϣ����
	 * void
	 */
	public void addListener(Listener listener,List eventtypes);
	/**
	 * Description ע�����������
	 * @param listener ��Ҫע��ļ�����
	 * @param List<ResourceChangeEventType> ��������Ҫ��������Ϣ����
	 * @parema boolean remote ���ּ������Ƿ����Զ���¼���
	 * 					true-�������غ�Զ���¼�
	 * 					false-��������ֻ���������¼�
	 * void
	 * @deprecated 
	 * @see public void addListener(Listener listener,List eventtypes,int listenerType)
	 */
	public void addListener(Listener listener,List eventtypes,boolean remote);
	
	
	
	/**
	 * 
	 * @param listener ��Ҫע��ļ�����
	 * @param eventtypes List<ResourceChangeEventType> ��������Ҫ��������Ϣ����
	 * @param listenerType �¼�����������
	 * 
	 */
	public void addListener(Listener listener,List eventtypes,int listenerType);
	
	/**
	 * 
	 * @param listener ��Ҫע��ļ������������������͵��¼�
	 * 
	 * @param listenerType �¼�����������
	 * 
	 */
	public void addListener(Listener listener,int listenerType);

	/**
	 * Description ���¼�����ʱ�����ø÷���֪ͨ���еļ�������ϵͳ����synchronizable
         *             ����������Ϣͬ�����첽������Ϣ����
	 * @param source �¼�Դ
         * @param synchronizable ������Ϣͬ�����첽������Ϣ���ƣ�false�첽����trueͬ����ʽ����
	 * void
	 */
	public void change(Event source,boolean synchronizable);

    /**
     * Description ���¼�����ʱ�����ø÷���֪ͨ���еļ�����
     *             ,ϵͳ����ͬ���ķ�ʽ�����¼���Ϣ
     * @param source �¼�Դ
     * void
     */
    public void change(Event source);
}
