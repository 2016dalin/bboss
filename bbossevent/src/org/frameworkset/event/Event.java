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
 * �¼������磺
 * �û�/����Ϣ���޸ġ���ɫ��Ϣ���޸ġ�
 * ��Դ��Ϣ���޸ġ������Ϣ���޸ġ�
 * �û�/���ɫ��ϵ���޸ĵȵ�
 * @author biaoping.yin
 * @version 1.0
 * 
 */
public interface Event<T> extends java.io.Serializable{

	public T getSource();

	public EventType getType();
	
	/**
	 * �Ƿ��첽��������Ⱥ�������¼����첽�ķ�ʽ�㲥������ֵΪfalse��
	 * ͬ����ʽ�㲥ʱ������true
	 * @return
	 */
	public boolean isSynchronized();
	
	public void setSynchronized(boolean issynchronized);	
	public boolean isRemote();	
	public boolean isRemoteLocal();
	public boolean isLocal();
	public EventTarget getEventTarget();
	
	/**
	 * ��Ϣ�������ͣ�
	 * ���ش���
	 * Զ�̴���
	 * ����Զ�̴��� 
	 */
	
	/**
	 * ���ش���
	 */
	public static int LOCAL = 0;
	/**
	 * Զ�̴���
	 */
	public static int REMOTE = 1;
	/**
	 * ����Զ�̴���
	 */
	public static int REMOTELOCAL = 2;
	
	



}
