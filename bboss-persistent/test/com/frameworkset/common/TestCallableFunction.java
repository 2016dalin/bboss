package com.frameworkset.common;

import javax.transaction.RollbackException;

import com.frameworkset.common.poolman.CallableDBUtil;
import com.frameworkset.common.poolman.Record;
import com.frameworkset.orm.transaction.TransactionManager;

public class TestCallableFunction {
	public static void testTest_fWithPositionIndex()
	{
		CallableDBUtil callableDBUtil = new CallableDBUtil();  
		try
		{
			callableDBUtil.prepareCallable("{? = call Test_f(?,?,?)}");
			callableDBUtil.registerOutParameter(1, java.sql.Types.INTEGER);
			callableDBUtil.setInt(2, 10);
			callableDBUtil.registerOutParameter(3, java.sql.Types.VARCHAR);
			callableDBUtil.registerOutParameter(4, java.sql.Types.VARCHAR);
			callableDBUtil.executeCallable();
			System.out.println("ret:" + callableDBUtil.getInt(1));
			System.out.println("name:" + callableDBUtil.getString(3));
			System.out.println("name1:" + callableDBUtil.getString(4));
						
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * ִ�к���ʱ����ͨ��������ʽ�󶨱���
	 */
	public static void testTest_fWithNameIndex()
	{
		CallableDBUtil callableDBUtil = new CallableDBUtil();
		try
		{
			callableDBUtil.prepareCallable("{? = call Test_f(?,?,?)}");
			callableDBUtil.registerOutParameter(1, java.sql.Types.INTEGER);
			callableDBUtil.setInt(2, 10);
			callableDBUtil.registerOutParameter(3, java.sql.Types.VARCHAR);
			callableDBUtil.registerOutParameter(4, java.sql.Types.VARCHAR);
			callableDBUtil.executeCallable();
			System.out.println("ret:" + callableDBUtil.getInt(1));
			System.out.println("name:" + callableDBUtil.getString(3));
			System.out.println("name1:" + callableDBUtil.getString(4));
					
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	public static void testTest_fWithNameIndexForXMLString()
	{
		CallableDBUtil callableDBUtil = new CallableDBUtil();
		try
		{
			callableDBUtil.prepareCallable("{? = call Test_f(?,?,?)}");
			callableDBUtil.registerOutParameter(1, java.sql.Types.INTEGER);
			//������Ĳ���: Ordinal binding and Named binding cannot be combined!
			callableDBUtil.setInt(2, 10);
			callableDBUtil.registerOutParameter(3, java.sql.Types.VARCHAR);
			callableDBUtil.registerOutParameter(4, java.sql.Types.VARCHAR);
			String xmlString = callableDBUtil.executeCallableForXML();
//			System.out.println("name1:" + callableDBUtil.getString("name"));
//			System.out.println("name2:" + callableDBUtil.getString("name1"));
			System.out.println("xmlString:" + xmlString);
					
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * ���ں����ĵ���ʹ��ʹ��˳��λ�ñ�ʶ���󶨱���
	 */
	public static void testTest_fWithNameIndexForXMLStringRowHandler()
	{
		CallableDBUtil callableDBUtil = new CallableDBUtil();
		try
		{
			callableDBUtil.prepareCallable("{? = call Test_f(?,?,?)}");
			//������Ĳ���: Ordinal binding and Named binding cannot be combined!
			callableDBUtil.registerOutParameter(1, java.sql.Types.INTEGER);
			callableDBUtil.setInt(2, 10);
			callableDBUtil.registerOutParameter(3, java.sql.Types.VARCHAR);
			callableDBUtil.registerOutParameter(4, java.sql.Types.VARCHAR);
			String xmlString = callableDBUtil.executeCallableForXML(new com.frameworkset.common.poolman.handle.RowHandler()
			{
				/**
				 * ���Ѿ�����õ��м�¼���д�����߼�
				 * @param rowValue
				 */
				public void handleRow(Object rowValue,Record record)
				{
					StringBuffer objects = (StringBuffer)rowValue;
					objects.append(record);
					System.out.println("objects:" + objects);
					
				}
			});
//			System.out.println("name1:" + callableDBUtil.getString("name"));
//			System.out.println("name2:" + callableDBUtil.getString("name1"));
			System.out.println("xmlString:" + xmlString);
					
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	public static void testTest_fWithNameIndexForObject()
	{
		CallableDBUtil callableDBUtil = new CallableDBUtil();
		try
		{
			
			callableDBUtil.prepareCallable("{? = call Test_f(?,?,?)}");
			callableDBUtil.registerOutParameter(1, java.sql.Types.INTEGER);
			callableDBUtil.setInt(2, 10);
			callableDBUtil.registerOutParameter(3, java.sql.Types.VARCHAR);
			callableDBUtil.registerOutParameter(4, java.sql.Types.VARCHAR);
			Test_f tets = (Test_f)callableDBUtil.executeCallableForObject(Test_f.class,new com.frameworkset.common.poolman.handle.RowHandler()
			{

				public void handleRow(Object rowValue,Record record) {
					Test_f objects = (Test_f)rowValue;
					try
					{
						objects.setRet(record.getString(1));
						objects.setName(record.getString(3));
						objects.setName1(record.getString(4));
					}
					catch(Exception e)
					{
						
					}
//					origine.put(new Integer(4), "55");
					System.out.println("rowValue:" + rowValue);
					
				}
				
			}
			);
			
			
//			System.out.println("name1:" + callableDBUtil.getString("name"));
//			System.out.println("name2:" + callableDBUtil.getString("name1"));
			System.out.println("Test_f is " + tets);
					
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
	
	/**
	 * ��������֤���洢���������û�жԲ���/ɾ��/�޸Ĳ����������ύ���ع�ʱ������ͨ��poolman
	 * ������������������;����洢�����жԲ���/ɾ��/�޸Ĳ����Ѿ����������ύ���߻ع�ʱ����ôӦ�õ�����ʹ洢������
	 * �е�������Ƿ������������ 
	 * @param i Ϊ0ʱ�ع�����1ʱ�ύ����
	 */
	public static void testTest_fWithNameIndexForObjectTx(int i)
	{
		
		TransactionManager tm = new TransactionManager();
		try
		{
			tm.begin();
			CallableDBUtil callableDBUtil = new CallableDBUtil();
			callableDBUtil.prepareCallable("{? = call Test_f(?,?,?)}");
			callableDBUtil.registerOutParameter(1, java.sql.Types.INTEGER);
			//������Ĳ���: Ordinal binding and Named binding cannot be combined!
			
			callableDBUtil.setInt(2, 10);
			callableDBUtil.registerOutParameter(3, java.sql.Types.VARCHAR);
			callableDBUtil.registerOutParameter(4, java.sql.Types.VARCHAR);
			Test_f tets = (Test_f)callableDBUtil.executeCallableForObject(Test_f.class);
			
			
//			System.out.println("name1:" + callableDBUtil.getString("name"));
//			System.out.println("name2:" + callableDBUtil.getString("name1"));
			System.out.println("Test_f is " + tets);
			callableDBUtil.executeInsert("insert into test(id,name) values('11','name11')");
			if(i == 0)
				tm.rollback();
			else
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
			e.printStackTrace();
		}		
	}
	
	public static void main(String[] args)
	{
		System.out.println("1------------------------------------------testTest_fWithNameIndexForObject()");
		testTest_fWithNameIndexForObject();
		System.out.println("2------------------------------------------testTest_fWithNameIndexForObjectTx(1)");
		testTest_fWithNameIndexForObjectTx(1);
		System.out.println("3------------------------------------------testTest_fWithPositionIndex()");
		testTest_fWithPositionIndex();
		System.out.println("4------------------------------------------testTest_fWithNameIndexForXMLString()");
		testTest_fWithNameIndexForXMLString();
		System.out.println("5------------------------------------------testTest_fWithNameIndexForXMLStringRowHandler()");
		testTest_fWithNameIndexForXMLStringRowHandler();
		
		CallableDBUtil.debugStatus();
	}
}
