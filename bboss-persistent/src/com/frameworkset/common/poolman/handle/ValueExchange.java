package com.frameworkset.common.poolman.handle;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DecimalFormat;

import oracle.jdbc.OracleTypes;
import sun.misc.BASE64Encoder;

import com.frameworkset.common.poolman.NestedSQLException;
import com.frameworkset.common.poolman.util.SQLUtil;
import com.frameworkset.orm.adapter.DB;
import com.frameworkset.util.NoSupportTypeCastException;
import com.frameworkset.util.ValueObjectUtil;

/**
 * 
 * 
 * <p>
 * Title: TypeHandler.java
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * ���ݽ�����ʵ�����ݿ��¼�ֶε�ֵ��java����������ֵ��ת��
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * 
 * 
 * @Date Oct 29, 2008 10:36:23 AM
 * @author biaoping.yin
 * @version 1.0
 */
public class ValueExchange {
//	public static final TypeHandlerFactory typeHandlerFactory = new TypeHandlerFactory();

//	public Object valueChange(CallableStatement cstmt, Class toType,
//			int sqlType, String dbname) {
//		switch (sqlType) {
//			
//		}
//		return null;
//	}
//
//	public Object valueChange(ResultSet rs, Class toType, int sqlType,
//			String dbname) {
//		return null;
//	}
	
	
//	public static TypeHandler getTypeHandler(int sqlType)
//	{
//		TypeHandler handler = null;
//		switch (sqlType) {
//
//		// case Types.LONGVARBINARY:
//		    /**********************************************************************
//		     * Types.BIT Types.BOOLEAN java.lang.Boolean
//		     **********************************************************************/
//			/**
//			 * <P>The constant in the Java programming language, sometimes referred
//			 * to as a type code, that identifies the generic SQL type 
//			 * <code>BIT</code>.
//			 */
//			case Types.BIT :
//				/**
//			     * The constant in the Java programming language, somtimes referred to
//			     * as a type code, that identifies the generic SQL type <code>BOOLEAN</code>.
//			     *
//			     * @since 1.4
//			     */
//			case Types.BOOLEAN :
//			
////				BooleanTypeHandler b = new BooleanTypeHandler();
//				handler = typeHandlerFactory.getTypeHandler(boolean.class);
////				return b.getResult(cs, columnIndex, javaType);
//				return handler;
//				
//		 /**********************************************************************
//	     * Types.TINYINT byte
//	     **********************************************************************/
//			/**
//			 * <P>The constant in the Java programming language, sometimes referred
//			 * to as a type code, that identifies the generic SQL type 
//			 * <code>TINYINT</code>.
//			 */
//			case Types.TINYINT :
////				ByteTypeHandler byteHander = new ByteTypeHandler();
////				return byteHander.getResult(cs, columnIndex, javaType);
//				handler = typeHandlerFactory.getTypeHandler(byte.class);
//				return handler;
//		 /**********************************************************************
//	     * Types.SMALLINT short
//	     **********************************************************************/
//			/**
//			 * <P>The constant in the Java programming language, sometimes referred
//			 * to as a type code, that identifies the generic SQL type 
//			 * <code>SMALLINT</code>.
//			 */
//			case Types.SMALLINT :
//				handler = typeHandlerFactory.getTypeHandler(short.class);
//				return handler;
////				return shortHandler.getResult(cs, columnIndex, javaType);
//		
//		 /**********************************************************************
//	     * Types.SMALLINT int
//	     **********************************************************************/
//			/**
//			 * <P>The constant in the Java programming language, sometimes referred
//			 * to as a type code, that identifies the generic SQL type 
//			 * <code>INTEGER</code>.
//			 */
//			case Types.INTEGER :
//				handler = typeHandlerFactory.getTypeHandler(int.class);
//				return handler;
//		/**********************************************************************
//	     * Types.BIGINT  long
//	     **********************************************************************/
//		/**
//		 * <P>The constant in the Java programming language, sometimes referred
//		 * to as a type code, that identifies the generic SQL type 
//		 * <code>BIGINT</code>.
//		 */
//			case Types.BIGINT:
//				/**
//				 * ����long���͵��ֶ�ʱ��Ҫ����Ӧ�ó�����long���ֶ��д�Ŵ��ı�ʱ��Ҫ�����Reader
//				 * res.getCharacterStream();
//				 */
////				Object reader = null;
////				// reader = cstmt.getCharacterStream(i);
////				Object temp = cstmt.getObject(i);
////				// value = new Object[] { temp, reader };
////				value = temp;
////				break;
//				handler = typeHandlerFactory.getTypeHandler(long.class);
//				return handler;
//				
//		
//				
//				
//		/**********************************************************************
//	     * Types.REAL float
//	     **********************************************************************/
//		/**
//		 * <P>The constant in the Java programming language, sometimes referred
//		 * to as a type code, that identifies the generic SQL type 
//		 * <code>REAL</code>.
//		 */
//			case Types.REAL :
//				handler = typeHandlerFactory.getTypeHandler(float.class);
//				return handler;
//
//		/**********************************************************************
//	     * Types.DOUBLE Types.FLOAT  double ???? Types.FLOAT ΪʲôҪ���Ϊdouble������float
//	     **********************************************************************/					
//		
//		/**
//		 * <P>The constant in the Java programming language, sometimes referred
//		 * to as a type code, that identifies the generic SQL type 
//		 * <code>FLOAT</code>.
//		 */
//			case Types.FLOAT :
//				handler = typeHandlerFactory.getTypeHandler(float.class);
//				return handler;
//		/**
//		 * <P>The constant in the Java programming language, sometimes referred
//		 * to as a type code, that identifies the generic SQL type 
//		 * <code>DOUBLE</code>.
//		 */
//			case Types.DOUBLE :
//				handler = typeHandlerFactory.getTypeHandler(double.class);
//				return handler;
//		/**********************************************************************
//		 * Types.NUMERIC Types.DECIMAL java.lang.BigDecimal
//		 **********************************************************************/
//		/**
//		 * <P>The constant in the Java programming language, sometimes referred
//		 * to as a type code, that identifies the generic SQL type 
//		 * <code>NUMERIC</code>.
//		 */
//			case Types.NUMERIC :					
//
//		/**
//		 * <P>The constant in the Java programming language, sometimes referred
//		 * to as a type code, that identifies the generic SQL type 
//		 * <code>DECIMAL</code>.
//		 */
//			case Types.DECIMAL :
////				value = cstmt.getBigDecimal(i);
////				BigDecimalTypeHandler big = new BigDecimalTypeHandler();
////				return big.getResult(cs, columnIndex, javaType);
//				handler = typeHandlerFactory.getTypeHandler(BigDecimal.class);
//				return handler;
////				break;
//				
//		/**********************************************************************
//		 * Types.CHAR Types.VARCHAR Types.LONGVARCHAR Types.CLOB java.lang.String
//		 **********************************************************************/
//		/**
//		 * <P>The constant in the Java programming language, sometimes referred
//		 * to as a type code, that identifies the generic SQL type 
//		 * <code>CHAR</code>.
//		 */
//			case Types.CHAR:
//				handler = typeHandlerFactory.getTypeHandler(String.class);
//				return handler;
//
//		/**
//		 * <P>The constant in the Java programming language, sometimes referred
//		 * to as a type code, that identifies the generic SQL type 
//		 * <code>VARCHAR</code>.
//		 */
//			case Types.VARCHAR :
//				handler = typeHandlerFactory.getTypeHandler(String.class);
//				return handler;
//
//		/**
//		 * <P>The constant in the Java programming language, sometimes referred
//		 * to as a type code, that identifies the generic SQL type 
//		 * <code>LONGVARCHAR</code>.
//		 */
//			case Types.LONGVARCHAR  :
//				handler = typeHandlerFactory.getTypeHandler(String.class,"LONGVARCHAR");
//				return handler;
//				 /**
//			     * The constant in the Java programming language, sometimes referred to
//			     * as a type code, that identifies the generic SQL type
//			     * <code>CLOB</code>.
//			     * @since 1.2
//			     */
//			case Types.CLOB:
////					Clob clob = (Clob) cstmt.getObject(i);
////					value = clob;
////					// value = this.clobToString(clob);
////					break;
////				try {
//					// not sure about this fix, so be overly cautious
//					// �ж��Ƿ��������������������Щ���ݿ���Ҫ������������Ŀո񣬱���oracle
//				handler = typeHandlerFactory.getTypeHandler(String.class,"CLOB");
//				return handler;
////					value = cstmt.getString(i);
////
//////					value = SQLUtil.getPool(dbname).getDbAdapter()
//////							.getCharValue(cstmt, i, (String) value);
////
////				} catch (Exception _e) {
////					// _e.printStackTrace();
////					// System.out.println("column :" + i + " " +
////					// meta.getColumnLabel(i));
////					// �����쳣����Щ���ݿ��У�����mssql
////					// server2000�����ֶε�ֵΪ�յ�����µ���res.getObject(i)�ᱨ
////					// ��ȡ���ֽ��쳣��������ݶ�ȡʧ��
////					try {
////						value = cstmt.getObject(i);
////					} catch (Exception e) {
////						value = null;
////					}
////				}
////				break;
//		/**********************************************************************
//		 * Types.DATE Types.TIME Types.TIMESTAMP OracleTypes.TIMESTAMPLTZ 
//		 * OracleTypes.TIMESTAMPNS OracleTypes.TIMESTAMPTZ java.util.Date
//		 **********************************************************************/
//
//		/**
//		 * <P>The constant in the Java programming language, sometimes referred
//		 * to as a type code, that identifies the generic SQL type 
//		 * <code>DATE</code>.
//		 */
//			case Types.DATE:
//				
//				handler = typeHandlerFactory.getTypeHandler(java.util.Date.class);
//				return handler;
////				try {
////					value = cstmt.getTimestamp(i);
////					break;
////				} catch (Exception e) {
////					value = cstmt.getDate(i);
////					break;
////				}
//
//		/**
//		 * <P>The constant in the Java programming language, sometimes referred
//		 * to as a type code, that identifies the generic SQL type 
//		 * <code>TIME</code>.
//		 */
//			case Types.TIME:
//				handler = typeHandlerFactory.getTypeHandler(java.util.Date.class);
//				return handler;
////				value = cstmt.getTime(i);
//
//		/**
//		 * <P>The constant in the Java programming language, sometimes referred
//		 * to as a type code, that identifies the generic SQL type 
//		 * <code>TIMESTAMP</code>.
//		 */
//			case Types.TIMESTAMP:
//				handler = typeHandlerFactory.getTypeHandler(java.util.Date.class);
//				return handler;
//			case OracleTypes.TIMESTAMPLTZ:
//				handler = typeHandlerFactory.getTypeHandler(java.util.Date.class);
//				return handler;
//			case OracleTypes.TIMESTAMPNS:
//				handler = typeHandlerFactory.getTypeHandler(java.util.Date.class);
//				return handler;
//			case OracleTypes.TIMESTAMPTZ:
//				handler = typeHandlerFactory.getTypeHandler(java.util.Date.class);
//				return handler;
//
//		/**
//		 * <P>The constant in the Java programming language, sometimes referred
//		 * to as a type code, that identifies the generic SQL type 
//		 * <code>BINARY</code>.
//		 */
//			case Types.BINARY :
//				handler = typeHandlerFactory.getTypeHandler(byte[].class,"BINARY");
//				return handler;
//
//		/**
//		 * <P>The constant in the Java programming language, sometimes referred
//		 * to as a type code, that identifies the generic SQL type 
//		 * <code>VARBINARY</code>.
//		 */
//			case Types. VARBINARY  :
//				handler = typeHandlerFactory.getTypeHandler(byte[].class,"VARBINARY");
//				return handler;
//		/**
//		 * <P>The constant in the Java programming language, sometimes referred
//		 * to as a type code, that identifies the generic SQL type 
//		 * <code>LONGVARBINARY</code>.
//		 */
//			case Types.LONGVARBINARY  :
//				handler = typeHandlerFactory.getTypeHandler(byte[].class,"LONGVARBINARY");
//				return handler;
//			case Types.BLOB:
////				Blob blob = (Blob) cstmt.getObject(i);
////				value = blob;
////				// value = this.blobToByteArray(blob);
////				break;
//				handler = typeHandlerFactory.getTypeHandler(byte[].class,"BLOB");
//				return handler;
//			 /**
//		     * The constant in the Java programming language, sometimes referred to
//		     * as a type code, that identifies the generic SQL type
//		     * <code>JAVA_OBJECT</code>.
//		     * @since 1.2
//		     */
//			case Types.JAVA_OBJECT  :
//				handler = typeHandlerFactory.getTypeHandler(Object.class, "OBJECT");
//				return handler;
//		/**
//		 * <P>The constant in the Java programming language, sometimes referred
//		 * to as a type code, that identifies the generic SQL type 
//		 * <code>NULL</code>.
//		 */
//			case Types.NULL	 :
//
//		    /**
//		     * The constant in the Java programming language that indicates
//		     * that the SQL type is database-specific and
//		     * gets mapped to a Java object that can be accessed via
//		     * the methods <code>getObject</code> and <code>setObject</code>.
//		     */
//			case Types.OTHER :
//
//		        
//
//		   
//
//		    /**
//		     * The constant in the Java programming language, sometimes referred to
//		     * as a type code, that identifies the generic SQL type
//		     * <code>DISTINCT</code>.
//		     * @since 1.2
//		     */
//			case Types.DISTINCT  :
//			
//		    /**
//		     * The constant in the Java programming language, sometimes referred to
//		     * as a type code, that identifies the generic SQL type
//		     * <code>STRUCT</code>.
//		     * @since 1.2
//		     */
//			case Types.STRUCT :
//
//		    /**
//		     * The constant in the Java programming language, sometimes referred to
//		     * as a type code, that identifies the generic SQL type
//		     * <code>ARRAY</code>.
//		     * @since 1.2
//		     */
//			case Types.ARRAY  :
//
//		    /**
//		     * The constant in the Java programming language, sometimes referred to
//		     * as a type code, that identifies the generic SQL type
//		     * <code>BLOB</code>.
//		     * @since 1.2
//		     */
//			
//
//		   
//
//		    /**
//		     * The constant in the Java programming language, sometimes referred to
//		     * as a type code, that identifies the generic SQL type
//		     * <code>REF</code>.
//		     * @since 1.2
//		     */
//			case Types.REF :
//		        
//		    /**
//		     * The constant in the Java programming language, somtimes referred to
//		     * as a type code, that identifies the generic SQL type <code>DATALINK</code>.
//		     *
//		     * @since 1.4
//		     */
//			case Types.DATALINK :
//				
//		    
//			
//		
//		
//			default:
//			// System.out.println("column :" + i + " " +
//			// meta.getColumnLabel(i));
//			// �����쳣����Щ���ݿ��У�����mssql
//			// server2000�����ֶε�ֵΪ�յ�����µ���res.getObject(i)�ᱨ
//			// ��ȡ���ֽ��쳣��������ݶ�ȡʧ��
//				handler = typeHandlerFactory.getTypeHandler(Object.class, "OBJECT");
//				return handler;
//		}
//	}
	
