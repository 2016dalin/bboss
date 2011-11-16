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
 * 
 * <p>Title: ResourceChangeEventType.java</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2009</p>
 *
 * <p>bboss workgroup</p>
 * @Date May 17, 2009
 * @author biaoping.yin
 * @version 1.0
 */
public interface ResourceChangeEventType extends EventType{
	
	/**վ�����*/
	public static final EventType EVENT_SITE_ADD = new ResourceChangeEventTypeImpl("EVENT_SITE_ADD");
			
		
	/**վ��ɾ��*/
	public static final EventType EVENT_SITE_DELETE = new ResourceChangeEventTypeImpl("EVENT_SITE_DELETE");
	/**վ���޸�*/
	public static final EventType EVENT_SITE_UPDATE = new ResourceChangeEventTypeImpl("EVENT_SITE_UPDATE");
	/**վ��״̬�޸�*/
	public static final EventType EVENT_SITESTATUS_UPDATE = new ResourceChangeEventTypeImpl("EVENT_SITESTATUS_UPDATE");
	
	/**վ��仯*/
	public static final EventType EVENT_SITE_CHANGE = new ResourceChangeEventTypeImpl("EVENT_SITE_CHANGE");
	
	/**Ƶ�����*/
	public static final EventType EVENT_CHANNEL_ADD = new ResourceChangeEventTypeImpl("EVENT_CHANNEL_ADD");
	/**Ƶ��ɾ��*/
	public static final EventType EVENT_CHANNEL_DELETE = new ResourceChangeEventTypeImpl("EVENT_CHANNEL_DELETE");
	/**Ƶ���޸�*/
	public static final EventType EVENT_CHANNEL_UPDATE = new ResourceChangeEventTypeImpl("EVENT_CHANNEL_UPDATE");
	/**Ƶ���ƶ�*/
	public static final EventType EVENT_CHANNEL_MOVE = new ResourceChangeEventTypeImpl("EVENT_CHANNEL_MOVE");
	
	/**Ƶ���仯*/
	public static final EventType EVENT_CHANNEL_CHANGE = new ResourceChangeEventTypeImpl("EVENT_CHANNEL_CHANGE");

}
