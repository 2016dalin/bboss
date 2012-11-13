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
package org.frameworkset.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.frameworkset.util.ClassUtil.ClassInfo;
import org.frameworkset.util.ClassUtil.PropertieDescription;

/**
 * <p> ClassWrapper.java</p>
 * <p> Description:���ݴ�������ݶ����ṩ��Ӧ�Ĳ������ݶ���ķ�����
 * 1.�������Ե�ֵ
 * 2.��ȡ����ֵ
 * 3.��ȡ�����Ӧ����������Ϣ��������Ϣ��������Ϣ�ȵȣ�
 *  </p>
 * <p> bboss workgroup </p>
 * <p> Copyright (c) 2009 </p>
 * 
 * @Date 2012-11-13 ����4:06:11
 * @author biaoping.yin
 * @version 1.0
 */
public class ClassWrapper {
	private Object obj;
	private ClassInfo classInfo;
	public ClassWrapper(Object obj)
	{
		this.obj = obj;
		if(obj != null)
			this.classInfo = ClassUtil.getClassInfo(obj.getClass());
	}
	
	public void setPropertyValue(String property, Object value)
	{
		if(this.obj != null)
		{
			this.classInfo.setPropertyValue(obj, property, value);
		}
	}
	public Object getPropertyValue(String property)
	{
		if(this.obj != null)
		{
			return this.classInfo.getPropertyValue(obj, property);
		}
		return null;
		
	}
	
	public Object getPropertyValue(String property,Object defaultValue)
	{
		Object ret = null;
		if(this.obj != null)
		{
			ret = this.classInfo.getPropertyValue(obj, property);
		}
		if(ret == null)
			return defaultValue;
		return ret;
		
	}
	
	public Method[] getDeclaredMethods()
	{
		return this.classInfo.getDeclaredMethods();
		
	
	}
    
   
    public Field[] getDeclaredFields()
    {
    	return this.classInfo.getDeclaredFields(); 			
    }
    
    public List<PropertieDescription> getPropertyDescriptors()
    {
    	return this.classInfo.getPropertyDescriptors();
    }
    
    
    public Method getDeclaredMethod(String name)
	{

    	return this.classInfo.getDeclaredMethod(name);
		
	}
    public Field getDeclaredField(String name)
    {
    	return this.classInfo.getDeclaredField(name);
    	
    		
    }
    
    
    public Class getClazz()
    {
    	return this.classInfo.getClazz();
    }
    
    
	public PropertieDescription getPropertyDescriptor(String name)
	{
		return this.classInfo.getPropertyDescriptor(name);
		
	}

	public Constructor getDefaultConstruction() throws NoSuchMethodException {
		return classInfo.getDefaultConstruction();
		
	}

	public boolean isPrimary() {
		return classInfo.isPrimary();
	}

	public boolean isBaseprimary() {
		return classInfo.isBaseprimary();
	}
	

}