	public static byte[] getResultBytes(Blob blob)
	throws SQLException {

		byte[] returnValue;
		if (blob != null) {
		//	returnValue = blob.getBytes(1, (int) blob.length());
			returnValue = ValueExchange.getByteArrayFromBlob(blob);
			return returnValue;
		} else {
			returnValue = null;
		}
		return returnValue;
	}
	public static byte[] convertObjectToBytes(Object bytes) throws SQLException{
		if(bytes == null)
			return null;
		if(bytes instanceof String)
		{
			return ((String)bytes).getBytes();
		}
		else if(bytes instanceof java.sql.Blob)
		{
			try {
				return getResultBytes((Blob)bytes);
			} catch (SQLException e) {
				
				throw e;
			}catch (Exception e) {
				
				throw new NestedSQLException(e);
			}
			
			
		}
		else if(bytes instanceof java.sql.Clob)
		{
			try {
				return getResultBytes((Clob)bytes);
			} catch (SQLException e) {				
				throw e;
			}catch (Exception e) {				
				throw new NestedSQLException(e);
			}
		}
		
			
		return (byte[])bytes;
	}
	
	public static byte[] getResultBytes(Clob clob) throws SQLException{
		String value;
//	    Clob clob = rs.getClob(columnIndex);
	    if (clob != null) {
//	      int size = (int) clob.length();
	      return ValueExchange.getByteArrayFromClob(clob);
//	      value = clob.getSubString(1, size);    
//	      return value.getBytes();
	    } else {
	    	return null;
	    }
	}
	public static Object getValueFromCallableStatement(CallableStatement cs,
			int columnIndex, int sqltype, Class javaType,String dbname) throws SQLException{
//		Object value = null;
//		try {
//			TypeHandler handler = getTypeHandler(sqltype);
//			if(handler != null)
//				return handler.getResult(cs, columnIndex, javaType, null);
//		} catch (SQLException ee) {
//			throw ee;
//		} catch (Exception ee) {
//			throw new NestedSQLException(ee);
//		}
//		return value;
		Object value = getValueFromCallableStatement(cs, columnIndex, sqltype,
				dbname);
		
		if(value == null)
			return ValueObjectUtil.getDefaultValue(javaType);
		return convert(value, value.getClass(), javaType);
	}
	
