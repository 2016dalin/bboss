/*****************************************************************************
 *                                                                           *
 *  This file is part of the tna framework distribution.                     *
 *  Documentation and updates may be get from  biaoping.yin the author of    *
 *  this framework							     							 *
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
 *                                                                           *
 *****************************************************************************/
package com.frameworkset.common.tag.pager.model;


/**
 * �����ĵ��е�Ҫ��ʾ�ֶ���Ϣ
 * @author biaoping.yin
 * created on 2005-5-18
 * version 1.0 
 */
public class Field extends UniqueHelper implements ModelObject{
    
    /**
     * �ֶ����ƣ���Ϊ���ݿ���ӶΣ�Ҳ����Ϊ�����е������ֶ�
     */
    private String field;
    
    private int colid = -1;
    
    /**
     * �ֶζ�Ӧ�ı�ͷ����
     */
    private String title;
    /**
     * �ֶ������ʽ
     */
    private String numerFormat;
    
    /**
     * �ֶ�������ڸ�ʽ
     */
    private String dateFormat;
    
    /**
     * �ֶ�ֵ�ļ��㹫ʽ
     */
    private Formula formula;
    
    /**
     * �ֶ�Ϊö������ʱ������ÿ��ֵ����Ӧ����ֵ������
     * ֵ"1"��Ӧ����ֵΪ"��",ֵ"0"��Ӧ����ֵΪ"Ů"
     */
    private TrueValue trueValue;
    

	/**
	 * @return Returns the field.
	 */
	public String getField() {
		return field;
	}
	/**
	 * @param field The field to set.
	 */
	public void setField(String field) {
		this.field = field;
	}
	
	/**
	 * @return Returns the title.
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title The title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return Returns the trueValue.
	 */
	public TrueValue getTrueValue() {
		return trueValue;
	}
	/**
	 * @param trueValue The trueValue to set.
	 */
	public void setTrueValue(TrueValue trueValue) {
		this.trueValue = trueValue;
	}
    /**
     * @return Returns the colid.
     */
    public int getColid() {
        return colid;
    }
    /**
     * @param colid The colid to set.
     */
    public void setColid(int colid) {
        this.colid = colid;
    }
   
    /**
     * @return Returns the dateFormat.
     */
    public String getDateFormat() {
        return dateFormat;
    }
    /**
     * @param dateFormat The dateFormat to set.
     */
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }
    /**
     * @return Returns the numerFormat.
     */
    public String getNumerFormat() {
        return numerFormat;
    }
    /**
     * @param numerFormat The numerFormat to set.
     */
    public void setNumerFormat(String numerFormat) {
        this.numerFormat = numerFormat;
    }
    /**
     * @return Returns the formula.
     */
    public Formula getFormula() {
        return formula;
    }
    /**
     * @param formula The formula to set.
     */
    public void setFormula(Formula formula) {
        this.formula = formula;
    }
}
