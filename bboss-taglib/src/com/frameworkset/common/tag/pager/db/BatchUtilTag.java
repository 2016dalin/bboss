package com.frameworkset.common.tag.pager.db;

import java.sql.SQLException;

import javax.servlet.jsp.JspException;

import com.frameworkset.common.poolman.SQLExecutor;
import com.frameworkset.common.tag.BaseTag;

/**
 * 
 * <p>Title: BatchUtilTag.java</p> 
 * <p>Description: </p>
 * <p>bboss workgroup</p>
 * <p>Copyright (c) 2008</p>
 * @Date 2010-3-13
 * @author biaoping.yin
 * @version 1.0
 */
public class BatchUtilTag extends BaseTag {
	protected String dbname;
	protected String type=SQLExecutor.BATCH_PREPARED;
	protected SQLExecutor sqlExecutor;
	 
	/**
	 * �Ƿ���Ҫ��Ԥ��������������Ż��������Ҫ������е�batchparams����sql����Ƿ���ͬ��������
	 * ��������ͬ��ŵ�һ�������������ͬһ��sql�����ڶ��preparedstatement���
	 * Ĭ�ϲ����򣬷�������
	 */
	protected boolean batchOptimize = false;
	/**
	 * 
	 */
	private static final long serialVersionUID = 7725636392877287693L;

	@Override
	public int doEndTag() throws JspException {
		try {
			if(sqlExecutor != null)
				sqlExecutor.execute();
		} catch (SQLException e) {
			throw new JspException(e);
		}
		dbname = null;
		type=SQLExecutor.BATCH_PREPARED;
		
		sqlExecutor = null;
		batchOptimize = false;
		return EVAL_PAGE;
	}

	@Override
	public int doStartTag() throws JspException {
		sqlExecutor = new SQLExecutor();
		sqlExecutor.setBatchDBName(dbname);
		sqlExecutor.setBatchOptimize( batchOptimize);
		sqlExecutor.setBatchtype(type);
		return EVAL_BODY_INCLUDE;
	}

	public String getDbname() {
		return dbname;
	}

	public void setDbname(String dbname) {
		this.dbname = dbname;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public SQLExecutor getSQLExecutor() {
		
		return this.sqlExecutor;
	}

	public boolean isBatchOptimize() {
		return batchOptimize;
	}

	public void setBatchOptimize(boolean batchOptimize) {
		this.batchOptimize = batchOptimize;
	}
}
