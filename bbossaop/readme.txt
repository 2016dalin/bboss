directory:
src--source code
test--test source code
sql-- db sql
lib--bboss-aop framework depends jars
bin--bboss-aop release package.

---------------------------------
bbossaop��������̣�
---------------------------------

bbossaop<-bbossevent 
[
	bboss-aop.jar,bboss-camel.jar,bboss-mina.jar,bboss-jms.jar,bboss-ws.jar,jgroups.jar
	
	org/frameworkset/spi/manager-rpc-jms.xml
	org/frameworkset/spi/manager-rpc-mina.xml
	org/frameworkset/spi/manager-rpc-service.xml
	org/frameworkset/spi/manager-rpc-webservices.xml
]
bbossaop<-cms_baseline 
[
	bboss-aop.jar,bboss-camel.jar,bboss-mina.jar,bboss-jms.jar,bboss-ws.jar
	
	
	org/frameworkset/spi/manager-rpc-jms.xml
	org/frameworkset/spi/manager-rpc-mina.xml
	org/frameworkset/spi/manager-rpc-service.xml
	org/frameworkset/spi/manager-rpc-webservices.xml
]
bbossaop<-bboss-ws 
[
	bboss-aop.jar,bboss-camel.jar,bboss-mina.jar,bboss-jms.jar,bboss-ws.jar
	
	org/frameworkset/spi/manager-rpc-jms.xml
	org/frameworkset/spi/manager-rpc-mina.xml
	org/frameworkset/spi/manager-rpc-service.xml
	org/frameworkset/spi/manager-rpc-webservices.xml
]
bbossaop<-active-ext 
[
	bboss-aop.jar,bboss-camel.jar,bboss-mina.jar,bboss-jms.jar,bboss-ws.jar	
	
	org/frameworkset/spi/manager-rpc-jms.xml
	org/frameworkset/spi/manager-rpc-mina.xml
	org/frameworkset/spi/manager-rpc-service.xml
	org/frameworkset/spi/manager-rpc-webservices.xml
]
bbossaop<-bboss-taglib [bboss-aop.jar]
bbossaop<-bboss-util [bboss-aop.jar]
bbossaop<-kettle 
[
	bboss-aop.jar,bboss-camel.jar,bboss-mina.jar,bboss-jms.jar,bboss-ws.jar
		
	org/frameworkset/spi/manager-rpc-jms.xml
	org/frameworkset/spi/manager-rpc-mina.xml
	org/frameworkset/spi/manager-rpc-service.xml
	org/frameworkset/spi/manager-rpc-webservices.xml
]
bbossaop<-portal [bboss-aop.jar]
bbossaop<-cas server [bboss-aop.jar]

bbossaop->bboss-persistent [frameworkset-pool.jar]
bbossaop->bboss-taglib [frameworkset.jar] �����ʹ���˱�ǩ��
bbossaop->bboss-util [frameworkset-util.jar]



---------------------------------
����������:
---------------------------------
�л��������У�ֱ���ڹ���Ŀ¼������ant����
---------------------------------
ע�����
---------------------------------
o ��������---bboss-aop.jar src test src-log        
    �������ݣ�
  classes�µ�com����org����bboss-magic-map.xml

o ��������---bboss-ws.jar src-cxf

o ��������---bboss-camel.jar src-camel

o ��������---bboss-mina.jar src-mina

o resources---������Դ�ļ�Ŀ¼����������ļ�



����˵����
o distribĿ¼�а�����bboss aop��ܷ�����jar����
bboss-aop.jar - aop��ܵĺ���jar��
bboss-camel.jar - bboss camel·����������ṩcamel·�ɹ���֧�֣��Ӷ�ʵ��bboss���֮���·�ɹ��ܣ��������ط������·�ɺ�Զ�̷������·��
bboss-jms.jar - bboss aop����ṩjms���õı�׼�ӿڣ�����jms 1.1�淶��ͬʱ�ṩ��bboss aopԶ�̷�����õ�jmsЭ��ʵ��
bboss-mina.jar - bboss aop���Զ�̷�����õ�minaЭ��ʵ��
bboss-ws.jar - bboss aop����е�webservcie���񷢲����ʵ�֣�Ŀǰ֧��apache cxf 2.2.4��ͬʱ�ṩ��bboss aopԶ�̷�����õ�webserviceЭ��ʵ��
jgroups.jar - ����JGroups-2.8.0.GA��չ���������bboss aop��ܵ���֤����Ȩ����/���ܹ���

