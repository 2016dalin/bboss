---------------------------------
bboss-tablib�������̣�
---------------------------------
bboss-taglib->common_old_dbcp [frameworkset-pool.jar]
bboss-taglib->bbossaop [bboss-aop.jar]
bboss-taglib->common_old_util [frameworkset-util.jar]

bboss-taglib<-cms_baseline [frameworkset.jar]
bboss-taglib<-bbossaop [frameworkset.jar]
bboss-taglib<-cas server [frameworkset.jar]
bboss-taglib<-portal [frameworkset.jar]
bboss-taglib<-bboss-ws [frameworkset.jar]

#######update function list since bbossgroups-3.4 begin###########
------2011-11-20------------
o ����jquery-1.4.2.min.js��load����֧����������Ĵ���
o ����pager.js��loadPageContent����֧����������Ĵ���
o ��ҳ��������֧��map��bean�а������ݲ������ݣ�֧������������ݣ�ͨ�����±�ǩʵ�֣�
params��ǩ��<pg:params name="userName" />
beanparams��ǩ��<pg:beanparams name="user"/>

���Ҫʹ����������ǩ����Ҫ�������³���
/WEB-INF/lib/frameworkset.jar
/include/jquery-1.4.2.min.js
/include/pager.js


ͬʱ��Ҫ���pager-taglib.tld���Ƿ�����һ��params��ǩ��beanparams��ǩ��

<!--
		����˵����Ϊ��ҳ�б��ǩ���Զ����õĳ�������Ӳ�������
	-->
	<tag>
		<name>params</name>
		<tagclass>com.frameworkset.common.tag.pager.tags.ParamsTag</tagclass>
		<bodycontent>empty</bodycontent>
		<attribute>
			<name>id</name>
			<rtexprvalue>true</rtexprvalue>
		</attribute>
		<attribute>
			<name>name</name>
			<required>true</required>
			<rtexprvalue>true</rtexprvalue>
		</attribute>
		
		<attribute>
			<name>encode</name>
			<rtexprvalue>true</rtexprvalue>
		</attribute>
	</tag>
	
	
	<!--
		����˵����Ϊ��ҳ�б��ǩ���Զ����õĳ�������Ӳ�������������java�������Ի���Map�м�ֵ��
	-->
	<tag>
		<name>beanparams</name>
		<tagclass>com.frameworkset.common.tag.pager.tags.BeanParamsTag</tagclass>
		<bodycontent>empty</bodycontent>
		<attribute>
			<name>id</name>
			<rtexprvalue>true</rtexprvalue>
		</attribute>
		<attribute>
			<name>name</name>
			<required>true</required>
			<rtexprvalue>true</rtexprvalue>
		</attribute>
		
		<attribute>
			<name>encode</name>
			<rtexprvalue>true</rtexprvalue>
		</attribute>
	</tag>
------2011-11-18------------
o ����params,beanparams��ǩ
params:Ϊ��ҳ�б��ǩ���Զ����õĳ�������Ӳ�������
beanparams:Ϊ��ҳ�б��ǩ���Զ����õĳ�������Ӳ�������������java�������Ի���Map�м�ֵ��
bean���Կ���ָ��com.frameworkset.common.tag.pager.IgnoreParamע�⣬beanparams��ǩ����Դ���
IgnoreParamע�������

------2011-11-2------------
o �޸�covert��ǩ��ָ���쳣


#######update function list since bbossgroups-3.3 begin###########
------2011-10-14------------
o ����convert��ǩ����ֵȫ��ת��Ϊ�ַ�����ֻ����Map�����д�ŵ����ݵ�key���ַ���
o ����cell��ǩ�����ӱ�ǩ���ܣ��Ľ�ʹ�÷�����Ƶķ���
#######update function list since bbossgroups-3.2 begin###########
------2011-08-06------------
o ��ǩ��cell��ǩ���߼���ǩ��expression��expressionValue���ʽ������rowcount������֧��
------2011-07-31------------
o ����map��mapkey������ǩ������ѭ������չʾmap�е�value����ֵ����value�����е�����ֵ�Լ�mapkeyֵ
ʹ�÷������£�
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
------2011-07-19------------
o cell��ǩ�ṩactual���ԣ�����ֱ������������趨��ֵ��ֵ����Ϊel���ʽ��ֵ
------2011-07-13------------
o �޸�empty��notempty�����߼���ǩ���Ӷ�Collection��Map�����Ϊempty�ж�֧��
o �޸�rowcount��ǩ��ȥ������Ŀո�
o ���Ʊ�ǩ�����ܲ���
������Ӧ��ָʾ��ͷ����ʶ����ͽ���
����ļ�
/bboss-mvc/WebRoot/include/pager.css
WebRoot\WEB-INF\lib\frameworkset.jar
�����ʹ�÷����ο������ڻ�����ܿ���Լ��.doc���е��½�3.5	��ҳ�ֶ������趨

