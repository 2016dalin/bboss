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
package com.frameworkset.common.poolman;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.sql.Array;
import java.sql.BatchUpdateException;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.frameworkset.persitent.util.SQLInfo;
import org.frameworkset.util.BigFile;

import com.frameworkset.common.poolman.NewSQLInfo;
import com.frameworkset.common.poolman.handle.NullRowHandler;
import com.frameworkset.common.poolman.handle.RowHandler;
import com.frameworkset.common.poolman.handle.XMLMark;
import com.frameworkset.common.poolman.sql.PrimaryKey;
import com.frameworkset.common.poolman.util.SQLManager;
import com.frameworkset.common.poolman.util.StatementParser;

/**
 * ִ��Ԥ��sql���
 * 
 * @author biaoping.yin created on 2005-6-8 version 1.0
 */
public class PreparedDBUtil extends DBUtil {
	
	
	
	/**
	 * ִ�е���Ԥ����sqlʱ������װ����
	 */
	protected Params Params = null;
	
	/**
	 * ������Ԥ���������������
	 * List<Params>
	 */
	protected List<Params> batchparams;
	
//	/**
//	 * ������Ԥ��������������������һ��������Ԥ������г��ֶ���
//	 * Map<sql,Params>
//	 */
//	private Map batchparamsIDXBySQL;
	
	/**
	 * �Ƿ���Ҫ��Ԥ��������������Ż��������Ҫ������е�batchparams����sql����Ƿ���ͬ��������
	 * ��������ͬ��ŵ�һ�������������ͬһ��sql�����ڶ��preparedstatement���
	 * Ĭ�ϲ����򣬷�������
	 */
	protected boolean batchOptimize = false;

	private static Logger log = Logger.getLogger(PreparedDBUtil.class);





	protected String prepareDBName = SQLManager.getInstance()
			.getDefaultDBName();






//	protected String[] preparedfields;

	public static final int INSERT = 0;

	public static final int UPDATE = 1;

	public static final int DELETE = 2;

	/**
	 * ��ҳ��ѯ���
	 */
	public static final int SELECT = 3;

	/**
	 * ͨ�ò�ѯ���
	 */
	public static final int SELECT_COMMON = 4;


	
	/**
	 * ��ʶִ��Ԥ������������ⲿ���ݽ���������
	 * true��ʶ���ⲿ����
	 * false��ʶ�����ⲿ����
	 */
//	protected boolean outcon = true;

	protected long offset;

	protected int pagesize;

	

	/**
	 * �������ֶ�������Ϣ
	 * 
	 * <p>
 * bboss workgroup
 * </p>
 * <p>
 * Copyright (c) 2007
 * </p>
 * 
 * @Date 2009-6-1 ����08:58:51
 * @author biaoping.yin
 * @version 1.0
 */
	class BigData {
		static final int BLOB = 1;

		static final int CLOB = 2;

		static final int TEXT = 3;
		static final int NUMERIC = 4;

		/** blog 1,clob 2,text 3 */
		int type;

		/**
		 * �ֶε�ֵ
		 */
		Object bigdata;

		String bigdataField;

		/**
		 * �ֶζ�Ӧ������
		 */
		int index;
	}

	/**
	 * �������ݿ�ʱ��������Ϣ
	 * 
 * <p>
 * bboss workgroup
 * </p>
 * <p>
 * Copyright (c) 2007
 * </p>
 * 
 * @Date 2009-6-1 ����08:58:51
 * @author biaoping.yin
 * @version 1.0
 */
	public static class UpdateKeyInfo {
		String tableName;

		String keyName;

		String keyValue;
		String type;

	}
	
	protected void setUpParams(Params Params,PreparedStatement statement,List resources) throws SQLException
	{
		setUpParams(Params,statement,null,resources);
	}
	
	
	
	private void setUpParams(Params Params,PreparedStatement statement,PreparedStatement statement_count,List resources)throws SQLException
	{
		if(resources == null)
			resources = new ArrayList();
		for(int i = 0; i < Params.params.size(); i ++)
		{
			Param param = (Param )Params.params.get(i);
			if(param.method.equals(Param.setString_int_String))//#30
			{
				statement.setString(param.index, (String)param.data);
				if(statement_count != null)
				{
					statement_count.setString(param.index, (String)param.data);
				}
			}
			else if(param.method.equals(Param.setInt_int_int))//#21
			{
				statement.setInt(param.index, ((Integer)param.data).intValue());
				if(statement_count != null)
				{
					statement_count.setInt(param.index, ((Integer)param.data).intValue());
				}
			}
			else if(param.method.equals(Param.setTimestamp_int_Timestamp))//#33
			{
				statement.setTimestamp(param.index, (Timestamp)param.data);
				if(statement_count != null)
				{
					statement_count.setTimestamp(param.index, (Timestamp)param.data);
				}
			}
			else if(param.method.equals(Param.setObject_int_Object))//#25
			{
				statement.setObject(param.index, param.data);
				if(statement_count != null)
				{
					statement_count.setObject(param.index, param.data);
				}
			}
			else if(param.method.equals(Param.setLong_int_long))//#22
			{
				statement.setLong(param.index, ((Long)param.data).longValue());
				if(statement_count != null)
				{
					statement_count.setLong(param.index, ((Long)param.data).longValue());
				}
			}
			else if(param.method.equals(Param.setDouble_int_double))//#19
			{
				statement.setDouble(param.index, ((Double)param.data).doubleValue());
				if(statement_count != null)
				{
					statement_count.setDouble(param.index, ((Double)param.data).doubleValue());
				}
			}
			else if(param.method.equals(Param.setFloat_int_float))//#20
			{
				statement.setFloat(param.index, ((Float)param.data).floatValue());
				if(statement_count != null)
				{
					statement_count.setFloat(param.index, ((Float)param.data).floatValue());
				}
			}
			
			else if(param.method.equals(Param.setNull_int_int))//#23
			{
				statement.setNull(param.index, ((Integer)param.data).intValue());
				if(statement_count != null)
				{
					statement_count.setNull(param.index, ((Integer)param.data).intValue());
				}
			}
			else if(param.method.equals(Param.setNull_int_int_String))//#24
			{
				Object[] data = (Object[])param.data;
				statement.setNull(param.index, ((Integer)data[0]).intValue(),(String)data[1]);
				if(statement_count != null)
				{
					statement_count.setNull(param.index, ((Integer)data[0]).intValue(),(String)data[1]);
				}
			}
			else if(param.method.equals(Param.setDate_int_sqlDate))//#17
			{
				statement.setDate(param.index, (java.sql.Date)param.data);
				if(statement_count != null)
				{
					statement_count.setDate(param.index, (java.sql.Date)param.data);
				}
			}
			else if(param.method.equals(Param.setDate_int_utilDate))//#18
			{
				statement.setDate(param.index, (java.sql.Date)param.data);
				if(statement_count != null)
				{
					statement_count.setDate(param.index, (java.sql.Date)param.data);
				}
			}
			else if(param.method.equals(Param.setDate_int_Date_Calendar))//#16
			{
				Object[] data = (Object[])param.data;
				statement.setDate(param.index, (java.sql.Date)data[0],(Calendar)data[1]);
				if(statement_count != null)
				{
					statement_count.setDate(param.index, (Date)data[0],(Calendar)data[1]);
				}
			}
			
			else if(param.method.equals(Param.setShort_int_short))//#29
			{
				statement.setShort(param.index, ((Short)param.data).shortValue());
				if(statement_count != null)
				{
					statement_count.setShort(param.index, ((Short)param.data).shortValue());
				}
			}
			else if(param.method.equals(Param.setTimestamp_int_Timestamp_Calendar))//#34
			{
				Object[] data = (Object[])param.data;
				statement.setTimestamp(param.index, (Timestamp)data[0],(Calendar)data[1]);
				if(statement_count != null)
				{
					statement_count.setTimestamp(param.index, (Timestamp)data[0],(Calendar)data[1]);
				}
			}
			else if(param.method.equals(Param.SET_ARRAY_INT_ARRAY))//#1
			{
				statement.setArray(param.index, (Array)param.data);
				if(statement_count != null)
				{
					statement_count.setArray(param.index, (Array)param.data);
				}
			}
			else if(param.method.equals(Param.SET_AsciiStream_INT_InputStream_INT))//#2
			{
				Object[] data = (Object[])param.data;
				InputStream in = null;
				try
				{
					
					in = (InputStream)data[0];
					statement.setAsciiStream(param.index, in, ((Integer)data[1]).intValue());
					if(statement_count != null)
					{
						statement_count.setAsciiStream(param.index, in, ((Integer)data[1]).intValue());
					}
				}
				catch(SQLException e)
				{
					throw e;
				}
				catch(Exception e)
				{
					throw new NestedSQLException(e);
				}
				finally
				{
					if(in != null)
						resources.add(in);
				}
				
			}
			
			else if(param.method.equals(Param.SET_BigDecimal_INT_BigDecimal))//#3
			{
				statement.setBigDecimal(param.index, (BigDecimal)param.data);
				if(statement_count != null)
				{
					statement_count.setBigDecimal(param.index, (BigDecimal)param.data);
				}
			}
			else if(param.method.equals(Param.setBinaryStream_int_InputStream_int) || 
					param.method.equals(Param.setBlob_int_InputStream_int))//#4
			{
				Object[] data = null;
				
				InputStream in = null;
				try
				{
					data =	(Object[])param.data;
					
					in = (InputStream)data[0];
					statement.setBinaryStream(param.index, in, ((Integer)data[1]).intValue());
					if(statement_count != null)
					{
						statement_count.setBinaryStream(param.index, in, ((Integer)data[1]).intValue());
					}
				}
				catch(SQLException e)
				{
					throw e;
				}
				catch(Exception e)
				{
					throw new NestedSQLException(e);
				}
				finally
				{
					data = null;
//					if(in != null)
//					{
//						try {
//							in.close();
//							
//						} catch (Exception e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
					if(in != null)
						resources.add(in);
//					in = null;
					
				}
				
				
			}
			else if(param.method.equals(Param.setBlob_int_bytearray) 
					|| param.method.equals(Param.setBlob_int_bytearray_String))//#5
			{
				
				
////			PreparedStatement stmt ;
////			stmt.setBinaryStream(parameterIndex, x, length);
////			stmt.set
//			this.setBinaryStream(i, bis, x.length);
				byte[] data = null;
				ByteArrayInputStream bis = null;
				try
				{

					data = (byte[])param.data;		
					if(data == null)
					{
						statement.setNull(param.index, Types.BLOB);
					}
					else
					{
						bis= new ByteArrayInputStream(data);
						statement.setBinaryStream(param.index, bis,data.length);
						if(statement_count != null)
						{
							statement_count.setBinaryStream(param.index, bis,data.length);
						}
					}
				}
				catch(SQLException e)
				{
					throw e;
				}
				catch(Exception e)
				{
					throw new NestedSQLException(e);
				}
				finally
				{
					data = null;
//					if(bis != null)
//					{
//						try
//						{
//							bis.close();
//							
//						}
//						catch(Exception e)
//						{
//							throw new NestedSQLException(e);
//						}
//						bis = null;
//					}
					if(bis != null)
						resources.add(bis);
					
				}
			}
			else if(param.method.equals(Param.setBlob_int_blob))//#5 //blob�ֶΰ���String��Blob����洢��Blob�����ֶεĹ���
			{
				if(param.data == null)
				{
					statement.setNull(param.index, Types.BLOB);
//					statement.setBlob(param.index, (Blob)param.data);
					if(statement_count != null)
					{
						statement_count.setNull(param.index, Types.BLOB);
					}
				}
				else
				{
					if(param.data instanceof String)
					{
						InputStream in = null;

						try
						{

							String data_str = (String)param.data;
							int len =data_str.length();
							in = new ByteArrayInputStream(data_str.getBytes());
							statement.setBinaryStream(param.index, in, (int)len);
							if(statement_count != null)
							{
								statement_count.setBinaryStream(param.index, in, (int)len);
							}
							
						}
						catch(SQLException e)
						{
							throw e;
						}
						catch(Exception e)
						{
							throw new NestedSQLException(e);
						}
						finally
						{

							if(in != null)
								resources.add(in);
							
						}
						
					}
					else
					{
						statement.setBlob(param.index, (Blob)param.data);
						if(statement_count != null)
						{
							statement_count.setBlob(param.index, (Blob)param.data);
						}
					}
				}
				
			}
//			else if(param.method.equals(Param.setBlob_int_bytearray_String))//#6
//			{
////				statement.setBlob(param.index, (Blob)param.data);
////				if(statement_count != null)
////				{
////					statement_count.setBlob(param.index, (Blob)param.data);
////				}
//				byte[] data = null;
//				ByteArrayInputStream bis = null;
//				try
//				{
//
//					data = (byte[])param.data;		
//					if(data == null)
//					{
//						statement.setNull(param.index, Types.BLOB);
//					}
//					else
//					{
//						bis= new ByteArrayInputStream(data);
//						statement.setBinaryStream(param.index, bis,data.length);
//						if(statement_count != null)
//						{
//							statement_count.setBinaryStream(param.index, bis,data.length);
//						}
//					}
//				}
//				catch(SQLException e)
//				{
//					throw e;
//				}
//				catch(Exception e)
//				{
//					throw new NestedSQLException(e);
//				}
//				finally
//				{
//					data = null;
//					if(bis != null)
//					{
//						try
//						{
//							bis.close();
//							
//						}
//						catch(Exception e)
//						{
//							throw new NestedSQLException(e);
//						}
//						bis = null;
//					}
//					
//				}
//			}
			else if(param.method.equals(Param.setBlob_int_File) 
					|| param.method.equals(Param.setBlob_int_File_String))//#7
			{
//				statement.setBlob(param.index, (Blob)param.data);
//				if(statement_count != null)
//				{
//					statement_count.setBlob(param.index, (Blob)param.data);
//				}
				File x = null;
				InputStream in = null;
//				try
//				this.setBinaryStream(i, getInputStream(x), Integer.MAX_VALUE);

				try
				{

					if(param.data == null)
					{
						statement.setNull(param.index, Types.BLOB);
						if(statement_count != null)
						{
		//					Reader reader = (Reader)data[0];
							statement_count.setNull(param.index, Types.BLOB);						
						}
					}
					else
					{
						long len =Integer.MAX_VALUE;
						if(param.data instanceof File)
						{
							x = (File)param.data;
		
							in = new java.io.BufferedInputStream(new FileInputStream(x));
							len = x.length();
						}
						else if(param.data instanceof BigFile)
						{
							BigFile f = (BigFile)param.data;
							len = f.getSize();
							in = new java.io.BufferedInputStream(f.getInputStream());
						}
						else if(param.data instanceof Object[])
						{
							Object[] values = ( Object[])param.data;
							in = new java.io.BufferedInputStream(( InputStream)values[0]);
							len = (Long)values[1];
						}
						else
							in = new java.io.BufferedInputStream(( InputStream)param.data);
	
						
						statement.setBinaryStream(param.index, in, (int)len);
						if(statement_count != null)
						{
							statement_count.setBinaryStream(param.index, in, (int)len);
						}
					}
				}
				catch(SQLException e)
				{
					throw e;
				}
				catch(Exception e)
				{
					throw new NestedSQLException(e);
				}
				finally
				{
					x = null;
//					if(in != null)
//					{
//						try {
////							in.close();
//							
//							
//						} catch (Exception e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//					in = null;
					if(in != null)
						resources.add(in);
					
				}
			}
//			else if(param.method.equals(Param.setBlob_int_File_String))//#8
//			{
//				statement.setBlob(param.index, (Blob)param.data);
//				if(statement_count != null)
//				{
//					statement_count.setBlob(param.index, (Blob)param.data);
//				}
//			}
			else if(param.method.equals(Param.setClob_int_File) 
					|| param.method.equals(Param.setClob_int_File_String))//#7
			{
//				statement.setClob(param.index, (Clob)param.data);
//				if(statement_count != null)
//				{
//					statement_count.setClob(param.index, (Clob)param.data);
//				}   
				
				StringReader reader  = null;
				InputStream in = null;
				InputStream dataf = null;
				try
				{
					
					if(param.data == null)
					{
						statement.setNull(param.index, Types.CLOB);
						if(statement_count != null)
						{
		//					Reader reader = (Reader)data[0];
							statement_count.setNull(param.index, Types.CLOB);						
						}
					}
					else
					{
						if(param.data instanceof File)
						{
							File data = null;
							data = (File)param.data;
							 in = new java.io.BufferedInputStream(new java.io.FileInputStream(data));
							long len = data.length();
							statement.setAsciiStream(param.index, in,(int)len);
							if(statement_count != null)
							{
			//					Reader reader = (Reader)data[0];
								statement_count.setAsciiStream(param.index, in,(int)len);					
								
							}
						}
						else if(param.data instanceof BigFile)
						{
							BigFile f = (BigFile)param.data;
							long len = f.getSize();
							 in = new java.io.BufferedInputStream(f.getInputStream());
							statement.setAsciiStream(param.index, in,(int)len);
							if(statement_count != null)
							{
			//					Reader reader = (Reader)data[0];
								statement_count.setAsciiStream(param.index, in,(int)len);						
								
							}
						}
						else if(param.data instanceof Object[])
						{
							Object[] values = ( Object[])param.data;
							 dataf = (InputStream)values[0];
							long len = (Long)values[1];
						    in = new java.io.BufferedInputStream(dataf);
							statement.setAsciiStream(param.index, in,(int)len);
							if(statement_count != null)
							{
			//					Reader reader = (Reader)data[0];
								statement_count.setAsciiStream(param.index, in,(int)len);				
								
							}
						}
						else
						{
							 dataf = (InputStream)param.data;
						    String d = getString( dataf);
							reader  = new java.io.StringReader(d);
							statement.setCharacterStream(param.index, reader,d.length());
							if(statement_count != null)
							{
			//					Reader reader = (Reader)data[0];
								statement_count.setCharacterStream(param.index, reader,d.length());						
								
							}
						}
					}
				}
				catch(SQLException e)
				{
					throw e;
				}
				catch(Exception e)
				{
					throw new NestedSQLException(e);
				}
				
				finally
				{
//					data = null;
//					if(reader != null)
//					{
//						try {
//							reader.close();
//						} catch (Exception e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						reader = null;
//					}
					if(reader != null)
						resources.add(reader);
					if(in != null)
					{
						resources.add(in);
					}
//						try
//						{
//							in.close();
//						}
//						catch (Exception e)
//						{
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
					if( dataf != null)
					{
//						try
//						{
//							dataf.close();
//						}
//						catch (Exception e)
//						{
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
						resources.add(dataf);
					}
					
				}
//				this.setCharacterStream(i, reader, Integer.MAX_VALUE);
			}
			else if(param.method.equals(Param.setClob_int_Clob))//#7
			{
				statement.setClob(param.index, (Clob)param.data);
				if(statement_count != null)
				{
					statement_count.setClob(param.index, (Clob)param.data);
				}
			}
//			else if(param.method.equals(Param.setClob_int_File_String))//#8
//			{
//				statement.setClob(param.index, (Clob)param.data);
//				if(statement_count != null)
//				{
//					statement_count.setClob(param.index, (Clob)param.data);
//				}
//			}
//			else if(param.method.equals(Param.setBlob_int_InputStream_int))//#9
//			{
//				statement.setBlob(param.index, (Blob)param.data);
//				if(statement_count != null)
//				{
//					statement_count.setBlob(param.index, (Blob)param.data);
//				}
//			}
			else if(param.method.equals(Param.setBoolean_int_boolean))//#10
			{
				statement.setBoolean(param.index, ((Boolean)param.data).booleanValue());
				if(statement_count != null)
				{
					statement_count.setBoolean(param.index, ((Boolean)param.data).booleanValue());
				}
			}
			else if(param.method.equals(Param.setByte_int_byte))//#11
			{
				statement.setByte(param.index, ((Byte)param.data).byteValue());
				if(statement_count != null)
				{
					statement_count.setByte(param.index, ((Byte)param.data).byteValue());
				}
			}
			else if(param.method.equals(Param.setBytes_int_bytearray))//#12
			{
				statement.setBytes(param.index, (byte[])(param.data));
				if(statement_count != null)
				{
					statement_count.setBytes(param.index, (byte[])(param.data));
				}
			}
			else if(param.method.equals(Param.setCharacterStream_int_Reader_int))//#13
			{
				Object[] data = null;
				Reader reader = null;
				try
				{
					data = (Object[])param.data;
					reader = (Reader)data[0];
					statement.setCharacterStream(param.index, reader,((Integer)data[1]).intValue());
					if(statement_count != null)
					{
	//					Reader reader = (Reader)data[0];
						statement_count.setCharacterStream(param.index, reader,((Integer)data[1]).intValue());						
						
					}
				}
				catch(SQLException e)
				{
					throw e;
				}
				catch(Exception e)
				{
					throw new NestedSQLException(e);
				}
				
				finally
				{
					data = null;
//					if(reader != null)
//					{
//						try {
//							reader.close();
//						} catch (Exception e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						reader = null;
//					}
					if(reader != null)
						resources.add(reader);
					
				}
				
			}
			else if(param.method.equals(Param.setClob_int_String) ||
					param.method.equals(Param.setClob_int_String_String))//#14
			{
//				java.io.StringReader reader = null;
					
//				this.setCharacterStream(i, reader, content.length());
//				statement.setClob(param.index, (Clob)param.data);
//				if(statement_count != null)
//				{
//					statement_count.setClob(param.index, (Clob)param.data);
//				}
				String data = null;
				Reader reader = null;
				try
				{
					data = (String)param.data;
					if(data == null)
					{
						statement.setNull(param.index, Types.CLOB);
						if(statement_count != null)
						{
		//					Reader reader = (Reader)data[0];
							statement_count.setNull(param.index, Types.CLOB);						
						}
					}
					else
					{
						reader = new java.io.StringReader(data);
						statement.setCharacterStream(param.index, reader,data.length());
						if(statement_count != null)
						{
		//					Reader reader = (Reader)data[0];
							statement_count.setCharacterStream(param.index, reader,data.length());						
							
						}
					}
				}
				catch(SQLException e)
				{
					throw e;
				}
				catch(Exception e)
				{
					throw new NestedSQLException(e);
				}
				
				finally
				{
					data = null;
//					if(reader != null)
//					{
//						try {
//							reader.close();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						reader = null;
//					}
					if(reader != null)
						resources.add(reader);
					
				}
				
			}
			
//			else if(param.method.equals(Param.setClob_int_String_String))//#15
//			{
//				statement.setClob(param.index, (Clob)param.data);
//				if(statement_count != null)
//				{
//					statement_count.setClob(param.index, (Clob)param.data);
//				}
//			}
			
			
			else if(param.method.equals(Param.setObject_int_Object_int))//#26
			{
				Object[] data = (Object[])param.data;
				statement.setObject(param.index, data[0],((Integer)data[1]).intValue());
				if(statement_count != null)
				{
					statement_count.setObject(param.index, data[0],((Integer)data[1]).intValue());
				}
			}
			else if(param.method.equals(Param.setObject_int_Object_int_int))//#27
			{
				Object[] data = (Object[])param.data;
				statement.setObject(param.index, data[0],((Integer)data[1]).intValue(),((Integer)data[2]).intValue());
				if(statement_count != null)
				{
					statement_count.setObject(param.index, data[0],((Integer)data[1]).intValue(),((Integer)data[2]).intValue());
				}
			}
			else if(param.method.equals(Param.setRef_int_Ref))//#28
			{
				statement.setRef(param.index, (Ref)param.data);
				if(statement_count != null)
				{
					statement_count.setRef(param.index, (Ref)param.data);
				}
			}
			
			
			else if(param.method.equals(Param.setTime_int_Time))//#31
			{
				statement.setTime(param.index, (Time)param.data);
				if(statement_count != null)
				{
					statement_count.setTime(param.index, (Time)param.data);
				}
			}
			else if(param.method.equals(Param.setTime_int_Time_Calendar))//#32
			{
				Object[] data = (Object[])param.data;
				statement.setTime(param.index, (Time)data[0],(Calendar)data[1]);
				if(statement_count != null)
				{
					statement_count.setTime(param.index, (Time)data[0],(Calendar)data[1]);
				}
			}
			
			else if(param.method.equals(Param.setUnicodeStream_int_InputStream_int))//#35
			{
				Object[] data = null;
				InputStream in = null;
				try
				{
					data = (Object[])param.data;
					in = (InputStream)data[0];
					statement.setUnicodeStream(param.index, in,((Integer)data[1]).intValue());
					if(statement_count != null)
					{
						statement_count.setUnicodeStream(param.index, in,((Integer)data[1]).intValue());
					}
				}
				finally
				{
//					if(in != null)
//					{
//						try {
//							in.close();
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
					data = null;
					if(in != null)
						resources.add(in);
				}
			}
			
		}
	}
	/*******************************************************************
	 * ͨ��Ԥ���봦��ʼ
	 * 
	 */
	public Object executePrepared() throws SQLException {
		if(this.batchparams != null && batchparams.size() > 0)
			throw new SQLException("Can not execute batch prepared operations as single prepared operation,Please call method executePreparedBatch()!");
		return executePrepared((Connection)null);
	}
	/**
	 * ���getCUDResult����Ϊtrue���򷵻�GetCUDResult���Ͷ���GetCUDResult�����Ժ������£�
	 * result������������������ԴautoprimarykeyΪtrue��������tableinfo���б����˱��������ϢresultΪ��������������֮resultΪ���µļ�¼��
	 * updateCount:���µļ�¼��
	 * keys:�Զ����������������ֻ��һ����¼��Ϊ��ͨ��������ж�����¼��ΪList<Object>����
	 * @param getCUDResult
	 * @return
	 * @throws SQLException
	 */
	public Object executePreparedGetCUDResult(boolean getCUDResult) throws SQLException {
		if(this.batchparams != null && batchparams.size() > 0)
			throw new SQLException("Can not execute batch prepared operations as single prepared operation,Please call method executePreparedBatch()!");
		return executePrepared((Connection)null,getCUDResult);
	}
	
	
	public Object executePreparedForObject(Class objectType) throws SQLException {
		if(this.batchparams != null && batchparams.size() > 0)
			throw new SQLException("Can not execute batch prepared operations as single prepared operation,Please call method executePreparedBatch()!");
		return executePreparedForObject(null,objectType);
	}
	
