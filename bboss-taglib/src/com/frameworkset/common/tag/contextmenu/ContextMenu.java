package com.frameworkset.common.tag.contextmenu;

import java.io.Serializable;
import java.util.Set;
/**
 * 
 * <p>Title: com.frameworkset.common.tag.contextmenu.ContextMenu.java</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company: chinacreator</p>
 * @Date 2006-9-15
 * @author biaoping.yin
 * @version 1.0
 */
public interface ContextMenu extends Serializable{
	/**
	 * ��ȡ�ڵ����Ľڵ��Ϲ�����Ҽ��˵�
	 * @return
	 */	
	public Set getNodeContextmenus();
	/**
	 * ��ȡ�����Ҽ��˵��б�
	 * @return
	 */
	public Set getTypeContextmenus();
	
	/**
	 * ����ڵ��Ҽ��˵�
	 *
	 */
	public void clearNodeContextmenus();
	/**
	 * �������Ҽ��˵�
	 *
	 */
	public void clearTypeContextmenus();
	/**
	 * ������е��Ҽ��˵�
	 *
	 */
	public void clearContextmenus();
	/**
	 * �������Ҽ��˵�
	 * @param menu
	 */
	public void addContextMenuOfType(Menu menu);
	/**
	 * �ϲ������Ҽ��˵��ͽڵ��Ҽ��˵�
	 *
	 */
	public void mergeContextMenus();
	
	/**
	 * ��ȡ���е��Ҽ��˵�
	 * @return
	 */
	public Set getContextmenus();
	
	/**
	 * ��ӽڵ��Ҽ��˵�
	 * @param node
	 * @param menu
	 */
	public void addContextMenuOfNode(AttachElement node,Menu menu);
	/**
	 * ���һ����Ҽ��˵�����
	 * @param menu
	 */
	public void addContextMenu(Menu menu);

}
