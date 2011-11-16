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
package com.frameworkset.derby;

import java.sql.SQLException;

import javax.transaction.RollbackException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.frameworkset.common.poolman.CallableDBUtil;
import com.frameworkset.common.poolman.DBUtil;
import com.frameworkset.common.poolman.PreparedDBUtil;
import com.frameworkset.common.poolman.Record;
import com.frameworkset.common.poolman.handle.NullRowHandler;
import com.frameworkset.orm.transaction.TransactionManager;

public class TestLob {
    @BeforeClass
    public static void createTable()
    {
        String droptableinfo = "" +
        		" drop table TABLEINFO ";

        String createtableinfo = " CREATE TABLE TABLEINFO "+
        "("+
        " TABLE_NAME          VARCHAR(255)        NOT NULL,"+
        "TABLE_ID_NAME       VARCHAR(255),"+
        "TABLE_ID_INCREMENT  int                 DEFAULT 1,"+
        "TABLE_ID_VALUE      int              DEFAULT 0,"+
        "TABLE_ID_GENERATOR  VARCHAR(255),"+
        "TABLE_ID_TYPE       VARCHAR(255),"+
        "TABLE_ID_PREFIX     VARCHAR(255)"+
        ")";
        String addpk = "ALTER TABLE TABLEINFO ADD   CONSTRAINT PK_TABLEINFO0 PRIMARY KEY (TABLE_NAME)";
//        " COMMENT ON TABLE TABLEINFO IS ''����Ϣά������'';"+
//        " execute immediate 'COMMENT ON COLUMN TABLEINFO.TABLE_NAME IS ''������''';"+
//        " execute immediate 'COMMENT ON COLUMN TABLEINFO.TABLE_ID_NAME IS ''�����������''';"+
//        " execute immediate 'COMMENT ON COLUMN TABLEINFO.TABLE_ID_INCREMENT IS ''�������������ȱʡΪ1''';"+
//        " execute immediate 'COMMENT ON COLUMN TABLEINFO.TABLE_ID_VALUE IS ''������ǰֵ��ȱʡΪ0''';"+
//        " execute immediate 'COMMENT ON COLUMN TABLEINFO.TABLE_ID_GENERATOR IS ''�Զ�����������ɻ���"+
//        "�����"+
//        "com.frameworkset.common.poolman.sql.PrimaryKey����''';"+
//        " execute immediate 'COMMENT ON COLUMN TABLEINFO.TABLE_ID_TYPE IS ''�������ͣ�string,int��''';"+
//        " execute immediate 'COMMENT ON COLUMN TABLEINFO.TABLE_ID_PREFIX IS ''����Ϊstring������ǰ׺����ָ���ɲ�ָ��,ȱʡֵΪ��''';"+
//        " execute immediate 'CREATE UNIQUE INDEX PK_TABLEINFO0 ON TABLEINFO(TABLE_NAME)';"+
//        " execute immediate 'ALTER TABLE TABLEINFO ADD   CONSTRAINT PK_TABLEINFO0 PRIMARY KEY (TABLE_NAME)';" +
//        " exception" +
//        " when others then " +
//        " execute immediate 'CREATE TABLE TABLEINFO ( TABLE_NAME          VARCHAR2(255)        NOT NULL,TABLE_ID_NAME       VARCHAR2(255),TABLE_ID_INCREMENT  NUMBER(5)                 DEFAULT 1,TABLE_ID_VALUE      NUMBER(20)                DEFAULT 0,TABLE_ID_GENERATOR  VARCHAR2(255),TABLE_ID_TYPE       VARCHAR2(255),TABLE_ID_PREFIX     VARCHAR2(255))';" +
//        " execute immediate 'COMMENT ON TABLE TABLEINFO IS ''����Ϣά������''';" +
//        " execute immediate 'COMMENT ON COLUMN TABLEINFO.TABLE_NAME IS ''������''';" +
//        " execute immediate 'COMMENT ON COLUMN TABLEINFO.TABLE_ID_NAME IS ''�����������''';" +
//        " execute immediate 'COMMENT ON COLUMN TABLEINFO.TABLE_ID_INCREMENT IS ''�������������ȱʡΪ1''';" +
//        " execute immediate 'COMMENT ON COLUMN TABLEINFO.TABLE_ID_VALUE IS ''������ǰֵ��ȱʡΪ0''';" +
//        " execute immediate 'COMMENT ON COLUMN TABLEINFO.TABLE_ID_GENERATOR IS ''�Զ�����������ɻ��Ʊ����com.frameworkset.common.poolman.sql.PrimaryKey����''';" +
//        " execute immediate 'COMMENT ON COLUMN TABLEINFO.TABLE_ID_TYPE IS ''�������ͣ�string,int��''';" +
//        " execute immediate 'COMMENT ON COLUMN TABLEINFO.TABLE_ID_PREFIX IS ''����Ϊstring������ǰ׺����ָ���ɲ�ָ��,ȱʡֵΪ��''';" +
//        " execute immediate 'CREATE UNIQUE INDEX PK_TABLEINFO0 ON TABLEINFO(TABLE_NAME)';" +
//        " execute immediate 'ALTER TABLE TABLEINFO ADD   CONSTRAINT PK_TABLEINFO0 PRIMARY KEY (TABLE_NAME)';" +
//        " end;";
        
//        String tableinfo = "begin " +
//        " execute immediate 'drop table TABLEINFO cascade constraints';"+
//" execute immediate 'CREATE TABLE TABLEINFO "+
//"("+
//" TABLE_NAME          VARCHAR2(255)        NOT NULL,"+
//"TABLE_ID_NAME       VARCHAR2(255),"+
//"TABLE_ID_INCREMENT  NUMBER(5)                 DEFAULT 1,"+
//"TABLE_ID_VALUE      NUMBER(20)                DEFAULT 0,"+
//"TABLE_ID_GENERATOR  VARCHAR2(255),"+
//"TABLE_ID_TYPE       VARCHAR2(255),"+
//"TABLE_ID_PREFIX     VARCHAR2(255)"+
//")';"+
//" execute immediate 'COMMENT ON TABLE TABLEINFO IS ''����Ϣά������''';"+
//" execute immediate 'COMMENT ON COLUMN TABLEINFO.TABLE_NAME IS ''������''';"+
//" execute immediate 'COMMENT ON COLUMN TABLEINFO.TABLE_ID_NAME IS ''�����������''';"+
//" execute immediate 'COMMENT ON COLUMN TABLEINFO.TABLE_ID_INCREMENT IS ''�������������ȱʡΪ1''';"+
//" execute immediate 'COMMENT ON COLUMN TABLEINFO.TABLE_ID_VALUE IS ''������ǰֵ��ȱʡΪ0''';"+
//" execute immediate 'COMMENT ON COLUMN TABLEINFO.TABLE_ID_GENERATOR IS ''�Զ�����������ɻ���"+
//"�����"+
//"com.frameworkset.common.poolman.sql.PrimaryKey����''';"+
//" execute immediate 'COMMENT ON COLUMN TABLEINFO.TABLE_ID_TYPE IS ''�������ͣ�string,int��''';"+
//" execute immediate 'COMMENT ON COLUMN TABLEINFO.TABLE_ID_PREFIX IS ''����Ϊstring������ǰ׺����ָ���ɲ�ָ��,ȱʡֵΪ��''';"+
//" execute immediate 'CREATE UNIQUE INDEX PK_TABLEINFO0 ON TABLEINFO(TABLE_NAME)';"+
//" execute immediate 'ALTER TABLE TABLEINFO ADD   CONSTRAINT PK_TABLEINFO0 PRIMARY KEY (TABLE_NAME)';" +
//" exception" +
//" when others then " +
//" execute immediate 'CREATE TABLE TABLEINFO ( TABLE_NAME          VARCHAR2(255)        NOT NULL,TABLE_ID_NAME       VARCHAR2(255),TABLE_ID_INCREMENT  NUMBER(5)                 DEFAULT 1,TABLE_ID_VALUE      NUMBER(20)                DEFAULT 0,TABLE_ID_GENERATOR  VARCHAR2(255),TABLE_ID_TYPE       VARCHAR2(255),TABLE_ID_PREFIX     VARCHAR2(255))';" +
//" execute immediate 'COMMENT ON TABLE TABLEINFO IS ''����Ϣά������''';" +
//" execute immediate 'COMMENT ON COLUMN TABLEINFO.TABLE_NAME IS ''������''';" +
//" execute immediate 'COMMENT ON COLUMN TABLEINFO.TABLE_ID_NAME IS ''�����������''';" +
//" execute immediate 'COMMENT ON COLUMN TABLEINFO.TABLE_ID_INCREMENT IS ''�������������ȱʡΪ1''';" +
//" execute immediate 'COMMENT ON COLUMN TABLEINFO.TABLE_ID_VALUE IS ''������ǰֵ��ȱʡΪ0''';" +
//" execute immediate 'COMMENT ON COLUMN TABLEINFO.TABLE_ID_GENERATOR IS ''�Զ�����������ɻ��Ʊ����com.frameworkset.common.poolman.sql.PrimaryKey����''';" +
//" execute immediate 'COMMENT ON COLUMN TABLEINFO.TABLE_ID_TYPE IS ''�������ͣ�string,int��''';" +
//" execute immediate 'COMMENT ON COLUMN TABLEINFO.TABLE_ID_PREFIX IS ''����Ϊstring������ǰ׺����ָ���ɲ�ָ��,ȱʡֵΪ��''';" +
//" execute immediate 'CREATE UNIQUE INDEX PK_TABLEINFO0 ON TABLEINFO(TABLE_NAME)';" +
//" execute immediate 'ALTER TABLE TABLEINFO ADD   CONSTRAINT PK_TABLEINFO0 PRIMARY KEY (TABLE_NAME)';" +
//" end;";
        
        String proce = 
            "CREATE   OR   REPLACE   PROCEDURE   tt"+   
                    " AS  " +
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
//            CallableDBUtil dbutil_ = new CallableDBUtil();
            
            
            
            dbutil.prepareCallable("derby",droptableinfo);
            
            dbutil.executeCallable();}
        catch (SQLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
            try
            {
                dbutil.prepareCallable("derby",createtableinfo);
                
                dbutil.executeCallable();
                
                dbutil.prepareCallable("derby",addpk);                
                
                dbutil.executeCallable();
                
                try
                {
                    String droptest = "drop table test";
                    dbutil.prepareCallable("derby",droptest);                
                    
                    dbutil.executeCallable();
                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                String createtest = "create table test(id int,blobname blob,clobname clob)";
//                String proce_tt = 
//                    "CREATE  PROCEDURE   tt"+   
//                            "" +
//                            "begin null; end;";
//                
                dbutil.prepareCallable("derby",createtest);                
                
                dbutil.executeCallable();
            }
            catch (SQLException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
//            try
//            {
//                dbutil.prepareCallable("derby",proce);
//                dbutil.executeCallable();
//            }
//            catch (SQLException e)
//            {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            dbutil.execute(proce);
//            CallableDBUtil dbutil_ = new CallableDBUtil();
//            dbutil_.prepareCallable("{call tt()}");
//         
//            dbutil_.executeCallable();
          
        
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
	 * @throws Exception 
	 */
    @Test
	public void testBlobWrite() throws Exception
	{
		PreparedDBUtil dbUtil = new PreparedDBUtil();
		try {
			dbUtil.preparedInsert("derby", "insert into test(id,blobname) values(?,?)");
			
			dbUtil.setString(1, DBUtil.getNextStringPrimaryKey("test"));
			dbUtil.setBlob(2, new java.io.File("resources/lob/manager.rar"));//ֱ�ӽ��ļ��洢�����ֶ���
			
			dbUtil.executePrepared();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
		finally
		{
			
		}
		
	}
//	
	
	/**
	 * ���oracle Blob�ֶεĲ������
	 */
    @Test
	public void testBigBlobWrite() throws Exception
	{
		PreparedDBUtil dbUtil = new PreparedDBUtil();
		TransactionManager tm = new TransactionManager();
		try {
			//��������
			tm.begin();
			//�Ȳ���һ����¼,blob�ֶγ�ʼ��Ϊempty_lob
			dbUtil.preparedInsert("derby", "insert into test(id,blobname) values(?,?)");
			String id = DBUtil.getNextStringPrimaryKey("test");
			dbUtil.setString(1, id);
			dbUtil.setNull(2,java.sql.Types.BLOB);//�����ÿյ�blob�ֶ�
			
			
			dbUtil.executePrepared();
			
			//���ҸղŵĲ���ļ�¼���޸�blob�ֶε�ֵΪһ���ļ�
			dbUtil = new PreparedDBUtil();
			dbUtil.preparedUpdate("derby","update test set  blobname =? where id = ?");
			dbUtil.setBlob(1, new java.io.File("resources/lob/manager.rar"));
			dbUtil.setString(2, id);
			
			dbUtil.executePrepared();
			
//			BLOB blob = (BLOB)dbUtil.getBlob(0, "blobname");
//			if(blob != null)
//			{				
//				DBUtil.updateBLOB(blob, new java.io.File("resources/lob/manager.rar"));
//			}
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
			throw e;
		}
		finally
		{
			tm = null;
			dbUtil = null;
		}
		
	}
//	
//	
	/**
	 * ���ֶεĶ�ȡ
	 */
    @Test
	public void testBlobRead()  throws Exception
	{
//        TransactionManager tm = new TransactionManager(); 
		PreparedDBUtil dbUtil = new PreparedDBUtil();
		try {

			//��ѯ���ֶ����ݲ��ҽ����ֶδ�ŵ��ļ���
			dbUtil.preparedSelect("derby", "select id,blobname from test");

			dbUtil.executePreparedWithRowHandler(new NullRowHandler(){

                @Override
                public void handleRow(Record origine) throws Exception
                {
                    origine.getFile("blobname", new java.io.File("resources/lob/reader/dominspector_" + origine.getRowid() +".rar"));
                }});
			
			System.out.println("testClobRead dbUtil.size():"+dbUtil.size());
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
		finally
		{
			
		}
		
	}
//	
	/**
	 * clob�ֶε�д��
	 */
    @Test
	public void testClobWrite()  throws Exception
	{
		PreparedDBUtil dbUtil = new PreparedDBUtil();
		try {
			dbUtil.preparedInsert("derby",  "insert into test(id,clobname) values(?,?)");
			
			dbUtil.setString(1, DBUtil.getNextStringPrimaryKey("test"));
			dbUtil.setClob(2,"clobvalue");//ֱ�ӽ��ַ����洢��clob�ֶ���
			dbUtil.executePrepared();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		    throw e;
		}
		finally
		{
			
		}
		
	}
	
	/**
	 * ���oracle Clob�ֶεĲ������
	 */
    @Test
	public void testBigClobWrite()  throws Exception
	{
		PreparedDBUtil dbUtil = new PreparedDBUtil();
		TransactionManager tm = new TransactionManager();
		try {
			//��������
			tm.begin();
			//�Ȳ���һ����¼,blob�ֶγ�ʼ��Ϊempty_lob
			dbUtil.preparedInsert("derby",  "insert into test(id,clobname) values(?,?)");
			String id = DBUtil.getNextStringPrimaryKey("test");
			dbUtil.setString(1, id);
			dbUtil.setNull(2,java.sql.Types.CLOB);//�����ÿյ�blob�ֶ�
			
			
			dbUtil.executePrepared();
			
			//���ҸղŵĲ���ļ�¼���޸�blob�ֶε�ֵΪһ���ļ�
			dbUtil = new PreparedDBUtil();
			dbUtil.preparedUpdate("derby", "update test set clobname =? where id = ?");
			dbUtil.setClob(1, new java.io.File("resources/lob/route.txt"));
			dbUtil.setString(2, id);
			dbUtil.executePrepared();
			
//			CLOB clob = (CLOB)dbUtil.getClob(0, "clobname");
//			if(clob != null)
//			{				
//				DBUtil.updateCLOB(clob, new java.io.File("resources/lob/route.txt"));
//			}
			tm.commit();
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			try {
				tm.rollback();
			} catch (RollbackException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			throw e;
		}
		finally
		{
			tm = null;
			dbUtil = null;
		}
		
	}
//	
	/**
	 * clob�ֶεĶ�ȡ
	 */
    @Test
	public void testClobRead()  throws Exception
	{
		PreparedDBUtil dbUtil = new PreparedDBUtil();
		try {
			//��ѯ���ֶ����ݲ��ҽ����ֶδ�ŵ��ļ���
			dbUtil.preparedSelect( "derby", "select id,clobname from test");
			dbUtil.executePreparedWithRowHandler(new NullRowHandler(){

                @Override
                public void handleRow(Record origine) throws Exception
                {
                    origine.getFile("clobname", new java.io.File("resources/lob/reader/route" + origine.getRowid() + ".txt"));
                    
                }});
			System.out.println("testClobRead dbUtil.size():"+dbUtil.size());
//			for(int i = 0; i < dbUtil.size(); i ++)
//			{
//				
//				dbUtil.getFile(i, "clobname", new java.io.File("resources/lob/route" + i + ".txt")); //��ȡclob�ֶε��ļ���
////				String clobvalue = dbUtil.getString(i, "clobname");//��ȡclob�ֶε��ַ���������
////				Clob clob = dbUtil.getClob(i, "clobname");//��ȡclob�ֶ�ֵ��clob���ͱ�����
//			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		    throw e ;
		}
		finally
		{
			dbUtil = null;
		}
		
	}
	
	public void main(String[] args)
	{
//		System.out.print("start.........");
//		DBUtil.debugMemory();
//		for(int i = 0; i < 1; i ++)
//		{
//			testBlobWrite();
////			testBigBlobWrite();
////			testClobWrite();
////			testBigClobWrite();
//			
//		}
//		
////		testBlobWrite();
////		testBigBlobWrite();
//		DBUtil.debugMemory();
//		testBlobRead();
////		testClobRead();
//		System.out.print("end.........");
//		DBUtil.debugMemory();
	}
	
	

}