	public Object[] executePreparedForObjectArray(Class objectType) throws SQLException {
		if(this.batchparams != null && batchparams.size() > 0)
			throw new SQLException("Can not execute batch prepared operations as single prepared operation,Please call method executePreparedBatch()!");
		return executePreparedForObjectArray(null,objectType);
	}
	
	public <T> List<T> executePreparedForList(Class<T> objectType) throws SQLException {
		if(this.batchparams != null && batchparams.size() > 0)
			throw new SQLException("Can not execute batch prepared operations as single prepared operation,Please call method executePreparedBatch()!");
		return executePreparedForList(null,objectType);
	}
	
	public String executePreparedForXML() throws SQLException {
		if(this.batchparams != null && batchparams.size() > 0)
			throw new SQLException("Can not execute batch prepared operations as single prepared operation,Please call method executePreparedBatch()!");
		return executePreparedForXML(null,null);
	}
	
	
	public Object executePreparedForObject(Connection con,Class objectType) throws SQLException {
		if(this.batchparams != null && batchparams.size() > 0)
			throw new SQLException("Can not execute batch prepared operations as single prepared operation,Please call method executePreparedBatch()!");
		return executePreparedForObject(con,objectType,null);
	}
	
	public Object[] executePreparedForObjectArray(Connection con,Class objectType) throws SQLException {
		if(this.batchparams != null && batchparams.size() > 0)
			throw new SQLException("Can not execute batch prepared operations as single prepared operation,Please call method executePreparedBatch()!");
		return executePreparedForObjectArray(con,objectType,null);
	}
	
	public <T> List<T> executePreparedForList(Connection con,Class<T> objectType) throws SQLException {
		if(this.batchparams != null && batchparams.size() > 0)
			throw new SQLException("Can not execute batch prepared operations as single prepared operation,Please call method executePreparedBatch()!");
		return executePreparedForList(con,objectType,null);
	}
	
	public String executePreparedForXML(Connection con) throws SQLException {
		if(this.batchparams != null && batchparams.size() > 0)
			throw new SQLException("Can not execute batch prepared operations as single prepared operation,Please call method executePreparedBatch()!");
		return executePreparedForXML(con,null);
	}
	
	public void executePreparedWithRowHandler(NullRowHandler rowhandler) throws SQLException
	{
	    executePreparedWithRowHandler(null,rowhandler);
	}
	
	public void executePreparedWithRowHandler(Connection con,NullRowHandler rowhandler) throws SQLException
    {
	    executePreparedForObject(con,null,rowhandler);
    }
	
	/******************************************************************
	 * ���д������ķ�����ʼ
	 *****************************************************************/
	public Object executePreparedForObject(Class objectType,RowHandler rowhandler) throws SQLException {
		if(this.batchparams != null && batchparams.size() > 0)
			throw new SQLException("Can not execute batch prepared operations as single prepared operation,Please call method executePreparedBatch()!");
		return executePreparedForObject(null,objectType,rowhandler);
	}
	
	public Object[] executePreparedForObjectArray(Class objectType,RowHandler rowhandler) throws SQLException {
		if(this.batchparams != null && batchparams.size() > 0)
			throw new SQLException("Can not execute batch prepared operations as single prepared operation,Please call method executePreparedBatch()!");
		return executePreparedForObjectArray(null,objectType,rowhandler);
	}
	
	public <T> List<T> executePreparedForList(Class<T> objectType,RowHandler rowhandler) throws SQLException {
		if(this.batchparams != null && batchparams.size() > 0)
			throw new SQLException("Can not execute batch prepared operations as single prepared operation,Please call method executePreparedBatch()!");
		return executePreparedForList(null,objectType,rowhandler);
	}
	
	public String executePreparedForXML(RowHandler rowhandler) throws SQLException {
		if(this.batchparams != null && batchparams.size() > 0)
			throw new SQLException("Can not execute batch prepared operations as single prepared operation,Please call method executePreparedBatch()!");
		return executePreparedForXML(null,rowhandler);
	}
	
	
	public Object executePreparedForObject(Connection con,Class objectType,RowHandler rowhandler) throws SQLException {
		if(this.batchparams != null && batchparams.size() > 0)
			throw new SQLException("Can not execute batch prepared operations as single prepared operation,Please call method executePreparedBatch()!");
		if(objectType != null)
		{
		    return innerExecute(con,objectType,rowhandler,ResultMap.type_objcet,false);
		}
		else
		{
		    return innerExecute(con,objectType,rowhandler,ResultMap.type_null,false);
		}
	}
	
	
	
	public Object[] executePreparedForObjectArray(Connection con,Class objectType,RowHandler rowhandler) throws SQLException {
		if(this.batchparams != null && batchparams.size() > 0)
			throw new SQLException("Can not execute batch prepared operations as single prepared operation,Please call method executePreparedBatch()!");
		return (Object[])innerExecute(con,objectType,rowhandler,ResultMap.type_objectarray,false);
	}
	
	public <T> List<T> executePreparedForList(Connection con,Class<T> objectType,RowHandler rowhandler) throws SQLException {
		if(this.batchparams != null && batchparams.size() > 0)
			throw new SQLException("Can not execute batch prepared operations as single prepared operation,Please call method executePreparedBatch()!");
		return (List<T>)innerExecute(con,objectType,rowhandler,ResultMap.type_list,false);
	}
	
	public String executePreparedForXML(Connection con,RowHandler rowhandler) throws SQLException {
		if(this.batchparams != null && batchparams.size() > 0)
			throw new SQLException("Can not execute batch prepared operations as single prepared operation,Please call method executePreparedBatch()!");
		return (String)innerExecute(con,XMLMark.class,rowhandler,ResultMap.type_xml,false);
	}
	
	
	
