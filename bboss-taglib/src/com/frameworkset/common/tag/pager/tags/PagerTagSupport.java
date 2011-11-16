/*
 *  Pager Tag Library
 *
 *  Copyright (C) 2002  James Klicman <james@jsptags.com>
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.frameworkset.common.tag.pager.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import com.chinacreator.cms.driver.jsp.CMSServletRequest;
import com.frameworkset.common.tag.BaseTag;

/**
 * 
 * To change for your class or interface
 * 
 * @author biaoping.yin
 * @version 1.0
 * 2005-2-3
 */
public abstract class PagerTagSupport extends BaseTag {
     

//	protected PagerTag pagerTag = null;
//	protected DetailTag detailTag = null;
	protected FieldHelper fieldHelper;
	protected PagerContext pagerContext;
	/**
	 * �ж��Ƿ񵼳�ҳ�����ݵ��ļ���
	 * @return boolean
	 */
	protected boolean isExportMeta()
	{
	    //���ҳ���ǩ��Ƕ���ڷ�ҳ��ǩ��ʱ������ݷ�ҳ��ǩ���ж��������Ƿ񵼳�ҳ������
	    //���ҳ���ǩ��Ƕ������ϸ��ǩ��ʱ���������ϸ��ǩ���ж��������Ƿ񵼳�ҳ������
	    //���򲻵���ҳ������
//	    if(pagerTag != null)
//	        return pagerTag.isExportMeta();
//	    else if(detailTag != null)
//	        return detailTag.isExportMeta();
//	    else
	        return false;
	}

	protected final void restoreAttribute(String name, Object oldValue) {
		if (oldValue != null)
			pageContext.setAttribute(name, oldValue);
		else 
			pageContext.removeAttribute(name);
	}
	
	protected PagerContext findPageContext()
	{
		return null;
	}

//	private final PagerTag findRequestPagerTag(String pagerId) {
//		Object obj = request.getAttribute(pagerId);
//		if (obj instanceof PagerTag)
//			return (PagerTag) obj;
//		return null;
//	}

