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

package com.frameworkset.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.frameworkset.util.BigFile;
import org.frameworkset.util.ClassUtil;
import org.frameworkset.util.ClassUtil.PropertieDescription;
import org.frameworkset.util.MethodParameter;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import bsh.Interpreter;

import com.frameworkset.common.poolman.NestedSQLException;
import com.frameworkset.spi.assemble.BeanInstanceException;
import com.frameworkset.spi.assemble.CurrentlyInCreationException;

/**
 * @author biaoping.yin ������ʹ��java.lang.reflection���ṩ�Ĺ��ܣ��ṩ���¹��ߣ� �Ӷ����л�ȡ��Ӧ���Ե�ֵ
 */
public class ValueObjectUtil {

	/**
	 * �����������ͣ�bboss��λ���£�
	 * java����Ļ�����������
	 * BigFile �󸽼�����
	 * ö������
	 * ��������
	 * �ַ�������
	 * ��Щ������ҪӦ����mvc���������������İ󶨹�����
	 */
	public static final Class[] baseTypes = {String.class,
		int.class ,Integer.class,
		long.class,Long.class,
		java.sql.Timestamp.class,java.sql.Date.class,java.util.Date.class,
		boolean.class ,Boolean.class,
		BigFile.class,
		float.class ,Float.class,
		short.class ,Short.class,
		double.class,Double.class,
		char.class ,Character.class,
		
		byte.class ,Byte.class,
		BigDecimal.class};
	/**
	 * �������л�����ʶ�������������   
	 */
	public static final Class[] basePrimaryTypes = {String.class,
		int.class ,
		long.class,		
		boolean.class ,
//		BigFile.class,
		float.class ,
		short.class ,
		double.class,
		char.class ,
		byte.class ,
		Class.class
		};
	private static final Logger log = Logger.getLogger(ValueObjectUtil.class);

	private static final SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	/**
	 * �������е����ڸ�ʽ
	 */
	private static final Map<String,SimpleDateFormat> dataformats = new HashMap<String,SimpleDateFormat>();

//	/**
//	 * Description:��ȡ����obj��property����ֵ
//	 * 
//	 * @param obj
//	 * @param property
//	 * @return Object
//	 */
//	public static Object getValue(Object obj, String property) {
//		return getValue(obj, property);
//	}

	/**
	 * Description:��ȡ����obj��property����ֵ,paramsΪ��������
	 * 
	 * @param obj
	 * @param property
	 * @param params
	 *            ��ȡ���Է���ֵ�Ĳ���
	 * @return Object
	 */
	public static Object getValue(Object obj, String property) {
		if (obj == null || property == null || property.trim().length() == 0)
			return null;

		try {

			PropertieDescription pd = ClassUtil.getPropertyDescriptor(obj.getClass(), property);
			return pd.getValue(obj);
				
			
		} catch (Exception e) {
			log.debug("û��Ϊ����[" + property + "]����get���߷��ز���ֵ��is����.");
		}
		
		return null;
	}
	
	
	public static Object getValue(Object obj, String property,Object[] params) {
		if (obj == null || property == null || property.trim().length() == 0)
			return null;

		try {
			if(params == null || params.length == 0)
			{
				PropertieDescription pd = ClassUtil.getPropertyDescriptor(obj.getClass(), property);
				return pd.getValue(obj);
				
			}
//			else
//			{
//				Method method = getMethodByPropertyName(obj, property);
//				return getValueByMethod(obj, method, params);
//			}
		} catch (Exception e) {
			log.debug("û��Ϊ����[" + property + "]����get���߷��ز���ֵ��is����.");
		}
		// Object ret = getValueByMethodName(obj, getMethodName(property),
		// params);
		// if (ret == null)
		// {
		// // log.debug("Try to get Boolean attribute for property[" + property
		// // + "].");
		// ret = getValueByMethodName(obj, getBooleanMethodName(property),
		// params);
		// if (ret != null)
		// log.debug("Get Boolean property[" + property + "=" + ret + "].");
		// }

		// return ret;
		return null;
	}

	/**
	 * Description:���ݷ������ƻ�ȡ�� �ڶ���obj�ϵ��øķ������ҷ��ص��õķ���ֵ
	 * 
	 * @param obj
	 * @param methodName
	 *            ��������
	 * @param params
	 *            �����Ĳ���
	 * @return Object
	 * @deprecated
	 */
	public static Object getValueByMethodName(Object obj, String methodName,
			Object[] params) {
		if (obj == null || methodName == null
				|| methodName.trim().length() == 0)
			return null;
		return getValueByMethodName(obj, methodName, params, null);
	}

//	/*
//	 *
//	 */
//	@Deprecated
//	public static Method getMethodByPropertyName(Object obj,
//			String propertyName, Class[] paramsTtype) throws Exception {
//		String name = getMethodName(propertyName);
//		Method method = null;
//		try {
//			method = obj.getClass().getMethod(name, paramsTtype);
//		} catch (SecurityException e) {
//			name = getBooleanMethodName(propertyName);
//			method = obj.getClass().getMethod(name, paramsTtype);
//		} catch (NoSuchMethodException e) {
//			name = getBooleanMethodName(propertyName);
//			method = obj.getClass().getMethod(name, paramsTtype);
//		} catch (Exception e) {
//			name = getBooleanMethodName(propertyName);
//			method = obj.getClass().getMethod(name, paramsTtype);
//		}
//		return method;
//
//	}
	
	
	public static Method getMethodByPropertyName(Object obj,
			String propertyName) throws Exception {
//		String name = getMethodName(propertyName);
		Method method = null;
		try {
//			method = obj.getClass().getMethod(name, paramsTtype);
			PropertieDescription pd = ClassUtil.getPropertyDescriptor(obj.getClass(), propertyName);
			if(pd != null)
				method = pd.getReadMethod();
			
		} catch (SecurityException e) {
//			String name = getBooleanMethodName(propertyName);
//			method = obj.getClass().getMethod(name, null);
		}  catch (Exception e) {
//			String name = getBooleanMethodName(propertyName);
//			method = obj.getClass().getMethod(name, null);
		}
		if(method == null)
		{
			String name = getBooleanMethodName(propertyName);
//			method = obj.getClass().getMethod(name, null);
			method = ClassUtil.getDeclaredMethod(obj.getClass(),name);
			if(method == null)
				 throw new NoSuchMethodException(obj.getClass().getName() + "." + name );
		}
		return method;

	}

	/**
	 * Description:���ݷ������ƻ�ȡ�� �ڶ���obj�ϵ��øķ������ҷ��ص��õķ���ֵ
	 * 
	 * @param obj
	 * @param methodName
	 *            ��������
	 * @param params
	 *            �����Ĳ���
	 * @param paramsTtype
	 *            �����Ĳ�������
	 * @deprecated           
	 * @return Object
	 */
	public static Object getValueByMethodName(Object obj, String methodName,
			Object[] params, Class[] paramsTtype) {
		if (obj == null || methodName == null
				|| methodName.trim().length() == 0)
			return null;
		try {
			if(paramsTtype == null || paramsTtype.length == 0)
			{
				Method method = ClassUtil.getDeclaredMethod(obj.getClass(),methodName);
				if (method != null)
					return method.invoke(obj, params);
			}
			else
			{
				Method method = obj.getClass().getMethod(methodName, paramsTtype);
				if (method != null)
					return method.invoke(obj, params);
			}
		} catch (Exception e) {
			// e.printStackTrace();
			log.info("NoSuchMethodException:" + e.getMessage());
		}
		return null;

	}

	/**
	 * Description:���ݷ������ƻ�ȡ�� �ڶ���obj�ϵ��øķ������ҷ��ص��õķ���ֵ
	 * 
	 * @param obj
	 * @param methodName
	 *            ��������
	 * @param params
	 *            �����Ĳ���
	 * @param paramsTtype
	 *            �����Ĳ�������
	 * @return Object
	 */
	public static Object getValueByMethod(Object obj, Method method,
			Object[] params) {
		if (obj == null || method == null)
			return null;
		try {
			// Method method = obj.getClass().getMethod(methodName,
			// paramsTtype);
			// if (method != null)
			return method.invoke(obj, params);
		} catch (Exception e) {
			// e.printStackTrace();
			log.info("Invoker method[" + method.getName() + "] on "
					+ obj.getClass().getCanonicalName() + " failed:"
					+ e.getMessage());
		}
		return null;

	}

	/**
	 * Description:ʵ���ڶ������method��Ϊ�÷��������������params
	 * 
	 * @param obj
	 *            ����
	 * @param method
	 *            �����õķ���
	 * @param params
	 *            ��������
	 * @return Object
	 * @throws Exception
	 *             Object
	 */
	public static Object invoke(Object obj, Method method, Object[] params)
			throws Exception {
		return method.invoke(obj, params);
	}

	/**
	 * ��ȡfieldName��getter��������
	 * 
	 * @param fieldName
	 * @return String
	 */
	public static String getMethodName(String fieldName) {
		String ret = null;
		if (fieldName == null)
			return null;
		String letter = String.valueOf(fieldName.charAt(0));
		letter = letter.toUpperCase();
		ret = "get" + letter + fieldName.substring(1);
		// System.out.println("method name:" + ret);
		return ret;

	}

	public static String getBooleanMethodName(String fieldName) {
		String ret = null;
		if (fieldName == null)
			return null;
		String letter = String.valueOf(fieldName.charAt(0));
		letter = letter.toUpperCase();
		ret = "is" + letter + fieldName.substring(1);
		// System.out.println("method name:" + ret);
		return ret;

	}

	/**
	 * ��ȡfieldName��setter����
	 * 
	 * @param fieldName
	 * @return String
	 * @deprecated
	 */
	public static String getSetterMethodName(String fieldName) {
		String ret = null;
		if (fieldName == null)
			return null;
		String letter = String.valueOf(fieldName.charAt(0));
		letter = letter.toUpperCase();
		ret = "set" + letter + fieldName.substring(1);
		// System.out.println("method name:" + ret);
		return ret;

	}

	// public final static boolean isSameType(Class type, Class toType)
	// {
	// if(toType == Object.class)
	// return true;
	//		
	// else if(type == toType)
	// return true;
	// else if(toType.isAssignableFrom(type))//���toType�ǲ���type�ĸ��������type��ʵ�ֵĽӿ�
	// {
	// return true;
	// }
	// else
	// if(type.isAssignableFrom(toType))//���type�ǲ���toType�ĸ������������ƿtype��ʵ�ֵĽӿ�
	// {
	// if(type.getName().equals(toType.getName()))
	// return true;
	// }
	//		
	// else if((type == int.class && toType == Integer.class) ||
	// type == Integer.class && toType == int.class)
	// {
	// return true;
	// }
	// else if((type == short.class && toType == Short.class) ||
	// type == Short.class && toType == short.class)
	// {
	// return true;
	// }
	// else if((type == long.class && toType == Long.class) ||
	// type == Long.class && toType == long.class)
	// {
	// return true;
	// }
	// else if((type == double.class && toType == Double.class) ||
	// type == Double.class && toType == double.class)
	// {
	// return true;
	// }
	// else if((type == float.class && toType == Float.class) ||
	// type == Float.class && toType == float.class)
	// {
	// return true;
	// }
	// else if((type == char.class && toType == Character.class) ||
	// type == Character.class && toType == char.class)
	// {
	// return true;
	// }
	// return false;
	//		
	//		
	// }

	/**
	 * 
	 * @param types
	 *            ���캯���Ĳ�������
	 * @param params
	 *            �ⲿ�������ʽ��������
	 * @return
	 */
	public static boolean isSameTypes(Class[] types, Class[] params,
			Object[] paramArgs) {

		if (types.length != params.length)
			return false;
		for (int i = 0; i < params.length; i++) {

			// if(!ValueObjectUtil.isSameType(type, toType))
			if (!isSameType(params[i], types[i], paramArgs[i])) {
				return false;
			}

		}
		return true;
	}

	public final static boolean isSameType(Class type, Class toType,
			Object value) {
		if (toType == Object.class)
			return true;

		else if (type == toType)
			return true;
		else if (toType.isAssignableFrom(type))// ���toType�ǲ���type�ĸ��������type��ʵ�ֵĽӿ�
		{
			return true;
		} else if (type.isAssignableFrom(toType))// ���type�ǲ���toType�ĸ������������ƿtoType��ʵ�ֵĽӿ�
		{
			try {
				toType.cast(value);
				return true;
			} catch (Exception e) {
				return false;
			}
			// if(type.getName().equals(toType.getName()))
			// return true;
		}

		else if ((type == int.class && toType == Integer.class)
				|| type == Integer.class && toType == int.class) {
			return true;
		} else if ((type == short.class && toType == Short.class)
				|| type == Short.class && toType == short.class) {
			return true;
		} else if ((type == long.class && toType == Long.class)
				|| type == Long.class && toType == long.class) {
			return true;
		} else if ((type == double.class && toType == Double.class)
				|| type == Double.class && toType == double.class) {
			return true;
		} else if ((type == float.class && toType == Float.class)
				|| type == Float.class && toType == float.class) {
			return true;
		} else if ((type == char.class && toType == Character.class)
				|| type == Character.class && toType == char.class) {
			return true;
		}
		return false;

	}

