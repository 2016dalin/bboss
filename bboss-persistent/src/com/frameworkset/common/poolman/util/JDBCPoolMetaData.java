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

import java.io.Serializable;

import com.frameworkset.common.poolman.PoolManConstants;

public class JDBCPoolMetaData implements Serializable{

	/* POOL ATTRIBUTES (set to default beforehand) */

    protected String poolname;
    
    transient JDBCPoolMetaData extenalInfo = null;

    private int initialObjects = PoolManConstants.DEFAULT_INITIAL_SIZE;
    private int minimumSize = PoolManConstants.DEFAULT_MIN_SIZE;
    private int maximumSize = PoolManConstants.DEFAULT_MAX_SIZE;
    private int objectTimeout = PoolManConstants.DEFAULT_TIMEOUT;
    private int userTimeout = PoolManConstants.DEFAULT_USERTIMEOUT;
    private int skimmerFrequency = PoolManConstants.DEFAULT_SKIMMER_SLEEP;
    private int shrinkBy = PoolManConstants.DEFAULT_SHRINKBY;
    private boolean emergencyCreates = PoolManConstants.DEFAULT_EMERGENCY_CREATES;
    private String maxWait = "30";//30��  
    
    private String databaseProductName;
    private String driverName;
    private String databaseProductVersion;
    private String driverVersion;
    
//    addDbMetaDataEntry(dbMetaData, "probe.jsp.dataSourceTest.dbMetaData.dbProdName", md.getDatabaseProductName());
//    addDbMetaDataEntry(dbMetaData, "probe.jsp.dataSourceTest.dbMetaData.dbProdVersion", md.getDatabaseProductVersion());
//    addDbMetaDataEntry(dbMetaData, "probe.jsp.dataSourceTest.dbMetaData.jdbcDriverName", md.getDriverName());
//    addDbMetaDataEntry(dbMetaData, "probe.jsp.dataSourceTest.dbMetaData.jdbcDriverVersion", md.getDriverVersion());
    
    private boolean usepool = true;
    private String logfile;
    private boolean debug = false;
    /* PHYSICAL CONNECTION ATTRIBUTES */
    private String driver;
    private String URL;
    private String username;
    private String password;

    private boolean nativeResults = false;

    /* POOL BEHAVIOR ATTRIBUTES */
    private String validationQuery;
    private String initialPoolSQL;
    private String initialConnectionSQL;
    private boolean external = PoolManConstants.EXTERNAL;
    
    private String externaljndiName;
    
    private boolean removeOnExceptions = PoolManConstants.DEFAULT_REMOVE_ON_EXC;
    private boolean poolingPreparedStatements = PoolManConstants.DEFAULT_POOL_PREP_STATEMENTS;
    private int maxOpenPreparedStatements = PoolManConstants.maxOpenPreparedStatements;
    

    /* TX ATTRIBUTES */
    private int transactionIsolationLevel = PoolManConstants.DEFAULT_ISO_LEVEL;
    private int transactionTimeout = PoolManConstants.DEFAULT_USERTIMEOUT;

    /* QUERY CACHE ATTRIBUTES */
    private boolean cacheEnabled = PoolManConstants.DEFAULT_CACHE_ENABLED;
    private int cacheSize = PoolManConstants.DEFAULT_CACHE_SIZE;
    private int cacheRefreshInterval = PoolManConstants.DEFAULT_CACHE_REFRESH;
    

    /* DATASOURCE ATTRIBUTES */
    private String JNDIName;
    private String interceptor = "com.frameworkset.common.poolman.interceptor.DummyInterceptor";
    
    private String jndiclass;
    private String jndiuser;
    private String jndipassword;
    public String getJndiclass() {
		return jndiclass;
	}

	public void setJndiclass(String jndiclass) {
		this.jndiclass = jndiclass;
	}

	private String jndiurl;
    /**
	 * @return the interceptor
	 */
	public String getInterceptor() {
		return interceptor;
	}

	/**
	 * @param interceptor the interceptor to set
	 */
	public void setInterceptor(String interceptor) {
		this.interceptor = interceptor;
	}

