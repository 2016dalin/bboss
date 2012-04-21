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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;
import org.frameworkset.log.DefaultBaseLogger;
import org.frameworkset.remote.EventRemoteService;
import org.frameworkset.remote.EventUtils;
import org.frameworkset.spi.ApplicationContext;
import org.frameworkset.spi.BaseSPIManager;
import org.frameworkset.spi.security.SecurityManager;
import org.frameworkset.thread.DelayThread;
import org.frameworkset.thread.RejectCallback;
import org.frameworkset.thread.ThreadPoolManagerFactory;
import org.frameworkset.thread.ThreadPoolManagerFactory.InnerThreadPoolExecutor;
import org.safehaus.uuid.UUID;
import org.safehaus.uuid.UUIDGenerator;

/**
 * �¼����������
 * 
 * @author biaoping.yin
 * @version 1.0
 */
public class EventHandle extends RejectCallback implements Notifiable {
	private static final Logger log = Logger.getLogger(EventHandle.class);
	private static final DefaultBaseLogger baselog = new DefaultBaseLogger (log);
	
	private static EventHandle instance;
	
	public static final String  EVENT_SERVICE = "event.serivce";
	/**
	 * ���ϵͳ�����еı��غ�Զ���¼�������Ȥ�ļ�����,��ĳ���¼�������ʱ���¼������ܽ���list�е����м�����������Ϣ List<Listener>
	 */
	private static final List list = new ArrayList();

	/**
	 * ���ϵͳ�����б����¼�������Ȥ�ļ�����,��ĳ���¼�������ʱ���¼������ܽ���list�е����м�����������Ϣ List<Listener>
	 */
	private static final List locallist = new ArrayList();
	/**
	 * �����������������¼����ͶԼ�����������������ĳ�����͵ı��ػ�Զ���¼�������ʱ�� �¼������ܳ��˸����е��¼�����Ȥ�ļ�������
	 * ����Ϣʱ��������Ը������¼�����Ȥ�ļ�����������Ϣ
	 * ��Ҫע����ǣ����ĳ������������ĳ���ض����͵��¼�����Ȥ�ֶ��������е��¼�����Ȥ����ô���ü����� ���ն������¼�����Ȥ�ļ��������� Map<EventType,List<Listener>>
	 */
	private static final Map listenersIndexbyType = new HashMap();

	/**
	 * �����������������¼����ͶԼ�����������������ĳ�����͵ı����¼�������ʱ�� �¼������ܳ��˸����е��¼�����Ȥ�ļ�������
	 * ����Ϣʱ��������Ը������¼�����Ȥ�ļ�����������Ϣ
	 * ��Ҫע����ǣ����ĳ������������ĳ���ض����͵��¼�����Ȥ�ֶ��������е��¼�����Ȥ����ô���ü����� ���ն������¼�����Ȥ�ļ��������� Map<EventType,List<Listener>>
	 */
	private static final Map localListenersIndexbyType = new HashMap();
	
	/**
	 * ������������Զ���¼��ļ������б�
	 */
	private static final List remoteListeners = new ArrayList();
	/**
	 * �����ض�����Զ���¼��ļ������б�
	 */
	private static final Map remoteListenersIndexbyType = new HashMap();
	
	private static String event_user = BaseSPIManager.getProperty("event.user","admin");
	private static String event_password = BaseSPIManager.getProperty("event.password","123456");

//	/**
//	 * �¼���������ͨ���������¼����ݵı仯������ڼ�Ⱥ�����й㲥�¼� Map<fqn,Event>
//	 * 
//	 */
//	private static AopMapContainer treeMap = null;
	
//	public static final String EVENT_CLUSTER = JBOSSCacheContainer.clusterName
//			+ ".EventRpcDispatcherGroup";
//	boolean rpcinited = false;
	
	

	protected EventHandle() {
		
	}
	
	public static EventHandle getInstance()
	{
		
		if(instance == null)
		{
			EventHandle temp = new EventHandle();
			instance = temp;
		}
		return instance;
	}



