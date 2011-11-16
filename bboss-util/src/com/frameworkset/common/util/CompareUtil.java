/*****************************************************************************
 *                                                                           *
 *  This file is part of the tna framework distribution.                     *
 *  Documentation and updates may be get from  biaoping.yin the author of    *
 *  this framework							     *
 *                                                                           *
 *  Sun Public License Notice:                                               *
 *                                                                           *
 *  The contents of this file are subject to the Sun Public License Version  *
 *  1.0 (the "License"); you may not use this file except in compliance with *
 *  the License. A copy of the License is available at http://www.sun.com    * 
 *                                                                           *
 *  The Original Code is tag. The Initial Developer of the Original          *    
 *  Code is biaoping yin. Portions created by biaoping yin are Copyright     *
 *  (C) 2000.  All Rights Reserved.                                          *
 *                                                                           *
 *  GNU Public License Notice:                                               *
 *                                                                           *
 *  Alternatively, the contents of this file may be used under the terms of  *
 *  the GNU Lesser General Public License (the "LGPL"), in which case the    *
 *  provisions of LGPL are applicable instead of those above. If you wish to *
 *  allow use of your version of this file only under the  terms of the LGPL *
 *  and not to allow others to use your version of this file under the SPL,  *
 *  indicate your decision by deleting the provisions above and replace      *
 *  them with the notice and other provisions required by the LGPL.  If you  *
 *  do not delete the provisions above, a recipient may use your version of  *
 *  this file under either the SPL or the LGPL.                              *
 *                                                                           *
 *  biaoping.yin (yin-bp@163.com)                                            *
 *  Author of Learning Java 						     					 *
 *                                                                           *
 *****************************************************************************/

package com.frameworkset.common.util;

import java.util.Date;

/**
 * ʵ�ָ����������ݵıȽϲ�����ʵ�ָ��ֶ���������ֵ�ıȽϲ��� 
 * @author biaoping.yin
 * @version 1.0
 */
public final class CompareUtil {
	
	
	
	
	/**	 
	 * Description: �Ƚ���������Ĵ�С
	 * @param left ��һ������
	 * @param right �ڶ�������
	 * true������
	 * false:����
	 * @return int 0 -��ʾ�����������
	 * 				-1-��ʾleft��rightС
	 * 				1 -��ʾleft��right��
	 */
	public static int compareValue(Object left, Object right)
	{				
		return compareValue(left, right,true);
	}
	
	/**	 
	 * Description: �Ƚ���������Ĵ�С
	 * @param left ��һ������
	 * @param right �ڶ�������
	 * @param desc ȷ���Ƚ��߼�˳��
	 * 				true������
	 * 				false:����
	 * @return int 0 -��ʾ�����������
	 * 				-1-��ʾleft��rightС
	 * 				1 -��ʾleft��right��
	 */
	public static int compareValue(Object left, Object right,boolean desc) 
	{    	
		
		if(left == null && right == null)
			return 0;
		if(left == null && right != null)
			return lessThan(desc);
		if(left != null && right == null)
			return moreThan(desc);
		if(int.class.isInstance(left) || Integer.class.isInstance(left))
		{
			return compareInt(((Integer)left).intValue(),((Integer)right).intValue(),desc);
		}
	
		if(short.class.isInstance(left) || Short.class.isInstance(left))
		{
			return compareShort(((Short)left).shortValue(),((Short)right).shortValue(),desc);
		}
	
		if(char.class.isInstance(left) || Character.class.isInstance(left))
		{
			return compareChar(((Character)left).charValue(),((Character)right).charValue(),desc);
		}
		if(long.class.isInstance(left) || Long.class.isInstance(left))
		{
			return compareLong(((Long)left).longValue(),((Long)right).longValue(),desc);
		}
	
		if(double.class.isInstance(left) || Double.class.isInstance(left))
		{
			return compareDouble(((Double)left).doubleValue(),((Double)right).doubleValue(),desc);
		}		
	
		if(String.class.isInstance(left))
		{
			return compareString((String)left,(String)right,desc);
		}
	
		if(boolean.class.isInstance(left) || Boolean.class.isInstance(left))
		{
			return compareBoolean(((Boolean)left).booleanValue(),((Boolean)right).booleanValue(),desc);
		}	
	
		if(Date.class.isInstance(left))
		{
			return compareDate(((Date)left),((Date)right),desc);
		}
	
		if(float.class.isInstance(left) || Float.class.isInstance(left))
		{
			return compareFloat(((Float)left).floatValue(),((Float)right).floatValue(),desc);
		}    	
		return 0;
	}
	
	/** 
	 * Description:�Ƚ��������������Ĵ�С
	 * @param left
	 * @param right
	 * @param desc ����˳��true����false����
	 * @return
	 * int
	 */
	public static int compareBoolean(boolean left,boolean right,boolean desc)
	{
		if(left && right)
			return 0;
		else if(left && !right)
			return moreThan(desc);
		else if(!left && right)
			return lessThan(desc);
		else
			return 0;		
	}

