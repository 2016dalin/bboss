<%@ page contentType="text/html; charset=GBK" language="java" import="test.*,java.util.*"%>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg"%>

<%
	//����map<String,PO>����
	
%>
<!-- 
	������list��ǩ��ֱ��ִ�����ݿ⣬��ȡ�б���Ϣʵ��
	statement:���ݿ��ѯ���
	dbname:��ѯ����Ӧ���ݿ����ƣ���poolman.xml�ļ��н�������
-->
<html>
<head>
<title>���Ի�ȡmap��Ϣʵ��</title>
</head>
<body>
	<table>
	    <h3>map<String,po>������Ϣ��������</h3>
		<pg:map requestKey="mapbeans">
		
			<tr class="cms_data_tr">
				<td>
					mapkey:<pg:mapkey/>
				</td> 
				<td>
					id:<pg:cell colName="id" />
				</td> 
				<td>
					name:<pg:cell colName="name" />
				</td> 
			</tr>
		</pg:map>
		
		
	</table>
	
	<table>
	    <h3>map<String,String>�ַ�����Ϣ��������</h3>
		<pg:map requestKey="mapstrings">
		
			<tr class="cms_data_tr">
				<td>
					mapkey:<pg:mapkey/>
				</td> 
				<td>
					value:<pg:cell/>
				</td> 
				
				
				
			</tr>
		</pg:map>
		
		
	</table>
	
	<table>
	    <h3>convert��ǩ</h3>
	    <pg:beaninfo requestKey="beandata">
	    <tr><td>ff:<pg:convert convertData="beanmapdata" colName="userName"/></td></tr>
		
		</pg:beaninfo>
		
	</table>
</body>
</html>
