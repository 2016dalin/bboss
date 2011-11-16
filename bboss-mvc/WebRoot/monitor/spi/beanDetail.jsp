
<%@page import="org.frameworkset.spi.assemble.ProviderInfoQueue"%>
<%@page import="java.util.List,org.frameworkset.spi.assemble.Pro"%>
<%@page import="java.lang.reflect.Method"%>
<%@page import="org.frameworkset.web.servlet.context.WebApplicationContext"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%><%@page import="java.net.URLEncoder"%><%
/**
 * 
 * <p>Title: ���������ϸ��Ϣ��ʾҳ��</p>
 *
 * <p>Description: ���������ϸ��Ϣ��ʾҳ��</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: chinacreator</p>
 * @Date 2008-9-19
 * @author gao.tang
 * @version 1.0
 */
 %>
<%@ page language="java" contentType="text/html; charset=GBK" session="false"%>
<%@page import="org.frameworkset.spi.assemble.*,org.frameworkset.spi.BaseApplicationContext"%>
<%@ taglib prefix="tab" uri="/WEB-INF/tabpane-taglib.tld" %>		

<% 
	String rootpath = request.getContextPath();
	String selected = request.getParameter("selected");
	String nodePath = request.getParameter("nodePath");
	BaseApplicationContext context = BaseApplicationContext.getBaseApplicationContext(nodePath);
	
	 
	boolean isWebApplicationContext = false;
	if(context instanceof WebApplicationContext)
	{
		isWebApplicationContext = true;
	}
	
	//String classType = request.getParameter("classType");
	//���������ϸ��Ϣ
	String proParentPath = request.getParameter("proParentPath");
	Pro providerManagerInfo =  null;
	if(proParentPath != null)
		providerManagerInfo = context.getInnerPro(proParentPath,selected) ;
	else
		providerManagerInfo = context.getProBean(selected) ;
	if(providerManagerInfo == null)
	{
		out.print("��ȫ��bean���");
		return ;
	}
	//��������
	String name = proParentPath == null ?selected:proParentPath + "[" + selected + "]";
	String pro_name = providerManagerInfo.getName() == null?"":providerManagerInfo.getName() ;
	
	//�Ƿ��ǵ�ʵ��ģʽ
	boolean isSinglable = providerManagerInfo.isSinglable();
	
	//interceptor-�����������������
	String interceptor = providerManagerInfo.getTransactionInterceptorClass();
	interceptor = interceptor==null?"û������":interceptor;
	
	String beanclazz = providerManagerInfo.getClazz() == null?"":providerManagerInfo.getClazz();
	String factory_bean = providerManagerInfo.getFactory_bean() == null?"":providerManagerInfo.getFactory_bean();
	String factory_clazz = providerManagerInfo.getFactory_class() == null?"":providerManagerInfo.getFactory_class();
	String factory_method = providerManagerInfo.getFactory_method() == null?"":providerManagerInfo.getFactory_method();
	String init_method = providerManagerInfo.getInitMethod() == null?"":providerManagerInfo.getInitMethod();
	String destroy_method = providerManagerInfo.getDestroyMethod() == null?"":providerManagerInfo.getDestroyMethod();
	
	
	
	
%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<title><%=pro_name %></title>
		<link rel="stylesheet" type="text/css" href="<%=rootpath%>/sysmanager/css/treeview.css">