	public static Object getValueFromCallableStatement(CallableStatement cs,
			String parameterName, int sqltype, Class javaType,String dbname) throws SQLException{
//		Object value = null;
//		try {
//			TypeHandler handler = getTypeHandler(sqltype);
//			if(handler != null)
//				return handler.getResult(cs, parameterName, javaType, null);
//		} catch (SQLException ee) {
//			throw ee;
//		} catch (Exception ee) {
//			throw new NestedSQLException(ee);
//		}
//		return value;
		Object value = getValueFromCallableStatement(cs, parameterName, sqltype,
				dbname);
		
		if(value == null)
			return value;
		return convert(value, value.getClass(), javaType);
	}
	
	
//	public static Object getValueFromCallableStatement(CallableStatement cs,
//			int columnIndex, int sqltype, Class javaType,String dbname) throws SQLException{
//		Object value = null;
//		try {
//			TypeHandler handler = getTypeHandler(sqltype);
//			if(handler != null)
//				return handler.getResult(cs, columnIndex, javaType, null);
//		} catch (SQLException ee) {
//			throw ee;
//		} catch (Exception ee) {
//			throw new NestedSQLException(ee);
//		}
//		return value;
//	}
//	
//	public static Object getValueFromCallableStatement(CallableStatement cs,
//			String parameterName, int sqltype, Class javaType,String dbname) throws SQLException{
//		Object value = null;
//		try {
//			TypeHandler handler = getTypeHandler(sqltype);
//			if(handler != null)
//				return handler.getResult(cs, parameterName, javaType, null);
//		} catch (SQLException ee) {
//			throw ee;
//		} catch (Exception ee) {
//			throw new NestedSQLException(ee);
//		}
//		return value;
//	}
	
//	public static Object getValueFromResultSet(ResultSet rs,
//			String parameterName, int sqltype, Class javaType,String dbname) throws SQLException{
//		Object value = null;
//		try {
//			TypeHandler handler = getTypeHandler(sqltype);
//			if(handler != null)
//				return handler.getResult(rs, parameterName, javaType, null);
//		} catch (SQLException ee) {
//			throw ee;
//		} catch (Exception ee) {
//			throw new NestedSQLException(ee);
//		}
//		return value;
//	}
//	
//	public static Object getValueFromResultSet(ResultSet rs,
//			int columnIndex, int sqltype, Class javaType,String dbname) throws SQLException{
//		Object value = null;
//		try {
//			TypeHandler handler = getTypeHandler(sqltype);
//			if(handler != null)
//				return handler.getResult(rs, columnIndex, javaType, null);
//		} catch (SQLException ee) {
//			throw ee;
//		} catch (Exception ee) {
//			throw new NestedSQLException(ee);
//		}
//		return value;
//	}
	
	
	
	
	public static Object getValueFromCallableStatement(CallableStatement cstmt, int i, int type,
			String dbname) throws SQLException {
		Object value = null;
		
		try {
		    DB db = SQLUtil.getPool(dbname).getDbAdapter();
			switch (type) {

			// case Types.LONGVARBINARY:
			case Types.CHAR:
				try {
					// not sure about this fix, so be overly cautious
					// �ж��Ƿ��������������������Щ���ݿ���Ҫ������������Ŀո񣬱���oracle

					value = cstmt.getString(i);
					
					value = db.getCharValue(cstmt, i,
							(String) value);

				} catch (Exception _e) {
					// _e.printStackTrace();
					// System.out.println("column :" + i + " " +
					// meta.getColumnLabel(i));
					// �����쳣����Щ���ݿ��У�����mssql
					// server2000�����ֶε�ֵΪ�յ�����µ���res.getObject(i)�ᱨ
					// ��ȡ���ֽ��쳣��������ݶ�ȡʧ��
					try {
						value = cstmt.getObject(i);
					} catch (Exception e) {
						value = null;
					}
				}
				break;
			case Types.BIGINT:
				/**
				 * ����long���͵��ֶ�ʱ��Ҫ����Ӧ�ó�����long���ֶ��д�Ŵ��ı�ʱ��Ҫ�����Reader
				 * res.getCharacterStream();
				 */
				Object reader = null;
//				reader = cstmt.getCharacterStream(i);
				Object temp = cstmt.getObject(i);
//				value = new Object[] { temp, reader };
				value = temp;
				break;
			case Types.BLOB:
			    Blob blob = cstmt.getBlob(i);
                value = blob;
                //value = this.blobToByteArray(blob);
                break;
			case Types.LONGVARBINARY:
			    
                value = db.getLONGVARBINARY(cstmt, i);
                //value = this.blobToByteArray(blob);
                break;
			case Types.CLOB:
			    Clob clob = cstmt.getClob(i);
                value = clob;
                //value = this.clobToString(clob);
                break;
			case Types.LONGVARCHAR:
				
				value = db.getLONGVARCHAR(cstmt, i);
				//value = this.clobToString(clob);
				break;
			case OracleTypes.TIMESTAMPLTZ:
				value = cstmt.getTimestamp(i);
				break;
			case OracleTypes.TIMESTAMPNS:
				value = cstmt.getTimestamp(i);
				break;
			case OracleTypes.TIMESTAMPTZ:
				value = cstmt.getTimestamp(i);
				break;
			case Types.TIMESTAMP:
				value = cstmt.getTimestamp(i);
				break;
			case Types.TIME:
				value = cstmt.getTime(i);
			
				break;
			case Types.DATE:
				try
				{
					value = cstmt.getTimestamp(i);break;
				}
				catch(Exception e)
				{
					value = cstmt.getDate(i);break;
				}
			default:
				// System.out.println("column :" + i + " " +
				// meta.getColumnLabel(i));
				// �����쳣����Щ���ݿ��У�����mssql
				// server2000�����ֶε�ֵΪ�յ�����µ���res.getObject(i)�ᱨ
				// ��ȡ���ֽ��쳣��������ݶ�ȡʧ��
				try {
					value = cstmt.getObject(i);
				} catch (Exception e) {
					value = null;
				}
				// value = res.getObject(i);
				break;
			}
		} catch (SQLException ee) {
			throw ee;
		} catch (Exception ee) {
			throw new NestedSQLException(ee);
		}
		return value;
	}
	
	
	
