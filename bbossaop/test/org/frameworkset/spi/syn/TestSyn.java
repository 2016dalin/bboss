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
package org.frameworkset.spi.syn;

import org.frameworkset.spi.BaseSPIManager;
import org.frameworkset.spi.SPIException;
import org.junit.Test;


public class TestSyn {
	/**
	 * ��Ĭ�ϵ��ṩ������ִ�����е�ͬ������
	 */
	@Test
	public void testSynmethod()
	{
		try {
			AI a = (AI)BaseSPIManager.getProvider("syn.a");
			//���Բ��������ʹ�������ͬ��ͬ��������ͬʱ��Ĭ��db��ldap�����ṩ������ִ��
			a.testSynInvoke();
			a.testSynInvoke("hello word.");
			
			//���Է�ͬ��������ֻ��Ĭ�ϵ��ṩ������ִ�б�����
			a.testNoSynInvoke();
			
			
			
			//���Գ�����
			//�ӿ��ж���������testSameName������һ��������������Ҫͬ�����ã���һ��������������Ҫͬ�����ã�
			a.testSameName();
			a.testSameName("hello word.");
			
			//���Գ�����
			//�ӿ��ж���������testSameName1������һ������������Ҫͬ�����ã���һ����������������Ҫͬ�����ã�
			a.testSameName1();
			a.testSameName1("hello word.");
			
			//���Դ�����ֵ��ͬ��������ͬʱ��Ĭ��db��ldap�����ṩ������ִ�У�
			//��ֻ����Ĭ���ṩ�߷����ķ���ֵ
			System.out.println(a.testSynInvokeWithReturn());
			a.testInt(111);
			a.testInt(22);
			a.testInt(33);
			a.testInt(44);
			
			//�����׳��쳣�����з���ֵ��ͬ��������ͬʱ��Ĭ��db��ldap�����ṩ������ִ�У������Ƿ����쳣�����еķ�������
			//ִ��һ�飬Ĭ���ṩ�ߵķ����׳��쳣����ldap���͵��ṩ��ִ����ȷ
			System.out.println(a.testSynInvokeWithException());
		
		} catch (SPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testSameMethod()
	{
		try {
			AI a = (AI)BaseSPIManager.getProvider("syn.a");
			
			
			
			//���Գ�����
			//�ӿ��ж���������testSameName������һ��������������Ҫͬ�����ã���һ��������������Ҫͬ�����ã�
			a.testSameName();
			a.testSameName("hello word.");
			
			//���Գ�����
			//�ӿ��ж���������testSameName1������һ������������Ҫͬ�����ã���һ����������������Ҫͬ�����ã�
			a.testSameName1();
			a.testSameName1("hello word.");
			
			
		
		} catch (SPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void testSynmethodWithType()
	{
		try {
			AI a = (AI)BaseSPIManager.getProvider("syn.a","ldap");
			//���Բ��������ʹ�������ͬ��ͬ��������ͬʱ��Ĭ��db��ldap�����ṩ������ִ��
			a.testSynInvoke();
			a.testSynInvoke("hello word.");
			
			//���Է�ͬ��������ֻ��ldap�ṩ������ִ�б�����
			a.testNoSynInvoke();
			
			//���Գ�����
			//�ӿ��ж���������testSameName������һ��������������Ҫͬ�����ã���һ��������������Ҫͬ�����ã�
			a.testSameName();
			a.testSameName("hello word.");
			
			//���Գ�����
			//�ӿ��ж���������testSameName1������һ������������Ҫͬ�����ã���һ����������������Ҫͬ�����ã�
			a.testSameName1();
			a.testSameName1("hello word.");
			
			//���Դ�����ֵ��ͬ��������ͬʱ��Ĭ��db��ldap�����ṩ������ִ�У�
			//��ֻ����ldap�ṩ�߷����ķ���ֵ
			System.out.println(a.testSynInvokeWithReturn());
			
			//�����׳��쳣�����з���ֵ��ͬ��������ͬʱ��Ĭ��db��ldap�����ṩ������ִ�У������Ƿ����쳣�����еķ�������
			//ִ��һ�飬Ĭ���ṩ�ߵķ����׳��쳣����ldap���͵��ṩ��ִ����ȷ
			System.out.println(a.testSynInvokeWithException());
			a.testInt(22);
		} catch (SPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void testSameMethodWithType()
	{
		try {
			AI a = (AI)BaseSPIManager.getProvider("syn.a","ldap");
			
			
			//���Գ�����
			//�ӿ��ж���������testSameName������һ��������������Ҫͬ�����ã���һ��������������Ҫͬ�����ã�
			a.testSameName();
			a.testSameName("hello word.");
			
			//���Գ�����
			//�ӿ��ж���������testSameName1������һ������������Ҫͬ�����ã���һ����������������Ҫͬ�����ã�
			a.testSameName1();
			a.testSameName1("hello word.");
			
			
		} catch (SPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testIntWithType()
	{
		try {
			AI a = (AI)BaseSPIManager.getProvider("syn.a","ldap");
			
			
			
			System.out.println(a.testInt(10));
			System.out.println(a.testIntNoSyn(11));
			
			
			
		} catch (SPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testInt()
	{
		try {
			AI a = (AI)BaseSPIManager.getProvider("syn.a");
			System.out.println(a.testInt(10));
			System.out.println(a.testIntNoSyn(11));
		} catch (SPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
	public static void main(String[] args)
	{
//		testSynmethod();
//		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//		testSynmethodWithType();
//		testSameMethod();
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//		testSameMethodWithType();
		testInt();
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		testIntWithType();
	}
	
}
