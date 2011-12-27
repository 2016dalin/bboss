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

import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.RollbackException;

import oracle.sql.BLOB;
import oracle.sql.CLOB;


import org.junit.Test;

import com.frameworkset.common.poolman.CallableDBUtil;
import com.frameworkset.common.poolman.DBUtil;
import com.frameworkset.common.poolman.PreparedDBUtil;
import com.frameworkset.common.poolman.Record;
import com.frameworkset.common.poolman.SQLExecutor;
import com.frameworkset.common.poolman.SQLParams;
import com.frameworkset.common.poolman.handle.FieldRowHandler;
import com.frameworkset.common.poolman.handle.NullRowHandler;
import com.frameworkset.orm.annotation.Column;
import com.frameworkset.orm.transaction.TransactionManager;
import com.frameworkset.util.StringUtil;
/**
 * 
 * <p>Title: TestLob.java</p>
 *
 * <p>Description: 
 * CREATE
    TABLE TEST
    (
        BLOBNAME BLOB,
        CLOBNAME CLOB,
        ID VARCHAR(100)
    )
 * </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 * @Date 2011-12-27 ����2:56:03
 * @author biaoping.yin
 * @version 1.0
 */
public class TestLob {
	
	public static class LobBean
	{
		private String id;
		@Column(type="blob")
		private String blobname;
		@Column(type="clob")
		private String clobname;
	}
	@Test
	public void testNewSQLParamInsert() throws Exception
	{
		SQLParams params = new SQLParams();
		params.addSQLParam("id", "1", SQLParams.STRING);
		// ID,HOST_ID,PLUGIN_ID,CATEGORY_ID,NAME,DESCRIPTION,DATASOURCE_NAME,DRIVER,JDBC_URL,USERNAME,PASSWORD,VALIDATION_QUERY
		params.addSQLParam("blobname", "abcdblob",
				SQLParams.BLOB);
		params.addSQLParam("clobname", "abcdclob",
				SQLParams.CLOB);
		SQLExecutor.insertBean("insert into test(id,blobname,clobname) values(#[id],#[blobname],#[clobname])", params);
	}
	@Test
	public void testNewBeanInsert() throws Exception
	{
		LobBean bean = new LobBean();
		bean.id = "2";
		bean.blobname = "abcdblob";
		bean.clobname = "abcdclob";
		SQLExecutor.insertBean("insert into test(id,blobname,clobname) values(#[id],#[blobname],#[clobname])", bean);
	}
	
	@Test
	public void testNewOrMappingQuery() throws Exception
	{
//		SQLParams params = new SQLParams();
//		params.addSQLParam("id", "1", SQLParams.STRING);
//		// ID,HOST_ID,PLUGIN_ID,CATEGORY_ID,NAME,DESCRIPTION,DATASOURCE_NAME,DRIVER,JDBC_URL,USERNAME,PASSWORD,VALIDATION_QUERY
//		params.addSQLParam("blobname", "abcdblob",
//				SQLParams.BLOB);
//		params.addSQLParam("clobname", "abcdclob",
//				SQLParams.CLOB);
		LobBean bean = SQLExecutor.queryObject(LobBean.class,"select * from test");
		System.out.println();
	}
	
	
	@Test
	public void testNewOrMappingsQuery() throws Exception
	{
//		SQLParams params = new SQLParams();
//		params.addSQLParam("id", "1", SQLParams.STRING);
//		// ID,HOST_ID,PLUGIN_ID,CATEGORY_ID,NAME,DESCRIPTION,DATASOURCE_NAME,DRIVER,JDBC_URL,USERNAME,PASSWORD,VALIDATION_QUERY
//		params.addSQLParam("blobname", "abcdblob",
//				SQLParams.BLOB);
//		params.addSQLParam("clobname", "abcdclob",
//				SQLParams.CLOB);
		List<LobBean> bean = SQLExecutor.queryList(LobBean.class,"select * from test");
		System.out.println();
	}
	
	
	
	/**
	 * CREATE
    TABLE CLOBFILE
    (
        FILEID VARCHAR(100),
        FILENAME VARCHAR(100),
        FILESIZE BIGINT,
        FILECONTENT CLOB(2147483647)
    )
	 */
	public void uploadClobFile(File file) throws Exception
	{

		
		String sql = "";
		try {
			sql = "INSERT INTO CLOBFILE (FILENAME,FILECONTENT,fileid,FILESIZE) VALUES(#[filename],#[FILECONTENT],#[FILEID],#[FILESIZE])";
			SQLParams sqlparams = new SQLParams();
			sqlparams.addSQLParam("filename", file.getName(), SQLParams.STRING);
			sqlparams.addSQLParam("FILECONTENT", file,SQLParams.CLOBFILE);
			sqlparams.addSQLParam("FILEID", UUID.randomUUID().toString(),SQLParams.STRING);
			sqlparams.addSQLParam("FILESIZE", file.length(),SQLParams.LONG);
			SQLExecutor.insertBean(sql, sqlparams);			
			
		} catch (Exception ex) {
		
			
			throw new Exception("�ϴ����������ٿ�ָ�����Ϣ����ʧ�ܣ�" + ex);
		} 
		
		
	}
	
