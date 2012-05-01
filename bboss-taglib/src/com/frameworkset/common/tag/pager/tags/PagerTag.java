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
 *  biaoping.yin (yin-bp@163.com)
 *                                                                           *
 *****************************************************************************/

package com.frameworkset.common.tag.pager.tags;

import com.frameworkset.common.poolman.SQLExecutor;
import com.frameworkset.common.tag.BaseTag;
import com.frameworkset.common.tag.pager.DataInfo;
import com.frameworkset.common.tag.pager.model.MetaDatas;
import com.frameworkset.common.tag.pager.parser.PagerTagExport;
import com.frameworkset.common.tag.pager.parser.ParseException;
import com.frameworkset.common.tag.pager.parser.TagExportParser;
import com.frameworkset.util.ListInfo;

import java.io.OutputStream;
import java.util.Stack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import org.apache.log4j.Logger;

/**
 * @author biaoping.yin
 * @version 1.0 2005-2-3
 */
public class PagerTag extends BaseTag implements FieldHelper, PagerInfo {
	private final static Logger log = Logger.getLogger(PagerTag.class);
//	protected String pretoken = "#\\[";
//    protected String endtoken = "\\]";
    /**
     * jquery��������
     */
    private String containerid ;
    
    /**
     * jquery����ѡ����
     */
    private String selector;
	/**
	 * ��ʶ�Ƿ���wapӦ��
	 */
	private boolean wapflag = false;

	// /**
	// * ����ҳ�������е�IndexTag�ı�ǩ Movied to PagerContext
	// */
	// Stack indexs;
	// static final String PARAMETER = "parameter";
	// static final String ATTRIBUTE = "attribute";
	private DataInfo dataInfo = null;

	static final String DEFAULT_ID = "pager";

	/** �������·�ҳ����form�ύ�¼���������ָ����Ҫ�ύ��form������ */
	private String form = null;

	/** ����Ҫ��������ʱ�Ƿ���ʾ���棬��form�������ʹ�� */
	private boolean promotion = false;
	
	/**���ύ֮ǰ��Ҫִ�е��¼�*/
	private String commitevent = null;

	private String sortKey = null;

	/**
	 * �����Ҫ�������ݵ�xml��pdf,word,excel,csvʱ ���ñ�����Ϊtrue������Ϊfalse
	 */
	private boolean exportMeta = false;

	/**
	 * ���ҳ�浼�����ļ�ʱ���в�������exportMetaΪtrueʱ���ñ�����
	 */
	protected MetaDatas metaDatas = null;

	/**
	 * ������ݵ��ļ�ʱ��Ҫ����ҳ���ϵ����ݼ��϶�ջ
	 */
	protected Stack dataSets;

	/**
	 * ��ʾ�������ֶ�����
	 */
	public static final int DEFAULT_MAX_ITEMS = Integer.MAX_VALUE,
			DEFAULT_MAX_PAGE_ITEMS = 10, DEFAULT_MAX_INDEX_PAGES = 10;

	private StringBuffer queryString = null;

	protected PagerContext pagerContext;

	static final String OFFSET_PARAM = ".offset";

	static final String
	// scope values
			PAGE = "page", REQUEST = "request",
			// index values
			CENTER = "center", FORWARD = "forward", HALF_FULL = "half-full";

	/*
	 * Tag Properties ������ת��url
	 */
	private String url = null;

	private String index = null;

	// /**
	// * items:�ܵļ�¼���� to pagercontext
	// */
	// private long items = 0;
	private int maxItems = DEFAULT_MAX_ITEMS;

	private int maxPageItems = DEFAULT_MAX_PAGE_ITEMS;

	private int maxIndexPages = DEFAULT_MAX_INDEX_PAGES;

	// private boolean isOffset = false;
	private boolean isOffset = true;

	private String export = null;

	private String scope = null;

	// /*
	// * Tag Variables
	// */
	// private StringBuffer uri = null;
	// private int params = 0;
	// /**
	// * offset����ǰҳ���һ����¼idֵ,
	// * ����offset=80��ʾ��ǰҳ���һ����¼idֵΪ80
	// */
	// private long offset = 0; to pagercontext
	// private long itemCount = 0; to pagercontext
	private String data = null;

	/**
	 * ���ݿ��ѯ�������
	 */
	private String statement = "";

	/**
	 * ���ݿ����ӳ�����
	 */
	private String dbname = "";

	// /**
	// * pageNumber����ǰ���ڵڼ�ҳ
	// */
	//
	// private long pageNumber = 0;

	// public int getParams()
	// {
	// return this.params;
	// }

