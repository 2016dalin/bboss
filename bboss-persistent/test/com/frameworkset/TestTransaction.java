package com.frameworkset;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.transaction.RollbackException;
import javax.transaction.Status;

import org.junit.Test;

import com.frameworkset.common.poolman.DBUtil;
import com.frameworkset.common.poolman.SQLExecutor;
import com.frameworkset.common.poolman.util.SQLManager;
import com.frameworkset.commons.dbcp.AbandonedTrace;
import com.frameworkset.orm.transaction.JDBCTransaction;
import com.frameworkset.orm.transaction.TransactionException;
import com.frameworkset.orm.transaction.TransactionManager;
import com.frameworkset.sqlexecutor.ListBean;

public class TestTransaction {
	public static void testLocalThread()
	{
		ThreadLocal local1 = new ThreadLocal();
		ThreadLocal local2 = new ThreadLocal();
		Object object1 = new Object();
		Object object2 = new Object();
		
		local1.set(object1);
		local2.set(object2);
		System.out.println(local1.get());
		System.out.println(local2.get());
		TransactionManager tm = new TransactionManager();
		try {
			tm.begin(tm.NO_TRANSACTION);
//			tm.getTransaction().getConnection(dbName);
			DBUtil dbUtil = new DBUtil();
//			dbUtil.getConection();
			
			dbUtil.executeDelete("dbname","sql");
			dbUtil.executeDelete("dbname1","sql");
			throw new Exception("");
		}
		catch(Exception e)
		{
			
		}
		finally
		{
			tm.release();
		}
		
		
	}
	
	@Test
	public void testDestroyTX()
	{
		 TransactionManager tm = new TransactionManager();
	        try {
	            tm.begin(TransactionManager.RW_TRANSACTION);
	            Connection con = DBUtil.getConection();
	            con.createStatement();
	            con.prepareStatement("select 1 from dual");
	            con = DBUtil.getConection("bspf");
	            con.prepareStatement("select 1 from dual");
	            tm.rollback();
	            List objects = DBUtil.getTraceObjects();
	            tm.destroyTransaction();
	            
	        
	        } catch (Exception e) {
	            
	          
	            
	            
	        } 
	        finally
			{
				tm.release();
			}
	}
	
	
	@Test
	public void testNullDestroyTX()
	{
		 TransactionManager tm = new TransactionManager();
	        try {
	            tm.begin(TransactionManager.RW_TRANSACTION);
//	            Connection con = DBUtil.getConection();
//	            con.createStatement();
//	            con.prepareStatement("select 1 from dual");
//	            con = DBUtil.getConection("bspf");
//	            con.prepareStatement("select 1 from dual");
//	            List objects = DBUtil.getTraceObjects();
	            tm.destroyTransaction();
	            
	        
	        } catch (Exception e) {
	            
	            try {
	                tm.rollback();
	            } catch (RollbackException e1) {
	                // TODO Auto-generated catch block
	                e1.printStackTrace();
	            }
	            
	            
	        } 
	        finally
			{
				tm.release();
			}
	}
	
	public static  void testTraceObjects()
	{
		Traces t = new Traces();
		t.start();
		LeakTraces l = new LeakTraces();
		l.start();
		
		l = new LeakTraces();
		l.start();
		
		l = new LeakTraces();
		l.start();
	}
	
	
	
