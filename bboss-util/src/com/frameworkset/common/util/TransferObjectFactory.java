/*****************************************************************************
 *                                                                           *
 *  This file is part of the tna framework distribution.                     *
 *  Documentation and updates may be get from  biaoping.yin the author of    *
 *  this framework							     							 *
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
 *                                                                           *
 *****************************************************************************/

package com.frameworkset.common.util;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Map;

import com.frameworkset.util.EditorInf;
/**
 * ʵ�ֶ���֮������ֵ�ĸ��ƹ��ܣ�map�����֮������ֵ�Ŀ�����hashTable/properties�����֮������ֵ�Ŀ���
 * @author biaoping.yin
 * @version 1.0
 */
public class TransferObjectFactory
{

	/**
	* Create a Transfer Object for the given object. The
	* given object must be an EJB Implementation and have
	* a superclass that acts as the class for the entity's
	* Transfer Object. Only the fields defined in this
	* superclass are copied in to the Transfer Object.
	*/
	public static Serializable createTransferObject(
		Object ejb,
		String whichTOType,
		String completeTOType)
	{

		return com.frameworkset.util.TransferObjectFactory.createTransferObject(ejb, whichTOType, completeTOType);
		
	}
	/**
	 * added by biaoping.yin 2005.8.13
	 * ��map�а���������ֵ���Ƶ�������,��Ӧ���Ե����ƺ����ͱ���һ��
	 * @param completeVO ������ֵ��map����
	 * @param whichToVO �ն���
	 * @return Object
	 */
	public static Object createTransferObject(
		Map completeVO,
		Object whichToVO)
	{
		return com.frameworkset.util.TransferObjectFactory.createTransferObject(
				completeVO,
				whichToVO);
	}
	public final static EditorInf getParamEditor(Method writeMethod)
	{
		return com.frameworkset.util.TransferObjectFactory. getParamEditor(writeMethod);
	}
	/**
	 * added by biaoping.yin 2005.8.13
	 * ��Hashtable�а���������ֵ���Ƶ�������,��Ӧ���Ե����ƺ����ͱ���һ��
	 * @param completeVO ������ֵ��Hashtable����
	 * @param whichToVO �ն���
	 * @return Object
	 */
	public static Object createTransferObject(
		Hashtable completeVO,
		Object whichToVO)
	{
		return com.frameworkset.util.TransferObjectFactory.createTransferObject(completeVO, whichToVO);
	}
	/**
	 * added by biaoping.yin 2004.5.20
	 * ��һ���������Ը��Ƶ���һ��������,��Ӧ���Ե����ƺ����ͱ���һ��
	 * @param completeVO ������ֵ�Ķ���
	 * @param whichToVO �ն���
	 * @return Object
	 */
	public static Object createTransferObject(
		Object completeVO,
		Object whichToVO)
	{
		return com.frameworkset.util.TransferObjectFactory.createTransferObject(completeVO, whichToVO);
	}
	
}