	// /**
	// * pageNumberInteger����װ��ǰҳҳ��Ķ���
	// */
	// private Long pageNumberInteger = null;

	/**
	 * idOffsetParam�������request�л�ȡoffsetֵ���������ƣ����磺pager.offset
	 */
	private String idOffsetParam = DEFAULT_ID + OFFSET_PARAM;

	/**
	 * ������Ҫ���������ķ�����
	 */
	private PagerTagExport pagerTagExport = null;

	private PagerContext oldPager = null;

	private Object oldOffset = null;

	private Object oldPageNumber = null;
	

	private boolean desc = true;
	
	private String pager_infoName = "pager_info";

	// /**
	// * Movied to PagerContext
	// * @return
	// */
	// public boolean getDesc()
	// {
	// return desc;
	// }

	public void setDesc(boolean desc) {
		this.desc = desc;
	}

	public PagerTag() {
		id = DEFAULT_ID;
	}

	/**
	 * ��������ؼ���
	 * 
	 * @param string
	 */
	public void setSortKey(String string) {
		// String t_key = request.getParameter("sortKey");
		// if(t_key != null && !t_key.trim().equals(""))
		// sortKey = t_key;
		// else
		sortKey = string;
	}

	// /**
	// * ��ȡ����ؼ��� Movied to PagerContext
	// * Description:
	// * @return
	// * String
	// */
	// public String getSortKey()
	// {
	// // String t_key = request.getParameter("sortKey");
	// // if(t_key == null )
	// // t_key = (String)request.getAttribute("sortKey");
	// // if(t_key != null && sortKey == null)
	// // sortKey = t_key;
	// return sortKey;
	// }

	public final void setId(String sid) {
		super.setId(sid);
		// OFFSET_PARAM = ".offset";
		idOffsetParam = sid + OFFSET_PARAM;
	}

	public final void setUrl(String value) {
		url = value;
	}

	public final String getUrl() {
		return url;
	}

	public final void setIndex(String val) throws JspException {
		if (!(val == null || CENTER.equals(val) || FORWARD.equals(val) || HALF_FULL
				.equals(val))) {
			throw new JspTagException(
					"value for attribute \"index\" "
							+ "must be either \"center\", \"forward\" or \"half-full\".");
		}
		index = val;
	}

	public final String getIndex() {
		return index;
	}

	// public final void setItems(long value) {
	//
	// items = value;
	// }
	//
	// public final long getItems() {
	// return items;
	// }

	public final void setMaxItems(int value) {
		maxItems = value;
	}

	public final int getMaxItems() {
		return maxItems;
	}

	public final void setMaxPageItems(int value) {
		maxPageItems = value;
	}

	// /**
	// * Movied to PagerContext
	// * @return
	// */
	// public final int getMaxPageItems() {
	// return maxPageItems;
	// }

	public final void setMaxIndexPages(int value) {
		maxIndexPages = value;
	}

//	public final int getMaxIndexPages() {
//		return maxIndexPages;
//	}

	public final void setIsOffset(boolean val) {
		isOffset = val;
	}

	public final boolean getIsOffset() {
		return isOffset;
	}

	public final void setExport(String value) throws JspException {
		if (export != value) {
			try {

				pagerTagExport = TagExportParser.parsePagerTagExport(value);
			} catch (ParseException ex) {
				throw new JspTagException(ex.getMessage());
			}
		}
		export = value;
	}

	public final String getExport() {
		return export;
	}

