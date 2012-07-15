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

package com.frameworkset.common.tag.tree.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;

import org.apache.log4j.Logger;
import org.frameworkset.web.servlet.support.RequestContextUtils;

import com.frameworkset.common.tag.BaseTag;
import com.frameworkset.common.tag.tree.COMTree;
import com.frameworkset.common.tag.tree.TreeFactory;

/**
 * ���û�ȡ���Ľڵ�������Ϣ��Ϊ�������ڵ㹹�����ڵ�
 * @author biaoping.yin
 * created on 2005-3-25
 * version 1.0
 */

public class TreeData extends BaseTag {

	private boolean enablecontextmenu = false;
	/**
		 * �����ڵ�checkbox ��ֵ
		 */
	

	private String checkboxValue;

	/**
	 * ���������ݻ������÷�Χ��
	 * request
	 * session
	 * pageContext
	 * ȱʡΪsession
	 *
	 */
	private String scope = "session";

	/**
	 * �����ڵ�radio��ťֵ
	 */
	private String radioValue;
	private final static Logger log = Logger.getLogger(TreeData.class);
	/**
	 * �����ڵ�id
	 */
	private String rootid = "-1";
	/**
	 * �����ڵ�����
	 */
	private String rootName = "���ڵ�";
	
	private String rootNameCode;
	/**
	 * ��Ĭ��չ���㼶
	 */
	private String expandLevel = "1";

	/**
	 * ��ѡģʽ��true:��ѡ��false:��ѡ
	 */
	private String singleSelection = "false";
	/**
	 * �Ƿ�ʵʱ���ÿ���ڵ����Ϣ�������ڵ��Ƿ���ڡ��ڵ�����ơ���û�ж��ӵ���Ϣ
	 * true��ʵʱ
	 * false:ÿ��ֻ���ٵ�ǰ�ڵ����Ϣ
	 */
	private boolean needObserver = false;

	/**
	 * �����Կ����Ƿ�ʱˢ�´���ĳ���¼��Ľ���ڵ���Ϣ
	 */
	protected boolean refreshNode = true;

	private String treetype = "1";

	/**
	 * ��չ������ͨ�����������������Ľڵ�
	 */
	private String extCondition = "";

	private String showRootHref = "true";

	/**
	 * ������ڵ㱸ע��Ϣ
	 */
	private String memo = "";
	
	/**
	 * ָ���Ƿ�����ڵ��������,ȱʡΪtrue
	 */
	private boolean sortable = false;

    /**
     * ��·����Ӧ���ļ�·��
     */
    private String path = null;
    
    public int doEndTag() throws JspException
	{
//		this.checkboxValue = null;
//		this.enablecontextmenu = false;
//		this.expandLevel = "1";
//		this.extCondition = "";
//		this.id = null;
//		this.memo = "";
//		this.needObserver = false;
//		this.path = null;
//		this.radioValue = null;
//		this.refreshNode = true;
//		this.rootid = "-1";
		this.rootName = "���ڵ�";
//		this.scope = "session";  
//		this.showRootHref = "true" ;
//		this.singleSelection = "false";
//		this.sortable = false;
//		this.treetype = "1";
		
			    
		return super.doEndTag();		
	}

	/**
	 * ���ڵ㸴ѡ���ֵ
	 * @return String
	 */
	public String getCheckboxValue() {
		return checkboxValue;
	}

	/**
	 * @return String
	 */
	public String getRadioValue() {
		return radioValue;
	}

	/**
	 * @param string
	 */
	public void setCheckboxValue(String string) {
		checkboxValue = string;
	}

	/**
	 * @param string
	 */
	public void setRadioValue(String string) {
		radioValue = string;
	}

	public TreeData() {

	}

	private String getExtCondition() {
		String extCondition = this.getHttpServletRequest().getParameter("extCondition");
		if (extCondition == null)
			extCondition = (String) this.getHttpServletRequest().getAttribute("extCondition");
		return extCondition;
	}
	
	public boolean needloadroot(TreeTag parent)
	{
		String expandid = request.getParameter(parent.getExpandParam());
		return expandid == null || this.getScope().equals("session");
	}

