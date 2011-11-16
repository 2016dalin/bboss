package org.frameworkset.spi.assemble;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


/**
 * 
 * <p>Title: ProSet.java</p> 
 * <p>Description: </p>
 * <p>bboss workgroup</p>
 * <p>Copyright (c) 2007</p>
 * @Date 2009-9-19 ����11:01:35
 * @author biaoping.yin
 * @version 1.0
 */
public  class ProSet<V extends Pro> extends TreeSet<V> 
{        
    private boolean isfreeze = false;
    /**
     * ����������ͣ��������������ͣ�
     * bean:������ֱ�ӽ���װ��ProListת��ΪList<po����>����
     * String��ProListת��ΪList<String>����
     * Pro��Ĭ������ProList<V extends Pro>������ת��������ָ��editor�༭��
     */
    private String componentType ;
    
    public void freeze()
    {
        this.isfreeze = true;
    }
    private boolean isFreeze()
    {
        
        return this.isfreeze;
    }
    
    private void modify() 
    {
        if(this.isFreeze())
            throw new CannotModifyException();
    }
    @Override
    public boolean add(V o)
    {
        modify(); 
        return super.add(o);
    }
    @Override
    public boolean addAll(Collection<? extends V> c)
    {
        modify();
        return super.addAll(c);
    }
    @Override
    public void clear()
    {
        modify();
        super.clear();
    }
    @Override
    public boolean remove(Object o)
    {
        modify();
        return super.remove(o);
    }
    @Override
    public boolean removeAll(Collection<?> c)
    {
        modify();
        return super.removeAll(c);
    }
    @Override
    public boolean retainAll(Collection<?> c)
    {
        modify();
        return super.retainAll(c);
    }
    public Iterator<V> iterator() {
        return super.iterator();
    }
    /**
     * �����̰߳�ȫ�ķ���
     * @param i
     * @return
     */
    public V get(int i)
    {
        Iterator<V> it = this.iterator();
        int t = 0;
        int size = this.size();
        while(t < size && it.hasNext())
        {
            if(i == t)
            {
                return it.next();
            }
            else
            {
                t ++;
            }
                
        }
        throw new java.lang.IllegalArgumentException("�к� i=" + i + "Խ�磬 ���ڻ���С�����������ݵ��ܸ���size=" + size);
    }
    public Pro getPro(int i)
    {
        return this.get(i);
    }
    public int getInt(int i)
    {
        
        Pro value = this.get(i);
        if(value == null)
            return 0;

        return value.getInt();
    }
    public int getInt(int i,int defaultValue)
    {
        Pro value = this.get(i);
        if(value == null)
            return defaultValue;
//        int value_ = Integer.parseInt(value.toString());
        return value.getInt();
    }
    
    
    public boolean getBoolean(int i)
    {
        Pro value = this.get(i);
        if(value == null)
            return false;
//        boolean value_ = Boolean.parseBoolean(value.toString());
        return value.getBoolean();
    }
    public boolean getBoolean(int i,boolean defaultValue)
    {
        Pro value = this.get(i);
        if(value == null)
            return defaultValue;
        boolean value_ = value.getBoolean(defaultValue);
        return value_;
    }
    
    public String getString(int i)
    {
        Pro value = this.get(i);
        if(value == null)
            return null;
        
        return value.getString();
    }
    public String getString(int i,String defaultValue)
    {
        Pro value = this.get(i);
        
        if(value == null)
            return defaultValue;
        
        return value.getString(defaultValue);
    }
    public ProList getList(int i,ProList defaultValue)
    {   
        Pro value = this.get(i);
        
        if(value == null)
            return defaultValue;
        
        return value.getList(defaultValue);
    }
    public ProList getList(int i)
    {   
        Pro value = this.get(i);
        if(value == null)
            return null;
        
        return value.getList();
    }
    