	/**
	 * ͨ�����Ա༭����ת������ֵ
	 * 
	 * @param obj
	 * @param editor
	 * @return
	 * @throws NoSupportTypeCastException
	 * @throws NumberFormatException
	 * @throws IllegalArgumentException
	 */
	public final static Object typeCast(Object obj, EditorInf editor)
			throws NoSupportTypeCastException, NumberFormatException,
			IllegalArgumentException {

		return editor.getValueFromObject(obj);
	}

	/**
	 * ��obj���������typeת��������toType ֧���ַ�����������������ת��: ֧�ֵ�����:
	 * int,char,short,double,float,long,boolean,byte
	 * java.sql.Date,java.util.Date, Integer Long Float Short Double Character
	 * Boolean Byte
	 * 
	 * @param obj
	 * @param type
	 * @param toType
	 * @return Object
	 * @throws ClassCastException
	 *             ,NumberFormatException,IllegalArgumentException
	 */
	public final static Object typeCast(Object obj, Class toType,String dateformat)
			throws NoSupportTypeCastException, NumberFormatException,
			IllegalArgumentException {
		if (obj == null)
			return null;
		return typeCast(obj, obj.getClass(), toType,dateformat);
	}
	
	public final static Object typeCast(Object obj, Class toType)
	throws NoSupportTypeCastException, NumberFormatException,
	IllegalArgumentException{
		return typeCast( obj, toType,(String )null);
	}
	public final static Object typeCast(Object obj, Class type, Class toType)
	throws NoSupportTypeCastException, NumberFormatException,
	IllegalArgumentException
	{
		return typeCast(obj, type, toType,null);
	}
	
	public static byte[] getByteArrayFromBlob(Blob blob) throws SQLException
	{
		if(blob == null)
			return null;
		ByteArrayOutputStream out = null;
		InputStream in = null;
		
		try
		{
			out = new java.io.ByteArrayOutputStream();
			in =	blob.getBinaryStream();
			
			
			byte[] buf = new byte[1024];
			int i =0;
			
			while((i = in.read(buf)) > 0)
			{
				
//				System.out.println("i=" + i);
				
				out.write(buf,0,i);
							
			}
		
			return out.toByteArray();
		}
		catch(SQLException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			throw new NestedSQLException(e);
		}
		finally
		{
			try
			{
				if(out != null)
				{
					out.close();
					out = null;
				}
			}
			catch(Exception e)
			{
				
			}
			
			try
			{
				if(in != null)
				{
					in.close();
					in = null;
				}
			}
			catch(Exception e)
			{
				
			}
		}


	}
	
	public static String getStringFromBlob(Blob blob) throws SQLException
	{		
		if(blob == null)
			return null;
		OutputStream out = null;
		InputStream in = null;
		
		try
		{
			out = new ByteArrayOutputStream();
			in =	blob.getBinaryStream();
			
			
			byte[] buf = new byte[1024];
			int i =0;
			
			while((i = in.read(buf)) > 0)
			{
				
//				System.out.println("i=" + i);
				
				out.write(buf,0,i);
							
			}
		
			return out.toString();
		}
		catch(SQLException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			throw new NestedSQLException(e);
		}
		finally
		{
			try
			{
				if(out != null)
				{
					out.close();
					out = null;
				}
			}
			catch(Exception e)
			{
				
			}
			
			try
			{
				if(in != null)
				{
					in.close();
					in = null;
				}
			}
			catch(Exception e)
			{
				
			}
		}


	}
	
	public static byte[] getByteArrayFromClob(Clob clob) throws SQLException
	{
		if(clob == null)
			return null;
		StringWriter w = null;
		
		Reader out = null;
		try
		{
			w = new StringWriter();
			out =	clob.getCharacterStream();
			char[] buf = new char[1024];
			int i =0;
			
			while((i = out.read(buf)) > 0)
			{
				w.write(buf,0,i);
							
			}
			String temp = w.toString();
			return temp.getBytes();
		}
		catch(SQLException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			throw new NestedSQLException(e);
		}
		finally
		{
			try
			{
				if(out != null)
				{
					out.close();
					out = null;
				}
			}
			catch(Exception e)
			{
				
			}
			
			try
			{
				if(w != null)
				{
					w.close();
					w = null;
				}
			}
			catch(Exception e)
			{
				
			}
		}
		
	}
	
	public static String getStringFromClob(Clob clob) throws SQLException
	{
		if(clob == null)
			return null;
		StringWriter w = null;
		
		Reader out = null;
		try
		{
			w = new StringWriter();
			out =	clob.getCharacterStream();
			char[] buf = new char[1024];
			int i =0;
			
			while((i = out.read(buf)) > 0)
			{
				w.write(buf,0,i);
							
			}
			return w.toString();
			
		}
		catch(SQLException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			throw new NestedSQLException(e);
		}
		finally
		{
			try
			{
				if(out != null)
				{
					out.close();
					out = null;
				}
			}
			catch(Exception e)
			{
				
			}
			
			try
			{
				if(w != null)
				{
					w.close();
					w = null;
				}
			}
			catch(Exception e)
			{
				
			}
		}
		
	}
	
