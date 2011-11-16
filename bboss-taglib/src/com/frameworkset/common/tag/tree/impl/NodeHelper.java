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

// import javax.servlet.jsp.JspException;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import com.frameworkset.common.tag.tree.itf.ITree;
import com.frameworkset.common.tag.tree.itf.ITreeIteratorElement;
import com.frameworkset.common.tag.tree.itf.ITreeNode;
import com.frameworkset.util.StringUtil;
import com.frameworkset.util.VelocityUtil;

/**
 * ���ศ��TreeTag���ɸ�������html���룬 ����ͨ��ecs/velocity/StringBuffer���ַ�ʽ������
 * 
 * @���� biaoping.yin
 * @���� 2004-3-19 16:28:13
 * @�汾 v1.0
 */

public class NodeHelper  implements Serializable
{
	private static final Logger log = Logger.getLogger(NodeHelper.class);
 
	/**
	 * ������ʾ�Ľڵ���Ϣ
	 */
	private ITreeIteratorElement element = null;
	
//	private 

	/**
	 * ����ʾ�Ľڵ�action
	 */
	private String action = null;

	/**
	 * �ýڵ��Ƿ�Ϊչ���ڵ�
	 */
	boolean expanded = false;

	/**
	 * �ڵ��Ƿ����ֽڵ�
	 */
	boolean hasChildren = true;

	/**
	 * �Ƿ�������ӽڵ�
	 */
	boolean isLastChild = false;

	/**
	 * �Ƿ��ǵ�һ�����ӽڵ�
	 */
	boolean isFirstChild = false;

	/**
	 * �ڵ��Ƿ�ѡ��
	 */
	boolean selected = false;

	/**
	 * ���������۵����ܣ� false�����۵� true���۵� ȱʡֵ��true
	 */
	private boolean isCollapse = true;

	/**
	 * �ڵ�href��չ��
	 */
	String extendString = "";

	/**
	 * �ڵ�����
	 */
	String type = "";

	/**
	 * ҳ���������
	 */
	HttpServletRequest request;

	/**
	 * �������ʱ��Ҫ��¼��ǰ�ڵ��ident���Ա����ɽڵ�Ķ��ӽڵ��indent ���ӽڵ��indent �� ����indent �� ���ӵ�indent
	 */
	StringBuffer indent;

	boolean dynamic = true;
	
	

	/**
	 * ҳ������Χ session��request��pageContext��ȱʡֵΪsession
	 */
	String scope = "session";
	
	

	/**
	 * ָ���ڵ������ͼƬ��Ŀ¼
	 */
	String imageFolder = "/images/";

	/**
	 * checkbox����
	 */
	String checkBox = null;

	/**
	 * radio����
	 */
	String radio = null;

	/**
	 * checkboxĬ��ѡ��ֵ���ԡ�,���ָ����ַ���
	 */
	private String[] checkBoxDefaultValue = null;

	/**
	 * checkbox ��ֵ
	 */

	private String checkboxValue;

	/**
	 * radio��Ĭ��ѡ��ֵ
	 */
	private String radioDefaultValue = null;

	/**
	 * checkbox��չ��
	 */
	private String checkBoxExtention;

	/**
	 * radio��ť��չ��
	 */
	private String radioExtention;

	/**
	 * radio��ťֵ
	 */
	private String radioValue;

	/**
	 * �ڵ�hrefĿ��
	 */
	String target = "";

	/**
	 * �ڵ��ʶ
	 */
	String nodeId;

	/**
	 * ҳ�����
	 */
	String params;

	/**
	 * ˫�¼����Ʊ���������ڵ�ʱ�Ƿ�չ���ڵ����һ��Ŀ¼��ǰ�������ǽڵ�Ҫ����һ��Ŀ¼ false��Ϊ��չ����trueΪչ����ȱʡΪfalse
	 */
	private boolean doubleEvent = false;

	private String nodeClickLink = null;

	private String nodeEventLink = null;
	private String sonids;

	public NodeHelper(ITreeIteratorElement element, HttpServletRequest request,String sonids)
	{
		this.element = element;
		this.expanded = element.isExpanded();
		this.hasChildren = element.getNode().hasChildren();
		this.radioValue = element.getNode().getRadioValue();
		this.checkboxValue = element.getNode().getCheckboxValue();
		this.isLastChild = element.isLastChild();
		this.isFirstChild = element.isFirstChild();
		this.selected = element.isSelected();
		this.type = element.getNode().getType();
		this.request = request;
		this.nodeId = element.getNode().getId();
		this.nodeClickLink = null;
		this.nodeEventLink = null;
		this.sonids = sonids; 
	}

	public void setCollapse(boolean isCollapse)
	{
		this.isCollapse = isCollapse;
	}

	public void setImageFolder(String imageFolder)
	{
		this.imageFolder = imageFolder;
	}

	public void setCheckBox(String checkBox)
	{
		this.checkBox = checkBox;
	}

	public void setRadio(String radio)
	{
		this.radio = radio;
	}

	/**
	 * ��ȡ�¼�����
	 * 
	 * @return
	 */
	private String getEvent()
	{
		if (!expanded && hasChildren && !isLastChild)
			return "expand";
		if (expanded && hasChildren && !isLastChild)
			return "collapse";
		if (!expanded && hasChildren && isLastChild)
			return "expand";
		if (expanded && hasChildren && isLastChild)
			return "collapse";
		return null;
	}

	private String getEventLabel()
	{
        if(itree.isDynamic())
        {
    		if (!expanded && hasChildren && !isLastChild)
    			return "չ��";
               
    		if (expanded && hasChildren && !isLastChild)
    			return "�۵�";
               
    		if (!expanded && hasChildren && isLastChild)
    			return "չ��";
                
    		if (expanded && hasChildren && isLastChild)
    			return "�۵�";
        }
        else
        {
            if (!expanded && hasChildren && !isLastChild)
    
                return "չ��/�۵�";
            if (expanded && hasChildren && !isLastChild)
    
                return "չ��/�۵�";
            if (!expanded && hasChildren && isLastChild)
   
                return "չ��/�۵�";
            if (expanded && hasChildren && isLastChild)
    
                return "չ��/�۵�";
        }
		return null;
	}

	public void getImageContent(StringBuffer buffer)
	{
		// StringBuffer buffer = new StringBuffer();

		// System.out.println(element.getName() + ":expanded=" + expanded +
		// ",hasChildren=" + hasChildren + ",isLastChild=" + isLastChild);
		if (!expanded && hasChildren && !isLastChild)
			// buffer.append(
			// getImageContent(
			// "expand",
			// getCollapsedMidNodeImage(),
			// getClosedFolderImage()));

			getImageContent(buffer, "expand", getCollapsedMidNodeImage(),
					getClosedFolderImage());
		/**
		 * 1,2 completed selected
		 */
		if (!expanded && hasChildren && isLastChild)

			getImageContent(buffer, "expand", getCollapsedLastNodeImage(),
					getClosedFolderImage());

		if (expanded && hasChildren && !isLastChild)

			getImageContent(buffer, "collapse", getExpandedMidNodeImage(),
					getOpenFolderImage());

		if (expanded && hasChildren && isLastChild)

			getImageContent(buffer, "collapse", getExpandedLastNodeImage(),
					getOpenFolderImage());

		// <td><a href="classtree.jsp?expand=<tree:nodeId
		// node="example.node"/>"><img src="images/collapsedLastNode.gif"
		// border="0"></a><img src="images/closedFolder.gif"></td>

		// <td><a href="classtree.jsp?collapse=<tree:nodeId
		// node="example.node"/>"><img src="images/expandedLastNode.gif"
		// border="0"></a><img src="images/openFolder.gif"></td>
		if (!expanded && !hasChildren && !isLastChild)

			getImageContent(buffer, "null", getNoChildrenMidNodeImage(),
					getNonFolderImage());
		// <td><img src="images/noChildrenMidNode.gif"><img
		// src="images/nonFolder.gif"></td>
		if ((!expanded && !hasChildren && isLastChild)
				|| (expanded && !hasChildren && isLastChild))

			getImageContent(buffer, "null", getNoChildrenLastNodeImage(),
					getNonFolderImage());
		// <td><img src="images/noChildrenLastNode.gif"><img
		// src="images/nonFolder.gif"></td>

		// return buffer.toString();
	}