	public static Object getValueFromCallableStatement(CallableStatement cstmt, String paramName, int sqltype,
			String dbname) throws SQLException {
		Object value = null;
//		SchemaType schemaType = SQLUtil.getSchemaType(dbname, sqltype);
//		String javaType = schemaType.getJavaType();
		try {
			switch (sqltype) {

			// case Types.LONGVARBINARY:
			case Types.CHAR:
				try {
					// not sure about this fix, so be overly cautious
					// �ж��Ƿ��������������������Щ���ݿ���Ҫ������������Ŀո񣬱���oracle

					value = cstmt.getString(paramName);
					
					value = SQLUtil.getPool(dbname).getDbAdapter().getCharValue(cstmt, paramName,
							(String) value);

				} catch (Exception _e) {
					// _e.printStackTrace();
					// System.out.println("column :" + i + " " +
					// meta.getColumnLabel(i));
					// �����쳣����Щ���ݿ��У�����mssql
					// server2000�����ֶε�ֵΪ�յ�����µ���res.getObject(i)�ᱨ
					// ��ȡ���ֽ��쳣��������ݶ�ȡʧ��
					try {
						value = cstmt.getObject(paramName);
					} catch (Exception e) {
						value = null;
					}
				}
				break;
			case Types.BIGINT:
				/**
				 * ����long���͵��ֶ�ʱ��Ҫ����Ӧ�ó�����long���ֶ��д�Ŵ��ı�ʱ��Ҫ�����Reader
				 * res.getCharacterStream();
				 */
				Object reader = null;
//				reader = cstmt.getCharacterStream(i);
				Object temp = cstmt.getObject(paramName);
				value = temp;
				break;
			case Types.BLOB:
			case Types.LONGVARBINARY:
				Blob blob = cstmt.getBlob(paramName);
				value = blob;
				//value = this.blobToByteArray(blob);
				break;
			case Types.CLOB:
			case Types.LONGVARCHAR:
				Clob clob = (Clob) cstmt.getClob(paramName);
				value = clob;
				//value = this.clobToString(clob);
				break;
			case OracleTypes.TIMESTAMPLTZ:
				value = cstmt.getTimestamp(paramName);
				break;
			case OracleTypes.TIMESTAMPNS:
				value = cstmt.getTimestamp(paramName);
				break;
			case OracleTypes.TIMESTAMPTZ:
				value = cstmt.getTimestamp(paramName);
				break;
			case Types.TIMESTAMP:
				value = cstmt.getTimestamp(paramName);
				break;
			case Types.TIME:
				value = cstmt.getTime(paramName);
			
				break;
			case Types.DATE:
				try
				{
					value = cstmt.getTimestamp(paramName);break;
					
				}
				catch(Exception e)
				{
					value = cstmt.getDate(paramName);break;
				}
			default:
				// System.out.println("column :" + i + " " +
				// meta.getColumnLabel(i));
				// �����쳣����Щ���ݿ��У�����mssql
				// server2000�����ֶε�ֵΪ�յ�����µ���res.getObject(i)�ᱨ
				// ��ȡ���ֽ��쳣��������ݶ�ȡʧ��
				try {
					value = cstmt.getObject(paramName);
				} catch (Exception e) {
					value = null;
				}
				// value = res.getObject(i);
				break;
			}
		} catch (SQLException ee) {
			throw ee;
		} catch (Exception ee) {
			throw new NestedSQLException(ee);
		}
		
		
		return value;
	}
	public static byte[] getByteArrayFromBlob(Blob blob) throws SQLException
	{
		return ValueObjectUtil.getByteArrayFromBlob(blob);


	}
	
