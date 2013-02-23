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
 *  distributed under the License is distributed on an "AS IS" bboss persistent,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.  
 */
package com.frameworkset.common.poolman.management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.frameworkset.common.poolman.DBUtil;
import com.frameworkset.common.poolman.sql.ColumnMetaData;
import com.frameworkset.common.poolman.sql.PrimaryKey;
import com.frameworkset.common.poolman.sql.PrimaryKeyCache;
import com.frameworkset.common.poolman.sql.PrimaryKeyCacheManager;
import com.frameworkset.common.poolman.util.SQLManager;
import com.frameworkset.orm.adapter.DB;
import com.frameworkset.orm.transaction.JDBCTransaction;
import com.frameworkset.orm.transaction.TransactionManager;

/**
 * @author biaoping.yin created on 2005-3-30 TODO To change the template for
 *         this generated type comment go to Window - Preferences - Java - Code
 *         Style - Code Templates
 */
public abstract class BaseTableManager {
	static PrimaryKeyCacheManager manager = PrimaryKeyCacheManager.getInstance();

	private static Logger log = Logger.getLogger(BaseTableManager.class);

	// ��ѯ�����Ϣsql
	public static final String queryTableInfoSql = "select * from tableinfo";

	public static final String queryTableInfoSqlBytableName = "select * from tableinfo where table_name=?";

	// ��ȡ�������ֵsql
	public static final String queryPrimaryKey = "select max(?) from ?";

