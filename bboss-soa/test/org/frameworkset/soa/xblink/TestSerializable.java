/**
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
package org.frameworkset.soa.xblink;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.frameworkset.soa.ObjectSerializable;
import org.junit.Test;

import com.frameworkset.util.FileUtil;
import com.thoughtworks.xstream.XStream;


/**
 * <p>TestSerializable.java</p>
 * <p> Description: </p>
 * <p> bboss workgroup </p>
 * <p> Copyright (c) 2009 </p>
 * 
 * @Date 2011-10-10
 * @author biaoping.yin
 * @version 1.0
 */
public class TestSerializable
{
	private static Logger log = Logger.getLogger(TestSerializable.class);
	 private static XStream xStream = new XStream(); 
	private void convertBeanToXStreamXml(int count,Person joe)
	{
		try
		{
			long start = System.currentTimeMillis();
			for(int i = 0; i < count; i++)
			{
				xStream.toXML(joe);
				
			}
			long end = System.currentTimeMillis();
			
			System.out.println("ִ��xtream beantoxml "+count+"�Σ���ʱ:"+(end - start)+"����");
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void convertXStreamXMLToBean(int count,String xml)
	{
		try
		{
			long start = System.currentTimeMillis();
			for(int i = 0; i < count; i++)
			{
				xStream.fromXML(xml);
			}
			long end = System.currentTimeMillis();
			
			System.out.println("ִ��xStream xmltobean "+count+"�Σ���ʱ:"+(end - start)+"����");
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void convertBeanToXml(int count,Person joe)
	{
		try
		{
			long start = System.currentTimeMillis();
			for(int i = 0; i < count; i++)
			{
				ObjectSerializable.toXML(joe);
			}
			long end = System.currentTimeMillis();
			
			System.out.println("ִ��bboss beantoxml "+count+"�Σ���ʱ:"+(end - start)+"����");
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void convertXMLToBean(int count,String xml)
	{
		try
		{
			long start = System.currentTimeMillis();
			for(int i = 0; i < count; i++)
			{
				ObjectSerializable.toBean( xml, Person.class);
			}
			long end = System.currentTimeMillis();
			
			System.out.println("ִ��bboss xmltobean "+count+"�Σ���ʱ:"+(end - start)+"����");
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/**
	 * 
	 */
	@Test
	public void test()
	{
		PhoneNumber phone = new PhoneNumber();
		phone.setCode(123);
		phone.setNumber("1234-456");

		PhoneNumber fax = new PhoneNumber();
		fax.setCode(123);
		fax.setNumber("<aaaa>9999-999</bbbb>");

	   Set dataSet = new TreeSet();
	   dataSet.add("aa");
	   dataSet.add("bb");
	   
	   List dataList = new ArrayList();
	   dataList.add("aa");
	   dataList.add("bb");
	   
	   Map dataMap = new HashMap();
	   dataMap.put("aa","aavalue");
	   dataMap.put("bb","bbvalue");
	   
	   String[] dataArray = new String[]{"aa","bb"};
	   String[][] datadoubleArray = new String[][]{{"aaa","bbb"},{"cccc","dddd"}};
		
		Person joe = new Person();
		joe.setFirstname("Joe");
		joe.setDataDoubleArray(datadoubleArray);
//		joe.setLastname("Walnes");
		//������֤bboss��Xstream�Ƿ�ᰴ��nullֵ���ݣ�Ҳ����˵lastname��Ĭ��ֵ"ssss"
		//���������ֶ���lastname����Ϊnull����������˵�����л���joe�е�lastnameӦ����null������Ĭ��ֵ"ssss"
		joe.setBirthdate(new Date());
		Date[] updates = new Date[]{new Date(),new Date()};
		joe.setUpdatedate(updates);
		joe.setLastname(null);
		joe.setPhone(phone);
		joe.setFax(fax);
		joe.setDataArray(dataArray);
		joe.setDataList(dataList);
		joe.setDataMap(dataMap);
		joe.setDataSet(dataSet);
		EnumData sex = EnumData.M;
		joe.setSex(sex);
		
		
		
		try
		{
			//Ԥ��bboss��xstream
			String xml = ObjectSerializable.toXML(joe);
			
			
			System.out.println(xml);
			Person person = ObjectSerializable.toBean(xml, Person.class);
			
			String xmlXstream = xStream.toXML(joe);
			Person p = (Person)xStream.fromXML(xmlXstream);
			System.out.println(xmlXstream);
			
			System.out.println();System.out.println("bboss���л�����������ʼ");System.out.println();
			
			long start = System.currentTimeMillis();			
			ObjectSerializable.toXML(joe);			
			long end = System.currentTimeMillis();			
			System.out.println("ִ��bboss beantoxml 1�Σ���ʱ:"+(end - start) +"����");
			
			
			convertBeanToXml(10,joe);
			
			
			convertBeanToXml(100,joe);
				
			
			convertBeanToXml(1000,joe);			
			
			
			convertBeanToXml(10000,joe);
			System.out.println();System.out.println("xstream���л�����������ʼ");System.out.println();
			start = System.currentTimeMillis();			
				xStream.toXML(joe);			
				 end = System.currentTimeMillis();			
				System.out.println("ִ��XStream beantoxml 1�Σ���ʱ:"+(end - start) +"����");
				
			convertBeanToXStreamXml(10,joe);
			convertBeanToXStreamXml(100,joe);
			convertBeanToXStreamXml(1000,joe);
			convertBeanToXStreamXml(10000,joe);
			
			System.out.println();System.out.println("bboss�����л�����������ʼ");System.out.println();
			start = System.currentTimeMillis();
			person =  ObjectSerializable.toBean(xml, Person.class);
			end = System.currentTimeMillis();			
			System.out.println("ִ��bboss xmltobean 1�Σ���ʱ:"+(end - start)+"����");			
			convertXMLToBean(10,xml);			
			convertXMLToBean(100,xml);
			convertXMLToBean(1000,xml);
			convertXMLToBean(10000,xml);
			
			System.out.println();System.out.println("xstream�����л�����������ʼ");System.out.println();
			start = System.currentTimeMillis();
			xStream.fromXML(xmlXstream);
			end = System.currentTimeMillis();			
			System.out.println("ִ��XStream xmltobean 1�Σ���ʱ:"+(end - start)+"����");
			convertXStreamXMLToBean(10,xmlXstream);
			convertXStreamXMLToBean(100,xmlXstream);
			convertXStreamXMLToBean(1000,xmlXstream);
			convertXStreamXMLToBean(10000,xmlXstream);
			
			//������������
			
			
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void test1()
	{
		PhoneNumber phone = new PhoneNumber();
		phone.setCode(123);
		phone.setNumber("1234-456");

		PhoneNumber fax = new PhoneNumber();
		fax.setCode(123);
		fax.setNumber("<aaaa>9999-999��������</bbbb>");

	   Set dataSet = new TreeSet();
	   dataSet.add("aa");
	   dataSet.add("bb");
	   
	   List dataList = new ArrayList();
	   dataList.add("aa");
	   dataList.add("bb");
	   
	   dataList.add(1);
	   
	   Map dataMap = new HashMap();
	   dataMap.put("aa","aavalue");
	   dataMap.put("bb","bbvalue");
	   
	   String[] dataArray = new String[]{"aa","bb"};
	   String[][] datadoubleArray = new String[][]{{"aaa","bbb"},{"cccc","dddd"}};
		
		Person joe = new Person();
		joe.setFirstname("Joe");
		joe.setDataDoubleArray(datadoubleArray);
//		joe.setLastname("Walnes");
		//������֤bboss��Xstream�Ƿ�ᰴ��nullֵ���ݣ�Ҳ����˵lastname��Ĭ��ֵ"ssss"
		//���������ֶ���lastname����Ϊnull����������˵�����л���joe�е�lastnameӦ����null������Ĭ��ֵ"ssss"
		joe.setBirthdate(new Date());
		Date[] updates = new Date[]{new Date(),new Date()};
		joe.setUpdatedate(updates);
		joe.setLastname(null);
		joe.setPhone(phone);
		joe.setFax(fax);
		joe.setDataArray(dataArray);
		joe.setDataList(dataList);
		joe.setDataMap(dataMap);
		joe.setDataSet(dataSet);
		EnumData sex = EnumData.M;
		joe.setSex(sex);
		
		
		
		try
		{
			//Ԥ��bboss��xstream
			String xml = ObjectSerializable.toXML(joe);
			
			
			System.out.println(xml);
			Person person = ObjectSerializable.toBean(xml, Person.class);
			System.out.println();
			
			
			//������������
			
			
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * @throws IOException 
	 * 
	 */
	@Test
	public void testBigData() throws IOException
	{
		//����ļ���������47565 �ֽڣ�Լ47k������
		String bigcontent = FileUtil.getFileContent(new File("D:\\workspace\\bbossgroups-3.2\\bboss-soa\\test\\org\\frameworkset\\soa\\testxstream.xml"), "GBK");
		PhoneNumber phone = new PhoneNumber();
		phone.setCode(123);
		phone.setNumber("1234-456");

		PhoneNumber fax = new PhoneNumber();
		fax.setCode(123);
		fax.setNumber(bigcontent);
		
	   Set dataSet = new TreeSet();
	   dataSet.add("aa");
	   dataSet.add("bb");
	   
	   List dataList = new ArrayList();
	   dataList.add("aa");
	   dataList.add("bb");
	   
	   Map dataMap = new HashMap();
	   dataMap.put("aa","aavalue");
	   dataMap.put("bb","bbvalue");
	   
	   String[] dataArray = new String[]{"aa","bb"};
	   
		
		Person joe = new Person();
		joe.setFirstname("Joe");
//		joe.setLastname("Walnes");
		//������֤bboss��Xstream�Ƿ�ᰴ��nullֵ���ݣ�Ҳ����˵lastname��Ĭ��ֵ"ssss"
		//���������ֶ���lastname����Ϊnull����������˵�����л���joe�е�lastnameӦ����null������Ĭ��ֵ"ssss"
		
		joe.setLastname(null);
		joe.setPhone(phone);
		joe.setFax(fax);
		joe.setDataArray(dataArray);
		joe.setDataList(dataList);
		joe.setDataMap(dataMap);
		joe.setDataSet(dataSet);
		
		
		
		try
		{
			//Ԥ��bboss��xstream
			String xml = ObjectSerializable.toXML(joe);
			
			
			System.out.println(xml);
			Person person = ObjectSerializable.toBean(xml, Person.class);
			
			String xmlXstream = xStream.toXML(joe);
			Person p = (Person)xStream.fromXML(xmlXstream);
			System.out.println(xmlXstream);
			
			System.out.println();System.out.println("bboss���л�����������ʼ");System.out.println();
			
			long start = System.currentTimeMillis();			
			ObjectSerializable.toXML(joe);			
			long end = System.currentTimeMillis();			
			System.out.println("ִ��bboss beantoxml 1�Σ���ʱ:"+(end - start) +"����");
			
			
			convertBeanToXml(10,joe);
			
			
			convertBeanToXml(100,joe);
				
			
			convertBeanToXml(1000,joe);			
			
			
			convertBeanToXml(10000,joe);
			System.out.println();System.out.println("xstream���л�����������ʼ");System.out.println();
			start = System.currentTimeMillis();			
				xStream.toXML(joe);			
				 end = System.currentTimeMillis();			
				System.out.println("ִ��XStream beantoxml 1�Σ���ʱ:"+(end - start) +"����");
				
			convertBeanToXStreamXml(10,joe);
			convertBeanToXStreamXml(100,joe);
			convertBeanToXStreamXml(1000,joe);
			convertBeanToXStreamXml(10000,joe);
			
			System.out.println();System.out.println("bboss�����л�����������ʼ");System.out.println();
			start = System.currentTimeMillis();
			person =  ObjectSerializable.toBean( xml, Person.class);
			end = System.currentTimeMillis();			
			System.out.println("ִ��bboss xmltobean 1�Σ���ʱ:"+(end - start)+"����");			
			convertXMLToBean(10,xml);			
			convertXMLToBean(100,xml);
			convertXMLToBean(1000,xml);
			convertXMLToBean(10000,xml);
			
			System.out.println();System.out.println("xstream�����л�����������ʼ");System.out.println();
			start = System.currentTimeMillis();
			xStream.fromXML(xmlXstream);
			end = System.currentTimeMillis();			
			System.out.println("ִ��XStream xmltobean 1�Σ���ʱ:"+(end - start)+"����");
			convertXStreamXMLToBean(10,xmlXstream);
			convertXStreamXMLToBean(100,xmlXstream);
			convertXStreamXMLToBean(1000,xmlXstream);
			convertXStreamXMLToBean(10000,xmlXstream);
			
			//������������
			
			
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	/**
	 * @throws IOException 
	 * 
	 */
	@Test
	public void testFileData() throws IOException
	{
		//����ļ���������47565 �ֽڣ�Լ47k������
		File fileData = new File("D:\\workspace\\bbossgroups-3.2\\bboss-soa\\test\\org\\frameworkset\\soa\\testxstream.xml");
		PhoneNumber phone = new PhoneNumber();
		phone.setCode(123);
		phone.setNumber("1234-456");

		PhoneNumber fax = new PhoneNumber();
		fax.setCode(123);
		fax.setNumber("<aaaa>9999-999</bbbb>");
		
	   Set dataSet = new TreeSet();
	   dataSet.add("aa");
	   dataSet.add("bb");
	   
	   List dataList = new ArrayList();
	   dataList.add("aa");
	   dataList.add("bb");
	   
	   Map dataMap = new HashMap();
	   dataMap.put("aa","aavalue");
	   dataMap.put("bb","bbvalue");
	   
	   String[] dataArray = new String[]{"aa","bb"};
	   
		
		FilePerson joe = new FilePerson();
		joe.setFileData(fileData);
		joe.setFirstname("Joe");
//		joe.setLastname("Walnes");
		//������֤bboss��Xstream�Ƿ�ᰴ��nullֵ���ݣ�Ҳ����˵lastname��Ĭ��ֵ"ssss"
		//���������ֶ���lastname����Ϊnull����������˵�����л���joe�е�lastnameӦ����null������Ĭ��ֵ"ssss"
		
		joe.setLastname(null);
		joe.setPhone(phone);
		joe.setFax(fax);
		joe.setDataArray(dataArray);
		joe.setDataList(dataList);
		joe.setDataMap(dataMap);
		joe.setDataSet(dataSet);
		
		
		
		try
		{
			//Ԥ��bboss��xstream
			String xml = ObjectSerializable.toXML(joe);
			
			
			System.out.println(xml);
			Person person = ObjectSerializable.toBean( xml, Person.class);
			
			String xmlXstream = xStream.toXML(joe);
			Person p = (Person)xStream.fromXML(xmlXstream);
			System.out.println(xmlXstream);
			
			System.out.println();System.out.println("bboss���л�����������ʼ");System.out.println();
			
			long start = System.currentTimeMillis();			
			ObjectSerializable.toXML(joe);			
			long end = System.currentTimeMillis();			
			System.out.println("ִ��bboss beantoxml 1�Σ���ʱ:"+(end - start) +"����");
			
			
			convertBeanToXml(10,joe);
			
			
			convertBeanToXml(100,joe);
				
			
			convertBeanToXml(1000,joe);			
			
			
			convertBeanToXml(10000,joe);
			System.out.println();System.out.println("xstream���л�����������ʼ");System.out.println();
			start = System.currentTimeMillis();			
				xStream.toXML(joe);			
				 end = System.currentTimeMillis();			
				System.out.println("ִ��XStream beantoxml 1�Σ���ʱ:"+(end - start) +"����");
				
			convertBeanToXStreamXml(10,joe);
			convertBeanToXStreamXml(100,joe);
			convertBeanToXStreamXml(1000,joe);
			convertBeanToXStreamXml(10000,joe);
			
			System.out.println();System.out.println("bboss�����л�����������ʼ");System.out.println();
			start = System.currentTimeMillis();
			person =  ObjectSerializable.toBean( xml, Person.class);
			end = System.currentTimeMillis();			
			System.out.println("ִ��bboss xmltobean 1�Σ���ʱ:"+(end - start)+"����");			
			convertXMLToBean(10,xml);			
			convertXMLToBean(100,xml);
			convertXMLToBean(1000,xml);
			convertXMLToBean(10000,xml);
			
			System.out.println();System.out.println("xstream�����л�����������ʼ");System.out.println();
			start = System.currentTimeMillis();
			xStream.fromXML(xmlXstream);
			end = System.currentTimeMillis();			
			System.out.println("ִ��XStream xmltobean 1�Σ���ʱ:"+(end - start)+"����");
			convertXStreamXMLToBean(10,xmlXstream);
			convertXStreamXMLToBean(100,xmlXstream);
			convertXStreamXMLToBean(1000,xmlXstream);
			convertXStreamXMLToBean(10000,xmlXstream);
			
			//������������
			
			
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	

}
