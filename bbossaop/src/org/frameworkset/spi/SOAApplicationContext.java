/*
 *  Copyright 2008-2010 biaoping.yin
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

import java.io.InputStream;
import java.net.URL;

import org.apache.log4j.Logger;
import org.frameworkset.spi.assemble.Pro;
import org.frameworkset.spi.assemble.ServiceProviderManager;
import org.frameworkset.spi.remote.ServiceID;

/**
 * <p>Title: SOAApplicationContext.java</p> 
 * <p>Description: </p>
 * <p>bboss workgroup</p>
 * <p>Copyright (c) 2008</p>
 * @Date 2011-5-9 ����06:12:52
 * @author biaoping.yin
 * @version 1.0
 */
public class SOAApplicationContext extends DefaultApplicationContext {
	private static Logger log = Logger.getLogger(SOAApplicationContext.class);
	public SOAApplicationContext(String soacontent) {
		super((String)soacontent,false);
	}
	
	
	
	
	
	public SOAApplicationContext(URL file, String path)
	{
		super((URL)file,  path);
		
	}
	
	public SOAApplicationContext(InputStream instream)
	{
		super((InputStream)instream,  false);
		
	}





	/**
	 * ������������SOAApplicationContext�����ǲ����õģ��Ǻ�
	 * ��ȡָ���������ļ�������bean������������������ļ��Ӳ���configfile��Ӧ�����ļ���ʼ
	 * ��ͬ�������ļ���������������룬����䲻����������ϵ������Ҳ�������κ����ù�ϵ��
	 * 
	 * @return
	 * 
	 */
	public static SOAApplicationContext getApplicationContext(String configfile) {
		if (configfile == null || configfile.equals("")) {
			log.debug("configfile is null or empty.default Config File["
					+ ServiceProviderManager.defaultConfigFile
					+ "] will be used. ");
			configfile = ServiceProviderManager.defaultConfigFile;
		}
		SOAApplicationContext instance = (SOAApplicationContext)applicationContexts.get(configfile);
		if (instance != null)
		{
			instance.initApplicationContext();
			return instance;
		}
		synchronized (lock) {
			instance = (SOAApplicationContext)applicationContexts.get(configfile);
			if (instance != null)
				return instance;
			instance = new SOAApplicationContext(configfile);
			BaseApplicationContext.addShutdownHook(new BeanDestroyHook(instance));
			applicationContexts.put(configfile, instance);
			

		}
		instance.initApplicationContext();
		return instance;
	}
	
	
	/**
	 * bean������������� ���serviceID��Ϊ�գ���serviceID�Ǹ���getBeanObject(Context context,
	 * String name,Object defaultValue)������name���ɵ�
	 * ������Ҫ����providerManagerInfo��name����refid������serviceID
	 * 
	 * @param context
	 * @param providerManagerInfo
	 * @param defaultValue
	 * @param serviceID
	 * @return
	 */
	public Object getBeanObject(CallContext context, Pro providerManagerInfo,
			Object defaultValue, ServiceID serviceID) {
		if (providerManagerInfo == null)
			throw new SPIException("bean����Ϊ�ա�");
		String key = providerManagerInfo.getName();
		if (providerManagerInfo.isRefereced()) {
			Object retvalue = providerManagerInfo.getTrueValue(context,
					defaultValue);
			return retvalue;
		}

		Object finalsynProvider = this.providerManager.getBeanObject(context,
				providerManagerInfo, defaultValue);
		return finalsynProvider;
		
	}
	
	/**
	 * bean��������
	 * 
	 * @param context
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Object getBeanObject(CallContext context, String name, Object defaultValue,
			boolean fromprovider) {
		// �����������
		int idx = name.indexOf("?");

		String _name = name;
		if (context == null)
			context = new CallContext(this);
		if (idx > 0) {
			String params = name.substring(idx + 1);
			context = buildCallContext(params, context);
			// name = name.substring(0,idx);
		}

		
		// new ServiceID(name,GroupRequest.GET_ALL,0,ServiceID.result_rsplist,
		// ServiceID.PROPERTY_BEAN_SERVICE);
		Pro providerManagerInfo = this.providerManager
				.getPropertyBean(name);
		return getBeanObject(context, providerManagerInfo, defaultValue,
				null);
		
	}
	
	


}