	/**
	 * Description:
	 * 
	 * @param listener
	 * @see com.chinacreator.security.authorization.ACLNotifiable#addListener(com.chinacreator.security.authorization.ACLListener)
	 */
	public void addListener(Listener listener) {
		addListener(listener, true);
	}

	/**
	 * Description:
	 * 
	 * @param listener
	 * @see com.chinacreator.security.authorization.ACLNotifiable#addListener(com.chinacreator.security.authorization.ACLListener)
	 */
	public void addListener(Listener listener, boolean remote) {
		

		if (remote) {
			this.addListener(listener,Listener.LOCAL_REMOTE);
//			if (!this.containListener(list, listener)) {
//				list.add(listener);
//			}
		} else {
			this.addListener(listener,Listener.LOCAL);
//			if (!this.containListener(locallist, listener)) {
//				locallist.add(listener);
//			}
		}

	}

	/**
	 * Description:ע�����������ע��ļ�����ֻ��eventtypes�а������¼����͵�Զ���¼��ͱ����¼�����Ȥ
	 * 
	 * @param listener
	 * @param List
	 *            <ResourceChangeEventType> ��������Ҫ��������Ϣ����
	 * @see com.chinacreator.security.authorization.ACLNotifiable#addListener(com.chinacreator.security.authorization.ACLListener)
	 */
	public void addListener(Listener listener, List eventtypes) {

		addListener(listener, eventtypes, true);
	}

	/**
	 * ע�����������ע��ļ�����ֻ��eventtypes�а������¼����͸���Ȥ�����������͵��¼�������Ȥ
	 * ����remote��ֵ�����������Ƿ����Զ���¼��ͱ����¼���remote=trueʱ����Զ�̺ͱ����¼���falseʱ ֻ���������¼���
	 */
	public void addListener(Listener listener, List eventtypes, boolean remote) {
		if(remote)
		{
			this.addListener(listener, eventtypes, Listener.LOCAL_REMOTE);
		}
		else
		{
			this.addListener(listener, eventtypes, Listener.LOCAL);
		}		
	}
	
	
	public void addListener(Listener listener, List eventtypes, int listenerType) {
		
		/**
		 * �жϼ������Ƿ��Ѿ��ڶ�������Ϣ������Ȥ�ļ������б��У��Ѿ����ھͷ��أ����� ����ִ�к����Ĺ���
		 */
		if(listenerType == Listener.LOCAL)
		{
			if(containListener(locallist, listener))
				return;
		}
		else if(listenerType == Listener.LOCAL_REMOTE)
		{
			if(containListener(list, listener))
				return;
		}
		else if(listenerType == Listener.REMOTE)
		{
			if(containListener(remoteListeners, listener))
				return;
		}
		else //�������Ͱ�LOCAL_REMOTE���ʹ���
		{
			if(containListener(list, listener))
				return;
		}
		if (eventtypes == null || eventtypes.size() == 0) {
			this.addListener(listener, listenerType);
		} else {
			if (listenerType == Listener.LOCAL_REMOTE) {
				for (int i = 0; i < eventtypes.size(); i++) {
					EventType eventType = (EventType) eventtypes.get(i);
					if (this.containEventType(listenersIndexbyType, eventType)) {
						List listeners = (List) listenersIndexbyType
								.get(eventType.toString());
						if (containListener(listeners, listener)) {
							continue;

						}
						listeners.add(listener);
					} else {
						List listeners = new ArrayList();
						listeners.add(listener);
						listenersIndexbyType.put(eventType.toString(),
								listeners);

					}
				}
			} 
			else if (listenerType == Listener.LOCAL) 
			{
				for (int i = 0; i < eventtypes.size(); i++) {
					EventType eventType = (EventType) eventtypes.get(i);
					if (this.containEventType(localListenersIndexbyType,
							eventType)) {
						List listeners = (List) localListenersIndexbyType
								.get(eventType.toString());
						if (containListener(listeners, listener)) {
							continue;

						}
						listeners.add(listener);
					} else {
						List listeners = new ArrayList();
						listeners.add(listener);
						localListenersIndexbyType.put(eventType.toString(),
								listeners);

					}
				}
			}
			
			else if (listenerType == Listener.REMOTE) 
			{
				for (int i = 0; i < eventtypes.size(); i++) {
					EventType eventType = (EventType) eventtypes.get(i);
					if (this.containEventType(remoteListenersIndexbyType,
							eventType)) {
						List listeners = (List) remoteListenersIndexbyType
								.get(eventType.toString());
						if (containListener(listeners, listener)) {
							continue;

						}
						listeners.add(listener);
					} else {
						List listeners = new ArrayList();
						listeners.add(listener);
						remoteListenersIndexbyType.put(eventType.toString(),
								listeners);

					}
				}
			}
			else  //�������͵ļ�����������LOCAL_REMOTE���ʹ���
			{
				log.warn("addListener:ע�᲻֧�ֵļ���������[" + listenerType + ",listener=" + listener + "] on line 332");
				for (int i = 0; i < eventtypes.size(); i++) {
					EventType eventType = (EventType) eventtypes.get(i);
					if (this.containEventType(listenersIndexbyType, eventType)) {
						List listeners = (List) listenersIndexbyType
								.get(eventType.toString());
						if (containListener(listeners, listener)) {
							continue;

						}
						listeners.add(listener);
					} else {
						List listeners = new ArrayList();
						listeners.add(listener);
						listenersIndexbyType.put(eventType.toString(),
								listeners);

					}
				}
			}
		}
	}



