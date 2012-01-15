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

import java.util.Vector;

import org.frameworkset.spi.remote.JGroupHelper;
import org.junit.Test;

import bboss.org.jgroups.Address;

/**
 * 
 * 
 * <p>Title: TestEventHandle.java</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *

 * @Date Sep 21, 2008 9:28:05 PM
 * @author biaoping.yin
 * @version 1.0
 */
public class ExampleEventPublish {
	public static void publishEventtype1()
	{
		/**
		 * �����¼���Ϣ��hello world type2.����ָ�����¼�������ΪExampleEventType.type2
		 * Ĭ�ϵ��¼���Ϣ�㲥;��ΪEvent.REMOTELOCAL,�����ָ���Լ����¼��㲥;��
		 *  ��Ϣֻ�ڱ��ش�������Ϣ�����͸����ж�ExampleEventType.type2������Ϣ����Ȥ�ı�����Ϣ�������ͱ���Զ���¼�������
		 * 	Event event = new EventImpl("hello world type2.",ExampleEventType.type2,Event.LOCAL);
		 *  ��Ϣ�������ϴ�������Ϣ�����͸����ж�ExampleEventType.type2������Ϣ����Ȥ��Զ����Ϣ�������ͱ���Զ���¼���������ͬʱҲ��ֱ�ӷ��͸����ؼ�����
		 *  Event event = new EventImpl("hello world type2.",ExampleEventType.type2,Event.REMOTELOCAL);
		 *  ��Ϣ�������ϴ�������Ϣ�����͸����ж�ExampleEventType.type2������Ϣ����Ȥ��Զ����Ϣ�������ͱ���Զ���¼�������
		 *  Event event = new EventImpl("hello world type2.",ExampleEventType.type2,Event.REMOTE);
		 */
		Event event = new EventImpl("hello world type1.",ExampleEventType.type1);
		
		/**
		 * �¼���ͬ����ʽ����
		 */
		
		EventHandle.getInstance().change(event);
		
	}
	
	public static void publishAsynEventtype1()
	{
		/**
		 * �����¼���Ϣ��hello world type2.����ָ�����¼�������ΪExampleEventType.type2
		 * Ĭ�ϵ��¼���Ϣ�㲥;��ΪEvent.REMOTELOCAL,�����ָ���Լ����¼��㲥;��
		 *  ��Ϣֻ�ڱ��ش�������Ϣ�����͸����ж�ExampleEventType.type2������Ϣ����Ȥ�ı�����Ϣ�������ͱ���Զ���¼�������
		 * 	Event event = new EventImpl("hello world type2.",ExampleEventType.type2,Event.LOCAL);
		 *  ��Ϣ�������ϴ�������Ϣ�����͸����ж�ExampleEventType.type2������Ϣ����Ȥ��Զ����Ϣ�������ͱ���Զ���¼���������ͬʱҲ��ֱ�ӷ��͸����ؼ�����
		 *  Event event = new EventImpl("hello world type2.",ExampleEventType.type2,Event.REMOTELOCAL);
		 *  ��Ϣ�������ϴ�������Ϣ�����͸����ж�ExampleEventType.type2������Ϣ����Ȥ��Զ����Ϣ�������ͱ���Զ���¼�������
		 *  Event event = new EventImpl("hello world type2.",ExampleEventType.type2,Event.REMOTE);
		 */
		Event event = new EventImpl("hello world type1.",ExampleEventType.type1);
		
		/**
		 * �¼���ͬ����ʽ����
		 */
		
		EventHandle.getInstance().change(event,false);
		
	}
	
	public static void publishEventtype2()
	{
		/**
		 * �����¼���Ϣ��hello world type2.����ָ�����¼�������ΪExampleEventType.type2
		 * Ĭ�ϵ��¼���Ϣ�㲥;��ΪEvent.REMOTELOCAL,�����ָ���Լ����¼��㲥;��
		 *  ��Ϣֻ�ڱ��ش�������Ϣ�����͸����ж�ExampleEventType.type2������Ϣ����Ȥ�ı�����Ϣ�������ͱ���Զ���¼�������
		 * 	Event event = new EventImpl("hello world type2.",ExampleEventType.type2,Event.LOCAL);
		 *  ��Ϣ�������ϴ�������Ϣ�����͸����ж�ExampleEventType.type2������Ϣ����Ȥ��Զ����Ϣ�������ͱ���Զ���¼���������ͬʱҲ��ֱ�ӷ��͸����ؼ�����
		 *  Event event = new EventImpl("hello world type2.",ExampleEventType.type2,Event.REMOTELOCAL);
		 *  ��Ϣ�������ϴ�������Ϣ�����͸����ж�ExampleEventType.type2������Ϣ����Ȥ��Զ����Ϣ�������ͱ���Զ���¼�������
		 *  Event event = new EventImpl("hello world type2.",ExampleEventType.type2,Event.REMOTE);
		 */
	
		Event event = new EventImpl("hello world type2.",ExampleEventType.type2);
		/**
		 * ��Ϣ���첽��ʽ����
		 */
		
		EventHandle.getInstance().change(event,false);
		
	}
	