	public static String getByteStringFromBlob(Blob blob) throws SQLException
	{
		if(blob == null)
			return null;
		ByteArrayOutputStream out = null;
		InputStream in = null;
		
		try
		{
			out = new java.io.ByteArrayOutputStream();
			in =	blob.getBinaryStream();
			
			
			byte[] buf = new byte[1024];
			int i =0;
			
			while((i = in.read(buf)) > 0)
			{
				
//				System.out.println("i=" + i);
				
				out.write(buf,0,i);
							
			}
			BASE64Encoder en = new BASE64Encoder();
			return en.encode(out.toByteArray());
		}
		catch(SQLException e)
		{
			throw e;
		}
		catch(Exception e)
		{
			throw new NestedSQLException(e);
		}
		finally
		{
			try
			{
				if(out != null)
				{
					out.close();
					out = null;
				}
			}
			catch(Exception e)
			{
				
			}
			
			try
			{
				if(in != null)
				{
					in.close();
					in = null;
				}
			}
			catch(Exception e)
			{
				
			}
		}


	}
	/**
	 * ��obj���������typeת��������toType ֧���ַ�����������������ת��: ֧�ֵ�����:
	 * int,char,short,double,float,long,boolean,byte
	 * java.sql.Date,java.util.Date, Integer Long Float Short Double Character
	 * Boolean Byte
	 * 
	 * @param obj
	 * @param type
	 * @param toType
	 * @return Object
	 * @throws ClassCastException
	 *             ,NumberFormatException,IllegalArgumentException
	 */
	public final static Object typeCast(Object obj, Class type, Class toType,String dateformat)
			throws NoSupportTypeCastException, NumberFormatException,
			IllegalArgumentException {
		if (obj == null)
			return null;
		if (isSameType(type, toType, obj))
			return obj;

		if (type.isAssignableFrom(toType)) // type��toType�ĸ��࣬����������ת���Ĺ��̣����ת�������ǲ���ȫ��
		{
			// return shell(toType,obj);
			if (!java.util.Date.class.isAssignableFrom(type))
				return toType.cast(obj);
		}

		if (type == byte[].class && toType == String.class) {
			return new String((byte[]) obj);
		} else if (type == String.class && toType == byte[].class) {
			return ((String) obj).getBytes();
		}
		else if (java.sql.Blob.class.isAssignableFrom(type) ) {
			
			try
			{
				if( File.class.isAssignableFrom(toType))
				{
					File tmp = File.createTempFile(java.util.UUID.randomUUID().toString(),".tmp");
					getFileFromBlob((Blob)obj,tmp);
					return tmp;
				}
				else if( byte[].class.isAssignableFrom(toType))
				{
					return ValueObjectUtil.getByteArrayFromBlob((Blob)obj);
				}
				else
					return ValueObjectUtil.getStringFromBlob((Blob)obj);
			}
			catch (Exception e)
			{
				throw new IllegalArgumentException(new StringBuffer(
				"�����޷�ת��,��֧��[").append(type.getName()).append("]��[")
				.append(toType.getName()).append("]ת��").append(",value is ").append(obj).toString());
			}
			
			
		}
		else if (java.sql.Clob.class.isAssignableFrom(type) ) {
			
			try
			{
				if( File.class.isAssignableFrom(toType))
				{
					File tmp = File.createTempFile(java.util.UUID.randomUUID().toString(),".tmp");
					getFileFromClob((Clob)obj,tmp);
					return tmp;
				}
				else if( byte[].class.isAssignableFrom(toType))
				{
					return ValueObjectUtil.getByteArrayFromClob((Clob)obj);
				}
				else
					return ValueObjectUtil.getStringFromClob((Clob)obj);
			}
			catch (Exception e)
			{
				throw new IllegalArgumentException(new StringBuffer(
				"�����޷�ת��,��֧��[").append(type.getName()).append("]��[")
				.append(toType.getName()).append("]ת��").append(",value is").append(obj).toString());
			}
			
			
		}

		else if (type == byte[].class && File.class.isAssignableFrom(toType)) {
			java.io.ByteArrayInputStream byteIn = null;
			java.io.FileOutputStream fileOut = null;
			if(!(obj instanceof byte[]))
			{
				Object[] object = (Object[]) obj;
				
				try {
					byteIn = new java.io.ByteArrayInputStream((byte[]) object[0]);
					fileOut = new java.io.FileOutputStream((File) object[1]);
					byte v[] = new byte[1024];
	
					int i = 0;
	
					while ((i = byteIn.read(v)) > 0) {
						fileOut.write(v, 0, i);
	
					}
					fileOut.flush();
					return object[1];
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						if (byteIn != null)
							byteIn.close();
					} catch (Exception e) {
	
					}
					try {
						if (fileOut != null)
							fileOut.close();
					} catch (Exception e) {
	
					}
				}
			}
			else
			{
				try {
					byteIn = new java.io.ByteArrayInputStream((byte[]) obj);
					File f = File.createTempFile(java.util.UUID.randomUUID().toString(), ".soa");
					fileOut = new java.io.FileOutputStream(f);
					byte v[] = new byte[1024];
	
					int i = 0;
	
					while ((i = byteIn.read(v)) > 0) {
						fileOut.write(v, 0, i);
	
					}
					fileOut.flush();
					return f;
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						if (byteIn != null)
							byteIn.close();
					} catch (Exception e) {
	
					}
					try {
						if (fileOut != null)
							fileOut.close();
					} catch (Exception e) {
	
					}
				}
			}
		} else if (List.class.isAssignableFrom(toType)) {

			if (!type.isArray()) {
				List valueto = new java.util.ArrayList();
				valueto.add(obj);
				return valueto;
			} else {
				if (type == String[].class) {
					List valueto = new java.util.ArrayList();
					for (String data : (String[]) obj)
						valueto.add(data);
					return valueto;
				}
			}

		}

		else if (Set.class.isAssignableFrom(toType)) {

			if (!type.isArray()) {
				Set valueto = new java.util.TreeSet();
				valueto.add(obj);
				return valueto;
			} else {
				if (type == String[].class) {
					Set valueto = new java.util.TreeSet();
					for (String data : (String[]) obj)
						valueto.add(data);
					return valueto;
				}

			}

		} else if (File.class.isAssignableFrom(toType)
				&& toType == byte[].class) {
			java.io.FileInputStream in = null;
			java.io.ByteArrayOutputStream out = null;
			try {
				int i = 0;
				in = new FileInputStream((File) obj);
				out = new ByteArrayOutputStream();
				byte v[] = new byte[1024];
				while ((i = in.read(v)) > 0) {
					out.write(v, 0, i);
				}
				return out.toByteArray();
			} catch (Exception e) {

			} finally {
				try {
					if (in != null)
						in.close();
				} catch (Exception e) {

				}
				try {
					if (out != null)
						out.close();
				} catch (Exception e) {

				}
			}

		} else if (type.isArray() && !toType.isArray()){ 
				//|| !type.isArray()
				//&& toType.isArray()) {
			// if (type.getName().startsWith("[")
			// && !toType.getName().startsWith("[")
			// || !type.getName().startsWith("[")
			// && toType.getName().startsWith("["))
			throw new IllegalArgumentException(new StringBuffer("�����޷�ת��,��֧��[")
					.append(type.getName()).append("]��[").append(
							toType.getName()).append("]ת��").append(",value is ").append(obj).toString());
		} else if (type == String.class && toType == Class.class) {
			try {
				return getClass((String) obj);
			} catch (ClassNotFoundException e) {
				throw new IllegalArgumentException(new StringBuffer(
						"�����޷�ת��,��֧��[").append(type.getName()).append("]��[")
						.append(toType.getName()).append("]ת��").append(",value is").append(obj).toString());
			}
		}
		Object arrayObj;

		/**
		 * ��������ת���ͻ�������֮���໥ת��
		 */
		if (!type.isArray() && !toType.isArray()) {
			arrayObj = basicTypeCast(obj, type, toType,dateformat);
		}

		/**
		 * �ַ���������������������֮��ת��
		 * ���������֮���ת��
		 * ������������������ת��
		 */
		else {

			arrayObj = arrayTypeCast(obj, type, toType,dateformat);
		}
		return arrayObj;
	}
	
	
	

	/**
	 * ͨ��BeanShell�ű���ת����������
	 * 
	 * @param toType
	 * @param obj
	 * @return
	 */
	public static Object shell(Class toType, Object obj) {
		Interpreter interpreter = new Interpreter();
		String shell = toType.getName() + " ret = (" + toType.getName()
				+ ")obj;return ret;";
		try {
			interpreter.set("obj", obj);
			Object ret = interpreter.eval(shell);
			return ret;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	public final static Object basicTypeCast(Object obj, Class type,
			Class toType) throws NoSupportTypeCastException,
			NumberFormatException 
			{
		return  basicTypeCast( obj, type,
				toType,null);
			}
	
	public static SimpleDateFormat getDateFormat(
			String dateformat)
	{
		if(dateformat == null || dateformat.equals(""))
			return format;
		SimpleDateFormat f = dataformats.get(dateformat);
		if(f != null)
			return f;
		f = new SimpleDateFormat(dateformat);
		dataformats.put(dateformat, f);
		return f;
	}
	/**
	 * Description:��������������ת��
	 * 
	 * @param obj
	 * @param type
	 * @param toType
	 * @return Object
	 * @throws NoSupportTypeCastException
	 * @throws NumberFormatException
	 * 
	 */
	public final static Object basicTypeCast(Object obj, Class type,
			Class toType,String dateformat) throws NoSupportTypeCastException,
			NumberFormatException {
		if (obj == null)
			return null;
		if (isSameType(type, toType, obj))
			return obj;

		if (type.isAssignableFrom(toType)) // type��toType�ĸ��࣬����������ת���Ĺ��̣����ת�������ǲ���ȫ��
		{
			if (!java.util.Date.class.isAssignableFrom(type))
				return shell(toType, obj);
		}
		/**
		 * ���obj�����Ͳ���String��ʱֱ�����쳣, ��֧�ַ��ַ������ַ������������ת��
		 */
		// if (type != String.class)
		// throw new NoSupportTypeCastException(
		// new StringBuffer("��֧��[")
		// .append(type)
		// .append("]��[")
		// .append(toType)
		// .append("]��ת��")
		// .toString());
		/*******************************************
		 * �ַ�����������ͼ����װ��ת�� * ���obj������Ӧ�����ݸ�ʽ,���׳� * NumberFormatException *
		 ******************************************/
		if (toType == long.class || toType == Long.class) {
			if (ValueObjectUtil.isNumber(obj))
				return ((Number) obj).longValue();
			else if(java.util.Date.class.isAssignableFrom(type))
			{
				return ((java.util.Date)obj).getTime();
			}
			return Long.parseLong(obj.toString());
		}
		if (toType == int.class || toType == Integer.class) {
			if (ValueObjectUtil.isNumber(obj))
				return ((Number) obj).intValue();
			return Integer.parseInt(obj.toString());
		}
		if (toType == float.class || toType == Float.class) {
			if (ValueObjectUtil.isNumber(obj))
				return ((Number) obj).floatValue();
			return Float.parseFloat(obj.toString());
		}
		if (toType == short.class || toType == Short.class) {
			if (ValueObjectUtil.isNumber(obj))
				return ((Number) obj).shortValue();
			return Short.parseShort(obj.toString());
		}
		if (toType == double.class || toType == Double.class) {
			if (ValueObjectUtil.isNumber(obj))
				return ((Number) obj).doubleValue();
			return Double.parseDouble(obj.toString());
		}
		if (toType == char.class || toType == Character.class)
			return new Character(obj.toString().charAt(0));

		if (toType == boolean.class || toType == Boolean.class) {
			String ret = obj.toString();
			if (ret.equals("1")) {
				return new Boolean(true);
			} else if (ret.equals("0")) {
				return new Boolean(false);
			}
			return new Boolean(obj.toString());
		}

		if (toType == byte.class || toType == Byte.class)
			return new Byte(obj.toString());
		if(toType == BigDecimal.class)
		{
			return converObjToBigDecimal(obj);
			
		}
		// ������ַ�����ֱ�ӷ���obj.toString()
		if (toType == String.class) {
			if (obj instanceof java.util.Date)
			{
				
				if(!"".equals(obj))
					return getDateFormat(dateformat).format(obj);
				return null;
			}
			
			return obj.toString();
		}
		
		Object date = convertObjToDate( obj,toType,dateformat);
		if(date != null)
			return date;
		
		if (type == String.class && toType == Class.class) {
			try {
				return getClass((String) obj);
			} catch (ClassNotFoundException e) {
				throw new IllegalArgumentException(new StringBuffer(
						"�����޷�ת��,��֧��[").append(type.getName()).append("]��[")
						.append(toType.getName()).append("]ת��").append(",value is ").append(obj).toString());
			}
		}
		
		if (type == String.class && toType.isEnum())
		{
			
			try {
				return convertStringToEnum((String )obj,toType);
			} catch (SecurityException e) {
				throw new IllegalArgumentException(new StringBuffer(
				"�����޷�ת��,��֧��[").append(type.getName()).append("]��ö������[")
				.append(toType.getName()).append("]ת��������ö��ֵ��Χ").append(",value is").append(obj).toString());
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentException(new StringBuffer(
				"�����޷�ת��,��֧��[").append(type.getName()).append("]��ö������[")
				.append(toType.getName()).append("]ת��������ö��ֵ��Χ").append(",value is").append(obj).toString());
			} catch (NoSuchMethodException e) {
				throw new IllegalArgumentException(new StringBuffer(
				"�����޷�ת��,��֧��[").append(type.getName()).append("]��ö������[")
				.append(toType.getName()).append("]ת��������ö��ֵ��Χ").append(",value is").append(obj).toString());
			} catch (IllegalAccessException e) {
				throw new IllegalArgumentException(new StringBuffer(
				"�����޷�ת��,��֧��[").append(type.getName()).append("]��ö������[")
				.append(toType.getName()).append("]ת��������ö��ֵ��Χ").append(",value is").append(obj).toString());
			} catch (InvocationTargetException e) {
				throw new IllegalArgumentException(new StringBuffer(
				"�����޷�ת��,��֧��[").append(type.getName()).append("]��ö������[")
				.append(toType.getName()).append("]ת��������ö��ֵ��Χ").append(",value is").append(obj).toString());
			}
		}

		throw new NoSupportTypeCastException(new StringBuffer("��֧��[").append(
				type).append("]��[").append(toType).append("]��ת��").append(",value is").append(obj).toString());

	}
	
	
	public static BigDecimal converObjToBigDecimal(Object obj)
	{
		if(obj.getClass() == long.class || obj.getClass() == Long.class)
			return BigDecimal.valueOf((Long)obj);
		if(obj.getClass() == short.class || obj.getClass() == Short.class)
			return BigDecimal.valueOf((Short)obj);
		if(obj.getClass() == int.class || obj.getClass() == Integer.class)
			return BigDecimal.valueOf((Integer)obj);
		if(obj.getClass() == double.class || obj.getClass() == Double.class)
			return BigDecimal.valueOf((Double)obj);
		if(obj.getClass() == float.class || obj.getClass() == Float.class)
			return BigDecimal.valueOf((Float)obj);
		return new BigDecimal(obj.toString());
	}
	
	public static Object convertObjToDate(Object obj,Class toType,String dateformat)
	{
		
		/**
		 * �ַ�����java.util.Date��java.sql.Date ����ת��
		 */
		if (toType == java.util.Date.class) {
			// if(obj instanceof java.sql.Date
			// || obj instanceof java.sql.Time
			// || obj instanceof java.sql.Timestamp)
			if (java.util.Date.class.isAssignableFrom(obj.getClass()))
				return obj;
			if(obj.getClass() == long.class || obj.getClass() == Long.class)
			{
				return new java.util.Date((Long)obj);
			}
			String data_str = obj.toString();
			try {
				
				if(!"".equals(data_str))					
					return getDateFormat(dateformat).parse(data_str);
				else
					return null;
			} catch (ParseException e) {
				try
				{
					long dl = Long.parseLong(data_str);
					return new java.util.Date(dl);
				}
				catch (Exception e1)
				{
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
				}
				
				log.error(e.getMessage(),e);
			}
			return new java.util.Date(data_str);
		}

		if (toType == java.sql.Date.class) {

			// if(obj instanceof java.sql.Date
			// || obj instanceof java.sql.Time
			// || obj instanceof java.sql.Timestamp)
			if (java.util.Date.class.isAssignableFrom(obj.getClass()))
				return new java.sql.Date(((java.util.Date) obj).getTime());
			if(obj.getClass() == long.class || obj.getClass() == Long.class)
			{
				return new java.sql.Date((Long)obj);
			}
			String data_str = obj.toString();
			try {
				
				
				if(!"".equals(data_str))					
					return new java.sql.Date(getDateFormat(dateformat).parse(data_str).getTime());
				else
					return null;
			} catch (ParseException e) {
				try
				{
					long dl = Long.parseLong(data_str);
					return new java.util.Date(dl);
				}
				catch (Exception e1)
				{
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
				}
				log.error(e.getMessage(),e);
			}
			java.sql.Date date = java.sql.Date.valueOf(data_str);// (new
			// java.util.Date(obj.toString()).getTime());

			return date;
		}
		
		if (toType == java.sql.Timestamp.class) {

			// if(obj instanceof java.sql.Date
			// || obj instanceof java.sql.Time
			// || obj instanceof java.sql.Timestamp)
			if (java.util.Date.class.isAssignableFrom(obj.getClass()))
				return new java.sql.Timestamp(((java.util.Date) obj).getTime());
			if(obj.getClass() == long.class || obj.getClass() == Long.class)
			{
				return new java.sql.Timestamp((Long)obj);
			}
			String data_str = obj.toString();
			try {
				
				if(!"".equals(data_str))					
					return new java.sql.Timestamp(getDateFormat(dateformat).parse(data_str).getTime());
				else
					return null;
			} catch (ParseException e) {
				try
				{
					long dl = Long.parseLong(data_str);
					return new java.util.Date(dl);
				}
				catch (Exception e1)
				{
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
				}
				log.error(e.getMessage(),e);
			}
			java.sql.Timestamp date = new Timestamp(java.sql.Date.valueOf(data_str).getTime());// (new
			// java.util.Date(obj.toString()).getTime());

			return date;
		}
		
		return null;
	}
	public static <T> T convertStringToEnum(String value,Class<T> enumType) throws SecurityException, NoSuchMethodException, 
				IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		
		Method valueof = enumType.getMethod("valueOf", String.class);
		return (T)valueof.invoke(null, value);
		
	}
	
	/**
	 * 
	 * @param <T>
	 * @param value
	 * @param enumType
	 * @param arrays
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static <T> T[] convertStringToEnumArray(String value,Class<T> enumType,T[] arrays) throws SecurityException, NoSuchMethodException, 
	IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
	
		Method valueof = enumType.getMethod("valueOf", String.class);
		
		Array.set(arrays, 0, valueof.invoke(null, value));
		return arrays;
		
	}
	
	public static <T> T[] convertStringsToEnumArray(String[] value,Class<T> enumType,T[] arrays) throws SecurityException, NoSuchMethodException, 
	IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		
		if(value == null)
			return null;
//		if(value.length == 0)
		
		int i = 0;
		Method valueof = enumType.getMethod("valueOf", String.class);
		for(String v:value)
		{			
			Array.set(arrays, i, valueof.invoke(null, value[i]));
			i ++;
		}
		
		return arrays;
	
	}
	
	public static <T> T[] convertStringToEnumArray(String value,Class<T> enumType) throws SecurityException, NoSuchMethodException, 
	IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
	
		Method valueof = enumType.getMethod("valueOf", String.class);
		Object retvalue = Array.newInstance(enumType, 1);
		Array.set(retvalue, 0, valueof.invoke(null, value));
		return (T[])retvalue;
		
	}
	
	public static <T> T[] convertStringsToEnumArray(String[] value,Class<T> enumType) throws SecurityException, NoSuchMethodException, 
	IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		
		if(value == null)
			return null;
//		if(value.length == 0)
		Object retvalue = Array.newInstance(enumType, value.length);
		int i = 0;
		Method valueof = enumType.getMethod("valueOf", String.class);
		for(String v:value)
		{			
			Array.set(retvalue, i, valueof.invoke(null, value[i]));
			i ++;
		}
		
		return (T[])retvalue;
//		return temp_;
	
	}
	
	

	public final static Object arrayTypeCast(Object obj, Class type,
			Class toType) throws NoSupportTypeCastException,
			NumberFormatException {
		return arrayTypeCast(obj, type,
				toType,null);
	}
	/**
	 * ��������ת�� ֧���ַ���������һ������������Զ�ת��: int[] Integer[] long[] Long[] short[] Short[]
	 * double[] Double[] boolean[] Boolean[] char[] Character[] float[] Float[]
	 * byte[] Byte[] java.sql.Date[] java.util.Date[]
	 * 
	 * @param obj
	 * @param type
	 * @param toType
	 * @return Object
	 * @throws NoSupportTypeCastException
	 * @throws NumberFormatException
	 */
	public final static Object arrayTypeCast(Object obj, Class type,
			Class toType,String dateformat) throws NoSupportTypeCastException,
			NumberFormatException {
		if (isSameType(type, toType, obj))
			return obj;
		// if (type != String[].class)
		// throw new NoSupportTypeCastException(
		// new StringBuffer("��֧��[")
		// .append(type)
		// .append("]��[")
		// .append(toType)
		// .append("]��ת��")
		// .toString());

		if (toType == long[].class) {
			Class componentType = ValueObjectUtil.isNumberArray(obj);
			if (componentType == null) {
				if(java.util.Date.class.isAssignableFrom(type))
				{
					java.util.Date date = (java.util.Date)obj;
					return new long[]{date.getTime()};
				}
				else if(!isDateArray(obj))
				{
					String[] values = (String[]) obj;
					long[] ret = new long[values.length];
					for (int i = 0; i < values.length; i++) {
						ret[i] = Long.parseLong(values[i]);
					}
					return ret;	
				}
				else
				{
					int len = Array.getLength(obj);
					long[] ret = new long[len];
					for(int i = 0;  i < len; i ++)
					{
						java.util.Date date = (java.util.Date)Array.get(obj, i);
						ret[i] = date.getTime();
					}
					return ret;
					
				}
				
			} else {
				return ValueObjectUtil.tolongArray(obj, componentType);

			}
		}
		if (toType == Long[].class) {

			Class componentType = ValueObjectUtil.isNumberArray(obj);
			if (componentType == null) {
				if(!isDateArray(obj))
				{
					String[] values = (String[]) obj;
					Long[] ret = new Long[values.length];
					for (int i = 0; i < values.length; i++) {
						ret[i] = new Long(values[i]);
					}
					
					return ret;
				}
				else
				{

					int len = Array.getLength(obj);
					Long[] ret = new Long[len];
					for(int i = 0;  i < len; i ++)
					{
						java.util.Date date = (java.util.Date)Array.get(obj, i);
						ret[i] = date.getTime();
					}
					return ret;
				}
			}
			else
			{
				return ValueObjectUtil.toLongArray(obj, componentType);
			}
		}

		if (toType == int[].class) {
			Class componentType = ValueObjectUtil.isNumberArray(obj);
			if (componentType == null) {
				String[] values = (String[]) obj;
				int[] ret = new int[values.length];
				for (int i = 0; i < values.length; i++) {
					ret[i] = Integer.parseInt(values[i]);
				}
				return ret;
			}
			else
			{
				return ValueObjectUtil.toIntArray(obj, componentType);
			}
		}
		if (toType == Integer[].class) {
			Class componentType = ValueObjectUtil.isNumberArray(obj);
			if (componentType == null) {
				String[] values = (String[]) obj;
				Integer[] ret = new Integer[values.length];
				for (int i = 0; i < values.length; i++) {
					ret[i] = new Integer(values[i]);
				}
				return ret;
			}
			else
			{
				return ValueObjectUtil.toIntegerArray(obj, componentType);
			}
		}
		if (toType == float[].class) {
			Class componentType = ValueObjectUtil.isNumberArray(obj);
			if (componentType == null) {
				String[] values = (String[]) obj;
				float[] ret = new float[values.length];
				for (int i = 0; i < values.length; i++) {
					ret[i] = Float.parseFloat(values[i]);
				}
				return ret;
			}
			else
			{
				return ValueObjectUtil.tofloatArray(obj, componentType);
			}
		}
		if (toType == Float[].class) {
			Class componentType = ValueObjectUtil.isNumberArray(obj);
			if (componentType == null) {
				String[] values = (String[]) obj;
				Float[] ret = new Float[values.length];
				for (int i = 0; i < values.length; i++) {
					ret[i] = new Float(values[i]);
				}
				return ret;
			}
			else
			{
				return ValueObjectUtil.toFloatArray(obj, componentType);
			}
		}
		if (toType == short[].class) {
			Class componentType = ValueObjectUtil.isNumberArray(obj);
			if (componentType == null) {
				String[] values = (String[]) obj;
				short[] ret = new short[values.length];
				for (int i = 0; i < values.length; i++) {
					ret[i] = Short.parseShort(values[i]);
				}
				return ret;
			}
			else
			{
				return ValueObjectUtil.toshortArray(obj, componentType);
			}
		}
		if (toType == Short[].class) {
			Class componentType = ValueObjectUtil.isNumberArray(obj);
			if (componentType == null) {
				String[] values = (String[]) obj;
				Short[] ret = new Short[values.length];
				for (int i = 0; i < values.length; i++) {
					ret[i] = new Short(values[i]);
				}
				return ret;
			}
			else
			{
				return ValueObjectUtil.toShortArray(obj, componentType);
			}
		}
		if (toType == double[].class) {
			Class componentType = ValueObjectUtil.isNumberArray(obj);
			if (componentType == null) {
				String[] values = (String[]) obj;
				double[] ret = new double[values.length];
				for (int i = 0; i < values.length; i++) {
					ret[i] = Double.parseDouble(values[i]);
				}
				return ret;
			}
			else
			{
				return ValueObjectUtil.todoubleArray(obj, componentType);
			}
		}
		if (toType == Double[].class) {
			Class componentType = ValueObjectUtil.isNumberArray(obj);
			if (componentType == null) {
				String[] values = (String[]) obj;
				Double[] ret = new Double[values.length];
				for (int i = 0; i < values.length; i++) {
					ret[i] = new Double(values[i]);
				}
				return ret;
			}
			else
			{
				return ValueObjectUtil.toDoubleArray(obj, componentType);
			}
		}
		
		if(toType == BigDecimal[].class)
		{
			
			return toBigDecimalArray(obj, null);
			
		}

		if (toType == char[].class) {
			String[] values = (String[]) obj;
			char[] ret = new char[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].charAt(0);
			}
			return ret;
		}
		if (toType == Character[].class) {
			String[] values = (String[]) obj;
			Character[] ret = new Character[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = new Character(values[i].charAt(0));
			}
			return ret;
		}

		if (toType == boolean[].class) {
			String[] values = (String[]) obj;
			boolean[] ret = new boolean[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = new Boolean(values[i]).booleanValue();
			}
			return ret;
		}
		if (toType == Boolean[].class) {
			String[] values = (String[]) obj;
			Boolean[] ret = new Boolean[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = new Boolean(values[i]);
			}
			return ret;
		}
		if (toType == byte[].class) {
			String[] values = (String[]) obj;
			byte[] ret = new byte[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = new Byte(values[i]).byteValue();
			}
			return ret;
		}
		if (toType == Byte[].class) {
			String[] values = (String[]) obj;
			Byte[] ret = new Byte[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = new Byte(values[i]);
			}
			return ret;
		}

		// ������ַ�����ֱ�ӷ���obj.toString()
		if (toType == String[].class) {
			{
				if (obj.getClass() == java.util.Date[].class)
					return SimpleStringUtil.dateArrayTOStringArray((Date[]) obj);
				String[] values = (String[]) obj;
				return values;
			}
			// short[] ret = new short[values.length];
			// for(int i = 0; i < values.length; i ++)
			// {
			// ret[i] = Short.parseShort(values[i]);
			// }
			// return ret;
		}
		/**
		 * �ַ�����java.util.Date��java.sql.Date ����ת��
		 */
		Object dates = convertObjectToDateArray( obj,type,toType, dateformat);
		if(dates != null)
			return dates;
		/**
		 * ö���������ʹ���ת��
		 */
		if(toType.isArray() && toType.getComponentType().isEnum())
		{
			if(type == String.class )
			{
				try {
					Object value = convertStringToEnumArray((String )obj,toType.getComponentType());
					
					return value;
				} catch (SecurityException e) {
					throw new IllegalArgumentException(new StringBuffer(
					"�����޷�ת��,��֧��[").append(type.getName()).append("]��ö������[")
					.append(toType.getName()).append("]ת��������ö��ֵ��Χ").append(",value is").append(obj).toString());
				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException(new StringBuffer(
					"�����޷�ת��,��֧��[").append(type.getName()).append("]��ö������[")
					.append(toType.getName()).append("]ת��������ö��ֵ��Χ").append(",value is").append(obj).toString());
				} catch (NoSuchMethodException e) {
					throw new IllegalArgumentException(new StringBuffer(
					"�����޷�ת��,��֧��[").append(type.getName()).append("]��ö������[")
					.append(toType.getName()).append("]ת��������ö��ֵ��Χ").append(",value is").append(obj).toString());
				} catch (IllegalAccessException e) {
					throw new IllegalArgumentException(new StringBuffer(
					"�����޷�ת��,��֧��[").append(type.getName()).append("]��ö������[")
					.append(toType.getName()).append("]ת��������ö��ֵ��Χ").append(",value is").append(obj).toString());
				} catch (InvocationTargetException e) {
					throw new IllegalArgumentException(new StringBuffer(
					"�����޷�ת��,��֧��[").append(type.getName()).append("]��ö������[")
					.append(toType.getName()).append("]ת��������ö��ֵ��Χ").append(",value is").append(obj).toString());
				}
			}
			else if(type == String[].class )
			{
				try {
					Object value = convertStringsToEnumArray((String[] )obj,toType.getComponentType());
					
					return value;
				} catch (SecurityException e) {
					throw new IllegalArgumentException(new StringBuffer(
					"�����޷�ת��,��֧��[").append(type.getName()).append("]��ö������[")
					.append(toType.getName()).append("]ת��������ö��ֵ��Χ").append(",value is").append(obj).toString());
				} catch (IllegalArgumentException e) {
					throw new IllegalArgumentException(new StringBuffer(
					"�����޷�ת��,��֧��[").append(type.getName()).append("]��ö������[")
					.append(toType.getName()).append("]ת��������ö��ֵ��Χ").append(",value is").append(obj).toString());
				} catch (NoSuchMethodException e) {
					throw new IllegalArgumentException(new StringBuffer(
					"�����޷�ת��,��֧��[").append(type.getName()).append("]��ö������[")
					.append(toType.getName()).append("]ת��������ö��ֵ��Χ").append(",value is").append(obj).toString());
				} catch (IllegalAccessException e) {
					throw new IllegalArgumentException(new StringBuffer(
					"�����޷�ת��,��֧��[").append(type.getName()).append("]��ö������[")
					.append(toType.getName()).append("]ת��������ö��ֵ��Χ").append(",value is").append(obj).toString());
				} catch (InvocationTargetException e) {
					throw new IllegalArgumentException(new StringBuffer(
					"�����޷�ת��,��֧��[").append(type.getName()).append("]��ö������[")
					.append(toType.getName()).append("]ת��������ö��ֵ��Χ").append(",value is").append(obj).toString());
				}
			}
				
		}

		throw new NoSupportTypeCastException(new StringBuffer("��֧��[").append(
				type).append("]��[").append(toType).append("]��ת��").append(",value is").append(obj).toString());

	}
	
	public static Object convertObjectToDateArray(Object obj,Class type,Class toType,String dateformat)
	{
		if (toType == java.util.Date[].class) {
			if(type.isArray())
			{
				if(type == String[].class)
				{
					String[] values = (String[]) obj;
					return SimpleStringUtil.stringArrayTODateArray(values,ValueObjectUtil.getDateFormat(dateformat));
				}
				else
				{
					long[] values = (long[])obj;
					return SimpleStringUtil.longArrayTODateArray(values,ValueObjectUtil.getDateFormat(dateformat));
				}
			}
			else
			{
				if(type == String.class)
				{
					String[] values = new String[] {(String)obj};
					return SimpleStringUtil.stringArrayTODateArray(values,ValueObjectUtil.getDateFormat(dateformat));
				}
				else 
				{
					long[] values = new long[] {((Long)obj).longValue()};
					return SimpleStringUtil.longArrayTODateArray(values,ValueObjectUtil.getDateFormat(dateformat));
				}
			}
		}
		if (toType == java.sql.Date[].class) {
			if(type.isArray())
			{
				if(type == String[].class)
				{
					String[] values = (String[] )obj;
					return SimpleStringUtil.stringArrayTOSQLDateArray(values,ValueObjectUtil.getDateFormat(dateformat));
				}
				else
				{
					long[] values = (long[] )obj;
					
					return SimpleStringUtil.longArrayTOSQLDateArray(values,ValueObjectUtil.getDateFormat(dateformat));
				}
			}
			else
			{
				if(type == String.class)
				{
					String[] values = new String[] {(String)obj};
					return SimpleStringUtil.stringArrayTOSQLDateArray(values,ValueObjectUtil.getDateFormat(dateformat));
				}
				else 
				{
					long[] values = new long[] {((Long)obj).longValue()};
					
					return SimpleStringUtil.longArrayTOSQLDateArray(values,ValueObjectUtil.getDateFormat(dateformat));
				}
			}
			
		}
		
		if (toType == java.sql.Timestamp[].class) {
			
			
			if(type.isArray())
			{
				if(type == String[].class)
				{
					String[] values = (String[] )obj;
					return SimpleStringUtil.stringArrayTOTimestampArray(values,ValueObjectUtil.getDateFormat(dateformat));
				}
				else
				{
					long[] values = (long[] )obj;
					
					return SimpleStringUtil.longArrayTOTimestampArray(values,ValueObjectUtil.getDateFormat(dateformat));
				}
			}
			else
			{
				if(type == String.class)
				{
					String[] values = new String[] {(String)obj};
					return SimpleStringUtil.stringArrayTOTimestampArray(values,ValueObjectUtil.getDateFormat(dateformat));
				}
				else 
				{
					long[] values = new long[] {((Long)obj).longValue()};					
					return SimpleStringUtil.longArrayTOTimestampArray(values,ValueObjectUtil.getDateFormat(dateformat));
				}
			}
		}
		return null;
	}

	public static void getFileFromString(String value, File outfile)
			throws SQLException {
		if(value == null)
			return;
		byte[] bytes = value.getBytes();
		getFileFromBytes(bytes, outfile);

	}

	public static void getFileFromBytes(byte[] bytes, File outfile)
			throws SQLException {
		if(bytes == null)
			return;
		FileOutputStream out = null;
		java.io.ByteArrayInputStream in = null;
		try {
			out = new FileOutputStream(outfile);
			byte v[] = (byte[]) bytes;
			in = new ByteArrayInputStream(v);
			byte b[] = new byte[1024];
			int i = 0;

			while ((i = in.read(b)) > 0) {
				out.write(b, 0, i);

			}

			out.flush();
		} catch (IOException e) {
			throw new NestedSQLException(e);
		} finally {
			try {
				if (out != null) {
					out.close();
					out = null;
				}
			} catch (Exception e) {

			}
			try {
				if (in != null) {
					in.close();
					in = null;
				}
			} catch (Exception e) {

			}
		}
	}

	public static void getFileFromClob(Clob value, File outfile)
			throws SQLException {
		if(value == null)
			return;
		Writer out = null;
		Reader stream = null;

		try {

			out = new FileWriter(outfile);
			Clob clob = (Clob) value;
			stream = clob.getCharacterStream();
			char[] buf = new char[1024];
			int i = 0;

			while ((i = stream.read(buf)) > 0) {
				out.write(buf, 0, i);

			}
			out.flush();
		} catch (IOException e) {
			throw new NestedSQLException(e);
		} finally {
			try {
				if (stream != null)
					stream.close();
			} catch (Exception e) {

			}
			try {
				if (out != null)
					out.close();
			} catch (Exception e) {

			}
		}
	}

	public static void getFileFromBlob(Blob value, File outfile)
			throws SQLException {
		if(value == null)
			return ;
		FileOutputStream out = null;
		InputStream in = null;
		try {
			out = new FileOutputStream(outfile);
			Blob blob = (Blob) value;
			byte v[] = new byte[1024];

			in = blob.getBinaryStream();
			int i = 0;

			while ((i = in.read(v)) > 0) {
				out.write(v, 0, i);

			}
			out.flush();
		} catch (IOException e) {
			throw new NestedSQLException(e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				in = null;

			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				out = null;
			}
		}
	}

	/**
	 * ���ݲ���ֵ������������ǰ����Ĳ������������ж�Ӧ�Ĳ���
	 * params�е�������paramArgs��Ӧλ����ͬ���͵Ĳ��޸ģ�����ͬ���޸�ΪparamArgs����Ӧ������
	 * 
	 * @param params
	 * @param paramArgs
	 * @return
	 */
	public static Class[] synParamTypes(Class[] params, Object[] paramArgs) {
		Class[] news = new Class[params.length];
		for (int i = 0; i < params.length; i++) {
			if (paramArgs[i] != null)
				news[i] = paramArgs[i].getClass();
			else {
				news[i] = params[i];
			}
		}
		return news;

	}

	public static Constructor getConstructor(Class clazz, Class[] params_,
			Object[] paramArgs) {
		return getConstructor(clazz, params_, paramArgs, false);

	}

	/**
	 * ���ݲ�������params_����ȡclazz�Ĺ��캯����paramArgsΪ������ֵ�����synTypesΪtrue������
	 * ͨ��������ֵ�Բ������ͽ���У�� ������params_���͵Ĺ��캯���ж��ʱ�������ʼƥ���ϵĹ��캯�������ǵ�synTypesΪtrueʱ��
	 * �ͻ᷵���ϸ����paramArgsֵ���Ͷ�Ӧ�Ĺ��캯�� paramArgsֵ������Ҳ����Ϊ��ȡ���캯���ĸ���������
	 * 
	 * @param clazz
	 * @param params_
	 * @param paramArgs
	 * @param synTypes
	 * @return
	 */
	public static Constructor getConstructor(Class clazz, Class[] params_,
			Object[] paramArgs, boolean synTypes) {
		if (params_ == null || params_.length == 0) {
			return null;

		}
		Class[] params = null;
		if (synTypes)
			params = synParamTypes(params_, paramArgs);
		else
			params = params_;
		try {

			// Class[] params_ = this.getParamsTypes(params);
			Constructor constructor = null;
			// if (params_ != null)
			constructor = clazz.getConstructor(params);

			return constructor;
		} catch (NoSuchMethodException e) {
			Constructor[] constructors = clazz.getConstructors();
			if (constructors == null || constructors.length == 0)
				throw new CurrentlyInCreationException(
						"Inject constructor error: no construction define in the "
								+ clazz + ",���������ļ��Ƿ�������ȷ,���������Ƿ���ȷ.");
			int l = constructors.length;
			int size = params.length;
			Class[] types = null;
			Constructor fault_ = null;
			for (int i = 0; i < l; i++) {
				Constructor temp = constructors[i];
				types = temp.getParameterTypes();
				if (types != null && types.length == size) {
					if (fault_ == null)
						fault_ = temp;
					if (isSameTypes(types, params, paramArgs))
						return temp;
				}

			}
			if (fault_ != null)
				return fault_;
			throw new CurrentlyInCreationException(
					"Inject constructor error: Parameters with construction defined in the "
							+ clazz
							+ " is not matched with the config paramenters .���������ļ��Ƿ�������ȷ,���������Ƿ���ȷ.");

			// TODO Auto-generated catch block
			// throw new BeanInstanceException("Inject constructor error:"
			// +clazz.getName(),e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new BeanInstanceException("Inject constructor error:"
					+ clazz.getName(), e);
		}

	}

	

	public static Object typeCast(Object value, Class requiredType,
			MethodParameter methodParam) throws NumberFormatException,
			IllegalArgumentException, NoSupportTypeCastException {
		// TODO Auto-generated method stub
		return ValueObjectUtil.typeCast(value, requiredType);
	}

	public static Object typeCast(Object oldValue, Object newValue,
			Class oldtype, Class toType, WrapperEditorInf editorInf) {
		// TODO Auto-generated method stub
		if (editorInf != null)
			return editorInf.getValueFromObject(newValue, oldValue);
		else {
			return ValueObjectUtil.typeCast(newValue, oldtype, toType);
		}
	}

	public static Object getDefaultValue(Class toType) {
		// ������ַ�����ֱ�ӷ���obj.toString()
		if (toType == String.class) {
			return null;
		}
		if (toType == long.class )
			return new Long(0);
		if (toType == int.class )
			return new Integer(0);
		if (toType == float.class )
			return new Float(0.0f);
		if (toType == double.class )
			return new Double(0.0d);
		if (toType == boolean.class ) 

		{
			return new Boolean(false);
		}
		if (toType == short.class )
			return new Short((short) 0);
		if (toType == char.class )
			return new Character('0');
		
		if( toType == Long.class)
			return null;
		
		if( toType == Integer.class)
			return null;
		
		
		if( toType == Float.class)
			return null;
		
		if( toType == Short.class)
			return null;
		
		if( toType == Double.class)
			return null;
		
		if (toType == Character.class)
			return null;
		

		if(toType == Boolean.class)
			return null;

		if (toType == byte.class)
			return new Byte((byte) 0);
		
		if (toType == Byte.class)
			return new Byte((byte) 0);

		
		return null;
	}

	//	
	// @org.junit.Test
	// public void doubletoint()
	// {
	// double number = 10.0;
	// int i = (int)number;
	// System.out.println(i);
	// }
