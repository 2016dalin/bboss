/*****************************************************************************
 *  Created on 2005-3-25                                                     *
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
package com.frameworkset.common.tag.pager;

import com.frameworkset.common.poolman.DBUtil;
import com.frameworkset.common.poolman.PreparedDBUtil;
import com.frameworkset.common.poolman.Record;
import com.frameworkset.common.poolman.SQLParams;
import com.frameworkset.util.ListInfo;

import java.sql.SQLException;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

/**
 * �ṩDataInfo�ӿڵ�ȱʡʵ�֣������ݿ��л�ȡ���ݼ�
 * @author biaoping.yin
 * created on 2005-3-25
 * version 1.0
 */
public class DefaultDataInfoImpl implements DataInfo {

    /**��װҳ�����ݱ���*/
    private ListInfo listInfo = null;
	private int pageItemsize;
    private long offSet;
	protected HttpServletRequest request = null;
	protected HttpSession session = null;

	protected String sql = null;
	protected String dbName = null;
	protected boolean listMode;
	protected boolean first = true;
	private static final Logger log = Logger.getLogger(DefaultDataInfoImpl.class);
	/**
	 * ��ʶ��ѯ�Ƿ���more��ѯ
	 */
	private boolean moreQuery;
	/**
	 * Ԥ���봦�����
	 */
    private SQLParams params;


    /**
     * ����ʵ��
     * @param sortKey
     * @param desc
     * @param offSet
     * @param pageItemsize
     * @param listMode
     * @param request
     */
    public void initial(String sortKey, boolean desc, long offSet,
            int pageItemsize, boolean listMode,HttpServletRequest request) {

    }

    /**
     * ��ʼ����ȡ��ҳ/�б����ݵı�Ҫ����
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
//            			String sortKey,
//            			boolean desc,
            			HttpServletRequest request) {
        initial( sql,
                dbName,
                offSet,
                pageItemsize,
                listMode,
//              String sortKey,
//              boolean desc,
                request,(SQLParams)null);

    }
    
    
    /**
     * ��ʼ����ȡ��ҳ/�б����ݵı�Ҫ����
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
//                      String sortKey,
//                      boolean desc,
                        HttpServletRequest request,SQLParams params) {
        this.sql = sql;
        this.dbName = dbName;
        this.offSet = offSet;
        this.pageItemsize =pageItemsize;
        this.request = request;
        this.params = params;

        if(request != null)
            session = request.getSession(false);
        this.listMode = listMode;
        listInfo = null;
        first = true;

    }
    
    


    /**
     * �����ݿ��л�ȡ��ҳҳ������
     * @see com.frameworkset.common.tag.pager.DataInfo#getPageItemsFromDB()
     */
    public Object[] getPageItemsFromDB() {

        if(first)
        {
            listInfo = getDataFromDB(sql,dbName,offSet,pageItemsize);
            first = false;
        }
        if(listInfo == null)
            return null;
        return listInfo.getArrayDatas();
    }

    /**
     * �����ݿ��л�ȡ�б�����
     * @see com.frameworkset.common.tag.pager.DataInfo#getListItemsFromDB()
     */
    public Object[] getListItemsFromDB() {
        if(first)
        {
            listInfo = getListItemsFromDB(sql,dbName);
            first = false;
        }
        if(listInfo == null)
            return null;
        return listInfo.getArrayDatas();
    }



    /**
     * ��ȡ���ݷ�װ���Class���
     */
    public Class getVOClass() {

        return Hashtable.class;
    }

    /* (non-Javadoc)
     * @see com.frameworkset.common.tag.pager.DataInfo#getItemCount()
     */
    public long getItemCount() {
        if(first)
        {
            if(!listMode)
                listInfo = getDataFromDB(sql,dbName,offSet,pageItemsize);
            else
                listInfo = getListItemsFromDB(sql,dbName);
            first = false;
        }
        if(listInfo == null)
            return 0;
        return listInfo.getTotalSize();
    }

