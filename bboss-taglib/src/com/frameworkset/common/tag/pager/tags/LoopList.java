/*
 *
 * Title: The ERP System of kelamayi Downhole Company [PMIP]
 *
 * Copyright: Copyright (c) 2004
 *
 * Company: westerasoft Co., Ltd
 * 
 * All right reserved.
 * 
 * Created on 2004-11-8
 * 
 * JDK version used		:1.4.1
 * 
 * Modification history:
 *
 */
package com.frameworkset.common.tag.pager.tags;

import java.io.OutputStream;
import java.util.List;

import com.frameworkset.common.tag.BaseTag;
/**
 * ���ܣ������ṩ��list����ѭ������ʾÿһ��list������
 * �ñ�ǩ��Ҫ��PagerDataSet��������ǩ�ڲ�ʹ��
 * @author zhiguo.wang
 * @version 1.0
 */
public class LoopList extends BaseTag
{
	
	
	/**
	 * ��Ҫѭ����list����
	 */
	private String listname=null;
	
	private int loopID=0;
		
	private List objList=null;
	
	public void setListname(String name){
		listname = name;
		
	}
	
	public String getListname(){
		return listname;
	}
	
	public int getLoopID(){
		
		return loopID;
	}
	
	public List getObjList(){
		return objList;
	}
	
	
	public int doStartTag(){
		
		if(getListname()==null){
			return SKIP_BODY;
		}
		
		PagerDataSet dataSet =
						(PagerDataSet) findAncestorWithClass(this, PagerDataSet.class);

		Object newObj = dataSet.getValue(dataSet.getRowid(),getListname());
		
		if(newObj!=null&&(newObj instanceof List)){
			
			objList = (List)newObj;
			if(objList.size()>0)
				return EVAL_BODY_INCLUDE;
			
		}
		
		return SKIP_BODY;
		
	}
	

	public int doAfterBody(){
		
		if(loopID < objList.size() - 1)
		{
			loopID ++;
			return EVAL_BODY_AGAIN;
		}
		
		return SKIP_BODY;
		
	}





	/**
	 *  Description:
	 * @return String
	 * @see com.frameworkset.common.tag.BaseTag#generateContent()
	 */
	public String generateContent()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 *  Description:
	 * @param output
	 * @see com.frameworkset.common.tag.BaseTag#write(java.io.OutputStream)
	 */
	public void write(OutputStream output)
	{
		// TODO Auto-generated method stub
		
	}

}