    public ProSet getSet(int i,ProSet defaultValue)
    {   
        Pro value = this.get(i);
        
        if(value == null)
            return defaultValue;
        
        return value.getSet(defaultValue);
    }
    public ProSet getSet(int i)
    {   
        Pro value = this.get(i);
        if(value == null)
            return null;
        
        return value.getSet();
    }
    
    public Object getObject(int i)
    {
        Pro value = this.get(i);
        if(value == null)
            return null;
        
        return value.getObject();
    }
    
    public Object getObject(int i,Object defaultValue)
    {
        Pro value = this.get(i);
        
        if(value == null)
            return defaultValue;
        
        return value.getObject(defaultValue);
    }
	/**
	 * @return the componentType
	 */
	public String getComponentType() {
		return componentType;
	}
	/**
	 * @param componentType the componentType to set
	 */
	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}
	
	private Set componentSet;
    private Object lock = new Object();
    
    public Set getComponentSet(Class settype)
    {
    	if(this.getComponentType() == null)
    		return this;
    	if(componentSet == null)
    	{
    		synchronized(lock)
    		{
    			if(componentSet == null)
    			{
    				if(this.size() > 0)
    				{
    					if(this.componentType.equalsIgnoreCase(Pro.COMPONENT_BEAN) || this.componentType.equalsIgnoreCase(Pro.COMPONENT_OBJECT_SHORTNAME) || this.componentType.equalsIgnoreCase(Pro.COMPONENT_OBJECT))
    					{
//    						componentSet = new TreeSet();
    						if(settype != TreeSet.class)
    						{
        						try {
        							componentSet = (TreeSet)settype.newInstance();
    							} catch (InstantiationException e) {
    								throw new BeanInstanceException(e);
    							} catch (IllegalAccessException e) {
    								throw new BeanInstanceException(e);
    							}
    						}
    						else
    						{
    							componentSet = new TreeSet();
    						}
    						Iterator keys = this.iterator();
	    					while(keys.hasNext())
	    					{
	    						Pro pro = (Pro)keys.next();
	    						componentSet.add(pro.getBean());	
	    					}
    					}
    					else if(this.componentType.equalsIgnoreCase(Pro.COMPONENT_STRING_SHORTNAME) || this.componentType.equalsIgnoreCase(Pro.COMPONENT_STRING))
    					{
    						if(settype != TreeSet.class)
    						{
        						try {
        							componentSet = (TreeSet)settype.newInstance();
    							} catch (InstantiationException e) {
    								throw new BeanInstanceException(e);
    							} catch (IllegalAccessException e) {
    								throw new BeanInstanceException(e);
    							}
    						}
    						else
    						{
    							componentSet = new TreeSet();
    						}
    						Iterator keys = this.iterator();
	    					while(keys.hasNext())
	    					{
	    						Pro pro = (Pro)keys.next();
	    						componentSet.add(pro.getString());	
	    					}
    					}
    					else
    					{
    						if(settype != TreeSet.class)
    						{
        						try {
        							componentSet = (TreeSet)settype.newInstance();
    							} catch (InstantiationException e) {
    								throw new BeanInstanceException(e);
    							} catch (IllegalAccessException e) {
    								throw new BeanInstanceException(e);
    							}
    						}
    						else
    						{
    							componentSet = new TreeSet();
    						}
    						Iterator keys = this.iterator();
	    					while(keys.hasNext())
	    					{
	    						Pro pro = (Pro)keys.next();
	    						componentSet.add(pro.getBean());	
	    					}
    					}
    				}
    				else
    				{
    					if(settype != TreeSet.class)
						{
    						try {
    							componentSet = (TreeSet)settype.newInstance();
							} catch (InstantiationException e) {
								throw new BeanInstanceException(e);
							} catch (IllegalAccessException e) {
								throw new BeanInstanceException(e);
							}
						}
						else
						{
							componentSet = new TreeSet();
						}
    				}    				
    			}
    		}
    	}
    	return componentSet;
    }
}