	private Object innerExecute(Connection con,Class objectType,RowHandler rowhandler,int type,boolean getCUDResult) throws SQLException
	{
		if(this.batchparams != null && batchparams.size() > 0)
			throw new SQLException("Can not execute batch prepared operations as single prepared operation,Please call method executePreparedBatch(Connection con)!");
		if(Params.prepareSqlifo == null || Params.prepareSqlifo.getNewsql() == null || Params.prepareSqlifo.getNewsql().equals(""))
		{
			throw new SQLException("ִ�д�����������Ԥִ��sql��䣡");
		}
		StatementInfo stmtInfo = null;

		Object result = null;
		PreparedStatement statement = null;
		PreparedStatement statement_count  = null;
//		UpdateSQL preparedUpdate = null;
//		String[] preparedfields = null; 
		List resources = null;
		try {
			stmtInfo = new StatementInfo(this.prepareDBName, this.Params.prepareSqlifo,
					
					false, offset, this.pagesize, isRobotQuery(this.prepareDBName), con,oraclerownum,true);
			stmtInfo.init();


			if (Params.action == INSERT) {
				// ��ʼ�����������ݿ�ִ�����
				boolean autokey = isAutoprimarykey(prepareDBName);
				if(autokey)
				{
					Object[] temp;
					// String tableName = this.fabricateTableName(sql,0);
					/**
					 * added by biaoping.yin on 2005.03.29
					 * �ع�insert���,���������Ϣ�����Ȼ�ȡ����ֵ
					 * 
					 * @param insertStmt�����ݿ�������
					 * @return ret ret[0]:���insert��� ret[1]:����µ�����ֵ
					 *         ret[2]:���±�tableinfo�в�����Ӧ����ֵ���
					 *         ret[3]:PrimaryKey����
					 */
					
					temp = StatementParser.refactorInsertStatement(stmtInfo);
					if(showsql(stmtInfo.getDbname()))
					{
						log.debug("Execute JDBC prepared statement:"+temp[0]);
					}
					statement = stmtInfo.prepareStatement(temp[0].toString());
	
					// ���sqlΪinsert��䲢�����µ�����ֵ���ɣ��򱣴������ֵ
					if (temp[1] != null) {
						result = temp[1];
					}
	//				if (temp[2] != null && temp[3] != null) {
	//					preparedUpdate = (UpdateSQL) temp[2];
	//
	//				}
	//				preparedfields = (String[]) temp[5];
	
					if (Params.updateKeyInfo == null) {
						Params.updateKeyInfo = new UpdateKeyInfo();	
					}
					if (temp[3] != null) {
						PrimaryKey primaryKey = (PrimaryKey) temp[3];
						if (Params.updateKeyInfo == null) {
							Params.updateKeyInfo = new UpdateKeyInfo();						
						}
						if(primaryKey.getTableName() != null)
							Params.updateKeyInfo.tableName = primaryKey.getTableName();
						if(primaryKey.getPrimaryKeyName() != null)
							Params.updateKeyInfo.keyName = primaryKey.getPrimaryKeyName();
						if (temp[1] != null && !temp[1].equals(""))
							Params.updateKeyInfo.keyValue = temp[1].toString();
						if(primaryKey.getMetaType() != null)
						{
							Params.updateKeyInfo.type = primaryKey.getMetaType();
						}
					}				
					
					if (Params.updateKeyInfo.keyValue == null && temp[1] != null)
						Params.updateKeyInfo.keyValue = temp[1].toString();
				}
				else
				{
					if(showsql(stmtInfo.getDbname()))
					{
						log.debug("Execute JDBC prepared statement:"+stmtInfo.getSql());
					}
					statement = stmtInfo.prepareStatement(stmtInfo.getSql());
				}
					
				

				// /**
				// * ���´��ֶ�
				// */
				//                    
				// this.updateBigDatas();
			} else if (Params.action == UPDATE) {
				if(showsql(stmtInfo.getDbname()))
				{
					log.debug("Execute JDBC prepared update statement:"+stmtInfo.getSql());
				}
				statement = stmtInfo.prepareStatement();
				//oracle���ֶδ����������Σ�ϵͳ��2008.11.5�նԴ��ֶεĴ���������Ż��������ٽ��е����Ĵ��ֶδ�����
				//biaoping.yin
//				try {
//					Object[] obj = StatementParser.refactorUpdateStatement(stmtInfo);
//					PrimaryKey primaryKey = (PrimaryKey) obj[0];
//
////					if (updateKeyInfo == null) {
////						if (primaryKey != null) {
////
////							updateKeyInfo = new UpdateKeyInfo();
////							updateKeyInfo.tableName = primaryKey
////									.getTableName();
////							updateKeyInfo.keyName = primaryKey
////									.getPrimaryKeyName();
////						}
////						updateKeyInfo.type = primaryKey.getType();
////					}
//					
//					if (Params.updateKeyInfo == null) {
//						Params.updateKeyInfo = new UpdateKeyInfo();	
//					}
//					if (primaryKey != null) {		
//						if(primaryKey
//								.getTableName() != null)
//						{
//							Params.updateKeyInfo.tableName = primaryKey
//									.getTableName();
//						}
//						if(primaryKey
//								.getPrimaryKeyName() != null)
//						{
//							Params.updateKeyInfo.keyName = primaryKey
//								.getPrimaryKeyName();
//						}
//						if(primaryKey.getMetaType() != null)
//						{
//							Params.updateKeyInfo.type = primaryKey.getMetaType();
//						}
//					}
//						
//					
//
//				} catch (ParserException e) {
//					e.printStackTrace();
//				}

			} 
			else if (Params.action == SELECT)// ��ҳ��ѯ����
			{
				boolean showsql = showsql(stmtInfo.getDbname()); 
				if(showsql)
				{
					log.debug("Execute JDBC prepared query statement:"+stmtInfo.getSql());
				}
				
				statement = stmtInfo.preparePagineStatement(showsql);
				if(Params.totalsize < 0)
				{
					//@Fixme
					stmtInfo.setTotalsizesql(Params.prepareSqlifo.getNewtotalsizesql());
					statement_count = stmtInfo.prepareCountStatement( showsql);
				}
				else
				{
					stmtInfo.setTotalsize(Params.totalsize);
				}
				

			} 
			else // delete��ͨ�ò�ѯ����
			{
				if(showsql(stmtInfo.getDbname()))
				{
					log.debug("Execute JDBC prepared query statement:"+stmtInfo.getSql());
				}
				statement = stmtInfo.prepareStatement();
			}
		
			
			if (Params.action != SELECT && Params.action != SELECT_COMMON) {
				resources = new ArrayList();
				setUpParams(Params,statement,resources);
				statement.execute();
				int updatecount = statement.getUpdateCount();
				if(result == null)
				{
					result = new Integer(updatecount);
				}
				GetCUDResult CUDResult = null;
				if(getCUDResult)
				{		
					List<Object> morekeys = null;
					if(Params.action == INSERT)
					{
						morekeys = getGeneratedKeys(statement);
					}				
					
					if(morekeys != null && morekeys.size() == 1)
						 CUDResult = new GetCUDResult(result,updatecount,morekeys.get(0));
					else
						 CUDResult = new GetCUDResult(result,updatecount,morekeys);
						
				}
//				ResultSet keys = statement.getGeneratedKeys();
				//oracle���ֶδ����������Σ�ϵͳ��2008.11.5�նԴ��ֶεĴ���������Ż��������ٽ��е����Ĵ��ֶδ�����
				//biaoping.yin
//				this.updateBigDatas(Params,Params.updateKeyInfo,
//										stmtInfo.getCon(),
//										stmtInfo.getDbname(),
//										preparedfields);
//				if (preparedUpdate != null) {
//					execute(stmtInfo.getCon(), preparedUpdate);
//				}
				statement.clearParameters();
				if(CUDResult == null)
					return result;
				else
					return CUDResult;
			} else if (Params.action == SELECT) {
				resources = new ArrayList();
				setUpParams(Params,statement,statement_count,resources);
				long start = stmtInfo.getPaginesql().getStart();
				long end = stmtInfo.getPaginesql().getEnd();
				if(start >= 0)
				{
					int startidx = Params.params != null?Params.params.size():0;
					statement.setLong(startidx+1, start);
					statement.setLong(startidx+2, end);
				}
				ResultMap resultMap =  this.doPrepareSelect(stmtInfo,
						   statement,
						   statement_count,
						   objectType,rowhandler,type);
				if(type == ResultMap.type_maparray)
				{
					

					this.allResults = (Record[])resultMap.getCommonresult();
					this.size = allResults == null ? 0 : allResults.length;
					resultMap = null;
					return null;
				}
				else
				{
					this.size = resultMap.getSize();
					return resultMap.getCommonresult();
				}
				
			} else if (Params.action == SELECT_COMMON) {
				resources = new ArrayList();
				setUpParams(Params,statement,resources);
				ResultMap resultMap = this.doPrepareSelectCommon(stmtInfo,
															 statement,
															 objectType,
															 rowhandler,type);
				if(type == ResultMap.type_maparray)
				{

					this.allResults = (Record[])resultMap.getCommonresult();
					this.size = allResults == null ? 0 : allResults.length;
					return null;
				}
				else
				{
					this.size = resultMap.getSize();
					return resultMap.getCommonresult();
				}
			} 
			else // δ֪���͵Ĳ���
			{
				return null;
			}
		} catch (SQLException e) {
			try{
				
				log.error("Execute prepared sql[" + (stmtInfo != null?stmtInfo.getSql():null) + "] failed:" , e);
			}
			catch(Exception ei)
			{
				
			}
			
			if(stmtInfo != null)
				stmtInfo.errorHandle(e);
			throw e;

		} catch (Exception e) {
			try{
				log.error("Execute prepared sql[" + (stmtInfo != null?stmtInfo.getSql():null) + "] failed:" , e);
//				log.error("Execute prepared sql[" + (stmtInfo != null?stmtInfo.getSql():null) + "] failed:" , e);
			}
			catch(Exception ei)
			{
				
			}
			if(stmtInfo != null)
				stmtInfo.errorHandle(e);			

			
			throw new NestedSQLException(e.getMessage(),e);
		} finally {
			if(resources != null)
				releaseResources(resources);
			if(stmtInfo != null)
			{
				stmtInfo.dofinally();
				stmtInfo = null;
			}
			
			this.resetFromSetMethod(null);
		}
	}
	
	protected List<Object> getGeneratedKeys(PreparedStatement statement) throws Exception
	{
		ResultSet keys = null;
		
		Object key = null;
		try
		{
			keys = statement.getGeneratedKeys();			
			List<Object> morekeys = null;
			 while (keys.next()) {
				 if(morekeys == null)
					 morekeys = new ArrayList<Object>();
				 
				 
				 key = keys.getObject(1);
				 morekeys.add(key);
			 
            }
			 return morekeys;
		}
		catch(Exception e)
		{
			throw e;
		}
		finally
		{
			if(keys != null)
				try {
					keys.close();
				} catch (Exception e) {
					
				}
		}
		
	}
	
	/**
	 * 
	 * @param resources
	 */
	protected void releaseResources(List resources)
	{
		if(resources == null)
			return;
		try
		{
			for(int i = 0; resources != null && i < resources.size(); i ++)
			{
				Object obj = resources.get(i);
				if(obj != null)
				{
//					System.out.println("aa");
//					this.debugMemory();
//					x.length();
//					in = this.getInputStream(x);//ռ��̫����ڴ�
//					in = new java.io.BufferedInputStream(new FileInputStream(x));
//					in = new FileInputStream(x);
	
					try
					{
						if(obj instanceof InputStream)
						{
							((InputStream)obj).close();
						}
						else if(obj instanceof Reader)
						{
							((Reader)obj).close();
						}
					}
					catch(Exception e)
					{
						
					}
//					System.out.println("bb");
//					this.debugMemory();
				}
			}
		}
		catch(Exception e)
		{
			
		}
		finally
		{
			resources.clear();
		}
	}
	/************************************************************************
	 * ���д������ķ�������
	 **************************************************************************/
	/**
	 * ִ��Ԥ�������������,֧������
	 * @return
	 * @throws SQLException
	 */
	public void executePreparedBatch() throws SQLException
	{
		if(this.batchparams == null || batchparams.size() == 0)
		{
//			throw new SQLException("Can not execute single prepared statement as batch prepared operation,Please call method executePrepared()!");
			log.info("Can not execute single prepared statement as batch prepared operation,Please call method executePrepared()!");
			return ;
		}
		executePreparedBatch(null);
	}
	
	public void executePreparedBatchGetCUDResult(GetCUDResult getCUDResult) throws SQLException
	{
		if(this.batchparams == null || batchparams.size() == 0)
		{
//			throw new SQLException("Can not execute single prepared statement as batch prepared operation,Please call method executePrepared()!");
			log.info("Can not execute single prepared statement as batch prepared operation,Please call method executePrepared()!");
			return ;
		}
		executePreparedBatch(null,getCUDResult);
	}

	public Object executePrepared(Connection con) throws SQLException
	{
		return executePrepared(con,false);
	}
	/**
	 * ִ��prepare��䣬���ҷ���ִ�к�Ľ��(��������������ɵ�����ֵ)
	 * 
	 * @return Object ����ֵ
	 * @throws SQLException
	 */
	public Object executePrepared(Connection con,boolean getCUDResult) throws SQLException {
//		if(this.batchparams != null && batchparams.size() > 0)
//			throw new SQLException("Can not execute batch prepared operations as single prepared operation,Please call method executePreparedBatch(Connection con)!");
//		if(Params.prepareselect_sql == null || Params.prepareselect_sql.equals(""))
//		{
//			throw new SQLException("ִ�д�����������Ԥִ��sql��䣡");
//		}
//		StatementInfo stmtInfo = new StatementInfo(this.prepareDBName, this.Params.prepareselect_sql,
//		
//				false, offset, this.pagesize, isRobotQuery(this.prepareDBName), con,oraclerownum,true);
//
//		Object result = null;
//		PreparedStatement statement = null;
//		PreparedStatement statement_count  = null;
//		UpdateSQL preparedUpdate = null;
//		String[] preparedfields = null;
//		
//		try {
//			stmtInfo.init();
//
//
//			if (Params.action == INSERT) {
//				// ��ʼ�����������ݿ�ִ�����
//				Object[] temp;
//				// String tableName = this.fabricateTableName(sql,0);
//				/**
//				 * added by biaoping.yin on 2005.03.29
//				 * �ع�insert���,���������Ϣ�����Ȼ�ȡ����ֵ
//				 * 
//				 * @param insertStmt�����ݿ�������
//				 * @return ret ret[0]:���insert��� ret[1]:����µ�����ֵ
//				 *         ret[2]:���±�tableinfo�в�����Ӧ����ֵ���
//				 *         ret[3]:PrimaryKey����
//				 */
//				temp = StatementParser.refactorInsertStatement(stmtInfo);
//				statement = stmtInfo.prepareStatement(temp[0].toString());
//
//				// ���sqlΪinsert��䲢�����µ�����ֵ���ɣ��򱣴������ֵ
//				if (temp[1] != null) {
//					result = temp[1];
//				}
//				if (temp[2] != null && temp[3] != null) {
//					preparedUpdate = (UpdateSQL) temp[2];
//
//				}
//				preparedfields = (String[]) temp[5];
//
//				if (Params.updateKeyInfo == null) {
//					Params.updateKeyInfo = new UpdateKeyInfo();	
//				}
//				if (temp[3] != null) {
//					PrimaryKey primaryKey = (PrimaryKey) temp[3];
//					if (Params.updateKeyInfo == null) {
//						Params.updateKeyInfo = new UpdateKeyInfo();						
//					}
//					if(primaryKey.getTableName() != null)
//						Params.updateKeyInfo.tableName = primaryKey.getTableName();
//					if(primaryKey.getPrimaryKeyName() != null)
//						Params.updateKeyInfo.keyName = primaryKey.getPrimaryKeyName();
//					if (temp[1] != null && !temp[1].equals(""))
//						Params.updateKeyInfo.keyValue = temp[1].toString();
//					if(primaryKey.getMetaType() != null)
//					{
//						Params.updateKeyInfo.type = primaryKey.getMetaType();
//					}
//				}				
//				
//				if (Params.updateKeyInfo.keyValue == null && temp[1] != null)
//					Params.updateKeyInfo.keyValue = temp[1].toString();
//				
//				
//
//				// /**
//				// * ���´��ֶ�
//				// */
//				//                    
//				// this.updateBigDatas();
//			} else if (Params.action == UPDATE) {
//				statement = stmtInfo.prepareStatement();
//				try {
//					Object[] obj = StatementParser.refactorUpdateStatement(stmtInfo);
//					PrimaryKey primaryKey = (PrimaryKey) obj[0];
//
////					if (updateKeyInfo == null) {
////						if (primaryKey != null) {
////
////							updateKeyInfo = new UpdateKeyInfo();
////							updateKeyInfo.tableName = primaryKey
////									.getTableName();
////							updateKeyInfo.keyName = primaryKey
////									.getPrimaryKeyName();
////						}
////						updateKeyInfo.type = primaryKey.getType();
////					}
//					
//					if (Params.updateKeyInfo == null) {
//						Params.updateKeyInfo = new UpdateKeyInfo();	
//					}
//					if (primaryKey != null) {		
//						if(primaryKey
//								.getTableName() != null)
//						{
//							Params.updateKeyInfo.tableName = primaryKey
//									.getTableName();
//						}
//						if(primaryKey
//								.getPrimaryKeyName() != null)
//						{
//							Params.updateKeyInfo.keyName = primaryKey
//								.getPrimaryKeyName();
//						}
//						if(primaryKey.getMetaType() != null)
//						{
//							Params.updateKeyInfo.type = primaryKey.getMetaType();
//						}
//					}
//						
//					
//
//				} catch (ParserException e) {
//					e.printStackTrace();
//				}
//
//			} 
//			else if (Params.action == SELECT)// ��ҳ��ѯ����
//			{
//
//				statement = stmtInfo.preparePagineStatement();
//
//				statement_count = stmtInfo.prepareCountStatement();
//
//			} 
//			else // delete��ͨ�ò�ѯ����
//			{
//				statement = stmtInfo.prepareStatement();
//			}
//		
//			
//			if (Params.action != SELECT && Params.action != SELECT_COMMON) {
//				setUpParams(Params,statement);
//				statement.execute();
//				this.updateBigDatas(Params,Params.updateKeyInfo,stmtInfo.getCon(),stmtInfo.getDbname(),preparedfields);
//				if (preparedUpdate != null) {
//					execute(stmtInfo.getCon(), preparedUpdate);
//				}
//				return result;
//			} else if (Params.action == SELECT) {
//				setUpParams(Params,statement,statement_count);
//				this.allResults = this.doPrepareSelect(stmtInfo,statement,statement_count,null,null);
//				this.size = allResults == null ? 0 : allResults.length;
//				return null;
//			} else if (Params.action == SELECT_COMMON) {
//				setUpParams(Params,statement);
//				this.allResults = this.doPrepareSelectCommon(stmtInfo,statement,null,null);
//				this.size = allResults == null ? 0 : allResults.length;
//				return null;
//			} 
//			else // δ֪���͵Ĳ���
//			{
//				return null;
//			}
//		} catch (SQLException e) {
//
//			log.error("Execute prepared sql[" + (stmtInfo != null?stmtInfo.getSql():null) + "] failed:" , e);
//			if(stmtInfo != null)
//				stmtInfo.errorHandle(e);
//			throw e;
//
//		} catch (Exception e) {
//			if(stmtInfo != null)
//				stmtInfo.errorHandle(e);			
//
//			log.error("Execute prepared sql[" + (stmtInfo != null?stmtInfo.getSql():null) + "] failed:" , e);
//			throw new NestedSQLException(e.getMessage(),e);
//		} finally {
//
//			if(stmtInfo != null)
//				stmtInfo.dofinally();
//			this.resetFromSetMethod(null);
//		}
		return innerExecute(con,null,null,ResultMap.type_maparray,getCUDResult);
	}

	/**
	 * Executes a pagination prepared statement and returns results in the form
	 * of a Hashtable array. ������ִ����Ϻ�����Խ�������л���
	 * 
	 * 
	 * @return �����
	 * @throws SQLException
	 */

