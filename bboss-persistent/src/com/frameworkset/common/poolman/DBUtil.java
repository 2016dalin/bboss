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

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;



import org.apache.log4j.Logger;

import com.frameworkset.common.poolman.handle.NullRowHandler;
import com.frameworkset.common.poolman.handle.RowHandler;
import com.frameworkset.common.poolman.handle.ValueExchange;
import com.frameworkset.common.poolman.handle.XMLMark;
import com.frameworkset.common.poolman.sql.PrimaryKey;
import com.frameworkset.common.poolman.sql.PrimaryKeyCacheManager;
import com.frameworkset.common.poolman.sql.UpdateSQL;
import com.frameworkset.common.poolman.util.SQLManager;
import com.frameworkset.common.poolman.util.SQLUtil;
import com.frameworkset.common.poolman.util.StatementParser;
import com.frameworkset.orm.transaction.JDBCTransaction;

/**
 * @author biaoping.yin 2005-3-24 version 1.0
 * 
 * ����SQLUtil��ʵ�ַ�ҳ��ѯ�Ĺ���,ִ�����ݿ����������
 */
public class DBUtil extends SQLUtil implements Serializable {
	/**
	 * ��������֮ǰ������״̬�� preparedCon batchCon
	 * 
	 */
	protected boolean oldcommited = true;   
	
//	static 
//	{
//		DBUtil dbutil = new DBUtil();
//		dbutil = null;
//	}
//	
	// /**
	// * ���ִ�д���������ݿ�����
	// */
	// private static ThreadLocal threadLocal = new ThreadLocal();

	private static Logger log = Logger.getLogger(DBUtil.class);

	protected String oraclerownum;

	/** ȱʡ�����ݿ����ӳ����� */
	// protected final static String DEFAULT_DBNAME = "oa";
//	/** ִ����������룬ɾ�������²�����ʶ���� */
//	protected boolean batchStart = true;

//	/** ִ����������룬ɾ������������� */
//	protected Statement batchStmt;
//
//	/** ִ����������룬ɾ�������²������ݿ����Ӿ�� */
//	protected Connection batchCon;
	
//	/**��ʶ�����������Ƿ�ʹ���ⲿ����,ȱʡΪfalse*/
//	protected boolean outbatchcon = false;

//	protected String batchDBName = SQLManager.getInstance().getDefaultDBName();
	protected String batchDBName = null;

	/**
	 * �������ݿ��ѯ�Ƿ��Ƿֿ�����ݿ��ѯ
	 */
	protected boolean isRobustQuery = false;

	/** ÿ�����ݵ����size */
	protected int fetchsize = 0;

	/** ����ÿ�ηֿ��ѯ����ʼ��ַ */
	protected int fetchoffset = 0;

	/** ��¼��ǰ�к� */
	protected int fetcholdrowid = 0;

	/**
	 * ����ֿ����ݲ�ѯ�����ݿ����ӳص�����
	 */
//	protected String fetchdbName = SQLManager.getInstance().getDefaultDBName();
	protected String fetchdbName = null;

	/**
	 * ����ֿ����ݿ��ѯ���
	 */
	protected String fetchsql;

//	/** ���ִ�������������Ľ���� */
//	protected List batchResult;
//
//	/** ���ִ����������������ı�ĸ�����伯 */
//	protected Set batchUpdateSqls;

	/** ����ÿ�β�ѯ�Ľ���� */
	protected Record[] allResults;
	
	/**
	 * ������sqls����б�����Ƕ�׻�ȡ���Ӻ�����й¶������
	 * �޸�addBatch�����еĴ����߼������������ݿ����ӣ�ֻ�ǽ�sql�����ӵ�
	 * batchSQLS�����У����е����ݿ����ͳһ�ŵ�executeBatch������ִ��
	 * ͬʱ��չexecuteBatch������
	 * executeBatch()
	 * executeBatch(String dbname)
	 * executeBatch(String dbname,java.sql.Connection con)
	 * added by biaoping.yin on 20080715
	 */
	protected List batchSQLS;
	
	

	public DBUtil() {
		super();
	}
	
	/**
	 * �Ѿ�����
	 * @param con
	 * @deprecated 
	 */
	public void setBatchConnection(Connection con)
	{
//		this.batchCon = con;
//		if(con != null)
//			this.outbatchcon = true;
	}

	/** ��ҳ��ѯʱ�������¼������ */
	protected long totalSize = 0;

	

	/** ����ÿ�β�ѯ���ݿ��ȡ��ʵ�ʼ�¼���� */
	public int size() {
		// �����robust��ѯ�������ܵļ�¼������Ȼ��ֿ鷵������
		return isRobustQuery ? (int)totalSize : size;
	}
	
	/** ����ÿ�β�ѯ���ݿ��ȡ��ʵ�ʼ�¼���� */
	public long longsize() {
		// �����robust��ѯ�������ܵļ�¼������Ȼ��ֿ鷵������
		return isRobustQuery ? totalSize : size;
	}

	/**
	 * @deprecated
	 * please use method getLongTotalSize()
	 * ��ȡ��¼������ 
	 */
	
	public int getTotalSize() {
		return (int)this.totalSize;
	}
	/** ��ȡ��¼������ */
	public long getLongTotalSize() {
		return this.totalSize;
	}
	
	

	/**
	 * �ж��Ƿ�ʱ������һ�����ݿ�Ļ�ȡ����
	 * 
	 * @param rowid
	 *            ��ǰ�к�
	 * @throws SQLException
	 */
	private void assertLoaded(int rowid) throws SQLException {
		// ����ֿ��ȡ���ݣ��ж��Ƿ�ʱ������һ�����ݿ�Ļ�ȡ������
		// ��������ȡ���������κβ���
		if (this.isRobustQuery) {
			URLEncoder d;
			// d.encode("");
			// ����к�δ�����仯ʱֱ�ӷ���
			if (rowid == fetcholdrowid)
				return;

			// �������ݿ�߽�
			int bound = this.fetchoffset + fetchsize;
			int newOffset = fetchoffset;
			// ��������֮ǰû�л�ȡʱ,
			// ����Ѿ���ȡ�������ֻ��˵�fetchoffset֮ǰ��ĳЩ��¼ʱ
			// �����Ҫ��ȡfetchoffset + fetchsize֮��ļ�¼ʱ
			// �����Ҫ��ȡfetchoffset + fetchsize�ļ�¼�Ѿ�ȡ��ʱ
			// ����Ҫִ�з���executeSelect(fetchdbName,fetchsql,newOffset,fetchsize)�����ݿ��л�ȡ����

			if (this.allResults == null) {
				newOffset = rowid - rowid % fetchsize;
				// System.out.println("this.allResults == null
				// newOffset:"+newOffset);
			} else if (rowid >= bound) {
				newOffset = rowid - rowid % fetchsize;
				// System.out.println("rowid >= (bound) newOffset:"+newOffset);
			} else if (rowid < fetchoffset) {
				newOffset = rowid - rowid % fetchsize;
				// System.out.println("rowid < fetchoffset
				// newOffset:"+newOffset);
			}
			// else if(rowid == bound && rowid < this.getTotalSize())
			// {
			// newOffset = bound;
			// System.out.println("rowid == bound && rowid < this.getTotalSize()
			// newOffset:"+newOffset);
			// }

			// �����ǰ��������Ѿ���ȡ��ϲ��һ������ݿ飬���������ݿ��л�ȡ��һ��������
			if (newOffset != fetchoffset) {
				this.executeSelect(fetchdbName, fetchsql, newOffset,
						this.fetchsize);
				// �����´λ�ȡ���ݵ����
				fetchoffset = newOffset;
			}
			// ����кŷ����仯����¼�仯����к�
			fetcholdrowid = rowid;

		}
	}

	/**
	 * ������÷ֿ��ȡ����ģʽ����Ҫ���¼����кţ�������Ҫ��������õ��кŷ���
	 * 
	 * @param rowid
	 * @return int �¼����к�
	 */
	private int getTrueRowid(int rowid) {
		if (!isRobustQuery)
			return rowid;
		else
			return rowid - fetchoffset;
	}

	/**
	 * 
	 * @param rowid
	 *            ��0��ʼ
	 * @param column
	 *            ��0��ʼ
	 * @return float
	 */
	public float getFloat(int rowid, int column) throws SQLException {

//		Object value = this.getObject(rowid, column);
//		if (value != null) {
//			return Float.parseFloat(value.toString());
//		} else
//			return 0.0f;
		inrange(rowid, column);
		return allResults[getTrueRowid(rowid)].getFloat(column);
	}

