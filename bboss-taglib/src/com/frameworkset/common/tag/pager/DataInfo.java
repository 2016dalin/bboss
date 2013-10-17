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

package com.frameworkset.common.tag.pager;

import com.frameworkset.common.poolman.SQLParams;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

/**
 * ҵ���������ʵ�ֵ�3���ӿ�������ȡ����ҵ������ݼ��ϣ�Ϊ��ҳTag�ṩ����
 * ��ȡֵ�����Class���
 * @author biaoping.yin
 * 2005-3-25
 * version 1.0
 */


public interface DataInfo
{
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
							boolean isList,
							HttpServletRequest request);

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
//						String sortKey,
//						boolean desc,
						boolean isList,
						HttpServletRequest request);


    /**
     * �����ҳtagֻ����Ϊ�б���ʵ��ʱ�����ø÷�����ȡ
     * Ҫ��ʾ���б�����
     * @return Object
     */
	public Object getListItems();


    /**
     * ��ҳ��ʾʱ�����ݿ��ȡÿҳ��������
     * @return Hashtable[]
     */
	public Object[] getPageItemsFromDB();

	/**
     * �����ҳtagֻ����Ϊ�б���ʵ��ʱ�����ø÷��������ݿ��л�ȡ
     * Ҫ��ʾ���б�����
     * @return java.util.List
     */
	public Object[] getListItemsFromDB();


    /**
     * ��ҳ��ʾʱ��ȡÿҳ��������
     * @return Object
     */
	public Object getPageItems();

    /**
     * ��ȡֵ�����Class���
     * @return java.lang.Class
     */
    public Class getVOClass();

    /**
     * ��ȡ����Դ�����ݵ�����,��ҳʱ����
     * @return int
     */
    public long getItemCount();

    /**
     * ��ȡ��ǰҳ���¼��
     * @return ��ǰҳ���¼��
     */
    public int getDataSize();
    
    public int getDataResultSize();
    
    /**
     * ��ȡ�������͵�����
     * @return
     */
    public Object getObjectData();
    
    public void initial(String sql,
            String dbName,
            long offSet,
            int pageItemsize,
            boolean listMode,
//          String sortKey,
//          boolean desc,
            HttpServletRequest request,SQLParams params);
    /**
     * ʶ��ǰ��ѯ�Ƿ���more��ҳ�������more��ҳ���ǩ�����������ܼ�¼����Ϣ 
     * @return
     */
    public boolean isMore();
    
//    public boolean isdbdata();
    
    

}
