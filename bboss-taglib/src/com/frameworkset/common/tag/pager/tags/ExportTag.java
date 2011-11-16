/*****************************************************************************
 *                                                                           *
 *  This file is part of the frameworkset distribution.                      *
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
 *  Code is biaoping.yin. Portions created by biaoping.yin are Copyright     *
 *  (C) 2004.  All Rights Reserved.                                          *
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
package com.frameworkset.common.tag.pager.tags;

import javax.servlet.jsp.JspException;

import com.frameworkset.common.tag.export.FileType;
import com.frameworkset.common.tag.pager.DataInfo;
import com.frameworkset.common.tag.pager.model.PageInfo;
import com.frameworkset.common.tag.pager.model.PageModel;

/**
 * �����ļ���ǩ
 * @author biaoping.yin
 * created on 2005-5-25
 * version 1.0 
 */
public class ExportTag extends PagerTagSupport
{
    /**
	 * ���ҳ�浼�����ļ�ʱ���в�������exportMetaΪtrueʱ���ñ�����
	 */
	protected PageInfo pageInfo;	
	
	
	/**
	 * �Ƿ�ȫ�������ļ�
	 */
	protected boolean exportAll = false;
	/**
	 * ȱʡ����Ϊword�ĵ�
	 * �û������������õ�����ʽ
	 */
	protected String type = FileType.WORD;
	

	
    public int doStartTag()    	
    {
//        String keyName = this.id +  ".pageInfo";
//        /**
//		 * �����Ҫ�������ݵ��ļ�
//		 * ���ʼ��pageInfo����
//		 */
//        session.removeAttribute(keyName);
//        if(this.pagerTag.isExportMeta())
//		{
//            pageInfo = new PageInfo();
//		    pageInfo.setDbName(pagerTag.getDbname());
//		    pageInfo.setEportAll(isExportAll());
//		    pageInfo.setList(pagerTag.ListMode());
//		    pageInfo.setMaxPageItems(pagerTag.getMaxPageItems());
//		    pageInfo.setMetaDatas(pagerTag.getMetaDatas());
//		    //ҳ���ѯ���ڱ�ǩ������ʱ��������
//		    pageInfo.setQueryString(pagerTag.getQueryString());
//		    pageInfo.setDbName(pagerTag.getDbname());
//		    DataInfo dataInfo = pagerTag.getDataInfo();
//		    //���´����µ�dataInfo�ӿڣ��Ա��ȡ��������
//		    dataInfo = newDataInfo(dataInfo);
//		    
//		    pageInfo.setDataInfo(dataInfo);
//		    
////		    PageModel pageModel = new PageModel();
////		    
////		    //��ʼ��pageModel
////		    pageModel.setParameter(pageInfo,dataInfo,null,this.pageContext);
//		    /**
//		     * �������е�ҳ�����
//		     */
//		    this.session.setAttribute(keyName,pageInfo);
//		}
        return EVAL_BODY_INCLUDE;        
    }
    
    /**
     * 
     * @param dataInfo
     * @return DataInfo
     */
    private DataInfo newDataInfo(DataInfo dataInfo)
    {
        try {
            return (DataInfo) dataInfo.getClass().newInstance();
        } catch (InstantiationException e) {
            System.out.println("error message:" + e.getMessage());
            e.printStackTrace();
        } catch (IllegalAccessException e) {            
            System.out.println("error message:" + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    public int doEndTag() throws JspException
    {
        return super.doEndTag();
    }
    
    /**
     * @return Returns the pageInfo.
     */
    public PageInfo getPageInfo() {
        return pageInfo;
    }
    /**
     * @param pageInfo The pageInfo to set.
     */
    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    /**
     * @return Returns the exportAll.
     */
    public boolean isExportAll() {
        return exportAll;
    }
    /**
     * @param exportAll The exportAll to set.
     */
    public void setExportAll(boolean exportAll) {
        this.exportAll = exportAll;
    }
    /**
     * @return Returns the type.
     */
    public String getType() {
        return type;
    }
    /**
     * @param type The type to set.
     */
    public void setType(String type) {
        this.type = type;
    }
}
