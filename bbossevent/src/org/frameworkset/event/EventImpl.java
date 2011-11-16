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

import org.frameworkset.spi.BaseSPIManager;


/**
 * �¼��ӿ�ʵ��
 *
 * @author biaoping.yin
 * @version 1.0
 */
public final class EventImpl<T> implements Event {
	/**
	 *  
	 */
	private static final long serialVersionUID = -1242731152082695701L;
	T source;
	EventType type ;
	/**
	 * �¼���Ϣ�㲥��Ŀ�ĵ�ַ��ֻ��eventBroadcastTypeΪԶ�̴���(Event.REMOTE)
	 * ����Զ�̴��� (Event.REMOTELOCAL)��������ʱ���ſ���ָ��target����
	 * ���targetΪnull����ô�¼������㲥�����е�Զ�̽ڵ�����ļ�����������ֻ�㲥��target
	 * ָ����Ŀ�ĵ�ַ��Ӧ��Զ�̼��������棬�����Ӧ��target�����ڣ���ôֱ�Ӷ�������¼���Ϣ
	 */
	EventTarget target = null;
	
	/**
	 * ��Ϣ�������ͣ�
	 * ���ش���(Event.LOCAL)
	 * Զ�̴���(Event.REMOTE)
	 * ����Զ�̴��� (Event.REMOTELOCAL)
	 */
	int eventBroadcastType = Event.REMOTELOCAL;
	boolean issynchronized = false;
	
	static int defaultEventBroadcastType = Event.REMOTELOCAL;
	static 
	{
	    String t = BaseSPIManager.getProperty("event.destinction.type","Event.REMOTELOCAL");
	    if(t.equals("Event.REMOTELOCAL"))
	    {
	        defaultEventBroadcastType = Event.REMOTELOCAL;
	    }
	    else if(t.equals("Event.REMOTE"))
	    {
	        defaultEventBroadcastType = Event.REMOTE;
	    }
	    else if(t.equals("Event.LOCAL"))
            {
                defaultEventBroadcastType = Event.LOCAL;
            }
	    else
	    {
	        defaultEventBroadcastType = Event.REMOTELOCAL;
	    }
	}
	
	public EventImpl(T source,EventType type )
	{
		this(source,type ,Event.REMOTELOCAL);
	}
	
	public EventImpl(T source,EventType type ,EventTarget target,int eventBroadcastType)
	{
		this.source = source;
		this.type = type;
		this.target = target;
		if(eventBroadcastType == Event.LOCAL ||
			eventBroadcastType == Event.REMOTE ||
			eventBroadcastType == Event.REMOTELOCAL)
		{
			this.eventBroadcastType = eventBroadcastType;
		}
		else
		{
			System.out.println("Incorrect EventBroadcastType:" + eventBroadcastType + ", the Event.REMOTELOCAL will been used.");
			this.eventBroadcastType = Event.REMOTELOCAL;
		}
		
	}
	
	public EventImpl(T source,EventType type ,EventTarget target)
	{
		this.source = source;
		this.type = type;
		this.target = target;
		
		
	}
	
	public EventImpl(T source,EventType type ,int eventBroadcastType)
	{
		this.source = source;
		this.type = type;
		if(eventBroadcastType == Event.LOCAL ||
			eventBroadcastType == Event.REMOTE ||
			eventBroadcastType == Event.REMOTELOCAL)
		{
			this.eventBroadcastType = eventBroadcastType;
		}
		else
		{
			System.out.println("Incorrect EventBroadcastType:" + eventBroadcastType + ", the Event.REMOTELOCAL will been used.");
			this.eventBroadcastType = Event.REMOTELOCAL;
		}
	}
	public EventImpl()
	{
		
	} 
	/**
	 *  Description:
	 * @return
	 * @see com.chinacreator.security.authorization.ACLEvent#getSource()
	 */
	public Object getSource() {

		return source;
	}

	/**
	 *  Description:
	 * @return
	 * @see com.chinacreator.security.authorization.ACLEvent#getType()
	 */
	public EventType getType() {

		return type;
	}
	public boolean isSynchronized() {

		return this.issynchronized;
	}
	
	public void setSynchronized(boolean issynchronized)
	{
		this.issynchronized = issynchronized;
	}
	public void setSource(T source) {
		this.source = source;
	}
	public void setType(EventType type) {
		this.type = type;
	}
	public void setIssynchronized(boolean issynchronized) {
		this.issynchronized = issynchronized;
	}
	
	public boolean isLocal() {
		
		return this.eventBroadcastType == Event.LOCAL;
	}
	public boolean isRemote() {
		
		return this.eventBroadcastType == Event.REMOTE;
	}
	public boolean isRemoteLocal() {
		return this.eventBroadcastType == Event.REMOTELOCAL;
	}

	public int getEventBroadcastType() {
		return eventBroadcastType;
	}

	public EventTarget getEventTarget() {
		
		return this.target;
	}

}