	/**
	 * �ϴ�����
	 * @param inputStream
	 * @param filename
	 * @return
	 * @throws Exception
	 */
	public boolean uploadFile(InputStream inputStream,long size, String filename) throws Exception {
		boolean result = true;
		String sql = "";
		try {
			sql = "INSERT INTO filetable (FILENAME,FILECONTENT,fileid,FILESIZE) VALUES(#[filename],#[FILECONTENT],#[FILEID],#[FILESIZE])";
			SQLParams sqlparams = new SQLParams();
			sqlparams.addSQLParam("filename", filename, SQLParams.STRING);
			sqlparams.addSQLParam("FILECONTENT", inputStream, size,SQLParams.BLOBFILE);
			sqlparams.addSQLParam("FILEID", UUID.randomUUID().toString(),SQLParams.STRING);
			sqlparams.addSQLParam("FILESIZE", size,SQLParams.LONG);
			SQLExecutor.insertBean(sql, sqlparams);			
			
		} catch (Exception ex) {
			ex.printStackTrace();
			result = false;
			throw new Exception("�ϴ����������ٿ�ָ�����Ϣ����ʧ�ܣ�" + ex);
		} finally {
			if(inputStream != null){
				inputStream.close();
			}
		}
		return result;
	}
	
	public File getDownloadFile(String fileid) throws Exception
	{
		try
		{
			return SQLExecutor.queryTField(
											File.class,
											new FieldRowHandler<File>() {

												@Override
												public File handleField(
														Record record)
														throws Exception
												{

													// �����ļ�����
													File f = new File("d:/",record.getString("filename"));
													// ����ļ��Ѿ�������ֱ�ӷ���f
													if (f.exists())
														return f;
													// ��blob�е��ļ����ݴ洢���ļ���
													record.getFile("filecontent",f);
													return f;
												}
											},
											"select * from filetable where fileid=?",
											fileid);
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	public File getDownloadClobFile(String fileid) throws Exception
	{
		try
		{
			return SQLExecutor.queryTField(
											File.class,
											new FieldRowHandler<File>() {

												@Override
												public File handleField(
														Record record)
														throws Exception
												{

													// �����ļ�����
													File f = new File("d:/",record.getString("filename"));
													// ����ļ��Ѿ�������ֱ�ӷ���f
													if (f.exists())
														return f;
													// ��blob�е��ļ����ݴ洢���ļ���
													record.getFile("filecontent",f);
													return f;
												}
											},
											"select * from CLOBFILE where fileid=?",
											fileid);
		}
		catch (Exception e)
		{
			throw e;
		}
	}

	
	public void deletefiles() throws Exception
	{

		SQLExecutor.delete("delete from filetable ");	
		SQLExecutor.delete("delete from CLOBFILE ");	
	}

	
	public List<HashMap> queryfiles() throws Exception
	{

		return SQLExecutor.queryList(HashMap.class, "select FILENAME,fileid,FILESIZE from filetable");
		
	}
	
	public List<HashMap> queryclobfiles()throws Exception
	{

		return SQLExecutor.queryList(HashMap.class, "select FILENAME,fileid,FILESIZE from CLOBFILE");
		
	}

	
	public void downloadFileFromBlob(String fileid, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception
	{
		try
		{
			SQLExecutor.queryByNullRowHandler(new NullRowHandler() {
				
				public void handleRow(Record record) throws Exception
				{

					StringUtil.sendFile(request, response, record
							.getString("filename"), record
							.getBlob("filecontent"));
				}
			}, "select * from filetable where fileid=?", fileid);
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
//	
	public void downloadFileFromClob(String fileid, final HttpServletRequest request,
			final HttpServletResponse response) throws Exception
	{

		try
		{
			SQLExecutor.queryByNullRowHandler(new NullRowHandler() {
				@Override
				public void handleRow(Record record) throws Exception
				{

					StringUtil.sendFile(request, response, record
							.getString("filename"), record
							.getClob("filecontent"));
				}
			}, "select * from CLOBFILE where fileid=?", fileid);
		}
		catch (Exception e)
		{
			throw e;
		}
		
	}
//    @BeforeClass
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
