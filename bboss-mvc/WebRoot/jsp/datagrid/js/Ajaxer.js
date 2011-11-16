/**
 * 	��װAjax ������
 * 	URL:����ȥ�����ҳ���ַ
 *  params :Ҫ����ȥ�Ĳ��������磺"reportId=1&templetId=2"
 *	callBackFunction:�ɹ�����õķ���
 *	errorFunction:ʧ�ܺ���õķ���
 *	�÷��� var mAjaxer = new Ajaxer(url,params,callBackFunction,errorFunction)
 *		  mAjaxer.send();
 *  by:haibo.liu
 *	version 1.0
 */
var Ajaxer = function(URL,params,callBackFunction,errorFunction){
	this.AjaxMethod = "POST";							//Ĭ�ϴ��䷽ʽ POST;
	this.SendObject = params;								//���������	
	this.ResponseType = "Text";						//����ֵ���� 
	//async - �Ƿ��첽��trueΪ�첽��falseΪͬ����Ĭ��Ϊtrue	
	this.Async = true;										//�Ƿ��첽��ʽ
	
 	
	function createXMLHttp(){
	    var xmlhttp;
	    try{
	        xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
	    }catch(ex){
	        try{
	            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	         }catch(ex){
	            xmlhttp = new XMLHttpRequest();
	          }
	    	}
	    return xmlhttp;
	}
	
	var xmlHttp;
	
 	this.send =	function ()
	{	
		this.Async = (typeof(callBackFunction) == 'function');
		//�����ͬ������ֱ�ӷ��ؽ����
		if(!this.Async){
			return this.sendSynchroMethod(URL,params);
		}
		//����ִ�е����첽
	    xmlHttp = null;
	    xmlHttp = createXMLHttp();
	    if(xmlHttp == null)
	    {
	        alert("����xmlHTTPʧ�ܣ�");
	    }else{
	        xmlHttp.onreadystatechange = this.SendBack;	         
	        xmlHttp.open(this.AjaxMethod,URL,this.Async);	       
	        xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");	       
	        xmlHttp.send(this.SendObject);
	    }	
	    return "";   
	}
	//�����ͬ����ʽ���Ͳ���ֱ�ӷ��ؽ��
	this.sendSynchroMethod = function(URL,params){
		//�����ͬ����ʽ��ʹ�õ�����_xmlHttp����	
		//alert("URL="+URL+"\nparams="+params);	
	    var _xmlHttp = createXMLHttp();
	    if(_xmlHttp == null)
	    {
	        alert("����_xmlHttpʧ�ܣ�");
	    }else{	         
	        _xmlHttp.open(this.AjaxMethod,URL,this.Async);	       
	        _xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");	       
	        _xmlHttp.send(this.SendObject);
	        if(_xmlHttp.status == 200){
	        	return _xmlHttp.responseText;
	        }
	        //alert("������������"+_xmlHttp.status+"����");	
	        throw new Error(_xmlHttp.status,"������������"+_xmlHttp.status+"����");         
	    }	   
	}	
	//������첽��ʽ���Ͳ��ûص�����
	this.SendBack = function (){
		try{
			if(xmlHttp.readyState == 4){				
				if(xmlHttp.status == 200){
					var res;
					res = xmlHttp.responseText;
					//�ڽ�res���ظ����÷���֮ǰ����Ҫ�ȴ���res��Ϊ�˲�Ӱ��������ʹ��Ajaxer�࣬ȥ�����д��룬��Ϊ���д��������DBUtil��
					//callBackFunction(DBUtil.callBackFunction(res));
					callBackFunction(res);
							
				}else{
					var error = eval(error + "=" + "{\"code\":\"" + xmlHttp.status + "\",\"message\":\"" + xmlHttp.statusText + "\"}" );
					errorFunction(error);
				}
			}
		}catch(e){
			alert(" ������������"+xmlHttp.status+"����"+e);
		}

	}
}
/**
 * 	��־��¼��
 * 	logInfo:��������
 *  logModule :������־ģ�飬��ҳ�������� ReportDBTools�ж����ģ�����ƣ��磺ReportDBTools.MODULE_BQXTBB
 *	log_type ��������	(�޲��� 0 ; ���� 1 ; ���� 2 ; ɾ�� 3 ; ���� 4)
 *  url ����ҳ����log.jsp�ļ������·��
 *	�÷��� new Log().log(logInfo, logModule, log_type);
 *  by:haibo.liu
 *	version 1.0
 */
var Log = function (){
	//�����ģ�Ĭ��Ϊ�գ�������ǿ�������Ϊnull�����Զ���ȡ����
 	this.contextPath = ""; 
 	//��û�����������ĵ�ʱ�򣬽�ȡĬ��ֵ
 	this.contextPath = getContextPathByClient();
	this.log =	function (logInfo, logModule, log_type){
		var params = "logInfo="+logInfo+"&logModule="+logModule+"&log_type="+log_type;
		url = window.location.protocol+"//"+window.location.host+this.contextPath+"/ynstjj/report/log/logManage.jsp";
		//�첽��¼��־
		new Ajaxer(url,params,callBackFunction,errorFunction).send();
	}
	callBackFunction = function(){};
	errorFunction = function(){};
}
/*
 * ��װ����
 */
function encodeParams(params){
	return encodeURIComponent(params);
}
//��ȡ������
function getContextPathByClient(){		
	var cp = location.pathname ;	
	cp = cp.substring(0,cp.indexOf("/",1));	
	if (cp.substring(0, 1) != "/") 
        cp = "/" + cp;
	if("/creatorepp"==cp){
    	return cp;
    } else {
    	return "";
    }
	return cp;
}
//ȥ�ո�
String.prototype.trim= function(){
	// ��������ʽ��ǰ��ո�
	// �ÿ��ַ�������� 
	return this.replace(/(^\s*)|(\s*$)/g, "");
}	
//ȥ�ո�
function jstrim(str){
	if(null == str) return null;
	try{				
		return str.trim();
	}catch(e){
		return str;
	}
}
//����js����
var Validator = function(){
	
}
//У���Ƿ���һ���Ϸ�������
Validator.isName = function(str){
	var reg = /^[\w\u4e00-\u9fa5������������������]+$/g;
	return reg.test(str);
}
//����Ƿ���һ���Ϸ���ָ������	
Validator.isItemName = function(str){
	var reg1 = /^[\S]+$/g;	
	var reg2 = /^[^%\'\",;:=+-\\{\\}\[\].]+$/g;
	return ((reg1.test(str)) && (reg2.test(str)));
}