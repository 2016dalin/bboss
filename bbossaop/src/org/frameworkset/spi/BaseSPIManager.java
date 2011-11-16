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

import java.util.List;

import org.frameworkset.spi.assemble.Pro;
import org.frameworkset.spi.assemble.ProList;
import org.frameworkset.spi.assemble.ProMap;
import org.frameworkset.spi.assemble.ProSet;
import org.frameworkset.spi.assemble.ServiceProviderManager;
import org.frameworkset.spi.remote.RPCAddress;
import org.frameworkset.spi.remote.ServiceID;
import org.frameworkset.spi.remote.Target;


/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description: ��������࣬�ṩaop����ײ�İ�װ��ʵ�ּ�aop���ع��ܣ��ṩ��־�Ĺ�������Ĺ�����
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company:
 * </p>
 * 
 * @author biaoping.yin
 * @version 1.0
 */
public abstract class BaseSPIManager
{

    public static ApplicationContext defaultContext; 
    static
    {
        // try {
        // // use reflection and catch the Exception to allow PoolMan to work
        // with 1.2 VM's
        // Class r = Runtime.getRuntime().getClass();
        // java.lang.reflect.Method m =
        // r.getDeclaredMethod("addShutdownHook", new Class[]{Thread.class});
        // m.invoke(Runtime.getRuntime(), new Object[]{new Thread(new
        // BeanDestroyHook())});
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
//        addShutdownHook(new BeanDestroyHook());
        try
        {
            defaultContext = ApplicationContext.getApplicationContext(ServiceProviderManager.defaultConfigFile);
        }
        catch(Exception e)
        {
//            defaultContext = ApplicationContext.getApplicationContext(ServiceProviderManager.defaultConfigFile);
            e.printStackTrace();
        }
        finally
        {
            
        }

    }
    
    public static ApplicationContext getDefaultApplicationContext()
    {
        return defaultContext;
    }
    
    

