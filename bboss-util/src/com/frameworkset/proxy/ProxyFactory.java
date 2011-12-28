package com.frameworkset.proxy;


import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Title: ProxyFactory</p>
 *
 * <p>Description: ������̬����ӿڹ�����</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: framework</p>
 *
 * @author biaoping.yin
 * @version 1.0
 */
public class ProxyFactory {
    /**
     * ����ҵ�����ʵ�ֵ����нӿڣ�����ÿ�δ���ʱ�����µݹ��ȡclass��ʵ�ֵ����нӿ�
     */
    private static Map<Class,Class[]> classInfs = new HashMap<Class,Class[]>();
	private static Class[] getClassArray(List set)
	{
		
		if(set == null || set.size() == 0)
			return NULL;
		Class[] ret = new Class[set.size()];
		for(int i = 0; i < set.size(); i ++)
		{
			ret[i] = (Class)set.get(i);
		}
		return ret;
	}
	
	private static Class[] NULL = new Class[0];
    public static Class[] getAllInterfaces(Class clazz)
    {        
        Class[] ret = classInfs.get(clazz);
        if(ret != null)
        {            
            return ret;
        }
        synchronized(classInfs)
        {
            ret = classInfs.get(clazz);
            if(ret != null)
            {            
                return ret;
            }
            List set = new java.util.ArrayList();
            getAllInterfaces(set,clazz);
            //handler.getDelegate().getClass());
            ret = getClassArray(set);
            classInfs.put(clazz, ret);
            return ret;
            
            
        }
//        return classInfs.get(clazz);
    }
    public static Object createProxy(InvocationHandler handler)
    {
    	
    	
        return Proxy.newProxyInstance(handler.getDelegate().getClass().getClassLoader()
                                      ,getAllInterfaces(handler.getDelegate().getClass())
                                      ,handler);
//        return Proxy.newProxyInstance(handler.getDelegate().getClass().getClassLoader()
//                                      ,handler.getDelegate().getClass().getInterfaces()
//                                      ,handler);

    }

    public static void main(String[] args) {

//        InvocationHandler al;
////        proxy.getProxyClass()
//        ProxyFactory proxyfactory = new ProxyFactory();
//    	List set = new java.util.ArrayList();
//    	getAllInterfaces(set,ArrayList.class);
        Class[] ret = getAllInterfaces(ArrayList.class);
        System.out.println(ret);
        ret = getAllInterfaces(ArrayList.class);
    }
    
    private static void merge(Class[] ins,List totals)
    {
    	for(int i = 0; i < ins.length; i ++)
    	{
    		if(totals.contains(ins[i]))
    			continue;
    		totals.add(ins[i]);
    		
//    		System.out.println(ins[i]);
    		getInterfacesOfInterface(totals,ins[i]);
    		
    	}
    }
    /**
     * ��ȡ������нӿڣ���������Ľӿ�
     * @param clazz
     * @return
     */
    public static void getAllInterfaces(List totals,Class clazz)
    {
    	Class[] ins = clazz.getInterfaces();
    	merge( ins, totals);
    	Class parent = clazz.getSuperclass();    	
    	if(parent == null)
    	{    		
    		return;
    	}    	
    	getAllInterfaces(totals,parent);  	
    	
    }
    
    public static void getInterfacesOfInterface(List totals,Class intf)
    {
    	Class[] sons = intf.getInterfaces();
    	
    	merge( sons, totals);
    }
    
   
}
