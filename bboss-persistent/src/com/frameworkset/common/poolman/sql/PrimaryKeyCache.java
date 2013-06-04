/*****************************************************************************
 *                                                                           *
 *  This file is part of the tna framework distribution.                     *
 *  Documentation and updates may be get from  biaoping.yin the author of    *
 *  this framework							     							 *
 *                                                                           *
 *  Sun Public License Notice:                                               *
 *                                                                           *
 *  The contents of this file are subject to the Sun Public License Version  *
 *  1.0 (the "License"); you may not use this file except in compliance with *
 *  the License. A copy of the License is available at http://www.sun.com    *
 *                                                                           *
 *  The Original Code is tag. The Initial Developer of the Original          *
 *  Code is biaoping yin. Portions created by biaoping yin are Copyright     *
 *  (C) 2000.  All Rights Reserved.                                          *
 *                                                                           *
 *  GNU Public License Notice:                                               *
 *                                                                           *
 *  Alternatively, the contents of this file may be used under the terms of  *
 *  the GNU Lesser General Public License (the "LGPL"), in which case the    *
 *  provisions of LGPL are applicable instead of those above. If you wish to *
 *  allow use of your version of this file only under the  terms of the LGPL *
 *  and not to allow others to use your version of this file under the SPL,  *
 *  indicate your decision by deleting the provisions above and replace      *
 *  them with the notice and other provisions required by the LGPL.  If you  *
 *  do not delete the provisions above, a recipient may use your version of  *
 *  this file under either the SPL or the LGPL.                              *
 *                                                                           *
 *  biaoping.yin (yin-bp@163.com)                                            *
 *                                                                           *
 *****************************************************************************/
package com.frameworkset.common.poolman.sql;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.frameworkset.common.poolman.management.BaseTableManager;

/**
 * �������ݿ��������Ϣ
 * 
 * @author biaoping.yin created on 2005-3-29 version 1.0
 */
public class PrimaryKeyCache {
	private static Logger log = Logger.getLogger(PrimaryKeyCache.class);

	// ���ݿ����ӳ�����
	private String dbname;

	// private static PrimaryKeyCache primaryKeyCache;
	private Map id_tables;
	
	/**
	 * û����tableinfo�д����������Ϣ�ı��������Ϣ��NULL_���滻
	 */
	private static final PrimaryKey NULL_ = new PrimaryKey();

	public PrimaryKeyCache(String dbname) {
		this.dbname = dbname;
		id_tables = new java.util.concurrent.ConcurrentHashMap(new HashMap());
	}

	// public static PrimaryKeyCache getInstance()
	// {
	// if(primaryKeyCache == null)
	// primaryKeyCache = new PrimaryKeyCache();
	// return primaryKeyCache;
	// }

	public void addIDTable(PrimaryKey primaryKey) {
		if (!id_tables.containsKey(primaryKey.getTableName()))
			id_tables.put(primaryKey.getTableName(), primaryKey);
	}
	
	public PrimaryKey getIDTable(String tableName) {
		return getIDTable(null,tableName);
		
		
		
	}
	
	public PrimaryKey getIDTable(Connection con,String tableName) {
		PrimaryKey key = (PrimaryKey) id_tables.get(tableName.toLowerCase());
		if (key != null)
		{
			if(key == NULL_)
				return null;
			return key;
		}
		else
		{
			key = loaderPrimaryKey(con,tableName);
			return key;
		}
		
		
		
	}

	/**
	 * @return Returns the dbname.
	 */
	public String getDbname() {
		return dbname;
	}
	
	/**
	 * ��̬���ӱ��������Ϣ��ϵͳ������
	 * @param tableName
	 * @return
	 */
	public PrimaryKey loaderPrimaryKey(String tableName) {
		return loaderPrimaryKey(null,tableName);
	}
	
	/**
	 * ��̬���ӱ��������Ϣ��ϵͳ������
	 * @param tableName
	 * @return
	 */
	public PrimaryKey loaderPrimaryKey(Connection con,String tableName) {
		try {
			
			log.debug("��ʼװ�ر�" + tableName +"����������Ϣ�����塣");
//			PrimaryKey key = this.getIDTable(tableName);
//			if(key != null)
//			{
//				System.out.println("��" + tableName +"����������Ϣ�Ѿ����ڣ�����װ�أ�");
//				return key;
//			}
			PrimaryKey key = BaseTableManager.getPoolTableInfo(dbname,con,
					tableName);
			if (key != null)
			{
				id_tables.put(key.getTableName().trim().toLowerCase(), key);
				log.debug("���װ�ر�" + tableName +"����������Ϣ��");
			}
			else
			{
				id_tables.put(tableName.trim().toLowerCase(),NULL_);
				log.debug("���װ�ر�" + tableName +"����������Ϣ,NULL_,");
			}
			
			return key;
		} catch (Exception ex) {
//			ex.printStackTrace();
			log.error(ex.getMessage(),ex);
		}
		return null;
	}

}
