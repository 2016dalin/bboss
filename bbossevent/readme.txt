directory:
src--source code
test--test source code
lib--bboss-event framework depends jars
dist--bboss-event release package.

---------------------------------
bbossevent�������̣�
---------------------------------
bbossevent<-cms_baseline [bboss-event.jar]

bbossevent->bboss-aop [bboss-aop.jar,bboss-camel.jar,bboss-mina.jar,bboss-jms.jar,bboss-ws.jar]
bbossevent->bboss-util [frameworkset-util.jar]

bbossevent->bboss-persistent [frameworkset-pool.jar] ��Ϊaop����е�����ע�������˳־ò���


------------------------------------------------------------------
update function list in bbossgroups-2.0-rc2 bbossgroups-2.0-rc1 since bbossgroups-2.0-rc
------------------------------------------------------------------
2010-10-09
------------------------------------------------------------------
o �޸�ִ���¼�������handle�������޸�Զ���¼������߼�
------------------------------------------------------------------
2010-09-26
------------------------------------------------------------------
o �޸�ִ���¼�������handle���������Ӷ�handle�����׳����쳣�Ĵ���
------------------------------------------------------------------
------------------------------------------------------------------
2010-09-13
------------------------------------------------------------------
o �޸Ĳ���������������aop��ܵ����°汾apiͬ������Ҫ��jgroups��api�����˱仯��
------------------------------------------------------------------
2010-08-30
------------------------------------------------------------------
o �޸��첽�¼����������¼����л��ƣ���ֹ�¼��ѻ�����̳߳ش���æ������

/bbossevent/resources/event-service-assemble.xml����������������event.block.queue.size
<property name="event.block.queue.size" value="200"/>
�̳߳�event.threadpool��ʧ�ܵȴ���������Ϊ��
<property name="waitFailHandler" value="org.frameworkset.event.EventHandle$WaiterFailedHandler"/>

update function list:
------------------------------
 1.0.2 - 2010-03-16
------------------------------
o �޸�Event��Listener,EventImpl�����֧࣬��java����
------------------------------
 1.0.2 - 2010-02-21
------------------------------
o ����jgroup 2.8.0 GA
o �滻log4j.jar Ϊlog4j-1.2.14.jar
o ����aop��ܣ���ȫ����֤������
o ���frameworkset-pool.jar
o �Ƴ�jg-magic-map.xml�ļ������ļ��Ѿ������jgroups.jar��
------------------------------
 1.0.2 - 2010-03-07
------------------------------
o ���̳߳�����bboss aop�����
o Զ���¼�EventTarget��ַ֧������4�ַ�����
1.Ĭ��rpcЭ��eventTarget��ʹ����mina��jgroupЭ�飩
EventTarget defualtprotocoltarget = new EventTarget("192.168.11.102",1186);
2.ָ��rpcЭ���eventTarget��ʹ����mina��jgroupЭ�飩
EventTarget target = new EventTarget("jgroup","192.168.11.102",1186);
3.ָ��Ŀ������url�ĵ�ַЭ��(������mina��jgroup��jms��webserviceЭ��)
EventTarget target = new EventTarget("jgroup::192.168.11.102:1186");
4.ָ��Ⱥ����ַ�б�
EventTarget target = new EventTarget("jgroup::192.168.11.102:1186;192.168.11.102:1187");