#######update function list since bbossgroups-3.1 begin###########
------2011-06-04------------
o �߼���ǩ���Զ�����list��beaninfo��ǩʹ��,�����������ԣ�
		<attribute>
			<name>requestKey</name>
			<rtexprvalue>true</rtexprvalue>
		</attribute>
		<attribute>
			<name>sessionKey</name>
			<rtexprvalue>true</rtexprvalue>
		</attribute>
		<attribute>
			<name>pageContextKey</name>
			<rtexprvalue>true</rtexprvalue>
		</attribute>
		<attribute>
			<name>parameter</name>
			<rtexprvalue>true</rtexprvalue>
		</attribute>
		<attribute>
			<name>actual</name>
			<rtexprvalue>true</rtexprvalue>
		</attribute>
ͨ���������ԣ����Է�����ƶ��߼���ǩ������ֵ��
requestKey��ָ����request��attribute�����л�ȡʵ��ֵ��
sessionKey��ָ����session��attribute�����л�ȡʵ��ֵ��
pageContextKey��ָ����pageContext��attribute�����л�ȡʵ��ֵ��
parameter:ָ����request��parameter�л�ȡʵ��ֵ
actual:ֱ��ָ��ʵ��ֵ�������Ǿ����ֵ��Ҳ������һ��el����
�������Ի����Ժ�property���Խ��������ȡֵ�����е�����ֵ
------2011-06-04------------
o request��session��ǩ��������dateformat��ʽ����

------2011-06-03------------
o �޸�config��ǩenablecontextmenu���Բ�������������©��
------2011-05-26------------
o ����empty��notempty�����߼���ǩʹ�÷�����null��notnullһ��
empty�ж�ָ�����ֶε�ֵ�Ƿ���null�����߿մ������������������ִ�б�ǩ���е��߼�
notempty�ж�ָ�����ֶε�ֵ�Ȳ���nullҲ���ǿմ�����ִ�б�ǩ�������
------2011-05-24------------
o �޸�null��notnull��ǩ������ȷ����������
o �޸�detail��ǩ����ʾ��Ϣ���Ǻ���ȷ�����⣻

#######update function list since bbossgroups-3.1 end###########


#######update function list since bbossgroups-3.0 begin###########
to do list��
�߼���ǩͨ�û�����������list��ǩ��ʹ��
���б�ǩ�����С����������
------2011-04-25------------
o �޸��ַ��������������jquery����ajax��������ʱ����ҳ��ѯ�������������⣬�Ľ��ַ���������������
------2011-04-20------------
o ����convert��ǩ��֧���ֵ�����ֵ�����Ƶ�ת��
���е�datasΪһ��map����ӳ��ֵ��name��Ӧ��key��convert��ǩͨ��name��ȡ����Ӧ������ֵ
Ȼ����ʾ��ҳ���ϣ������Ӧ��ֵû����ô���defaultValue��Ӧ��ֵ�����û������defaultValue
��ôֱ�����name��
<pg:convert convertData="datas" colName="name" defaultValue=""/>
pager-taglib.tld
frameworkset.jar
------2011-04-18------------
o �����ҳ��ͨ��ajax��ʽ���ض����ҳҳ��ʱ����ת���ܲ�������ʹ�õ�����
pager.js
frameworkset.jar
------2011-04-14------------
o mvc�д��ݸ���ҳ��ǩ��·���޸�Ϊ�������ĵľ��Ե�ַ��������ҳ�����Ե�ַ�ͷ�ҳ��Ӧ��ҳ������·����һ��ʱ�����ܷ�ҳ
------2011-03-24------------
o �޸�mvcʵ�ַ�ҳ����ʱ��ͨ��handleMappingע��ָ����url·���޷����з�ҳ��bug���޸ĵĳ������£�
�޸�֮ǰ
com.frameworkset.common.tag.pager.tags.PagerContext
   public static String getPathwithinHandlerMapping(HttpServletRequest request)
	{
		return (String) request
		.getAttribute("org.frameworkset.web.servlet.HandlerMapping.pathWithinHandlerMapping");
		
		
			 
	}  
	�޸�֮��
  com.frameworkset.common.tag.pager.tags.PagerContext
   public static String getPathwithinHandlerMapping(HttpServletRequest request)
	{
		 String urlpath = (String) request
		.getAttribute("org.frameworkset.web.servlet.HandlerMapping.pathWithinHandlerMapping");
		 if(urlpath != null && urlpath.startsWith("/") )
		 {
			 urlpath = request.getContextPath() + urlpath;
		 }
		 return urlpath;
			 
	}  
------2011-03-20------------
o �Ľ��Ҽ��˵����ܣ������Ҽ��˵����ܣ��漰�Ĺ����У�ʹ���Ҽ��˵�������ǩ��ʹ���Ҽ��˵����б���ҳ��ǩ���Լ�����������ص�ҳ��
�޸ĵĳ����У�
1.bboss-tablib/src/com/frameworkset/common/tag/contextmenu/ContextMenuTag.java
2./bboss-tablib/webapp/WEB-INF/templates/contextmenu/popscript.vm
3.jar����frameworkset.jar
------2011-03-06------------
o �޸�����ǩ��ѡ�����¼�firefox����������
o �޸�����ǩĬ��ѡ�нڵ��������õ���¼�ʱBooleanֵ��Stringת���쳣����

