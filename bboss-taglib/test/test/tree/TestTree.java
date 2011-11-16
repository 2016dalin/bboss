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

import com.frameworkset.common.tag.tree.COMTree;
import com.frameworkset.common.tag.tree.itf.ITreeNode;

/**
 * 
 * <p>TestTree.java</p>
 * <p> Description: </p>
 * <p> bboss workgroup </p>
 * <p> Copyright (c) 2009 </p>
 * 
 * @Date Jun 14, 2009
 * @author biaoping.yin
 * @version 1.0
 */
public class TestTree extends COMTree {
	
	
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
		    Map params = new HashMap();
		    
			TreeNode son = nodes.get(i);
			if(son.level == 0)
			    params.put("node_linktarget", "#outline");
			else if(son.level == 1)
                params.put("node_linktarget", "#details");
			else if(son.level == 2)
                params.put("node_linktarget", "#footer");
			else
			{
			    params.put("node_linktarget", "#footer");
			    params.put("nodeLink", "javascript:alert('yes');");
			}
			
			//���ýڵ�Ĭ��ѡ�У�
			
			params.put("node_checkboxchecked", new Boolean(true));
			
			    
			addNode(parent,
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
		}
		return true;
	}

}
