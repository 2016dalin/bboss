directory:
src--source code
test--test source code
conf--contain database pool config files
listener--database transaction leak listener file
lib--bboss persistent framework depends jars
dist--bboss persistent release package.


---------------------------------
bboss-persistent�������̣�
---------------------------------
bboss-persistent<-active-ext [frameworkset-pool.jar]
bboss-persistent<-bboss-ws [frameworkset-pool.jar]
bboss-persistent<-bbossaop [frameworkset-pool.jar]
bboss-persistent<-bbossevent [frameworkset-pool.jar]
bboss-persistent<-cms_baseline [frameworkset-pool.jar]
bboss-persistent<-bboss-taglib [frameworkset-pool.jar]
bboss-persistent<-kettle [frameworkset-pool.jar]
bboss-persistent<-portal [frameworkset-pool.jar]
bboss-persistent<-cas server [frameworkset-pool.jar]

to do list:
��
#######update function list since bbossgroups-3.5 begin###########
o �־ò�ģ��sql��������������������ʽ�л�Ϊbboss�Դ��ı����������ƣ�֧���������ͱ�����
������������
��������
����������ϸ����������£�
   ���飨һά���飬��ά���飩
 List
 Map
 
������һ���������ʹ�õļ�ʾ����
String[] FIELDNAMES = new
String[]{"ss","testttt","sdds","insertOpreation","ss556"};
		String deleteAllsql = "delete from LISTBEAN where FIELDNAME in
(#[FIELDNAMES[0]],#[FIELDNAMES[1]],#[FIELDNAMES[2]],#[FIELDNAMES[3]],#[FIELDNAMES[4]])";
		Map conditions = new HashMap();
		conditions.put("FIELDNAMES", FIELDNAMES);
		
o �־ò���ģ��sql�б�������������չ���Ż�����������ʽ�л�Ϊ������д��sql������������
������ʽֻ�ܽ����򵥵ı������޷��������ӵı�����ʽ
     #[HOST_ID]���ָ�ʽ������ʽ�ܹ�����
     #[HOST_ID->bb[0]]���ִ����õĸ�ʽ��������Ͳ��ܽ�����
     VariableHandler.parserSQLStruction�������Խ����������ָ�ʽ�ı����������ܹ������ӵı���

����Ϣ��Variable�б�ķ�ʽ�洢���Թ��־ò��ܶ���Щ������ֵ

�����������ܿ����Ѿ���ɲ��������ֳ����Ĳ��ԣ���һ�����Ǿ��Ǹû����滻ԭ����������ʽ��ʽ��
����Ĳ��Է�����鿴����������
/bboss-util/test/com/frameworkset/util/TestVaribleHandler.java�е���ط���
public void varialparserUtil()
public void regexUtilvsVarialparserUtil()			
#######update function list since bbossgroups-3.4 begin###########

o ������ӳ����޷����ҵ�tomcat 6��weblogic ��������Դ������
o ���sql server jtd�����޷���ȷ�ҵ�DB adaptor������
o PreparedDBUtil����public void setBlob(int i, String x) throws SQLException ����������ֱ����blob�����ֶ��д����ַ���
o �޸�TestLob����������������ʾSQLExecutor/ConfigSQLExecutor�����Blob��clob�в����ַ����ķ�����
	@Test
	public void testNewSQLParamInsert() throws Exception
	{
		SQLParams params = new SQLParams();
		params.addSQLParam("id", "1", SQLParams.STRING);
		// ID,HOST_ID,PLUGIN_ID,CATEGORY_ID,NAME,DESCRIPTION,DATASOURCE_NAME,DRIVER,JDBC_URL,USERNAME,PASSWORD,VALIDATION_QUERY
		params.addSQLParam("blobname", "abcdblob",
				SQLParams.BLOB);
		params.addSQLParam("clobname", "abcdclob",
				SQLParams.CLOB);
		SQLExecutor.insertBean("insert into test(id,blobname,clobname) values(#[id],#[blobname],#[clobname])", params);
	}
	@Test
	public void testNewBeanInsert() throws Exception
	{
		LobBean bean = new LobBean();
		bean.id = "2";
		bean.blobname = "abcdblob";
		bean.clobname = "abcdclob";
		SQLExecutor.insertBean("insert into test(id,blobname,clobname) values(#[id],#[blobname],#[clobname])", bean);
	}
	
	ע�����
	public static class LobBean
	{
		private String id;
		@Column(type="blob")
		private String blobname;
		@Column(type="clob")
		private String clobname;
	}
	��type�ֱ�Ϊblobfile����clobfileʱ���ͱ���Ҫ���ֶε�ֵ����Ϊ�������֣�
	1.java.io.File
	2.MultipartFile
	3.InputStream
	
	���磺
	public static class LobBean
	{
		private String id;
		@Column(type="blobfile")
		private File blobname;
		@Column(type="clobfile")
		private File clobname;
	}
o SQLExecutor/ConfigSQLExecutor������ӵ��ֶζ��¼�������ΪList<String>�Ĳ�ѯ���ܣ�ʹ�÷������£�
ʹ�÷�����
@Test
	public void dynamicquery() throws SQLException
	{
		 
		List<ListBean> result =  SQLExecutor.queryList(ListBean.class,"select id  from LISTBEAN");
		 
		 
		 System.out.println(result.size());
		 
		 List<String> result_string =  SQLExecutor.queryList(String.class,"select id  from LISTBEAN");
		 
		 
		 System.out.println(result_string.size());
		 
		 List<Integer> result_int =  SQLExecutor.queryList(Integer.class,"select id  from LISTBEAN");
		 
		 
		 System.out.println(result_int.size());
		 
	}
	
	@Test
	public void dynamicqueryObject() throws SQLException
	{
		 
		ListBean result =  SQLExecutor.queryObject(ListBean.class,"select id  from LISTBEAN");
		 
		 
		 System.out.println(result.getId());
		 
		 String result_string =  SQLExecutor.queryObject(String.class,"select id  from LISTBEAN");
		 
		 
		 System.out.println(result_string);
		 
		 int result_int =  SQLExecutor.queryObject(int.class,"select id  from LISTBEAN");
		 
		 
		 System.out.println(result_int);
		 
	}
o �޸�ִ��clob �ļ�����������¿�ָ�������

sql = "INSERT INTO CLOBFILE (FILENAME,FILECONTENT,fileid,FILESIZE) VALUES(#[filename],#[FILECONTENT],#[FILEID],#[FILESIZE])";
			SQLParams sqlparams = new SQLParams();
			sqlparams.addSQLParam("filename", file.getOriginalFilename(), SQLParams.STRING);
			sqlparams.addSQLParam("FILECONTENT", file,SQLParams.CLOBFILE);
			sqlparams.addSQLParam("FILEID", UUID.randomUUID().toString(),SQLParams.STRING);
			sqlparams.addSQLParam("FILESIZE", file.getSize(),SQLParams.LONG);
			SQLExecutor.insertBean(sql, sqlparams);	
o �Ż�or mapping����
o �������µ�frameworkset-util.jar���־ò�or mapping���Ƶ�bean�����Բ�����Ҫget/set����
o ����̬��ӵ�����Դ��removeAbandoned��������Ϊfalse
#######update function list since bbossgroups-3.3 begin###########
------2011-09-22-------------
o ����MultipartFile����־û����ܣ������Ѻ���ʾ�����������������ΪMultipartFile��Ҫ�Զ��洢�����ݿ��blob����clob�ֶ�ʱ
��Ҫ���@Column(type="blobfile")����@Column(type="clobfile")����ѯ���ֶ�����ʱ�����⽫���ֶ�ע�뵽����ΪMultipartFile��������
------2011-09-20-------------
o ����ProArray�������л����ܴ��ڵ�����
------2011-09-06-------------
o SQLParams���޷���ȡ����bean���ֶζ�����Ϣ
o ResultMap���޷���ȡ����bean���ֶζ�����Ϣ
o ������Wraper���͡�Boolean��Charaset��Byte��Wraper���͵Ļ�ȡĬ��ֵ����Ϊ����null
o SQLParams��getParamJavatype������Long��Double��Float��Short��Bloone������ȷ������


------2011-08-16-------------
o �����̬sql����У�bean����û��set����ʱ�����߼��жϲ�����ȷ����������
------2011-08-15-------------
o �Ż�blob/clob�����޸�ĳЩ�����blob/clobΪ��ʱ���������
#######update function list since bbossgroups-3.2 begin###########
------2011-08-05-------------
o �������ں�ʱ������ʱת��Ϊ�ַ���ʱ�����ֵΪ��ʱ�׳���ָ���쳣�������޸�
------2011-07-25-------------
o ���sql serverԪ���ݻ�ȡΪ�յ�����
------2011-07-24-------------
o �Ľ�SQLParams api������ֱ�Ӷ�MultipartFile�������clob����blob�С�
sqlparams.addSQLParam("FILECONTENT", multipartfile,SQLParams.BLOBFILE);

���ڴ��ֶεĴ�����������·�����
sqlparams.addSQLParam("FILECONTENT", multipartfile,SQLParams.BLOBFILE);//ֱ�Ӵ���MultipartFile������в���
sqlparams.addSQLParam("FILECONTENT", inputStream, size,SQLParams.BLOBFILE);//ֱ�Ӵ���InputStream�����Լ�����СSize���Խ��в���
------2011-07-15-------------
o ����FieldRowHandler���������Ա�ʵ�ִ�blob/clob�л�ȡ�����ֶ��ļ�����Ĵ���,����������������Ҳ����ʹ��FieldRowHandler��ʹ��ʾ�����£�
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
											"select filename,filecontent from CLOBFILE where fileid=?",
											fileid);
		}
		catch (Exception e)
		{
			throw e;
		}
	}