------2011-03-06------------
 
o list ��cell��ǩ��϶�String�����֡������б����ݽ������

#######update function list since bbossgroups-3.0 end###########
------------------------------------------------------------------
update function list in bbossgroups-2.0-rc2 since bbossgroups-2.0-rc1
------------------------------------------------------------------
2011-02-12
------------------------------------------------------------------

o ��ҳ��ǩ��mvc��ܽ�ϣ�ͨ��mvc�Ŀ���������ֱ�ӿ��Ը���ҳ��ǩ�ṩ����
o ����notcontain��notmatch����������ʽ�߼��жϱ�ǩ����contain��match�����߼��жϱ�ǩ�Ĺ����෴

------------------------------------------------------------------
2010-09-03
------------------------------------------------------------------
��com.frameworkset.common.tag.pager.ObjectDataInfoImpl����û��ʵ�����·�����
public long getItemCount() 
public int getDataSize()
���µ���ʱ�׳��쳣��

ʵ���������������󣬹���������
------------------------------------------------------------------
2010-09-03
------------------------------------------------------------------
o ��ҳ��ǩ��������ҳ��size����,���������������޸�
�޸ĳ���
frameworkset.jar
/bboss-tablib/src/com/frameworkset/common/tag/pager/tags/IndexTag.java
/bboss-tablib/src/com/frameworkset/common/tag/pager/tags/PagerContext.java
/bboss-tablib/src/com/frameworkset/common/tag/pager/tags/PagerDataSet.java
/bboss-tablib/src/com/frameworkset/common/tag/pager/tags/PagerTag.java
/bboss-tablib/webapp/include/pager_custom.js
/bboss-tablib/webapp/include/pager.js

��ǩ�����ļ�/bboss-tablib/webapp/WEB-INF/pager-taglib.tldΪindex��ǩ���sizescope����
<!-- 
		����ҳ����ʾ��¼��Χ��Ĭ��Ϊ
		"5","10","20","30","40","50","60","70","80","90","100"
		�û������Զ��������Χ���Զ��ŷָ�����
		�����pager��ǩ��list��ǩ��ָ����maxPageItems���Զ�Ӧ��ҳ���¼��������sizescope��Χ�У���ô
		����maxPageItems��Ϊ��һ��ѡ����뵽sizescope��
		 -->
		<attribute>
			<name>sizescope</name>
			<rtexprvalue>true</rtexprvalue>
		</attribute>


ʹ��jquery��ص���ʽ����������ƶ�������ʽ��

table_gray�����ʽ
.table_gray{
border:1px solid #4eadf7;
border-collapse:collapse;
}
.table_gray td{
border:1px solid #4eadf7;
border-collapse:collapse;
text-align: center;
}
.table_gray thead td{
background-color:#7cc5fa;
font-size:12px;
font-weight:bold;
}
.table_gray .down{
background:url(images/th_down.png) right no-repeat;
padding-right:6px;
}

.table_gray .updown{
background:url(images/th_updown.png) right no-repeat;
padding-right:6px;
}

//��������ʽ
.space_color{
background-color:#d7edfd;
}

//����ƶ���������ʽ
tr.highlight {
	background: #C8F3FB;
}