	protected ResultMap doPrepareSelect(StatementInfo stmtInfo,
									   PreparedStatement statement,
									   PreparedStatement statement_count,
									   Class objectType,RowHandler rowhandler,int result_type) throws SQLException {

		
		try {
			ResultMap resultMap = new ResultMap();
			ResultSet res = null;
			ResultSet rs = null;
			if(stmtInfo.getTotalsize() < 0)
			{
				try
				{
					rs = statement_count.executeQuery();
	//				stmtInfo.addResultSet(rs);
					if (rs.next()) {
						totalSize = rs.getInt(1);
					}
					this.offset = stmtInfo.rebuildOffset(totalSize);
				}
				finally//�ͽ��ر�
				{
					if(rs != null)
					{
						try {
							rs.close();
						} catch (Exception e) {
							
						}
						rs = null;
					}
					if(statement_count != null)
					{
						try {
							statement_count.close();
							statement_count = null;
						} catch (Exception e) {
							
						}
					}
				}
			}
			else
			{
				this.totalSize = stmtInfo.getTotalsize();
			}
			if (totalSize > 0) {

				res = statement.executeQuery();
				stmtInfo.addResultSet(res);
				stmtInfo.absolute(res);
				stmtInfo.cacheResultSetMetaData( res,true);				
				this.meta = stmtInfo.getMeta();
				if(rowhandler != null)
					rowhandler.init(meta, stmtInfo.getDbname());
				resultMap = stmtInfo.buildResultMap(res, objectType, rowhandler, stmtInfo.getMaxsize(), true, result_type);

			}		
			else //���û�����ݣ�����Ҫ��ȡԴ����
			{
				res = statement.executeQuery();
				stmtInfo.addResultSet(res);
				stmtInfo.absolute(res);
				stmtInfo.cacheResultSetMetaData( res,true);	
				if(rowhandler != null)
					rowhandler.init(meta, stmtInfo.getDbname());
				this.meta = stmtInfo.getMeta();
			}
			return resultMap;

		} catch (SQLException sqle) {
			throw sqle;
		} finally {

		}

//		return results;

	}
	
	
	/**
	 * Executes a prepared statement and returns results in the form of a
	 * Hashtable array. 
	 * ������ִ����Ϻ�����Խ�������л���
	 * ִ����ͨ��ѯ��û�жԽ�������з�ҳ����
	 * 
	 * @return �����
	 * @throws SQLException
	 */

	protected ResultMap doPrepareSelectCommon(StatementInfo stmtInfo,
											 PreparedStatement statement,
											 Class objectType,
											 RowHandler rowhandler,int result_type) throws SQLException {

		
		try {
			
			ResultSet res = null;
			res = statement.executeQuery();
			stmtInfo.addResultSet(res);
			stmtInfo.cacheResultSetMetaData( res,false);
			this.meta = stmtInfo.getMeta();
			if(rowhandler != null)
				rowhandler.init(meta, stmtInfo.getDbname());
			ResultMap resultMap = new ResultMap();
//			if(result_type == ResultMap.type_maparray)
//			{
//				Record[] results = stmtInfo.buildResult(res,10,false);
//				resultMap.setCommonresult(results);
//				if(results != null)
//					resultMap.setSize(results.length);
//			}
//			else if(result_type == ResultMap.type_list)
//			{
//				List results = stmtInfo.buildResultForList(res, 10, false, objectType, rowhandler);
//				resultMap.setCommonresult(results);
//				if(results != null)
//					resultMap.setSize(results.size());
//			}
//			else if(result_type == ResultMap.type_objcet)
//			{
//				if(res.next())
//				{
//					Object result = ResultMap.buildValueObject(res, objectType, stmtInfo, rowhandler);
//					resultMap.setCommonresult(result);
//					if(result != null)
//					{
//						resultMap.setSize(1);
//					}
//				}
//			}
//			else if(result_type == ResultMap.type_objectarray)
//			{
//				Object[] results = stmtInfo.buildResultForObjectArray(res, 10, false, objectType, rowhandler);
//				resultMap.setCommonresult(results);
//				if(results != null)
//				{
//					resultMap.setSize(results.length);
//				}
//			}
//			else if(result_type == ResultMap.type_xml)
//			{
//				resultMap.setCommonresult(stmtInfo.buildResultForXml(res, 10, false, objectType, rowhandler));
//				resultMap.setSize(stmtInfo.getRowcount());
//			}
			
			resultMap = stmtInfo.buildResultMap(res, objectType, rowhandler, 10, false, result_type);
			
			return resultMap;

		} catch (SQLException sqle) {			
			throw sqle;
		} finally {

		}

//		return null;

	}

	private void initBigdata() {
		if (Params.bigdatas == null)
			Params.bigdatas = new ArrayList();

	}

	private void initConditions() {
		if (Params.conditions == null)
			Params.conditions = new ArrayList();
	}

//	private void clearBigdata() {
//		params.updateKeyInfo = null;
//		
//		if (params.bigdatas != null) {
//			params.bigdatas.clear();
//			params.bigdatas = null;
//		}
//	}

//	/**
//	 * ����ȱʡ���ݿ��Ԥ����������
//	 * 
//	 * @param sql
//	 * @throws SQLException
//	 */
//	public void preparedInsert(String sql) throws SQLException {
//		preparedInsert(sql,(Connection)null);
//	}
	
	/**
	 * ����ȱʡ���ݿ��Ԥ����������
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	public void preparedInsert(String sql) throws SQLException {
	    preparedInsert( prepareDBName, sql);		
		
	}
	
	/**
     * ����ȱʡ���ݿ��Ԥ����������
     * 
     * @param sql
     * @throws SQLException
     */
    public void preparedInsert(Params params,NewSQLInfo sql) throws SQLException {
        preparedInsert( params,prepareDBName, sql);        
        
    }
    

    
    
	
	

	/**
	 * 
	 * @param content
	 * @param conn
	 * @param table
	 * @param clobColumn
	 * @param keyColumn
	 * @param keyValue
	 * @param dbName
	 * @throws SQLException
	 * @throws IOException
	 * @deprecated
	 */
	public static void updateClob(Object content, Connection conn,
			String table, String clobColumn, String keyColumn, String keyValue,
			String dbName) throws SQLException, IOException

	{
		DBUtil.getDBAdapter(dbName).updateClob(content, conn, table, clobColumn, keyColumn, keyValue, dbName)
		;
	}

	/**
	 * 
	 * @param instream
	 * @param conn
	 * @param table
	 * @param blobColumn
	 * @param keyColumn
	 * @param keyValue
	 * @param dbName
	 * @throws SQLException
	 * @throws IOException
	 * @deprecated
	 */
	public static void updateBlob(InputStream instream, Connection conn,
			String table, String blobColumn, String keyColumn, String keyValue,
			String dbName) throws SQLException, IOException

	{
		DBUtil.getDBAdapter(dbName).updateBlob(instream, conn, table, blobColumn, keyColumn, keyValue, dbName);
			
	}
	
	

	/**
	 * ��������������
	 * 
	 * @param value
	 * @param conn
	 * @param table
	 * @param blobColumn
	 * @param keyColumn
	 * @param keyValue
	 * @throws SQLException
	 * @throws IOException
	 * @deprecated
	 */
	public static void updateBlob(byte[] value, Connection conn, String table,
			String blobColumn, String keyColumn, String keyValue, String dbName)
			throws SQLException, IOException

	{
		DBUtil.getDBAdapter(dbName).updateBlob(value, conn, table, blobColumn, keyColumn, keyValue, dbName);
	}

	

	/**
	 * ��������������tableinfo��ָ�����������Ϣ�����ǲ�ͨ��poolman���Զ��������������
	 * ���´��д��ֶ�clob,blob���ݿ��¼ʱ��Ҫ���ñ���¼��Ψһ��ʶ��һ��Ϊ���ݿ������� �������ݿ�����������Ϊlong��
	 * 
	 * @param i
	 * @param keyvalue
	 * @throws SQLException
	 */
	public void setPrimaryKey(int i, long keyvalue) throws SQLException {
		try {
//			this.statement.setLong(i, keyvalue);
			this.addParam(i, new Long(keyvalue), Param.setLong_int_long);
			if (Params.updateKeyInfo == null)
				Params.updateKeyInfo = new UpdateKeyInfo();			
			Params.updateKeyInfo.keyValue = keyvalue + "";
			Params.updateKeyInfo.type = "long";
		} catch (SQLException e) {
			this.resetFromSetMethod(e);
		}
	}

	/**
	 * ������������û����tableinfo��ָ�����������Ϣ�ı�
	 * ���´��д��ֶ�clob,blob���ݿ��¼ʱ��Ҫ���ñ���¼��Ψһ��ʶ��һ��Ϊ���ݿ������� �������ݿ�����������Ϊlong��
	 * 
	 * @param i
	 * @param keyvalue
	 * @param keyName
	 * @throws SQLException
	 */
	public void setPrimaryKey(int i, long keyvalue, String keyname)
			throws SQLException {
		try {
//			this.statement.setLong(i, keyvalue);
			this.addParam(i, new Long(keyvalue), Param.setLong_int_long);
			if (Params.updateKeyInfo == null)
				Params.updateKeyInfo = new UpdateKeyInfo();	
			
			Params.updateKeyInfo.keyValue = keyvalue + "";
			Params.updateKeyInfo.keyName = keyname;
			Params.updateKeyInfo.type = "long";

			
		} catch (SQLException e) {
			this.resetFromSetMethod(e);
		}
	}

	/**
	 * �������ֶ�clob��blob�ļ�¼��������ʱ�� ����Ԥ�������������ʱ��Ҫͨ�����������ó���������Ĳ�������
	 * 
	 * @param i
	 * @param value
	 * @param filedname
	 * @throws SQLException
	 * @deprecated
	 */
	public void setContdition(int i, String value, String filedname)
			throws SQLException {
		this.setString(i, value);
		this.initConditions();
		BigData condition = new BigData();
		condition.bigdataField = filedname;
		condition.bigdata = new Integer(value);
		condition.type = BigData.TEXT;
		condition.index = i;

		Params.conditions.add(condition);

	}

	/**
	 * �������ֶ�clob��blob�ļ�¼��������ʱ�� ����Ԥ�������������ʱ��Ҫͨ�����������ó���������Ĳ�������
	 * 
	 * @param i
	 * @param value
	 * @param filedname
	 * @throws SQLException
	 * @deprecated
	 */
	public void setContdition(int i, int value, String filedname)
			throws SQLException {

		this.setInt(i, value);
		initConditions();
		BigData condition = new BigData();
		condition.bigdataField = filedname;
		condition.bigdata = new Integer(value);
		condition.type = BigData.NUMERIC;
		condition.index = i;

		Params.conditions.add(condition);

	}

	/**
	 * ��������������tableinfo��ָ�����������Ϣ�����ǲ�ͨ��poolman���Զ��������������
	 * ���´��д��ֶ�clob,blob���ݿ��¼ʱ��Ҫ���ñ���¼��Ψһ��ʶ��һ��Ϊ���ݿ������� �������ݿ�����������Ϊint��
	 * 
	 * @param i
	 * @param keyvalue
	 * @throws SQLException
	 */
	public void setPrimaryKey(int i, int keyvalue) throws SQLException {
		try {
//			this.statement.setInt(i, keyvalue);
			this.addParam(i, new Integer(keyvalue), Param.setInt_int_int);
			if (Params.updateKeyInfo == null)
				Params.updateKeyInfo = new UpdateKeyInfo();
			Params.updateKeyInfo.keyValue = keyvalue + "";
			Params.updateKeyInfo.type = "int";
		} catch (SQLException e) {
			this.resetFromSetMethod(e);
		}
	}

	/**
	 * ������������û����tableinfo��ָ�����������Ϣ�ı�
	 * ���´��д��ֶ�clob,blob���ݿ��¼ʱ��Ҫ���ñ���¼��Ψһ��ʶ��һ��Ϊ���ݿ������� �������ݿ�����������Ϊint��
	 * 
	 * @param i
	 * @param keyvalue
	 * @param keyName
	 * @throws SQLException
	 */
	public void setPrimaryKey(int i, int keyvalue, String keyName)
			throws SQLException {
		try {
//			this.statement.setInt(i, keyvalue);
			this.addParam(i, new Integer(keyvalue), Param.setInt_int_int);
			if (Params.updateKeyInfo == null)
				Params.updateKeyInfo = new UpdateKeyInfo();
//			if (updateKeyInfo != null) {
			Params.updateKeyInfo.keyValue = keyvalue + "";
			Params.updateKeyInfo.keyName = keyName;
			Params.updateKeyInfo.type = "int";
//			}
		} catch (SQLException e) {
			this.resetFromSetMethod(e);
		}

	}

	/**
	 * ��������������tableinfo��ָ�����������Ϣ�����ǲ�ͨ��poolman���Զ��������������
	 * ���´��д��ֶ�clob,blob���ݿ��¼ʱ��Ҫ���ñ���¼��Ψһ��ʶ��һ��Ϊ���ݿ������� �������ݿ�����������ΪString��int��long��
	 * 
	 * @param i
	 * @param keyvalue
	 * @throws SQLException
	 */
	public void setPrimaryKey(int i, String keyvalue) throws SQLException {
		try {
			if(Params.updateKeyInfo == null)
			{
				Params.updateKeyInfo = new UpdateKeyInfo();
			}
			Params.updateKeyInfo.type = "string";
			Params.updateKeyInfo.keyValue = keyvalue;
			this.addParam(i, keyvalue, Param.setString_int_String);
//			if (updateKeyInfo != null) {
//				if (updateKeyInfo.type != null) {
//					if (updateKeyInfo.type.equalsIgnoreCase("int")
//							|| updateKeyInfo.type
//									.equalsIgnoreCase("java.lang.Integer")) {
//						try {
////							this.statement
////									.setInt(i, Integer.parseInt(keyvalue));
//							this.addParam(i, new Integer(keyvalue), Param.setInt_int_int);
//						}
//						catch(SQLException e)
//						{
//							throw e;
//						} 
//						catch (Exception e) {
//							throw new SQLException("�Ƿ������������index=" + i
//									+ ",value=" + keyvalue + ",Ҫ������Ϊ����");
//						}
//					}
//
//					else if (updateKeyInfo.type.equalsIgnoreCase("long")
//							|| updateKeyInfo.type
//									.equalsIgnoreCase("java.lang.long")) {
//						try {
////							this.statement.setLong(i, Long.parseLong(keyvalue));
//							this.addParam(i, new Long(keyvalue), Param.setLong_int_long);
//						} 
//						catch(SQLException e)
//						{
//							throw e;
//						}
//						catch (Exception e) {
//							throw new SQLException("�Ƿ������������index=" + i
//									+ ",value=" + keyvalue + ",Ҫ������Ϊlong����");
//						}
//					} else {
////						this.statement.setString(i, keyvalue);
//						this.addParam(i, keyvalue, Param.setString_int_String);
//					}
//				} 
//				else
//				{
////					this.statement.setString(i, keyvalue);
//					this.addParam(i,keyvalue, Param.setString_int_String);
//				}
//				this.updateKeyInfo.keyValue = keyvalue;
//
//			} else {
//				// updateKeyInfo.keyValue = keyvalue;
//				
////				this.statement.setString(i, keyvalue);
//				this.addParam(i, keyvalue, Param.setString_int_String);
//			}
		} catch (SQLException e) {
			this.resetFromSetMethod(e);
		}

	}

	/**
	 * ������������û����tableinfo��ָ�����������Ϣ�ı�
	 * ���´��д��ֶ�clob,blob���ݿ��¼ʱ��Ҫ���ñ���¼��Ψһ��ʶ��һ��Ϊ���ݿ������� �������ݿ�����������ΪString��int��long��
	 * 
	 * @param i
	 * @param keyvalue
	 * @param keyName
	 * @throws SQLException
	 */
	public void setPrimaryKey(int i, String keyvalue, String keyName)
			throws SQLException {
		try {
			Params.updateKeyInfo = new UpdateKeyInfo();
			Params.updateKeyInfo.keyValue = keyvalue;
			Params.updateKeyInfo.keyName = keyName;
			Params.updateKeyInfo.type = "string";
			this.addParam(i,keyvalue, Param.setString_int_String);
			
//			if (updateKeyInfo != null) {
//
//				if (updateKeyInfo.type != null) {
//					if (updateKeyInfo.type.equalsIgnoreCase("int")
//							|| updateKeyInfo.type
//									.equalsIgnoreCase("java.lang.Integer")) {
//						try {
////							this.statement
////									.setInt(i, Integer.parseInt(keyvalue));
//							this.addParam(i, new Integer(keyvalue), Param.setInt_int_int);
//						}
//						catch(SQLException e)
//						{
//							throw e;
//						} 
//						catch (Exception e) {
//							throw new SQLException("�Ƿ������������index=" + i
//									+ ",value=" + keyvalue + ",Ҫ������Ϊ����");
//						}
//					}
//
//					else if (updateKeyInfo.type.equalsIgnoreCase("long")
//							|| updateKeyInfo.type
//									.equalsIgnoreCase("java.lang.long")) {
//						try {
////							this.statement.setLong(i, Long.parseLong(keyvalue));
//							this.addParam(i, new Long(keyvalue), Param.setLong_int_long);
//						} 
//						catch(SQLException e)
//						{
//							throw e;
//						}
//						catch (Exception e) {
//							throw new SQLException("�Ƿ������������index=" + i
//									+ ",value=" + keyvalue + ",Ҫ������Ϊlong����");
//						}
//					}
//					else 
//					{
////						this.statement.setString(i, keyvalue);
//						this.addParam(i,keyvalue, Param.setString_int_String);
//					}
//				}
//				else
//				{
////					this.statement.setString(i, keyvalue);
//					this.addParam(i,keyvalue, Param.setString_int_String);
//				}
				
				
//			}
//			else {
////				this.statement.setString(i, keyvalue);
//				
//				this.addParam(i,keyvalue, Param.setString_int_String);
//				// this.updateKeyInfo.keyValue = keyvalue ;
//				// this.updateKeyInfo.keyName = keyName ;
//			}
			
		} catch (SQLException e) {
			this.resetFromSetMethod(e);
		}

	}