    /**
     * ���ϵͳ��ֹͣʱ�Ļص�����
     * 
     * @param destroyVMHook
     */
    public static void addShutdownHook(Runnable destroyVMHook)
    {
//        try
//        {
//            // use reflection and catch the Exception to allow PoolMan to work
//            // with 1.2 VM's
//            Class r = Runtime.getRuntime().getClass();
//            java.lang.reflect.Method m = r.getDeclaredMethod("addShutdownHook", new Class[] { Thread.class });
//            m.invoke(Runtime.getRuntime(), new Object[] { new Thread(destroyVMHook) });
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
        ApplicationContext.addShutdownHook(destroyVMHook);
    }

//    public static void destroySingleBeans()
//    {
//        if (singleDestorys != null && singleDestorys.size() > 0)
//        {
//            //			
//            Iterator<DisposableBean> ite = singleDestorys.iterator();
//            while (ite.hasNext())
//            {
//                DisposableBean et = ite.next();
//                try
//                {
//                    et.destroy();
//                }
//                catch (Exception e)
//                {
//                    log.error(e);
//                }
//            }
//        }
//        
//        if(destroyServiceMethods != null && destroyServiceMethods.size() > 0)
//        {
//        	Iterator<Map.Entry<String, Object>> entrys = destroyServiceMethods.entrySet().iterator();
//        	for(;entrys.hasNext();)
//        	{
//        		Map.Entry<String, Object> entry = entrys.next();
//        		Object value = entry.getValue();
//        		String method = entry.getKey();
//        		try
//				{
//					Method m = value.getClass().getDeclaredMethod(method, new Class[0]);
//					m.invoke(value, new Object[0]);
//				}
//				catch (SecurityException e)
//				{
//					log.error(e);
//				}
//				catch (NoSuchMethodException e)
//				{
//					log.error(e);
//				}
//				catch (Exception e)
//				{
//					log.error(e);
//				}
//        	}
//        }
//    }

//    public static void registDisableBean(DisposableBean disposableBean)
//    {
//        singleDestorys.add(disposableBean);
//    }
//    
//    public static void registDestroyMethod(String destroyMethod,Object instance)
//    {
//    	destroyServiceMethods.put(destroyMethod, instance);
//    }

//    private static Logger log = Logger.getLogger(BaseSPIManager.class);

//    /**
//     * ȱʡ�ӿ�key
//     */
//    public static final String DEFAULT_CACHE_KEY = "DEFAULT_CACHE_KEY";
//
//    /**
//     * ͬ������key
//     */
//
//    public static final String SYNCHRO_CACHE_KEY = "SYNCHRO_CACHE_KEY";

//    /**
//     * ����������ͬ�����Ƶ�provider����ӿ�ʵ�� �ô���ӿڿ�����������ƵĹ���Ҳ����û����������ƣ����ݾ�������������� createInf
//     * 
//     * @return Object
//     */
//    public static Object createInf(final CallContext callcontext, final BaseTXManager providerManagerInfo,
//            final Object delegate, final ServiceID serviceID)
//    {
//        return ProxyFactory.createProxy(new InvocationHandler(delegate)
//        {
//            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
//            {
//                if (!serviceID.isRemote())
//                {
//                    Interceptor interceptor = providerManagerInfo.getTransactionInterceptor();
//                    try
//                    {
//
//                        Object obj = null;// ֻ����delegate�з������ص�ֵ
//                        // �������ͬ��������������ͬ�����������ӿڷ���
//                        // log.debug("method.getName():" + method.getName());
//                        //log.debug("providerManagerInfo.getSynchronizedMethod("
//                        // +
//                        // method.getName() + "):" +
//                        // providerManagerInfo.getSynchronizedMethod
//                        // (method.getName()));
//                        if (interceptor != null)
//                            interceptor.before(method, args);
//                        obj = method.invoke(delegate, args);
//                        if (interceptor != null)
//                            interceptor.after(method, args);
//                        return obj;
//
//                    }
//                    catch (InvocationTargetException e)
//                    {
//                        log.error(e);
//                        if (interceptor != null)
//                        {
//                            try
//                            {
//                                interceptor.afterThrowing(method, args, e.getTargetException());
//                            }
//                            catch (Exception ei)
//                            {
//                                ei.printStackTrace();
//                            }
//                        }
//
//                        // �������׳����쳣ֱ���׳������쳣
//                        throw e.getTargetException();
//                    }
//                    catch (Throwable t)
//                    {
//                        log.error(t);
//                        try
//                        {
//                            // t.printStackTrace();
//                            if (interceptor != null)
//                                interceptor.afterThrowing(method, args, t);
//                        }
//                        catch (Exception e)
//                        {
//                            e.printStackTrace();
//                        }
//                        throw t;
//                    }
//                    finally
//                    {
//                        try
//                        {
//                            if (interceptor != null)
//                                interceptor.afterFinally(method, args);
//
//                        }
//                        catch (Exception e)
//                        {
//                            e.printStackTrace();
//                        }
//                        interceptor = null;
//                    }
//                }
//                else
//                {
//
//                    return RPCHelper.getRPCHelper().rpcService(serviceID, method, args,callcontext);
//
//                }
//            }
//        });
//
//    }

//    /**
//     * ����û��ͬ������������Ƶ�provider����ӿ�ʵ�� �÷�����ʵ���߼�Ŀǰ��createInf����һ��
//     * 
//     * @return Object
//     */
//    protected static Object createTXInf(final CallContext callcontext, final BaseTXManager providerManagerInfo,
//            final Object delegate, ServiceID serviceID)
//    {
//        return createInf(callcontext, providerManagerInfo, delegate, serviceID);
//
//    }

//    /**
//     * ����������ͬ�����ƹ��ܵ����߱���������ܵ�provider����ӿ�ʵ�� createInf
//     * 
//     * @return Object
//     */
//    protected static Object createSynInf(final CallContext callcontext, final ProviderManagerInfo providerManagerInfo,
//            final Object delegate, final ServiceID serviceID)
//    {
//        return ProxyFactory.createProxy(new InvocationHandler(delegate)
//        {
//            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
//            {
//                if (!serviceID.isRemote())
//                {
//                    Interceptor interceptor = providerManagerInfo.getTransactionInterceptor();
//                    try
//                    {
//
//                        Object obj = null;// ֻ����delegate�з������ص�ֵ
//                        // �������ͬ��������������ͬ�����������ӿڷ���
//                        // log.debug("method.getName():" + method.getName());
//                        //log.debug("providerManagerInfo.getSynchronizedMethod("
//                        // +
//                        // method.getName() + "):" +
//                        // providerManagerInfo.getSynchronizedMethod
//                        // (method.getName()));
//                        if (!providerManagerInfo.isSynchronizedEnabled()
//                                || !providerManagerInfo.isSynchronizedMethod(method))
//                        {// ���û�������Է�������Ӧ�ķ���������Ϊ��ʶ��ͬ������
//                            // ��
//                            // �������ִ������provider��ͬ������
//
//                            if (interceptor != null)
//                                interceptor.before(method, args);
//                            obj = method.invoke(delegate, args);
//                            if (interceptor != null)
//                                interceptor.after(method, args);
//                            return obj;
//
//                        }
//                        else
//                        {
//                            // ��ȡ�����ṩ�߶��У��ö����Ѿ�����ָ�������ȼ�˳���������
//                            ProviderInfoQueue providerInfoQueue = providerManagerInfo.getProviderInfoQueue();
//
//                            //ͬ���������еĽӿڷ��������ͬ���������������Է���������Ҫ������������Ҫôȫ���ɹ���Ҫôȫ��ʧ��
//                            // �����ڴ���Ĺ�����,һЩ�����ṩ�߷�����ִ��ʱ�׳��쳣��������ִ�к�����ͬ������
//
//                            Throwable throwable = null;
//                            if (interceptor != null)
//                                interceptor.before(method, args);
//                            for (int i = 0; i < providerInfoQueue.size(); i++)
//                            {
//                                SecurityProviderInfo securityProviderInfo = providerInfoQueue
//                                        .getSecurityProviderInfo(i);
//                                if (!securityProviderInfo.isUsed() && !securityProviderInfo.isIsdefault())
//                                {
//                                    continue;
//                                }
//                                // Object provider =
//                                // !providerManagerInfo.isSinglable() ?
//                                // securityProviderInfo
//                                // .getProvider(parent) :
//                                // securityProviderInfo.getSingleProvider
//                                // (parent);
//                                Object provider = securityProviderInfo.getProvider(callcontext);
//                                boolean isdelegate = provider.getClass() == delegate.getClass();
//                                // ȱʡ��provider�ͱ���ʶΪʹ�õ�provider����Ҫ����ͬ������
//                                if (isdelegate || securityProviderInfo.isUsed() || securityProviderInfo.isIsdefault())
//                                {
//                                    if (isdelegate && !securityProviderInfo.isUsed())
//                                    {
//                                        log.warn("���õ�Provider[" + delegate.getClass() + "]��[" + securityProviderInfo
//                                                + "]������Ϊδʹ�õķ����ṩ�ߡ�");
//                                    }
//
//                                    /**
//                                     * ȱʡ���ṩ�ߣ�Ҳ����Ĭ�ϵ��ṩ�ߣ�����Ҫ����������ʱ��ֻ���������ṩ�ߵķ���ֵ
//                                     */
//                                    if (isdelegate)
//                                    {
//                                        try
//                                        {
//                                            obj = method.invoke(provider, args);
//                                        }
//                                        catch (InvocationTargetException e)
//                                        {
//                                            throwable = e.getTargetException();
//                                            log.error("���÷����ṩ��[" + securityProviderInfo + "]�з���[" + method + "]�쳣��"
//                                                    + e.getMessage(), e);
//                                        }
//                                        catch (Exception e)
//                                        {
//                                            throwable = e;
//                                            log.error("���÷����ṩ��[" + securityProviderInfo + "]�з���[" + method + "]�쳣��"
//                                                    + e.getMessage(), e);
//                                        }
//
//                                    }
//                                    else
//                                    {
//                                        try
//                                        {
//                                            method.invoke(provider, args);
//                                        }
//                                        catch (Exception e)
//                                        {
//                                            // �������񷽷�����ʱ���������������ṩ�߷���ִ��ʱ���쳣
//                                            log.error("ͬ�����÷����ṩ��[" + securityProviderInfo + "]�з���[" + method + "]�쳣��"
//                                                    + e.getMessage(), e);
//                                        }
//                                    }
//
//                                }
//                                // log.debug(
//                                // "providerInfoQueue.getSecurityProviderInfo("+
//                                // i
//                                // +").getProvider()" +
//                                // providerInfoQueue.getSecurityProviderInfo(i)
//                                // .getProvider());
//                            }
//                            // �׳�ȱʡ�����ṩ�߷����쳣
//                            if (throwable != null)
//                                throw throwable;
//                            if (interceptor != null)
//                                interceptor.after(method, args);
//                            return obj;
//                        }
//                    }
//                    catch (InvocationTargetException e)
//                    {
//                        if (interceptor != null)
//                        {
//                            try
//                            {
//                                interceptor.afterThrowing(method, args, e.getTargetException());
//                            }
//                            catch (Exception ei)
//                            {
//                                ei.printStackTrace();
//                            }
//                        }
//                        throw e.getTargetException();
//                    }
//                    catch (Throwable t)
//                    {
//                        try
//                        {
//                            t.printStackTrace();
//                            if (interceptor != null)
//                                interceptor.afterThrowing(method, args, t);
//                        }
//                        catch (Exception e)
//                        {
//                            e.printStackTrace();
//                        }
//                        throw t;
//                    }
//                    finally
//                    {
//                        try
//                        {
//                            if (interceptor != null)
//                                interceptor.afterFinally(method, args);
//                        }
//                        catch (Exception e)
//                        {
//                            e.printStackTrace();
//                        }
//                        interceptor = null;
//                    }
//
//                }
//                else
//                {
//                    return RPCHelper.getRPCHelper().rpcService(serviceID, method, args,callcontext);
//                }
//            }
//        });
//    }

//    /**
//     * ��������ͬ����������Ƶ�provider����ӿ�ʵ�� createInf
//     * 
//     * @return Object
//     */
//    protected static Object createSynTXInf(final CallContext callcontext, final ProviderManagerInfo providerManagerInfo,
//            final Object delegate, final ServiceID serviceID)
//    {
//        return ProxyFactory.createProxy(new InvocationHandler(delegate)
//        {
//            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
//            {
//                if (!serviceID.isRemote())
//                {
//                    Interceptor interceptor = providerManagerInfo.getTransactionInterceptor();
//                    try
//                    {
//
//                        Object obj = null;// ֻ����delegate�з������ص�ֵ
//                        // �������ͬ��������������ͬ�����������ӿڷ���
//                        // log.debug("method.getName():" + method.getName());
//                        //log.debug("providerManagerInfo.getSynchronizedMethod("
//                        // +
//                        // method.getName() + "):" +
//                        // providerManagerInfo.getSynchronizedMethod
//                        // (method.getName()));
//                        if (!providerManagerInfo.isSynchronizedEnabled()
//                                || !providerManagerInfo.isSynchronizedMethod(method))
//                        {// ���û�������Է�������Ӧ�ķ���������Ϊ��ʶ��ͬ������
//                            // ��
//                            // �������ִ������provider��ͬ������
//
//                            if (interceptor != null)
//                                interceptor.before(method, args);
//                            obj = method.invoke(delegate, args);
//                            if (interceptor != null)
//                                interceptor.after(method, args);
//                            return obj;
//
//                        }
//                        else
//                        {
//                            // ��ȡ�����ṩ�߶��У��ö����Ѿ�����ָ�������ȼ�˳���������
//                            ProviderInfoQueue providerInfoQueue = providerManagerInfo.getProviderInfoQueue();
//
//                            //ͬ���������еĽӿڷ��������ͬ���������������Է���������Ҫ������������Ҫôȫ���ɹ���Ҫôȫ��ʧ��
//                            // �����ڴ���Ĺ�����,һЩ�����ṩ�߷�����ִ��ʱ�׳��쳣��������ִ�к�����ͬ������
//
//                            SynchronizedMethod synm = providerManagerInfo.isTransactionMethod(method);
//                            boolean isTXMethod = synm != null;
//                            Throwable throwable = null;
//                            if (interceptor != null)
//                                interceptor.before(method, args);
//                            for (int i = 0; i < providerInfoQueue.size(); i++)
//                            {
//                                SecurityProviderInfo securityProviderInfo = providerInfoQueue
//                                        .getSecurityProviderInfo(i);
//                                if (!securityProviderInfo.isUsed() && !securityProviderInfo.isIsdefault())
//                                {
//                                    continue;
//                                }
//                                Object provider = securityProviderInfo.getProvider(callcontext);
//                                // Object provider =
//                                // !providerManagerInfo.isSinglable() ?
//                                // securityProviderInfo
//                                // .getProvider(parent) :
//                                // securityProviderInfo.getSingleProvider
//                                // (parent);
//                                boolean isdelegate = provider.getClass() == delegate.getClass();
//                                // ȱʡ��provider�ͱ���ʶΪʹ�õ�provider����Ҫ����ͬ������
//                                if (isdelegate || securityProviderInfo.isUsed() || securityProviderInfo.isIsdefault())
//                                {
//                                    if (isdelegate && !securityProviderInfo.isUsed())
//                                    {
//                                        log.warn("���õ�Provider[" + delegate.getClass() + "]��[" + securityProviderInfo
//                                                + "]������Ϊδʹ�õķ����ṩ�ߡ�");
//                                    }
//
//                                    if (!isTXMethod) // �������񷽷�ʱ��
//                                                     // ִ�����е�provider��ͬһ��������
//                                    // ����ʧ�ܵķ������ã�
//                                    {
//                                        /**
//                                         * ȱʡ���ṩ�ߣ�Ҳ����Ĭ�ϵ��ṩ�ߣ�����Ҫ����������ʱ��
//                                         * ֻ���������ṩ�ߵķ���ֵ
//                                         */
//                                        if (isdelegate)
//                                        {
//                                            try
//                                            {
//                                                obj = method.invoke(provider, args);
//                                            }
//                                            catch (InvocationTargetException e)
//                                            {
//                                                throwable = e.getTargetException();
//                                                log.error("���÷����ṩ��[" + securityProviderInfo + "]�з���[" + method + "]�쳣��"
//                                                        + e.getMessage(), e);
//                                            }
//                                            catch (Exception e)
//                                            {
//                                                throwable = e;
//                                                log.error("���÷����ṩ��[" + securityProviderInfo + "]�з���[" + method + "]�쳣��"
//                                                        + e.getMessage(), e);
//                                            }
//                                        }
//                                        else
//                                        {
//                                            try
//                                            {
//                                                method.invoke(provider, args);
//                                            }
//                                            catch (Exception e)// �׳��쳣ʱ����¼���쳣��
//                                            // ����ִ�к�����provider�ķ���
//                                            {
//                                                // �������񷽷�����ʱ���������������ṩ�߷���ִ��ʱ���쳣
//                                                log.error("ͬ�����÷����ṩ��[" + securityProviderInfo + "]�з���[" + method
//                                                        + "]�쳣��" + e.getMessage(), e);
//                                            }
//                                        }
//                                        // �׳�ȱʡ�����ṩ�߷����쳣��ͬʱ��ֹ����providerͬ��ִ���߼�
//
//                                    }
//                                    else
//                                    // �����Է���ִ���߼����������������쳣��������Ҫ������������쳣
//                                    {
//                                        if (isdelegate)
//                                        {
//                                            try
//                                            {
//                                                obj = method.invoke(provider, args);
//                                            }
//                                            catch (InvocationTargetException e)
//                                            {
//                                                if (synm.isRollbackException(e.getTargetException())) // �������쳣
//                                                // ��
//                                                // �����׳��쳣
//                                                // ��
//                                                // �ж����������ṩ�߷���ִ��
//                                                {
//                                                    throw e.getTargetException();
//                                                }
//                                                else
//                                                {
//                                                    throwable = e.getTargetException();// �׳��쳣ʱ��¼���쳣
//                                                    // ��
//                                                    // ����ִ�к�����provider��ͬ������
//                                                }
//                                            }
//
//                                            catch (Exception e)
//                                            {
//                                                if (synm.isRollbackException(e)) // �������쳣
//                                                // ��
//                                                // �����׳��쳣
//                                                // ��
//                                                // �ж����������ṩ�߷���ִ��
//                                                {
//                                                    throw e;
//                                                }
//                                                else
//                                                {
//                                                    throwable = e;// �׳��쳣ʱ��¼���쳣��
//                                                    // ����ִ�к�����provider��ͬ������
//                                                }
//                                            }
//                                        }
//                                        else
//                                        {
//                                            try
//                                            {
//                                                method.invoke(provider, args);
//                                            }
//                                            catch (InvocationTargetException e)
//                                            {
//                                                if (synm.isRollbackException(e.getTargetException())) // �������쳣
//                                                // ��
//                                                // �����׳��쳣
//                                                // ��
//                                                // �ж����������ṩ�߷���ִ��
//                                                {
//                                                    throw e;
//                                                }
//                                                else
//                                                {
//                                                    log.error("ͬ�����÷����ṩ��[" + securityProviderInfo + "]�з���[" + method
//                                                            + "]�쳣��" + e.getMessage(), e);
//                                                }
//                                            }
//                                            catch (Exception e)
//                                            {
//                                                if (synm.isRollbackException(e)) // �������쳣
//                                                // ��
//                                                // �����׳��쳣
//                                                // ��
//                                                // �ж����������ṩ�߷���ִ��
//                                                {
//                                                    throw e;
//                                                }
//                                                else
//                                                {
//                                                    log.error("ͬ�����÷����ṩ��[" + securityProviderInfo + "]�з���[" + method
//                                                            + "]�쳣��" + e.getMessage(), e);
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                                // log.debug(
//                                // "providerInfoQueue.getSecurityProviderInfo("+
//                                // i
//                                // +").getProvider()" +
//                                // providerInfoQueue.getSecurityProviderInfo(i)
//                                // .getProvider());
//                            }
//                            if (throwable != null)// ����ڷ�����ִ�й����з����쳣���׳����쳣
//                                throw throwable;
//                            if (interceptor != null)
//                                interceptor.after(method, args);
//                            return obj;
//                        }
//                    }
//                    catch (InvocationTargetException e)
//                    {
//                        if (interceptor != null)
//                        {
//                            try
//                            {
//                                interceptor.afterThrowing(method, args, e.getTargetException());
//                            }
//                            catch (Exception ei)
//                            {
//                                ei.printStackTrace();
//                            }
//                        }
//                        throw e.getTargetException();
//                    }
//                    catch (Throwable t)
//                    {
//                        try
//                        {
//                            t.printStackTrace();
//                            if (interceptor != null)
//                                interceptor.afterThrowing(method, args, t);
//                        }
//                        catch (Exception e)
//                        {
//                            e.printStackTrace();
//                        }
//                        throw t;
//                    }
//                    finally
//                    {
//                        try
//                        {
//                            if (interceptor != null)
//                                interceptor.afterFinally(method, args);
//                        }
//                        catch (Exception e)
//                        {
//                            e.printStackTrace();
//                        }
//                        interceptor = null;
//                    }
//                }
//                else
//                {
//                    return RPCHelper.getRPCHelper().rpcService(serviceID, method, args,callcontext);
//                }
//            }
//        });
//
//    }