	public int doStartTag() {
		TreeTag parent = (TreeTag) this.getParent();
		//���û���������Ч��Χ
		parent.setScope(getScope());
		parent.setEnablecontextmenu(this.isEnablecontextmenu());
		String key = parent.getTree();
        
		extCondition = getExtCondition();
		String request_scope = this.getHttpServletRequest().getParameter("request_scope");

		COMTree comTree  = null;
		HttpSession session = this.getSession();
		HttpServletRequest request = this.getHttpServletRequest();
		//��session�л�ȡcom tree
		if(session != null &&getScope().equals("session"))
			comTree = (COMTree) session.getAttribute(key);
		//��pageContext�л�ȡcom tree
		else if(getScope().equals("pageContext"))
			comTree = (COMTree) pageContext.getAttribute(key);
		//��request_session�л�ȡcom treeʵ��

//		else if(session != null &&request_scope != null && request_scope.equals("request"))
		else if(request_scope != null && request_scope.equals("request"))
		{
//			comTree = (COMTree) session.getAttribute(key);
			comTree = (COMTree) request.getAttribute(key);
		}
		String parent_indent = request.getParameter("node_parent_indent");
		if(comTree != null)
        {
            comTree.setPageContext(pageContext);
            comTree.setNeedObservable(needObserver());
            comTree.setRefreshNode(isRefreshNode());
            comTree.setExtCondition(extCondition);
            comTree.setEnablecontextmenu(this.isEnablecontextmenu());
            comTree.setSortable(this.isSortable());
            
            /**
             * �����һ�ι���������ϵ��Ҽ��˵���Ҫ�������е������Ҽ��˵���
             * ������Ҫ���֮ǰ���������нڵ����͵��Ҽ��˵�
             */
	        if( parent_indent == null)
	        	comTree.buildContextMenusWraper();
	        else
	        	comTree.clearNodeContextmenus();
	        
            
        }
		//�������Ϊkey�����Ѿ���session�д��ڣ����ж������Ƿ���ͬ���������ͬ����Ҫ���³�ʼ����
        
		if (comTree != null
			&& extCondition != null
			&& !extCondition.trim().equals(comTree.getExtCondition())) {
			

			int level = 1;
			try
			{
				level = Integer.parseInt(getExpandLevel().trim());
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println("getExpandLevel():" + getExpandLevel());
			}
			//����������۵�������������Ĭ��չ���㼶����100
			if(!parent.isCollapse())
			    level += 100;
			if(parent.getMode() == null)
			{
				comTree.setDynamic(parent.isDynamic());
				
			}
			else
			{
				comTree.setMode(parent.getMode());
			}
			
			comTree.setRecursive(parent.isRecursive());
			comTree.setUprecursive(parent.isUprecursive());
			comTree.setPartuprecursive(parent.isPartuprecursive());
			
			comTree.loadTree(
				getRootid(),
				getI18NRootName(),
				level,
				showRootHref(),
				getMemo(),getRadioValue(),getCheckboxValue(),path);
			comTree.setSingleSelectionMode(singleSelection());
		}
		//�������Ϊkey������session�в����ڣ���ʼ����Ӧ���͵���������ŵ�session�У�����Ϊkey
		if (comTree == null) {
			String type = getTreetype();
			comTree = (COMTree) TreeFactory.getTreeData(type);
			if (comTree == null) {
				log.info(
					"type " + type + " not found in treedata.properties!!");
				return SKIP_BODY;
			}
			comTree.setPageContext(pageContext);
			comTree.setNeedObservable(needObserver());
			comTree.setRefreshNode(isRefreshNode());
			comTree.setSortable(this.isSortable());
			if (extCondition != null)
				comTree.setExtCondition(extCondition);
			int level = Integer.parseInt(getExpandLevel());
//			����������۵�������������Ĭ��չ���㼶����100
			if(!parent.isCollapse())
			    level += 100;
			
			comTree.setEnablecontextmenu(this.isEnablecontextmenu());
			comTree.buildContextMenusWraper();
			if(parent.getMode() == null)
			{
				comTree.setDynamic(parent.isDynamic());
				
			}
			else
			{
				comTree.setMode(parent.getMode());
			}
			comTree.setRecursive(parent.isRecursive());
			comTree.setUprecursive(parent.isUprecursive());
			comTree.setPartuprecursive(parent.isPartuprecursive());
			if(needloadroot(parent))
			{
				comTree.loadTree(
					getRootid(),
					getI18NRootName(),
					level,
					showRootHref(),
					getMemo(),getRadioValue(),getCheckboxValue(),path);
			}
			else
			{
				comTree.addExpandListener();
				comTree.addCollapseListener();
				comTree.addSelectListener();
				comTree.level = level;
			}
			comTree.setSingleSelectionMode(singleSelection());
			
			//�����ѳ�ʼ����com tree��session��
			if(session != null && getScope().equals("session"))
				session.setAttribute(key, comTree);
			//�����ѳ�ʼ����com tree��request_session ��
//			else if(session != null && getScope().equals("request"))
//				session.setAttribute(key, comTree);
			else if( getScope().equals("request"))
				request.setAttribute(key, comTree);
			else if(getScope().equals("pageContext"))
				pageContext.setAttribute(key, comTree);
		}
		
		return SKIP_BODY;
	}
	
	
	

