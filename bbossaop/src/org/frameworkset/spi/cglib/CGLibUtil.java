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

package org.frameworkset.spi.cglib;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;

import org.apache.log4j.Logger;
import org.frameworkset.spi.CallContext;
import org.frameworkset.spi.assemble.BaseTXManager;
import org.frameworkset.spi.assemble.Pro;
import org.frameworkset.spi.assemble.ProviderInfoQueue;
import org.frameworkset.spi.assemble.ProviderManagerInfo;
import org.frameworkset.spi.assemble.SecurityProviderInfo;
import org.frameworkset.spi.assemble.SynchronizedMethod;
import org.frameworkset.spi.async.AsyncCall;
import org.frameworkset.spi.async.CallBack;
import org.frameworkset.spi.async.CallBackService;
import org.frameworkset.spi.async.CallBackServiceImpl;
import org.frameworkset.spi.async.CallService;
import org.frameworkset.spi.remote.RPCHelper;
import org.frameworkset.spi.remote.ServiceID;

import com.frameworkset.proxy.Interceptor;

/**
 * <p>Title: CGLibUtil.java</p> 
 * <p>Description: </p>
 * <p>bboss workgroup</p>
 * <p>Copyright (c) 2007</p>
 * @Date 2010-6-19 ����06:00:28
 * @author biaoping.yin
 * @version 1.0
 */
public class CGLibUtil {
	private static Logger log = Logger.getLogger(CGLibUtil.class);
	private static AsyncCall asyncCall;
	private static Object lock = new Object();
	
	private static void init()
	{
		if(asyncCall != null )
			return;
		synchronized(lock)
		{
			if(asyncCall == null )
			{
				asyncCall = new AsyncCall();
				asyncCall.start();
			}
		}
		
	}
	public static Object invoke(final Object delegate, final Method method, final Object[] args,
			final MethodProxy proxy,final CallContext callcontext,final ServiceID serviceID,final BaseTXManager providerManagerInfo) throws Throwable
	{
		final SynchronizedMethod synmethod = providerManagerInfo.isAsyncMethod(method) ;
		if(synmethod == null )
		{
			return invoke_(delegate, method, args,
					proxy,callcontext,serviceID,providerManagerInfo) ;
		}
		else
		{
//			if(synmethod.getAsyncResultMode() == Result.YES 
//					&& synmethod.getAsyncCallback() == null 
//					&& synmethod.getAsyncTimeout() <= 0 )
//			{
//				return invoke_(delegate, method, args,
//						proxy,callcontext,serviceID,providerManagerInfo) ; 
//			}
			init();

			
			return asyncCall.runCallService(new CallService(){
				public SynchronizedMethod getAsyncMethod() {
					return synmethod;
				}

				public CallBackService getCallBackService() {
					if(callcontext != null && callcontext.getApplicationContext() != null && synmethod.getAsyncCallback() != null)
					{
						CallBack callBack = (CallBack)callcontext.getApplicationContext().getBeanObject(synmethod.getAsyncCallback());
						return new CallBackServiceImpl(callBack);
					}
					return null;
				}
				public Object call() throws Exception {
					
					try {
						return invoke_(delegate, method, args,
								proxy,callcontext,serviceID,providerManagerInfo);
					} catch (Exception e) {
						throw e;
					}
					 catch (Throwable e) {
						throw new Exception(e);
					}
				}
				
			});
		}
			
		
	}
	