    /**
     * ͨ���ض������ṩ�ӿڻ�ȡ��
     * 
     * @param providerManagerType
     *            String
     * @return Object
     * @throws SPIException
     */
    public static Object getProvider(String providerManagerType) throws SPIException
    {
        return getProvider(providerManagerType, null);
    }

    /**
     * ��ȡȫ��ȱʡ��provider
     * 
     * @return Object
     * @throws SPIException
     */
    public static Object getProvider() throws SPIException
    {
        return getProvider(null, null);
    }

    // /**
    // * ͨ���ض������������ض�����Դʵ�ֵ��ṩ�ӿڻ�ȡ��
    // * @param providerManagerType String
    // * @param sourceType String
    // * @return Provider
    // * @throws SPIException
    // */
    // public static Provider getProvider(String providerManagerType,String
    // sourceType) throws SPIException
    // {
    // ProviderManagerInfo providerManagerInfo = null;
    // if(providerManagerType != null)
    // providerManagerInfo = ConfigManager.getInstance().
    // getProviderManagerInfo(providerManagerType);
    // else
    // {
    // providerManagerInfo =
    // ConfigManager.getInstance().getDefaultProviderManagerInfo();
    // providerManagerType = providerManagerInfo.getId() ;
    // }
    // //��spi�����ߵĻ���ؼ���ΪproviderManagerType �� ":" + sourceType;
    // String cacheKey = sourceType != null?providerManagerType+":"+sourceType
    // :providerManagerType+":"+DEFAULT_CACHE_KEY;
    // String syncacheKey = providerManagerType+":"+SYNCHRO_CACHE_KEY;
    //
    // Provider provider = null;
    // //�ж��Ƿ�����Ϊ��ʵ��ģʽ������ǻ�ȡ��ʵ�����������´���providerʵ��
    // if(providerManagerInfo.isSinglable())
    // {
    // provider = (Provider) providers.get(cacheKey);
    // if (provider == null) {
    // try {
    // if (sourceType == null) {
    // provider =
    // (Provider) providerManagerInfo.
    // getDefaulProviderInfo().
    // getProvider();
    // } else {
    // provider =
    // (Provider) providerManagerInfo.
    // getSecurityProviderInfoByType(sourceType).
    // getProvider();
    //
    // }
    // providers.put(cacheKey, provider);
    // } catch (Exception e) {
    // throw new SPIException(
    // "Failed to get UserManagement class instance..."
    // + e.toString());
    // }
    // }
    // }
    // else
    // {
    // if (sourceType == null)
    // provider = providerManagerInfo.
    // getDefaulProviderInfo().getProvider();
    // else
    // provider = providerManagerInfo.
    // getSecurityProviderInfoByType(sourceType)
    // .getProvider();
    // }
    // //�������ͬ�����ƣ���ȡͬ������ӿڣ�����ֱ�ӷ���ȱʡ�Ĺ���ӿ�
    // if (providerManagerInfo.isSynchronizedEnabled()) {
    // Provider synProvider = null;
    // //����ǵ�ʵ�����ȡ����ʵ���������������ɴ���ʵ��
    // if(providerManagerInfo.isSinglable())
    // {
    // synProvider = (Provider) providers.get(
    // syncacheKey);
    //
    // if (synProvider == null) {
    // Provider finalsynProvider = provider;
    // synProvider = (Provider) createInf(providerManagerInfo,
    // finalsynProvider);
    // if (synProvider != null) {
    // providers.put(
    // syncacheKey, synProvider);
    // }
    // }
    // }
    // else
    // {
    // Provider finalsynProvider = provider;
    // synProvider = (Provider) createInf(providerManagerInfo,
    // finalsynProvider);
    //
    // }
    // return synProvider;
    // }
    // else
    // {
    // return provider;
    // }
    // }

