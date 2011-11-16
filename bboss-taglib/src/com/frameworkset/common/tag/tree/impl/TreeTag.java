/*
   The Jenkov JSP Tree Tag provides extra tasks for Apaches Ant build tool

   Copyright (C) 2003 Jenkov Development

   Jenkov JSP Tree Tag is free software; you can redistribute it and/or
   modify it under the terms of the GNU Lesser General Public
   License as published by the Free Software Foundation; either
   version 2.1 of the License, or (at your option) any later version.

   Jenkov JSP Tree Tag is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
   Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public
   License along with this library; if not, write to the Free Software
   Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA


   Contact Details:
   ---------------------------------
   Company:     Jenkov Development - www.jenkov.com
   Project URL: http://www.jenkov.dk/projects/treetag/treetag.jsp
   Email:       info@jenkov.com
*/


package com.frameworkset.common.tag.tree.impl;

import com.frameworkset.common.tag.contextmenu.ContextMenu;
import com.frameworkset.common.tag.contextmenu.ContextMenuTag;
import com.frameworkset.common.tag.pager.config.PageConfig;
import com.frameworkset.common.tag.tree.itf.ITree;
import com.frameworkset.common.tag.tree.itf.ITreeIteratorElement;
import com.frameworkset.common.tag.tree.itf.ITreeNode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

import org.apache.log4j.Logger;

/**
 * 
 * <p>Title: com.frameworkset.common.tag.tree.impl.TreeTag.java</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: chinacreator</p>
 * @Date 2004-1-15
 * @author biaoping.yin
 * @version 1.0
 */
