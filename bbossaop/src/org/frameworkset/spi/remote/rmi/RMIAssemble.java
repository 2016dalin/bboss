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

package org.frameworkset.spi.remote.rmi;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.frameworkset.spi.BaseApplicationContext;
import org.frameworkset.spi.SOAFileApplicationContext;
import org.frameworkset.spi.assemble.Pro;
import org.frameworkset.spi.assemble.ProMap;

/**
 * <p>Title: RMIAssemble.java</p> 
 * <p>Description: </p>
 * <p>bboss workgroup</p>
 * <p>Copyright (c) 2007</p>
 * @Date 2011-5-18 ����04:17:55
 * @author biaoping.yin
 * @version 1.0
 */
public class RMIAssemble {
	private static final Logger logger = Logger.getLogger(RMIAssemble.class);
	
	private static Map<String,Pro> tracks = new HashMap<String,Pro>();
	private static int port = 1099;
	private static String ip = "localhost";
	private static String address = "rmi://localhost:1099";
	private static void initAddress()
	{
		SOAFileApplicationContext context = new SOAFileApplicationContext ("org/frameworkset/spi/manager-rpc-rmi.xml","GBK");
		try
		{
			ProMap map = context.getMapProperty("rpc.protocol.rmi.params");
			if(map == null)
			{
				ip = "localhost";
				port = 1099;
			}
			else
			{
				ip = map.getString("connection.bind.ip", "localhost");
				
				port = map.getInt("connection.bind.port", 1099);
			}
			address = "//" + ip + ":" + port;
			
		}
		finally
		{
			context.destroy(true);
		}
		
	}
	public static void loadAllRMIService()
	{
		initAddress();
		RMIUtil.startPort(port);
		try {
			loadModulesRMIService();
		} catch (Exception e) {
			logger.error(e);
		}
		try {
			loadMvcRMIService();
		} catch (Exception e) {
			logger.error(e);
		}
		
	}
	
	public static String getRealPath(String contextPath, String path) {
		
		
		if (contextPath == null || contextPath.equals("")) {
//			System.out.println("StringUtil.getRealPath() contextPath:"
//					+ contextPath);
			return path == null?"":path;
		}
		if (path == null || path.equals("")) {
			
			return contextPath;
		}
		
		contextPath = contextPath.replace('\\', '/');
		path = path.replace('\\', '/');
		if (path.startsWith("/") ) {
			
			if (!contextPath.endsWith("/"))
				return contextPath + path;
			else {
				return contextPath.substring(0,contextPath.length() - 1) + path;
			}

		} else {
			if (!contextPath.endsWith("/"))
				return contextPath + "/" + path;
			else {
				return contextPath + path;
			}
		}

	}
	
	
	/**
	 * ��Ҫȷ��mvc��������RMIService��������֮ǰ�����������ȡ�����κ���mvc��������õ�RMIService����
	 */
	private  static void loadMvcRMIService()
	{
		
		try {
			org.frameworkset.spi.BaseApplicationContext context = org.frameworkset.spi.DefaultApplicationContext
					.getApplicationContext("org/frameworkset/spi/rmi/rmi-assemble.xml");
			loaderContextRMIServices((BaseApplicationContext) context
					.getBeanObject("webapplicationcontext"));
		} catch (Exception e) {
			logger.warn(e);
		}
	}
	
	
	private  static void loadModulesRMIService()
	{
		org.frameworkset.spi.BaseApplicationContext context = org.frameworkset.spi.DefaultApplicationContext
		.getApplicationContext("org/frameworkset/spi/rmi/rmi-assemble.xml");
		String[] RMIServices_modules = context.getStringArray("rmi.services.modules");
		if(RMIServices_modules == null || RMIServices_modules.length == 0)
			return ;
		org.frameworkset.spi.ApplicationContext context_ = null;
		/**
		 * Ԥ��������wsģ��Ӧ��������
		 */
		for(String t:RMIServices_modules)
		{
			context_ = org.frameworkset.spi.ApplicationContext.getApplicationContext(t);
//			loaderContextRMIServices(context_);
		}
		/**
		 * ��ȡÿ��ģ���������е�RMIService���񲢼���֮
		 */
		for(String t:RMIServices_modules)
		{
			context_ = org.frameworkset.spi.ApplicationContext.getApplicationContext(t);
			loaderContextRMIServices(context_);
		}
		
	}
	
	public static void loaderContextRMIServices(BaseApplicationContext context)
	{
		if(context == null)
			return ;
		if (logger.isDebugEnabled()) {
			logger.debug("Looking up and Load RMIServices in application context: " + context.getConfigfile());
		}
		Set<String> beanNames = context.getPropertyKeys();
		if(beanNames == null || beanNames.size() == 0)
			return ;
		// Take any bean name that we can determine URLs for.
		Iterator<String> beanNamesItr = beanNames.iterator();
		while(beanNamesItr.hasNext()) {
			String beanName = beanNamesItr.next();
			try
			{
				Pro rmipro = context.getProBean(beanName);
				if(!rmipro.isRMIService())
					continue;
				else {
					registerRMI( rmipro);
				}
			}
			catch(Exception e)
			{
//				if (logger.) 
				{
					logger.error("Detect Handler bean name '" + beanName + "' failed: " + e.getMessage(),e);
				}
			}
			
		}
		
	}
	
	public static boolean hasPublished(String servicePort)
	{
		return  tracks.containsKey(servicePort);
	}
	
	public static Pro getPublishedRMIInfo(String servicePort)
	{
		return  tracks.get(servicePort);
	}
	
	public static void tracePublishedRMIInfo(String servicePort,Pro wsinfo)
	{
		tracks.put(servicePort,wsinfo);
	}
	
	
	private static void registerRMI(Pro rmipro) {
		try
        { 
            String servicePort = rmipro.getRMIAttribute("rmi:address");
            if(servicePort == null || servicePort.trim().equals(""))
                throw new java.lang.IllegalArgumentException("rmi service [name="+rmipro.getName() + ",servicePort="+servicePort+"] config error: must config servicePort attribute for rmi service ["+rmipro.getName() + "] in " + rmipro.getConfigFile()  );
            if(tracks.containsKey(servicePort))
            {
            	logger.warn("RMIService ["+rmipro.getName() + "] in "+rmipro.getConfigFile() 
            			+ " has been registed in "+ tracks.get(servicePort).getConfigFile() + ",ignored this regist action.");
            	return;
            }
            java.rmi.Remote RMIService = (java.rmi.Remote)rmipro.getBeanObject();
//           String temp = null;
//            if(servicePort.startsWith(address))
//            {
//            	temp = servicePort;
//            }
//            else
//            {
//            	temp = getRealPath(address, servicePort);
//            }
            if(RMIUtil.rebindservice(servicePort, RMIService))
//            	  if(RMIUtil.rebindservice(temp, RMIService))
            {
            	logger.debug("rmi���"+rmipro.getClazz()+"�����ڷ����ַ��" + servicePort);
            	tracks.put(servicePort, rmipro);
			
            }
            else
            {
            	 logger.debug("rmi���"+rmipro.getClazz()+"�ڷ����ַ[" + servicePort + "]��ʧ��,��ϸ�����鿴ϵͳ��־��");
            }
            
           
        }
        catch(Exception e)
        {        	
           logger.warn(e.getMessage(),e);
        }
		
	}
}