------2011-07-15------------
o ���Ӷ��ļ��ϴ����ʹ�db���ع��ܵ�֧��,ʹ��ʵ��
�ϴ�
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
����	
	SQLExecutor.queryByNullRowHandler(new NullRowHandler(){
				@Override
				public void handleRow(Record record)
						throws Exception
				{
					record.getBlob("filecontent").getBinaryStream();
					StringUtil.sendFile(request, response, record.getString("filename"),record.getBlob("filecontent"));
				}}, "select * from filetable where fileid=?",fileid);
o ���û��ָ��һ��sql��䣬PreparedDBUtil.executePreparedBatch�������쳣��������Ǻܺ���
ֱ�Ӹ�Ϊinfo��ʽ��

#######update function list since bbossgroups-3.1 begin###########

------2011-06-10------------
o ����SQLExecutor�з����ֶβ�ѯAPI�е�����ת��©��
------2011-06-8------------
o ��������������ʾ��Ϣ��externaljndiName
o �޸�ֻ�����ⲿ����Դ������£�pool������ջ�������
    java.lang.StackOverflowError
	at java.lang.Thread.currentThread(Native Method)
	at org.apache.xerces.util.SecuritySupport12$1.run(Unknown Source)
	at java.security.AccessController.doPrivileged(Native Method)
	at org.apache.xerces.util.SecuritySupport12.getContextClassLoader(Unknown Source)
	at org.apache.xerces.util.ObjectFactory.findClassLoader(Unknown Source)
	at org.apache.xerces.impl.dv.DTDDVFactory.getInstance(Unknown Source)
	at org.apache.xerces.impl.dv.DTDDVFactory.getInstance(Unknown Source)
	at 
	�޸ĳ����嵥��
	com.frameworkset.common.poolman.sql.PoolMan
	com.frameworkset.common.poolman.util.JDBCPool
	com.frameworkset.common.poolman.util.SQLManager
	com.frameworkset.common.poolman.util.SQLUtil
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
------2011-06-2------------
o �޸�ʹ��Columnע�����ö�������������ӳ��ʱ������sql����а���Ӧ�����Ա���ֵʧ�ܵ�©��
------2011-05-20------------
o �޸���������datasource©��
o �޸�ͨ��ģ����������Դ������������
o �޸�һϵ�п�ָ������
o �޸�ʹ���ⲿ����Դʱ�����ⲿ����Դ��oracleʱ�޷���ȡ����Դ��Ԫ��������

------2011-05-17------------
o �޸�sqlexecutor����������timestamp����ʧʱ�����ȱ��
------2011-05-03------------
o �޸������񻷾���ͨ��JDBCPool�ķ���
public TableMetaData getTableMetaDataFromDatabase(Connection con,
			String tableName)
��ȡ�����ض�����Դ�ı�Ԫ���ݵ�����
------2011-05-03------------
o �޸�preDBUtil.preparedSelect(params, dbName, sql, offset, long)
	�ڲ�ѯû�����ݵ�����£�preDBUtil.getMeta()���ص���null��
#######update function list since bbossgroups-3.1 end###########


#######update function list since bbossgroups-3.0 begin###########
------2011-04-25------------
o �޸���ȡ�������͵�ֵʱ��һ��rs�з���nullʱ��û����ȷ��ȡ����ȱʡֵ������
ValueExchange.convert����
------2011-04-18------------
o ��������й¶�����ƣ���manager-provider.xml�����Ӽ��ҳ���ַ�������ã�
<!-- ���ݿ�����й¶���url���ͷ�Χ���� -->
	<property name="transaction.leakcheck.files" >
		<array componentType="String">
			<property value=".jsp"/>
			<property value=".do"/>
			<property value=".page"/>
			<property value=".action"/>
			<property value=".ajax"/>
		</array>
	</property>
------2011-04-16------------
o ���ƴ�����ֵ���������ģ�����֧�ַ������͵ķ���
public void stringarraytoList(final List<ListBean> beans) throws Throwable {
		List<ListBean> ret = TemplateDBUtil.executeTemplate(
				new JDBCValueTemplate<List<ListBean>>(){
					public List<ListBean>  execute() throws Exception {
						String sql = "INSERT INTO LISTBEAN (" + "ID," + "FIELDNAME," 
						+ "FIELDLABLE," + "FIELDTYPE," + "SORTORDER,"
						+ " ISPRIMARYKEY," + "REQUIRED," + "FIELDLENGTH,"
						+ "ISVALIDATED" + ")" + "VALUES"
						+ "(#[id],#[fieldName],#[fieldLable],#[fieldType],#[sortorder]"
						+ ",#[isprimaryKey],#[required],#[fieldLength],#[isvalidated])";
						SQLExecutor.delete("delete from LISTBEAN");
						SQLExecutor.insertBeans(sql, beans);
						return beans;
				}
			}
		);
	}
------2011-04-11------------
o ����ConfigSQLExecutor��SQLExecutor��������к�bean������صĽӿڣ�
	Object bean������������ͨ�ĵ�ֵ����Ҳ������һ��SQLParams����,Ҳ������һ��Map����
	ʹ�÷����ο�����������
	/bboss-persistent/test/com/frameworkset/sqlexecutor/ConfigSQLExecutorTest.java

------2011-04-07------------
o ���Ӹ��ݱ������ƴ������ļ��л�ȡsql�������������ݿ����,��Ӧsql�����ļ��ṩ��ʱˢ�»���
  �����⵽sql�ļ����޸ģ��ʹ��¼����ļ���ǰ���ǿ���ˢ�»��ƣ�
com.frameworkset.common.poolman.ConfigSQLExecutor
�����ʹ�÷���Ϊ��
ConfigSQLExecutor executor = new ConfigSQLExecutor("com/frameworkset/sqlexecutor/sqlfile.xml");
Map dbBeans  =  executor.queryObject(HashMap.class, "sqltest");
String result = executor.queryFieldBean("sqltemplate", bean);
�����ļ���

<?xml version="1.0" encoding='gb2312'?>
<properties>
<description>
<![CDATA[
	sql�����ļ�
	����ͨ����������name����Ĭ��sql���ض����ݿ��sqlͨ����
	���ƺ�������ݿ����ͺ�׺�����֣����磺
	sqltest
	sqltest-oracle
	sqltest-derby
	sqltest-mysql
	�ȵȣ�������ʵ������ʾ�˾������÷���
 ]]>