	public static String getByteStringFromBlob(Blob blob) throws SQLException
	{
		return ValueObjectUtil. getByteStringFromBlob( blob);


	}
	public static String getStringFromBlob(Blob blob) throws SQLException
	{		
		return ValueObjectUtil.getStringFromBlob(blob);


	}
	public static byte[] getByteArrayFromClob(Clob clob) throws SQLException
	{
		return ValueObjectUtil.getByteArrayFromClob( clob);
		
	}
	
	public static String getStringFromClob(Clob clob) throws SQLException
	{
		return ValueObjectUtil.getStringFromClob(clob);
		
	}
	
	public static String getStringFromReader(Reader in) throws SQLException
	{
		StringWriter w = null;
		if(in == null)
			return null;
		
//		Reader out = null;
		try
		{
			w = new StringWriter();
//			out =	in;
			char[] buf = new char[1024];
			int i =0;
			
			while((i = in.read(buf)) > 0)
			{
				w.write(buf,0,i);
							
			}
			return w.toString();
			
		}
//		catch(SQLException e)
//		{
//			throw e;
//		}
		catch(Exception e)
		{
			throw new NestedSQLException(e);
		}
		finally
		{
			try
			{
				if(in != null)
				{
					in.close();
					in = null;
				}
			}
			catch(Exception e)
			{
				
			}
			
			try
			{
				if(w != null)
				{
					w.close();
					w = null;
				}
			}
			catch(Exception e)
			{
				
			}
		}
		
	}
	
	
	public static String getStringFromStream(InputStream in) throws SQLException
	{
		if(in == null)
			return null;
		OutputStream out = null;
//		InputStream in = null;
		
		try
		{
			out = new ByteArrayOutputStream();
//			in =	blob.getBinaryStream();
			
			
			byte[] buf = new byte[1024];
			int i =0;
			
			while((i = in.read(buf)) > 0)
			{
				
//				System.out.println("i=" + i);
				
				out.write(buf,0,i);
							
			}
		
			return out.toString();
		}
//		catch(SQLException e)
//		{
//			throw e;
//		}
		catch(Exception e)
		{
			throw new NestedSQLException(e);
		}
		finally
		{
			try
			{
				if(out != null)
				{
					out.close();
					out = null;
				}
			}
			catch(Exception e)
			{
				
			}
			
			try
			{
				if(in != null)
				{
					in.close();
					in = null;
				}
			}
			catch(Exception e)
			{
				
			}
		}
		
	}
	
	
	