//��ѡ�е���ʽ
tr.selected {
	background: #FF8C05;
	color: #fff;


------------------------------------------------------------------
2010-09-01
------------------------------------------------------------------
o �޸Ĺ�����com.frameworkset.common.filter.CharacterEncodingHttpServletRequestWrapper������ض��������������
���磺
��������http://localhost:8080/test/test.jsp?key=���
��ִ���������
String values[] = request.getParameterValues("key");//�õ���valuesΪ�������飺{"���"}
Ȼ����ִ���������
String values = request.getParameter("key");//�õ���ֵΪ��������


------------------------------------------------------------------
update function list in bbossgroups-2.0-rc since bbossgroups-1.0
------------------------------------------------------------------
2010-07-31
------------------------------------------------------------------
o ���Ӳ˵�����õ���ʾ����
------------------------------------------------------------------
update function list in bbossgroups-1.0 
------------------------------------------------------------------
o pager��ǩ���� ����csslink��ǩ��jscript��ǩ��config��ǩ
 ͨ��csslink��ǩ�����css�ļ��ܹ��Զ�ȥ�ع��ܣ�Ҳ���Ǳ�����ҳ�����ظ�����ͬһ��css�ļ�
ͨ��jscript��ǩ�����js�ļ��ܹ��Զ�ȥ�ع��� ��Ҳ���Ǳ�����ҳ�����ظ�����ͬһ��js�ļ�
ͨ��congfig��ǩĬ�ϵ���ҳ��js��css�ļ����ܹ��Զ�ȥ�ع��� ��Ҳ���Ǳ�����ҳ�����ظ���������js��css�ļ�����

	/include/pager.js
	/include/jquery-1.4.2.min.js
	/include/treeview.css
	/include/themes/default/easyui.css
	/include/themes/icon.css
	/include/jquery.easyui.min.js
	
ʹ�÷�����

<pg:csslink src="contextpath/ccc.css"/>
<pg:jscript src="contextpath/ccc.js"/>

<pg:config/>
firefox�����������޸ģ�
o �޸�tabs.js�ļ����޸�tabpane�ڻ���µ����⣬����²�֧��ֱ����id.attrname�ķ�ʽ��ȡ����ֵ
o tree��ǩ��
/bboss-tablib/src/com/frameworkset/common/tag/tree/impl/NodeHelper.java
o �Ҽ��˵�
����Ҽ��˵���firefox��������޷�������ʾ����
����޷�չʾ�༶�Ҽ��˵�������
�Ľ��Ҽ��˵���ӽӿ����£�
			Menu menu = new Menu();
			menu.addContextMenuItem(Menu.MENU_OPEN);
			menu.addContextMenuItem(Menu.MENU_EXPAND);
			menu.addContextMenuItem("���","javascript:edit('���')",Menu.icon_edit);
			
			menu.addSeperate();
			menu.addContextMenuItem("�༭�༭�༭�༭","javascript:edit('�༭')",Menu.icon_add);
			
			Menu.ContextMenuItem sitemenuitem2 = menu.addContextMenuItem("sitemenuitem2","javascript:edit('sitemenuitem2')",Menu.icon_ok);
			sitemenuitem2.addSubContextMenuItem("��menusubmenuitem_","javascript:edit('��menusubmenuitem_')",Menu.icon_ok);	
			sitemenuitem2.addSubContextMenuItem("��cut","javascript:edit('��cut')",Menu.icon_cut);				
			sitemenuitem2.addSubContextMenuItem("��icon_back","javascript:edit('��icon_back')",Menu.icon_back);
			sitemenuitem2.addSubContextMenuItem("��icon_cancel","javascript:edit('��icon_cancel')",Menu.icon_cancel);
			sitemenuitem2.addSubContextMenuItem("��icon_help","javascript:edit('��icon_help')",Menu.icon_help);
			sitemenuitem2.addSubContextMenuItem("��icon_no","javascript:edit('��icon_no')",Menu.icon_no);
			sitemenuitem2.addSubContextMenuItem("��icon_print","javascript:edit('��icon_print')",Menu.icon_print);
			sitemenuitem2.addSubContextMenuItem("��icon_redo","javascript:edit('��icon_redo')",Menu.icon_redo);
			sitemenuitem2.addSubContextMenuItem("��icon_reload","javascript:edit('icon_reload')",Menu.icon_reload);
			sitemenuitem2.addSubContextMenuItem("icon_remove","javascript:edit('icon_remove')",Menu.icon_remove);
			sitemenuitem2.addSubContextMenuItem("icon_save","javascript:edit('icon_save')",Menu.icon_save);
			sitemenuitem2.addSubContextMenuItem("icon_search","javascript:edit('icon_search')",Menu.icon_search);
			sitemenuitem2.addSubContextMenuItem("icon_undo","javascript:edit('icon_undo')",Menu.icon_undo);
			ContextMenuItem third = sitemenuitem2.addSubContextMenuItem("�ڶ���","javascript:edit('icon_undo')",Menu.icon_undo);
			third.addSubContextMenuItem("����", "javascript:edit('icon_undo')",Menu.icon_undo);
----------------------------------------
1.0.2 - 2010-4-22
----------------------------------------
o cell��ǩ����
֧��long�͵��ֶ�ֱ��ת��Ϊjava.util.Date����
----------------------------------------
1.0.2 - 2010-4-22
----------------------------------------
o bug�޸�
bug 1 jquery�����ַ�ת��
/bboss-tablib/webapp/include/pager.js
/bboss-tablib/webapp/WEB-INF/templates/tree.vm

var ret = object.replace(/:/g,"\\:");
    	ret = ret.replace(/\./g,"\\.");
    	
bug 2 ����jquery�󣬵�����ֻ��һ���ڵ㣬���Ҳ�չʾ���ڵ�ʱ�����ɵ����ű��ж�������һ��</div>
----------------------------------------
1.0.2 - 2010-4-15
----------------------------------------

o �ű����Ƶ���
/cms_baseline/sourcecode/cms/WebRoot/WEB-INF/templates/contextmenu/popmenu.vm
/bboss-tablib/src/com/frameworkset/common/tag/contextmenu/ContextMenuTag.java
/bboss-tablib/src/com/frameworkset/common/tag/pager/config/PageConfig.java
/bboss-tablib/src/com/frameworkset/common/tag/pager/tags/IndexTag.java
/bboss-tablib/src/com/frameworkset/common/tag/tree/impl/NodeHelper.java
/bboss-tablib/src/com/frameworkset/common/tag/tree/impl/TreeTag.java
----------------------------------------
1.0.2 - 2010-4-14
----------------------------------------
o ��ǩ��jquery���죬�Ҽ��˵��޸ģ�creatorcim��δͬ��
/bboss-tablib/src/com/frameworkset/common/tag/pager/config/PageConfig.java
/cms_baseline/sourcecode/cms/WebRoot/include/pager.js
/cms_baseline/sourcecode/cms/WebRoot/WEB-INF/templates/contextmenu/parentpopmenu.vm
/cms_baseline/sourcecode/cms/WebRoot/WEB-INF/templates/contextmenu/popmenu.vm
/cms_baseline/sourcecode/cms/WebRoot/WEB-INF/templates/contextmenu/popscript.vm
/cms_baseline/sourcecode/cms/WebRoot/WEB-INF/templates/tree.vm
/cms_baseline/sourcecode/cms/WebRoot/WEB-INF/pager-taglib.tld
/bboss-tablib/src/com/frameworkset/common/tag/pager/tags/IndexTag.java
----------------------------------------
1.0.2 - 2010-3-11
----------------------------------------
o ��ҳ��ת��������bug�޸���sorkeyΪnullʱ����������¸�ʽ�Ĳ�����sortkey=null,�����жϺ󲻼ӾͿ���
/bboss-tablib/src/com/frameworkset/common/tag/pager/tags/IndexTag.java
/bboss-tablib/webapp/include/pager.js


o �޸�����ǩ�ݹ�ѡ��bug
���ϵݹ�
���µݹ�
���ϲ��ֵݹ�
/WEB-INF/templates/tree.vm
o pager��ǩjquery����

��������ȫ����Ҫ����Ϊjqueryģʽ
title�������Ӹ���Ϊjqueryģʽ
����jquery��ѯģʽ��ǩ�飨δ��ɣ�

��ص��ļ���
/bboss-tablib/webapp/WEB-INF/pager-taglib.tld
/bboss-tablib/webapp/include/pager.js
/bboss-tablib/src/com/frameworkset/common/tag/pager/tags/IndexTag.java
/bboss-tablib/src/com/frameworkset/common/tag/pager/tags/PagerDataSet.java
/bboss-tablib/src/com/frameworkset/common/tag/pager/tags/PagerContext.java
/bboss-tablib/src/com/frameworkset/common/tag/pager/tags/PagerTag.java
/bboss-tablib/src/com/frameworkset/common/tag/pager/tags/TitleTag.java
/bboss-tablib/src/com/frameworkset/common/tag/pager/config/PageConfig.java



o ����ǩprototype.jsǨ�Ƶ�jquery
�޸��ļ�/creatorcim/cimconsole/WEB-INF/templates/contextmenu/popmenu.vm����prototype.js����jquery
����<script language="javascript" src="${contextpath}/include/jquery-1.4.2.min.js"></script>

�޸�/WEB-INF/templates/tree.vmģ���ļ������е�prototye�����滻Ϊjquery����

���������ǩconfig
com.frameworkset.common.tag.pager.config.PageConfig

/bboss-tablib/webapp/WEB-INF/pager-taglib.tld�������±�ǩ����
<tag>
		<name>config</name>
		<tagclass>com.frameworkset.common.tag.pager.config.PageConfig</tagclass>
		<bodycontent>JSP</bodycontent>
		
	</tag>
ҳ�����÷�����<pg:config/>����ǩִ�к�����������ݣ�
<script src="/cimconsole/include/jquery-1.4.2.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="/cimconsole/include/treeview.css"/>

�޸ĳ���
/bboss-tablib/src/com/frameworkset/common/tag/tree/impl/ListNodeHelper.java
/bboss-tablib/src/com/frameworkset/common/tag/tree/impl/NodeHelper.java
/bboss-tablib/src/com/frameworkset/common/tag/tree/impl/TreeTag.java
����prototype�ű���Ͷ�ţ���ΪͶ�����½ű���
<script src="/cimconsole/include/jquery-1.4.2.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="/cimconsole/include/treeview.css"/>


//NodeHelper.getPrototypeScript(ret,request.getContextPath()); //jquery
//            	if(!this.enablecontextmenu) //jquery
//            		NodeHelper.getPrototypeScript(ret,request.getContextPath()); 

<a firsted="true" name="icon_root" �޸�Ϊ<a firsted="true" id="icon_root"

�޸�tree��ǩ����jquery����
 <!-- 
        	�Ƿ�jqueryװ��,true-�ǣ�false-����
        	Ĭ��ֵ��false;
        	Ϊfalseʱ,��ǩ�⽫�Զ�Ϊҳ������������ʽ�ͽű������򲻵���
        	<script src="/cimconsole/include/jquery-1.4.2.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="/cimconsole/include/treeview.css"/>
        	trueʱ��������ʽ�ͽű���ͨ���ⲿ����
         -->
        <attribute> 
            <name>jquery</name>
            <required>false</required>
            <rtexprvalue>true</rtexprvalue>
        </attribute>


o ����ִ�����ݿ�Ԥ����ģ�������ر�ǩ

dbutil-ִ�����ݿ�����ɾ���Ĳ�����Ԥ�������ͨ��
sqlparams-����֧����pager��ǩ��beaninfo��ǩ��list��ǩ��ִ��Ԥ��������İ󶨱������ϣ�ͬʱ����ָ��sql�󶨱����Ķ����﷨�ֽ����

batchutil-ִ��Ԥ������������ͨ���������
statement-ָ��batchutilҪִ�е���������䣬������Ԥ����sql��䣬Ҳ��������ͨsql���
batch-ָ��statementָ����Ԥ����sql����һ��󶨱���
sqlparam-����ָ��Ԥ���������sql�󶨱���������ֵ���������͡����ݸ�ʽ��ֻ��������dbutil��sqlparams��statement,batch������ǩ�С�

dbutil��ǩ������˵�����£�

	statement��ָ��Ҫִ�е�sql��䣬������ͨsql��䣬Ҳ�����Ǵ��󶨱�����sqlģ����䣬����Ҫд������
	dbname��ָ�����ݿ����ӳ����ƣ���ѡ���ԣ�Ĭ��Ϊpoolman�����õĵ�һ�����ӳ�
	pretoken��ָ��Ԥ����sql���İ󶨱�������ǰ�÷ֽ������������÷ֽ��endtokenһ��ָ������ѡ���ԣ�Ĭ��Ϊ#[
	endtoken��ָ��Ԥ����sql���İ󶨱���������÷ֽ����������ǰ�÷ֽ��pretokenһ��ָ������ѡ���ԣ�Ĭ��Ϊ]
	action:ָ��sql��Ӧ���ݿ�������ͣ�������delete,insert,update,����ѡ��		
	result:ָ�����ݿ��������洢���������ƣ���ѡ���ԣ�Ĭ��ֵ��������Ϊ��dbutil_result

