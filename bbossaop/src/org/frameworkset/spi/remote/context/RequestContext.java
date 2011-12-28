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

package org.frameworkset.spi.remote.context;

import java.util.Iterator;
import java.util.Map;

import org.frameworkset.spi.UtilMap;
import org.frameworkset.spi.assemble.SynchronizedMethod;
import org.frameworkset.spi.remote.Header;
import org.frameworkset.spi.remote.ServiceID;
import org.frameworkset.spi.security.SecurityContext;
import org.frameworkset.spi.security.SecurityException;

/**
 * <p>
 * Title: RequestContext.java
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * bboss workgroup
 * </p>
 * <p>
 * Copyright (c) 2007
 * </p>
 * 
 * @Date 2010-7-25 ����01:42:23
 * @author biaoping.yin
 * @version 1.0
 */
public class RequestContext {
	public RequestContext(SecurityContext securityContext, boolean setcontext) {
		super();
		this.securityContext = securityContext;
		if (securityContext == null)
			this.securityContext = new SecurityContext();
		this.securityContext.setRequest(this);
		if (setcontext)
			setRequestContext(this);
	}

	SecurityContext securityContext;
	private static final ThreadLocal<RequestContext> requestLocal = new ThreadLocal<RequestContext>();

	void setRequestContext(RequestContext context) {
		requestLocal.set(context);
	}

	public static void destoryRequestContext() {
		requestLocal.set(null);
	}

	SecurityContext getSecurityContext_() {
		return this.securityContext;
	}

	public static SecurityContext getSecurityContext() {
		RequestContext context = getRequestContext();
		return context != null ? context.getSecurityContext_() : null;
	}

	public static RequestContext getRequestContext() {
		RequestContext context = requestLocal.get();
		if (context == null)
			context = new RequestContext(new SecurityContext(), false);
		return context;
	}

	/**
	 * ִ��Զ�̵��õ�׼�����ܣ�����֤�ͼ�Ȩ
	 * 
	 * @param method_call
	 * @throws Throwable
	 */
	public void preMethodCall(ServiceID id, String method, Class[] types,
			Map<String, Header> headers) throws Throwable {
		try {
			// SecurityContext securityContext =
			// method_call.getSecurityContext();
			initPermissionInfo(id, method, types);
			if (SecurityContext.getSecurityManager().checkUser(securityContext)) {
				// initPermissionInfo( id, method, types);
				if (SecurityContext.getSecurityManager().checkPermission(
						securityContext)) {
					if (securityContext == null)
						securityContext = new SecurityContext();
					// SecurityContext.setSecurityContext(securityContext);
				} else {
					throw new SecurityException("Ȩ�޼��ʧ��,��ǰ�û��޷�ִ�з��������"
							+ securityContext);
				}
			} else {

				throw new SecurityException("��֤ʧ��,�����û�ƾ֤��Ϣ�Ƿ���ȷ��"
						+ securityContext);
			}
			if (headers != null && headers.size() > 0) {
				Iterator<String> keys = headers.keySet().iterator();
				while (keys.hasNext()) {
					String key = keys.next();
					Header head = headers.get(key);
					this.addParameters(key, head.getValue());
				}
				// this.addParameters(headers);
			}
		} catch (SecurityException e) {
			throw e;
		}
		catch (Exception e) {
			throw new SecurityException("" + securityContext);
		}
	}

	private void initPermissionInfo(ServiceID id, String method, Class[] types) {
		// SecurityContext securityContext = method_call.getSecurityContext();

		// ServiceID id = (ServiceID)method_call.getArgs()[0];
		// if(securityContext == null)
		// return;
		setServiceid(id.getService());
		// String method = (String)method_call.getArgs()[1];
		// Class[] types = (Class[])method_call.getArgs()[3];
		setServiceid(id.getService());
		setMethodop(SynchronizedMethod.buildMethodUUID(method, types));

	}

	/**
	 * ���ʵķ�����ʶ����������Ȩ�޿���
	 */
	private String methodop;
	/**
	 * ���ʵķ����ʶ����������Ȩ�޿���
	 */
	private String serviceid;

	/**
	 * Ӧ��ִ�л������õ���������Ϣ
	 */
	private UtilMap callparameters = new UtilMap();

	public void addParameters(Object key, Object value) {
		this.callparameters.put(key, value);
	}

	public void addParameters(Map parameters) {
		this.callparameters.putAll(parameters);
	}

	public Object getParameter(Object key) {
		return this.callparameters.get(key);
	}

	public int getIntParameter(String key) {

		return callparameters.getInt(key);
	}

	public long getLongParameter(String key) {
		return callparameters.getLong(key);
	}

	public int getIntParameter(String key, int defaultValue) {
		return callparameters.getInt(key, defaultValue);
	}

	public long getLongParameter(String key, long defaultValue) {
		return callparameters.getLong(key, defaultValue);
	}

	public boolean getBooleanParameter(String key) {
		return callparameters.getBoolean(key);
	}

	public boolean getBooleanParameter(String key, boolean defaultValue) {
		return callparameters.getBoolean(key, defaultValue);
	}

	public String getStringParameter(String key) {
		return callparameters.getString(key);
	}

	public String getStringParameter(String key, String defaultValue) {
		return callparameters.getString(key, defaultValue);
	}

	public Object getObjectParameter(String key) {
		return callparameters.getObject(key);
	}

	public Object getObjectParameter(String key, Object defaultValue) {
		return callparameters.getObject(key);
	}

	public String getServiceid() {
		return serviceid;
	}

	public void setServiceid(String serviceid) {
		this.serviceid = serviceid;
	}

	public String getMethodop() {

		return methodop;
	}

	public void setMethodop(String methodop) {

		this.methodop = methodop;
	}

	public void setCallparameters(UtilMap callparameters) {
		if (callparameters != null && callparameters.size() > 0) {
			if (this.callparameters == null)
				this.callparameters = new UtilMap();
			this.callparameters.putAll(callparameters);
		}
	}

}