	public void addListener(Listener listener, int listenerType) {
		if (listenerType == Listener.LOCAL_REMOTE) {
			
			if (!this.containListener(list, listener)) {
				list.add(listener);
			}
		} else if (listenerType == Listener.LOCAL){			
			if (!this.containListener(locallist, listener)) {
				locallist.add(listener);
			}
		}
		else if (listenerType == Listener.REMOTE){			
			if (!this.containListener(remoteListeners, listener)) {
				remoteListeners.add(listener);
			}
		}
		else //�������͵ļ�����������LOCAL_REMOTE���ʹ���
		{
			log.warn("addListener:ע�᲻֧�ֵļ���������[" + listenerType + ",listener=" + listener + "] on line 375");
			if (!this.containListener(list, listener)) {
				list.add(listener);
			}
		}
		
	}

	/**
	 * �жϼ������Ƿ����б��д���
	 */
	private boolean containListener(List listeners, Listener listener) {
		for (int i = 0; i < listeners.size(); i++) {
			Listener temp = (Listener) listeners.get(i);
			if (temp == listener) {
				return true;
			}
		}
		return false;
	}

	/**
	 * �ж��¼������Ƿ��Ѿ���������
	 * 
	 * @param eventType
	 * @return
	 */
	private boolean containEventType(Map indexs, EventType eventType) {
		return indexs.containsKey(eventType.toString());
	}

	/**
	 * Description:
	 * 
	 * @param source
	 * @see com.chinacreator.security.authorization.ACLNotifiable#change(java.lang.Object)
	 */
	public void change(Event event) {
		change(event, true);

	}

	/**
	 * ��Ա����¼���Զ���¼�����Ȥ�ļ������㲥�¼�
	 * 
	 * @param event
	 */
	private static void _handleCommon(Event event) {
		for (int i = 0; list != null && i < list.size(); i++) {
			Listener listener = (Listener) list.get(i);
			try {
				listener.handle(event);
			} catch (Exception e) {
				log.error(event,e);
			}
		}

		EventType eventType = event.getType();
		List listeners = (List) listenersIndexbyType.get(eventType.toString());
		for (int i = 0; listeners != null && i < listeners.size(); i++) {
			Listener listener = (Listener) listeners.get(i);
			try {
				listener.handle(event);
			} catch (Exception e) {
				log.error(event,e);
			}
		}

	}