sqlparams��ǩ����˵�����£�
	pretoken��ָ��Ԥ����sql���İ󶨱�������ǰ�÷ֽ������������÷ֽ��endtokenһ��ָ������ѡ���ԣ�Ĭ��Ϊ#[
	endtoken��ָ��Ԥ����sql���İ󶨱���������÷ֽ����������ǰ�÷ֽ��pretokenһ��ָ������ѡ���ԣ�Ĭ��Ϊ]
	sqlparamskey:ָ���������洢��request ���Լ��еı������ƣ�������pager��beaninfo��list��ǩ���ú�ģ��sql�İ󶨱�������ֵ
	sqlparams�����pager��beaninfo��list��ǩһ��ʹ��
	
batchutil��ǩ����˵�����£�
		dbname-�����������Ӧ�����ݿ����ӳص����ƣ���ѡ���ԣ�Ĭ��Ϊpoolman�����õĵ�һ�����ӳ�
		type-������������ͣ�ȡֵ��Χcommon,prepared,��ѡ���ԣ�Ĭ��ֵΪprepared
		batchOptimize-�Ż�Ԥ����������������Ʊ���
		
statement��ǩ����˵�����£�
	sql-ָ���������sql��䣬����ʹԤ����sql������ͨsql���
	pretoken-ָ��Ԥ����sql���İ󶨱�������ǰ�÷ֽ������������÷ֽ��endtokenһ��ָ������ѡ���ԣ�Ĭ��Ϊ#[
	endtoken-ָ��Ԥ����sql���İ󶨱���������÷ֽ����������ǰ�÷ֽ��pretokenһ��ָ������ѡ���ԣ�Ĭ��Ϊ]
		
