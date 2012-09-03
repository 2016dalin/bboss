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
 *  
 *                                                                           *
 *****************************************************************************/

package com.frameworkset.common.tag.pager.tags;


import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Clob;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.log4j.Logger;

import com.chinacreator.cms.driver.jsp.CMSServletRequest;
import com.frameworkset.common.poolman.SQLExecutor;
import com.frameworkset.common.tag.exception.FormulaException;
import com.frameworkset.common.tag.pager.ClassData;
import com.frameworkset.common.tag.pager.ClassDataList;
import com.frameworkset.common.tag.pager.DataInfo;
import com.frameworkset.common.tag.pager.DefaultDataInfoImpl;
import com.frameworkset.common.tag.pager.ObjectDataInfoImpl;
import com.frameworkset.common.tag.pager.model.DataModel;
import com.frameworkset.common.tag.pager.model.Formula;
import com.frameworkset.util.StringUtil;
import com.frameworkset.util.ValueObjectUtil;

/**
 * ����DataInfo�ӿڻ�ȡ��ҳ/�б�/��ϸ���ݣ� ���Է�װ��Щ���ݵ��ض������ݽṹ�У�Ϊ��ʾ������׼��
 * 
 * 
 * �ж��Ƿ���Ƕ���б������Ƕ���б���������������ԣ� colName,property,sortKey,desc
 * 
 * @author biaoping.yin
 */
public class PagerDataSet extends PagerTagSupport {
//    protected String pretoken = "#\\[";
//    protected String endtoken = "\\]";
//	protected boolean flag = false;
	/**
	 * ���ͨ��title��ǩ���������ֶΣ�ͨ����������
	 * �����Ƿ��Զ��Ե�ǰҳ�������򣬻��������ݼ��������ֹ�����sql����ȫ����������
	 * true-�Զ�����ȱʡֵ
	 * false-�����ݼ��������ֹ�����sql����ȫ����������,������ݼ�����û�н�titleָ���������ֶ���Ϊsql�������ֶΣ���ô
	 *       ָ���������ֶν���������
	 */
	protected boolean autosort = true;
	
	protected Object actual;
	
	
	// protected Tag origineTag = null;

	/***************************************************************************
	 * �������ݹ���ϵͳ������ӵ�ϵ�����Կ�ʼ **
	 **************************************************************************/

	// /**
	// * Ƶ��id
	// */
	// private String channelid="";
	/** �������·�ҳ����form�ύ�¼���������ָ����Ҫ�ύ��form������ */
	private String form = null;

	/*
	 * Tag Properties ������ת��url
	 */
	private String url = null;

	/**
	 * ����������𣬶�ӦPagerContext�е�index����
	 */
	private String navindex = null;

	private int items;

	private int maxItems = DEFAULT_MAX_ITEMS;

	private int maxPageItems = DEFAULT_MAX_PAGE_ITEMS;

	private int maxIndexPages = DEFAULT_MAX_INDEX_PAGES;

	private static final int DEFAULT_MAX_ITEMS = Integer.MAX_VALUE,
			DEFAULT_MAX_PAGE_ITEMS = 10, DEFAULT_MAX_INDEX_PAGES = 10;

	private boolean isOffset = true;

	private String scope = null;

	private String data = null;

	/**
	 * ��ʶ�Ƿ���wapӦ��
	 */
	private boolean wapflag = false;

	/** ����Ҫ��������ʱ�Ƿ���ʾ���棬��form�������ʹ�� */
	private boolean promotion = false;

	boolean isList = true;

	String field = "";

	String width = null;

	String title = null;

	/***************************************************************************
	 * �������ݹ���ϵͳ������ӵ�ϵ�����Խ��� **
	 **************************************************************************/

	/**
	 * ����ȫ�ֱ���
	 */
	protected DataModel dataModel = null;

	private int index = -1;

	private static final Logger log = Logger.getLogger(PagerDataSet.class);
	
	/**
	 * �ⲿ����ı���������Ϣ
	 */
	private String rowidName = "rowid";
	private String dataSetName = "dataSet";
	

	/**
	 * �������ݼ��Ķ�ջ
	 */
	protected Stack stack = null;

	protected String sessionKey = null;

	protected String requestKey = null;

	protected String pageContextKey = null;

	protected boolean needClear = false;
	
	protected String sqlparamskey = "sql.params.key";

	/**
	 * ����dataSet�Ļ�ȡ��Χ
	 */
	protected static final String SESSION_SCOPE = "session";

	protected static final String REQUEST_SCOPE = "request";

	protected static final String PAGECONTEXT_SCOPE = "pageContext";

	protected static final String COLUMN_SCOPE = "column";

	protected static final String DB_SCOPE = "db";

	/**
	 * ���ݹ����䳣�������ݹ���ϵͳ����ʱ��Ҫ�õ��ķ�Χ
	 */
	protected static final String CMS_SCOPE = "cms";

	/**
	 * ����ҳ��pageSet�Ķ�ջ
	 */
	protected static final String PAGERDATASET_STACK = "PAGERDATASET_STACK";

	/**
	 * ����dataSet�г��ֵ����й�ʽ �Թ�ʽ��Ϊkeyֵ ��Formula������Ϊֵ
	 * ��ǩ��Թ�ʽ�Ľ����ȱ���
	 */
	private Map formulas = null;

	// static
	// {
	// try
	// {
	// System.setOut(new PrintStream(new FileOutputStream(new
	// java.io.File("d:/test.txt"))));
	// }
	// catch (FileNotFoundException e1)
	// {
	//
	// e1.printStackTrace();
	// }
	// }
	/**
	 * �ַ������飬���ҳ����Ҫ��ʾ�����ֶ�����
	 */
	protected java.lang.String[] fields;

	protected ClassDataList theClassDataList = new ClassDataList();

	protected String sortKey = null;

	/***************************************************************************
	 * ��ֵ��������colName��Ӧ������ΪCollectionʱ��* ����ǩ����collection��Ϊ����Դ�� *
	 * ѭ��Ƕ�����collection�еĶ������� * property�¼���������
	 **************************************************************************/
	/**
	 * ����
	 */
	protected String colName;

	/**
	 * �¼���������
	 */
	protected String property;

	int rowid = 0;

	/**
	 * �����б����ͣ���ȡ����
	 */
	String type = "";

	/**
	 * �������ݿ��ѯ���
	 */
	protected String statement = null;

	/**
	 * �������ݿ����ӳ�����
	 */
	protected String dbname = null;

	protected String desc = null;

	/**
	 * �Ƿ������ⲿ���� true:������ȱʡֵ false��������
	 */
	protected boolean declare = true;
	
    /**
     * jquery��������
     */
    private String containerid ;
    
    /**
     * jquery����ѡ����
     */
    private String selector;

	public PagerDataSet() {

	}
//	 public HttpServletRequest getHttpServletRequest()
//    {
//    	return (HttpServletRequest) pageContext.getRequest();
//    }
//    
//    public HttpServletResponse getHttpServletResponse()
//    {
//    	return (HttpServletResponse) pageContext.getResponse();
//    }
//    
//    public JspWriter getJspWriter()
//    {
//    	
//    	return (JspWriter) pageContext.getOut();
//    }
//    
//    public HttpSession getSession()
//    {
//    	return getHttpServletRequest().getSession(false);
//    }
	
	/**
	 * ������췽��ֻ�����ݹ���ϵͳ����ʱ����
	 */
	public PagerDataSet(HttpServletRequest request,
			HttpServletResponse response, PageContext pageContext) {
//		self_var_flag = true;
		this.request = request;
		this.session = request.getSession(false);
		this.response = response;
		this.pageContext = pageContext;

	}


	

	public PagerContext getPagerContext()
	{
//		System.out.println("public PagerContext getPagerContext()");
		if(this.pagerContext == null)
		{
			
			if(stack.size() == 0)
				return null;
			PagerDataSet dataSet = (PagerDataSet)this.stack.peek();
			return dataSet.getPagerContext();
		}
		else
		{
			return this.pagerContext;
		}
	}
	public long getRowcount() 
	{
		PagerContext pagerContext = getPagerContext();
		if(pagerContext != null)
		{
			if(pagerContext != null && !pagerContext.ListMode())
				return pagerContext.getItemCount();
			else
				return pagerContext.getDataSize();
		}
		else
		{
			return 0;
		}
	}
	
	public int getPageSize() 
	{
		PagerContext pagerContext = getPagerContext();
		if(pagerContext != null)
		{
			return pagerContext.getDataSize();
		}
		else
		{
			return 0;
		}
	}
	
	

	/**
	 * ��ʼ��ʱ����Ҫ���õķ���������˳��1
	 * 
	 * @param fields
	 */
	public void initField(String[] fields) {

		this.fields = fields;
		for (int i = 0; this.fields != null && i < this.fields.length; i++) {
			this.fields[i] = this.fields[i].trim();
		}
	}

	/**
	 * @param dataInfo
	 * @param fields -
	 *            �ַ������飬���ҳ����Ҫ��ʾ�����ֶ�����
	 * 
	 * public PagerDataSet(DataInfo dataInfo, String[] fields) {
	 * this.loadClassData(dataInfo.getDataList()); this.fields = fields; }
	 */

	/**
	 * Access method for the fields property.
	 * 
	 * @return the current value of the fields property
	 */
	public java.lang.String[] getFields() {
		return fields;
	}

	/**
	 * ��ȡdataSet��ֵ��������� rowid:��������ȷ��ֵ�����λ�� columnid����������ȷ����ȡֵ������ĸ�����
	 * 
	 * ����ȡ�����Է�װ��һ��Object���󷵻�
	 * 
	 * @param rowid
	 * @param columnid
	 * @return Object
	 */
	public Object getValue(int rowid, int columnid) {
		return getValue(rowid, locateField(columnid));
	}

	/**
	 * �к�rowid����Ӧ�����Զ��������ֵ
	 * 
	 * @param rowid
	 * @param columnid
	 * @param property
	 * @return Object
	 */
	public Object getValue(int rowid, int columnid, String property) {
		return getValue(rowid, locateField(columnid), property);

	}
	
