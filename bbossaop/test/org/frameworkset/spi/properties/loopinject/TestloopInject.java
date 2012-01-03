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

package org.frameworkset.spi.properties.loopinject;

import org.frameworkset.spi.ApplicationContext;
import org.frameworkset.spi.BaseApplicationContext;
import org.frameworkset.spi.DefaultApplicationContext;
import org.junit.Before;
import org.junit.Test;


/**
 * <p>Title: TestloopInject.java</p> 
 * <p>Description: bbossgroups 3.5��ǰ�İ汾��֧������ѭ������ע�룬
 * 3.5�Ժ�İ汾�ṩ�˺ܺõ�֧��,����֧�־ֲ�ѭ������
 * �ֲ�ѭ�����õ����Ƹ�ʽ����Ϊ��
 * name1->name12->name121
 * 
 * ���ڹ��캯��������ע�봦��
 * ���ڹ�������ע�봦��
 * ���ڴ����ÿ�ʼ������ע�봦���������֧��ѭ�����ã�������Ҫ��һ���Ľ���
 * </p>
 * <p>bboss workgroup</p>
 * <p>Copyright (c) 2007</p>
 * @Date 2010-1-17 ����08:41:46
 * @author biaoping.yin
 * @version 1.0
 */
public class TestloopInject
{
	BaseApplicationContext context ;
	@Before
	public void init()
	{
		context = DefaultApplicationContext.getApplicationContext("org/frameworkset/spi/properties/loopinject/loopinject.xml");
	}
	@Test
	public void injectloop()
	{
		A a = (A)context.getBeanObject("loop.a");
		
		System.out.println(a);
	}
	
	@Test
	public void selfinjectloop()
	{
		BaseApplicationContext context = ApplicationContext.getApplicationContext("org/frameworkset/spi/properties/loopinject/test.xml"); 
		Test1 a = (Test1)context.getBeanObject("test1");
		System.out.println(a);
	}
}