sqlparam ����˵����
	name���󶨱������ƣ������Ԥ����sqlģ���еı������Ʊ���һ�£���ѡ��
	value������ֵ����ѡ��
	type���������ͣ���ѡ�Ĭ��ֵΪstring,��Ӧ��ȡֵ��Χ���£�
			bigdecimal
        	boolean
        	byte
        	byte[]
			date
			double 
			float
			int 
			long
			short 
			string
			time
			timestamp
			blob
			clob
			blobfile
			clobfile
	dataformat�����ݸ�ʽ����Ҫ����ָ����������(date,time,timestamp)�Ĵ洢��ʽ
 

�ر�˵����
��pretoken ��endtoken����������Ҫ����֧���ڱ�ǩԤ�����ѯ����ʱ����sql���������﷨
���磺
pretoken = "#\\["
endtoken = "\\]"
����ı���ֵ����Ĭ�ϵı����ֽ����������Ա����ָ���Լ��ķֽ��

������Ҫʵ�ֵĹ��ܣ�
���Ӵ洢���̣�����ִ�б�ǩ

ʹ��ʵ�����ο�����������
beaninfo:/bboss-tablib/webapp/pager/testDetailTag_prepareddb.jsp
list:/bboss-tablib/webapp/pager/testListPagertag_prepareddb.jsp
pager:/bboss-tablib/webapp/pager/testPagerTag_prepareddb.jsp
dbutil:
	����-/bboss-tablib/webapp/pager/testPagerTag_preparedInsert.jsp
	ɾ��-/bboss-tablib/webapp/pager/testPagerTag_preparedDeletedb.jsp
	����-/bboss-tablib/webapp/pager/testPagerTag_preparedUpdatedb.jsp

