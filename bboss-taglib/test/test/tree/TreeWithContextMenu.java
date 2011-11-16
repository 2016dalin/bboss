/**
 *  Copyright 2008 biaoping.yin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.  
 */
package test.tree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import com.frameworkset.common.tag.contextmenu.Menu;
import com.frameworkset.common.tag.contextmenu.Menu.ContextMenuItem;
import com.frameworkset.common.tag.tree.COMTree;
import com.frameworkset.common.tag.tree.itf.ITreeNode;

/**
 * <p>TreeWithContextMenu.java</p>
 * <p> Description: </p>
 * <p> bboss workgroup </p>
 * <p> Copyright (c) 2009 </p>
 * 
 * @Date Jun 14, 2009
 * @author biaoping.yin
 * @version 1.0
 */
public class TreeWithContextMenu extends COMTree {
	
	
	@Override
	public void setPageContext(PageContext pageContext) {
		
		super.setPageContext(pageContext);
		TreeUtil.buildTreeDatas();
	}

	@Override
	public boolean hasSon(ITreeNode parent) {
		String uid = parent.getId();
		TreeNode node = TreeUtil.getTreeNode(uid);
		if(node.hasSon())
			return true;
		return false;
	}

	@Override
	public boolean setSon(ITreeNode parent, int curLevel) {
		String uid = parent.getId();
		TreeNode node = TreeUtil.getTreeNode(uid);
		List<TreeNode> nodes = node.getSons();
		Map params = null;
		/**
		 * �����ÿ���ڵ�ʱ���ڵ�����в������õ�һ��Map�����У��������µļ������������б����Ĳ������ƣ�������Ϊ����������
			node_recursive:ָ���ض����ڵ�ĸ�ѡ���Ƿ���еݹ�ѡ��Ĺ��ܣ�ֵ����Boolean
			node_uprecursive:ָ���ض����ڵ�ĸ�ѡ���Ƿ���еݹ�ѡ���ϼ���ѡ��Ĺ��ܣ�ֵ����Boolean
			node_partuprecursive��booleanֵ��ָ���ض��ڵ�ĸ�ѡ���Ƿ���в��ֵݹ�
			node_checkboxname������ָ���ڵ�ǰ�ĵĸ�ѡ�������
			nodeLink:ָ��ÿ�����ڵ�����ӵ�ַ��������javascript������Ҳ�����ǳ�����
			node_linktarget��ָ��ÿ���ڵ����ӵ�ַ������������
			node_radioname������ÿ���ڵ㵥ѡ������
			node_checkboxchecked :booleanֵ ��ʶ��ѡ���Ƿ�ѡ��
			node_checkboxdisabled��booleanֵ ��ʶ�ڵ�ǰ�ĸ�ѡ���Ƿ񱻽���
			node_radiochecked��booleanֵ ��ʶ�ڵ�ǰ�ĵ�ѡ��ť�Ƿ�ѡ��
			node_radiodisabled��booleanֵ ��ʶ�ڵ�ǰ�ĵ�ѡ��ť�Ƿ񱻽���
		 */
//		Map<paramname,paramvalue> params = new HashMap();
		for(int i = 0; i < nodes.size(); i ++)
		{
			
			TreeNode son = nodes.get(i);
			params = new HashMap();
			if(son.getName().equals("root-1-2"))
				params.put("node_checkboxchecked", new Boolean(true));
			ITreeNode t = addNode(parent,
					son.getUid(), //treeid 
					son.getUid(), //tree node name
	                null,// node type
	                true, //show href,true ʱ���ڵ㽫�������ӣ�falseʱ����������
	                curLevel, //current level
	                (String) null,//��ע
	                (String) son.getUid(), //radio value,��ѡ��ť
	                (String) son.getUid(), //��ѡ���ֵ
	                params  //Ϊ�ڵ�����ָ��url�Ĳ���<paramname,paramvalue>
	                );
			Menu menu = new Menu();
			menu.addContextMenuItem(Menu.MENU_OPEN);
			
			menu.addContextMenuItem(Menu.MENU_EXPAND);
			menu.addContextMenuItem("���","javascript:edit('���')",Menu.icon_edit);
			//Menu.ContextMenuItem sitemenuitem0 = new Menu.ContextMenuItem();
			//sitemenuitem0.setName("�༭�༭�༭�༭");
			//sitemenuitem0.setLink("javascript:edit('�༭')");
			//sitemenuitem0.setIcon(Menu.icon_edit);
			//menu.addContextMenuItem(sitemenuitem0);
			menu.addSeperate();
			menu.addContextMenuItem("�༭�༭�༭�༭","javascript:edit('�༭')",Menu.icon_add);
			
			Menu.ContextMenuItem sitemenuitem2 = menu.addContextMenuItem("sitemenuitem2","javascript:edit('sitemenuitem2')",Menu.icon_ok);
			sitemenuitem2.addSubContextMenuItem("��menusubmenuitem_","javascript:edit('��menusubmenuitem_')",Menu.icon_ok);	
			sitemenuitem2.addSubContextMenuItem("��cut","javascript:edit('��cut')",Menu.icon_cut);				
			sitemenuitem2.addSubContextMenuItem("��icon_back","javascript:edit('��icon_back')",Menu.icon_back);
			sitemenuitem2.addSubContextMenuItem("��icon_cancel","javascript:edit('��icon_cancel')",Menu.icon_cancel);
			sitemenuitem2.addSubContextMenuItem("��icon_help","javascript:edit('��icon_help')",Menu.icon_help);
			sitemenuitem2.addSubContextMenuItem("��icon_no","javascript:edit('��icon_no')",Menu.icon_no);
			sitemenuitem2.addSubContextMenuItem("��icon_print","javascript:edit('��icon_print')",Menu.icon_print);
			sitemenuitem2.addSubContextMenuItem("��icon_redo","javascript:edit('��icon_redo')",Menu.icon_redo);
			sitemenuitem2.addSubContextMenuItem("��icon_reload","javascript:edit('icon_reload')",Menu.icon_reload);
			sitemenuitem2.addSubContextMenuItem("icon_remove","javascript:edit('icon_remove')",Menu.icon_remove);
			sitemenuitem2.addSubContextMenuItem("icon_save","javascript:edit('icon_save')",Menu.icon_save);
			sitemenuitem2.addSubContextMenuItem("icon_search","javascript:edit('icon_search')",Menu.icon_search);
			sitemenuitem2.addSubContextMenuItem("icon_undo","javascript:edit('icon_undo')",Menu.icon_undo);
			ContextMenuItem third = sitemenuitem2.addSubContextMenuItem("�ڶ���","javascript:edit('icon_undo')",Menu.icon_undo);
			third.addSubContextMenuItem("����", "javascript:edit('icon_undo')",Menu.icon_undo);
//			//����һ���Ӳ˵�
//			Menu submenu = new Menu();
//			submenu.setIdentity("submenu_" + son.getUid());//��֤ÿ���Ӳ˵���id��Ψһ�ԣ�ÿ���ڵ���Ӳ˵���ҪΨһ
//			Menu.ContextMenuItem submenuitem1 = new Menu.ContextMenuItem();
//			submenuitem1.setName("�Ӳ˵�1");
//			submenuitem1.setLink("javascript:edit('�Ӳ˵�1')");
//			submenuitem1.setIcon(request.getContextPath() + "/tree/tree_images/edit.gif");
//			submenu.addContextMenuItem(submenuitem1);
//			
//			Menu.ContextMenuItem submenuitem2 = new Menu.ContextMenuItem();
//			submenuitem2.setName("�Ӳ˵�2");
//			submenuitem2.setLink("javascript:edit('�Ӳ˵�2')");
//			submenuitem2.setIcon(request.getContextPath() + "/tree/tree_images/edit.gif");
//			submenu.addContextMenuItem(submenuitem2);
//			
//			Menu.ContextMenuItem cascade = new Menu.ContextMenuItem();
//		
//			cascade.setName("�༶�˵���ʾ");
//			cascade.setSubMenu(submenu);
//			
//			
//			
//			menu.addContextMenuItem(cascade);
			
//			//�ж��Ƿ���ģ������Ȩ��
//			
//			super.addContextMenuOfType(menu);
			
			
			super.addContextMenuOfNode(t,menu);
		}
		return true;
	}
}
