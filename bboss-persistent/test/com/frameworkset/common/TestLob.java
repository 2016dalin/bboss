/*
 *  Copyright 2008 biaoping.yin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.frameworkset.common;

import java.sql.SQLException;

import javax.transaction.RollbackException;

import oracle.sql.BLOB;
import oracle.sql.CLOB;

import org.junit.BeforeClass;
import org.junit.Test;

import com.frameworkset.common.poolman.CallableDBUtil;
import com.frameworkset.common.poolman.DBUtil;
import com.frameworkset.common.poolman.PreparedDBUtil;
import com.frameworkset.orm.transaction.TransactionManager;

public class TestLob {
    @BeforeClass
    public static void createTable()
    {
        String proce = 
//            "CREATE   OR   REPLACE   PROCEDURE   tt"+   
//                    " AS  " +
                    "begin "+
                        "execute immediate 'drop table test'; "+
                        "execute immediate 'create table test(id number(10),blobname blob,clobname clob)'; "+
                        "execute immediate 'delete from TABLEINFO where table_name=''test'''; "+
                        "execute immediate 'INSERT INTO TABLEINFO ( TABLE_NAME, TABLE_ID_NAME, TABLE_ID_INCREMENT, TABLE_ID_VALUE,"+
                        "TABLE_ID_GENERATOR, TABLE_ID_TYPE, TABLE_ID_PREFIX ) VALUES ("+
                        "''test'', ''id'', 1, 0, NULL, ''int'', NULL)'; "+
                        "commit; "+
                        "EXCEPTION "+
                        "when others then "+
                        "execute immediate 'create table test(id number(10),blobname blob,clobname clob)'; " +
                        "execute immediate 'delete from TABLEINFO where table_name=''test'''; "+
                        "execute immediate 'INSERT INTO TABLEINFO ( TABLE_NAME, TABLE_ID_NAME, TABLE_ID_INCREMENT, TABLE_ID_VALUE,"+
                        "TABLE_ID_GENERATOR, TABLE_ID_TYPE, TABLE_ID_PREFIX ) VALUES ("+
                        "''test'', ''id'', 1, 0, NULL, ''int'', NULL)'; "+
                        "commit;"+
                        "END;";
        
//        String proce = "{begin "+
//                                "execute immediate 'drop table test'; "+
//                                "execute immediate 'create table test(id number(10),blobname blob,clobname clob)'; "+
//                                "EXCEPTION "+
//                                "when others then "+
//                                "execute immediate 'create table test(id number(10),blobname blob,clobname clob)'; "+
//                                "END;}";
      
        CallableDBUtil dbutil = new CallableDBUtil();
        try
        {
            dbutil.prepareCallable(proce);
            
            dbutil.executeCallable();
//            dbutil.execute(proce);
//            CallableDBUtil dbutil_ = new CallableDBUtil();
//            dbutil_.prepareCallable("{call tt()}");
//         
//            dbutil_.executeCallable();
          
        }
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        dbutil.prepareCallable(proce);
//        try
//        {
//            dbutil.executeCallable();
//        }
//        catch (SQLException e)
//        {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }
//  @Test
//  public void testBlobWrite()
//  {
//      
//  }
	/**
     * ��һ�ֲ���blob�ֶεķ�����ͨ�õ�ģʽ
	 */
    @Test
	public void testBlobWrite()
	{
		PreparedDBUtil dbUtil = new PreparedDBUtil();
		try {
			dbUtil.preparedInsert( "insert into test(id,blobname) values(?,?)");
			
			dbUtil.setString(1, DBUtil.getNextStringPrimaryKey("test"));
			dbUtil.setBlob(2, new java.io.File("d:/dominspector.rar"));//ֱ�ӽ��ļ��洢�����ֶ���			
			dbUtil.executePrepared();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			
		}
		
	}
	
	
	/**
	 * ���oracle Blob�ֶεĲ������
	 */
    @Test
	public void testBigBlobWrite()
	{
		PreparedDBUtil dbUtil = new PreparedDBUtil();
		TransactionManager tm = new TransactionManager();
		try {
			//��������
			tm.begin();
			//�Ȳ���һ����¼,blob�ֶγ�ʼ��Ϊempty_lob
			dbUtil.preparedInsert( "insert into test(id,blobname) values(?,?)");
			String id = DBUtil.getNextStringPrimaryKey("test");
			dbUtil.setString(1, id);
			dbUtil.setBlob(2,BLOB.empty_lob());//�����ÿյ�blob�ֶ�
			
			
			dbUtil.executePrepared();
			
			//���ҸղŵĲ���ļ�¼���޸�blob�ֶε�ֵΪһ���ļ�
			dbUtil = new PreparedDBUtil();
			dbUtil.preparedSelect("select blobname from test where id = ?");
			dbUtil.setString(1, id);
			dbUtil.executePrepared();
			
			BLOB blob = (BLOB)dbUtil.getBlob(0, "blobname");
			if(blob != null)
			{				
				DBUtil.updateBLOB(blob, new java.io.File("d:/dominspector.rar"));
			}
			tm.commit();
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				tm.rollback();
			} catch (RollbackException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		finally
		{
			tm = null;
			dbUtil = null;
		}
		
	}
	
	
	/**
	 * ���ֶεĶ�ȡ
	 */
    @Test
	public void testBlobRead()
	{
		PreparedDBUtil dbUtil = new PreparedDBUtil();
		try {
			//��ѯ���ֶ����ݲ��ҽ����ֶδ�ŵ��ļ���
			dbUtil.preparedSelect( "select id,blobname from test");
			dbUtil.executePrepared();
			
			for(int i = 0; i < dbUtil.size(); i ++)
			{
				
				dbUtil.getFile(i, "blobname", new java.io.File("e:/dominspector.rar"));//��blob�ֶε�ֵת��Ϊ�ļ�
//				Blob blob = dbUtil.getBlob(i, "blobname");//��ȡblob�ֶε�ֵ��blob�����С�
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			
		}
		
	}
	
	/**
	 * clob�ֶε�д��
	 */
    @Test
	public void testClobWrite()
	{
		PreparedDBUtil dbUtil = new PreparedDBUtil();
		try {
			dbUtil.preparedInsert( "insert into test(id,clobname) values(?,?)");
			
			dbUtil.setString(1, DBUtil.getNextStringPrimaryKey("test"));
			dbUtil.setClob(2,"clobvalue");//ֱ�ӽ��ַ����洢��clob�ֶ���
			dbUtil.executePrepared();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			
		}
		
	}
	
	/**
	 * ���oracle Clob�ֶεĲ������
	 */
    @Test
	public void testBigClobWrite()
	{
		PreparedDBUtil dbUtil = new PreparedDBUtil();
		TransactionManager tm = new TransactionManager();
		try {
			//��������
			tm.begin();
			//�Ȳ���һ����¼,blob�ֶγ�ʼ��Ϊempty_lob
			dbUtil.preparedInsert( "insert into test(id,clobname) values(?,?)");
			String id = DBUtil.getNextStringPrimaryKey("test");
			dbUtil.setString(1, id);
			dbUtil.setClob(2,CLOB.empty_lob());//�����ÿյ�blob�ֶ�
			
			
			dbUtil.executePrepared();
			
			//���ҸղŵĲ���ļ�¼���޸�blob�ֶε�ֵΪһ���ļ�
			dbUtil = new PreparedDBUtil();
			dbUtil.preparedSelect("select clobname from test where id = ?");
			dbUtil.setString(1, id);
			dbUtil.executePrepared();
			
			CLOB clob = (CLOB)dbUtil.getClob(0, "clobname");
			if(clob != null)
			{				
				DBUtil.updateCLOB(clob, new java.io.File("d:\\route.txt"));
			}
			tm.commit();
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				tm.rollback();
			} catch (RollbackException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		finally
		{
			tm = null;
			dbUtil = null;
		}
		
	}
	
	/**
	 * clob�ֶεĶ�ȡ
	 */
    @Test
	public void testClobRead()
	{
		PreparedDBUtil dbUtil = new PreparedDBUtil();
		try {
			//��ѯ���ֶ����ݲ��ҽ����ֶδ�ŵ��ļ���
			dbUtil.preparedSelect( "select id,clobname from test");
			dbUtil.executePrepared();
			
			for(int i = 0; i < dbUtil.size(); i ++)
			{
				
				dbUtil.getFile(i, "clobname", new java.io.File("d:/route" + i + ".txt")); //��ȡclob�ֶε��ļ���
//				String clobvalue = dbUtil.getString(i, "clobname");//��ȡclob�ֶε��ַ���������
//				Clob clob = dbUtil.getClob(i, "clobname");//��ȡclob�ֶ�ֵ��clob���ͱ�����
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			dbUtil = null;
		}
		
	}
	
	public void main(String[] args)
	{
		System.out.print("start.........");
		DBUtil.debugMemory();
		for(int i = 0; i < 1; i ++)
		{
			testBlobWrite();
//			testBigBlobWrite();
//			testClobWrite();
//			testBigClobWrite();
			
		}
		
//		testBlobWrite();
//		testBigBlobWrite();
		DBUtil.debugMemory();
		testBlobRead();
//		testClobRead();
		System.out.print("end.........");
		DBUtil.debugMemory();
	}
	
	

}