ͬʱҲ������jms��mina��webservice����ʱ������jar����
lib\camel- camel����� �汾Ϊcamel 2.0
lib\cxf - webservice���apache cxf 2.2.4�����

lib\jms - jms�淶jar����active mq����� active mq 5.2.0
lib\mina - minaЭ������� ��apache mina-2.0.0-M6
lib\ bboss aop���������һ�¹��߰�

o resourceĿ¼Ϊbboss aop���������ļ��Ĵ��Ŀ¼���������л���ʱ����Ҫ��resource�µ�xml�ļ�������
classpath��Ŀ¼�£�����classesĿ¼��



more details see my blog [http://blog.csdn.net/yin_bp]

update function list:
------------------------------
 1.0.7 - 2010-03-08
------------------------------
o ����restful���rpc����Э��rest,������﷨���£�
(rest::a/b/c)/rpc.test
ϵͳ���𲽽���a/b/c�������ڵ�ĵ�ַ��
a,b,c�ֱ����Զ�̷�������ַ
ϵͳ����a,b,c��˳����·��Զ�̷�����ã����Ƚ�Զ�������͵�a��������Ȼ����a·�ɵ�b������������b·�ɵ�c������
��c������������ٽ�������ظ�b��b�ٷ��ظ�a�������������һ��restful����Զ�̷�����ù��̡�

a��b��c�ֱ����������ı�ʶ���������ʶ�����Զ�Ӧ��һ�������������������ַ������֮���ӳ���ϵ������һ��ע��������ά��
ÿ������������������һ��ע��������������ڱ���ע��ı�ʶ���������������ַ��ӳ���ϵ��

aop����ṩ��һ���ӿ�����ȡӳ���ϵ��
org.frameworkset.spi.remote.restful.RestfulServiceConvertor
�ӿڷ���Ϊ��
public String convert(String restfuluddi,String serviceid);
���в���restfuluddi��Ӧ�ڷ�������ʶ��a,b,c������serviceidΪ�����Զ�̷���id
�����÷���ת���ĵ�ַ������Ϊ���¸�ʽ��
"(" + protocol + "::" +  uri + ")/" + serviceid + "?user=" + user + "&password=" + password;
���е�protocol��uri��user��password���Ǹ��ݷ�������ʶ����ע�������в�ѯ������Ϣ��

������һ���򵥵Ľӿ�ʵ�֣�
public class RestfulServiceConvertorImplTest implements RestfulServiceConvertor
{

    public String convert(String restfuluddi, String serviceid)
    {
        if(restfuluddi.equals("a"))
        {
            String uri = "172.16.17.216:1187";
            String user = "admin";
            String password = "123456";
            String protocol = "mina";
            String returl = "(" + protocol + "::" +  uri + ")/" + serviceid + "?user=" + user + "&password=" + password;
            
            return returl;
        }
        else if(restfuluddi.equals("b"))
        {
            String uri = "172.16.17.216:1187";
            String user = "admin";
            String password = "123456";
            String protocol = "mina";
            String returl = "(" + protocol + "::" +  uri + ")/" + serviceid + "?user=" + user + "&password=" + password;
            
            return returl;
        }
        else if(restfuluddi.equals("c"))
        {
            String uri = "172.16.17.216:1187";
            String user = "admin";
            String password = "123456";
            String protocol = "mina";
            String returl = "(" + protocol + "::" +  uri + ")/" + serviceid + "?user=" + user + "&password=" + password;
            
            return returl;
        }
        
        else if(restfuluddi.equals("d"))
        {
            String uri = "172.16.17.216:1187";
            String user = "admin";
            String password = "123456";
            String protocol = "mina";
            String returl = "(" + protocol + "::" +  uri + ")/" + serviceid + "?user=" + user + "&password=" + password;
            
            return returl;
        }
        else 
        {
            String uri = "172.16.17.216:1187";
            String user = "admin";
            String password = "123456";
            String protocol = "jgroup";
            String returl = "(" + protocol + "::" +  uri + ")/" + serviceid + "?user=" + user + "&password=" + password;
            
            return returl;
        }
            
    }

}
RestfulServiceConvertor�ӿڵ�ʵ���౻������manager-rpc-service.xml�ļ��У�
		<!-- 
			restful����ַת����
		 -->
		<property name="rpc.restful.convertor" singlable="true"
					      class="org.frameworkset.spi.serviceid.RestfulServiceConvertorImplTest"/>
������Ա����ʵ���Լ��ĵ�ַת������ֱ���滻rpc.restful.convertor���ü��ɡ�
				      
�ٿ���һ����restful���ķ������ʾ����
		RPCTestInf testInf = (RPCTestInf)BaseSPIManager.getBeanObject("(rest::a/b/c/d)/rpc.test");
        //ѭ��ִ��10�η������
        for(int i = 0; i < 10; i ++)
        {
            try
            {
                System.out.println("testInf.getCount():" + i + " = "+testInf.getCount());
            }
            catch(Exception e)
            {
                e.printStackTrace();
                
            }
        }

����˵����
����ڽ���rest���ĵ�ַʱ���ܹ��Զ�ʶ��ڵ��Ӧ�ĵ�ַ�Ƿ��Ǳ��ص�ַ������������µ�ַ��
	(rest::a/b/c)/rpc.test
�������ڽ�����ַʱ������a�Ǳ��ص�ַ��������������һ����ַb��ֱ������һ��Զ�̵�ַʱ��ִ��Զ�̷�����ã����ȫ���Ǳ��ص�ַ����ô����rest����Զ�̷���
���þ���һ�����ص��á�
rest����ַ�нڵ��Ӧ�������ַ��Э�������aop���Ŀǰ�ṩ��Э���е��κ�һ�֣�jms��webservice��mina��jgroups

rest����ַ���������֤�����������ԡ�


o bboss.org.jgroups.protocols.pbcast.STREAMING_STATE_TRANSFER������2.9
FIXED for https://jira.jboss.org/jira/browse/JGRP-1136
o ������������2.9
bboss.org.jgroups.protocols.UNICAST
bboss.org.jgroups.stack.AckReceiverWindow
bboss.org.jgroups.stack.AckSenderWindow
FIXED for https://jira.jboss.org/jira/browse/JGRP-1122

o ������������2.9
bboss.org.jgroups.protocols.pbcast.NAKACK
bboss.org.jgroups.stack.NakReceiverWindow

FIXED for 
https://jira.jboss.org/jira/browse/JGRP-1104
https://jira.jboss.org/jira/browse/JGRP-1133
------------------------------
 1.0.7 - 2010-02-11
------------------------------
o jgroup 2.7.0 GA ������2.8.0 GA


------------------------------
 1.0.7 - 2009-05-05
------------------------------

o ����Ȩ�ޡ���֤����Ϣ���ܹ��ܣ����ʽģ��-org/frameworkset/spi/manager-rpc-service.xml
<property name="system.securityManager" class="org.frameworkset.spi.security.SecurityManagerImpl">
			<construction>
				<property name="securityconfig" 
					      refid="attr:rpc.security" />	
			</construction>	
		</property>
		
		<property name="rpc.security" >
			<map>
				<property name="rpc.login.module" enable="true" class="org.frameworkset.spi.security.LoginModuleTest"/>
				<property name="rpc.authority.module" enable="true" class="org.frameworkset.spi.security.AuthorityModuleTest"/>
				<property name="data.encrypt.module" enable="true" class="org.frameworkset.spi.security.EncryptModuleTest"/>	
			</map>
		</property>
o �����鲥���ý���쳣�����ܣ�һ��һ��ӿڵ�������һ�������쳣���������������쳣��ʽ����
o bean ���property�е������������Ч
org/frameworkset/spi/assemble/ProviderParser.java
/bbossaop/src/org/frameworkset/spi/BaseSPIManager.java
��������
/bbossaop/test/org/frameworkset/spi/properties/interceptor
o ����ע�ⷽʽ�����ݿ��������
	@Transaction("REQUIRED_TRANSACTION")
    @RollbackExceptions("") //@RollbackExceptions("{exception1,excpetion2}")
    ��ϸ����ο���������/bbossaop/test/org/frameworkset/spi/transaction/annotation
o �������ù�������
	bean������ʽ�޸�
	�����ֲ�bean�����������ض����������������ռ䣩��֧������ֲ���Χ���ã������

o ���Ƽ�ع���
  a). �����������ȫ������property�ڵ����ϸ��ѯҳ��
  b). ֧�ֶ�property���������ڵ����ϸ��ѯҳ��

o propertyԪ������editor�ӽڵ㣬ָ������ע��ʱ�����Ա༭��ת����

ϵͳĬ��֧���ַ�����������������ת��:					 
					 * int,char,short,double,float,long,boolean,byte
					 * java.sql.Date,java.util.Date,
					 * Integer
					 * Long
					 * Float
					 * Short
					 * Double
					 * Character
					 * Boolean
					 * Byte
					������ǻ������������Ǿ���Ҫͨ���Զ�������Ա༭����ʵ�֣����Ա༭������ʵ�ֽӿ�:
						com.frameworkset.util.EditorInf
						
						Object getValueFromObject(Object fromValue) ;    
    					Object getValueFromString(String fromValue);


o ��չaop���rpcЭ�飬֧��minaЭ��ʵ��rpc����Ŀǰ֧������Э�飺jgroup��mina,jms��webservice
��ϸ�������뿴�����ļ���
/bbossaop/src/org/frameworkset/spi/manager-rpc-service.xml
/bbossaop/src/org/frameworkset/spi/manager-rpc-webservice.xml
/bbossaop/src/org/frameworkset/spi/manager-rpc-jms.xml
/bbossaop/src/org/frameworkset/spi/manager-rpc-mina.xml

��������ĵ�Э���ܰ汾˵��
webservice - apache-cxf-2.2.4
mina - mina-2.0.0-M6
jms - ֧��jms �淶�ӿ�
jgroup - JGroups-2.7.0.GA