	public static String getStringFromObject(Object value) throws SQLException

	{
		String ret = null;
		if(value == null)
		{
			return null;
		}
		else
		{
			try
			{
				if(value instanceof String)
					return (String)value;
				
				else if(value instanceof Clob)
				{
					ret = getStringFromClob((Clob)value);
				}
				else if(value instanceof Blob)
				{
					ret = getStringFromBlob((Blob)value);
				}
				else if(value instanceof byte[])
				{
					return new String(((byte[]) value));
				}
				else if(value instanceof InputStream)
				{
					ret = ValueExchange.getStringFromStream((InputStream)value);
				}
				else if(value instanceof Reader)
				{
					ret = ValueExchange.getStringFromReader((Reader)value);
				}
				else
				{
					return value.toString();
				}
			}
			catch(SQLException ioe)
			{
				throw ioe;
			}
			catch(Exception e)
			{
				throw new NestedSQLException(e);
			}
				
		}
		return ret;
	}
	
//	public static Object getValueFromResultSet(ResultSet rs,
//			String parameterName, int sqltype, Class javaType,String dbname) throws SQLException{
//		Object value = null;
//		try {
//			TypeHandler handler = getTypeHandler(sqltype);
//			if(handler != null)
//				return handler.getResult(rs, parameterName, javaType, null);
//		} catch (SQLException ee) {
//			throw ee;
//		} catch (Exception ee) {
//			throw new NestedSQLException(ee);
//		}
////		return value;
//		
//	}
	
