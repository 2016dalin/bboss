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
package com.frameworkset.common.tag.pager;
import java.util.Map;

import org.apache.log4j.Logger;

import com.frameworkset.common.poolman.util.SQLUtil.DBHashtable;
import com.frameworkset.util.ValueObjectUtil;
/**
 * ��װ����ҵ�񷽷������ļ����е�ֵ�����Class�����public fields  
 * @author biaoping.yin
 * 2005-3-25
 * version 1.0
 */



public class ClassData 
{	
	private static final Logger log = Logger.getLogger(ClassData.class);
	
	/**
	 * ֵ����
	 */
	private Object valueObject;
	private Map data = null;
	private Object mapkey = null;
	
	private boolean toUpercase = true;
	/**
//	 * ֵ������ķ���
//	 */
//	private Method[] methods;
//	private Field[] fields;
	public ClassData()
	{
	}
	
	
	public ClassData(Map data)
	{
		this.data = data;
		if(data instanceof DBHashtable)
		{
			
		}
		else
		{
			this.toUpercase = false;
		}
	}
	public ClassData(Map data,boolean toUpercase)
	{
	    this.data = data;
	    this.toUpercase = toUpercase;
	}
	/**
	 * ����������һ��ֵ�����Field������Ϊ�������������ڶ�˽�е�valueObject����fields
	 * ������г�ʼ��
	 * @param valueObject - ֵ����,������ʼ��valueObjce����
	 * @param fields - ֵ�����������Է��ʵ�����������
	 */
//	public ClassData(Object valueObject, 
//					 Method[] methods, 
//					 Field[] fields)
//	{
//		this.setValueObject(valueObject);
////		this.methods = methods;
////		this.fields = fields;
//	}
	
	public ClassData(Object valueObject)
	{
	    
		this.setValueObject(valueObject);
//		this.methods = valueObject.getClass().getMethods();
//		this.fields = valueObject.getClass().getFields();		
	}
	
	public ClassData(Object valueObject,Object mapkey,boolean toUpercase)
	{
	    
		this.setValueObject(valueObject);
		this.mapkey = mapkey;
		this.toUpercase = toUpercase;
//		this.methods = valueObject.getClass().getMethods();
//		this.fields = valueObject.getClass().getFields();		
	}
	/**
	 * Access method for the valueObject property.
	 * 
	 * @return   the current value of the valueObject property
	 */
	public Object getValueObject()
	{
		if(this.data != null)
		{
			
			return data;
		}
		return valueObject;
	}
	
	
	/**
	 * Sets the value of the valueObject property.
	 * 
	 * @param aValueObject the new value of the valueObject property
	 */
	private void setValueObject(Object aValueObject)
	{
		if(aValueObject == null)
			return;
		
		if((aValueObject instanceof DBHashtable))
		{
			this.data = (Map)aValueObject;
		}
		else if((aValueObject instanceof Map))
		{
			this.data = (Map)aValueObject;
			toUpercase = false;
		}
		else
		{
			valueObject = aValueObject;
		}
	}
	
	/**
	 * ��methods�в�ѯ�����ֶε�getter Method����,����ҵ��򷵻ظö��󣬷��򷵻�null�����׳��쳣
	 * 
	 * @return Method
	 */
//	private Method seekMethod(String fieldName)
//	{
////		if (methods == null)
////			return null;
////		for (int i = 0; i < this.methods.length; i++)
////		{
////			if (methods[i]
////				.getName()
////				.equals(ValueObjectUtil.getMethodName(fieldName)))
////				return methods[i];
////		}
//		return null;
//	}
	/**
	 * ��fields�в�ѯ�������Ƶ�Field����,����ҵ��򷵻ظö��󣬷��򷵻�null�����׳��쳣
	 * 
	 * @return Field
	 */
//	private Field seekField(String fieldName)
//	{
////		if (fields == null)
////			return null;
////		for (int i = 0; i < this.fields.length; i++)
////		{
////			if (fields[i].getName().equals(fieldName))
////				return fields[i];
////		}
//		return null;
//	}
	

	/**
	 * ���ظ�������fieldName��ֵ��
	 * ����ֵ������ΪObject
	 * @param fieldName - ��������
	 * @return Object
	 */
	public Object getValue(String fieldName)
	{
		if (fieldName == null)
			return null;
		/**
		 * �����װ���ݵ�ֵ����ΪHashtableʱֱ�Ӵ�hashtable�л�ȡ���ݣ�
		 * ����ͨ��ֵ�����ȡ��Ӧ�ֶε�ֵ
		 */
		if(data != null)
		{
			if(this.toUpercase)
				return data.get(fieldName.toUpperCase());
			else
				return data.get(fieldName); 
		}
		else
		{
			
		    return ValueObjectUtil.getValue(valueObject,fieldName);
		}	
	}
	
    /**
     * @return Returns the data.
     */
    public Map getData() {
        return data;
    }
    /**
     * @param data The data to set.
     */
    public void setData(Map data) {
        this.data = data;
    }


	
	public Object getMapkey()
	{
	
		return mapkey;
	}


	
	
}