</description>
	<property name="sqltest"><![CDATA[select * from LISTBEAN]]>
	</property>	
	<property name="sqltest-oracle"><![CDATA[select * from LISTBEAN]]>
	</property>
	<property name="sqltemplate"><![CDATA[select FIELDNAME from LISTBEAN where FIELDNAME=#[fieldName]]]>
	</property>
	<property name="sqltemplate-oracle"><![CDATA[select FIELDNAME from LISTBEAN where FIELDNAME=#[fieldName]  ]]>
	</property>	
	<property name="dynamicsqltemplate"><![CDATA[select *  from CIM_ETL_REPOSITORY  where 1=1 
					#if($HOST_ID && !$HOST_ID.equals("")) and HOST_ID = #[HOST_ID] #end  
						and PLUGIN_ID = #[PLUGIN_ID] and CATEGORY_ID = #[CATEGORY_ID] and APP = #[APP]]]>	
	</property>
</properties>

ˢ�»��Ƶ����÷�����
��manager-provider.xml�ļ����������������ɣ�
<property name="sqlfile.refresh_interval" value="10000"/>
��value����0ʱ�Ϳ���sqlfile�ļ��ĸ��¼����ƣ�ÿ��valueָ����ʱ�����ͼ��һ�Σ��и��¾����¼��أ��������¼���
------2011-04-06------------
o ����һ���ѯ�����ֶεķ��ͽӿڣ�ʹ�÷������£�
		String sql = "select REQUIRED from LISTBEAN ";
		int id=  SQLExecutor.queryTField(int.class, sql);
		long id=  SQLExecutor.queryTField(long.class, "select seq_name.nextval from LISTBEAN ");
		String sql = "select FIELDLABLE from LISTBEAN ";
		String id=  SQLExecutor.queryTField(String.class, sql);
		System.out.println(id);
o 3.0api���ӷ���List<HashMap>������Ĳ�ѯ�ӿ�֧�֣�ʹ�÷������£���Ԥ�������Ϊ������
@Test
	public void queryListMap() throws SQLException
	{
		String sql = "select * from LISTBEAN name=?";
		List<HashMap> dbBeans  =  SQLExecutor.queryListWithDBName(HashMap.class, "mysql", sql,"ttt");
		System.out.println(dbBeans);
	}
	
	
	public void queryListMapWithbeanCondition() throws SQLException
	{
		String sql = "select * from LISTBEAN name=#[name]";
		ListBean beanobject = new ListBean();
		beanobject.setName("duoduo");
		List<HashMap> dbBeans  =  SQLExecutor.queryListWithDBName(HashMap.class, "mysql", sql,beanobject);
		System.out.println(dbBeans);
	}
	
------2011-03-30------------
o 3.0api�����ƶ�java.util.Date���Ͷ����������ݵĴ���
------2011-03-04------------
o ����һ�����ݲ�����������Դ��api�����Կ�������Դ�����ӳ�����Դ���Ƿ����ӳ�����Դ
DBUtil���������¾�̬������
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
        		String externaljndiName        		
        		)
        		
o ����Դ�����ļ�������usepoolԪ�� �����Կ�������Դ�����ӳ�����Դ���Ƿ����ӳ�����Դ
<?xml version="1.0" encoding="gb2312"?>
<poolman>
	<datasource>
		<dbname>bspf</dbname>
		<loadmetadata>false</loadmetadata>
		<jndiName>jdbc/mysql-ds</jndiName>
		<driver>oracle.jdbc.driver.OracleDriver</driver>
		<usepool>false</usepool>
		<url>jdbc:oracle:thin:@//172.16.25.139:1521/dtjf</url>	
		<username>dtjf</username>
		<password>dtjf</password>
		<txIsolationLevel>READ_COMMITTED</txIsolationLevel>		
	    <logAbandoned>true</logAbandoned>
		<readOnly>false</readOnly>
		<keygenerate>composite</keygenerate>	
		<autoprimarykey>false</autoprimarykey>
		<showsql>false</showsql>
	</datasource>
</poolman>       		

o ���������ӳ�����Դ�ļ�����ݲɼ����������չʾ�ӿ�
------2011-03-02------------
 
o �޸�odbc ������ʹ��o/r mapping��ѯ�����ֶδ����쳣����
o ��չSQLExecutor����������в�ѯList����������Է��͵�֧��
o �޸�double����������int����ת�������⣬��������ֵת��Ϊ����Ĺ��ܣ�֧��������������֮����໥ת��

#######update function list since bbossgroups-3.0 end###########
------------------------------------------------------------------------
update function list in bbossgroups-3.0 since bbossgroups-2.0-rc2
------------------------------------------------------------------------
o SQLExecutor���������һ�����ݿ����api

------------------------------------------------------------------------
update function list in bbossgroups-2.0-rc2 since bbossgroups-2.0-rc1
------------------------------------------------------------------------
----------------------------------------
bbossgroups-2.1-RC - 2011-02-9
----------------------------------------
o ���������ݿ���ֱ�ӻ�ȡ��Ԫ���ݺ����б�Ԫ���ݵ�api
----------------------------------------
bbossgroups-2.1-RC - 2011-01-20
----------------------------------------
o �������ӳء��ⲿ���ӳؼ�������Ϣ��ȡ��ʽ������ⲿ���ӳض�Ӧ��ʵ�����ӳ������һ���ڲ������ӳ�
��ô�ⲿ���ӳص�����ʱ�䣬ͣ��ʱ�佫ֱ����ʵ�����ӳص�ʱ�䡣

----------------------------------------
bbossgroups-2.1-RC - 2010-12-30
----------------------------------------
o ���Ӵ������ļ��������ӳط���-ʹ�÷����ο���������com.frameworkset.common.Monitor
o ������sql����������߼��ж���书��,���磺
			PreparedDBUtil dbutil = new PreparedDBUtil();
			String listRepositorySql = "select *  from CIM_ETL_REPOSITORY  where 1=1 " +
					"#if($HOST_ID && !$HOST_ID.equals(\"\")) " +
					"	and HOST_ID = #[HOST_ID]" +
					"#end  " +
					" and PLUGIN_ID = #[PLUGIN_ID] " +
					" and CATEGORY_ID = #[CATEGORY_ID] and APP = #[APP]";
			String sql = listRepositorySql.toString();

			try {
				// ��ѯ����
				SQLParams params = new SQLParams();
				params.addSQLParam("HOST_ID", null,//����host_id����Ϊ��ֵNull���߿մ�""����ֱ�Ӻ�����Ӹò�������ô����е�and HOST_ID = #[HOST_ID]"�������Ե�
												   //"#if($HOST_ID && !$HOST_ID.equals(\"\")) " +
												   //"	and HOST_ID = #[HOST_ID]" +
												   //"#end
						SQLParams.STRING);
				params.addSQLParam("PLUGIN_ID", "pluginid",
						SQLParams.STRING);
				params.addSQLParam("CATEGORY_ID", "catogoryid",
						SQLParams.STRING);
				params.addSQLParam("APP", "app", SQLParams.STRING);

				dbutil.preparedSelect(params, "bspf",
								sql);
				// ִ�в�ѯ
				dbutil.executePrepared();

select *  from CIM_ETL_REPOSITORY  where 1=1 #if($HOST_ID && !$HOST_ID.equals("")) and HOST_ID = #[HOST_ID] #end   and PLUGIN_ID = #[PLUGIN_ID]  and CATEGORY_ID = #[CATEGORY_ID] and APP = #[APP]
----------------------------------------
bbossgroups-2.1-RC - 2010-12-01
----------------------------------------
o �޸Ĳ�ѯԪ���ݻ�����ƣ����÷����ӳػ���Ļ��ƣ��������ݿ�֮��ĳ�ͻ

o SQLParams��Params�����copy��������sql�������и��ƣ��Ա��ڱ�ǩ���н��з�ҳ��ѯʱ����ҳ���¼�������仯ʱ����sqlparams����

------------------------------------------------------------------------
update function list in bbossgroups-2.0-rc1 since bbossgroups-2.0-rc
------------------------------------------------------------------------
----------------------------------------
bbossgroups-2.0-rc1 - 2010-07-30
----------------------------------------
select to_char(inserttime,'HH24MISS'),count(*) from TEST_CURRENT 
group by to_char(inserttime,'HH24MISS') order by to_char(inserttime,'HH24MISS')
truncate table TEST_CURRENT
purge recyclebin
----------------------------------------
bbossgroups-2.0-rc1 - 2010-08-03
----------------------------------------
o �����ݿ��������ƣ�
��������������ӳ�bspf��mq�ϵ�һ���������mqʵ��������bspf��Ϊ�ⲿ����Դ����ômq�Ķ�Ӧ�������bspf��������ϲ�Ҳ����˵ʵ����
mqֱ��ʹ��bspf������
o �ⲿ����Դ�����Ϣ���ƣ�Դ���ݼ������ƣ�������Ϣ����
<datasource external="true">

    <dbname>mq</dbname>
    <externaljndiName>jdbc/mysql-ds</externaljndiName>
	<showsql>false</showsql>

  </datasource>
  <datasource external="true">
    <dbname>kettle</dbname>
   
    <externaljndiName>jdbc/mysql-ds</externaljndiName>
	<showsql>false</showsql>
  </datasource>
  
  
----------------------------------------
bbossgroups-2.0-rc1 - 2010-07-23
----------------------------------------


o ����to_char����������,to_date�������䣬Ŀǰ�޸ĵ���oracle��mysql
----------------------------------------
bbossgroups-2.0-rc1 - 2010-07-22
----------------------------------------
o ��չdb���������ݿ�����ת������������ָ��ת�����ڸ�ʽ��������
o �Ż�/bboss-persistent/src/com/frameworkset/common/poolman/management/BaseTableManager.java�м���tableinfo��Ϣ��uuid��sequenceʱ������������ֵ
o mysql ���ݿ�������bug�޸�
/bboss-persistent/src/com/frameworkset/orm/adapter/DBMM.java
public String getIDMAXSql(String table_name,String table_id_name,String table_id_prefix,String type)
	{
    	//SUBSTR(table_id_name,LENGTH(table_id_prefix))
    	String maxSql = "select max("+ table_id_name + ") from " + table_name;
    	if(type.equalsIgnoreCase("string") || type.equalsIgnoreCase("java.lang.string"))
    	{
    		if(table_id_prefix != null && !table_id_prefix.trim().equals(""))
    		{
    		//��bigint �޸�ΪDECIMAL������ᱨ������ԭ����mysql��cast��������ֹbigint����
    			maxSql = "select max(CAST(SUBSTRING(" + table_id_name + ",len(" + table_id_prefix + ") + 1) as DECIMAL))) from " + table_name;
    		}
    		else
    		{
    		//��bigint �޸�ΪDECIMAL������ᱨ������ԭ����mysql��cast��������ֹbigint����
    			maxSql = "select max(CAST(" + table_id_name + " as DECIMAL)) from " + table_name;
    		}
    	}
		
		return maxSql;
	}

update function list:
----------------------------------------
bbossgroup 1.0rc - 2010-07-7
----------------------------------------
o ��չ���������ܣ��û������Զ����ض����ݿ��������
��ʵ�ֵ������������com.frameworkset.orm.adapter.DB�̳л�����com.frameworkset.orm.adapter.DB����̳�
�Զ������������÷�����
poolman.xml�ļ������ã�
<poolman>
	<adaptor dbtype="oracle">com.frameworkset.orm.adaptors.MyOracle
	</adaptor>
	<adaptor dbtype="oracle_other">com.frameworkset.orm.adaptors.OtherOracle
	</adaptor>
	<adaptor dbtype="db2net">com.frameworkset.orm.adaptors.MyDB2Adaptor
	</adaptor>
	.........

</poolman>

o DBUtil�������ݿ����ӳش���������
        public static void startPool(String poolname,String driver,String jdbcurl,String username,String password,String readOnly,String validationQuery)
    	{
        	SQLUtil.getSQLManager().startPool(poolname, driver, jdbcurl, username, password, readOnly, validationQuery);
    	}
    	
    	
o DBUtil����������,��������Ӧ��ƽ̨�������������Ӧ�����������Щ����
    	
    	public static InterceptorInf getInterceptorInf(String dbname)
    	{
    		return SQLUtil.getSQLManager().getPool(dbname).getInterceptor();
    	}
    	��صĳ���
    	com.frameworkset.common.poolman.interceptor.DummyInterceptor
    	com.frameworkset.common.poolman.interceptor.InterceptorInf
    	poolman.xml�ļ�������InterceptorInf�ķ�����
    	��datasource���������½ڵ㼴�ɣ�
    	<interceptor>com.frameworkset.orm.adaptors.MyInterceptor</interceptor>
    	�Զ������������Ҫ��com.frameworkset.common.poolman.interceptor.InterceptorInf�ӿڼ̳У�ʵ����������������
    	public String getDefaultDBName();
		public String convertSQL(String sql,String dbtype,String dbname);
    	���ߴ�com.frameworkset.common.poolman.interceptor.DummyInterceptor�̳У����ط�����
    	public String convertSQL(String sql, String dbtype, String dbname) {
			return sql;
		}
	
		public String getDefaultDBName() {
			return SQLManager.getInstance().getPool(null).getDBName();
			
		}
    	

o �޸�weglobic������jndi����ʧ��bug
��س���
com.frameworkset.common.poolman.util.JDBCPool
----------------------------------------
bbossgroup 1.0rc - 2010-07-2
----------------------------------------
o �޸�db2Դ���ݼ��ر�����ʱ���������û����ݵ����⣬�޸ĳ���
/bboss-persistent/src/com/frameworkset/common/poolman/util/JDBCPool.java
/bboss-persistent/src/com/frameworkset/orm/adapter/DB.java
----------------------------------------
bbossgroup 1.0rc - 2010-05-27
----------------------------------------
o ������ط������
com.frameworkset.common.poolman.monitor
----------------------------------------
bbossgroup 1.0rc - 2010-05-21
----------------------------------------
o ����uuid�������ɻ���
/bboss-persistent/src/com/frameworkset/common/poolman/sql/PrimaryKey.java
����uuid���ƽ�����36λ���ȵ��ַ���Ψһ����ֵ

----------------------------------------
bbossgroup 1.0rc - 2010-05-07
----------------------------------------

o mysql�������ɻ���bug�޸�
���������ķ�����ֱ��д����dbname��sequence����
o com.frameworkset.commons.dbcp.DelegatingResultSetȥ���˶�OracleResultSet������
----------------------------------------
bbossgroup 1.0rc - 2010-05-05
----------------------------------------
o �����ر����ӳط���
----------------------------------------
 DBUtil.stopPool(dbname);
 DBUtil.startPool(dbname);
bbossgroup 1.0rc - 2010-04-29
----------------------------------------
o ����pool�ļ����Ϣ
starttime
stoptime
----------------------------------------
bbossgroup 1.0rc - 2010-04-24
----------------------------------------
o ����mysql�������ɻ���
1.����sequence���ݿ�
CREATE DATABASE sequence; 
��sequence�ϴ�����
CREATE TABLE sequence.sequence_data ( 
sequence_name varchar(100) NOT NULL, 
sequence_increment int(11) unsigned NOT NULL DEFAULT 1, 
sequence_min_value int(11) unsigned NOT NULL DEFAULT 1, 
sequence_max_value bigint(20) unsigned NOT NULL DEFAULT 18446744073709551615, 
sequence_cur_value bigint(20) unsigned DEFAULT 1, 
sequence_cycle boolean NOT NULL DEFAULT FALSE, 
PRIMARY KEY (sequence_name) 
) ENGINE=MyISAM;
������sequence
-- This code will create sequence with default values. 
--INSERT INTO sequence.sequence_data 
--(sequence_name) 
--VALUE 
--(sq_my_sequence) ; 

 
-- You can also customize the sequence behavior. 
--INSERT INTO sequence.sequence_data 
--(sequence_name, sequence_increment, sequence_max_value) 
--VALUE 
--(sq_sequence_2, 10, 100) 
--;

������ȡsequenceֵ�ĺ�����
CREATE FUNCTION nextval (seq_name varchar(100)) 
RETURNS bigint(20) NOT DETERMINISTIC 
BEGIN
DECLARE cur_val bigint(20); 
SELECT
sequence_cur_value INTO cur_val 
FROM
sequence.sequence_data 
WHERE
sequence_name = seq_name ; 
IF cur_val IS NOT NULL THEN
UPDATE
sequence.sequence_data 
SET
sequence_cur_value = IF ( 
(sequence_cur_value + sequence_increment) > sequence_max_value, 
IF ( 
sequence_cycle = TRUE, 
sequence_min_value, 
NULL
), 
sequence_cur_value + sequence_increment ) 
WHERE
sequence_name = seq_name ; 
END IF; 
RETURN cur_val; 
END;

�޸ĵĳ���
/bboss-persistent/src/com/frameworkset/common/poolman/sql/PrimaryKey.java
/bboss-persistent/src/com/frameworkset/orm/adapter/DB.java--����sequence������������
/bboss-persistent/src/com/frameworkset/orm/adapter/DBMM.java--����sequence������������

o �޸�bug,�����ӳػ�û�г�ʼ��ʱͨ�����·�����ȡ����ʱ��NullpointException
PreparedDBUtil.getNextPrimaryKey("mysql",
							"cim_dbpool"))
�޸ĳ���
/bboss-persistent/src/com/frameworkset/common/poolman/DBUtil.java
��Ӿ�̬��ʼ������

----------------------------------------
1.0.5 - 2010-04-01
----------------------------------------
o ��ӻ�ȡ���ݿ�������б�ķ���
com.frameworkset.common.poolman.util.SQLManager
com.frameworkset.common.poolman.util.PoolManager

 public List<String> getAllPoolNames() {
        assertLoaded();
        return super.getAllPoolNames();
    }

com/frameworkset/common/poolman/util/SQLUtil.java

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
----------------------------------------
1.0.5 - 2010-03-30
----------------------------------------
o ���mysql���ݿ����Record.getClob��Record.getBlob����������ת���쳣����
ԭ�����mysql��blob��clob�ֶη��ص�jdbc���ͷֱ�ΪLONGVARBINARY��LONGVARCHAR
��/bboss-persistent/src/com/frameworkset/common/poolman/handle/ValueExchange.java���н�
LONGVARBINARY��LONGVARCHAR���͵��ֶζ�ȡʱ����ResultSet.getObject��������ȡ��Ĭ�Ϸ���byte[]��������
����취��

LONGVARBINARY-����ResultSet.getBlob��������ȡ
LONGVARCHAR-����ResultSet.getClob��������ȡ

�����ݿ�������com.frameworkset.orm.adapter.DB���ṩLONGVARBINARY��LONGVARCHAR����ֵ���������������Ա㲻ͬ�����ݿ�����������͵����ݽ������⴦��
mysql���ݿ�������com.frameworkset.orm.adapter.DBMM����DB�еķ�����blob��clob���������������ݿ���ʱ��DB�е�Ĭ�Ϸ���������
----------------------------------------
1.0.5 - 2010-03-29
----------------------------------------
o �޸����⣬DBUtil.getDBDate(new Date())����ʱ����ʽ����ȷ����
����취
com.frameworkset.orm.adapter.DBOracle����������db_format����֤java���ڸ�ʽDATE_FORMAT��oracle���ڸ�ʽ����һ��
//DD-MM-YYYY HH24:MI:SS old format
    public static final String db_format = "yyyy-MM-dd HH24:mi:ss";


o ����sqlģ��Ԥ����������
com.frameworkset.common.poolman.SQLExecutor
SQLExecutor��Ҫ����֧��bboss��ǩ��Ԥ��������ɾ���ġ��顢�������ܣ�������ֱ�ӿ��Ÿ�Ӧ�ó���ʹ��

��Ҫ�ṩ�������ԣ�
���ݿ�Ԥ����/��ͨ��ѯ������
���룬ɾ�����޸�
Ԥ������룬ɾ�����޸�
������Ԥ����/��ͨ���룬ɾ�����޸Ĳ���

ϵͳ�ṩע��ģʽ��֧������Ԥ�������ģʽ
SQLExecutor�����ԭ�����ݿ�����Ļ����Ͽ�����һ���µ�������̳�ԭ��ȫ�����ܡ�


----------------------------------------
1.0.5 - 2010-01-19
----------------------------------------

o ������������ö����com.frameworkset.orm.annotation.TransactionType
���������¼������ͣ�
/**
     * ʼ�մ���������
     */
    NEW_TRANSACTION,
    
    /**
     * ���û�����񴴽���������������뵱ǰ����
     */
    REQUIRED_TRANSACTION,
    /**
     * ������ͼ�������û�в���������,Ĭ�����
     */
    MAYBE_TRANSACTION,
    /**
     * û������
     */
    NO_TRANSACTION,
    
    /**
     * δ֪��������
     */
    UNKNOWN_TRANSACTION,
    
    /**
     * ��д�������ͣ�֧�����ݿ��д�����������������Ĳ���
     * �������񶼿��Կ���
     */
    RW_TRANSACTION

----------------------------------------
1.0.5 - 2010-01-19
----------------------------------------
o oracle��derby��mysql����lob�ֶε�����
��lob�ֶε�д����oracle��derby��mysql�л���һ��
��lob�ֶ�ʱ��oracle֧���ڶ�ȡ����֮�⻹���Զ�ȡblob�ֶκ�clob�ֶε����ݣ�����derby��mysql�оͲ����ԣ�������clob�ֶ�Ϊʾ�������磺

oracle�������²����ǿ��Եģ�
		PreparedDBUtil dbUtil = new PreparedDBUtil();
		try {
			//��ѯ���ֶ����ݲ��ҽ����ֶδ�ŵ��ļ���
			dbUtil.preparedSelect( "select id,clobname from test");
			dbUtil.executePrepared();//ִ�ж�ȡ����
			
			for(int i = 0; i < dbUtil.size(); i ++)
			{
				
				dbUtil.getFile(i, "clobname", new java.io.File("d:/route" + i + ".txt")); //��ȡclob�ֶε��ļ��У��ڶ�ȡ������֮�����clob�ֶ�
//				String clobvalue = dbUtil.getString(i, "clobname");//��ȡclob�ֶε��ַ���������
//				Clob clob = dbUtil.getClob(i, "clobname");//��ȡclob�ֶ�ֵ��clob���ͱ�����
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			dbUtil = null;
		}

��ô��mysql��derby����ô���أ��Ǿ�Ҫ������NullRowHandler����������RowHandler��������ʵ��һ�������ڶ�blob�ֶεĴ�����(blobΪ��)��
		PreparedDBUtil dbUtil = new PreparedDBUtil();
		try {

			//��ѯ���ֶ����ݲ��ҽ����ֶδ�ŵ��ļ���
			dbUtil.preparedSelect("derby", "select id,blobname from test");
			final List<File> datas = new ArrayList<File>();//�洢blob�ֶ�blobname��datas�б���
			dbUtil.executePreparedWithRowHandler(new NullRowHandler(){

                @Override
                public void handleRow(Record origine) throws Exception
                {
                    File file = origine.getFile("blobname", new java.io.File("resources/lob/reader/dominspector_" + origine.getRowid() +".rar"));
                    datas.add(file);
                }});
			
			System.out.println("testblobRead dbUtil.size():"+dbUtil.size());//���������Ĵ�С
			System.out.println("testblobRead datas.size():"+datas.size());//���������Ĵ�С
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw e;
		}
		finally
		{
			
		}

o DBUtil�����������·���,����֧�ֲ�������ֵ����ͨ��ѯ�ͷ�ҳ��ѯ������

public void executeSelectWithRowHandler(String sql,Connection con,NullRowHandler rowhandler)  throws SQLException
public void executeSelectWithRowHandler(String sql,NullRowHandler rowhandler)  throws SQLException
public void executeSelectWithRowHandler(String dbname,String sql,NullRowHandler rowhandler)  throws SQLException
public void executeSelectWithRowhandler(String dbname, String sql, Connection con,NullRowHandler rowhandler) throws SQLException
public void executeSelectWithRowHandler(String sql,long offset,int pagesize,Connection con,NullRowHandler rowhandler)  throws SQLException
public void executeSelectWithRowHandler(String sql,long offset,int pagesize,NullRowHandler rowhandler)  throws SQLException
public void executeSelectWithRowHandler(String dbname,String sql,long offset,int pagesize,NullRowHandler rowhandler)  throws SQLException
public void executeSelectWithRowhandler(String dbname, String sql,long offset,int pagesize, Connection con,NullRowHandler rowhandler) throws SQLException

�û�����ͨ�������������ɵع����Լ��Ĳ�ѯ�������    ,�ɲο��򵥵Ĳ������У�
/bboss-persistent/test/com/frameworkset/common/rowhandler/DBUtilRowHandler.java

o PreparedDBUtil�������������,����֧�ֲ�������ֵ����ͨ��ѯ�ͷ�ҳ��ѯ������

public void executePreparedWithRowHandler(NullRowHandler rowhandler) throws SQLException
public void executePreparedWithRowHandler(Connection con,NullRowHandler rowhandler) throws SQLException
�û�����ͨ�������������ɵع����Լ��Ĳ�ѯ�������      ,�ɲο��򵥵Ĳ������У�
/bboss-persistent/test/com/frameworkset/common/rowhandler/PreparedDBUtilRowhandler.java


o ���ӳ�����com.frameworkset.common.poolman.handle.NullRowHandler��

public abstract class NullRowHandler extends BaseRowHandler
{

    ������������
    

    public abstract void handleRow(Record origine) throws Exception;
}
���������Զ��巵��ֵ���ͣ�����ʵ����ʵ�ֳ��󷽷�public abstract void handleRow(Record origine) throws Exception;����recordΪ��ǰ��¼���Խ�����Ӧ�Ĵ���

�����ʹ�÷�������ο���������/bboss-persistent/test/com/frameworkset/derby/TestLob.java�еķ�����
public void testBlobRead()  throws Exception

o ����ʹ��mysql��derbyʱ��һЩ���⣬

o mysql,derby���ݿ��datasource�����ļ�poolman.xml�е�readOnly����Ҫ����Ϊfalse

o ����ע�ⷽʽ�����ݿ��������
	@Transaction("REQUIRED_TRANSACTION")
    @RollbackExceptions("") //@RollbackExceptions("{exception1,excpetion2}")
    ��ϸ����ο���������/bbossaop/test/com/chinacreator/spi/transaction/annotation
----------------------------------------
1.0.5 - 2009-12-14
----------------------------------------
o �޸�prepareddbutil�еķ�ҳ��ѯ��������offset����������int�޸�Ϊlong
/bboss-persistent/src/com/frameworkset/common/poolman/PreparedDBUtil.java
----------------------------------------
1.0.5 - 2009-6-16
----------------------------------------

o PoolManager �������·�����
 public boolean exist(String name)
    {
        return this.pools.containsKey(name);
    }
  SQLUtil  �������·�����
  �ж����ݿ����ӳ��Ƿ����
   public static boolean exist(String dbname)
	    {
	      SQLManager datab = getSQLManager();
              return  datab.exist(dbname);
	    }
   

o �޸Ĺ�˾��Ϣ

o �޸��ʼ���ַ��������
If the StackTrace contains an InstanceAlreadyExistsException, then you have  encountered a ClassLoader linkage problem.  Please email poolman@codestudio.com **
	at com.frameworkset.common.poolman.util.SQLManager.requestConnection(SQLManager.java:190)
	at com.frameworkset.common.poolman.util.SQLUtil.getConection(SQLUtil.java:930)
	at com.chinacreator.mq.transfer.send.SendBigData.execute(SendBigData.java:268)
o ����Serializable�����ȡ�ӿ�
com.frameworkset.common.poolman.DBUtil

	
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

com.frameworkset.common.poolman.Record
public Serializable getSerializable(String field) throws SQLException
    {
        Blob blog = this.getBlob(field);
        if(blog == null)
            return null;
        Serializable object = null;
        InputStream in = null;        
        java.io.ObjectInputStream objectin = null;
        try
        {
            
            in = blog.getBinaryStream();
            objectin = new ObjectInputStream(in);
            
            object = (Serializable)objectin.readObject();
        }
        catch(SQLException e)
        {
            throw e;
        }
        catch(Exception e)
        {
            throw new NestedSQLException(e);
        }
        return object;
    }
    
    public Serializable getSerializable(int parameterIndex) throws SQLException
    {
        Blob blog = this.getBlob(parameterIndex);
        if(blog == null)
            return null;
        Serializable object = null;
        InputStream in = null;        
        java.io.ObjectInputStream objectin = null;
        try
        {
            
            in = blog.getBinaryStream();
            objectin = new ObjectInputStream(in);
            
            object = (Serializable)objectin.readObject();
        }
        catch(SQLException e)
        {
            throw e;
        }
        catch(Exception e)
        {
            throw new NestedSQLException(e);
        }
        return object;
        
    }
o ��bboss-persistent\src\com\frameworkset\common\poolman\Record.java
  ��ȡblob�����ջ��������⣺
  java.lang.StackOverflowError
	at com.frameworkset.common.poolman.Record.getBlob(Record.java:1630)
  ԭ����������·���������ѭ��
  public Blob getBlob (String parameterName) throws SQLException
{
	return (Blob)this.getBlob(parameterName);
}
�޸�Ϊ��
public Blob getBlob (String parameterName) throws SQLException
    {
    	return (Blob)this.getObject(parameterName);
    }
  ���ɡ�
  
o �쳣��java.util.ConcurrentModificationException: concurrent access to HashMap attempted by Thread[Thread-58,5,main]
����������
	[09-4-28 16:37:53:375 CST] 0000003b SystemErr     R java.util.ConcurrentModificationException: concurrent access to HashMap attempted by Thread[Thread-58,5,main]
	at java.util.HashMap.onEntry(HashMap.java(Inlined Compiled Code))
	at java.util.HashMap.transfer(HashMap.java(Compiled Code))
	at java.util.HashMap.resize(HashMap.java(Inlined Compiled Code))
	at java.util.HashMap.addEntry(HashMap.java(Compiled Code))
	at java.util.HashMap.put(HashMap.java(Compiled Code))
	at com.frameworkset.common.poolman.sql.PrimaryKeyCache.loaderPrimaryKey(PrimaryKeyCache.java:143)
	at com.frameworkset.common.poolman.sql.PrimaryKeyCache.getIDTable(PrimaryKeyCache.java:95)
	at com.frameworkset.common.poolman.util.StatementParser.refactorInsertStatement(StatementParser.java:421)
	at com.frameworkset.common.poolman.util.StatementParser.refactorInsertStatement(StatementParser.java:362)
	at com.frameworkset.common.poolman.DBUtil.doJDBCInsert(DBUtil.java:1372)
	at com.frameworkset.common.poolman.DBUtil.executeInsert(DBUtil.java:1166)
	at com.frameworkset.common.poolman.DBUtil.executeInsert(DBUtil.java:1176)
	at com.frameworkset.common.poolman.DBUtil.executeInsert(DBUtil.java:1172)
	at com.chinacreator.sms.BaseMTWriterImpl.traceSendSuccess(BaseMTWriterImpl.java:61)
	at com.chinacreator.sms.thread.QueueServiceHandleThread.run(QueueServiceHandleThread.java:79)
	at java.lang.Thread.run(Thread.java:570)

[09-4-28 16:37:53:375 CST] 0000003b SystemErr     R 	at java.util.HashMap.onEntry(HashMap.java(Inlined Compiled Code))
[09-4-28 16:37:53:375 CST] 0000003b SystemErr     R 	at java.util.HashMap.transfer(HashMap.java(Compiled Code))
[09-4-28 16:37:53:375 CST] 0000003b SystemErr     R 	at java.util.HashMap.resize(HashMap.java(Inlined Compiled Code))
[09-4-28 16:37:53:375 CST] 0000003b SystemErr     R 	at java.util.HashMap.addEntry(HashMap.java(Compiled Code))
[09-4-28 16:37:53:375 CST] 0000003b SystemErr     R 	at java.util.HashMap.put(HashMap.java(Compiled Code))
[09-4-28 16:37:53:375 CST] 0000003b SystemErr     R 	at com.frameworkset.common.poolman.sql.PrimaryKeyCache.loaderPrimaryKey(PrimaryKeyCache.java:143)
[09-4-28 16:37:53:375 CST] 0000003b SystemErr     R 	at com.frameworkset.common.poolman.sql.PrimaryKeyCache.getIDTable(PrimaryKeyCache.java:95)
[09-4-28 16:37:53:375 CST] 0000003b SystemErr     R 	at com.frameworkset.common.poolman.util.StatementParser.refactorInsertStatement(StatementParser.java:421)
[09-4-28 16:37:53:375 CST] 0000003b SystemErr     R 	at com.frameworkset.common.poolman.util.StatementParser.refactorInsertStatement(StatementParser.java:362)
[09-4-28 16:37:53:375 CST] 0000003b SystemErr     R 	at com.frameworkset.common.poolman.DBUtil.doJDBCInsert(DBUtil.java:1372)
[09-4-28 16:37:53:375 CST] 0000003b SystemErr     R 	at com.frameworkset.common.poolman.DBUtil.executeInsert(DBUtil.java:1166)
[09-4-28 16:37:53:375 CST] 0000003b SystemErr     R 	at com.frameworkset.common.poolman.DBUtil.executeInsert(DBUtil.java:1176)
[09-4-28 16:37:53:375 CST] 0000003b SystemErr     R 	at com.frameworkset.common.poolman.DBUtil.executeInsert(DBUtil.java:1172)
[09-4-28 16:37:53:375 CST] 0000003b SystemErr     R 	at com.chinacreator.sms.BaseMTWriterImpl.traceSendSuccess(BaseMTWriterImpl.java:61)
[09-4-28 16:37:53:375 CST] 0000003b SystemErr     R 	at com.chinacreator.sms.thread.QueueServiceHandleThread.run(QueueServiceHandleThread.java:79)
[09-4-28 16:37:53:375 CST] 0000003b SystemErr     R 	at java.lang.Thread.run(Thread.java:570)

����������������ֻ��ż�����֣��ڷ�����ѹ���󣬻������ݿ�ѹ�����ʱ�����׳��֣������ĵ���http://jira.codehaus.org/browse/XFIRE-1119
����������ҵ�������еķ�����
com.frameworkset.common.poolman.sql.PrimaryKeyCache
�����Ӧ��Map����map�޸�Ϊͬ��Map��
	id_tables = new java.util.concurrent.ConcurrentHashMap(new HashMap());
	
o �����д������Է��͵�֧��

public abstract class RowHandler<T>
public abstract void handleRow(T rowValue,Record record);



----------------------------------------
1.0.4 - 2009-5-22
----------------------------------------
o ����com.frameworkset.common.poolman.handle.RowHandler

��ԭ���Ľӿڸ�Ϊ������
���ӿڷ���public void handleRow(Object rowValue,Record origine)
��Ϊ���󷽷�public abstract void handleRow(Object rowValue,Record origine)
����init��destroy�������Ա���в�ѯԴ���ݺ����ݿ�����dbname�ĳ�ʼ�������٣���Щ��Ϣ�Դ洢���̺ͺ��������е��д������������á�

���ӷ���
public SchemaType getSchemaType(String colName)
�Ա����д������� ��ȡ�е�java�����������ƺ����ݿ���������
        
o �޸�com/frameworkset/common/poolman/Record.java��
���Ӽ�¼��ԭʼ�к���Ϣ��get/set������
/**
	 * ���ü�¼��Ӧ�����ݿ�ԭʼ��¼�к�
	 */
	private int rowid;
	public void setRowid(int rowid)
	{
	    this.rowid = rowid;
	}
	public int getRowid()
        {
            return rowid;
        }
 
o ResoultMap���ж�Record��ĳ�ʼ�������������Ż�

o ��չxml�д�����

�����Զ�����ڵ�����
�����Զ����ַ���
�����Զ���汾��
��ӹ���xml�ڵ㴮����������

ʵ��
��com.frameworkset.common.poolman.handle.XMLRowHandler������������·�����
        /**
         * rowValue����ΪStringBuffer
         */
	public void handleRow(Object rowValue,Record origine) ��Ĭ��ʵ��
	
/**
	 * ����xml���ĸ��ڵ�����
	 * ȱʡΪrecords���û�������չ�������
	 * @return
	 */
	public String getRootName()
	{
	    return "records";
	}
	
	/**
         * ����xml�ı����ַ���
         * ȱʡΪgb2312���û�������չ�������
         * @return
         */
        public String getEncoding()
        {
            return "gb2312";
        }
        
        
        /**
         * ����xml�﷨�İ汾��
         * ȱʡΪ1.0���û�������չ�������
         * @return
         */
        public String getVersion()
        {
            return "1.0";
        }

����xml�ڵ㴮�ķ���
public static String buildNode(String columnNodeName,//xml�ڵ�����
                                      String columnName,//�������name���Ե�ֵ
                                      String columnType, //�����jdbc������������ֵ
                                      String columnJavaType, //�����java������������ֵ
                                      String value,//���ֵ
                                      String split)//�����ڵ�֮��ķָ��

public static String buildNode(String columnNodeName, //xml�ڵ�����
                                Map attributes,//�ڵ����Լ�
                                String value, //�ڵ�ֵ
                                String split)//�ڵ��ķָ��
��Щ��������ȱʡʵ�֣������һ�µĻ������������и��ǡ�

ʹ��ʵ��
public class TestXMLHandler {
    public static void testCustomXMLHandler()
    {
        PreparedDBUtil db = new PreparedDBUtil();
        try {
            db.preparedSelect("select * from tableinfo");
//            String results_1 = db.executePreparedForXML();
            String results_ = db.executePreparedForXML(new XMLRowHandler(){
//
//               
                public void handleRow(Object rowValue, Record origine)  {
                    StringBuffer record = (StringBuffer )rowValue;
                    record.append("    <record>\r\n");
   
                    try {
                        SchemaType schemaType = super.getSchemaType("TABLE_NAME"); 
                        record.append(super.buildNode("attribute", 
                                                      "TABLE_NAME", 
                                                      schemaType.getName(),
                                                      schemaType.getJavaType(), 
                                                      origine.getString("TABLE_NAME"),
                                                      "\r\n"));
                        schemaType = super.getSchemaType("table_id_name");
                        record.append(super.buildNode("attribute", 
                                                      "table_id_name", 
                                                      schemaType.getName(),
                                                      schemaType.getJavaType(),  
                                                      origine.getString("table_id_name"),
                                                      "\r\n"));
                        schemaType = super.getSchemaType("TABLE_ID_INCREMENT");
                        record.append(super.buildNode("attribute", 
                                                      "TABLE_ID_INCREMENT", 
                                                      schemaType.getName(),
                                                      schemaType.getJavaType(),  
                                                      origine.getString("TABLE_ID_INCREMENT"),
                                                      "\r\n"));
                        
                        schemaType = super.getSchemaType("TABLE_ID_GENERATOR");
                        record.append(super.buildNode("attribute", 
                                                      "TABLE_ID_GENERATOR", 
                                                      schemaType.getName(),
                                                      schemaType.getJavaType(), 
                                                      origine.getString("TABLE_ID_GENERATOR"),
                                                      "\r\n"));
                        schemaType = super.getSchemaType("TABLE_ID_TYPE");
                        record.append(super.buildNode("attribute", 
                                                      "TABLE_ID_TYPE", 
                                                      schemaType.getName(),
                                                      schemaType.getJavaType(),  
                                                      origine.getString("TABLE_ID_TYPE"),
                                                      "\r\n"));
                        
                    } catch (SQLException e) {
                        
                        throw new RowHandlerException(e);
                    }
                    record.append("    </record>\r\n");
                }

                
                public String getEncoding() {
                    // TODO Auto-generated method stub
                    return "gbk";
                }

               
                public String getRootName() {
                    // TODO Auto-generated method stub
                    return "tableinfo";
                }

               
                public String getVersion() {
                    // TODO Auto-generated method stub
                    return "2.0";
                }
                
            });
            
            System.out.println(results_);
//            System.out.println(results_1);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    public static void testDefualtXML()
    {
        PreparedDBUtil db = new PreparedDBUtil();
        try {
            db.preparedSelect("select * from tableinfo");
            String results_1 = db.executePreparedForXML();

            System.out.println(results_1);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args)
    {
        testCustomXMLHandler();
    }
}

o �޸�bug��ִ��o/r mapping ��ѯʱ�������������/byte/boolean������ֵΪnullʱ���ᱨ�����쳣��
Build ValueObject for ResultSet[select * from mq_node where NODE_NAME='test'] Get Column[CA_ID] from  ResultSet to com.chinacreator.mq.client.MqNode@10cec16.CA_ID[int] failed:null
ERROR 01-06 17:30:25,093 - Build ValueObject for ResultSet[select * from mq_node where NODE_NAME='test'] Get Column[CA_ID] from  ResultSet to com.chinacreator.mq.client.MqNode@10cec16.CA_ID[int] failed:null
java.lang.IllegalArgumentException
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:39)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:25)
	at java.lang.reflect.Method.invoke(Method.java:585)
	at com.frameworkset.common.poolman.ResultMap.buildValueObject(ResultMap.java:193)
	at com.frameworkset.common.poolman.StatementInfo.buildResultMap(StatementInfo.java:731)
	at com.frameworkset.common.poolman.util.SQLUtil.innerExecuteJDBC(SQLUtil.java:540)
	at com.frameworkset.common.poolman.DBUtil.executeSelectForObject(DBUtil.java:3753)
	at com.frameworkset.common.poolman.DBUtil.executeSelectForObject(DBUtil.java:3742)
	at com.frameworkset.common.poolman.DBUtil.executeSelectForObject(DBUtil.java:3618)
	at com.chinacreator.mq.client.MqNodeService.getNodeByName(MqNodeService.java:150)
	at be.ibridge.kettle.consumer_stream.ConsumerService.buildMQClient(ConsumerService.java:63)
	at be.ibridge.kettle.consumer_stream.Consumer.processRow(Consumer.java:155)
	at be.ibridge.kettle.consumer_stream.Consumer.run(Consumer.java:200)

ԭ�������
���ݿ��ѯ���صĽ������������������/byte/booleanΪnullʱ��ԭ���Ĵ������ֱ�ӷ���null�������Ƿ��ؾ������������/byte/boolean��ȱʡֵ�����½�nullֵ���ø���������ʧ�ܡ�

����취���޸Ķ�Ӧ��������/byte/boolean��handle�ӿ��ṩnullֵ��ת������,��nullת��Ϊ��Ӧ��ȱʡֵ��
com/frameworkset/common/poolman/handle/type/BigDecimalTypeHandler.java
com/frameworkset/common/poolman/handle/type/BooleanTypeHandler.java
com/frameworkset/common/poolman/handle/type/ByteTypeHandler.java
com/frameworkset/common/poolman/handle/type/DoubleTypeHandler.java
com/frameworkset/common/poolman/handle/type/FloatTypeHandler.java
om/frameworkset/common/poolman/handle/type/IntegerTypeHandler.java
com/frameworkset/common/poolman/handle/type/LongTypeHandler.java
com/frameworkset/common/poolman/handle/type/ShortTypeHandler.java

----------------------------------------
1.0.3-1 - 2009-4-16
----------------------------------------

o ����������
��sql����м�����ͬ�������в�ͬ����ʱ���ͻ��ڶ�ֵʱ����ָ���쳣��
����̨��Ϣ��

java.lang.NullPointerException
	at com.frameworkset.common.poolman.Record.seekField(Record.java:694)
	at com.frameworkset.common.poolman.Record.getObject(Record.java:724)
	at com.frameworkset.common.poolman.Record.getString(Record.java:156)
	at com.frameworkset.common.poolman.DBUtil.getValue(DBUtil.java:292)
	at com.chinacreator.jspreport.DimenDao.findReportBeans(DimenDao.java:133)
	at com.chinacreator.jspreport.DimenDao.getReport(DimenDao.java:222)
	at com.chinacreator.jspreport.report.ReportList.getDataList(ReportList.java:31)
	at com.frameworkset.common.tag.pager.DataInfoImpl.getItemCount(DataInfoImpl.java:147)
	at com.frameworkset.common.tag.pager.tags.PagerContext.setDataInfo(PagerContext.java:1094)
	at com.frameworkset.common.tag.pager.tags.PagerContext.initContext(PagerContext.java:954)
	at com.frameworkset.common.tag.pager.tags.PagerContext.init(PagerContext.java:209)
	at com.frameworkset.common.tag.pager.tags.PagerTag.doStartTag(PagerTag.java:1030)
	at org.apache.jsp.jspreport.report_005flist_jsp._jspService(report_005flist_jsp.java:299)
	at org.apache.jasper.runtime.HttpJspBase.service(HttpJspBase.java:133)
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:856)
	at org.apache.jasper.servlet.JspServletWrapper.service(JspServletWrapper.java:311)
	at org.apache.jasper.servlet.JspServlet.serviceJspFile(JspServlet.java:301)
	at org.apache.jasper.servlet.JspServlet.service(JspServlet.java:248)
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:856)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:284)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:204)
	at com.frameworkset.common.filter.CharsetEncodingFilter.doFilter(CharsetEncodingFilter.java:92)
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:233)
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:204)
	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:256)
	at org.apache.catalina.core.StandardValveContext.invokeNext(StandardValveContext.java:151)
	at org.apache.catalina.core.StandardPipeline.invoke(StandardPipeline.java:563)
	at org.apache.catalina.core.StandardContextValve.invokeInternal(StandardContextValve.java:245)
	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:199)
	at org.apache.catalina.core.StandardValveContext.invokeNext(StandardValveContext.java:151)
	at org.apache.catalina.core.StandardPipeline.invoke(StandardPipeline.java:563)
	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:195)
	at org.apache.catalina.core.StandardValveContext.invokeNext(StandardValveContext.java:151)
	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:164)
	at org.apache.catalina.core.StandardValveContext.invokeNext(StandardValveContext.java:149)
	at org.apache.catalina.core.StandardPipeline.invoke(StandardPipeline.java:563)
	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:156)
	at org.apache.catalina.core.StandardValveContext.invokeNext(StandardValveContext.java:151)
	at org.apache.catalina.core.StandardPipeline.invoke(StandardPipeline.java:563)
	at org.apache.catalina.core.ContainerBase.invoke(ContainerBase.java:972)
	at org.apache.coyote.tomcat5.CoyoteAdapter.service(CoyoteAdapter.java:209)
	at org.apache.coyote.http11.Http11Processor.process(Http11Processor.java:670)
	at org.apache.coyote.http11.Http11Protocol$Http11ConnectionHandler.processConnection(Http11Protocol.java:517)
	at org.apache.tomcat.util.net.TcpWorkerThread.runIt(PoolTcpEndpoint.java:575)
	at org.apache.tomcat.util.threads.ThreadPool$ControlRunnable.run(ThreadPool.java:666)
	at java.lang.Thread.run(Thread.java:595)