	public static Object getValueFromResultSet(ResultSet rs,
			int columnIndex, int sqltype, Class javaType,String dbname) throws SQLException{
//		Object value = null;
//		try {
//			TypeHandler handler = getTypeHandler(sqltype);
//			if(handler != null)
//				return handler.getResult(rs, columnIndex, javaType, null);
//		} catch (SQLException ee) {
//			throw ee;
//		} catch (Exception ee) {
//			throw new NestedSQLException(ee);
//		}
//		return value;
		Object value = getValueFromRS(rs, columnIndex, sqltype,
				 dbname);
		if(value == null)
			return ValueObjectUtil.getDefaultValue(javaType);;
		return convert(value, value.getClass(), javaType);
		
		
	}
	public static Object convert(Object value, Class type, Class javaType) throws SQLException{
		try {
			if(javaType == null || value == null)
				return ValueObjectUtil.getDefaultValue(javaType);
			if(java.sql.Clob.class.isAssignableFrom(type))
			{
				if(javaType == Clob.class)
					return value;
//				value = ValueExchange.getStringFromClob((Clob)value);
			}
			else if(java.sql.Blob.class.isAssignableFrom(type))
			{
				if(javaType == Blob.class)
					return value;
//				value = ValueExchange.getStringFromBlob((Blob)value);
			}
				
			return ValueObjectUtil.typeCast(value, value.getClass(), javaType);
		} catch (NumberFormatException e) {
			throw new NestedSQLException(e);
		} catch (IllegalArgumentException e) {
			throw new NestedSQLException(e);
		} catch (NoSupportTypeCastException e) {
			throw new NestedSQLException(e);
		} catch (Exception e) {
			throw new NestedSQLException(e);
		}
//		return null;
	}
	public static Object getValueFromRS(ResultSet res, int i, int type,
			String dbname) throws SQLException {
		Object value = null;
		DB db = SQLUtil.getPool(dbname).getDbAdapter();
		try {
			switch (type) {

			// case Types.LONGVARBINARY:
			case Types.CHAR:
				try {
					// not sure about this fix, so be overly cautious
					// �ж��Ƿ��������������������Щ���ݿ���Ҫ������������Ŀո񣬱���oracle

					value = res.getString(i);
					
					value = db.getCharValue(res, i,
							(String) value);

				} catch (SQLException _e) {
//					// _e.printStackTrace();
//					// System.out.println("column :" + i + " " +
//					// meta.getColumnLabel(i));
//					// �����쳣����Щ���ݿ��У�����mssql
//					// server2000�����ֶε�ֵΪ�յ�����µ���res.getObject(i)�ᱨ
//					// ��ȡ���ֽ��쳣��������ݶ�ȡʧ��
//					value = res.getObject(i);
					throw _e;
					
				}
				catch (Exception _e) {
//					// _e.printStackTrace();
//					// System.out.println("column :" + i + " " +
//					// meta.getColumnLabel(i));
//					// �����쳣����Щ���ݿ��У�����mssql
//					// server2000�����ֶε�ֵΪ�յ�����µ���res.getObject(i)�ᱨ
//					// ��ȡ���ֽ��쳣��������ݶ�ȡʧ��
//					value = res.getObject(i);
					throw new NestedSQLException(_e);
					
				}
				break;
			case Types.BIGINT:
				/**
				 * ����long���͵��ֶ�ʱ��Ҫ����Ӧ�ó�����long���ֶ��д�Ŵ��ı�ʱ��Ҫ�����Reader
				 * res.getCharacterStream();
				 */
//				Object reader = null;
//				reader = res.getCharacterStream(i);
//				Object temp = res.getObject(i);
//				value = new Object[] { temp, reader };
				long temp = res.getLong(i);
				value = new Long(temp);				
				break;
			case Types.BLOB:
			    Blob blob = res.getBlob(i);
                value = blob;
                //value = this.blobToByteArray(blob);
                break;
			case Types.LONGVARBINARY:
				
				value = db.getLONGVARBINARY(res, i);
				//value = this.blobToByteArray(blob);
				break;
			case Types.CLOB:
			    Clob clob = res.getClob(i);
                value = clob;
                //value = this.clobToString(clob);
                break;
			case Types.LONGVARCHAR:
				
				value = db.getLONGVARCHAR(res, i);
				//value = this.clobToString(clob);
				break;
			case OracleTypes.TIMESTAMPLTZ:
				value = res.getTimestamp(i);
				break;
			case OracleTypes.TIMESTAMPNS:
				value = res.getTimestamp(i);
				break;
			case OracleTypes.TIMESTAMPTZ:
				value = res.getTimestamp(i);
				break;
			case Types.TIMESTAMP:
				value = res.getTimestamp(i);
				break;
			case Types.TIME:
				value = res.getTime(i);
			
				break;
			case Types.DATE:
				try
				{
					value = res.getTimestamp(i);break;
				}
				catch(Exception e)
				{
					value = res.getDate(i);break;
				}
			default:
				// System.out.println("column :" + i + " " +
				// meta.getColumnLabel(i));
				// �����쳣����Щ���ݿ��У�����mssql
				// server2000�����ֶε�ֵΪ�յ�����µ���res.getObject(i)�ᱨ
				// ��ȡ���ֽ��쳣��������ݶ�ȡʧ��
				
				value = res.getObject(i);
				
				// value = res.getObject(i);
				break;
			}
		} 
		catch(SQLException ioe)
		{
			throw ioe;
		}
		catch(Exception e)
		{
			throw new NestedSQLException(e);
		}
		return value;
	}
	/**
	 * ����ת��Ϊ����
	 * @param column
	 * @return
	 */
	public static String numberToCN(String column){
		 char[] cnNumbers={'��','Ҽ','��','��','��','��','½','��','��','��'};
	
		 if(!"".equals(column)){
			 StringBuffer stringbuffer = new StringBuffer();
			 for(int i=0;i<column.length();i++){
				 char temp = column.charAt(i);
				 int number =0;
				 if(temp == '.'){
					 stringbuffer.append(".");
				 }else {	 
					 number = Integer.parseInt(String.valueOf(temp));
				 	 stringbuffer.append(cnNumbers[number]);
				 }
			 }
			return stringbuffer.toString();
		 }else{
			 return null;
		 }
		
	}
	public static String toStringNumber(double value,String length,String precision,int numbertocn,String pattern)
	{
		//���ж�ֵ�Ƿ�Ϊ��

		String ret = "";
		// ����ɶ������Ĵ���
		if(numbertocn==1){
			return numberToCN(String.valueOf(value));
		}else if(pattern != null && !"".equals(pattern)){
			DecimalFormat form= new DecimalFormat(pattern);
			ret = form.format(value);
		}
		
		//���������ȡ����ȴ���
		StringBuffer fmt=new StringBuffer();

		int i;
		DecimalFormat form;
		
		int length_ = -1;
		int precision_ = -1;
		

		if (value >= 0) 
			fmt.append(' '); //to compensate for minus sign.
		
		if(precision != null && !"".equals(precision)){
			precision_ = Integer.parseInt(precision);
		}
		if(length != null && !"".equals(length)){
			length_ = Integer.parseInt(length);
		}
		
		
		if (precision_<0)  // Default: two decimals
		{
			for (i=0;i<length_;i++) 
				fmt.append('0');
			fmt.append(".00"); // for the .00
		}else{
			for (i=0;i<=length_;i++)
				fmt.append('0'); // all zeroes.
			
			if(length_<0)
				length_ = String.valueOf(value).length()-1;
			
			int pos = length_-precision_+1-(value<0?1:0);
			
			if (pos>=0 && pos <fmt.length())
			{
				fmt.setCharAt(length_-precision_+1-(value<0?1:0), '.'); // one 'comma'
			}else if(pos >= fmt.length()){
				for(int j=0;j< String.valueOf(value).length()+1;j++)
					fmt.append('0'); 
				fmt.setCharAt(pos+1-(value<0?1:0), '.');
			}if(pos<=0){
				for(int j=0;j< precision_;j++)
					fmt.append('0'); 
				fmt.setCharAt(0, '.');
			}
		}
		
		form= new DecimalFormat(fmt.toString());
		ret=form.format(value);
		return ret;
	}
	
