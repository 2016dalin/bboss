/**
 * 	DataGrid��װ��
 *  id :����"#table1"
 *  params :��DataGrid��ʼ���������
 */
var DataGrid = function(id, statParams) {
	DataGrid.log("constructor().id="+id);
	try {
		//statParams:��չ��������Ҫ������ͳ������
		this.statParams = statParams;
		if (null == this.statParams) {
			this.statParams = {};
		}
		
		if (null == id) {
			throw new Error(DataGrid.msg_init_lostparam);
			//jQuery.error(DataGrid.msg_init_lostparam)
		} else {
			//alert("data="+jQuery.data(editers,"productid"));
			this.id = id;
		}
		
		//��ǰ���ڱ༭��
		//this.currRowIndex = 0;
		//���༭��
		this.lastIndex = 0;
		this.hasError = false;
		
		/*ע��editer*/
		//$(this.id).editer = this;
		//alert($(this.id).editer);
	} catch(e) {
		DataGrid.log("constructor().error="+DataGrid.msg_init_error+e.description,'error');
		$.messager.alert('������Ϣ',DataGrid.msg_init_error+e.description,'error');
		return ;
	}
	
	/**��Ա����**/
	/*�������ݣ�֧�����ֲ������ַ�����jquery������ͨ�����Զ��庯��*/
	this.loadData = function(params) {
		DataGrid.log("��ѯ���ݿ�ʼ......","message");
		DataGrid.log("loadData().params="+params);
		$(this.id).datagrid('options').url = DataGrid.getDataUrl;
		var queryParams = $(this.id).datagrid('options').queryParams;
		var params_type = typeof(params);
		DataGrid.log("loadData().params_type="+params_type);
		if (params_type == "string") {
			params = $(params);
			DataGrid.addQueryParams(queryParams, params);
		} else if (params_type == "object") {
			DataGrid.addQueryParams(queryParams, params);
		} else if (params_type == "function") {
			//jQuery.isFunction(params);
			params(queryParams);
		}
		
		DataGrid.log("loadData().queryParams="+jQuery.param(queryParams));
		//������ݣ����߿��Խ�columns=null
		$(this.id).datagrid('loadData',{"total":0,"rows":[]});
		$(this.id).datagrid('load',queryParams);
		DataGrid.log("��ѯ���ݽ���......","message");
		/*
		var id = this.id;
		$.getJSON(DataGrid.getDataUrl+jQuery.param(this.params),
					params,
					function(data){
						if(null != data){
							$(id).datagrid('loadData', data);
						}else{
							$.messager.alert('������Ϣ','�Բ��𣡼�������ʧ�ܣ�','error');
						};
		});
		*/
		//$(this.id).datagrid('loaded');
	}
	/*��������*/
	this.saveData = function() {
		DataGrid.log("�������ݿ�ʼ......","message");
		DataGrid.log("saveData()");
		try{
			//���浱ǰ����
			//alert("this.lastIndex="+this.lastIndex);			
			$(this.id).datagrid('endEdit', this.lastIndex);	
			//ת��Ϊjsoncode�벢�ύ����̨���ݿ�				
			var rows = $(this.id).datagrid('getChanges');				
			if(rows.length>0){					
				this.submitData();
			}else{
				$.messager.alert('��ʾ��Ϣ',"�Բ�������û�и��Ļ������д������ݣ���������ݣ�",'info');
				return false;
			}				
		}catch(e){
			this.onSaveDataError(null, e.description, null);				
		}
		DataGrid.log("�������ݽ���......","message");
	}
	
	/*�ύ����*/
	this.submitData = function(data){
		DataGrid.log("submitData()");
		var deleted_rows = $(this.id).datagrid('getChanges','deleted');
		var updated_rows = $(this.id).datagrid('getChanges','updated');
		var inserted_rows = $(this.id).datagrid('getChanges','inserted');	
		
		var deleted_data = DataGrid.obj2json(deleted_rows);
		var updated_data = DataGrid.obj2json(updated_rows);
		var inserted_data = DataGrid.obj2json(inserted_rows);
		
		var data = "{\"deleted\":"+deleted_data+","
					+"\"updated\":"+updated_data+","
					+"\"inserted\":"+inserted_data+"}";
		DataGrid.log("submitData().data="+data);
		//var obj = JSON.parse(data);
		var url = DataGrid.saveDataUrl+jQuery.param($(this.id).datagrid('options').queryParams);
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
						success: this.onSaveDataSuccess,
						complete: function(XMLHttpRequest, textStatus){
							//HideLoading();
						},
						error: this.onSaveDataError
				});
		//var r = $.post(url,param,onSaveDataSuccess,"json");	
	}
	/*�������ݳ���*/
	this.onSaveDataError = function(XMLHttpRequest, textStatus, errorThrown) {	
		DataGrid.log("onSaveDataError().textStatus="+textStatus,'error');
		$.messager.alert('������Ϣ',"�Բ��𣡱���ʧ��:"+textStatus+"",'error');		
		//����ʧ��ȡ���ͻ��˱��θ���
		//$('#tt').datagrid('rejectChanges');
	}
	/*�������ݳ���*/
	this.onSaveDataSuccess = function(data, textStatus) {
		DataGrid.log("onSaveDataSuccess().data="+data);
		if (data.status == 1) {
			//����ɹ�ȷ�Ͽͻ��˱��θ���
			$(this.id).datagrid('acceptChanges');
			$.messager.alert('������Ϣ',data.msg,'info');
		} else {
			$.messager.alert('��ʾ��Ϣ',data.msg,'error');
		}
		//$.messager.show(0, data.msg);  
	}
	/*���һ��*/
	this.appendRow = function(params) {
		DataGrid.log("appendRow().params="+params);
		$(this.id).datagrid('endEdit', this.lastIndex);
		$(this.id).datagrid('appendRow',params);
		this.lastIndex = $(this.id).datagrid('getRows').length-1;
		//$(this.id).datagrid('select', this.lastIndex);
		$(this.id).datagrid('beginEdit', this.lastIndex);	
	}
	/*����һ��*/
	this.insertRow = function(params) {alert(22);
		DataGrid.log("insertRow().params="+params);
		//$(this.id).datagrid('endEdit', this.lastIndex);
		alert(3);
		$(this.id).datagrid('insertRow',params);alert(33);
		//this.lastIndex = $(this.id).datagrid('getRows').length-1;
		//$(this.id).datagrid('select', this.lastIndex);
		//$(this.id).datagrid('beginEdit', this.lastIndex);	
	}
	/*ɾ��һ��*/
	this.deleteRow = function(params) {
		DataGrid.log("deleteRow().params="+params);
		var row = $(this.id).datagrid('getSelected');
		if (row){
			var index = $(this.id).datagrid('getRowIndex', row);
			$(this.id).datagrid('deleteRow', index);
		}	
	}
	/*���һ��*/
	this.onClickRow = function(rowIndex) {
		DataGrid.log("onClickRow().rowIndex="+rowIndex);
		if (this.lastIndex != rowIndex){
			$(this.id).datagrid('endEdit', this.lastIndex);
			$(this.id).datagrid('beginEdit', rowIndex);
		}else{
			$(this.id).datagrid('beginEdit', rowIndex);
		}
		this.lastIndex = rowIndex;	
	}
	/*�ܾ��޸�*/
	this.rejectChanges = function() {
		DataGrid.log("rejectChanges()...");
		$(this.id).datagrid('rejectChanges');
	}
	/*��ȡ��ѯ����*/
	this.getQueryParams = function(){
		DataGrid.log("getQueryParams()...");
		var queryParams = $(this.id).datagrid('options').queryParams;
		DataGrid.log("getQueryParams().queryParams="+jQuery.param(queryParams));
		return queryParams;
	}
	/*��������*/
	this.run = function(){
		DataGrid.log("run()");
		//alert(1);
		//$(this.id).datagrid;
	};
	/*���ݹ�����*/
	this.onLoadSuccess = function(data) {
		//alert("statParams="+this.statParams);
		//return data;
	}
	/*�Զ���չ��*/
	this.customDisplay = function() {
		DataGrid.log("customDisplay()");
		$.messager.alert('��ʾ��Ϣ','���ڿ�����......','info');
		var columns = $(this.id).datagrid('options').columns;
		var columns_json = DataGrid.obj2json(columns);
		alert(columns_json);
		
	}
}
/**��̬���ÿ�ʼ**/
//����һ����־��¼��
DataGrid.logger = new Logger();