//	@org.junit.Test
//	public void floatoint() {
//		float number = 10.1f;
//		int i = ((Number) number).intValue();
//		System.out.println(i);
//	}
//
//	@org.junit.Test
//	public void numberArray() {
//		double[] number = new double[] { 10.1 };
//		numberArray(number);
//	}
//
//	public void numberArray(Object tests) {
//		System.out.println(isNumberArray(tests));
//
//	}

	/**
	 * ����ȽϹ��ܣ�value1 > value2 ����1��value1 < value2 ����-1��value1 == value2 ����0
	 * �Ƚ�֮ǰ���Ƚ�value2ת��Ϊvalue1������
	 * Ŀǰֻ֧�����ֺ�String���������͵ıȽϣ��������Ͳ���ʹ�øķ������бȽ�
	 */
	public static int typecompare(Object value1,Object value2)
	{
		if(value1 == null && value2 != null)
			return -1;
		if(value1 != null && value2 == null)
			return 1;
		if(value1 == null && value2 == null)
			return 0;
		Class vc1 = value1.getClass();

		try
		{
			if (value1 instanceof String && value2 instanceof String)
			{
				return ((String)value1).compareTo((String)value2);
			}
			else if (value1 instanceof String )
			{
				return ((String)value1).compareTo(String.valueOf(value2));
			}		
			else if (vc1 == int.class || Integer.class.isAssignableFrom(vc1))
			{
				return intcompare(((Integer)value1).intValue(),value2);
			}
			else if(vc1 == long.class
					|| Long.class.isAssignableFrom(vc1))
				return longCompare(((Long)value1).longValue(),value2);
			else if(vc1 == double.class
					|| Double.class.isAssignableFrom(vc1))
				return doubleCompare(((Double)value1).doubleValue(),value2);
			else if(vc1 == float.class
					|| Float.class.isAssignableFrom(vc1))
				return floatCompare(((Float)value1).floatValue(),value2);
			else if(vc1 == short.class
					|| Short.class.isAssignableFrom(vc1))
				return shortCompare(((Short)value1).shortValue(),value2);
			else if(java.util.Date.class.isAssignableFrom(vc1))
				return dateCompare((java.util.Date)value1,value2);
			else if(value1 instanceof java.util.Date && value2 instanceof java.util.Date)
				return ((java.util.Date)value1).compareTo(((java.util.Date)value2));
		}
		catch(Throwable e)
		{
			log.error("compare value1=" + value1 + ",value2=" + value2 + " failed,use default String compare rules instead.",e);
			return String.valueOf(value1).compareTo(String.valueOf(value2));
		}
		
		return String.valueOf(value1).compareTo(String.valueOf(value2));
		
	}
	
	
	public static int intcompare(int value1,Object value2)
	{
		Class vc2 = value2.getClass();
		if(String.class.isAssignableFrom(vc2))
		{
			int v2 = Integer.parseInt((String)value2);
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
		else if(Integer.class.isAssignableFrom(vc2))
		{
			int v2 = ((Integer)value2).intValue();
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
		else if(Long.class.isAssignableFrom(vc2))
		{
			long v2 = (Long)value2;
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
		else if(Double.class.isAssignableFrom(vc2))
		{
			double v2 = ((Double)value2).doubleValue();
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
		else if(Float.class.isAssignableFrom(vc2))
		{
			float v2 = ((Float)value2).floatValue();
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
		else if(Short.class.isAssignableFrom(vc2))
		{
			short v2 = ((Short)value2).shortValue();
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
		
		else if(java.util.Date.class.isAssignableFrom(vc2))
		{
			long v2 = ((java.util.Date)value2).getTime();
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
		else
		{
			int v2 = Integer.parseInt((String)value2);
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
			
		
		
	}
	
	public static int IntegerCompare(Integer value1,Object value2)
	{
		return intcompare(value1.intValue(),value2);
	}
	
	public static int longCompare(long value1,Object value2)
	{
		Class vc2 = value2.getClass();
		 if(String.class.isAssignableFrom(vc2))
		{
			long v2 = Long.parseLong((String)value2);
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
		 else if(Integer.class.isAssignableFrom(vc2))
		{
			int v2 = ((Integer)value2).intValue();
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
		else if(Long.class.isAssignableFrom(vc2))
		{
			long v2 = (Long)value2;
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
		else if(Double.class.isAssignableFrom(vc2))
		{
			double v2 = ((Double)value2).doubleValue();
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
		else if(Float.class.isAssignableFrom(vc2))
		{
			float v2 = ((Float)value2).floatValue();
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
		else if(Short.class.isAssignableFrom(vc2))
		{
			short v2 = ((Short)value2).shortValue();
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
		
		else if(java.util.Date.class.isAssignableFrom(vc2))
		{
			long v2 = ((java.util.Date)value2).getTime();
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
		else
		{
			long v2 = Long.parseLong((String)value2);
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
	}
	
	public static int LongCompare(Long value1,Object value2)
	{
		return longCompare(value1.longValue(),value2);
	}
	
	public static int doubleCompare(double value1,Object value2)
	{
		Class vc2 = value2.getClass();
		if(String.class.isAssignableFrom(vc2))
		{
			double v2 = Double.parseDouble((String)value2);
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
		else if(Integer.class.isAssignableFrom(vc2))
		{
			int v2 = ((Integer)value2).intValue();
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
		else if(Long.class.isAssignableFrom(vc2))
		{
			long v2 = (Long)value2;
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
		else if(Double.class.isAssignableFrom(vc2))
		{
			double v2 = ((Double)value2).doubleValue();
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
		else if(Float.class.isAssignableFrom(vc2))
		{
			float v2 = ((Float)value2).floatValue();
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
		else if(Short.class.isAssignableFrom(vc2))
		{
			short v2 = ((Short)value2).shortValue();
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
		
		else if(java.util.Date.class.isAssignableFrom(vc2))
		{
			long v2 = ((java.util.Date)value2).getTime();
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
		else
		{
			double v2 = Double.parseDouble((String)value2);
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
	}
	
	public static int DoubleCompare(Double value1,Object value2)
	{
		return doubleCompare(value1.doubleValue(),value2);
	}
	
	public static int floatCompare(float value1,Object value2)
	{
		Class vc2 = value2.getClass();
		if(String.class.isAssignableFrom(vc2))
		{
			float v2 = Float.parseFloat(String.valueOf(value2));
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
		else if(Integer.class.isAssignableFrom(vc2))
		{
			int v2 = ((Integer)value2).intValue();
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
		else if(Long.class.isAssignableFrom(vc2))
		{
			long v2 = (Long)value2;
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
		else if(Double.class.isAssignableFrom(vc2))
		{
			double v2 = ((Double)value2).doubleValue();
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
		else if(Float.class.isAssignableFrom(vc2))
		{
			float v2 = ((Float)value2).floatValue();
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
		else if(Short.class.isAssignableFrom(vc2))
		{
			short v2 = ((Short)value2).shortValue();
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
		
		else if(java.util.Date.class.isAssignableFrom(vc2))
		{
			long v2 = ((java.util.Date)value2).getTime();
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
		else
		{
			float v2 = Float.parseFloat(String.valueOf(value2));
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
	}
	
	public static int FloatCompare(Float value1,Object value2)
	{
		return floatCompare(value1.floatValue(),value2);
	}
	
	public static int shortCompare(short value1,Object value2)
	{
		Class vc2 = value2.getClass();
		if(String.class.isAssignableFrom(vc2))
		{
			short v2 = Short.parseShort(String.valueOf(value2));
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
		else if(Integer.class.isAssignableFrom(vc2))
		{
			int v2 = ((Integer)value2).intValue();
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
		else if(Long.class.isAssignableFrom(vc2))
		{
			long v2 = (Long)value2;
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
		else if(Double.class.isAssignableFrom(vc2))
		{
			double v2 = ((Double)value2).doubleValue();
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
		else if(Float.class.isAssignableFrom(vc2))
		{
			float v2 = ((Float)value2).floatValue();
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
		else if(Short.class.isAssignableFrom(vc2))
		{
			short v2 = ((Short)value2).shortValue();
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
		
		else if(java.util.Date.class.isAssignableFrom(vc2))
		{
			long v2 = ((java.util.Date)value2).getTime();
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
		else
		{
			short v2 = Short.parseShort(String.valueOf(value2));
			if(value1 == v2)
				return 0;
			else if(value1 > v2)
				return 1;
			else	
				return -1;
		}
	}
	
	public static int ShortCompare(Short value1,Object value2)
	{
		return shortCompare(value1.shortValue(),value2);
	}
	
	public static int dateCompare(java.util.Date value1,Object value2)
	{
		try {
			Class vc2 = value2.getClass();
			if(java.util.Date.class.isAssignableFrom(vc2))
			{
				java.util.Date v2 = ((java.util.Date)value2);
				return dateCompare(value1,v2);
			}
			else if(String.class.isAssignableFrom(vc2))
			{
				java.util.Date v2 = ValueObjectUtil.format.parse((String)value2);
				return dateCompare(value1,v2);
			}
			else if(Long.class.isAssignableFrom(vc2))
			{
				java.util.Date v2 = new java.util.Date(((Long)value2).longValue());
				return dateCompare(value1,v2);
			}
			if(Integer.class.isAssignableFrom(vc2))
			{
				
				java.util.Date v2 = new java.util.Date(((Integer)value2).intValue());
				return dateCompare(value1,v2);
			}
			
			else if(Double.class.isAssignableFrom(vc2))
			{
				
				java.util.Date v2 = new java.util.Date(((Double)value2).longValue());
				return dateCompare(value1,v2);
			}
			else if(Float.class.isAssignableFrom(vc2))
			{
				java.util.Date v2 = new java.util.Date(((Float)value2).longValue());
				return dateCompare(value1,v2);
			}
			else if(Short.class.isAssignableFrom(vc2))
			{
				java.util.Date v2 = new java.util.Date(((Short)value2).longValue());
				return dateCompare(value1,v2);
			}
			else
			{
				java.util.Date v2 = ValueObjectUtil.format.parse(String.valueOf(value2));
				return dateCompare(value1,v2);
			}
				
			
		} catch (Exception e) {
			return -1;
		}
	}
	
	public static int dateCompare(java.util.Date value1,java.util.Date value2)
	{
		return value1.compareTo(value2);
	}

	public static boolean isNumber(Object value) {

		boolean isnumber = value instanceof Number;
		// System.out.println(isnumber);
		// isnumber = number_d instanceof Number;
		// System.out.println(isnumber);
		return isnumber;

	}

	public static Class isNumberArray(Object value) {
		if (!value.getClass().isArray())
			return null;

		Class componentType = value.getClass().getComponentType();
		if(String.class.isAssignableFrom(componentType))
			return null;
		if (componentType == int.class
				|| Integer.class.isAssignableFrom(componentType)
				|| componentType == long.class
				|| Long.class.isAssignableFrom(componentType)
				|| componentType == double.class
				|| Double.class.isAssignableFrom(componentType)
				|| componentType == float.class
				|| Float.class.isAssignableFrom(componentType)
				|| componentType == short.class
				|| Short.class.isAssignableFrom(componentType))
			return componentType;
		return null;
	}
	
	public static boolean isDateArray(Object value) {
		if (!value.getClass().isArray())
			return false;

		Class componentType = value.getClass().getComponentType();
		if(java.util.Date.class.isAssignableFrom(componentType))
			return true;
		
		return false;
	}

	public static short[] toshortArray(Object value, Class componentType) {
		if (!value.getClass().isArray()) {
			if (isNumber(value)) {
				return new short[] { ((Number) value).shortValue() };
			} else {
				return new short[] { Short.parseShort(value.toString()) };
			}
		}

		short[] ret = null;
		if (componentType == int.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			int[] values = (int[]) value;
			ret = new short[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = (short)values[i];
			}
			return ret;

		}
		if (Integer.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Integer[] values = (Integer[]) value;
			ret = new short[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].shortValue();
			}
			return ret;
		}

		if (componentType == long.class){
			long[] values = (long[]) value;
			ret = new short[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = (short) values[i];
			}
			return ret;
		}
			
		if (Long.class.isAssignableFrom(componentType)) {
			Long[] values = (Long[]) value;
			ret = new short[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].shortValue();
			}
			return ret;
		}
		if (componentType == double.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			double[] values = (double[]) value;
			ret = new short[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = (short) values[i];
			}
			return ret;

		}
		if (Double.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Double[] values = (Double[]) value;
			ret = new short[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].shortValue();
			}
			return ret;
		}

		if (componentType == float.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			float[] values = (float[]) value;
			ret = new short[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = (short) values[i];
			}
			return ret;

		}
		if (Float.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Float[] values = (Float[]) value;
			ret = new short[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].shortValue();
			}
			return ret;
		}

		if (componentType == short.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			return (short[]) value;
		}
		if (Short.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Short[] values = (Short[]) value;
			ret = new short[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].shortValue();
			}
			return ret;
		}

		return null;
	}
	
	public static Short[] toShortArray(Object value, Class componentType) {
		if (!value.getClass().isArray()) {
			if (isNumber(value)) {
				return new Short[] { ((Number) value).shortValue()};
			} else {
				return new Short[] { Short.parseShort(value.toString()) };
			}
		}

		Short[] ret = null;
		if (componentType == int.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			int[] values = (int[]) value;
			ret = new Short[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] =(short) values[i];
			}
			return ret;

		}
		if (Integer.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Integer[] values = (Integer[]) value;
			ret = new Short[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].shortValue();
			}
			return ret;
		}

		if (Long.class.isAssignableFrom(componentType)){
			Long[] values = (Long[]) value;
			ret = new Short[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].shortValue();
			}
			return ret;
			
		}
			
		if (long.class == componentType) {
			long[] values = (long[]) value;
			ret = new Short[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] =(short) values[i];
			}
			return ret;
		}
		if (componentType == double.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			double[] values = (double[]) value;
			ret = new Short[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = (short) values[i];
			}
			return ret;

		}
		if (Double.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Double[] values = (Double[]) value;
			ret = new Short[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].shortValue();
			}
			return ret;
		}

		if (componentType == float.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			float[] values = (float[]) value;
			ret = new Short[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = (short) values[i];
			}
			return ret;

		}
		if (Float.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Float[] values = (Float[]) value;
			ret = new Short[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].shortValue();
			}
			return ret;
		}

		if (componentType == short.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			short[] values = (short[]) value;
			ret = new Short[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = (short) values[i];
			}
			return ret;

		}
		if (Short.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			return (Short[]) value;
		}

		return null;
	}
	
	
	
	public static int[] toIntArray(Object value, Class componentType) {
		if (!value.getClass().isArray()) {
			if (isNumber(value)) {
				return new int[] { ((Number) value).intValue() };
			} else {
				return new int[] { Integer.parseInt(value.toString()) };
			}
		}

		int[] ret = null;
		if (componentType == int.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			return (int[])value;

		}
		if (Integer.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Integer[] values = (Integer[]) value;
			ret = new int[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i];
			}
			return ret;
		}

		if (componentType == long.class){
			long[] values = (long[]) value;
			ret = new int[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = (int) values[i];
			}
			return ret;
		}
		if (Long.class.isAssignableFrom(componentType)) {
			Long[] values = (Long[]) value;
			ret = new int[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].intValue();
			}
			return ret;
		}
		if (componentType == double.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			double[] values = (double[]) value;
			ret = new int[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = (int) values[i];
			}
			return ret;

		}
		if (Double.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Double[] values = (Double[]) value;
			ret = new int[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].intValue();
			}
			return ret;
		}

		if (componentType == float.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			float[] values = (float[]) value;
			ret = new int[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = (int) values[i];
			}
			return ret;

		}
		if (Float.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Float[] values = (Float[]) value;
			ret = new int[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].intValue();
			}
			return ret;
		}

		if (componentType == short.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			short[] values = (short[]) value;
			ret = new int[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = (int) values[i];
			}
			return ret;

		}
		if (Short.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Short[] values = (Short[]) value;
			ret = new int[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].intValue();
			}
			return ret;
		}

		return null;
	}
	
	public static Integer[] toIntegerArray(Object value, Class componentType) {
		if (!value.getClass().isArray()) {
			if (isNumber(value)) {
				return new Integer[] { ((Number) value).intValue() };
			} else {
				return new Integer[] { Integer.parseInt(value.toString()) };
			}
		}

		Integer[] ret = null;
		if (componentType == int.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			int[] values = (int[]) value;
			ret = new Integer[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = new Integer(values[i]);
			}
			return ret;

		}
		if (Integer.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			return (Integer[]) value;
			
		}

		if (Long.class.isAssignableFrom(componentType)){
			Long[] values = (Long[]) value;
			ret = new Integer[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].intValue();
			}
			return ret;
		}
			
		if (long.class == componentType) {
			long[] values = (long[]) value;
			ret = new Integer[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = (int)values[i];
			}
			return ret;
		}
		if (componentType == double.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			double[] values = (double[]) value;
			ret = new Integer[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] =  (int) values[i];
			}
			return ret;

		}
		if (Double.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Double[] values = (Double[]) value;
			ret = new Integer[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].intValue();
			}
			return ret;
		}

		if (componentType == float.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			float[] values = (float[]) value;
			ret = new Integer[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = (int) values[i];
			}
			return ret;

		}
		if (Float.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Float[] values = (Float[]) value;
			ret = new Integer[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].intValue();
			}
			return ret;
		}

		if (componentType == short.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			short[] values = (short[]) value;
			ret = new Integer[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = (int) values[i];
			}
			return ret;

		}
		if (Short.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Short[] values = (Short[]) value;
			ret = new Integer[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].intValue();
			}
			return ret;
		}

		return null;
	}
	
	public static long[] tolongArray(Object value, Class componentType) {
		if (!value.getClass().isArray()) {
			if (isNumber(value)) {
				return new long[] { ((Number) value).longValue() };
			} else {
				return new long[] { Long.parseLong(value.toString()) };
			}
		}

		long[] ret = null;
		if (componentType == int.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			int[] values = (int[]) value;
			ret = new long[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i];
			}
			return ret;

		}
		if (Integer.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Integer[] values = (Integer[]) value;
			ret = new long[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i];
			}
			return ret;
		}

		if (componentType == long.class)
			return (long[]) value;
		if (Long.class.isAssignableFrom(componentType)) {
			Long[] values = (Long[]) value;
			ret = new long[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i];
			}
			return ret;
		}
		if (componentType == double.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			double[] values = (double[]) value;
			ret = new long[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = (long) values[i];
			}
			return ret;

		}
		if (Double.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Double[] values = (Double[]) value;
			ret = new long[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].longValue();
			}
			return ret;
		}

		if (componentType == float.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			float[] values = (float[]) value;
			ret = new long[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = (long) values[i];
			}
			return ret;

		}
		if (Float.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Float[] values = (Float[]) value;
			ret = new long[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].longValue();
			}
			return ret;
		}

		if (componentType == short.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			short[] values = (short[]) value;
			ret = new long[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = (long) values[i];
			}
			return ret;

		}
		if (Short.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Short[] values = (Short[]) value;
			ret = new long[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].longValue();
			}
			return ret;
		}

		return null;
	}
	
	public static Long[] toLongArray(Object value, Class componentType) {
		if (!value.getClass().isArray()) {
			if (isNumber(value)) {
				return new Long[] { ((Number) value).longValue() };
			} else {
				return new Long[] { Long.parseLong(value.toString()) };
			}
		}

		Long[] ret = null;
		if (componentType == int.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			int[] values = (int[]) value;
			ret = new Long[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = new Long(values[i]);
			}
			return ret;

		}
		if (Integer.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Integer[] values = (Integer[]) value;
			ret = new Long[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].longValue();
			}
			return ret;
		}

		if (Long.class.isAssignableFrom(componentType))
			return (Long[]) value;
		if (long.class == componentType) {
			long[] values = (long[]) value;
			ret = new Long[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i];
			}
			return ret;
		}
		if (componentType == double.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			double[] values = (double[]) value;
			ret = new Long[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = (long) values[i];
			}
			return ret;

		}
		if (Double.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Double[] values = (Double[]) value;
			ret = new Long[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].longValue();
			}
			return ret;
		}

		if (componentType == float.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			float[] values = (float[]) value;
			ret = new Long[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = (long) values[i];
			}
			return ret;

		}
		if (Float.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Float[] values = (Float[]) value;
			ret = new Long[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].longValue();
			}
			return ret;
		}

		if (componentType == short.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			short[] values = (short[]) value;
			ret = new Long[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = (long) values[i];
			}
			return ret;

		}
		if (Short.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Short[] values = (Short[]) value;
			ret = new Long[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].longValue();
			}
			return ret;
		}

		return null;
	}
	public static float[] tofloatArray(Object value, Class componentType) {
		if (!value.getClass().isArray()) {
			if (isNumber(value)) {
				return new float[] { ((Number) value).floatValue() };
			} else {
				return new float[] { Float.parseFloat(value.toString()) };
			}
		}

		float[] ret = null;
		if (componentType == int.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			int[] values = (int[]) value;
			ret = new float[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i];
			}
			return ret;

		}
		if (Integer.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Integer[] values = (Integer[]) value;
			ret = new float[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i];
			}
			return ret;
		}

		if (componentType == long.class){
			long[] values = (long[]) value;
			ret = new float[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = (float) values[i];
			}
			return ret;
			
		}
		
		if (Long.class.isAssignableFrom(componentType)) {
			Long[] values = (Long[]) value;
			ret = new float[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i];
			}
			return ret;
		}
		if (componentType == double.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			double[] values = (double[]) value;
			ret = new float[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = (float) values[i];
			}
			return ret;

		}
		if (Double.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Double[] values = (Double[]) value;
			ret = new float[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].floatValue();
			}
			return ret;
		}

		if (componentType == float.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			return (float[]) value;

		}
		if (Float.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Float[] values = (Float[]) value;
			ret = new float[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].floatValue();
			}
			return ret;
		}

		if (componentType == short.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			short[] values = (short[]) value;
			ret = new float[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = (float) values[i];
			}
			return ret;

		}
		if (Short.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Short[] values = (Short[]) value;
			ret = new float[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].floatValue();
			}
			return ret;
		}

		return null;
	}
	
	public static Float[] toFloatArray(Object value, Class componentType) {
		if (!value.getClass().isArray()) {
			if (isNumber(value)) {
				return new Float[] { ((Number) value).floatValue() };
			} else {
				return new Float[] { Float.parseFloat(value.toString()) };
			}
		}

		Float[] ret = null;
		if (componentType == int.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			int[] values = (int[]) value;
			ret = new Float[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = new Float(values[i]);
			}
			return ret;

		}
		if (Integer.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Integer[] values = (Integer[]) value;
			ret = new Float[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].floatValue();
			}
			return ret;
		}

		if (Long.class.isAssignableFrom(componentType)){
			Long[] values = (Long[]) value;
			ret = new Float[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].floatValue();
			}
			return ret;
		}
			
		if (long.class == componentType) {
			long[] values = (long[]) value;
			ret = new Float[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = (float)values[i];
			}
			return ret;
		}
		if (componentType == double.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			double[] values = (double[]) value;
			ret = new Float[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = (float) values[i];
			}
			return ret;

		}
		if (Double.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Double[] values = (Double[]) value;
			ret = new Float[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].floatValue();
			}
			return ret;
		}

		if (componentType == float.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			float[] values = (float[]) value;
			ret = new Float[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = (float) values[i];
			}
			return ret;

		}
		if (Float.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			return (Float[]) value;
		}

		if (componentType == short.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			short[] values = (short[]) value;
			ret = new Float[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = (float) values[i];
			}
			return ret;

		}
		if (Short.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Short[] values = (Short[]) value;
			ret = new Float[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].floatValue();
			}
			return ret;
		}

		return null;
	}
	public static double[] todoubleArray(Object value, Class componentType) {
		if (!value.getClass().isArray()) {
			if (isNumber(value)) {
				return new double[] { ((Number) value).doubleValue()};
			} else {
				return new double[] { Double.parseDouble(value.toString()) };
			}
		}

		double[] ret = null;
		if (componentType == int.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			int[] values = (int[]) value;
			ret = new double[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i];
			}
			return ret;

		}
		if (Integer.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Integer[] values = (Integer[]) value;
			ret = new double[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].doubleValue();
			}
			return ret;
		}

		if (componentType == double.class)
			return (double[]) value;
		if (Long.class.isAssignableFrom(componentType)) {
			Long[] values = (Long[]) value;
			ret = new double[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].doubleValue();
			}
			return ret;
		}
		if (componentType == long.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			long[] values = (long[]) value;
			ret = new double[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] =  values[i];
			}
			return ret;

		}
		if (Double.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Double[] values = (Double[]) value;
			ret = new double[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].doubleValue();
			}
			return ret;
		}

		if (componentType == float.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			float[] values = (float[]) value;
			ret = new double[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = (double) values[i];
			}
			return ret;

		}
		if (Float.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Float[] values = (Float[]) value;
			ret = new double[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].doubleValue();
			}
			return ret;
		}

		if (componentType == short.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			short[] values = (short[]) value;
			ret = new double[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] =  values[i];
			}
			return ret;

		}
		if (Short.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Short[] values = (Short[]) value;
			ret = new double[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].doubleValue();
			}
			return ret;
		}

		return null;
	}
	
	public static BigDecimal[] toBigDecimalArray(Object value, Class componentType) {
		if (!value.getClass().isArray()) {
			
				return new BigDecimal[] { converObjToBigDecimal(value)};
			
		}

		BigDecimal[] ret = null;
		int length = Array.getLength(value);
		ret = new BigDecimal[length];
		for (int i = 0; i < length; i++) {
			ret[i] = converObjToBigDecimal(Array.get(value, i));
		}
		return ret;
		

		

		
	}
	
	
	public static Double[] toDoubleArray(Object value, Class componentType) {
		if (!value.getClass().isArray()) {
			if (isNumber(value)) {
				return new Double[] { ((Number) value).doubleValue() };
			} else {
				return new Double[] { Double.parseDouble(value.toString()) };
			}
		}

		Double[] ret = null;
		if (componentType == int.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			int[] values = (int[]) value;
			ret = new Double[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = new Double(values[i]);
			}
			return ret;

		}
		if (Integer.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Integer[] values = (Integer[]) value;
			ret = new Double[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].doubleValue();
			}
			return ret;
		}

		if (Long.class.isAssignableFrom(componentType)){
			Long[] values = (Long[]) value;
			ret = new Double[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].doubleValue();
		     }
		return ret;
		}
		if (long.class == componentType) {
			long[] values = (long[]) value;
			ret = new Double[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = new Double(values[i]);
			}
			return ret;
		}
		if (componentType == double.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			double[] values = (double[]) value;
			ret = new Double[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = (double) values[i];
			}
			return ret;

		}
		if (Double.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			return (Double[]) value;
		}

		if (componentType == float.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			float[] values = (float[]) value;
			ret = new Double[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = (double) values[i];
			}
			return ret;

		}
		if (Float.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Float[] values = (Float[]) value;
			ret = new Double[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].doubleValue();
			}
			return ret;
		}

		if (componentType == short.class)
		// || Integer.class.isAssignableFrom(componentType))
		{
			short[] values = (short[]) value;
			ret = new Double[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = (double) values[i];
			}
			return ret;

		}
		if (Short.class.isAssignableFrom(componentType))
		// || Integer.class.isAssignableFrom(componentType))
		{
			Short[] values = (Short[]) value;
			ret = new Double[values.length];
			for (int i = 0; i < values.length; i++) {
				ret[i] = values[i].doubleValue();
			}
			return ret;
		}

		return null;
	}
	static enum Test
	{
		A,B,C
	}
	public static void main(String[] args) throws ClassNotFoundException
	{
//		Test[][] a = new Test[][]{{Test.A,Test.B}};
//		String ttt = a.getClass().getName();
//		Class x= Class.forName(ttt);
//		System.out.println();
		System.out.println(ValueObjectUtil.typecompare(0,"0"));
		System.out.println(ValueObjectUtil.typecompare(0,0));
//		try {
//			Test[] temp = ValueObjectUtil.convertStringsToEnumArray(new String[]{"A","B","C"}, Test.class);
//			System.out.println(temp.getClass().getComponentType());
//			System.out.println(temp[0].getClass());
//			Method f=  Calle.class.getMethod("testEnum", new Class[]{Test[].class});
//			
//			f.invoke(null, new Object[]{temp});
//		} catch (SecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NoSuchMethodException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(a.getClass().isArray());
//		System.out.println(a.getClass().getComponentType().isEnum());
	}
	
	static class Calle
	{
		public static void testEnum(Test... tests)
		{
			System.out.println(tests);
		}
	}
	
	public static String getTypeName(Class array)
	{
		if(array == null)
			return null;
		if(array.isArray())
		{	
			String ret = array.getComponentType().getName();
			if(ret.equals("java.lang.String"))
				return "String[]";
			else if(ret.equals("java.lang.Object"))
				return "Object[]";
			else if(ret.equals("java.lang.Class"))
				return "Class[]";
			else if(ret.equals("byte"))
				return "byte[]";	
			else
			{
				ret = array.getName();
			}
			
			return ret;
		}
		else
		{
			String ret = array.getCanonicalName() ;
			if(ret.equals("java.lang.String"))
				return "String";
			else if(ret.equals("java.lang.Object"))
				return "Object";
			else if(ret.equals("java.lang.Class"))
				return "Class";
			
			return ret;
		}
			
		
	}
	
	public static String getSimpleTypeName(Class array)
	{
		if(array == null)
			return null;
		if(array.isArray())
		{	
			String ret = array.getComponentType().getCanonicalName();
			if(ret.equals("java.lang.String"))
				return "String[]";
			else if(ret.equals("java.lang.Object"))
				return "Object[]";
			else if(ret.equals("java.lang.Class"))
				return "Class[]";
			else if(ret.equals("byte"))
				return "byte[]";	
		
			return array.getName();
		}
		else
		{
			String ret = array.getName() ;
			if(ret.equals("java.lang.String"))
				return "String";
			else if(ret.equals("java.util.ArrayList"))
				return "ArrayList";
			else if(ret.equals("java.util.HashMap"))
				return "HashMap";
			
			else if(ret.equals("java.lang.Class"))
				return "Class";
			
			else if(ret.equals("java.util.TreeSet"))
				return "TreeSet";
			else if(ret.equals("java.lang.Object"))
				return "Object";
			
			return ret;
		}
			
		
	}
	
	/**
	 * ��ȡ����Ԫ����������
	 * @param array
	 * @return
	 */
	public static String getComponentTypeName(Class array)
	{
		if(array == null)
			return null;
		if(array.isArray())
		{	
			String ret = array.getComponentType().getName() ;
		
			if(ret.equals("java.lang.String"))
				return "String";
			else if(ret.equals("java.lang.Object"))
				return "Object";
			else if(ret.equals("java.lang.Class"))
				return "Class";
			else if(ret.equals("byte"))
				return "byte";	
			
			return ret;
		}
		else
		{
			String ret = array.getCanonicalName() ;
			
			if(ret.equals("java.lang.String"))
				return "String";
			else if(ret.equals("java.lang.Object"))
				return "Object";
			else if(ret.equals("java.lang.Class"))
				return "Class";
			return ret;
		}
			
		
	}
	
	public static Class<?> getClass(String type) throws ClassNotFoundException {
		if(type == null)
			return null;
		else if(type.equals("String") )
		{
			return String.class;
		}
		else if (type.equals("int"))
			return int.class;
		else if (type.equals("long"))
			return long.class;
		else if (type.equals("Long"))
			return Long.class;
		else if (type.equals("boolean"))
			return boolean.class;
		else if (type.equals("double"))
			return double.class;
		else if (type.equals("float"))
			return float.class;
		else if (type.equals("ArrayList"))
			return ArrayList.class;

		else if (type.equals("HashMap"))
			return HashMap.class;
		else if (type.equals("string") ||  type.equals("java.lang.String")
				|| type.equals("java.lang.string"))
			return String.class;

			
		else if (type.equals("short"))
			return short.class;
		else if (type.equals("char"))
			return char.class;
	

		else if (type.equals("Boolean"))
			return Boolean.class;
		else if (type.equals("Double"))
			return Double.class;
		else if (type.equals("Float"))
			return Float.class;
		else if (type.equals("Short"))
			return Short.class;
		else if (type.equals("Char") || type.equals("Character")
				|| type.equals("character"))
			
			return Character.class;
		else if( type.equals("String[]"))
		{
			return String[].class;
		}
		else if (type.equals("int[]"))
			return int[].class;
		else if (type.equals("byte[]"))
			return byte[].class;
		else if (type.equals("string[]")  || type.equals("java.lang.String[]"))
			return String[].class;
		else if (type.equals("boolean[]"))
			return boolean[].class;
		else if (type.equals("double[]"))
			return double[].class;
		else if (type.equals("float[]"))
			return float[].class;
		else if (type.equals("short[]"))
			return short[].class;
		else if (type.equals("char[]"))
			return char[].class;
		else if (type.equals("long[]"))
			return long[].class;
		else if (type.equals("Long[]"))
			return Long[].class;

		else if (type.equals("Boolean[]"))
			return Boolean[].class;
		else if (type.equals("Double[]"))
			return Double[].class;
		else if (type.equals("Float[]"))
			return Float[].class;
		else if (type.equals("Short[]"))
			return Short[].class;
		else if (type.equals("Char[]") || type.equals("Character[]")
				|| type.equals("character[]"))
			return Character[].class;
		else if (type.equals("Class") || type.equals("class"))
			return Class.class;
		else if (type.equals("Class[]") || type.equals("class[]"))
			return Class[].class;
		else if (type.equals("byte"))
			return byte.class;
		else if (type.equals("TreeSet"))
			return TreeSet.class;
		else if(type.endsWith("[]"))
		{
			int len = type.length() - 2;
			int idx = type.indexOf("[");
			String subClass = type.substring(0,idx);
			String array = type.substring(idx);
			int count = 0;
			StringBuilder builder = new StringBuilder();
			
			
			for(int i = 0; i < array.length(); i ++)
			{
				char c = array.charAt(i);
				if(c == '[')
					builder.append("[");
			}
			builder.append("L").append(subClass).append(";");
			return Class.forName(builder.toString());
			
		    
		}
		Class<?> Type = Class.forName(type);
		return Type;
	}
	
	public static String byteArrayEncoder(byte[] contents)
	{
		BASE64Encoder en = new BASE64Encoder();
		return en.encode(contents);
	}
	
	public static byte[] byteArrayDecoder(String contents) throws Exception
	{
		if(contents == null)
			return null;
		BASE64Decoder en = new BASE64Decoder();
		try {
			return en.decodeBuffer(contents);
		} catch (IOException e) {
			throw e;
		}
		
	}
	
	public static String getFileContent(String configFile)
    {
    	try {
			return getFileContent(configFile,"GBK");
		} catch (Exception e) {
			return null;
		}
    }
    