	public final void setScope(String val) throws JspException {
		if (!(val == null || PAGE.equals(val) || REQUEST.equals(val))) {
			throw new JspTagException("value for attribute \"scope\" "
					+ "must be either \"page\" or \"request\".");
		}
		scope = val;
	}

	
	protected String sqlparamskey = "sql.params.key";
	/**
	 * ��ǩ��ʼʱִ�����·���
	 */
	public int doStartTag() throws JspException {
		// log.debug("id:" + id);
		// if(id == null || id.trim().equals(""))
		// {
		// log.debug("DoStartTag id == null || id.trim().equals(\"\")");
		pageContext.setAttribute(pager_infoName, this);
		HttpServletRequest request = this.getHttpServletRequest();
		HttpServletResponse response = this.getHttpServletResponse();
		// ��ʼ��ҳ����������Ϣ
		this.pagerContext = new PagerContext(request, response,
				this.pageContext,this);

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
		pagerContext.setNavindex(this.index);
		pagerContext.setPromotion(this.promotion);
		pagerContext.setScope(this.scope);
		pagerContext.setTitle(this.title);
		pagerContext.setMaxIndexPages(this.maxIndexPages);
		pagerContext.setMaxItems(this.maxItems);
		
//		Object object = request.getAttribute(data);
		String baseUri = request.getRequestURI();
		boolean isControllerPager = PagerContext.isPagerMehtod(request); 
		String cookieid = null;
		if(isControllerPager)
		{
//			baseUri = PagerContext.getPathwithinHandlerMapping(request);
			baseUri = PagerContext.getHandlerMappingRequestURI(request);
//			String mappingpath = PagerContext.getHandlerMappingPath(request); 
//			cookieid = this.pagerContext.getId() == null ?PagerDataSet.COOKIE_PREFIX + mappingpath :PagerDataSet.COOKIE_PREFIX + mappingpath + "|" +this.pagerContext.getId();
			cookieid = PagerContext.getControllerCookieID(request);
			pagerContext.setUrl(baseUri);
//			ListInfo mvcinfo = (ListInfo)object;
			int controllerPagerSize = PagerContext.getControllerPagerSize(request);
//			pagerContext.setMaxPageItems(mvcinfo.getMaxPageItems());
//			pagerContext.setCustomMaxPageItems(mvcinfo.getMaxPageItems());
			pagerContext.setMaxPageItems(controllerPagerSize);
			pagerContext.setCustomMaxPageItems(PagerContext.getCustomPagerSize(request));
			
			
		}
		else
		{
			pagerContext.setUrl(url);
			cookieid = this.pagerContext.getId() == null ?PagerDataSet.COOKIE_PREFIX + baseUri :PagerDataSet.COOKIE_PREFIX + baseUri + "|" +this.pagerContext.getId();
		
			int defaultSize = PagerDataSet.consumeCookie(cookieid,maxPageItems,request,pagerContext);
			pagerContext.setCustomMaxPageItems(maxPageItems);
			pagerContext.setMaxPageItems(defaultSize);
		}
		pagerContext.setCookieid(cookieid);
		pagerContext.setWapflag(this.wapflag);
		pagerContext.setWidth(this.width);
		pagerContext.setIsOffset(this.isOffset);
		pagerContext.setData(this.data);
		pagerContext.setDbname(this.dbname);
		pagerContext.setStatement(this.statement);
		SQLExecutor sqlExecutor = (SQLExecutor)request.getAttribute(sqlparamskey);
		pagerContext.setSQLExecutor(sqlExecutor);
//		Object object = request.getAttribute(data);
//		if(object instanceof ListInfo)
//		{
//			pagerContext.setUrl(PagerContext.getHandlerMappingPath(request));
//		}
//		else
//			pagerContext.setUrl(url);
		pagerContext.setCommitevent(this.commitevent);
		

		pagerContext.setUri();
		pagerContext.setContainerid(this.getContainerid());
		pagerContext.setSelector(this.getSelector());
		// params = 0;
		// offset = 0;
		// itemCount = 0;

		// �����Ƿ��������ǽ���
		String desc_key = pagerContext.getKey("desc");

		String t_desc = request.getParameter(desc_key);
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

		
		try {
			pagerContext.init();
		}  catch (LoadDataException e) {
			if(e.getCause() == null)
				log.info(e.getMessage());
			else
				log.info(e.getCause().getMessage());
//			return SKIP_BODY;
		}
		catch (Throwable e) {
			if(e.getCause() == null)
				log.info(e.getMessage());
			else
				log.info(e.getCause().getMessage());
			throw new JspException(e);
//			return SKIP_BODY;
		}

		// //addParam("sortKey",getSortKey());
		// //:log
		// if(!pagerContext.ListMode())//:log����Ƿ�ҳģʽ����ʼ��������
		// {
		// pagerContext.initContext();
		// }
		//
		// else//:log������б�ģʽ��ֱ������dataInfo��Ϣ����
		// pagerContext.setDataInfo(pagerContext.getData());

		// /**
		// * �����Ҫ�������ݵ��ļ������ñ��������г�ʼ��
		// */
		// this.setMeta();

		return EVAL_BODY_INCLUDE;
	}

	public void setMeta() {
		if (this.isExportMeta())
			this.metaDatas = new MetaDatas();
	}

	// /**
	// * Movied to PagerContext
	// * @return
	// */
	// public String getUri()
	// {
	// return uri.toString();
	// }