�޸ĳ���
com/frameworkset/common/poolman/Record.java


------------------------------
 1.0.3 - 2009-4-13
------------------------------

 o ����ʹ��poolman��δ������tableinfo����̨��SQLException�쳣
�޸ĳ���
com/frameworkset/common/poolman/management/BaseTableManager.java

 o sql server 2005��ҳ��ѯ�쳣����
ԭ����sqlserver����Ҫ��������statement
Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY); 
����poolman����������ȡStatement ��stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
��˱�����Ĵ���
com.microsoft.sqlserver.jdbc.SQLServerException: ��֧�ִ��α�����/������
�ϡ�

�޸ķ�����
ͨ����������������ȡ�α����ͣ�

�޸ĵĳ���
com\frameworkset\orm\adapter\DB.java
com\frameworkset\orm\adapter\DBMSSQL.java
com\frameworkset\common\poolman\StatementInfo.java
com\frameworkset\common\poolman\DBUtil.java


o ���ӻ�ȡDataSource�Ľӿ�
�޸ĵĳ���
com\frameworkset\common\poolman\util\SQLUtil.java
����������������
��ȡȱʡ���ӳض�Ӧ��datasource
public static DataSource getDataSource()
	
��ȡ�������ݿ����ӳ�����Ӧ��datasource
	public static DataSource getDataSource(String dbname)
      
