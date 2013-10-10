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
	 String rootPath = org.frameworkset.mvc.FileController.getWorkFoldPath();
%>
<%@ taglib uri="/WEB-INF/treetag.tld" prefix="tree" %>
<%@ page language="java" contentType="text/html; charset=GBK"%>

<%
String treetype = request.getParameter("treetype");
String id = treetype+"root";
%>
<html>
	<head>
		<title>�򵥵Ĳ�����</title>
		<style type="text/css">
.a_bg_color{
	color: white;
    cursor:hand;
	background-color: #191970
}
</style>
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
		    	           imageFolder="../examples/tree_images"
		    	           collapse="true"
		    			   includeRootNode="true"	
		    			  jquery="true"
		    			   mode="static-dynamic"
		    			   > 
		    		          
		                  
		                   <!-- ָ���������ݼ������͸��ڵ���Ϣ
		                   		treetype-���ݼ�������ʵ���࣬������test.tree.TestTree
		                   		scope ���ݼ���������Ĵ洢��Χ��һ����request����
		                   		
		                   		ָ�����ڵ����Ϣ��
		                   		rootid ���ڵ��id
		                   		rootName ���ڵ������
		                   		
		                   		expandLevel Ĭ��չ�����ټ�
		                   		enablecontextmenu �Ƿ������Ҽ��˵���true���ã�false������
		                    -->
		    			   <tree:treedata treetype="org.frameworkset.mvc.FolderTree"
		    	                   scope="request"
		    	                   rootid="<%=rootPath %>"  
		    	                   rootName="��Դ����"
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
