package com.frameworkset.common.tag.pager.tags;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import org.apache.log4j.Logger;
import org.frameworkset.util.ClassUtil;
import org.frameworkset.util.ClassUtil.ClassInfo;
import org.frameworkset.util.ClassUtil.PropertieDescription;

import com.frameworkset.common.poolman.SQLExecutor;
import com.frameworkset.common.poolman.SQLParams;
import com.frameworkset.common.poolman.SetSQLParamException;
import com.frameworkset.common.tag.CMSTagUtil;
import com.frameworkset.common.tag.html.CMSListTag;
import com.frameworkset.common.tag.pager.DataInfo;
import com.frameworkset.common.tag.pager.DefaultDataInfoImpl;
import com.frameworkset.common.tag.pager.IgnoreParam;
import com.frameworkset.common.tag.pager.ListInfoDataInfoImpl;
import com.frameworkset.common.tag.pager.ObjectDataInfoImpl;
import com.frameworkset.common.tag.pager.parser.PagerTagExport;
import com.frameworkset.platform.cms.driver.context.ContentContext;
import com.frameworkset.platform.cms.driver.context.PagineContext;
import com.frameworkset.platform.cms.driver.dataloader.CMSBaseListData;
import com.frameworkset.platform.cms.driver.dataloader.CMSDataLoadException;
import com.frameworkset.platform.cms.driver.dataloader.CMSDetailDataLoader;
import com.frameworkset.platform.cms.driver.jsp.CMSServletRequest;
import com.frameworkset.platform.cms.driver.jsp.InternalImplConverter;
import com.frameworkset.util.ListInfo;
import com.frameworkset.util.SimpleStringUtil;
import com.frameworkset.util.StringUtil;

/**
 * ҳ����Ϣ�����ģ��Է�ҳ��������Ϣ���з�װ
 * <p>
 * Title: com.frameworkset.common.tag.pager.tags.PagerContext.java
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright (c) 2006.10
 * </p>
 * 
 * <p>
 * Company: chinacreator
 * </p>
 * 
 * @Date 2007-2-1 10:04:58
 * @author biaoping.yin
 * @version 1.0
 */