    /**
     * ϵͳ�ṩ��ȱʡ���ݿ����������ɻ��ƿ���ͨ�����ַ�ʽ�������ݿ�����
     * 1.auto:�Զ�����
     * 2.composite����ѯ����������
     */
    private String keygenerate = PoolManConstants.DEFAULT_KEY_GENERATE;
    

    /**
     * ���ݿ�����
     */
    private String dbtype = null;
    
    /**
     * �Ƿ����Ԫ����
     */
    private String loadmetadata = "false";
    
    /**
     * �Ƿ��Զ���������
     */
    private boolean autoprimarykey = false;
    private boolean showsql = false;
    /**
     * �Ƿ񻺳��ѯ�б�Ԫ����
     */
    private boolean cachequerymetadata = true;
    
    /**
     * ��ʶ��ҳ��ѯ�Ƿ�ʹ�ø�Ч��ѯ��ȱʡΪtrue
     * Ϊfalseʱ������ִ�и�Ч��ѯ
     */
    private boolean robotquery = true;
    
    /**
     * ���������Ӵ���ʱ���Ƿ�Կ������ӽ�����Ч�Լ����ƿ���
     * true-��飬����鵽����Ч����ʱ��ֱ��������Ч����
     * false-����飬ȱʡֵ
     */
    private boolean testWhileidle = false; 
    
	/**
	 * �����ӳ�ʱʱ�Ƿ��ͷ�����
	 */
	private String removeAbandoned = "false";
	
	/**
	 * �����ӳ�ʱ�ͷ�����ʱ���Ƿ��ӡ��̨��־
	 */
	private boolean logAbandoned = false;
	
	/**
	 * �趨�����Ƿ���readOnly����
	 */
	private boolean readOnly = true;
    
    
    
   

    /* PHYSICAL CONNECTION METHODS */