	public static String toStringDate(Date date, String dataformat) {
		//����ת����������
		if(date == null)
			return null;
		if(dataformat != null && !"".equals(dataformat)){
			return ValueObjectUtil.getDateFormat(dataformat).format(date);
		}else {
			return ValueObjectUtil.getDateFormat(null).format(date);
		}
	}
	
	public static String toStringInteger(int value, String length,
			int numbertocn, String dataformat) {
		//���ж�ֵ�Ƿ�Ϊ��

		String ret = "";
		// ����ɶ������Ĵ���
		if(numbertocn == 1){
			return numberToCN(String.valueOf(value));
		}else if(dataformat != null && !"".equals(dataformat)){
			DecimalFormat form= new DecimalFormat(dataformat);
			ret = form.format(value);
		}
		//���������ȴ���
		StringBuffer fmt=new StringBuffer();
		int i;
		DecimalFormat form;

		if (value>=0) fmt.append(' '); //to compensate for minus sign.
		int len = 0;
		if(length != null && !"".equals(length)){
			len = Integer.parseInt(length);
			for (i=0;i<len;i++)
				fmt.append('0'); // all zeroes.
		}
		
		form= new DecimalFormat(fmt.toString());
		return form.format(ret);
	}
	

	public static String toStringBigNumber(BigDecimal bigDecimal,
			String length, int numbertocn,
			String dataformat) {
		//���ж�ֵ�Ƿ�Ϊ��

		String ret = "";
		// ����ɶ������Ĵ���
		if(numbertocn == 1){
			return numberToCN(String.valueOf(bigDecimal));
		}else if(dataformat != null && !"".equals(dataformat)){
			DecimalFormat form= new DecimalFormat(dataformat);
			ret = form.format(bigDecimal);
		}
		
		
		//���������ȴ���
		StringBuffer retbuffer = new StringBuffer(ret);
		int length_ = 0;
		if(length != null && !"".equals(length)){
			length_ = Integer.parseInt(length);
			rightPad(retbuffer,length_);
		}
		ret = retbuffer.toString();
		return ret;
	}
	
	public static final String rightPad(StringBuffer ret, int limit)
	{
		int len = ret.length();
		int l;

		if (len > limit)
		{
			ret.setLength(limit);
		} else
		{
			for (l = len; l < limit; l++)
				ret.append(' ');
		}
		return ret.toString();
	}
	
	public static String toStringString(String origin,int swtich_case, String length) {
		if(origin == null || "".equals(origin))
			return null;
		if(swtich_case == 1){
			origin =  origin.toUpperCase();
		}
			
		String retval=null;
		StringBuffer ret = new StringBuffer(origin);
		
		int length_ = 0;
		if( length != null  && !"".equals(length)){
			length_ = Integer.parseInt(length);
			if(length_>16384){
				length_ = 16384;
			}
			rightPad(ret, length_);	
		}
		retval = ret.toString();
		
		return retval;
	}

	public static String toStringTimeStamp(Timestamp timestamp,
			String dataformat) {
		//����ת����������
		if(timestamp == null)
			return null;
		if(dataformat != null && !"".equals(dataformat)){
			return ValueObjectUtil.getDateFormat(dataformat).format(timestamp);
		}else {
			return ValueObjectUtil.getDateFormat(null).format(timestamp);
		}
	}
	
	public static String toStringTime(Time time){
		if(time != null)
			return time.toString();
		return null;
	}
	public static String getByteStringFromBytes(byte[] bytes) {
		if(bytes == null)
			return null;
		BASE64Encoder en = new BASE64Encoder();
		return en.encode(bytes);
	}
	public static String getStringFromBytes(byte[] bytes) {
		if(bytes == null)
			return null;
		return new String(bytes);
	}

}