	public int doStartTag() throws JspException {
		
//		if (id != null) {
//			pagerTag = findRequestPagerTag(id);
//			
////			if (pagerTag == null)
////				throw new JspTagException("pager tag with id of \"" + id +
////											"\" not found.");
//		} else {
//			pagerTag = (PagerTag) findAncestorWithClass(this, PagerTag.class);
//			if (pagerTag == null) {
//				pagerTag = findRequestPagerTag(PagerTag.DEFAULT_ID);
////				if (pagerTag == null)
////					throw new JspTagException("not nested within a pager tag" +
////								" and no pager tag found at request scope.");
//			}
//		}
//		
//		if(pagerTag == null)
//		    detailTag = (DetailTag) findAncestorWithClass(this, DetailTag.class);
//		if(this.pagerTag == null)
//		    fieldHelper = detailTag;
//		else
//		    fieldHelper = pagerTag;
//		
//		
//		return EVAL_BODY_INCLUDE;
		pagerContext = null;
		/*
		 * ���Ҳ���ʼ����ǩ��pagerContext�������������Ҫ����Χ�����У�����pagerContext
		 * 1.��ǩ��������������Χ�ı�ǩ�ṩ�����ȸ��ݱ�ǩ
		 * �����idֵ��request�в��ң����û���ҵ������idΪnullʱ
		 * ����Ҫ 
		 */
		if (id != null) 
		{
//			pagerTag = findRequestPagerTag(id);
			this.pagerContext = this.findRequestPagerContext(id);
			if (pagerContext == null) {
				pagerContext = findRequestPagerContext(PagerTag.DEFAULT_ID);
			}

		} 
		else 
		{
			
			PagerTag pagerTag = (PagerTag) findAncestorWithClass(this, PagerTag.class);
			if(this instanceof DetailTag)
			{
				
			}
			else if(this instanceof PagerDataSet)
			{
				if(pagerTag != null)
				{
					PagerDataSet listTag = (PagerDataSet) findAncestorWithClass(this, PagerDataSet.class);
					if(listTag == null)
						pagerContext = pagerTag.pagerContext;
				}
				else
				{
					
				}
			}
			else if(this instanceof PagerRowCount)
			{
				if(pagerTag != null)
				{
					PagerDataSet listTag = (PagerDataSet) findAncestorWithClass(this, PagerDataSet.class);
					if(listTag == null)
						pagerContext = pagerTag.pagerContext;
					else
						pagerContext = listTag.pagerContext;
				}
				else
				{
					PagerDataSet listTag = (PagerDataSet) findAncestorWithClass(this, PagerDataSet.class);
					if(listTag != null)
						pagerContext = listTag.pagerContext;
					
				}
			}
			else if(this instanceof IndexTag)
			{
				if(pagerTag != null)
				{
					PagerDataSet listTag = (PagerDataSet) findAncestorWithClass(this, PagerDataSet.class);
					if(listTag == null)
						pagerContext = pagerTag.pagerContext;
					else
						pagerContext = listTag.pagerContext;
				}
				else
				{
					PagerDataSet listTag = (PagerDataSet) findAncestorWithClass(this, PagerDataSet.class);
					if(listTag != null)
						pagerContext = listTag.pagerContext;
				}
			}
			else if(this instanceof CellTag)
			{
				//�����ʱ��pagerContext
			}
			else if(this instanceof ParamTag)
			{
				if(pagerTag != null)
				{
					PagerDataSet listTag = (PagerDataSet) findAncestorWithClass(this, PagerDataSet.class);
					if(listTag == null)
						pagerContext = pagerTag.pagerContext;
					else
						pagerContext = listTag.pagerContext;
				}
			}
			else if(this instanceof TitleTag)
			{

				if(pagerTag != null)
				{
					PagerDataSet listTag = (PagerDataSet) findAncestorWithClass(this, PagerDataSet.class);
					if(listTag == null)
						pagerContext = pagerTag.pagerContext;
					else
						pagerContext = listTag.pagerContext;
				}
			}
			else if(this instanceof RowIDTag)
			{
				if(pagerTag != null)
				{
//					PagerDataSet listTag = (PagerDataSet) findAncestorWithClass(this, PagerDataSet.class);
//					if(listTag == null)
						pagerContext = pagerTag.pagerContext;
//					else
//						pagerContext = listTag.pagerContext;
				}
				else
				{
					PagerDataSet listTag = (PagerDataSet) findAncestorWithClass(this, PagerDataSet.class);
					if(listTag != null)
						pagerContext = listTag.pagerContext;
				}
			}
			else if(this instanceof NotifyTag)
			{
				if(pagerTag != null)
				{
//					PagerDataSet listTag = (PagerDataSet) findAncestorWithClass(this, PagerDataSet.class);
//					if(listTag == null)
						pagerContext = pagerTag.pagerContext;
//					else
//						pagerContext = listTag.pagerContext;
				}
				else
				{
					PagerDataSet listTag = (PagerDataSet) findAncestorWithClass(this, PagerDataSet.class);
					if(listTag != null)
						pagerContext = listTag.pagerContext;
				}
			}
			if(this instanceof QueryStringTag)
			{
				if(pagerTag != null)
				{
//					PagerDataSet listTag = (PagerDataSet) findAncestorWithClass(this, PagerDataSet.class);
//					if(listTag == null)
						pagerContext = pagerTag.pagerContext;
//					else
//						pagerContext = listTag.pagerContext;
				}
				else
				{
					PagerDataSet listTag = (PagerDataSet) findAncestorWithClass(this, PagerDataSet.class);
					if(listTag != null)
						pagerContext = listTag.pagerContext;
				}
			}
				
				
//			/**
//			 * 
//			 */
//			if (pagerTag == null ) {
//				
//				PagerDataSet listTag = (PagerDataSet) findAncestorWithClass(this, PagerDataSet.class);
//				if(listTag != null)
//				{
//					pagerContext = listTag.pagerContext;
//				}
//				else
//				{
//					DetailTag detailTag = (DetailTag) findAncestorWithClass(this, DetailTag.class);
//					if(detailTag != null)
//						pagerContext = detailTag.pagerContext;
//					else if(this instanceof CellTag)
//					{
//						
//						
//						
//					}
//				}
//				if(pagerContext == null)
//				{
//					pagerContext = findRequestPagerContext(PagerTag.DEFAULT_ID);
//				}
//			}
//			else
//			{
//				pagerContext = pagerTag.pagerContext;
//			}
		}
		
		if(pagerContext == null)
		{
			//���ж��⴦��
		}
		fieldHelper = pagerContext;
//		if(this.pagerTag == null)
//		    fieldHelper = detailTag;
//		else
//		    fieldHelper = pagerTag;
		return EVAL_BODY_INCLUDE;
	}

	private PagerContext findRequestPagerContext(String pagerContextID) {
		HttpServletRequest request = this.getHttpServletRequest();
//		HttpServletResponse response = this.getHttpServletResponse();
		Object obj = request.getAttribute(pagerContextID);
		if (obj instanceof PagerContext)
			return (PagerContext) obj;
		return null;
	}

	public int doEndTag() throws JspException {
		HttpServletRequest request = this.getHttpServletRequest();
//		HttpServletResponse response = this.getHttpServletResponse();
		if (request == null || !(request instanceof CMSServletRequest) )
		{
			pagerContext = null;
			fieldHelper = null;
		}
		return super.doEndTag();
//		return EVAL_PAGE;
	}
	
	public static void main(String[] args)
	{
		String ss = null;
		boolean f = ss instanceof String;
		System.out.println(f);
	}
	
	
	public void release() {
		pagerContext = null;
		super.release();
	}
	
	protected FieldHelper getFieldHelper()
	{    
	    return fieldHelper;
	}
}

/* vim:set ts=4 sw=4: */
