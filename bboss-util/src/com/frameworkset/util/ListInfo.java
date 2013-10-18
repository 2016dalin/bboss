package com.frameworkset.util;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Title: ListInfo</p>
 *
 * <p>Description:
 *    ��װ��ҳ��Ϣ
 * </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author biaoping.yin
 * @version 1.0
 */
public class ListInfo implements Serializable{
    /**�Ƿ���ʾ���е�����*/
    private boolean showAll = false;
    /*
    * ��ʾ�������ֶ�����
	 */
	public static final int DEFAULT_MAX_ITEMS = Integer.MAX_VALUE,
			DEFAULT_MAX_PAGE_ITEMS = 10, DEFAULT_MAX_INDEX_PAGES = 10;
    
    private int maxPageItems = DEFAULT_MAX_PAGE_ITEMS;

    /**��ȡ��������*/
    private long totalSize;
    /**
     * ʵ�ʴ����ݿ��ѯ���ļ�¼����������ֿ��ܺ�Ӧ�ò����getSize()����
     * �õ������ݲ�һ�£���ΪӦ�ó�����ܻ��޸����ݼ��е����ݣ����ӻ���ɾ����¼��
     * ����more��ѯʱ�����Ƿ�ﵽ��������һ����¼ʱ��Ҫʹ��resultSize
     */
    private int resultSize;
    /***/
    private Serializable object;
    
    /**
     * �ж����ݼ����Ƿ���ֱ�Ӵ�dbutil�л�ȡ
     * Ϊtrueʱ��ʾֱ�Ӵ�dbutil�л�ȡ��������
     */
    private boolean isdbdata = false;


   /**
    * ����ȡ����ǰҳ�ļ�¼����
    */
    private List datas;
    private Object[] dbDatas;
    /**
	 * more��ҳ��ѯ����������ܼ�¼�������û�м�¼��ô���ص�ListInfo��datas��sizeΪ0,
	 * �������ܣ�ͬʱǰ̨��ǩ��Ҳ������Ӧ�ĵ���
	 */
    private boolean more = false;
   
    
    /**
	 * @return the more
	 */
	public boolean isMore() {
		return more;
	}
	/**
	 * @param more the more to set
	 */
	public void setMore(boolean more) {
		this.more = more;
	}
    public ListInfo()
   {

   }
   
    /**
     * added by biaoping.yin on 20080728
     * @return
     */
   public Object getObjectDatas()
   {
	   if(!this.isdbdata())
	   {
		   return this.datas;
	   }
	   else
	   {
		   return this.dbDatas;
	   }
   }
   /**
    * Access method for the datas property.
    *
    * @return   the current value of the datas property
    */
   public List getDatas()
   {
       return datas;
   }

   /**
    * Sets the value of the datas property.
    *
    * @param aDatas the new value of the datas property
    */
   public void setDatas(List aDatas)
   {
       datas = aDatas;
   }

   public String toString()
   {
       return "ListInfo:"+ this.getClass() + "\r\ndatas:" + datas + "\r\ntotalSize:" + totalSize;
   }
   /**
    * @return Returns the dbDatas.
    */
   public Object[] getArrayDatas() {
       return dbDatas;
   }
   /**
    * @param dbDatas The dbDatas to set.
    */
   public void setArrayDatas(Object[] dbDatas) {
       this.dbDatas = dbDatas;
       this.isdbdata = true;
   }
   
   public boolean isdbdata()
   {
	   return this.isdbdata;
   }

    public Serializable getObject() {
        return object;
    }

    public boolean isShowAll() {
        return showAll;
    }


    public long getTotalSize() {
        return totalSize;
    }
    /**
     * ��ȡ���ս�����еĵ�ҳ��¼��
     * @return
     */
    public int getSize()
    {
    	if(!isdbdata())
    	{
    		return this.datas != null ?this.datas.size():0;
    		
    	}
    	else
    	{
    		return this.dbDatas != null ?this.dbDatas.length:0;
    	}
    }
    
    



    public void setObject(Serializable object) {
        this.object = object;
    }

    public void setShowAll(boolean showAll) {
        this.showAll = showAll;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

	/**
	 * @return the maxPageItems
	 */
	public int getMaxPageItems() {
		return maxPageItems;
	}

	/**
	 * @param maxPageItems the maxPageItems to set
	 */
	public void setMaxPageItems(int maxPageItems) {
		this.maxPageItems = maxPageItems;
	}
	/**
	 * ��ȡԭʼ��ѯ���ݿ�õ��ĵ�ҳ��¼����
	 * @return the resultSize
	 */
	public int getResultSize() {
		return resultSize;
	}
	/**
	 * @param resultSize the resultSize to set
	 */
	public void setResultSize(int resultSize) {
		this.resultSize = resultSize;
	}
}
