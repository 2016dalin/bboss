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
 *  Author of Learning Java 						     					 *
 *                                                                           *
 *****************************************************************************/
package com.frameworkset.common.tag.pager;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.frameworkset.security.AccessControlInf;
import org.frameworkset.security.SecurityUtil;

import com.frameworkset.common.poolman.SQLParams;
import com.frameworkset.common.tag.BaseTag;
import com.frameworkset.util.ListInfo;

/**
 * @author biaoping.yin
 * tag������Ա��Ҫ�̳�DataInfoImpl�࣬ʵ�����³��󷽷���
 * protected abstract ListInfo getDataList(String sortKey,
										 boolean desc,
										 int offSet,
										 int pageItemsize)���÷�����ҳ����
  protected abstract ListInfo getDataList(String sortKey,
									 boolean desc)���÷����б����

 *
 */
public abstract class DataInfoImpl implements DataInfo
{
    
	private ListInfo listInfo = null;
	String sortKey;
	boolean desc = true;
	int pageItemsize;
    long offSet;
	protected transient HttpServletRequest request = null;
	protected transient  HttpSession session = null;
    protected transient  AccessControlInf accessControl = null;
	protected boolean listMode = false;
	boolean first;

	/**
     * ��ʼ����ȡ��ҳ/�б����ݵı�Ҫ����
     * @param sortKey �����ֶ�
     * @param desc ����˳��true��ʾ����false��ʾ����
     * @param offSet ��ȡ��ҳ����ʱ���øò������û�ȡ���ݵ����
     * @param pageItemsize ��ȡ��ҳ����ʱ���øò������û�ȡ���ݵ�����
     */
	public void initial(String sortKey,
						boolean desc,
						long offSet,
						int pageItemsize,
						boolean listMode,
						HttpServletRequest request)
	{
		this.sortKey = sortKey;
		this.desc = desc;
		this.offSet = offSet;
		this.pageItemsize =pageItemsize;
		this.request = request;
		session = request.getSession(false);
		/**
		 * 2009.07.02 ע�ͣ��������ϵͳƽ̨��Ҫ��
		 */
		if(BaseTag.ENABLE_TAG_SECURITY)
		{
//                    accessControl = AccessControl.getAccessControl();
//                    if(accessControl == null)
//                    {
//                    	accessControl = AccessControl.getInstance();
//                    	accessControl.checkAccess(request,null,null,false);
//                    }
			accessControl = SecurityUtil.getAccessControl(request, null, null);
		}
		listInfo = null;
		this.listMode = listMode;
		first = true;

	}

	/**
     * ����ʵ�֣���ʼ����ȡ��ҳ/�б����ݵı�Ҫ����
     * @param sql ���ݿ��ѯ���
     * @param dbName ���ݿ����ӳ�����
     * @param offSet ��ȡ��ҳ����ʱ���øò������û�ȡ���ݵ����
     * @param pageItemsize ��ȡ��ҳ����ʱ���øò������û�ȡ���ݵ�����
     */
	public void initial(String sql,
	        		    String dbName,
						long offSet,
						int pageItemsize,
						boolean listMode,
						HttpServletRequest request)
	{
	}

	/**
	* ��ҳ��ʾʱ��ȡÿҳ��������
	* sortKey:�����ֶ�
	* desc:���������trueΪ����falseΪ����
	* @param offSet - ������Դ��ȡ���ݵ��α�λ��
	*
	* @param pageItemsize - ÿҳ��ʾ����������
	* @return java.util.List
	*/
	protected abstract ListInfo getDataList(String sortKey,
										 boolean desc,
										 long offSet,
										 int pageItemsize);



	/**
	 * �����ҳtagֻ����Ϊ�б���ʵ��ʱ�����ø÷���
	 * sortKey:�����ֶ�
	 * desc:���������trueΪ����falseΪ����
	 * @return java.util.List
	 */
	protected abstract ListInfo getDataList(String sortKey,
									 boolean desc);
	/**
	 * ʶ���ѯ�Ƿ���more��ҳ��ѯ
	 * @return
	 */
	public boolean isMore()
	{
		if(first)
		{
		    if(!listMode)
		        listInfo = getDataList(sortKey,desc,offSet,pageItemsize);
		    else
		        listInfo = getDataList(sortKey,desc);
		    first = false;
		}
		if(listInfo == null)
			return false;
		return listInfo.isMore();
	}
	/**
	 * tag�е������·�����ȡ��ҳʱ���ܵ������������Ա����ҳ������
	 */
	public long getItemCount()
		//throws Exception
	{
		if(first)
		{
		    if(!listMode)
		        listInfo = getDataList(sortKey,desc,offSet,pageItemsize);
		    else
		        listInfo = getDataList(sortKey,desc);
		    first = false;
		}
		if(listInfo == null)
			return 0;
		return listInfo.getTotalSize();
	}