    /**
     * ��ȡ���������ض��ṩ��ʵ������
     */
    public static Object getProvider(String providerManagerType, String sourceType) throws SPIException
    {
        return getProvider((CallContext) null, providerManagerType, sourceType,false);
    }

//    private static Map<String,Object> servicProviders = new HashMap<String,Object>();
//    
//    private static Map<String,Object> destroyServiceMethods = new HashMap<String,Object>();
//
//    private static final List<DisposableBean> singleDestorys = new ArrayList<DisposableBean>();

    /**
     * ͨ���ض������������ض�����Դʵ�ֵ��ṩ�ӿڻ�ȡ��
     * 
     * @param providerManagerType
     *            String
     * @param sourceType
     *            String
     * @return Object
     * @throws SPIException
     */
    // public static Object getProvider(Context parent,
    // String providerManagerType,
    // String sourceType) throws SPIException
    // {
    // ServiceID serviceID = new ServiceID(providerManagerType);
    // ProviderManagerInfo providerManagerInfo = null;
    // if(providerManagerType != null)
    // {
    // providerManagerInfo = defaultContext.
    // getProviderManagerInfo(providerManagerType);
    // }
    // else
    // {
    // providerManagerInfo =
    // defaultContext.getDefaultProviderManagerInfo();
    // providerManagerType = providerManagerInfo.getId() ;
    // }
    // if(providerManagerInfo == null)
    // {
    // throw new SPIException("SPI[" + providerManagerType +"] in "
    // + parent + " does not exist.");
    // }
    // //��spi�����ߵĻ���ؼ���ΪproviderManagerType �� ":" + sourceType;
    // if(sourceType == null || sourceType.equals(""))
    // {
    // sourceType = providerManagerInfo.getDefaulProviderInfo().getType();
    // }
    //        
    // String key = providerManagerType + ":" + sourceType;
    // Object finalsynProvider = null;
    // if(providerManagerInfo.isSinglable())
    // {
    // finalsynProvider = servicProviders.get(key);
    // if(finalsynProvider != null)
    // return finalsynProvider;
    // }
    //
    // Object provider = null;
    // //�ж��Ƿ�����Ϊ��ʵ��ģʽ������ǻ�ȡ��ʵ�����������´���providerʵ��
    // if(providerManagerInfo.isSinglable())
    // {
    //            
    // try {
    // provider = providerManagerInfo.
    // getSecurityProviderInfoByType(sourceType).
    // getSingleProvider(parent);
    // } catch (CurrentlyInCreationException e) {
    // // TODO Auto-generated catch block
    // // e.printStackTrace();
    // throw new SPIException(e);
    // }
    //               
    //            
    // }
    // else
    // {
    // try {
    // provider = providerManagerInfo.
    // getSecurityProviderInfoByType(sourceType)
    // .getProvider(parent);
    // } catch (CurrentlyInCreationException e) {
    // // log.error(e);
    // // e.printStackTrace();
    // // return null;
    // throw new SPIException(e);
    // }
    // }
    // if(provider == null)
    // throw new SPIException("�������["+key + "]Ϊnull,������������Ƿ���ȷ��");
    //            
    // finalsynProvider = provider;
    //        
    // //���ɹ������Ķ�̬����ʵ��,����������µ��������Ҫ��������
    // try
    // {
    // if(providerManagerInfo.enableTransaction() &&
    // providerManagerInfo.isSynchronizedEnabled())
    // {
    // if(providerManagerInfo.getProviderInfoQueue().size() > 1)
    // finalsynProvider =
    // BaseSPIManager.createSynTXInf(parent,providerManagerInfo
    // ,finalsynProvider);
    // else
    // {
    // finalsynProvider =
    // BaseSPIManager.createTXInf(parent,providerManagerInfo,finalsynProvider);
    // }
    //        		
    // }
    // else if(providerManagerInfo.enableTransaction())
    // {
    // finalsynProvider =
    // BaseSPIManager.createTXInf(parent,providerManagerInfo,finalsynProvider);
    // }
    // else if(providerManagerInfo.isSynchronizedEnabled())
    // {
    // if(providerManagerInfo.getProviderInfoQueue().size() > 1)
    // {
    // finalsynProvider =
    // BaseSPIManager.createSynInf(parent,providerManagerInfo,finalsynProvider);
    // }
    // else if(providerManagerInfo.usedCustomInterceptor())
    // {
    // finalsynProvider =
    // BaseSPIManager.createInf(parent,providerManagerInfo,finalsynProvider);
    // }
    //        		
    // }
    // else if(providerManagerInfo.usedCustomInterceptor())
    // {
    // finalsynProvider =
    // BaseSPIManager.createInf(parent,providerManagerInfo,finalsynProvider);
    // }
    //        		
    //
    // }
    // catch(Exception e)
    // {
    // e.printStackTrace();
    // throw new SPIException(e);
    //        	
    // }
    // if(providerManagerInfo.isSinglable())
    // {
    // synchronized(servicProviders)
    // {
    // servicProviders.put(key, finalsynProvider);
    // }
    // }
    // return finalsynProvider;
    //        
    // }
    