	private boolean needPeak()
	{
		return this.pagerContext == null;
	}

	/**
	 * ��ȡ�к�Ϊrowid��ֵ��������������ΪcolName��ֵ
	 * 
	 * @param rowid -
	 *            ֵ�����к�
	 * @param colName -
	 *            ��������
	 * @return Object
	 */
	public Object getValue(int rowid, String colName) {
		if (rowid == -1)
			return null;
		try
		{
        		if(!this.needPeak())
        		{
        		     
        			return getValue(theClassDataList.get(rowid), colName);
        		}
        		else
        		{
        			if(stack.size() == 0)
        				return null;
        			PagerDataSet dataSet = (PagerDataSet)this.stack.peek();
        			return dataSet.getValue(rowid,colName);
        		}
		}
		catch(Exception e)
		{
		    throw new RuntimeException("��ȡ����[colName=" + colName + "]ʧ�ܣ�" + theClassDataList + "=" + theClassDataList,e);
		}
	}
	
	
	/**
	 * ��ȡ�к�Ϊrowid��ֵ��������������ΪcolName��ֵ
	 * 
	 * @param rowid -
	 *            ֵ�����к�
	 * @param colName -
	 *            ��������
	 * @return Object
	 */
	public Object getValue(int rowid) {
		if (rowid == -1)
			return null;
		try
		{
        		if(!this.needPeak())
        		{
        		     
        			return getValue(theClassDataList.get(rowid));
        		}
        		else
        		{
        			if(stack.size() == 0)
        				return null;
        			PagerDataSet dataSet = (PagerDataSet)this.stack.peek();
        			return dataSet.getValue(rowid);
        		}
		}
		catch(Exception e)
		{
		    throw new RuntimeException("��ȡ����[colName=" + colName + "]ʧ�ܣ�" + theClassDataList + "=" + theClassDataList,e);
		}
	}
	
	/**
	 * ��ȡ��Ӧ��ԭʼ������������
	 * @param rowid
	 * @return
	 */
	public Object getOrigineObject(int rowid)
	{
		if(!this.needPeak())
		{
			ClassData object = this.theClassDataList.get(rowid);
			return object.getValueObject();
		}
		else
		{
			if(stack.size() == 0)
				return null;
			PagerDataSet dataSet = (PagerDataSet)this.stack.peek();
			return dataSet.getOrigineObject(rowid);
		}
	}
	
	public Object getMapKey()
	{
		return getMapKey(rowid);
	}
	
	public Object getMapKey(int rowid)
	{
		if(!this.needPeak())
		{
			ClassData object = this.theClassDataList.get(rowid);
			return object.getMapkey();
		}
		else
		{
			if(stack.size() == 0)
				return null;
			PagerDataSet dataSet = (PagerDataSet)this.stack.peek();
			return dataSet.getMapKey(rowid);
		}
	}
	
	/**
	 * ��ȡ��ǰ��ԭʼ������������
	 * @param rowid
	 * @return
	 */
	public Object getOrigineObject()
	{
		return getOrigineObject(rowid);
	}
	
	

	public Object getValue(String colName) {
		if (rowid == -1)
			return null;
		return getValue(rowid, colName);
	}

	/**
	 * ��ȡֵ�����������õ�����ֵ���󣨱�������ΪcolName�� �����ԣ���������ΪsubColName��ֵ
	 * 
	 * @param rowid
	 * @param colName
	 * @param subColName
	 * @return Object
	 */

	public Object getValue(int rowid, String colName, String subColName) {
		if(!this.needPeak())
		{
			Object referObj = getValue(rowid, colName);
			if (referObj == null)
				return null;
			ClassData referData = new ClassData(referObj);
			return getValue(referData, subColName);
		}
		else
		{
			if(stack.size() == 0)
				return null;
			PagerDataSet dataSet = (PagerDataSet)this.stack.peek();
			return dataSet.getValue( rowid,  colName,  subColName);
		}
	}

	/**
	 * ��data�л�ȡ����ֵ
	 * 
	 * @param data
	 * @param colName
	 * @return Object
	 */
	public Object getValue(ClassData data, String colName) {
		Object value = data.getValue(colName);
		
		return value;
	}
	
	/**
	 * ��data�л�ȡ����ֵ
	 * 
	 * @param data
	 * @param colName
	 * @return Object
	 */
	public Object getValue(ClassData data) {
		Object value = data.getValueObject();
		
		return value;
	}

	// public List getValue(int rowid, String colName,String subColName)
	// {
	// return (List)theClassDataList.get(rowid).getValue(colName);
	// }

	/**
	 * @param rowid
	 * @param columnid
	 * @return java.lang.String
	 */
	public String getString(int rowid, int columnid) {
		Object value = getValue(rowid, columnid);
		
		return convertLobToString(value);
		// return String.valueOf(getValue(rowid, columnid));
	}

	public String getStringWithDefault(int rowid, int columnid, Object defaultValue) {
		Object value = getValue(rowid, columnid);
		value = convertLobToString(value);
		return value == null ? defaultValue.toString() : value.toString();
		// return String.valueOf(getValue(rowid, columnid));
	}

	public String getString(int rowid, int columnid, String property) {
		Object value = getValue(rowid, columnid, property);
		
		return convertLobToString(value);
		// return String.valueOf(getValue(rowid, columnid));
	}
	
	public Object getObject(int rowid, int columnid, String property) {
		Object value = getValue(rowid, columnid, property);
		
		return convertLobToObject(value);
		// return String.valueOf(getValue(rowid, columnid));
	}

	public String getStringWithDefault(int rowid, int columnid, String property,
			Object defaultValue) {
		Object value = getValue(rowid, columnid, property);
		value = convertLobToString(value);
		return value == null ? defaultValue.toString() : value.toString();
		// return String.valueOf(getValue(rowid, columnid));
	}

	/**
	 * @param rowid
	 * @param colName
	 * @return java.lang.String
	 */
	public String getString(int rowid, String colName) {
		Object value = getValue(rowid, colName);
		return convertLobToString(value);
	}
	
	/**
	 * @param rowid
	 * @param colName
	 * @return java.lang.String
	 */
	public Object getObject(int rowid, String colName) {
		Object value = getValue(rowid, colName);
		return convertLobToObject(value);
	}
	
	/**
	 * @param rowid
	 * @param colName
	 * @return java.lang.String
	 */
	public String getString(int rowid) {
		Object value = getValue(rowid);
		return convertLobToString(value);
	}
	
	/**
	 * @param rowid
	 * @param colName
	 * @return java.lang.String
	 */
	public Object getObject(int rowid) {
		Object value = getValue(rowid);
		return convertLobToObject(value);
	}
	
	/**
	 * ��ȡ���Զ�Ӧ��map����
	 * @param colName
	 * @return java.lang.String
	 */
	public Map getMap(String colName) {
		Object value = getValue(rowid, colName);
		try
		{
			return value == null ? null : (Map)value;
		}
		catch(Exception e)
		{
			return null;
		}
	}

	/**
	 * @param rowid
	 * @param colName
	 * @return java.lang.String
	 */
	public String getString(String colName) {
		Object value = getValue(rowid, colName);
		
		return convertLobToString(value);
	}

	/**
	 * ����������ȡ��Ӧ�ĸ�ֵ
	 * 
	 * @param rowid
	 * @param colName
	 * @param index
	 * @return
	 */
	public String getString(String colName, int index) {
		if (index >= stack.size() || index < 0) {
			log.debug("Get [" + colName + "] error: Out of index[" + index
					+ "],stack size is " + stack.size());
			return null;
		}
		// ����������ȡ�ֶε�ֵ
		PagerDataSet dataSet = (PagerDataSet) this.stack.elementAt(index);
		Object value = dataSet.getValue(dataSet.getRowid(), colName);
		
		return convertLobToString(value);
	}



	/**
	 * @param rowid
	 * @param colName
	 * @return java.lang.String
	 */
	public String getStringWithDefault(int rowid, String colName, Object defaultValue) {
		Object value = getValue(rowid, colName);
		value = convertLobToString(value);
		return value == null ? defaultValue.toString() : value.toString();
	}

	/**
	 * @param rowid
	 * @param colName
	 * @return java.lang.String
	 */
	public String getStringWithDefault(String colName, Object defaultValue) {
		Object value = getValue(rowid, colName);
		value = convertLobToString(value);
		return value == null ? defaultValue.toString() : value.toString();
	}

	/**
	 * @param rowid
	 * @param colName
	 * @return java.lang.String
	 */
	public String getStringWithDefault(String colName, Object defaultValue, int index) {
		if (index >= stack.size() || index < 0) {
			log.debug("Get [" + colName + "] error: Out of index[" + index
					+ "],stack size is " + stack.size());
			return defaultValue.toString();
		}
		// ����������ȡ�ֶε�ֵ
		PagerDataSet dataSet = (PagerDataSet) this.stack.elementAt(index);
		Object value = dataSet.getValue(dataSet.getRowid(), colName);
		value = convertLobToString(value);
		return value == null ? defaultValue.toString() : value.toString();
	}

	public String getString(int rowid, String colName, String property) {
		Object value = getValue(rowid, colName, property);
		return convertLobToString(value);
	}
	
	public Object getObject(int rowid, String colName, String property) {
		Object value = getValue(rowid, colName, property);
		return convertLobToObject(value);
	}
	
	public String getString( String colName, String property) {
		Object value = getValue(rowid, colName, property);
		return convertLobToString(value);
	}
	
	/**
	 * ����������Ӧ�Ķ�������Ի�ȡ����ֵ
	 * @param colName
	 * @param property
	 * @param defaultValue
	 * @return
	 */
	public String getStringWithDefault( String colName, String property,Object defaultValue) {
		Object value = getValue(rowid, colName, property);
		if(value == null)
			return defaultValue.toString();
		return convertLobToString(value);
	}
	