	/**
	 * tag�е������·�����ȡ��ҳ����
	 */
	public Object getPageItems()
	{
		if(first)
		{
			listInfo = getDataList(sortKey,desc,offSet,pageItemsize);
			first = false;
		}
		if(listInfo == null)
			return new ArrayList();
		Object datas = listInfo.getObjectDatas();
		return datas == null?new ArrayList():datas;
	}

	/**
	 * tag�е������·�����ȡ�б�����
	 */
	public Object getListItems()
	{
	    if(first)
	    {
	        listInfo = getDataList(sortKey,desc);
	        first = false;
	    }
	    if(listInfo == null)
			return new ArrayList();
	    Object datas = listInfo.getObjectDatas();
		return datas == null?new ArrayList():datas;
//		return listInfo.getDatas() == null?new ArrayList():listInfo.getDatas();
	}

	/**
	 * tag�е������·�����ȡ���ص����ݼ�����������ֵ�����Class����
	 */
	public Class getVOClass() {
//		if(listInfo == null
//		     || listInfo.getDatas() == null
//		     || listInfo.getDatas().size() == 0)
//			return null;
//		return listInfo.getDatas().get(0).getClass();
		return null;
	}

	 /**
     * ��ҳ��ʾʱ�����ݿ��ȡÿҳ��������
     * @return Hashtable[]
     */
	public Object[] getPageItemsFromDB()
	{
	    return null;
	}

	/**
     * �����ҳtagֻ����Ϊ�б���ʵ��ʱ�����ø÷��������ݿ��л�ȡ
     * Ҫ��ʾ���б�����
     * @return java.util.List
     */
	public Object[] getListItemsFromDB(){return null;}

	/**
	 * ��ȡ��ǰҳ��ļ�¼����
	 */
	public int getDataSize()
	{
	    if(first)
		{
		    if(!listMode)
		        listInfo = getDataList(sortKey,desc,offSet,pageItemsize);
		    else
		        listInfo = getDataList(sortKey,desc);
		    first = false;
		}
		if(listInfo == null )
			return 0;
		return listInfo.getSize();
	}
	
	/**
	 * ��ȡ��ǰҳ��ļ�¼����
	 */
	public int getDataResultSize()
	{
	    if(first)
		{
		    if(!listMode)
		        listInfo = getDataList(sortKey,desc,offSet,pageItemsize);
		    else
		        listInfo = getDataList(sortKey,desc);
		    first = false;
		}
		if(listInfo == null )
			return 0;
		return listInfo.getResultSize();
	}

	/**
	 * ���б���з�ҳ����,����Դ��һ���б�
	 * @param datas �б�����
	 * @param offset ��ȡ���ݵ���ʼλ��
	 * @param pageItems ��ȡ���ݵ�����
	 * @return ListInfo �Է�ҳ���ݺ��ܼ�¼�����ķ�װ��
	 */

	public static ListInfo pagerList(List datas,int offset,int pageItems)
	{
     
		if(datas == null)
			return null;
		List list = new ArrayList();
		if(offset >= datas.size())
		{
		    int temp = datas.size() % pageItems;
		    offset = datas.size() - temp;
		}
		for(int i = offset; i < datas.size() && i < offset + pageItems; i ++)
		{
		    list.add(datas.get(i));
		}
		ListInfo listInfo = new ListInfo();
		listInfo.setDatas(list);
		listInfo.setTotalSize(datas.size());
		return listInfo;
	}
	
	public Object getObjectData()
	{
		throw new UnsupportedOperationException("getObjectData()");
	}
	
	public void initial(String sql,
            String dbName,
            long offSet,
            int pageItemsize,
            boolean listMode,
//          String sortKey,
//          boolean desc,
            HttpServletRequest request,SQLParams params)
	{
	    
	}
	
}
