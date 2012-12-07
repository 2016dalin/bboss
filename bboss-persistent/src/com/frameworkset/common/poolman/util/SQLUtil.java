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
package com.frameworkset.common.poolman.util;



import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.transaction.SystemException;

import org.apache.log4j.Logger;

import com.frameworkset.common.poolman.DBUtil;
import com.frameworkset.common.poolman.NestedSQLException;
import com.frameworkset.common.poolman.NewSQLInfo;
import com.frameworkset.common.poolman.PreparedDBUtil;
import com.frameworkset.common.poolman.Record;
import com.frameworkset.common.poolman.ResultMap;
import com.frameworkset.common.poolman.StatementInfo;
import com.frameworkset.common.poolman.handle.RowHandler;
import com.frameworkset.common.poolman.interceptor.InterceptorInf;
import com.frameworkset.common.poolman.management.PoolManBootstrap;
import com.frameworkset.common.poolman.sql.ColumnMetaData;
import com.frameworkset.common.poolman.sql.ForeignKeyMetaData;
import com.frameworkset.common.poolman.sql.PoolManDataSource;
import com.frameworkset.common.poolman.sql.PoolManResultSetMetaData;
import com.frameworkset.common.poolman.sql.PrimaryKeyMetaData;
import com.frameworkset.common.poolman.sql.TableMetaData;
import com.frameworkset.common.poolman.sql.UpdateSQL;
import com.frameworkset.orm.adapter.DB;
import com.frameworkset.orm.adapter.DB.PagineSql;
import com.frameworkset.orm.adapter.DBFactory;
import com.frameworkset.orm.engine.model.SchemaType;
import com.frameworkset.orm.transaction.JDBCTransaction;
import com.frameworkset.orm.transaction.TXConnection;
import com.frameworkset.orm.transaction.TransactionException;
import com.frameworkset.orm.transaction.TransactionManager;

/**
 * 
 * <p>Title: SQLUtil.java</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 * @Date Dec 7, 2008 9:04:43 PM
 * @author biaoping.yin
 * @version 1.0
 */
public class SQLUtil implements Serializable{
	private static final Logger log = Logger.getLogger(SQLUtil.class);

//	private static SQLUtil myself;
	/** ÿ�β�ѯ���ݿ��ȡ��ʵ�ʼ�¼���� */
	protected int size = 0;
	/**
	 * �������ݲ����ύģʽ true:�Զ��ύ false���ֶ��ύ ȱʡΪtrue���Զ��ύ
	 */
	protected boolean autocommit = true;
	
	/**
	 * ��������Զ��ύģʽ��true:�Զ��ύ false���ֶ��ύ ȱʡΪfalse���ֶ��ύ
	 */
	protected boolean batchautocommit = false;

	public static final int MAX_ATTEMPTS = 3;
	

	public String propsfilename;

	private Properties props;

//	/** ����ÿ�β�ѯ���ݿ����ֶδ�д��ĸֵ */
//	protected String[] f_temps;

//	/** ����ÿ�β�ѯ���ݿ����ֶ� */
//	protected String[] fields;

	protected PoolManResultSetMetaData meta;

	
	
//	protected static MetaDataCacheControl metas;
//	protected static CacheModel metas ;
//	static
//	{
//		MetaDataCacheControl metasControl = new MetaDataCacheControl();
//		
//		metas = new CacheModel();
//		try {
//			metas.setCacheController(metasControl);
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	/* TESTING */
	public static void main(String[] args) {

		System.out.println("\n");
		if (args.length < 2) {
			System.out
					.println("SYNTAX:\njava com.frameworkset.common.poolman.SQLUtil "
							+ "\"[name of database as specified in poolman.props]\""
							+ "\"[SQL statement to be executed]\"\n");
			System.exit(0);
		}

//		try {
//			SQLResult res = SQLUtil.getInstance().execute(args[0], args[1]);
//			while (res.hasNext()) {
//				Hashtable row = res.next();
//				for (Enumeration enum1 = row.keys(); enum1.hasMoreElements();) {
//					String key = enum1.nextElement().toString();
//					System.out.print(key + ": ");
//					System.out.print(row.get(key).toString() + "\t");
//				}
//				System.out.print("\n");
//			}
//			if (res.size() < 1)
//				System.out
//						.println("SQLUtil: No results were returned for that SQL statement.");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}

		System.out.println("\n");
		System.exit(1);

	}

//	/**
//	 * The default method used to retrieve an instance of SQLUtil, based on the
//	 * properties file SQLManager.DEFAULT_PROPSFILE, which must be in your
//	 * CLASSPATH.
//	 */
//	public static SQLUtil getInstance() {
//		if (myself == null) {
//			synchronized (SQLUtil.class) {
//				myself = new SQLUtil();
//			}
//		}
//		return myself;
//	}
//
//	/**
//	 * An alternative method to retrieve an instance of SQLUtil, using a
//	 * specified props file.
//	 */
//	public static SQLUtil getInstance(String propsfilename) {
//		if (myself == null) {
//			synchronized (SQLUtil.class) {
//				myself = new SQLUtil(propsfilename);
//			}
//		}
//		return myself;
//	}
//
//	/**
//	 * An alternative method to retrieve an instance of SQLUtil, using a
//	 * Properties object.
//	 */
//	public static SQLUtil getInstance(Properties p) {
//		if (myself == null) {
//			synchronized (SQLUtil.class) {
//				myself = new SQLUtil(p);
//			}
//		}
//		return myself;
//	}
//
//	protected SQLUtil() {
//		this(PoolManConstants.XML_CONFIG_FILE);
//	}
//
//	private SQLUtil(String propsfilename) {
//		this.propsfilename = propsfilename;
//	}
//
//	private SQLUtil(Properties p) {
//		this.props = p;
//	}

