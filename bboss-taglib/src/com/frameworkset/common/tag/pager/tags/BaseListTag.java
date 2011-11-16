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
package com.frameworkset.common.tag.pager.tags;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.frameworkset.common.tag.BaseTag;
import com.frameworkset.common.tag.pager.DataInfo;

/**
 * 
 * 
 * ��ҳ/�б�tag����ʾ��������ͨ��ʵ��
 * com.frameworkset.common.tag.pager.DataInfo��������ṩ��
 * ��ҳ/�б�tag���request�����õ�dataInfo�����л�ȡ�������
 * BaseListTag�������ǰѸ�������ŵ�dataInfo�����У� 
 * ���dataInfo��������Ϊ��Ĭ������Ϊ��dataInfo��
 * 
 * @author biaoping.yin
 * @version 1.0
 */ 
 
public abstract class BaseListTag extends BaseTag{
	
	/**
	 * ���ݻ�ȡ�ӿ��������ļ��д�ŵ�key����
	 */   
	protected String dataInfo;
	/**����������ȡ�����ŵ�request�е���������,ȱʡֵΪdataInfo��ֵ*/
	protected String keyName;
	/**
	 * ����������ȡ�����ŵ�request�е���������
	 * @return dataInfo ������ȡ�����ŵ�request�е���������
	 */
	public String getDataInfo() {
		if(dataInfo == null)
			return "dataInfo";
		return dataInfo;
	}

	/**
	 * ����������ȡ�����ŵ�request�е���������
	 * @param string
	 */
	public void setDataInfo(String string) {
		dataInfo = string;
	}
	
	/**
	 * ��ʼ����ҳ�б�����ݻ�ȡ�ӿ�
	 * @param dataInfo �����DataInfo�ӿڵ�ʵ����
	 */
	public void initDatainfo(DataInfo dataInfo)
	{		
		HttpServletRequest request = getHttpServletRequest();
//		HttpSession session = request.getSession(false) ;
		if(getKeyName() == null || getKeyName().trim().equals(""))
		{
			request.setAttribute(getDataInfo(),dataInfo);
		}
		else
		{
			request.setAttribute(getKeyName(),dataInfo);			
		}		
	}
	
	/* (non-Javadoc)
		 * @see com.frameworkset.common.tag.BaseTag#generateContent()
		 */
		public String generateContent() {
			// TODO Auto-generated method stub
			return null;
		}

		/* (non-Javadoc)
		 * @see com.frameworkset.common.tag.BaseTag#write(java.io.OutputStream)
		 */
		public void write(OutputStream output) {
			// TODO Auto-generated method stub
		
		}
	

	/**
	 * @return String
	 */
	public String getKeyName() {
		return keyName;
	}

	/**
	 * @param string
	 */
	public void setKeyName(String string) {
		keyName = string;
	}

}
