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

import java.util.concurrent.atomic.AtomicLong;

import org.frameworkset.spi.ClientProxyContext;
import org.frameworkset.spi.DefaultApplicationContext;
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
	
	static java.util.concurrent.atomic.AtomicLong longcount = new AtomicLong(0);
	@Before
	public void init()
	{
		//��ȡmvc�����������Զ�̷�����ýӿڣ�mvc�����ɷ����mvc����Զ���ʼ��
		mvcinf = ClientProxyContext.getWebMVCClientBean(
				"(http::localhost:8080/bboss-mvc/http.rpc)" +
				"/client.proxy.demo",
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
	@Test
	public void testAopFactoryPattern()
	{
		 
		//������������
		DefaultApplicationContext context = DefaultApplicationContext.getApplicationContext("org/frameworkset/spi/remote/clientproxy/consumer.xml");
		//��ȡ�ͻ������ʵ��
		ClientInf client = context.getTBeanObject("clientservice", ClientInf.class);
		//����Զ�̷�������
		client.helloworld("aaa");
	}
	
	public static void testHttp()
	{
		WSService WSService = ClientProxyContext.getSimpleClientBean("org/frameworkset/web/ws/testwsmodule.xml", 
				"(http::localhost:8080/bboss-mvc/http.rpc)" +
				"/mysfirstwsservice", 
				WSService.class);
		WSService.sayHello("");
//		t tt = new t(WSService);
		t tt1 = new t(WSService);
		t tt2 = new t(WSService);
		t tt3 = new t(WSService);
		t tt4 = new t(WSService);
		t tt5 = new t(WSService);
		t tt6 = new t(WSService);
		t tt7 = new t(WSService);
		t tt8 = new t(WSService);
		t tt9 = new t(WSService);
		t tt10 = new t(WSService);
		long s = System.currentTimeMillis();
		tt1.start();
		tt2.start();
		tt3.start();
		tt4.start();
		tt5.start();
		tt6.start();
		tt7.start();
		tt8.start();
		tt9.start();
		tt10.start();
		
		try {
			tt1.join();
			tt2.join();
			tt3.join();
			tt4.join();
			tt5.join();
			tt6.join();
			tt7.join();
			tt8.join();
			tt9.join();
			tt10.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long e = System.currentTimeMillis();
		
		
//		long count = tt1.getCount() + tt2.getCount()
//				+ tt3.getCount()
//				+ tt4.getCount()
//				+ tt5.getCount()
//				+ tt6.getCount()
//				+ tt7.getCount()
//				+ tt8.getCount()
//				+ tt9.getCount()
//				+ tt10.getCount()
//				;
		long times = (10 * 10 * 10000)/((e-s)/1000);		System.out.println(times);
	}
	
	
	public static void testNetty()
	{
		RPCTestInf WSService = ClientProxyContext.getSimpleClientBean("org/frameworkset/spi/remote/manager-rpc-test.xml", 
				"(netty::192.168.1.22:12347)" +
				"/rpc.test", 
				RPCTestInf.class);
		WSService.sayHelloWorld("��ã����");
//		t tt = new t(WSService);
		tnetty tt1 = new tnetty(WSService);
		tnetty tt2 = new tnetty(WSService);
		tnetty tt3 = new tnetty(WSService);
		tnetty tt4 = new tnetty(WSService);
		tnetty tt5 = new tnetty(WSService);
		tnetty tt6 = new tnetty(WSService);
		tnetty tt7 = new tnetty(WSService);
		tnetty tt8 = new tnetty(WSService);
		tnetty tt9 = new tnetty(WSService);
		tnetty tt10 = new tnetty(WSService);
		long s = System.currentTimeMillis();
		tt1.start();
		tt2.start();
		tt3.start();
		tt4.start();
		tt5.start();
		tt6.start();
		tt7.start();
		tt8.start();
		tt9.start();
		tt10.start();
		
		try {
			tt1.join();
			tt2.join();
			tt3.join();
			tt4.join();
			tt5.join();
			tt6.join();
			tt7.join();
			tt8.join();
			tt9.join();
			tt10.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long e = System.currentTimeMillis();
		
		
//		long count = tt1.getCount() + tt2.getCount()
//				+ tt3.getCount()
//				+ tt4.getCount()
//				+ tt5.getCount()
//				+ tt6.getCount()
//				+ tt7.getCount()
//				+ tt8.getCount()
//				+ tt9.getCount()
//				+ tt10.getCount()
//				;
		long times = (10 * 10 * 10000)/((e-s)/1000);		System.out.println(times);
	}
	
	public static void main(String[] args)
	{
//		testHttp();
		testNetty();
		
	}

	public static class t extends Thread
	{
		WSService WSService; 
		public t(WSService WSService)
		{
			this.WSService = WSService;
		}
		
		public void run()
		{
			
			long e = 0;
			long i = 10  * 10000;
			
//			long s = System.currentTimeMillis();
			while(true)
			{
				String re = WSService.sayHello("���");
//				longcount.incrementAndGet();
//				e = System.currentTimeMillis() -s;
				e ++;
				if(e >= i)
					break;
			}
			
//			System.out.println("testMvcClient:" + (e -s));
		}
		
	
	}
	
	public static class tnetty extends Thread
	{
		RPCTestInf WSService; 
		public tnetty(RPCTestInf WSService)
		{
			this.WSService = WSService;
		}
		
		
		public void run()
		{
			
			long e = 0;
			long i = 10  * 10000;
			
//			long s = System.currentTimeMillis();
			while(true)
			{
				String re = WSService.sayHelloWorld("���,���");
//				longcount.incrementAndGet();
//				e = System.currentTimeMillis() -s;
				e ++;
				if(e >= i)
					break;
			}
			
//			System.out.println("testMvcClient:" + (e -s));
		}
		
		
	}

}
