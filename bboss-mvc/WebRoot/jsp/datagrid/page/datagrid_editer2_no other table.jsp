<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ page contentType="text/html; charset=GBK"%>
<%
String templet_id = "1";
String report_id = "1";
String tableInfoId = "CAN_BE_TABLE_NAME";
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<title>DataGrid Editer</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jsp/datagrid/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/jsp/datagrid/themes/icon.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/classic/tables.css" type="text/css"></link>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/datagrid/jquery-1.4.4.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/datagrid/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/datagrid/json2.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/datagrid/js/datagrid-1.0.js"></script>
	
	<script>
		
		var products = [
		    {productid:'FI-SW-01',name:'Koi'},
		    {productid:'K9-DL-01',name:'Dalmation'},
		    {productid:'RP-SN-01',name:'Rattlesnake'},
		    {productid:'RP-LI-02',name:'Iguana'},
		    {productid:'FL-DSH-01',name:'Manx'},
		    {productid:'FL-DLH-02',name:'Persian'},
		    {productid:'AV-CB-01',name:'Amazon Parrot'}
		];
		/*��Ʒ��ʽ��*/
		function formatter_product(value, rowData, rowIndex) {
			for(var i=0; i<products.length; i++){
				if (products[i].productid == value) return products[i].name;
			}
			return value;
		}
		/*�ɱ���ʽ��*/
		function formatter_unitcost(value, rowData, rowIndex) {
			if (null == value || "" == value) {
				//alert("�Բ��𣡳ɱ�����Ϊ�գ�");
				return 0.0;
			}
			
			return value;
		}
		
		/*У����*/
		function validator(rowIndex, rowData) {
			//У��ɱ�
			if(null == rowData["unitcost"] || "" == rowData["unitcost"]){
				$.messager.alert('������Ϣ','�Բ��𣡳ɱ�����Ϊ�գ�','error');
				//alert("�Բ��𣡳ɱ�����Ϊ�գ�");
				//��λ�����޸�
				$('#tt').datagrid('beginEdit', rowIndex);
				return false;
			}
		}
		
		var editer_params = {	
			templet_id:'<%=templet_id%>',		
			report_id:'<%=report_id%>',
			tableInfoId:'<%=tableInfoId%>'
			
		};
		/*��ʼ������*/
		var editer1 = new DataGrid('#tt',editer_params);
		
		/*��ʼ������*/
		$(function(){
			//��ʼ��tabs
			$('#tabs').tabs({
				tools:[{
					iconCls:'icon-add',
					handler: function(){
						$.messager.alert('��ʾ��Ϣ','���Զ�̬�������Ҫ�༭�ı�','info');
					}
				},{
					iconCls:'icon-save',
					handler: function(){
						$.messager.alert('��ʾ��Ϣ','���������������޸��˵ı�','info');	
					}
				}]
			});
					
			//��ʼ��datagrid��һ��Ҫע������
			var editer = editer1;
			<!-- datagrid start -->
			$('#tt').datagrid({
				toolbar:[{
					text:'���',
					iconCls:'icon-add',
					handler:function(){
						editer.appendRow({
							REPORT_ID:editer.params.report_id,
							itemid:'EST-',
							productid:'',
							listprice:'',
							unitcost:'',
							attr1:'',
							status:'P'
						});
					}
				},'-',{
					text:'ɾ��',
					iconCls:'icon-remove',
					handler:function(){
						editer.deleteRow();
					}
				},'-',{
					text:'����',
					iconCls:'icon-undo',
					handler:function(){
						editer.rejectChanges();
					}
				}
				,'-',{
					text:'����',
					iconCls:'icon-save',
					handler:function(){
						editer.saveData();
					}
				}],
				fitColumns:true,
				loadMsg:'���ڼ������ݣ����Ժ�...',
				onBeforeLoad:function(){
					editer.rejectChanges();
				},
				onClickRow:function(rowIndex){
					editer.onClickRow(rowIndex);
				},				
				onAfterEdit:function(rowIndex, rowData, changes){
					//validator(rowIndex, rowData);					
				}				
				<!-- datagrid end -->
			});
			
			//��ʼ������
			initSelect("productid", products);
			//��������
			doQuery();
		});
		
		/*��������*/
		function doQuery(){			
			inputs = $("#form1 input,#form1 select");	
			//֧�����ַ�ʽ����
			//editer1.loadData("itemid=i&a=33");
			editer1.loadData(inputs);
		}
		
		/*��ʼ������*/
		function initSelect(selectId, datas) {
			var id = "#"+selectId;
			var selectObj = $(id);
			//alert("data="+jQuery.data(products[0],"productid"));
			//�����һ�����У��Ա��ѯ��ȫ������
			$(id).append("<option ></option>"); 
			for (var i=0; i<products.length; i++) {
				$(id).append("<option value='"+products[i].productid+"'>"+products[i].name+"</option>"); 
			}
			
		}
		
		/*���Ŷ���Ч��*/
		function animate() {
			$("#queryTable").hide("slow");
			$("#queryTable").show("slow");
		}
				
	</script>