	/**
	 * 
	 * @param sql
	 * @return
	 * @throws SQLException
	 * @deprecated ������ʹ��
	 */
	public SQLResult execute(String sql) throws SQLException {
		return execute(null, sql);
	}

	/**
	 * 
	 * @param dbname
	 * @param sql
	 * @return
	 * @throws SQLException
	 * @deprecated ������ʹ��
	 */
	public SQLResult execute(String dbname, String sql) throws SQLException {
		return makeResult(executeSql(dbname, sql,null));
	}
	
	/**
	 * 
	 * 
	 * @param dbname
	 * @param sql
	 * @return
	 * @throws SQLException
	 * @deprecated ������ʹ��
	 */
	public SQLResult execute(String dbname, String sql,Connection con) throws SQLException {
		return makeResult(executeSql(dbname, sql,con));
	}

	/** Simply executes executeSql(sql, true). */
	public Hashtable[] executeSql(String sql) throws SQLException {
		return executeSql(null, sql,null);
	}
	
	/** Simply executes executeSql(sql, true). */
	public Hashtable[] executeSql(String sql,Connection con) throws SQLException {
		return executeSql(null, sql,con);
	}

	/**
	 * Wraps an array of Hashtables into a more convenient data structure,
	 * called a SQLResult.
	 * @deprecated ������ʹ��
	 */
	protected SQLResult makeResult(Hashtable[] h) throws SQLException {
		return new SQLResult(h);
	}

	/** Reference the SQLManager, which contains all available JDBCPools. */
	public static SQLManager getSQLManager() {
		SQLManager datab = null;
		try {
			/*
			 * if (props != null) datab = SQLManager.getInstance(this.props);
			 * else
			 */
			datab = SQLManager.getInstance();
		} catch (Exception pe) {
			throw new RuntimeException(
					"Couldn't get a reference to the SQLManager: "
							+ pe.toString());
		}
		return datab;
	}
	
	/**
     * ��ȡ���ݿ�������
     * @return DB
     */
    public static DB getDBAdapter()
    {
        return getDBAdapter(null);
    }

    /**
     * ��ȡ�������ݿ����Ƶ����ݿ�������
     * @return DB
     */
    public static DB getDBAdapter(String dbName)
    {
        return getSQLManager().getDBAdapter(dbName);
    }

	public static ColumnMetaData getColumnMetaData(String dbName,
			String tableName, String columnName) {

		return getPool(dbName).getColumnMetaData(tableName, columnName);
	}
	
	public static ColumnMetaData getColumnMetaData(String dbName,
			String tableName, String columnName,Connection con) {

		return getPool(dbName).getColumnMetaData(con,tableName, columnName);
	}

	public static Set getTableMetaDatas(String dbName) {
		return getPool(dbName).getTableMetaDatas();
	}
	


	public static TableMetaData getTableMetaData(String dbName, String tableName) {
		return getPool(dbName).getTableMetaData(tableName);
	}

	public static Set getColumnMetaDatas(String dbName, String tableName) {

		return getPool(dbName).getColumnMetaDatas(tableName);
	}

	public static ForeignKeyMetaData getForeignKeyMetaData(String dbName,
			String tableName, String columnName) {
		return getPool(dbName).getForeignKeyMetaData(tableName, columnName);

	}

	public static Set getForeignKeyMetaDatas(String dbName, String tableName) {
		return getPool(dbName).getForeignKeyMetaDatas(tableName);
	}

	public static PrimaryKeyMetaData getPrimaryKeyMetaData(String dbName,
			String tableName, String columnName) {
		return getPool(dbName).getPrimaryKeyMetaData(tableName, columnName);
	}

	public static Set getPrimaryKeyMetaDatas(String dbName, String tableName) {
		return getPool(dbName).getPrimaryKeyMetaDatas(tableName);
	}

	public static ColumnMetaData getColumnMetaData(String tableName,
			String columnName) {

		return getPool(null).getColumnMetaData(tableName, columnName);
	}
	
	public static ColumnMetaData getColumnMetaData(String tableName,
			String columnName,Connection con) {

		return getPool(null).getColumnMetaData(con,tableName, columnName);
	}

	public static Set getTableMetaDatas() {
		return getPool(null).getTableMetaDatas();
	}

	public static TableMetaData getTableMetaData(String tableName) {
		return getPool(null).getTableMetaData(tableName);
	}
	
	
	

	public static TableMetaData getTableMetaDataFromDataBase(String tableName) {
		return getPool(null).getTableMetaDataFromDatabase(tableName);
	}
	
	public static TableMetaData getTableMetaDataFromDataBase(String dbname,String tableName) {
		return getPool(dbname).getTableMetaDataFromDatabase(tableName);
	}

	public static Set getColumnMetaDatas(String tableName) {
		return getPool(null).getColumnMetaDatas(tableName);
	}

	public static ForeignKeyMetaData getForeignKeyMetaData(String tableName,
			String columnName) {
		return getPool(null).getForeignKeyMetaData(tableName, columnName);

	}

	public static Set getForeignKeyMetaDatas(String tableName) {
		return getPool(null).getForeignKeyMetaDatas(tableName);
	}

	public static PrimaryKeyMetaData getPrimaryKeyMetaData(String tableName,
			String columnName) {
		return getPool(null).getPrimaryKeyMetaData(tableName, columnName);
	}

	public static Set getPrimaryKeyMetaDatas(String tableName) {
		return getPool(null).getPrimaryKeyMetaDatas(tableName);
	}