ͨ��jndi���Ʋ�������Դ
  public static DataSource getDataSourceByJNDI(String jndiname) throws NamingException
      

com/frameworkset/common/poolman/handle/type/CallableStatementResultSet.java
�������·�����
public Object getObject(int arg0, Map arg1) throws SQLException


com/frameworkset/common/poolman/util/JDBCPool.java
���ӷ�����
public static DataSource find(String jndiName) throws NamingException


����ȫ�ֱ�����
public static Context ctx = null;
	
o ����bboss jndi �����Ļ���
����jndi�����Ļ��������ϵͳ����Ӧ�÷�������������ʱ�޷�����jndi�󶨵����⡣
�������޸ĵĳ������£�
com/frameworkset/common/poolman/jndi/DummyContext.java��������
com/frameworkset/common/poolman/jndi/DummyContextFactory.java��������
com/frameworkset/common/poolman/util/JDBCPool.java���޸ģ�



o DBUtilִ������������ϲ�ѯ���������Ĳ�ѯ�ֶ��а�����ͬ�ֶ�����û��ָ����Щ�ֶεı���ʱ����ѯ�����ǰ��ı��ֶε�ֵ������ı��ֶε�ֵ���ǡ����磺
select table_a.id,table_b.id from table_a,table_b;

��������£�
DBUtil dbUtil = new DBUtil();
		dbUtil.executeSelect("select table_a.id,table_b.id from table_a,table_b ");
		System.out.println("table_a.id" + dbUtil.getInt(0, 0));
		System.out.println("table_b.id" + dbUtil.getInt(0, 1));

