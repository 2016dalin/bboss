/************************2008-08-27 author ���� **************************
Ϊ����Ӧ����������ֵ����ѡ��
*************************2008-08-27 author ���� **************************/
/*
document.attachEvent("onblur", searchOptionOnblur);
//var currentSearchSelect = null;
function searchOptionOnblur(currentSearchSelect){
	if(currentSearchSelect != null && window.event.srcElement == currentSearchSelect){
		currentSearchSelect.code = "";
	}
}
ʾ�����룺���Բο� TestOptionSearcher.html ҳ��

--���ڵ�ѡ��������:

<select name="levy_item" onpropertychange="searchDictFun(this);conculate();" style="width:240px;" validator="num" cnname="˰��˰Ŀ" msg="��ѡ��˰��˰Ŀ��" 
  onkeydown="searchOption(this);"  onkeypress="resetKeyPress();" onblur="clearCode(this);" onchange="clearCode(this);"
 >
������Ҫ�����ĸ��¼�: onkeydown="searchOption(this);"  onkeypress="resetKeyPress();" onblur="clearCode(this);" onchange="clearCode(this);"
onkeydown�¼��Ǳ����

--���ڶ�ѡ����Ҫ������ı���ѯ��

<input type="TEXT" name="test" onkeydown="searchOption(levy_item);"  onblur="clearCode(levy_item);" onchange="clearCode(levy_item);"/>
<input type="button" name="btn" value="����" onclick="doSearch(test.value,levy_item)"/>
<br>
<select name="levy_item" id="levy_item" size=8 multiple>
 <option name="o1">011-ú��</option>
 <option name="o1">012-ԭ��</option>
</select>
*/
//�û������룬ֱ�Ӷ�λselect��option
function searchOption(oSelect, defaultValue){
	//���ѡ���ǵ�ѡ�޹أ�ͨ��oSelect.multiple�����жϵ�ѡ���Ƕ�ѡ
	//alert(oSelect.multiple);
	searchSelect(oSelect, defaultValue);
}

//�Ӷ�ѡ��select������
function searchSelect(oSelect, defaultValue){
	//alert("searchMultipleSelect");
	/*��û���ҵ�ѡ���ʱ������Ĭ��ֵ*/
	var defaultIndex = oSelect.selectedIndex ;//�������˴������ֵʱ��Ĭ��Ϊ֮ǰ�Ĳ��������ҪĬ��Ϊ�գ���ֻ�����-1
	if(null == defaultValue || "current" == defaultValue){
		//currentΪĬ�Ϸ�ʽ
	}
	else if("blank" == defaultValue){
		defaultIndex = -1 ;
	}else if("first" == defaultValue){
		defaultIndex = 0 ;
	}else if("last" == defaultValue){
		defaultIndex = oSelect.options.length-1 ;
	}else if(Number.NaN != parseInt(defaultValue)){
		defaultIndex = parseInt(defaultValue) ;
	}
//alert(defaultIndex);
	if(window.event.keyCode == 13){//����ǻس����ţ���ִ����������	   
		doSearch(oSelect.code,oSelect, defaultIndex) ;
		oSelect.code = "";//ÿ������ϣ��򽫱����ÿա�
	}else{//���򱣴�select������		
		if(oSelect.code == null){ oSelect.code = "" ;}	

		var tempCode = window.event.keyCode ;
		//��ΪС���������code�벻ͬ����ת��ΪС���̵ģ���48��57������0-9��
		if(window.event.keyCode >= 48 && window.event.keyCode <= 57){
			tempCode += 48;
		}
		//alert(tempCode);
		//������ı���
		//if(event.srcElement.type == "text"){
			//oSelect.code = event.srcElement.value;
		//}else
		{
			//ɾ����
			if(tempCode == 8 && oSelect.code.length>0){				
				oSelect.code = oSelect.code.substring(0,oSelect.code.length-1);			
			}else if(tempCode>=96 && tempCode<=105){
				oSelect.code += new String(tempCode-96) ;//unicode��-96֮��������ֱ���
			}
		}
		//alert(oSelect.code);
		doSearch(oSelect.code,oSelect, defaultIndex) ;
	}
}

//������λ�������û������뷵����������index
function doSearch(content,oSelect, defaultIndex){	
	var index = defaultIndex ;	//ԭ����Ĭ��ΪoSelect.selectedIndex�������ÿ�ֵ���;	
	for(var i=0; i<oSelect.options.length; i++){
		if(oSelect.options[i].text.indexOf(content) >= 0){
			index = i ;		
			break ;
		}
	}
	oSelect.selectedIndex = index;
	//return index ;
}

//���select��codeֵ
function clearCode(oSelect){
	if(event.srcElement.type == "text"){
			oSelect.code = event.srcElement.value;
	}else{
		oSelect.code = "";
	}	
}

function resetKeyPress(){
	window.event.returnValue = false;
	//oSelect.focus();
}
/**************************************************/