	/**
	 * Get a Vector containing all the names of the database pools currently in
	 * use.
	 */
	public Enumeration getAllPoolnames() {
		SQLManager datab = getSQLManager();
		if (datab == null)
			return null;
		return datab.getAllPoolnames();
	}
    /**
     * Get a Vector containing all the names of the database pools currently in
     * use.
     */
    public static List<String> getAllPoolNames() {
        SQLManager datab = getSQLManager();
        if (datab == null)
            return null;
        return datab.getAllPoolNames();
    }
	/** @return JDBCPool The pool requested by name. */
	public static JDBCPool getPool(String dbname) {
		SQLManager datab = getSQLManager();
		return (JDBCPool) datab.getPool(dbname);
	}
	
	  public static boolean exist(String dbname)
	    {
	      SQLManager datab = getSQLManager();
              return  datab.exist(dbname);
	    }

	/**
	 * ����ȱʡ�����ݿ����ӳ�
	 * 
	 * @param dbname
	 * @return
	 */
	public static JDBCPool getPool() {
		SQLManager datab = getSQLManager();
		return (JDBCPool) datab.getPool(null);
	}

	public static void updateTableMetaData(String tableName) {
		updateTableMetaData(tableName, null);
	}

	public static void updateTableMetaData(String tableName, String dbName) {
		getPool(dbName).updateTableMetaData(tableName);
	}

	/**
	 * Begins the actual database operation by preparing resources. It calls
	 * doJDBC() to perform the actual operation.
	 */
	public Record[] executeSql(String dbname, String sql,Connection con)
			throws SQLException {

		Record[] hashResults = null;



		try {
		
			hashResults = doJDBC(dbname, sql,con);
		}
		catch (SQLException e) {
			
			throw e;
		} 
		catch (Exception e) {
			
			throw new NestedSQLException(e);
		} finally {
		}

		return hashResults;

	}

	// /** This method is called by the cache thread in SQLCache. */
	// public Hashtable[] doJDBC(String dbname, String sql, boolean goNative)
	// throws SQLException {
	//
	// SQLManager datab = getSQLManager();
	// if (datab == null)
	// throw new SQLException("Unable to initialize PoolMan's SQLManager");
	//
	// Connection con = null;
	// // if ((dbname == null) || (dbname.equals("")))
	// // con = datab.requestConnection();
	// // else
	// con = datab.requestConnection(dbname);
	// System.out.println("sqlutil doJDBC get poolman connection:" + con);
	//
	// return doJDBC(dbname, sql, con, goNative);
	// }

	// /** Executes a statement and returns results in the form of a Hashtable
	// array. */
	// protected Hashtable[] doJDBC(String dbname, String sql, Connection con)
	// throws SQLException {
	// return doJDBC(dbname, sql, con, false);
	// }
	/**
	 * Executes a statement and returns results in the form of a Hashtable
	 * array.
	 */
	protected Record[] doJDBC(String dbname, String sql,Connection con) throws SQLException {
		return doJDBC(dbname, sql, false,con);
	}

	
	
	protected static JDBCTransaction getTransaction()
	{
		
		return TransactionManager.getTransaction();
	}
	
	/**
	 * �ж����ݿ����ӳ��Ƿ���ʾsql���
	 * @param dbname
	 * @return
	 */
	public static boolean showsql(String dbname)
	{
		try
		{
			return DBUtil.getSQLManager().getPool(dbname).getJDBCPoolMetadata().isShowsql();
		}
		catch(Exception e)
		{
			return false;
		}
	}
	protected ResultMap innerExecuteJDBC(StatementInfo stmtInfo,
			Class objectType,RowHandler rowhandler,int result_type) throws SQLException
	{
		
		
		

		try {
			ResultMap resultMap = new ResultMap();	
			Record[] results = null;
			ResultSet res = null;
			Statement s = null;
			if(stmtInfo != null)
				stmtInfo.init();		

			s = stmtInfo.createStatement();

			if(showsql(stmtInfo.getDbname()))
			{
				log.debug("Execute JDBC statement:"+stmtInfo.getSql());
			}
			boolean hasResult = s.execute(stmtInfo.getSql());
//			results = new DBHashtable[10];
			if(hasResult)
			{
				res = s.getResultSet();
				stmtInfo.addResultSet(res);
				stmtInfo.cacheResultSetMetaData(res,false);
				this.meta = stmtInfo.getMeta();
//				results = stmtInfo.buildResult(res,10,false);
//				this.fields = stmtInfo.getFields();
//				this.f_temps = fields;
				
				resultMap = stmtInfo.buildResultMap(res, objectType, rowhandler, 10, false, result_type);
				if(resultMap != null)
					this.size = resultMap.getSize();
			}
			else //�ǲ�ѯ���������
			{
				
				results = stmtInfo.buildCommonResult(s);
				resultMap.setCommonresult(results);
				if(results != null)
				{
					resultMap.setSize(1);
					this.size = resultMap.getSize();
				}
			}
			stmtInfo.commit();
			
			return resultMap;

		} catch (SQLException sqle) {
			// ������ֶ��ύ���ݿ�ģʽ����������ʱ�ع����е����ݿ����
			try{
				
				log.error(stmtInfo.getSql(), sqle);
			}
			catch(Exception ei)
			{
				
			}
			if(stmtInfo != null)
				stmtInfo.errorHandle(sqle);
			throw sqle;
		} 

		catch (Exception e) {
			try{
				
				log.error(stmtInfo.getSql(), e);
			}
			catch(Exception ei)
			{
				
			}
			
			if(stmtInfo != null)
				stmtInfo.errorHandle(e);
			throw new NestedSQLException(e.getMessage(),e);
		}
		finally {
			if(stmtInfo != null)
				stmtInfo.dofinally();
			stmtInfo = null;

		}
		
		
	
	//	return results;
	}
	