batchutil:
	��ͨ���������-/bboss-tablib/webapp/pager/testPagerTag_batchdb.jsp
	Ԥ�������������-/bboss-tablib/webapp/pager/testPagerTag_preparedbatchdb.jsp

o ��չ��ҳ���б���ϸ��Ϣҳ��ֱ������statementִ�����ݿ��ѯ���ܣ�����Ԥ�����ѯ��ʽ

o beaninfo��ǩ��pager��ǩ��list��ǩ�����������ԣ�
sqlparamskey:ָ�����󶨱��������洢��request ���Լ��еı������ƣ��Ա�pager��beaninfo��list��ǩ��ȡsql�İ󶨱�������ֵ


����������ص��ļ���
/bboss-tablib/webapp/WEB-INF/pager-taglib.tld
frameworkset.jar
frameworkset-pool.jar
frameworkset-util.jar
bboss-aop.jar

----------------------------------------
1.0.2 - 2010-1-7
----------------------------------------
o ����ant�����ű�:build.xml��build.properties
o ���ӱ�ǩ���������warӦ��webappĿ¼
o ����distribĿ¼��ŷ����������ļ�

----------------------------------------
1.0.2 - 2010-1-4
----------------------------------------
o ���tabpane��ǩ��Դ��Ŀ¼
o ����ǩ��treetag.tld��������ǩquery���������£�
<!-- 
	 	ʵ������ǩ�Ĳ�ѯ���ܣ������ھ�̬���Ͷ�����ϵ���
		ֻ�ܲ����Ѿ�չʾ���������ڵ�
	  -->
	 <tag>
		<name>query</name>
        <tagclass>com.frameworkset.common.tag.tree.impl.QueryTag</tagclass>		
        <bodycontent>JSP</bodycontent>
        <!-- 
        	rootid:ָ�����ĸ��ڵ�id��Ĭ��ֵΪ0����Ӧ��treedata��rootid����ֵ
         -->        
        <attribute>
          <name>rootid</name>
          <required>false</required>
          <rtexprvalue>true</rtexprvalue>
        </attribute>
        <!-- 
        	templatepath:�����ѯ�򡢲�ѯ��ť��velocityģ��ű��ļ�·���������velocityģ���ļ��еĸ�Ŀ¼
        	���磺ģ��Ŀ¼Ϊd:/templates/treequery.vm,��ôtemplatepath��ֵ��Ϊtreequery.vm
         -->        
        <attribute>
          <name>templatepath</name>
          <required>false</required>
          <rtexprvalue>true</rtexprvalue>
        </attribute>
	 </tag>
ʹ�÷�����
	 <tree:query/> <!-- rootidĬ��Ϊ0��templatepathĬ��Ϊtreequery.vm -->
	 <tree:query rootid="0"/> <!-- templatepathĬ��Ϊtreequery.vm -->
	 <tree:query templatepath="treequery.vm"/> <!-- rootidĬ��Ϊ0 -->
	 <tree:query rootid="0" templatepath="treequery.vm"/>


2009.12.7
---------------------------------------------------------------------------------
o �޸�index��ǩ������ҳ�淶Χ����
<!-- 
			�м�ҳ����ʽ����
		 -->
		<attribute>
			<name>classname</name>
			<required>false</required>
			<rtexprvalue>true</rtexprvalue>
		</attribute>
		
		<!-- 
			չʾ���м�ҳ������Ĭ��Ϊ-1,����չʾ�м�ҳ
		 -->
		<attribute>
			<name>tagnumber</name>
			<required>false</required>
			<rtexprvalue>true</rtexprvalue>
		</attribute>
		
		<!-- 
			�м�ҳ��չ����
		 -->
		<attribute>
			<name>centerextend</name>
			<required>false</required>
			<rtexprvalue>true</rtexprvalue>
		</attribute>
�޸ĵĳ���
WebRoot/WEB-INF/pager-taglib.tld
/bboss-taglib/src/com/frameworkset/common/tag/pager/tags/IndexTag.java		

ʹ�÷�����<pg:index tagnumber="10"/>

o �޸�index��ǩ����������ԣ�
<!--
			����ҳ����ʹ��pager.js����pager_custom.js
			Ϊtrueʱʹ��pager_custom.js����ʱҪ��ҳ����Ҫ������form����
			   <form name="com.frameworkset.goform" method="post"></form>
			Ϊfalseʱʹ��pager.js
			�����Ե�Ĭ��ֵΪfalse
		-->
		<attribute>
			<name>custom</name>
			<required>false</required>
			<rtexprvalue>true</rtexprvalue>
		</attribute>
