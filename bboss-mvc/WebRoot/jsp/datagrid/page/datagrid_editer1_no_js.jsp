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
	<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/datagrid/js/DataGrid.js"></script>
	
	<script>
		var editer_params = {	
			templet_id:'<%=templet_id%>',		
			report_id:'<%=report_id%>',
			tableInfoId:'<%=tableInfoId%>'
			
		};
		var dataGrid1 = new DataGrid('#tt',editer_params);
		
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
				alert("�Բ��𣡳ɱ�����Ϊ�գ�");
				//��λ�����޸�
				$('#tt').datagrid('beginEdit', rowIndex);
				return false;
			}
		}
		
		$(function(){
			<!-- datagrid start -->
			var lastIndex;
			$('#tt').datagrid({
				toolbar:[{
					text:'���',
					iconCls:'icon-add',
					handler:function(){
					$('#tt').datagrid('endEdit', lastIndex);
						$('#tt').datagrid('appendRow',{
							REPORT_ID:'<%=report_id %>',
							itemid:'EST-',
							productid:'',
							listprice:'',
							unitcost:'',
							attr1:'',
							status:'P'
						});
						var lastIndex = $('#tt').datagrid('getRows').length-1;
						$('#tt').datagrid('beginEdit', lastIndex);
					}
				},'-',{
					text:'ɾ��',
					iconCls:'icon-remove',
					handler:function(){
						var row = $('#tt').datagrid('getSelected');
						if (row){
							var index = $('#tt').datagrid('getRowIndex', row);
							$('#tt').datagrid('deleteRow', index);
						}
					}
				},'-',{
					text:'����',
					iconCls:'icon-undo',
					handler:function(){
						$('#tt').datagrid('rejectChanges');
					}
				}
				/*	
				,'-',{
					text:'������Ϣ',
					iconCls:'icon-search',
					handler:function(){
						//var rows = $('#tt').datagrid('getChanges','deleted');	
						var rows = $('#tt').datagrid('getChanges');
						alert('changed rows: ' + rows.length + ' lines');
						if(rows.length>0){							
							alert("�ı���:"+rows[0]["itemid"]);							
						}
					}
				}							
				,'-',{
					text:'ȷ��',
					iconCls:'icon-ok',
					handler:function(){
						$('#tt').datagrid('acceptChanges');
					}
				}
				*/
				,'-',{
					text:'����',
					iconCls:'icon-save',
					handler:function(){
						saveData();
					}
				}],
				fitColumns:true,
				loadMsg:'���ڼ������ݣ����Ժ�...',
				onBeforeLoad:function(){
					$(this).datagrid('rejectChanges');
				},
				onClickRow:function(rowIndex){
					if (lastIndex != rowIndex){
						$('#tt').datagrid('endEdit', lastIndex);
						$('#tt').datagrid('beginEdit', rowIndex);
					}else{
						$('#tt').datagrid('beginEdit', rowIndex);
					}
					lastIndex = rowIndex;
					
					dataGrid1.currRowIndex = rowIndex;
				},
				onBeforeEdit:function(rowIndex, rowData){
					//alert("rowIndex="+rowIndex+"\nrowData="+object2json(rowData));
				},
				onAfterEdit:function(rowIndex, rowData, changes){
					validator(rowIndex, rowData);
					//alert("rowIndex="+rowIndex+"\nrowData="+object2json(rowData)+"\nchanges="+object2json(changes));
					/*
					if(rowData["unitcost"]==null || rowData["unitcost"] == "" ){
						alert("�ɱ�����Ϊ�գ�");
						$('#tt').datagrid('beginEdit', rowIndex);
						return false;
					}
					*/
				}
				/**
				,columns:[[
						{field:'itemid',title:'Item ID',rowspan:2,width:80,sortable:true},
						{field:'productid',title:'��Ʒ',rowspan:2,width:80,sortable:true},				
						{title:'��Ʒ����1',colspan:2},
						{field:'productid',title:'��Ʒ',rowspan:2,width:100,sortable:true},	
						{title:'��Ʒ����2',colspan:2}
					],[
						{field:'listprice',title:'�۸�(��)',width:80,align:'right',sortable:true,editor:"numberbox"},
						{field:'unitcost',title:'�ɱ�(��)',width:80,align:'right',sortable:true,editor:'numberbox'},
						{field:'attr1',title:'����',width:100,editor:'text'},
						{field:'status',title:'״̬',width:60,align:'center',editor:"{type:'checkbox',options:{on:'P',off:''}}"}
					]]
					**/
				<!-- datagrid end -->
			});
		});
		
		var templet_id = "1";
		var report_id = "1";
		
		/*������ת��Ϊjsoncode*/
		function object2json(jsonObj){
		
			var jsoncode =  JSON.stringify(jsonObj); 
			//alert(jsoncode);			
			//document.writeln(jsoncode); 
			return jsoncode;
		} 
		
		/*��������*/
		function saveData() {
			try{
				//���浱ǰ����
				//alert("dataGrid1.currRowIndex="+dataGrid1.currRowIndex);			
				$('#tt').datagrid('endEdit', dataGrid1.currRowIndex);	
				//ת��Ϊjsoncode�벢�ύ����̨���ݿ�				
				var rows = $('#tt').datagrid('getChanges');				
				if(rows.length>0){					
					submitData();
				}else{
					alert("�Բ�������û�и��Ļ������д������ݣ���������ݣ�");
					return false;
				}				
			}catch(e){
				onSaveDataError(null, e.description, null);				
			}
		}
		
		/*�ύ����*/
		function submitData(data){
			var deleted_rows = $('#tt').datagrid('getChanges','deleted');
			var updated_rows = $('#tt').datagrid('getChanges','updated');
			var inserted_rows = $('#tt').datagrid('getChanges','inserted');	
			
			var deleted_data = object2json(deleted_rows);
			var updated_data = object2json(updated_rows);
			var inserted_data = object2json(inserted_rows);
			
			var data = "{\"deleted\":"+deleted_data+","
						+"\"updated\":"+updated_data+","
						+"\"inserted\":"+inserted_data+"}";
			//var obj = JSON.parse(data);
								
			var url = "${pageContext.request.contextPath}/datagrid/saveData.htm?templet_id=<%=templet_id %>&report_id=<%=report_id %>&tableInfoId=<%=tableInfoId %>";
			param = "data="+data;
			//var data = encodeURIComponent(data);
			
			var r = $.ajax({
							type: "post",
							url: url,
							contentType: "application/x-www-form-urlencoded; charset=utf-8",
							dataType: "json",
							async: false,
							cache: false,
							timeout: 3000,	
							data: {data: data},						
							beforeSend: function(XMLHttpRequest){
								//ShowLoading();
							},
							success: onSaveDataSuccess,
							complete: function(XMLHttpRequest, textStatus){
								//HideLoading();
							},
							error: onSaveDataError
					});
			
			//var r = $.post(url,param,onSaveDataSuccess,"json");	
		}
		
		/*�������ݳ���*/
		function onSaveDataError(XMLHttpRequest, textStatus, errorThrown) {			
			alert("�Բ��𣡱���ʧ��:"+textStatus+"");
			//����ʧ��ȡ���ͻ��˱��θ���
			//$('#tt').datagrid('rejectChanges');
		}
		
		/*�������ݳ���*/
		function onSaveDataSuccess(data, textStatus) {
			
			if (data.status == 1) {
				//����ɹ�ȷ�Ͽͻ��˱��θ���
				$('#tt').datagrid('acceptChanges');
				alert(data.msg);
			} else {
				alert(data.msg);
			}
			//$.messager.show(0, data.msg);  
		}
		
		function init() {
			//loadData();
			//animate();
			initSelect("productid", products);
		}
		
		/*��������*/
		function loadData(){
			/*
			alert("");
			var b = $('#tt').datagrid('loadData','datagrid_data2.json');
			alert(b);
			*/
		}
		//�ú�����������
		//$(document).ready(initSelect("productid", products));
		
		/*��ʼ������*/
		function initSelect(selectId, datas) {
			var id = "#"+selectId;
			//products productid
			var selectObj = $(id);
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
<body onload="init();">
	<h1>Editable DataGrid</h1>
	<!-- datagrid_data2.json -->
	<input type="button" onclick="animate();">
	<form>
	<fieldset style="width:643px;height:auto">
		<legend>��ѯ����</legend>

				<table id="queryTable" class="genericTbl">
					<tr>
						<td width="25%" align="right">
							itemid
						</td>
						<td width="25%" align="right">
							<input type="text" name="itemid">
						</td>
						<td width="25%" align="right">
							��Ʒ
						</td>
						<td width="25%" align="right">
							<select style="width:100%;" name="productid" id="productid"></select>
						</td>

					</tr>
					<tr>
						<td style="" width="25%" align="right">
							�۸� >=
						</td>
						<td width="25%" align="right">
							<input type="text" name="listprice">
						</td>
						<td style="" width="25%" align="right">
							�ɱ� >=
						</td>
						<td width="25%" align="right">
						<input type="text" name="unitcost">
						</td>
					</tr>
				</table>
			</fieldset>
	</form>
	
	<table id="tt" style="width:650px;height:auto"
			title="���ݱ༭��" iconCls="icon-edit" singleSelect="true"
			idField="itemid" url="${pageContext.request.contextPath}/datagrid/getData.htm?templet_id=<%=templet_id %>&report_id=<%=report_id %>&tableInfoId=<%=tableInfoId %>">
			<!-- �򵥱�ͷ��
		<thead>
			<tr>
				<th field="itemid" width="80">Item ID</th>
				<th field="productid" width="100" formatter="formatter_product" editor="{type:'combobox',options:{valueField:'productid',textField:'name',data:products,required:true}}">��Ʒ</th>
				<th field="listprice" width="80" align="right" editor="{type:'numberbox',options:{precision:1}}">�۸�(��)</th>
				<th field="unitcost" width="80" align="right" editor="numberbox">�ɱ�(��)</th>
				<th field="attr1" width="150" editor="text">����</th>
				<th field="status" width="60" align="center" editor="{type:'checkbox',options:{on:'P',off:''}}">״̬</th>
			</tr>
		</thead>
		-->		
		<!-- ���ӱ�ͷ -->
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
	
</body>
</html>