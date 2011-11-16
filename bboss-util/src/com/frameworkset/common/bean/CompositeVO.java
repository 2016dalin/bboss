/*
 * Created on 2004-6-3
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.frameworkset.common.bean;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * @author biaoping.yin
 * ���ֵ���󣬿����Զ�����ͬ���͵Ķ��󣨶����Ӧ��������ValueObject�̳У�
 * ValueObjectΪϵͳ������ֵ����Ļ��ࣩ�ַ����ض���map�С�
 * 
 * �����ڶԶ��ֵ�����������ɾ���ĵȲ���������󴫵ݺͻ�����Щ����
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class CompositeVO extends AbstractCompositeVO 
{
	/****************************************************
	 *      ��϶�����Ի����ҵ�����ݽ�������ɾ����    *
	 * 		���ֲ�����Ľ��,���¶������ֲ�����Ӧ�ı�־ *
	 ****************************************************/
	/**
	 * ɾ��״̬��־
	 */
	protected final static int DELETE = 0;
	/**
	 * ����״̬��ʶ
	 */
	protected final static int UPDATE = 1;
	/**
	 * ���״̬��ʶ
	 */
	protected final static int ADD = 2;
	/**
	 * ����״̬��ʶ
	 */
	protected final static int CACHE = 3;
	/**
	 * ����һ��Ķ���
	 * @param obj
	 */
	public void cacheVO(ValueObject obj)
	{
		setVO(obj, CACHE);
	}
	
	public void cacheVOS(List vos)
	{
	    for(int i = 0; vos != null && i < vos.size(); i ++)
	        setVO((ValueObject)vos.get(i), CACHE);
	}
	/**
	 * ��ȡ��������
	 * @param clazz
	 * @return Iterator
	 */
	public Iterator getCacheVO(Class clazz)
	{
		return this.getVO(clazz, new int[] { CACHE });
	}
	/**
	 * ������ҵ����Ϣʱ������װ��Ϣ��ֵ�����ŵ��ö����Ӧ��ɢ����
	 * @param obj
	 */
	public void updateVO(ValueObject obj)
	{
		if (!synchroUpdate(obj))
			setVO(obj, UPDATE);
	}
	/**
	 * ����϶����л�ȡ����ֵ����,
	 * ���������Ա��������һ��ֵ���������ֵ����ļ��ϣ�
	 * ������϶�����ȡ����ʱ���ø÷���
	 * @param clazz Ҫ��ȡֵ�����Ӧ��Class�������磺xxVO.class
	 * @return ��������͵Ķ�������򷵻ظö�����򷵻�null
	 */
	public ValueObject getSingleVO(Class clazz)
	{
		Iterator iterator = getAllVO(clazz);
		if (iterator.hasNext())
			return (ValueObject) iterator.next();
		return null;
	}	
	
	
	/**
	 * ��ȡɢ��������clazz�Ķ���
	 * @param clazz
	 * @return Iterator
	 */
	public Iterator getAllVO(Class clazz)
	{
		return getVO(clazz, new int[] { ADD, UPDATE, DELETE, CACHE });
	}
	/**
	 * �½�ҵ����Ϣʱ������װ��Ϣ��ֵ�����ŵ��ö����Ӧ��ɢ����
	 * ע��:���ñ�����ʱ��Ҫ�޸�obj������
	 * @param obj
	 */
	public void addVO(ValueObject obj)
	{
		addVO(obj,true);
	}
	
	/**
	 * ���obj��ֵ������,����keyGenerator�ж��Ƿ��������true����,false������
	 * @param obj
	 * @param keyGenerator
	 */
	public void addVO(ValueObject obj,boolean keyGenerator)
	{
	    addVO(obj,keyGenerator,false);
//		Map voBin = getMap(obj.getClass());
//		//�����Ҫ��������,����������ø�������ֵ������,�������ֵ�����е�����
//		if(keyGenerator)
//		{
//			Object id = getId(voBin.keySet().iterator());
//			obj.setKey(id);
//		}		
//		setVO(obj, ADD);
	}
	
	/**
	 * ���obj��ֵ������,����keyGenerator�ж��Ƿ��������true����,false������
	 * @param obj
	 * @param keyGenerator
	 */
	public void addVO(ValueObject obj,boolean keyGenerator,boolean needSynchro)
	{
		Map voBin = getMap(obj.getClass());
		//�����Ҫ��������,����������ø�������ֵ������,�������ֵ�����е�����
		if(keyGenerator)
		{
			Object id = getId(voBin.keySet().iterator());
			obj.setKey(id);
			setVO(obj, ADD);
		}		
		else if(needSynchro)
		{
		    if(!synchroAdd(obj))
		        setVO(obj, ADD);
		}
		else
		    setVO(obj, ADD);
		
	}
	
	protected boolean synchroAdd(ValueObject obj)
	{
	    return this.synchroAdd(obj,StatusConst.DELETE);
	}
	
	/**
	 * ɾ��ҵ����Ϣʱ������װ��Ϣ��ֵ�����ŵ��ö����ɢ����
	 * @param obj
	 */
	public void deleteVO(ValueObject obj)
	{
		if (!synchroDelete(obj))
			setVO(obj, DELETE);
	}
	/**
	 * ��ȡ�������½�������clazz��Ӧ��ֵ����
	 * @param clazz Ҫ��ȡֵ�����Ӧ��Class�������磺xxVO.class
	 * @return ����ֵ����ĵ�����
	 */
	public Iterator getNewVO(Class clazz)
	{
		return getVO(clazz, new int[] { ADD });
	}
	/**
	 * ��ȡ�����и��µ�����clazz��Ӧ��ֵ����
	 * @param clazz :Ҫ��ȡֵ�����Ӧ��Class�������磺xxVO.class
	 * @return ����ֵ����ĵ�����
	 */
	public Iterator getUpdateVO(Class clazz)
	{
		return getVO(clazz, new int[] { UPDATE });
	}
	
	/**
	 * ��ȡclazz���������޸�����״̬�Ķ���
	 * @param clazz
	 * @return Iterator
	 */
	public Iterator getNew2ndUpdateVO(Class clazz)
	{
		return getVO(clazz, new int[] { UPDATE, ADD });
	}
	
	/**
	 * ��ȡclazz���������޸ģ���������״̬�Ķ���
	 * @param clazz
	 * @return Iterator
	 */
	public Iterator getNew2ndUpdate2ndCacheVO(Class clazz)
	{
		return getVO(clazz, new int[] { UPDATE, ADD, CACHE });
	}
	     
	/**
	 * ��ȡclazz���������޸ģ�ɾ������״̬�Ķ���
	 * @param clazz
	 * @return Iterator
	 */
	public Iterator getNew2ndUpdate2ndDeleteVO(Class clazz)
	{
		return getVO(clazz, new int[] { UPDATE, ADD, DELETE });
	}
	/**
	 * ��ȡ������ɾ��������clazz��Ӧ��ֵ����
	 * @param clazz :Ҫ��ȡֵ�����Ӧ��Class�������磺xxVO.class
	 * @return ����ֵ����ĵ�����
	 */
	public Iterator getDeleteVO(Class clazz)
	{
		return getVO(clazz, new int[] { DELETE });
	}	
	
	/**
	 * ����delete��add�ĳ�ͻ
	 * @param obj
	 */
	protected boolean synchroDelete(ValueObject obj)
	{
		return synchroDelete(obj,ADD);
	}
	/**
	 * ����update��add�ĳ�ͻ
	 * @param obj
	 */
	protected boolean synchroUpdate(ValueObject obj)
	{
		return synchroUpdate(obj,ADD);
	}	
	
	/**
	 * �Ƴ����������л��������Ϊclazz��״̬Ϊstatus��ֵ����
	 * @param clazz
	 */
	public void deleteAll(Class clazz)
	{
	    Map bin = getMap(clazz,false);
	    
	    if(bin == null)
	        return;
	    
	    Iterator keys = bin.values().iterator();
//	    for(int i = 0; i < keys.size(); i ++)
//	    {
//	        keys.removeAll(keys);
//	        //DecoratorVO dec = (DecoratorVO)keys.get(i);	        
//	    }
	    List removeVO = new ArrayList();
	    while(keys.hasNext())
	    {
	        Object dec = keys.next();	        
	        //System.out.println("first:"+((DecoratorVO)dec).status);
	        if(((DecoratorVO)dec).status == StatusConst.ADD)
	            removeVO.add(((DecoratorVO)dec).vo);
	        else
	            ((DecoratorVO)dec).status = StatusConst.DELETE;
	        //System.out.println(dec);
	        //deleteVO((ValueObject)((DecoratorVO)dec).vo);	        
	        
	    }
	    
	    for(int i = 0; i < removeVO.size(); i ++)
	    {
	        ValueObject vo = (ValueObject)removeVO.get(i);
	        bin.remove(vo.getKey());
	    }
	    
	    
	}
}
