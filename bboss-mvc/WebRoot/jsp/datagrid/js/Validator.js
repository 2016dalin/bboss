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