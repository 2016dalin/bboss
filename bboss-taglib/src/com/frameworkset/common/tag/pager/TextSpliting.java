package com.frameworkset.common.tag.pager;

import java.io.Serializable;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author biaoping.yin
 * @version 1.0
 */
public class TextSpliting implements Serializable{

    /**
     * ��ȡ����ҳ����ı����ݣ�ҳ����볬��ҳ��ĺ��뷶Χʱ��
     * ȡ��ҳ��ҳ�����С��0���������һҳ��ҳ�����������һҳ��ҳ�룩
     * @param text String �ı�����
     * @param pageNumber int ҳ�����
     * @param pageSize int ÿҳ��ʾ���ı�����
     * @return TextListInfo ��װ������Ϣ��
     *                      ��ҳ������
     *                      ��ǰҳ����룬
     *                      ��ǰҳ����ʾ���ı�
     *
     */
    public static TextListInfo splitStringByPageNumber(String text,int pageNumber,int pageSize)
    {
        return splitString(text,(pageNumber -1) * pageSize,pageSize);
    }
    public static TextListInfo splitString(String text,int offset,int pageSize)
    {
        TextListInfo listInfo = new TextListInfo();
        if(text == null && text.length() == 0)
        {
            listInfo.setTotal(0);
            listInfo.setCurPage(0);
            listInfo.setCurValue("");
            return listInfo;
        }
        int length = text.length();

        int total = length / pageSize ;
        if(length % pageSize != 0)
            total += 1;

        if(offset < 0)
            offset = 0;
        else
        {

            if (offset >= length) {
                int size = length % pageSize;
                if(size == 0)
                {
                    offset = length - pageSize ;
                }
                else
                {
                    offset = length - size ;
                }
            }
        }
        int end = offset + pageSize < length? offset + pageSize: length ;
        String curvalue = text.substring(offset,end);
        listInfo.setCurValue(curvalue);
        listInfo.setCurPage(offset/pageSize + 1);
        listInfo.setTotal(total);
        return listInfo;
    }

    public static void main(String[] args)
    {
        String test = "�л����񹲺͹������ˣ������л����񹲺͹����꣡������";
        for(int i = 1; i <= 13; i ++)
        System.out.println( TextSpliting.splitStringByPageNumber(test,i,2));
    }

}
