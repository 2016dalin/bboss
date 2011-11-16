<%
/**
 * �򵥵��Ҽ��˵�������
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
		<title>�Ҽ��˵�������</title>
	<!--  
	������ʽ�ļ�
	-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/include/treeview.css">
	<script type="text/javascript"> 
	function edit(message)
	{
		alert(message);
	}
	</script>
	</head>
	<body class="contentbodymargin" scroll="yes">
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
		         <tree:tree tree="TreeWithContextMenu"
		    	           node="TreeWithContextMenu.node"
		    	           imageFolder="tree_images"
		    	           collapse="true"
		    			   includeRootNode="true"			   
		    			   href="testtreenode.jsp"    			   
		    			   target="_blank"
		    			   mode="static-dynamic"
		    			   > 
		    			   <!--
		    			   		����չ�����۵�ʱ  ����ҳ��Ĳ���
		    			   		uprecursive="true" 
		    			   		partuprecursive="false"
		    			   		recursive="true"
		    			   -->                        
		                   <tree:param name="oid"/>
		                    <tree:checkbox recursive="true" uprecursive="true" name="test_checkbox"/>
		                   <!-- ָ���������ݼ������͸��ڵ���Ϣ
		                   		treetype-���ݼ�������ʵ���࣬������test.tree.TestTree
		                   		scope ���ݼ���������Ĵ洢��Χ��һ����request����
		                   		
		                   		ָ�����ڵ����Ϣ��
		                   		rootid ���ڵ��id
		                   		rootName ���ڵ������
		                   		
		                   		expandLevel Ĭ��չ�����ټ�
		                   		enablecontextmenu �Ƿ������Ҽ��˵���true���ã�false������
		                    -->
		                   
		                    
		    			   <tree:treedata treetype="test.tree.TreeWithContextMenu"
		    	                   scope="request"
		    	                   rootid="root"  
		    	                   rootName="������"
		    	                   expandLevel="2"
		    	                   showRootHref="true"
		    	                   needObserver="false"
		    	                   refreshNode="false"
		    	                   enablecontextmenu="true" 
		    	                   />
		
		    	</tree:tree>
		         </td></tr>
		    </table>
		</div>
	</body>
</html>