    public static Object getProvider(CallContext parent, String providerManagerType, String sourceType) throws SPIException
    {
        return getProvider(parent, providerManagerType, sourceType,false);
    }
    static Object getProvider(CallContext callContext, String providerManagerType, String sourceType,boolean frombeanobject) throws SPIException
    {
        return defaultContext.getProvider(callContext, providerManagerType, sourceType, frombeanobject);
//        int idx = providerManagerType.indexOf("?");
//        
//        String _name = providerManagerType;
//        if(callContext == null)
//            callContext = new CallContext();
//        if(idx > 0)
//        {
//            String params = providerManagerType.substring(idx + 1);
//            callContext = buildCallContext(params,callContext);
//            providerManagerType = providerManagerType.substring(0,idx);
//        }
//        ServiceID serviceID = buildServiceID(RPCHelper.serviceids,providerManagerType, ServiceID.PROVIDER_BEAN_SERVICE, sourceType);
////        if(callContext != null && callContext.getSecutiryContext() != null)
////            callContext.getSecutiryContext().setServiceid(serviceID.getService());
//        // new ServiceID(providerManagerType,sourceType,GroupRequest.GET_ALL,0,
//        // ServiceID.result_rsplist,ServiceID.PROVIDER_BEAN_SERVICE);
//        // ServiceID(String serviceID,int resultMode,int waittime,int
//        // resultType)
//
//        ProviderManagerInfo providerManagerInfo = null;
//        if (providerManagerType != null)
//        {
//            providerManagerInfo = defaultContext.getProviderManagerInfo(serviceID.getService());
//        }
//        else
//        {
//            providerManagerInfo = defaultContext.getDefaultProviderManagerInfo();
//            providerManagerType = providerManagerInfo.getId();
//        }
//        if (providerManagerInfo == null)
//        {
//            if(frombeanobject)
//                throw new SPIException("SPI[" + providerManagerType + "] in " + callContext.getLoopContext() + " does not exist.");
//            else
//            {
//                return BaseSPIManager.getBeanObject(callContext,_name,null,true);
//            }
//        }
//        // ��spi�����ߵĻ���ؼ���ΪproviderManagerType �� ":" + sourceType;
//        if (sourceType == null || sourceType.equals(""))
//        {
//            sourceType = providerManagerInfo.getDefaulProviderInfo().getType();
//        }
//
//        String key = providerManagerType + ":" + sourceType;
//        Object finalsynProvider = null;
//        if (providerManagerInfo.isSinglable())
//        {
//            finalsynProvider = servicProviders.get(key);
//            if (finalsynProvider != null)
//                return finalsynProvider;
//        }
//
//        Object provider = null;
//
//        provider = providerManagerInfo.getSecurityProviderInfoByType(sourceType).getProvider(callContext);
//        if (provider == null)
//            throw new SPIException("�������[" + key + "]Ϊnull,������������Ƿ���ȷ��");
//
//        finalsynProvider = provider;
//
//        // ���ɹ������Ķ�̬����ʵ��,����������µ��������Ҫ��������
//        try
//        {
//            if (providerManagerInfo.enableTransaction() && providerManagerInfo.isSynchronizedEnabled())
//            {
//                if (providerManagerInfo.getProviderInfoQueue().size() > 1)
//                    finalsynProvider = BaseSPIManager.createSynTXInf(callContext, providerManagerInfo, finalsynProvider,
//                            serviceID);
//                else
//                {
//                    finalsynProvider = BaseSPIManager.createTXInf(callContext, providerManagerInfo, finalsynProvider,
//                            serviceID);
//                }
//
//            }
//            else if (providerManagerInfo.enableTransaction())
//            {
//                finalsynProvider = BaseSPIManager.createTXInf(callContext, providerManagerInfo, finalsynProvider, serviceID);
//            }
//            else if (providerManagerInfo.isSynchronizedEnabled())
//            {
//                if (providerManagerInfo.getProviderInfoQueue().size() > 1)
//                {
//                    finalsynProvider = BaseSPIManager.createSynInf(callContext, providerManagerInfo, finalsynProvider,
//                            serviceID);
//                }
//                else if (providerManagerInfo.usedCustomInterceptor())
//                {
//                    finalsynProvider = BaseSPIManager.createInf(callContext, providerManagerInfo, finalsynProvider,
//                            serviceID);
//                }
//
//            }
//            else if (providerManagerInfo.usedCustomInterceptor())
//            {
//                finalsynProvider = BaseSPIManager.createInf(callContext, providerManagerInfo, finalsynProvider, serviceID);
//            }
//            else if (serviceID.isRemote())
//            {
//                finalsynProvider = BaseSPIManager.createInf(callContext, providerManagerInfo, finalsynProvider, serviceID);
//            }
//
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            throw new SPIException(e);
//
//        }
//        if (providerManagerInfo.isSinglable()
//                && (providerManagerInfo.enableTransaction() || providerManagerInfo.isSynchronizedEnabled()
//                        || providerManagerInfo.usedCustomInterceptor() || serviceID.isRemote()))
//        {
//            if(callContext == null || (callContext != null && !callContext.containHeaders() && !serviceID.isRestStyle()))
//            {
//                synchronized (servicProviders)
//                {
//    
//                    Object temp = servicProviders.get(key);
//                    if (temp != null)
//                        return temp;
//                    servicProviders.put(key, finalsynProvider);
//                }
//            }
//            
//        }
//        return finalsynProvider;

    }