	protected static DB oracleDB; 
	private static Object lock = new Object();
	protected static void initOracleDB() throws InstantiationException
	{
		if(oracleDB != null)
			return;
		synchronized(lock)
		{
			if(oracleDB != null)
				return;
			oracleDB = DBFactory.create("oracle");
		}
	}
	public static void updateBLOB(Blob blob,File file) throws SQLException
	{

		try
		{
			initOracleDB();
			oracleDB.updateBLOB(blob, file);
		}
		catch (Exception e)
		{
			throw new NestedSQLException(e);
		}
	}
	
	public static void updateBLOB(Blob blob,InputStream instream) throws SQLException
	{
		
		try
		{
			initOracleDB();
			oracleDB.updateBLOB(blob, instream);
		}
		catch (Exception e)
		{
			throw new NestedSQLException(e);
		}
	}
	
	public static void updateCLOB(Clob clob,Object content) throws SQLException
	{

		try
		{
			initOracleDB();
			oracleDB.updateCLOB(clob, content);
		}
		catch (Exception e)
		{
			throw new NestedSQLException(e);
		}
	}
	
	

	/**
	 * Executes a statement and returns results in the form of a Hashtable
	 * array.
	 */
	protected Record[] doJDBC(String dbname_, String sql_, boolean goNative_,Connection con_)
			throws SQLException {

		StatementInfo stmtInfo = null;
		try
		{
			stmtInfo = new StatementInfo(dbname_,new NewSQLInfo( sql_), goNative_,con_,!this.isAutoCommit());
			ResultMap resultMap = innerExecuteJDBC(stmtInfo,
					null,null,ResultMap.type_maparray);
			return (Record[])resultMap.getCommonresult();
		}
		catch(SQLException e)
		{
			throw e;
		}
		finally
		{
			if(stmtInfo != null)
			{
				stmtInfo.dofinally();
				stmtInfo = null;
			}
		}
//		Record[] results = null;
//		ResultSet res = null;
//		Statement s = null;
//
//		try {
//			if(stmtInfo != null)
//				stmtInfo.init();
//
//			
//
//			
//
//			s = stmtInfo.createStatement();
//
//	
//			boolean hasResult = s.execute(stmtInfo.getSql());
////			results = new DBHashtable[10];
//			if(hasResult)
//			{
//				res = s.getResultSet();
//				stmtInfo.addResultSet(res);
//				stmtInfo.cacheResultSetMetaData(res);
//				this.meta = stmtInfo.getMeta();
//				results = stmtInfo.buildResult(res,10,false);
////				this.fields = stmtInfo.getFields();
////				this.f_temps = fields;
//			}
//			else
//			{
//				results = stmtInfo.buildCommonResult(s);
//			}
//			stmtInfo.commit();
//
//		} catch (SQLException sqle) {
//			// ������ֶ��ύ���ݿ�ģʽ����������ʱ�ع����е����ݿ����
//
//			if(stmtInfo != null)
//				stmtInfo.errorHandle(sqle);
//			throw sqle;
//		} 
//
//		catch (Exception e) {
//
//			if(stmtInfo != null)
//				stmtInfo.errorHandle(e);
//			throw new NestedSQLException(e.getMessage(),e);
//		}
//		finally {
//			if(stmtInfo != null)
//				stmtInfo.dofinally();
//
//		}
//		
//		return results;

	}

	/**
	 * �������ݿ������ύģʽ true���Զ��ύ���ݿ�����ģʽ false:�ֶ��ύ���ݿ�����ģʽ ȱʡΪ:true,�Զ��ύ���ݿ�����
	 * 
	 * @param autocommit
	 */
	public void setAutoCommit(boolean autocommit) {
		this.autocommit = autocommit;
		this.batchautocommit = autocommit;
	}

	protected boolean isAutoCommit() {
		return autocommit;
	}
	
	protected boolean isBatchAutoCommit()
	{
		return this.batchautocommit;
	}
	
	
	/**
	 * ��ȡ���ݿ�����
	 * 
	 * @return Connection ���ݿ�����
	 * @throws SQLException
	 */
	public static Connection getConection() throws SQLException {
		return getConection(null);
	}