	/**
	 * �����ض����ݿ��Ԥ����������
	 * 
	 * @param dbName
	 * @param sql
	 * @throws SQLException
	 */
	public void preparedInsert(String dbName, String sql) throws SQLException {
	    preparedInsert((Params )null,dbName, new NewSQLInfo(sql));
		
	}
	
	/**
     * �����ض����ݿ��Ԥ����������
     * 
     * @param dbName
     * @param sql
     * @throws SQLException
     */
    public void preparedInsert(Params params,String dbName, NewSQLInfo sql) throws SQLException {
        if(params == null)
            Params = this.buildParams();
        else
            Params = params;
        Params.action = INSERT;
        preparedSql(Params,dbName, sql);
        
    }
    
    /**
     * �����ض����ݿ��Ԥ����������
     * 
     * @param dbName
     * @param sql
     * @throws SQLException
     */
    public void preparedInsert(SQLParams params,String dbName, SQLInfo sql) throws SQLException {
        params.buildParams(sql, dbName);
        preparedInsert(params.getRealParams(),dbName, params.getNewsql());
        
    }
    /**
     * �����ض����ݿ��Ԥ����������
     * 
     * @param dbName
     * @param sql
     * @throws SQLException
     */
    public void preparedInsert(SQLParams params,String dbName, String sql) throws SQLException {
        params.buildParams(sql, dbName);
        preparedInsert(params.getRealParams(),dbName, params.getNewsql());
        
    }
    
    /**
     * �����ض����ݿ��Ԥ����������
     * 
     * @param dbName
     * @param sql
     * @throws SQLException
     */
    public void preparedInsert(SQLParams params,SQLInfo sql) throws SQLException {
        preparedInsert(params,this.prepareDBName, sql);
        
    }
    
    /**
     * �����ض����ݿ��Ԥ����������
     * 
     * @param dbName
     * @param sql
     * @throws SQLException
     */
    public void preparedInsert(SQLParams params,String sql) throws SQLException {
        preparedInsert(params,this.prepareDBName, sql);
        
    }
    
	
//	/**
//	 * �����ض����ݿ��Ԥ����������
//	 * 
//	 * @param dbName
//	 * @param sql
//	 * @throws SQLException
//	 */
//	public void preparedInsert(String dbName, String sql) throws SQLException {
//		this.action = INSERT;
//		preparedSql(dbName, sql);
//	}

	
//	public void preparedSql(Params Params,String dbName, NewSQLInfo sql) throws SQLException {
////		
//////		this.outcon = con == null ? false:true;
////		this.prepareDBName = dbName;
////		Params.prepareselect_sql = sql;
//////		params = this.buildParams();
//		preparedSql(Params,dbName, new SQLInfo (sql,false,false));
//	}
	
	public void preparedSql(Params Params,String dbName, NewSQLInfo sql) throws SQLException {
		
//		this.outcon = con == null ? false:true;
		this.prepareDBName = dbName;
		Params.prepareSqlifo = sql;
//		params = this.buildParams();
	}

	/**
	 * ��Ԥ���봦������г����쳣���ͷ���Դ�ķ���
	 * 
	 * @param e
	 * @throws SQLException
	 */
	private void resetFromSetMethod(Exception e) throws SQLException {
		
//		action = -1;
//		if (this.updateKeyInfo != null)
//			this.updateKeyInfo = null;
//		if (this.bigdatas != null) {
//			this.bigdatas.clear();
//			this.bigdatas = null;
//		}
		if(this.Params != null )
		{
			this.Params.clear();
			this.Params = null;
		}
		if(this.batchparams != null)
		{
			batchparams.clear();
			batchparams = null;
		}
		
//		if(this.conditions != null)
//		{
//			this.conditions.clear();
//			this.conditions = null;
//		}
	
		this.prepareDBName = SQLManager.getInstance().getDefaultDBName();
//		this.prepareselect_sql = null;
		this.oldcommited = true;
		oraclerownum = null;
		this.batchOptimize = false;
		
		if (e != null) {
			if (e instanceof SQLException)
				throw (SQLException) e;
			else {
				throw new SQLException(e.getMessage());
			}
		}

	}
	
	
	
	public static String getFileContent(File file) throws IOException {
		Writer swriter = null;
		Reader reader = null;
		try {
			reader = new FileReader(file);
			swriter = new StringWriter();

			int len = 0;
			char[] buffer = new char[1024];
			while ((len = reader.read(buffer)) > 0) {
				swriter.write(buffer, 0, len);
			}
			swriter.flush();

		
			return swriter.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
				}
			if (swriter != null)
				try {
					swriter.close();
				} catch (IOException e) {
				}
		}
		
	}

	/**
	 * ���´�������Ϣ
	 * 
	 * @throws IOException
	 * @throws SQLException
	 * @deprecated
	 */
	private void updateBigDatas(Params params,UpdateKeyInfo updateKeyInfo,
								Connection preparedCon,
								String prepareDBName,
								String[] preparedfields) throws SQLException, IOException {
		for (int i = 0; params.bigdatas != null && i < params.bigdatas.size(); i++) {
			BigData bigdata = (BigData) params.bigdatas.get(i);
			String bigdataField = bigdata.bigdataField;
			if (bigdataField == null)
			{
				//����������index��λ�ú�prepared������λ�ñ���һ�£����Ǵ�1��ʼ�ģ������ڻ�ȡ�ֶ�ʱ����Ҫ���±���Ϊindex-1
				bigdataField = preparedfields[bigdata.index -1];
			}
			String msg = "û��ָ������Ĵ��ֶ�����";
			if (params.action == UPDATE)
				msg = "û��ָ������Ĵ��ֶ�����";

			if (bigdataField == null)
				throw new SQLException(msg);
			switch (bigdata.type) {
			case BigData.CLOB:
				updateClob(bigdata.bigdata, preparedCon,
						updateKeyInfo.tableName, bigdataField,
						updateKeyInfo.keyName,
						updateKeyInfo.keyValue, prepareDBName);
				break;
			case BigData.BLOB:
				if (bigdata.bigdata instanceof byte[])
					updateBlob((byte[]) bigdata.bigdata, preparedCon,
							updateKeyInfo.tableName, bigdataField,
							updateKeyInfo.keyName,
							updateKeyInfo.keyValue, prepareDBName);
				else if (bigdata.bigdata instanceof File)
					updateBlob(new FileInputStream((File) bigdata.bigdata),
							preparedCon, updateKeyInfo.tableName,
							bigdataField, updateKeyInfo.keyName,
							updateKeyInfo.keyValue, prepareDBName);
				else if (bigdata.bigdata instanceof InputStream)
					updateBlob((InputStream) bigdata.bigdata, preparedCon,
							updateKeyInfo.tableName, bigdataField,
							updateKeyInfo.keyName,
							updateKeyInfo.keyValue, prepareDBName);
				else
					throw new SQLException("Blob �ֶ������쳣��δ֪������["
							+ bigdata.bigdata + "]");
				break;
			default:
				throw new SQLException("δ֪�Ĵ��ֶ����ͣ�[type=" + bigdata.type + "]");

			}

		}
	}


	
	/**
	 * ����Ԥ����������
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	public void preparedUpdate(String sql) throws SQLException {
		Params = this.buildParams();
		Params.action = UPDATE;
		preparedSql(Params,prepareDBName, new NewSQLInfo(sql));
	}

	/**
	 * ����Ԥ����������
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	public void preparedSelect(String sql) throws SQLException {

	    preparedSelect((Params)null,new NewSQLInfo(sql));
	}
	
	/**
     * ����Ԥ����������
     * 
     * @param sql
     * @throws SQLException
     */
    public void preparedSelect(Params params,NewSQLInfo sql) throws SQLException {

        preparedSelect(params,prepareDBName, sql);
    }
    
    
    /**
     * ����Ԥ����������
     * 
     * @param sql
     * @throws SQLException
     */
    public void preparedSelect(SQLParams params_,String sql) throws SQLException {
        params_.buildParams(sql,prepareDBName);            
        preparedSelect(params_.getRealParams(),params_.getNewsql());
    }
    
    
    
	
//	/**
//	 * ����Ԥ����������
//	 * 
//	 * @param sql
//	 * @throws SQLException
//	 */
//	public void preparedSelect(String sql) throws SQLException {
//
//		preparedSelect(prepareDBName, sql,con);
//	}

//	/**
//	 * ����Ԥ����������
//	 * 
//	 * @param sql
//	 * @throws SQLException
//	 */
//	public void preparedSelect(String sql, int offset, int pagesize)
//			throws SQLException {
//
//		preparedSelect(prepareDBName, sql, offset, pagesize);
//	}
	
//	/**
//	 * @deprecated use public void preparedSelect(String sql, long offset, int pagesize)
//            throws SQLException 
//	 */
//	public void preparedSelect(String sql, int offset, int pagesize)
//    throws SQLException
//    {
//	    preparedSelect(sql, (long)offset, pagesize);        
//    }
	/**
	 * ����Ԥ�����ѯ���
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	public void preparedSelect(String sql, long offset, int pagesize)
			throws SQLException {

		preparedSelect(prepareDBName, sql, offset, pagesize,-1L);
	}
	
	/**
	 * ����Ԥ�����ѯ���
	 * @mark
	 * @param sql
	 * @throws SQLException
	 */
	public void preparedSelect(String sql, long offset, int pagesize,long totalsize)
			throws SQLException {

		preparedSelect(prepareDBName, sql, offset, pagesize,totalsize);
	}
	
	/**
	 * ����Ԥ�����ѯ���
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	public void preparedSelectWithTotalsizesql(String sql, long offset, int pagesize,String totalsizesql)
			throws SQLException {

		preparedSelectWithTotalsizesql(prepareDBName, sql, offset, pagesize,totalsizesql);
	}
	
//	/**
//	 * @deprecated see preparedSelect(String sql, long offset, int pagesize,
//            String oraclerownum) throws SQLException
//     */
//    public void preparedSelect(String sql, int offset, int pagesize,
//            String oraclerownum) throws SQLException
//    {
//        preparedSelect( sql, (long )offset, pagesize,
//                oraclerownum);
//    }

	/**
	 * ����Ԥ�����ѯ���
	 * @mark
	 * @param sql
	 * @throws SQLException
	 */
	public void preparedSelect(String sql, long offset, int pagesize,
			String oraclerownum,long totalsize) throws SQLException {

		preparedSelect(prepareDBName, sql, offset, pagesize, oraclerownum,totalsize);
	}
	
	/**
	 * ����Ԥ�����ѯ���
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	public void preparedSelectWithTotalsizesql(String sql, long offset, int pagesize,
			String oraclerownum,String totalsizesql) throws SQLException {

		preparedSelectWithTotalsizesql(prepareDBName, sql, offset, pagesize, oraclerownum,totalsizesql);
	}
	
	/**
	 * ����Ԥ�����ѯ���
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	public void preparedSelect(String sql, long offset, int pagesize,
			String oraclerownum) throws SQLException {

		preparedSelect(prepareDBName, sql, offset, pagesize, oraclerownum,-1L);
	}

//	/**
//	 * ����Ԥ����������
//	 * 
//	 * @param sql
//	 * @throws SQLException
//	 */
//	public void preparedSelect(String prepareDBName, String sql)
//			throws SQLException {
//		preparedSelect(prepareDBName, sql,(Connection)null);
//	}
	/**
	 * ����Ԥ����������
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	public void preparedSelect(String prepareDBName, String sql)
			throws SQLException {
		Params = this.buildParams();
		preparedSelect(Params ,prepareDBName, new NewSQLInfo(sql));
	}
	
//	/**
//     * ����Ԥ����������
//     * 
//     * @param sql
//     * @throws SQLException
//     */
//    public void preparedSelect(Params params,String prepareDBName, String sql)
//            throws SQLException {
////        if(params == null)
////        {
////            Params = this.buildParams();
////        }
////        else
////        {
////            Params = params ; 
////        }
////        Params.action = SELECT_COMMON;
////        Params.prepareselect_sql = sql;
////        preparedSql(Params,prepareDBName, sql);
//    	preparedSelect(params,prepareDBName, new SQLInfo (sql,false,false));
//    }
    
    
    /**
     * ����Ԥ����������
     * 
     * @param sql
     * @throws SQLException
     */
    public void preparedSelect(Params params,String prepareDBName, NewSQLInfo sql)
            throws SQLException {
        if(params == null)
        {
            Params = this.buildParams();
        }
        else
        {
            Params = params ; 
        }
        Params.action = SELECT_COMMON;
//        Params.prepareSqlifo = sql;
        preparedSql(Params,prepareDBName, sql);
    }
    
    /**
     * ����Ԥ����������
     * 
     * @param sql
     * @throws SQLException
     */
    public void preparedSelect(SQLParams params,String prepareDBName, String sql)
            throws SQLException {
    	if(params != null)
    	{
	        params.buildParams(sql,prepareDBName);
	        preparedSelect(params.getRealParams(),prepareDBName, params.getNewsql());
    	}
    	else
    	{
    		preparedSelect((Params)null,prepareDBName, new NewSQLInfo(sql));
    	}
    }


//	/**
//	 * ����Ԥ����������
//	 * 
//	 * @param sql
//	 * @throws SQLException
//	 */
//	public void preparedSelect(String prepareDBName, String sql, int offset,
//			int pagesize) throws SQLException {
//		preparedSelect(prepareDBName, sql, offset,pagesize,(Connection) null);
//	}
	
//	/**
//     * Ԥ�����ѯ����
//     * @deprecated see preparedSelect(String prepareDBName, String sql, long offset,
//            int pagesize)
//     */
//    public void preparedSelect(String prepareDBName, String sql, int offset,
//            int pagesize) throws SQLException {
//        
//        preparedSelect( prepareDBName, sql, (long )offset,
//                pagesize);
//    }
	/**
	 * Ԥ�����ѯ����
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	public void preparedSelect(String prepareDBName, String sql, long offset,
			int pagesize) throws SQLException {
		
		preparedSelect(prepareDBName, sql, offset, pagesize, oraclerownum,-1L);
	}
	
	/**
	 * Ԥ�����ѯ����
	 * @mark
	 * @param sql
	 * @throws SQLException
	 */
	public void preparedSelect(String prepareDBName, String sql, long offset,
			int pagesize,long totalsize) throws SQLException {
		
		preparedSelect(prepareDBName, sql, offset, pagesize, oraclerownum,totalsize);
	}
	
	/**
	 * Ԥ�����ѯ����
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	public void preparedSelectWithTotalsizesql(String prepareDBName, String sql, long offset,
			int pagesize,String totalsizesql) throws SQLException {
		
		preparedSelectWithTotalsizesql(prepareDBName, sql, offset, pagesize, oraclerownum,totalsizesql);
	}
	
	/**
	 * Ԥ�����ѯ����
	 * @mark
	 * @param sql
	 * @throws SQLException
	 */
	public void preparedSelect(Params params,String prepareDBName, NewSQLInfo sql, long offset,
			int pagesize,long totalsize) throws SQLException {
		
		preparedSelect(params,prepareDBName, sql, offset, pagesize, oraclerownum,totalsize);
	}
	
	
//	/**
//	 * Ԥ�����ѯ����
//	 * @mark
//	 * @param sql
//	 * @throws SQLException
//	 */
//	public void preparedSelect(Params params,String prepareDBName, SQLInfo sql, long offset,
//			int pagesize,long totalsize) throws SQLException {
//		
//		preparedSelect(params,prepareDBName, sql, offset, pagesize, oraclerownum,totalsize);
//	}
	
	/**
	 * Ԥ�����ѯ����
	 * preparedSelectWithTotalsizesql((Params)null,prepareDBName, sql, offset, pagesize,totalsizesql)
	 * @param sql
	 * @throws SQLException
	 */
	public void preparedSelectWithTotalsizesql(Params params,String prepareDBName, NewSQLInfo sql, long offset,
			int pagesize) throws SQLException {
		
		preparedSelectWithTotalsizesql(params,prepareDBName, sql, offset, pagesize, oraclerownum);
	}

	
	/**
	 * Ԥ�����ѯ����
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	public void preparedSelect(Params params,String prepareDBName, NewSQLInfo sql, long offset,
			int pagesize) throws SQLException {
		
		preparedSelect(params,prepareDBName, sql, offset, pagesize, oraclerownum,-1L);
	}
	
	/**
     * Ԥ�����ҳ��ѯ���� ���ⲿ�����ܼ�¼��
     * @mark
     * @param sql
     * @throws SQLException
     */
    public void preparedSelect(SQLParams params,String prepareDBName, String sql, long offset,
            int pagesize,long totalsize) throws SQLException {
    	if( params != null)
    	{
    		params.buildParams(sql,prepareDBName);
    		preparedSelect(params.getRealParams(),prepareDBName, params.getNewsql(), offset, pagesize,totalsize);
    	}
    	else
    		preparedSelect((Params)null,prepareDBName, new NewSQLInfo(sql), offset, pagesize,totalsize);
    }
    
    /**
     * Ԥ�����ҳ��ѯ�������ܼ�¼��ͨ��totalsizesql��ѯ��ȡ
     * 
     * @param sql
     * @throws SQLException
     */
    public void preparedSelectWithTotalsizesql(SQLParams params,String prepareDBName, String sql, long offset,
            int pagesize,String totalsizesql) throws SQLException {
    	if( params != null)
    	{
    		params.buildParams(sql,totalsizesql,prepareDBName);
    		preparedSelectWithTotalsizesql(params.getRealParams(),prepareDBName, params.getNewsql(), offset, pagesize);
    	}
    	else
    		preparedSelectWithTotalsizesql((Params)null,prepareDBName, new NewSQLInfo(sql,totalsizesql), offset, pagesize);
    }
    
    /**
     * Ԥ�����ѯ����
     * 
     * @param sql
     * @throws SQLException
     */
    public void preparedSelect(SQLParams params,String prepareDBName, String sql, long offset,
            int pagesize) throws SQLException {
    	if( params != null)
    	{
    		params.buildParams(sql,prepareDBName);
    		preparedSelect(params.getRealParams(),prepareDBName, params.getNewsql(), offset, pagesize,-1L);
    	}
    	else
    		preparedSelect((Params)null,prepareDBName, new NewSQLInfo(sql), offset, pagesize,-1L);
    }
    
    
    
    /**
     * Ԥ�����ѯ����
     * @mark
     * @param sql
     * @throws SQLException
     */
    public void preparedSelect(SQLParams params,String sql, long offset,
            int pagesize,long totalsize) throws SQLException {
    	preparedSelect( params,null,  sql,  offset,
                 pagesize,totalsize);
    }
    
    /**
     * Ԥ�����ѯ����
     * 
     * @param sql
     * @throws SQLException
     */
    public void preparedSelectWithTotalsizesql(SQLParams params,String sql, long offset,
            int pagesize, String totalsizesql) throws SQLException {
    	preparedSelectWithTotalsizesql( params,null,  sql,  offset,
                 pagesize,totalsizesql);
    }
    
    /**
     * Ԥ�����ѯ����
     * 
     * @param sql
     * @throws SQLException
     */
    public void preparedSelect(SQLParams params,String sql, long offset,
            int pagesize) throws SQLException {
    	preparedSelect( params,null,  sql,  offset,
                 pagesize,-1L);
    }