</head>
<body>
	<div style="width:1000px;height:auto;">
	<h1 align="center">Editable DataGrid</h1>
	<!-- tab start -->
	<div id="tabs" >
		<div title="��ɾ�Ĳ��" style="padding:15px;">
			<!-- form start -->
			<form name='form1' id='form1'>
			<table id="queryTable" class="genericTbl"  style="width:100%;height:auto">
			<tr>
			<td>
			<fieldset>
				<legend>��ѯ����</legend>
		
						<table id="queryTable1">
							<tr>
								<td width="10%" align="right">
									itemid like
								</td>
								<td width="20%" align="left">
									<input type="text" name="itemid">
								</td>
								<td width="10%" align="right">
									��Ʒ =
								</td>
								<td width="20%" align="left">
									<select style="width:100%;" name="productid" id="productid"></select>
								</td>
								<td width="20%" align="center" nowrap>
									<a class="easyui-linkbutton" icon="icon-add" href="javascript:void(0)" onclick="">����</a>
								</td>
							</tr>
							<tr>
								<td style="" width="10%" align="right">
									�۸� >=
								</td>
								<td width="20%" align="left">
									<input type="text" name="listprice">
								</td>
								<td style="" width="10%" align="right">
									�ɱ� >=
								</td>
								<td width="20%" align="left">
								<input type="text" name="unitcost">
								</td>
								<td width="20%" align="center" nowrap>
									<a class="easyui-linkbutton" icon="icon-search" href="javascript:void(0)" onclick="doQuery()">��ѯ</a>
								</td>
							</tr>
						</table>
					</fieldset>
					</td>
					</tr>
			</table>
			</form>
			<!-- form end -->
			<!-- dadagrid start -->
			<!-- url="http://127.0.0.1:8000/bboss/datagrid/getData.htm?templet_id=1&report_id=1&tableInfoId=CAN_BE_TABLE_NAME" -->
			<table id="tt" 
					title="���ݱ༭��" iconCls="icon-edit" singleSelect="true"
					idField="itemid" url="http://172.16.81.53:8000/bboss/datagrid/getData.htm?templet_id=1&report_id=1&tableInfoId=CAN_BE_TABLE_NAME">
				<thead>
					<tr>
						<th field="itemid" width="80" rowspan='2' editor="{type:'text'}">Item ID</th>
						<th field="productid" width="100" rowspan='2' formatter="formatter_product" editor="{type:'combobox',options:{valueField:'productid',textField:'name',data:products,required:true}}">��Ʒ</th>
						<th colspan='2'>��Ʒ����1</th>
						<!-- 
						<th field="productid" width="100" rowspan='2'>��Ʒ</th>
						 -->
						<th colspan='2'>��Ʒ����2</th>	
								
					</tr>
					<tr>				
						<th sorter="listpriceSorter" field="listprice" width="80" align="right" editor="{type:'numberbox',options:{precision:1,required:true}}">�۸�(��)</th>
						<th field="unitcost" width="80" align="right" editor="numberbox" formatter="formatter_unitcost">�ɱ�(��)</th>
						<th field="attr1" width="150" editor="text">����</th>
						<th field="status" width="60" align="center" editor="{type:'checkbox',options:{on:'P',off:''}}">״̬</th>
					</tr>
				</thead>
			</table>
			<!-- dadagrid end -->
			
		</div>
		<div title="ϵͳ�����" closable="true" style="padding:15px;" cache="false" href="tabs_href_test.html">
			This is Tab2 with close button.
		</div>
		<div title="��Ƕiframe" closable="true">
			<iframe scrolling="yes" frameborder="0"  src="http://www.google.com" style="width:100%;height:100%;"></iframe>
		</div>
		<div title="DataGrid����" closable="false" align="left" style="padding:15px;">
				<p>
					���ߣ�����
				</p>
				<p>
					��ʵ�ֹ���
				</p>					
				<ul>
					<li>
						�����ɽ�����ɾ�Ĳ��������������κ����ݿ��������datagrid�Զ����
					</li>
					<li>
						
					</li>
				</ul>
				<p>
					δʵ�ֹ���
				</p>					
				<ul>
					<li>
						��ʱû��ʵ�����ݵ��뵼��
					</li>
					<li>
						
					</li>
				</ul>
			</div>
	</div>
	<!-- tab end -->
	</div>
</body>
</html>