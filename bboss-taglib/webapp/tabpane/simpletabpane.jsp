<%@page contentType="text/html; charset=GBK"%>
<!-- 
	�����ǩ��ʾdemo�����׶�
	
	�����ǩ�����ļ�
 -->
<%@ taglib prefix="tab" uri="/WEB-INF/tabpane-taglib.tld"%>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK" />
		<!-- ����tabpane����������ļ�������js����ʽ -->
		<tab:tabConfig />
		<title></title>

		<script language="javascript">
	
</script>
	</head>
	<body bgcolor="#F7F8FC">

		<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" align="center">
			
			<tr>
				<td colspan="2" align="left">
				<!-- tabpane container����Ϊ�����ǩtabpane����������Ҫע�⣺
					id ���Ե�ֵ���뱣֤Ӧ��ȫ��Ψһ�ԣ�������������cookie��ʱ��
					selectedTabPaneId ������������ȱʡѡ�еĳ����ǩtabpane����ֵΪ��Ӧtabpane��id������ֵ
					enablecookie �����Ƿ���cookie�м�¼�ϴ�ѡ�е�tabpane��壬��¼֮���´��ٽ����ҳ��ʱĬ��ѡ�еĳ����ǩ����
					             cookie�м�¼��tabpane����������¼cookie����ôcontainer ��ǩ��id���Ա��뱣��ȫ��Ψһ�ԣ�����ͻ��໥���š�
					             ȱʡΪfalse
					jsTabListener ָ��tabpane����л�����js�¼������л��������ʱ���ͻ�ִ��ָ����js�¼�����
					skin ���ó����ǩ�����Ƥ����ϵͳȱʡ�ṩ�����¼���Ƥ����ѡ��
						wireframe
						invisible
						default
						bluesky
						grassgreen
						amethyst
						bluegreen
						
						����˼�壬default��Ϊȱʡ���������
				 -->
					<tab:tabContainer id="simple-test-container" enablecookie="false" selectedTabPaneId="rule-config">
					<!--  
						����ĳ����ǩ��
						id ��������ԣ�����ͬһ��������ȫ��Ψһ
						tabTitle  ����ı���
						lazeload ���������������iframe��ǩ����ʶ�Ƿ��ӳٵ���һ�η��ʸ����ʱ�ż���iframe�е�ҳ��
					-->
						<tab:tabPane id="mq-server-config" tabTitle="MQ������" lazeload="true">
						<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" align="center">
							<tr>
								<td colspan="2" align="left">
								<!--  
									iframe��ǩ��
									ͨ��iframe��ǩ������tabpane�����õ�iframe��ܣ��������ĺô��Ϳ���ʵ��iframe��ܵ��ӳټ��ع��ܣ���ν
									�ӳټ��أ����ǵ���һ�ν�����Ӧ��tabpane���ʱ�ż���iframe��ܶ�Ӧ��ҳ��
								-->
								<tab:iframe  id="role-iframe" src="testiframe.jsp"  frameborder="0"
									 width="98%" height="98%"></tab:iframe>
								</td>
							</tr>
						</table>
						</tab:tabPane>

						<tab:tabPane id="mirror-info" tabTitle="������Ϣ" lazeload="true">
						<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" align="center">			
							<tr>
								<td colspan="2" align="left">
											������Ϣ	
								</td>
							</tr>
						</table>
						</tab:tabPane>

						<tab:tabPane id="mem-config" tabTitle="�ڴ�����*" lazeload="true">
							<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" align="center">			
							<tr>
								<td colspan="2" align="left">
											�ڴ�����*
								</td>
							</tr>
						</table>
						</tab:tabPane>
						
						<tab:tabPane id="connector-config" tabTitle="��������*" lazeload="true">
							<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" align="center">			
							<tr>
								<td colspan="2" align="left">
											��������*
								</td>
							</tr>
						</table>
						</tab:tabPane>
						
						<tab:tabPane id="user-config" tabTitle="�û�����" lazeload="true">
							<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" align="center">			
							<tr>
								<td colspan="2" align="left">
											�û�����
								</td>
							</tr>
						</table>
						</tab:tabPane>
						
						<tab:tabPane id="rule-config" tabTitle="·�ɹ�������" lazeload="true">
							<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" align="center">			
							<tr>
								<td colspan="2" align="left">
											·�ɹ�������
								</td>
							</tr>
						</table>
						</tab:tabPane>
											
						<tab:tabPane id="ssl-config" tabTitle="ssl����" lazeload="true">
							<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0" align="center">			
							<tr>
								<td colspan="2" align="left">
											ssl����
								</td>
							</tr>
						</table>
						</tab:tabPane>
					</tab:tabContainer>
				</td>
			</tr>
		</table>
		<table width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
			
			<tr>
				<td colspan="2" align="left">
					<!--  
						�������ѡ�����ӣ�ͨ��selectedTabPaneId����ָ����Ҫ���ӽ���ĳ������
						��ѡ��href��������ָ���Զ�������ӵ�ַurl
					-->
					<tab:tabLink selectedTabPaneId="ssl-config">����ssl����</tab:tabLink>
				</td>
			</tr>
			
			<tr>
				<td align="left">
					<!--  
						�������ѡ�񵼺���������ͨ����������ǩҳ��ʵ�ֳ����������µ���
						tabContainerId ����ָ���ڳ�������tabContainerId�еĳ���֮����е���
					-->
					<tab:prevTabButton tabContainerId="simple-test-container">��һ��</tab:prevTabButton>
					<tab:nextTabButton tabContainerId="simple-test-container">��һ��</tab:nextTabButton>
				</td>
			</tr>
		</table>
	</body>
</html>
