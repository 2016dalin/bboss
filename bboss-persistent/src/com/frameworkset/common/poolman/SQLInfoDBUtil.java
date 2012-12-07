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

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.frameworkset.persitent.util.SQLInfo;

public class SQLInfoDBUtil extends PreparedDBUtil {
	
	
	


	private static Logger log = Logger.getLogger(SQLInfoDBUtil.class);
	
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
	public void preparedUpdate(String dbName, SQLInfo sql) throws SQLException {
		Params = this.buildParams();
		Params.action = UPDATE;
		
		preparedSql(Params,dbName, new NewSQLInfo(sql));
	}
	/**
	 * �����ض����ݿ�Ԥ����ɾ�����
	 * 
	 * @param dbName
	 * @param sql
	 * @throws SQLException
	 */
	public void preparedDelete(String dbName, SQLInfo sql) throws SQLException {
	    preparedDelete((Params)null,dbName, new NewSQLInfo(sql));
	}
	/**
	 * �����ض����ݿ��Ԥ����������
	 * 
	 * @param dbName
	 * @param sql
	 * @throws SQLException
	 */
	public void preparedInsert(String dbName, SQLInfo sql) throws SQLException {
	    preparedInsert((Params )null,dbName, sql);
		
	}
	
	/**
     * �����ض����ݿ��Ԥ����������
     * 
     * @param dbName
     * @param sql
     * @throws SQLException
     */
    public void preparedInsert(Params params,String dbName, SQLInfo sql) throws SQLException {
        if(params == null)
            Params = this.buildParams();
        else
            Params = params;
        Params.action = INSERT;
        preparedSql(Params,dbName, new NewSQLInfo(sql));
        
    }
	
	/**
	 * Ԥ�����ѯ����
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	public void preparedSelect(String prepareDBName, SQLInfo sql, long offset,
			int pagesize) throws SQLException {
		
		preparedSelect(prepareDBName, sql, offset, pagesize, oraclerownum,-1L);
	}
	
	/**
	 * ����Ԥ�����ѯ���
	 * @mark
	 * @param sql
	 * @throws SQLException
	 */
	public void preparedSelect(SQLInfo sql, long offset, int pagesize,long totalsize)
			throws SQLException {

		preparedSelect(prepareDBName, sql, offset, pagesize,totalsize);
	}
	/**
	 * Ԥ�����ѯ����
	 * @mark
	 * @param sql
	 * @throws SQLException
	 */
	public void preparedSelect(String prepareDBName, SQLInfo sql, long offset,
			int pagesize,long totalsize) throws SQLException {
		
		preparedSelect(prepareDBName, sql, offset, pagesize, oraclerownum,totalsize);
	}
	/**
	 * Ԥ�����ѯ����
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	public void preparedSelectWithTotalsizesql(String prepareDBName, SQLInfo sql, long offset,
			int pagesize,SQLInfo totalsizesql) throws SQLException {
		
		preparedSelectWithTotalsizesql(prepareDBName, sql, offset, pagesize, oraclerownum,totalsizesql);
	}
	/**
	 * ����Ԥ����������
	 * 
	 * @param sql
	 * @throws SQLException
	 */
	public void preparedSelect(String prepareDBName, SQLInfo sql)
			throws SQLException {
		Params = this.buildParams();
		preparedSelect(Params ,prepareDBName, new NewSQLInfo(sql));
	}

	/**
     * Ԥ�����ҳ��ѯ���� ���ⲿ�����ܼ�¼��
     * @mark
     * @param sql
     * @throws SQLException
     */
    public void preparedSelect(SQLParams params,String prepareDBName, SQLInfo sql, long offset,
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
    public void preparedSelectWithTotalsizesql(SQLParams params,String prepareDBName, SQLInfo sql, long offset,
            int pagesize,SQLInfo totalsizesql) throws SQLException {
    	if( params != null)
    	{
    		params.buildParams(sql,totalsizesql,prepareDBName);
    		preparedSelectWithTotalsizesql(params.getRealParams(),prepareDBName, params.getNewsql(), offset, pagesize);
    	}
    	else
    		preparedSelectWithTotalsizesql((Params)null,prepareDBName, new NewSQLInfo(sql,totalsizesql), offset, pagesize);
    }

	/**
     * ����Ԥ����������
     * 
     * @param sql
     * @throws SQLException
     */
    public void preparedSelect(SQLParams params,String prepareDBName, SQLInfo sql)
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


}