	private String getPreImageForstatic(boolean expanded)
	{
		if (!expanded && !isLastChild)
			return getCollapsedMidNodeImage();
		/**
		 * 1,2 completed selected
		 */
		if (!expanded && isLastChild)

			return getCollapsedLastNodeImage();

		if (expanded && !isLastChild)

			return getExpandedMidNodeImage();

		if (expanded && isLastChild)
			return getExpandedLastNodeImage();
		return "";

	}
	
	private String getFolderImageForstatic(boolean expanded)
	{
		if (!expanded)
			return getClosedFolderImage();
		else
			return getOpenFolderImage();

	}

	public void getNodeContent(StringBuffer buffer)
	{
		// return getNodeContent(buffer,"selected", selected);
		getNodeContent(buffer, "selected", selected);
	}

	private String getCustomParams(Map attributes)
	{
		StringBuffer buffer = new StringBuffer();
		boolean flag = false;
		if (attributes != null)
		{

			Iterator keys = attributes.keySet().iterator();
			while (keys.hasNext())
			{
				String key = (String) keys.next();
				 /**
                 * �ж�key�Ƿ���ϵͳ��ȱʡ�����Ĳ���
                 */
				if(this.isInnerVariable(key))
				{
					continue;
				}
				if (!flag)
				{
					String value = (String) attributes.get(key);
					buffer.append(key).append("=").append(value);
					flag = true;
				}
				else
				{
					String value = (String) attributes.get(key);
					buffer.append("&").append(key).append("=").append(value);
				}

			}
		}
		return buffer.toString();
	}
	
	
	private boolean isInnerVariable(String key)
	{
		boolean isInnerVariable = false;
		/**
         * �ж�key�Ƿ���ϵͳ��ȱʡ�����Ĳ���
         */
		if (key.equals("nodeLink"))//�ڵ����Ӳ�������
			isInnerVariable = true;
		else if (key.equals("node_recursive"))//��ѡ���Ƿ�ݹ�ѡ���������Ϊboolean����
			isInnerVariable = true;
        
		else if (key.equals("node_linktarget"))//�ڵ����ӵ�ַkey
        {
			isInnerVariable = true;
        }
        
		else if (key.equals("node_checkboxname"))//�ڵ㸴ѡ������key
        {
            
			isInnerVariable = true;
        }
        
		else if (key.equals("node_radioname"))//�ڵ㵥ѡ������key
        {
            
			isInnerVariable = true;
        }
		else if(key.equals("node_uprecursive"))//�ڵ㸴ѡ���Ƿ��еݹ�ѡ���ϼ��Ĺ���
        {
			isInnerVariable = true;
        }
		else if(key.equals("node_partuprecursive"))//�ڵ㸴ѡ���Ƿ��еݹ�ѡ���ϼ��Ĺ���
        {
			isInnerVariable = true;
        }
		else if(key.equals("node_checkboxchecked")) //��ʶ��ѡ���Ƿ�ѡ��
			isInnerVariable = true;
		else if(key.equals("node_checkboxdisabled")) //��ʶ��ѡ���Ƿ񱻽���
			isInnerVariable = true;
		else if(key.equals("node_radiochecked")) //��ʶ��ѡ��ť�Ƿ�ѡ��
			isInnerVariable = true;
		else if(key.equals("node_radiodisabled")) //��ʶ��ѡ��ť�Ƿ񱻽���
			isInnerVariable = true;	
		return isInnerVariable;
	}
	

	/**
	 * ��ȡ�ڵ�������
	 * 
	 * @return
	 */
	private String getNodeClickLink(String event)
	{
		if (nodeClickLink != null)
			return this.nodeClickLink;
		nodeClickLink = "";
		String nodeLink = null;
		Map attributes = element.getNode().getParams();
		
		// �����Ҫ����href�����ȡ���ӵ�ַ������
		if (this.element.getNode().getShowHref())
		{
			if (attributes != null)
				nodeLink = (String) attributes.get("nodeLink");
			if (nodeLink == null)
				nodeLink = this.getAction();
		}
		if(!StringUtil.isJavascript(nodeLink))
		{
			String selectedNode = request.getParameter("selectedNode");
			StringBuffer buffer = new StringBuffer();
			
			boolean flag = nodeLink != null && nodeLink.trim().length() > 0;
			String nodeEvent = getEvent();
			String eventLink = getEventLink(nodeEvent).toString();
			if (flag)
			{
				buffer.append("doClickTreeNode('").append(
						StringUtil.getRealPath(request, nodeLink)).append(
						nodeLink.indexOf("?") == -1 ? "?" : "&").append(event)
						.append("=").append(element.getId());
				if (element.getNode().getType() != null)
					buffer.append("&classType=")
							.append(element.getNode().getType());
	
				if (element.getNode().getMemo() != null)
					buffer.append("&nodeMemo=").append(element.getNode().getMemo());
				if (element.getNode().getPath() != null)
	
					buffer.append("&nodePath=").append(
							StringUtil.encode(element.getNode().getPath(), null));
				String target = getTarget();
				if (attributes != null)
				{
					String temp = (String) attributes.get("node_linktarget");
					if(temp != null && !temp.equals(""))
						target = temp;
					Iterator keys = attributes.keySet().iterator();
					while (keys.hasNext())
					{
						String key = (String) keys.next();
	                    /**
	                     * �ж�key�Ƿ���ϵͳ��ȱʡ�����Ĳ���
	                     */
						if(this.isInnerVariable(key))
						{
							continue;
						}
									          
						String value = (String) attributes.get(key);
						buffer.append("&").append(key).append("=").append(value);
	
					}
				}
	
				buffer.append("','" + element.getNode().getId() + "',").append("'")
						.append(target).append("','").append(selectedNode);
	
				if (!eventLink.equals("") && doubleEvent)
				{
					if(itree.isDynamic())
						buffer.append("','").append(eventLink).append("')\" ");
					else
						buffer.append("','").append(eventLink).append("','").append(this.element.getId()).append("')\" ");
						
					
				}
				else
					buffer.append("')");
	
			}
			else if (!eventLink.equals(""))
			{
				if(itree.isDynamic()) //����̬����ģʽ
				{
					buffer.append("doClickImageIcon('").append(
								eventLink).append("')");
				}
				else //������ϵ�ģʽ
				{
//					buffer.append("doClickImageIcon('").append(
//							eventLink).append("','").append(this.element.getId()).append("')");
					//function doClickImageIcon(linkUrl,eventNode,name,nodetype,nodepath)
					buffer.append("doClickImageIcon('").append(
							eventLink).append("','")
							.append(this.element.getId())
							.append("','")
							.append(StringUtil.encode(this.element.getName()))
							.append("','")
							.append(this.element.getNode().getType())	
//							.append("','")
//							.append(this.element.getNode().getShowHref())
//							.append("','")
//							.append(this.element.getNode().hasChildren())
							.append("','")
							.append(this.element.getNode().getPath())
							.append("','")
							.append(this.element.isFirstChild())
							.append("','")
							.append(this.element.isLastChild())
							.append("')");
				}
	
			}
			return nodeClickLink = buffer.toString();
		}
		else
		{
			return nodeClickLink = nodeLink;
		}
		
	}