public class PagerContext
// extends BaseSQLParamManager
		implements FieldHelper, PagerInfo, Serializable {
	/**
	 * ���ݿ��ѯ�������
	 */
	protected String statement = "";
	/**
	 * ���ڶ����ǩ����
	 */
	protected boolean moreQuery = false;
	/**
	 * ���ڶ������յ�ʵ�ʵ�moreֵ����moreQuery��listInfo��ȡֵ����ǩ������moreQuery���Բ��������ݿ��ѯ��ȡmoreQuery��ֵ�������listInfoʶ����ȡlistInfo��isMoreֵ��
	 */
	protected boolean more = false;
	
	protected String dbname;
	protected String cookieid;
	private boolean inited = false;
	private Object actual;
	public Object getActual() {
		return actual;
	}

	public void setActual(Object actual) {
		this.actual = actual;
	}

	private Tag tag;
	private SQLExecutor sqlExecutor;

	private final static Logger log = Logger.getLogger(PagerContext.class);

	private HttpServletRequest request;

	private HttpServletResponse response;

	private HttpSession session;

	private PageContext pageContext;

	/**
	 * ������ǰ��pagerContext���Ա��ǩִ����Ϻ����
	 */
	private Object oldPagerContext;
	 /**
     * jquery��������
     */
    private String containerid ;
    
    /**
     * jquery����ѡ����
     */
    private String selector;
    
    /**
     * ��notify��ǩ������list��ǩ��ʱ��Ϊ�˱���
     * �ظ�����notify��ǩ����notifyed��������Ϊtrue
     * 
     */
    private boolean notifyed = false;

	public PagerContext(HttpServletRequest request,
			HttpServletResponse response, PageContext pageContext, Tag tag) {
		this.request = request;
		this.session = request.getSession(false);
		this.response = response;
		this.pageContext = pageContext;
		this.tag = tag;
	}

	static final String
	// scope values
			PAGE = "page",
			REQUEST = "request",
			// index values
			CENTER = "center", FORWARD = "forward", HALF_FULL = "half-full";

	/**
	 * items:�ܵļ�¼����
	 */
	private long items = 0;

	/**
	 * offset����ǰҳ���һ����¼idֵ, ����offset=80��ʾ��ǰҳ���һ����¼idֵΪ80
	 */
	private long offset = 0;

	private long itemCount = 0;

	private boolean desc = true;

	private String sortKey = null;

	boolean isList = false;

	private DataInfo dataInfo = null;

	private String field = "";

	private String width = null;

	private String title = null;

	private String id;

	/**
	 * pagerTag�е�index����������Ϊnavindex
	 */
	private String navindex = null;

	/**
	 * ҳ�����Ѿ����õĵ�����������
	 */
	Stack indexs;

	private int maxItems = DEFAULT_MAX_ITEMS;

	/******************************************************************
	 * ListTag ������ *
	 ******************************************************************/
	private int index = -1;

	protected String sessionKey = null;
	protected String requestKey = null;
	protected String pageContextKey = null;
	/**
	 * ����dataSet�Ļ�ȡ��Χ
	 */
	protected static final String SESSION_SCOPE = "session";
	protected static final String REQUEST_SCOPE = "request";
	protected static final String ACTUAL_SCOPE = "actual";
	protected static final String PAGECONTEXT_SCOPE = "pageContext";
	protected static final String COLUMN_SCOPE = "column";
	protected static final String DB_SCOPE = "db";
	protected static final String CELL_SCOPE = "cellscope";

	/*************************************************
	 * ��ֵ��������colName��Ӧ������ΪCollectionʱ��* ����ǩ����collection��Ϊ����Դ�� *
	 * ѭ��Ƕ�����collection�еĶ������� * property�¼���������
	 *************************************************/
	/**
	 * ����
	 */
	protected String colName;

	/**
	 * �¼���������
	 */
	protected String property;
	/**
	 * ���ݹ����䳣�������ݹ���ϵͳ����ʱ��Ҫ�õ��ķ�Χ
	 */
	protected static final String CMS_SCOPE = "cms";

	protected String commitevent;

	/******************************************************************
	 * DetailTag ������ *
	 ******************************************************************/

	/**
	 * ��ʼ��������
	 * 
	 * @throws LoadDataException
	 */
	public void init() throws LoadDataException {
		if (!inited) {
			/*
			 * id��ֵΪ��pager��,������ǰ��ҳ��������Ļ�����Ȼ���ٽ���ǰ�����������õ�request��pageContext��
			 */
			if (REQUEST.equals(scope)) {
				String t_id = "pagerContext.";
				if (this.id != null && !id.trim().equals(""))
					t_id += id;
				else
					t_id += DEFAULT_ID;
				this.oldPager = (PagerContext) request.getAttribute(t_id);
				request.setAttribute(t_id, this);
			}

			if (!ListMode())// :log����Ƿ�ҳģʽ����ʼ��������
			{

				initContext();

			}

			else
				// :log������б�ģʽ��ֱ������dataInfo��Ϣ����
				setDataInfo();

			if (this.tag instanceof PagerDataSet) {
				/**
				 * ��������ݹ����Ƶ���ĵ���ҳ�б�Ļ�����Ҫ����ҳ���������
				 * ��Ϊ���ɷ�ҳ�����ı�ǩindex��Ҫʹ�õ�pagerContext����ʵ��
				 * 
				 * 
				 */
				String t_id = "pagerContext.";
				if (request instanceof CMSServletRequest) {
					CMSServletRequest cmsRequest = (CMSServletRequest) request;
					t_id += cmsRequest.getContext().getID();

				} else if (this.id != null && !id.trim().equals("")) {
					t_id += id;
					this.oldPagerContext = request.getAttribute(t_id);
				} else {
					t_id += DEFAULT_ID;
					this.oldPagerContext = request.getAttribute(t_id);
				}

				this.request.setAttribute(t_id, this);
				// this.setId(id);
			}
			inited = true;
		}

	}

	/**
	 * ��������ؼ���
	 * 
	 * @param string
	 */
	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
		// String sortKey_key = this.getKey("sortKey");
		//
		// String t_sortKey = request.getParameter(sortKey_key);
		// //�����ȡ����sortKeyΪ��ʱ��ͨ��request.getAttribute��ȡ
		// if(t_sortKey == null)
		// t_sortKey = (String)request.getAttribute(sortKey_key);
		// //��������ȡ����sortKey��Ϊnullʱ������sortKey
		// if(t_sortKey != null)
		// setSortKey(t_sortKey);
	}

	/**
	 * ��ȡ����ؼ��� Description:
	 * 
	 * @return String
	 */
	public String getSortKey() {
		// String t_key = request.getParameter("sortKey");
		// if(t_key == null )
		// t_key = (String)request.getAttribute("sortKey");
		// if(t_key != null && sortKey == null)
		// sortKey = t_key;
		return sortKey;
	}

	public boolean getDesc() {
		return desc;
	}

	public void setDesc(boolean desc) {
		this.desc = desc;
		// // �����Ƿ��������ǽ���
		// String desc_key = this.getKey("desc");
		//
		// String t_desc = request.getParameter(desc_key);
		// if(t_desc != null && t_desc.equals("false"))
		// desc = false;
		// else if(t_desc != null && t_desc.equals("true"))
		// desc = true;
	}

	boolean ListMode() {
		// if(isList == null || isList.trim().length() == 0)
		// return false;
		return isList;
	}

	/**
	 * @param string
	 */
	public void setIsList(boolean isList) {
		this.isList = isList;
	}

	public final DataInfo getDataInfo() {
		return this.dataInfo;
	}

	/**
	 * @return String[]
	 */
	public String[] getFields() {
		if (field == null)
			return null;

		return StringUtil.split(field, ",");
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
		if (width == null)
			return null;
		return StringUtil.split(width, ",");
	}

	public void setField(String field) {
		this.field = field;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return String[]
	 */
	public String[] getTitles() {
		if (title != null)
			return StringUtil.split(title, ",");
		return null;
	}

	public void setDataInfo(DataInfo dataInfo) {
		this.dataInfo = dataInfo;
	}

	public boolean isWapflag() {
		return wapflag;
	}

	/**
	 * ��ʶ�Ƿ���wapӦ��
	 */
	private boolean wapflag = false;

	public void setWapflag(boolean wapflag) {
		this.wapflag = wapflag;
	}

	/** �������·�ҳ����form�ύ�¼���������ָ����Ҫ�ύ��form������ */
	private String form = null;

	/**
	 * Description:
	 * 
	 * @return String
	 */
	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public String getId() {
		return this.id;
	}

	/**
	 * idOffsetParam�������request�л�ȡoffsetֵ���������ƣ����磺pager.offset
	 */
	private String idOffsetParam = DEFAULT_ID + OFFSET_PARAM;

	public static final String DEFAULT_ID = "pager";
	public static final String OFFSET_KEY = "offset";
	public static final String DESC_KEY = "desc";
	public static final String SORT_KEY = "sortKey";
	public static final String PAGE_SIZE = "PAGE_SIZE";

	public static final String OFFSET_PARAM = "." + OFFSET_KEY;
	
	
	
	
	
	

	public final void setId(String sid) {
		this.id = sid;
		if (id == null || id.trim().equals("")) {
			idOffsetParam = DEFAULT_ID + OFFSET_PARAM;
		} else
			idOffsetParam = id + OFFSET_PARAM;
	}

	/**
	 * pageNumber����ǰ���ڵڼ�ҳ
	 */

	private long pageNumber = 0;

	/**
	 * ��ȡ��ǰҳ���ڵ�ҳ����
	 * 
	 * @return int
	 */
	final long getPageNumber() {
		return pageNumber;
	}

	/**
	 * ��ȡ����Դ�ļ�¼����
	 * 
	 * @return int
	 */
	final long getItemCount() {
		return (items != 0 ? items : itemCount);
	}

	/**
	 * ��ȡҳ��������
	 * 
	 * @return int
	 */
	final long getPageCount() {

		return pageNumber(getItemCount());
	}

	/**
	 * ��ʾ�������ֶ�����
	 */
	private static final int DEFAULT_MAX_ITEMS = Integer.MAX_VALUE,
			DEFAULT_MAX_PAGE_ITEMS = 10, DEFAULT_MAX_INDEX_PAGES = 10;
	/**
	 * ÿҳ��ʾ������¼����
	 */
	private int maxPageItems = DEFAULT_MAX_PAGE_ITEMS;
	/**
	 * ����pager��ǩ��list��ǩmaxPageItems�����趨��ҳ���¼��С
	 */
	private int customMaxPageItems;

	private int maxIndexPages = DEFAULT_MAX_INDEX_PAGES;

	/**
	 * ����˵�������㵱ǰҳ����
	 * 
	 * @param offset
	 * @return int
	 */
	private final long pageNumber(long offset) {
		return (offset / maxPageItems) + (offset % maxPageItems == 0 ? 0 : 1);
	}

	/**
	 * ��ȡ���һҳ��ҳ�룬���繲��5ҳ������4�����ֻ��0ҳ�򷵻�0��
	 * 
	 * @return int
	 */
	public long getLastPageNumber() {
		return Math.max(0, getPageCount() - 1);
	}

	public long getLastOffset() {
		return maxPageItems * getLastPageNumber();
	}

	/** ����Ҫ��������ʱ�Ƿ���ʾ���棬��form�������ʹ�� */
	private boolean promotion = false;

	/**
	 * Description:
	 * 
	 * @return String
	 */
	public boolean getPromotion() {
		return promotion;
	}

	public void setPromotion(boolean promotion) {
		this.promotion = promotion;
	}

	private StringBuffer queryString = null;

	/**
	 * ��ȡҳ���ѯ����
	 * 
	 * @return String
	 */
	public String getQueryString() {
		return queryString == null ? "" : queryString.toString();
	}

	/**
	 * ҳ���ϰ�����������
	 */
	private int params = 0;

	/**
	 * �жϵ�ǰҳ���Ƿ��������
	 * 
	 * @return
	 */
	public boolean hasParams() {
		return params != 0;
	}

	/*
	 * Tag Variables
	 */
	private StringBuffer uri = null;

	/**
	 * ҳ���ַ��
	 * 
	 * @return
	 */
	public String getUri() {
		return uri.toString();
	}

	/**
	 * Description:��ȡҳ���ѯ�Ĳ���
	 * 
	 * @return StringBuffer
	 */
	public String getQueryString(long offset, String sortKey, boolean desc) {
		String offsetString = "";
		if (queryString != null) {
			int length = queryString.length();
			queryString.append(params == 0 ? "" : "&").append(idOffsetParam)
					.append('=').append(offset);

			if (sortKey != null)
				queryString.append("&").append(getKey("sortKey")).append("=")
						.append(sortKey);
			queryString.append("&").append(getKey("desc")).append("=").append(
					desc);
			offsetString = queryString.toString();
			queryString.setLength(length);
		}
		return offsetString;
	}

	/**
	 * Description:��ȡҳ���ѯ�Ĳ���
	 * 
	 * @return StringBuffer
	 */
	public String getQueryString(String sortKey, boolean desc) {

		String offsetString = "";
		if (queryString != null && sortKey != null) {
			int length = queryString.length();
			queryString.append(params == 0 ? "" : "&")
					.append(getKey("sortKey")).append("=").append(sortKey);
			queryString.append("&").append(getKey("desc")).append("=").append(
					desc);
			offsetString = queryString.toString();
			queryString.setLength(length);
		}
		return offsetString;
	}

	/**
	 * ��ȡΨһ�Ĳ�������
	 * 
	 * @param oringine
	 * @return String ��������
	 */
	public String getKey(String oringine) {
		if (id == null || id.trim().equals("")) {
			return DEFAULT_ID + "." + oringine;
		} else
			return id + "." + oringine;
	}
	
	/**
	 * ��ȡΨһ�Ĳ�������
	 * 
	 * @param oringine
	 * @return String ��������
	 */
	public static String getKey(String id,String oringine) {
		if (id == null || id.trim().equals("")) {
			return DEFAULT_ID + "." + oringine;
		} else
			return id + "." + oringine;
	}
	
	/**
	 * ��ȡΨһ�Ĳ�������
	 * 
	 * @param oringine
	 * @return String ��������
	 */
	public static String getDefultKey(String oringine) {
		return getKey(null,oringine);
	}

	/**
	 * 
	 * Description:�����Զ���ķ�ҳ���������ӵ�ַ
	 * 
	 * @param formName
	 *            ������
	 * @param params
	 *            ҳ���������
	 * @param promotion
	 *            �ύ��ʱ�Ƿ���Ҫ��ʾ��Ϣ
	 * @param forwardUrl
	 *            ֱ����תҳ���ַ�� ��promotionΪtrueʱ��������ʾ�Ƿ��ύ���ݣ� ѡ��ʱ�ύ������ֱ����ת��������ָ����ҳ��
	 * @return String String
	 */
	protected String getCustomUrl(String formName, String params,
			boolean promotion, String forwardUrl, String id) {
		String customFirstUrl = "javascript:pageSubmit('" + formName + "','"
				+ params + "','" + forwardUrl + "'," + promotion + ",'" + id
				+ "')";
		return customFirstUrl;

	}

	/**
	 * ��ȡ��iҳ��ҳ��url
	 * 
	 * @param i
	 * @return String
	 */
	final String getPageUrl(long i) {
		// �жϵ�Ҫ��ȡ��ҳ��url�ǵ�ǰҳʱ�����ؿ�����
		if (i == this.pageNumber)// || i == (this.getPageCount() - 1))
			return "";
		// ����Ҫ��ת����˳��
		return getOffsetUrl(maxPageItems * i, this.getSortKey(), this.getDesc());
	}
	
	
	/**
	 * ��ȡ��iҳ��ҳ��url
	 * 
	 * @param i
	 * @return String
	 */
	final String getTruePageUrl() {
		
		
		// ����Ҫ��ת����˳��
		return getUrlWithoutOffset( this.getSortKey(), this.getDesc());
	}
	
	
	

	/**
	 * ��ȡ��pageOffset��sortKey������url
	 * 
	 * @param pageOffset
	 * @param sortKey
	 *            added by biaoping.yin on 2005-02-03
	 * @return String
	 */
	public final String getOffsetUrl(long pageOffset, String sortKey,
			boolean desc) {
		int uriLen = uri.length();
		// this.addParam(idOffsetParam,pageOffset + "");
		uri.append(params == 0 ? '?' : '&').append(idOffsetParam).append('=')
				.append(pageOffset);
		if (sortKey != null)
			uri.append("&").append(getKey("sortKey")).append("=").append(
					sortKey);
		uri.append("&").append(getKey("desc")).append("=").append(desc + "");
		String offsetUrl = uri.toString();
		uri.setLength(uriLen);
		return offsetUrl;
	}
	
	
	/**
	 * ��ȡ��pageOffset��sortKey������url
	 * 
	 * @param pageOffset
	 * @param sortKey
	 *            added by biaoping.yin on 2005-02-03
	 * @return String
	 */
	public final String getUrlWithoutOffset( String sortKey,
			boolean desc) {
		int uriLen = uri.length();
		uri.append(params == 0 ? '?' : '&').append(idOffsetParam).append('=')
		.append(0);
		
		if (sortKey != null)
			uri.append("&").append(getKey("sortKey")).append("=").append(
					sortKey);
		uri.append("&").append(getKey("desc")).append("=").append(desc + "");
		String offsetUrl = uri.toString();
		uri.setLength(uriLen);
		return offsetUrl;
	}

	/**
	 * ��ȡpageOffset��ʼ��pageOffset+maxPageItems�ļ�¼���ڵ�ҳ��
	 * 
	 * @param pageOffset
	 * @return Integer
	 */
	public final Long getOffsetPageNumber(long pageOffset) {
		return new Long(1 + pageNumber(pageOffset));
	}

	/**
	 * ������һҳ��¼��ʼλ��
	 * 
	 * @return int
	 */
	public final long getNextOffset() {
		return offset + maxPageItems;
	}

	/**
	 * �ж��Ƿ�����һҳ
	 * 
	 * @return boolean
	 */
	public final boolean hasNextPage() {
		if(!this.isMore())
		{
			boolean hasNextPage = (getItemCount() > getNextOffset());
			return hasNextPage;
		}
		else
		{
			//��ҳ��¼��
			if(this.getDataSize() < this.getMaxPageItems()) 
				return false;
			else
				return true;
			
			
		}
	}

	/**
	 * �ж��Ƿ�����һҳ
	 * 
	 * @return boolean
	 */
	public final boolean hasPrevPage() {
		return (offset > 0);
	}

	/**
	 * ������һҳ��¼����ʼλ��
	 * 
	 * @return int
	 */
	public final long getPrevOffset() {
		return Math.max(0l, offset - maxPageItems);
	}

	/**
	 * �ж�ҳ��page�Ƿ����
	 * 
	 * @param page
	 * @return boolean
	 */
	public final boolean hasPage(long page) {
		return (page >= 0 && getItemCount() > (page * maxPageItems));
	}

	public final boolean nextItem() {
		boolean showItem = false;
		if (itemCount < maxItems) {
			showItem = (itemCount >= offset && itemCount < (offset + maxPageItems));
			itemCount++;
		}
		return showItem;
	}

	public final long getOffset() {
		return offset;
	}

	protected final boolean isIndexNeeded() {
		return (offset != 0 || getItemCount() > maxPageItems);
	}

	public final String getCurrentUrl() {
		String ret = getOffsetUrl(getPageNumber() * maxPageItems, getSortKey(),
				getDesc());
		return ret;
		// return new
		// Input().setType(Input.HIDDEN).setValue(URLEncoder.encode(ret)).setName("curUrl").toString();

	}

	static final String PARAMETER = "parameter";

	static final String ATTRIBUTE = "attribute";

	public int getParams() {
		return this.params;
	}

	/**
	 * ��request�л�ȡ����ֵ�� ������ӵ��������� Description:
	 * 
	 * @param name
	 * @param type
	 *            void
	 */
	public final void addParamByRequest(String name, String type,
			Object defauleValue, boolean encode,int encodecount) {
		if (type == null)
			return;

		String value = null;
		if (type.equals(PARAMETER)) {
			value = request.getParameter(name);
		} else if (type.equals(ATTRIBUTE)) {
			value = (String) request.getAttribute(name);
		}

		if (value != null) {
			// value = value.trim();
			if (encode) {
//				name = URLEncoder.encode(name);
//				value = URLEncoder.encode(value);
				value = SimpleStringUtil.urlencode(value, "UTF-8",encodecount);
			}
			addQueryParam(name, value);

			uri.append(params == 0 ? '?' : '&').append(name).append('=')
					.append(value);
			params++;
		} else if (defauleValue != null) {
			// value = value.trim();
			if (encode) {

//				name = URLEncoder.encode(name);
//				value = URLEncoder.encode(defauleValue.toString());
				value = SimpleStringUtil.urlencode(defauleValue.toString(), "UTF-8",encodecount);
			} else {
				value = defauleValue.toString();
			}
			addQueryParam(name, value);

			uri.append(params == 0 ? '?' : '&').append(name).append('=')
					.append(value.toString());
			params++;
		}
	}
	
	/**
	 * ��request�л�ȡ����ֵ�� ������ӵ��������� Description:
	 * 
	 * @param name
	 * @param type
	 *            void
	 */
	public final void addParamsByRequest(String name, boolean encode,int encodecount) {
		

		String values[] = null;
		
		values = request.getParameterValues(name);
		
		if (values != null) {
			for(int i = 0; values != null && i < values.length; i ++)
			{
				String value = values[i];
				// value = value.trim();
				if (encode) {
//					name = URLEncoder.encode(name);
//					name = name;
//					value = URLEncoder.encode(value);
					value = SimpleStringUtil.urlencode(value, "UTF-8",encodecount);
				}
				addQueryParam(name, value);
	
				uri.append(params == 0 ? '?' : '&').append(name).append('=')
						.append(value);
				params++;
			}
		}
	}
	
	
	/**
	 * ��request�л�ȡ����ֵ�� ������ӵ��������� Description:
	 * 
	 * @param name
	 * @param type
	 *            void
	 */
	public final void addBeanParams(String name, boolean encode,String scope,int encodecount) {
		
		Object bean = request.getAttribute(name);
		if(bean == null)
			return ;
		if(bean instanceof Map)
		{
			mapParamsAppend((Map) bean, name,  encode, scope,encodecount);
		}
		else
		{
			beanParamsAppend( bean, name,  encode, scope,encodecount);
		}
		
	}
	
	private final void beanParamsAppend(Object bean,String name, boolean encode,String scope,int encodecount)
	{
//		BeanInfo beanInfo;
//		try {
//			
//			beanInfo = Introspector.getBeanInfo(bean.getClass());
//		} catch (IntrospectionException e1) {
//			return;
//		}
		ClassInfo beanInfo = ClassUtil.getClassInfo(bean.getClass());
		List<PropertieDescription> attributes = beanInfo.getPropertyDescriptors();
		
		
		if(attributes == null || attributes.size() <=0)
			return ;
//		Method m = null;
//		Field field= null;
		for(PropertieDescription f:attributes)
		{	
			name = f.getName();

			if(!f.canread())
				continue;
			
			IgnoreParam ignore = f.findAnnotation(IgnoreParam.class);

			if(ignore != null)
			{

					continue;
			}
			
			Object v = null;
			try
			{
				v = f.getValue(bean);
			}
			catch (Exception e)
			{
				continue;
			}
			
			if(v == null)
				continue;
			if(!v.getClass().isArray())
			{
				
				
				String value = String.valueOf(v);
				// value = value.trim();
				
				if (encode) {					
//					value = URLEncoder.encode(value);
					value = SimpleStringUtil.urlencode(value, "UTF-8",encodecount);
					
				}
				addQueryParam(name, value);
	
				uri.append(params == 0 ? '?' : '&').append(name).append('=')
						.append(value);
				params++;
			}
			else //��������ֵ
			{
				int size = Array.getLength(v);
				if(size == 0)
					continue;
				for(int i = 0; i < size; i  ++)
				{
					Object v_ = Array.get(v, i);
					String value = String.valueOf(v_);
					if (encode) {					
//						value = URLEncoder.encode(value);
						value = SimpleStringUtil.urlencode(value, "UTF-8",encodecount);
					}
					addQueryParam(name, value);
					uri.append(params == 0 ? '?' : '&').append(name).append('=')
							.append(value);
					params++;
				}
			}
		}
	}
	
	private final void mapParamsAppend(Map bean,String name, boolean encode,String scope,int encodecount)
	{
		if(bean.isEmpty())
			return;
		Iterator<Map.Entry> it = bean.entrySet().iterator();
		while(it.hasNext())
		{	
			Map.Entry entry = it.next();
			Object v = entry.getValue();
			
			
			if(v == null)
				continue;
			name = String.valueOf(entry.getKey());
			if(!v.getClass().isArray())
			{
				
				
				String value = String.valueOf(v);
				// value = value.trim();
				
				if (encode) {					
//					value = URLEncoder.encode(value);
					value = SimpleStringUtil.urlencode(value, "UTF-8",encodecount);
				}
				addQueryParam(name, value);
	
				uri.append(params == 0 ? '?' : '&').append(name).append('=')
						.append(value);
				params++;
			}
			else //��������ֵ
			{
				int size = Array.getLength(v);
				if(size == 0)
					continue;
				for(int i = 0; i < size; i  ++)
				{
					Object v_ = Array.get(v, i);
					String value = String.valueOf(v_);
					if (encode) {					
						value = SimpleStringUtil.urlencode(value, "UTF-8",encodecount);
//						value = URLEncoder.encode(value);
					}
					addQueryParam(name, value);
					uri.append(params == 0 ? '?' : '&').append(name).append('=')
							.append(value);
					params++;
				}
			}
		}
	}

	public final void addQueryParam(String name, String value) {

		queryString.append(params == 0 ? "" : "&").append(name).append('=')
				.append(value);
	}

	public final void addParam(String name, String value, Object defaultValue,
			boolean encode,int encodecount) {
		// String[] values = null;
		if (value == null) {
			value = request.getParameter(name);
			if (value == null)
				value = (String) request.getAttribute(name);
			// if(value != null)
			// values = new String[] {value};
		}
		// else
		// values = new String[] {value};
		if (value != null) {
			// value = value.trim();
			String tempName = "";
			String tempValue = "";
			if (encode) {
//				tempName = URLEncoder.encode(name);
				tempName = name;
//				tempValue = URLEncoder.encode(value);
				tempValue = SimpleStringUtil.urlencode(value, "UTF-8",encodecount);
			} else {
				tempName = name;
				tempValue = value;
			}
			uri.append(params == 0 ? '?' : '&').append(tempName).append('=')
					.append(tempValue);
			addQueryParam(tempName, tempValue);
			// queryString.append(params == 0 ? "" :
			// "&").append(name).append('=').append(values[i]);
			params++;
			// for(int i = 0; i < values.length; i ++)
			// {
			// System.out.println("values[" + i + "]:" + values[i]);
			//
			//
			// }
		} else if (defaultValue != null) {
			String tempName = "";
			String tempValue = "";
			if (encode) {
//				tempName = URLEncoder.encode(name);
				tempName = name;
//				tempValue = URLEncoder.encode(defaultValue.toString());
				tempValue = SimpleStringUtil.urlencode(defaultValue.toString(), "UTF-8",encodecount);
				
			} else {
				tempName = name;
				tempValue = defaultValue.toString();
			}

			uri.append(params == 0 ? '?' : '&').append(tempName).append('=')
					.append(tempValue);
			addQueryParam(tempName, tempValue);
			// queryString.append(params == 0 ? "" :
			// "&").append(name).append('=').append(values[i]);
			params++;
		}

		// else {
		// /**
		// * Ϊ�������������ʱ����Ҫ��������Ҫ���ɴ�
		// */
		// //// String[] values =
		// pageContext.getRequest().getParameterValues(name);
		// ////
		// //// if (values != null) {
		// //// name = URLEncoder.encode(name);
		// //// for (int i = 0, l = values.length; i < l; i++) {
		// //// value = URLEncoder.encode(values[i]);
		// //// uri.append(params == 0 ? '?' : '&')
		// //// .append(name).append('=').append(value);
		// ////
		// //// params++;
		// //// }
		// //// }
		// //
		// // value = (String)pageContext.getRequest().getAttribute(name);
		// // //value = (String)pageContext.getRequest().getgetAttribute(name);
		// // //System.out.println(name + ":"+value);
		// if (value != null)
		// {
		// name = URLEncoder.encode(name);
		// value = URLEncoder.encode(value);
		//
		// uri.append(params == 0 ? '?' : '&')
		// .append(name).append('=').append(value);
		//
		// params++;
		//
		// }
		//
		// }

	}

	private String scope = null;

	public final String getScope() {
		return scope;
	}

	/**
	 * pageNumberInteger����װ��ǰҳҳ��Ķ���
	 */
	private Long pageNumberInteger = null;

	/**
	 * ������Ҫ���������ķ�����
	 */
	private PagerTagExport pagerTagExport = null;

	private Object oldPager = null;

	private Object oldOffset = null;

	private Object oldPageNumber = null;

	final void initContext() throws LoadDataException {
		String offsetParam = null;
		PagineContext context = null;
		/**
		 * ��������ݹ���ϵͳ����Ƶ�������������offset����ͨ�������������Ĵ������
		 */
		CMSServletRequest cmsrequest = InternalImplConverter
				.getInternalRequest(request);
		if (cmsrequest != null && !ListMode()) {

			context = (PagineContext) cmsrequest.getContext();
			if (!(context instanceof ContentContext)) /**
			 * 
			 * �����ǰ�������ڷ����ĵ������ĵ��в�������и�����ҳ����������Ŀ���е�������÷�ҳ����������ȡ�ĵ���ͷ���� ��������ǲ������
			 */
			{
				context.setMaxPageItems(this.getMaxPageItems());
				offsetParam = context.getOffset() + "";
			} else {
				context.getPublishMonitor().addFailedMessage(
						"�ĵ�ϸ��ҳ���еĸ�����ǩ������isList����Ϊfalse",
						context.getPublisher());
			}
		} else {
			offsetParam = request.getParameter(idOffsetParam);
			if (offsetParam == null) {
				offsetParam = (String) request.getAttribute(idOffsetParam);
			}
		}
		/**
		 * ��ʼ��offset
		 */

		if (offsetParam == null)
			offset = 0;
		if (offsetParam != null || !ListMode()) {

			try {
				if (offsetParam != null)
					offset = Math.max(0l, Long.parseLong(offsetParam));
				setDataInfo();
				long newPageCount = pageNumber(offset);
				if(!this.isMore())
				{
					long lastPagerNumber = getLastPageNumber();
					if (newPageCount > lastPagerNumber) {
						offset = lastPagerNumber * getMaxPageItems();
//						/**
//						 * ���»�ȡ���ݣ�������ݿ�ײ��ܹ�����������⣬������Ҫ���»�ȡ����
//						 */
//						setDataInfo();
					}
				}
				
				if (context != null && !(context instanceof ContentContext)) {
					context.setOffset(offset);
					/**
					 * �����ǰ��ҳ���offset�б仯��Ҫ����context�е�ֵ
					 */
					context.setCurrentPageNumber((int) newPageCount);
					context.setTotalSize(this.getTotalSize());
				} else if (context instanceof ContentContext) {
					// context.getPublishMonitor().addFailedMessage("�ĵ�ϸ��ҳ���еĸ�����ǩ������isList����Ϊfalse",context.getPublisher());
				}

				// if (isOffset) ��2004/4/30ע��
				itemCount = offset;

			} catch (NumberFormatException ignore) {
				ignore.printStackTrace();
			}
		} else {
			setDataInfo();
		}
		// this.addQueryParam(idOffsetParam,offset + "");

		pageNumber = pageNumber(offset);

		pageNumberInteger = new Long(1 + pageNumber);

		if (REQUEST.equals(scope)) {
			/*
			 * id��ֵΪ��pager��,������ǰ��ҳ��
			 */
			// oldPager = request.getAttribute(id);
			// request.setAttribute(id, this);

			if (pagerTagExport != null) {
				String name;
				if ((name = pagerTagExport.getPageOffset()) != null) {
					oldOffset = request.getAttribute(name);
					request.setAttribute(name, new Long(offset));
				}
				if ((name = pagerTagExport.getPageNumber()) != null) {
					oldPageNumber = request.getAttribute(name);
					request.setAttribute(name, pageNumberInteger);
				}
			}
		} else {
			if (pagerTagExport != null) {
				String name;
				if ((name = pagerTagExport.getPageOffset()) != null) {
					/**
					 * offset����ǰҳ���һ����¼idֵ, ����offset=80��ʾ��ǰҳ���һ����¼idֵΪ80
					 */
					oldOffset = pageContext.getAttribute(name);
					pageContext.setAttribute(name, new Long(offset));
				}
				if ((name = pagerTagExport.getPageNumber()) != null) {
					/**
					 * offset����ǰҳ���һ����¼idֵ, ����offset=80��ʾ��ǰҳ���һ����¼idֵΪ80
					 */
					oldPageNumber = pageContext.getAttribute(name);
					pageContext.setAttribute(name, pageNumberInteger);
				}
			}
		}
	}

	private String data = null;

	/**
	 * ��ȡ���ݻ�ȡ����request�еĴ������
	 * 
	 * @return String ���ݻ�ȡ����request�еĴ������
	 */
	public String getData() {

		return data == null ? "dataInfo" : data;
	}

	public final int getMaxPageItems() {
		return maxPageItems;
	}
	

	/**
	 * ��ʼ�����ݻ�ȡ�ӿ�
	 * 
	 * @param dataType
	 *            �ӿ��������ļ���Ӧ����������
	 */
	public void setDataInfo() throws LoadDataException {
		
		if (this.tag instanceof PagerTag) {
			String dataType = this.getData();
			// ����Ƿ��������ݿ��ѯ��䣬���������ֱ�ӹ���������ݿ�ʵ�ֵ�ȱʡDataInfo�ӿڣ�
			// �����request�����л�ȡDataInfo�ӿ�
			if (statement != null && !statement.equals("")) {
				DefaultDataInfoImpl dataInfo_ = new DefaultDataInfoImpl();
				this.dataInfo = dataInfo_;
				try {
					dataInfo_.setMoreQuery(moreQuery);
					this.setMore(moreQuery);
					dataInfo.initial(statement, dbname, getOffset(),
							getMaxPageItems(), ListMode(), request, this
									.getSQLParams());
				} catch (SetSQLParamException e) {
					throw new LoadDataException(e);
				}
				if (!ListMode() ) {
					long totalsize = dataInfo.getItemCount();
					if(!dataInfo.isMore())
						setItems(totalsize);
				}
			} else {
				Object dataInfo_temp =  request.getAttribute(dataType);
				if(dataInfo_temp instanceof DataInfo)
				{
					dataInfo = (DataInfo) dataInfo_temp;
					if (dataInfo == null) {
						log.info("����DataInfo�����Ѿ�������ȷ");
						return;
					}
	
					/**
					 * �����ֱ�ӵ�ȱʡ�����ݿ�ʵ�֣����ø�ʵ�ֵĳ�ʼ�������� �������ͨ�õĳ�ʼ������
					 */
					if (dataInfo instanceof DefaultDataInfoImpl)
						dataInfo.initial(null, null, getOffset(),
								getMaxPageItems(), this.ListMode(),
								// String sortKey,
								// boolean desc,
								request);
					else {
						dataInfo.initial(getSortKey(), this.desc, getOffset(),
								getMaxPageItems(), this.ListMode(), request);
	
					}
				}
				else if(PagerContext.isPagerMehtod(request) )
				{
					if(dataInfo_temp != null && dataInfo_temp instanceof ListInfo)
						this.dataInfo = new ListInfoDataInfoImpl((ListInfo)dataInfo_temp);
				}
				// ����Ƿ�ҳģʽ���ü�¼����
				if (dataInfo != null && !ListMode()) {
					
					long totalsize = dataInfo.getItemCount();
					this.setMore(dataInfo.isMore());
					if(!dataInfo.isMore())
						setItems(totalsize);
				}

			}
		} else if (this.tag instanceof PagerDataSet) {
			// PagerDataSet listTag = (PagerDataSet)tag;
			/**
			 * �ж��Ƿ���Ƕ���б������Ƕ���б���������������ԣ� colName,property,sortKey,desc
			 */
			// System.out.println("this:" + this);
			if (getColName() != null) {
				try {
					load(COLUMN_SCOPE);
				} catch (LoadDataException e) {
//					log.info(e.getMessage());
					throw e;
					// return SKIP_BODY;
				}

			} else if (statement != null && !statement.equals("")) {
				try {
					load(DB_SCOPE);
				} catch (LoadDataException e) {
//					log.info(e.getMessage());
					// return SKIP_BODY;
					throw e;
				}
			}

			else if (requestKey != null) {

				try {
					load(REQUEST_SCOPE);
					// data = request.getAttribute(listTag.requestKey);
				} catch (LoadDataException e) {
//					log.info(e.getMessage());
					// return SKIP_BODY;
					throw e;
				}

			}
			else if (this.actual != null) {

				try {
					load(ACTUAL_SCOPE);
					// data = request.getAttribute(listTag.requestKey);
				} catch (LoadDataException e) {
//					log.info(e.getMessage());
					// return SKIP_BODY;
					throw e;
				}

			}

			else if (sessionKey != null)
				try {
					load(SESSION_SCOPE);
				} catch (LoadDataException e1) {
//					log.info(e1.getMessage());
					// return SKIP_BODY;
					throw e1;
				}
			else if (pageContextKey != null) {
				try {
					load(PAGECONTEXT_SCOPE);
				} catch (LoadDataException e2) {
//					log.info(e2.getMessage());
					// return SKIP_BODY;
					throw e2;
				}
			}
			
			
			else if (this.tag instanceof CMSListTag) {
				CMSListTag cmsListTag = (CMSListTag) tag;
				CMSBaseListData dataInfo = CMSTagUtil
						.getCMSBaseListData(cmsListTag.getDatatype());
				/**
				* ����cms��Ҫ�ſ�ע�͵Ĵ��� 
				* https://github.com/bbossgroups/bboss-cms.git
				* */
				Map<String,Object> params = cmsListTag.getParams();
				if(params != null)
				{
					if(!params.containsKey("doctype") && cmsListTag.getDocType() != null)
					{
						params.put("doctype", cmsListTag.getDocType() );
					}
					else 
					{
						
					}
					dataInfo.setOutlineInfo(cmsListTag.getSite(), cmsListTag
							.getChannel(), cmsListTag.getCount(), params);

				
				}
				else
				{
					dataInfo.setOutlineInfo(cmsListTag.getSite(), cmsListTag
							.getChannel(), cmsListTag.getCount(), cmsListTag.getDocType());
				}
				/**
				* �ɵ�cmsʹ�����´��룬������Ҫ����dataInfo.setOutlineInfo(cmsListTag.getSite(), cmsListTag
						.getChannel(), cmsListTag.getCount());
				
				dataInfo.setOutlineInfo(cmsListTag.getSite(), cmsListTag
						.getChannel(), cmsListTag.getCount());*/
				if (cmsListTag.getDocumentid() != null)
					dataInfo.setDocumentid(cmsListTag.getDocumentid());

				dataInfo.initial(getSortKey(), this.desc, getOffset(),
						getMaxPageItems(), this.ListMode(), request);
				this.dataInfo = dataInfo;
				// ����Ƿ�ҳģʽ���ü�¼����
				if (!ListMode()) {
					setItems(dataInfo.getItemCount());
				}
			} else {
				try {
					load(CELL_SCOPE);
				} catch (LoadDataException e2) {
//					log.info(e2.getMessage());
					// return SKIP_BODY;
					throw e2;
				}
			}

		}

	}

	public SQLParams getSQLParams() throws SetSQLParamException {
		if (this.getSQLExecutor() != null)
			return this.getSQLExecutor().getSQLParams();
		return null;
	}

	// protected PagerDataSet searchDataSet(Tag obj,Class clazz) {
	// PagerDataSet dataSet = null;
	//		
	// if (this.getIndex() < 0) {
	// dataSet =
	// (PagerDataSet)BaseTag.findAncestorWithClass(obj, clazz);
	// } else {
	// //int idx = index();
	// java.util.Stack stack =
	// (java.util.Stack) request.getAttribute(
	// PagerDataSet.PAGERDATASET_STACK);
	// dataSet = (PagerDataSet) stack.elementAt(this.getIndex());
	// }
	// return dataSet;
	// }

	protected void load(String scope) throws LoadDataException {
		Object data = null;
		if (scope.equals(REQUEST_SCOPE) ) {

			// dataInfo = (Collection)request.getAttribute(requestKey);
			data = request.getAttribute(requestKey);
		}
		else if(scope.equals(ACTUAL_SCOPE))
		{
			data = this.actual;
		}
		
		else if (scope.equals(DB_SCOPE)) {
//			this.dataInfo = new DefaultDataInfoImpl();
			/**
			 * �����ֱ�ӵ�ȱʡ�����ݿ�ʵ�֣����ø�ʵ�ֵĳ�ʼ�������� �������ͨ�õĳ�ʼ������
			 */

			DefaultDataInfoImpl dataInfo_ = new DefaultDataInfoImpl();
			this.dataInfo = dataInfo_;
			try {
				dataInfo_.setMoreQuery(moreQuery);
				this.setMore(moreQuery);
				dataInfo.initial(this.statement, this.dbname, getOffset(),
						getMaxPageItems(), this.ListMode(),
						// String sortKey,
						// boolean desc,
						request, this.getSQLParams());
			} catch (SetSQLParamException e) {
				throw new LoadDataException(e);
			}
			//			
			// ����Ƿ�ҳģʽ���ü�¼����
			if (!ListMode()) {
				long totalsize = dataInfo.getItemCount();
				if(!dataInfo.isMore())
					setItems(totalsize);
			}
			return;
		} else if (session != null && scope.equals(SESSION_SCOPE)) {
			data = session.getAttribute(sessionKey);
		} else if (scope.equals(PAGECONTEXT_SCOPE)) {
			data = pageContext.getAttribute(pageContextKey);
		} else if (scope.equals(COLUMN_SCOPE)) {

			PagerDataSet dataSet = ((PagerDataSet) tag).searchDataSet(tag,
					PagerDataSet.class);

			// System.out.println("dataSet:" + dataSet);
			// System.out.println("dataSet.getRowid():" + dataSet.getRowid());
			/**
			 * ���ݵ���getProperty()��������ֵ�жϵ�ǰ���Ƿ�Ϊbean ���Ϊ��,��ʾ�ֶ�Ϊcollection
			 * 
			 * �����ʾ�ֶ���һ��javabean,getProperty()���ص�ֵΪ�ö����һ�����ԣ�����Ϊcollection
			 */

			if (getProperty() == null) {

				data = dataSet.getValue(dataSet.getRowid(), getColName());

				// System.out.println("dataInfo.size:" + dataInfo.size());
			} else {
				data = dataSet.getValue(dataSet.getRowid(), getColName(),
						getProperty());
			}

		} else if (scope.equals(CELL_SCOPE)) {
			if(request instanceof CMSServletRequest)
			{
				CMSServletRequest cmsRequest = (CMSServletRequest) request;
				// CMSDetailDataLoader dataLoader =
				// (CMSDetailDataLoader)request.getAttribute("dataset."
				// + cmsRequest.getContext().getID());
	
				CMSDetailDataLoader dataLoader = cmsRequest.getContext()
						.getCMSDetailDataLoader();
				try {
					if (dataLoader == null)
						return;
					data = dataLoader.getContent((ContentContext) cmsRequest
							.getContext());
				} catch (CMSDataLoadException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				PagerDataSet dataSet = ((PagerDataSet) tag).searchDataSet(tag,
						PagerDataSet.class);
				data = dataSet.getValue(dataSet.getRowid());
			}
		}

		else
			throw new LoadDataException("load data from " + scope + " error");

		if (data == null)
			throw new LoadDataException("load data from " + scope
					+ " :dataInfo == null or dataInfo.size() == 0");
		// try {
		// if(data instanceof Collection)
		// loadClassData((Collection)data);
		// else if(data instanceof Iterator)
		// loadClassData((Iterator)data);
		// else if(data instanceof Object[])
		// {
		// loadClassData((Object[])data);
		// }
		// else if(data instanceof DefaultDataInfoImpl)
		// loadClassData(data);

		// } catch (LoadDataException e) {
		// log.info(e.getMessage());
		// //e.printStackTrace();
		// }
		
//		boolean islistinfo = (data instanceof ListInfo);
		if(!PagerContext.isPagerMehtod(request))
		{
			this.dataInfo = new ObjectDataInfoImpl(data);
		}
		else
		{
			if(  data instanceof ListInfo)
			{
				this.dataInfo = new ListInfoDataInfoImpl((ListInfo)data);
				// ����Ƿ�ҳģʽ���ü�¼����
				if (!ListMode()) {
					long totalsize = dataInfo.getItemCount();
					if(!dataInfo.isMore())
						setItems(totalsize);
				}
			}
			else
			{
				this.dataInfo = new ObjectDataInfoImpl(data);
			}
		}
	}

	public final void setItems(long value) {

		items = value;
	}

	public final long getItems() {
		return items;
	}

	/**
	 * ��ȡ��sortKey������url,���б������������
	 * 
	 * @param sortKey
	 *            added by biaoping.yin on 2005-02-03
	 * @return String
	 */
	public final String getUrl(String sortKey, boolean desc) {
		int uriLen = uri.length();
		// this.addParam(idOffsetParam,pageOffset + "");
		uri.append(params == 0 ? '?' : '&').append(getKey("sortKey")).append(
				"=").append(sortKey);
		uri.append("&").append(getKey("desc")).append("=").append(desc + "");
		String offsetUrl = uri.toString();
		uri.setLength(uriLen);
		return offsetUrl;
	}

	/**
	 * ��ȡ����ҳ�Ŀ�ʼҳ��
	 * 
	 * @return int
	 */
	final long getFirstIndexPage() {
		long firstPage = 0;
		long halfIndexPages = maxIndexPages / 2;
		if (FORWARD.equals(navindex)) {
			firstPage = Math.min(pageNumber + 1, getPageCount());
		} else if (!(HALF_FULL.equals(navindex) && pageNumber < halfIndexPages)) {
			long pages = getPageCount();
			if (pages > maxIndexPages) {
				// put the current page in middle of the index
				firstPage = Math.max(0, pageNumber - halfIndexPages);

				long indexPages = pages - firstPage;
				if (indexPages < maxIndexPages)
					firstPage -= (maxIndexPages - indexPages);
			}
		}

		return firstPage;
	}

	/**
	 * ��ȡ����ҳ����ֹλ��
	 * 
	 * @param firstPage
	 * @return int
	 */
	final long getLastIndexPage(long firstPage) {
		long pages = getPageCount();
		long halfIndexPages = maxIndexPages / 2;
		long maxPages;
		if (HALF_FULL.equals(navindex) && pageNumber < halfIndexPages) {
			maxPages = pageNumber + halfIndexPages;
		} else {
			maxPages = firstPage + maxIndexPages;
		}
		return (pages <= maxPages ? pages : maxPages) - 1;
	}

	/**
	 * ��ȡ��iҳ��ҳ��
	 * 
	 * @param i
	 * @return Integer
	 */
	final Long getPageNumber(long i) {
		if (i == pageNumber)
			return pageNumberInteger;
		return new Long(1 + i);
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public final void setMaxIndexPages(int value) {
		maxIndexPages = value;
	}

	public final int getMaxIndexPages() {
		return maxIndexPages;
	}

	public String getNavindex() {
		return navindex;
	}

	public void setNavindex(String navindex) {
		this.navindex = navindex;
	}

	private String url = null;

	private boolean isOffset;
	

	public final String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUri() {
		String baseUri;
		if (url != null) {
			baseUri = url;
		} else {

			baseUri = request.getRequestURI();
			// System.out.println(baseUri);
			int i = baseUri.indexOf('?');
			if (i != -1)
				baseUri = baseUri.substring(0, i);
		}

		if (uri == null)
			uri = new StringBuffer(baseUri.length() + 32);
		else
			uri.setLength(0);
		if (queryString == null)
			queryString = new StringBuffer(32);
		else
			// ����bug�����queryString���ݣ�����queryString�������ۻ�
			queryString.setLength(0);
		uri.append(baseUri);
	}

	public void release() {
		if (REQUEST.equals(scope)) {
			restoreAttribute(request, id, oldPager);

			request.removeAttribute(getData());
			oldPager = null;
			if (pagerTagExport != null) {
				String name;
				if ((name = pagerTagExport.getPageOffset()) != null) {
					restoreAttribute(request, name, oldOffset);
					oldOffset = null;
				}

				if ((name = pagerTagExport.getPageNumber()) != null) {
					restoreAttribute(request, name, oldPageNumber);
					oldPageNumber = null;
				}
			}

		} else {
			if (pagerTagExport != null) {
				String name;
				if ((name = pagerTagExport.getPageOffset()) != null) {
					restoreAttribute(pageContext, name, oldOffset);
					oldOffset = null;
				}

				if ((name = pagerTagExport.getPageNumber()) != null) {
					restoreAttribute(pageContext, name, oldPageNumber);
					oldPageNumber = null;
				}
			}
		}
	}

	public static void restoreAttribute(PageContext pageContext, String name,
			Object oldValue) {
		if (oldValue != null)
			pageContext.setAttribute(name, oldValue);
		else
			pageContext.removeAttribute(name);
	}

	public static void restoreAttribute(ServletRequest request, String name,
			Object oldValue) {
		if (oldValue != null)
			request.setAttribute(name, oldValue);
		else
			request.removeAttribute(name);
	}

	public void setIsOffset(boolean isOffset) {
		this.isOffset = isOffset;

	}

	public boolean isOffset() {
		return isOffset;
	}

	public void setMaxItems(int maxItems) {
		this.maxItems = maxItems;
	}

	public void setMaxPageItems(int maxPageItems) {
		this.maxPageItems = maxPageItems;

	}
	
	public void setCustomMaxPageItems(int customMaxPageItems)
	{
		this.customMaxPageItems = customMaxPageItems;
	}

	public void setData(String data) {
		this.data = data;

	}

	/**
	 * �����ǰҳ��ʵ�ʽ������¼��
	 * 
	 * @see com.frameworkset.common.tag.pager.tags.PagerInfo#getDataSize()
	 */
	public int getDataSize() {

		return dataInfo == null ? 0 : dataInfo.getDataSize();
	}
	
	/**
	 * �����ǰҳ�����ݿ�ԭʼ��¼���������ܾ���Ӧ�ó��������ݼ�¼�ᷢ���仯������ͨ��getDataSize������ȡʵ�ʵ�ҳ��¼��
	 * 
	 * @see com.frameworkset.common.tag.pager.tags.PagerInfo#getDataSize()
	 */
	public int getDataResultSize() {

		return dataInfo == null ? 0 : dataInfo.getDataResultSize();
	}

	/**
	 * ��ȡҳ����ܼ�¼��
	 * 
	 * @return
	 */
	public long getTotalSize() {
		return dataInfo == null ? 0 : dataInfo.getItemCount();
	}

	public String getPageContextKey() {
		return pageContextKey;
	}

	public void setPageContextKey(String pageContextKey) {
		this.pageContextKey = pageContextKey;
	}

	public String getRequestKey() {
		return requestKey;
	}

	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public void setIndex(int index) {
		this.index = index;

	}

	public int getIndex() {
		return index;
	}

	public String getCommitevent() {
		return commitevent;
	}

	public void setCommitevent(String commitevent) {
		this.commitevent = commitevent;
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public SQLExecutor getSQLExecutor() {
		return sqlExecutor;
	}

	public void setSQLExecutor(SQLExecutor sqlExecutor) {
		this.sqlExecutor = sqlExecutor;
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

	/**
	 * @return the cookieid
	 */
	public String getCookieid() {
		return cookieid;
	}

	/**
	 * @param cookieid the cookieid to set
	 */
	public void setCookieid(String cookieid) {
		this.cookieid = cookieid;
	}

	/**
	 * @return the customMaxPageItems
	 */
	public int getCustomMaxPageItems() {
		return customMaxPageItems;
	}

	public boolean isNotifyed() {
		return notifyed;
	}

	public void setNotifyed(boolean notifyed) {
		this.notifyed = notifyed;
	}
	/**********************bboss mvc��ҳ��Ϲ�����Ҫ�ķ��� ��ʼ**************************************************/ 
	public static String HandlerMapping_pathWithinHandlerMapping = "org.frameworkset.web.servlet.HandlerMapping.pathWithinHandlerMapping";
	 public static String getPathwithinHandlerMapping(HttpServletRequest request)
	{
		 String urlpath = (String) request.getAttribute(HandlerMapping_pathWithinHandlerMapping);
		 if(urlpath != null && urlpath.startsWith("/") )
		 {
			 urlpath = request.getContextPath() + urlpath;
		 }
		 return urlpath;
			 
	}
	 public static String HandlerMapping_requesturi = "org.frameworkset.web.servlet.HandlerMapping.requesturi";
	 public static String getHandlerMappingRequestURI(HttpServletRequest request)
		{
			 String urlpath = (String) request.getAttribute(HandlerMapping_requesturi);
			
			 return urlpath;
				 
		}
	 
	 public static String HandlerMapping_HandlerMappingPath = "org.frameworkset.web.servlet.HandlerMapping.HandlerMappingPath";
	 public static String getHandlerMappingPath(HttpServletRequest request)
		{
			return (String) request
			.getAttribute(HandlerMapping_HandlerMappingPath);
		}
	 public static String HandlerMapping_PAGER_METHOD_FLAG = "org.frameworkset.web.servlet.HandlerMapping.PAGER_METHOD_FLAG";
	 public static boolean isPagerMehtod(HttpServletRequest request)
	{
		 Object value = request.getAttribute(HandlerMapping_PAGER_METHOD_FLAG);
		 if(value == null)
			 return false;
		return (Boolean)value;
	}
	 public static String HandlerMapping_PAGER_PAGESIZE_FLAG = "org.frameworkset.web.servlet.HandlerMapping.PAGER_PAGESIZE_FLAG";
	 public static int getControllerPagerSize(HttpServletRequest request)
		{
		 Object value = request.getAttribute(HandlerMapping_PAGER_PAGESIZE_FLAG);
		 if(value == null)
			 return PagerTag.DEFAULT_MAX_PAGE_ITEMS;
			return (Integer)value;
		}
	 public static String HandlerMapping_PAGER_CUSTOM_PAGESIZE = "org.frameworkset.web.servlet.HandlerMapping.PAGER_CUSTOM_PAGESIZE";
	 /**
	  * ��ȡ�Զ����ҳ����
	  * @param request
	  * @return
	  */
	 public static int getCustomPagerSize(HttpServletRequest request)
		{
		 Object value = request.getAttribute(HandlerMapping_PAGER_CUSTOM_PAGESIZE);
		 if(value == null)
			 return PagerTag.DEFAULT_MAX_PAGE_ITEMS;
			return (Integer)value;
		}
	 public static String HandlerMapping_PAGER_COOKIEID = "org.frameworkset.web.servlet.HandlerMapping.PAGER_COOKIEID";
	 public static String getControllerCookieID(HttpServletRequest request)
		{
			return (String)request.getAttribute(HandlerMapping_PAGER_COOKIEID);
		}
	 /**********************bboss mvc��ҳ��Ϲ�����Ҫ�ķ��� ����**************************************************/

	public boolean isMoreQuery() {
		return moreQuery;
	}

	public void setMoreQuery(boolean moreQuery) {
		this.moreQuery = moreQuery;
	}

	public boolean isMore() {
		return more;
	}

	public void setMore(boolean more) {
		this.more = more;
	}
}