��ѯ�Ľ����ʾtable_a.id��ֵ��Ϊtable_b.id�ֶε�ֵ��

ԭ�������

ִ�б��β�ѯ�󣬽����Ԫ����Ϊ��
[id,id]
�ɼ�������ѯ�ֶε�������ͬ����û��ָ�������ֶΣ�����DBUtil��ʱ��Ž���������ݽṹΪһ��hash��keyΪ�ֶ�����ֵΪ��Ӧ�ֶε�ֵ�������͵���ԭ�����ֶε�ֵ��ǰ����ֶε�ֵ���ǡ�
����취��
A.	Ϊ�ֶ�ָ����ͬ�ı���
B.	�޸�ԭ���Ĵ洢���ƣ�Ϊ��ͬ������������ͬ�ı���
���������Ĺ���Ϊ������+#$_+�����������磺
��ԭ����[id,id]ת��Ϊ[id,id#$_1]


�޸ĵĳ����嵥Ϊ��
/bboss-persistent/src/com/frameworkset/common/poolman/sql/PoolManResultSetMetaData.java
/bboss-persistent/src/com/frameworkset/common/poolman/ResultMap.java
/bboss-persistent/src/com/frameworkset/common/poolman/Record.java
/bboss-persistent/src/com/frameworkset/common/poolman/DBUtil.java

���Խű���
create table TABLE_A
(
  ID  NUMBER(10),
  ID1 NUMBER(10)
)
/
create table TABLE_b
(
  ID  NUMBER(10),
  ID1 NUMBER(10)
)
/
insert into table_a (id,id1) value(1,11)
/
insert into table_b (id,id1) value(2,22)
/

���Գ���
/bboss-persistent/test/com/frameworkset/common/TestTwoTablewithSameCol.java
ִ�в�ѯ�����ȷ��

o  sqlserver��������ݿ�����е�������char(1),��ô, DBUtil��ѯ����ֶε�ʱ��,�ͱ���
�����ԭ�������poolman.xml�ļ���ָ�������ݿ�����������ȷ��
<jndiName>hb_datasource_jndiname</jndiName>
<driver>com.microsoft.sqlserver.jdbc.SQLServerDriver</driver>
��ȷ���䷨Ϊ��
<jndiName>hb_datasource_jndiname</jndiName>
<driver>com.microsoft.jdbc.sqlserver.SQLServerDriver</driver>
���ڲ���ȷ�Ƿ����com.microsoft. sqlserver.jdbc.SQLServerDriver��������
�����������������sqlserver 2005�汾����������
��Ҫ��������
/bboss-persistent/src/com/frameworkset/orm/adapter/DBFactory.java
�Ա��ܹ���ȷ���ҵ�sql server������������ͬ���ݿ������������ͨ������������ҵģ�����������������

o  ����oracle��ҳ����
�޸ĳ���
com/frameworkset/orm/adapter/DBOracle.java




------------------------------
 1.0.2 - DEC 24, 2008
------------------------------

 o Improved  blob/clob handle perfermence.
 o Fixed maybe memery leaker in handle blob/clob columns.

First version of bboss persitent released.

------------------------------
 1.0.1 - DEC 14, 2008
------------------------------

First version of bboss persitent released.

hibernate.rar 
����ļ�������bboss persistent �� hibernate��ϵĳ��򣬰���hibernateʹ��bboss persistent ��������
hibernateʹ��bboss persistent �ṩ������Դ

[http://blog.csdn.net/yin_bp]