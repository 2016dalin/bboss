/**
 * 	��װDBUtil ���ݿ�ײ��࣬����Ajax����
 *	����˵����
 	sql				��	Ҫִ�е�sql���
 	outerCallBack	��	���ò�����Ϊ��ʱ��DBUtil���첽��ʽִ��sql��䣬��Ҫ�ⲿ�ṩһ��js������������ͬ����ʽִ��sql��䣬һ�㶼��ͬ����ʽִ��
 *	���ز�����
 	��ִ�в�ѯ���ʱ������ɹ����ض�ά���飬�����׳��쳣
 	��ִ���������ݿⷽ��ʱ������ɹ�����"true"�������׳��쳣
 	
 *  DBUtil�÷�: �ɲ���ҳ�� testDBHelper.jsp
 	ͬ����ʽ��
	 	try{
	 		var rs = dbUtil().executeSelect("select * from dual");
	 	}catch(e){
	 		alert(e);
	 	}
	 	��ͬ��
	 	try{
	 		var rs = dbUtil().executeSql("select * from dual");
	 	}catch(e){
	 		alert(e);
	 	} 	
	 	ִ��������
	 	try{//�ȶ���sql����
	 		var sqls = ["update ta_test_dbutil t set t.value='v3'","insert into ta_test_dbutil values('n2','v2',4)"];
	 		var rs = dbUtil().executeSql(sqls);
	 	}catch(e){
	 		alert(e);
	 	} 		
 	�첽��ʽ��
	 	//doSomeThingΪֻ��һ�������Ļص�����
	 	dbUtil().executeSql("select * from dual",doSomeThing);
	 	
 *  by:fenggao.li
 *	version 1.0
 */
 var DBUtil = function(){
 	//��DBUtil��ͷ�ľ��Ǿ�̬����
 	//����
	DBUtil.OP_TYPE_INSERT = "1";
	//ɾ��
	DBUtil.OP_TYPE_DELETE = "2";
	//�޸�
	DBUtil.OP_TYPE_UPDATE = "3";
	//��ѯ
	DBUtil.OP_TYPE_SELECT = "4";
	//������
	DBUtil.OP_TYPE_BATCH = "5";
 	//�Ƿ����
 	DBUtil.debug = false;
 	
 	//���ݿ����ʵ����
	this.dbImplName = "DBUtil";	
	//����Դ����
	this.dbName = "bspf";	
	
	//�������ͣ�1����2ɾ��3�޸�4��ѯ
	this.opType = "";	
	//��ִ�е�sql���
	this.sql = "";	
 	
 	this.rs = "" ;
 	
 	//�ⲿ�ص�����
 	//this.outerCallBack ;
 	
 	//�����ģ�Ĭ��Ϊ��
 	this.contextPath = ""; 		
 	
 	/*ִ���������*/
 	this.executeInsert = function(sql,outerCallBack){ 		
 		return this.execute(DBUtil.OP_TYPE_INSERT,sql,outerCallBack);
 	}
 	
 	/*ִ��ɾ�����*/
 	this.executeDelete = function(sql,outerCallBack){ 		
 		return this.execute(DBUtil.OP_TYPE_DELETE,sql,outerCallBack);
 	}
 	
 	/*ִ���޸����*/
 	this.executeUpdate = function(sql,outerCallBack){
 		return this.execute(DBUtil.OP_TYPE_UPDATE,sql,outerCallBack);
 	}
 	
 	/*ִ�в�ѯ���*/
 	this.executeSelect = function(sql,outerCallBack){ 		
 		return this.execute(DBUtil.OP_TYPE_SELECT,sql,outerCallBack);
 	}
 	
 	/*ִ�����������*/
 	this.executeBatch = function(sqls,outerCallBack){ 	
 		//�����sql����������	
 		return this.execute(DBUtil.OP_TYPE_BATCH,sqls,outerCallBack);
 	}
 	
 	/*ִ�����ݿⷽ��*/
 	this.executeSql = function(sql,outerCallBack){
 		if(null == sql){
 			throw new Error("�Բ���SQL��䲻��Ϊ�գ�");
 		} 		
 		if(sql instanceof Array){ 			
 			return this.execute(DBUtil.OP_TYPE_BATCH,sql,outerCallBack);
 		}else{
 			sql = jstrim(sql).toLowerCase(); 			
 			if(sql.indexOf("select") == 0){ 				
 				return this.execute(DBUtil.OP_TYPE_SELECT,sql,outerCallBack);
 			}else if(sql.indexOf("insert") == 0){ 				
 				return this.execute(DBUtil.OP_TYPE_INSERT,sql,outerCallBack);
 			}else if(sql.indexOf("delete") == 0){ 				
 				return this.execute(DBUtil.OP_TYPE_DELETE,sql,outerCallBack);
 			}else if(sql.indexOf("update") == 0){ 				
 				return this.execute(DBUtil.OP_TYPE_UPDATE,sql,outerCallBack);
 			}else{
 				throw new Error("�Բ���������Ϸ���SQL��䣡");
 			}
 		}
 	}
 	
 	/*ִ�����ݿⷽ��*/
 	this.execute = function(opType,sql,outerCallBack){ 	
 		//var contextPathTemp = (contextPath==null || contextPath=="") ? contextPath : contextPath+"/";
 		//��û�����������ĵ�ʱ�򣬽�ȡĬ��ֵ
 		this.contextPath = getContextPathByClient(); 		
 		try{
	 		var url = window.location.protocol+"//"+window.location.host+this.contextPath+"/commons/common/dbHelper.jsp";
	 		//sql = encodeParams(sql); //�ڴ����JSPҳ���ʱ�򣬽�����+�ŵ�SQL��������ͳһ���룬���Խ������ַ������ȥ 		
	 		var params = "dbImplName="+this.dbImplName+"&dbName="+this.dbName+"&opType="+opType;
	 		if(opType == DBUtil.OP_TYPE_BATCH){
	 			for(var i=0; i<sql.length; i++){
	 				params += "&sql="+encodeParams(sql[i]);
	 			}
	 		}else{
	 			params += "&sql="+encodeParams(sql);
	 		}
	 		var mAjaxer = new Ajaxer(url,params,outerCallBack,errorFunction);
	 		var r = mAjaxer.send();
	 		return DBUtil.callBackFunction(r); 		
 		}catch(e){
 			if(DBUtil.debug){alert("��������SQL��\n"+sql);} 	
 			throw e;		
 		}	
 	}
 	
 	//�ص�����
 	DBUtil.callBackFunction = function(res){
 		if(null==res || ""==res) return "";
 		var rs = "";
 		//alert("�����˻ص�����:"+res);		
  		//alert("1");
  		var myJsonObject = eval('('+res+')');
  		//alert("myJsonObject="+myJsonObject);
  		//alert("2");
  		var opType = myJsonObject.opType;
  		//alert("opType="+opType);
  		//alert("3");
  		var errCode = myJsonObject.errCode;  		 		
		//������ù�������������
		if(null != errCode){
			//alert(errCode);
			throw new Error(errCode);
			//rs = errCode;
			//return rs;
		}
		//����ǲ�������
		if(opType == DBUtil.OP_TYPE_SELECT){
			rs = myJsonObject.resultSet;
			//alert("callBackFunction.rs="+rs);
			/**
			var rs = myJsonObject.resultSet;		
			for(var i=0; i<rs.length; i++){
				alert(rs[i][0]+","+rs[i][1]+","+rs[i][2]+","+rs[i][3]);
			}
			*/
			
		}else{
			rs = "";
		}
		return rs;
		//eval("doSomeThing(1);");					
 	};
 	//��������	
	errorFunction = function(error){
		this.rs = "";
		alert("����ʧ��:"+error);
	};
 }
 
