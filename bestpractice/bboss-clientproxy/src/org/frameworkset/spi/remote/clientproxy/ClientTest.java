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

package org.frameworkset.spi.remote.clientproxy;

import org.frameworkset.spi.ClientProxyContext;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>Title: ClientTest.java</p> 
 * <p>Description: </p>
 * <p>bboss workgroup</p>
 * <p>Copyright (c) 2007</p>
 * @Date 2011-10-20 ����01:12:19
 * @author biaoping.yin
 * @version 1.0
 */
public class ClientTest {
	ClientInf mvcinf ;
	WSService WSService; 
	ClientInf defaultinf ; 
	ClientInf simpleinf;
	@Before
	public void init()
	{
		//��ȡmvc�����������Զ�̷�����ýӿڣ�mvc�����ɷ����mvc����Զ���ʼ��
		mvcinf = ClientProxyContext.getWebMVCClientBean(
				"(http::localhost:8080/bboss-mvc/http.rpc)" +
				"/client.proxy.demo?user=admin&password=123456",
				ClientInf.class);
		
		//��ȡApplicationContext���������������Զ�̷�����ýӿ�
		//ApplicationContext�������������·�ʽ����
//		ApplicationContext context = ApplicationContext.getApplicationContext("org/frameworkset/web/ws/testwsmodule.xml");
		WSService = ClientProxyContext.getSimpleClientBean("org/frameworkset/web/ws/testwsmodule.xml", 
				"(http::localhost:8080/bboss-mvc/http.rpc)" +
				"/mysfirstwsservice?user=admin&password=123456", 
				WSService.class);
		
		//��ȡ��������Ĭ�������������Զ�̷�����ýӿ�
		//��������Ĭ������manager-provider.xml���������·�ʽ����
//		ApplicationContext context = ApplicationContext.getApplicationContext();
		//�����Ǵ�ͳ��Զ�̷����ȡ��ʽ������Ҫ�󱾵�����Ӧ�Ľӿں����ʵ���Լ������ļ����µ�api�Ѿ���������������
//		context.getTBeanObject("(http::localhost:8080/bboss-mvc/http.rpc)" +
//				"/client.proxy.simpledemo?user=admin&password=123456",  ClientInf.class);
		defaultinf = ClientProxyContext.getApplicationClientBean( "(http::localhost:8080/bboss-mvc/http.rpc)" +
				"/client.proxy.simpledemo?user=admin&password=123456", ClientInf.class);
		
		//��ȡ�ͻ��˵��ô���ӿ�
		//������������org/frameworkset/spi/ws/webserivce-modules.xml���������·�ʽ����
//		DefaultApplicationContext context = DefaultApplicationContext.getApplicationContext("org/frameworkset/spi/ws/webserivce-modules.xml");
		simpleinf = ClientProxyContext.getSimpleClientBean("org/frameworkset/spi/ws/webserivce-modules.xml",//������ʶ
		                                                            "(http::localhost:8080/bboss-mvc/http.rpc)/client.proxy.simpledemo?user=admin&password=123456",//���������ַ 
		                                                            ClientInf.class);//����ӿ�
		TestInf TestInf = ClientProxyContext.getWebMVCClientBean(
		                                				"(http::localhost:8080/bboss-mvc/http.rpc)" +
		                                				"//dateconvert/*.html?user=admin&password=123456",
		                                				TestInf.class);
		
		//����Ԥ��
		mvcinf.helloworld("aaaa�����");
		WSService.sayHello("aaaa�����");
		simpleinf.helloworld("aaaa�����");
		defaultinf.helloworld("aaaa�����");
		String test = TestInf.dateconvert();
		System.out.println();
	}
	@Test
	public void testA()
	{
		
	}
	@Test
	public void testMvcClient()
	{
		long s = System.currentTimeMillis();
		//����Զ�̷�������,��������ý��
		String re = (mvcinf.helloworld("aaaa�����"));
		long e = System.currentTimeMillis();
		System.out.println("testMvcClient:" +re + "," + (e -s));
		
		
	}
	
	
	@Test
	public void testWSServiceClient()
	{
		
		long s = System.currentTimeMillis();
		//����Զ�̷�������,��������ý��
		String re = WSService.sayHello("aaaa�����");
		long e = System.currentTimeMillis();
		System.out.println("testWSServiceClient:" +re + "," + (e -s));
	}
	
	@Test
	public void testDefaultApplicationClient()
	{
		long s = System.currentTimeMillis();
		//����Զ�̷�������,��������ý��
		String re = (defaultinf.helloworld("aaaa�����"));
		long e = System.currentTimeMillis();
		System.out.println("testDefaultApplicationClient:" +re + "," + (e -s));
	}
	
	
	@Test
	public void testSimpleClient()
	{
		long s = System.currentTimeMillis();
		//����Զ�̷�������,��������ý��
		String re = (simpleinf.helloworld("aaaa�����"));
		long e = System.currentTimeMillis();
		System.out.println("testSimpleClient:" +re + "," + (e -s));
		
	}


}