    public BaseSPIManager()
    {
    }

    // public static void main(String[] args)
    // {
    // Method[] methods = Test.class.getMethods();
    // for(int i = 0; i < methods.length; i ++)
    // {
    // Method m = methods[i];
    // Class[] cluss = m.getParameterTypes();
    // for(int j = 0; j < cluss.length; j ++)
    // {
    //    			
    // }
    // }
    //    	
    // System.out.println(String.class);
    // }

    public static void main(String[] args)
    {
        long s = System.currentTimeMillis();
        for (int i = 0; i < 600000; i++)
        {
            System.out.println(i);
        }
        long end = System.currentTimeMillis();
        System.out.println((end - s) + "s");
    }

    public static String getProperty(String name)
    {
        return defaultContext.getProperty(name);
    }

    public static int getIntProperty(String name)
    {
        return defaultContext.getIntProperty(name);
    }

    public static boolean getBooleanProperty(String name)
    {
        return defaultContext.getBooleanProperty(name);
    }

    public static String getProperty(String name, String defaultValue)
    {
        return defaultContext.getProperty(name, defaultValue);
    }

    public static Object getObjectProperty(String name)
    {
        return getObjectProperty(name, null);
    }

    public static Object getObjectProperty(String name, String defaultValue)
    {
        return defaultContext.getObjectProperty(name, defaultValue);
    }

    public static int getIntProperty(String name, int defaultValue)
    {
        return defaultContext.getIntProperty(name, defaultValue);
    }

    public static boolean getBooleanProperty(String name, boolean defaultValue)
    {
        return defaultContext.getBooleanProperty(name, defaultValue);
    }

    /**
     * ����Զ�̵���Ŀ��ip��ַ�Ͷ˿ڻ�ȡ��Ӧ�������з��صĽ��
     * ������mina��jgroupЭ��
     * ����Э��Ӧ��ͨ���ض�Э��Ľӿںʹ�Э���getRPCResult������ȡ
     * ��Ӧ�������Ľӿ�
     * 
     * @param ip
     * @param port
     * @param values
     * @return     
     * @throws Throwable 
     */
    public static Object getRPCResult(String ip, String port, Object values) throws Throwable
    {
        
        return ApplicationContext.getRPCResult(ip, port, values);
    }
    
    /**
     * ����url��ַ��ȡ��Ӧ�Ľ��ֵ
     * 
     * @param url ��Ӧ��Զ�̵�ַurl
     * @param values �ಥ���÷��صĽ����
     * @param protocol rpc��Ӧ��Զ��Э�飬
     * @see org.frameworkset.remote.Target.BROADCAST_TYPE_MUTICAST
            org.frameworkset.remote.Target.BROADCAST_TYPE_UNICAST        
        org.frameworkset.remote.Target.BROADCAST_TYPE_JRGOUP    
        org.frameworkset.remote.Target.BROADCAST_TYPE_MINA    
        org.frameworkset.remote.Target.BROADCAST_TYPE_JMS        
        org.frameworkset.remote.Target.BROADCAST_TYPE_RMI
        org.frameworkset.remote.Target.BROADCAST_TYPE_EJB
        org.frameworkset.remote.Target.BROADCAST_TYPE_CORBA            
        org.frameworkset.remote.Target.BROADCAST_TYPE_WEBSERVICE
     * @return
     * @throws Throwable 
     */
    public static Object getRPCResult(String url, Object values,String protocol) throws Throwable
    {
        return ApplicationContext.getRPCResult(url, values, protocol);
        
    }
    
    public static Object getRPCResult(int index, Object values) throws Throwable
    {
        return ApplicationContext.getRPCResult(index, values);
        
    }
    
    public static int getRPCResultSize(Object values)
	 {
		 return ApplicationContext.getRPCResultSize(values);
	 }

    public static Object getBeanObject(String name)
    {
        return getBeanObject(name, null);
        // return ServiceProviderManager.getInstance().getObjectProperty(name);
        // if(value == null)
        // throw new AssembleException("�����ļ�û��ָ������[" + name + "]��");

    }

    public static Object getBeanObject(String name, Object defaultValue)
    {
        return getBeanObject(null, name, defaultValue,false);
        // return ServiceProviderManager.getInstance().getObjectProperty(name,
        // defaultValue);
        // if(value == null)
        // throw new AssembleException("�����ļ�û��ָ������[" + name + "]��");

    }

    public static ProSet getSetProperty(String name)
    {
        return defaultContext.getSetProperty(name);
        // if(value == null)
        // throw new AssembleException("�����ļ�û��ָ������[" + name + "]��");

    }

    public static ProSet getSetProperty(String name, ProSet defaultValue)
    {
        return defaultContext.getSetProperty(name, defaultValue);

    }

    public static ProList getListProperty(String name)
    {
        return defaultContext.getListProperty(name);

    }

    public static ProList getListProperty(String name, ProList defaultValue)
    {
        return defaultContext.getListProperty(name, defaultValue);

    }

    public static ProMap getMapProperty(String name)
    {
        return defaultContext.getMapProperty(name);
        // if(value == null)
        // throw new AssembleException("�����ļ�û��ָ������[" + name + "]��");

    }

    public static ProMap getMapProperty(String name, ProMap defaultValue)
    {
        return defaultContext.getMapProperty(name, defaultValue);

    }

    /**
     * ��ȡ��ǰ������ͨ�Ľڵ��嵥
     */
    @SuppressWarnings("unchecked")
    public static List<RPCAddress> getAllNodes()
    {
//    {
//        List<Address> nodes = JGroupHelper.getJGroupHelper().getAppservers();
//        List<RPCAddress> ret = new ArrayList<RPCAddress>();
//        for (IpAddress ipaddr : nodes)
//        {
//            RPCAddress rpcaddr = new RPCAddress(ipaddr.getIpAddress(), ipaddr.getPort());
//            ret.add(rpcaddr);
//        }

//        return ret;
        return ApplicationContext.getAllNodes();
    }
    
    
    public static List<RPCAddress> getAllNodes(String protocol)
    {
//    {
//        List<Address> nodes = JGroupHelper.getJGroupHelper().getAppservers();
//        List<RPCAddress> ret = new ArrayList<RPCAddress>();
//        for (IpAddress ipaddr : nodes)
//        {
//            RPCAddress rpcaddr = new RPCAddress(ipaddr.getIpAddress(), ipaddr.getPort());
//            ret.add(rpcaddr);
//        }

//        return ret;
        return ApplicationContext.getAllNodes(protocol);
    }

    // /**
    // * ���������Ҫ�Է������������Ӧ�Ĵ���
    // * @param context
    // * @param name
    // * @param defaultValue
    // * @return
    // */
    // @SuppressWarnings("unchecked")
    // public static Object getBeanObject(Context context, String name,Object
    // defaultValue)
    // {
    // ServiceID serviceID = new
    // ServiceID(name,GroupRequest.GET_ALL,0,ServiceID.
    // result_rsplist,ServiceID.PROPERTY_BEAN_SERVICE);
    // Pro providerManagerInfo =
    // defaultContext.getPropertyBean
    // (serviceID.getService());
    // if(providerManagerInfo == null)
    // {
    // ProviderManagerInfo providerManagerInfo_ = null;
    //            
    // providerManagerInfo_ = ServiceProviderManager.getInstance()
    // .getProviderManagerInfo(serviceID.getService());
    // if(providerManagerInfo_ == null)
    // throw new SPIException("û�ж�������Ϊ[" + name + "]��bean����");
    // return BaseSPIManager.getProvider(context, name, null);
    // }
    //        
    // String key = name;
    // Object finalsynProvider = null;
    // if (providerManagerInfo.isSinglable()) {
    // finalsynProvider = servicProviders.get(key);
    // if (finalsynProvider != null)
    // return finalsynProvider;
    // }
    // finalsynProvider =
    // defaultContext.getBeanObject(context
    // ,serviceID.getService(),defaultValue);
    // // ServiceID serviceID = new
    // ServiceID(name,GroupRequest.GET_ALL,0,ServiceID
    // .result_rsplist,ServiceID.PROPERTY_BEAN_SERVICE);
    // if(providerManagerInfo.enableTransaction() || serviceID.isRemote())
    // {
    //            
    // finalsynProvider = createInf(context, providerManagerInfo,
    // finalsynProvider,serviceID);
    // if (providerManagerInfo.isSinglable()) {
    // synchronized (servicProviders) {
    // servicProviders.put(key, finalsynProvider);
    // }
    // }
    // }
    // return finalsynProvider;
    // }

