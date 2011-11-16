/**
 * 	DataGrid��װ��
 *  params :��DataGrid��ʼ���������
 */
var DataGrid = function(id, _params) {
	try {
		//DataGridʵ����������
		this.params = _params;
		if (null == this.params) {
			this.params = {};
		}
		
		if (null == id || null == this.params.tableInfoId) {
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
		$.messager.alert('������Ϣ',DataGrid.msg_init_error+e.description,'error');
		return ;
	}
	
	/**��Ա����**/
	/*��������*/
	this.loadData = function(params) {
		if (typeof(params) == "string") {
				
		} else {
			params = jQuery.param(params);
		}
		//$.messager.alert('params',params,'info');
		var id = this.id;
		//alert(DataGrid.getDataUrl+jQuery.param(this.params)+'&'+params);
		$.getJSON(DataGrid.getDataUrl+jQuery.param(this.params),
					params,
					function(data){
						if(null != data){
							$(id).datagrid('loadData', data);
						}else{
							$.messager.alert('������Ϣ','�Բ��𣡼�������ʧ�ܣ�','error');
						};
		});
	}
	/*��������*/
	this.saveData = function() {
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
	}
	
	/*�ύ����*/
	this.submitData = function(data){
		var deleted_rows = $(this.id).datagrid('getChanges','deleted');
		var updated_rows = $(this.id).datagrid('getChanges','updated');
		var inserted_rows = $(this.id).datagrid('getChanges','inserted');	
		
		var deleted_data = this.object2json(deleted_rows);
		var updated_data = this.object2json(updated_rows);
		var inserted_data = this.object2json(inserted_rows);
		
		var data = "{\"deleted\":"+deleted_data+","
					+"\"updated\":"+updated_data+","
					+"\"inserted\":"+inserted_data+"}";
		//var obj = JSON.parse(data);
		var url = DataGrid.saveDataUrl+jQuery.param(this.params);
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
		$.messager.alert('������Ϣ',"�Բ��𣡱���ʧ��:"+textStatus+"",'error');		
		//����ʧ��ȡ���ͻ��˱��θ���
		//$('#tt').datagrid('rejectChanges');
	}
	/*�������ݳ���*/
	this.onSaveDataSuccess = function(data, textStatus) {
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
		$(this.id).datagrid('endEdit', this.lastIndex);
		$(this.id).datagrid('appendRow',params);
		this.lastIndex = $(this.id).datagrid('getRows').length-1;
		//$(this.id).datagrid('select', this.lastIndex);
		$(this.id).datagrid('beginEdit', this.lastIndex);	
	}
	/*���һ��*/
	this.deleteRow = function(params) {
		var row = $(this.id).datagrid('getSelected');
		if (row){
			var index = $(this.id).datagrid('getRowIndex', row);
			$(this.id).datagrid('deleteRow', index);
		}	
	}
	/*���һ��*/
	this.onClickRow = function(rowIndex) {
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
		$(this.id).datagrid('rejectChanges');
	}
	/*������ת��Ϊjsoncode*/
	this.object2json = function(jsonObj){
		var jsoncode =  JSON.stringify(jsonObj); 
		//alert(jsoncode);			
		return jsoncode;
	}
	/*��������*/
	this.run = function(){
	//alert(1);
	//$(this.id).datagrid;
	};
}
/*��̬��������*/
DataGrid.editers = {};

//��ȡ������
DataGrid.getContextPath = function() {		
	var cp = location.pathname ;	
	cp = cp.substring(0,cp.indexOf("/",1));	
	if (cp.substring(0, 1) != "/") {
        cp = "/" + cp;
    }
	return cp;
}

DataGrid.log = function(msg) {

}

DataGrid.showLogs = function() {

}

//����editer
DataGrid.getEditer = function(id) {
return DataGrid.editers.id;
}
/*��̬��������*/
DataGrid.msg_init_error = "����DataGridʧ��:";
DataGrid.msg_init_lostparam = "ȱ�ٲ���";
DataGrid.getDataUrl = window.location.protocol+"//"+window.location.host+DataGrid.getContextPath()+"/datagrid/getData.htm?";
DataGrid.saveDataUrl = window.location.protocol+"//"+window.location.host+DataGrid.getContextPath()+"/datagrid/saveData.htm?";
DataGrid.getNextKeyUrl = window.location.protocol+"//"+window.location.host+DataGrid.getContextPath()+"/datagrid/getNextKey.htm?";
DataGrid.getTableInfoUrl = window.location.protocol+"//"+window.location.host+DataGrid.getContextPath()+"/datagrid/getTableInfo.htm?";

/*
 * ��װ����
 */
DataGrid.encodeParams = function(params) {
	return encodeURIComponent(params);
}
