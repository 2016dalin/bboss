package com.frameworkset.proxy;

import java.lang.reflect.Method;

/**
 * <p>Title: InvocationHandler</p>
 *
 * <p>Description: ����̬�������</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author biaoping.yin
 * @version 1.0
 */
public  abstract class InvocationHandler implements java.lang.reflect.InvocationHandler{
    /**
     * ��Ҫ��̬����Ľӿ�ʵ�ֳ���
     */
    protected Object delegate;
    /**
     * �������ṩ���е�������Ľӿ�
     */
    protected Interceptor interceptor;
    public abstract Object invoke(Object proxy, Method method, Object[] args) throws Throwable ;
    public InvocationHandler(Object delegate)
    {
        this.delegate = delegate;
        this.interceptor = null;
    }

    public InvocationHandler(Object delegate,Interceptor interceptor)
    {
        this.delegate = delegate;
        this.interceptor = interceptor;
    }


    public Object getDelegate() {
        return delegate;
    }

    public Interceptor getInterceptor() {
        return interceptor;
    }

}