	/**
	 * Access method for the treetype property.
	 *
	 * @return   the current value of the treetype property
	 */
	public String getTreetype() {
		return treetype == null ? "1" : treetype;
	}

	/**
	 * Sets the value of the treetype property.
	 *
	 * @param aTreetype the new value of the treetype property
	 */
	public void setTreetype(String aTreetype) {
		treetype = aTreetype;
	}


	/**
	 * @return String
	 */
	public String getExpandLevel() {
		return expandLevel == null ? "1" : expandLevel;
	}

	/**
	 * @return boolean
	 */
	public boolean needObserver() {
		return needObserver ;
	}

	/**
	 * @return String
	 */
	public String getRootid() {
		return rootid == null ? "-1" : rootid;
	}

	/**
	 * @return String
	 */
	public String getRootName() {
		return rootName;
	}
	
	/**
	 * @return String
	 */
	public String getI18NRootName() {
		if(this.getRootNameCode() == null)
		{
			return this.getRootName();
		}
		else
		{	
			return RequestContextUtils.getI18nMessage(this.getRootNameCode(), this.rootName, request);
		}
	}

	/**
	 * @return boolean
	 */
	public boolean singleSelection() {
		return singleSelection == null
			? true
			: new Boolean(singleSelection).booleanValue();
	}

	/**
	 * @param string
	 */
	public void setExpandLevel(String string) {
		expandLevel = string;
	}

	/**
	 * @param string
	 */
	public void setNeedObserver(boolean string) {
		needObserver = string;
	}

	/**
	 * @param string
	 */
	public void setRootid(String string) {
		rootid = string;
	}

	/**
	 * @param string
	 */
	public void setRootName(String string) {
		rootName = string;
	}

	/**
	 * @param string
	 */
	public void setSingleSelection(String string) {
		singleSelection = string;
	}

	/**
	 * Description:
	 * @return
	 * String
	 */
	public boolean showRootHref() {
		return showRootHref == null
			? true
			: !showRootHref.equalsIgnoreCase("false");
	}

	/**
	 * Description:
	 * @param string
	 * void
	 */
	public void setShowRootHref(String string) {
		showRootHref = string;
	}

	/**
	 * @return String
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * @param string
	 */
	public void setMemo(String string) {
		memo = string;
	}

	/**
	 * Description:
	 * @return
	 * boolean
	 */
	public boolean isRefreshNode() {
		return refreshNode;
	}

	/**
	 * Description:
	 * @param b
	 * void
	 */
	public void setRefreshNode(boolean b) {
		refreshNode = b;
	}

	/**
	 * Description:
	 * @return
	 * String
	 */
	public String getScope() {
		return scope;
	}

    public String getPath() {
        return path;
    }

    /**
	 * Description:
	 * @param string
	 * void
	 */
	public void setScope(String string) {
		scope = string;
	}

    public void setPath(String path) {
        this.path = path;
    }

	public boolean isEnablecontextmenu() {
		return enablecontextmenu;
	}

	public void setEnablecontextmenu(boolean enablecontextmenu) {
		this.enablecontextmenu = enablecontextmenu;
	}

	public boolean isSortable()
	{
		return sortable;
	}

	public void setSortable(boolean sortable)
	{
		this.sortable = sortable;
	}

	public String getRootNameCode() {
		return rootNameCode;
	}

	public void setRootNameCode(String rootNameCode) {
		this.rootNameCode = rootNameCode;
	}

}
