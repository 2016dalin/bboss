package test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import com.frameworkset.common.poolman.DBUtil;
import com.frameworkset.common.poolman.JDBCValueTemplate;
import com.frameworkset.common.poolman.PreparedDBUtil;
import com.frameworkset.common.poolman.TemplateDBUtil;


public class TestValueTemplateDBUtil {
	public static void testrollbackWithConnection() throws Throwable
	{
		Object ret = TemplateDBUtil.executeTemplate(
				new JDBCValueTemplate(){
					public Object execute() throws Exception {
						DBUtil dbUtil = new DBUtil();
						Connection con = PreparedDBUtil.getConection();
						PreparedDBUtil db = null;
						try {
							for (int i = 0; i < 1; i++) {
								db = new PreparedDBUtil();
								
									db
											.preparedInsert(
													"insert into td_reg_bank_acc_bak (create_acc_time,starttime,endtime) values(?,?,?)");
									Date today = new Date(new java.util.Date().getTime());
//									db.setInt(1, 1000 + i);

									db.setDate(1, new java.util.Date());
									db.setDate(2, new java.util.Date());
									db.setDate(3, new java.util.Date());

									// if(true && i < 50)
									// {
									// throw new Exception("e");
									// }

									db.executePrepared(con);

								
							}
							
							
						
						}
						catch(SQLException e)
						{
							con.rollback();
							throw e;
						}
						catch(Exception e)
						{
							con.rollback();
							throw e;
						}
						finally {
							db.resetPrepare();
							con.close();
						}
						
						dbUtil.executeInsert("insert into table values()");
						return "go";
					}
				
				}
			);
		System.out.println("testrollbackWithConnection return value:" + ret);
	}
	
	public static void testrollback() throws Throwable
	{
		Object ret = TemplateDBUtil.executeTemplate(
				new JDBCValueTemplate(){
					public Object execute() throws Exception {
						DBUtil dbUtil = new DBUtil();
						
						PreparedDBUtil db = null;
						try {
							for (int i = 0; i < 1; i++) {
								db = new PreparedDBUtil();
								
									db.preparedInsert(
													"insert into td_reg_bank_acc_bak (create_acc_time,starttime,endtime) values(?,?,?)");
									Date today = new Date(new java.util.Date().getTime());
//									db.setInt(1, 1000 + i);

									db.setDate(1, new java.util.Date());
									db.setDate(2, new java.util.Date());
									db.setDate(3, new java.util.Date());

									// if(true && i < 50)
									// {
									// throw new Exception("e");
									// }

									db.executePrepared();

								
							}
						} finally {
							
						}
						
						dbUtil.executeInsert("insert into table values()");
						return "go";
					}
				
				}
			);
		System.out.println("testrollback return value:" + ret);
	}
	
	public static void testcommit() throws Throwable
	{
		
		Object ret = TemplateDBUtil.executeTemplate(
				new JDBCValueTemplate(){
					/**
					 * ����execute()������ִ�ж��ᱻ������һ�����ݿ�������
					 * �����쳣�׳�ʱTemplateDBUtil.executeTemplate()�����ͻ��Զ��ع��������ݿ�����
					 * ������������������������ͻ��Զ����ύ����������ֵ���ظ����ó���
					 * ͨ��ģ�幤���ṩ�ı�����������Ա����Ҫ��д�Լ����������Ϳ���˳����ʵ�����ݿ�������Բ���
					 */
					public Object execute() throws Exception {
						DBUtil dbUtil = new DBUtil();						
						PreparedDBUtil db = null;
						try {
							for (int i = 0; i < 1; i++) {
								db = new PreparedDBUtil();								
								db.preparedInsert("insert into " +
										"td_reg_bank_acc_bak " +
										"(create_acc_time,starttime,endtime) " +
										"values(?,?,?)");
								Date today = new Date(
										new java.util.Date().getTime());
								db.setDate(1, new java.util.Date());
								db.setDate(2, new java.util.Date());
								db.setDate(3, new java.util.Date());
								db.executePrepared();
							}
						} 
						catch(Exception e)
						{
							throw e;//�׳��쳣���������������ݿ�����ع�
						}
						try
						{
							dbUtil.executeInsert("insert into " +
									"td_reg_bank_acc_bak " +
									"(clob1,clob2) values('aa','bb')");
						}
						catch(Exception e)
						{
							throw e;//�׳��쳣���������������ݿ�����ع�
						}						
						//����˳��ִ����ɣ������Զ��ύ�������ط����ķ���ֵ
						return "go";
					}				
				}
			);
		System.out.println("testcommit return value:" + ret);
	}
	
	
	public static void testcommitWithConnection() throws Throwable
	{
		Object ret = TemplateDBUtil.executeTemplate(
				new JDBCValueTemplate(){
					public Object execute() throws Exception {
						DBUtil dbUtil = new DBUtil();
						PreparedDBUtil db = null;
						try {
							for (int i = 0; i < 1; i++) {
								db = new PreparedDBUtil();
								
									db.preparedInsert(
													"insert into td_reg_bank_acc_bak (create_acc_time,starttime,endtime) values(?,?,?)");
									Date today = new Date(new java.util.Date().getTime());
//									db.setInt(1, 1000 + i);

									db.setDate(1, new java.util.Date());
									db.setDate(2, new java.util.Date());
									db.setDate(3, new java.util.Date());

									// if(true && i < 50)
									// {
									// throw new Exception("e");
									// }

									db.executePrepared(dbUtil.getConection());

								
							}
						} finally {
							
						}
						
						dbUtil.executeInsert("insert into td_reg_bank_acc_bak (clob1,clob2) values('conaa','conbb')");
						return "go";
					}
				
				}
			);
		System.out.println("testcommitWithConnection return value:" + ret);
	}
	
	public static void main(String[] args) 
	{
		try
		{
			TestValueTemplateDBUtil.testrollback();
		}
		catch(Throwable e)
		{
			e.printStackTrace();
		}
		try {
			TestValueTemplateDBUtil.testrollbackWithConnection();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			TestValueTemplateDBUtil.testcommit();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			TestValueTemplateDBUtil.testcommitWithConnection();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(DBUtil.getNumActive());
		System.out.println(DBUtil.getNumIdle());
		System.out.println(DBUtil.getMaxNumActive());
		
	}

}