    public String getDriver() {
        return this.driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getURL() {
        return this.URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getUserName() {
        return this.username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /* DATASOURCE METHODS */

    public String getDbname() {
        return getName();
    }

    public void setDbname(String n) {
        setName(n);
    }

    public String getJNDIName() {
        return this.JNDIName;
    }

    public void setJNDIName(String n) {
        this.JNDIName = n;
    }

    public boolean isNativeResults() {
        return this.nativeResults;
    }

    public void setNativeResults(boolean b) {
        this.nativeResults = b;
    }

    /* POOL BEHAVIOR METHODS */
    public boolean isPoolPreparedStatements() {
        return poolingPreparedStatements;
    }

    public void setPoolPreparedStatements(boolean poolingPreparedStatements) {
        this.poolingPreparedStatements = poolingPreparedStatements;
    }

    public String getValidationQuery() {
        return this.validationQuery;
    }

    public void setValidationQuery(String sql) {
        this.validationQuery = sql;
    }

    public String getInitialPoolSQL() {
        return this.initialPoolSQL;
    }

    public void setInitialPoolSQL(String sql) {
        this.initialPoolSQL = sql;
    }

    public String getInitialConnectionSQL() {
        return this.initialConnectionSQL;
    }

    public void setInitialConnectionSQL(String sql) {
        this.initialConnectionSQL = sql;
    }

    public boolean isRemoveOnExceptions() {
        return this.removeOnExceptions;
    }

    public void setRemoveOnExceptions(boolean b) {
        this.removeOnExceptions = b;
    }

    /* POOLED CONNECTION METHODS */

    public int getInitialConnections() {
        return getInitialObjects();
    }

    public void setInitialConnections(int n) {
        setInitialObjects(n);
    }

    public int getConnectionTimeout() {
        return getObjectTimeout();
    }

    public void setConnectionTimeout(int n) {
        setObjectTimeout(n);
    }

    /* TX METHODS */

    public int getTransactionTimeout() {
        return this.transactionTimeout;
    }

    public void setTransactionTimeout(int n) {
        this.transactionTimeout = n;
    }

    public int getIsolationLevel() {
        return this.transactionIsolationLevel;
    }

    public void setIsolationLevel(int n) {
        this.transactionIsolationLevel = n;
    }

    public String getTxIsolationLevel() {
        return convertIsoToString(getIsolationLevel());
    }

    public void setTxIsolationLevel(String s) {
        setIsolationLevel(convertIsoToInt(s));
    }

    private int convertIsoToInt(String s) {

        int n = PoolManConstants.DEFAULT_ISO_LEVEL;

        s = s.toUpperCase().trim();

        if (s.equals("NONE"))
            n = java.sql.Connection.TRANSACTION_NONE;
        else if (s.equals("READ_COMMITTED"))
            n = java.sql.Connection.TRANSACTION_READ_COMMITTED;
        else if (s.equals("READ_UNCOMMITTED"))
            n = java.sql.Connection.TRANSACTION_READ_UNCOMMITTED;
        else if (s.equals("REPEATABLE_READ"))
            n = java.sql.Connection.TRANSACTION_REPEATABLE_READ;
        else if (s.equals("SERIALIZABLE"))
            n = java.sql.Connection.TRANSACTION_SERIALIZABLE;
        else
            System.out.println("Unrecognized isolation level " + s +
                               " using default setting of " +
                               convertIsoToString(n));

        return n;

    }

    private String convertIsoToString(int n) {

        String result = null;

        switch (n) {
            case java.sql.Connection.TRANSACTION_NONE:
                result = "NONE";
                break;
            case java.sql.Connection.TRANSACTION_READ_COMMITTED:
                result = "READ_COMMITTED";
                break;
            case java.sql.Connection.TRANSACTION_READ_UNCOMMITTED:
                result = "READ_UNCOMMITTED";
                break;
            case java.sql.Connection.TRANSACTION_REPEATABLE_READ:
                result = "REPEATABLE_READ";
                break;
            case java.sql.Connection.TRANSACTION_SERIALIZABLE:
                result = "SERIALIZABLE";
                break;
            default:
                break;
        }

        return result;

    }

    /* QUERY CACHE METHODS */

    public boolean isCacheEnabled() {
        return this.cacheEnabled;
    }

    public void setCacheEnabled(
            boolean b) {
        this.cacheEnabled = b;
    }

    public int getCacheSize() {
        return this.cacheSize;
    }

    public void setCacheSize(
            int n) {
        this.cacheSize = n;
    }

    public int getCacheRefreshInterval() {
        return this.cacheRefreshInterval;
    }

    public void setCacheRefreshInterval(
            int seconds) {
        this.cacheRefreshInterval = seconds;
    }

    /**
     * @return Returns the keygenerate.
     */
    public String getKeygenerate() {
        return keygenerate;
    }

    public String getDbtype() {
        return dbtype;
    }

    /**
     * @param keygenerate The keygenerate to set.
     */
    public void setKeygenerate(String keygenerate) {
        this.keygenerate = keygenerate;
    }

    public void setDbtype(String dbtype) {
        this.dbtype = dbtype;
    }

	public String getLoadmetadata() {
		return loadmetadata;
	}

	public void setLoadmetadata(String loadmetadata) {
		this.loadmetadata = loadmetadata;
	}
	



    /* POOL ID METHODS */

    public String getName() {
        return this.poolname;
    }

    public void setName(String name) {
        this.poolname = name;
    }

    /* POOL PROPERTY METHODS */

    public int getInitialObjects() {
        return this.initialObjects;
    }

    public void setInitialObjects(int n) {
        this.initialObjects = n;
    }

    public int getMinimumSize() {
        return this.minimumSize;
    }

    public void setMinimumSize(int n) {
        this.minimumSize = n;
    }

    public int getMaximumSize() {
        return this.maximumSize;
    }

    public void setMaximumSize(int n) {
        this.maximumSize = n;
    }

    public int getObjectTimeout() {
        return this.objectTimeout;
    }

    public void setObjectTimeout(int n) {
        this.objectTimeout = n;
    }

    public int getUserTimeout() {
        return this.userTimeout;
    }

    public void setUserTimeout(int n) {
        this.userTimeout = n;
    }

    public int getSkimmerFrequency() {
        return this.skimmerFrequency;
    }

    public void setSkimmerFrequency(int n) {
        this.skimmerFrequency = n;
    }

    public int getShrinkBy() {
        return this.shrinkBy;
    }

    public void setShrinkBy(int n) {
        this.shrinkBy = n;
    }

    public String getLogFile() {
        return this.logfile;
    }

    public void setLogFile(String filename) {
        this.logfile = filename;
    }

    public boolean isDebugging() {
        return this.debug;
    }

    public void setDebugging(boolean b) {
        this.debug = b;
    }

    public boolean isMaximumSoft() {
        return this.emergencyCreates;
    }

    public void setMaximumSoft(boolean b) {
        this.emergencyCreates = b;
    }

	public boolean isExternal() {
		return external;
	}

	public void setExternal(boolean external) {
		this.external = external;
	}

	public String getExternaljndiName() {
		return externaljndiName;
	}

	public void setExternaljndiName(String externaljndiName) {
		this.externaljndiName = externaljndiName;
	}
	
	public String getMaxWait() {
		return maxWait;
	}

	public void setMaxWait(String maxWait) {
		this.maxWait = maxWait;
	}
	



	public String getRemoveAbandoned() {
		return removeAbandoned;
	}

	public void setRemoveAbandoned(String removeAbandoned) {
		this.removeAbandoned = removeAbandoned;
	}

	public boolean isRobotquery() {
		return robotquery;
	}

	public void setRobotquery(boolean robotquery) {
		this.robotquery = robotquery;
	}

	public boolean isTestWhileidle() {
		return testWhileidle;
	}

	public void setTestWhileidle(boolean testWhileidle) {
		this.testWhileidle = testWhileidle;
	}

	public boolean isLogAbandoned() {
		return logAbandoned;
	}

	public void setLogAbandoned(boolean logAbandoned) {
		this.logAbandoned = logAbandoned;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	/**
	 * �Ƿ��Զ�ͬ��sequence
	 */
	boolean synsequence = false;
	public boolean synsequence() {
		
		return synsequence;
	}
	
	public void setSynsequence(boolean synsequence)
	{
		this.synsequence = synsequence;
	}

	public int getMaxOpenPreparedStatements() {
		return maxOpenPreparedStatements;
	}

	public void setMaxOpenPreparedStatements(int maxOpenPreparedStatements) {
		this.maxOpenPreparedStatements = maxOpenPreparedStatements;
	}

	public boolean getAutoprimarykey() {
		return autoprimarykey;
	}

	public void setAutoprimarykey(boolean autoprimarykey) {
		this.autoprimarykey = autoprimarykey;
	}

	public boolean isShowsql() {
		return showsql;
	}

	public void setShowsql(boolean showsql) {
		this.showsql = showsql;
	}

	public boolean cachequerymetadata() {
		return cachequerymetadata;
	}

	public void setCachequerymetadata(boolean cachequerymetadata) {
		this.cachequerymetadata = cachequerymetadata;
	}

	

	/**
	 * @param extenalInfo the extenalInfo to set
	 */
	public void setExtenalInfo(JDBCPoolMetaData extenalInfo) {
		this.extenalInfo = extenalInfo;
		if(extenalInfo != null)
		{
		    setDriver(extenalInfo.getDriver());		    

		    setURL(extenalInfo.getURL()) ;

		    

		    setUserName(extenalInfo.getUserName());

		    
		    setPassword(extenalInfo.getPassword()) ;

		    
		    
		    

		    setNativeResults(extenalInfo.isNativeResults());

		    

		    setPoolPreparedStatements(extenalInfo.isPoolPreparedStatements()) ;
		    

		    setValidationQuery(extenalInfo.getValidationQuery());
		    

		    setInitialPoolSQL(extenalInfo.getInitialPoolSQL());

		    

		    setInitialConnectionSQL(extenalInfo.getInitialConnectionSQL()) ;

		    
		    setRemoveOnExceptions(extenalInfo.isRemoveOnExceptions());

		    
		    setInitialConnections(extenalInfo.getInitialConnections());

		    setConnectionTimeout(extenalInfo.getConnectionTimeout());
		    setTransactionTimeout(extenalInfo.getTransactionTimeout());

		    setIsolationLevel(extenalInfo.getIsolationLevel());


		    setTxIsolationLevel(extenalInfo.getTxIsolationLevel());

		    
		    
		    setCacheEnabled(extenalInfo.isCacheEnabled());
		    setCacheSize(extenalInfo.getCacheSize());
		    setCacheRefreshInterval(extenalInfo.getCacheRefreshInterval());

		    setKeygenerate(extenalInfo.getKeygenerate());

		    setDbtype(extenalInfo.getDbtype());

			setLoadmetadata(extenalInfo.getLoadmetadata());
			


		    setInitialObjects(extenalInfo.getInitialObjects());
		    setMinimumSize(extenalInfo.getMinimumSize());


		    setMaximumSize(extenalInfo.getMaximumSize());


		    setObjectTimeout(extenalInfo.getObjectTimeout())		    ;
		    setUserTimeout(extenalInfo.getUserTimeout());

		    

		    setSkimmerFrequency(extenalInfo.getSkimmerFrequency());

		    setShrinkBy(extenalInfo.getShrinkBy());


		    
		    

		    
		    setMaximumSoft(extenalInfo.isMaximumSoft());			

			

			
			
			
			setMaxWait(extenalInfo.getMaxWait());
			



			setRemoveAbandoned(extenalInfo.getRemoveAbandoned());

			
			setRobotquery(extenalInfo.isRobotquery());

			
			setTestWhileidle(extenalInfo.isTestWhileidle());

			

			setLogAbandoned(extenalInfo.isLogAbandoned());

			

			setReadOnly(extenalInfo.isReadOnly());
			
			setSynsequence(extenalInfo.synsequence());
			setMaxOpenPreparedStatements(extenalInfo.getMaxOpenPreparedStatements());


			setAutoprimarykey(extenalInfo.getAutoprimarykey()) ;			

			setCachequerymetadata(extenalInfo.cachequerymetadata());
			
			setDatabaseProductName(extenalInfo.getDatabaseProductName());
			
			setDatabaseProductVersion(extenalInfo.getDatabaseProductVersion());
			
			setDriverName(extenalInfo.getDriverName());
			
			setDriverVersion(extenalInfo.getDriverVersion());
			this.setJndiclass(extenalInfo.getJndiclass());
			this.setJndiurl(extenalInfo.getJndiurl());
			this.setJndiuser(extenalInfo.getJndiuser());
			this.setJndipassword(extenalInfo.getJndipassword());
			this.setUsepool(extenalInfo.isUsepool());
		}
	}

	/**
	 * @return the databaseProductName
	 */
	public String getDatabaseProductName() {
		return databaseProductName;
	}

	/**
	 * @param databaseProductName the databaseProductName to set
	 */
	public void setDatabaseProductName(String databaseProductName) {
		this.databaseProductName = databaseProductName;
	}

	/**
	 * @return the driverName
	 */
	public String getDriverName() {
		return driverName;
	}

	/**
	 * @param driverName the driverName to set
	 */
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	/**
	 * @return the databaseProductVersion
	 */
	public String getDatabaseProductVersion() {
		return databaseProductVersion;
	}

	/**
	 * @param databaseProductVersion the databaseProductVersion to set
	 */
	public void setDatabaseProductVersion(String databaseProductVersion) {
		this.databaseProductVersion = databaseProductVersion;
	}

	/**
	 * @return the driverVersion
	 */
	public String getDriverVersion() {
		return driverVersion;
	}

	/**
	 * @param driverVersion the driverVersion to set
	 */
	public void setDriverVersion(String driverVersion) {
		this.driverVersion = driverVersion;
	}

	/**
	 * @return the usepool
	 */
	public boolean isUsepool() {
		return usepool;
	}

	/**
	 * @param usepool the usepool to set
	 */
	public void setUsepool(boolean usepool) {
		this.usepool = usepool;
	}

	public String getJndiurl() {
		return jndiurl;
	}

	public void setJndiurl(String jndiurl) {
		this.jndiurl = jndiurl;
	}

	public String getJndiuser() {
		return jndiuser;
	}

	public void setJndiuser(String jndiuser) {
		this.jndiuser = jndiuser;
	}

	public String getJndipassword() {
		return jndipassword;
	}

	public void setJndipassword(String jndipassword) {
		this.jndipassword = jndipassword;
	}
}
