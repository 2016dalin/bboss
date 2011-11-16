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

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * <p>Title: ExampleListener.java</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *

 * @Date Sep 21, 2008 6:03:33 PM
 * @author biaoping.yin
 * @version 1.0
 */
public class ExampleListener implements Listener<String>{
	/**
	 * ע�������,����ExampleEventType.type1��ExampleEventType.type2�������͵��¼�
	 * �¼���Ϣ�����Ǳ����¼���������Զ�̱�����Ϣ��Ҳ������Զ����Ϣ
	 */
	public void init()
	{
		List eventtypes = new ArrayList();
		eventtypes.add(ExampleEventType.type1);
		eventtypes.add(ExampleEventType.type2);
		eventtypes.add(ExampleEventType.type2withtarget);
		//�������������¼���Ϣ�����Ǳ����¼���������Զ�̱�����Ϣ��Ҳ������Զ����Ϣ
		//�����ָ��eventtypes������������͵��¼���Ϣ
		NotifiableFactory.getNotifiable().addListener(this, eventtypes);
		/**
		 * ֻ����������Ϣ�ͱ��ط����ı���Զ����Ϣ
		 * NotifiableFactory.getNotifiable().addListener(this, eventtypes,Listener.LOCAL);
		 * ֻ����������Ϣ�ʹ�Զ����Ϣ�㲥��������Զ����Ϣ��Զ�̱�����Ϣ
		 * NotifiableFactory.getNotifiable().addListener(this, eventtypes,Listener.LOCAL_REMOTE);
		 * ֻ������Զ����Ϣ�㲥��������Զ����Ϣ��Զ�̱�����Ϣ
		 * NotifiableFactory.getNotifiable().addListener(this, eventtypes,Listener.REMOTE);
		 */
		
	}
	/**
	 * �������������Ϣ
	 */
	public void handle(Event<String> e) {
		
		if(e == null)
			return;
		if(e.getType().equals(ExampleEventType.type1))
		{
			System.out.println("Receive event type is " + e.getType());
			
		}
		else if(e.getType().equals(ExampleEventType.type2))
		{
			System.out.println("Receive event type is " + e.getType());
		}
		else if(e.getType().equals(ExampleEventType.type2withtarget))
		{
			System.out.println("Receive event type is " + e.getType() + " from "  + e.getEventTarget());
		}
		else
		{
			System.out.println("Unknown event type :" + e.getType());
		}
		System.out.println("the event source :" + e.getSource());
		String oj = e.getSource();
		
	}

}