//    public static String getFileContent(String configFile,String charset)
//    {
//    	
//        
//        	try {
//				return getFileContent(getClassPathFile(configFile),charset);
//			} catch (Exception e) {
//				return null;
//			}
//       
//    }
    private static ClassLoader getTCL() throws IllegalAccessException, InvocationTargetException {
        Method method = null;
        try {
            method = (java.lang.Thread.class).getMethod("getContextClassLoader", null);
        } catch (NoSuchMethodException e) {
            return null;
        }
        return (ClassLoader)method.invoke(Thread.currentThread(), null);
    }
    /**
     * InputStream reader = null;
					ByteArrayOutputStream swriter = null;
					OutputStream temp = null;
					try {
						reader = ValueObjectUtil
								.getInputStreamFromFile(PoolManConstants.XML_CONFIG_FILE_TEMPLATE);

						swriter = new ByteArrayOutputStream();
						temp = new BufferedOutputStream(swriter);

						int len = 0;
						byte[] buffer = new byte[1024];
						while ((len = reader.read(buffer)) > 0) {
							temp.write(buffer, 0, len);
						}
						temp.flush();
						pooltemplates = swriter.toString("GBK");

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						if (reader != null)
							try {
								reader.close();
							} catch (IOException e) {
							}
						if (swriter != null)
							try {
								swriter.close();
							} catch (IOException e) {
							}
						if (temp != null)
							try {
								temp.close();
							} catch (IOException e) {
							}
					}
     * @param file
     * @param charSet
     * @return
     * @throws IOException
     */
    public static String getFileContent(File file,String charSet) throws IOException
    {
    	ByteArrayOutputStream swriter = null;
        OutputStream temp = null;
        InputStream reader = null;
        try
        {
        	reader = new FileInputStream(file);
        	swriter = new ByteArrayOutputStream();
        	temp = new BufferedOutputStream(swriter);

            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = reader.read(buffer)) > 0)
            {
            	temp.write(buffer, 0, len);
            }
            temp.flush();
            return swriter.toString(charSet);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return "";
        }
        catch (IOException e)
        {
            e.printStackTrace();
            throw e;
        }
        finally
        {
            if (reader != null)
                try
                {
                    reader.close();
                }
                catch (IOException e)
                {
                }
            if (swriter != null)
                try
                {
                    swriter.close();
                }
                catch (IOException e)
                {
                }
            if (temp != null)
                try
                {
                	temp.close();
                }
                catch (IOException e)
                {
                }
        }
    }
    
    public static String getFileContent(String file,String charSet) 
    {
    	ByteArrayOutputStream swriter = null;
        OutputStream temp = null;
        InputStream reader = null;
        try
        {
        	reader = getInputStreamFromFile(file);
        	swriter = new ByteArrayOutputStream();
        	temp = new BufferedOutputStream(swriter);

            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = reader.read(buffer)) > 0)
            {
            	temp.write(buffer, 0, len);
            }
            temp.flush();
            return swriter.toString(charSet);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return "";
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return "";
        } catch (Exception e) {
			// TODO Auto-generated catch block
        	return "";
		}
        finally
        {
            if (reader != null)
                try
                {
                    reader.close();
                }
                catch (IOException e)
                {
                }
            if (swriter != null)
                try
                {
                    swriter.close();
                }
                catch (IOException e)
                {
                }
            if (temp != null)
                try
                {
                	temp.close();
                }
                catch (IOException e)
                {
                }
        }
    }
    
    public static byte[] getBytesFileContent(String file) 
    {
    	ByteArrayOutputStream swriter = null;
        OutputStream temp = null;
        InputStream reader = null;
        try
        {
        	reader = getInputStreamFromFile(file);
        	swriter = new ByteArrayOutputStream();
        	temp = new BufferedOutputStream(swriter);

            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = reader.read(buffer)) > 0)
            {
            	temp.write(buffer, 0, len);
            }
            temp.flush();
            return swriter.toByteArray();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return null;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
			// TODO Auto-generated catch block
        	return null;
		}
        finally
        {
            if (reader != null)
                try
                {
                    reader.close();
                }
                catch (IOException e)
                {
                }
            if (swriter != null)
                try
                {
                    swriter.close();
                }
                catch (IOException e)
                {
                }
            if (temp != null)
                try
                {
                	temp.close();
                }
                catch (IOException e)
                {
                }
        }
    }
    
    public static File getClassPathFile(String configFile) throws Exception
    {
    	String url = configFile;
        try {
        	
        	
	            URL confURL = ValueObjectUtil.class.getClassLoader().getResource(configFile);
	            
	            if (confURL == null)
	            
	                confURL = ValueObjectUtil.class.getClassLoader().getResource("/" + configFile);
	            else
	            {
//	            	String path = confURL.toString();
//	            	System.out.println(path);
	            }
	
	            if (confURL == null)
	                confURL = getTCL().getResource(configFile);
	            if (confURL == null)
	                confURL = getTCL().getResource("/" + configFile);
	            if (confURL == null)
	                confURL = ClassLoader.getSystemResource(configFile);
	            if (confURL == null)
	                confURL = ClassLoader.getSystemResource("/" + configFile);
	
	            if (confURL == null) {
	                url = System.getProperty("user.dir");
	                url += "/" + configFile;
	                File f = new File(url);
	            	if(f.exists())
	            		return f;
	            	return null;
	            } else {
	                url = confURL.getFile();
	                File f = new File(url);
	            	if(f.exists())
	            		return f;
	            	else
	            		f = new File(confURL.toString());
	            	return f;
	            }
        	
	        
        	
        	
        }
    	catch(Exception e)
    	{
    		throw e;
    	}
    }
    
    public static InputStream getInputStreamFromFile(String configFile) throws Exception
    {
    	String url = configFile;
        try {
            URL confURL = ValueObjectUtil.class.getClassLoader().getResource(configFile);
            if (confURL == null)
                confURL = ValueObjectUtil.class.getClassLoader().getResource("/" + configFile);

            if (confURL == null)
                confURL = getTCL().getResource(configFile);
            if (confURL == null)
                confURL = getTCL().getResource("/" + configFile);
            if (confURL == null)
                confURL = ClassLoader.getSystemResource(configFile);
            if (confURL == null)
                confURL = ClassLoader.getSystemResource("/" + configFile);

            if (confURL == null) {
                url = System.getProperty("user.dir");
                url += "/" + configFile;
                return new FileInputStream(new File(url));
            } else {
            	return confURL.openStream();
            }
        }
    	catch(Exception e)
    	{
    		  return new FileInputStream(configFile);
    	}
    }
    
    /**
     * �ж���type�Ƿ��ǻ����������ͻ��߻���������������
     * @param type
     * @return
     */
    public static boolean isPrimaryType(Class type)
    {
    	if(!type.isArray())
    	{
    		if(type.isEnum())
    			return true;
	    	for(Class primaryType:ValueObjectUtil.baseTypes)
	    	{
	    		if(primaryType.isAssignableFrom(type))
	    			return true;
	    	}
	    	return false;
    	}
    	else
    	{
    		return isPrimaryType(type.getComponentType());
    	}
    	
    }
    
    /**
     * �ж���type�Ƿ��ǻ�����������
     * @param type
     * @return
     */
    public static boolean isBasePrimaryType(Class type)
    {
    	if(!type.isArray())
    	{
    		if(type.isEnum())
    			return true;
	    	for(Class primaryType:ValueObjectUtil.basePrimaryTypes)
	    	{
	    		if(primaryType.isAssignableFrom(type))
	    			return true;
	    	}
	    	return false;
    	}
    	return false;
    	
    }
	
}