<%@ include file="/include/css.jsp"%>
		<link rel="stylesheet" type="text/css" href="<%=rootpath%>/sysmanager/css/contentpage.css">
		<link rel="stylesheet" type="text/css" href="<%=rootpath%>/sysmanager/css/tab.winclassic.css">
		<tab:tabConfig/>	
	</head>
	
	<body class="contentbodymargin" scroll="no">
	<div style="width:100%;height:100%;overflow:auto">
	<table class="thin" width="100%">
		<tr><td colspan="3" class="headercolor">ҵ���������������Ϣ</td></tr>
		<tr>
		<td class="headercolor" width="20%">������ʶ</td>
		<td class="headercolor" width="30%">��������</td>
		<td class="headercolor" width="50%">������������ļ�</td>
		</tr>
		<tr>
		<td width="20%"><%=!isWebApplicationContext?nodePath:nodePath+"("+context.getConfigfile()+")" %></td>
		<td width="20%"><%=context.getClass().getCanonicalName() %></td>
		<td width="20%"><%=providerManagerInfo.getConfigFile() %></td>
		</tr>
	</table>
	<table class="thin" width="100%">
		<tr><td colspan="3" class="headercolor">ҵ���������������Ϣ</td></tr>
		<tr>
		<td class="headercolor" width="20%">����������</td>
		<td class="headercolor" width="30%">���Զ�Ӧֵ</td>
		
		<td class="headercolor" width="50%">����</td>
		</tr>
		<tr>
		<td width="20%">name</td><td width="30%"><%=pro_name %></td><td width="50%">name-�������/��ʶ</td>
		</tr>
		
		<tr>
		<td width="20%">�������·��</td><td width="20%"><%=name %></td><td width="50%">�������·����Ϣ</td>
		
		</tr>
		
		<tr>
		<td width="20%">singlable</td><td width="30%"><%=isSinglable %></td><td width="50%">singlable-�Ƿ��ǵ�ʵ��ģʽ</td>
		</tr>
		
		<tr>
		<td width="20%">interceptor</td><td width="20%"><%=interceptor %></td><td width="50%">interceptor-�����������</td>
		
		</tr>
		
		<tr>
		<td width="20%">class</td><td width="20%"><%=beanclazz %></td><td width="50%">class-���ʵ����</td>
		
		</tr>
		
		<tr>
		<td width="20%">factory_bean</td><td width="20%"><%
		if(factory_bean != null)
			{
				if(isWebApplicationContext)
				{
					factory_bean = factory_bean == ""?"":"<a href='beanDetail.jsp?isWebApplicationContext=true&selected="+factory_bean+"&nodePath="+nodePath+"' target='_blank'>"+factory_bean+"</a>";
				}
				else
				{
					factory_bean = factory_bean == ""?"":"<a href='beanDetail.jsp?isWebApplicationContext=false&selected="+factory_bean+"&nodePath="+nodePath+"' target='_blank'>"+factory_bean+"</a>";
				}
				out.print(factory_bean); 
								
			}%></td><td width="50%">factory_bean-���ʵ�ֹ���������</td>
		
		</tr>
		<tr>
		<td width="20%">factory_clazz</td><td width="20%"><%=factory_clazz %></td><td width="50%">factory_clazz-���ʵ�ֹ���������</td>
		
		</tr>
		<tr>
		<td width="20%">factory_method</td><td width="20%"><%=factory_method %></td><td width="50%">factory_method-���ʵ�ֹ���������</td>
		
		</tr>
		
		<tr>
		<td width="20%">init_method</td><td width="20%"><%=init_method %></td><td width="50%">init_method-���ʵ��������ʱ������õķ���</td>
		
		</tr>
		
		<tr>
		<td width="20%">destroy_method</td><td width="20%"><%=destroy_method %></td><td width="50%">destroy_method-���ʵ��������ʱ������õķ���</td>
		
		</tr>
	</table>
	<tab:tabContainer id="beanifoDetail" skin="amethyst">
		<tab:tabPane id="reference" tabTitle="����ע��" lazeload="true" >
			<% 
				List referencesList = providerManagerInfo.getReferences();
				
			%>
			<table class="thin" width="100%">
			<tr><td colspan="4">
				
				refid-���õĹ�������id����Ӧmanager�ڵ��id���ԣ���ѡ����<br>
				
				value-��Ӧ�ֶ�fieldname��ֵ<br>
			</td></tr>
			<tr>
				<td class="headercolor">�ֶ�����</td>
				<td class="headercolor">�ֶ����Ƶ�ֵ</td>
				<td class="headercolor">��������</td>
				<td class="headercolor">����</td>
			</tr>
			<% 
			    
				if(referencesList != null && referencesList.size() > 0){
					String refproParentPath = proParentPath;
					if(refproParentPath == null)
					{
						refproParentPath = selected + "^^reference";
					}
					else
					{
						refproParentPath = refproParentPath +"#!#"+selected + "^^reference";
					}
					if(refproParentPath != null && !refproParentPath.equals(""))
		{
			refproParentPath = URLEncoder.encode(refproParentPath);
		}
					for(int i = 0; i < referencesList.size(); i++){
						Pro pro = (Pro)referencesList.get(i);
						
						String key = null;
						String _name = pro.getName();
						
						key = i + "";
	%>
	
		<tr>
	<td><%=key %></td>	
	<td>
	<%
		if(pro.isBean())
		{
			%>
	<a href="beanDetail.jsp?selected=<%=key %>&proParentPath=<%=refproParentPath %>&nodePath=<%=nodePath %>" target="_blank" name="serviceDetail"  ><%
		out.print("name=" + (_name == null?key:_name) + "<br>");		
		out.print("class=" + pro.getClazz() + "<br>");	
		out.print("singlable=" + pro.isSinglable() + "<br>");	
		
		
	 %></a>
	<%
		}
		else if(pro.isRefereced())
		{
		%>
	
	<%
	if(pro.isAttributeRef()) { 
		String refid_ = pro.getRefid();
		Pro tmp_pro = context.getProBean(refid_);
		
	%>
	<a href="<%=tmp_pro !=null && tmp_pro.isBean()?"beanDetail.jsp":"proDetail.jsp"%>?selected=<%=refid_ %>&nodePath=<%=nodePath %>" target="_blank" name="serviceDetail"  ><%
		out.print("name=" + (_name == null?key:_name) + "<br>");
		
		out.print("refid=" + pro.getRefid() + "<br>");
		
		
	 %></a>
	 <%} else if(pro.isServiceRef()) {
	 	String refserviceid = pro.getRefid();
	  %>
	<a href="../managerserviceDetail.jsp?selected=<%=refserviceid %>&nodePath=<%=nodePath %>" target="_blank" name="serviceDetail"  ><%
		out.print("name=" +(_name == null?key:_name) + "<br>");
		
		out.print("refid=" + pro.getRefid() + "<br>");
		
		
	 %></a>
	 <%}%>
	 
	<%}
	else
		{
			%>
	<a href="proDetail.jsp?selected=<%=key %>&proParentPath=<%=refproParentPath %>&nodePath=<%=nodePath %>" target="_blank" name="serviceDetail"  ><%
		out.print("name=" + (_name == null?key:_name) + "<br>");
		if(pro.getValue() != null)
		{
			if(pro.isList())
				out.print("value=[List set]<br>");
			else if(pro.isMap())
				out.print("value=<Map set><br>");
			else if(pro.isSet())
				out.print("value=<Set set><br>");
			else if(pro.isArray())
				out.print("value=<Array set><br>");
			else 
				out.print("value=" + pro.getValue() + "<br>");		
		}
		if(pro.getClazz() != null)
			out.print("class=" + pro.getClazz() + "<br>");		
		if(pro.getRefid() != null)
			out.print("refid=" + pro.getRefid() + "<br>");
		
		
	 %></a>
	<%
		}
	 %>
	</td>
	<td ><%
		if(pro.isBean())
		{
			out.print("���");
		}
		else if(pro.isRefereced())
		{
			out.print("����");
		}
		else
		{
			out.print("ȫ������");
		}
	 %></td>
	 <td ><%
	  out.print(pro.getDescription() == null?"":pro.getDescription());
	  %></td>
	</tr>
	<% 
	   }
	   out.print("<tr><td colspan='4'>�ܹ�������" + referencesList.size() + "������ע�������</td></tr>");	
	  }else{ 
	%>
	<tr><td colspan="4">û������ע�������</td></tr>
	<%} %>
					
					</table>
					
		</tab:tabPane>
		<tab:tabPane id="construction" tabTitle="���캯������" >
				<% 
					Construction construction = providerManagerInfo.getConstruction();
					if(construction == null)
					{
						%>
						
						<table class="thin" width="100%">
				<tr><td colspan="4">
					û�ж��幹�캯��
				</td></tr></table>
						<%
						
					}
					else
					{
						List constructionparams = providerManagerInfo.getConstructorParams();
						
					%>
					<table class="thin" width="100%">
					<tr><td colspan="4">
						fieldname-��Ӧ�Ĺ�������ṩ���е��ֶ����ƣ���ѡ����<br>						
						value-��Ӧ�ֶ�fieldname��ֵ<br>
					</td></tr>
					<tr>
						<td class="headercolor">�ֶ�����</td>
						<td class="headercolor">�ֶ����Ƶ�ֵ</td>
						<td class="headercolor">����</td>
						<td class="headercolor">����</td>
					</tr>
					<% 
					    
						if(constructionparams != null && constructionparams.size() > 0){
							String constructproParentPath = proParentPath;
							if(constructproParentPath == null)
							{
								constructproParentPath = selected + "^^construction";
							}
							else
							{
								constructproParentPath = constructproParentPath +"#!#"+selected + "^^construction";
							}
							if(constructproParentPath != null && !constructproParentPath.equals(""))
		{
			constructproParentPath = URLEncoder.encode(constructproParentPath);
		}
							for(int i = 0; i < constructionparams.size(); i++){
								Pro pro = (Pro)constructionparams.get(i);
								String key = null;
								String _name = pro.getName();
								key = i + "";
						
			
	%>
	
		<tr>
	<td><%=key %></td>	
	<td>
	<%
		if(pro.isBean())
		{
			%>
	<a href="beanDetail.jsp?selected=<%=key %>&proParentPath=<%=constructproParentPath %>&nodePath=<%=nodePath %>" target="_blank" name="serviceDetail"  ><%
		out.print("name=" + (_name == null?key:_name) + "<br>");		
		out.print("class=" + pro.getClazz() + "<br>");	
		out.print("singlable=" + pro.isSinglable() + "<br>");	
		
		
	 %></a>
	<%
		}
		else if(pro.isRefereced())
		{
		%>
	
	<%
	if(pro.isAttributeRef()) { 
		String refid_ = pro.getRefid();
		Pro tmp_pro = context.getProBean(refid_);
		
	%>
	<a href="<%=tmp_pro !=null && tmp_pro.isBean()?"beanDetail.jsp":"proDetail.jsp"%>?selected=<%=refid_ %>&nodePath=<%=nodePath %>" target="_blank" name="serviceDetail"  ><%
		out.print("name=" + (_name == null?key:_name) + "<br>");
		
		out.print("refid=" + pro.getRefid() + "<br>");
		//out.print("�������ͣ�" );out.print("���������������");
		
	 %></a>
	 <%} else if(pro.isServiceRef()) {
	 	String refserviceid = pro.getRefid();
	  %>
	<a href="../managerserviceDetail.jsp?selected=<%=refserviceid %>&nodePath=<%=nodePath %>" target="_blank" name="serviceDetail"  ><%
		out.print("name=" +(_name == null?key:_name) + "<br>");
		
		out.print("refid=" + pro.getRefid() + "<br>");
		//out.print("�������ͣ�" );out.print("�����������");
		
	 %></a>
	 <%}%>
	 
	<%}
	else
		{
			String url = "proDetail.jsp?selected="+key +"&proParentPath="+constructproParentPath +"&nodePath="+nodePath;
			if(beanclazz != null && beanclazz.equals("com.frameworkset.common.poolman.ConfigSQLExecutor") && pro != null)
			{
				url = "sqlconfigfileDetail.jsp?selected=sql:"+ pro.getValue() +"&classType=sqlapplicationmodule&nodePath="+nodePath;
			}
			%>
	<a href="<%=url%>" target="_blank" name="serviceDetail"  ><%
		out.print("name=" + (_name == null?key:_name) + "<br>");
		if(pro.getValue() != null)
		{
			if(pro.isList())
				out.print("value=[List set]<br>");
			else if(pro.isMap())
				out.print("value=[Map set]<br>");
			else if(pro.isSet())
				out.print("value=[Set set]<br>");
			else if(pro.isArray())
				out.print("value=[Array set]<br>");
			else 
				out.print("value=" + pro.getValue() + "<br>");		
		}
		if(pro.getClazz() != null)
			out.print("class=" + pro.getClazz() + "<br>");		
		if(pro.getRefid() != null)
			out.print("refid=" + pro.getRefid() + "<br>");
		//out.print("�������ͣ�" );out.print("���������������");
		
	 %></a>
	<%
		}
	 %>
	</td>
	<td ><%
		if(pro.isBean())
		{
			out.print("���");
		}
		else if(pro.isRefereced())
		{
			out.print("����");
		}
		else
		{
			out.print("ȫ������");
		}
	 %></td>
	 <td ><%
	 out.print(pro.getDescription() == null?"":pro.getDescription());
	  %></td>
	</tr>
	<% 
	   }
	   out.print("<tr><td colspan='4'>�ܹ�������" + constructionparams.size() + "�����캯��������</td></tr>");	
	  }else{ 
	%>
	<tr><td colspan="2">û�й��캯��������</td></tr>
	<%} %>
					
					</table>
					<%} %>
			</tab:tabPane>
		
		
		
		
		
		
		
		<tab:tabPane id="transactions" tabTitle="���񷽷�" lazeload="true" >
		<% 
			List transactionMethodsList = providerManagerInfo.getTransactionMethods();
		%>
			<table class="thin" width="100%">
			<tr>
			<td colspan="4">
				������Ҫ����������Ƶķ���<br>
				����˵����<br>
				name-�������ƣ�������һ��������ʽ��������ʽ���﷨��ο�jakarta-oro������ĵ������ʹ��<br>
				������ʽ�����ʱ���򷽷��������ķ��������������ԣ����ǻع��쳣��Ч��<br>
				pattern-�������Ƶ�������ʽƥ��ģʽ��ģʽƥ���˳��������λ�õ�Ӱ�죬��������ں�������м䣬<br>
						��ô����ִ��֮ǰ�ķ���ƥ�䣬���ƥ�����˾Ͳ���Ը�ģʽ��������ƥ���ˣ�����ִ��ƥ�������<br>
						���ƥ�����ض��ķ������ƣ���ô�������������Ҫ����������Ƶķ���<br>
						���磺ģʽtestInt.*ƥ��ӿ�����testInt��ͷ���κη���<br>
				txtype-��Ҫ���Ƶ��������ͣ�ȡֵ��Χ��<br>
				NEW_TRANSACTION��<br>
				REQUIRED_TRANSACTION��<br>
				MAYBE_TRANSACTION��<br>
				NO_TRANSACTION��<br>
				RW_TRANSACTION
			</td>
			</tr>
			<%if(transactionMethodsList == null || transactionMethodsList.size() == 0){ %>
			<tr><td>û�ж�����Ҫ����������Ƶķ�����</td></tr>
			<%} %>
			</table>
			
			<% 
				
				for(int i = 0; transactionMethodsList != null && i < transactionMethodsList.size(); i++){
					SynchronizedMethod synchronizedMethod = (SynchronizedMethod)transactionMethodsList.get(i);
					//�������ƣ�name��pattern����ͬʱ����
					String methodName = synchronizedMethod.getMethodName();
					//pattern-ƥ�䷽�����Ƶ�������ʽ
					String pattern = synchronizedMethod.getPattern();
					//txtype-��Ҫ���Ƶ��������ͣ�ȡֵ��Χ��NEW_TRANSACTION��REQUIRED_TRANSACTION��MAYBE_TRANSACTION��NO_TRANSACTION
					String txtype = synchronizedMethod.getTxtype() == null?"": synchronizedMethod.getTxtype().toString();
					//�ع��쳣
					List rollbackExceptionsList = synchronizedMethod.getRollbackExceptions();
					//�����б�
					List paramList = synchronizedMethod.getParams();
			%>
			<table class="thin" width="100%">
			<tr>
			<td class="bginputcolor" height="30" colspan="4"><strong>&lt;method&gt;
			<%
				if(pattern != null){
					out.print(pattern);
				}else{
					out.print(methodName);
				}
				out.print("(");
				for(int txt = 0; paramList != null && txt < paramList.size(); txt ++){
					Pro paramTxt = (Pro)paramList.get(txt);
					if(txt < paramList.size() - 1){
						out.print(paramTxt.getClazz() + ",");
					}else{
						out.print(paramTxt.getClazz());
					}
				}
				out.print(")");
				if(pattern != null && paramList != null){
					out.print("<br>������ʽƥ��ķ�������Ҫ���ò�����");
				} 
			%>
			</strong></td>
			</tr>
			<tr>
			<td width="200">��������</td><td colspan="3"><%=txtype %></td>
			</tr>
			<tr>
			<td width="200" rowspan="<%=rollbackExceptionsList.size()+1 %>">����ع��쳣</td>
			<td class="headercolor">�쳣��</td><td class="headercolor" >�쳣��ⷶΧ</td><td class="headercolor" >����</td>
			</tr>
			<%if(rollbackExceptionsList != null && rollbackExceptionsList.size() > 0) {
				for(int ep = 0; ep < rollbackExceptionsList.size(); ep++){
					RollbackException rollbackException = (RollbackException)rollbackExceptionsList.get(ep);
					//�쳣��
					String epClass = rollbackException.getExceptionName();
					//INSTANCEOF = 1; IMPLEMENTS = 0;
					String type = "INSTANCEOF";
					if(rollbackException.getExceptionType() == 0){
						type = "IMPLEMENTS";
					}
			%>
			<tr>
			<td ><%=epClass %></td><td ><%=type %></td>
			<td>
			<%
				if(type.equals("IMPLEMENTS")){
					out.print("IMPLEMENTSֻ����쳣�౾�������쳣�������");
				}
				else if(type.equals("INSTANCEOF")){
					out.print("INSTANCEOF����쳣�౾������������");
				} 
				
			%>
			</td>
			</tr>
			<%}
			}else{ %>
			<tr>
			<td colspan="4" align="center">û�����ûع��쳣</td>
			</tr>
			<%} %>
			</table>
			<br>
			<%
				}
			%>
			
		</tab:tabPane>
		
		
		
		<tab:tabPane id="interceptor" tabTitle="������" lazeload="true" >
			<% 
				List interceptorsList = providerManagerInfo.getInterceptors();
				
			%>
			<table class="thin" width="100%">
			<tr><td>
				class-��������ʵ���࣬���е�������������ʵ��<br>
		      	com.frameworkset.proxy.Interceptor�ӿ�<br>
		      	Ŀǰϵͳ���ṩ������ȱʡ��������<br>
		      	���ݿ����������������org.frameworkset.spi.<br>
		      	interceptor.TransactionInterceptor��,֧�ֶ�����ʽ����Ĺ���<br>
			</td></tr>
			<tr><td class="headercolor">��������</td></tr>
			<%
				if(interceptorsList != null && interceptorsList.size() > 0){
					for(int ip = 0; ip < interceptorsList.size(); ip ++){
						InterceptorInfo interceptorInfo = (InterceptorInfo)interceptorsList.get(ip);
						String clazz = interceptorInfo.getClazz();
			%>
			<tr><td><%=clazz %></td></tr>		
			<%
					}
				}else{
			%>
			<tr><td>û��������������</td></tr>
			<%	
				} 
			%>
			
			</table>
		</tab:tabPane>
		
		<tab:tabPane id="mvcpaths" tabTitle="mvc·��ӳ��" lazeload="true" >
			<% 
				Map mvcpaths = providerManagerInfo.getMvcpaths();
				
			%>
			<table class="thin" width="100%">
			
			<tr><td class="headercolor">ӳ������</td><td class="headercolor">ӳ��·��</td></tr>
			<%
				if(mvcpaths != null && mvcpaths.size() > 0)
				{
					Iterator its = mvcpaths.keySet().iterator();
					while(its.hasNext())
					{
						String namepath = (String)its.next();
			%>
			<tr><td><%=namepath %></td><td><%=mvcpaths.get(namepath) %></td></tr>		
			<%
					}
				}else{
			%>
			<tr><td></td></tr>
			<%	
				} 
			%>
			
			</table>
		</tab:tabPane>
		
		<tab:tabPane id="extendattrs" tabTitle="��չ����" lazeload="true" >
			<% 
				Map extendattrs = providerManagerInfo.getExtendsAttributes();
				
			%>
			<table class="thin" width="100%">
			
			<tr><td class="headercolor">��չ��������</td><td class="headercolor">��չ����ֵ</td></tr>
			<%
				if(extendattrs != null && extendattrs.size() > 0)
				{
					Iterator its = extendattrs.keySet().iterator();
					while(its.hasNext())
					{
						String namepath = (String)its.next();
			%>
			<tr><td><%=namepath %></td><td><%=extendattrs.get(namepath) %></td></tr>		
			<%
					}
				}else{
			%>
			<tr><td></td></tr>
			<%	
				} 
			%>
			
			</table>
		</tab:tabPane>
		
		<tab:tabPane id="webservices" tabTitle="webservice����" lazeload="true" >
			<% 
				Map webservices = providerManagerInfo.getWSAttributes();
				
			%>
			<table class="thin" width="100%">
			
			<tr><td class="headercolor">webservice��������</td><td class="headercolor">webservice����ֵ</td></tr>
			<%
				if(webservices != null && webservices.size() > 0)
				{
					Iterator its = webservices.keySet().iterator();
					while(its.hasNext())
					{
						String namepath = (String)its.next();
			%>
			<tr><td><%=namepath %></td><td><%=webservices.get(namepath) %></td></tr>		
			<%
					}
				}else{
			%>
			<tr><td></td></tr>
			<%	
				} 
			%>
			
			</table>
		</tab:tabPane>
		
		<tab:tabPane id="rmiattrs" tabTitle="rmi��������" >
		<% 
				Map rmiattrs = providerManagerInfo.getRMIAttributes();
				
			%>
			<table class="thin" width="100%">
			
			<tr><td class="headercolor">rmi������������</td><td class="headercolor">rmi��������ֵ</td></tr>
			<%
				if(rmiattrs != null && rmiattrs.size() > 0)
				{
					Iterator its = rmiattrs.keySet().iterator();
					while(its.hasNext())
					{
						String namepath = (String)its.next();
			%>
			<tr><td><%=namepath %></td><td><%=rmiattrs.get(namepath) %></td></tr>		
			<%
					}
				}else{
			%>
			<tr><td></td></tr>
			<%	
				} 
			%>
			
			</table>
		</tab:tabPane>
		
		
	</tab:tabContainer>
	
	
		
	</div>
	</body>
</html>