	/**
	 * �����¼�������
	 * 
	 * @param event
	 */
	private static void handleEvent(Event event) {
		if(event.isLocal() )
		{
			if(event.getEventTarget() != null)
				log.warn("�����¼�����ָ���¼�����Զ��Ŀ�꣺target=" + event.getEventTarget() + ",event source:" + event.getSource() + ",event type:" + event.getType());
			try {
				_handleCommon(event);
			} catch (Exception e) {
				log.error(e);
				e.printStackTrace();
			}
			try {
				_handleLocal(event);
			} catch (Exception e) {
				log.error("",e);
			}
			return;
		}
		    
			
		else if(event.isRemoteLocal())
		{
			try {
				_handleLocal(event);//���ȷ��͸������¼����ͼ�����
			} catch (Exception e) {
				log.error("",e);
			}
			if (!EventUtils.remoteevent_enabled()) {
				
				try {
					_handleCommon(event);//���û�����ü�Ⱥ����ô�򱾵�Զ���¼����ͼ�����������Ϣ
				} catch (Exception e) {
					log.error("",e);
				}
				if(event.getEventTarget() != null)
					log.warn("Զ���¼������ã����Է���Զ���¼���target=" + event.getEventTarget() + ",event source:" + event.getSource() + ",event type:" + event.getType());
				
			}
			else
			{				
				if(EventUtils.cluster_enable() && event.getEventTarget() == null)
				{
					
					try{
						EventRemoteService eventRemoteService = (EventRemoteService)BaseSPIManager.getBeanObject(getEVENT_SERVICEName(null));
						UUIDGenerator uuid_gen = UUIDGenerator.getInstance();
						UUID uuid = uuid_gen.generateRandomBasedUUID();
						String key = uuid.toString();
					    eventRemoteService.remoteEventChange(key,event);
					}catch(Exception e){
						log.error("",e);
						_handleCommon(event);//�����Ⱥ�����£���������£���Ϣ�ᱻͨ�ü��������գ����������Ⱥ����ʧ�ܣ���ô��Ҫֱ�ӽ��¼������Լ�
						return;
						
					}
				}
				else if(event.getEventTarget() != null)
				{	
					try{
						EventRemoteService eventRemoteService = (EventRemoteService)BaseSPIManager.getBeanObject(getEVENT_SERVICEName(event.getEventTarget()));
						UUIDGenerator uuid_gen = UUIDGenerator.getInstance();
						UUID uuid = uuid_gen.generateRandomBasedUUID();
						String key = uuid.toString();
					    eventRemoteService.remoteEventChange(key,event);					    
					}catch(Exception e){
						log.error("",e);
					}
					try {
						_handleCommon(event);//���ؿ��ܻ��ظ����յ�ͬһ���¼�����Ϊ���ָ����Ŀ���ַ�����˱��ص�ַ
					} catch (Exception e) {
						log.error("",e);
					}
					
										
				}
					
				else 
				{
					try {
						_handleCommon(event);
					} catch (Exception e) {
						log.error("",e);
					}
				}
			}
		} 
		
		else if(event.isRemote()) //���ܷ��͸�����Զ���¼��������ͱ����¼�������
		{
			if (!EventUtils.remoteevent_enabled()) {
				log.debug("ϵͳ������Ϊ����Զ���¼�,����Զ���¼��Ĵ���target=" + event.getEventTarget() + ",event source:" + event.getSource() + ",event type:" + event.getType());
			}
			else
			{
				if(EventUtils.cluster_enable() || event.getEventTarget() != null)
				{
				    EventRemoteService eventRemoteService = (EventRemoteService)BaseSPIManager.getBeanObject(getEVENT_SERVICEName(event.getEventTarget()));
					UUIDGenerator uuid_gen = UUIDGenerator.getInstance();
					UUID uuid = uuid_gen.generateRandomBasedUUID();
					String key = uuid.toString();
		
					try{
					    eventRemoteService.remoteEventChange(key,event);
					}catch(Exception e){
						
						log.error("",e);
//						_handleRemote(event);
						return;
						
					}
				}
					
				
			}
		}
		

	}
	private static String static_networks = BaseSPIManager.getProperty("event.static-networks","all");
	public static String getEVENT_SERVICEName(EventTarget target)
	{
		String user = target ==null || target.getUserAccount() == null || target.getUserAccount().equals("")?event_user:target.getUserAccount();
		String password =  target ==null || target.getUserPassword() == null || target.getUserPassword().equals("")?event_password:target.getUserPassword();
		  
			
		StringBuffer st = new StringBuffer();
	    if(target == null)
	    {
	         st.append("(").append(static_networks).append(")/").append(EVENT_SERVICE);
	    }
	    else
	    {
	         st.append("(").append(target.getStringTargets()).append(")/" ).append( EVENT_SERVICE);
	    }
	    if(user != null)
	    {
	    	st.append("?").append(SecurityManager.USER_ACCOUNT_KEY).append("=").append(user).append("&");
	    	st.append(SecurityManager.USER_PASSWORD_KEY).append("=").append(password != null?password:"");
	    }
	    
	    return st.toString();
	}

