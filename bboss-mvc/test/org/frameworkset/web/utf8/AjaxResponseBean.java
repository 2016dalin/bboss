package org.frameworkset.web.utf8;

import java.io.Serializable;

/**
 * 
 *<p>Title:AjaxResponseBean.java</p>
 *<p>Description: �淶��װ��̨�����Ľ����Ϣ������ת����json��ʽ</p>
 *<p>Copyright:Copyright (c) 2010</p>
 *<p>Company:���Ͽƴ�</p>
 *@author ������
 *@version 1.0
 *2011-4-19
 */
public class AjaxResponseBean implements Serializable {
	//״̬,success��ʾ�ɹ���error��ʾʧ��
	private String status;
	
	//��Ϣ
	private String data;
	
	private String e;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getE() {
		return e;
	}
	public void setE(String e) {
		this.e = e;
	}
}