	/**
	 * ���ɽڵ��html�ı����ڵ��Ƿ�������Լ���ʲô�������������¼��������
	 * ����Ҫ���õ�����ӣ����ǽڵ�����ֽڵ㣬��ô��Ҫ����չ���ýڵ���һ�������� ��Ҫ���õ�����ӣ����ڵ㲻�����ӽڵ������Ҫ����չ���ڵ����һ������
	 * ��Ҫ���õ�����ӣ��ڵ�����ֽڵ㣬Ҳ��Ҫ����չ���ýڵ���һ��������
	 * 
	 * @param buffer
	 * @param event
	 * @param selected
	 */
	private void getNodeContent(StringBuffer buffer, String event,
			boolean selected)
	{

		// ����ӵ��ͼ�괦���ݹ����Ĳ���,Ȼ���ж�Ĭ��ѡ�еĽ��

		String selectedNode = request.getParameter("selectedNode");
		// StringBuffer buffer = new StringBuffer();
		if (nowrap)
			buffer.append("<td nowrap title=\"").append(element.getNode().getName()).append("\">");
		else
			buffer.append("<td title=\"").append(element.getNode().getName()).append("\">");

		buffer.append(getCheckBox());
		buffer.append(getRadio());
		String nodeClickEvent = this.getNodeClickLink(event);
		
		if (!nodeClickEvent.equals(""))
		{
			if (this.enablecontextmenu)
			{
				String id = "";
				if (this.element.getNode().isEnablecontextmenu()) // ����ڵ㱾���ƶ����Ҽ��˵��������ñ�����Ҽ��˵���
					id = "_node_" + this.element.getNode().getId();
				else
					// �����������ֱ��ʹ�ýڵ����Ͷ�Ӧ���Ҽ��˵�
					id = "_type_" + this.element.getNode().getType();
				String params = this.getCustomParams(this.element.getNode()
						.getParams());
				String expandLabel = this.getEventLabel();
				buffer
						.append("<a ")
						.append(
								expandLabel == null ? "" : "expandLabel=\""
										+ expandLabel + "\"")
						.append(" openNode=\"")
						.append(this.element.getNode().getId())
						.append("\"")
						.append(
								expandLabel == null ? " "
										: " expandNode=\"icon_"
												+ this.element.getNode()
														.getId() + "\"")
						.append(" id=\"")
						.append(id)
						.append("\" params=\"")
						.append(params)
						.append(
//								"\" oncotextmenu='InitializedDocEvent();' name=\"")
						"\"  name=\"")
						.append(this.element.getNode().getId()).append(
								"\" onclick=\"").append(nodeClickEvent).append(
								"\" style=\"cursor:hand;\"");
			}
			else
			{

				buffer.append("<a name=\"").append(
						this.element.getNode().getId()).append("\" onclick=\"")
						.append(nodeClickEvent).append(
								"\" style=\"cursor:hand;\"");
			}

			if (selectedNode != null
					&& selectedNode.equals(this.element.getNode().getId()))
				buffer.append(" class=\"selectedTextAnchor\"");
			buffer.append(">");
		}
		else
		{
			if (this.enablecontextmenu)
			{
				String id = "";
				if (this.element.getNode().isEnablecontextmenu()) // ����ڵ㱾���ƶ����Ҽ��˵��������ñ�����Ҽ��˵���
					id = "_node_" + this.element.getNode().getId();
				else
					// �����������ֱ��ʹ�ýڵ����Ͷ�Ӧ���Ҽ��˵�
					id = "_type_" + this.element.getNode().getType();
				String params = this.getCustomParams(this.element.getNode()
						.getParams());
				String expandLabel = this.getEventLabel();
				buffer
						.append("<a ")
						.append(
								expandLabel == null ? "" : "expandLabel=\""
										+ expandLabel + "\"")
						.append(" openNode=\"")
						.append(this.element.getNode().getId())
						.append("\"")
						.append(
								expandLabel == null ? " "
										: " expandNode=\"icon_"
												+ this.element.getNode()
														.getId() + "\"")
						.append(" id=\"")
						.append(id)
						.append("\" params=\"")
						.append(params)
						.append(
//								"\" oncotextmenu='InitializedDocEvent();' name=\"")
						"\" name=\"")
						.append(this.element.getNode().getId()).append(
								"\">");
			}
			else
			{
				//do nothing
			}
		}

		// if (selected)
		// buffer.append("<b>");

		String t_temp = element.getName();
		t_temp = StringUtil.replaceAll(t_temp,"'","\\'");
		buffer.append(t_temp);

		// if (selected)
		// buffer.append("</b>");

		// buffer.append("</span>");

		if (!nodeClickEvent.equals(""))
		{
			buffer.append("</a>");
			getCatchScript(buffer, request, element.getId());
		}
		else
		{
			if (this.enablecontextmenu)
				buffer.append("</a>");
			else
			{
				//do nothing.
			}
		}
			
		buffer.append("</td>");
	}

	private String localAction;

	/**
	 * �������ڵ��Ƿ���
	 */
	private boolean nowrap = true;

	private boolean enablecontextmenu;

	/**
	 * ���µݹ����нڵ�
	 */
    private boolean recursive = false;
    
    /**
     * ���ϵݹ鵽���и��ڵ�
     */
    private boolean uprecursive = false;
    
    /**
     * ���ϵݹ鵽������Ҫ�ݹ鸸�ڵ�
     */
    private boolean partrecursive = false;
    
    

    private String checkboxOnchange;

	private ITree itree;

	/**
	 * ��ȡ�������ӣ����ҽ�ҳ�����׷�ӵ����Ӻ���
	 * 
	 * @return String
	 */
	public String getLocalAction()
	{
		String temp = localAction;
		if (getParams() == null || getParams().trim().length() == 0)
			return temp;
		int index = temp.indexOf('?');
		if (index == -1)
		{
			temp += "?" + this.getParams();

		}
		else
			temp += "&" + this.getParams();
		return temp;
	}

	public String getLocalAction(int index, String attach)
	{
		return new StringBuffer(localAction).insert(index, attach).toString();
	}

	/**
	 * ��ȡ�¼�����
	 * 
	 * @param event
	 * @return
	 */
	private String getEventLink(String event)
	{
		if (this.nodeEventLink != null)
			return nodeEventLink;
		if (event == null)
			return nodeEventLink = "";
		String location = getLocalAction();
		int index = location.indexOf('?');

		// ��������������۵�����ô����ͼ������ӣ���������
		StringBuffer temp_b = new StringBuffer();

		// temp_b
		// // .append("<a name=\"#" + anchor
		// // +"\"></a><a onclick=\"doClickImageIcon('");
		// .append("doClickImageIcon('");
		// .append(this.getLocalAction());

		if (index == -1)
			temp_b.append(location)

			.append("?");
		else
			temp_b.append(location).append("&");
		// buffer.append("anchor=")
		// .append(anchor)
		// .append("&");
		temp_b.append(event).append("=").append(element.getId());
		temp_b.append("&request_scope=").append(getScope());
		// .append("')");
		return nodeEventLink = temp_b.toString();
	}
     private String getParent_indent()
    {
        String parent_indent = request.getParameter("node_parent_indent");
        return parent_indent;
    }
     
     private String getType()
     {
         if(type == null)
             return "";
         else
         {
             if(type.equals("1"))
                 return "root";
             else
                 return type;
         }
             
     }
     