public class TreeTag extends ContextMenuTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(TreeTag.class);
	/**
	 * ����ÿ���������ƣ�������Ӣ�ģ�������ϵͳ�е�Ψһ��
	 */
	protected String tree = null;

	protected String node = null;
	protected String level = null;
	protected String expanded = null;
	protected Iterator treeIterator = null;
	protected String expandParam = null;
	protected String collapseParam = null;
	protected String selectParam = null;
	protected String includeRootNode = null;
	protected String href = null;

	protected StringBuffer params = null;
	protected boolean enabledynamic = true; 
	
	boolean uprecursive = false;
	

    /**
     * ˫�¼����Ʊ���������ڵ�ʱ�Ƿ�չ���ڵ����һ��Ŀ¼��ǰ�������ǽڵ�Ҫ����һ��Ŀ¼
     * false��Ϊ��չ����trueΪչ����ȱʡΪfalse
     */
    private boolean doubleEvent = false;
	/**
	 * ��ʾģʽ
	 * tree�����ṹ��ʾ��������
	 * list���б�ģʽ��ʾ��������
	 */
	protected String showmode = "tree";

	//�Ƿ����jenKov tag
	protected String isjenkov = "false";

	/**
     * ���������۵����ܣ�
     * false�����۵�
     * true���۵�
     * ȱʡֵ��true
     */

    protected boolean collapse = true;

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
	 * ��չ���������ڵ��һЩ���⹦��ʱ,����js�������ܵȵȣ����ø�����
	 */
	protected String extendString;

	/**
		 * ����ͼƬ·��
		 */
	protected String imageFolder = "/images/";

	/**
	 * ��ѡ����������
	 */
	private String checkBox = null;
	private String[] checkBoxDefaultValue = null;
	private String checkBoxExtention;
    private boolean nowrap = true;
    private boolean enablecontextmenu = false;
    /**
     * �������Ķ���̬��Ϊ����
     */
    private boolean dynamic = true;
    
    /**
     * �����������ݼ��ػ��ƣ�
     *      static����̬��ϼ���
     *      static-dynamic:����̬��ϼ���
     *      dynamic������̬����
     *      ȱʡֵ��dynamic 
     */
    private String mode = null;
    /**
     * �Ƿ�jqueryװ��,true-�ǣ�false-����
            Ĭ��ֵ��false;
            Ϊfalseʱ,��ǩ�⽫�Զ�Ϊҳ������������ʽ�ͽű������򲻵���
            trueʱ��������ʽ�ͽű���ͨ���ⲿ����
     */
    private boolean jquery = false; 
    
   
	public void setCheckBoxExtention(String checkBoxExtention) {
		this.checkBoxExtention = checkBoxExtention;
	}
	/**
	 * ��ѡ��ť��������
	 */
	private String radio = null;
	private String radioDefaultValue = null;
	private String radioExtention;
	public void setRadioExtention(String radioExtention) {
		this.radioExtention = radioExtention;
	}

	private String target = null;
	
	private ITree itree = null;
    private boolean recursive = false;
    private String checkboxOnchange;
	private boolean partuprecursive = false;

	public void clear()
	{
		//this.itree = null;
		jquery = false;
            
		/**
		 * ����ÿ���������ƣ�������Ӣ�ģ�������ϵͳ�е�Ψһ��
		 */
		tree = null;

		node = null;
		level = null;
		expanded = null;
		treeIterator = null;
		expandParam = null;
		collapseParam = null;
		selectParam = null;
		includeRootNode = null;
		href = null;

		params = null;
		enabledynamic = true; 
		
		uprecursive = false;
		

	    /**
	     * ˫�¼����Ʊ���������ڵ�ʱ�Ƿ�չ���ڵ����һ��Ŀ¼��ǰ�������ǽڵ�Ҫ����һ��Ŀ¼
	     * false��Ϊ��չ����trueΪչ����ȱʡΪfalse
	     */
	    doubleEvent = false;
		/**
		 * ��ʾģʽ
		 * tree�����ṹ��ʾ��������
		 * list���б�ģʽ��ʾ��������
		 */
		showmode = "tree";

		//�Ƿ����jenKov tag
		isjenkov = "false";

		/**
	     * ���������۵����ܣ�
	     * false�����۵�
	     * true���۵�
	     * ȱʡֵ��true
	     */

	    collapse = true;

		/**
		 * ���������ݻ������÷�Χ��
		 * request
		 * session
		 * pageContext
		 * ȱʡΪsession
		 *
		 */
		scope = "session";

		/**
		 * ��չ���������ڵ��һЩ���⹦��ʱ,����js�������ܵȵȣ����ø�����
		 */
		extendString = null;

		/**
			 * ����ͼƬ·��
			 */
		imageFolder = "/images/";

		/**
		 * ��ѡ����������
		 */
		checkBox = null;
		checkBoxDefaultValue = null;
		checkBoxExtention = null;
	    nowrap = true;
	    enablecontextmenu = false;
	    /**
	     * �������Ķ���̬��Ϊ����
	     */
	    dynamic = true;
	    
	    /**
	     * �����������ݼ��ػ��ƣ�
	     *      static����̬��ϼ���
	     *      static-dynamic:����̬��ϼ���
	     *      dynamic������̬����
	     *      ȱʡֵ��dynamic 
	     */
	    mode = null;
		
		/**
		 * ��ѡ��ť��������
		 */
		radio = null;
		radioDefaultValue = null;
		radioExtention = null;
		target = null;
		
		itree = null;
	    recursive = false;
	    checkboxOnchange = null;
		partuprecursive = false;


	}

	/**
	 * ��������ͼƬ·��
	 */
	public void setImageFolder(String folder) {
		this.imageFolder = folder;
	}

	/**
	 * ��ȡ����ͼƬ·��
	 */
	public String getImageFolder() {
		return this.imageFolder == null ? "/images/" : imageFolder;
	}

	/**
	 * ��ʹ��checkbox��ǩ
	 * @return ��ѡ������
	 */
	public String getCheckBox() {
		return checkBox;
	}

	/**
	 * @return String
	 */
	public String getRadio() {
		return radio;
	}

	/**
	 * @param string
	 */
	public void setCheckBox(String string) {
		checkBox = string;
	}

	/**
	 * @param string
	 */
	public void setRadio(String string) {
		radio = string;
	}
	public String getTree() {
		return this.tree;
	}

	public void setTree(String tree) {
		this.tree = tree;
	}

	public void setIsjenkov(String isjenkov) {
		if (isjenkov == null)
			return;
		isjenkov = isjenkov.trim().toLowerCase();
		if (isjenkov.equals("true") || isjenkov.equals("false")) {
			this.isjenkov = isjenkov;
		}

	}

	public String getIsjenkov() {
		return isjenkov;
	}

	public String getNode() {
		return this.node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getExpandParam() {
		if (this.expandParam == null)
			return "expand";
		return this.expandParam;
	}

	public void setExpandParam(String expandParam) {
		this.expandParam = expandParam;
	}

	public String getCollapseParam() {
		if (this.collapseParam == null)
			return "collapse";
		return this.collapseParam;
	}

	public void setCollapseParam(String collapseParam) {
		this.collapseParam = collapseParam;
	}

	public void setSelectParam(String selectParam) {
		this.selectParam = selectParam;
	}

	public String getSelectParam() {
		if (this.selectParam == null)
			return "selected";
		return this.selectParam;
	}

	public String getIncludeRootNode() {
		if (includeRootNode == null)
			return "true";
		return includeRootNode;
	}

	public void setIncludeRootNode(String includeRootNode) {
		this.includeRootNode = includeRootNode;
	}

	protected void validateAttributes() throws JspException {
		if (getTree() == null)
			throw new JspException("attribute tree must not be null!");
		if (getNode() == null)
			throw new JspException("attribute node must not be null!");
	}

	protected boolean isTreeAvailable() throws JspException {
		HttpSession session = this.getSession();
		if (session != null && session.getAttribute(getTree()) == null) {
			return false;
		}
		return true;
	}
	
	public ContextMenu getContextMenu()
	{
		return getTreeFormScope();
	}

	protected ITree getTreeFormScope()
	{
		HttpSession session = this.getSession();
		if(session != null && getScope().equals("session"))
			return (ITree)session.getAttribute(getTree());
//		else if(session != null && getScope().equals("request"))
//			return (ITree)session.getAttribute(getTree());
		else if( getScope().equals("request"))
			return (ITree)request.getAttribute(getTree());
		else if(getScope().equals("pageContext"))
			return (ITree)pageContext.getAttribute(getTree());
		return null;
	}
	protected void expandCollapseNode() {
		HttpServletRequest request = getHttpServletRequest();
		String expandId = request.getParameter(getExpandParam());

		String collapseId = request.getParameter(getCollapseParam());

		//ITree tree = (ITree) session.getAttribute(getTree());
		ITree tree = getTreeFormScope();

		if (expandId != null) {
			if (!tree.isExpanded(expandId))
				tree.expand(expandId,this.getMode(),this.getScope(),this.getHttpServletRequest());
		} else if (collapseId != null) {
			tree.collapse(collapseId);
		}
	}

	protected void select() {
		HttpServletRequest request = getHttpServletRequest();
		String selectId = request.getParameter(getSelectParam());
		HttpSession session = request.getSession(false) ; 
		if (session != null && selectId != null) {
			ITree tree = (ITree) session.getAttribute(getTree());

			if (!tree.isSelected(selectId)) {
				tree.select(selectId);
			}

		}
	}

	private int adoptJenkovStartTagMode() throws JspException {
		validateAttributes();
		expandCollapseNode();
		select();
		if (!isTreeAvailable()) {
			return SKIP_BODY;
		}
		HttpServletRequest request = getHttpServletRequest();
		HttpSession session = request.getSession(false) ;
		if(session == null)
			return SKIP_BODY;
		ITree tree = (ITree) session.getAttribute(getTree());
		this.treeIterator = tree.iterator(getIncludeRootNode().equals("true"));
		if (this.treeIterator.hasNext()) {
			ITreeIteratorElement element =
				(ITreeIteratorElement) this.treeIterator.next();
			session.setAttribute(getNode(), element);
			return EVAL_BODY_INCLUDE;
		}
		return SKIP_BODY;

	}

	private int adoptJenkovAfterBodyTagMode() {
		if (this.treeIterator.hasNext()) {
			HttpServletRequest request = getHttpServletRequest();
			HttpSession session = request.getSession(false) ;
			ITreeIteratorElement element =
				(ITreeIteratorElement) this.treeIterator.next();
			if(session != null)
				session.setAttribute(getNode(), element);
			return EVAL_BODY_AGAIN;
		}
		return SKIP_BODY;
	}

	private boolean StringToBoolean(String value) {
		return new Boolean(isjenkov).booleanValue();
	}
	/**
	 * TreeTag�Ǿ����޸�jenKov�е�TreeTag�����ģ�
	 * Ϊ�˱���ԭ�е�jenKov�����÷�������������mode��
	 * ����isJenkov��ֵ�жϲ�������mode��
	 * true : ʹ��jenkov�������
	 * false: ʹ���޸ĺ����

	 */

	public int doStartTag() throws JspException {
	    params = new StringBuffer();
		if (!StringToBoolean(getIsjenkov())) {
			return EVAL_BODY_INCLUDE;
		} else
			return adoptJenkovStartTagMode();
	}
	
	
	
	public void initContextMenu()
	{
		
	}
	public int doEndTag() throws JspException {
		
		try{
			
			
			if (!StringToBoolean(getIsjenkov())) {
				Object obj = this.getTreeFormScope();//session.getAttribute(getTree());
	
	
				if (obj == null) {
					log.info("�Ҳ���ָ�����͵���:" + getTree());
					return EVAL_PAGE;
				}
				
				ITree tree = (ITree) obj;
				this.itree = tree;
				
				//validateAttributes();
				expandCollapseNode();
				select();
				JspWriter out = this.getJspWriter();
				try {
					if(itree.isDynamic())
					{
						this.treeIterator =
							tree.iterator(getIncludeRootNode().equals("true"));
						out.print(this.generateContent());
					}
					else if(itree.isStaticDynamic())
					{
						String parent_indent = getParent_indent(request);
						if(parent_indent != null)
	                    {
	    					this.treeIterator =
	    						tree.iterator(parent_indent);
	//                        request.setAttribute(this.getTree() + "_generateContent",generateContent());
	                        //������ϵ�������ģʽ
	    					
                            
                            
	                        updateFather(tree.getCurExpanded(),this.generateContent());
	                    }
	                    else
	                    {
	                        this.treeIterator =
	                            tree.iterator(getIncludeRootNode().equals("true"));
	                        
	                        out.print(this.generateContent());
	                    }
						
					}
					else
					{
						this.treeIterator =
	                        tree.iterator(getIncludeRootNode().equals("true"));
						
	                    out.print(generateContent());
					}
				} catch (IOException e) {
					e.printStackTrace();
					throw new JspException(e.getMessage());
				}
	//			this.istreemenu = true;
				
				super.doEndTag();
	//			this.istreemenu = false;
				return EVAL_PAGE;
				
			}
		}
		finally
		{
			clear();
		}
		return EVAL_PAGE;
	}
    //Ǩ��prototype��jqueryʱ����
//    private void updateFather(ITreeNode node,String sons) throws IOException
//    {
//        StringBuffer script = new StringBuffer();
////        System.out.println()
//        sons = StringUtil.replaceAll(sons,"\"","\\\"");
//        sons = StringUtil.replaceAll(sons,"'","\\'");
//        
//        	
//        script.append("<script language=\"javascript\">")
//            .append("parent.setSon(\"")
//            .append(node.getId())
//            .append("\",\"")
//            .append(sons)
//             .append("\",\"")
//            .append(node.getSonids() != null? node.getSonids().toString():"")
//            .append("\",\"")
//            .append((node.getParent() != null ?node
//			         .getParent().getFatherids().toString():""))
//            .append("\");")
//            .append("</script>");
//        super.getJspWriter().print(script.toString());
//        //System.out.println(script.toString());
//    }
    
    
    private void updateFather(ITreeNode node,String sons) throws IOException
    {
        StringBuffer script = new StringBuffer();
        
//        System.out.println()
//        sons = StringUtil.replaceAll(sons,"\"","\\\"");
//        sons = StringUtil.replaceAll(sons,"'","\\'");
        
//        	
//        script.append("<script language=\"javascript\">")
//            .append("parent.setSon(\"")
//            .append(node.getId())
//            .append("\",\"")
//            .append(sons)
//             .append("\",\"")
//            .append(node.getSonids() != null? node.getSonids().toString():"")
//            .append("\",\"")
//            .append((node.getParent() != null ?node
//			         .getParent().getFatherids().toString():""))
//            .append("\");")
//            .append("</script>");
//        super.getJspWriter().print(script.toString());       
        //System.out.println("sonids:" + node.getSonids() != null? node.getSonids().toString():"");
        script.append(sons);        
        out.print(script);
        //System.out.println(script.toString());
    }
    
    

    /**
     * ��ȡ����indent
     * @return
     */
    public static  String getParent_indent(HttpServletRequest request)
    {
//    	HttpServletRequest request = getHttpServletRequest();
//		HttpSession session = request.getSession(false) ;
        String parent_indent = request.getParameter("node_parent_indent");
        return parent_indent;
    }

	public int doAfterBody() throws JspException {
		if (StringToBoolean(getIsjenkov())) {
			return adoptJenkovAfterBodyTagMode();
		}
		return SKIP_BODY;

	}

	/* (non-Javadoc)
	 * @see com.frameworkset.common.tag.BaseTag#generateContent()
	 */
	public String generateContent() {
		return generateContent(this.treeIterator);
	}

	private String generateContent(Iterator element) {
		StringBuffer ret = new StringBuffer();
		String nodeHref = null;
		String selfHref = null;
		if(href != null)
		{
			int idx = href.indexOf('|');
			if( idx == -1)
				nodeHref = href;
			else
			{
				nodeHref = href.substring(0,idx);
				selfHref = href.substring(idx + 1);
			}
		}
		HttpServletRequest request = getHttpServletRequest();
//		HttpSession session = request.getSession(false) ;

		if(selfHref == null || selfHref.trim().equals(""))
		{
		    //û�м�¼ҳ��������������ĳЩ����½��������ʾ����ȷ
			selfHref = request.getRequestURI();
		}
       
		
		if(itree.isDynamic())
        {
			
			NodeHelper.getInitScript(ret,request.getParameter("selectedNode"),request.getContextPath()); 
			
			if(!this.isJquery())
			{
				 ret.append(PageConfig.getJqueryConfig(request)); //jquery
				 ret.append(PageConfig.getTreeConfig(request)); //jquery
	             ret.append(PageConfig.getPopScript( request, enablecontextmenu)); //jquery
			}
			NodeHelper.getSelectedScript(ret,itree,this.getTree());
			
			ret.append("<table>");
        }
		else if(itree.isStaticDynamic())
		{
              
            String parent_indent = getParent_indent(request);
            
            if(parent_indent == null)
            {
            	//���û�����Ҽ��˵������Ǿ�̬ҳ�棬����Ҫ�������prototype������ű�
//            	if(!this.enablecontextmenu) //jquery
//            	    if(!this.isJquery())
//                        NodeHelper.getPrototypeScript(ret,request,this.enablecontextmenu);
            	if(!this.isJquery())
    			{
    				 ret.append(PageConfig.getJqueryConfig(request)); //jquery
	                ret.append(PageConfig.getTreeConfig(request)); //jquery
	                ret.append(PageConfig.getPopScript( request, enablecontextmenu)); //jquery
    			}
                
                NodeHelper.getInitScript(ret,request.getParameter("selectedNode"),request.getContextPath()); 
                NodeHelper.getSelectedScript(ret,itree,this.getTree());
               
                                
            }

		}
		else if(itree.isStatic())
		{
//			���û�����Ҽ��˵������Ǿ�̬ҳ�棬����Ҫ�������prototype������ű�
//        	if(!this.enablecontextmenu)
//        	    if(!this.isJquery())
//                    NodeHelper.getPrototypeScript(ret,request,this.enablecontextmenu);
			if(!this.isJquery())
			{
				 ret.append(PageConfig.getJqueryConfig(request)); //jquery
			    ret.append(PageConfig.getTreeConfig(request)); //jquery
	            ret.append(PageConfig.getPopScript( request, enablecontextmenu)); //jquery
			}
			NodeHelper.getInitScript(ret,request.getParameter("selectedNode"),request.getContextPath()); 
			NodeHelper.getSelectedScript(ret,itree,this.getTree());       
		}
		String sonids = null;
		if(itree.getCurExpanded() != null && itree.getCurExpanded().getSonids() != null)
		    sonids = itree.getCurExpanded().getSonids().toString();
		if(sonids == null) sonids = "";

        boolean needroot = this.getIncludeRootNode().equals("true") ;
		while (this.treeIterator.hasNext()) {
			ITreeIteratorElement node =
				(ITreeIteratorElement) this.treeIterator.next();
			NodeHelper helper = new NodeHelper(node, request,sonids);
            /**
             * ���ø�ѡ��ѡ��ʱ�Ƿ�ݹ�ѡ��
             */
//            helper.setRecursive(this.isRecursive());
            /**
             * ���ø�ѡ���onchange�¼�
             */
            
            helper.setTree(this.itree);
            helper.setIncludeRootNode(needroot);
            helper.setCheckboxOnchange(this.getCheckboxOnchange());
//			helper.setDynamic(this.isDynamic());
			helper.setCollapse(this.isCollapse());
			helper.setEnablecontextmenu(this.isEnablecontextmenu());
			helper.setImageFolder(this.getImageFolder());
			helper.setCheckBox(this.getCheckBox());
			helper.setRadio(this.getRadio());
			helper.setTarget(this.getTarget());
			helper.setExtendString(getExtendString());
			helper.setCheckBoxDefaultValue(getCheckBoxDefaultValue());
			helper.setRadioDefaultValue(getRadioDefaultValue());
			helper.setCheckBoxExtention(getCheckBoxExtention());
			helper.setRadioExtention(getRadioExtention());
			helper.setNowrap(this.isNowrap());
            helper.setDoubleEvent(isDoubleEvent());
			//��¼ҳ�����
			helper.setParams(params.toString());

			helper.setScope(this.getScope());
			helper.setAction(nodeHref);

			helper.setLocalAction(selfHref);			
			helper.getUpper(ret);
            helper.getIndent(ret);
            helper.getImageContent(ret);
            helper.getNodeContent(ret);
            
            helper.getBoot(ret);
            
            helper = null;
		}
		//ret.append(NodeHelper.getCatchScript(request));
        //NodeHelper.getCatchScript(ret,request);
		
		if(this.isDynamic())
			ret.append("</table>");
//		else
//			ret.append("</div>");
//	      try
//	        {
//	            System.setOut(new PrintStream(new FileOutputStream(new File("d:/first.log"))));
//	        }
//	        catch (FileNotFoundException e)
//	        {
//	            // TODO Auto-generated catch block
//	            e.printStackTrace();
//	        }
//	        System.out.println(ret);
		return ret.toString();
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
	public String getTarget() {
		return target;
	}

	/**
	 * @param string
	 */
	public void setTarget(String string) {
		target = string;
	}

	/**
	 * @return String
	 */
	public String getHref() {
		return href;
	}

	/**
	 * @param string
	 */
	public void setHref(String string) {
		href = string;
	}

	/**
	 * @return String
	 */
	public String getExtendString() {
		return extendString;
	}

	/**
	 * @param string
	 */
	public void setExtendString(String string) {
		extendString = string;
	}

	public void setCheckBoxDefaultValue(String[] string) {
		this.checkBoxDefaultValue = string;
	}

	/**
	 * �μ�checkbox��ǩ
	 * @return String
	 */
	public String[] getCheckBoxDefaultValue() {
		return checkBoxDefaultValue;
	}

	/**
	 * @return String
	 */
	public String getRadioDefaultValue() {
		return radioDefaultValue;
	}

	/**
	 * @param string
	 */
	public void setRadioDefaultValue(String string) {
		radioDefaultValue = string;
	}

	/**
	 * �μ�checkbox��ǩ
	 * @return String
	 */
	public String getCheckBoxExtention() {
		return checkBoxExtention;
	}

	/**
	 * @return String
	 */
	public String getRadioExtention() {
		return radioExtention;
	}

	public static void main(String[] args)
	{
		java.util.StringTokenizer token = new java.util.StringTokenizer("a|b","|");
		System.out.println(token.countTokens());
		while(token.hasMoreTokens())
		{
			System.out.println(token.nextToken());
		}
		System.out.println();
		token = new java.util.StringTokenizer("a|","|");
		System.out.println(token.countTokens());
		while(token.hasMoreTokens())
		{
			System.out.println(token.nextToken());
		}
		System.out.println();
		token = new java.util.StringTokenizer("|b","|");
		System.out.println(token.countTokens());
		while(token.hasMoreTokens())
		{
			System.out.println(token.nextToken());
		}
	}

	/**
	 * Description:
	 * @return
	 * String
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * Description:
	 * @param string
	 * void
	 */
	public void setScope(String string) {
		scope = string;
	}

    /**
     * @return Returns the showmode.
     */
    public String getShowmode() {
        return showmode;
    }
    /**
     * @param showmode The showmode to set.
     */
    public void setShowmode(String showmode) {
        this.showmode = showmode;
    }
    /**
     * @return Returns the collapse.
     */
    public boolean isCollapse() {
        return collapse;
    }

    public boolean isNowrap() {
        return nowrap;
    }

    public boolean isDoubleEvent() {
        return doubleEvent;
    }

    /**
     * @param collapse The collapse to set.
     */
    public void setCollapse(boolean collapse) {
        this.collapse = collapse;
    }

    public void addParam(String name,String value)
    {
        if(params == null )
        {
            params = new StringBuffer(0);

        }
        if(params.length() == 0)
        {
            params.append(name).append("=").append(value);
        }
        else
            params.append("&").append(name)
            	  .append("=").append(value);
    }

    public void setNowrap(boolean nowrap) {
        this.nowrap = nowrap;
    }

    public void setDoubleEvent(boolean doubleEvent) {
        this.doubleEvent = doubleEvent;
    }

	public boolean isEnablecontextmenu() {
		return enablecontextmenu;
	}

	public void setEnablecontextmenu(boolean enablecontextmenu) {
		this.enablecontextmenu = enablecontextmenu;
	}

	public boolean isEnabledynamic()
	{
		return enabledynamic;
	}

	public void setEnabledynamic(boolean enabledynamic)
	{
		this.enabledynamic = enabledynamic;
	}

	public boolean isDynamic()
	{
		return Tree.mode_dynamic.equals(mode);
	}

	public void setDynamic(boolean dynamic)
	{
		if(dynamic)
			this.mode = Tree.mode_dynamic;
		else
			this.mode = Tree.mode_static;
	}

    public void setRecursive(boolean recursive)
    {
        this.recursive = recursive;
        
    }

    public void setCheckboxOnchange(String onchange)
    {
        this.checkboxOnchange = onchange;
        
    }

    public String getCheckboxOnchange()
    {
        return checkboxOnchange;
    }

    public boolean isRecursive()
    {
        return recursive;
    }

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public boolean isUprecursive() {
		return uprecursive;
	}

	public void setUprecursive(boolean uprecursive) {
		this.uprecursive = uprecursive;
	}
	
	public void setPartuprecursive(boolean partuprecursive)
	{
		this.partuprecursive  = partuprecursive;
	}
	
	public boolean isPartuprecursive()
	{
		return partuprecursive;
	}

    public boolean isJquery()
    {
        return jquery;
    }

    public void setJquery(boolean jquery)
    {
        this.jquery = jquery;
    }
}
