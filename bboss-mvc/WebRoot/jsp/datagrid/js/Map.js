/*  
 * �ӿڣ� size() ��ȡMAPԪ�ظ��� isEmpty() �ж�MAP�Ƿ�Ϊ�� clear() ɾ��MAP����Ԫ�� put(key, value)  
 * ��MAP������Ԫ�أ�key, value) remove(key) ɾ��ָ��KEY��Ԫ�أ��ɹ�����True��ʧ�ܷ���False get(key)  
 * ��ȡָ��KEY��Ԫ��ֵVALUE��ʧ�ܷ���NULL element(index)  
 * ��ȡָ��������Ԫ�أ�ʹ��element.key��element.value��ȡKEY��VALUE����ʧ�ܷ���NULL containsKey(key)  
 * �ж�MAP���Ƿ���ָ��KEY��Ԫ�� containsValue(value) �ж�MAP���Ƿ���ָ��VALUE��Ԫ�� values()  
 * ��ȡMAP������VALUE�����飨ARRAY�� keys() ��ȡMAP������KEY�����飨ARRAY��  
 *   
 * ���ӣ� var map = new Map();  
 * map.put("key", "value"); var val = map.get("key") ����  
 */  
function Map() {	
    this.elements = new Array();   
  
    // ��ȡMAPԪ�ظ���   
    this.size = function() {   
        return this.elements.length;   
    }   
  
    // �ж�MAP�Ƿ�Ϊ��   
    this.isEmpty = function() {   
        return (this.elements.length < 1);   
    }   
  
    // ɾ��MAP����Ԫ��   
    this.clear = function() {   
        this.elements = new Array();   
    }   
  
    // ��MAP������Ԫ�أ�key, value)   
    this.put = function(_key, _value) {   
        this.elements.push({   
                    key : _key,   
                    value : _value   
                });   
    }   
       
    // ����Ԫ�ز�����   
    this.putOverride = function(_key,_value){   
        this.remove(_key);   
        this.put(_key,_value);   
    }   
  
    // ɾ��ָ��KEY��Ԫ�أ��ɹ�����True��ʧ�ܷ���False   
    this.remove = function(_key) {   
        var bln = false;   
        try {   
            for (i = 0; i < this.elements.length; i++) {   
                if (this.elements[i].key == _key) {   
                    this.elements.splice(i, 1);   
                    return true;   
                }   
            }   
        } catch (e) {   
            bln = false;   
        }   
        return bln;   
    }   
  
    // ��ȡָ��KEY��Ԫ��ֵVALUE��ʧ�ܷ���NULL   
    this.get = function(_key) {   
        try {   
            for (i = 0; i < this.elements.length; i++) {   
                if (this.elements[i].key == _key) {   
                    return this.elements[i].value;   
                }   
            }   
        } catch (e) {   
            return null;   
        }   
    }   
  
    // ��ȡָ��������Ԫ�أ�ʹ��element.key��element.value��ȡKEY��VALUE����ʧ�ܷ���NULL   
    this.element = function(_index) {   
        if (_index < 0 || _index >= this.elements.length) {   
            return null;   
        }   
        return this.elements[_index];   
    }   
  
    // �ж�MAP���Ƿ���ָ��KEY��Ԫ��   
    this.containsKey = function(_key) {   
        var bln = false;   
        try {   
            for (i = 0; i < this.elements.length; i++) {   
                if (this.elements[i].key == _key) {   
                    bln = true;   
                }   
            }   
        } catch (e) {   
            bln = false;   
        }   
        return bln;   
    }   
  
    // �ж�MAP���Ƿ���ָ��VALUE��Ԫ��   
    this.containsValue = function(_value) {   
        var bln = false;   
        try {   
            for (i = 0; i < this.elements.length; i++) {   
                if (this.elements[i].value == _value) {   
                    bln = true;   
                }   
            }   
        } catch (e) {   
            bln = false;   
        }   
        return bln;   
    }   
  
    // ��ȡMAP������VALUE�����飨ARRAY��   
    this.values = function() {   
        var arr = new Array();   
        for (i = 0; i < this.elements.length; i++) {   
            arr.push(this.elements[i].value);   
        }   
        return arr;   
    }   
  
    // ��ȡMAP������KEY�����飨ARRAY��   
    this.keys = function() {   
        var arr = new Array();   
        for (i = 0; i < this.elements.length; i++) {   
            arr.push(this.elements[i].key);   
        }   
        return arr;   
    }
    
    // ��Array,copy 
    this.copyArray = function(_Array){
        var len = _Array.length;
    	var arr = new Array();
    	if(arr.constructor == Array){ //��ά��
    		for(var i=0; i<len; i++){
    			arr.push(_Array[i]);
    		}
    	} else {//һά��
    		
    	}
    	return arr;
    }
}   
  
/**  
   �������飬�ɽ��ִ��е�����������������飬���������Сֵ������  
*/  
function NumberArray(){   
       
    this.elements = new Array();   
    this.addandReturnMax = function(num){   
        this.add(num);   
        this.sort();   
        return this.max();   
    }   
    this.add = function(num){   
        num = num.replace(/\D/g,'');   
        this.elements.push(num);   
    }   
    this.sort = function(){   
        this.elements = this.bubbleSort();   
        return this.elements;   
    }   
    this.max = function(){   
        return this.elements.slice(0,1);   
    }   
    this.min = function(){   
        return this.elements.slice(-1,0);   
    }   
       
    this.bubbleSort = function() {   
        var arr = this.elements;   
        // ���ѭ������Ҫ����arr.length�������ֵ����   
        for (var i = 0; i < arr.length; i++) {   
            // �ڲ�ѭ�����ҵ���i���Ԫ�أ�������͵�i��Ԫ�ؽ���   
            for (var j = i; j < arr.length; j++) {   
                if (parseInt(arr[i]) < parseInt(arr[j])) {   
                    // ��������Ԫ�ص�λ��   
                    var temp = arr[i];   
                    arr[i] = arr[j];   
                    arr[j] = temp;   
                }   
            }   
        }   
        return arr;   
    }    
    this.clear = function(){   
        this.elements = new Array();   
    }   
}