	public static Object invoke(final Object delegate, final Method method, final Object[] args,
			final MethodProxy proxy,final Pro providerManagerInfo) throws Throwable
	{
		final SynchronizedMethod synmethod = providerManagerInfo.isAsyncMethod(method) ;
		if(synmethod == null )
		{
			return invoke_(delegate, method, args,
					proxy,providerManagerInfo) ;
		}
		else
		{
//			if(synmethod.getAsyncResultMode() == Result.YES 
//					&& synmethod.getAsyncCallback() == null 
//					&& synmethod.getAsyncTimeout() <= 0 )
//			{
//				return invoke_(delegate, method, args,
//						proxy,callcontext,serviceID,providerManagerInfo) ; 
//			}
			init();

			
			return asyncCall.runCallService(new CallService(){
				public SynchronizedMethod getAsyncMethod() {
					return synmethod;
				}

				public CallBackService getCallBackService() {
					if(providerManagerInfo.getApplicationContext() != null && synmethod.getAsyncCallback() != null)
					{
						CallBack callBack = (CallBack)providerManagerInfo.getApplicationContext().getBeanObject(synmethod.getAsyncCallback());
						return new CallBackServiceImpl(callBack);
					}
					return null;
				}
				public Object call() throws Exception {
					
					try {
						return invoke_(delegate, method, args,
								proxy,providerManagerInfo);
					} catch (Exception e) {
						throw e;
					}
					 catch (Throwable e) {
						throw new Exception(e);
					}
				}
				
			});
		}
			
		
	}
	