ʹ�÷�����
    EventTarget target = new EventTarget("jgroup","192.168.11.102",1186);
		Event event = new EventImpl("hello world type2 with target[" + target +"].",
								ExampleEventType.type2withtarget,
								target,
								Event.REMOTE);

		EventHandle.getInstance().change(event);
		
		EventTarget defualtprotocoltarget = new EventTarget("192.168.11.102",1186);
		event = new EventImpl("hello world type2 with default target[" + defualtprotocoltarget +"].",
								ExampleEventType.type2withtarget,
								defualtprotocoltarget,
								Event.REMOTE);

		EventHandle.getInstance().change(event);
		
		defualtprotocoltarget = new EventTarget("jgroup::192.168.11.102:1186");
		event = new EventImpl("hello world type2 with jgroup target[" + defualtprotocoltarget +"].",
								ExampleEventType.type2withtarget,
								defualtprotocoltarget,
								Event.REMOTE);

		EventHandle.getInstance().change(event);
		
		defualtprotocoltarget = new EventTarget("jgroup::192.168.11.102:1186;192.168.11.102:1187");
		event = new EventImpl("hello world type2 with jgroups target[" + defualtprotocoltarget +"].",
								ExampleEventType.type2withtarget,
								defualtprotocoltarget,
								Event.REMOTE);

		EventHandle.getInstance().change(event);

------------------------------
 1.0.2 - 2010-01-07
------------------------------
o ���ant�����ű��������ļ���build.xml,build.properties

------------------------------
 1.0.2 - 2009-06-01
------------------------------
o ���ant�����ű��������ļ���build.xml,build.properties
o ��дjava.util.concurrent �̳߳ع��ܣ�
/bbossevent/src/org/frameworkset/thread/ThreadPoolExecutor.java
/bbossevent/src/org/frameworkset/thread/RejectedExecutionHandler.java
/bbossevent/src/manager-provider.xml
org/frameworkset/thread/ThreadPoolManagerFactory$InnerThreadPoolExecutor�����ӷ�����
public boolean busy(RejectCallback rejectcallback, BaseLogger log)
����˵����
rejectcallback--�ص������������б��߳��Ѿ�æ�ȴ��˶��ٴΣ��Ա�����ͳ��ÿ�εĵȴ�ʱ��
log-��������־��¼�ӿڣ�ͨ��log���Խ�ÿ�εȴ�����Ϣ�����ǰ�˿���̨