    public static Object getBeanObject(CallContext context, String name, Object defaultValue)
    {
        return getBeanObject(context, name, defaultValue,false);
    }
    
//    public static CallContext buildCallContext(String params,CallContext context)
//    {
//        if(context == null)
//            context = new CallContext();
//    	StringTokenizer tokenizer = new StringTokenizer(params,"&",false);
//    	   
//        /**
//         * Э���а��������Բ���������������·������
//         */
//        Headers headers = null;
//    	SecurityContext securityContext = null;
//    	String user = null;
//    	String password = null;
//    	while(tokenizer.hasMoreTokens())
//    	{
//    	    
//    		String parameter = tokenizer.nextToken();
//    		
//    		int idex = parameter.indexOf("=");
//    		if(idex <= 0)
//    		{
//    			throw new SPIException("�Ƿ��ķ��������[" + params +"]");
//    		}
//    		StringTokenizer ptokenizer = new StringTokenizer(parameter,"=",false);
//    		String name = ptokenizer.nextToken();
//    		String value = ptokenizer.nextToken();
//    		Header header = new Header(name,value);
//    		if(name.equals(SecurityManager.USER_ACCOUNT_KEY))
//    		{
//        		user = value;
//        		
//    		}
//    		else if(name.equals(SecurityManager.USER_PASSWORD_KEY))
//            {
//                password = value;
//                
//            }
//    		else
//    		{
//        		if(headers == null)
//        		    headers = new Headers(); 
//        		headers.put(header.getName(),header);
//    		}
//    	}
//    	if(securityContext == null)
//            securityContext = new SecurityContext(user,password);
//    	context.setSecutiryContext(securityContext);
//    	context.setHeaders(headers);   
//    	return context;
//    }
    
    /**
     * bean��������
     * 
     * @param context
     * @param name
     * @param defaultValue
     * @return
     */
    @SuppressWarnings("unchecked")
    static Object getBeanObject(CallContext context, String name, Object defaultValue,boolean fromprovider)
    {
//    	//�����������
//    	int idx = name.indexOf("?");
//    	
//    	
//    	String _name = name;
//    	if(context == null)
//            context = new CallContext();
//    	if(idx > 0)
//    	{
//    		String params = name.substring(idx + 1);
//    		context = buildCallContext(params,context);
////        	name = name.substring(0,idx);
//    	}
//    	 
//    	
//        ServiceID serviceID = buildServiceID(RPCHelper.serviceids,name, ServiceID.PROPERTY_BEAN_SERVICE);
//        // new ServiceID(name,GroupRequest.GET_ALL,0,ServiceID.result_rsplist,
//        // ServiceID.PROPERTY_BEAN_SERVICE);
//        Pro providerManagerInfo = defaultContext.getPropertyBean(serviceID.getService());
////        if(context != null && context.getSecutiryContext() != null)
////            context.getSecutiryContext().setServiceid(serviceID.getService());
//        if (providerManagerInfo == null)
//        {
//            if(!fromprovider)
//            {
//                ProviderManagerInfo providerManagerInfo_ = null;
//    
//                providerManagerInfo_ = defaultContext.getProviderManagerInfo(serviceID.getService());
//                if (providerManagerInfo_ == null)
//                    throw new SPIException("û�ж�������Ϊ[" + name + "]��bean����");
//                return BaseSPIManager.getProvider(context, _name, null,true);
//            }
//            else
//            {
//                throw new SPIException("û�ж�������Ϊ[" + name + "]��bean����");
//            }
//        }
//        return getBeanObject(context, providerManagerInfo, defaultValue, serviceID);
        return defaultContext.getBeanObject( context,  name,  defaultValue, fromprovider);
        // String key = name;
        // Object finalsynProvider = null;
        // if (providerManagerInfo.isSinglable()) {
        // finalsynProvider = servicProviders.get(key);
        // if (finalsynProvider != null)
        // return finalsynProvider;
        // }
        // finalsynProvider =
        // defaultContext.getBeanObject(
        // context,serviceID.getService(),defaultValue);
        // // ServiceID serviceID = new
        // ServiceID(name,GroupRequest.GET_ALL,0,ServiceID
        // .result_rsplist,ServiceID.PROPERTY_BEAN_SERVICE);
        // if(providerManagerInfo.enableTransaction() || serviceID.isRemote())
        // {
        //            
        // finalsynProvider = createInf(context, providerManagerInfo,
        // finalsynProvider,serviceID);
        // if (providerManagerInfo.isSinglable()) {
        // synchronized (servicProviders) {
        // servicProviders.put(key, finalsynProvider);
        // }
        // }
        // }
        // return finalsynProvider;
    }

    public static Object getBeanObject(CallContext context, String name)
    {
        return getBeanObject(context, name, null,false);
    }

    public static Pro getProBean(String name)
    {
        // TODO Auto-generated method stub
        return defaultContext.getProBean(name);
    }