	/**
	 * �Ƚ������ַ����Ĵ�С
	 * @param left
	 * @param right
	 * @param desc ����˳��true����false����
	 * @return int
	 */
	public static int compareString(String left, String right,boolean desc) 
	{
		int ret = left.compareTo(right);
		if(ret > 0)
			return moreThan(desc);
		else if(ret < 0)
			return lessThan(desc);
		else
			return 0;
	}

	/**
	 * �Ƚ����������Ĵ�С
	 * @param left
	 * @param right
	 * @return int
	 */
	public static int compareInt(int left, int right,boolean desc) 
	{
		if(left < right)
			return lessThan(desc);
		else if(left == right)
			return 0;
		else
			return moreThan(desc);     	
	}

	/**
	 * �Ƚ��������ڵĴ�С
	 * @param left
	 * @param right
	 * @param desc ����˳��true����false���� 
	 * @return int
	 */
	public static int compareDate(Date left, Date right,boolean desc) 
	{
		int ret = left.compareTo(right);    	
		if(ret > 0)
			return moreThan(desc);
		else if(ret < 0)
			return lessThan(desc);
		else
			return ret;
	}

	/**
	 * �Ƚ������������Ĵ�С
	 * @param left
	 * @param right
	 * @param desc ����˳��true����false����
	 * @return int
	 */
	public static int compareLong(long left, long right,boolean desc) 
	{
		if(left < right)
			return lessThan(desc);
		else if(left > right)
			return moreThan(desc);
		else
			return 0;
	}

	/**
	 * �Ƚ������������Ĵ�С
	 * @param left
	 * @param right
	 * @param desc ����˳��true����false����
	 * @return int
	 */
	public static int compareShort(short left, short right,boolean desc) 
	{
		if(left < right)
			return lessThan(desc);
		else if(left > right)
			return moreThan(desc);
		else
			return 0;
	}

	/**
	 * �Ƚ�����˫�������Ĵ�С
	 * @param left
	 * @param right
	 * @param desc ����˳��true����false����
	 * @return int
	 */
	public static int compareDouble(double left, double right,boolean desc) 
	{
		if(left < right)
		   return lessThan(desc);
		else if(left > right)
		   return moreThan(desc);
		else
		   return 0;
	}

	/**
	 * ��������������Ĵ�С
	 * @param left
	 * @param right
	 * @param desc ����˳��true����false����
	 * @return int
	 */
	public static int compareFloat(float left, float right,boolean desc) 
	{
		if(left < right)
			return lessThan(desc);
		else if(left > right)
			return moreThan(desc);
		else
			return 0;
	}

	/**
	 * �Ƚ������ַ��Ĵ�С
	 * @param left
	 * @param right 
	 * @param desc ����˳��true����false����
	 * @return int
	 */
	public static int compareChar(char left, char right,boolean desc) 
	{    	
		if(left < right)
			return lessThan(desc);
		else if(left > right)
			return moreThan(desc);
		else
			return 0;
	}

	

	/**
	 * Description:����desc��ֵ������ȽϽ��ΪС��ʱ�ķ��ؽ��
	 * @param desc ����˳��true����false����
	 * @return int
	 */
	private static int lessThan(boolean desc)
	{
		return desc?1:-1; 
	}
   
	/**
	 * Description:����desc��ֵ������ȽϽ��Ϊ����ʱ�ķ��ؽ��
     * @param desc ����˳��true����false����
	 * @return
	 * int
	 */
	private static int moreThan(boolean desc)
	{
		return desc?-1:1;
	} 
	
	/** 
	 * Description:�Ƚ϶���obj���ֶ�field��ֵ��compareValue�Ĵ�С
	 * @param obj
	 * @param field
	 * @param compareValue
	 * @return
	 * int
	 */
	public static int compareField(Object obj,String field,Object compareValue)
	{				
		return compareField(obj,field,compareValue,true);				
	}
	
	/** 
	 * Description:�Ƚ϶���obj���ֶ�field��ֵ��compareValue�Ĵ�С
	 * @param obj
	 * @param field
	 * @param compareValue
	 * @param desc ����˳��true����false����
	 * @return
	 * int
	 */
	public static int compareField(Object obj,String field,Object compareValue,boolean desc)
	{
		Object value = ValueObjectUtil.getValue(obj,field.trim());		
		return compareValue(value,compareValue,desc);				
	}
	
	/**
	 * Description:�Ƚ϶���obj���ֶ�field�����obj1���ֶ�field1�Ĵ�С
	 * @param obj
	 * @param field
	 * @param obj1
	 * @param field1
	 * @param desc ����˳��true����false����
	 * @return
	 * int
	 */
	public static int compareField(Object obj,String field,Object obj1,String field1,boolean desc)
	{
		Object value = ValueObjectUtil.getValue(obj,field.trim());		
		Object value1 = ValueObjectUtil.getValue(obj1,field1.trim());
		return compareValue(value,value1,desc);				
	}
	
	/**
	 * Description:�Ƚ϶���obj���ֶ�field�����obj1���ֶ�field1�Ĵ�С
	 * @param obj
	 * @param field
	 * @param obj1
	 * @param field1	
	 * @return
	 * int
	 */
	public static int compareField(Object obj,String field,Object obj1,String field1)
	{
		
		return compareField(obj,field,obj1,field1,true);				
	}
}