	static class Traces extends Thread
	{

		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			while(true)
			{
				 List objects = DBUtil.getTraceObjects();
				 printTrace( objects);
				 try {
					 synchronized(this)
					 {
						 sleep(10);
					 }
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		public void printTrace( List objects)
		{
			if(objects == null)
				return;
			for(Object obj : objects)
			 {
				 AbandonedTrace trace = (AbandonedTrace)obj;
				 System.out.println("CreateTime:" + trace.getCreateTime());
				 System.out.println("Stack:" );
				 Exception stack = trace.getCreateBy();
				 if(stack != null)
					 trace.getCreateBy().printStackTrace(System.out);
				 List subtraces = trace.getTraces();
				 printTrace( subtraces);
		     }
		}
		
	}
	
	static class LeakTraces extends Thread
	{

		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			boolean flag = false;
			while(true)
			{
				TransactionManager tm = new TransactionManager();
		        try {
		            tm.begin(TransactionManager.RW_TRANSACTION);
		            Connection con = DBUtil.getConection();
		            con.createStatement().close();
		            con.prepareStatement("select 1 from dual").close();
		            con = DBUtil.getConection("mq");
		            con.prepareStatement("select 1 from dual").close();
		          
		            //tm.destroyTransaction();
		            if(flag)
		            	tm.commit();
		            else
		            	flag = true;
		            try {
						 synchronized(this)
						 {
							 sleep(5);
						 }
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        
		        } catch (Exception e) {
		            
		            try {
		                tm.rollback();
		            } catch (RollbackException e1) {
		                // TODO Auto-generated catch block
		                e1.printStackTrace();
		            }
		            
		            
		        } 
			}
		}
		
	}
	public void testTX11() throws Exception
	{
		TransactionManager tm = new TransactionManager();
        try {
            tm.begin();
            
            //����һϵ��db����            
            //�������commit            
            tm.commit();
        }
        catch (Exception e) {            
            throw e;            
        } 
        finally
        {
        	tm.release();
        }
	}
	public void testTX() throws Exception
	{
		TransactionManager tm = new TransactionManager();
        try {
            tm.begin(TransactionManager.RW_TRANSACTION);
            
            //����һϵ��db����
            
            //ע�����RW_TRANSACTION������Բ�����commit������tm.releasenolog()
            //�������ͷ����񣬵���commitҲ����
            
            tm.commit();
        }
        catch (Exception e) {
            
            throw e;
            
        } 
        finally
        {
        	tm.releasenolog();
        }
	}
	@Test
	public void testNoCompaintRWTX() throws Exception
	{
	    TransactionManager tm = new TransactionManager();
        try {
            tm.begin(TransactionManager.RW_TRANSACTION);
            Connection con = DBUtil.getConection();
            log("0",tm.getStatus());
            System.out.println("con.getAutoCommit():"+con.getAutoCommit());
            
            testInnerStatus();
//          tm.begin(tm.NEW_TRANSACTION);
//          DBUtil dbUtil = new DBUtil();
//          dbUtil.executeDelete("sql");
//          innerTest();
            
//          dbUtil.executeDelete("sql");
            tm.commit();
//          tm.commit();
            log("1",tm.getStatus());
        
        } catch (Exception e) {
            e.printStackTrace();
            try {
                tm.rollback();
            } catch (RollbackException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            throw e;
            
        } 
	}
	@Test
	public void testRWTX() throws Exception
    {
        TransactionManager tm = new TransactionManager();
        try {
            tm.begin(TransactionManager.RW_TRANSACTION);
            Connection con = DBUtil.getConection();
            log("0",tm.getStatus());
            System.out.println("testRWTX con.getAutoCommit():"+con.getAutoCommit());            
            
            tm.commit();

            log("1",tm.getStatus());
        
        } catch (Exception e) {
            e.printStackTrace();
            try {
                tm.rollback();
            } catch (RollbackException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            throw e;
            
        } 
    }
	
	
	@Test
    public void testRollbackRWTX() throws Exception
    {
        TransactionManager tm = new TransactionManager();
        try {
            tm.begin(TransactionManager.RW_TRANSACTION);
            Connection con = DBUtil.getConection();
            log("0",tm.getStatus());
            System.out.println("testRWTX con.getAutoCommit():"+con.getAutoCommit());            
            if(true)
                throw new Exception();
            tm.commit();

            log("1",tm.getStatus());
        
        } catch (Exception e) {
            log("2",tm.getStatus());
            e.printStackTrace();
            try {
                tm.rollback();
                log("3",tm.getStatus());
            } catch (RollbackException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            throw e;
            
        } 
    }
	
	public void innerRWTX() throws Exception
	{
	    TransactionManager tm = new TransactionManager();
        try {
            tm.begin(TransactionManager.RW_TRANSACTION);
            Connection con = DBUtil.getConection();
            log("0",tm.getStatus());
            System.out.println("innerRWTX con.getAutoCommit():"+con.getAutoCommit());            
            
            tm.commit();

            log("1",tm.getStatus());
        
        } catch (Exception e) {
            log("2",tm.getStatus());
            e.printStackTrace();
            try {
                tm.rollback();
                log("3",tm.getStatus());
            } catch (RollbackException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            throw e;
            
        } 
	}
	
	@Test
    public void testInnerRWTX() throws Exception
    {
        TransactionManager tm = new TransactionManager();
        try {
            tm.begin(TransactionManager.RW_TRANSACTION);
            Connection con = DBUtil.getConection();
            log("0",tm.getStatus());
            System.out.println("testRWTX con.getAutoCommit():"+con.getAutoCommit());            
            innerRWTX();
            tm.commit();

            log("1",tm.getStatus());
        
        } catch (Exception e) {
            log("2",tm.getStatus());
            e.printStackTrace();
            try {
                tm.rollback();
                log("3",tm.getStatus());
            } catch (RollbackException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            throw e;
            
        } 
    }
	
	/**
	 * 
	 * @param beans
	 * @return
	 */
	public void stringarraytoList(List<ListBean> beans) {
		String sql = "INSERT INTO LISTBEAN (ID,FIELDNAME,FIELDLABLE,FIELDTYPE,SORTORDER,"
				+ " ISPRIMARYKEY,REQUIRED,FIELDLENGTH,ISVALIDATED)VALUES(#[id],#[fieldName],#[fieldLable],#[fieldType],#[sortorder]"
				+ ",#[isprimaryKey],#[required],#[fieldLength],#[isvalidated])";
		TransactionManager tm = new TransactionManager();
		try {
			tm.begin();
			SQLExecutor.delete("delete from LISTBEAN");
			SQLExecutor.insertBeans(sql, beans);
			innertxoperation();
			tm.commit();
		} catch (Exception e) {
			try {
				tm.rollback();
			} catch (RollbackException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}	
	public void innertxoperation()	{
		TransactionManager tm = new TransactionManager();
		try {
			tm.begin();
			String id = SQLExecutor.queryField("select id from LISTBEAN where filename=?","dupduo");
			SQLExecutor.delete("delete from LISTBEAN where id=?", id);
			tm.commit();
		} catch (Exception e) {
			try {
				tm.rollback();
			} catch (RollbackException e1) {				
				e1.printStackTrace();
			}
		}
	}
    
	
	public static void testStatus()
	{
		TransactionManager tm = new TransactionManager();
		try {
			tm.begin();
			DBUtil.getConection();
			log("0",tm.getStatus());
			
			testInnerStatus();
//			tm.begin(tm.NEW_TRANSACTION);
//			DBUtil dbUtil = new DBUtil();
//			dbUtil.executeDelete("sql");
//			innerTest();
			
//			dbUtil.executeDelete("sql");
			tm.commit();
//			tm.commit();
			log("1",tm.getStatus());
		
		} catch (Exception e) {
			e.printStackTrace();
			try {
				tm.rollback();
			} catch (RollbackException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		} 
		
		
		
		
	}
	
	public static void testInnerStatus()
	{

		TransactionManager tm = new TransactionManager();
		try {
			tm.begin();
			log("inner",tm.getStatus());
			
//			DBUtil dbUtil = new DBUtil();
//			dbUtil.executeDelete("sql");
//			dbUtil.executeDelete("sql");
			tm.commit();
			log("inner1",tm.getStatus());
			
			
		
		} catch (Exception e) {
			
				try {
					tm.rollback();
				} catch (RollbackException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	public static void testDeleteTx()
	{
			TransactionManager tm = new TransactionManager();
		
			try {
				tm.begin();
				log("delete befer" ,tm.getStatus());
				
				DBUtil dbUtil = new DBUtil();
				dbUtil.executeDelete("delete from test");
				JDBCTransaction tx = tm.suspend();
				
				dbUtil.executeDelete("delete from test1");
				
				tm.resume(tx);
				log("delete after" ,tm.getStatus());
				tm.commit();
				log("delete commit after" ,tm.getStatus());
				
			} catch (TransactionException e) {
				try {
					tm.rollback();
				} catch (RollbackException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					tm.rollback();
				} catch (RollbackException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			finally
			{
				try {
					tm.commit();
				} catch (RollbackException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
			
		
		
	}
	
	public static void log(String action,int status)
	{
		switch(status)
		{
			case Status.STATUS_ACTIVE:
				
				System.out.println(action + "-����״̬��Status.STATUS_ACTIVE" );
				break;
			case Status.STATUS_COMMITTED:
				System.out.println(action + "-����״̬��Status.STATUS_COMMITTED" );
				break;
			case Status.STATUS_COMMITTING:
				System.out.println(action + "-����״̬��Status.STATUS_COMMITTING" );
				break;
			case Status.STATUS_MARKED_ROLLBACK:
				System.out.println(action + "-����״̬��Status.STATUS_MARKED_ROLLBACK" );
				break;
			case Status.STATUS_NO_TRANSACTION:
				System.out.println(action + "-����״̬��Status.STATUS_NO_TRANSACTION" );
				break;
			case Status.STATUS_ROLLEDBACK:
				System.out.println(action + "-����״̬��Status.STATUS_ROLLEDBACK" );
				break;
			case Status.STATUS_ROLLING_BACK:
				System.out.println(action + "-����״̬��Status.STATUS_ROLLING_BACK" );
				break;
				
			case Status.STATUS_UNKNOWN:
				System.out.println(action + "-����״̬��Status.STATUS_UNKNOWN" );
				break;
		}
	}
	
	public static void testConnection() throws SQLException
	{
		Connection con = null;
		Connection con1 = null;
		Statement stmt = null;
		Statement stmt1 = null;
		try {
			
			con = SQLManager.getInstance().requestConnection();
			con1 = SQLManager.getInstance().requestConnection();
			
			con.setAutoCommit(false);
			stmt = con.createStatement();
			stmt1 = con1.createStatement();
			stmt.executeQuery("select * from test where name='biaoping.yinfasdfasdf' for update");
			//stmt.execute("commit");
//			stmt1.execute("delete from test where name='biaoping.yinfasdfasdf'");//stmt1������ϵͳ����
			stmt.execute("delete from test where name='biaoping.yinfasdfasdf'");//stmt1������ϵͳ����
			//stmt.execute("commit");
			con.commit();
			
			//con1.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try
			{
			
				if(stmt != null)
					stmt.close();
			}
			catch(Exception e)
			{
				
			}
			try
			{
			
				if(stmt1 != null)
				stmt1.close();
			}
			catch(Exception e)
			{
				
			}
			try
			{
				if(con != null)
				{
					con.setAutoCommit(true);
					con.close();
				}
			}
			catch(Exception e)
			{
				
			}
			try
			{
				if(con1 != null)
				{
					con1.setAutoCommit(true);
					con1.close();
				}
			}
			catch(Exception e)
			{
				
			}
			
		}
	}
	

	public static void testCommitStatement()
	{
//		 nextStatement = conn.prepareStatement("COMMIT");
		 TransactionManager tm = new TransactionManager();
		 
			
			try {
				tm.begin();
				log("inner1 bef",tm.getStatus());
				
				//System.out.println(tm.getTransaction().getConnection());
				DBUtil dbUtil = new DBUtil();
				dbUtil.executeDelete("delete from test");
				dbUtil.executeInsert("insert into test(name) values('biaoping.yinfasdfasdf')");
				
				dbUtil.execute("commit");
//				log("inner1 after",tm.getStatus());
//				
//				dbUtil.executeInsert("insert into test1(name) values('biaoping.yin1')");
//				log("inner2 after",tm.getStatus());
//				
//				
//				tm.commit();
//				log("commit",tm.getStatus());
//				
				
				tm.commit();
				
			} catch (TransactionException e) {
				log("rollback1",tm.getStatus());
				
				try {
					tm.rollback();
				} catch (RollbackException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				log("rollback1 after",tm.getStatus());
				e.printStackTrace();
			
			}
				catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log("rollback2",tm.getStatus());
				
				try {
					tm.rollback();
				} catch (RollbackException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				log("rollback2 after",tm.getStatus());
			}
	}
	public static void testInsertTx()
	{
		TransactionManager tm = new TransactionManager();
		
			try {
				tm.begin();
				log("insert bef",tm.getStatus());
				
				System.out.println(tm.getTransaction().getConnection());
				DBUtil dbUtil = new DBUtil();
				dbUtil.executeDelete("delete from test");
				dbUtil.executeDelete("delete from test1");
				
				JDBCTransaction tx = tm.suspend();
				try
				{
					dbUtil.executeInsert("insert into test(name) values('biaoping.yin1111')");
					
					log("insert after",tm.getStatus());
					
					dbUtil.executeInsert("insert into test1(name) values('biaoping.yin12222')");
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				
				tm.resume(tx);
				log("insert after",tm.getStatus());
				
				
				tm.commit();
				log("insert commit",tm.getStatus());
				
				
			
				
			} catch (TransactionException e) {
				log("rollback1",tm.getStatus());
				
				try {
					tm.rollback();
				} catch (RollbackException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				log("rollback1 after",tm.getStatus());
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log("rollback2",tm.getStatus());
				
				try {
					tm.rollback();
				} catch (RollbackException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				log("rollback2 after",tm.getStatus());
			}
			
		
		
	}
	
	public static void testPreparedDeleteTx()
	{
		TransactionManager tm = new TransactionManager();
		
		try {
			//��ʼ����,��begin��commit֮��ĸ������ݿ������������������,�����м�������Ĺ�����ж�,�����жϻָ������񽫼���,
			//��������������ִ�й�������������µ�����,��ǰ���񽫱��ж�
			tm.begin();
			log("delete before",tm.getStatus());
			
			
			DBUtil dbUtil = new DBUtil();
			//ִ������ɾ������,���һ��ʧ����������ͻع�
			dbUtil.executeDelete("delete from test");
			dbUtil.executeDelete("delete from test1");
			//��������,���������ݿ�����������������ֱ�����񱻻ָ�
			JDBCTransaction tx = tm.suspend();
			try
			{
				dbUtil.executeInsert("insert into test1(name) values('biaoping.yin')");
			}
			catch(Exception e)
			{
				
			}
			//�ָ�����,֮������񽫼���֮ǰ���жϵ�������
			tm.resume(tx);
			log("delete after",tm.getStatus());
			
			dbUtil.executeInsert("insert into test1(name) values('biaoping.yin1')");
			
			//�ύ����
			tm.commit();
			log("delete commit",tm.getStatus());
			
			
		
			
		} catch (TransactionException e) {
			log("delete rollback before",tm.getStatus());
			
			try {
				//�ع�����
				tm.rollback();
			} catch (RollbackException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			log("delete rollback after",tm.getStatus());
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log("delete rollback1 before",tm.getStatus());
			
			try {
				tm.rollback();
			} catch (RollbackException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			log("delete rollback1 after",tm.getStatus());
		}
		finally
		{
			try {
				tm.commit();
			} catch (RollbackException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
		
		
	}
	
	public static void testInnerInsertTx()
	{
		TransactionManager tm = new TransactionManager();
		
			try {
				tm.begin();
				log("insert bef",tm.getStatus());
				
				
				DBUtil dbUtil = new DBUtil();
				dbUtil.executeDelete("delete from test");
				dbUtil.executeDelete("delete from test1");
				JDBCTransaction tx = tm.suspend();
				TransactionManager tm1 = new TransactionManager();
				try
				{
					tm1.begin();
					dbUtil.executeInsert("insert into test(name) values('biaoping.yin1111')");
					
					log("insert after",tm.getStatus());
					
					dbUtil.executeInsert("insert into test1(name) values('biaoping.yin12222')");
					tm1.commit();
				}
				catch(Exception e)
				{
					try
					{
						tm1.rollback();
					}
					catch(Exception e1)
					{
						
					}
				}
				tm.resume(tx);
				log("insert after",tm.getStatus());
				
				
				tm.commit();
				log("insert commit",tm.getStatus());
				
				
			
				
			} catch (TransactionException e) {
				log("rollback1",tm.getStatus());
				
				try {
					tm.rollback();
				} catch (RollbackException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				log("rollback1 after",tm.getStatus());
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log("rollback2",tm.getStatus());
				
				try {
					tm.rollback();
				} catch (RollbackException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				log("rollback2 after",tm.getStatus());
			}
			
		
		
	}
	
	public static void testNestDoubleTX()
	{
		TransactionManager tm = new TransactionManager();
		try{
			tm.begin();
			System.out.println("testNestDoubleTX():" + tm.getTransaction().getConnection());
			testDoubleTX();
			tm.commit();
		}
		catch(Exception e)
		{
			try {
				tm.rollback();
			} catch (RollbackException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public static void testNestDoubleTXRollbackOne()
	{
		TransactionManager tm = new TransactionManager();
		try{
			tm.begin();
			System.out.println("testNestDoubleTX():" + tm.getTransaction().getConnection());
			testDoubleTX_RollbackOne();
			tm.commit();
		}
		catch(Exception e)
		{
			try {
				tm.rollback();
			} catch (RollbackException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public static void testNestDoubleTXRollbackTwo()
	{
		TransactionManager tm = new TransactionManager();
		try{
			tm.begin();
			System.out.println("testNestDoubleTX():" + tm.getTransaction().getConnection());
			testDoubleTX_RollbackTwo();
			tm.commit();
		}
		catch(Exception e)
		{
			try {
				tm.rollback();
			} catch (RollbackException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public static void testNestDoubleTXRollbackThree()
	{
		TransactionManager tm = new TransactionManager();
		try{
			tm.begin();
			System.out.println("testNestDoubleTX():" + tm.getTransaction().getConnection());
			testDoubleTX_RollbackTwo();
			tm.rollback();
		}
		catch(Exception e)
		{
			try {
				tm.rollback();
			} catch (RollbackException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public static void testDoubleTX()
	{
		TransactionManager tm = new TransactionManager();
		try{
			tm.begin();
			System.out.println("testDoubleTX() 1:" + tm.getTransaction().getConnection());
			tm.commit();
		}
		catch(Exception e)
		{
			try {
				tm.rollback();
			} catch (RollbackException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		TransactionManager tm1 = new TransactionManager();
		try{
			tm1.begin();
			System.out.println("testDoubleTX() 2:" + tm1.getTransaction().getConnection());
			tm1.commit();
		}
		catch(Exception e)
		{
			try {
				tm1.rollback();
			} catch (RollbackException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public static void testDoubleTX_RollbackOne()
	{
		TransactionManager tm = new TransactionManager();
		try{
			tm.begin();
			System.out.println("testDoubleTX() 1:" + tm.getTransaction().getConnection());
			tm.rollback();
		}
		catch(Exception e)
		{
			try {
				tm.rollback();
			} catch (RollbackException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		TransactionManager tm1 = new TransactionManager();
		try{
			tm1.begin();
			System.out.println("testDoubleTX() 2:" + tm1.getTransaction().getConnection());
			tm1.commit();
		}
		catch(Exception e)
		{
			try {
				tm1.rollback();
			} catch (RollbackException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public static void testDoubleTX_RollbackTwo()
	{
		TransactionManager tm = new TransactionManager();
		try{
			tm.begin();
			System.out.println("testDoubleTX() 1:" + tm.getTransaction().getConnection());
			tm.rollback();
		}
		catch(Exception e)
		{
			try {
				tm.rollback();
			} catch (RollbackException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		TransactionManager tm1 = new TransactionManager();
		try{
			tm1.begin();
			System.out.println("testDoubleTX() 2:" + tm1.getTransaction().getConnection());
			tm1.rollback();
		}
		catch(Exception e)
		{
			try {
				tm1.rollback();
			} catch (RollbackException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	
	public static void testReadRollbackTX()
	{
		TransactionManager tm = new TransactionManager();
		try
		{
			tm.begin();
			DBUtil dbUtil = new DBUtil();
			String id = String.valueOf(dbUtil.executeInsert("insert into test(name) values('biaoping.yin1111')"));
			dbUtil.executeSelect("select * from test where id='" + id +"'");
			System.out.println("before rollback size:" + dbUtil.size());
			tm.rollback();
			dbUtil.executeSelect("select * from test where id='" + id +"'");
			System.out.println("after rollbacke size:" + dbUtil.size());
			
		}
		catch(Exception e)
		{
			try {
				tm.rollback();
			} catch (RollbackException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public static void testReadCommitTX()
	{
		TransactionManager tm = new TransactionManager();
		try
		{
			tm.begin();
			DBUtil dbUtil = new DBUtil();
			String id = String.valueOf(dbUtil.executeInsert("insert into test(name) values('biaoping.yin1111')"));
			dbUtil.executeSelect("select * from test where id='" + id +"'");
			System.out.println("before commit size:" + dbUtil.size());
			tm.commit();
			dbUtil.executeSelect("select * from test where id='" + id +"'");
			System.out.println("after commit size:" + dbUtil.size());
			
		}
		catch(Exception e)
		{
			try {
				tm.rollback();
			} catch (RollbackException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	static boolean flag = false;
	public static void main(String[] args)
	{
//		int i = DBUtil.getNumActive();
//		MainMonitor m = new MainMonitor();
//		m.start();
//		try
//		{
//		testLocalThread();
//		testStatus();
//		testDeleteTx();
//		testInsertTx();
		
		
//		testCommitStatement();
//		try {
//			testConnection();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//		try
//		{
//			TestTransaction.testNestDoubleTX();
//			TestTransaction.testNestDoubleTXRollbackOne();
//			TestTransaction.testNestDoubleTXRollbackTwo();
//			TestTransaction.testNestDoubleTXRollbackThree();
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//		
//		testReadCommitTX();
		testTraceObjects();
		flag = true;
	}
	
	static class MainMonitor extends Thread {
		public void run() {
			while (true) {
				int i = DBUtil.getNumActive();
				int j = DBUtil.getNumIdle();
				
				System.out.println("DBUtil.getNumIdle():" + j);
				System.out.println("DBUtil.getNumActive():" + i);
				System.out.println("flag :" + flag );
				
				if(flag && i == 0)
					break;
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			int i = DBUtil.getNumActive();
			int j = DBUtil.getNumIdle();
			System.out.println("DBUtil.getNumIdle():" + j);
			System.out.println("DBUtil.getNumActive():" + i);
			
		}
	}

}