	/**
	 * ��lob�ֶ�������ַ���
	 * @param value
	 * @return
	 */
	private String convertLobToString(Object value)
	{
		if(value == null)
			return null;
		if(!(value instanceof Clob) && !(value instanceof Blob))
			return value == null ? null : value.toString();
		else if(value instanceof Clob)
		{
			try
			{
				 Clob temp = (Clob)value;
	             int leg = (int)temp.length();
	             if(leg > 0)
	             	return temp.getSubString(1,leg);
	             else
	             	return "";
			}
			catch(Exception e)
			{
				
			}
		}
		else if(value instanceof Blob)
		{
			try
			{
				 Blob blob = (Blob)value;
	             int length = (int)blob.length();
	             if(length > 0)
	             {
		                byte ret[] = new byte[(int)blob.length()];
		                ret = blob.getBytes(1,length);
		                return new String(ret);
	             }
	             else
	             {
	             	return "";
	             }
			}
			catch(Exception e)
			{
				
			}
		}
		return value.toString();
	}
	
	/**
	 * ��lob�ֶ�������ַ���
	 * @param value
	 * @return
	 */
	private Object convertLobToObject(Object value)
	{
		if(value == null)
			return null;
		if(!(value instanceof Clob) && !(value instanceof Blob))
			return value ;
		else if(value instanceof Clob)
		{
			try
			{
				 Clob temp = (Clob)value;
	             int leg = (int)temp.length();
	             if(leg > 0)
	             	return temp.getSubString(1,leg);
	             else
	             	return "";
			}
			catch(Exception e)
			{
				
			}
		}
		else if(value instanceof Blob)
		{
			try
			{
				 Blob blob = (Blob)value;
	             int length = (int)blob.length();
	             if(length > 0)
	             {
		                byte ret[] = new byte[(int)blob.length()];
		                ret = blob.getBytes(1,length);
		                return new String(ret);
	             }
	             else
	             {
	             	return "";
	             }
			}
			catch(Exception e)
			{
				
			}
		}
		return value;
	}
	public String getString(String colName, String property, int index) {
		if (index >= stack.size() || index < 0) {
			log.debug("Get [" + colName + "] error: Out of index[" + index
					+ "],stack size is " + stack.size());
			return null;
		}
		// ����������ȡ�ֶε�ֵ
		PagerDataSet dataSet = (PagerDataSet) this.stack.elementAt(index);
		Object value = dataSet.getValue(dataSet.getRowid(), colName, property);
		
		return convertLobToString(value);
	}

	public String getStringWithDefault(int rowid, String colName, String property,
			Object defaultValue) {
		Object value = getValue(rowid, colName, property);
		value = convertLobToString(value);
		return value == null ? defaultValue.toString() : value.toString();
	}

	public String getStringWithDefault(String colName, String property,
			Object defaultValue, int index) {
		if (index >= stack.size() || index < 0) {
			log.debug("Get [" + colName + "] error: Out of index[" + index
					+ "],stack size is " + stack.size());
			return defaultValue.toString();
		}
		// ����������ȡ�ֶε�ֵ
		PagerDataSet dataSet = (PagerDataSet) this.stack.elementAt(index);
		Object value = dataSet.getValue(dataSet.getRowid(), colName, property);
		value = convertLobToString(value);
		return value == null ? defaultValue.toString() : value.toString();
	}

	/**
	 * @param rowid
	 * @param columnid
	 * @return int
	 */
	public int getInt(int rowid, int columnid) {
		return ((Integer) getValue(rowid, columnid)).intValue();
	}

	/**
	 * @param rowid
	 * @param colName
	 * @return java.lang.String
	 */
	public int getInt(int rowid, String colName) {
		return ((Integer) getValue(rowid, colName)).intValue();
	}

	/**
	 * �������ݼ�����index���ֶ�����,��ȡ�����ֶε�ֵ
	 * 
	 * @param colName
	 * @param index
	 * @return
	 */

	public int getInt(String colName, int index) {
		if (index >= stack.size() || index < 0) {
			log.debug("Get [" + colName + "] error: Out of index[" + index
					+ "],stack size is " + stack.size());
			return -1;
		}
		// ����������ȡ�ֶε�ֵ
		PagerDataSet dataSet = (PagerDataSet) this.stack.elementAt(index);
		Object value = dataSet.getValue(dataSet.getRowid(), colName, property);
		if (value == null)
			return -1;
		return ((Integer) value).intValue();
	}

	/**
	 * �����ֶ�����,��ȡ�����ֶε�ֵ
	 * 
	 * @param rowid
	 * @param colName
	 * @return java.lang.String
	 */
	public int getInt(String colName) {
		return ((Integer) getValue(rowid, colName)).intValue();
	}

	/**
	 * @param rowid
	 * @param columnid
	 * @return Date
	 */
	public Date getDate(int rowid, int columnid) {
		return (Date) getValue(rowid, columnid);
	}

	/**
	 * @param rowid
	 * @param columnid
	 * @return Date
	 */
	public Date getDate(int columnid) {
		return (Date) getValue(rowid, columnid);
	}

	/**
	 * @param rowid
	 * @param columnid
	 * @return Date
	 */
	public String getFormatDate(int rowid, int columnid, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Object obj = getValue(rowid, columnid);
		// System.out.println("column " + columnid + ": "+obj);
		if (obj == null)
			return null;

		try {
			Date date = (Date) obj;

			return dateFormat.format(date);
		} catch (Exception e) {
			// e.printStackTrace();
			return obj.toString();
		}

	}

	/**
	 * @param rowid
	 * @param colName
	 * @return Date
	 */
	public Date getDate(int rowid, String colName) {
		return (Date) getValue(rowid, colName);
	}

	/**
	 * 
	 * @param colName
	 * @return Date
	 */
	public Date getDate(String colName) {
		return (Date) getValue(rowid, colName);
	}

	/**
	 * @param rowid
	 * @param colName
	 * @return Date
	 */
	public String getFormatDate(int rowid, String colName, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Object obj = getValue(rowid, colName);
		// System.out.println("colName " + colName + ": "+obj);
		if (obj == null)
			return null;
		try {
		    if(obj instanceof Date)
		    {
    			Date date = (Date) obj;
    			return dateFormat.format(date);
		    }
		    else if(obj instanceof Long)
		    {
		        long va = ((Long)obj).longValue();
		        if(va <= 0)
		            return "";
		        Date date = new Date(va);
                return dateFormat.format(date);
		    }
		    else 
            {
                return obj.toString();
            }
            
		} catch (Exception e) {
			// e.printStackTrace();
			return obj.toString();
		}
	}
	
	
	/**
	 * @param rowid
	 * @param colName
	 * @return Date
	 */
	public String getFormatDate(int rowid,  String format) {
		SimpleDateFormat dateFormat = ValueObjectUtil.getDateFormat(format);
		Object obj = getValue(rowid);
		// System.out.println("colName " + colName + ": "+obj);
		if (obj == null)
			return null;
		try {
		    if(obj instanceof Date)
		    {
    			Date date = (Date) obj;
    			return dateFormat.format(date);
		    }
		    else if(obj instanceof Long)
		    {
		        long va = ((Long)obj).longValue();
		        if(va <= 0)
		            return "";
		        Date date = new Date(va);
                return dateFormat.format(date);
		    }
		    else 
            {
                return obj.toString();
            }
            
		} catch (Exception e) {
			// e.printStackTrace();
			return obj.toString();
		}
	}

	/**
	 * @param rowid
	 * @param colName
	 * @return Date
	 */
	public Date getDate(int rowid, String colName, String property) {
		return (Date) getValue(rowid, colName, property);
	}

