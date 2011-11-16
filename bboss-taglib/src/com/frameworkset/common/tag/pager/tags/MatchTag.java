/*****************************************************************************
 *                                                                           *
 *  This file is part of the tna framework distribution.                     *
 *  Documentation and updates may be get from  biaoping.yin the author of    *
 *  this framework							     *
 *                                                                           *
 *  Sun Public License Notice:                                               *
 *                                                                           *
 *  The contents of this file are subject to the Sun Public License Version  *
 *  1.0 (the "License"); you may not use this file except in compliance with *
 *  the License. A copy of the License is available at http://www.sun.com    * 
 *                                                                           *
 *  The Original Code is tag. The Initial Developer of the Original          *    
 *  Code is biaoping yin. Portions created by biaoping yin are Copyright     *
 *  (C) 2000.  All Rights Reserved.                                          *
 *                                                                           *
 *  GNU Public License Notice:                                               *
 *                                                                           *
 *  Alternatively, the contents of this file may be used under the terms of  *
 *  the GNU Lesser General Public License (the "LGPL"), in which case the    *
 *  provisions of LGPL are applicable instead of those above. If you wish to *
 *  allow use of your version of this file only under the  terms of the LGPL *
 *  and not to allow others to use your version of this file under the SPL,  *
 *  indicate your decision by deleting the provisions above and replace      *
 *  them with the notice and other provisions required by the LGPL.  If you  *
 *  do not delete the provisions above, a recipient may use your version of  *
 *  this file under either the SPL or the LGPL.                              *
 *                                                                           *
 *  biaoping.yin (yin-bp@163.com)                                            *
 *  Author of Learning Java 						     					 *
 *                                                                           *
 *****************************************************************************/
package com.frameworkset.common.tag.pager.tags;

import javax.servlet.jsp.JspException;

import com.frameworkset.common.tag.exception.FormulaException;
import com.frameworkset.util.StringUtil;

/**
 * �������ڷ�ҳ/�б�/��ϸ��Ϣ��ǩ�߼��жϵı�ǩ���࣬
 * ������߼���ǩֻ��ʵ�����еĳ��󷽷�match()����
 * 
 * @author biaoping.yin
 * @version 1.0
 */
public abstract class MatchTag extends BaseValueTag {
	
    /**ʵ��ֵ*/
	protected Object actualValue;
	
	/**����ֵ*/
	protected Object value;
	
	/**���ʽ��ͨ��������ʽ�Ľ����ȡ����ֵ*/
	protected String expressionValue;
	
	/**
	 * �����Ƿ���ֵ��ӵ����ֶε�ö��ֵ�У���չ�ֶΣ����ɱ���ʱ���ã�
	 */
	protected boolean addTruevalue = false; 
	
	protected String pattern;
	
	/**
	 * in��notin��ǩƥ��ķ�Χö��ֵ
	 * �����Ǳ������ʽ��ָ�������磺{xxxscope}
	 */
	
	protected String scope;
	
	protected String[] scopes;
	
	
	
	/**
	 * @return String
	 */
	public Object getValue()
	{
		return value;
	}

	/**
	 * @param string
	 */
	public void setValue(Object string)
	{
		value = string;
	}
	
	public int doStartTag() throws JspException {
	    init();
//	    dataSet = searchDataSet(this,PagerDataSet.class);
//	    t_formula = dataSet.getFormula(getExpression());
	    actualValue = evaluateActualValue();
//		actualValue = getOutStr();
//		setMeta();
		//�������ֵΪ���ʽ���������ʽ��ֵ���õ�����ֵ
		if(this.getExpressionValue() != null)
            try {
            	Object value_ = getFormulaValue(getExpressionValue());
            	if(value_ != null)
            	{
//            		value = String.valueOf(value_);
            		value = value_;
            	}
            	else
            	{
            		value = null;
            	}
            } catch (FormulaException e) {
                e.printStackTrace();
                return SKIP_BODY;                
            }
		if(match())		
			return EVAL_BODY_INCLUDE;
		else
			return SKIP_BODY;
	}
	
	public int doEndTag() throws JspException 
	{
		int ret = super.doEndTag();
		this.actualValue = null;
		this.addTruevalue = false; 
		this.value = null;
		this.expressionValue = null;
		this.pattern = null;
		this.scope = null;
		this.scopes = null;
//		this.requestKey = null ;
//		this.sessionKey= null ;
//		this.pageContextKey= null ;
//		this.parameter= null ;
		
		return ret;
		
	}
	
	protected abstract boolean match();

    /**
     * @return Returns the addTruevalue.
     */
    public boolean isAddTruevalue() {
        return addTruevalue;
    }
    /**
     * @param addTruevalue The addTruevalue to set.
     */
    public void setAddTruevalue(boolean addTruevalue) {
        this.addTruevalue = addTruevalue;
    }
    /**
     * @return Returns the expressionValue.
     */
    public String getExpressionValue() {
        return expressionValue;
    }
    /**
     * @param expressionValue The expressionValue to set.
     */
    public void setExpressionValue(String expressionValue) {
        this.expressionValue = expressionValue;
    }

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String[] getScopes() {
		if(scopes == null)
			calculatescope();
		return scopes;
	}

	private void calculatescope() {
		Object temp = super.getFormulaValueWithAttribute(scope);
		if(temp != null)
		{
			this.scopes = StringUtil.split(temp.toString(),"\\,");
		}
		else
		{
			this.scopes = StringUtil.split(scope,"\\,");
		}
			
		
		
	}

	
	public Object getActualValue()
	{
	
		return actualValue;
	}

	
	public void setActualValue(Object actualValue)
	{
	
		this.actualValue = actualValue;
	}

	
	
}