<!-- 
			������ť�Ƿ�ʹ��ͼƬ,Ĭ��ֵΪfalse
			ֻ��useimage=trueʱ��imagedir��imageextend��������
			���useimage=trueʱ��û��ָ��imagedir��imageextend���ԣ���ô����Ĭ������
			ʹ�÷���
			<pg:index useimage="true" imagedir="/include/images" imageextend=" border=0 " tagnumber="-1"/>
		 -->
		<attribute>
			<name>useimage</name>
			<required>false</required>
			<rtexprvalue>true</rtexprvalue>
		</attribute>
		<!-- 
			������ťͼƬ���Ŀ¼����ŵ�ͼƬ����Ϊ��
			 first.gif-�� ҳ
    		 next.gif-��һҳ
    		 pre.gif-��һҳ
    		 last.gif-β ҳ
    		Ĭ��ֵΪ��/include/images
		 -->
		<attribute>
			<name>imagedir</name>
			<required>false</required>
			<rtexprvalue>true</rtexprvalue>
		</attribute>
		<!-- 
			����ͼƬ����չ���Դ���Ĭ��ֵΪ��" border=0 "
		 -->
		<attribute>
			<name>imageextend</name>
			<required>false</required>
			<rtexprvalue>true</rtexprvalue>
		</attribute>

�޸ĵĳ���
WebRoot/WEB-INF/pager-taglib.tld
/bboss-taglib/src/com/frameworkset/common/tag/pager/tags/IndexTag.java

��������
---------------------------------------------------------
1.��pager-taglib.tld��index��ǩ������������ԣ�

<tag>
		<name>index</name>
		����������
		<!--
			����ҳ����ʹ��pager.js����pager_custom.js
			Ϊtrueʱʹ��pager_custom.js����ʱҪ��ҳ����Ҫ������form����
			   <form name="com.frameworkset.goform" method="post"></form>
			Ϊfalseʱʹ��pager.js
			�����Ե�Ĭ��ֵΪfalse
		-->
		<attribute>
			<name>custom</name>
			<required>false</required>
			<rtexprvalue>true</rtexprvalue>
		</attribute>
<!-- 
			������ť�Ƿ�ʹ��ͼƬ,Ĭ��ֵΪfalse
			ֻ��useimage=trueʱ��imagedir��imageextend��������
			���useimage=trueʱ��û��ָ��imagedir��imageextend���ԣ���ô����Ĭ������
			ʹ�÷���
			<pg:index useimage="true" imagedir="/include/images" imageextend=" border=0 " tagnumber="-1"/>
		 -->
		<attribute>
			<name>useimage</name>
			<required>false</required>
			<rtexprvalue>true</rtexprvalue>
		</attribute>
		<!-- 
			������ťͼƬ���Ŀ¼����ŵ�ͼƬ����Ϊ��
			 first.gif-�� ҳ
    		 next.gif-��һҳ
    		 pre.gif-��һҳ
    		 last.gif-β ҳ
    		Ĭ��ֵΪ��/include/images
		 -->
		<attribute>
			<name>imagedir</name>
			<required>false</required>
			<rtexprvalue>true</rtexprvalue>
		</attribute>
		<!-- 
			����ͼƬ����չ���Դ���Ĭ��ֵΪ��" border=0 "
		 -->
		<attribute>
			<name>imageextend</name>
			<required>false</required>
			<rtexprvalue>true</rtexprvalue>
		</attribute>

<!-- 
			�м�ҳ����ʽ����
		 -->
		<attribute>
			<name>classname</name>
			<required>false</required>
			<rtexprvalue>true</rtexprvalue>
		</attribute>
		
		<!-- 
			չʾ���м�ҳ������Ĭ��Ϊ-1,����չʾ�м�ҳ
		 -->
		<attribute>
			<name>tagnumber</name>
			<required>false</required>
			<rtexprvalue>true</rtexprvalue>
		</attribute>
		
		<!-- 
			�м�ҳ��չ����
		 -->
		<attribute>
			<name>centerextend</name>
			<required>false</required>
			<rtexprvalue>true</rtexprvalue>
		</attribute>
		
	.......		
	</tag>
	
2.	��IndexTag.class�����frameworkset.jar��·����com\frameworkset\common\tag\pager\tags �滻ԭ���༴��
---------------------------------------------------------------------------------------------
bboss taglib v1.0.1
2009.10.13 
---------------------------------------------------------------------------------------------
o ����aop�������
Adding: bboss-taglib\lib\bboss-aop.jar  application/octet-stream
Adding: bboss-taglib\lib\bboss-event.jar  application/octet-stream
Modified: bboss-taglib\lib\frameworkset-pool.jar  



2009.01.08
-------------------------------------------------------------------------
fixed bug 1#:
dataSet is null exception.beaninfo tag's stack is same as the dataset(list tag).cms outline tag use the same stack as list tag.
�������dataSet��ֵû����ȷ���õ����⣬
this.removeVariable();����������recoverParentDataSet();֮ǰ����

declare����˵����
declare�������ƣ��Ƿ������µ�dataSet������rowid����������listǶ��ʹ�õ������
          ��ǩ����ʼ�ջ�������Щ����