o �¼�����е��̳߳�����
  �Ľ��̳߳ع��ܣ���������ܾ������е�WaitPolicy������delaytime��maxWait,waitFailHandler���ԣ��Ա�ʵ�ֵȴ�ʱ��������ԣ����ṩ���ȴ�����
  ����������ȴ���������������waitFailHandler����ֱ�ӽ����񷢸�waitFailHandler������������������
 
 ���Է�������ö���̳߳أ���ϸ��Ϣ���Բο�src/event-service-assemble.xml�е����ã�
 
 <!-- 
			���ȴ�5�Σ�ÿ����ʱ��10%
		 -->
		<property name="event.threadpool">
			<map>
				<property name="corePoolSize" value="5"/>
				<property name="maximumPoolSize" value="10"/>
				<!-- 
				TimeUnit.SECONDS
				TimeUnit.MICROSECONDS
				TimeUnit.MILLISECONDS
				TimeUnit.NANOSECONDS
				ʱ�䵥λ���������²�����
				keepAliveTime
				waitTime
				delayTime����delayTimeΪ����ʱ������ǰٷֱ�ʱ��Ч��
				 -->
				<property name="timeUnit" value="TimeUnit.SECONDS"/>
				<property name="keepAliveTime" value="40"/>		
				<!-- 
				/**
		             
		             * LinkedBlockingQueue
		             * PriorityBlockingQueue
		             * ArrayBlockingQueue
		             * SynchronousQueue
		             */
				 -->
				<property name="blockingQueueType" value="ArrayBlockingQueue"/>		
				<property name="blockingQueue" value="10"/>
				
				<!-- 
				RejectedExecutionHandler
				����ʵ��java.util.concurrent.RejectedExecutionHandler�ӿ�
				Ŀǰϵͳ�ṩ����ȱʡʵ�֣�
				org.frameworkset.thread.WaitPolicy  ѭ���ȴ�event.threadpool.waitTimeָ����ʱ�䣬��λΪ��
				java.util.concurrent.ThreadPoolExecutor$DiscardPolicy ֱ�Ӷ������񣬲��׳��쳣
				java.util.concurrent.ThreadPoolExecutor$AbortPolicy ֱ�Ӷ��������׳��쳣RejectedExecutionException
				java.util.concurrent.ThreadPoolExecutor$CallerRunsPolicy ֱ������
				java.util.concurrent.ThreadPoolExecutor$DiscardOldestPolicy ������У������ϵ�����ɾ��
				 -->
				<property name="rejectedExecutionHandler" value="org.frameworkset.thread.WaitPolicy"/>
				<!-- 
					���²���ֻ�������õ�org.frameworkset.thread.WaitPolicy����ʱ����Ҫ����
				 -->
				<property name="waitTime" value="1"/>
				<property name="delayTime" value="10%"/>
				<property name="maxWaits" value="5"/>
				<property name="waitFailHandler" value="org.frameworkset.thread.TestThread$WaitFailHandlerTest"/>
			</map>
		</property> 
		
		����ʹ�÷������£�
		 
		 ThreadPoolExecutor executer = ThreadPoolManagerFactory.getThreadPoolExecutor("event.threadpool");//��ȡʵ��
		 �����Ӧ��"event.threadpool"û�б����ã���ô��ʹ��Ĭ�ϵ��̳߳ز������ã�
		 	defaultPoolparams.put("corePoolSize", "5");
		    defaultPoolparams.put("maximumPoolSize", "10");
		    defaultPoolparams.put("keepAliveTime", "30");
		    defaultPoolparams.put("timeUnit", "TimeUnit.SECONDS");
		    defaultPoolparams.put("blockingQueue", "10");
		    defaultPoolparams.put("blockingQueueType", "ArrayBlockingQueue");	    
		    defaultPoolparams.put("rejectedExecutionHandler", "org.frameworkset.thread.WaitPolicy");
		    
		    defaultPoolparams.put("waitTime","1");
		    defaultPoolparams.put("delaytime","10%");
		    defaultPoolparams.put("maxWaits","10");
		 
        for(int i = 0; i < 50 ; i ++)
            executer.execute(new WaitRun());//ͨ���̳߳�ִ������
           
        ���������£�
        public static class WaitRun extends DelayThread
    {

        public void run()
        {
            //do something.

        }

    }
    waitFailHandler ��������
      public static class WaitFailHandlerTest implements WaitFailHandler<WaitRun>
    {

        public void failhandler(WaitRun r)
        {
            System.out.println("r:" + r);
            
        }
        
    }
	
	
	
  
o �¼�������Զ���¼��޷����͵�����
ԭ���ǣ�

/bbossaop/src/org/frameworkset/remote/Target.java��û�����л�����������
/bbossevent/src/org/frameworkset/event/EventTarget.java�ڷ���ʱ�޷�ʶ����target�еĶ���

����취����bboss-aop���

o ����frameworkset-pool.jar 
��com.frameworkset.common.poolman.sql.PrimaryKeyCache�е�ȫ�ֱ���
  Map id_tables�ĳ�ʼ����ʽ��Ϊ����map��������ֶ��̰߳�ȫ�����⡣
  
  public PrimaryKeyCache(String dbname) {
		this.dbname = dbname;
		id_tables = new java.util.concurrent.ConcurrentHashMap();
	}
	


------------------------------
 1.0.1 - 2009-05-17
------------------------------
o ��׼���¼�������
o ֧��Զ���¼�
  ֧�ֵ�Ե�Ͷ��Զ���¼��㲥�ͽ��չ���
  �ɾ�ָ̬���¼��㲥������ڵ㼯��Ҳ����ȱʡ��Ⱥ�е����нڵ㷢���¼���Ϣ
o ֧��ͬ�����첽�����¼����ͻ���
  

First version of bboss-event-1.0.1 released.

[http://blog.csdn.net/yin_bp]
[http://www.openbboss.com]