ʹ�÷����뿴����������
org/frameworkset/spi/remote/xxxx/TestClient.java
org/frameworkset/spi/remote/xxxx/TestServer.java
���е�xxxx��ʶ�����Э�����ƣ�jgroup��mina,jms��webservice

o property�ڵ�������init-method��destroy-method��������
		init-method��destroy-method�������Էֱ��Ӧaop����ṩ������InitializingBean��DisposableBean
		ʵ�ֵķ������������Ѿ�ʵ����InitializingBean�Ͳ���Ҫָ��init-method����
		������ʵ����DisposableBean�ӿھͲ���Ҫָ��destroy-method����
		
o �����������ڹ���ӿ�
public interface InitializingBean {
    
    void afterPropertiesSet() throws Exception;

}

public interface DisposableBean
{
    /**
     * Invoked by a BeanFactory on destruction of a singleton.
     * @throws Exception in case of shutdown errors.
     * Exceptions will get logged but not rethrown to allow
     * other beans to release their resources too.
     */
    void destroy() throws Exception;
}

InitializingBean�ӿ��ж�����afterPropertiesSet�������������ʵ�����������һ����������ʵ����������aop��ܽ�
���ýӿڷ���afterPropertiesSet�����ʵ�����г�ʼ��

DisposableBean������destroy�������������ʵ�����������һ��ϵͳ�˳�����������ʱ��aop��ܽ����������destroy������
�������ʵ�������ͷ���Դ�������ڵ���ģʽ

��س���
/bbossaop/src/org/frameworkset/spi/BaseSPIManager.java
/bbossaop/src/org/frameworkset/spi/BeanDestroyHook.java
/bbossaop/src/org/frameworkset/spi/assemble/BeanAccembleHelper.java
  
o BaseSPIManager����������˷�����
	public static Object getBeanObject(String name)--������ȡ��ͨ�����ʵ��<ͨ��property��������>
													 ͨ��manager��providerָ���������Ȼʹ��
													 public static Object getProvider(String providerManagerType)�ӿ�
													 ��ȡ
	
	
	
o �����µ����Զ����﷨���޸�ϵͳ������ע�����

o �����Զ����Map���ͣ�org.frameworkset.spi.assemble.ProviderParser$ProMap<K, V>
o �����Զ����Map���ͣ�org.frameworkset.spi.assemble.ProviderParser$ProList<K, V>
o �����Զ����Map���ͣ�org.frameworkset.spi.assemble.ProviderParser$ProSet<K, V>
o �޸�����������property�ڵ�Ķ��壺
    properties(property*)
	properties-attributelist{
		name-ָ��һ�����Եķ�������		
	}
	property(property*,map,list,set)
	property-attributelist{
		name-ָ����������
		label-ָ�����Եı��
		value-ָ�����Ե�ֵ������ֵҲ����ͨ��property�ڵ�������ı�ָ��
		class-ָ��ֵ��Ӧ��java�������ƣ�int,boolean,string,�û��Զ��������	
		refid-�ڵ�����ֵΪrefid��Ӧ�����Ի��߷����ṩ�ߵ�ֵ
		      ���������ǰ���ã�attr@��Ϊǰ׺
		      ������õ��Ƿ���service@��Ϊǰ׺��
		      �������õĽ��������÷��������ķ�ʽ���滻��û�м��ص��������Ժͷ���
	}
	map(property+)
	list(property+)
	set(property+)

  �޸��˽ڵ�Ķ��巽��
  
  ʹ��˵����
  ���ã�
  <property name="connection.params">
		<!-- http://activemq.apache.org/maven/activemq-core/xsddoc/http___activemq.org_config_1.0/element/connectionFactory.html -->
			<map>
				<property label="alwaysSessionAsync"  name="alwaysSessionAsync" value="true" class="boolean">
					<description> <![CDATA[If this flag is set then a seperate thread is not used for dispatching messages for each Session in the Connection.
									 However, a separate thread is always used if there is more than one session, 
									or the session isn't in auto acknowledge or dups ok mode]]></description>
				</property>
				<property label="alwaysSyncSend" name="alwaysSyncSend" value="false" class="boolean">
					<description> <![CDATA[Set true if always require messages to be sync sent  ]]></description>
				</property>
				<property label="closeTimeout" name="closeTimeout" value="15000" >
					<description> <![CDATA[Sets the timeout before a close is considered complete. ]]></description>
				</property>
				<property label="copyMessageOnSend" name="copyMessageOnSend" value="true" >
					<description> <![CDATA[Should a JMS message be copied to a new JMS Message object as part of the send() method in JMS.  ]]></description>
				</property>
				<property label="disableTimeStampsByDefault" name="disableTimeStampsByDefault" value="false" >
					 <description> <![CDATA[Sets whether or not timestamps on messages should be disabled or not.]]></description>
				</property>
				<property label="dispatchAsync" name="dispatchAsync" value="true" >
					<description> <![CDATA[ Enables or disables the default setting of whether or not consumers have their messages <a href="http://activemq.apache.org/consumer-dispatch-async.html">dispatched synchronously or asynchronously by the broker</a>. ]]></description>
				</property>
				<property label="objectMessageSerializationDefered" name="objectMessageSerializationDefered" value="false" >
					<description> <![CDATA[When an object is set on an ObjectMessage, the JMS spec requires the object to be serialized by that set method. ]]></description>
				</property>
				<property label="optimizeAcknowledge" name="optimizeAcknowledge" value="false" >
					<description> <![CDATA[ ]]></description>
				</property>
				<property label="optimizedMessageDispatch" name="optimizedMessageDispatch" value="true" >
					<description> <![CDATA[ If this flag is set then an larger prefetch limit is used - only applicable for durable topic subscribers. ]]></description>
				</property>
				<property label="producerWindowSize" name="producerWindowSize" value="0" >
					<description> <![CDATA[ producerWindowSize ]]></description>
				</property>
				<property label="statsEnabled" name="statsEnabled" value="false" >
					<description> <![CDATA[ statsEnabled ]]></description>
				</property>
				<property label="useAsyncSend" name="useAsyncSend" value="false" >
					<description> <![CDATA[ Forces the use of <a href="http://activemq.apache.org/async-sends.html">Async Sends</a> which adds a massive performance boost; but means that the send() method will return immediately whether the message has been sent or not which could lead to message loss.   ]]></description>
				</property>
				<property label="useCompression" name="useCompression" value="false" >
					<description> <![CDATA[  Enables the use of compression of the message bodies   ]]></description>
				</property>
				<property label="useRetroactiveConsumer" name="useRetroactiveConsumer" value="false" >
					<description> <![CDATA[  Sets whether or not retroactive consumers are enabled   ]]></description>
				</property>
				<property label="watchTopicAdvisories" name="watchTopicAdvisories" value="true" >
					<description> <![CDATA[  watchTopicAdvisories   ]]></description>
				</property>
				<property label="sendTimeout" name="sendTimeout" value="0" >
					<description> <![CDATA[  sendTimeout   ]]></description>
				</property>
				<property label="durableTopicPrefetch" name="durableTopicPrefetch" value="100">
					<description> <![CDATA[  durableTopicPrefetch   ]]></description>
				</property>
				<property label="inputStreamPrefetch" name="inputStreamPrefetch" value="100" >
					<description> <![CDATA[  inputStreamPrefetch   ]]></description>
				</property>
				<property label="maximumPendingMessageLimit" name="maximumPendingMessageLimit" value="0" >
					<description> <![CDATA[  maximumPendingMessageLimit   ]]></description>
				</property>
				<property label="optimizeDurableTopicPrefetch" name="optimizeDurableTopicPrefetch" value="1000" >
					<description> <![CDATA[  optimizeDurableTopicPrefetch  ]]></description>
				</property>
				<property label="queueBrowserPrefetch" name="queueBrowserPrefetch" value="500" >
					<description> <![CDATA[  queueBrowserPrefetch]]></description>
				</property>
				<property label="queuePrefetch" name="queuePrefetch" value="1000" >
					<description> <![CDATA[  queuePrefetch]]></description>
				</property>
				<property label="topicPrefetch" name="topicPrefetch" value="32766" >
					<description> <![CDATA[ topicPrefetch ]]></description>
				</property>
				<property label="�Ƿ�ʹ�����ӳ�" name="useConnectionPool" value="�Ƿ�ʹ�����ӳ�" >
					<description> <![CDATA[ trueʹ�ã�false��ʹ�� ]]></description>
				</property>				
			</map>
		</property>	
		
		java����
  ProMap map = BaseSPIManager.getMapProperty("connection.params");
        System.out.println(map);
        System.out.println("alwaysSessionAsync��" + map.getBoolean("alwaysSessionAsync"));
        System.out.println("closeTimeout��" + map.getInt("closeTimeout"));
        System.out.println("useConnectionPool String��" + map.getString("useConnectionPool"));
        System.out.println("useConnectionPool Pro object:��" + map.getPro("useConnectionPool"));
        
        /**
         * �Ƿ���ȡ����
         */
        try
        {
            System.out.println("useConnectionPool int��" + map.getInt("useConnectionPool"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        /**
         * �Ƿ���ȡboolean
         */
        try
        {
            System.out.println("useConnectionPool boolean��" + map.getBoolean("useConnectionPool"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
o BaseSPIManager������ȡ��ǰ������ͨ�Ľڵ��嵥�ķ���
/**
     * ��ȡ��ǰ������ͨ�Ľڵ��嵥
     */
    @SuppressWarnings("unchecked")
    public static List<RPCAddress> getAllNodes()
    
  �����ʹ�÷���Ϊ��
  public static void testGetAllNodes()
    {
        List<RPCAddress> addrs = BaseSPIManager.getAllNodes();
    }  
    
   RPCAddress�Ķ������£�
   public class RPCAddress
{
    private String ip;
    private InetAddress ipAddress;
    public String getIp()
    {
        return ip;
    }
    public void setIp(String ip)
    {
        this.ip = ip;
    }
    public int getPort()
    {
        return port;
    }
    public void setPort(int port)
    {
        this.port = port;
    }
    private int port;
    public RPCAddress(String ip,int port)
    {
        this.ip = ip;
        this.port = port;
    }
    public RPCAddress(InetAddress ipAddress, int port)
    {
        this.ipAddress = ipAddress;
        this.port = port;
        this.ip = this.ipAddress.getHostAddress();
    }
    
    public String getHostName()
    {
        return this.ipAddress.getHostName();
    }
    
    public String getCanonicalHostName()
    {
        return this.ipAddress.getCanonicalHostName();
    }
} 
o �޸�D:\workspace\bbossaop\src\com\chinacreator\spi\assemble\ProviderParser.java��
	����ProMap�ڲ���̬�࣬�������չ��java.util.HashMap�࣬���������·��������ṩint��
	boolean��String�����������ԵĻ�ȡ��Ĭ��ֵ��ȡ������
	
	public int getInt(String key)
	public int getInt(String key,int defaultValue)
	public boolean getBoolean(String key)
	public boolean getBoolean(String key,boolean defaultValue)
	public String getString(String key)
	public String getString(String key,String defaultValue)
	
	bboss aop��ܽ������ProMap����Ϊ����Map���͵Ĳ����洢����
	
o �������ݿ����ӳ�
bbossaop/lib/frameworkset-pool.jar

o ��չ����property�ڵ㹦��
  ֧��list��map�������͵����ԣ�֧������Ƕ��
 bbossaop/src/org/frameworkset/spi/BaseSPIManager.java
bbossaop/test/com/chinacreator/spi/properties/TestProperties.java
/bbossaop/test/com/chinacreator/spi/properties/mq-init.xml
bbossaop/src/org.frameworkset.spi.assemble.ProviderParser 
bbossaop/src/org/frameworkset/spi/assemble/ServiceProviderManager.java
/bbossaop/src/manager-provider.xml
/bbossaop/src/org/framework/persitent/util/SQL.java

BaseSPIManager.java���������½ӿڣ�
public static Object getObjectProperty(String name) ;
public static Object getObjectProperty(String name,Object defaultValue) ;
public static Set getSetProperty(String name) ;
public static Set getSetProperty(String name,Set defaultValue) ;
public static List getListProperty(String name) ;
public static List getListProperty(String name,List defaultValue) ;
public static Map getMapProperty(String name) ;
public static Map getMapProperty(String name,Map defaultValue) ;

���ļ�/bbossaop/test/com/chinacreator/spi/properties/mq-init.xml�а�����map��list��set����������ʵ����
  
o ����Զ��ͨѶ���������Զ��ͨѶ������
2009��5��2��

����jgroup
jgroup��2.4.1 ������2.7.0

ʵ��bboss aop Զ�̷���İ�ȫ����֤����
/bbossaop/src/org/frameworkset/remote/SecurityContext.java

ʵ��˼·��

��֤�������û������������֤���ƣ�����ssl ��ȫ����������
��Ȩ��֧�������ɫ���ʿ��ƹ��ܣ�֧������������ʿ��ƹ���


ʵ�ֶ������紫����ʽ

unicast::192.19:1180;192.19:1182 �������÷�ʽ
muticast::192.19:1180;192.19:1182 �ಥ���÷�ʽ��Ҫ��ÿ���ڵ�����ǵ�ǰ��Ⱥ�еĽڵ㣬����ϵͳ����

ʵ��·�ɹ���

ʵ��unicat���������鲥����

o ʵ���첽�ص�����

o Э���ļ�����
tcp 
udp -Ĭ��ֵ
������������
<property name="cluster_protocol" value="udp"/>	
���������ļ�
replSync-service-aop-tcp.xml

o  �¼�������Զ���¼��޷����͵�����
ԭ���ǣ�
/bbossaop/src/org/frameworkset/remote/Target.java��û�����л�����������
/bbossevent/src/org/frameworkset/event/EventTarget.java�ڷ���ʱ�޷�ʶ����target�еĶ���

o �޸����Զ����﷨������ͨ���ڵ�ֵָ������ֵ
propertie�ڵ��п��԰�������ֵ
�޸ĵĳ���
/bbossaop/src/org/frameworkset/spi/assemble/ProviderParser.java

�ṩproperty�ڵ��У�鹦�ܣ����û��ָ��name���ԣ��ں�̨�׳���Ӧ���쳣��Ϣ

o  ����ģʽ�´�������ʵ����ͬ����
�޸ĵĳ�������
D:\workspace\bbossaop\src\com\chinacreator\spi\assemble\SecurityProviderInfo.java
public Object getSingleProvider(Context parent) 
 /**
     * ����ģʽ�´�������ʵ����ͬ����
     */
    private Object lock = new Object();

o ����sql������ù�������
D:\workspace\bbossaop\src\org\framework\persitent\util\SQL.java
ͨ������࣬���ǿ����Ƚ���ͬ���ݿ��sql���������aop���ȫ�������ļ��У�Ȼ��ͨ�����SQL.java��������ȡ��ͬ�����ݿ����͵�ql���
���磺
<properties name="insertContact">		
		<property name="insertContact-mysql">
			<![CDATA[insert into CONTACTS (ANNIVERSARY_DAY, ANNIVERSARY_MONTH, BIRTH_DAY, 
					BIRTH_MONTH, EMAIL_ALTERNATE, EMAIL_PRIMARY, 
					FIRST_NAME, GSM_NO_ALTERNATE, GSM_NO_PRIMARY, HOME_ADDRESS, 
					HOME_CITY, HOME_COUNTRY, HOME_FAKS, HOME_PHONE, 
					HOME_PROVINCE, HOME_ZIP, LAST_NAME, MIDDLE_NAME, 
					NICK_NAME, PERSONAL_NOTE, SEX, SPOUSE_NAME, TITLE, 
					USERNAME, WEB_PAGE, WORK_ADDRESS, 
					WORK_ASSISTANT_NAME, WORK_CITY, WORK_COMPANY, WORK_COUNTRY,
					 WORK_DEPARTMENT, WORK_FAKS, WORK_JOB_TITLE, WORK_MANAGER_NAME, 
					 WORK_OFFICE, WORK_PHONE, WORK_PROFESSION, WORK_PROVINCE, 
					 WORK_ZIP) 
					 values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 
					 ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)]]> 
	
		</property>
		<property name="insertContact-oracle">
			<![CDATA[insert into CONTACTS (id,ANNIVERSARY_DAY, ANNIVERSARY_MONTH, BIRTH_DAY, 
					BIRTH_MONTH, EMAIL_ALTERNATE, EMAIL_PRIMARY, 
					FIRST_NAME, GSM_NO_ALTERNATE, GSM_NO_PRIMARY, HOME_ADDRESS, 
					HOME_CITY, HOME_COUNTRY, HOME_FAKS, HOME_PHONE, 
					HOME_PROVINCE, HOME_ZIP, LAST_NAME, MIDDLE_NAME, 
					NICK_NAME, PERSONAL_NOTE, SEX, SPOUSE_NAME, TITLE, 
					USERNAME, WEB_PAGE, WORK_ADDRESS, 
					WORK_ASSISTANT_NAME, WORK_CITY, WORK_COMPANY, WORK_COUNTRY,
					 WORK_DEPARTMENT, WORK_FAKS, WORK_JOB_TITLE, WORK_MANAGER_NAME, 
					 WORK_OFFICE, WORK_PHONE, WORK_PROFESSION, WORK_PROVINCE, 
					 WORK_ZIP) 
					 values (SEQ_CONTACT.NEXTVAL,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 
					 ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)]]> 
	
		</property>
</properties>

����ͨ��SQL.java�ṩ�ķ�������ȡ��Ӧ��sql��䣺

String sql = SQL.getSQL(dbname,"insertContact");
˵��:
dbname��Ӧ��poolman.xml�ļ������õ�����Դ�е�dbname���־ò��ܻ��Զ�ʶ���Ӧ������Դ�����ݿ����ͣ�Ȼ���ȡ��
��ͬ��insertContact��䣬����dbname��Ӧ�����ݿ���mysql����ô�ͻ��ȡ��insertContact-mysql��Ӧ��sql���
�����oracle����ô��ȡ����insertContact-oracle��Ӧ����䡣


     

------------------------------
 1.0.6 - 2009-04-24
------------------------------

o add rpc component to bboss aop,supports rpc service like ejb or rmi using bboss aop.

o add global system property confige function

more details see my blog [http://blog.csdn.net/yin_bp]

1.	����Զ�̹������
2.	ʵ��aop ���Զ�̷�����ù���
3.	����ȫ���������ù���



------------------------------
 1.0.5 - 2009-02-12
------------------------------
 
 o extend ioc function, support constructor ioc,such as:
   
   <manager id="constructor.a" 
		singlable="true">
		<provider type="A" default="true"
			class="com.chinacreator.spi.constructor.ConstructorImpl" />		
		<construction>	
			<param value="hello world"/>
			<param refid="interceptor.a"/>
			<param type="com.chinacreator.spi.constructor.Test"/>
		</construction>
	</manager>
   
   note:
   add construction node to support construction ioc.
   sub node param of construction give contructor's parameters,the param node order of construction node is according to 
   the order of Constructor of the service.
   throw param node you can :
       give the value of a constructor parameter.
       give a service reference for a constructor parameter.
       give a common class that can been instance by the ioc container for a constructor parameter.    
 o extend properties ioc ��add a properties [class] for reference node to support:
      give a common class that can been instance by the ioc container to set the value of a field of the service.
      such as:
      <reference fieldname="test" class="com.chinacreator.spi.reference.Test"/>
      

------------------------------
 1.0.4 - 2009-02-05
------------------------------
 o update frameworkset-util.jar.
 o update frameworkset-pool.jar.
 o update frameworkset.jar.
 o support all interfaces of spi service.
   expample:
   class A implements interface AI,and AI extends interface BaseAI,and so on;
   A extends class BaseA and BaseA implements interface IBaseAI and so on;
   
   then you config service A in manager-provider-xxxx.xml with name servevie.a,use BaseSPIManager to get an instance of A,the following is allowed:
   
   AI inst = (AI)BaseSPIManager.getProvider("servevie.a");
   BaseAI inst = (BaseAI)BaseSPIManager.getProvider("servevie.a");
   IBaseAI inst = (IBaseAI)BaseSPIManager.getProvider("servevie.a");
   
   
 
 
------------------------------
 1.0.3 - 2009-02-04
------------------------------
 o Fixed java.lang.NullPointerException in org/frameworkset/spi/interceptor/InterceptorWrapper.java.
 
 
------------------------------
 1.0.2 - 2008-12-25
------------------------------ 


------------------------------
 1.0.1 - 2008-12-24
------------------------------ 
First version of bboss-aop-1.0.1 released.

[http://blog.csdn.net/yin_bp]
[http://www.openbboss.com]