/*����jQuery���ڲ�������*/
DataGrid.error = function(message) {
	DataGrid.log("jquery.error()="+message,"error");
}
//��ʱ���ò�����
//jQuery.error = DataGrid.error;
/*��¼��־��Ϣ*/
DataGrid.debug = function(debug) {
	DataGrid.logger.debug = debug;
}
DataGrid.log = function(msg,level) {
	DataGrid.logger.log(msg,level);
}
/*��ʾ��־��Ϣ*/
DataGrid.showLogs = function() {
	DataGrid.logger.showLogs();
}
/*�����־*/
DataGrid.clearLogs = function() {
	DataGrid.logger.clearLogs();
}
/*��ȡ������*/
DataGrid.getContextPath = function() {	
	DataGrid.log("DataGrid.getContextPath()");	
	var cp = location.pathname ;	
	cp = cp.substring(0,cp.indexOf("/",1));	
	if (cp.substring(0, 1) != "/") {
        cp = "/" + cp;
    }
	return cp;
}
/*��̬����*/
DataGrid.msg_init_error = "����DataGridʧ��:";
DataGrid.msg_init_lostparam = "ȱ�ٲ���";
DataGrid.getDataUrl = window.location.protocol+"//"+window.location.host+DataGrid.getContextPath()+"/datagrid/getData.htm?";
DataGrid.saveDataUrl = window.location.protocol+"//"+window.location.host+DataGrid.getContextPath()+"/datagrid/saveData.htm?";
DataGrid.getNextKeyUrl = window.location.protocol+"//"+window.location.host+DataGrid.getContextPath()+"/datagrid/getNextKey.htm?";
DataGrid.getTableInfoUrl = window.location.protocol+"//"+window.location.host+DataGrid.getContextPath()+"/datagrid/getTableInfo.htm?";
DataGrid.editers = {};
/*��̬����*/
//����editer
DataGrid.getEditer = function(id) {
	return DataGrid.editers.id;
}
/*��Ϊֻ�ܹ����������õ�queryParams�У��������Ĳ���������jQuery���󣬻���һ�㣬��ô��ʱ��Ҫ�����ö�������ԣ��������õ�queryParams��*/
DataGrid.addQueryParams = function(queryParams, params) {
	try {
		//jQuery.isPlainObject(params);�����Ƿ�һ������Ķ���
		if (params instanceof jQuery) {
			DataGrid.log("DataGrid.addQueryParams().params is a jQuery Object");
			var json = "{";			
			$.each(params, function(i, o){
				json += "\""+o.name+"\":\""+o.value+"\",";
				//eval��������ȫ����ie�Ͽ���ִ�У�ͬʱ��ff�������Ҳ�޷����У����Կ�������������ȡ��				
				//window.eval("queryParams."+o.name+"=\""+o.value+"\"");
				
			});
			if (params.length>0) {
				json = json.substring(0,json.length-1);
			}
			json += "}"; 
			//���´���ò���
			params = jQuery.parseJSON(json);
		} 
		
		DataGrid.log("DataGrid.addQueryParams()�ϲ�ǰ��params="+jQuery.param(params));
		//��ȵݹ�ϲ���������
		jQuery.extend(true,queryParams, params);
		DataGrid.log("DataGrid.addQueryParams()�ϲ����queryParams="+jQuery.param(queryParams));
	} catch(e) {
  		DataGrid.log("DataGrid.addQueryParams().params can not be eval!","error");
  		$.messager.alert('������Ϣ','�Բ��𣡲�ѯʧ�ܣ��ڴ��ݲ�ѯ������ʱ����������'+e,'error');
  		throw e;
	}
}
/*��װ����*/
DataGrid.encodeParams = function(params) {
	DataGrid.log("DataGrid.encodeParams().params="+params);
	return encodeURIComponent(params);
}
/*������ת��Ϊjsoncode*/
DataGrid.obj2json = function(jsonObj){
	DataGrid.log("DataGrid.obj2json().jsonObj="+jsonObj);
	var jsoncode =  JSON.stringify(jsonObj); 
	//alert(jsoncode);			
	return jsoncode;
}