	/**
	 * ��ֻ�Ա����¼�����Ȥ�ļ��������㲥�����¼�
	 * 
	 * @param event
	 */
	private static void _handleLocal(Event event) {
		for (int i = 0; locallist != null && i < locallist.size(); i++) {
			Listener listener = (Listener) locallist.get(i);
			try {
				listener.handle(event);
			} catch (Exception e) {
				log.error(event,e);
			}
		}

		EventType eventType = event.getType();
		List listeners = (List) localListenersIndexbyType.get(eventType.toString());
		for (int i = 0; listeners != null && i < listeners.size(); i++) {
			Listener listener = (Listener) listeners.get(i);
			try {
				listener.handle(event);
			} catch (Exception e) {
				log.error(event,e);
			}
		}

	}
	
	/**
	 * ��ֻ��Զ���¼�����Ȥ�ļ��������㲥�����¼�
	 * 
	 * @param event
	 */
	private static void _handleRemote(Event event) {
		for (int i = 0; EventHandle.remoteListeners != null && i < EventHandle.remoteListeners.size(); i++) {
			Listener listener = (Listener) locallist.get(i);
			try {
				listener.handle(event);
			} catch (Exception e) {
				log.error(event,e);
			}
		}

		EventType eventType = event.getType();
		List listeners = (List) EventHandle.remoteListenersIndexbyType.get(eventType.toString());
		for (int i = 0; listeners != null && i < listeners.size(); i++) {
			Listener listener = (Listener) listeners.get(i);
			try {
				listener.handle(event);
			} catch (Exception e) {
				log.error(event,e);
			}
		}

	}

	/**
	 * ���ϵͳ����Ⱥ���߶�ʵ���仺�����ݲ��Ҽ�Ⱥ�Ѿ����������������ڼ�Ⱥ�й㲥
	 */
	public void change(Event event, boolean synchronizable) {
	
		event.setSynchronized(synchronizable);
		_change(event);

	}
	static InnerThreadPoolExecutor executor ;
	/**
	 * �����¼�����
	 * 
	 * @param event
	 */
	private void _change(Event event) {

		if (!event.isSynchronized()) {
			init();
			executor.busy(this, baselog);
			commandsQueue.add(event);
		   
		} else {
			handleEvent(event);
		}

	}

	public void change(String eventsource, EventType eventType) {

		change(eventsource, eventType, true);
	}

	public void change(String eventsource, EventType eventType,
			boolean synchronizable) {
		Event event = new EventImpl(eventsource, eventType);
		change(event, synchronizable);
	}

	/**
	 * �����̣߳��첽�������е��¼�����ʵʱ���»�������Ȩ����Ϣ
	 * 
	 * @author biaoping.yin
	 * @version 1.0
	 */
	static class HandleThread extends DelayThread {
		// Iterator listeners;