	private static Object invoke_(Object delegate, Method method, Object[] args,
			MethodProxy proxy,CallContext callcontext,ServiceID serviceID,BaseTXManager providerManagerInfo) throws Throwable
    {
        if (!serviceID.isRemote())
        {
            Interceptor interceptor = providerManagerInfo.getTransactionInterceptor();
            try
            {

                Object obj = null;// ֻ����delegate�з������ص�ֵ
                // �������ͬ��������������ͬ�����������ӿڷ���
                // log.debug("method.getName():" + method.getName());
                //log.debug("providerManagerInfo.getSynchronizedMethod("
                // +
                // method.getName() + "):" +
                // providerManagerInfo.getSynchronizedMethod
                // (method.getName()));
                if (interceptor != null)
                    interceptor.before(method, args);
                if(proxy == null)
                	obj = method.invoke(delegate, args);
                else
                	obj = method.invoke(delegate, args);
                if (interceptor != null)
                    interceptor.after(method, args);
                return obj;

            }
            catch (InvocationTargetException e)
            {
                //log.error(e);
                if (interceptor != null)
                {
                    try
                    {
                        interceptor.afterThrowing(method, args, e.getTargetException());
                    }
                    catch (Exception ei)
                    {
                        ei.printStackTrace();
                    }
                }

                // �������׳����쳣ֱ���׳������쳣
                throw e.getTargetException();
            }
            catch (Throwable t)
            {
                log.error(t);
                try
                {
                    // t.printStackTrace();
                    if (interceptor != null)
                        interceptor.afterThrowing(method, args, t);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                throw t;
            }
            finally
            {
                try
                {
                    if (interceptor != null)
                        interceptor.afterFinally(method, args);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                interceptor = null;
            }
        }
        else
        {

            return RPCHelper.getRPCHelper().rpcService(serviceID, method, args,callcontext);

        }
    }
	
	private static Object invoke_(Object delegate, Method method, Object[] args,
			MethodProxy proxy,BaseTXManager providerManagerInfo) throws Throwable
    {
//        if (!serviceID.isRemote())
        {
            Interceptor interceptor = providerManagerInfo.getTransactionInterceptor();
            try
            {

                Object obj = null;// ֻ����delegate�з������ص�ֵ
                // �������ͬ��������������ͬ�����������ӿڷ���
                // log.debug("method.getName():" + method.getName());
                //log.debug("providerManagerInfo.getSynchronizedMethod("
                // +
                // method.getName() + "):" +
                // providerManagerInfo.getSynchronizedMethod
                // (method.getName()));
                if (interceptor != null)
                    interceptor.before(method, args);
                if(proxy == null)
                	obj = method.invoke(delegate, args);
                else
                	obj = method.invoke(delegate, args);
                if (interceptor != null)
                    interceptor.after(method, args);
                return obj;

            }
            catch (InvocationTargetException e)
            {
                //log.error(e);
                if (interceptor != null)
                {
                    try
                    {
                        interceptor.afterThrowing(method, args, e.getTargetException());
                    }
                    catch (Exception ei)
                    {
                        ei.printStackTrace();
                    }
                }

                // �������׳����쳣ֱ���׳������쳣
                throw e.getTargetException();
            }
            catch (Throwable t)
            {
                log.error(t);
                try
                {
                    // t.printStackTrace();
                    if (interceptor != null)
                        interceptor.afterThrowing(method, args, t);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                throw t;
            }
            finally
            {
                try
                {
                    if (interceptor != null)
                        interceptor.afterFinally(method, args);

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                interceptor = null;
            }
        }
      
    }
	public static Object invokeSynTX(final Object delegate, final Method method, final Object[] args,
			final MethodProxy proxy,final CallContext callcontext,final ServiceID serviceID,final ProviderManagerInfo providerManagerInfo) throws Throwable {
		final SynchronizedMethod synmethod = providerManagerInfo.isAsyncMethod(method) ;
		if(synmethod == null )
		{
			
			return invokeSynTX_(delegate, method, args,
					proxy,callcontext,serviceID,providerManagerInfo) ;
		}
		else
		{
//			if(synmethod.getAsyncResultMode() == Result.YES 
//					&& synmethod.getAsyncCallback() == null 
//					&& synmethod.getAsyncTimeout() <= 0 )
//			{
//				return invokeSynTX_(delegate, method, args,
//						proxy,callcontext,serviceID,providerManagerInfo) ;
//			}
			init();			
			return asyncCall.runCallService(new CallService(){

				public SynchronizedMethod getAsyncMethod() {
					return synmethod;
				}

				public CallBackService getCallBackService() {
					if(callcontext != null && callcontext.getApplicationContext() != null)
					{
						CallBack callBack = (CallBack)callcontext.getApplicationContext().getBeanObject(synmethod.getAsyncCallback());
						return new CallBackServiceImpl(callBack);
					}
					return null;
				}
				public Object call() throws Exception {					
					try {
						return invokeSynTX_(delegate, method, args,
								proxy,callcontext,serviceID,providerManagerInfo) ;
					} catch (Exception e) {
						throw e;
					}
					 catch (Throwable e) {
						throw new Exception(e);
					}
				}				
			});
		}
			
		
	}
	private static Object invokeSynTX_(Object delegate, Method method, Object[] args,
			MethodProxy proxy,CallContext callcontext,ServiceID serviceID,ProviderManagerInfo providerManagerInfo) throws Throwable {
		if (!serviceID.isRemote())
        {
            Interceptor interceptor = providerManagerInfo.getTransactionInterceptor();
            try
            {

                Object obj = null;// ֻ����delegate�з������ص�ֵ
                // �������ͬ��������������ͬ�����������ӿڷ���
                // log.debug("method.getName():" + method.getName());
                //log.debug("providerManagerInfo.getSynchronizedMethod("
                // +
                // method.getName() + "):" +
                // providerManagerInfo.getSynchronizedMethod
                // (method.getName()));
                if (!providerManagerInfo.isSynchronizedEnabled()
                        || !providerManagerInfo.isSynchronizedMethod(method))
                {// ���û�������Է�������Ӧ�ķ���������Ϊ��ʶ��ͬ������
                    // ��
                    // �������ִ������provider��ͬ������

                    if (interceptor != null)
                        interceptor.before(method, args);
                    if(proxy == null)
                    	obj = method.invoke(delegate, args);
                    else
                    	obj = method.invoke(delegate, args);
                    if (interceptor != null)
                        interceptor.after(method, args);
                    return obj;

                }
                else
                {
                    // ��ȡ�����ṩ�߶��У��ö����Ѿ�����ָ�������ȼ�˳���������
                    ProviderInfoQueue providerInfoQueue = providerManagerInfo.getProviderInfoQueue();

                    //ͬ���������еĽӿڷ��������ͬ���������������Է���������Ҫ������������Ҫôȫ���ɹ���Ҫôȫ��ʧ��
                    // �����ڴ���Ĺ�����,һЩ�����ṩ�߷�����ִ��ʱ�׳��쳣��������ִ�к�����ͬ������

                    SynchronizedMethod synm = providerManagerInfo.isTransactionMethod(method);
                    boolean isTXMethod = synm != null;
                    Throwable throwable = null;
                    if (interceptor != null)
                        interceptor.before(method, args);
                    for (int i = 0; i < providerInfoQueue.size(); i++)
                    {
                        SecurityProviderInfo securityProviderInfo = providerInfoQueue
                                .getSecurityProviderInfo(i);
                        if (!securityProviderInfo.isUsed() && !securityProviderInfo.isIsdefault())
                        {
                            continue;
                        }
                        Object provider = securityProviderInfo.getProvider(callcontext);
                        // Object provider =
                        // !providerManagerInfo.isSinglable() ?
                        // securityProviderInfo
                        // .getProvider(parent) :
                        // securityProviderInfo.getSingleProvider
                        // (parent);
                        boolean isdelegate = provider.getClass() == delegate.getClass();
                        // ȱʡ��provider�ͱ���ʶΪʹ�õ�provider����Ҫ����ͬ������
                        if (isdelegate || securityProviderInfo.isUsed() || securityProviderInfo.isIsdefault())
                        {
                            if (isdelegate && !securityProviderInfo.isUsed())
                            {
                                log.warn("���õ�Provider[" + delegate.getClass() + "]��[" + securityProviderInfo
                                        + "]������Ϊδʹ�õķ����ṩ�ߡ�");
                            }

                            if (!isTXMethod) // �������񷽷�ʱ��
                                             // ִ�����е�provider��ͬһ��������
                            // ����ʧ�ܵķ������ã�
                            {
                                /**
                                 * ȱʡ���ṩ�ߣ�Ҳ����Ĭ�ϵ��ṩ�ߣ�����Ҫ����������ʱ��
                                 * ֻ���������ṩ�ߵķ���ֵ
                                 */
                                if (isdelegate)
                                {
                                    try
                                    {
                                    	if(proxy == null)
                                    		obj = method.invoke(provider, args);
                                    	else
                                    		obj = method.invoke(provider, args);
                                    }
                                    catch (InvocationTargetException e)
                                    {
                                        throwable = e.getTargetException();
                                        log.error("���÷����ṩ��[" + securityProviderInfo + "]�з���[" + method + "]�쳣��"
                                                + e.getMessage(), e);
                                    }
                                    catch (Exception e)
                                    {
                                        throwable = e;
                                        log.error("���÷����ṩ��[" + securityProviderInfo + "]�з���[" + method + "]�쳣��"
                                                + e.getMessage(), e);
                                    }
                                }
                                else
                                {
                                    try
                                    {
                                    	
                                    	method = provider.getClass().getMethod(method.getName(), method.getParameterTypes());
                                        method.invoke(provider, args);
                                    }
                                    catch (Exception e)// �׳��쳣ʱ����¼���쳣��
                                    // ����ִ�к�����provider�ķ���
                                    {
                                        // �������񷽷�����ʱ���������������ṩ�߷���ִ��ʱ���쳣
                                        log.error("ͬ�����÷����ṩ��[" + securityProviderInfo + "]�з���[" + method
                                                + "]�쳣��" + e.getMessage(), e);
                                    }
                                }
                                // �׳�ȱʡ�����ṩ�߷����쳣��ͬʱ��ֹ����providerͬ��ִ���߼�

                            }
                            else
                            // �����Է���ִ���߼����������������쳣��������Ҫ������������쳣
                            {
                                if (isdelegate)
                                {
                                    try
                                    {
                                    	if(proxy == null)
                                        	obj = method.invoke(provider, args);
                                    	else
                                    		obj = method.invoke(provider, args);
                                    }
                                    catch (InvocationTargetException e)
                                    {
                                        if (synm.isRollbackException(e.getTargetException())) // �������쳣
                                        // ��
                                        // �����׳��쳣
                                        // ��
                                        // �ж����������ṩ�߷���ִ��
                                        {
                                            throw e.getTargetException();
                                        }
                                        else
                                        {
                                            throwable = e.getTargetException();// �׳��쳣ʱ��¼���쳣
                                            // ��
                                            // ����ִ�к�����provider��ͬ������
                                        }
                                    }

                                    catch (Exception e)
                                    {
                                        if (synm.isRollbackException(e)) // �������쳣
                                        // ��
                                        // �����׳��쳣
                                        // ��
                                        // �ж����������ṩ�߷���ִ��
                                        {
                                            throw e;
                                        }
                                        else
                                        {
                                            throwable = e;// �׳��쳣ʱ��¼���쳣��
                                            // ����ִ�к�����provider��ͬ������
                                        }
                                    }
                                }
                                else
                                {
                                    try
                                    {
                                    	method = provider.getClass().getMethod(method.getName(), method.getParameterTypes());
                                        method.invoke(provider, args);
                                    }
                                    catch (InvocationTargetException e)
                                    {
                                        if (synm.isRollbackException(e.getTargetException())) // �������쳣
                                        // ��
                                        // �����׳��쳣
                                        // ��
                                        // �ж����������ṩ�߷���ִ��
                                        {
                                            throw e;
                                        }
                                        else
                                        {
                                            log.error("ͬ�����÷����ṩ��[" + securityProviderInfo + "]�з���[" + method
                                                    + "]�쳣��" + e.getMessage(), e);
                                        }
                                    }
                                    catch (Exception e)
                                    {
                                        if (synm.isRollbackException(e)) // �������쳣
                                        // ��
                                        // �����׳��쳣
                                        // ��
                                        // �ж����������ṩ�߷���ִ��
                                        {
                                            throw e;
                                        }
                                        else
                                        {
                                            log.error("ͬ�����÷����ṩ��[" + securityProviderInfo + "]�з���[" + method
                                                    + "]�쳣��" + e.getMessage(), e);
                                        }
                                    }
                                }
                            }
                        }
                        // log.debug(
                        // "providerInfoQueue.getSecurityProviderInfo("+
                        // i
                        // +").getProvider()" +
                        // providerInfoQueue.getSecurityProviderInfo(i)
                        // .getProvider());
                    }
                    if (throwable != null)// ����ڷ�����ִ�й����з����쳣���׳����쳣
                        throw throwable;
                    if (interceptor != null)
                        interceptor.after(method, args);
                    return obj;
                }
            }
            catch (InvocationTargetException e)
            {
                if (interceptor != null)
                {
                    try
                    {
                        interceptor.afterThrowing(method, args, e.getTargetException());
                    }
                    catch (Exception ei)
                    {
                        ei.printStackTrace();
                    }
                }
                throw e.getTargetException();
            }
            catch (Throwable t)
            {
                try
                {
                    t.printStackTrace();
                    if (interceptor != null)
                        interceptor.afterThrowing(method, args, t);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                throw t;
            }
            finally
            {
                try
                {
                    if (interceptor != null)
                        interceptor.afterFinally(method, args);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                interceptor = null;
            }
        }
        else
        {
            return RPCHelper.getRPCHelper().rpcService(serviceID, method, args,callcontext);
        }
	}
	public static Object invokeSyn(final Object delegate, final Method method, final Object[] args,
			final MethodProxy proxy,final CallContext callcontext,final ServiceID serviceID,final ProviderManagerInfo providerManagerInfo) throws Throwable{		
		final SynchronizedMethod synmethod = providerManagerInfo.isAsyncMethod(method) ;
		if(synmethod == null )
		{
			
			return invokeSyn_(delegate, method, args,
					proxy,callcontext,serviceID,providerManagerInfo);
		}
		else
		{
//			if(synmethod.getAsyncResultMode() == Result.YES 
//					&& synmethod.getAsyncCallback() == null 
//					&& synmethod.getAsyncTimeout() <= 0 )
//			{
//				return invokeSyn_(delegate, method, args,
//						proxy,callcontext,serviceID,providerManagerInfo);
//			}
			init();			
			return asyncCall.runCallService(new CallService(){

				public SynchronizedMethod getAsyncMethod() {
					return synmethod;
				}

				public CallBackService getCallBackService() {
					if(callcontext != null && callcontext.getApplicationContext() != null)
					{
						CallBack callBack = (CallBack)callcontext.getApplicationContext().getBeanObject(synmethod.getAsyncCallback());
						return new CallBackServiceImpl(callBack);
					}
					return null;
				}
				public Object call() throws Exception {					
					try {
						return invokeSyn_(delegate, method, args,
								proxy,callcontext,serviceID,providerManagerInfo);
					} catch (Exception e) {
						throw e;
					}
					 catch (Throwable e) {
						throw new Exception(e);
					}
				}				
			});
		}
	}
	private static Object invokeSyn_(Object delegate, Method method, Object[] args,
			MethodProxy proxy,CallContext callcontext,ServiceID serviceID,ProviderManagerInfo providerManagerInfo) throws Throwable
	
    {
        if (!serviceID.isRemote())
        {
            Interceptor interceptor = providerManagerInfo.getTransactionInterceptor();
            try
            {

                Object obj = null;// ֻ����delegate�з������ص�ֵ
                // �������ͬ��������������ͬ�����������ӿڷ���
                // log.debug("method.getName():" + method.getName());
                //log.debug("providerManagerInfo.getSynchronizedMethod("
                // +
                // method.getName() + "):" +
                // providerManagerInfo.getSynchronizedMethod
                // (method.getName()));
                if (!providerManagerInfo.isSynchronizedEnabled()
                        || !providerManagerInfo.isSynchronizedMethod(method))
                {// ���û�������Է�������Ӧ�ķ���������Ϊ��ʶ��ͬ������
                    // ��
                    // �������ִ������provider��ͬ������

                    if (interceptor != null)
                        interceptor.before(method, args);
                    if(proxy == null)
                    	obj = method.invoke(delegate, args);
                    else
                    	obj = method.invoke(delegate, args);
                    if (interceptor != null)
                        interceptor.after(method, args);
                    return obj;

                }
                else
                {
                    // ��ȡ�����ṩ�߶��У��ö����Ѿ�����ָ�������ȼ�˳���������
                    ProviderInfoQueue providerInfoQueue = providerManagerInfo.getProviderInfoQueue();

                    //ͬ���������еĽӿڷ��������ͬ���������������Է���������Ҫ������������Ҫôȫ���ɹ���Ҫôȫ��ʧ��
                    // �����ڴ���Ĺ�����,һЩ�����ṩ�߷�����ִ��ʱ�׳��쳣��������ִ�к�����ͬ������

                    Throwable throwable = null;
                    if (interceptor != null)
                        interceptor.before(method, args);
                    for (int i = 0; i < providerInfoQueue.size(); i++)
                    {
                        SecurityProviderInfo securityProviderInfo = providerInfoQueue
                                .getSecurityProviderInfo(i);
                        if (!securityProviderInfo.isUsed() && !securityProviderInfo.isIsdefault())
                        {
                            continue;
                        }
                        // Object provider =
                        // !providerManagerInfo.isSinglable() ?
                        // securityProviderInfo
                        // .getProvider(parent) :
                        // securityProviderInfo.getSingleProvider
                        // (parent);
                        Object provider = securityProviderInfo.getProvider(callcontext);
                        boolean isdelegate = provider.getClass() == delegate.getClass();
                        // ȱʡ��provider�ͱ���ʶΪʹ�õ�provider����Ҫ����ͬ������
                        if (isdelegate || securityProviderInfo.isUsed() || securityProviderInfo.isIsdefault())
                        {
                            if (isdelegate && !securityProviderInfo.isUsed())
                            {
                                log.warn("���õ�Provider[" + delegate.getClass() + "]��[" + securityProviderInfo
                                        + "]������Ϊδʹ�õķ����ṩ�ߡ�");
                            }

                            /**
                             * ȱʡ���ṩ�ߣ�Ҳ����Ĭ�ϵ��ṩ�ߣ�����Ҫ����������ʱ��ֻ���������ṩ�ߵķ���ֵ
                             */
                            if (isdelegate)
                            {
                                try
                                {
                                	if(proxy == null)
                                    	obj = method.invoke(provider, args);
                                	else
                                		obj = method.invoke(provider, args);
                                }
                                catch (InvocationTargetException e)
                                {
                                    throwable = e.getTargetException();
                                    log.error("���÷����ṩ��[" + securityProviderInfo + "]�з���[" + method + "]�쳣��"
                                            + e.getMessage(), e);
                                }
                                catch (Exception e)
                                {
                                    throwable = e;
                                    log.error("���÷����ṩ��[" + securityProviderInfo + "]�з���[" + method + "]�쳣��"
                                            + e.getMessage(), e);
                                }

                            }
                            else
                            {
                                try
                                {
                                	method = provider.getClass().getMethod(method.getName(), method.getParameterTypes());
                                    method.invoke(provider, args);
                                }
                                catch (Exception e)
                                {
                                    // �������񷽷�����ʱ���������������ṩ�߷���ִ��ʱ���쳣
                                    log.error("ͬ�����÷����ṩ��[" + securityProviderInfo + "]�з���[" + method + "]�쳣��"
                                            + e.getMessage(), e);
                                }
                            }

                        }
                        // log.debug(
                        // "providerInfoQueue.getSecurityProviderInfo("+
                        // i
                        // +").getProvider()" +
                        // providerInfoQueue.getSecurityProviderInfo(i)
                        // .getProvider());
                    }
                    // �׳�ȱʡ�����ṩ�߷����쳣
                    if (throwable != null)
                        throw throwable;
                    if (interceptor != null)
                        interceptor.after(method, args);
                    return obj;
                }
            }
            catch (InvocationTargetException e)
            {
                if (interceptor != null)
                {
                    try
                    {
                        interceptor.afterThrowing(method, args, e.getTargetException());
                    }
                    catch (Exception ei)
                    {
                        ei.printStackTrace();
                    }
                }
                throw e.getTargetException();
            }
            catch (Throwable t)
            {
                try
                {
                    t.printStackTrace();
                    if (interceptor != null)
                        interceptor.afterThrowing(method, args, t);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                throw t;
            }
            finally
            {
                try
                {
                    if (interceptor != null)
                        interceptor.afterFinally(method, args);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                interceptor = null;
            }

        }
        else
        {
            return RPCHelper.getRPCHelper().rpcService(serviceID, method, args,callcontext);
        }
    }
	
	/**
	 * ��ȡ���ʵ��
	 * @param <T>
	 * @param rettype
	 * @param beanType
	 * @param proxy
	 * @return
	 */
	public static <T> T getBeanInstance(Class<T> rettype,Class beanType,MethodInterceptor proxy)
	{
		 Enhancer enhancer = new Enhancer();
		 
	        enhancer.setSuperclass(beanType);
	        enhancer.setCallbacks(new Callback[] { proxy, NoOp.INSTANCE });
	        enhancer.setCallbackFilter(filter);
	        return (T) enhancer.create();
	}
	
	/**
	 * ��ȡ���ʵ��
	 * @param <T>
	 * @param rettype
	 * @param beanType
	 * @param proxy
	 * @return
	 */
	public static <T> T getBeanInstance(Class<T> rettype,MethodInterceptor proxy)
	{
		 Enhancer enhancer = new Enhancer();
		 
	        enhancer.setSuperclass(rettype);
	        enhancer.setCallbacks(new Callback[] { proxy, NoOp.INSTANCE });
	        enhancer.setCallbackFilter(filter);
	        return (T) enhancer.create();
	}
	
	private static AopProxyFilter filter = new AopProxyFilter();
}