	public static void publishEventtype2Withtarget()
	{
		/**
		 * �����¼���Ϣ��hello world type2.����ָ�����¼�������ΪExampleEventType.type2withtarget
		 * Ĭ�ϵ��¼���Ϣ�㲥;��ΪEvent.REMOTELOCAL,�����ָ���Լ����¼��㲥;��
	
		 *  ��Ϣ�������ϴ�������Ϣ�����͸����ж�ExampleEventType.type2������Ϣ����Ȥ��Զ����Ϣ�������ͱ���Զ���¼���������ͬʱҲ��ֱ�ӷ��͸����ؼ�����
		 *  Event event = new EventImpl("hello world type2.",ExampleEventType.type2,Event.REMOTELOCAL);
		 *  ��Ϣ�������ϴ�������Ϣ�����͸����ж�ExampleEventType.type2������Ϣ����Ȥ��Զ����Ϣ�������ͱ���Զ���¼�������
		 *  Event event = new EventImpl("hello world type2.",ExampleEventType.type2,Event.REMOTE);
		 */
		

		EventTarget defualtprotocoltarget = null;
		Event event = null;
		Vector<Address> addresses = JGroupHelper.getJGroupHelper().getAppservers();
		if(addresses.size() > 0)//��һ���ڵ㷢������
		{
			defualtprotocoltarget = new EventTarget("jgroup::" + addresses.get(0));
			
			defualtprotocoltarget.setUserAccount("admin");
			defualtprotocoltarget.setUserPassword("123456");
		    event = new EventImpl("hello world type2 with jgroup target[" + defualtprotocoltarget +"].",
									ExampleEventType.type2withtarget,
									defualtprotocoltarget,
									Event.REMOTE);
	
			EventHandle.getInstance().change(event);
		}
		if(addresses.size() > 2)//��ǰ�����ڵ㷢������
		{
			defualtprotocoltarget = new EventTarget("jgroup::" + addresses.get(0) + ";" + addresses.get(1) );
			event = new EventImpl("hello world type2 with jgroups target[" + defualtprotocoltarget +"].",
									ExampleEventType.type2withtarget,
									defualtprotocoltarget,
									Event.REMOTE);

			EventHandle.getInstance().change(event);
		}
		
		//�����нڵ�㲥��Ϣ
		{
			defualtprotocoltarget = new EventTarget("jgroup::all" );
			event = new EventImpl("hello world type2 with jgroups target[" + defualtprotocoltarget +"].",
									ExampleEventType.type2withtarget,
									defualtprotocoltarget);
	
			EventHandle.getInstance().change(event);
		}
	}
	
	@Test
	public static void publishFileEvent()
	{
		/**
		 * �����¼���Ϣ��hello world type2.����ָ�����¼�������ΪExampleEventType.type2withtarget
		 * Ĭ�ϵ��¼���Ϣ�㲥;��ΪEvent.REMOTELOCAL,�����ָ���Լ����¼��㲥;��
	
		 *  ��Ϣ�������ϴ�������Ϣ�����͸����ж�ExampleEventType.type2������Ϣ����Ȥ��Զ����Ϣ�������ͱ���Զ���¼���������ͬʱҲ��ֱ�ӷ��͸����ؼ�����
		 *  Event event = new EventImpl("hello world type2.",ExampleEventType.type2,Event.REMOTELOCAL);
		 *  ��Ϣ�������ϴ�������Ϣ�����͸����ж�ExampleEventType.type2������Ϣ����Ȥ��Զ����Ϣ�������ͱ���Զ���¼�������
		 *  Event event = new EventImpl("hello world type2.",ExampleEventType.type2,Event.REMOTE);
		 */
		

		EventTarget
		defualtprotocoltarget = null;
		Event event = null;
//		Vector<Address> addresses = JGroupHelper.getJGroupHelper().getAppservers();
		
		
		defualtprotocoltarget = new EventTarget("netty","172.16.7.108",12347);
		event = new EventImpl("hello world type2 with jgroups target[" + defualtprotocoltarget +"].",
								ExampleEventType.type2withtarget,
								defualtprotocoltarget,
								Event.REMOTE);

		EventHandle.getInstance().change(event,true);
		
		
	}
	
	public static void main(String[] args)
	{


		publishFileEvent();
	}


	
	

}