	/**
	 * 
	 * @param rowid
	 *            ��0��ʼ
	 * @param column
	 *            ��0��ʼ
	 * @return String
	 */
	public String getValue(int rowid, int column) {
//		if (rowid >= size() || rowid < 0)
//			return "out of row range";
//		if (column >= this.meta.getColumnCounts() || column < 0)
//			return "out of column range";
//		try {
//			Object value = allResults[getTrueRowid(rowid)].getObject(column);
//			if(value == null)
//				return "";
//			else
//				return value.toString();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return "";
//		}
		try
		{
			inrange(rowid, column);
			return allResults[getTrueRowid(rowid)].getString(column);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 
	 * @param rowid
	 *            ��0��ʼ
	 * @param column
	 *            ��0��ʼ
	 * @return String
	 */
	public String getString(int rowid, int column, String defalueValue)
			throws SQLException {

		inrange(rowid, column);
		String value = allResults[getTrueRowid(rowid)].getString(column);

		if(value != null)
			return value;
		return defalueValue;
	}

	/**
	 * 
	 * @param rowid
	 *            ��0��ʼ
	 * @param column
	 *            ��0��ʼ
	 * @return String
	 * @throws SQLException
	 */
	public String getValue(int rowid, int column, String defaultValue)
			throws SQLException {
		
		inrange(rowid, column);
		String value = allResults[getTrueRowid(rowid)].getString(column);
		return value != null ?value:"";
		
	}
	
	
	
	/**
	 * 
	 * @param rowid
	 *            ��0��ʼ
	 * @param column
	 *            ��0��ʼ
	 * @return String
	 */
	public String getString(int rowid, int column) throws SQLException {
		inrange(rowid, column);
		return allResults[getTrueRowid(rowid)].getString(column);
	}

	public void getFile(int rowid, String column, File file)
			throws SQLException, IOException {
		inrange(rowid, column);
		allResults[getTrueRowid(rowid)].getFile(column,file);
	}
	
	public void getFile(int rowid, int column, File file)
	throws SQLException, IOException {
		inrange(rowid, column);
		allResults[getTrueRowid(rowid)].getFile(column,file);
	}


	/**
	 * 
	 * @param rowid
	 *            ��0��ʼ
	 * @param field
	 * @return String
	 */
	public String getValue(int rowid, String field) throws SQLException {
//		Object value = getObject(rowid, field);
//		if (value != null)
//			return value.toString();
//		else
//			return "";
		this.inrange(rowid, field);
		return this.allResults[this.getTrueRowid(rowid)].getString(field);

	}

	/**
	 * 
	 * @param rowid
	 *            ��0��ʼ
	 * @param field
	 * @return String
	 */
	public String getValue(int rowid, String field, String defaultValue)
			throws SQLException {
//		Object value = getObject(rowid, field);
//		if (value != null)
//			return value.toString();
//		else
//			return defaultValue;
		String value = this.getValue(rowid, field);
		if(value != null)
			return field;
		else
			return defaultValue;

	}

	/**
	 * 
	 * @param rowid
	 *            ��0��ʼ
	 * @param field
	 * @return int
	 */
	public int getInt(int rowid, String field) throws SQLException {
//		Object value = this.getObject(rowid, field);
//		// System.out.println("value.getClass():"+value.getClass());
//		if (value != null)
//			if (!(value instanceof Object[])) {
//				return Integer.parseInt(value.toString());
//			} else {
//
//				return Integer.parseInt(((Object[]) value)[0].toString());
//			}
//		else
//			return 0;
		inrange(rowid, field);
		return allResults[getTrueRowid(rowid)].getInt(field);

	}

	/**
	 * 
	 * @param rowid
	 *            ��0��ʼ
	 * @param column
	 *            start from zero
	 * @return int
	 */
	public int getInt(int rowid, int column) throws SQLException {
//		Object value = this.getObject(rowid, column);
//		// System.out.println("value.getClass():"+value.getClass());
//		if (value != null) {
//			if (!(value instanceof Object[])) {
//				return Integer.parseInt(value.toString());
//			} else {
//
//				return Integer.parseInt(((Object[]) value)[0].toString());
//			}
//
//		} else
//			return 0;
		inrange(rowid, column);
		return allResults[getTrueRowid(rowid)].getInt(column);

	}

	/**
	 * 
	 * @param rowid
	 *            ��0��ʼ
	 * @param field
	 * @return float
	 */
	public float getFloat(int rowid, String field) throws SQLException {
//		Object value = this.getObject(rowid, field);
//		if (value != null)
//			if (!(value instanceof Object[])) {
//				return Float.parseFloat(value.toString());
//			} else {
//
//				return Float.parseFloat(((Object[]) value)[0].toString());
//			}
//		else
//			return 0.0f;
		inrange(rowid, field);
		return allResults[getTrueRowid(rowid)].getFloat(field);
	}

	/**
	 * 
	 * @param rowid
	 *            ��0��ʼ
	 * @param field
	 * @return double
	 */
	public double getDouble(int rowid, String field) throws SQLException {
//		Object value = this.getObject(rowid, field);
//		if (value != null)
//			if (!(value instanceof Object[])) {
//				return Double.parseDouble(value.toString());
//			} else {
//
//				return Double.parseDouble(((Object[]) value)[0].toString());
//			}
//		else
//			return 0;
		inrange(rowid, field);
		return allResults[getTrueRowid(rowid)].getDouble(field);
	}

	/**
	 * 
	 * @param rowid
	 *            ���㿪ʼ
	 * @param column
	 *            ���㿪ʼ
	 * @return double
	 */

	public double getDouble(int rowid, int column) throws SQLException {
//		Object value = this.getObject(rowid, column);
//		if (value != null) {
//			if (!(value instanceof Object[])) {
//				return Double.parseDouble(value.toString());
//			} else {
//
//				return Double.parseDouble(((Object[]) value)[0].toString());
//			}
//		} else
//			return 0;
		inrange(rowid, column);
		return allResults[getTrueRowid(rowid)].getDouble(column);

	}

	/**
	 * 
	 * @param rowid
	 *            ��0��ʼ
	 * @param field
	 * @return long
	 */
	public long getLong(int rowid, String field) throws SQLException {
//		Object temp = this.getObject(rowid, field);
//		if (temp == null)
//			return 0l;
//		if (temp instanceof Object[]) {
//			Object[] value = (Object[]) temp;
//
//			if (value != null)
//				return Long.parseLong(value[0].toString());
//			else
//				return 0l;
//		} else {
//
//			return Long.parseLong(temp.toString());
//
//		}
		inrange(rowid, field);
		return allResults[getTrueRowid(rowid)].getLong(field);
	}

	/**
	 * 
	 * @param rowid
	 *            ���㿪ʼ
	 * @param column
	 *            ���㿪ʼ
	 * @return long
	 * @throws SQLException
	 */
	public long getLong(int rowid, int column) throws SQLException {
//		Object temp = this.getObject(rowid, column);
//		if (temp == null)
//			return 0l;
//		if (temp instanceof Object[]) {
//			Object[] value = (Object[]) temp;
//
//			if (value != null)
//				return Long.parseLong(value[0].toString());
//			else
//				return 0l;
//		} else {
//
//			return Long.parseLong(temp.toString());
//
//		}
		inrange(rowid, column);
		return allResults[getTrueRowid(rowid)].getLong(column);
	}

	/**
	 * 
	 * @param rowid
	 *            ��0��ʼ
	 * @param field
	 * @return byte[]
	 */
	public byte[] getByteArray(int rowid, String field) throws SQLException {
//		Object value = getObject(rowid, field);
//		if (value != null && value instanceof byte[]) {
//			return (byte[]) value;
//		}
//
//		else
//			throw new SQLException("field [" + field + "] classcast error:"
//					+ value.getClass());
		inrange(rowid,field);
		return allResults[getTrueRowid(rowid)].getBytes(field);
	}

	/**
	 * 
	 * @param rowid
	 *            ���㿪ʼ
	 * @param column
	 *            ���㿪ʼ
	 * @return byte[]
	 * @throws SQLException
	 */
	public byte[] getByteArray(int rowid, int column) throws SQLException {
		inrange(rowid, column);
		return allResults[getTrueRowid(rowid)].getBytes(column);
//		Object value = this.getObject(rowid, column);
//		if (value != null && value instanceof byte[]) {
//			return (byte[]) value;
//		}
//
//		else
//			throw new SQLException("field index [" + column
//					+ "] classcast error:" + value.getClass());
	}

	/**
	 * @param rowid
	 *            ��0��ʼ
	 * @param field
	 * @return String
	 */
	public String getString(int rowid, String field) throws SQLException {
//		Object value = this.getObject(rowid, field);
//		if (value != null) {
////			if (value instanceof byte[]) {
////				return new String(((byte[]) value));
////			} else
////				return value.toString();
//			return ValueExchange.getStringFromObject(value);
//		} else
//			return "";
		inrange(rowid, field);
		String value = allResults[getTrueRowid(rowid)].getString(field);
		return value != null?value:"";
	}

	/**
	 * @param rowid
	 *            ��0��ʼ
	 * @param field
	 * @return String
	 */
	public String getString(int rowid, String field, String defaultValue)
			throws SQLException {
		Object value = this.getObject(rowid, field);
		if (value != null) {
//			if (value instanceof byte[]) {
//				return new String(((byte[]) value));
//			} else
//				return value.toString();
			return ValueExchange.getStringFromObject(value);
		}

		else
			return defaultValue;
	}

	/**
	 * 
	 * @param rowid
	 *            ��0��ʼ
	 * @param field
	 * @return Object
	 */
	public Object getObject(int rowid, String field) throws SQLException {
		inrange(rowid, field);
		return allResults[getTrueRowid(rowid)].getObject(field);

//		throw new SQLException("��ȡ�ֶ�[" + field
//				+ "]��ֵʧ�ܣ��ֶο��ܳ��ֶ�Σ����߱��ֶ�û�а����ڶ�Ӧ��sql����У��������sql����Ƿ���ȷ");

	}
	
	protected void inrange(int rowid, int column) throws SQLException
	{
		if (rowid >= size() || rowid < 0)
			throw new SQLException("out of row range:" + rowid);
		if (column >= this.meta.getColumnCounts() || column < 0)
			throw new SQLException("out of column range:" + column);
		try {
			assertLoaded(rowid);
		} catch (SQLException e) {
			throw e;
		}
	}
	protected void inrange(int rowid, String columnName) throws SQLException
	{
//		if (rowid >= size() || rowid < 0)
//			throw new SQLException("out of row range:" + rowid);
//		if (column >= this.meta.getColumnCounts() || column < 0)
//			throw new SQLException("out of column range:" + column);
//		try {
//			assertLoaded(rowid);
//		} catch (SQLException e) {
//			throw e;
//		}
		if (rowid >= size() || rowid < 0)
			throw new SQLException("out of row range: " + rowid);
		if (columnName == null || columnName.trim().equals(""))
			throw new SQLException("field name error:[field=" + columnName + "]");
		try {
			assertLoaded(rowid);
		} catch (SQLException e) {
			throw e;
		}

		if (!check(columnName)) 
			throw new SQLException("Field [" + columnName + "] is not in the query list.");
			
	}
	
	protected void inrange(int rowid) throws SQLException
	{
		if (rowid >= size() || rowid < 0)
			throw new SQLException("out of row range:" + rowid);
		
		try {
			assertLoaded(rowid);
		} catch (SQLException e) {
			throw e;
		}
	}

	/**
	 * 
	 * @param rowid
	 *            ��0��ʼ
	 * @param column
	 *            ��0��ʼ
	 * @return Object
	 */
	public Object getObject(int rowid, int column) throws SQLException {
		inrange(rowid, column);
		Object object = allResults[getTrueRowid(rowid)].getObject(column);
		return object;
	}

	/**
	 * @param rowid
	 *            ���㿪ʼ
	 * @param field
	 * @return Date
	 */
	public Date getDate(int rowid, String field) throws SQLException {
		inrange(rowid, field);
		return allResults[getTrueRowid(rowid)].getDate(field);
	}

	/**
	 * ������id����id��ȡ�ֶ�ֵ
	 * 
	 * @param rowid
	 *            ��0��ʼ
	 * @param column
	 *            ���㿪ʼ
	 * @return Date
	 */

	public Date getDate(int rowid, int column) throws SQLException {
		inrange(rowid, column);
		return allResults[getTrueRowid(rowid)].getDate(column);
	}

	private boolean check(String field) {
//		String fields[] = this.meta.get_columnLabel_upper();
//		String field_ = field.trim().toUpperCase();
//		for (int i = 0; fields != null && i < fields.length; i++) {
//			if (fields[i].equals(field_))
//				return true;
//		}
		return true;
	}



	/**
	 * �����е��ֶ�����ת��Ϊ��д
	 * 
	 */
//	private void fieldsToUPPERCASE() {
//		if (fields != null) {
//			f_temps = new String[fields.length];
//			for (int i = 0; i < fields.length; i++)
//				f_temps[i] = fields[i].toUpperCase();
//		}
//
//	}


	/**
	 * Executes a statement and returns results in the form of a Hashtable
	 * array. ������ִ����Ϻ�����Խ�������л���
	 * 
	 * @param dbname
	 *            ���ݿ����ӳ�����
	 * @param sql
	 *            ���ݲ�ѯ���߸������
	 * 
	 * @param goNative
	 *            �Ƿ�ʹ��ԭʼ�����ݿ�api
	 * @param offset
	 *            ���ؼ�¼����ʼ��ַ
	 * @param maxsize
	 *            ���ؼ�¼����
	 * @return �����
	 * @throws SQLException
	 */


	protected Record[] doJDBC(String dbname, String sql,
			// Connection con,
					boolean goNative, long offset, int maxsize, Connection con_,
					Class objectType,RowHandler rowHandler,int resultType)
					throws SQLException {
		StatementInfo stmtInfo = null;
		try
		{
			stmtInfo = new StatementInfo(dbname, new NewSQLInfo(sql),
			// Connection con,
					goNative, offset, maxsize, isRobotQuery(dbname), con_,oraclerownum);

			return doJDBC(stmtInfo, objectType, rowHandler);
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

	}
	
	protected ResultMap innerExecutePagineJDBC(StatementInfo stmtInfo,
				Class objectType,RowHandler rowhandler,int result_type) throws SQLException
	{
		
		
		ResultMap resultMap = new ResultMap();

		try {
			
			ResultSet res = null;
			Statement s = null;
			Statement s1 = null;
			ResultSet rs = null;
			stmtInfo.init();
			
			/**
			 * ���ڽ���� 
                            ԭ����Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY); 
                               ��仰��д����Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                               com.microsoft.sqlserver.jdbc.SQLServerException: ��֧�ִ��α�����/������ϡ� 
			 */
			
//			s = stmtInfo.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
//					ResultSet.CONCUR_UPDATABLE);
			
			s = stmtInfo.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
			                             stmtInfo.getCursorType(stmtInfo.getDbname()));


			// See if this was a select statement
			String count = stmtInfo.countSql();
//			s1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
//					ResultSet.CONCUR_UPDATABLE);
			s1 = stmtInfo.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					stmtInfo.getCursorType(stmtInfo.getDbname()));
			rs = s1.executeQuery(count);
			stmtInfo.addResultSet(rs);
			// log.debug("Get count by sql:" + count);
			if (rs.next()) {
				totalSize = rs.getInt(1);				
			}


			stmtInfo.rebuildOffset(totalSize);

			if (totalSize > 0) {


				String paginesql = stmtInfo.paginesql(false).getSql();
				if(showsql(stmtInfo.getDbname()))
				{
					log.debug("JDBC pageine origine statement:" + stmtInfo.getSql());
					log.debug("JDBC pageine statement:" + paginesql);
				}
//				log.debug("paginesql:" + paginesql);
				s.execute(paginesql);
//				results = new DBHashtable[stmtInfo.getMaxsize()];
				res = s.getResultSet();
				stmtInfo.addResultSet(res);
				stmtInfo.absolute(res);
				stmtInfo.cacheResultSetMetaData( res,true);

				this.meta = stmtInfo.getMeta();
				resultMap = stmtInfo.buildResultMap(res, objectType, 
															 rowhandler, stmtInfo.getMaxsize(),
															 true, result_type);
				if(resultMap != null)
					this.size = resultMap.getSize();

			}
			return resultMap;
			

		} catch (SQLException sqle) {
			try{
				
				log.error(sqle.getMessage(),sqle);
			}
			catch(Exception ei)
			{
				
			}
			if(stmtInfo != null)
				stmtInfo.errorHandle(sqle);
			throw sqle;
		} catch (Exception e) {
			try{
				
				log.error(e.getMessage(),e);
			}
			catch(Exception ei)
			{
				
			}
			if(stmtInfo != null)
				stmtInfo.errorHandle(e);
			throw new NestedSQLException(e.getMessage(),e);
		} finally {
			if(stmtInfo != null)
				stmtInfo.dofinally();
//			stmtInfo = null;

			
		}

//		return results;
	}
	
	

	/**
	 * Executes a statement and returns results in the form of a Hashtable
	 * array. ������ִ����Ϻ�����Խ�������л���
	 * 
	 * @param dbname
	 *            ���ݿ����ӳ�����
	 * @param sql
	 *            ���ݲ�ѯ���߸������
	 * 
	 * @param goNative
	 *            �Ƿ�ʹ��ԭʼ�����ݿ�api
	 * @param offset
	 *            ���ؼ�¼����ʼ��ַ
	 * @param maxsize
	 *            ���ؼ�¼����
	 * @return �����
	 * @throws SQLException
	 */
	
	protected Record[] doJDBC(StatementInfo stmtInfo,
			Class objectType,RowHandler rowHandler) throws SQLException {
		ResultMap resultMap = this.innerExecutePagineJDBC(stmtInfo,
														  objectType, 
														  rowHandler, 
														  ResultMap.type_maparray);
		return (Record[])resultMap.getCommonresult();
//		// log.debug("doJDBC sql:" + sql + ",offset=" + offset + ",maxsize="
//		// + maxsize);
////		StatementInfo stmtInfo = null;
//		Record[] results = null;
//		ResultSet res = null;
//		Statement s = null;
//		Statement s1 = null;
//		ResultSet rs = null;
//		
//
//
//		try {
//			
//			stmtInfo.init();
//			
//
//			s = stmtInfo.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
//					ResultSet.CONCUR_UPDATABLE);
//
//
//			// See if this was a select statement
//			String count = stmtInfo.countSql();
////			s1 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
////					ResultSet.CONCUR_UPDATABLE);
//			s1 = stmtInfo.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
//					ResultSet.CONCUR_UPDATABLE);
//			rs = s1.executeQuery(count);
//			stmtInfo.addResultSet(rs);
//			// log.debug("Get count by sql:" + count);
//			if (rs.next()) {
//				totalSize = rs.getInt(1);				
//			}
//
//
//			stmtInfo.rebuildOffset(totalSize);
//
//			if (totalSize > 0) {
//
//
//				String paginesql = stmtInfo.paginesql();
//				log.debug("paginesql:" + paginesql);
//				s.execute(paginesql);
////				results = new DBHashtable[stmtInfo.getMaxsize()];
//				res = s.getResultSet();
//				stmtInfo.addResultSet(res);
//				stmtInfo.absolute(res);
//				stmtInfo.cacheResultSetMetaData( res);
//
//				this.meta = stmtInfo.getMeta();
//				results = stmtInfo.buildResult(res,stmtInfo.getMaxsize(),true);
//
//			}
//			
//
//		} catch (SQLException sqle) {
//			if(stmtInfo != null)
//				stmtInfo.errorHandle(sqle);
//			throw sqle;
//		} catch (Exception e) {
//			if(stmtInfo != null)
//				stmtInfo.errorHandle(e);
//			throw new NestedSQLException(e.getMessage(),e);
//		} finally {
//			if(stmtInfo != null)
//				stmtInfo.dofinally();
//
//			
//		}
//
//		return results;

	}

	

	
	

	/**
	 * ��dbname��Ӧ�����ݿ��в����¼
	 * 
	 * @param dbname
	 *            ���ݿ����ӳ�����
	 * @param sql
	 *            �������
	 * @return Object ����
	 * @throws SQLException
	 */
	public Object executeInsert(String dbname, String sql) throws SQLException {

		return executeInsert( dbname,  sql,(Connection )null);

	}
	
	/**
	 * ��dbname��Ӧ�����ݿ��в����¼
	 * 
	 * @param dbname
	 *            ���ݿ����ӳ�����
	 * @param sql
	 *            �������
	 * @return Object ����
	 * @throws SQLException
	 */
	public Object executeInsert(String dbname, String sql,Connection con) throws SQLException {

		return doJDBCInsert(sql, dbname, false,con);

	}

	public Object executeInsert(String sql) throws SQLException {

		return executeInsert( sql,(Connection)null);
	}
	public Object executeInsert(String sql,Connection con) throws SQLException {

		return executeInsert(null, sql, con);
	}

	/**
	 * ִ��ɾ������
	 * 
	 * @param sql
	 * @return ɾ����Ϣ
	 * @throws SQLException
	 */
	public Object executeDelete(String sql) throws SQLException {
		// Connection con =
		// SQLManager.getInstance().requestConnection(DEFAULT_DBNAME);
		return executeDelete(SQLManager.getInstance().getDefaultDBName(), sql);
		// return doJDBCInsert(sql,con,false);
	}

	/**
	 * ִ��ɾ������
	 * 
	 * @param sql
	 *            ɾ�����
	 * @param dbName
	 *            ���ݿ����ӳ�����
	 * @return ɾ����Ϣ
	 * @throws SQLException
	 */
	public Object executeDelete(String dbName, String sql, Connection con)
			throws SQLException {
		// Connection con =
		// SQLManager.getInstance().requestConnection(DEFAULT_DBNAME);
		return executeSql(dbName, sql, con);
		// return doJDBCInsert(sql,con,false);
	}

	/**
	 * ִ��ɾ������
	 * 
	 * @param sql
	 *            ɾ�����
	 * @param dbName
	 *            ���ݿ����ӳ�����
	 * @return ɾ����Ϣ
	 * @throws SQLException
	 */
	public Object executeDelete(String dbName, String sql) throws SQLException {
		// Connection con =
		// SQLManager.getInstance().requestConnection(DEFAULT_DBNAME);
		return executeDelete(dbName, sql, (Connection)null);
		// return doJDBCInsert(sql,con,false);
	}

	/**
	 * ִ��ɾ������
	 * 
	 * @param sql
	 *            ɾ�����
	 * @param goNative
	 *            �Ƿ�ʹ��ԭʼ���ݿ�����
	 * @return ɾ����Ϣ
	 * @throws SQLException
	 */
	public Object executeDelete(String sql, boolean goNative, Connection con)
			throws SQLException {
		// Connection con =
		// SQLManager.getInstance().requestConnection(DEFAULT_DBNAME);
		return executeDelete(SQLManager.getInstance().getDefaultDBName(), sql,
				goNative, con);
		// return doJDBCInsert(sql,con,false);
	}

	/**
	 * ִ��ɾ����䡣
	 * 
	 * @param dbName
	 *            ���ݿ����ӳ�����
	 * @param sql
	 *            ���ݿ�ɾ�����
	 * @param goNative
	 *            �Ƿ�ʹ��ԭʼ�����ݿ�����
	 * @return ɾ����Ϣ
	 * @throws SQLException
	 */
	public Object executeDelete(String dbName, String sql, boolean goNative,
			Connection con) throws SQLException {
		// Connection con =
		// SQLManager.getInstance().requestConnection(DEFAULT_DBNAME);
		return doJDBC(dbName, sql, goNative, con);
		// return doJDBCInsert(sql,con,false);
	}

	/**
	 * �Ż������ݱ����ݵĲ�ѯ�� ����fethchsize�Ĵ�С����ÿ�����ݻ�ȡ��¼������ �������ݿ��ѯ��Ч��
	 * 
	 * @param dbName
	 *            ���ݿ����ӳص�����
	 * @param sql
	 *            ����ѯ��sql���
	 * @param fetchsize
	 *            ÿ������������������
	 * @throws SQLException
	 */
	public void executeSelect(String dbName, String sql, int fetchsize)
			throws SQLException {
		this.isRobustQuery = true;
		this.fetchsize = fetchsize;
		this.fetchdbName = dbName;
		this.fetchsql = sql;
		this.executeSelect(dbName, sql, fetchoffset, fetchsize);

	}

	/**
	 * �Ż������ݱ����ݵĲ�ѯ�� ����fethchsize�Ĵ�С����ÿ�����ݻ�ȡ��¼������ �������ݿ��ѯ��Ч��
	 * 
	 * @param sql
	 *            ����ѯ��sql���
	 * @param fetchsize
	 *            ÿ������������������
	 * @throws SQLException
	 */
	public void executeSelect(String sql, int fetchsize) throws SQLException {
		this.executeSelect(null, sql, fetchsize);
	}

	/**
	 * ����fetch����
	 * 
	 */
	public void resetFetch() {
		this.isRobustQuery = false;
		this.fetchsize = 0;
		this.fetchdbName = null;
		this.fetchsql = null;
		this.totalSize = 0;
		this.allResults = null;
//		this.f_temps = null;
//		this.fields = null;
		this.size = 0;
		this.fetcholdrowid = 0;
	}

	/**
	 * ���÷�ҳ���б����
	 * 
	 */
	public void resetPager() {
		this.totalSize = 0;
		this.size = 0;
//		this.f_temps = null;
//		this.fields = null;
		this.allResults = null;
	}

	/**
	 * �������������
	 * 
	 */
	public void resetBatch() {
		this.autocommit = true;
		this.batchautocommit = false;
		if(this.batchSQLS != null)
		{
			this.batchSQLS.clear();
			this.batchSQLS = null;
		}
		setBatchDBName(SQLManager.getInstance().getDefaultDBName());
	}

	/**
	 * ִ�����ݿ���������
	 * 
	 * @param dbname
	 * @param sql
	 * 
	 * @param goNative
	 * @return ���������ݿ�����
	 * @throws SQLException
	 */
	public Object doJDBCInsert(String sql_, String dbname_, boolean goNative_,Connection con_)
			throws SQLException {
		StatementInfo stmtInfo = null;

		Statement s = null;

		try {
			stmtInfo =  new StatementInfo(dbname_,
					new NewSQLInfo(sql_),
					goNative_,
					 con_,
					 false);
			stmtInfo.init();			
			boolean autokey = isAutoprimarykey(dbname_);
			s = stmtInfo.createStatement();	
			if(autokey)
			{
				Object[] temp;
				temp = StatementParser.refactorInsertStatement(stmtInfo.getCon(), stmtInfo.getSql(), stmtInfo.getDbname());
			
				PrimaryKey primaryKey = (PrimaryKey) temp[3];
				if (temp[1] != null) {
	
					try {
						String changesqlString = (String) temp[0];
						// String ret = s.executeUpdate(sql) + "";
						if(showsql(dbname_))
						{
							log.debug("JDBC Insert statement:" + stmtInfo.getSql());
						}
						s.executeUpdate(changesqlString);
	
						// return ret;
						if (temp[2] != null && temp[3] != null) {
	
							UpdateSQL updateTableinfo = (UpdateSQL) temp[2];
							execute(stmtInfo.getCon(), updateTableinfo);
						}
						stmtInfo.commit();
	
						return temp[1];
	
					} catch (Exception e) {
						// if(tx != null)
						// tx.setRollbackOnly();
						primaryKey.restoreKey(temp[1]);
						log.error(temp[0], e);
						throw e;
					}
	
				} else {
					int i = s.executeUpdate(stmtInfo.getSql());
					stmtInfo.commit();
					return new Integer(i);
				}
			}
			else
			{
				if(showsql(dbname_))
				{
					log.debug("JDBC Insert statement:" + stmtInfo.getSql());
				}
				int i = s.executeUpdate(stmtInfo.getSql());
				stmtInfo.commit();
				return new Integer(i);
			}
		} catch (SQLException e) {
			try{
				
				log.error(stmtInfo.getSql(), e);
			}
			catch(Exception ei)
			{
				
			}
			if(stmtInfo != null)
				stmtInfo.errorHandle(e);
			// e.printStackTrace();
			
			throw e;
		} catch (Exception e) {
			try{
				
				log.error(stmtInfo.getSql(), e);
			}
			catch(Exception ei)
			{
				
			}
			if(stmtInfo != null)
				stmtInfo.errorHandle(e);
			throw new NestedSQLException(e.getMessage(),e);
		} finally {
			if(stmtInfo != null)
				stmtInfo.dofinally();
			stmtInfo = null;
		}
	}

	/**
	 * ִ�����ݿ��ѯ����
	 * 
	 * @param sql
	 *            ��ѯ���
	 * @return hash���飬��װ��ѯ���
	 * @throws SQLException
	 * @deprecated
	 */
	public Hashtable[] executeQuery(String sql) throws SQLException {
		return executeQuery(sql, (Connection) null);
	}

	/**
	 * ִ�����ݿ��ѯ����
	 * 
	 * @param sql
	 *            ��ѯ���
	 * @return hash���飬��װ��ѯ���
	 * @throws SQLException
	 *  @deprecated
	 */
	public Hashtable[] executeQuery(String sql, Connection con)
			throws SQLException {
		return executeSql(SQLManager.getInstance().getDefaultDBName(), sql, con);
	}

	/**
	 * ִ�����ݿ��ѯ����
	 * 
	 * @param sql
	 *            ��ѯ���
	 * @param fields
	 *            ��ѯ�ֶ�����
	 * @throws SQLException
	 *  @deprecated
	 */
	public void executeQuery(String sql, String fields[], Connection con)
			throws SQLException {

		allResults = executeSql(SQLManager.getInstance().getDefaultDBName(),
				sql, con);
		this.size = allResults == null ? 0 : allResults.length;
//		this.fields = fields;
//		fieldsToUPPERCASE();
	}

	/**
	 * ִ�����ݿ��ѯ����
	 * 
	 * @param sql
	 *            ��ѯ���
	 * @param fields
	 *            ��ѯ�ֶ�����
	 * @throws SQLException
	 *  @deprecated
	 */
	public void executeQuery(String sql, String fields[]) throws SQLException {

		executeQuery(sql, fields, (Connection) null);
	}

	/**
	 * ִ�����ݿ��ѯ����
	 * 
	 * @param sql
	 *            ��ѯ���
	 * @throws SQLException
	 */
	public void executeSelect(String sql) throws SQLException {
		executeSelect(sql, (Connection) null);
	}

	/**
	 * ִ�����ݿ��ѯ����
	 * 
	 * @param sql
	 *            ��ѯ���
	 * @throws SQLException
	 */
	public void executeSelect(String sql, Connection con) throws SQLException {
		executeSelect(SQLManager.getInstance().getDefaultDBName(), sql, con);
	}

	/**
	 * ִ�����ݿ��ѯ������
	 * 
	 * @param sql
	 *            ��ѯ���
	 * @throws SQLException
	 */
	public void executeSelectLimit(String sql, int limit) throws SQLException {
		executeSelectLimit(SQLManager.getInstance().getDefaultDBName(), sql,
				limit);
	}

	/**
	 * ִ��oracle�ض���Ч���ݿ��ѯ����������������oracle�����ṩ�Ļ��ƣ�����oracle�ĸ�Чȡtop n����¼��ѯ
	 * ��ȡ��Ч��oracle��ѯ��䣬sql���Ѿ�д��ROW_NUMBER() OVER ( ORDER BY cjrq ) rownum
	 * �����ܵ��ñ���������oralce�ķ�ҳ���,����rownum�Ͷ�Ӧ�˱������Ĳ���rownum
	 * 
	 * @param sql
	 *            ��ѯ���
	 * @throws SQLException
	 */
	public void executeSelectLimitForOracle(String sql, int limit, String rownum)
			throws SQLException {
		executeSelectLimitForOracle(
				SQLManager.getInstance().getDefaultDBName(), sql, limit, rownum);
	}

	/**
	 * ִ���������ݿ����������ݿ��ѯ����
	 * 
	 * @param dbName
	 *            ���ݿ����ӳ�����
	 * @param sql
	 *            ��ѯ���
	 * @param limit
	 *            ��¼����
	 * @throws SQLException
	 */
	public void executeSelectLimit(String dbName, String sql, int limit)
			throws SQLException {
		executeSelectLimit(dbName, sql, limit, (Connection) null);

		// this.fields = fields;
		// fieldsToUPPERCASE();
	}

	/**
	 * ִ���������ݿ����������ݿ��ѯ����
	 * 
	 * @param dbName
	 *            ���ݿ����ӳ�����
	 * @param sql
	 *            ��ѯ���
	 * @param limit
	 *            ��¼����
	 * @throws SQLException
	 */
	public void executeSelectLimit(String dbName, String sql, int limit,
			Connection con) throws SQLException {
		sql = getPool(dbName).getDbAdapter().getLimitSelect(sql, limit);
		allResults = executeSql(dbName, sql, con);
		this.size = allResults == null ? 0 : allResults.length;

		// this.fields = fields;
		// fieldsToUPPERCASE();
	}

	/**
	 * ִ��oracle��Ч�ض��������ݿ����������ݿ��ѯ����
	 * 
	 * @param dbName
	 *            ���ݿ����ӳ�����
	 * @param sql
	 *            ��ѯ���
	 * @param limit
	 *            ��¼����
	 * @throws SQLException
	 */
	public void executeSelectLimitForOracle(String dbName, String sql,
			int limit, String rownum) throws SQLException {

		executeSelectLimitForOracle(dbName, sql, limit, rownum,
				(Connection) null);

		// this.fields = fields;
		// fieldsToUPPERCASE();
	}

	/**
	 * ִ��oracle��Ч�ض��������ݿ����������ݿ��ѯ����
	 * 
	 * @param dbName
	 *            ���ݿ����ӳ�����
	 * @param sql
	 *            ��ѯ���
	 * @param limit
	 *            ��¼����
	 * @throws SQLException
	 */
	public void executeSelectLimitForOracle(String dbName, String sql,
			int limit, String rownum, Connection con) throws SQLException {

		if (rownum == null || rownum.equals(""))
			sql = getPool(dbName).getDbAdapter().getLimitSelect(sql, limit);
		else
			sql = getPool(dbName).getDbAdapter().getOracleLimitSelect(sql,
					limit, rownum);
		allResults = executeSql(dbName, sql, con);
		this.size = allResults == null ? 0 : allResults.length;

		// this.fields = fields;
		// fieldsToUPPERCASE();
	}

	/**
	 * ִ�����ݿ��ѯ����
	 * 
	 * @param dbName
	 *            ���ݿ����ӳ�����
	 * @param sql
	 *            ��ѯ���
	 * @throws SQLException
	 */
	public void executeSelect(String dbName, String sql) throws SQLException {

		executeSelect(dbName, sql, (Connection) null);

		// this.fields = fields;
		// fieldsToUPPERCASE();
	}

	/**
	 * ִ�����ݿ��ѯ����
	 * 
	 * @param dbName
	 *            ���ݿ����ӳ�����
	 * @param sql
	 *            ��ѯ���
	 * @throws SQLException
	 */
	public void executeSelect(String dbName, String sql, Connection con)
			throws SQLException {

		allResults = executeSql(dbName, sql, con);
		this.size = allResults == null ? 0 : allResults.length;
		
		// this.fields = fields;
		// fieldsToUPPERCASE();
	}
	public void clear()
	{
		allResults = null;
	}

	/**
	 * ִ�����ݿ��ѯ����
	 * 
	 * @param dbName
	 *            ��ѯ�����ݿ����ӳ�
	 * @param sql
	 *            ��ѯ���
	 * @return hash���飬��װ��ѯ���
	 * @throws SQLException
	 *  @deprecated
	 */
	public Hashtable[] executeQuery(String dbName, String sql)
			throws SQLException {
		return executeQuery(dbName, sql, (Connection) null);
	}

	/**
	 * ִ�����ݿ��ѯ����
	 * 
	 * @param dbName
	 *            ��ѯ�����ݿ����ӳ�
	 * @param sql
	 *            ��ѯ���
	 * @return hash���飬��װ��ѯ���
	 * @throws SQLException
	 *  @deprecated
	 */
	public Hashtable[] executeQuery(String dbName, String sql, Connection con)
			throws SQLException {
		return executeSql(dbName, sql, con);
	}

	/**
	 * ִ�����ݿ��ѯ����
	 * 
	 * @param dbName
	 *            ��ѯ�����ݿ����ӳ�
	 * @param sql
	 *            ��ѯ���
	 * @param fields
	 *            ��ѯ�ֶ�����
	 * @throws SQLException
	 *  @deprecated
	 */
	public void executeQuery(String dbName, String sql, String fields[])
			throws SQLException {
		executeQuery(dbName, sql, fields, (Connection) null);
	}

	/**
	 * ִ�����ݿ��ѯ����
	 * 
	 * @param dbName
	 *            ��ѯ�����ݿ����ӳ�
	 * @param sql
	 *            ��ѯ���
	 * @param fields
	 *            ��ѯ�ֶ�����
	 * @throws SQLException
	 *  @deprecated
	 */
	public void executeQuery(String dbName, String sql, String fields[],
			Connection con) throws SQLException {
		this.allResults = executeSql(dbName, sql, con);
		this.size = allResults == null ? 0 : allResults.length;
//		this.fields = fields;
//		fieldsToUPPERCASE();
	}

	/**
	 * ִ�з�ҳ��ѯ����
	 * 
	 * @param sql
	 *            ��ѯ���
	 * @param offset
	 *            ��ҳ����������ȡ��¼����ʼλ��
	 * @param maxsize
	 *            ��ҳ����������ȡ����¼����
	 * @return hash���飬��װ��ѯ���
	 * @throws SQLException
	 *  @deprecated
	 */
	public Hashtable[] executeQuery(String sql, int offset, int maxsize)
			throws SQLException {
		return executeQuery(sql, offset, maxsize, (Connection) null);
	}

	/**
	 * ִ�з�ҳ��ѯ����
	 * 
	 * @param sql
	 *            ��ѯ���
	 * @param offset
	 *            ��ҳ����������ȡ��¼����ʼλ��
	 * @param maxsize
	 *            ��ҳ����������ȡ����¼����
	 * @return hash���飬��װ��ѯ���
	 * @throws SQLException
	 *  @deprecated
	 */
	public Hashtable[] executeQuery(String sql, int offset, int maxsize,
			Connection con) throws SQLException {
		return executeSql(SQLManager.getInstance().getDefaultDBName(), sql,
				offset, maxsize, con,null,null,ResultMap.type_maparray);
	}

	/**
	 * ִ�з�ҳ��ѯ����
	 * 
	 * @param sql
	 *            ��ѯ���
	 * @param offset
	 *            ��ҳ����������ȡ��¼����ʼλ��
	 * @param maxsize
	 *            ��ҳ����������ȡ����¼����
	 * @return hash���飬��װ��ѯ���
	 * @throws SQLException
	 *  @deprecated
	 */
	public void executeQuery(String sql, int offset, int maxsize,
			String fields[]) throws SQLException {

		executeQuery(sql, offset, maxsize, fields, (Connection) null);
	}

	/**
	 * ִ�з�ҳ��ѯ����
	 * 
	 * @param sql
	 *            ��ѯ���
	 * @param offset
	 *            ��ҳ����������ȡ��¼����ʼλ��
	 * @param maxsize
	 *            ��ҳ����������ȡ����¼����
	 * @return hash���飬��װ��ѯ���
	 * @throws SQLException
	 *  @deprecated
	 */
	public void executeQuery(String sql, int offset, int maxsize,
			String fields[], Connection con) throws SQLException {

		this.allResults = executeSql(SQLManager.getInstance()
				.getDefaultDBName(), sql, offset, maxsize, con,null,null,ResultMap.type_maparray);
		this.size = allResults == null ? 0 : allResults.length;
//		this.fields = fields;
//		fieldsToUPPERCASE();
	}

	/**
	 * ִ�з�ҳ��ѯ����
	 * 
	 * @param dbName
	 *            ��ѯ�����ݿ����ӳ�
	 * @param sql
	 *            ��ѯ���
	 * @param offset
	 *            ��ҳ����������ȡ��¼����ʼλ��
	 * @param maxsize
	 *            ��ҳ����������ȡ����¼����
	 * @return hash���飬��װ��ѯ���
	 * @throws SQLException
	 *  @deprecated
	 */
	public Hashtable[] executeQuery(String dbName, String sql, long offset,
			int maxsize) throws SQLException {
		return executeQuery(dbName, sql, offset, maxsize, (Connection) null);
	}

	/**
	 * ִ�з�ҳ��ѯ����
	 * 
	 * @param dbName
	 *            ��ѯ�����ݿ����ӳ�
	 * @param sql
	 *            ��ѯ���
	 * @param offset
	 *            ��ҳ����������ȡ��¼����ʼλ��
	 * @param maxsize
	 *            ��ҳ����������ȡ����¼����
	 * @return hash���飬��װ��ѯ���
	 * @throws SQLException
	 *  @deprecated
	 */
	public Hashtable[] executeQuery(String dbName, String sql, long offset,
			int maxsize, Connection con) throws SQLException {
		return executeSql(dbName, sql, offset, maxsize, con,null,null,ResultMap.type_maparray);
	}

	/**
	 * ִ�з�ҳ��ѯ����
	 * 
	 * @param dbName
	 *            ��ѯ�����ݿ����ӳ�
	 * @param sql
	 *            ��ѯ���
	 * @param offset
	 *            ��ҳ����������ȡ��¼����ʼλ��
	 * @param maxsize
	 *            ��ҳ����������ȡ����¼����
	 * @return hash���飬��װ��ѯ���
	 * @throws SQLException
	 */
	public void executeSelect(String dbName, String sql, long offset,
			int maxsize) throws SQLException {
		executeSelect(dbName, sql, offset, maxsize, (Connection) null);
	}

	/**
	 * ִ�з�ҳ��ѯ����
	 * 
	 * @param dbName
	 *            ��ѯ�����ݿ����ӳ�
	 * @param sql
	 *            ��ѯ���
	 * @param offset
	 *            ��ҳ����������ȡ��¼����ʼλ��
	 * @param maxsize
	 *            ��ҳ����������ȡ����¼����
	 * @return hash���飬��װ��ѯ���
	 * @throws SQLException
	 */
	public void executeSelect(String dbName, String sql, long offset,
			int maxsize, Connection con) throws SQLException {
		this.allResults = executeSql(dbName, sql, offset, maxsize, con,null,null,ResultMap.type_maparray);

		this.size = allResults == null ? 0 : allResults.length;
	}

	/**
	 * 
	 * ִ�з�ҳ��ѯ����,
	 * 
	 * @param dbName
	 *            ��ѯ�����ݿ����ӳ�
	 * @param sql
	 *            ��ѯ���
	 * @param offset
	 *            ��ҳ����������ȡ��¼����ʼλ��
	 * @param maxsize
	 *            ��ҳ����������ȡ����¼����
	 * 
	 * 
	 * @param robotquery
	 *            �����Ƿ���
	 * @throws SQLException
	 */
	public void executeSelect(String dbName, String sql, long offset,
			int maxsize, boolean robotquery) throws SQLException {

		executeSelect( dbName,  sql,  offset,
				 maxsize,  robotquery,(Connection )null);
	}
	
	/**
	 * 
	 * ִ�з�ҳ��ѯ����,
	 * 
	 * @param dbName
	 *            ��ѯ�����ݿ����ӳ�
	 * @param sql
	 *            ��ѯ���
	 * @param offset
	 *            ��ҳ����������ȡ��¼����ʼλ��
	 * @param maxsize
	 *            ��ҳ����������ȡ����¼����
	 * 
	 * 
	 * @param robotquery
	 *            �����Ƿ���
	 * @throws SQLException
	 */
	public void executeSelect(String dbName, String sql, long offset,
			int maxsize, boolean robotquery,Connection con) throws SQLException {

		this.allResults = executeSql(dbName, sql, offset, maxsize, robotquery,con,null,null,ResultMap.type_maparray);

		this.size = allResults == null ? 0 : allResults.length;
	}

	/**
	 * ��ָ��dbName�����ݿ���ִ�з�ҳ��ѯ����������������oracle�����ṩ�ķ�ҳ���ƣ�����oracle�ĸ�Ч��ҳ��ѯ
	 * ��ȡ��Ч��oracle��ҳ��䣬sql���Ѿ�д��ROW_NUMBER() OVER ( ORDER BY cjrq ) rownum
	 * �����ܵ��ñ���������oralce�ķ�ҳ���,����rownum�Ͷ�Ӧ�˱������Ĳ���rownum ���磺 String sql = "select
	 * name,row_number() over (order by id,name) rownum_ from test"; DBUtil
	 * dbUtil = new DBUtil();
	 * dbUtil.executeSelectForOracle("bspf",sql,offset,maxsize,"rownum_"); .....
	 * dbUtil.size(); dbUtil.getTotalSize();
	 * 
	 * 
	 * @param dbName
	 *            ��ѯ�����ݿ����ӳ�
	 * @param sql
	 *            ��ѯ���
	 * @param offset
	 *            ��ҳ����������ȡ��¼����ʼλ��
	 * @param maxsize
	 *            ��ҳ����������ȡ����¼����
	 * @param rownum
	 *            oracle���кű�������������oracle�ĸ�Ч��ҳ��ѯ
	 * @return hash���飬��װ��ѯ���
	 * @throws SQLException
	 */
	public void executeSelectForOracle(String dbName, String sql, long offset,
			int maxsize, String rownum) throws SQLException {
		executeSelectForOracle(dbName, sql, offset, maxsize, rownum,
				(Connection) null);

	}

	/**
	 * ��ָ��dbName�����ݿ���ִ�з�ҳ��ѯ����������������oracle�����ṩ�ķ�ҳ���ƣ�����oracle�ĸ�Ч��ҳ��ѯ
	 * ��ȡ��Ч��oracle��ҳ��䣬sql���Ѿ�д��ROW_NUMBER() OVER ( ORDER BY cjrq ) rownum
	 * �����ܵ��ñ���������oralce�ķ�ҳ���,����rownum�Ͷ�Ӧ�˱������Ĳ���rownum ���磺 String sql = "select
	 * name,row_number() over (order by id,name) rownum_ from test"; DBUtil
	 * dbUtil = new DBUtil();
	 * dbUtil.executeSelectForOracle("bspf",sql,offset,maxsize,"rownum_"); .....
	 * dbUtil.size(); dbUtil.getTotalSize();
	 * 
	 * 
	 * @param dbName
	 *            ��ѯ�����ݿ����ӳ�
	 * @param sql
	 *            ��ѯ���
	 * @param offset
	 *            ��ҳ����������ȡ��¼����ʼλ��
	 * @param maxsize
	 *            ��ҳ����������ȡ����¼����
	 * @param rownum
	 *            oracle���кű�������������oracle�ĸ�Ч��ҳ��ѯ
	 * @return hash���飬��װ��ѯ���
	 * @throws SQLException
	 */
	public void executeSelectForOracle(String dbName, String sql, long offset,
			int maxsize, String rownum, Connection con) throws SQLException {
		this.oraclerownum = rownum;
		this.allResults = executeSql(dbName, sql, offset, maxsize, con,null,null,ResultMap.type_maparray);

		this.size = allResults == null ? 0 : allResults.length;
		oraclerownum = null;

	}

	/**
	 * ��ȱʡ�����ݿ���ִ�з�ҳ��ѯ����������������oracle�����ṩ�ķ�ҳ���ƣ�����oracle�ĸ�Ч��ҳ��ѯ
	 * ��ȡ��Ч��oracle��ҳ��䣬sql���Ѿ�д��ROW_NUMBER() OVER ( ORDER BY cjrq ) rownum
	 * �����ܵ��ñ���������oralce�ķ�ҳ���,����rownum�Ͷ�Ӧ�˱������Ĳ���rownum
	 * 
	 * @param sql
	 *            ��ѯ���
	 * @param offset
	 *            ��ҳ����������ȡ��¼����ʼλ��
	 * @param maxsize
	 *            ��ҳ����������ȡ����¼����
	 * @param rownum
	 *            oracle���кű�������������oracle�ĸ�Ч��ҳ��ѯ
	 * @return hash���飬��װ��ѯ���
	 * @throws SQLException
	 */
	public void executeSelectForOracle(String sql, long offset, int maxsize,
			String rownum) throws SQLException {
		executeSelectForOracle(null, sql, offset, maxsize, rownum);

	}

	/**
	 * ִ�з�ҳ��ѯ����
	 * 
	 * @param sql
	 *            ��ѯ���
	 * @param offset
	 *            ��ҳ����������ȡ��¼����ʼλ��
	 * @param maxsize
	 *            ��ҳ����������ȡ����¼����
	 * @return hash���飬��װ��ѯ���
	 * @throws SQLException
	 */
	public void executeSelect(String sql, long offset, int maxsize)
			throws SQLException {
		executeSelect(null, sql, offset, maxsize);
	}

	/**
	 * ִ�з�ҳ��ѯ����
	 * 
	 * @param dbName
	 *            ��ѯ�����ݿ����ӳ�
	 * @param sql
	 *            ��ѯ���
	 * @param offset
	 *            ��ҳ����������ȡ��¼����ʼλ��
	 * @param maxsize
	 *            ��ҳ����������ȡ����¼����
	 * @return hash���飬��װ��ѯ���
	 * @throws SQLException
	 *  @deprecated
	 */
	public void executeQuery(String dbName, String sql, long offset,
			int maxsize, String fields[]) throws SQLException {
		executeQuery(dbName, sql, offset, maxsize, fields, (Connection) null);
	}

	/**
	 * ִ�з�ҳ��ѯ����
	 * 
	 * @param dbName
	 *            ��ѯ�����ݿ����ӳ�
	 * @param sql
	 *            ��ѯ���
	 * @param offset
	 *            ��ҳ����������ȡ��¼����ʼλ��
	 * @param maxsize
	 *            ��ҳ����������ȡ����¼����
	 * @return hash���飬��װ��ѯ���
	 * @throws SQLException
	 *  @deprecated
	 */
	public void executeQuery(String dbName, String sql, long offset,
			int maxsize, String fields[], Connection con) throws SQLException {
		this.allResults = executeSql(dbName, sql, offset, maxsize, con,null,null,ResultMap.type_maparray);

		this.size = allResults == null ? 0 : allResults.length;
//		this.fields = fields;
//		fieldsToUPPERCASE();
	}

	

	public static void closeConection(Connection connection)
			throws SQLException {
		closeConection(null, connection);

	}

	public static void closeConection(String dbName, Connection connection)
			throws SQLException {
		JDBCTransaction tx = getTransaction();
		if (tx == null) {
			SQLManager datab = getSQLManager();
			datab.returnConnection(dbName, connection);
		} else {
			// try {
			// return tx.getConnection(dbName);
			// } catch (TransactionException e) {
			//				
			// throw new SQLException(e.getMessage());
			// }
		}
	}

	public static void closeResources(Statement stmt, ResultSet rs)
			throws SQLException {
		// SQLManager datab = getSQLManager();
		SQLManager.closeResources(stmt, rs);
	}

	/**
	 * ִ�����ݿ�ĸ������
	 * 
	 * @param updateSql
	 *            update���
	 * @return Hashtable[] �������½����Ϣ
	 * @throws SQLException
	 */
	public Hashtable[] executeUpdate(String updateSql) throws SQLException {
		return executeSql(updateSql);
	}

	/**
	 * ִ�����ݿ�ĸ������
	 * 
	 * @param updateSql
	 *            update���
	 * @param dbName
	 *            ���ݿ�����
	 * @return Hashtable[] �������½����Ϣ
	 * @throws SQLException
	 */
	public Hashtable[] executeUpdate(String dbName, String updateSql)
			throws SQLException {
		return executeUpdate(dbName, updateSql, (Connection) null);
	}

	/**
	 * ִ�����ݿ�ĸ������
	 * 
	 * @param updateSql
	 *            update���
	 * @param dbName
	 *            ���ݿ�����
	 * @return Hashtable[] �������½����Ϣ
	 * @throws SQLException
	 */
	public Hashtable[] executeUpdate(String dbName, String updateSql,
			Connection con) throws SQLException {
		return this.executeSql(dbName, updateSql, con);
	}

	/**
	 * Begins the actual database operation by preparing resources. It calls
	 * doJDBC() to perform the actual operation.
	 * 
	 * @param dbname
	 *            ��ѯ�����ݿ����ӳ�
	 * @param sql
	 *            ��ѯ���
	 * @param offset
	 *            ��ҳ����������ȡ��¼����ʼλ��
	 * @param maxsize
	 *            ��ҳ����������ȡ����¼����
	 * @return hash���飬��װ��ѯ���
	 * @throws SQLException
	 */

	protected Record[] executeSql(String dbname, String sql, long offset,
			int maxsize, Connection con,Class objectType,RowHandler rowHandler,int resultType) throws SQLException {


		return executeSql(dbname, sql, offset,
				maxsize, isRobotQuery(dbname), con, objectType, rowHandler, resultType);

	}

	/**
	 * Begins the actual database operation by preparing resources. It calls
	 * doJDBC() to perform the actual operation.
	 * 
	 * @param dbname
	 *            ��ѯ�����ݿ����ӳ�
	 * @param sql
	 *            ��ѯ���
	 * @param offset
	 *            ��ҳ����������ȡ��¼����ʼλ��
	 * @param maxsize
	 *            ��ҳ����������ȡ����¼����
	 * @return hash���飬��װ��ѯ���
	 * @throws SQLException
	 */

	protected Record[] executeSql(String dbname, String sql, long offset,
			int maxsize, boolean robotquery,Connection con,Class objectType,RowHandler rowHandler,int resultType) throws SQLException {

		Record[] hashResults = null;
		/**
		 * ����Ƿֿ鴦��,��ô����totalSize
		 */
		if (isRobustQuery) {
			totalSize = 0;
		}

		
		StatementInfo stmtInfo = null;
		try {
			
			stmtInfo = new StatementInfo(dbname, new NewSQLInfo(sql),
					// Connection con,
							false, offset, maxsize, robotquery, con,oraclerownum);
			hashResults = doJDBC(stmtInfo, objectType, rowHandler);
		} catch (SQLException e) {
			
			throw e;
		} catch (Exception e) {
			
			throw new NestedSQLException(e);
		} finally {
			stmtInfo = null;
		}

		return hashResults;

	}
	
	/**
	 * 
	 * @param sqls
	 */
	public void setBatchSQLS(List sqls)
	{
		this.batchSQLS = sqls;
	}
	
	
	/**
	 * ���������sql���
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	public void addBatch(String sql) throws SQLException {
		if(this.batchSQLS == null)
			this.batchSQLS = new ArrayList();
		if(sql == null || sql.equals(""))
		{
			this.batchSQLS.clear();
			throw new SQLException("Add batch SQL failed: sql is [" + sql + "]");
		}
		this.batchSQLS.add(sql);
	}



	/**
	 * ����һ�����������Ϣ
	 * 
	 * @param batchUpdateSqls
	 * @throws SQLException
	 */
	private void updateTableInfo(Set batchUpdateSqls,Connection batchCon) throws SQLException {
		if (batchUpdateSqls == null)
			return;
		// DBUtil dbUtil = new DBUtil();
		try {
			// // �������ݿ�������ύģʽ
			// dbUtil.setAutoCommit(isAutoCommit());

			for (Iterator i = batchUpdateSqls.iterator(); i.hasNext();) {
				execute(batchCon, (UpdateSQL) i.next());
				// dbUtil.addBatch(((UpdateSQL) i.next()).getUpdateSql());
			}

			// dbUtil.executeBatch();
		} catch (SQLException e) {

			throw e;
		
		} catch (Exception e) {

			throw new SQLException(e.getMessage());
		}

	}


	/**
	 * �ع�����
	 */
	public void rollbackTransaction() {

	}
	/**
	 * ִ�����������ִ������������֮ǰ����ָ���ض������ݿ����ӳص�����
	 * 
	 * @return ����ǲ�����������ô�������в���������
	 * @throws Exception
	 */
	public Object[] executeBatch() throws SQLException {
		return this.executeBatch((String)null);
	}
	
	
	/**
	 * ִ�����������ִ������������֮ǰ����ָ���ض������ݿ����ӳص�����
	 * 
	 * @return ����ǲ�����������ô�������в���������
	 * @throws Exception
	 */
	public Object[] executeBatch(Connection con) throws SQLException {
		return this.executeBatch(null,con);
	}
	
	/**
	 * ���ض������ݿ�����ִ�����������
	 * @param dbname
	 * @return
	 * @throws Exception
	 */
	public Object[] executeBatch(String dbname) throws SQLException {
		return executeBatch(dbname,null);
	}
	
	
	/**
	 * ִ�����������ִ������������֮ǰ����ָ���ض������ݿ����ӳص�����
	 * 
	 * @return ����ǲ�����������ô�������в���������
	 * @throws Exception
	 */
	public Object[] executeBatch(boolean needtransaction) throws SQLException {
		return this.executeBatch(null,needtransaction);
	}
	
	/**
	 * ���ض������ݿ�����ִ�����������
	 * @param dbname
	 * @return
	 * @throws Exception
	 */
	public Object[] executeBatch(String dbname,boolean needtransaction) throws SQLException {
		return executeBatch(dbname,null,needtransaction);
	}
	
	
	/**
	 * ���ض������ݿ��������ִ�����ݿ����������
	 * @param dbname
	 * @param con
	 * @return
	 * @throws Exception
	 */
	protected Object[] executeBatch(String dbname,Connection con,boolean needtransaction ) throws SQLException {
		try
		{
			return this.executeBatch(this.batchSQLS,
									 dbname == null?batchDBName :dbname,
									 con,
									 needtransaction);
		}
		finally
		{
			if(this.batchSQLS != null)
				this.batchSQLS.clear();
			
			setBatchDBName(SQLManager.getInstance().getDefaultDBName());
		}		
	}
	
	/**
	 * �ж�ָ�������ݿ��Ƿ��������Զ��������ݿ�������ģʽ
	 * 
	 * @return true ��ʶ����
	 *         false ��ʶ������
	 */
	public static boolean isAutoprimarykey(String dbname)
	{
		boolean autokey = getSQLManager().getPool(dbname).isAutoprimarykey();
		return autokey;
		
	}
	/**
	 * ���ض������ݿ��������ִ�����ݿ������������
	 * @param batchSQLS �������sql��伯
	 * @param dbname dbname == null?batchDBName:dbname ���ݿ��߼�������
	 * @param con �ⲿ��������ݿ����ӣ����Ϊ�������������ӳ���������
	 *        ʹ���ⲿ����ʱ�������������κ������ԵĲ���������������ݿ���쳣��ֱ���׳�����쳣��
	 * @param needtransaction ��ʶ����������Ƿ�����ֶ����Ƶ�����trueΪ��Ҫ����ֻ�����е�sql��䶼ִ�гɹ����ύ������
	 * 		  ȫ���ع�
	 *        falseΪ����Ҫ���񣬰�˳��ִ�������������ֱ��ȫ��ִ��������������ݿ��쳣����ֹ
	 *        ��Ҫע���������ڸ÷����������������Ļ�����ִ�У���needtransaction������������
	 * @return
	 * @throws SQLException
	 */
	protected Object[] executeBatch(List batchSQLS,
								 String dbname_,
								 Connection con_,
								 boolean needtransaction_ ) throws SQLException {
		if (batchSQLS == null || batchSQLS.size() == 0) {
			System.out.println("û��Ҫ����������������У�");
			return null;
		}
		StatementInfo stmtInfo = null;
		Object[] ret_keys = null;
		// ���������ָ��ִ�е����ݿ�������������ò���������ɵ�����ֵ
		List batchResult = new ArrayList();
		// ���������ָ��ִ�е����ݿ�������������ò������ִ�к���Ҫ���±��������Ϣ�����
		Set batchUpdateSqls = new TreeSet();

		Statement batchStmt = null;
		if(dbname_ == null)
			dbname_ = SQLManager.getInstance().getDefaultDBName();
		try {	
			stmtInfo = new StatementInfo(dbname_,
					null,
					false,
					 con_,
					 needtransaction_);
			stmtInfo.init();

			// ��ʼ�����������ݿ�ִ�����
			batchStmt = stmtInfo.createStatement();
			boolean autokey = isAutoprimarykey(dbname_);
			for(int i = 0; i < batchSQLS.size(); i ++)
			{
				String sql = (String)batchSQLS.get(i);
				/**
				 * must be removed.
				 */
				sql = DBUtil.getInterceptorInf(dbname_).convertSQL(sql, DBUtil.getDBAdapter(dbname_).getDBTYPE(), dbname_);
				if(autokey)
				{
					Object[] objs = StatementParser.refactorInsertStatement(
							stmtInfo.getCon(), sql, stmtInfo.getDbname());
					/**
					 * ret[0] ���insert��� ret[1] ����µ�����ֵ ret[2] ���±�tableinfo�в�����Ӧ����ֵ���
					 * ret[3] PrimaryKey����
					 */
		
					// �������ô�����sql���
					sql = (String) objs[0];
					if(showsql(stmtInfo.getDbname()))
					{
						log.debug("Execute JDBC batch statement:"+sql);
					}
//					log.info("Add Batch Statement:" + sql);
		
					// ִ��statement���������������
		
					batchStmt.addBatch(sql);
					// ���sqlΪinsert��䲢�����µ�����ֵ���ɣ��򱣴������ֵ
					if (objs[1] != null ) {
						batchResult.add(objs[1]);
					}
					if (objs[2] != null) {
						batchUpdateSqls.add(objs[2]);
					}
				}
				else
				{
//					sql = (String) objs[0];
//					log.info("Add Batch Statement:" + sql);
					if(showsql(stmtInfo.getDbname()))
					{
						log.debug("Execute JDBC batch statement:"+sql);
					}
					// ִ��statement���������������		
					batchStmt.addBatch(sql);
				}
			}	
			
			
			int[] ret = batchStmt.executeBatch();
			// batchUpdateSqls
			if(autokey)
			{
				if (batchUpdateSqls.size() > 0)
					updateTableInfo(batchUpdateSqls,stmtInfo.getCon());
				// ���ִ�е������ݿ��������������Զ��������ݿ����������򷵻����еĲ������������
	
				if (batchResult != null && batchResult.size() > 0) {
					ret_keys = new Object[batchResult.size()];
					for (int i = 0; i < batchResult.size(); i++)
						ret_keys[i] = batchResult.get(i);
					// System.arraycopy(null,0,null,1,0);
				}
			}
			else
			{
				if(ret != null )
				{
					ret_keys = new Object[ret.length];
					for(int i = 0;  i < ret.length; i ++)
					{
						ret_keys[i] = new Integer(ret[i]);
					}
				}
			}

			// ������ֶ��ύ���ݿ�����ģʽ�����еĲ�����ɺ����batchCon�ύ�����ύ����

			stmtInfo.commit();
		} 
		catch(java.sql.BatchUpdateException e)
		{
			try{
				
				log.error("success batch update statements:" + e.getUpdateCounts(),e);
			}
			catch(Exception ei)
			{
				
			}
			
			if(stmtInfo != null)
				stmtInfo.errorHandle(e);
			
			throw e;
		}
		catch (SQLException e) {
			try{
				
				log.error(e.getMessage(),e);
			}
			catch(Exception ei)
			{
				
			}
			
			if(stmtInfo != null)
				stmtInfo.errorHandle(e);
			
			throw e;
		
			// return ret;
		} catch (Exception e) {
			try{
				
				log.error(e.getMessage(),e);
			}
			catch(Exception ei)
			{
				
			}
			if(stmtInfo != null)
				stmtInfo.errorHandle(e);			
//			log.error(e.getMessage(),e);
			throw new NestedSQLException(e.getMessage(),e);
			
					
			
		} finally {
			if(stmtInfo != null)
				stmtInfo.dofinally();
			stmtInfo = null;
			// ����������ִ�б�ʶ
			if(batchSQLS != null)
				batchSQLS.clear();
			if (batchResult != null) {
				batchResult.clear();
				batchResult = null;
				batchUpdateSqls.clear();
				batchUpdateSqls = null;
			}

			setBatchDBName(SQLManager.getInstance().getDefaultDBName());
		}
		return ret_keys;
	}
	
	/**
	 * ���ض������ݿ��������ִ�����ݿ����������
	 * @param dbname
	 * @param con
	 * @return
	 * @throws Exception
	 */
	public Object[] executeBatch(String dbname,Connection con ) throws SQLException {
		try
		{
			return this.executeBatch(batchSQLS, dbname == null?batchDBName :dbname, con, !isBatchAutoCommit());
		}
		finally
		{
			if(this.batchSQLS != null)
				this.batchSQLS.clear();
			this.batchautocommit = false;
			this.autocommit = true;
			setBatchDBName(SQLManager.getInstance().getDefaultDBName());
		}
	}
	
	

	/**
	 * @return Returns the batchDBName.
	 */
	public String getBatchDBName() {
		return batchDBName;
	}

	/**
	 * @param batchDBName
	 *            The batchDBName to set.
	 */
	public void setBatchDBName(String batchDBName) {
		this.batchDBName = batchDBName;
	}

	// ======================================================================
	// Methods for accessing results by column index
	// ======================================================================

	/**
	 * Retrieves the value of the designated column in the current row of this
	 * <code>ResultSet</code> object as a <code>boolean</code> in the Java
	 * programming language.
	 * 
	 * @param rowid
	 *            the first row is 0, the second is 1
	 * @param columnIndex
	 *            the first column is 0, the second is 1, ...
	 * @return the column value; if the value is SQL <code>NULL</code>, the
	 *         value returned is <code>false</code>
	 * @exception SQLException
	 *                if a database access error occurs
	 */
	public boolean getBoolean(int rowid, int columnIndex) throws SQLException {
		inrange(rowid, columnIndex);
		return allResults[getTrueRowid(rowid)].getBoolean(columnIndex);
//		Object value = this.getObject(rowid, columnIndex);
//		
//		if (value != null)
//			return Boolean.getBoolean(value.toString());
//		else
//			return false;
	}

	/**
	 * Retrieves the value of the designated column in the current row of this
	 * <code>ResultSet</code> object as a <code>byte</code> in the Java
	 * programming language.
	 * 
	 * @param rowid
	 *            the first row is 0, the second is 1
	 * @param columnIndex
	 *            the first column is 0, the second is 1, ...
	 * @return the column value; if the value is SQL <code>NULL</code>, the
	 *         value returned is <code>0</code>
	 * @exception SQLException
	 *                if a database access error occurs
	 */
	public byte getByte(int rowid, int columnIndex) throws SQLException {
//		Object value = this.getObject(rowid, columnIndex);
//		try
//		{
//			if(value != null)
//			{
//				Byte byte_ = (Byte)value;
//				return byte_.byteValue();
//			}
//			else
//				return 1;
//		}
//		catch(Exception e)
//		{
//			try
//			{
//				if (value != null)
//					return Byte.parseByte(value.toString());
//				else
//					return 1;
//			}
//			catch(Exception ie)
//			{
//				throw new NestedSQLException(ie);
//				
//			}
////			throw new NestedSQLException(e); 
//		}
		
		inrange(rowid, columnIndex);
		return allResults[getTrueRowid(rowid)].getByte(columnIndex);
	}

	/**
	 * Retrieves the value of the designated column in the current row of this
	 * <code>ResultSet</code> object as a <code>short</code> in the Java
	 * programming language.
	 * 
	 * @param rowid
	 *            the first row is 0, the second is 1
	 * @param columnIndex
	 *            the first column is 0, the second is 1, ...
	 * @return the column value; if the value is SQL <code>NULL</code>, the
	 *         value returned is <code>0</code>
	 * @exception SQLException
	 *                if a database access error occurs
	 */
	public short getShort(int rowid, int columnIndex) throws SQLException {
//		Object value = this.getObject(rowid, columnIndex);
//		if (value != null)
//			return Short.parseShort(value.toString());
//		else
//			return 0;
		inrange(rowid, columnIndex);
		return allResults[getTrueRowid(rowid)].getShort(columnIndex);
	}

	/**
	 * Retrieves the value of the designated column in the current row of this
	 * <code>ResultSet</code> object as a <code>java.sql.Time</code> object
	 * in the Java programming language.
	 * 
	 * @param rowid
	 *            the first row is 0, the second is 1
	 * @param columnIndex
	 *            the first column is 0, the second is 1, ...
	 * @return the column value; if the value is SQL <code>NULL</code>, the
	 *         value returned is <code>null</code>
	 * @exception SQLException
	 *                if a database access error occurs
	 */
	public java.sql.Time getTime(int rowid, int columnIndex)
			throws SQLException {
		inrange(rowid, columnIndex);
		return allResults[getTrueRowid(rowid)].getTime(columnIndex);
		
//		return (java.sql.Time) this.getObject(rowid, columnIndex);

	}

	/**
	 * Retrieves the value of the designated column in the current row of this
	 * <code>ResultSet</code> object as a <code>java.sql.Timestamp</code>
	 * object in the Java programming language.
	 * 
	 * @param rowid
	 *            the first row is 0, the second is 1
	 * @param columnIndex
	 *            the first column is 0, the second is 1, ...
	 * @return the column value; if the value is SQL <code>NULL</code>, the
	 *         value returned is <code>null</code>
	 * @exception SQLException
	 *                if a database access error occurs
	 */
	public java.sql.Timestamp getTimestamp(int rowid, int columnIndex)
			throws SQLException {
//		return (java.sql.Timestamp) this.getObject(rowid, columnIndex);
		inrange(rowid, columnIndex);
		return allResults[getTrueRowid(rowid)].getTimestamp(columnIndex);
	}

	/**
	 * Retrieves the value of the designated column in the current row of this
	 * <code>ResultSet</code> object as a stream of ASCII characters. The
	 * value can then be read in chunks from the stream. This method is
	 * particularly suitable for retrieving large <char>LONGVARCHAR</char>
	 * values. The JDBC driver will do any necessary conversion from the
	 * database format into ASCII.
	 * 
	 * <P>
	 * <B>Note:</B> All the data in the returned stream must be read prior to
	 * getting the value of any other column. The next call to a getter method
	 * implicitly closes the stream. Also, a stream may return <code>0</code>
	 * when the method <code>InputStream.available</code> is called whether
	 * there is data available or not.
	 * 
	 * @param rowid
	 *            the first row is 0, the second is 1
	 * @param columnIndex
	 *            the first column is 0, the second is 1, ...
	 * @return a Java input stream that delivers the database column value as a
	 *         stream of one-byte ASCII characters; if the value is SQL
	 *         <code>NULL</code>, the value returned is <code>null</code>
	 * @exception SQLException
	 *                if a database access error occurs
	 */
	public java.io.InputStream getAsciiStream(int rowid, int columnIndex)
			throws SQLException {
		inrange(rowid, columnIndex);
		return allResults[getTrueRowid(rowid)].getAsciiStream(columnIndex);
//		return (java.io.InputStream) getObject(rowid, columnIndex);
	}

	/**
	 * Retrieves the value of the designated column in the current row of this
	 * <code>ResultSet</code> object as as a stream of two-byte Unicode
	 * characters. The first byte is the high byte; the second byte is the low
	 * byte.
	 * 
	 * The value can then be read in chunks from the stream. This method is
	 * particularly suitable for retrieving large <code>LONGVARCHAR</code>values.
	 * The JDBC driver will do any necessary conversion from the database format
	 * into Unicode.
	 * 
	 * <P>
	 * <B>Note:</B> All the data in the returned stream must be read prior to
	 * getting the value of any other column. The next call to a getter method
	 * implicitly closes the stream. Also, a stream may return <code>0</code>
	 * when the method <code>InputStream.available</code> is called, whether
	 * there is data available or not.
	 * 
	 * @param rowid
	 *            the first row is 0, the second is 1
	 * @param columnIndex
	 *            the first column is 0, the second is 1, ...
	 * @return a Java input stream that delivers the database column value as a
	 *         stream of two-byte Unicode characters; if the value is SQL
	 *         <code>NULL</code>, the value returned is <code>null</code>
	 * 
	 * @exception SQLException
	 *                if a database access error occurs
	 * @deprecated use <code>getCharacterStream</code> in place of
	 *             <code>getUnicodeStream</code>
	 */
	public java.io.InputStream getUnicodeStream(int rowid, int columnIndex)
			throws SQLException {
		inrange(rowid, columnIndex);
		return allResults[getTrueRowid(rowid)].getUnicodeStream(columnIndex);
//		return (java.io.InputStream) this.getObject(rowid, columnIndex);
	}

	/**
	 * Retrieves the value of the designated column in the current row of this
	 * <code>ResultSet</code> object as a binary stream of uninterpreted
	 * bytes. The value can then be read in chunks from the stream. This method
	 * is particularly suitable for retrieving large <code>LONGVARBINARY</code>
	 * values.
	 * 
	 * <P>
	 * <B>Note:</B> All the data in the returned stream must be read prior to
	 * getting the value of any other column. The next call to a getter method
	 * implicitly closes the stream. Also, a stream may return <code>0</code>
	 * when the method <code>InputStream.available</code> is called whether
	 * there is data available or not.
	 * 
	 * @param rowid
	 *            the first row is 0, the second is 1
	 * @param columnIndex
	 *            the first column is 0, the second is 1, ...
	 * @return a Java input stream that delivers the database column value as a
	 *         stream of uninterpreted bytes; if the value is SQL
	 *         <code>NULL</code>, the value returned is <code>null</code>
	 * @exception SQLException
	 *                if a database access error occurs
	 */
	public java.io.InputStream getBinaryStream(int rowid, int columnIndex)
			throws SQLException {
		inrange(rowid, columnIndex);
		return allResults[getTrueRowid(rowid)].getBinaryStream(columnIndex);
	}

	// ======================================================================
	// Methods for accessing results by column name
	// ======================================================================

	/**
	 * Retrieves the value of the designated column in the current row of this
	 * <code>ResultSet</code> object as a <code>boolean</code> in the Java
	 * programming language.
	 * 
	 * @param rowid
	 *            the first row is 0, the second is 1
	 * @param colName
	 *            the SQL name of the column
	 * @return the column value; if the value is SQL <code>NULL</code>, the
	 *         value returned is <code>false</code>
	 * @exception SQLException
	 *                if a database access error occurs
	 */
	public boolean getBoolean(int rowid, String colName) throws SQLException {
//		Object value = this.getObject(rowid, colName);
//		if (value != null)
//			return Boolean.getBoolean(value.toString());
//		return false;
		inrange(rowid, colName);
		return allResults[getTrueRowid(rowid)].getBoolean(colName);

	}
	
	

	/**
	 * Retrieves the value of the designated column in the current row of this
	 * <code>ResultSet</code> object as a <code>byte</code> in the Java
	 * programming language.
	 * 
	 * @param rowid
	 *            the first row is 0, the second is 1
	 * @param colName
	 *            the SQL name of the column
	 * @return the column value; if the value is SQL <code>NULL</code>, the
	 *         value returned is <code>0</code>
	 * @exception SQLException
	 *                if a database access error occurs
	 */
	public byte getByte(int rowid, String colName) throws SQLException {
		inrange(rowid, colName);
		return allResults[getTrueRowid(rowid)].getByte(colName);
	}

	/**
	 * Retrieves the value of the designated column in the current row of this
	 * <code>ResultSet</code> object as a <code>short</code> in the Java
	 * programming language.
	 * 
	 * @param rowid
	 *            start with 0
	 * @param colName
	 *            the SQL name of the column
	 * @return the column value; if the value is SQL <code>NULL</code>, the
	 *         value returned is <code>0</code>
	 * @exception SQLException
	 *                if a database access error occurs
	 */
	public short getShort(int rowid, String colName) throws SQLException {
		inrange(rowid, colName);
		return allResults[getTrueRowid(rowid)].getShort(colName);
	}

	/**
	 * Retrieves the value of the designated column in the current row of this
	 * <code>ResultSet</code> object as a <code>java.sql.Time</code> object
	 * in the Java programming language.
	 * 
	 * @param rowid
	 *            start with 0
	 * @param colName
	 *            the SQL name of the column
	 * @return the column value; if the value is SQL <code>NULL</code>, the
	 *         value returned is <code>null</code>
	 * @exception SQLException
	 *                if a database access error occurs
	 */
	public java.sql.Time getTime(int rowid, String colName) throws SQLException {
		inrange(rowid, colName);
		return allResults[getTrueRowid(rowid)].getTime(colName);
	}

	/**
	 * Retrieves the value of the designated column in the current row of this
	 * <code>ResultSet</code> object as a <code>java.sql.Timestamp</code>
	 * object.
	 * 
	 * @param rowid
	 *            start with 0
	 * @param colName
	 *            the SQL name of the column
	 * @return the column value; if the value is SQL <code>NULL</code>, the
	 *         value returned is <code>null</code>
	 * @exception SQLException
	 *                if a database access error occurs
	 */
	public java.sql.Timestamp getTimestamp(int rowid, String colName)
			throws SQLException {
//		return (java.sql.Timestamp) this.getObject(rowid, colName);
		inrange(rowid, colName);
		return allResults[getTrueRowid(rowid)].getTimestamp(colName);
	}

	/**
	 * Retrieves the value of the designated column in the current row of this
	 * <code>ResultSet</code> object as a stream of ASCII characters. The
	 * value can then be read in chunks from the stream. This method is
	 * particularly suitable for retrieving large <code>LONGVARCHAR</code>
	 * values. The JDBC driver will do any necessary conversion from the
	 * database format into ASCII.
	 * 
	 * <P>
	 * <B>Note:</B> All the data in the returned stream must be read prior to
	 * getting the value of any other column. The next call to a getter method
	 * implicitly closes the stream. Also, a stream may return <code>0</code>
	 * when the method <code>available</code> is called whether there is data
	 * available or not.
	 * 
	 * @param rowid
	 *            start with 0
	 * @param colName
	 *            the SQL name of the column
	 * @return a Java input stream that delivers the database column value as a
	 *         stream of one-byte ASCII characters. If the value is SQL
	 *         <code>NULL</code>, the value returned is <code>null</code>.
	 * @exception SQLException
	 *                if a database access error occurs
	 */
	public java.io.InputStream getAsciiStream(int rowid, String colName)
			throws SQLException {
		inrange(rowid, colName);
		return allResults[getTrueRowid(rowid)].getAsciiStream(colName);
//		return (java.io.InputStream) this.getObject(rowid, colName);
	}

	/**
	 * Retrieves the value of the designated column in the current row of this
	 * <code>ResultSet</code> object as a stream of two-byte Unicode
	 * characters. The first byte is the high byte; the second byte is the low
	 * byte.
	 * 
	 * The value can then be read in chunks from the stream. This method is
	 * particularly suitable for retrieving large <code>LONGVARCHAR</code>
	 * values. The JDBC technology-enabled driver will do any necessary
	 * conversion from the database format into Unicode.
	 * 
	 * <P>
	 * <B>Note:</B> All the data in the returned stream must be read prior to
	 * getting the value of any other column. The next call to a getter method
	 * implicitly closes the stream. Also, a stream may return <code>0</code>
	 * when the method <code>InputStream.available</code> is called, whether
	 * there is data available or not.
	 * 
	 * @param rowid
	 *            start with 0
	 * @param colName
	 *            the SQL name of the column
	 * @return a Java input stream that delivers the database column value as a
	 *         stream of two-byte Unicode characters. If the value is SQL
	 *         <code>NULL</code>, the value returned is <code>null</code>.
	 * @exception SQLException
	 *                if a database access error occurs
	 * @deprecated use <code>getCharacterStream</code> instead
	 */
	public java.io.InputStream getUnicodeStream(int rowid, String colName)
			throws SQLException {
		inrange(rowid, colName);
		return allResults[getTrueRowid(rowid)].getUnicodeStream(colName);
	}

	/**
	 * Retrieves the value of the designated column in the current row of this
	 * <code>ResultSet</code> object as a stream of uninterpreted
	 * <code>byte</code>s. The value can then be read in chunks from the
	 * stream. This method is particularly suitable for retrieving large
	 * <code>LONGVARBINARY</code> values.
	 * 
	 * <P>
	 * <B>Note:</B> All the data in the returned stream must be read prior to
	 * getting the value of any other column. The next call to a getter method
	 * implicitly closes the stream. Also, a stream may return <code>0</code>
	 * when the method <code>available</code> is called whether there is data
	 * available or not.
	 * 
	 * @param rowid
	 *            the first row is 0, the second is 1
	 * @param colName
	 *            the SQL name of the column
	 * @return a Java input stream that delivers the database column value as a
	 *         stream of uninterpreted bytes; if the value is SQL
	 *         <code>NULL</code>, the result is <code>null</code>
	 * @exception SQLException
	 *                if a database access error occurs
	 */
	public java.io.InputStream getBinaryStream(int rowid, String colName)
			throws SQLException {
//		return (java.io.InputStream) this.getObject(rowid, colName);
		inrange(rowid, colName);
		return allResults[getTrueRowid(rowid)].getBinaryStream(colName);
	}

	// --------------------------JDBC 2.0-----------------------------------

	// ---------------------------------------------------------------------
	// Getters and Setters
	// ---------------------------------------------------------------------

	/**
	 * Retrieves the value of the designated column in the current row of this
	 * <code>ResultSet</code> object as a <code>java.io.Reader</code>
	 * object.
	 * 
	 * @return a <code>java.io.Reader</code> object that contains the column
	 *         value; if the value is SQL <code>NULL</code>, the value
	 *         returned is <code>null</code> in the Java programming language.
	 * @param rowid
	 *            start with 0
	 * @param columnIndex
	 *            the first column is 0, the second is 1, ...
	 * @exception SQLException
	 *                if a database access error occurs
	 * @since 1.2
	 */
	public java.io.Reader getCharacterStream(int rowid, int columnIndex)
			throws SQLException {
//		Object[] value = (Object[]) this.getObject(rowid, columnIndex);
//		Reader reader = (java.io.Reader) value[1];
//		return reader;
		inrange(rowid, columnIndex);
		return allResults[getTrueRowid(rowid)].getCharacterStream(columnIndex);
		
	}

	/**
	 * Retrieves the value of the designated column in the current row of this
	 * <code>ResultSet</code> object as a <code>java.io.Reader</code>
	 * object.
	 * 
	 * @param rowid
	 *            start with 0
	 * @param colName
	 *            the name of the column
	 * @return a <code>java.io.Reader</code> object that contains the column
	 *         value; if the value is SQL <code>NULL</code>, the value
	 *         returned is <code>null</code> in the Java programming language
	 * @exception SQLException
	 *                if a database access error occurs
	 * @since 1.2
	 */
	public java.io.Reader getCharacterStream(int rowid, String colName)
			throws SQLException {
		inrange(rowid, colName);
		return allResults[getTrueRowid(rowid)].getCharacterStream(colName);
	}

	/**
	 * Retrieves the value of the designated column in the current row of this
	 * <code>ResultSet</code> object as a <code>java.math.BigDecimal</code>
	 * with full precision.
	 * 
	 * @param rowid
	 *            start with 0
	 * @param columnIndex
	 *            the first column is 0, the second is 1, ...
	 * @return the column value (full precision); if the value is SQL
	 *         <code>NULL</code>, the value returned is <code>null</code>
	 *         in the Java programming language.
	 * @exception SQLException
	 *                if a database access error occurs
	 * @since 1.2
	 */
	public BigDecimal getBigDecimal(int rowid, int columnIndex)
			throws SQLException {
		return (BigDecimal) this.getObject(rowid, columnIndex);
	}

	/**
	 * Retrieves the value of the designated column in the current row of this
	 * <code>ResultSet</code> object as a <code>java.math.BigDecimal</code>
	 * with full precision.
	 * 
	 * @param rowid
	 *            start with 0
	 * @param colName
	 *            the column name
	 * @return the column value (full precision); if the value is SQL
	 *         <code>NULL</code>, the value returned is <code>null</code>
	 *         in the Java programming language.
	 * @exception SQLException
	 *                if a database access error occurs
	 * @since 1.2
	 * 
	 */
	public BigDecimal getBigDecimal(int rowid, String colName)
			throws SQLException {
		return (BigDecimal) this.getObject(rowid, colName);
	}

	/**
	 * Retrieves the value of the designated column in the current row of this
	 * <code>ResultSet</code> object as a <code>byte[]</code> object in the
	 * Java programming language.
	 * 
	 * @param rowid
	 *            the first row is 0, the second is 1
	 * @param columnIndex
	 *            the first column is 0, the second is 1, ...
	 * @return a <code>byte[]</code> object representing the SQL
	 *         <code>byte[]</code> value in the specified column
	 * @exception SQLException
	 *                if a database access error occurs
	 * @since 1.2
	 */
	public Blob getBlob(int rowid, int columnIndex) throws SQLException {
		return (Blob) this.getObject(rowid, columnIndex);
	}

	/**
	 * Retrieves the value of the designated column in the current row of this
	 * <code>ResultSet</code> object as a <code>Clob</code> object in the
	 * Java programming language.
	 * 
	 * @param rowid
	 *            the first row is 0, the second is 1
	 * @param columnIndex
	 *            the first column is 0, the second is 1, ...
	 * @return a <code>Clob</code> object representing the SQL
	 *         <code>CLOB</code> value in the specified column
	 * @exception SQLException
	 *                if a database access error occurs
	 * @since 1.2
	 */
	public Clob getClob(int rowid, int columnIndex) throws SQLException {
		inrange(rowid, columnIndex);
		return allResults[getTrueRowid(rowid)].getClob(columnIndex);
//		return (Clob) this.getObject(rowid, columnIndex);
	}

	/**
	 * Retrieves the value of the designated column in the current row of this
	 * <code>ResultSet</code> object as an <code>Array</code> object in the
	 * Java programming language.
	 * 
	 * @param rowid
	 *            the first row is 0,the second is 1
	 * @param column
	 *            the first column is 0, the second is 1, ...
	 * @return an <code>Array</code> object representing the SQL
	 *         <code>ARRAY</code> value in the specified column
	 * @exception SQLException
	 *                if a database access error occurs
	 * @since 1.2
	 */
	public Array getArray(int rowid, int column) throws SQLException {
		return (Array) this.getObject(rowid, column);
	}

	/**
	 * Retrieves the value of the designated column in the current row of this
	 * <code>ResultSet</code> object as a <code>Ref</code> object in the
	 * Java programming language.
	 * 
	 * @param rowid
	 *            the first row is 0, the second is 1
	 * @param colName
	 *            the column name
	 * @return a <code>Ref</code> object representing the SQL <code>REF</code>
	 *         value in the specified column
	 * @exception SQLException
	 *                if a database access error occurs
	 * @since 1.2
	 */
	public Ref getRef(int rowid, String colName) throws SQLException {
		return (Ref) this.getObject(rowid, colName);
	}

	/**
	 * Retrieves the value of the designated column in the current row of this
	 * <code>ResultSet</code> object as a <code>Blob</code> object in the
	 * Java programming language.
	 * 
	 * @param rowid
	 *            the first row is 0, the second is 1
	 * @param colName
	 *            the name of the column from which to retrieve the value
	 * @return a <code>byte[]</code> object representing the SQL
	 *         <code>byte[]</code> value in the specified column
	 * @exception SQLException
	 *                if a database access error occurs
	 * @since 1.2
	 */
	public Blob getBlob(int rowid, String colName) throws SQLException {
		return (Blob) this.getObject(rowid, colName);
	}

	/**
	 * Retrieves the value of the designated column in the current row of this
	 * <code>ResultSet</code> object as a <code>Clob</code> object in the
	 * Java programming language.
	 * 
	 * @param rowid
	 *            the first row is 0, the second is 1
	 * @param colName
	 *            the name of the column from which to retrieve the value
	 * @return a <code>Clob</code> object representing the SQL
	 *         <code>CLOB</code> value in the specified column
	 * @exception SQLException
	 *                if a database access error occurs
	 * @since 1.2
	 */
	public Clob getClob(int rowid, String colName) throws SQLException {
		inrange(rowid, colName);
		return allResults[getTrueRowid(rowid)].getClob(colName);
	}

	/**
	 * Retrieves the value of the designated column in the current row of this
	 * <code>ResultSet</code> object as an <code>Array</code> object in the
	 * Java programming language.
	 * 
	 * @param rowid
	 *            the first row is 0, the second is 1
	 * @param colName
	 *            the name of the column from which to retrieve the value
	 * @return an <code>Array</code> object representing the SQL
	 *         <code>ARRAY</code> value in the specified column
	 * @exception SQLException
	 *                if a database access error occurs
	 * @since 1.2
	 */
	public Array getArray(int rowid, String colName) throws SQLException {
		return (Array) this.getObject(rowid, colName);
	}

	/**
	 * Retrieves the value of the designated column in the current row of this
	 * <code>ResultSet</code> object as a <code>java.net.URL</code> object
	 * in the Java programming language.
	 * 
	 * @param rowid
	 *            the first row is 0, the second is 1
	 * @param columnIndex
	 *            the index of the column 0 is the first, 1 is the second,...
	 * @return the column value as a <code>java.net.URL</code> object; if the
	 *         value is SQL <code>NULL</code>, the value returned is
	 *         <code>null</code> in the Java programming language
	 * @exception SQLException
	 *                if a database access error occurs, or if a URL is
	 *                malformed
	 * @since 1.4
	 */
	public java.net.URL getURL(int rowid, int columnIndex) throws SQLException {
//		return (java.net.URL) this.getObject(rowid, columnIndex);
		inrange(rowid, columnIndex);
		return allResults[getTrueRowid(rowid)].getURL(columnIndex);
	}

	/**
	 * Retrieves the value of the designated column in the current row of this
	 * <code>ResultSet</code> object as a <code>java.net.URL</code> object
	 * in the Java programming language.
	 * 
	 * @param rowid
	 *            the first row is 0, the second is 1
	 * @param colName
	 *            the SQL name of the column
	 * @return the column value as a <code>java.net.URL</code> object; if the
	 *         value is SQL <code>NULL</code>, the value returned is
	 *         <code>null</code> in the Java programming language
	 * @exception SQLException
	 *                if a database access error occurs or if a URL is malformed
	 * @since 1.4
	 */
	public java.net.URL getURL(int rowid, String colName) throws SQLException {
//		return (java.net.URL) this.getObject(rowid, colName);
		inrange(rowid, colName);
		return allResults[getTrueRowid(rowid)].getURL(colName);
	}

	/**
	 * Returns the bytes from a result set
	 * 
	 * @param res
	 *            The ResultSet to read from
	 * @param columnName
	 *            The name of the column to read from
	 * 
	 * @return The byte value from the column
	 */
	protected byte[] getBytesFromResultset(ResultSet res, String columnName)
			throws SQLException {
		// read the bytes from an oracle blob
		try
		{
			initOracleDB();
			return super.oracleDB.getBytesFromResultset(res, columnName);
		}
		catch (Exception e)
		{
			throw new NestedSQLException(e);
		}
	}

	/**
	 * Returns the bytes from a result set
	 * 
	 * @param res
	 *            The ResultSet to read from
	 * @param columnName
	 *            The name of the column to read from
	 * 
	 * @return The byte value from the column
	 */
	protected byte[] getBytesFromBlob(Blob blob) throws SQLException {
		try
		{
			initOracleDB();
			return super.oracleDB.getBytesFromBlob(blob);
		}
		catch (Exception e)
		{
			throw new NestedSQLException(e);
		}
	}

	/**
	 * Returns the bytes from a result set
	 * 
	 * @param res
	 *            The ResultSet to read from
	 * @param columnName
	 *            The name of the column to read from
	 * 
	 * @return The byte value from the column
	 */
	protected byte[] getBytesFromClob(Clob clob) {
		try
		{
			initOracleDB();
			return super.oracleDB.getBytesFromClob(clob);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Returns the bytes from a result set
	 * 
	 * @param res
	 *            The ResultSet to read from
	 * @param columnName
	 *            The name of the column to read from
	 * 
	 * @return The byte value from the column
	 */
	protected String getStringFromClob(Clob clob) {
		try
		{
			initOracleDB();
			return super.oracleDB.getStringFromClob( clob);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * ��ȡ���ݿ�����һ������ֵ
	 * 
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public static long getNextPrimaryKey(String tableName) throws SQLException {
		return getNextPrimaryKey(SQLManager.getInstance().getDefaultDBName(),
				tableName);
	}

	/**
	 * ��ȡ���ݿ�����һ������ֵ
	 * 
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public static long getNextPrimaryKey(Connection con, String tableName)
			throws SQLException {
		return getNextPrimaryKey(con, SQLManager.getInstance()
				.getDefaultDBName(), tableName);
	}

	/**
	 * ��ȡ���ݿ�����һ������ֵ
	 * 
	 * @param tableName
	 * @return
	 */
	public static long getNextPrimaryKey(String dbName, String tableName)
			throws SQLException {
		return getNextPrimaryKey(null, dbName, tableName);
	}

	/**
	 * ��ȡ���ݿ�����һ������ֵ
	 * 
	 * @param tableName
	 * @return
	 */
	public static long getNextPrimaryKey(Connection con, String dbName,
			String tableName) throws SQLException {
		try {
			
			PrimaryKey primaryKey = PrimaryKeyCacheManager.getInstance()
					.getPrimaryKeyCache(dbName).getIDTable(
							tableName.toLowerCase());
			long ret = ((Long) primaryKey.generateObjectKey(con).getPrimaryKey())
					.longValue();
			//primaryKey.updateTableinfo(con);
			return ret;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NestedSQLException("Get Next long PrimaryKey error for dbName="
					+ dbName + ",tableName=" + tableName + ":" + e.getMessage(),e);	
		} catch (Exception e) {
			e.printStackTrace();
			throw new NestedSQLException("Get Next long PrimaryKey error for dbName="
					+ dbName + ",tableName=" + tableName + ":" + e.getMessage(),e);
		}
	}

	/**
	 * ��ȡ���ݿ�����һ������ֵ
	 * 
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public static String getNextStringPrimaryKey(String tableName)
			throws SQLException {
		return getNextStringPrimaryKey(null, SQLManager.getInstance()
				.getDefaultDBName(), tableName);
	}

	/**
	 * ��ȡ���ݿ�����һ������ֵ
	 * 
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	public static String getNextStringPrimaryKey(Connection con,
			String tableName) throws SQLException {
		return getNextStringPrimaryKey(con, SQLManager.getInstance()
				.getDefaultDBName(), tableName);
	}

	/**
	 * ��ȡ���ݿ�����һ������ֵ
	 * 
	 * @param tableName
	 * @return
	 */
	public static String getNextStringPrimaryKey(String dbName, String tableName)
			throws SQLException {
		return getNextStringPrimaryKey(null, dbName, tableName);
	}

	/**
	 * ��ȡ���ݿ�����һ������ֵ
	 * 
	 * @param tableName
	 * @return
	 */
	public static String getNextStringPrimaryKey(Connection con, String dbName,
			String tableName) throws SQLException {
		try {
			PrimaryKey primaryKey = PrimaryKeyCacheManager.getInstance()
					.getPrimaryKeyCache(dbName).getIDTable(
							tableName.toLowerCase());
			String key = primaryKey.generateObjectKey(con).getPrimaryKey()
					.toString();
			//primaryKey.updateTableinfo(con);
			return key;
		} catch (Exception e) {
			e.printStackTrace();
			throw new NestedSQLException(
					"Get Next String PrimaryKey error for dbName=" + dbName
							+ ",tableName=" + tableName+ ":" + e.getMessage(),e);
		}
	}
	
	/**
	 * ֱ�ӻ�ȡ���е����ݿ��ѯ������������װ���ɸ���ҳ�б��ǩչʾ
	 * @return
	 */
	public Record[] getAllResults()
	{
		return this.allResults;
	}
	
	/*****************************************************************************************
	 * for object
	 */
	public Object executeSelectForObject(String sql,Class objectType) throws SQLException
	{
		return executeSelectForObject(sql,objectType,(Connection)null);
	}
	
	/*****************************************************************************************
	 * for object
	 */
	public Object[] executeSelectForObjectArray(String sql,Class objectType) throws SQLException
	{
		return executeSelectForObjectArray(sql,objectType,(Connection)null);
	}
	
	
	/*****************************************************************************************
	 * for object
	 */
	public Object[] executeSelectForObjectArray(String dbname,String sql,Class objectType) throws SQLException
	{
		return executeSelectForObjectArray(dbname,sql,objectType,(Connection)null,(RowHandler)null);
	}
	
	/*****************************************************************************************
	 * for object
	 */
	public Object[] executeSelectForObjectArray(String sql,Class objectType,Connection con) throws SQLException
	{
		return executeSelectForObjectArray(sql,objectType,con,null);
	}
	
	/*****************************************************************************************
	 * for object
	 */
	public Object[] executeSelectForObjectArray(String sql,Class objectType,Connection con,RowHandler rowhandler) throws SQLException
	{
		return executeSelectForObjectArray(null,sql,objectType,con,rowhandler);
	}
	
	
	public Object[] executeSelectForObjectArray(String dbname,String sql,
												Class objectType,Connection con,
												RowHandler rowhandler) throws SQLException
	{
		
		StatementInfo stmtInfo = null;
		try{
			stmtInfo = new StatementInfo(dbname, new NewSQLInfo(sql), false,con,false);
			ResultMap resultMap = innerExecuteJDBC(stmtInfo,
					objectType,rowhandler,ResultMap.type_objectarray);
			return (Object[])resultMap.getCommonresult();
		}
		finally
		{
			if(stmtInfo != null)
			{
				stmtInfo.dofinally();
				stmtInfo = null;
			}
		}
	}
	
	
	/*****************************************************************************************
	 * for object array pagine
	 */
	public Object[] executeSelectForObjectArray(String sql,long offset,int pagesize,Class objectType) throws SQLException
	{
		return executeSelectForObjectArray(sql,offset,pagesize,objectType,(Connection)null);
	}
	
	public Object[] executeSelectForObjectArray(String dbname,String sql,long offset,int pagesize,Class objectType) throws SQLException
	{
		return executeSelectForObjectArray(dbname,sql, offset, pagesize, objectType,null,null);
//		return executeSelectForObjectArray(dbname,sql,offset,pagesize,objectType,(Connection)null);
	}
	
	public Object[] executeSelectForObjectArray(String dbname,String sql,long offset,int pagesize,Class objectType,Connection con) throws SQLException
	{
		return executeSelectForObjectArray(dbname,sql, offset, pagesize, objectType,con,null);
	}
	
	/*****************************************************************************************
	 * for object
	 */
	public Object[] executeSelectForObjectArray(String sql,long offset,int pagesize,Class objectType,Connection con) throws SQLException
	{
		return executeSelectForObjectArray(sql,offset,pagesize,objectType,con,null);
	}
	
	/*****************************************************************************************
	 * for object 
	 */
	public Object[] executeSelectForObjectArray(String sql,long offset,int pagesize,Class objectType,Connection con,RowHandler rowhandler) throws SQLException
	{
		return executeSelectForObjectArray((String)null,sql,offset,pagesize,objectType,con,rowhandler);
	}
	
	
	public Object[] executeSelectForObjectArray(String dbname,String sql,long offset,int pagesize,Class objectType,Connection con,RowHandler rowhandler) throws SQLException
	{
		StatementInfo stmtInfo = new StatementInfo(dbname, new NewSQLInfo(sql),
				// Connection con,
						false, offset, pagesize, isRobotQuery(dbname), con,oraclerownum);
		ResultMap resultMap = innerExecutePagineJDBC(stmtInfo, objectType, rowhandler, ResultMap.type_objectarray);
		return (Object[])resultMap.getCommonresult();
	}
	
	
	
	
	
	public Object executeSelectForObject(String sql,Class objectType, Connection con)  throws SQLException
	{
		return executeSelectForObject(sql,objectType,con,(RowHandler)null);
	}
	
	public void executeSelectWithRowHandler(String sql,Connection con,NullRowHandler rowhandler)  throws SQLException
    {
        executeSelectWithRowhandler(null, sql, con,rowhandler) ;
    }
	
	public void executeSelectWithRowHandler(String sql,NullRowHandler rowhandler)  throws SQLException
    {
	    executeSelectWithRowhandler(null, sql, null,rowhandler) ;
    }
	
	
	public void executeSelectWithRowHandler(String dbname,String sql,NullRowHandler rowhandler)  throws SQLException
    {
	    executeSelectWithRowhandler(dbname, sql, null,rowhandler) ;
    }
	
	public void executeSelectWithRowhandler(String dbname, String sql, Connection con,NullRowHandler rowhandler) throws SQLException
    {
        StatementInfo stmtInfo = null;
        try
        {
            stmtInfo = new StatementInfo(dbname, new NewSQLInfo(sql), false,con,false);
            ResultMap resultMap = innerExecuteJDBC(stmtInfo,
                    null,rowhandler,ResultMap.type_null);
//            return (List)resultMap.getCommonresult();
        }
        finally
        {
            if(stmtInfo != null)
            {
                stmtInfo.dofinally();
                stmtInfo = null;
            }
        }
    }
	
	public void executeSelectWithRowHandler(String sql,long offset,int pagesize,Connection con,NullRowHandler rowhandler)  throws SQLException
    {
        executeSelectWithRowhandler(null, sql, offset, pagesize, con,rowhandler) ;
    }
	public void executeSelectWithRowHandler(String sql,long offset,int pagesize,NullRowHandler rowhandler)  throws SQLException
    {
	    executeSelectWithRowhandler(null, sql, offset, pagesize, null,rowhandler) ;
    }
	public void executeSelectWithRowHandler(String dbname,String sql,long offset,int pagesize,NullRowHandler rowhandler)  throws SQLException
    {
	    executeSelectWithRowhandler(dbname, sql,  offset, pagesize,null,rowhandler) ;
    }
	
	public void executeSelectWithRowhandler(String dbname, String sql,long offset,int pagesize, Connection con,NullRowHandler rowhandler) throws SQLException
    {
		StatementInfo stmtInfo = new StatementInfo(dbname, new NewSQLInfo(sql),
					
							false, offset, pagesize, true, con,oraclerownum);
			this.innerExecutePagineJDBC(stmtInfo, null, rowhandler, ResultMap.type_null);
		
    }
	
	
	
//	executeSelect(String, int)
//	executeSelectForList(String, long, int)
	public Object executeSelectForObject(String dbname, String sql,Class objectType)  throws SQLException
	{
		return executeSelectForObject( dbname,  sql, objectType,(RowHandler)null);
	}
	
	public Object executeSelectForObject(String dbname, String sql, Connection con,Class objectType) throws SQLException
	{
		return executeSelectForObject( dbname,  sql,  con, objectType,(RowHandler)null);
	}
	
	/***************************************************************************
	 * 	for list
	 ***************************************************************************/
	
	public List executeSelectForList(String sql,Class objectType) throws SQLException
	{
		return executeSelectForList( sql, objectType, (Connection )null);
	}
	
	public List executeSelectForList(String sql,Class objectType, Connection con)  throws SQLException
	{
		return executeSelectForList( sql, objectType, con,(RowHandler)null);
	}
	
	
	
//	executeSelect(String, int)
//	executeSelectForList(String, long, int)
	public List executeSelectForList(String dbname, String sql,Class objectType)  throws SQLException
	{
		return executeSelectForList( dbname,  sql, (Connection)null,objectType);
	}
	
	public List executeSelectForList(String dbname, String sql, Connection con,Class objectType) throws SQLException
	{
		return executeSelectForList( dbname,  sql, con,objectType,(RowHandler)null);
	}
	
	
	/***************************************************************************
	 * 	for xml
	 ***************************************************************************/
	


	
	
	
//	executeSelect(String, int)
//	executeSelectForList(String, long, int)

	

	
	/*********************************************************************************************
	 * ��ҳforList
	 ********************************************************/
	
//	executeSelect(String, String, int)
	public List executeSelectForList(String sql, long offset, int pagesize,Class objectType) throws SQLException
	{
		return executeSelectForList((String )null, sql, offset,  pagesize, objectType);
	}
	public List executeSelectForList(String dbname, String sql,long offset, int pagesize,Class objectType) throws SQLException
	{
		return executeSelectForList( dbname,  sql,  offset,  pagesize, isRobotQuery(dbname) , objectType);
	}
	public List executeSelectForList(String dbname, String sql, long offset, int pagesize, boolean robotquery,Class objectType) throws SQLException
	{
		return executeSelectForList( dbname,  sql,  offset,  pagesize, robotquery , (Connection)null,objectType);
	}
	public List executeSelectForList(String dbname, String sql,  long offset, int pagesize, boolean robotquery,Connection con,Class objectType) throws SQLException
	{
		return executeSelectForList( dbname,  sql,   offset,  pagesize,  robotquery, con, objectType,(RowHandler)null);
	}
	public List executeSelectForList(String dbname, String sql, long offset, int pagesize,Connection con,Class objectType) throws SQLException
	{
		return executeSelectForList( dbname,  sql,  offset,  pagesize, isRobotQuery(dbname) , con,objectType);
	}
	public List executeSelectForOracleList(String sql, long offset, int pagesize, String oraclerownum,Class objectType) throws SQLException
	{
		return executeSelectForOracleList((String )null,  sql,  offset,  pagesize,  oraclerownum, objectType) ;
	}
	public List executeSelectForOracleList(String dbname, String sql, long offset, int pagesize, String oraclerownum,Class objectType) throws SQLException
	{
		return executeSelectForOracleList( dbname,  sql,  offset,  pagesize,  oraclerownum,(Connection )null,objectType) ;
	}
	public List executeSelectForOracleList(String dbname, String sql, long offset, int pagesize, String oraclerownum,Connection con,Class objectType) throws SQLException
	{
		return executeSelectForOracleList( dbname,  sql,  offset,  pagesize,  oraclerownum, con, objectType,(RowHandler)null);
	}
	
	
	/*********************************************************************************************
	 * ��ҳfor xml
	 ********************************************************/
	







	/*****************************************************************
	 * with rowhandler
	 ****************************************************************/
	/*****************************************************************************************
	 * for object
	 */
	public Object executeSelectForObject(String sql,Class objectType,RowHandler rowhandler) throws SQLException
	{
		return executeSelectForObject((String)null, sql,objectType,rowhandler) ;
	}
	
	public Object executeSelectForObject(String sql,Class objectType, Connection con,RowHandler rowhandler)  throws SQLException
	{
		return executeSelectForObject( (String)null,sql,con, objectType,   rowhandler)  ;
	}
	
	
	
//	executeSelect(String, int)
//	executeSelectForList(String, long, int)
	public Object executeSelectForObject(String dbname, String sql,Class objectType,RowHandler rowhandler)  throws SQLException
	{
		return executeSelectForObject( dbname,  sql,(Connection)null, objectType, rowhandler);
	}
	
	public Object executeSelectForObject(String dbname, String sql, 
										 Connection con,Class objectType,
										 RowHandler rowhandler) throws SQLException
	{
		StatementInfo stmtInfo = null;
		try
		{
			stmtInfo = new StatementInfo(dbname, new NewSQLInfo(sql), false,con,false);
			ResultMap resultMap = innerExecuteJDBC(stmtInfo,
					objectType,rowhandler,ResultMap.type_objcet);
			return (Object)resultMap.getCommonresult();
		}
		finally
		{
			if(stmtInfo != null)
			{
				stmtInfo.dofinally();
				stmtInfo = null;
			}
		}
	}
	
	/***************************************************************************
	 * 	for list
	 ***************************************************************************/
	
	public List executeSelectForList(String sql,Class objectType,RowHandler rowhandler) throws SQLException
	{
		return executeSelectForList( sql, objectType,(Connection)null, rowhandler);
	}
	
	public List executeSelectForList(String sql,Class objectType, Connection con,RowHandler rowhandler)  throws SQLException
	{
		return executeSelectForList( (String)null,sql,   con, objectType,rowhandler);
	}
	
	
	
//	executeSelect(String, int)
//	executeSelectForList(String, long, int)
	public List executeSelectForList(String dbname, String sql,Class objectType,RowHandler rowhandler)  throws SQLException
	{
		return executeSelectForList( dbname,  sql, (Connection )null,objectType, rowhandler) ;
	}
	
	public List executeSelectForList(String dbname, String sql, Connection con,Class objectType,RowHandler rowhandler) throws SQLException
	{
		StatementInfo stmtInfo = null;
		try
		{
			stmtInfo = new StatementInfo(dbname, new NewSQLInfo(sql), false,con,false);
			ResultMap resultMap = innerExecuteJDBC(stmtInfo,
					objectType,rowhandler,ResultMap.type_list);
			return (List)resultMap.getCommonresult();
		}
		finally
		{
			if(stmtInfo != null)
			{
				stmtInfo.dofinally();
				stmtInfo = null;
			}
		}
	}
	
	
	
	
	/***************************************************************************
	 * 	for xml
	 ***************************************************************************/
	

	
	
	

	public String executeSelectForXML(String dbname, String sql, Connection con,RowHandler rowhandler) throws SQLException
	{
		StatementInfo stmtInfo = null;
		try{
			stmtInfo = new StatementInfo(dbname, new NewSQLInfo(sql), false,con,false);
			ResultMap resultMap = innerExecuteJDBC(stmtInfo,
					XMLMark.class,rowhandler,ResultMap.type_xml);
			return (String)resultMap.getCommonresult();
		}
		finally
		{
			if(stmtInfo != null)
			{
				stmtInfo.dofinally();
				stmtInfo = null;
			}
		}
	}
	
	public String executeSelectForXML( String sql, Connection con,RowHandler rowhandler) throws SQLException
	{
		return executeSelectForXML(null, sql, con,rowhandler);
	}
	
	public String executeSelectForXML( String sql, RowHandler rowhandler) throws SQLException
	{
		return executeSelectForXML(null, sql, null,rowhandler);
	}
	
	public String executeSelectForXML( String sql) throws SQLException
	{
		return executeSelectForXML(null, sql, null,null);
	}
	
	public String executeSelectForXML( String dbname,String sql) throws SQLException
	{
		return executeSelectForXML(dbname, sql, null,null);
	}
	
	public String executeSelectForXML( String dbname,String sql,Connection con) throws SQLException
	{
		return executeSelectForXML(dbname, sql, con,null);
	}
	
	
//	---------------------
	public String executeSelectForXML(String dbname,String sql,long offset,int pagesize,  Connection con,RowHandler rowhandler) throws SQLException
	{
		return executeSelectForXML( dbname, sql, offset, pagesize,  isRobotQuery(dbname) ,con, rowhandler);
	}
	
	public String executeSelectForXML( String sql,long offset,int pagesize,  Connection con,RowHandler rowhandler) throws SQLException
	{
		return executeSelectForXML(null, sql,offset,pagesize, con,rowhandler);
	}
	
	public String executeSelectForXML( String sql,long offset,int pagesize,  RowHandler rowhandler) throws SQLException
	{
		return executeSelectForXML(null, sql,offset,pagesize, null,rowhandler);
	}
	
	public String executeSelectForXML( String sql,long offset,int pagesize) throws SQLException
	{
		return executeSelectForXML(null, sql,offset,pagesize, null,null);
	}
	
	public String executeSelectForXML( String dbname,String sql,long offset,int pagesize) throws SQLException
	{
		return executeSelectForXML(dbname, sql,offset,pagesize, null,null);
	}
	
	public String executeSelectForXML( String dbname,String sql,long offset,int pagesize,Connection con) throws SQLException
	{
		return executeSelectForXML(dbname, sql,offset,pagesize, con,null);
	}
	
//	-----------------------
	public String executeSelectForXML(String dbname,String sql,long offset,int pagesize,boolean robotquery , Connection con,RowHandler rowhandler) throws SQLException
	{
		StatementInfo stmtInfo = new StatementInfo(dbname, new NewSQLInfo(sql),
				// Connection con,
						false, offset, pagesize, robotquery, con,oraclerownum);
		ResultMap resultMap = innerExecutePagineJDBC(stmtInfo, XMLMark.class, rowhandler, ResultMap.type_xml);
		return (String)resultMap.getCommonresult();
	}
	
	public String executeSelectForXML( String sql,long offset,int pagesize,boolean robotquery,  Connection con,RowHandler rowhandler) throws SQLException
	{
		return executeSelectForXML(null, sql,offset,pagesize, robotquery,con,rowhandler);
	}
	
	public String executeSelectForXML( String sql,long offset,int pagesize,boolean robotquery,  RowHandler rowhandler) throws SQLException
	{
		return executeSelectForXML(null, sql,offset,pagesize, robotquery,null,rowhandler);
	}
	
	public String executeSelectForXML( String sql,long offset,int pagesize,boolean robotquery) throws SQLException
	{
		return executeSelectForXML(null, sql,offset,pagesize,robotquery,null,null);
	}
	
	public String executeSelectForXML( String dbname,String sql,long offset,int pagesize,boolean robotquery) throws SQLException
	{
		return executeSelectForXML(dbname, sql,offset,pagesize, robotquery,null,null);
	}
	
	public String executeSelectForXML( String dbname,String sql,long offset,int pagesize,boolean robotquery,Connection con) throws SQLException
	{
		return executeSelectForXML(dbname, sql,offset,pagesize, robotquery,con,null);
	}
	
	
	/*********************************************************************************************
	 * ��ҳforList with handler
	 ********************************************************/
	
//	executeSelect(String, String, int)
	public List executeSelectForList(String sql, long offset, int pagesize,Class objectType,RowHandler rowhandler) throws SQLException
	{
		return executeSelectForList((String )null, sql, offset,  pagesize, objectType, rowhandler) ;
	}
	public List executeSelectForList(String dbname, String sql,long offset, int pagesize,Class objectType,RowHandler rowhandler) throws SQLException
	{
		return executeSelectForList(dbname, sql, offset,  pagesize, isRobotQuery(dbname),objectType, rowhandler) ;
	}
	public List executeSelectForList(String dbname, String sql, long offset, int pagesize, boolean robotquery,Class objectType,RowHandler rowhandler) throws SQLException
	{
		return executeSelectForList(dbname, sql, offset,  pagesize, isRobotQuery(dbname),(Connection)null,objectType, rowhandler) ;
	}
	public List executeSelectForList(String dbname, String sql,  long offset, int pagesize, boolean robotquery,Connection con,Class objectType,RowHandler rowhandler) throws SQLException
	{
		StatementInfo stmtInfo = new StatementInfo(dbname, new NewSQLInfo(sql),
				// Connection con,
						false, offset, pagesize, robotquery, con,oraclerownum);
		ResultMap resultMap = this.innerExecutePagineJDBC(stmtInfo, objectType, rowhandler, ResultMap.type_list);
		return (List)resultMap.getCommonresult();
	}
	public List executeSelectForList(String dbname, String sql, long offset, int pagesize,Connection con,Class objectType,RowHandler rowhandler) throws SQLException
	{
		return executeSelectForList(dbname, sql, offset,  pagesize, isRobotQuery(dbname),con,objectType, rowhandler) ;
	}
	public List executeSelectForOracleList(String sql, long offset, int pagesize, String oraclerownum,Class objectType,RowHandler rowhandler) throws SQLException
	{
		return executeSelectForOracleList((String)null,sql,  offset,  pagesize,  oraclerownum, objectType, rowhandler) ;
	}
	public List executeSelectForOracleList(String dbname, String sql, long offset, int pagesize, String oraclerownum,Class objectType,RowHandler rowhandler) throws SQLException
	{
		return executeSelectForOracleList( dbname,  sql,  offset,  pagesize,  oraclerownum,(Connection)null, objectType, rowhandler) ;
	}
	public List executeSelectForOracleList(String dbname, String sql, long offset, int pagesize, String oraclerownum,Connection con,Class objectType,RowHandler rowhandler) throws SQLException
	{
		StatementInfo stmtInfo = new StatementInfo(dbname, new NewSQLInfo(sql),
				// Connection con,
						false, offset, pagesize, isRobotQuery(dbname), con,oraclerownum);
		ResultMap resultMap = this.innerExecutePagineJDBC(stmtInfo, objectType, rowhandler, ResultMap.type_list);
		return (List)resultMap.getCommonresult();
	}
	
	
	/*********************************************************************************************
	 * ��ҳfor xml
	 ********************************************************/
	
	public Serializable getSerializable(int rowid,String fieldname) throws SQLException
	{
	    inrange(rowid, fieldname);
            return allResults[getTrueRowid(rowid)].getSerializable(fieldname);
	}
	
	public Serializable getSerializable(int rowid,int columnIndex)throws SQLException
        {
	    inrange(rowid, columnIndex);
            return allResults[getTrueRowid(rowid)].getSerializable(columnIndex);
        }

	public void addBatch(List<String> batchsqls) {
		this.batchSQLS = batchsqls;
		
	}




	


}