//	/**
//	 * ����Ԥ����������
//	 * 
//	 * @param sql
//	 * @throws SQLException
//	 */
//	public void preparedSelect(String prepareDBName, String sql, int offset,
//			int pagesize, String oraclerownum) throws SQLException {
//		preparedSelect(prepareDBName, sql, offset,pagesize, oraclerownum,(Connection) null);
//	}
	
//	/**
//     * ����Ԥ�����ѯ���
//     * @deprecated see preparedSelect(String prepareDBName, String sql, long offset,
//            int pagesize, String oraclerownum)
//     */
//    public void preparedSelect(String prepareDBName, String sql, int offset,
//            int pagesize, String oraclerownum) throws SQLException {
//        preparedSelect(prepareDBName, sql, (long )offset,
//                pagesize, oraclerownum);
//    }
	/**
	 * ����Ԥ�����ѯ���
	 * @mark
	 * @param sql
	 * @throws SQLException
	 * preparedSelect(prepareDBName, sql, offset, pagesize, oraclerownum,totalsize);
	 */
	public void preparedSelect(String prepareDBName, String sql, long offset,
			int pagesize, String oraclerownum,long totalsize) throws SQLException {
		preparedSelect( prepareDBName, new SQLInfo( sql,false,false), offset,
				pagesize, oraclerownum,totalsize);
	}
	
	/**
	 * ����Ԥ�����ѯ���
	 * @mark
	 * @param sql
	 * @throws SQLException
	 * preparedSelect(prepareDBName, sql, offset, pagesize, oraclerownum,totalsize);
	 */
	public void preparedSelect(String prepareDBName, SQLInfo sql, long offset,
			int pagesize, String oraclerownum,long totalsize) throws SQLException {
		Params = this.buildParams();
		
		preparedSelect(Params ,prepareDBName, new NewSQLInfo(sql), offset,
				pagesize, oraclerownum,totalsize);
	}
	
	/**
	 * ����Ԥ�����ѯ���
	 * 
	 * @param sql
	 * @throws SQLException
	 * preparedSelectWithTotalsizesql(prepareDBName, sql, offset, pagesize, oraclerownum,totalsizesql);
	 */
	public void preparedSelectWithTotalsizesql(String prepareDBName, String sql, long offset,
			int pagesize, String oraclerownum,String totalsizesql) throws SQLException {
		preparedSelectWithTotalsizesql(prepareDBName, new SQLInfo( sql,false,false), offset,
				pagesize, oraclerownum,new SQLInfo( totalsizesql,false,false));
	}
	
	/**
	 * ����Ԥ�����ѯ���
	 * preparedSelectWithTotalsizesql((Params)null,prepareDBName, sql, offset, pagesize,totalsizesql)
	 * @param sql
	 * @throws SQLException
	 * preparedSelectWithTotalsizesql(prepareDBName, sql, offset, pagesize, oraclerownum,totalsizesql);
	 */
	public void preparedSelectWithTotalsizesql(String prepareDBName, SQLInfo sql, long offset,
			int pagesize, String oraclerownum,SQLInfo totalsizesql) throws SQLException {
		Params = this.buildParams();
		
		preparedSelectWithTotalsizesql(Params ,prepareDBName, new NewSQLInfo(sql,totalsizesql), offset,
				pagesize, oraclerownum);
	}
	
	/**
	 * ����Ԥ�����ѯ���
	 *
	 * @param sql
	 * @throws SQLException
	 */
	public void preparedSelect(String prepareDBName, String sql, long offset,
			int pagesize, String oraclerownum) throws SQLException {
		Params = this.buildParams();
		preparedSelect(Params ,prepareDBName, new NewSQLInfo(sql), offset,
				pagesize, oraclerownum,-1L);
	}
	
//	/**
//	 * ����Ԥ�����ѯ���
//	 * @mark
//	 * @param sql
//	 * @throws SQLException
//	 */
//	public void preparedSelect(Params params,String prepareDBName, NewSQLInfo sql, long offset,
//			int pagesize, String oraclerownum,long totalsize) throws SQLException {
////	    if(params == null)
////            Params = this.buildParams();
////        else
////            Params = params;
//////		Params = params;
////		Params.action = SELECT;
////		this.pagesize = pagesize;
////		Params.totalsize = totalsize;
////		this.offset = StatementInfo.rebuildOffset(offset, pagesize,totalsize);
////		
////		Params.prepareselect_sql = sql;
////		this.oraclerownum = oraclerownum;
////		
////		preparedSql(Params,prepareDBName, sql);
//		preparedSelect(params,prepareDBName, new SQLInfo (sql,false,false),offset,
//				pagesize, oraclerownum,totalsize) ;
//	}
	
	/**
	 * ����Ԥ�����ѯ���
	 * @mark
	 * @param sql
	 * @throws SQLException
	 */
	public void preparedSelect(Params params,String prepareDBName, NewSQLInfo sql, long offset,
			int pagesize, String oraclerownum,long totalsize) throws SQLException {
	    if(params == null)
            Params = this.buildParams();
        else
            Params = params;
//		Params = params;
		Params.action = SELECT;
		this.pagesize = pagesize;
		Params.totalsize = totalsize;
		this.offset = StatementInfo.rebuildOffset(offset, pagesize,totalsize);
		
		Params.prepareSqlifo = sql;
		this.oraclerownum = oraclerownum;
		
		preparedSql(Params,prepareDBName, sql);
	}
	
	
	
	/**
	 * ����Ԥ�����ҳ��ѯ���������ӷ�ҳ�ܼ�¼��sql���
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	public void preparedSelectWithTotalsizesql(Params params,String prepareDBName, NewSQLInfo sql, long offset,
			int pagesize, String oraclerownum) throws SQLException {
	    if(params == null)
            Params = this.buildParams();
        else
            Params = params;
//		Params = params;
		Params.action = SELECT;
		this.offset = offset;
		this.pagesize = pagesize;
		Params.prepareSqlifo= sql;
		this.oraclerownum = oraclerownum;
//		Params.totalsizesql = totalsizesql;
		preparedSql(Params,prepareDBName, sql);
	}
	
	/**
	 * ����Ԥ�����ѯ���
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	public void preparedSelect(Params params,String prepareDBName, NewSQLInfo sql, long offset,
			int pagesize, String oraclerownum) throws SQLException {
		preparedSelect(params,prepareDBName, sql, offset,
				pagesize, oraclerownum,-1L);
	}
	
	
	
	/**
     * ����Ԥ�����ѯ���
     * @mark
     * @param sql
     * @throws SQLException
     */
    public void preparedSelect(SQLParams params,String prepareDBName, String sql, long offset,
            int pagesize, String oraclerownum,long totalsize) throws SQLException {
        if(params != null)
        {
	    	params.buildParams(sql,prepareDBName);
	        preparedSelect(params.getRealParams(),prepareDBName, params.getNewsql(), offset,
	                pagesize, oraclerownum,totalsize);
        }
        else
        {
        	preparedSelect((Params)null,prepareDBName, new NewSQLInfo(sql), offset,
	                pagesize, oraclerownum,totalsize);
        }
    }
    
	/**
     * ����Ԥ�����ѯ���
     * 
     * @param sql
     * @throws SQLException
     */
    public void preparedSelectWithTotalsizesql(SQLParams params,String prepareDBName, String sql, long offset,
            int pagesize, String oraclerownum,String totalsizesql) throws SQLException {
        if(params != null)
        {
	    	params.buildParams(sql,totalsizesql,prepareDBName);
	        preparedSelectWithTotalsizesql(params.getRealParams(),prepareDBName, params.getNewsql(), offset,
	                pagesize, oraclerownum);
        }
        else
        {
        	preparedSelectWithTotalsizesql((Params)null,prepareDBName, new NewSQLInfo(sql,totalsizesql), offset,
	                pagesize, oraclerownum);
        }
    }
    
    /**
     * ����Ԥ�����ѯ���
     * 
     * @param sql
     * @throws SQLException
     */
    public void preparedSelect(SQLParams params,String prepareDBName, String sql, long offset,
            int pagesize, String oraclerownum) throws SQLException {
        if(params != null)
        {
	    	params.buildParams(sql,prepareDBName);
	        preparedSelect(params.getRealParams(),prepareDBName, params.getNewsql(), offset,
	                pagesize, oraclerownum,-1L);
        }
        else
        {
        	preparedSelect((Params)null,prepareDBName, new NewSQLInfo(sql), offset,
	                pagesize, oraclerownum,-1L);
        }
    }
    
    
    /**
     * ����Ԥ�����ѯ���
     * @mark
     * @param sql
     * @throws SQLException
     */
    public void preparedSelect(SQLParams params, String sql, long offset,
            int pagesize, String oraclerownum,long totalsize) throws SQLException {
    	preparedSelect( params,null, sql, offset,
                pagesize, oraclerownum,totalsize);
    }
    
    /**
     * ����Ԥ�����ѯ���
     * 
     * @param sql
     * @throws SQLException
     */
    public void preparedSelectWithTotalsizesql(SQLParams params, String sql, long offset,
            int pagesize, String oraclerownum,String totalsizesql) throws SQLException {
    	preparedSelectWithTotalsizesql( params,null, sql, offset,
                pagesize, oraclerownum,totalsizesql);
    }
    
    /**
     * ����Ԥ�����ѯ���
     * 
     * @param sql
     * @throws SQLException
     */
    public void preparedSelect(SQLParams params, String sql, long offset,
            int pagesize, String oraclerownum) throws SQLException {
    	preparedSelect( params,null, sql, offset,
                pagesize, oraclerownum,-1L);
    }
    
    public static String convertOperationType(int action)
    {
        switch(action)
        {
            case PreparedDBUtil.SELECT:
                return "SELECT";
            case PreparedDBUtil.INSERT:
                return "INSERT";
            case PreparedDBUtil.UPDATE:
                return "UPDATE";
            case PreparedDBUtil.DELETE:
                return "DELETE";
            case PreparedDBUtil.SELECT_COMMON:
                return "SELECT_COMMON";
            default:
                return "unkown action";
                
        }
    }
	
	
//	/**
//	 * �����ض����ݿ��Ԥ����������
//	 * 
//	 * @param dbName
//	 * @param sql
//	 * @throws SQLException
//	 */
//	public void preparedUpdate(String dbName, String sql) throws SQLException {
//		preparedUpdate(dbName, sql,(Connection)null);
//	}
	
	/**
	 * �����ض����ݿ��Ԥ����������
	 * 
	 * @param dbName
	 * @param sql
	 * @throws SQLException
	 */
	public void preparedUpdate(String dbName, String sql) throws SQLException {
		Params = this.buildParams();
		Params.action = UPDATE;
		
		preparedSql(Params,dbName, new NewSQLInfo(sql));
	}
	
//	/**
//     * �����ض����ݿ��Ԥ����������
//     * 
//     * @param dbName
//     * @param sql
//     * @throws SQLException
//     */
//    public void preparedUpdate(Params params,String dbName, String sql) throws SQLException {
//    	preparedUpdate(params,dbName, new SQLInfo( sql,false,false));
//    }
    
    /**
     * �����ض����ݿ��Ԥ����������
     * 
     * @param dbName
     * @param sql
     * @throws SQLException
     */
    public void preparedUpdate(Params params,String dbName, NewSQLInfo sql) throws SQLException {
        if(params == null)
            Params = this.buildParams();
        else
            Params = params;
        Params.action = UPDATE;
        
        preparedSql(Params,dbName,sql);
    }
    
    /**
     * �����ض����ݿ��Ԥ����������
     * 
     * @param dbName
     * @param sql
     * @throws SQLException
     */
    public void preparedUpdate(SQLParams params,String dbName, SQLInfo sql) throws SQLException {
        if(params != null)
        {
        	params.buildParams(sql, dbName);        
        	preparedUpdate(params.getRealParams(),dbName, params.getNewsql());
        }
        else
        {
        	preparedUpdate((Params)null,dbName, new NewSQLInfo(sql));
        }
    }
    
    /**
     * �����ض����ݿ��Ԥ����������
     * 
     * @param dbName
     * @param sql
     * @throws SQLException
     */
    public void preparedUpdate(SQLParams params,String dbName, String sql) throws SQLException {
        if(params != null)
        {
        	params.buildParams(sql, dbName);        
        	preparedUpdate(params.getRealParams(),dbName, params.getNewsql());
        }
        else
        {
        	preparedUpdate((Params)null,dbName, new NewSQLInfo(sql));
        }
    }
    /**
     * �����ض����ݿ��Ԥ����������
     * 
     * @param dbName
     * @param sql
     * @throws SQLException
     */
    public void preparedUpdate(SQLParams params,SQLInfo sql) throws SQLException {
                
        preparedUpdate(params,this.prepareDBName, sql);
    }
    
    /**
     * �����ض����ݿ��Ԥ����������
     * 
     * @param dbName
     * @param sql
     * @throws SQLException
     */
    public void preparedUpdate(SQLParams params,String sql) throws SQLException {
                
        preparedUpdate(params,this.prepareDBName, sql);
    }
    
    /**
     * �����ض����ݿ��Ԥ����������
     * 
     * @param dbName
     * @param sql
     * @throws SQLException
     */
    public void preparedUpdate(Params params, String sql) throws SQLException {
        preparedUpdate(params, this.prepareDBName,new NewSQLInfo(sql));
    }

//	/**
//	 * ����ȱʡ���ݿ�Ԥ����ɾ�����
//	 * 
//	 * @param sql
//	 * @throws SQLException
//	 */
//	public void preparedDelete(String sql) throws SQLException {
//		preparedDelete(sql,(Connection)null);
//	}
	
	/**
	 * ����ȱʡ���ݿ�Ԥ����ɾ�����
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	public void preparedDelete(String sql) throws SQLException {
	    preparedDelete((Params)null,sql);
	}
	
	/**
     * ����ȱʡ���ݿ�Ԥ����ɾ�����
     * 
     * @param sql
     * @throws SQLException
     */
    public void preparedDelete(Params params,String sql) throws SQLException {
        preparedDelete(params,this.prepareDBName, new NewSQLInfo(sql));
    }

//	/**
//	 * �����ض����ݿ�Ԥ����ɾ�����
//	 * 
//	 * @param dbName
//	 * @param sql
//	 * @throws SQLException
//	 */
//	public void preparedDelete(String dbName, String sql) throws SQLException {
//		preparedDelete(dbName, sql,(Connection)null);
//	}
	
	/**
	 * �����ض����ݿ�Ԥ����ɾ�����
	 * 
	 * @param dbName
	 * @param sql
	 * @throws SQLException
	 */
	public void preparedDelete(String dbName, String sql) throws SQLException {
	    preparedDelete((Params)null,dbName, new NewSQLInfo(sql));
	}
	