	// /**
	// * Moved to PagerContext
	// */
	// private static void restoreAttribute(ServletRequest request, String name,
	// Object oldValue)
	// {
	// if (oldValue != null)
	// request.setAttribute(name, oldValue);
	// else
	// request.removeAttribute(name);
	// }
	//
	// /**
	// * Moved to PagerContext
	// * @param pageContext
	// * @param name
	// * @param oldValue
	// */
	// private static void restoreAttribute(PageContext pageContext, String
	// name,
	// Object oldValue)
	// {
	// if (oldValue != null)
	// pageContext.setAttribute(name, oldValue);
	// else
	// pageContext.removeAttribute(name);
	// }

	/**
	 * ��ȡ���ݻ�ȡ����request�еĴ������
	 * 
	 * @return String ���ݻ�ȡ����request�еĴ������
	 */
	public String getData() {

		return data == null ? "dataInfo" : data;
	}

	public int doEndTag() throws JspException {
		/**
		 * �ָ������Ļ����ɵ������Ļ�����������ݻ�ȡ�ӿ�
		 */
		this.pagerContext.release();
		this.commitevent = null;
		this.form = null;
		this.promotion = false;
//		pretoken= null;
//        endtoken= null;
        sqlparamskey = "sql.params.key";
        
		//		
		// if (REQUEST.equals(scope)) {
		//			
		// PagerContext.restoreAttribute(request, id, oldPager);
		//			
		// request.removeAttribute(getData());
		// oldPager = null;
		//
		// // if (pagerTagExport != null) {
		// // String name;
		// // if ((name = pagerTagExport.getPageOffset()) != null) {
		// // PagerContext.restoreAttribute(request, name, oldOffset);
		// // oldOffset = null;
		// // }
		// //
		// // if ((name = pagerTagExport.getPageNumber()) != null) {
		// // PagerContext.restoreAttribute(request, name, oldPageNumber);
		// // oldPageNumber = null;
		// // }
		// // }
		//			
		// } else {
		// if (pagerTagExport != null) {
		// String name;
		// if ((name = pagerTagExport.getPageOffset()) != null) {
		// PagerContext.restoreAttribute(pageContext, name, oldOffset);
		// oldOffset = null;
		// }
		//
		// if ((name = pagerTagExport.getPageNumber()) != null) {
		// PagerContext.restoreAttribute(pageContext, name, oldPageNumber);
		// oldPageNumber = null;
		// }
		// }
		// }

		//
		// // limit size of re-usable StringBuffer
		// if (uri.capacity() > 1024)
		// uri = null;
		//
		// // indexs = null;
		// pageNumberInteger = null;

		/**
		 * return EVAL_PAGE:��������������ǩ���ҳ�����
		 */
		// try
		// {
		// pageContext.getOut().print("<table width=\"100%\">");
		// }
		// catch(Exception e)
		// {
		// throw new JspException(e.getMessage());
		// }
		// release();
		return EVAL_PAGE;
	}

	public void release() {
		url = null;
		index = null;

		// items = 0;
		maxItems = DEFAULT_MAX_ITEMS;
		maxPageItems = DEFAULT_MAX_PAGE_ITEMS;
		maxIndexPages = DEFAULT_MAX_INDEX_PAGES;
		isOffset = false;
		export = null;
		scope = null;
		//
		// uri = null;
		queryString = null;
		// params = 0;
		// offset = 0;
		// itemCount = 0;
		// pageNumber = 0;
		// pageNumberInteger = null;

		idOffsetParam = DEFAULT_ID + OFFSET_PARAM;
		pagerTagExport = null;
		oldPager = null;
		oldOffset = null;
		oldPageNumber = null;
		
		this.containerid = null;
		this.selector = null;

		super.release();
	}

	public String getIdOffsetParam() {
		return this.idOffsetParam;
	}

	/**
	 * @param string
	 */
	public void setWidth(String string) {
		width = string;
	}

	/**
	 * @return String[]
	 */
	public String[] getWidths() {
		return this.pagerContext.getWidths();
	}

	public static void main(String[] args) {

	}

	/**
	 * @return String[]
	 */
	public String[] getTitles() {
		return this.pagerContext.getTitles();
	}

	/**
	 * @param string
	 */
	public void setTitle(String string) {
		title = string;
	}

	String field = "";

	String width = null;

	String title = null;

	/**
	 * @return String[]
	 */
	public String[] getFields() {
		return this.pagerContext.getFields();
	}

	/**
	 * @param string
	 */
	public void setField(String string) {
		field = string;
	}

	boolean isList = false;

	// /** Movied to PagerContext
	// * @return boolean �ж��Ƿ����б�ģʽ
	// */
	// boolean ListMode() {
	// // if(isList == null || isList.trim().length() == 0)
	// // return false;
	// return isList;
	// }

