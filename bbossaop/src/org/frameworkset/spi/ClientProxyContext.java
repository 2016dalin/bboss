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

package org.frameworkset.spi;

import org.frameworkset.spi.assemble.ServiceProviderManager;
import org.frameworkset.spi.cglib.CGLibUtil;
import org.frameworkset.spi.cglib.RemoteCGLibProxy;
import org.frameworkset.spi.remote.RPCHelper;
import org.frameworkset.spi.remote.ServiceID;




/**
 * <p>Title: ClientProxyContext.java</p> 
 * <p>Description: Զ�̷��������̬���ô��������ɸ��ݷ����ַ������ӿڣ������Ӧ��������ʶ
 * ���ɷ���Ŀͻ��˵��ô��������Ȼ��ͨ�����������ʵ��Զ�̷�����ã�ʹ��ʵ�����£�
 * 
 * @Test
	public void testMvcClient()
	{
		//��ȡmvc�����������Զ�̷�����ýӿڣ�mvc�����ɷ����mvc����Զ���ʼ��
		ClientInf inf = ClientProxyContext.getWebMVCClientBean(
				"(http::172.16.25.108:8080/bboss-mvc/http.rpc)" +
				"/client.proxy.demo?user=admin&password=123456",
				ClientInf.class);
		//����Զ�̷�������,��������ý��
		System.out.println(inf.helloworld("aaaa�����"));
		
		
	}
	
	
	@Test
	public void testApplicationClient()
	{
		//��ȡApplicationContext���������������Զ�̷�����ýӿ�
		WSService inf = ClientProxyContext.getApplicationClientBean("org/frameworkset/web/ws/testwsmodule.xml", 
				"(http::172.16.25.108:8080/bboss-mvc/http.rpc)" +
				"/mysfirstwsservice?user=admin&password=123456", 
				WSService.class);
		//ApplicationContext�������������·�ʽ����
//		ApplicationContext context = ApplicationContext.getApplicationContext("org/frameworkset/web/ws/testwsmodule.xml");
		//����Զ�̷�������,��������ý��
		System.out.println(inf.sayHello("aaaa�����"));
	}
	
	@Test
	public void testDefaultApplicationClient()
	{
		//��ȡ��������Ĭ�������������Զ�̷�����ýӿ�
		ClientInf inf = ClientProxyContext.getApplicationClientBean( "(http::172.16.25.108:8080/bboss-mvc/http.rpc)" +
				"/client.proxy.simpledemo?user=admin&password=123456", ClientInf.class);
		//����Զ�̷�������,��������ý��
		System.out.println(inf.helloworld("aaaa�����"));
		//��������Ĭ������manager-provider.xml���������·�ʽ����
//		ApplicationContext context = ApplicationContext.getApplicationContext();
		//�����Ǵ�ͳ��Զ�̷����ȡ��ʽ������Ҫ�󱾵�����Ӧ�Ľӿں����ʵ���Լ������ļ����µ�api�Ѿ���������������
//		context.getTBeanObject("(http::172.16.25.108:8080/bboss-mvc/http.rpc)" +
//				"/client.proxy.simpledemo?user=admin&password=123456",  ClientInf.class);
	}
	
	
	@Test
	public void testSimpleClient()
	{
		//��ȡ�ͻ��˵��ô���ӿ�
		ClientInf inf = ClientProxyContext.getSimpleClientBean("org/frameworkset/spi/ws/webserivce-modules.xml",//������ʶ
		                                                            "(http::172.16.25.108:8080/bboss-mvc/http.rpc)/client.proxy.simpledemo?user=admin&password=123456",//���������ַ 
		                                                            ClientInf.class);//����ӿ�
		//����Զ�̷�������,��������ý��
		System.out.println(inf.helloworld("aaaa�����"));
		
		//������������org/frameworkset/spi/ws/webserivce-modules.xml���������·�ʽ����
//		DefaultApplicationContext context = DefaultApplicationContext.getApplicationContext("org/frameworkset/spi/ws/webserivce-modules.xml");
	}
 * </p>
 * <p>bboss workgroup</p>
 * <p>Copyright (c) 2007</p>
 * @Date 2011-10-19 ����09:43:26
 * @author biaoping.yin
 * @version 1.0
 */
public class ClientProxyContext
{
	/**
	 * ��ȡDefaultApplicationContext���������еķ���������ô���
	 * @param <T> ��������
	 * @param context ������ʶ��һ����������ʼ���������ļ�·��
	 * @param name  ����������ʵ�ַ
	 * @param type  ����ӿ����ͣ�ʹ�÷�����ʵ�ֽӿڵ��Զ�����ת��
	 * @return ����������ô���
	 */
	public static <T> T getSimpleClientBean(String context,String name,Class<T> type)
	{
		ServiceID serviceID = buildServiceID(name,context,BaseApplicationContext.container_type_simple);
		CallContext ccontext = new CallContext(context,BaseApplicationContext.container_type_simple);
		BaseApplicationContext.buildClientCallContext(serviceID.getUrlParams(), ccontext);
		return CGLibUtil.getBeanInstance(type, new RemoteCGLibProxy(serviceID,ccontext));
		
	}
	
	/**
	  * ��ȡ�����Ĭ�������еķ���������ô���
	 * @param <T> ��������	 
	 * @param name  ����������ʵ�ַ
	 * @param type  ����ӿ����ͣ�ʹ�÷�����ʵ�ֽӿڵ��Զ�����ת��
	 * @return ����������ô���
	 */
	public static <T> T getApplicationClientBean(String name,Class<T> type)
	{
		return getApplicationClientBean(ServiceProviderManager.defaultConfigFile,name,type);
	}
	
	/**
	  * ��ȡApplicationContext���������еķ���������ô���
	 * @param <T> ��������
	 * @param context ������ʶ��һ����������ʼ���������ļ�·��
	 * @param name  ����������ʵ�ַ
	 * @param type  ����ӿ����ͣ�ʹ�÷�����ʵ�ֽӿڵ��Զ�����ת��
	 * @return ����������ô���
	 */
	public static <T> T getApplicationClientBean(String context,String name,Class<T> type)
	{
		ServiceID serviceID = buildServiceID(name,context,BaseApplicationContext.container_type_application);
		CallContext ccontext = new CallContext(context,BaseApplicationContext.container_type_application);
		BaseApplicationContext.buildClientCallContext(serviceID.getUrlParams(), ccontext);
		return CGLibUtil.getBeanInstance(type, new RemoteCGLibProxy(serviceID,ccontext));
	}
	
	/**
	  * ��ȡMVC�����еķ���������ô���
	 * @param <T>	�������� 
	 * @param name  ����������ʵ�ַ
	 * @param type  ����ӿ����ͣ�ʹ�÷�����ʵ�ֽӿڵ��Զ�����ת��
	 * @return ����������ô���
	 */
	public static <T> T getWebMVCClientBean(String name,Class<T> type)
	{
		ServiceID serviceID = buildServiceID(name,BaseApplicationContext.mvccontainer_identifier,BaseApplicationContext.container_type_mvc);
		CallContext ccontext = new CallContext(BaseApplicationContext.mvccontainer_identifier,BaseApplicationContext.container_type_mvc);
		BaseApplicationContext.buildClientCallContext(serviceID.getUrlParams(), ccontext);
		return CGLibUtil.getBeanInstance(type, new RemoteCGLibProxy(serviceID,ccontext));
	}
	
	public static ServiceID buildServiceID(String serviceid,String applicationcontext,int containerType) {
		return RPCHelper.buildClientServiceID(serviceid, applicationcontext		, containerType		 );

	}

}