//	/**
//     * �����ض����ݿ�Ԥ����ɾ�����
//     * 
//     * @param dbName
//     * @param sql
//     * @throws SQLException
//     */
//    public void preparedDelete(Params params,String dbName, NewSQLInfo sql) throws SQLException {
//    	preparedDelete(params,dbName, sql);
//    }
    
    /**
     * �����ض����ݿ�Ԥ����ɾ�����
     * 
     * @param dbName
     * @param sql
     * @throws SQLException
     */
    public void preparedDelete(Params params,String dbName, NewSQLInfo sql) throws SQLException {
        if(params == null)
            Params = this.buildParams();
        else
            Params = params; 
        Params.action = DELETE;
        preparedSql(Params,dbName, sql);
    }
    
    /**
     * �����ض����ݿ�Ԥ����ɾ�����
     * 
     * @param dbName
     * @param sql
     * @throws SQLException
     */
    public void preparedDelete(SQLParams params,String dbName, SQLInfo sql) throws SQLException {
        if(params != null)
        {
        	params.buildParams(sql, dbName);
        	preparedDelete(params.getRealParams(),dbName, params.getNewsql());
        }
        else
        {
        	preparedDelete((Params)null,dbName, new NewSQLInfo(sql));
        }
        
        
    }
    
    public void preparedDelete(SQLParams params,String dbName, String sql) throws SQLException {
        if(params != null)
        {
        	params.buildParams(sql, dbName);
        	preparedDelete(params.getRealParams(),dbName, params.getNewsql());
        }
        else
        {
        	preparedDelete((Params)null,dbName, new NewSQLInfo(sql));
        }
        
        
    }
    
    /**
     * �����ض����ݿ�Ԥ����ɾ�����
     * 
     * @param dbName
     * @param sql
     * @throws SQLException
     */
    public void preparedDelete(SQLParams params, SQLInfo sql) throws SQLException {
        preparedDelete(params, this.prepareDBName,  sql);
    }
    
    /**
     * �����ض����ݿ�Ԥ����ɾ�����
     * 
     * @param dbName
     * @param sql
     * @throws SQLException
     */
    public void preparedDelete(SQLParams params, String sql) throws SQLException {
        preparedDelete(params, this.prepareDBName,  sql);
    }
	protected Param buildParam()
	{
		return new Param();
	}
	
	protected Params buildParams()
	{
		return new Params();
	}
	
	/**
	 * ����Ԥ�������
	 * @param index
	 * @param data
	 * @param method
	 */
	private void  addParam(int index,Object data,String method) throws SQLException
	{
		
		Param param = buildParam();
		param.data = data;
		param.index = index;
		param.method = method;
		Params.params.add(param);
		
	}

	// JDBC 2.0
	public void setArray(int i, Array x) throws SQLException {
		try {
			
//			if (this.action == SELECT)
//				this.statement_count.setArray(i, x);
//			this.statement.setArray(i, x);
			addParam(i,x,Param.SET_ARRAY_INT_ARRAY);		
			
		} catch (Exception e) {
			this.resetFromSetMethod(e);
		}
	}

	public void setAsciiStream(int i, InputStream x, int length)
			throws SQLException {
		try {
//			if (this.action == SELECT)
//				this.statement_count.setAsciiStream(i, x, length);
//			this.statement.setAsciiStream(i, x, length);
			
			this.addParam(i, new Object[] {x,new Integer(length)}, Param.SET_AsciiStream_INT_InputStream_INT);
		} catch (Exception e) {
			this.resetFromSetMethod(e);
		}
	}

	public void setBigDecimal(int i, BigDecimal x) throws SQLException {
		try {
//			if (this.action == SELECT)
//				this.statement_count.setBigDecimal(i, x);
//			this.statement.setBigDecimal(i, x);
			this.addParam(i, x, Param.SET_BigDecimal_INT_BigDecimal);
		} catch (SQLException e) {
			this.resetFromSetMethod(e);
		}
	}

	public void setBinaryStream(int i, InputStream x, int length)
			throws SQLException {
		try {

//			if (this.action == SELECT)
//				this.statement_count.setAsciiStream(i, x, length);
//			this.statement.setBinaryStream(i, x, length);
			
			
			this.addParam(i, new Object[]{x,new Integer(length)}, Param.setBinaryStream_int_InputStream_int);
		} catch (SQLException e) {
			this.resetFromSetMethod(e);
		}
	}

	// public void setBlob(int i, Blob x) throws SQLException
	// {
	// this.initBigdata();
	// BigData bigData = new BigData();
	// // bigData.bigdata = this.statement.setBlob(i, x);
	// }

	public void setBlob(int i, byte[] x) throws SQLException {
		try {
//			this.initBigdata();
//			BigData bigData = new BigData();
//			bigData.bigdata = x;
//			bigData.index = i;
//			bigData.type = bigData.BLOB;
//			Params.bigdatas.add(bigData);
//			// if (this.action == this.INSERT)
////			this.statement.setBlob(i, BLOB.empty_lob());
			
//			this.addParam(i, BLOB.empty_lob(), Param.setBlob_int_bytearray);
			if(x != null)
				
			{
//				ByteArrayInputStream bis=new ByteArrayInputStream(x);
////				PreparedStatement stmt ;
////				stmt.setBinaryStream(parameterIndex, x, length);
////				stmt.set
//				this.setBinaryStream(i, bis, x.length);
				this.addParam(i, x, Param.setBlob_int_bytearray);
//				this.addParam(i, data, method)
			}
			else
			{
				this.setNull(i, Types.BLOB);
			}
		} catch (SQLException e) {
			this.resetFromSetMethod(e);
		}

	}
	
	public void setBlob(int i, Blob blob) throws SQLException {
		try {
//			this.initBigdata();
//			BigData bigData = new BigData();
//			bigData.bigdata = x;
//			bigData.index = i;
//			bigData.type = bigData.BLOB;
//			Params.bigdatas.add(bigData);
//			// if (this.action == this.INSERT)
////			this.statement.setBlob(i, BLOB.empty_lob());
			
//			this.addParam(i, BLOB.empty_lob(), Param.setBlob_int_bytearray);
			if(blob != null)
				
			{
//				ByteArrayInputStream bis=new ByteArrayInputStream(x);
////				PreparedStatement stmt ;
////				stmt.setBinaryStream(parameterIndex, x, length);
////				stmt.set
//				this.setBinaryStream(i, bis, x.length);
//				this.addParam(i, data, method)
				this.addParam(i, blob, Param.setBlob_int_blob);
			}
			else
			{
				this.setNull(i, Types.BLOB);
			}
		} catch (SQLException e) {
			this.resetFromSetMethod(e);
		}

	}
	
	
	public void setClob(int i, Clob clob) throws SQLException {
		try {
//			this.initBigdata();
//			BigData bigData = new BigData();
//			bigData.bigdata = x;
//			bigData.index = i;
//			bigData.type = bigData.BLOB;
//			Params.bigdatas.add(bigData);
//			// if (this.action == this.INSERT)
////			this.statement.setBlob(i, BLOB.empty_lob());
			
//			this.addParam(i, BLOB.empty_lob(), Param.setBlob_int_bytearray);
			if(clob != null)
				
			{
//				ByteArrayInputStream bis=new ByteArrayInputStream(x);
////				PreparedStatement stmt ;
////				stmt.setBinaryStream(parameterIndex, x, length);
////				stmt.set
//				this.setBinaryStream(i, bis, x.length);
//				this.addParam(i, data, method)
				this.addParam(i, clob, Param.setClob_int_Clob);
			}
			else
			{
				this.setNull(i, Types.CLOB);
			}
		} catch (SQLException e) {
			this.resetFromSetMethod(e);
		}

	}
	
	

	public void setBlob(int i, byte[] x, String field) throws SQLException {
//		try {
//			this.initBigdata();
//			BigData bigData = new BigData();
//			bigData.bigdata = x;
//			bigData.index = i;
//			bigData.type = bigData.BLOB;
//			bigData.bigdataField = field;
//			String s;
//			Params.bigdatas.add(bigData);
//			// if (this.action == this.INSERT)
////			this.statement.setBlob(i, BLOB.empty_lob());
//			
//			this.addParam(i, BLOB.empty_lob(), Param.setBlob_int_bytearray_String);
//		} catch (SQLException e) {
//			this.resetFromSetMethod(e);
//		}
		this.setBlob(i, x);
	}

	public void setBlob(int i, File x) throws SQLException {
//		try {
//			this.initBigdata();
//			BigData bigData = new BigData();
//			bigData.bigdata = x;
//			bigData.index = i;
//			bigData.type = BigData.BLOB;
//			Params.bigdatas.add(bigData);
//			// if (this.action == INSERT)
////			this.statement.setBlob(i, BLOB.empty_lob());
//			
//			this.addParam(i, BLOB.empty_lob(), Param.setBlob_int_File);
//		} catch (SQLException e) {
//			this.resetFromSetMethod(e);
//		}
		if(x == null)
		{
			this.setNull(i, Types.BLOB);
		}
		else
		{
			this.addParam(i, x, Param.setBlob_int_File);
		}
	}
	
	public void setBlob(int i, String x) throws SQLException {
//		try {
//			this.initBigdata();
//			BigData bigData = new BigData();
//			bigData.bigdata = x;
//			bigData.index = i;
//			bigData.type = BigData.BLOB;
//			Params.bigdatas.add(bigData);
//			// if (this.action == INSERT)
////			this.statement.setBlob(i, BLOB.empty_lob());
//			
//			this.addParam(i, BLOB.empty_lob(), Param.setBlob_int_File);
//		} catch (SQLException e) {
//			this.resetFromSetMethod(e);
//		}
		if(x == null)
		{
			this.setNull(i, Types.BLOB);
		}
		else
		{
//			this.setBinaryStream(i, getInputStream(x), Integer.MAX_VALUE);
			this.addParam(i, x, Param.setBlob_int_blob);
			
		}
	}

	public String getString(File x) throws SQLException
	{
		java.io.FileInputStream in = null ;
		ByteArrayOutputStream out = null;
		
			try {
				in = new java.io.FileInputStream(x);
				out = new ByteArrayOutputStream();
				byte v[] = new byte[1024];
				int i = 0;
				while((i = in.read(v)) > 0)
				{
					out.write(v, 0, i);
				}
				return new String(out.toByteArray());
				
			} catch (FileNotFoundException e) {
				throw new NestedSQLException(e);
			} catch (IOException e) {
				throw new NestedSQLException(e);
			}
			finally
			{
				if(in != null)
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				if(out != null)
					try {
						out.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			}
		
		
	}
	public String getString(InputStream in) throws SQLException
	{
		
		ByteArrayOutputStream out = null;
		
			try {
		
				out = new ByteArrayOutputStream();
				byte v[] = new byte[1024];
				int i = 0;
				while((i = in.read(v)) > 0)
				{
					out.write(v, 0, i);
				}
				return new String(out.toByteArray());
				
			} catch (FileNotFoundException e) {
				throw new NestedSQLException(e);
			} catch (IOException e) {
				throw new NestedSQLException(e);
			}
			finally
			{
				if(in != null)
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				if(out != null)
					try {
						out.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			}
		
		
	}
	public InputStream getInputStream(File x) throws SQLException
	{
		java.io.FileInputStream in = null ;
		ByteArrayOutputStream out = null;
		
			try {
				in = new java.io.FileInputStream(x);
				out = new ByteArrayOutputStream();
				byte v[] = new byte[1024];
				int i = 0;
				while((i = in.read(v)) > 0)
				{
					out.write(v, 0, i);
				}
				return new ByteArrayInputStream(out.toByteArray());
				
			} catch (FileNotFoundException e) {
				throw new NestedSQLException(e);
			} catch (Exception e) {
				throw new NestedSQLException(e);
			}
			finally
			{
				if(in != null)
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				if(out != null)
					try {
						out.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			}
		
		
	}
	
	
	

	public void setBlob(int i, File x, String field) throws SQLException {
//		try {
//			
//			this.initBigdata();
//			BigData bigData = new BigData();
//			bigData.bigdata = x;
//			bigData.index = i;
//			bigData.type = BigData.BLOB;
//			bigData.bigdataField = field;
//			Params.bigdatas.add(bigData);
//			// if (this.action == INSERT)
////			this.statement.setBlob(i, BLOB.empty_lob());
//			
//			this.addParam(i, BLOB.empty_lob(), Param.setBlob_int_File_String);
//		} catch (SQLException e) {
//			this.resetFromSetMethod(e);
//		}
		this.setBlob(i, x);
	}
	
	public void setClob(int i, File file) throws SQLException {
//		try {
//			this.initBigdata();
//			BigData bigData = new BigData();
//			bigData.bigdata = file;
//			bigData.index = i;
//			bigData.type = BigData.CLOB;			
//			Params.bigdatas.add(bigData);
//			// if (this.action == INSERT)
////			this.statement.setBlob(i, BLOB.empty_lob());
//			
//			this.addParam(i, CLOB.empty_lob(), Param.setClob_int_File);
//		} catch (SQLException e) {
//			this.resetFromSetMethod(e);
//		}
//		java.io.FileReader reader;
		
//			reader = new java.io.FileReader(file);
//		StringReader reader  = new java.io.StringReader(getString( file));
//		this.setCharacterStream(i, reader, Integer.MAX_VALUE);
		this.addParam(i, file, Param.setClob_int_File);
		
		
		
	}

	public void setClob(int i, File file, String field) throws SQLException {
//		try {
//			this.initBigdata();
//			BigData bigData = new BigData();
//			bigData.bigdata = file;
//			bigData.index = i;
//			bigData.type = BigData.CLOB;
//			bigData.bigdataField = field;
//			Params.bigdatas.add(bigData);
//			// if (this.action == INSERT)
////			this.statement.setBlob(i, BLOB.empty_lob());
//			
//			this.addParam(i, CLOB.empty_lob(), Param.setClob_int_File_String);
//		} catch (SQLException e) {
//			this.resetFromSetMethod(e);
//		}
		this.setClob(i, file);
		
	}

	public void setBoolean(int i, boolean x) throws SQLException {
		try {
//			if (this.action == SELECT)
//				this.statement_count.setBoolean(i, x);
//			this.statement.setBoolean(i, x);
			
			this.addParam(i, new Boolean(x), Param.setBoolean_int_boolean);
		} catch (SQLException e) {
			this.resetFromSetMethod(e);
		}
	}

	public void setByte(int i, byte b) throws SQLException {
		try {
//			if (this.action == SELECT)
//				this.statement_count.setByte(i, b);
//			this.statement.setByte(i, b);
			this.addParam(i, new Byte(b), Param.setByte_int_byte);
		} catch (SQLException e) {
			this.resetFromSetMethod(e);
		}
	}

	public void setBytes(int i, byte[] x) throws SQLException {
		try {
//			if (this.action == SELECT)
//				this.statement_count.setBytes(i, x);
//			this.statement.setBytes(i, x);
			this.addParam(i, x, Param.setBytes_int_bytearray);
		} catch (SQLException e) {
			this.resetFromSetMethod(e);
		}
	}

	public void setCharacterStream(int i, Reader reader, int length)
			throws SQLException {
		try {
//			if (this.action == SELECT)
//				this.statement_count.setCharacterStream(i, reader, length);
//			this.statement.setCharacterStream(i, reader, length);
			this.addParam(i, new Object[] {reader,new Integer(length)}, Param.setCharacterStream_int_Reader_int);
		} catch (SQLException e) {
			this.resetFromSetMethod(e);
		}
	}


	
	/**
	 * ����clob�ֶε�ֵ��clob�ֶε����ƴ�insert����fields�ֶ��в��һ��,��
	 * Ҫ���ֶε�λ�ú�i����һ��
	 * @param i
	 * @param content
	 * @throws SQLException
	 */
	public void setClob(int i, String content) throws SQLException {
//		try {
//			if (SQLManager.getInstance().getDBAdapter(this.prepareDBName)
//					.getDBTYPE().equals(DBFactory.DBOracle)) {
//				this.initBigdata();
//				BigData bigdata = new BigData();
//				bigdata.bigdata = content;
//				bigdata.type = BigData.CLOB;
//				bigdata.index = i;
//				Params.bigdatas.add(bigdata);
//				// if(action == INSERT)
////				this.statement.setClob(i, CLOB.empty_lob());
//				this.addParam(i, CLOB.empty_lob(), Param.setClob_int_String);
//			} else {
////				this.statement.setString(i, content);
//				this.addParam(i, content, Param.setString_int_String);
//			}
//		} catch (SQLException e) {
//			this.resetFromSetMethod(e);
//		}
		if(content != null)
		{
//			java.io.StringReader reader = new java.io.StringReader(content);
//			this.setCharacterStream(i, reader, content.length());
//			this.setClob(i, content)
			this.addParam(i, content, Param.setClob_int_String);
		}
		else
		{
			this.setNull(i, Types.CLOB);
		}

	}

	public void setClob(int i, String content, String field)
			throws SQLException {
//		try {
//			if (SQLManager.getInstance().getDBAdapter(this.prepareDBName)
//					.getDBTYPE().equals(DBFactory.DBOracle)) {
//				this.initBigdata();
//
//				BigData bigdata = new BigData();
//				bigdata.bigdata = content;
//				bigdata.type = BigData.CLOB;
//				bigdata.index = i;
//				bigdata.bigdataField = field;
//				Params.bigdatas.add(bigdata);
//				// if(action == INSERT)
////				this.statement.setClob(i, CLOB.empty_lob());
//				this.addParam(i, CLOB.empty_lob(), Param.setClob_int_String_String);
//			} else {
//				// this.statement.setClob(i, clob);
//				this.addParam(i, content, Param.setString_int_String);
//			}
//		} catch (SQLException e) {
//			this.resetFromSetMethod(e);
//		}
		this.setClob(i, content);

	}

	public void setDate(int i, java.sql.Date d) throws SQLException {
		try {
//			if (this.action == SELECT)
//				this.statement_count.setDate(i, d);
//			this.statement.setDate(i, d);
			this.addParam(i, d, Param.setDate_int_sqlDate);
		} catch (SQLException e) {
			this.resetFromSetMethod(e);
		}
	}

	/**
	 * ������ͨ�õ�date���͵�����
	 * 
	 * @param i
	 * @param d
	 *            ��������
	 * @throws SQLException
	 */
	public void setDate(int i, java.util.Date d) throws SQLException {
		try {
			if (d != null) {
//				if (this.action == SELECT)
//					this.statement_count.setDate(i, new java.sql.Date(d
//							.getTime()));
//				this.statement.setDate(i, new java.sql.Date(d.getTime()));
				this.addParam(i, new java.sql.Date(d.getTime()), Param.setDate_int_utilDate);
			} else {
//				if (this.action == SELECT)
//					this.statement_count.setNull(i, Types.NULL);
//				this.statement.setNull(i, Types.NULL);
				this.addParam(i, new Integer(Types.DATE), Param.setNull_int_int);
			}
		} catch (SQLException e) {
			this.resetFromSetMethod(e);
		}

	}

	public void setDate(int i, java.sql.Date d, Calendar cal)
			throws SQLException {
		try {
//			if (this.action == SELECT)
//				this.statement_count.setDate(i, d, cal);
//			this.statement.setDate(i, d, cal);
			this.addParam(i, new Object[] {d,cal}, Param.setDate_int_Date_Calendar);
		} catch (SQLException e) {
			this.resetFromSetMethod(e);
		}
	}

	public void setDouble(int i, double d) throws SQLException {
		try {
//			if (this.action == SELECT)
//				this.statement_count.setDouble(i, d);
//			this.statement.setDouble(i, d);
			this.addParam(i, new Double(d),Param.setDouble_int_double);
		} catch (SQLException e) {
			this.resetFromSetMethod(e);
		}
	}

	public void setFloat(int i, float f) throws SQLException {
		try {
//			if (this.action == SELECT)
//				this.statement_count.setFloat(i, f);
//			this.statement.setFloat(i, f);
			this.addParam(i, new Float(f), Param.setFloat_int_float);
		} catch (SQLException e) {
			this.resetFromSetMethod(e);
		}
	}

	// /**
	// * �����´���clob��blob�ֶε�
	// *
	// * @param i
	// * @param key
	// * @throws SQLException
	// */
	// public void setPrimaryKey(int i, int value, String table, String keyName)
	// throws SQLException {
	// this.updateKeyInfo = new UpdateKeyInfo();
	// this.updateKeyInfo.tableName = table;
	// this.updateKeyInfo.keyValue = value + "";
	// this.updateKeyInfo.keyName = keyName;
	// this.statement.setInt(i, value);
	// }

	// /**
	// * �����´���clob��blob�ֶε�
	// *
	// * @param i
	// * @param key
	// * @throws SQLException
	// */
	// public void setPrimaryKey(int i, String value, String table, String
	// keyName)
	// throws SQLException {
	// this.updateKeyInfo = new UpdateKeyInfo();
	// this.updateKeyInfo.tableName = table;
	// this.updateKeyInfo.keyValue = value + "";
	// this.updateKeyInfo.keyName = keyName;
	// this.statement.setString(i, value);
	// }

	public void setInt(int i, int x) throws SQLException {
		try {
//			if (this.action == SELECT)
//				this.statement_count.setInt(i, x);
//			this.statement.setInt(i, x);
			this.addParam(i, new Integer(x), Param.setInt_int_int);
		} catch (SQLException e) {
			this.resetFromSetMethod(e);
		}
	}

	public void setLong(int i, long l) throws SQLException {
		try {
//			if (this.action == SELECT)
//				this.statement_count.setLong(i, l);
//			this.statement.setLong(i, l);
			this.addParam(i, new Long(l), Param.setLong_int_long);
		} catch (SQLException e) {
			this.resetFromSetMethod(e);
		}
	}

	public void setNull(int i, int sqlType) throws SQLException {
		try {
//			if (this.action == SELECT)
//				this.statement_count.setNull(i, sqlType);
//			this.statement.setNull(i, sqlType);
			this.addParam(i, new Integer(sqlType), Param.setNull_int_int);
		} catch (SQLException e) {
			this.resetFromSetMethod(e);
		}
	}

	public void setNull(int i, int sqlType, String typeName)
			throws SQLException {
		try {
//			if (this.action == SELECT)
//				this.statement_count.setNull(i, sqlType, typeName);
//			this.statement.setNull(i, sqlType, typeName);
			this.addParam(i, new Object[] {new Integer(sqlType),typeName}, Param.setNull_int_int_String);
		} catch (SQLException e) {
			this.resetFromSetMethod(e);
		}
	}

	public void setObject(int i, Object o) throws SQLException {
		try {
//			if (this.action == SELECT)
//				this.statement_count.setObject(i, o);
//			this.statement.setObject(i, o);
			this.addParam(i, o, Param.setObject_int_Object);
		} catch (SQLException e) {
			this.resetFromSetMethod(e);
		}
	}

	public void setObject(int i, Object o, int targetType) throws SQLException {
		try {
//			if (this.action == SELECT)
//				this.statement_count.setObject(i, o, targetType);
//			this.statement.setObject(i, o, targetType);
			this.addParam(i, new Object[]{o,new Integer(targetType)}, Param.setObject_int_Object_int);
		} catch (SQLException e) {
			this.resetFromSetMethod(e);
		}
	}

	public void setObject(int i, Object o, int targetType, int scale)
			throws SQLException {
		try {
//			if (this.action == SELECT)
//				this.statement_count.setObject(i, o, targetType, scale);
//			this.statement.setObject(i, o, targetType, scale);
			this.addParam(i, new Object[]{o,new Integer(targetType),new Integer(scale)}, Param.setObject_int_Object_int_int);
		} catch (SQLException e) {
			this.resetFromSetMethod(e);
		}
	}

	public void setRef(int i, Ref ref) throws SQLException {
		try {
//			if (this.action == SELECT)
//				this.statement_count.setRef(i, ref);
//			this.statement.setRef(i, ref);
			this.addParam(i,ref, Param.setRef_int_Ref);
		} catch (SQLException e) {
			this.resetFromSetMethod(e);
		}
	}

	public void setShort(int i, short s) throws SQLException {
		try {
//			if (this.action == SELECT)
//				this.statement_count.setShort(i, s);
//			this.statement.setShort(i, s);
			this.addParam(i, new Short(s), Param.setShort_int_short);
		} catch (SQLException e) {
			this.resetFromSetMethod(e);
		}
	}

	public void setString(int i, String s) throws SQLException {
		try {
//			if (this.action == SELECT)
//				this.statement_count.setString(i, s);
//			this.statement.setString(i, s);
			this.addParam(i, s, Param.setString_int_String);
		} catch (SQLException e) {
			this.resetFromSetMethod(e);
		}
	}

	public void setTime(int i, Time t) throws SQLException {
		try {
//			if (this.action == SELECT)
//				this.statement_count.setTime(i, t);
//			this.statement.setTime(i, t);
			this.addParam(i, t, Param.setTime_int_Time);
		} catch (SQLException e) {
			this.resetFromSetMethod(e);
		}
	}

	public void setTime(int i, Time t, Calendar cal) throws SQLException {
		try {
//			if (this.action == SELECT)
//				this.statement_count.setTime(i, t, cal);
//			this.statement.setTime(i, t, cal);
			this.addParam(i, new Object[]{t,cal}, Param.setTime_int_Time_Calendar);
		} catch (SQLException e) {
			this.resetFromSetMethod(e);
		}
	}

	public void setTimestamp(int i, Timestamp t) throws SQLException {
		try {
//			if (this.action == SELECT)
//				this.statement_count.setTimestamp(i, t);
//			this.statement.setTimestamp(i, t);
			this.addParam(i, t, Param.setTimestamp_int_Timestamp);
		} catch (SQLException e) {
			this.resetFromSetMethod(e);
		}
	}

	public void setTimestamp(int i, Timestamp t, Calendar cal)
			throws SQLException {
		try {
//			if (this.action == SELECT)
//				this.statement_count.setTimestamp(i, t, cal);
//			this.statement.setTimestamp(i, t, cal);
			this.addParam(i, new Object[]{t,cal}, Param.setTimestamp_int_Timestamp_Calendar);
		} catch (SQLException e) {
			this.resetFromSetMethod(e);
		}
	}

	public void setUnicodeStream(int i, InputStream x, int length)
			throws SQLException {
		try {
//			if (this.action == SELECT)
//				this.statement_count.setAsciiStream(i, x, length);
//			this.statement.setUnicodeStream(i, x, length);
			this.addParam(i, new Object[]{x,new Integer(length)}, Param.setUnicodeStream_int_InputStream_int);
		} catch (SQLException e) {
			this.resetFromSetMethod(e);
		}
	}

	/**
	 * @return Returns the prepareDBName.
	 */
	public String getPrepareDBName() {
		return prepareDBName;
	}

	/**
	 * @param prepareDBName
	 *            The prepareDBName to set.
	 */
	public void setPrepareDBName(String prepareDBName) {
		this.prepareDBName = prepareDBName;
	}

	public static void main(String[] args) {
		PreparedDBUtil dbUtil = new PreparedDBUtil();
		String sql = "insert into tableinfo(1) values(?)";
		try {
			dbUtil.preparedInsert(sql);
			dbUtil.setInt(0, 1);
			Object obj = dbUtil.executePrepared();
		} catch (SQLException e) {			
			e.printStackTrace();
		}

	}

	/**
	 * ����Ԥ�������
	 * 
	 */
	public void resetPrepare() {
		try {
			this.resetFromSetMethod(null);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/*******************************************************************************************
	 *  ��չprepared������ܣ���������Ԥ��������Ĵ���,��صķ������£�
	 *  void addPreparedBatch()
	 *  Object[] executePreparedBatch()
	 *  
	 *******************************************************************************************/
	/**
	 * ��ʾһ�����������Ĳ����������
	 * ׼��������һ����������䣬���ֻ��һ������������¼��Ҫ������ֱ��ʹ��params��������
	 * ������¼�Ĳ�����Ȼ�����executePrepared()����������������
	 */
	
	public void addPreparedBatch()
	{
		if(this.batchparams == null)
		{
			batchparams = new ArrayList<Params>();
//			batchparamsIDXBySQL = new HashMap();
		}
		batchparams.add(this.Params);
//		batchparamsIDXBySQL.put(Params.prepareselect_sql, Params);
		NewSQLInfo old = this.Params.prepareSqlifo;
		Params = this.buildParams();
		Params.prepareSqlifo = old;
		
		
	}
	
	public void addPreparedBatch(ListSQLParams batchsqlparams_) throws SQLException
	{
		if(batchsqlparams_ == null || batchsqlparams_.getSqlparams() == null || batchsqlparams_.getSqlparams().size() == 0)
			throw new SQLException("batchsqlparams == null || batchsqlparams.size() == 0");
		if(this.batchparams == null)
		{
			batchparams = new ArrayList<Params>();
		}
		List<SQLParams> batchsqlparams = batchsqlparams_.getSqlparams(); 
		int i = 0;
		boolean multiparser = batchsqlparams_.multiparser();
		NewSQLInfo newsql = null;
		for(SQLParams sqlParams:batchsqlparams)
		{
			if(!multiparser )
			{
				if(i == 0)
				{
					sqlParams.buildParams( this.prepareDBName);
					newsql = sqlParams.getNewsql();
					i ++;
				}
				else
				{
					sqlParams.buildParamsNewSQLInfo( this.prepareDBName,newsql);
				}
								
			}
			else
				sqlParams.buildParams( this.prepareDBName);
			this.Params = sqlParams.getRealParams();
			this.Params.prepareSqlifo = sqlParams.getNewsql();
			batchparams.add(this.Params);
			
			
		}
		
		
	}
	
	/**
	 * �û����Ե���Ԥ�����������Ż���ʶ��Ϊtrueʱ�Ὣ���е�������������򣬽���ͬ��sql���ŵ�һ��
	 * ͨ��ͳһ��preparedstatement�����������������
	 * @param batchOptimize
	 */
	public void setBatchOptimize(boolean batchOptimize)
	{
		this.batchOptimize = batchOptimize;
	}
	
	private String buildInfo(int r[])
	{
		if(r.length == 0)
			return "No sql been executed.";
		StringBuffer ret = new StringBuffer();
		for(int i = 0; i < r.length; i ++)
		{
			if(i == 0)
				ret.append(i )
					.append(":").append(r[i]);
			else
			{
				ret.append(",").append(i )
				.append(":").append(r[i]);
			}
		}
		return ret.toString();
	}
	public void executePreparedBatch(Connection con_) throws SQLException
	{
		executePreparedBatch(con_,null) ;
	}
	/**
	 * ִ��Ԥ�������������,֧���������con������������������ӣ���ʹ�ø���������
	 * ���con == null���ж��ⲿ�����Ƿ���ڣ�������ڴ��ⲿ�����л�ȡһ������������������������
	 * ���������������ӳ��л�ȡһ������,���е�������������������һ������֮�С�
	 * �����ָ���ض����ݿ����������Ҫ����this.setPrepareDBName(prepareDBName);����ָ��ִ�е��߼�
	 * ���ݿ�����	
	 * @param con �ⲿ��������ݿ�����
	 * @param getCUDResult �Ƿ񷵻ش����������������ݵĴ��������������¼�¼�����Զ�������������Ϣ
	 * @return
	 * @throws SQLException
	 */
	public void executePreparedBatch(Connection con_,GetCUDResult CUDResult) throws SQLException
	{
		if(this.batchparams == null || batchparams.size() == 0)
		{
//			throw new SQLException("Can not execute single prepared statement as batch prepared operation,Please call method executePrepared(Connection con)!");
			log.info("Can not execute single prepared statement as batch prepared operation,Please call method executePrepared(Connection con)!");
			return ;
		}
		StatementInfo stmtInfo = null;

		
		PreparedStatement statement = null;
		List resources = null;
//		GetCUDResult CUDResult = null;
		try
		{	
			stmtInfo = new StatementInfo(this.prepareDBName,
					null,
					false,
					 con_,
					 false);
			stmtInfo.init();
			//�����Ҫ�Ż�����sql������Ҫ����
			if(this.batchOptimize)
			{
				java.util.Collections.sort(batchparams);
			}
			NewSQLInfo old_sql = null;
			boolean showsql = showsql(stmtInfo.getDbname());
			
			while(batchparams.size() > 0)
			{
				Params Params = (Params)batchparams.remove(0);
				if (Params.action == SELECT) {
					throw new SQLException("Batch prepared Operation do not support pagine query opations.");
				} else if (Params.action == SELECT_COMMON) {
					throw new SQLException("Batch prepared Operation do not support query opations.");
				} 
				
				if(old_sql == null )
				{
					
					old_sql = Params.prepareSqlifo;
					if(showsql)
					{
						log.debug("Execute JDBC prepared batch statement:"+Params.prepareSqlifo.getNewsql());
					}
					statement = stmtInfo
							.prepareStatement(Params.prepareSqlifo.getNewsql());
					if(resources == null)
						resources = new ArrayList();
					setUpParams(Params,statement,resources);
					statement.addBatch();
					
				}
//				else if(Params.prepareselect_sql == null)
//				{
//					setUpParams(Params,statement);	
//					statement.addBatch();
//				}
				else if(!old_sql.equals(Params.prepareSqlifo))
				{
					try
					{
						int[] ret = statement.executeBatch();	
						if(showsql)
						{
							log.debug(new StringBuffer("Execute prepared Batch sql[")
							.append(old_sql)
							.append("] success:")
							.append(buildInfo(ret))
							.toString());
						}
					}
					finally
					{
						try
						{
							statement.close();
						}
						catch(Exception e)
						{
							
						}
						statement = null;
						if(resources != null)
						{
							this.releaseResources(resources);
							resources = null;
						}
					}
					
					old_sql = Params.prepareSqlifo;
					if(showsql)
					{
						log.debug("Execute JDBC prepared batch statement:"+Params.prepareSqlifo.getNewsql());
					}
					statement = stmtInfo
							.prepareStatement(Params.prepareSqlifo.getNewsql());
					if(resources == null)
						resources = new ArrayList();
					setUpParams(Params,statement,resources);	
					statement.addBatch();
				}	
				else
				{			
					if(resources == null)
						resources = new ArrayList();
					setUpParams(Params,statement,resources);	
					statement.addBatch();
				}
				
				if(batchparams.size() == 0)
				{
					try
					{
						int[] ret = statement.executeBatch();	
						if(showsql)
						{
							log.debug(new StringBuffer("Execute prepared Batch sql[")
							.append(old_sql)
							.append("] success:")
							.append(buildInfo(ret))
							.toString());
						}
					
						
						
						if(CUDResult != null)
						{		
							List<Object> morekeys = getGeneratedKeys(statement);
//							CUDResult = new GetCUDResult(ret,ret,morekeys);
							CUDResult.setKeys(morekeys);
							CUDResult.setResult(ret);
							CUDResult.setUpdatecount(ret);
						}
//						else
//							CUDResult = new GetCUDResult(ret,ret,null);
					}
					finally
					{
						try
						{
							statement.close();
						}
						catch(Exception e)
						{
							
						}
						statement = null;
						if(resources != null)
						{
							this.releaseResources(resources);
							resources = null;
						}
					}
//					statement = null;
					break;
				}
				
				
						
			}
		}
		catch(BatchUpdateException error)
		{
//			try{
//				
//				log.error("Execuete batch prepared Error:" + error.getMessage(), error);
//			}
//			catch(Exception ei)
//			{
//				
//			}
			
			if(stmtInfo != null)
				stmtInfo.errorHandle(error);
			
			
			throw error;
		}
	    catch (Exception e) {
//	    	try{
//				
//	    		log.error("Execuete batch prepared Error:" + e.getMessage(), e);
//			}
//			catch(Exception ei)
//			{
//				
//			}
			
	    	
			if(stmtInfo != null)
				stmtInfo.errorHandle(e);
			if(e instanceof SQLException)
				throw (SQLException)e;
			else
				throw new NestedSQLException(e.getMessage(),e);
		} finally {
			if(stmtInfo != null)
				stmtInfo.dofinally();
			stmtInfo = null;
			if(resources != null)
			{
				this.releaseResources(resources);
			}
				
			this.resetFromSetMethod(null);
		}
//		return CUDResult;

	}



	
	

	
}

