<%
/**
 * �򵥵��Ҽ��˵�������
 */
 String treetype = request.getParameter("treetype");
String id = treetype+"root";
 %>

<%@ taglib uri="/WEB-INF/treetag.tld" prefix="tree" %>
<%@ page language="java" contentType="text/html; charset=GBK"%>

		<div id="treebody" class="shadow">
				<div class="info">
					<p>
		        <!-- 
		        	ͨ��һ���׵�����ǩ��������
		        	tree ����ָ������Ψһ����
		        	imageFolder ָ�����ڵ��ͼ��Ŀ¼
		        	collapse ָ�����ڵ��Ƿ�ȫ���̿������Ҳ����۵���true�����۵���falseȫ��չ�����ǲ����۵�
		        	includeRootNode �Ƿ�������ڵ�
		        	href �ڵ�ȫ��url��ַ
		        	target �ڵ�url��������
		        	mode ��������չʾģʽ��Ϊ����ģʽ
		         -->
		         <tree:tree tree="TreeWithContextMenu"
		    	           node="TreeWithContextMenu.node"
		    	           imageFolder="/jsp/tree/tree_images"
		    	           collapse="true"
		    			   includeRootNode="true"			   
		    			   href="/jsp/tree/testtreenode.jsp"    			   
		    			   target="_blank"
		    			   mode="static-dynamic"
		    			   jquery="true"
		    			   > 
		    			   <!--
		    			   		����չ�����۵�ʱ  ����ҳ��Ĳ���
		    			   		uprecursive="true" 
		    			   		partuprecursive="false"
		    			   		recursive="true"
		    			   -->                        
		                   <tree:param name="treetype"/>
		                    <tree:checkbox recursive="false" uprecursive="false" name="test_checkbox"/>
		                   <!-- ָ���������ݼ������͸��ڵ���Ϣ
		                   		treetype-���ݼ�������ʵ���࣬������test.tree.TestTree
		                   		scope ���ݼ���������Ĵ洢��Χ��һ����request����
		                   		
		                   		ָ�����ڵ����Ϣ��
		                   		rootid ���ڵ��id
		                   		rootName ���ڵ������
		                   		
		                   		expandLevel Ĭ��չ�����ټ�
		                   		enablecontextmenu �Ƿ������Ҽ��˵���true���ã�false������
		                    -->
		                   
		                    
		    			   <tree:treedata treetype="org.frameworkset.web.tree.TreeWithContextMenu1"
		    	                   scope="request"
		    	                   rootid="<%=id %>"  
		    	                   rootName="������"
		    	                   expandLevel="2"
		    	                   showRootHref="true"
		    	                   needObserver="false"
		    	                   refreshNode="false"
		    	                   enablecontextmenu="true" 
		    	                   />
		
		    	</tree:tree>
		         	</p>
				</div>
			</div>
	