	/**
	 * @param string
	 */
	public void setIsList(boolean string) {
		isList = string;
	}

	// /**
	// * ��ȡ���ݻ�ȡ����request�еĴ������
	// * @return String ���ݻ�ȡ����request�еĴ������
	// */
	// public String getData() {
	//
	// return data == null?"dataInfo":data;
	// }

	/**
	 * @param string
	 */
	public void setData(String string) {
		data = string;
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

	// /**
	// * ��ȡҳ���ѯ���� Movied to PagerContext
	// * @return String
	// */
	// public String getQueryString()
	// {
	// return queryString == null?"":queryString.toString();
	// }
	// /**
	// * Description:��ȡҳ���ѯ�Ĳ��� Movied to PagerContext
	// * @return
	// * StringBuffer
	// */
	// public String getQueryString(long offset,String sortKey,boolean desc) {
	// String offsetString = "";
	// if(queryString != null)
	// {
	// int length = queryString.length();
	// queryString.append(params == 0 ? "" : "&")
	// .append(idOffsetParam)
	// .append('=')
	// .append(offset);
	//
	// if(sortKey != null)
	// queryString.append("&").append(getKey("sortKey")).append("=").append(sortKey);
	// queryString.append("&").append(getKey("desc")).append("=").append(desc);
	// offsetString = queryString.toString();
	// queryString.setLength(length);
	// }
	// return offsetString;
	// }

	// /**
	// * Description:��ȡҳ���ѯ�Ĳ��� Movied to PagerContext
	// * @return
	// * StringBuffer
	// */
	// public String getQueryString(String sortKey,boolean desc) {
	//
	// String offsetString = "";
	// if(queryString != null && sortKey != null)
	// {
	// int length = queryString.length();
	// queryString.append(params == 0 ? "" : "&")
	// .append(getKey("sortKey")).append("=").append(sortKey);
	// queryString.append("&").append(getKey("desc")).append("=").append(desc);
	// offsetString = queryString.toString();
	// queryString.setLength(length);
	// }
	// return offsetString;
	// }

	// /**
	// * Movied to PagerContext
	// * Description:
	// * @return
	// * String
	// */
	// public String getForm() {
	// return form;
	// }

	/**
	 * Description:
	 * 
	 * @param string
	 *            void
	 */
	public void setForm(String string) {
		form = string;
	}

	// /**
	// * Movied to PagerContext
	// * Description:
	// * @return
	// * String
	// */
	// public boolean getPromotion() {
	// return promotion;
	// }

	/**
	 * Description:
	 * 
	 * @param string
	 *            void
	 */
	public void setPromotion(boolean string) {
		promotion = string;
	}

	// /**
	// * Movied to PagerContext
	// * @return
	// */
	// public boolean hasParams()
	// {
	// return params != 0;
	// }
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
	 * �����ǰҳ���¼����
	 * 
	 * @see com.frameworkset.common.tag.pager.tags.PagerInfo#getDataSize()
	 */
	public int getDataSize() {

		return this.pagerContext == null ? 0 : pagerContext.getDataSize();
	}

	/**
	 * @return Returns the exportMeta.
	 */
	public boolean isExportMeta() {
		return exportMeta;
	}

	/**
	 * @param exportMeta
	 *            The exportMeta to set.
	 */
	public void setExportMeta(boolean exportMeta) {
		this.exportMeta = exportMeta;
	}

	/**
	 * @return Returns the dataSets.
	 */
	public Stack getDataSets() {
		return dataSets;
	}

	/**
	 * @param dataSets
	 *            The dataSets to set.
	 */
	public void setDataSets(Stack dataSets) {
		this.dataSets = dataSets;
	}

	/**
	 * @return Returns the metaDatas.
	 */
	public MetaDatas getMetaDatas() {
		return metaDatas;
	}

	// /**
	// * Movied to PagerContext
	// * @return
	// */
	// public boolean isWapflag() {
	// return wapflag;
	// }

	/**
	 * @param metaDatas
	 *            The metaDatas to set.
	 */
	public void setMetaDatas(MetaDatas metaDatas) {
		this.metaDatas = metaDatas;
	}

	public void setWapflag(boolean wapflag) {
		this.wapflag = wapflag;
	}

	public String getPager_infoName() {
		return pager_infoName;
	}

	public void setPager_infoName(String pager_infoName) {
		this.pager_infoName = pager_infoName;
	}

	public String getCommitevent() {
		return commitevent;
	}

	public void setCommitevent(String commitevent) {
		this.commitevent = commitevent;
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

 
}