    private boolean isFirsted()
    {
        return this.element.isExpanded() || this.itree.isStatic();
    }
	private void getImageContent(StringBuffer buffer, String event,
			String nodeImage, String typeImage)
	{
		String nodeClickEvent = this.getNodeClickLink("selected");
		// StringBuffer buffer = new StringBuffer();
		if (!event.equals("null") && isCollapse())
		{

			buffer.append("<td nowrap>");

			StringBuffer temp_b = new StringBuffer();// getEventLink(event);
			if(itree.isDynamic() || !this.hasChildren)
			{
				temp_b.append("<a id=\"icon_").append(element.getId()).append(
								"\" onclick=\"doClickImageIcon('").append(
								getEventLink(event)).append(
								"')\" style=\"cursor:hand;\">");
			}
			else  
			{
				
				temp_b.append("<a firsted=\"").append(isFirsted()).append("\" id=\"icon_").append(element.getId()).append(
				"\" onclick=\"doClickImageIcon('").append(
				getEventLink(event))
				.append("','").append(
				this.element.getId())
				.append("','")
				.append(StringUtil.encode(this.element.getName()))
				.append("','")
				.append(this.element.getNode().getType())	
//				.append("','")
//				.append(this.element.getNode().getShowHref())
//				.append("','")
//				.append(this.element.getNode().hasChildren())
				.append("','")
				.append(this.element.getNode().getPath())
				.append("','")
				.append(this.element.isFirstChild())
				.append("','")
				.append(this.element.isLastChild())
				.append("')\" style=\"cursor:hand;\" indent=\"")
				.append(this.indent).append("\"")
				.append(" collapsedimg=\"").append(this.getPreImageForstatic(false)).append("\"")
				.append(" expandedimg=\"").append(this.getPreImageForstatic(true)).append("\"")
				.append(" closedimg=\"").append(this.getFolderImageForstatic(false)).append("\"")
				.append(" openedimg=\"").append(this.getFolderImageForstatic(true)).append("\"")
				.append(">");
			}

			if (this.enablecontextmenu && !nodeClickEvent.equals(""))
			{
				String id = "";
				if (this.element.getNode().isEnablecontextmenu()) // ����ڵ㱾���ƶ����Ҽ��˵��������ñ�����Ҽ��˵���
					id = "_node_" + this.element.getNode().getId();
				else
					// �����������ֱ��ʹ�ýڵ����Ͷ�Ӧ���Ҽ��˵�
					id = "_type_" + this.element.getNode().getType();
				String params = this.getCustomParams(this.element.getNode()
						.getParams());
				String expandLabel = this.getEventLabel();
				buffer
						.append(temp_b.toString())
						.append("<img ")
						.append(
								expandLabel == null ? "" : "expandLabel=\""
										+ expandLabel + "\"")
						.append(" openNode=\"")
						.append(this.element.getNode().getId())
						.append("\"")
						.append(
								expandLabel == null ? " "
										: " expandNode=\"icon_"
												+ this.element.getNode()
														.getId() + "\"")
						.append(" id=\"")
						.append(id)
						.append("\" params=\"")
						.append(params)
						.append(
//								"\" oncotextmenu='InitializedDocEvent();' name=\"icon0_").append(this.element.getId())
						"\" name=\"icon0_").append(this.element.getId())
						.append("\" src=\"")
						.append(nodeImage).append("\" border=\"0\">");

			}
			else

				buffer.append(temp_b.toString()).append("<img name=\"icon0_").append(this.element.getId())
						.append("\" src=\"").append(
						nodeImage).append("\" border=\"0\">");

			buffer.append("</a>");
			// buffer.append(temp_b.toString())
			buffer.append("<a onclick=\"javascript:doclickevt(document.getElementById('icon_").append(
					element.getId()).append(
					"'));\" style=\"cursor:hand;\">");
			if (this.enablecontextmenu && !nodeClickEvent.equals(""))
			{
				String id = "";
				if (this.element.getNode().isEnablecontextmenu()) // ����ڵ㱾���ƶ����Ҽ��˵��������ñ�����Ҽ��˵���
					id = "_node_" + this.element.getNode().getId();
				else
					// �����������ֱ��ʹ�ýڵ����Ͷ�Ӧ���Ҽ��˵�
					id = "_type_" + this.element.getNode().getType();
				String params = this.getCustomParams(this.element.getNode()
						.getParams());
				String expandLabel = this.getEventLabel();
				buffer
						.append("<img ")
						.append(
								expandLabel == null ? "" : "expandLabel=\""
										+ expandLabel + "\"")
						.append(" openNode=\"")
						.append(this.element.getNode().getId())
						.append("\"")
						.append(
								expandLabel == null ? " "
										: " expandNode=\"icon_"
												+ this.element.getNode()
														.getId() + "\"")
						.append(" id=\"")
						.append(id)
						.append("\" params=\"")
						.append(params)
						.append(
//								"\" oncotextmenu=\"InitializedDocEvent();\" src=\"");
						"\"  src=\"");
			}
			else
				buffer.append("<img src=\"");
			buffer.append(typeImage).append("\" name=\"icon1_").append(this.element.getId())
						.append("\"></a></td>");

		}
		else
		{
			if (this.element.getNode().getShowHref() && this.enablecontextmenu
					&& !nodeClickEvent.equals(""))
			{
				String id = "";
				if (this.element.getNode().isEnablecontextmenu()) // ����ڵ㱾���ƶ����Ҽ��˵��������ñ�����Ҽ��˵���
					id = "_node_" + this.element.getNode().getId();
				else
					// �����������ֱ��ʹ�ýڵ����Ͷ�Ӧ���Ҽ��˵�
					id = "_type_" + this.element.getNode().getType();
				String params = this.getCustomParams(this.element.getNode()
						.getParams());

				buffer.append("<td>").append("<img src=\"").append(nodeImage)
						.append("\">");
				buffer
						.append("<img openNode=\"")
						.append(this.element.getNode().getId())
						.append("\" id=\"")
						.append(id)
						.append("\" params=\"")
						.append(params)
						.append(
//								"\" oncotextmenu=\"InitializedDocEvent();\" src=\"")
						"\"  src=\"")
						.append(typeImage).append("\"></td>");
			}
			else
				buffer.append("<td nowrap>").append("<img src=\"").append(nodeImage)
						.append("\">").append("<img src=\"").append(typeImage)
						.append("\"></td>");
		}
		// return buffer.toString();
	}