/*����һ��ϵͳ����dbUitl*/
var dbUtil = new DBUtil();	
/*����һ��ϵͳ����pdbUitl*/
var pdbUtil = new DBUtil();	

/**
 * 	��װAjax ������
 * 	URL:����ȥ�����ҳ���ַ
 *  params :Ҫ����ȥ�Ĳ��������磺"reportId=1&templetId=2"
 *	callBackFunction:�ɹ�����õķ���
 *	errorFunction:ʧ�ܺ���õķ���
 *	Ajaxer�÷�: 
 *	ͬ����ʽ�� var rs = new Ajaxer(url,params).send();��Ҳ��ʹ���첽��ʽ��
 *	�첽��ʽ�� var mAjaxer = new Ajaxer(url,params,callBackFunction,errorFunction).send();
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
	//�����ģ�Ĭ��Ϊ��
 	this.contextPath = ""; 
 	//��û�����������ĵ�ʱ�򣬽�ȡĬ��ֵ
 	if(this.contextPath == null){ 		
 		this.contextPath = getContextPathByClient();
 	}
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
}//��ȡ������
function getContextPathByClient(){		
	var cp = location.pathname ;	
	cp = cp.substring(0,cp.indexOf("/",1));	
	if (cp.substring(0, 1) != "/") 
        cp = "/" + cp;
    /*
	if("/creatorepp"==cp){
    	return cp;
    } else {
    	return "";
    }
    */
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