		Event source;

		HandleThread(Event source) {
			this.source = source;
//			if (source != null) {
//				Thread t = new Thread(this);
//				t.start();
//			}
		}

		/**
		 * Description:�߳�����
		 * 
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			if(source != null)
				handleEvent(source);
		}

                public void setReject()
                {
                    // TODO Auto-generated method stub
                    
                }
	}

	

	// public static void main(String[] a)
	// {
	// //// org.jboss.mx.util.PropertyAccess d;
	// // EventHandle handle = new EventHandle ();
	// //
	// // Event e = new EventImpl("",ACLEventType.ORGUNIT_INFO_DELETE);
	// // handle.change(e);
	// //// treeMap.put(e.toString(), e);
	// //// System.out.println("adte:" + treeMap.get(e.toString()));
	// // //treeMap.remove(e.toString());
	// // T t = new T();
	// // t.run();
	// // B b = new B();
	// // b.run();
	// // testSerialzableObject();
	//		
	//		
	// }

//	static class T extends Thread {
//		public void run() {
//			EventHandle handle = new EventHandle();
//			try {
//				handle.init();
//			} catch (ChannelException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			System.out.println();
//			// Event e = new EventImpl("",ACLEventType.ORGUNIT_INFO_DELETE);
//			// handle.change(e);
//		}
//	}
//
//	static class B extends Thread {
//		public void run() {
//			while (true) {
//				try {
//					sleep(10000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			// EventHandle handle = new EventHandle ();
//
//			// Event e = new EventImpl("",ACLEventType.ORGUNIT_INFO_DELETE);
//			// handle.change(e);
//		}
//	}
	/**
	 * �ⲿ���õ�Զ�̴����������е�Զ���¼�ͨ���ⲿ����������
	 * @param eventfqn
	 * @return
	 * @throws Exception
	 */
	public static int remotechange(String eventfqn,Event event) throws Exception {
		log.info("remote fqn:" + eventfqn);

		if (event == null) {
			log.info("Remote event handle ignore event=null.");
			return 0;
		}
		
		log.info("Remote event target :" + event.getEventTarget());
		log.info("Remote event type:" + event.getType());
		try {
//			if(event.isRemoteLocal())
//			{
//				_handleCommon(event);
//			}
//			else if(event.isRemote())
			{
				try
				{
					_handleRemote(event);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				try
				{
					_handleCommon(event);
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	private static int maxSerials = ApplicationContext.getApplicationContext().getIntProperty("event.block.queue.size",200);
	private static LinkedBlockingQueue<Event> commandsQueue = new LinkedBlockingQueue<Event>(maxSerials);
	public static class WaiterFailedHandler implements org.frameworkset.thread.WaitFailHandler<HandleThread>
    {
        public void failhandler(HandleThread run)
        {
        	commandsQueue.add(run.source);
        }

    }
	private static Thread evntHanderWorker;
	private static boolean stopped = false;
	private static boolean inited = false;
	
	
	static synchronized void init()
	{
		if(inited )
			return;
		inited = true;
		executor = (InnerThreadPoolExecutor)ThreadPoolManagerFactory.getThreadPoolExecutor("event.threadpool");
		evntHanderWorker = new Thread(new Runnable(){
			public void run() {
				
				while(true)
				{
					try {
						Event<?> event = commandsQueue.take();
						if(stopped)
							break;
						executor.execute(new HandleThread(event));
					} catch (InterruptedException e) {
//						e.printStackTrace();
						if(stopped)
							break;
						log.error(e);
						continue;
					}
					
				}
				 
			}},"Event Handler Worker");
		evntHanderWorker.start();
	}
	
	public static void shutdown()
	{
		if(stopped)
			return;
		stopped = true;
		try {
			if(evntHanderWorker != null)
				synchronized(evntHanderWorker)
				{
					
					evntHanderWorker.interrupt();
					
				}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			if(executor != null)
				executor.shutdown();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//inited = false;
	}



	


}