	/**
	 * ��ȡ��ͷ����
	 */
	public void getUpper(StringBuffer buffer)
	{
		
		
			if (itree.isDynamic())
				buffer
						.append("<tr><td>")
						.append(
								"<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">")
						.append("<tr>");
			else if(itree.isStaticDynamic())
            {
				// ����̬��ϵ����ڵ�
                String parent_indent = request.getParameter("node_parent_indent");
                if(element.isFirstChild()) //\\����
                { 
                    if(parent_indent != null)
                        buffer.append("<div sonids=\"").append(sonids).append("\" style=\"display:none;\" id=\"div_parent_")
                        
                              .append(element.getNode().getParent().getId()).append("\">");
                    else
                    {
                       
                        buffer.append("<div id=\"div_parent_")
                        .append(element.getNode().getParent() != null ?element.getNode().getParent().getId() : "").append("\">");
                    }
    				
                }
                
                buffer
                .append("<div id=\"div_")
                .append(this.element.getId())
                .append("\">")

                .append(
                        "<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">")
                .append("<tr>");
            }
			else if(itree.isStatic())
			{
//				
                if(element.isFirstChild()) //\\����
                { 
                    
                    
                    {
                        if(element.getNode().getParent() == null )
                        {
	                        buffer.append("<div id=\"div_parent_\">");
                        }
                        else
                        {
                        	if(itree.isExpanded(element.getNode().getParent().getId()))
                        	{
                        		 buffer.append("<div id=\"div_parent_")
     	                        .append(element.getNode().getParent().getId() ).append("\">");
	                        	
                        		
                        	}
                        	else
                        	{
                        		buffer.append("<div style=\"display:none;\" id=\"div_parent_")
		                        .append(element.getNode().getParent().getId() ).append("\">");
                        	}
                        }
                    }
    				
                }
                
                buffer
                .append("<div id=\"div_")
                .append(this.element.getId())
                .append("\">")

                .append(
                        "<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\">")
                .append("<tr>");
			}
            
		
		
	}
    
    

	/**
	 * ��ȡ����
	 * 
	 * @return String
	 */
	public void getBoot(StringBuffer buffer)
	{
		// StringBuffer buffer = new StringBuffer();
		if (itree.isDynamic())
			buffer.append("</tr>").append("</table></td></tr>");
		else
        {
			buffer.append("</tr>").append("</table></div>");
            String parent_indent = request.getParameter("node_parent_indent");
            
            if(this.element.isLastChild())
            {
                //����Ǿ�̬���չ���ڵ㣬��ֱ�����</div>����رձ��
                if(parent_indent != null)
                {
                    buffer.append("</div>");
                }
                else
                    /**
                     * modifiye 
                     * �жϵ�ǰ�Զ�չ���Ľڵ��Ƿ��ж���:
                     *  û�ж�����ֱ�����</div>����رձ�ǣ����ҵݹ��жϵ�ǰ�ڵ�����������Ƿ������ĺ���������򲹳�</div>
                     *  �ж�������Ҫ�������⴦�����Ǻ���û�м�����ֱ����Ӹ���</div>���
                     */
                {
                    if(!element.getNode().hasChildren())
                    {
                        
                        buffer.append("</div>");
                        setParentLastChildBoot(element.getNode().getParent(),buffer);
                        
                    }
                    else if(element.getNode().childrenSize() == 0) //
                    {
                    	buffer.append("</div>");
                    }
                }
            }
        }
		
	}
    
    private void setParentLastChildBoot(ITreeNode parent,StringBuffer buffer)
    {
        if(parent == null)
            return ;
        if(isLastChild(parent))
        {
            if(parent.isRoot() )
            {
                if( this.includeRootNode)
                {
                    buffer.append("</div>");
                    setParentLastChildBoot(parent.getParent(),buffer);
                }
            }
            else
            {
                buffer.append("</div>");
                setParentLastChildBoot(parent.getParent(),buffer);
            }
        }
    }
    
    private boolean isLastChild(ITreeNode node)
    {
        return node.getRightNode() == null;
    }

	/**
	 * ��ȡ�ڵ�����Ÿ�ʽ
	 */
	public void getIndent(StringBuffer indent)
	{
		Iterator indentationProfileIterator = element.getIndendationProfile()
				.iterator();
		// StringBuffer indent = new StringBuffer();
		indent.append("<td>");
		if (!itree.isDynamic() && this.element.getNode().hasChildren())
			this.indent = new StringBuffer();
		while (indentationProfileIterator.hasNext())
		{
			boolean isVerticalLineIndentationType = !((Boolean) indentationProfileIterator
					.next()).booleanValue();
			if (this.indent != null)
				this.indent.append(!isVerticalLineIndentationType ? "1" : "0");
			if (isVerticalLineIndentationType)
			{
				// :log imgsrc���붯̬��ȡ
				indent.append("<img src=\"").append(getVerticalLine()).append(
						"\">");
			}
			else
			{
				indent.append("<img src=\"").append(getBlankSpace()).append(
						"\">");
			}
		}
		indent.append("</td>");
		// return indent.toString();

		/**
		 * this.indentationProfileIterator =
		 * getElement().getIndendationProfile().iterator();
		 * 
		 * if(this.indentationProfileIterator.hasNext()){
		 * pageContext.getRequest().setAttribute(getIndentationType(),
		 * this.indentationProfileIterator.next()); return EVAL_BODY_INCLUDE; }
		 * return SKIP_BODY;
		 * 
		 */
	}

	private String getVerticalLine()
	{
		return this.getImageFolder() + "verticalLine.gif";
		// "<img src=\"images/verticalLine.gif\">";
	}

	private String getBlankSpace()
	{
		return this.getImageFolder() + "blankSpace.gif";
		// "<img src=\"images/blankSpace.gif\">";
	}

	private String getCollapsedMidNodeImage()
	{
		return this.getImageFolder() + "collapsedMidNode.gif";
	}

	private String getExpandedMidNodeImage()
	{
		return this.getImageFolder() + "expandedMidNode.gif";
	}

	/**
	 * ��ȡ��Ӧ���͹ر�Ŀ¼ǰ��ͼ��
	 * 
	 * @return String
	 */
	private String getClosedFolderImage()
	{
		if (this.element.getNode().isRoot())
			return this.getImageFolder() + "close_root.gif";
		else
		{
			String type = this.element.getNode().getType();
			if (type == null || type.equals(""))
			{
				return this.getImageFolder() + "closedFolder.gif";
			}
			else
			{
				return this.getImageFolder() + type + "_closedFolder.gif";
			}
		}
	}

	private String getOpenFolderImage()
	{
		if (this.element.getNode().isRoot())
			return this.getImageFolder() + "open_root.gif";
		else
		{
			// return this.getImageFolder() + "openFolder.gif";
			String type = this.element.getNode().getType();
			if (type == null || type.equals(""))
			{
				return this.getImageFolder() + "openFolder.gif";
			}
			else
			{
				return this.getImageFolder() + type + "_openFolder.gif";
			}
		}

	}

	private String getCollapsedLastNodeImage()
	{
		return this.getImageFolder() + "collapsedLastNode.gif";
	}

	private String getExpandedLastNodeImage()
	{
		return this.getImageFolder() + "expandedLastNode.gif";
	}

	private String getNoChildrenMidNodeImage()
	{
		return this.getImageFolder() + "noChildrenMidNode.gif";
	}

	private String getNonFolderImage()
	{
		// return this.getImageFolder() + "nonFolder.gif";
		if (this.element.getNode().isRoot())
			return this.getImageFolder() + "close_root.gif";
		String type = this.element.getNode().getType();
		if (type == null || type.equals(""))
		{
			return this.getImageFolder() + "nonFolder.gif";
		}
		else
		{
			return this.getImageFolder() + type + "_nonFolder.gif";
		}
	}

	private String getNoChildrenLastNodeImage()
	{
		return this.getImageFolder() + "noChildrenLastNode.gif";
	}

	public void setAction(String action)
	{
		this.action = action;
	}

	public String getAction()
	{

		return StringUtil.getRealPath(request, action);
		// return this.action;
	}

	/**
	 * ��ȡ��ѡ�����
	 * 
	 * @return String
	 */
	public String getCheckBox()
	{
		// ���û�����õ�ѡ������ƣ����ߵ�ѡ���ֵΪnullʱ����������Ӧ�ĸ�ѡ��
		String value = this.getCheckboxValue();
		// System.out.println("check box value:" + value);
		if (checkBox == null || checkBox.trim().equals("") || value == null)
			return "";
		String checked = "";

		String[] values = getCheckBoxDefaultValues();
		StringBuffer ret = new StringBuffer();
        String checkBox = this.checkBox;
        boolean recursive = itree.isRecursive();
        
        boolean uprecursive = itree.isUprecursive();
        
        boolean partuprecursive = itree.isPartuprecursive();
        //node_recursive,node_checkboxname
        Map params = this.element.getNode().getParams();
        boolean disabled = false;
        boolean bchecked = false;
        
        
        if(params != null)
        {
            Boolean btemp = (Boolean)params.get("node_recursive");
            if(btemp != null)
                recursive = btemp.booleanValue();
            String temp = (String)params.get("node_checkboxname");
            
            btemp = (Boolean)params.get("node_uprecursive");
            if(btemp != null)
                uprecursive = btemp.booleanValue();
            btemp = (Boolean)params.get("node_partuprecursive");
            if(btemp != null)
            	partuprecursive = btemp.booleanValue();
            
            btemp = (Boolean)params.get("node_checkboxdisabled");            
            if(btemp != null)
            	disabled = btemp.booleanValue();
            
            btemp = (Boolean)params.get("node_checkboxchecked");            
            if(btemp != null)
            	bchecked = btemp.booleanValue();
            

            if(temp != null)
                checkBox = temp;
            
                
        }
        
         
		ret.append("<input type='checkbox' name='").append(checkBox).append(
				"' value='");

		if (value == null)
			ret.append(nodeId);
		else
			ret.append(value);
        
		if(bchecked )
		{
			checked = "checked";
		}
		else if (values != null && StringUtil.containKey(values, value))
		{
			checked = "checked";
		}
		ret.append("' ").append(checked).append(" ").append(" id='checkbox_")
				.append(nodeId).append("'").append(" sonids='")
				.append(element.getNode().getSonids()).append("'").append(" ")
				.append(getCheckBoxExtention());
        if(this.checkboxOnchange != null)
            ret.append(" onClick='")
                .append(this.checkboxOnchange)
                .append("'"); 
        else
            ret.append(" onClick='treeNodeCheckboxChange(null,event)'");
        if(recursive)
        {
            ret.append(" recursive='true'");
        }
        
        else
        {
            ret.append(" recursive='false'");
        }
        
        if(uprecursive)
        {
            ret.append(" uprecursive='true'");
        }
        
        else
        {
            ret.append(" uprecursive='false'");
        }
        if(disabled)
        {
        	ret.append(" disabled");
        }
        ret.append(" partuprecursive='")
           .append(partuprecursive + "")
           .append("'");
        ret.append(">");
		return ret.toString();
	}

//	/**
//	 * ��ȡ��ǰ�ڵ�������ӽڵ��id�����һ�������ԡ�##����Ϊ�ָ���
//	 * 
//	 * @return �������ɵĴ�
//	 */
//	public static String getSonIDs(ITreeNode node)
//	{
//		List list = node.getChildren();
//		StringBuffer ret = new StringBuffer();
//		if (list != null)
//		{
//			boolean first = true;
//			for (int i = 0; i < list.size(); i++)
//			{
//				if (first)
//				{
//					ret.append("").append(
//							((ITreeNode) list.get(i)).getId());
//					first = false;
//				}
//				else
//					ret.append("##").append("").append(
//							((ITreeNode) list.get(i)).getId());
//
//			}
//		}
//		return ret.toString();
//	}

	/**
	 * @return String
	 */
	public String getImageFolder()
	{
		return StringUtil.getRealPath(request, getPath(imageFolder));
	}

	private String getPath(String path)
	{
		if (path == null)
			return path;
		path = StringUtil.replaceAll(path, "\\\\", "/");
		if (path.endsWith("/"))
			return path;
		return path.concat("/");
	}

	/**
	 * ��ȡ��ѡ��Ĵ���
	 * 
	 * @return String
	 */
	public String getRadio()
	{
		String value = this.getRadioValue();
		// System.out.println("value:"+value);
		// System.out.println("radio:"+radio);
		// ���û�����õ�ѡ������ƣ����ߵ�ѡ���ֵΪnullʱ����������Ӧ�ĵ�ѡ��
		if (radio == null || radio.trim().equals("") || value == null)
			return "";
		String checked = "";
		Map params = this.element.getNode().getParams();
		boolean disabled = false;
		boolean bchecked = false;
		
		
		if(params != null)
		{
		    Boolean btemp = (Boolean)params.get("node_radiodisabled");            
		    if(btemp != null)
		    	disabled = btemp.booleanValue();
		    
		    btemp = (Boolean)params.get("node_radiochecked");            
		    if(btemp != null)
		    	bchecked = btemp.booleanValue();	
		    String node_radioname = (String)params.get("node_radioname"); 
		    if(node_radioname != null && !node_radioname.equals(""))
		    {
		    	radio = node_radioname;
		    }
		        
		}
	        
		if(bchecked)
		{
			checked = "checked";
		}
		else if (getRadioDefaultValue() != null
				&& getRadioDefaultValue().equals(value))
			checked = "checked";

		StringBuffer ret = new StringBuffer();
		ret.append("<input type='radio' name='").append(radio).append(
				"' value='");

		if (value == null)
			ret.append(nodeId);
		else
			ret.append(value);
		ret.append("' ");
		if(disabled)
		{
			ret.append(" disabled ");
		}
		ret.append(checked).append(" ").append(
				this.getRadioExtention()).append(">");
		return ret.toString();
		// return radio == null
		// ? ""
		// : new Input()
		// .setType("radio")
		// .setName(radio)
		// .setValue(nodeId)
		// .setChecked(this.getRadioDefaultValue().equals(nodeId))
		// .toString();
	}

	/**
	 * @return target
	 */
	public String getTarget()
	{
		return target;
	}

	/**
	 * @param string
	 */
	public void setTarget(String string)
	{
		target = string;
	}

	/**
	 * @param string
	 */
	public void setLocalAction(String string)
	{
		localAction = string;
	}

	public void setExtendString(String string)
	{
		extendString = string;
	}

	/**
	 * @return String
	 */
	public String getExtendString()
	{
		return extendString == null ? "" : extendString;
	}

	/**
	 * ��ѡ���ȱʡֵ����"$$"�ָ�
	 * 
	 * @return String[]
	 */
	public String[] getCheckBoxDefaultValues()
	{
//		String value = getCheckBoxDefaultValue();
//		if (value == null)
//			return null;
//		// System.out.println("check box default values:" + value);
//		String[] ret = StringUtil.split(value, "\\$\\$");
//		// for(int i = 0; i < ret.length; i ++)
//		// System.out.println("ret[" + i + "]:" + ret[i]);

		return this.checkBoxDefaultValue;

	}

	/**
	 * @return String
	 */
	public String[] getCheckBoxDefaultValue()
	{
		return checkBoxDefaultValue;
	}

	/**
	 * @return String
	 */
	public String getRadioDefaultValue()
	{
		return radioDefaultValue;
	}

	/**
	 * @param string
	 */
	public void setCheckBoxDefaultValue(String[] string)
	{
		checkBoxDefaultValue = string;
	}

	/**
	 * @param string
	 */
	public void setRadioDefaultValue(String string)
	{
		radioDefaultValue = string;
	}

	/**
	 * ��ȡ��ѡ�����չ����
	 * 
	 * @return String[]
	 */
	public String getCheckBoxExtention()
	{
		return checkBoxExtention == null ? "" : checkBoxExtention;
	}

	/**
	 * @param string
	 */
	public void setCheckBoxExtention(String string)
	{
		checkBoxExtention = string;
	}

	/**
	 * @return String
	 */
	public String getRadioExtention()
	{
		return radioExtention == null ? "" : radioExtention;
	}

	/**
	 * @param string
	 */
	public void setRadioExtention(String string)
	{
		radioExtention = string;
	}

	/**
	 * @return String
	 */
	public String getCheckboxValue()
	{
		return checkboxValue;
	}

	/**
	 * @return String
	 */
	public String getRadioValue()
	{
		return radioValue;
	}

	/**
	 * @param string
	 */
	public void setCheckboxValue(String string)
	{
		checkboxValue = string;
	}

	/**
	 * @param string
	 */
	public void setRadioValue(String string)
	{
		radioValue = string;
	}

	/**
	 * ��������ǰҳ��Ľ���ڵ� Description:
	 * 
	 * @return String
	 */
	public static void getCatchScript(StringBuffer ret,
			HttpServletRequest request, String curNodeId)
	{
		String anchor = request.getParameter("collapse");
		if (anchor == null)
		{
			// return "";
			anchor = request.getParameter("expand");
		}
		if (anchor == null)
			return;

		// StringBuffer ret = new StringBuffer();
		// ret.append("<tr><td><a id='anchor_id'
		// href='#").append(anchor).append("></a>");
		// ret.append("<tr><td><A id=\"anchor_id\"
		// HREF=\"#icon_").append(anchor).append("\"></A>");
		// ret.append("<script language='javascript'><!--\r\n")
		// .append("anchor_id.click();")
		// .append("\r\n//--></script></td></tr>");
		if (curNodeId.equals(anchor))
		{
			ret.append("<A id=\"anchor_id\" HREF=\"#icon_").append(anchor)
					.append("\"></A>");
			ret.append("<script language='javascript'><!--\r\n").append(
					"anchor_id.click();").append("\r\n//--></script>");
		}
		// return ret.toString();
	}
	
	public static void getSelectedScript(StringBuffer buffer,ITree tree,String treeid)
	{
		Template tpl = VelocityUtil.getTemplate("tree.vm");
		VelocityContext context = new VelocityContext();
		context.put("isStaticDynamic",new Boolean(tree.isStaticDynamic()));
		context.put("isStatic",new Boolean(tree.isStatic()));
		context.put("tree", treeid);
		context.put("rootid", tree.getRoot().getId());
		
		
		try {
			StringWriter out = new StringWriter();
			
			tpl.merge(context,out);
			out.flush();
			String temp = out.toString();
			buffer.append(temp);
			out.close();
			
			
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MethodInvocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
//	public static void getSelectedScript(StringBuffer buffer,ITree tree)
//	{
//
//        buffer.append("<a id=\"tree_node_bridge\">")
//              .append("</a><a id=\"tree_node_localbridge\">")
//              .append("</a>");
//        if(tree.isStaticDynamic() || tree.isStatic())
//            buffer.append("<iframe height='0' width='0' name='tree_content_bridge' FRAMEBORDER='no' border=0 ></iframe>");
//        buffer.append("<script language=\"javascript\"> ")
//              .append("function doClickTreeNode(linkUrl,selected,target,oldselected,eventLink,nodeid) ")
//              .append("{ ")
//              .append("     if(oldselected && document.getElementById(oldselected))")
//              .append("         document.getElementById(oldselected).className = \"unselectedTextAnchor\";")
//              .append("     if(selectNode && document.getElementById(selectNode))")
//              .append("         document.getElementById(selectNode).className = \"unselectedTextAnchor\"; ")
//              .append("     selectNode = selected; ")
//              .append("if(document.getElementById(selectNode)) ")
//              .append("     document.getElementById(selectNode).className = \"selectedTextAnchor\"; ")
//              .append("     tree_node_bridge.href=linkUrl; ")
//              .append("tree_node_bridge.target=target; ")
//              .append("tree_node_bridge.click(); ")
//              .append("if(eventLink)")
//              .append("{ ")
//              .append("doClickImageIcon(eventLink,nodeid);")
//              .append("} ")
//              .append("} ")
//              .append(" function doClickImageIcon(linkUrl,eventNode)")
//              .append("{")
//              .append("if(eventNode)")
//              .append("{")
//              .append("var firsted = $(\"icon_\" + eventNode).firsted; ")
//              .append("if(firsted == \"true\")")
//              .append("{")
//              .append("nodetoggle(eventNode); ")
//              .append("}")
//              .append("else ")
//              .append("{ ")
//              .append("if(selectNode) ")
//              .append("{")
//              .append("linkUrl += \"&selectedNode=\" + selectNode; ")
//              .append("}")
//              .append("var indent = $(\"icon_\" + eventNode).indent;")
//              .append("linkUrl += \"&node_parent_indent=\" + indent;")
//              .append("document.getElementById(\"icon_\" + eventNode).firsted = \"true\"; ")
//              .append("getSonOfNode(linkUrl,eventNode); ")
//              .append("} ")
//              .append("} ")
//              .append("else ")
//              .append(" {")
//              .append("if(selectNode)")
//              .append("{")
//              .append("linkUrl += \"&selectedNode=\" + selectNode; ")
//              .append("}")
//              .append("tree_node_localbridge.href=linkUrl;  ")
//              .append("tree_node_localbridge.target=\"\"; ")
//              .append("tree_node_localbridge.click();")
//              .append("}")
//              .append("}")
//              .append("function nodetoggle(eventNode) ")
//              .append("{")
//              .append(" 	if(!$(\"div_parent_\" + eventNode)) return;")//����¼��ڵ㲻���ڣ���ֱ�ӷ���
//              .append("if(Element.visible(\"div_parent_\" + eventNode)) ")
//              .append("{")
//              .append("$(\"icon0_\" + eventNode).src = $(\"icon_\" + eventNode).collapsedimg;")
//              .append("$(\"icon1_\" + eventNode).src = $(\"icon_\" + eventNode).closedimg; ")
//              .append("}")
//              .append(" else ")
//              .append(" {")
//              .append("$(\"icon0_\" + eventNode).src = $(\"icon_\" + eventNode).expandedimg; ")
//              .append("$(\"icon1_\" + eventNode).src = $(\"icon_\" + eventNode).openedimg; ")
//              .append("}")
//              .append("Element.toggle(\"div_parent_\" + eventNode); ")
//              .append("} ")
//              .append("function getSonOfNode(linkUrl,eventNode)")
//              .append(" {  ")
//              .append("$(\"tree_content_bridge\").src = linkUrl; ")
//              .append("} ")
//              .append("function setSon(father,sons,sonids,fatherids)")
//              .append("{")
//              .append("new Insertion.After(\"div_\" + father,sons); ")
//              .append("nodetoggle(father); ")
//              .append("var checkNode = document.getElementById(\"checkbox_\" + father);")
//              .append("if(!checkNode)")
//              .append("     return;")
//              .append("   var recursive = checkNode.recursive;")
//              .append("if(recursive && recursive == \"true\")")
//              .append("{")
//              .append("   checkNode.sonids = sonids;")
//              .append("   checkSonCheckbox(checkNode,sonids);")
//              .append("}")
//              .append("}")
//              .append("function treeNodeCheckboxChange(checkNode)")
//              .append("{")
//              .append("if(!checkNode)")
//              .append("     checkNode = event.srcElement;")
//              .append("var sonids = checkNode.sonids;")
//              .append("var recursive = checkNode.recursive;")
//              .append("if(recursive && recursive == \"true\")")
//              .append("		checkSonCheckbox(checkNode,sonids);")
//              
//              .append("var fatherids = checkNode.fatherids;")
//              .append("var uprecursive = checkNode.uprecursive;")
//              .append("if(uprecursive && uprecursive == \"true\")")
//              .append("		checkFatherCheckbox(checkNode,fatherids);")
//              .append("}")
//              .append("function checkSonCheckbox(checkNode,sonids)")
//              .append("{")
//              .append(" if(sonids && sonids != \"\")")
//              .append(" {")
//              .append("   var sons = sonids.split(\"##\");")
//              .append("   if(sons.length){  ")
//              .append("  	for(var i = 0; i < sons.length; i ++)")
//              .append("  	{")
//              .append("   		if($(\"checkbox_\" + sons[i])){")
//              .append("      		$(\"checkbox_\" + sons[i]).checked = checkNode.checked;")
//              .append("      		checkSonCheckbox($(\"checkbox_\" + sons[i]),$(\"checkbox_\" + sons[i]).sonids);")
//              .append("			}")	
//              .append("  	}")
//              .append("	  }else")
//              .append("   {")
//              .append("   	if($(\"checkbox_\" + sons)){")
//              .append("  		$(\"checkbox_\" + sons).checked = checkNode.checked;")
//              .append("      	checkSonCheckbox($(\"checkbox_\" + sons),$(\"checkbox_\" + sons).sonids);")
//              .append("		 }")
//              .append("   }")
//              .append(" }")
//              .append("}")
//              .append("function checkFatherCheckbox(checkNode,fatherids)")
//              .append("{")
//              .append(" if(checkNode.checked && fatherids && fatherids != \"\")")
//              .append(" {")
//              .append("   var fathers = fatherids.split(\"##\");")
//              .append("   if(fathers.length)")
//              .append("  	for(var i = 0; i < fathers.length; i ++)")
//              .append("  	{")
//              .append("   		if($(\"checkbox_\" + fathers[i]))")
//              .append("      		$(\"checkbox_\" + fathers[i]).checked = checkNode.checked;")
//              
//              .append("  	}")
//              .append("	  else")
//              .append("   {")
//              .append("   		if($(\"checkbox_\" + fathers))")
//              .append("  			$(\"checkbox_\" + fathers).checked = checkNode.checked;")
//             
//              .append("   }")
//              .append(" }")
//              .append("}")
//              /**
//               * ȥ���ӽڵ�ʱ���ݹ��⸸�ڵ��Ƿ���Ҫ��ȥ��
//               */
//              .append("function checkPartFatherCheckbox(checkNode,fatherids)")
//              .append("{")
//              .append("      ")
//              .append(" if(fatherids && fatherids != \"\")")
//              .append(" {")
//              .append("   var fathers = fatherids.split(\"##\");")
//              .append("   if(checkNode.checked){")
//              .append("   	if(fathers.length)")
//              .append("  		for(var i = 0; i < fathers.length; i ++)")
//              .append("  		{")
//              .append("				if($(\"checkbox_\" + fathers[i]).checked) return;")
//              .append("   			if($(\"checkbox_\" + fathers[i]))")
//              .append("      			$(\"checkbox_\" + fathers[i]).checked = true;")
//              
//              .append("  		}")
//              .append("	  	else")
//              .append("   	{")
//              .append("   		if($(\"checkbox_\" + fathers))")
//              .append("  			$(\"checkbox_\" + fathers).checked = checkNode.checked;")
//             
//              .append("   	}")
//              .append("    }")
//              .append("    else")
//              .append("	   {")
//              .append("   	if(fathers.length){")
//              .append("			var flag = false;")
//              .append("  		for(var i = fathers.length - 1; i >= 0; i --)")
//              .append("  		{")
//              .append("   			if($(\"checkbox_\" + fathers[i])){")
//              .append("					if($(\"checkbox_\" + fathers[i]).checked == false) return;")
//              .append("					var check = false;")
//              .append("      			var sonids = $(\"checkbox_\" + fathers[i]).sonids;")
//              .append("   				var sons = sonids.split(\"##\");")	
//               .append("   				if(sons.length)")
//	              .append("					for( var j = 0; sons.length && j < sons.length; j ++)")	
//	              .append("					{")
//	              .append("						if($(\"checkbox_\" + sons[j]).checked)")
//	              .append("						{check = true;break;}		")
//	              								
//	              .append("					}")	
//				          .append("	  	else")
//			              .append("   	{")
//			              .append("   		if($(\"checkbox_\" + sonids).checked)")
//			              .append("			{check = true;}		")
//			              .append("   	}")
//              .append(" 				if(check) break;")
//              .append("    				else ")
//              .append("						$(\"checkbox_\" + fathers[i]).checked = false;")
//              .append("				}")	
//              .append("  		}")
//              .append("	  	}else")
//              .append("   	{")
//              .append("   		if($(\"checkbox_\" + fathers)){")
//              .append("					if($(\"checkbox_\" + fathers).checked == false) return;")
//              .append("					var check = false;")
//              .append("      			var sonids = $(\"checkbox_\" + fathers).sonids;")
//              .append("   				var sons = sonids.split(\"##\");")	
//               .append("   				if(sons.length)")
//	              .append("					for( var j = 0; sons.length && j < sons.length; j ++)")	
//	              .append("					{")
//	              .append("						if($(\"checkbox_\" + sons[j]).checked)")
//	              .append("						{check = true;break;}		")
//	              								
//	              .append("					}")	
//				          .append("	  	else")
//			              .append("   	{")
//			              .append("   		if($(\"checkbox_\" + sonids).checked)")
//			              .append("			{check = true;}		")
//			              .append("   	}")
//              .append(" 				if(check) break;")
//              .append("    				else ")
//              .append("						$(\"checkbox_\" + fathers).checked = false;")
//              .append("			}")	
//             
//              .append("   	}")
//              .append("    }")
//              		
//              .append(" }")
//              .append("}")
//              .append("</script>");
//	}

	// /**
	// * ��ȡ
	// * @param buffer
	// * @param event
	// */
	// private void getSelectedScript(StringBuffer buffer,String event)
	// {
	//
	// }

	public static void getInitScript(StringBuffer ret, String selectedNode,String contextpath)
	{
		// StringBuffer ret = new StringBuffer();
		ret.append("<script language=\"javascript\">");
		ret.append("     var selectNode;");
		if (selectedNode != null)
		{
			ret.append("selectNode = \"").append(selectedNode).append("\";");
		}
		ret.append("</script >");
		
		/*
		 * �Ѿ��Ƶ�textMenu.vm�ļ���
		 */
//        ret.append("<script language=\"javascript\" src=\"").append(contextpath).append("/include/prototype-1.4.0.js\"></script >");
		// return ret.toString();
	}
	
//	public static void getPopScript(StringBuffer ret,HttpServletRequest request,boolean enablecontextmenu)
//	{
//	    try
//        {
//            ret.append(PageConfig.getPopScript(request,enablecontextmenu));
//        }
//	    catch (ResourceNotFoundException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        catch (ParseErrorException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        catch (MethodInvocationException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        catch (IOException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//	}
	
//	public static void getConfigScript(StringBuffer ret,HttpServletRequest request)
//	{
//	    try
//        {
//            ret.append(PageConfig.getConfig(request));
//        }
//        catch (ResourceNotFoundException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        catch (ParseErrorException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        catch (MethodInvocationException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        catch (IOException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
////		ret.append("<script language=\"javascript\" src=\"").append(contextpath).append("/include/prototype-1.4.0.js\"></script >");
//	}

	public static void main(String args[])
	{
		// String[] temp = StringUtil.split("aaa$$www","\\$\\$");
		// System.out.println(temp.length);
		// System.out.println(temp[0]);
		// System.out.println(temp[1]);
		System.out.println("��");
		System.out.println(System.getProperties());

	}

	public void setScope(String scope)
	{
		this.scope = scope;
	}

	/**
	 * Description:
	 * 
	 * @return String
	 */
	public String getScope()
	{
		return scope;
	}

	/**
	 * @return Returns the isCollapse.
	 */
	public boolean isCollapse()
	{
		return isCollapse;
	}

	/**
	 * @return Returns the params.
	 */
	public String getParams()
	{
		return params;
	}

	public boolean isDoubleEvent()
	{
		return doubleEvent;
	}

	/**
	 * @param params
	 *            The params to set.
	 */
	public void setParams(String params)
	{
		this.params = params;
	}

	/**
	 * @param b
	 */
	public void setNowrap(boolean nowrap)
	{

		this.nowrap = nowrap;

	}

	public void setDoubleEvent(boolean doubleEvent)
	{
		this.doubleEvent = doubleEvent;
	}

	public void setEnablecontextmenu(boolean b)
	{
		enablecontextmenu = b;
	}

	public boolean isEnablecontextmenu()
	{
		return enablecontextmenu;
	}

//	public boolean isDynamic()
//	{
//		return dynamic;
//	}
	private boolean includeRootNode = true;
	public void setTree(ITree itree)
	{
		this.itree = itree;
	}
//	public void setDynamic(boolean dynamic)
//	{
//		this.dynamic = dynamic;
//	}
    
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

	public boolean isUprecursive() {
		return uprecursive;
	}

	public void setUprecursive(boolean uprecursive) {
		this.uprecursive = uprecursive;
	}

    public boolean isIncludeRootNode()
    {
        return includeRootNode;
    }

    public void setIncludeRootNode(boolean includeRootNode)
    {
        this.includeRootNode = includeRootNode;
    }
}
