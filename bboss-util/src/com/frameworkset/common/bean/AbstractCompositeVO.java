/*
 * Created on 2004-5-29
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.frameworkset.common.bean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.frameworkset.util.CompareUtil;




/**
 * @author biaoping.yin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class AbstractCompositeVO implements Serializable
{
	private static Logger log = Logger.getLogger(AbstractCompositeVO.class);
	/**
	 * binMap��������һ��HashMap�������У�
	 * ÿ��HashMap�����keyΪhashmap�������ɶ����class��
	 * �����е�Map����ֻ����һ���ض����͵�ֵ����ɢ�С�
	 */
	private Map binMap = new HashMap();

	/**
	 * ��ȡ���clazz�Ķ���ʵ����Map����
	 * @param clazz Ҫ��ȡֵ�����Ӧ��Class�������磺xxVO.class
	 * @return Map
	 */

	protected Map getMap(Class clazz)
	{
		return getMap(clazz,true);
	}

	/**
	 * ��ȡ��Ӧclass��binMap
	 * @param clazz
	 * @param createBin ���ƻ�ȡ��binΪnullʱ���Ƿ�Ϊ��Ӧ��class����һ���µ�bin
	 * @return Map
	 */
	protected Map getMap(Class clazz,boolean createBin)
	{
		Map voBin = (Map)binMap.get(clazz);
		if(voBin == null)
		{
			if(createBin)
			{
				voBin = new HashMap();
				binMap.put(clazz,voBin);
			}
			else
				return null;
		}
		return voBin;
	}
	/**
	 * Ϊ�ض����͵�ֵ�������һ��δʹ�õ�keyֵ
	 * Description:
	 * @param iterator
	 * @return
	 * long
	 */
	protected Object getId(Iterator iterator)
	{
		long maxId = 0;
		while(iterator.hasNext())
		{
			Object obj = iterator.next();
			long temp = ((Long)obj).longValue();
						maxId = Math.max(
								temp,
								maxId);

		}
		maxId ++;
		return new Long(maxId);
	}

	/**
	 * ��ȡֵ�����keyֵ
	 * Description:
	 * @param dcVO
	 * @return Object
	 * @throws IllegalArgumentException
	 *
	 */
	private Object getKey(DecoratorVO dcVO)
		throws IllegalArgumentException
	{
		if(dcVO == null)
			throw new IllegalArgumentException("DecoratorVO dcVO Ϊ��!");
		if(dcVO.vo == null)
			throw new IllegalArgumentException("ֵ����Ϊ��!");
		return ((ValueObject)dcVO.vo).getKey();
	}

	/**
	 * ���ض����͵�ֵ�����������
	 * Description:
	 * @param list
	 * void
	 */
	private void sort(List list)
	{
	    Collections.sort(list,new Comparator()
		  {
				public int compare(Object o1, Object o2)
				{
					DecoratorVO dcO1 = (DecoratorVO)o1;
					DecoratorVO dcO2 = (DecoratorVO)o2;
					try
					{
					    CompareUtil cUtil = new CompareUtil();
						return cUtil.compareValue(getKey(dcO1),getKey(dcO2));
					}
					catch(Exception e)
					{
						//e.printStackTrace();
						log.info(e.getMessage());
						return 0;
					}

				}
			  });
	}
	/**
	 * ��ȡ����Ϊclazz������filter�е�������ֵ����ĵ�����
	 * �����ÿ��ֵ�����key��������
	 * @param clazz
	 * @param operations
	 * @return Iterator
	 */
	protected Iterator getVO(Class clazz,int[] operations)
	{
		List list = new ArrayList(getMap(clazz).values());
		sort(list);
		return new UtilIterator(list.iterator(),operations);
	}

	/**
	 * ��ֵ����obj�Ͳ�����Ϣ��װ��DecoratorVO������뵽obj��Ӧ��ɢ����
	 * @param obj
	 * @param operation
	 */
	protected void setVO(ValueObject obj,int operation)
	{
	    //System.out.println("setvo obj.getKey():" + obj.getKey());
		getMap(obj.getClass()).put(obj.getKey(),new DecoratorVO(obj, operation));
	}

	/**
	 * ��ȡ������ϢΪclazz,�ؼ���Ϊkey�Ķ���
	 * @param clazz
	 * @param key
	 * @return ValueObject
	 */
	public ValueObject getSingleVO(Class clazz,long key)
	{
		Map map = this.getMap(clazz);
		if(map == null)
			return null;
		DecoratorVO dec = (DecoratorVO)map.get(new Long(key));
		if(dec != null)
			return (ValueObject)dec.vo;
		return null;
	}


	/**
	 * ����delete��status�ĳ�ͻ
	 * @param obj
	 */
	protected boolean synchroDelete(ValueObject obj,int add)
	{
		if (obj == null)
			return false;
		Map newBin = getMap(obj.getClass());
		Object key = obj.getKey();
		if (newBin.containsKey(key))
		{
			DecoratorVO dec = (DecoratorVO) newBin.get(key);
			if (dec.status == add)
			{
				newBin.remove(key);
				return true;
			}
		}
		return false;
	}

	/**
	 * ����update��status�ĳ�ͻ
	 * @param obj
	 */
	protected boolean synchroAdd(ValueObject obj,int delete)
	{
		if (obj == null)
			return false;
		Map newBin = getMap(obj.getClass());
		Object key = obj.getKey();
		if (newBin.containsKey(key))
		{
			DecoratorVO dec = (DecoratorVO) newBin.get(key);
			if (dec.status == delete)
			{
				dec.status = StatusConst.CACHE;
				return true;
			}
		}
		return false;
	}

	/**
	 * ����update��status�ĳ�ͻ
	 * @param obj
	 */
	protected boolean synchroUpdate(ValueObject obj,int add)
	{
		if (obj == null)
			return false;
		Map newBin = getMap(obj.getClass());
		Object key = obj.getKey();
		if (newBin.containsKey(key))
		{
			DecoratorVO dec = (DecoratorVO) newBin.get(key);
			if (dec.status == add)
			{
				dec.vo = obj;
				return true;
			}
		}
		return false;
	}

	/**
	 * ��ȡ���з���operations������clazz����
	 * @param clazz :Ҫ��ȡֵ�����Ӧ��Class�������磺xxVO.class
	 * @return ����ֵ����ĵ�����
	 */
	public Iterator getVOByStatus(Class clazz,int[] status)
	{
		return getVO(clazz, status);
	}



	/**
	 * �жϸ�����ֵ�����Ƿ����
	 * @param obj
	 */
	public boolean contain(ValueObject obj)
	{
		if (obj == null)
			return false;
		Map newBin = getMap(obj.getClass(),false);
		if(newBin == null)
			return false;
		Object key = obj.getKey();
		return newBin.containsKey(key);
		//return false;
	}

	/**
	 * �жϸ�����ֵ������״̬Ϊstatus�Ƿ����
	 * Description:
	 * @param obj
	 * @param status
	 * @return
	 * boolean
	 */
	public boolean contain(ValueObject obj,int status)
	{
		if (obj == null)
			return false;
		Map newBin = getMap(obj.getClass(),false);
		if(newBin == null)
			return false;
		Object key = obj.getKey();
		DecoratorVO dec = (DecoratorVO) newBin.get(key);

		if (dec != null && dec.status == status)
		{
			return true;
		}
		return false;
	}

	/**
	 * Description:�ж��Ƿ��������Ϊclazz���Ұ�������field,
	 * ����ֵΪfieldValue��ֵ����������ڷ���true�����򷵻�false
	 * @param clazz
	 * @param field
	 * @param fieldValue
	 * @return
	 * boolean
	 */

	public boolean contain(Class clazz,String field,Object fieldValue)
	{
		return contain(clazz,field,fieldValue,null);
	}

	/**
	 * Description:�ж��Ƿ��������Ϊclazz���Ұ�������field,
	 * ����ֵΪfieldValue�Լ�״̬������status�е�ֵ����������ڷ���true�����򷵻�false
	 * @param clazz
	 * @param field
	 * @param fieldValue
	 * @param status
	 * @return
	 * boolean
	 */
	public boolean contain(Class clazz,String field,Object fieldValue,int[] status)
	{
		Map newBin = getMap(clazz,false);
		if(newBin == null)
			return false;
		Iterator iterator = new MapIterator(newBin,field,fieldValue,status);
		if(iterator.hasNext())
		{
			return true;
		}
		return false;
	}
	/**
	 * Description:�ж��Ƿ��������Ϊclazz���Ұ�������field,
	 * ����ֵΪfieldValue�Լ�״̬Ϊstatus��ֵ����������ڷ���true�����򷵻�false
	 * @param clazz
	 * @param field
	 * @param fieldValue
	 * @param status
	 * @return
	 * boolean
	 */
	public boolean contain(Class clazz,String field,Object fieldValue,int status)
	{
		return contain(clazz,field,fieldValue,new int[] {status});
	}

	/**
	 * Description:��ȡ����Ϊclazz����������field������ֵΪfieldValue��ֵ���������
	 * @param clazz ֵ���������
	 * @param field ��Ϊ�������ֶ�
	 * @param fieldValue ��Ϊ�������ֶε�ֵ
	 * @return Iterator ����������clazz����ĵ�����
	 */
	public Iterator getVoBy(Class clazz,String field,Object fieldValue)
	{
		return getVoBy(clazz,field,fieldValue,null);
	}

	/**
	 * Description:��ȡ����Ϊclazz����������field��
	 * ����ֵΪfieldValue����״̬Ϊ����status��֮һ��ֵ���������
	 * @param clazz ֵ���������
	 * @param field ��Ϊ�������ֶ�
	 * @param fieldValue ��Ϊ�������ֶε�ֵ
	 * @param status ֵ����״̬����
	 * @return Iterator ����������clazz����ĵ�����
	 */
	public Iterator getVoBy(Class clazz,String field,Object fieldValue,int[] status)
	{
		Map newBin = getMap(clazz,false);
		if(newBin == null)
			return null;
		return new MapIterator(newBin,field,fieldValue,status);

	}

	/**
	 * Description:��ȡ����Ϊclazz����������field��
	 * ����ֵΪfieldValue����״̬Ϊstatus��ֵ���������
	 * @param clazz ֵ���������
	 * @param field ��Ϊ�������ֶ�
	 * @param fieldValue ��Ϊ�������ֶε�ֵ
	 * @param status ֵ����״̬
	 * @return Iterator ����������clazz����ĵ�����
	 */
	public Iterator getVoBy(Class clazz,String field,Object fieldValue,int status)
	{
		return getVoBy(clazz,field,fieldValue,new int[] {status});
	}



	/**
	 * ��ȡ������ϢΪclazz,�ؼ���Ϊkey�Ķ���
	 * @param clazz
	 * @param key
	 * @return ValueObject
	 */
	public ValueObject getSingleVO(Class clazz,Object key,int status)
	{
		Map map = this.getMap(clazz);
		if(map == null)
			return null;
		DecoratorVO dec = (DecoratorVO)map.get(key);
		if(dec != null && dec.status == status)
			return (ValueObject)dec.vo;
		return null;
	}

	private int matchStatus(DecoratorVO decorator, int[] filter)
	{
		int status = decorator.status;
		for(int i = 0; i < filter.length; i ++)
		{
			if(status == filter[i])
			{
				return i;
			}
		}
		return -1;
	}

	/**
	 * �Ƴ����������л��������Ϊclazz��ֵ����
	 * @param clazz
	 */
	public void removeVO(Class clazz)
	{
	    Map bin = this.getMap(clazz);
	    if(bin == null)
	        return;
	    bin.clear();

	}

	/**
	 * �Ƴ����������л��������Ϊclazz��״̬Ϊstatus��ֵ����
	 * @param clazz
	 */
	public void removeVO(Class clazz,int[] status)
	{
	    Map bin = this.getMap(clazz);

	    if(bin == null)
	        return;
	    bin.clear();
	}

	/**
	 * ��clazz���͵����ж����״̬�޸�Ϊstatus
	 * @param clazz
	 * @param status
	 */
	protected void updateStatus(Class clazz,int status)
	{
	    Map bin = this.getMap(clazz);

	    if(bin == null)
	        return;
	    Iterator keys = bin.keySet().iterator();
	    while(keys.hasNext())
	    {
	        DecoratorVO dec = (DecoratorVO)bin.get(keys.next());
	        dec.status = status;
	    }

	}


	/**
	 * inner class DecoratorVO implement decorator pattern to decorator an object with a status
	 * @author biaoping.yin
	 *
	 * To change the template for this generated type comment go to
	 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
	 */
	protected static class DecoratorVO implements Serializable
	{
		public DecoratorVO(Object vo, int status)
		{
			this.vo = vo;
			this.status = status;
		}

		public boolean equals(Object obj)
		{
			if(obj != null)
				return vo.equals(obj);
			return false;
		}

		public String toString()
		{
			return "[status = " + status + "]"  + "[vo = " + vo + "]";
		}
		public Object vo;
		public int status;
	}

	/**
	 *
	 * @author biaoping.yin
	 * created on 2004-4-19
	 * version 1.0
	 */
	protected class UtilIterator implements Iterator, Serializable
	{
		Iterator allData;
		DecoratorVO decorator;
		int[] filter;
		public UtilIterator(Iterator allData, int[] filter)
		{
			this.allData = allData;
			this.filter = filter;
			decorator = null;
		}
		/* (non-Javadoc)
		 * @see java.util.Iterator#hasNext()
		 */
		public boolean hasNext()
		{
			boolean found = false;
			while (allData.hasNext() && !found)
			{
				decorator = (DecoratorVO) allData.next();

				//int match = Arrays.binarySearch(filter, decorator.status);
				if(filter == null)
				{
					found = true;
				}
				else
				{
					int match = matchStatus(decorator, filter);
					found = (match != -1);
				}
			}
			if (!found)
				decorator = null;
			return found;
		}
		/* (non-Javadoc)
		 * @see java.util.Iterator#next()
		 */
		public Object next()
		{
			if (decorator != null)
				return decorator.vo;
			log.info("no such element!!!");
			return null;
		}
		/* (non-Javadoc)
		 * @see java.util.Iterator#remove()
		 */
		public void remove()
		{
			throw new UnsupportedOperationException("remove(),δ֧�ֵĲ���");
		}
	}

	/**
	 *
	 * @author biaoping.yin
	 * created on 2004-4-19
	 * version 1.0
	 */
	protected class MapIterator implements Iterator, Serializable
	{
		Iterator allData;
		DecoratorVO decorator;
		int[] filter;
		String field;
		Object fieldValue;
		public MapIterator(Map bin,String field,Object fieldValue)
		{
			this(bin,field,fieldValue,null);
		}
		public MapIterator(Map bin,String field,Object fieldValue,int[] filter)
		{
		    //����list�Ը��ֶ����������
		    List list = new ArrayList(bin.values());
			sort(list);
			this.allData = list.iterator();
			this.filter = filter;
			this.field = field;
			this.fieldValue = fieldValue;
			decorator = null;
		}


		/* (non-Javadoc)
		 * @see java.util.Iterator#hasNext()
		 */
		public boolean hasNext()
		{

			boolean found = false;
			while(allData.hasNext() && !found )
			{
				decorator = (DecoratorVO) allData.next();
				found = found();
			}

			if (!found)
				decorator = null;
			return found;
		}

		private boolean found()
		{
			if(filter == null)
				return fieldMatch();
			//return (Arrays.binarySearch(filter, decorator.status) != -1) && fieldMatch();
			return (matchStatus(decorator,filter) != -1) && fieldMatch();
		}

		private boolean fieldMatch()
		{
			CompareUtil util = new CompareUtil();
			if(util.compareField(decorator.vo,field,fieldValue) == 0)
				return true;
			return false;

		}
		/* (non-Javadoc)
		 * @see java.util.Iterator#next()
		 */
		public Object next()
		{
			if (decorator != null)
				return decorator.vo;
			log.info("no such element!!!");
			return null;
		}
		/* (non-Javadoc)
		 * @see java.util.Iterator#remove()
		 */
		public void remove()
		{
			throw new UnsupportedOperationException("remove(),δ֧�ֵĲ���");
		}
	}
}