	/**
	 * @param rowid
	 * @param colName
	 * @return Date
	 */
	public String getFormatDate(int rowid, String colName, String property,
			String format) {
		Object obj = getValue(rowid, colName, property);
		// System.out.println("colName " + colName + ":"+ property+ ": "+obj);
		if (obj == null)
			return null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			Date date = (Date) obj;

			return dateFormat.format(date);
		} catch (Exception e) {
			// e.printStackTrace();
			return obj.toString();
		}
	}

	/**
	 * @param rowid
	 * @param columnid
	 * @param property
	 * @return Date
	 */
	public Date getDate(int rowid, int columnid, String property) {
		return (Date) getValue(rowid, columnid, property);
	}

	/**
	 * ��ȡ��ʽ���������ַ���
	 * 
	 * @param rowid
	 * @param columnid
	 * @param property
	 * @param format
	 * @return String
	 */

	public String getFormatDate(int rowid, int columnid, String property,
			String format) {
		Object obj = getValue(rowid, columnid, property);
		// System.out.println("column " + columnid + ":"+ property+ ": "+obj);
		if (obj == null)
			return null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			Date date = (Date) obj;

			return dateFormat.format(date);
		} catch (Exception e) {
			// e.printStackTrace();
			return obj.toString();
		}

	}

	/**
	 * @param rowid
	 * @param columnid
	 * @return char
	 */
	public char getChar(int rowid, int columnid) {
		return ((Character) getValue(rowid, columnid)).charValue();
	}

	/**
	 * @param rowid
	 * @param colName
	 * @return char
	 */
	public char getChar(int rowid, String colName) {
		return ((Character) getValue(rowid, colName)).charValue();
	}

	/**
	 * @param rowid
	 * @param columnid
	 * @return long
	 */
	public long getLong(int rowid, int columnid) {
		return ((Long) getValue(rowid, columnid)).longValue();
	}

	/**
	 * @param rowid
	 * @param colName
	 * @return long
	 */
	public long getLong(int rowid, String colName) {
		return ((Long) getValue(rowid, colName)).longValue();
	}

	/**
	 * @param rowid
	 * @param columnid
	 * @return short
	 */
	public short getShort(int rowid, int columnid) {
		return ((Short) getValue(rowid, columnid)).shortValue();
	}

	/**
	 * @param rowid
	 * @param colName
	 * @return short
	 */
	public short getShort(int rowid, String colName) {
		return ((Short) getValue(rowid, colName)).shortValue();
	}

	/**
	 * @param rowid
	 * @param columnid
	 * @return double
	 */
	public String getFormatData(int rowid, int columnid, String format) {
		NumberFormat numerFormat = new DecimalFormat(format);
		Object data = getValue(rowid, columnid);
		if (data == null)
			return null;
		// double value = dd.doubleValue();

		return numerFormat.format(data);
	}

	/**
	 * @param rowid
	 * @param colName
	 * @return double
	 */
	public String getFormatData(int rowid, String colName, String format) {
		NumberFormat numerFormat = new DecimalFormat(format);
		Object data = getValue(rowid, colName);
		if (data == null)
			return null;
		// double value = dd.doubleValue();

		return numerFormat.format(data);
	}
	
	
	/**
	 * @param rowid
	 * @param colName
	 * @return double
	 */
	public String getFormatData(int rowid, String format) {
		NumberFormat numerFormat = new DecimalFormat(format);
		Object data = getValue(rowid);
		if (data == null)
			return null;
		// double value = dd.doubleValue();

		return numerFormat.format(data);
	}

	/**
	 * @param rowid
	 * @param columnid
	 * @return double
	 */
	public String getFormatData(int rowid, int columnid, String property,
			String format) {
		NumberFormat numerFormat = new DecimalFormat(format);
		Object data = getValue(rowid, columnid, property);
		if (data == null)
			return null;
		// double value = dd.doubleValue();

		return numerFormat.format(data);
	}

	/**
	 * @param rowid
	 * @param colName
	 * @return double
	 */
	public String getFormatData(int rowid, String colName, String property,
			String format) {
		NumberFormat numerFormat = new DecimalFormat(format);
		Object data = getValue(rowid, colName, property);
		if (data == null)
			return null;
		// double value = dd.doubleValue();

		return numerFormat.format(data);
	}

	/**
	 * @param rowid
	 * @param columnid
	 * @return double
	 */
	public double getDouble(int rowid, int columnid) {
		return ((Double) getValue(rowid, columnid)).doubleValue();
	}

	/**
	 * @param rowid
	 * @param columnid
	 * @return double
	 */
	public double getDouble(int columnid) {
		return ((Double) getValue(rowid, columnid)).doubleValue();
	}

	/**
	 * @param rowid
	 * @param colName
	 * @return double
	 */
	public double getDouble(int rowid, String colName) {
		return ((Double) getValue(rowid, colName)).doubleValue();
	}

	/**
	 * @param rowid
	 * @param colName
	 * @return double
	 */
	public double getDouble(String colName) {
		return ((Double) getValue(rowid, colName)).doubleValue();
	}

	/**
	 * @param rowid
	 * @param columnid
	 * @return double
	 */
	public double getDouble(int rowid, int columnid, String property) {
		return ((Double) getValue(rowid, columnid, property)).doubleValue();
	}

	/**
	 * @param rowid
	 * @param colName
	 * @return double
	 */
	public double getDouble(int rowid, String colName, String property) {
		return ((Double) getValue(rowid, colName, property)).doubleValue();
	}

	/**
	 * @param rowid
	 * @param columnid
	 * @return float
	 */
	public float getFloat(int rowid, int columnid) {
		return ((Float) getValue(rowid, columnid)).floatValue();
	}

	/**
	 * @param rowid
	 * @param colName
	 * @return float
	 */
	public float getFloat(int rowid, String colName) {
		return ((Float) getValue(rowid, colName)).floatValue();
	}

	/**
	 * @param rowid
	 * @param colName
	 * @return boolean
	 */
	public boolean getBoolean(int rowid, String colName) {
		return ((Boolean) getValue(rowid, colName)).booleanValue();
	}

	/**
	 * @param rowid
	 * @param colName
	 * @return boolean
	 */
	public boolean getBoolean(String colName) {
		return ((Boolean) getValue(rowid, colName)).booleanValue();
	}

	/**
	 * @param rowid
	 * @param columnid
	 * @return boolean
	 */
	public boolean getBoolean(int rowid, int columnid) {
		return ((Boolean) getValue(rowid, columnid)).booleanValue();
	}

	/**
	 * @param rowid
	 * @param columnid
	 * @return boolean
	 */
	public boolean getBoolean(int columnid) {
		return ((Boolean) getValue(rowid, columnid)).booleanValue();
	}

	/**
	 * ����dataInfo�ӿڷ�������ȡ���� ����dataSet�е����ж���
	 * 
	 * @param dataInfo
	 * @param isList
	 * @throws LoadDataException
	 */
	protected void loadClassData(DataInfo dataInfo, boolean isList)
			throws LoadDataException {
		// List list;
		if (dataInfo == null)
			throw new LoadDataException(
					"load list Data error in loadClassData(DataInfo dataInfo, boolean isList):���ݶ���Ϊ��");
		// log.info();
		// Class voClass = dataInfo.getVOClass();
		/**
		 * ͨ��dataInfo�ӿڻ�ȡ���ݷ�Ϊ���������һ���ǲ���DefaultDataInfoImplʵ�֣�������ΪDataInfoImplʵ��
		 * �ڴ˴�Ҫ����Ӧ���жϣ��ֱ���
		 */
		if (dataInfo instanceof DefaultDataInfoImpl) {
			if (isList)
				loadClassData(dataInfo.getListItemsFromDB(), dataInfo
						.getVOClass());
			else
				loadClassData(dataInfo.getPageItemsFromDB(), dataInfo
						.getVOClass());
		} else {
			Object datas = null;
			if (isList)
			{
				datas = dataInfo.getListItems();
				
			}
			// list = dataInfo.getListItems();
			else {
				datas = dataInfo.getPageItems();				
				// list = dataInfo.getPageItems();
			}
			if(datas instanceof List)
			{
				loadClassData(((List)datas).iterator(), dataInfo
						.getVOClass());
			}
			else
			{
				loadClassData((Object[])datas, dataInfo
						.getVOClass());
			}
		}
		// :log
	}

	/**
	 * װ��hashtable�����е����ݣ�ͨ�����Ի�ȡhashtable�е�����ʱ������ȫ���û�Ϊ��д
	 * 
	 * @param datas
	 * @param voClazz
	 */
	protected void loadClassData(Map[] datas, Class voClazz) {
		// modified by biaoping.yin on 2005.03.28
		if (datas == null)
			return;
		if (theClassDataList == null)
			theClassDataList = new ClassDataList();
		for (int i = 0; i < datas.length; i++) {
			theClassDataList.add(new ClassData(datas[i]));
		}

	}

	/**
	 * װ��Map�����е����ݣ� ͨ�����Ի�ȡmap�е�����ʱ������toUpercase�����������Ƿ�����ȫ���û�Ϊ��д
	 * 
	 * @param datas
	 * @param toUpercase
	 * @param voClazz
	 */
	protected void loadClassData(Map[] datas, Class voClazz,
			boolean toUpercase) {
		// modified by biaoping.yin on 2005.03.28
		if (datas == null)
			return;
		
		if (theClassDataList == null)
			theClassDataList = new ClassDataList();
		for (int i = 0; i < datas.length; i++) {
			theClassDataList.add(new ClassData(datas[i], toUpercase));
		}

	}

	/**
	 * װ��map�е����ݣ�����ͨ�����Ի�ȡʱ��������Խ��д�д�û�
	 * 
	 * @param datas
	 * @param voClazz
	 */
	protected void loadClassData(Map data, Class voClazz) {
		// modified by biaoping.yin on 2007.02.4
		if (data == null)
			return;
		if (theClassDataList == null)
			theClassDataList = new ClassDataList();
		theClassDataList.add(new ClassData(data, false));
		// for(int i = 0; i < datas.length; i ++)
		// {
		// theClassDataList.add(new ClassData(datas[i]));
		// }

	}
	
	/**
	 * װ��map�е����ݣ�����map��ǩ����չʾmap�е�����
	 * key��ǩ�����ڵ��������л��߶�Ӧ��keyֵ
	 * cell��ǩ����չʾvalue���������ֵ����value������	 * 
	 * @param data
	 */
	protected void loadMapClassData(Map data,MapTag maptag) {
		
		if (data == null)
			return;
		if (theClassDataList == null)
			theClassDataList = new ClassDataList();
		if(data.size() <=0)
			return;
		
		if(!StringUtil.isEmpty(maptag.getKey()))
		{	
			theClassDataList.add(new ClassData(data.get(maptag.getKey()),maptag.getKey(), false));
		}
		else if(!StringUtil.isEmpty(maptag.getKeycolName()))
		{
			PagerDataSet dataSet = (PagerDataSet)stack.elementAt(stack.size() - 2);
			Object key = maptag.getKeycolName();
			theClassDataList.add(new ClassData(data.get(key),key, false));
		}
		else if(maptag.isKeycell())
		{
			PagerDataSet dataSet = (PagerDataSet)stack.elementAt(stack.size() - 2);
			Object key = dataSet.getValue(dataSet.getRowid());
			theClassDataList.add(new ClassData(data.get(key),key, false));
		}
		else
		{
			Iterator its = data.entrySet().iterator();
			while(its.hasNext())
			{
				Map.Entry ent = (Map.Entry)its.next();
				theClassDataList.add(new ClassData(ent.getValue(),ent.getKey(), false));
			}
		}
		// for(int i = 0; i < datas.length; i ++)
		// {
		// theClassDataList.add(new ClassData(datas[i]));
		// }

	}

	/**
	 * װ�ض������������е�����
	 * 
	 * @param datas
	 * @param voClazz
	 */
	protected void loadClassData(Object[] datas) {
		// modified by biaoping.yin on 2005.03.28
		if (datas == null)
			return;
		if (theClassDataList == null)
			theClassDataList = new ClassDataList();
//		Class valueClass = null;
		for (int i = 0; i < datas.length; i++) {
//			if (valueClass == null)
//				valueClass = datas[i].getClass();
			theClassDataList.add(new ClassData(datas[i]));
		}

	}
	
	/**
	 * װ�ض������������е�����
	 * 
	 * @param datas
	 * @param voClazz
	 */
	protected void loadClassData(Object[] datas,Class clazz) {
		loadClassData(datas);
//		// modified by biaoping.yin on 2005.03.28
//		if (datas == null)
//			return;
//		if (theClassDataList == null)
//			theClassDataList = new ClassDataList();
////		Class valueClass = null;
//		for (int i = 0; i < datas.length; i++) {
////			if (valueClass == null)
////				valueClass = datas[i].getClass();
//			theClassDataList.add(new ClassData(datas[i]));
//		}

	}

	/**
	 * 
	 * @param dataInfo
	 * @param voClazz
	 */
	protected void loadClassData(Iterator dataInfo, Class voClazz)
			throws LoadDataException {
		if (dataInfo == null)
			throw new LoadDataException(
					"load list Data error loadClassData(Iterator dataInfo, Class voClazz):���ݶ���Ϊ��");
		// log.info();
		while (dataInfo.hasNext()) {
			this.loadClassData(dataInfo.next(), voClazz);
		}

	}

	/**
	 * װ�ؼ����е�����
	 * 
	 * @param dataInfo
	 */
	protected void loadClassData(Collection dataInfo) throws LoadDataException {
		if (dataInfo == null)
			throw new LoadDataException(
					"load list Data error loadClassData(Collection dataInfo):���ݶ���Ϊ��");
		
		
			Iterator it = dataInfo.iterator();
			
			if (theClassDataList == null)
				theClassDataList = new ClassDataList();
			while (it.hasNext()) {
				theClassDataList.add(new ClassData(dataInfo));
			}
				/**
				 * ���µĴ����ȡ�������ݽ�������
				 */
				// sortKey = getSortKey();
				// sortBy(getSortKey(),desc());
			
		
	}

	/**
	 * 
	 * @param dataInfo
	 * @param voClazz
	 */
	protected void loadClassData(Object dataInfo, Class voClazz)
			throws LoadDataException {
		if (dataInfo == null)
			throw new LoadDataException(
					"load list Data error in loadClassData(Object dataInfo, Class voClazz):���ݶ���Ϊ��");
		// log.info();
//		Field[] fields = voClazz == null ? null : voClazz.getFields();
//		Method[] methods = voClazz == null ? null : voClazz.getMethods();
		if (theClassDataList == null)
			theClassDataList = new ClassDataList();
//		if(dataInfo instanceof Map)
//		{
//			theClassDataList.add(new ClassData((Map)dataInfo,false));
//		}
//		else
//		{
//			theClassDataList.add(new ClassData(dataInfo));
//		}
		theClassDataList.add(new ClassData(dataInfo));
			

	}

	/**
	 * װ�ص������е����ݶ���
	 * 
	 * @param dataInfo
	 * 
	 */
	protected void loadClassData(Iterator dataInfo) throws LoadDataException {
		if (dataInfo == null)
			throw new LoadDataException(
					"load list Data error in loadClassData(Object dataInfo, Class voClazz):���ݶ���Ϊ��");
		// log.info();

		if (theClassDataList == null)
			theClassDataList = new ClassDataList();
		while (dataInfo.hasNext()) {
			Object data = dataInfo.next();
			theClassDataList.add(new ClassData(data));
		}

	}

	/**
	 * װ�ؿ����ݣ���ϸ��ʾҳ���Ͽ���
	 */
	protected void loadClassDataNull() {
		if (theClassDataList == null)
			theClassDataList = new ClassDataList();
		theClassDataList.add(new ClassData(null));
	}

	// /**
	// * ����bug
	// * װ�����ݣ��������ݻ�ȡ�ӿڵ���Ӧ������ȡ���ݼ���Ȼ��װ��
	// * @param dataInfo
	// */
	// protected void loadClassData(Object dataInfo) throws LoadDataException {
	// if (dataInfo == null)
	// {
	// log.debug("load Data in loadClassData(Object dataInfo):���ݶ���Ϊ��");
	// if (theClassDataList == null)
	// theClassDataList = new ClassDataList();
	// theClassDataList.add(new ClassData(null, null, null));
	//
	// // throw new LoadDataException(
	// // "load list Data error in loadClassData(Object dataInfo):���ݶ���Ϊ��");
	// }
	// else if(dataInfo instanceof DefaultDataInfoImpl) //log.info();
	// //���dataInfoΪDefaultDataInfoImpl����ʱֱ�ӵ���
	// {
	//
	// DataInfo defaultDataInfo = (DefaultDataInfoImpl)dataInfo;
	// //��ҳ�б����ʽpagerTag����Ϊ�գ�������ϸ��Ϣ��ʾʱ�ͻ�Ϊ��
	// if(pagerContext != null)
	// {
	// defaultDataInfo.initial(statement,
	// dbname,
	// -1,
	// -1,
	// pagerContext.ListMode(),
	// null);
	// }
	// else
	// {
	//
	// defaultDataInfo.initial(statement,
	// dbname,
	// -1,
	// -1,
	// true,
	// null);
	// }
	// loadClassData(defaultDataInfo.getListItemsFromDB(),null);
	// }
	// else
	// {
	// Class voClazz = dataInfo.getClass();
	// Field[] fields = voClazz == null ? null : voClazz.getFields();
	// Method[] methods = voClazz == null ? null : voClazz.getMethods();
	//
	// if (theClassDataList == null)
	// theClassDataList = new ClassDataList();
	// theClassDataList.add(new ClassData(dataInfo, methods, fields));
	// }
	//
	//
	//
	// }
	/**
	 * �������index ��λfields�������±�Ϊindex����������ظ���
	 * 
	 * �쳣���� ����Խ���쳣
	 * 
	 * @param index
	 * @return java.lang.String
	 */
	protected String locateField(int index) {
		if (index >= fields.length || index < 0)
			return null;
		return fields[index];
	}

	/**
	 * ��classDatas�еĶ������򣬷�������ֱ�ӵ���classDatas.sortby(sortKey,desc)������?
	 * 
	 * desc����������������� true :���� false:����
	 * 
	 * sortKey�����������ֶ�
	 * 
	 * @param sortKey -
	 *            ����ؼ���
	 * @param desc
	 */
	public void sortBy(String sortKey, boolean desc) {
		if (theClassDataList != null)
			theClassDataList.sortBy(sortKey, desc);
		else
			// :log
			log.info("û��Ҫ�����б����ݣ�" + PagerDataSet.class.getName() + ".sort("
					+ sortKey + "," + desc + ")");
	}

	/**
	 * ��ȡ��¼����
	 * 
	 * @return int
	 */
	public int size() {
		if(!this.needPeak())
		{
			return theClassDataList == null ? 0 : theClassDataList.size();
		}
		else
		{
			if(this.stack.size() == 0)
			{
				return 0;
			}
			else
			{
				PagerDataSet dataSet = (PagerDataSet)stack.peek();
				return dataSet.size();
			}
		}
	}

	/**
	 * @param rowid
	 * @param columnid
	 * @return byte
	 */
	public byte getByte(int rowid, int columnid) {
		return ((Byte) getValue(rowid, columnid)).byteValue();
	}

	/**
	 * @param rowid
	 * @param colName
	 * @return byte
	 */
	public byte getByte(int rowid, String colName) {
		return ((Byte) getValue(rowid, colName)).byteValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.frameworkset.common.tag.BaseTag#generateContent()
	 */
	public String generateContent() {

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.frameworkset.common.tag.BaseTag#write(java.io.OutputStream)
	 */
	public void write(OutputStream output) {

	}

	public void push() {
		HttpServletRequest request = this.getHttpServletRequest();
		stack = (Stack) request.getAttribute(PAGERDATASET_STACK);
		if (stack == null) {
			stack = new Stack();
			request.setAttribute(PAGERDATASET_STACK, stack);
		}
		stack.push(this);
		// //���渱��
		// if(isExportMeta())
		// savecopy();
	}

	// /**
	// * ��pagerContext�б���pageDataSet�ĸ�����������ҳ�湦��ʹ�����ݼ���
	// */
	// protected void savecopy()
	// {
	// this.pagerContext.dataSets.push(this);
	// }

	public PagerDataSet pop() {
		PagerDataSet obj = (PagerDataSet) stack.pop();
		HttpServletRequest request = this.getHttpServletRequest();
		if (stack.size() == 0)
			request.removeAttribute(PAGERDATASET_STACK);
		return obj;

	}

	/**
	 * �ָ���dataSet����
	 * 
	 */
	protected void recoverParentDataSet() {
		if (stack.size() > 0) {
			PagerDataSet dataSet = (PagerDataSet) stack.peek();
			this.pageContext.setAttribute(this.getDataSetName(), dataSet);
			this.pageContext.setAttribute(this.getRowidName(), dataSet.getRowid() + "");
		}
	}

	protected int index() {
		return index;
	}

	/**
	 * ����index��ȡ��Ӧλ���ϵ�
	 * 
	 * @param index
	 * @return PagerDataSet
	 */
	public PagerDataSet getPagerDataSet(int index) {
		HttpServletRequest request = this.getHttpServletRequest();
		java.util.Stack stack = (java.util.Stack) request
				.getAttribute(PagerDataSet.PAGERDATASET_STACK);
		return (PagerDataSet) stack.elementAt(index);
	}

	/**
	 * ����ҳ���ϵ����ݼ������ָ�����������ȡ��������Ӧ�����ݼ������򷵻����ǩobj��������ݼ�
	 * 
	 * @param obj
	 * @param clazz
	 * @return PagerDataSet
	 */
	protected PagerDataSet searchDataSet(Tag obj, Class clazz) {
		PagerDataSet dataSet = null;
		if (this.getIndex() < 0) {
			dataSet = (PagerDataSet) findAncestorWithClass(obj, clazz);
		} else {
			dataSet = getPagerDataSet(getIndex());
			// java.util.Stack stack =
			// (java.util.Stack) request.getAttribute(
			// PagerDataSet.PAGERDATASET_STACK);
			// dataSet = (PagerDataSet) stack.elementAt(getIndex());
		}
		return dataSet;
	}
    public static int consumeCookie(String cookieid,int defaultsize,HttpServletRequest request,PagerContext pagerContext) {
		
		Cookie[] cookies = getPageCookies(request);
		Cookie cookie;
		
		for (int i = 0; i < cookies.length; i++) {
			cookie = cookies[i];
			if (isPagerCookie(cookie)) {				
				if (isCookieForThisPagerTag(cookie,cookieid,pagerContext)) {
						try {
							return Integer.parseInt(cookie.getValue());
						} catch (Exception e) {
							return defaultsize;
						}
					
				}
			}
		}
		return defaultsize;
	}
	
	
	
	
	
    public static  Cookie[] getPageCookies(HttpServletRequest request) {
//		HttpServletRequest request = this.getHttpServletRequest();
//        HttpSession session = request.getSession(false);
		Cookie[] cookies = request.getCookies();
		if (null == cookies) {
			cookies = new Cookie[0];
		}
		return cookies;
	}
	public static final String COOKIE_PREFIX = "pager.";
	public static  boolean isPagerCookie(final Cookie cookie) {
		return 0 == cookie.getName().indexOf(COOKIE_PREFIX)	;
	}
	
	public static  boolean isCookieForThisPagerTag(final Cookie cookie,String cookieid,PagerContext pagerContext) {
		
		if(pagerContext != null && pagerContext.getId() != null)
			return cookie.getName().equals(cookieid);
		else
		{
			return cookie.getName().equals(cookieid);
		}
	}
	

	/**
	 * ��ʼ����pagerContext
	 * 
	 * @throws LoadDataException
	 */
	public void init() throws LoadDataException {
		// ��ʼ��ҳ����������Ϣ
		// if(this.origineTag == null)
		HttpServletRequest request = this.getHttpServletRequest();
		HttpServletResponse response = this.getHttpServletResponse();
		this.pagerContext = new PagerContext(request, response,
				this.pageContext, this);
		// else
		// this.pagerContext = new PagerContext(this.request, this.response,
		// this.pageContext,this.origineTag);

		// /*
		// * id��ֵΪ��pager��,������ǰ��ҳ��������Ļ�����Ȼ���ٽ���ǰ�����������õ�request��pageContext��
		// */
		// if(REQUEST.equals(scope))
		// {
		// this.oldPager = (PagerContext)request.getAttribute(id);
		// request.setAttribute(id,pagerContext);
		// }

		// }
		// else
		// {
		// log.debug("DoStartTag pager_info_" + id);
		// pageContext.setAttribute("pager_info_" + id,this);
		// }
		
		pagerContext.setIsList(this.isList);
		pagerContext.setField(this.field);
		pagerContext.setForm(this.form);
		pagerContext.setId(this.getId());
		pagerContext.setNavindex(this.navindex);
		pagerContext.setPromotion(this.promotion);
		pagerContext.setScope(this.scope);
		pagerContext.setTitle(this.title);
		pagerContext.setMaxIndexPages(this.maxIndexPages);
		pagerContext.setMaxItems(this.maxItems);
		
		
//		Object temp = null;
//		if (requestKey != null) {			
//				temp = request.getAttribute(requestKey);
//		}
//
//		else if (sessionKey != null)
//		{
//			temp = session.getAttribute(sessionKey);
//		}
//		else if (pageContextKey != null) {
//			temp = pageContext.getAttribute(pageContextKey);
//		}

		String baseUri = request.getRequestURI();
		boolean isControllerPager = PagerContext.isPagerMehtod(request); 
		String cookieid = null;
		if(isControllerPager)
		{
//			baseUri = PagerContext.getPathwithinHandlerMapping(request);
			baseUri = PagerContext.getHandlerMappingRequestURI(request);
//			String mappingpath = PagerContext.getHandlerMappingPath(request); 
//			cookieid = this.pagerContext.getId() == null ?COOKIE_PREFIX + mappingpath :COOKIE_PREFIX + mappingpath + "|" +this.pagerContext.getId();
			cookieid = PagerContext.getControllerCookieID(request);
			pagerContext.setUrl(baseUri);
//			ListInfo mvcinfo = (ListInfo)temp;
			int controllerPagerSize = PagerContext.getControllerPagerSize(request);
//			pagerContext.setMaxPageItems(mvcinfo.getMaxPageItems());
//			pagerContext.setCustomMaxPageItems(mvcinfo.getMaxPageItems());
			pagerContext.setMaxPageItems(controllerPagerSize);
			pagerContext.setCustomMaxPageItems(PagerContext.getCustomPagerSize(request));
		}
		else
		{
			pagerContext.setUrl(url);
			cookieid = this.pagerContext.getId() == null ?COOKIE_PREFIX + baseUri :COOKIE_PREFIX + baseUri + "|" +this.pagerContext.getId();
		
			int defaultSize = consumeCookie(cookieid,maxPageItems,request,pagerContext);
			pagerContext.setMaxPageItems(defaultSize);
			pagerContext.setCustomMaxPageItems(maxPageItems);
		}
		pagerContext.setCookieid(cookieid);
		
		pagerContext.setWapflag(this.wapflag);
		pagerContext.setWidth(this.width);
		pagerContext.setIsOffset(this.isOffset);
		pagerContext.setDbname(this.dbname);
		pagerContext.setStatement(this.statement);
		SQLExecutor sqlExecutor = (SQLExecutor)request.getAttribute(sqlparamskey);
		pagerContext.setSQLExecutor(sqlExecutor);
//		pagerContext.setPretoken(pretoken);
//		pagerContext.setEndtoken(endtoken);
		
		pagerContext.setData(this.data);
		
		pagerContext.setIndex(this.index);
		pagerContext.setColName(this.colName);
		pagerContext.setProperty(this.property);
		pagerContext.setRequestKey(this.requestKey);
		pagerContext.setSessionKey(this.sessionKey);
		pagerContext.setPageContextKey(this.pageContextKey);
		

		pagerContext.setUri();
		pagerContext.setContainerid(this.getContainerid());
        pagerContext.setSelector(this.getSelector());
		// params = 0;
		// offset = 0;
		// itemCount = 0;

		// �����Ƿ��������ǽ���
		String desc_key = pagerContext.getKey("desc");

		String t_desc = request.getParameter(desc_key);
		boolean desc = false;
		if (t_desc != null && t_desc.equals("false"))
			desc = false;
		else if (t_desc != null && t_desc.equals("true"))
			desc = true;

		pagerContext.setDesc(desc);
		// ��������ؼ��֣�����ͨ��request.getParameter��ȡ

		String sortKey_key = pagerContext.getKey("sortKey");

		String t_sortKey = request.getParameter(sortKey_key);
		// �����ȡ����sortKeyΪ��ʱ��ͨ��request.getAttribute��ȡ
		if (t_sortKey == null)
			t_sortKey = (String) request.getAttribute(sortKey_key);
		// ��������ȡ����sortKey��Ϊnullʱ������sortKey
		if (t_sortKey != null)
			pagerContext.setSortKey(t_sortKey);

		// pagerContext.setDataInfo();

		pagerContext.init();
		// if(!this.isList)
		// {
		// this.request.setAttribute(this.getId(),pagerContext);
		// }
	}

	public int doStartTag() throws JspException {
		super.doStartTag();
		push();
		setVariable();
		this.formulas = new HashMap();
		/**
		 * ֧�����ݹ���ϵͳ�÷�������Ҫ
		 */
		if (this.pagerContext == null) {
			try {
				init();
				
			} catch (LoadDataException e) {
				if(e.getCause() == null)
					log.info(e.getMessage());
				else
					log.info(e.getCause().getMessage());
				return SKIP_BODY;
			}
			catch (Throwable e) {
				if(e.getCause() == null)
					log.info(e.getMessage());
				else
					log.info(e.getCause().getMessage());
				return SKIP_BODY;
			}
		}
		
		
		
//		flag = true;
		// setMeta();

		// else //�������Ƕ���б�������ͨ�ķ�ҳ/�б���ʾ
		// {

		initField(pagerContext.getFields());

		DataInfo dataInfo = pagerContext.getDataInfo();
		if (dataInfo == null)
			return SKIP_BODY;
		doDataLoading();
		if(this.size() > 0)
			return EVAL_BODY_INCLUDE;
		else
			return SKIP_BODY;

	}

	public void doDataLoading() {
		/**
		 * �õ�ҳ����Ҫ��ʾ��ֵ�������ֶ�
		 * 
		 */
		DataInfo dataInfo = this.pagerContext.getDataInfo();
		try {
			if (dataInfo instanceof ObjectDataInfoImpl) {
				Object data = dataInfo.getObjectData();
				if (data instanceof Collection)
					loadClassData((Collection) data);
				else if (data instanceof Iterator)
					loadClassData((Iterator) data);
				else if (data instanceof Map) {
					if(!(this instanceof MapTag))
					{
						this.loadClassData((Map) data, null);
					}
					else
					{
						loadMapClassData((Map) data,(MapTag)this);
					}
				} 
				else if (data instanceof Map[]) // ���������һ���������飬����ü��ض�������ķ���
				{
					loadClassData((Map[]) data, null, false);
				} 
				else if (data instanceof Object[]) // ���������һ���������飬����ü��ض�������ķ���
				{
					loadClassData((Object[]) data);
				} else {
					loadClassData(data, data.getClass());
				}
			}
			// else if(dataInfo instanceof DefaultDataInfoImpl)
			// {
			// loadClassData(dataInfo);
			// }

			else if (dataInfo instanceof DataInfo)
				loadClassData(dataInfo, pagerContext.ListMode());
			// /**
			// * ���µĴ����ȡ�������ݣ�����ǰҳ�����ݣ���������
			// */
			// sortKey = pagerContext.getSortKey();

		}  catch (LoadDataException e) {
			if(e.getCause() == null)
				log.info(e.getMessage());
			else
				log.info(e.getCause().getMessage());
//			return SKIP_BODY;
		}
		

		if (size() > 0) {

			/**
			 * ���µĴ����ȡ�������ݽ�������
			 */
			// ��ȡ��ǩ�������õ�������
			boolean t_desc = true;
			if (desc == null && pagerContext != null) // �����ǩ����û����������˳���Ҵ���pager��ǩ�����pager��ǩ�л�ȡ����˳��
				t_desc = pagerContext.getDesc();
			if (desc != null) {
				t_desc = new Boolean(desc).booleanValue();
			}
			if (sortKey == null && pagerContext != null)
				sortKey = pagerContext.getSortKey();
			if (sortKey != null && autosort  )
			{
				sortBy(sortKey.trim(), t_desc);
			}
//			return EVAL_BODY_INCLUDE;
		} 
		else
			rowid = -1;
	}

	/**
	 * ��̬������dataSet��Ԫģ��
	 */
	protected void setMeta() {
		if (isExportMeta()) {
			if (dataModel == null) {
				// ����Ԫ������Ϣ
				dataModel = new DataModel();
				dataModel.setField(this.getColName());
				dataModel.setIndex(this.getIndex());
				dataModel.setProperty(this.getProperty());
			}
			// /**
			// * ����dataModel
			// */
			// if(!dataModel.isHasAdded())
			// {
			// //���û�����ϲ��dataSetԪ��������pagerContext�������ӣ�
			// //����ֱ��������dataSetԪ���ݣ���dataModel��ӵ�����
			// if(pagerContext != null &&
			// !pagerContext.getMetaDatas().hasDataModel())
			// {
			// pagerContext.getMetaDatas().addDataModel(dataModel);
			// dataModel.setHasAdded(true);
			// }
			// else
			// {
			// //ֱ�ӱ��浽��һ��dataSet��Ԫģ����
			// PagerDataSet data_father =
			// (PagerDataSet)findAncestorWithClass(this, PagerDataSet.class);
			// data_father.getDataModel().getMetaDatas().addDataModel(dataModel);
			// dataModel.setHasAdded(true);
			// }
			//
			// }

		}
	}

	public void setVariable() {
		pageContext.setAttribute(this.getDataSetName(), this);
		pageContext.setAttribute(this.getRowidName(), rowid + "");
		
	}

	

	public int doAfterBody() {
		if (this.rowid < this.size() - 1) {
			rowid++;
//			pageContext.setAttribute(this.getDataSetName(), this);
			pageContext.setAttribute(this.getRowidName(), rowid + "");

			return EVAL_BODY_AGAIN;
		} else {
			return SKIP_BODY;
		}
	}

	/**
	 * @return ����ؼ���
	 */
	public String getSortKey() {

		return sortKey;
	}

	/**
	 * @param string
	 */
	public void setSortKey(String string) {
		// sortKey = request.getParameter("sortKey");
		// if (sortKey == null && string != null)
		sortKey = string;
	}

	/**
	 * @return ���ݼ�������˳��desc����asc����
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param string
	 */
	public void setDesc(String string) {
		desc = string;
	}

	/**
	 * @return String
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param string
	 */
	public void setType(String string) {
		type = string;
	}

	/**
	 * @return ������������
	 */
	public String getColName() {
		return colName;
	}

	// /**
	// * @return
	// */
	// public String getProperty() {
	// return property;
	// }

	/**
	 * @param string
	 */
	public void setColName(String string) {

		colName = string;
	}

	// /**
	// * @param string
	// */
	// public void setProperty(String string) {
	// property = string;
	// }

	/**
	 * @return ���ݼ���Ӧ������������
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * @param string
	 */
	public void setProperty(String string) {
		property = string;
	}
	
	public int doEndTag() throws JspException {
//		System.out.println("this=" + this);
//		System.out.println("dataSet doendtag();");
//		System.out.println("flag=" + flag);
		this.removeVariable();
		clear();
//		if(flag)
		recoverParentDataSet();
//		flag = false;
		//if(declare)
		
		this.declare = true;

		this.formulas = null;
		this.sortKey = null;
		this.desc = null;
//		pretoken= null;
//        endtoken= null;
		this.sqlparamskey = "sql.params.key";
		/**
		 * added by biaoping.yin on 20080912 start.
		 */
		this.autosort = true;
		this.needClear = false;
		sessionKey = null;

		requestKey = null;

		pageContextKey = null;
		this.containerid = null;
        this.selector = null;
		/**
		 * added by biaoping.yin on 20080912 end.
		 */		
		return super.doEndTag();

	}

	public void removeVariable() {
		// if(id != null && !id.trim().equals(""))
		// {
		// pageContext.removeAttribute("dataSet_" + id);
		// pageContext.removeAttribute("rowid_" + id);
		// }
		// else
		{
			pageContext.removeAttribute(this.getDataSetName());
			pageContext.removeAttribute(this.getRowidName());
		}
	}
	
	/**
	 * ���ݹ���ʱʹ�õķ������ͷŻ��������ͻ�ԭ����������
	 */
	public void cmsClear()
	{
		removeVariable();
		theClassDataList = null;
		// if(index == null || index.trim().equals(""))

		rowid = 0;
	}

	/**
	 * 
	 * Description:���ö���theClassDataList���к�rowid ����Ƕ��ʹ������»���� void
	 */
	public void clear() {
		theClassDataList = null;
		// if(index == null || index.trim().equals(""))

		rowid = 0;
//		if(flag)
		pop();
		HttpServletRequest request = this.getHttpServletRequest();
		HttpSession session = request.getSession(false);
		
		// �������
		if (getNeedClear()) {
			if (requestKey != null)
				request.removeAttribute(requestKey);
			else if (session != null && sessionKey != null)
				session.removeAttribute(sessionKey);
			else if (pageContextKey != null)
				pageContext.removeAttribute(pageContextKey);

			/**
			 * ����������ܲ���������Ϊweb�������ܶ�request������Ӧ��wraper��װ��
			 * Ҫ��õ�ʵ�ʵ�request��Ҫͨ���ض��ķ������в���
			 */
			if (!(request instanceof CMSServletRequest))
				this.pagerContext = null;
		}

	}

	/**
	 * ��ȡ���ݼ�����
	 * 
	 * @return ���ݼ�����
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param string
	 */
	public void setIndex(int string) {
		index = string;
	}

	/**
	 * ���ݼ���pageContext�еĻ�������
	 * 
	 * @return String
	 */
	public String getPageContextKey() {
		return pageContextKey;
	}

	/**
	 * ���ݼ���request�еĻ�������
	 * 
	 * @return String
	 */
	public String getRequestKey() {
		return requestKey;
	}

	/**
	 * ���ݼ���session�еĻ�������
	 * 
	 * @return String
	 */
	public String getSessionKey() {
		return sessionKey;
	}

	/**
	 * @param string
	 */
	public void setPageContextKey(String string) {
		pageContextKey = string;
	}

	/**
	 * @param string
	 */
	public void setRequestKey(String string) {
		requestKey = string;
	}

	/**
	 * @param string
	 */
	public void setSessionKey(String string) {
		sessionKey = string;
	}

	/**
	 * ��ȡ�Ƿ���Ҫ�Զ�������ݼ�����
	 * 
	 * @return boolean false����Ҫ��true��Ҫ
	 */
	public boolean getNeedClear() {

		return needClear;
	}

	/**
	 * @param string
	 */
	public void setNeedClear(boolean string) {
		needClear = string;
	}

	/**
	 * @return Returns the dbname.
	 */
	public String getDbname() {
		return dbname;
	}

	/**
	 * @param dbname
	 *            The dbname to set.
	 */
	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	/**
	 * @return Returns the statement.
	 */
	public String getStatement() {
		return statement;
	}

	/**
	 * @param statement
	 *            The statement to set.
	 */
	public void setStatement(String statement) {
		this.statement = statement;
	}

	/**
	 * @return Returns the dataModel.
	 */
	public DataModel getDataModel() {
		return dataModel;
	}

	/**
	 * @param dataModel
	 *            The dataModel to set.
	 */
	public void setDataModel(DataModel dataModel) {
		this.dataModel = dataModel;
	}

	/**
	 * ��ͺ���
	 * 
	 * @param colName
	 * @return Object ���
	 */
	public Object sum(String colName) throws FormulaException {
		int value_i = 0;
		short value_s = 0;
		long value_l = 0;
		double value_d = 0;
		float value_f = 0;
		Object left = null;
		// �������ͣ�0��������
		// �������ͣ�1����������
		int type = 0;
		boolean start = false;
		for (int i = 0; i < this.size(); i++) {
			left = this.getValue(i, colName);
			if (left == null)
				throw new FormulaException("attribute '" + colName
						+ "' is null!");
			if (!start) {
				if (int.class.isInstance(left)
						|| Integer.class.isInstance(left)) {
					type = 0;
					value_i += ((Integer) left).intValue();
				} else if (short.class.isInstance(left)
						|| Short.class.isInstance(left)) {
					type = 1;
					value_s += ((Short) left).shortValue();
				} else if (long.class.isInstance(left)
						|| Long.class.isInstance(left)) {
					type = 2;
					value_l += ((Long) left).longValue();
				} else if (double.class.isInstance(left)
						|| Double.class.isInstance(left)) {
					type = 3;
					value_d += ((Double) left).doubleValue();
				} else if (float.class.isInstance(left)
						|| Float.class.isInstance(left)) {
					type = 4;
					value_f += ((Float) left).floatValue();
				}
				start = true;
			}
			switch (type) {
			case 0:
				value_i += ((Integer) left).intValue();
				break;
			case 1:
				value_s += ((Short) left).shortValue();
				break;
			case 2:
				value_l += ((Long) left).longValue();
				break;
			case 3:
				value_d += ((Double) left).doubleValue();
				break;
			case 4:
				value_f += ((Float) left).floatValue();
				break;
			}
		}

		switch (type) {
		case 0:
			return new Integer(value_i);
		case 1:
			return new Short(value_s);
		case 2:
			return new Long(value_l);
		case 3:
			return new Double(value_d);
		case 4:
			return new Float(value_f);
		default:
			throw new FormulaException("attribute '" + colName
					+ "' must be a number!");
		}
	}

	/**
	 * ��ͺ���
	 * 
	 * @param colName
	 * @param property
	 * @return Object ���
	 */
	public Object sum(String colName, String property) throws FormulaException {
		int value_i = 0;
		short value_s = 0;
		long value_l = 0;
		double value_d = 0;
		float value_f = 0;
		Object left = null;
		// �������ͣ�0��������
		// �������ͣ�1����������
		int type = 0;
		boolean start = false;
		for (int i = 0; i < this.size(); i++) {
			left = this.getValue(i, colName, property);

			if (!start) {
				if (int.class.isInstance(left)
						|| Integer.class.isInstance(left)) {
					type = 0;
					value_i += ((Integer) left).intValue();
				} else if (short.class.isInstance(left)
						|| Short.class.isInstance(left)) {
					type = 1;
					value_s += ((Short) left).shortValue();
				} else if (long.class.isInstance(left)
						|| Long.class.isInstance(left)) {
					type = 2;
					value_l += ((Long) left).longValue();
				} else if (double.class.isInstance(left)
						|| Double.class.isInstance(left)) {
					type = 3;
					value_d += ((Double) left).doubleValue();
				} else if (float.class.isInstance(left)
						|| Float.class.isInstance(left)) {
					type = 4;
					value_f += ((Float) left).floatValue();
				}
				start = true;
			}
			switch (type) {
			case 0:
				value_i += ((Integer) left).intValue();
				break;
			case 1:
				value_s += ((Short) left).shortValue();
				break;
			case 2:
				value_l += ((Long) left).longValue();
				break;
			case 3:
				value_d += ((Double) left).doubleValue();
				break;
			case 4:
				value_f += ((Float) left).floatValue();
				break;
			}
		}

		switch (type) {
		case 0:
			return new Integer(value_i);
		case 1:
			return new Short(value_s);
		case 2:
			return new Long(value_l);
		case 3:
			return new Double(value_d);
		case 4:
			return new Float(value_f);
		default:
			throw new FormulaException("attribute '" + colName + "." + property
					+ "' must be a number!");
		}
	}

	/**
	 * ��ƽ��ֵ
	 * 
	 * @param colName
	 * @return float
	 */
	public float avg(String colName) throws FormulaException {
		Object left = this.sum(colName);
		float count = (float) this.count(colName);
		if (int.class.isInstance(left) || Integer.class.isInstance(left)) {
			return ((Integer) left).floatValue() / count;
		} else if (short.class.isInstance(left) || Short.class.isInstance(left)) {
			return ((Short) left).floatValue() / count;
		} else if (long.class.isInstance(left) || Long.class.isInstance(left)) {
			return ((Long) left).floatValue() / count;
		} else if (double.class.isInstance(left)
				|| Double.class.isInstance(left)) {

			return ((Double) left).floatValue() / count;
		} else if (float.class.isInstance(left) || Float.class.isInstance(left)) {
			return ((Float) left).floatValue() / count;
		}
		return 0.0f;

	}

	/**
	 * ��ƽ��ֵ
	 * 
	 * @param colName
	 * @param property
	 * @return float
	 */

	public float avg(String colName, String property) throws FormulaException {
		Object left = this.sum(colName, property);
		float count = (float) this.count(colName, property);
		if (int.class.isInstance(left) || Integer.class.isInstance(left)) {
			return ((Integer) left).floatValue() / count;
		} else if (short.class.isInstance(left) || Short.class.isInstance(left)) {
			return ((Short) left).floatValue() / count;
		} else if (long.class.isInstance(left) || Long.class.isInstance(left)) {
			return ((Long) left).floatValue() / count;
		} else if (double.class.isInstance(left)
				|| Double.class.isInstance(left)) {

			return ((Double) left).floatValue() / count;
		} else if (float.class.isInstance(left) || Float.class.isInstance(left)) {
			return ((Float) left).floatValue() / count;
		}
		return 0.0f;

	}

	/**
	 * ��������
	 * 
	 * @param colName
	 * @return int
	 */
	public int count(String colName) {
		return this.size();
	}

	/**
	 * ��������
	 * 
	 * @param colName
	 * @param property
	 * @return int
	 */
	public int count(String colName, String property) {
		return this.size();
	}

	/**
	 * ��ȡformula����Ӧ�� ���ʽ����
	 * 
	 * @param formula
	 * @return Formula
	 */
	public Formula getFormula(String formula) {
		if (formula == null)
			return null;
		
		if(this.formulas == null)
			this.formulas = new HashMap();
		Formula f = (Formula) formulas.get(formula);
		if (f != null)
		{
//			f.setDataSet(this);
			return f;
		}
		f = new Formula(this, formula);
		formulas.put(formula, f);
		return f;
		
	}

	public boolean isDeclare() {
		return declare;
	}

	public void setDeclare(boolean declare) {
		this.declare = declare;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public String getNavindex() {
		return navindex;
	}

	public void setNavindex(String navindex) {
		this.navindex = navindex;
	}

	public boolean isOffset() {
		return isOffset;
	}

	public void setOffset(boolean isOffset) {
		this.isOffset = isOffset;
	}

	public int getItems() {
		return items;
	}

	public void setItems(int items) {
		this.items = items;
	}

	public int getMaxIndexPages() {
		return maxIndexPages;
	}

	public void setMaxIndexPages(int maxIndexPages) {
		this.maxIndexPages = maxIndexPages;
	}

	public int getMaxItems() {
		return maxItems;
	}

	public void setMaxItems(int maxItems) {
		this.maxItems = maxItems;
	}

	public int getMaxPageItems() {
		return maxPageItems;
	}

	public void setMaxPageItems(int maxPageItems) {
		this.maxPageItems = maxPageItems;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isWapflag() {
		return wapflag;
	}

	public void setWapflag(boolean wapflag) {
		this.wapflag = wapflag;
	}

	public void setIsList(boolean isList) {
		this.isList = isList;
	}

	

	public String getDataSetName() {
		return dataSetName;
	}

	public void setDataSetName(String dataSetName) {
		if(!dataSetName.equals("") && !dataSetName.equals("null"))
			this.dataSetName = dataSetName;
	}

	public String getRowidName() {
		return rowidName;
	}

	public void setRowidName(String rowidName) {
		if(!rowidName.equals("") && !rowidName.equals("null"))
			this.rowidName = rowidName;
	}
	
	/**
	 * ��ȡ�ⲿչʾ���к�
	 * @param offset �Ƿ�ʹ��ƫ���������ʹ����ӵ�ǰҳ�����ʼλ�ÿ�ʼ�������0��ʼ
	 * @param increament ƫ����������ֵ
	 * @return
	 */
	public int getOuterRowid(boolean offset,int increament)
	{
		
		PagerContext pagerContext = getPagerContext();
		if(pagerContext == null)
			return -1;
		if(offset)
			return getRowid() + increament;
		else
			return  (int)(pagerContext.getOffset() + getRowid() + increament);
	}
	
	/**
	 * ��ȡ��ǰ���кţ�ÿҳ�����㿪ʼ
	 * @return
	 */
	public int getRowid() {
//		PagerContext pagerContext = getPagerContext();
		if(this.pagerContext == null)
		{
			if(stack.size() == 0)
				return -1;
			PagerDataSet dataSet = (PagerDataSet)stack.peek();
			return dataSet.getRowid();
			
		}
		return this.rowid;
	}
	/**
	 * ��ȡǶ��list�����Ŷ�Ӧ��list���ⲿչʾ�к�
	 * @param index Ƕ��list���� ��	
	 * @return
	 */
	public int getRowidByIndex(int index) {
		if (index >= stack.size() || index < 0) {
			log.debug("Get [" + colName + "] error: Out of index[" + index
					+ "],stack size is " + stack.size());
			return -1;
		}
		// ����������ȡ�ֶε�ֵ
		PagerDataSet dataSet = (PagerDataSet) this.stack.elementAt(index);
		return dataSet.getRowid();
	}
	
	/**
	 * ��ȡǶ��list�����Ŷ�Ӧ��list���ⲿչʾ�к�
	 * @param index Ƕ��list���� ��
	 * @param offset �Ƿ�ʹ��ƫ���������ʹ����ӵ�ǰҳ�����ʼλ�ÿ�ʼ�������0��ʼ
	 * @param increament ƫ����������ֵ
	 * @return
	 */
	public int getOutRowidByIndex(int index,boolean offset,int increament) {
		if (index >= stack.size() || index < 0) {
			log.debug("Get [" + colName + "] error: Out of index[" + index
					+ "],stack size is " + stack.size());
			return -1;
		}
		// ����������ȡ�ֶε�ֵ
		PagerDataSet dataSet = (PagerDataSet) this.stack.elementAt(index);		
		return dataSet.getOuterRowid( offset, increament);
	}
	
	
	/**
	 * ��ȡҳ��ļ�¼��ʼ��ַ
	 * @return
	 */
	public int getOffset()
	{
		PagerContext pagerContext = getPagerContext();
		if(pagerContext == null)
			return -1;
		return (int)this.pagerContext.getOffset();
	}

	public boolean isPromotion() {
		return promotion;
	}

	public void setPromotion(boolean promotion) {
		this.promotion = promotion;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public boolean isAutosort() {
		return autosort;
	}

	public void setAutosort(boolean autosort) {
		this.autosort = autosort;
	}

	public String getSqlparamskey() {
		return sqlparamskey;
	}

	public void setSqlparamskey(String sqlparamskey) {
		this.sqlparamskey = sqlparamskey;
	}

    public String getContainerid()
    {
        return containerid;
    }

    public void setContainerid(String containerid)
    {
        this.containerid = containerid;
    }

    public String getSelector()
    {
        return selector;
    }

    public void setSelector(String selector)
    {
        this.selector = selector;
    }

	
	public Object getActual()
	{
	
		return actual;
	}

	
	public void setActual(Object actual)
	{
	
		this.actual = actual;
	}
    
   
    

}