	// ����tableinfo�б����������ֵsql
	public static final String updateTableInfoSql = "update tableinfo set table_id_value=? where upper(table_name)=?";
	private static final Object token = new Object();
	public static PrimaryKeyCache getPoolTableInfos(String poolName)
			throws Exception {
		Connection con = null;
		Statement stmt = null;
		ResultSet rs1 = null;
		Statement q_pstmt = null;
		PreparedStatement u_pstmt = null;

		ResultSet rs = null;
		Map trace = new HashMap();
		
		try {
			PrimaryKeyCache keyCache = new PrimaryKeyCache(poolName);
			con = SQLManager.getInstance().requestConnection(poolName);
			log.info("load pool[" + poolName + "] tables information.......");
			stmt = con.createStatement();
			try {
				rs = stmt.executeQuery(queryTableInfoSql);
			} catch (Exception e1) {
				log.info("Ignore load table infomation !Check table [tableinfo] in your database! ");
				return null;
			}
			DB dbAdapter = SQLManager.getInstance().getDBAdapter(poolName);
			
			q_pstmt = con.createStatement();
			
			u_pstmt = con.prepareStatement(updateTableInfoSql);
			
			// �������ݿ����ӳص����������
			
			while (rs.next()) {
				String table_name = rs.getString("table_name").trim();
				if(trace.containsKey(table_name.toLowerCase()))
				{
					log.info("���棺tableinfo���д��ڶ�����" + table_name + "����������Ϣ,ֻ�ܱ���һ����¼�������Ӱ����ñ���ص�ҵ��Ĵ���.") ;
				}
				else
				{
					trace.put(table_name.toLowerCase(), token);
				}
				log.info("    load table[" + table_name
						+ "] information.......");
//				System.out.println("    load table[" + table_name
//						+ "] information.......");
				String table_id_name = rs.getString("table_id_name").trim();
				int table_id_increment = rs.getInt("table_id_increment");
				long table_id_value = rs.getLong("table_id_value");
				String type = rs.getString("TABLE_ID_TYPE");
				String metaType = "int";
				if(type == null || type.equals(""))
				{
					type = "int";
				}
				if(type .equals("sequence") )
				{
					ColumnMetaData cd = DBUtil.getColumnMetaData(table_name,table_id_name,con);
					if(cd != null)
					{
						int type_ = cd.getDataType();
						metaType = PrimaryKey.getJavaType(type_);
					}
					else
					{
						metaType = "int";
					}
				}
				else if(type .equals("uuid") )
				{
					ColumnMetaData cd = DBUtil.getColumnMetaData(table_name,table_id_name,con);
					if(cd != null)
					{
						int type_ = cd.getDataType();
						metaType = PrimaryKey.getJavaType(type_);
					}
					else
					{
						metaType = "string";
					}
				}
				else 
				{
					metaType = type;
				}
				String prefix = rs.getString("table_id_prefix");
				String TABLE_ID_GENERATOR = rs.getString("TABLE_ID_GENERATOR");
				if (prefix == null)
					prefix = "";
				
				
				// Connection con1 =
				// SQLManager.getInstance().requestConnection(poolName);

				try {
					// to_number(logoff_time-logon_time);
					// SUBSTR('Message',1,4);
					/**
					 * SUBSTR(table_id_name,LENGTH(table_id_prefix))
					 */

					// System.out.println("table_name:" + table_name);
					String maxSql = dbAdapter.getIDMAXSql(table_name,
							table_id_name, prefix, metaType);
					if(!type .equals("uuid") && !type .equals("sequence")  )
					{
						
						rs1 = q_pstmt.executeQuery(maxSql);
						long new_table_id_value = 0l;
						
	
						if (rs1.next()) {
	
							new_table_id_value = rs1.getLong(1);// PrimaryKey.parserSequence(rs1.getString(1),prefix,type,table_name);
						}
	
						// ���tableinfo���б�����ֵ����ʵ������ֵ�����ʱ������tableinfo�б������ֵ
						if (new_table_id_value > table_id_value || new_table_id_value < table_id_value) {
							
							u_pstmt.setString(2, table_name.toUpperCase());
							u_pstmt.setLong(1, new_table_id_value);
							u_pstmt.execute();
							table_id_value = new_table_id_value;
						}
					}
					PrimaryKey primaryKey = new PrimaryKey(poolName, table_name
							.toLowerCase(), table_id_name.toLowerCase(),
							table_id_increment, table_id_value, type,
							prefix,maxSql,TABLE_ID_GENERATOR,con);
					keyCache.addIDTable(primaryKey);
				} catch (Exception e) {
					// e.printStackTrace();
					// ��ȡ���������Ϣʧ��ʱ�Զ���������������Ϣ��ʼ�� 
					log.error("    load table[" + table_name
							+ "] failed!\r\nerror message:\r\n"
							+ e.getMessage(),e);
					continue;
				}
			}
			return keyCache;
		} catch (Exception e) {
			log.info("Load table infomation failed! " + e.getMessage()  + ". Please check table [tableinfo] in your database! ");
			return null;
		}

		finally {
			trace.clear();
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				;
			}
			try {

				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
				;
			}
			try {

				if (rs1 != null)
					rs1.close();
			} catch (Exception e) {
				;
			}
			try {

				if (q_pstmt != null)
					q_pstmt.close();
			} catch (Exception e) {
				;
			}
			try {

				if (u_pstmt != null)
					u_pstmt.close();
			} catch (Exception e) {
				;
			}
			try {

				if (con != null) {
					con.close();
				}
			} catch (Exception e) {
				;
			}

		}

	}

	/**
	 * ������������Ϣ������ʱͨ�������������ݿ���ʵʱ���ر��������Ϣ
	 * 
	 * @param poolName
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public static PrimaryKey getPoolTableInfo(String poolName, String tableName)
			throws Exception {
		return getPoolTableInfo( poolName,null,  tableName);
	}
	
	
	/**
	 * ������������Ϣ������ʱͨ�������������ݿ���ʵʱ���ر��������Ϣ
	 * 
	 * @param poolName
	 * @param Connection con �ⲿ�����ϵͳ����
	 * @param tableName
	 * @return
	 * @throws Exception
	 * added by biaoping.yin on 2008.05.29
	 */
	public static PrimaryKey getPoolTableInfo(String poolName,Connection con, String tableName)
			throws Exception {
//		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs1 = null;
		Statement q_pstmt = null;
		PreparedStatement u_pstmt = null;
		//�����Ƿ�ʹ���ⲿ���ӱ�ʶ����������conΪ��ʱ����Ҫ�ı�outcon��ֵΪfalse
		boolean outcon = true;

		ResultSet rs = null;
		JDBCTransaction tx = null;
		log.debug("load pool[" + poolName + "] tables information.......");
		try {
			if(con == null)
			{
				tx = TransactionManager.getTransaction();
				//�жϵ�ǰ�Ĳ����Ƿ������񻷾��У������ʹ�����񻷾������� 
				if(tx != null)
				{
					con = tx.getConnection(poolName);
				}
				else
				{
					con = SQLManager.getInstance().requestConnection(poolName);
				}
				outcon = false;
			}

			q_pstmt = con.createStatement();
			u_pstmt = con.prepareStatement(updateTableInfoSql);
			stmt = con.prepareStatement(queryTableInfoSqlBytableName);
			stmt.setString(1, tableName);
			rs = stmt.executeQuery();
			DB dbAdapter = SQLManager.getInstance().getDBAdapter(poolName);
			// �������ݿ����ӳص����������

			if (rs.next()) {
				String table_name = rs.getString("table_name").trim();
				log.info("    load table[" + table_name
						+ "] information.......");
				String table_id_name = rs.getString("table_id_name").trim();
				int table_id_increment = rs.getInt("table_id_increment");
				long table_id_value = rs.getLong("table_id_value");
				String type = rs.getString("table_id_type");
				String prefix = rs.getString("table_id_prefix");
				String TABLE_ID_GENERATOR = rs.getString("TABLE_ID_GENERATOR");
				String metaType = "int";
				if(type == null || type.equals(""))
				{
					type = "int";
				}
				if(type.equals("sequence"))
				{
					ColumnMetaData cd = DBUtil.getColumnMetaData(table_name,table_id_name,con);
					if(cd != null)
					{
						int type_ = cd.getDataType();
						metaType = PrimaryKey.getJavaType(type_);
					}else
					{
						metaType = "int";
					}
				}
				else if(type.equals("uuid"))
				{
					ColumnMetaData cd = DBUtil.getColumnMetaData(table_name,table_id_name,con);
					if(cd != null)
					{
						int type_ = cd.getDataType();
						metaType = PrimaryKey.getJavaType(type_);
					}else
					{
						metaType = "string";
					}
				}
				else 
				{
					metaType = type;
				}
//				if(!type.equals("sequence"))
				{
				

					// Connection con1 =
					// SQLManager.getInstance().requestConnection(poolName);
					String maxSql = dbAdapter.getIDMAXSql(table_name,
							table_id_name, prefix, metaType);
					
					try {
						if(!type .equals("uuid") && !type .equals("sequence") )
						{
							rs1 = q_pstmt.executeQuery(maxSql);
							long new_table_id_value = 0;
							if (rs1.next()) {
								new_table_id_value = rs1.getLong(1);// PrimaryKey.parserSequence(rs1.getString(1),prefix,type,table_name);
							}
		
							// ���tableinfo���б�����ֵ����ʵ������ֵ�����ʱ������tableinfo�б������ֵ
							if (new_table_id_value > table_id_value || new_table_id_value < table_id_value) {
								u_pstmt.setString(2, table_name.toUpperCase());
								u_pstmt.setLong(1, new_table_id_value);
								u_pstmt.execute();
								table_id_value = new_table_id_value;
							}
						}
						PrimaryKey primaryKey = new PrimaryKey(poolName, table_name
								.toLowerCase(), table_id_name.toLowerCase(),
								table_id_increment, table_id_value, type,
								prefix,maxSql,TABLE_ID_GENERATOR,con);
						return primaryKey;
					} catch (Exception e) {
						// e.printStackTrace();
						// ��ȡ���������Ϣʧ��ʱ�Զ���������������Ϣ��ʼ��
						log.error("    load table[" + table_name
								+ "] failed:"
								+ e.getMessage(),e);
	
					}
				}
//				else
//				{
//					PrimaryKey primaryKey = new PrimaryKey(poolName, table_name
//							.toLowerCase(), table_id_name.toLowerCase(),
//							table_id_increment, table_id_value, type,
//							prefix,"select " + TABLE_ID_GENERATOR + ".nextval from dual");
//					return primaryKey;
//				}
			}
		} catch (Exception e) {
//			throw e;
		    

		} finally {
			try {
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				;
			}
			try {
				if (stmt != null)
					stmt.close();
			} catch (Exception e) {
				;
			}
			try {
				if (rs1 != null)
					rs1.close();
			} catch (Exception e) {
				;
			}
			try {
				if (q_pstmt != null)
					q_pstmt.close();
			} catch (Exception e) {
				;
			}
			try {
				if (u_pstmt != null)
					u_pstmt.close();
			} catch (Exception e) {
				;
			}

			try {
				if (con != null ) {
					if(tx == null && !outcon)
					{
						con.close();
					}
				}
				
			} catch (Exception e) {
				;

			}
			con = null;

		}

		return null;
	}

	public void initTableInfo() throws Exception {
		log.debug("initial tableinfo start.......");
		// ��ȡ���е����ݿ����ӳص�����
		java.util.List<String> poolNames = SQLManager.getInstance()
				.getAllPoolNamesIfExist();

		// ͬ�����ݿ����ñ���tableinfo�е���Ӧ����Ϣ������ֵ��

		// ͬ����ʼ
		for(int i = 0; i < poolNames.size(); i ++) {
			String poolName = poolNames.get(i);
			// PrimaryKeyCache keyCache = new PrimaryKeyCache(poolName);
			try {
				String _poolname = SQLManager.getRealDBNameFromExternalDBName(poolName);
				if(manager.getPrimaryKeyCache(_poolname) == null)
				{
					PrimaryKeyCache keyCache = getPoolTableInfos(_poolname);
					
					if(keyCache != null)
					    manager.addPrimaryKeyCache(keyCache);
				}
			} catch (Exception e) {
				// ��ȡ���������Ϣʧ��ʱ�Զ���������������Ϣ��ʼ��
				log.info("load pool[" + poolName
						+ "] tables information failed!\r\nerror message:\r\n"
						,e);
				// e.printStackTrace();
				// continue;
				// throw e;
			}

		}

		log.debug("initial tableinfo complete!");
	}
	
	public static void initTableInfo(String poolName) throws Exception {
		log.debug("initial tableinfo start for ["+poolName+"].......");
		

		// ͬ�����ݿ����ñ���tableinfo�е���Ӧ����Ϣ������ֵ��

		// ͬ����ʼ
	
			try {
				String _poolname = SQLManager.getRealDBNameFromExternalDBNameIfExist(poolName);
				if(manager.getPrimaryKeyCache(_poolname) == null)
				{
					PrimaryKeyCache keyCache = getPoolTableInfos(_poolname);
					
					if(keyCache != null)
					    manager.addPrimaryKeyCache(keyCache);
				}
			} catch (Exception e) {
				// ��ȡ���������Ϣʧ��ʱ�Զ���������������Ϣ��ʼ��
				log.error("load pool[" + poolName
						+ "] tables information failed!\r\nerror message:\r\n"
						+ e.getMessage(),e);
				// e.printStackTrace();
				// continue;
				// throw e;
			}

		

		log.debug("initial tableinfo complete!");
	}
	
	public static void removePrimaryKeyCache(String poolname)
	{
		if(manager != null)
		{
//			String poolname_ = SQLManager.getRealDBNameFromExternalDBName(poolname);
			manager.removePrimaryKeyCache(poolname);
		}
	}

	// ���»�������Ϣ�����ݿ���
	public void updateTableInfo() {

	}

}
