package org.frameworkset.upload.dao.impl;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.frameworkset.upload.dao.UpLoadDao;
import org.frameworkset.web.multipart.MultipartFile;

import com.frameworkset.common.poolman.Record;
import com.frameworkset.common.poolman.SQLExecutor;
import com.frameworkset.common.poolman.SQLParams;
import com.frameworkset.common.poolman.handle.FieldRowHandler;
import com.frameworkset.common.poolman.handle.NullRowHandler;
import com.frameworkset.util.StringUtil;

public class UpLoadDaoImpl implements UpLoadDao {
	
	@Override
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
	public void uploadClobFile(MultipartFile file) throws Exception
	{

		
		String sql = "";
		try {
			sql = "INSERT INTO CLOBFILE (FILENAME,FILECONTENT,fileid,FILESIZE) VALUES(#[filename],#[FILECONTENT],#[FILEID],#[FILESIZE])";
			SQLParams sqlparams = new SQLParams();
			sqlparams.addSQLParam("filename", file.getOriginalFilename(), SQLParams.STRING);
			sqlparams.addSQLParam("FILECONTENT", file,SQLParams.CLOBFILE);
			sqlparams.addSQLParam("FILEID", UUID.randomUUID().toString(),SQLParams.STRING);
			sqlparams.addSQLParam("FILESIZE", file.getSize(),SQLParams.LONG);
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

	@Override
	public void deletefiles() throws Exception
	{

		SQLExecutor.delete("delete from filetable ");	
		SQLExecutor.delete("delete from CLOBFILE ");	
	}

	@Override
	public List<HashMap> queryfiles() throws Exception
	{

		return SQLExecutor.queryList(HashMap.class, "select FILENAME,fileid,FILESIZE from filetable");
		
	}
	@Override
	public List<HashMap> queryclobfiles()throws Exception
	{

		return SQLExecutor.queryList(HashMap.class, "select FILENAME,fileid,FILESIZE from CLOBFILE");
		
	}

	@Override
	public void downloadFileFromBlob(String fileid, final HttpServletRequest request,
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
							.getBlob("filecontent"));
				}
			}, "select * from filetable where fileid=?", fileid);
		}
		catch (Exception e)
		{
			throw e;
		}
	}
	
	@Override
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

	




	
	
	
}
