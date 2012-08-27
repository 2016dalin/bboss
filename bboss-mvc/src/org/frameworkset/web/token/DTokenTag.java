package org.frameworkset.web.token;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import com.frameworkset.common.tag.BaseTag;
/**
 * ��̬���Ʊ�ǩ
 * @author yinbp
 *
 */
public class DTokenTag extends BaseTag {
	/**
	 * ������3��ֵ�����û��ָ��Ĭ��Ϊinput���ͣ���
	 * json ���json���ʽ�����ƺ�ֵ�ķָ���Ĭ��Ϊ'�ţ����Ҫָ��Ϊ"�ţ���ô��Ҫ
	 * ͨ��jsonsplit������ָ��
	 * 
	 * input���input hidden
	 * 
	 * param(�Ⱥű��ʽ)
	 * 
	 * 
	 * 
	 */
	private String element = "input";
	private String jsonsplit ="'";
	/**
	 * ������������Ψһ��ʶ��һ������ֻ��Ҫһ�����ƣ������ͬһ������
	 * ��dttoken��ǩ��Ҫ���ֶ�Σ��������߼��ж��г��ֶ�Σ���ô����Ҫָ��
	 * fid���ԣ��������ⶼ����Ҫָ��fid����
	 * 
	 */
	private String fid ;
	private boolean cache = true;
	public String getElement() {
		return element;
	}
	public void setElement(String element) {
		this.element = element;
	}
	public String getJsonsplit() {
		return jsonsplit;
	}
	public void setJsonsplit(String jsonsplit) {
		this.jsonsplit = jsonsplit;
	}
	@Override
	public void doFinally() {
		
		super.doFinally();
		element = "input";
		jsonsplit ="'";
		this.cache = true;
		this.fid = null;
	}
	@Override
	public int doStartTag() throws JspException {
		
		int ret = super.doStartTag();
		try {
			out.print(MemTokenManagerFactory.getMemTokenManager().buildDToken(element,this.jsonsplit,request,fid,this.cache));
		} catch (IOException e) {
			throw new JspException(e);
		}
		catch (RuntimeException e) {
			//
		}
		return ret;
	}
	public String getFid() {
		return fid;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public boolean isCache() {
		return cache;
	}
	public void setCache(boolean cache) {
		this.cache = cache;
	}
	
	

}