    /**
	* ��ҳ��ʾʱ�����ݿ��ȡÿҳ����������ʵ�ʷ������ݿ�Ĳ���
	* sql:��ѯ���
	* dbName:���ݿ����ӳص�����
	* desc:���������trueΪ����falseΪ����
	* @param offSet - ������Դ��ȡ���ݵ��α�λ��
	*
	* @param pageItemsize - ÿҳ��ʾ����������
	* @return ListInfo
	*/
	protected ListInfo getDataFromDB(String sql,
									 String dbName,
									 long offSet,
									 int pageItemsize)
	{
	    //�������ݿ���ʶ���
	    
	    try {
	        ListInfo listInfo = new ListInfo();
	        if(this.params == null)
	        {
	            DBUtil dbUtil = new DBUtil();
	            Hashtable[] tables = (Hashtable[])dbUtil.executeSelectForObjectArray(dbName,sql,offSet,pageItemsize,Record.class);            
                listInfo.setArrayDatas(tables);
                listInfo.setTotalSize(dbUtil.getLongTotalSize());
                return listInfo;
	        }
	        else
	        {
	            PreparedDBUtil dbUtil = new PreparedDBUtil();
	            dbUtil.preparedSelect(params.copy(), dbName, sql, offSet, pageItemsize);
	            Hashtable[] tables = (Hashtable[])dbUtil.executePreparedForObjectArray(Record.class);            
                listInfo.setArrayDatas(tables);
                listInfo.setTotalSize(dbUtil.getLongTotalSize());
                return listInfo;
	        }
        } catch (SQLException e) {
            log.error(e);
	        return null;
        }

	}
	
	

	/**
	 * ��ɴ����ݿ��ȡ�б���ʾ���ݵ�ʵ�ʲ���
	 */
	public ListInfo getListItemsFromDB(String sql,
			  						   String dbName)
	{
	   
	    
	    try {
	        ListInfo listInfo = new ListInfo();
	        if(this.params == null)
            {
	            //�������ݿ���ʶ���
	            DBUtil dbUtil = new DBUtil();
	            
                Hashtable[] tables = (Hashtable[])dbUtil.executeSelectForObjectArray(dbName,sql,Record.class);
                listInfo.setArrayDatas(tables);
                return listInfo;
            }
	        else
	        {
	          //�������ݿ���ʶ���
                PreparedDBUtil dbUtil = new PreparedDBUtil();
                dbUtil.setMore(this.moreQuery);
                dbUtil.preparedSelect(params.copy(), dbName, sql);
                Hashtable[] tables = (Hashtable[])dbUtil.executePreparedForObjectArray(Record.class);  
                listInfo.setArrayDatas(tables);
                listInfo.setMore(this.moreQuery);
                return listInfo;
	        }
        } catch (SQLException e) {
            log.error(e);
	        return null;
        }
	}

	/**
     * δʵ��
     * @see com.frameworkset.common.tag.pager.DataInfo#getPageItems()
     */
    public Object getPageItems() {

        return null;
    }

    /**
     * δʵ��
     */
    public Object getListItems() {
        return null;
    }

    /* (non-Javadoc)
     * @see com.frameworkset.common.tag.pager.DataInfo#getDataSize()
     */
    public int getDataSize() {
        if(first)
        {
            if(!listMode)
                listInfo = getDataFromDB(sql,dbName,offSet,pageItemsize);
            else
                listInfo = getListItemsFromDB(sql,dbName);
            first = false;
        }
        return listInfo == null
        				|| listInfo.getArrayDatas() == null
        					?0:listInfo.getArrayDatas().length;
    }

	public Object getObjectData() {
		throw new UnsupportedOperationException("getObjectData()");
	}

	public boolean isMoreQuery() {
		return moreQuery;
	}

	public void setMoreQuery(boolean moreQuery) {
		this.moreQuery = moreQuery;
	}

	@Override
	public boolean isMore() {
		if(first)
        {
            if(!listMode)
                listInfo = getDataFromDB(sql,dbName,offSet,pageItemsize);
            else
                listInfo = getListItemsFromDB(sql,dbName);
            first = false;
        }
        if(listInfo == null)
            return moreQuery;
        return listInfo.isMore();
	}
}
