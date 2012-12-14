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
package org.frameworkset.web.servlet.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.frameworkset.web.servlet.view.UrlBasedViewResolver;

/**
 * <p>Title: HandlerMeta.java</p> 
 * <p>Description: </p>
 * <p>bboss workgroup</p>
 * <p>Copyright (c) 2008</p>
 * @Date 2010-9-24
 * @author biaoping.yin
 * @version 1.0
 */

public  class HandlerMeta
{
	private Object handler;
	private Map<String,String> pathNames ;
	private static final Object trace = new Object();
	public HandlerMeta()
	{
		
	}
	public HandlerMeta(Object handler,Map<String,String> pathNames)
	{
		this.handler = handler;
		this.pathNames = pathNames;
	}
	
	/**
	 * @return the handler
	 */
	public Object getHandler() {
		return handler;
	}
	
	/**
	 * @return the handler name
	 */
	public String getHandlerName() {
		if(handler == null)
			return null;
		if(handler instanceof String)
			return (String)handler;
		else
			return handler.getClass().getCanonicalName();
	}
	/**
	 * @return the pathNames
	 */
	public Map<String,String> getPathNames() {
		return pathNames;
	}
	/**
	 * @param handler the handler to set
	 */
	public void setHandler(Object handler) {
		this.handler = handler;
	}
	/**
	 * @param pathNames the pathNames to set
	 */
	public void setPathNames(Map<String,String> pathNames) {
		this.pathNames = pathNames;
	}
	
	/**
	 * ��Ч������ѭ��
	 * @param path
	 * @param method
	 * @param handler
	 * @param request
	 * @return
	 * @throws PathURLNotSetException
	 */
	public String getUrlPath(String path,String method,Object handler,HttpServletRequest request) throws PathURLNotSetException
	{
		/*
		 * ���ȳ���2��������ȡ������2�α������õ�������࣬һ����������
		 * ������do-whileѭ������������õ�����
		 */
		//��һ�γ���
		String tmp = this.getPathNames() != null? this.getPathNames().get(path):null;
		
		if(tmp == null)
			throw new PathURLNotSetException(path,method,handler,request);
		if(!UrlBasedViewResolver.isPathVariable(tmp))
			return tmp;
		//�ڶ��γ���
		String old = tmp;
		tmp = this.getPathNames().get(tmp);
		if(tmp == null)
			throw new PathURLNotSetException(path,method,handler,request);
		if(!UrlBasedViewResolver.isPathVariable(tmp))
			return tmp;
		if(tmp.equals(old))
		{
			throw new PathURLNotSetException(path,old + "->" + tmp,method,handler,request);
		}
		
		//����do-whileѭ�����ڣ���¼ǰ���εĵ���·��
		List<String> trace = new ArrayList<String>(getPathNames().size());
		trace.add(old);
		trace.add(tmp);
		try
		{
			do
			{	
				tmp = this.getPathNames().get(tmp);
				if(tmp == null)
					throw new PathURLNotSetException(path,method,handler,request);
				
				if(!UrlBasedViewResolver.isPathVariable(tmp))
				{
					break;
				}
				if(trace.contains(tmp))
				{
					trace.add(tmp);
					throw new PathURLNotSetException(path,PathURLNotSetException.buildLooppath(trace),method,handler,request);
				}
				else
					trace.add(tmp);
				
				
			}
			while(true);
		}
		finally
		{
			trace.clear();
			trace = null;
		}
		
		return tmp;
	}
}