	/**
	 * ��ȡ���ݿ�����,���ϵͳ����ϵͳ���������񻷾�ʱ�������������е�����
	 * 
	 * @return Connection ���ݿ�����
	 * @throws SQLException
	 */
	public static Connection getConection(String dbName) throws SQLException {
		JDBCTransaction tx = getTransaction();
		if (tx == null) {
			SQLManager datab = getSQLManager();
			return datab.requestConnection(dbName);
		} else {
			try {
				return new TXConnection(tx.getConnection(dbName));
			} catch (TransactionException e) {
				try {
					tx.setRollbackOnly();
				} catch (IllegalStateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SystemException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				throw new SQLException(e.getMessage());
			}
		}
	}
	
	/**
	 * ��ȡ���ݿ�����,���ϵͳ����ϵͳ���������񻷾�ʱ�������������е�����
	 * 
	 * @return Connection ���ݿ�����
	 * @throws SQLException
	 */
	public static Connection getConectionFromDatasource(DataSource datasource) throws SQLException {
		JDBCTransaction tx = getTransaction();
		if (tx == null) {
//			SQLManager datab = getSQLManager();
//			return datab.requestConnection(datasource.getPoolName());
			return datasource.getConnection();
		} else {
			try {
				/*
				 * ����Դ�����bboss ����Դ��ֱ�ӴӸ�������Դ���ƻ�ȡ��������
				 * ���������Դ 
				 */
				if(datasource instanceof PoolManDataSource)
				{
					return new TXConnection(tx.getConnection(((PoolManDataSource)datasource).getPoolName()));
				}
				else
				{
					Connection con = tx.getConnectionFromDS(datasource);
					if(con instanceof TXConnection)
						return con;
					return new TXConnection(con);
				}
			} catch (TransactionException e) {
				try {
					tx.setRollbackOnly();
				} catch (IllegalStateException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SystemException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				throw new SQLException(e.getMessage());
			}
		}
	}
	/**
	 * �жϵ�ǰ�����Ƿ��������
	 * @return
	 */
	public static boolean joinTX()
	{
		
		return SQLUtil.getTransaction() != null;
	}

	public ResultSetMetaData getMeta() {
		return meta;
	}

	public static String getDBDate(Date date) {
		return SQLManager.getInstance().getDBAdapter().getDateString(date);
	}

	public static String getDBDate(Date date, String dbName) {
		return SQLManager.getInstance().getDBAdapter(dbName)
				.getDateString(date);
	}

	public static String getDBDate(String date, String dbName) {
		return SQLManager.getInstance().getDBAdapter(dbName)
				.getDateString(date);
	}

	public static String getDBDate(String date) {
		return SQLManager.getInstance().getDBAdapter().getDateString(date);
	}
	
	
	
	public static String getDBDateWithFormat(Date date,String format) {
		return SQLManager.getInstance().getDBAdapter().getDateString(date,format);
	}

	public static String getDBDateWithFormat(Date date,String format, String dbName) {
		return SQLManager.getInstance().getDBAdapter(dbName)
				.getDateString(date,format);
	}

	public static String getDBDateWithFormat(String date,String format, String dbName) {
		return SQLManager.getInstance().getDBAdapter(dbName)
				.getDateString(date,format);
	}

	public static String getDBDateWithFormat(String date,String format) {
		return SQLManager.getInstance().getDBAdapter().getDateString(date,format);
	}

	/**
	 * ��ȡȱʡ���ݿ�ķ�ҳsql���
	 * 
	 * @param sql
	 * @return
	 */
	public static PagineSql getDBPagineSql(String sql, long offset, int maxsize,boolean prepared) {
		return SQLManager.getInstance().getDBAdapter().getDBPagineSql(sql,
				offset, maxsize, prepared);
	}
	
	public static boolean isRobotQuery(String dbName)
	{
		return SQLManager.getInstance().getPool(dbName).isRobotQuery();
	}
	

	/**
	 * ��ȡָ�����ݿ�ķ�ҳ����sql��䣬ͨ��oracle�ĸ�Ч��ѯ���
	 * 
	 * @param dbName
	 * @param sql
	 * @return
	 */
	public static PagineSql getDBPagineSqlForOracle(String dbName, String sql,
			long offset, int maxsize, String rownum,boolean prepared) {
		return SQLManager.getInstance().getDBAdapter(dbName)
				.getOracleDBPagineSql(sql, offset, maxsize, rownum, prepared);

	}

	/**
	 * ��clob�ֶε�ֵ���õ��ַ�����
	 * 
	 * @param clob
	 * @return
	 * @throws SQLException
	 */
	protected String clobToString(Clob clob) throws SQLException {
		int leg = (int) clob.length();
	
		if (leg > 0)
			return clob.getSubString(1, leg);
		else
			return "";
	}

	/**
	 * ��blob�ֶ��е�ֵת��Ϊ�ַ���
	 * 
	 * @param blob
	 * @return
	 * @throws SQLException
	 */
	protected String blobToString(Blob blob) throws SQLException {
		int length = (int) blob.length();
		if (length > 0) {
			byte ret[] = new byte[(int) blob.length()];
			ret = blob.getBytes(1, length);
			return new String(ret);
		} else {
			return "";
		}
	}

	/**
	 * ��blob�ֶ��е�ֵת��Ϊ�ַ���
	 * 
	 * @param blob
	 * @return
	 * @throws SQLException
	 */
	protected byte[] blobToByteArray(Blob blob) throws SQLException {
		int length = (int) blob.length();
		if (length > 0) {
			byte ret[] = new byte[(int) blob.length()];
			ret = blob.getBytes(1, length);
			return ret;
		}
		
		return new byte[0];
	}

	/**
	 * �������ݿ��ѯ�ֶ�
	 * @return
	 */
	public String[] getFields() {
		return this.meta.get_columnLabel_upper();
	}
	
	/**
	 * ���ִ�д���������ݿ�����
	 */
	private static ThreadLocal threadLocal = new ThreadLocal();
	
	
	/**
	 * ��ʼһ�����ݿ�����
	 * @throws SQLException 
	 */
	public void beginTransaction() throws SQLException
	{
//		Transaction transaction = null;
//		threadLocal.set(SQLManager.getInstance().requestConnection(
//				batchDBName));
	}
	
	/**
	 * û�д������еĻ�ʹ���ֳɵ�
	 * ���Ǵ���
	 * @param created
	 * @throws SQLException
	 */
	public void beginTransaction(int created) throws SQLException
	{
//		Transaction transaction = null;
//		threadLocal.set(SQLManager.getInstance().requestConnection(
//				batchDBName));
	}
	
	

	/**
	 * �ύ����
	 *
	 */
	public void commitTransaction() throws SQLException

	{
//		Connection con = (Connection)threadLocal.get();
////		con.commit();
	}
	
	/**
	 * �ع�����
	 */
	public void rollbackTransaction() throws SQLException

	{
		
	}
	
	
	public static void setValue(PreparedStatement stmt,
			int index,String table,
			String column,String value,
			String dbName,Connection con) throws  SQLException
	{
		
		ColumnMetaData columnmeta = DBUtil.getColumnMetaData(dbName,table,column,con);
		if(columnmeta.getSchemaType() == SchemaType.BIGINT ||
				columnmeta.getSchemaType() == SchemaType.INTEGER||
				columnmeta.getSchemaType() == SchemaType.NUMERIC||
				columnmeta.getSchemaType() == SchemaType.SMALLINT||
				columnmeta.getSchemaType() == SchemaType.DECIMAL||
				
				columnmeta.getSchemaType() == SchemaType.TINYINT
				
			)
		{
			try
			{
				if(value == null)
				{
					stmt.setNull(index, java.sql.Types.NUMERIC);
				}
				else
				{
					stmt.setInt(index,Integer.parseInt(value));
				}
			}
			catch(SQLException e)
			{
				throw e;
			}
			catch(Exception e)
			{
				throw new SQLException(e.getMessage());
			}
		}
		else 
		{
			if(value == null)
			{
				stmt.setNull(index, java.sql.Types.VARCHAR);
			}
			else
			{
				stmt.setString(index,value);
			}
		}
		
	}
	
	public static void execute(Connection con,UpdateSQL sql) throws SQLException
	{
		
//		PreparedDBUtil predbUtil = new PreparedDBUtil();
//		PreparedDBUtil predbUtil1 = new PreparedDBUtil();
//		boolean autocommit = con.getAutoCommit();
//		try
//		{
//			
//			if(autocommit)
//				con.setAutoCommit(false);
//			
//			predbUtil1.preparedSelect(sql.getDbName(),"select TABLE_ID_VALUE from tableinfo where LOWER(table_name)=? and table_id_value <? for update",con);
//			predbUtil1.setString(1, (String)sql.getDatas().get(1));
//			predbUtil1.setInt(2, ((Integer)sql.getDatas().get(2)).intValue());
//			predbUtil1.executePrepared();
//			if(predbUtil1.size() > 0)
//			{
//				predbUtil.preparedUpdate(sql.getDbName(),sql.getUpdateSql(),con);
//				List datas = sql.getDatas();
//				for(int i = 0; datas != null && i < datas.size(); i ++)
//				{
//					Object data = datas.get(i);
//					
//					setValue(predbUtil,i + 1,data);
//				}
//				predbUtil.executePrepared();
//			}
//			if(autocommit)
//			{
//				con.commit();
//			}
//		}
//		catch(SQLException e)
//		{
//			if(autocommit)
//			{	
//				con.rollback();
//			}
//			throw e;
//		}
//		finally
//		{
//			predbUtil.resetPrepare();
//			predbUtil1.resetPrepare();
//			con.setAutoCommit(autocommit);
//		}
//		predbUtil.
	}
	
	public static void setValue(PreparedDBUtil predbUtil,int index,Object value) throws SQLException
	{
		if(value == null)
		{
			predbUtil.setNull(index,Types.NULL);
		}
		else if(value instanceof Integer)
		{
			predbUtil.setInt(index,((Integer)value).intValue());
		}
		else if(value instanceof String)
		{
			predbUtil.setString(index,value.toString());
		}
		else if(value instanceof Timestamp)
		{
			predbUtil.setTimestamp(index,(Timestamp)value);
		}
		else if(value instanceof java.sql.Date)
		{
			predbUtil.setDate(index,(java.sql.Date)value);
		}
		else
		{
			predbUtil.setObject(index,value);
		}
	}
	/**
	 * ��ȡ��ǰ���ӳ�������ʹ�õ�����
	 * �ӿ�ֻ���ڲ�����Դ���ã��ⲿ����Դ����-1
	 * @return
	 */
	public static int getNumActive(String dbName)
	{
		JDBCPool pool = SQLManager.getInstance().getPool(dbName);
		if(pool != null)
			return pool.getNumActive();
		else
			return -1;
	}
	
	/**
	 * ��ȡ��ǰ���ӳ��п��е�������
	 * �ӿ�ֻ���ڲ�����Դ���ã��ⲿ����Դ����-1
	 * @return
	 */
	public static int getNumIdle(String dbName)
	{
		JDBCPool pool = SQLManager.getInstance().getPool(dbName);
		if(pool != null)
			return pool.getNumIdle();
		else
			return -1;
		
	}
	
	/**
     * ��ȡ��ǰ���ӳ��п��е�������
     * �ӿ�ֻ���ڲ�����Դ���ã��ⲿ����Դ����-1
     * @return
     */
    public static long getStartTime(String dbName)
    {
    	JDBCPool pool = SQLManager.getInstance().getPool(dbName);
		if(pool != null)
			 return pool.getStartTime();
		else
			return -1;
       
    }
    
    
    /**
     * ��ȡ��ǰ���ӳ��п��е�������
     * �ӿ�ֻ���ڲ�����Դ���ã��ⲿ����Դ����-1
     * @return
     */
    public static String getStatus(String dbName)
    {
    	JDBCPool pool = SQLManager.getInstance().getPool(dbName);
		if(pool != null)
			return pool.getStatus();
		else
			return "unloaded";
        
    }
    
    
    /**
     * ��ȡ��ǰ���ӳ��п��е�������
     * �ӿ�ֻ���ڲ�����Դ���ã��ⲿ����Դ����-1
     * @return
     */
    public static long getStopTime(String dbName)
    {
    	JDBCPool pool = SQLManager.getInstance().getPool(dbName);
		if(pool != null)
			 return pool.getStopTime();
		else
			return -1;
       
    }
    
    
	
	
	/**
	 * ��ȡ��ǰ���ӳ�������ʹ�õ�����
	 * �ӿ�ֻ���ڲ�����Դ���ã��ⲿ����Դ����-1
	 * @return
	 */
	public static int getNumActive()
	{
		
			return getNumActive(null);
		
	}
	
	
	/**
	 * ��ȡ��ǰ���ӳ�������ʹ�õ�����
	 * �ӿ�ֻ���ڲ�����Դ���ã��ⲿ����Դ����-1
	 * @return
	 */
	public static int getMaxNumActive()
	{
		return getMaxNumActive(null);
		
	}
	
	

	/**
	 * ��ȡ��ǰ���ӳ�������
	 * �ӿ�ֻ���ڲ�����Դ���ã��ⲿ����Դ����-1
	 * @return
	 */
	public static List getTraceObjects()
	{
		return getTraceObjects(null);
//		return SQLManager.getInstance().getPool(null).getTraceObjects();
	}
	
	
	/**
	 * ��ȡ��ǰ���ӳ�������
	 * �ӿ�ֻ���ڲ�����Դ���ã��ⲿ����Դ����-1
	 * @return
	 */
	public static List getTraceObjects(String dbname)
	{
		JDBCPool pool = SQLManager.getInstance().getPool(dbname);
		if(pool != null)
			return pool.getTraceObjects();
		else
			return null;
//		return SQLManager.getInstance().getPool(dbname).getTraceObjects();
	}
	
	/**
	 * ��ȡ��ǰ���ӳ�������ʹ�õ�����
	 * �ӿ�ֻ���ڲ�����Դ���ã��ⲿ����Դ����-1
	 * @return
	 */
	public static int getMaxNumActive(String dbName)
	{
		JDBCPool pool = SQLManager.getInstance().getPool(dbName);
		if(pool != null)
			return pool.getMaxNumActive();
		else
			return -1;
//		return SQLManager.getInstance().getPool(dbName).getMaxNumActive();
	}
	
	/**
	 * ��ȡ��ǰ���ӳ��п��е�������
	 * �ӿ�ֻ���ڲ�����Դ���ã��ⲿ����Դ����-1
	 * @return
	 */
	public static int getNumIdle()
	{
		return getNumIdle(null);
	}
	
//	public static JDBCPoolMetaData getJDBCPoolMetaData(String dbname)
//	{
//		return SQLManager.getInstance().getPool(dbname).getJDBCPoolMetadata();
//	}
	
	public static JDBCPoolMetaData getJDBCPoolMetaData()
	{
		return getJDBCPoolMetaData(null);
	}
	
	/**
	 * 
	 * 
	 * <p>Title: DBHashtable.java</p>
	 *
	 * <p>Description: �����жϹ�ϣ���Ƿ��Ǵ�dbutil��ֱ�ӻ�ȡ��hash��ı�ʶ��
	 * ������java.util.Hashtableһ��
	 * </p>
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
	public static class DBHashtable extends Hashtable implements java.util.Map
	{

		public DBHashtable(int i) {
			super(i);
		}
		
		public DBHashtable()
		{
			super();
		}
		
		public DBHashtable(int initialCapacity, float loadFactor)
		{
			super(initialCapacity,loadFactor);
		}
		
		public DBHashtable(Map t)
		{
			super(t);
		}		
	}
	public static void debugMemory()
	{
		System.out.println("FreeMemory:" + Runtime.getRuntime().freeMemory() / 1024/1024 + "M");
		System.out.println("MaxMemory:" + Runtime.getRuntime().maxMemory() / 1024/1024 + "M");
		
		System.out.println("TotalMemory:" + Runtime.getRuntime().totalMemory()/ 1024/1024 + "M");
	}
	public static void debugStatus()
	{
		debugStatus(SQLUtil.getPool().getDBName());
	}
	public static void debugStatus(String DBName)
	{
		System.out.println(new StringBuffer("[").append(DBName).append("] idle connenctions:").append(SQLUtil.getNumIdle(DBName)));
		System.out.println(new StringBuffer("[").append(DBName).append("] active connenctions:").append(SQLUtil.getNumActive(DBName)));
		
	}
	
	public static SchemaType getSchemaType(String dbname,int sqltype)
	{
		return getPool(dbname).getDbAdapter().getSchemaTypeFromSqlType(sqltype);
	}
	
	public static String getJavaType(String dbname,int sqltype)
	{
		return getSchemaType(dbname,sqltype).getJavaType();
	}
	
	public static DataSource getDataSource()
	{
	    return getDataSource(null);
	}
	
	public static DataSource getDataSource(String dbname)
        {
	    return SQLUtil.getSQLManager().getPool(dbname).getDataSource();
        }
	
	
        
        public static DataSource getDataSourceByJNDI(String jndiname) throws NamingException
        {
            DataSource datasource = JDBCPool.find(jndiname,null);
            if(datasource == null)
            {
                SQLUtil.getSQLManager().getPool(null);
                return JDBCPool.find(jndiname,null);
            }
            else
                return datasource;
            
        }
        
    	public static JDBCPool getJDBCPoolByJNDIName(String jndiname)
        {
            JDBCPool pool = SQLUtil.getSQLManager().getPoolByJNDIName(jndiname,true);
            return pool;
        }
        
        public static JDBCPool getJDBCPoolByJNDIName(String jndiname,boolean needcheckStart)
        {
            JDBCPool pool = SQLUtil.getSQLManager().getPoolByJNDIName(jndiname,needcheckStart);
            return pool;
        }
        
        public static String getDBNameByJNDIName(String jndiname) throws NamingException
        {
        	try
        	{
        		return getJDBCPoolByJNDIName(jndiname).getDBName();
        	}
        	catch(Exception e)
        	{
        		log.debug(e.getMessage());
        		return null;
        	}
        }
        
        public static void stopPool(String dbname) throws Exception
        {
            SQLUtil.getSQLManager().stopPool(dbname);
//            if(pool != null)
//                pool.stopPool();
        }
        public static void startPool(String dbname) throws Exception
        {
        	
            SQLUtil.getSQLManager().startPool(dbname);
        }
        
        public static String statusCheck(String dbname)
        {
            return SQLUtil.getSQLManager().statusCheck(dbname);
           
        }
        
        public static void startPool(String poolname,String driver,String jdbcurl,String username,String password,String readOnly,String validationQuery)
    	{
        	SQLManager.startPool(poolname, driver, jdbcurl, username, password, readOnly, validationQuery);
    	}
        
        
        public static void startPool(String poolname,String driver,String jdbcurl,String username,String password,
        		String readOnly,
        		String txIsolationLevel,
        		String validationQuery,
        		String jndiName,   
        		int initialConnections,
        		int minimumSize,
        		int maximumSize,
        		boolean usepool,
        		boolean  external,
        		String externaljndiName        ,boolean showsql		
        		)
    	{
        	SQLManager.startPool( poolname, driver, jdbcurl, username, password,
            		 readOnly,
            		 txIsolationLevel,
            		 validationQuery,
            		 jndiName,   
            		 initialConnections,
            		 minimumSize,
            		 maximumSize,
            		 usepool,
            		  external,
            		 externaljndiName ,showsql       		
            		);
    	}
        
        
    	
    	
    	
    	public static JDBCPoolMetaData getJDBCPoolMetaData(String dbname)
    	{
    		return SQLUtil.getSQLManager().getJDBCPoolMetaData(dbname);
    	}
    	
    	public static InterceptorInf getInterceptorInf(String dbname)
    	{
    		return SQLUtil.getSQLManager().getPool(dbname).getInterceptor();
    	}
    	public static void startPoolFromConf(String configfile)
    	{
    		startPoolFromConf( configfile, null,null);
    	}
    	public static void startPoolFromConf(String configfile,String dbnamespace)
    	{
    		startPoolFromConf( configfile, dbnamespace,null);
    	}
    	
    	public static void startPoolFromConf(String configfile,String dbnamespace,String[] dbnames)
    	{
    		PoolManBootstrap.startDBSFromConf(configfile,  dbnamespace,dbnames);
    	}
    	
    	public static void startPoolFromConf(String configfile,String[] dbnames) {
    		
    		startPoolFromConf( configfile, null,dbnames);
    	}
	
	
    	public static List<TableMetaData> getTableMetaDatasFromDataBase() {
    		return getPool(null).getTablesFromDatabase();
    	}
    	
    	public static List<TableMetaData> getTableMetaDatasFromDataBaseByPattern(String tableNamepattern) {
    		return getTableMetaDatasFromDataBaseByPattern(null,tableNamepattern);
    	}
    	
    	public static List<TableMetaData> getTableMetaDatasFromDataBase(String dbanme) {
    		return getPool(dbanme).getTablesFromDatabase();
    	}
    	
    	public static List<TableMetaData> getTableMetaDatasFromDataBaseByPattern(String dbanme,String tableNamepattern) {
    		return getPool(dbanme).getTablesFromDatabase(tableNamepattern);
    	}
    	
    	public static List<TableMetaData> getTableMetaDatasFromDataBase(String[] tableTypes) {
    		return getPool(null).getTablesFromDatabase(tableTypes);
    	}
    	
    	public static List<TableMetaData> getTableMetaDatasFromDataBaseByPattern(String tableNamepattern,String[] tableTypes) {
    		return getTableMetaDatasFromDataBaseByPattern(null,tableNamepattern,tableTypes);
    	}
    	
    	public static List<TableMetaData> getTableMetaDatasFromDataBase(String dbanme,String[] tableTypes) {
    		return getPool(dbanme).getTablesFromDatabase(tableTypes);
    	}
    	
    	public static List<TableMetaData> getTableMetaDatasFromDataBaseByPattern(String dbanme,String tableNamepattern,String[] tableTypes) {
    		return getPool(dbanme).getTablesFromDatabase(tableNamepattern,tableTypes);
    	}
    	
    	public static List<TableMetaData> getTableMetaDatasFromDataBase(boolean loadColumns) {
    		return getPool(null).getTablesFromDatabase(loadColumns);
    	}
    	
    	public static List<TableMetaData> getTableMetaDatasFromDataBaseByPattern(String tableNamepattern,boolean loadColumns) {
    		return getTableMetaDatasFromDataBaseByPattern(null,tableNamepattern,loadColumns);
    	}
    	
    	public static List<TableMetaData> getTableMetaDatasFromDataBase(String dbanme,boolean loadColumns) {
    		return getPool(dbanme).getTablesFromDatabase(loadColumns);
    	}
    	
    	public static List<TableMetaData> getTableMetaDatasFromDataBaseByPattern(String dbanme,String tableNamepattern,boolean loadColumns) {
    		return getPool(dbanme).getTablesFromDatabase(tableNamepattern,loadColumns);
    	}
    	
    	public static List<TableMetaData> getTableMetaDatasFromDataBase(String[] tableTypes,boolean loadColumns) {
    		return getPool(null).getTablesFromDatabase(tableTypes,loadColumns);
    	}
    	
    	public static List<TableMetaData> getTableMetaDatasFromDataBaseByPattern(String tableNamepattern,String[] tableTypes,boolean loadColumns) {
    		return getTableMetaDatasFromDataBaseByPattern(null,tableNamepattern,tableTypes,loadColumns);
    	}
    	
    	public static List<TableMetaData> getTableMetaDatasFromDataBase(String dbanme,String[] tableTypes,boolean loadColumns) {
    		return getPool(dbanme).getTablesFromDatabase(tableTypes,loadColumns);
    	}
    	
    	public static List<TableMetaData> getTableMetaDatasFromDataBaseByPattern(String dbanme,String tableNamepattern,String[] tableTypes,boolean loadColumns) {
    		return getPool(dbanme).getTablesFromDatabase(tableNamepattern,tableTypes,loadColumns);
    	}

}
