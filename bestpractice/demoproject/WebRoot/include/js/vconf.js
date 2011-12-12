/**
 * @fileoverview ����֤�� ��֤�������ö�����validator.js���ʵ�ֱ���֤
 * @author creator
**/
(function(){

	/**
	* SSO check user exist
	* author creator
	* date 2007.5.8
	* last modify by creator
	*/
	var checkUrl = "/signup/check_user.php";
	var checkDomainUrl = "/signup/check_domain.php";
	var memberYes = "�û����ѱ�ע��";
	var memberNo = "�û�������";
	var error = "�첽ͨ�Ŵ���";
	var defer = "���Ĳ�ѯ�������࣬��һ���Ӻ��ٲ�ѯ";
	var type1 = "freemail",type2 = "vipmail",type3 = "sinauser",type4 = "2008mail";
	
	/* ajax engine */
	function ajaxCheck(url,from,name, callBack) {
		var XHR;
		var date = new Date();
		var parameter = "from=" + from + "&name=" + name + "&timeStamp= " + date.getTime();
		try {
			try{
				XHR=new ActiveXObject("Microsoft.XMLHTTP");
			}catch(e){
					try{
						XHR=new XMLHttpRequest();
					} catch (e){ }
			}
			XHR.open("POST",url);
			XHR.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
			XHR.onreadystatechange = function(){
				if(XHR.readyState==4) {
					if(XHR.status==200) {
						if(callBack) callBack(from,XHR.responseText);
					}
				}
			}
			XHR.send(parameter);
		}catch (e) {
			alert(e.toString());
		}
	}
	
	/* checkUserExist */
	function checkUserExist(from,name, callBack) {
		ajaxCheck(checkUrl,from,name, callBack);
	}				
	function checkDomainExist(from,name,callBack) {
		ajaxCheck(checkDomainUrl,from,name,callBack);
	}

	/**
	 * �ж��û����Ƿ����ظ��������Ѿ����뷽��
	 */	
	function checkUsername(e, v) {
		checkUserExist('othermail', e.value, function(from, responseText){
			if(from == 'othermail'){
				var msg = "";
				if(responseText.search("no")>=0){
					msg = 'hide';
				}else if(responseText.search("yes")>=0){
					msg = '******************';
				}else{
					msg = '�첽ͨ�Ŵ���';
				}
				v.showErr(e, msg);
			}
		});
	}

	/**
	 * �ж��û����Ƿ����ظ��������Ѿ����뷽��
	 */	
	function checkFreemail(e, v) {
		checkUserExist('freemail', e.value, function(from, responseText){
			if(from == 'freemail'){
				var msg = "";
				if(responseText.search("no")>=0){
					msg = 'hide';
				}else if(responseText.search("yes")>=0){
					msg = '���������ѱ�ռ��';
				}else{
					msg = '�첽ͨ�Ŵ���';
				}
				v.showErr(e, msg);
			}
		});
	}

	/**
	 *  ͨ�� Ajax �ж������Ƿ����ظ��������Ѿ����뷽��
	 */
	function checkDomain(e, v) {
		checkDomainExist('sinauser', e.value, function(from, responseText){
			if(from == 'sinauser'){
				var msg = "";
				if(responseText.search("no")>=0){
					msg = 'hide';
				}else if(responseText.search("yes")>=0){
					msg = '�����ѱ���';
				}else{
					msg = '�첽ͨ�Ŵ���';
				}
				v.showErr(e, msg);
			}
		});
	}
	
	/**
	 *  ��������ǿ�ȷ���1
	 */
	function CharMode(iN) {
		if (iN >= 65 && iN <= 90) return 2;
		if (iN >= 97 && iN <= 122) return 4;
		else return 1;
	}
	
	/**
	 *  ��������ǿ�ȷ���2
	 */
	function bitTotal(num) {
		var modes = 0;
		for (var i=0;i<3;i++) {
			if (num & 1) modes++;
			num >>>= 1;
		}
		return modes;
	}
	
	
	
	/**
	 * ����֤�� ��֤�������ö���
	 */
	var conf = {
		/**
		 *  �����ύʱִ�еĺ���
		 */
		//'submitFn': function(el){
			
		//},
		/**
		 *  ��ȡ����ʱִ�еĺ���
		 *  �����ж�ǿ����Ҫ����ӦHTML�ṹ֧��
		 */
		'focusFn': function(el, v){
			var alt = el.alt;
			var arg = /focusFn{([^}].+?)}/.exec(alt);
			arg = (arg == null) ? false : arg[1];
			$removeClassName($(arg), 'hide');
		},
		'����': {						
			msg: '{name}����ӦΪ{range}λ'
		},
		'��ͬ': {						
			msg: '{name}��һ��'
		},
		"������": {
			msg: '������{name}',
			reg: /./
		},
		"������sel": {
			msg: '��ѡ��{name}',
			reg: /./
		},
		"ȫ����": {
			msg: '{name}����Ϊȫ����',
			reg: /[^\d]+/
		},
		"������": {
			msg: '{name}����������',
			reg: /^[^\d]+$/
		},
		"�пո�": {
			msg: '{name}���ܰ����ո��',
			reg: /^[^ ��]+$/
		},
		"�����ַ": {
			msg: '�����ַ��ʽ����ȷ',
			reg: /^[0-9a-z][_.0-9a-z-]{0,31}@([0-9a-z][0-9a-z-]{0,30}\.){1,4}[a-z]{2,4}$/
		},
		"�ֻ�����": {
			msg: '{name}����ȷ',
			reg: /^1(3\d{1}|5[389])\d{8}$/
		},
		"֤������": {
			msg: '{name}����ȷ',
			reg: /^(d){5,18}$/
		},
		"�д�д": {
			msg: '{name}�����д�д��ĸ',
			reg: /[A-Z]/,
			regFlag: true
		},
		"��ȫ��": {
			msg: '{name}���ܰ���ȫ���ַ�',
			reg: /[\uFF00-\uFFFF]/,
			regFlag: true
		},
		"��β�����ǿո�": {
			msg: '��β�����ǿո�',
			reg: /(^\s+)|(\s+$)/,
			regFlag: true
		},
		"���ַ�": {
			msg: '{name}���ܰ��������ַ�',
			reg: />|<|,|\[|\]|\{|\}|\?|\/|\+|=|\||\'|\\|\"|:|;|\~|\!|\@|\#|\*|\$|\%|\^|\&|\(|\)|`/i ,
			regFlag : true
		},
		"���ַ�pwd": {
			msg: '��������ʹ�������ַ�',
			reg: />|<|\+|,|\[|\]|\{|\}|\/|=|\||\'|\\|\"|:|;|\~|\!|\@|\#|\*|\$|\%|\^|\&|\(|\)|`/i,
			regFlag : true
		},
		"ȫ�����ַ�": {
			msg: '{name}���ܰ��������ַ�',
			reg: />|<|,|\[|\]|\{|\}|\?|\/|\+|=|\||\'|\\|\"|:|;|\~|\!|\@|\#|\*|\$|\%|\^|\&|\(|\)|\-|\��|\.|`/i ,
			regFlag : true
		},
		"Ŀ���ַ���ַ�": {
			msg: '{name}���ܰ��� . ����������ַ�',
			reg: />|<|,|\[|\]|\{|\}|\?|\/|\+|=|\||\'|\\|\"|:|;|\~|\!|\@|\#|\*|\$|\%|\^|\&|\(|\)|\-|\��|`/i ,
			regFlag : true
		},
		"�����ַ���ַ�": {
			msg: '{name}���ܰ��� :/,.() ����������ַ�',
			reg: />|<|\[|\]|\{|\}|\?|\+|=|\||\'|\\|\"|;|\~|\!|\@|\#|\*|\$|\%|\^|\&|\-|\��|`/i ,
			regFlag : true
		},
		"���ֹ��ַ�": {
			msg: '{name}���ܰ��������ַ�,���԰���:> . ',
			reg: /<|,|\[|\]|\{|\}|\?|\/|\+|=|\||\'|\\|\"|:|;|\~|\!|\@|\#|\*|\$|\%|\^|\&|\(|\)|\-|\��|`/i ,
			regFlag : true
		},
		"������": {
			msg: '{name}��֧������',
			reg: /[\u4E00-\u9FA5]/i,
			regFlag : true
		},
		"�����ַ�": {
			msg: '{name}��֧�������ַ�',
			reg: /[^a-zA-Z\.��\u4E00-\u9FA5\uFE30-\uFFA0]/i,
			regFlag : true
		},
		"�»���": {
			msg: '�»��߲��������',
			fn:  function(e, v){
				var val = e.value;
				return (val.slice(val.length-1)=="_") ? this.msg : '';
			}
		},
		"��β�������»���": {
			msg: '��β�������»���',
			reg: /(^_+)|(_+$)/,
			regFlag: true
		},
		"���»���": {
			msg: '���ܰ����»���',
			fn:  function(e, v){
				var val = e.value;
				return (val.search("_") >= 0) ? this.msg : '';
			}
		},
		"��Ϊ��": {
			fn:  function(e, v){
				if(!e.value){
					e.style.background = '';
					return 'custom';
				}else { 
					return ''; 
				}
			}
		},
		"������ĸ": {
			msg: '���ܰ������ֺ�Ӣ����ĸ������ַ�',
			reg: /[^0-9a-zA-Z]/i,
			regFlag : true
		},
		"����": {
			msg: '���ܰ�������������ַ�',
			reg: /[^0-9]/i,
			regFlag : true
		},
		"������ĸ����": {
			msg: '���ܰ������֡�Ӣ����ĸ�ͺ���������ַ�',
			reg: /[^0-9a-zA-Z\u4E00-\u9FA5]/,
			regFlag : true
		},
		"������ĸ���Ŀո��»���": {
			msg: '���ܰ���ȫ���ַ�',
			reg: /[^0-9a-zA-Z\u4E00-\u9FA5\_\ ]/,
			regFlag : true
		},
		"��ѡ��": {
			msg: '��ѡ��{name}',
			fn: function(e,v) {
				switch (e.type.toLowerCase()) {
					case 'checkbox':
						return e.checked ? 'clear' : this.msg;
					case 'radio':
						var radioes = document.getElementsByName(e.name);
						for(var i=0; i<radioes.length; i++) {
							if(radioes[i].checked) return 'clear';
						}
						return this.msg;
					default:
						return 'clear';
				}
			}
		},
			"��ѡ��": {
			msg: '��ѡ��{name}',
			fn: function(e,v) {
				switch (e.type.toLowerCase()) {
					case 'select-one':
							return e.value ? 'clear': this.msg;
					default:
						return 'clear';
				}
			}
		},
		"����": {
			msg: '{name}',
			fn: function(e,v) {
				switch (e.type.toLowerCase()) {
					case 'checkbox':
						return e.checked ? 'clear' : this.msg;
					case 'radio':
						var radioes = document.getElementsByName(e.name);
						for(var i=0; i<radioes.length; i++) {
							if(radioes[i].checked) return 'clear';
						}
						return this.msg;
					default:
						return 'clear';
				}
			}
		},
		"�ж�ǿ��": {
			fn: function(e,v) {
				for (var i=1;i<=3;i++) {
					try {
						$removeClassName($("passW" + i), "passWcurr");
					}catch (e) {}
				}
				var password = e.value;
				var Modes = 0;
				var n = password.length;
				for (var i=0;i<n;i++) {
					Modes |= CharMode(password.charCodeAt(i));
				}
				var btotal = bitTotal(Modes);
				if (n >= 10) btotal++;
				switch(btotal) {
					case 1:
						try {
							$addClassName($("passW1"), "passWcurr");
						}catch (e) {}
						return;
					case 2:
						try {
							$addClassName($("passW2"), "passWcurr");
						}catch (e) {}
						return;
					case 3:
						try {
							$addClassName($("passW3"), "passWcurr");
						}catch (e) {}
						return;
					case 4:
						try {
							$addClassName($("passW3"), "passWcurr");
						}catch (e) {}
						return;
					default:
						return;
				}
			}
		},
		"�ж���֤��": {
			fn: function(e,v) {
				if (/^[0-9a-zA-Z]/.test(e.value)) {
					if (/[^0-9a-zA-Z]/.test(e.value)) return "��֤�����";
				}else if (/^[\u4E00-\u9FA5]/.test(e.value)) {
					if (/[^\u4E00-\u9FA5]/.test(e.value)) return "��֤�����";
				}
				return "";
			}
		},
		"����": { fn: checkUsername },
		"����ע������": { fn: checkFreemail },
		"������": { fn: checkDomain }
	}
	
	//ע��ȫ��conf����
	if (window.$vconf == null) window.$vconf = conf;
})();
