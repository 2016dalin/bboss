<%@ page contentType="text/html; charset=GBK" language="java" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>
<%@ page import="org.frameworkset.web.servlet.support.RequestContext" %>

<!-- 
	������ͨ����������ȡ��ҳ�б����ݣ������ṩ��ѯ����
-->
<html>
<head>
<title>������ͨ����������ȡ��ҳ�б����ݣ������ṩ��ѯ����</title>
</head>
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
				<tr class="cms_report_tr">
						<!--���÷�ҳ��ͷ-->
					<form action="<%=RequestContext.getPathWithinHandlerMappingPath(request)%>" method="post">
						<th  style="width:20%" class="order1 sorted">�����������</th>
						<th  style="width:5%" colspan="100"><input type="text" name="TABLE_NAME" value="<%=request.getParameter("TABLE_NAME") %>"><input type="submit" name="��ѯ" value="��ѯ"></th>
					</form>
				</tr>
						
	    
				<!--��ҳ��ʾ��ʼ,��ҳ��ǩ��ʼ��-->
				<pg:pager scope="request" data="pagedata" 
						  isList="false">
				<pg:param name="TABLE_NAME"/>
					<tr class="cms_report_tr">
						<!--���÷�ҳ��ͷ-->

						<th width="2%" align=center style="width:5%" class="order1 sorted">
						<input class="checkbox" 
							type="checkBox" hidefocus=true 
							name="checkBoxAll" 
							onClick="checkAll('checkBoxAll','ID')"> 
						</th>
						<pg:title nowrap="true" width="8%" title="TABLE_NAME"
											sort="true" colName="TABLE_NAME" />
						
						<th width="8%" class="order1 sorted">
							TABLE_ID_GENERATOR					</th>
							<th width="8%" class="order1 sorted">
							TABLE_ID_TYPE					</th>
						
					</tr>
				
				<pg:notify>
					<tr class="cms_report_tr">
						

					<td width="2%" align=center style="width:5%">
						û������
					</td>
					</tr>				
				</pg:notify>
					
				<pg:list autosort="false">
				
					<tr class="cms_report_tr">
						

						<td width="2%" align=center style="width:5%" >
							<input class="checkbox" hideFocus onClick="checkOne('checkBoxAll','ID')" 
							type="checkbox" name="ID" 
							value="<pg:cell colName="TABLE_NAME" defaultValue=""/>">
							<img border="0" src="${pageContext.request.contextPath}<pg:theme code="exclamation.gif"/>"
                                         alt="<pg:message code="probe.jsp.datasources.list.misconfigured.alt"/>"/>										
						</td>
						<td width="8%" >
							<pg:cell colName="TABLE_NAME" defaultValue=""/>		
							<pg:message var="messagecode" code="probe.jsp.wrongparams"/>
							${messagecode}
										</td>
							
						
						<td width="8%" >
							<pg:cell colName="TABLE_ID_GENERATOR" defaultValue=""/>		
							<pg:message var="messagecode" code="probe.jsp.wrongparams"/>
							${messagecode}
										</td>
						<td width="8%" >
							<pg:cell colName="TABLE_ID_TYPE" defaultValue=""/>		
							<pg:message var="messagecode" code="probe.jsp.wrongparams"/>
							${messagecode}
										</td>
					</tr>
					</pg:list>
					
					
					<tr><pg:index/></tr>
				</pg:pager> 
	
				
	</table>
</body>
</html>
