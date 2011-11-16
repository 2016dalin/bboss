<%
/**
 * �򵥵Ĳ�����
 */
 %>

 <%     
	response.setHeader("Cache-Control", "no-cache"); 
	response.setHeader("Pragma", "no-cache"); 
	response.setDateHeader("Expires", -1);  
	response.setDateHeader("max-age", 0); 
%>
<%@ taglib uri="/WEB-INF/treetag.tld" prefix="tree" %>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<%
	
%>
<html>
	<head>
		<title>�򵥵Ĳ�����</title>
	<!--  
	������ʽ�ļ�
	-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/include/treeview.css">
	</head>
	<body class="contentbodymargin" scroll="no">
		<div id="contentborder">
		    <table >
		    	
		        <tr><td>
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
		         <tree:tree tree="simple_tree"
		    	           node="simple_tree.node"
		    	           imageFolder="tree_images"
		    	           collapse="true"
		    			   includeRootNode="true"			   
		    			   href="testtreenode.jsp"    			   
		    			   target="_blank"
		    			   mode="static-dynamic"
		    			   > 
		    			   <!--
		    			   		����չ�����۵�ʱ  ����ҳ��Ĳ���
		    			   -->                        
		                   <tree:param name="oid"/>
		                   <!-- ָ���������ݼ������͸��ڵ���Ϣ
		                   		treetype-���ݼ�������ʵ���࣬������test.tree.TestTree
		                   		scope ���ݼ���������Ĵ洢��Χ��һ����request����
		                   		
		                   		ָ�����ڵ����Ϣ��
		                   		rootid ���ڵ��id
		                   		rootName ���ڵ������
		                   		
		                   		expandLevel Ĭ��չ�����ټ�
		                   		enablecontextmenu �Ƿ������Ҽ��˵���true���ã�false������
		                    -->
		    			   <tree:treedata treetype="test.tree.TestTree"
		    	                   scope="request"
		    	                   rootid="root"  
		    	                   rootName="������"
		    	                   expandLevel="2"
		    	                   showRootHref="true"
		    	                   needObserver="false"
		    	                   refreshNode="false"
		    	                   enablecontextmenu="false" 
		    	                   />
		
		    	</tree:tree>
		         </td></tr>
		    </table>
		</div>
	</body>
</html>