    public static Object getBeanObject(CallContext context, Pro providerManagerInfo)
    {
        return getBeanObject(context, providerManagerInfo, null, null);
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
    public static Object getBeanObject(CallContext context, Pro providerManagerInfo, Object defaultValue,
            ServiceID serviceID)
    {
        return defaultContext.getBeanObject( context,  providerManagerInfo,  defaultValue,
                 serviceID);
//        if (providerManagerInfo == null)
//            throw new SPIException("bean����Ϊ�ա�");
//        String key = providerManagerInfo.getName();
//        if(providerManagerInfo.isRefereced())
//        {
//        	Object retvalue = providerManagerInfo.getTrueValue(context,defaultValue);
//        	return retvalue;
//        }
//        
//        Object finalsynProvider = null;
//        if (serviceID == null)
//            serviceID = buildServiceID(RPCHelper.serviceids,key, ServiceID.PROPERTY_BEAN_SERVICE);
//        // new ServiceID(key, GroupRequest.GET_ALL ,0,ServiceID.result_rsplist,ServiceID.PROPERTY_BEAN_SERVICE);
//        key = serviceID.getOrigineServiceID();
//        finalsynProvider = defaultContext.getBeanObject(context, providerManagerInfo,
//                defaultValue);
//        if (providerManagerInfo.enableTransaction() || providerManagerInfo.usedCustomInterceptor() || serviceID.isRemote())
//        {
//            if (providerManagerInfo.isSinglable() )
//            {
////                String key = serviceID.getServiceID();
//                if(context != null && !context.containHeaders() && !serviceID.isRestStyle() )//�������ͷ��Ϣʱ�������ཫ���ܱ����壬ԭ����ͷ��Ϣ�Ķ�̬�Իᵼ�»���ʵ������
//                {
//                    Object provider = servicProviders.get(key);
//                    if (provider != null)
//                        return provider;
//                    synchronized (servicProviders)
//                    {
//                        provider = servicProviders.get(key);
//                        if (provider != null)
//                            return provider;
//                        provider = createInf(context, providerManagerInfo, finalsynProvider, serviceID);
//                        servicProviders.put(key, provider);
//                    }
//                    return provider;
//                }
//                else
//                {
//                    finalsynProvider = createInf(context, providerManagerInfo, finalsynProvider, serviceID);
//                    return finalsynProvider;
//                }
//                
//            }
//            else
//            {
//                finalsynProvider = createInf(context, providerManagerInfo, finalsynProvider, serviceID);
//                return finalsynProvider;
//            }
//        }
//        else
//        {
//            return finalsynProvider;
//        }
    }

//    private static Map<String, ServiceID> serviceids = new java.util.WeakHashMap<String, ServiceID>();

//    public static ServiceID buildServiceID(Map<String,ServiceID> serviceids,String serviceid, int serviceType, String providertype)
//    {
//        return RPCHelper.buildServiceID( serviceids,serviceid, serviceType, providertype);
//
//    }
//    
//    public static ServiceID buildServiceID(String serviceid, int serviceType)
//    {
//       
//////        SoftReference<ServiceID> reference;
////        
////        
////            long timeout = getRPCRequestTimeout();
////            ServiceID serviceID = new ServiceID(serviceid, null, GroupRequest.GET_ALL, timeout, ServiceID.result_rsplist,
////                    serviceType);
////           
//           
//        return RPCHelper.buildServiceID(serviceid, serviceType);
//
//    }
    
    

//    public static ServiceID buildServiceID(Map<String, ServiceID> serviceids,String serviceid, int serviceType)
//    {
//
//        return buildServiceID(serviceids,serviceid, serviceType, null);
//
//    }
//    
//    public static ServiceID buildBeanServiceID(Map<String, ServiceID> serviceids,String serviceid)
//    {
//
//        return buildServiceID(serviceids,serviceid, ServiceID.PROPERTY_BEAN_SERVICE, null);
//
//    }
    
    /**
     * У���ַ�Ƿ��ǺϷ�����ͨ�ĵ�ַ
     * @param address
     * @return
     */
    public static boolean validateAddress(RPCAddress address)
    {
//          Vector servers = getAppservers();
//          for(int i = 0; i < servers.size() ; i ++)
//          {
//                  IpAddress ipAddress = (IpAddress)servers.get(0);
//                  if(ipAddress.equals(address))
//                          return true;
//                  
//          }
//          return false;
//      return JGroupHelper.getJGroupHelper().validateAddress(address);
//        return RPCHelper.getRPCHelper().validateAddress(address);
        return ApplicationContext.validateAddress(address);
    }
    
    /**
     * �ж�ϵͳ�Ƿ������˼�Ⱥ����          
     * @return
     */
    public static boolean clusterstarted() {
//        return JGroupHelper.getJGroupHelper().clusterstarted() 
//                || MinaRPCServer.getMinaRPCServer().started() 
//                || JMSServer.getJMSServer().started();
        return ApplicationContext.clusterstarted();
    }
    
    
    /**
     * �ж�ϵͳ�Ƿ������˼�Ⱥ����          
     * @return
     */
    public static boolean clusterstarted(String protocol) {
        
//        if(protocol == null)
//        {
//            protocol = Util.default_protocol;
//        }
//        if(protocol.equals(Target.BROADCAST_TYPE_JRGOUP))
//            return JGroupHelper.getJGroupHelper().clusterstarted(); 
//        else if(protocol.equals(Target.BROADCAST_TYPE_MINA))
//        {
//            return MinaRPCServer.getMinaRPCServer().started();
//        }
//        else if(protocol.equals(Target.BROADCAST_TYPE_JMS))
//        {
//            return JMSServer.getJMSServer().started();
//        }
//        else
//        {
//            throw new java.lang.IllegalArgumentException("δ֧��Э�飺" + protocol);
//        }
        return ApplicationContext.clusterstarted(protocol);
        
    }
    
    
    public static String getClusterName()
    {
        return ApplicationContext.getClusterName();
    }

    /**
     * ��ȡwebservice������ý��
     * @param url
     * @param ret
     * @return
     * @throws Throwable 
     */
	public static Object getWSRPCResult(String url, Object ret) throws Throwable
	{
	    return ApplicationContext.getRPCResult(url, ret,Target.BROADCAST_TYPE_WEBSERVICE);
	}
	
	/**
     * ��ȡwebservice������ý��
     * @param url
     * @param ret
     * @return
	 * @throws Throwable 
     */
    public static Object getJMSRPCResult(String url, Object ret) throws Throwable
    {
        return ApplicationContext.getRPCResult(url, ret,Target.BROADCAST_TYPE_JMS);
    }
    
    /**
     * ��ȡwebservice������ý��
     * @param url
     * @param ret
     * @return
     * @throws Throwable 
     */
    public static Object getMinaRPCResult(String url, Object ret) throws Throwable
    {
        return ApplicationContext.getRPCResult(url, ret,Target.BROADCAST_TYPE_MINA);
    }
    
    /**
     * ��ȡwebservice������ý��
     * @param url
     * @param ret
     * @return
     * @throws Throwable 
     */
    public static Object getRMIRPCResult(String url, Object ret) throws Throwable
    {
        return ApplicationContext.getRPCResult(url, ret,Target.BROADCAST_TYPE_RMI);
    }
    
    /**
     * ��ȡwebservice������ý��
     * @param url
     * @param ret
     * @return
     * @throws Throwable 
     */
    public static Object getNettyRPCResult(String url, Object ret) throws Throwable
    {
        return ApplicationContext.getRPCResult(url, ret,Target.BROADCAST_TYPE_NETTY);
    }
    
    /**
     * ��ȡwebservice������ý��
     * @param url
     * @param ret
     * @return
     * @throws Throwable 
     */
    public static Object getJGroupRPCResult(String url, Object ret) throws Throwable
    {
        return ApplicationContext.getRPCResult(url, ret,Target.BROADCAST_TYPE_JRGOUP);
    }
    
    
    
    public static String getStringExtendAttribute(String name ,String extendName)
    {
        return defaultContext.getStringExtendAttribute(name,extendName);
    }

    
    public static Object getExtendAttribute(String name ,String extendName)
    {
//        Pro pro = getProBean(name);
//        if(pro == null)
//            return null;
//        return pro.getExtendAttribute(extendName);
        return defaultContext.getExtendAttribute(name ,extendName);
        
    }
    
    
    public static int getIntExtendAttribute(String name ,String extendName)
    {
//        Pro pro = getProBean(name);
//        if(pro == null)
//            return -1;
//        return pro.getIntExtendAttribute(extendName);
        return defaultContext.getIntExtendAttribute( name , extendName);
        
    }
    
    public static long getLongExtendAttribute(String name ,String extendName)
    {
     // TODO Auto-generated method stub
//        Pro pro = getProBean(name);
//        if(pro == null)
//            return -1;
//        return pro.getLongExtendAttribute(extendName);
        return defaultContext.getLongExtendAttribute( name , extendName);
    }
    
    public static boolean getBooleanExtendAttribute(String name ,String extendName)
    {
//     // TODO Auto-generated method stub
//        Pro pro = getProBean(name);
//        if(pro == null)
//            return false;
//        return pro.getBooleanExtendAttribute(extendName);
        return defaultContext.getBooleanExtendAttribute( name , extendName);
    }

}
