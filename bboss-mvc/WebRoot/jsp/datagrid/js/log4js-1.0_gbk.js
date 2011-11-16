/**
 * 	Log��װ��
 */
var Logger = function(debug) {
	//Ĭ�ϲ��������Թ���
	if (null == debug) {debug = false};
	this.debug = debug;
	//��¼��־
	this.logs = "";
	//�Ƿ��д�����־
	this.hasError = false;
	
	/*����jQuery���ڲ�������*/
	this.error = function(message) {
		this.log("jquery.error()="+message,"error");
		this.hasError = true;
	}
	//��ʱ���ò�����
	//jQuery.error = this.error;
	/*��¼��־��Ϣ*/
	this.log = function(msg,level) {
		if (null == level || "" == level) {
			level = "info";
		}
		if (this.debug) { 
			var prefix = level+">>";
			if (typeof(msg) == "string") {
				var html =  prefix+msg+"<br>";
				//������Ϣ�ú�ɫ��ʾ
				if ("error" == level) {
					html = "<font color='red'>"+html+"</font>";
					this.hasError = true;
				} else if ("message" == level) {
					//������Ϣ����ɫ��ʾ
					html = "<font color='blue'>"+html+"</font>";
				}
				this.logs += "<"+(new Date()).toTimeString()+"><br>"+html;		
			} else {
				//params = jQuery.param(params);
			}
			
		}
	}
	/*��ʾ��־��Ϣ*/
	this.showLogs = function() {
		if (true != this.debug) {
			this.logs = "<font color='red'>��Ҫ����Ϊdebug=true��ſɼ�¼������Ϣ��</font>";
		}
		var fn = "<center><a href='javascript:void(0)' onclick=\"Logger.mailtoAdmin($('#logdiv').html())\">�����ʼ�</a>&nbsp;&nbsp;&nbsp;&nbsp;"
		fn += "<a href='javascript:void(0)' onclick=\"Logger.copyToClipboard($('#logdiv').text())\">����</a></center>"
		var logdiv = "<div id='logdiv'>"+this.logs+"</div>"
		$.messager.alert('������Ϣ',logdiv+fn);
		this.clearLogs();
	}
	/*�����־*/
	this.clearLogs = function() {
		this.logs = "";
		this.hasError = false;
	}
}
/**��̬���ÿ�ʼ**/
/*��ȡ������*/
Logger.getContextPath = function() {	
	//this.log("this.getContextPath()");	
	var cp = location.pathname ;	
	cp = cp.substring(0,cp.indexOf("/",1));	
	if (cp.substring(0, 1) != "/") {
        cp = "/" + cp;
    }
	return cp;
}
/*��̬����*/
Logger.mailtoAdminUrl = window.location.protocol+"//"+window.location.host+Logger.getContextPath()+"/log/mailtoAdmin.htm?";
/*��̬����*/
/*�����ʼ�*/
Logger.mailtoAdmin = function(msg) {
	//alert(msg);
	var url = Logger.mailtoAdminUrl;
	$.messager.alert('��ʾ��Ϣ','��ϲ�����ʼ����ͳɹ������ǽ����촦���������','info');
}
/*���Ƶ����а�*/
Logger.copyToClipboard = function(msg) {
	//alert(msg);
	if ($.browser.msie){//�ж�IE
		window.clipboardData.setData('text', msg);
		$.messager.alert('��ʾ��Ϣ','��ϲ�����Ѿ����Ƶ��˼����壡','info');
	}else{
		$.messager.alert('��ʾ��Ϣ','�����������֧�ּ���������������и��ƣ�','error');
	}
			
	
}
