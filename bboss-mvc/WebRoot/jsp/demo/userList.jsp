<%@ page contentType="text/html; charset=GBK" language="java" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ taglib uri="/WEB-INF/commontag.tld" prefix="common"%>
<%@ page import="org.frameworkset.web.servlet.support.RequestContext" %>

<!-- 
	������ͨ�����ݼ�������ȡ��ҳ�б����ݣ������ṩ��ѯ����
-->
<html>
<head>
<title>������ͨ�����ݼ�������ȡ��ҳ�б����ݣ������ṩ��ѯ����</title>
</head>
<link rel="shortcut icon"
			href="${pageContext.request.contextPath}/css/favicon.gif">
		<link rel="stylesheet"
			href="${pageContext.request.contextPath}/css/classic/tables.css"
			type="text/css">
		<link rel="stylesheet"
			href="${pageContext.request.contextPath}/css/classic/main.css"
			type="text/css">
		<link rel="stylesheet"
			href="${pageContext.request.contextPath}/css/classic/mainnav.css"
			type="text/css">
		<link rel="stylesheet"
			href="${pageContext.request.contextPath}/css/classic/messages.css"
			type="text/css">
		<link rel="stylesheet"
			href="${pageContext.request.contextPath}/css/classic/tooltip.css"
			type="text/css">
<body>
	<table class="genericTbl">
			<tr >
				<form action="/bboss-mvc/pagerqueryuser.htm" method="post">
					<td  style="width:20%" class="order1 sorted">�������û�����</td>
					<td  style="width:5%" colspan="100"><input type="text" name="userName" value="<common:request name="userName" defaultValue=""/>">
							<input type="submit" name="��ѯ" value="��ѯ"></td>
				</form>
			</tr>
				<!--��ҳ��ʾ��ʼ,��ҳ��ǩ��ʼ��-->
			<pg:pager scope="request" data="users" 
						  isList="false">
				<pg:param name="userName"/>
					<tr >
						<!--���÷�ҳ��ͷ-->

						<th  width="2%" align=center  class="order1 sorted">
						ȫѡ<input class="checkbox" 
							type="checkBox" hidefocus=true 
							name="checkBoxAll" 
							onClick="checkAll('checkBoxAll','ID')"> 
						</th>
						<th width="8%" class="order1 sorted">
							�û�id					</th>
						<th width="8%" class="order1 sorted">
							�û�����					</th>
						<th width="8%" class="order1 sorted">
							�û�����					</th>
						
					</tr>
				
				<pg:notify>
					<tr >
						<td width="2%" align=center colspan="100" style="width:5%" class="order1 sorted">
							û���û�
						</td>
					</tr>				
				</pg:notify>
				<pg:list >
					<tr class="even">
						<td width="2%" align=center style="width:5%">
							<input class="checkbox" hideFocus onClick="checkOne('checkBoxAll','ID')" 
							type="checkbox" name="ID" ></td>
						<td	><pg:cell colName="userId" defaultValue=""/>
						</td>
						<td width="8%" >
							<pg:cell colName="userName" defaultValue=""/></td>
						<td width="8%" >
							<pg:cell colName="userPassword" defaultValue=""/>		
						</td>
					</tr>
					</pg:list>
					<tr><pg:index/></tr>
			</pg:pager> 
	</table>
</body>
</html>
