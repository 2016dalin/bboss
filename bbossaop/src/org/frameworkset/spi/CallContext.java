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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.frameworkset.spi.assemble.Context;
import org.frameworkset.spi.remote.Headers;
import org.frameworkset.spi.security.SecurityContext;

/**
 * <p>Title: CallContext.java</p> 
 * <p>Description: �������������</p>
 * <p>bboss workgroup</p>
 * <p>Copyright (c) 2007</p>
 * @Date 2010-2-9 ����10:43:54
 * @author biaoping.yin
 * @version 1.0
 */
public class CallContext implements java.io.Serializable
{
	private boolean isSOAApplication = false;
	private int  containerType = BaseApplicationContext.container_type_simple;
	private transient BaseApplicationContext applicationContext;
	private String  applicationContextPath ;
	
    /**
     * ������ð�ȫ������
     */
    private SecurityContext secutiryContext; 
    /**
     * ������õ���Ϣͷ���Լ�
     */
    private Headers headers;
    /**
     * �������ѭ������ע��������
     */
    private transient Context loopcontext;
    /**
     * Ӧ��ģ��������
     * @param applicationContext
     */
    public CallContext(BaseApplicationContext applicationContext)
    {
    	
        this.applicationContext = applicationContext;
        this.isSOAApplication = this.applicationContext instanceof SOAApplicationContext;
    }
    
    public CallContext(String applicationContext,int containerType)
    {
    	
        this.applicationContextPath = applicationContext;
        this.containerType = containerType;
        
    }
    
    public SecurityContext getSecutiryContext()
    {
        return secutiryContext;
    }
    public void setSecutiryContext(SecurityContext secutiryContext)
    {
        this.secutiryContext = secutiryContext;
    }
    public Headers getHeaders()
    {
        return headers;
    }
    public void setHeaders(Headers headers)
    {
        this.headers = headers;
    }
    public Context getLoopContext()
    {
        return loopcontext;
    }
    public void setLoopContext(Context loopcontext)
    {
        this.loopcontext = loopcontext;
    }
    
    public boolean containHeaders()
    {
        return this.headers != null && this.headers.size() > 0;
    }
    public BaseApplicationContext getApplicationContext()
    {
    	if(applicationContext != null)
    	{
    		return applicationContext;
    	}
    	
    	return applicationContext = BaseApplicationContext.getBaseApplicationContext(this.applicationContextPath,this.containerType);
    	
    }
    
	
	public boolean isSOAApplication()
	{
	
		return isSOAApplication;
	}
	
	
   
}
