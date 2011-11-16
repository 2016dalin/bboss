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
package org.frameworkset.spi.transaction;

import java.sql.SQLException;

import com.frameworkset.common.poolman.DBUtil;

public class A1 implements AI{

	public void testTXInvoke(String msg) throws Exception {
		System.out.println("A1:" + msg);
		
		
		DBUtil db = new DBUtil();
		System.out.println("db.getNumIdle():" +db.getNumIdle());
		System.out.println("db.getNumActive():" +db.getNumActive());
		String id = db.getNextStringPrimaryKey("test");
		db.executeInsert("insert into test(id,name) values('"+id+"','testTXInvoke(" + msg +")')");
		db.executeInsert("insert into test1(A) values('testTXInvoke(" + msg +")')");
		System.out.println("1db.getNumActive():" +db.getNumActive());
		System.out.println("1db.getNumIdle():" +db.getNumIdle());
		
	}
	
	public void testTXInvoke() throws SQLException {
		System.out.println("A1.testTXInvoke():no param");
		DBUtil db = new DBUtil();
		System.out.println("db.getNumIdle():" +db.getNumIdle());
		System.out.println("db.getNumActive():" +db.getNumActive());
		String id = db.getNextStringPrimaryKey("test");
		db.executeInsert("insert into test(id,name) values('"+id+"','testTXInvoke()')");
		db.executeInsert("insert into test1(A) values('testTXInvoke')");
		System.out.println("1db.getNumActive():" +db.getNumActive());
		System.out.println("1db.getNumIdle():" +db.getNumIdle());
		
		
	}
	
	public void testNoTXInvoke()
	{
		System.out.println("A1:NoTXInvoke");
		DBUtil db = new DBUtil();
		try {
			String id = db.getNextStringPrimaryKey("test");
			db.executeInsert("insert into test(id,name) values('"+id+"','testTXInvoke()')");
			db.executeInsert("insert into test1(A) values('testTXInvoke()')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public String testTXInvokeWithReturn() {
		System.out.println("call A1.testTXInvokeWithReturn()");
		DBUtil db = new DBUtil();
		try {
			String id = db.getNextStringPrimaryKey("test");
			db.executeInsert("insert into test(id,name) values('"+id+"','testTXInvokeWithReturn()')");
			db.executeInsert("insert into test1(A) values('testTXInvokeWithReturn()')");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "return is A1";
	}
	
	/**
	 * ֻҪ�׳��쳣������ͻع�
	 */
	public String testTXInvokeWithException() throws Exception {
		System.out.println("call A1.testTXInvokeWithException()");
		DBUtil db = new DBUtil();
		
		String id = db.getNextStringPrimaryKey("test");
		db.executeInsert("insert into test(id,name) values('"+id+"','testTXInvokeWithException()')");
		if(true)
			throw new Exception1("A1 throw a exception");
		return "A1 exception find.";
	}

	public void testSameName() throws SQLException {
		System.out.println("call A1.testSameName()");
		DBUtil db = new DBUtil();
		String id = db.getNextStringPrimaryKey("test");
		
		db.executeInsert("insert into test(id,name) values('"+id+"','testSameName()')");
		
		
	}

	public void testSameName(String msg) throws SQLException {
		System.out.println("call A1.testSameName("+msg+")");
		DBUtil db = new DBUtil();
		String id = db.getNextStringPrimaryKey("test");
		db.executeInsert("insert into test(id,name) values('"+id+"','testSameName("+msg+")')");
		
	}

	public void testSameName1() throws SQLException {
		System.out.println("call A1.testSameName1()");
		
		DBUtil db = new DBUtil();
		
		String id = db.getNextStringPrimaryKey("test");
		db.executeInsert("insert into test(id,name) values('"+id+"','testSameName1()')");
		
	}

	public void testSameName1(String msg) throws SQLException {
		System.out.println("call A1.testSameName1(String msg):" + msg);
		DBUtil db = new DBUtil();
		
		String id = db.getNextStringPrimaryKey("test");
		db.executeInsert("insert into test(id,name) values('"+id+"','testSameName1("+msg+")')");
		
	}

	public int testInt(int i) {
		System.out.println("call A1.testInt(int i)��" + i);
		return i;
		
	}
	
	public int testIntNoTX(int i) {
		System.out.println("call A1.testIntNoTX(int i)��" + i);
		return i;		
	}
	
	/**
	 * ����쳣���ԣ�������ʵ���쳣��Ҳ���������ʵ���쳣
	 * ���е��쳣������������ع�
	 */
	public void testTXWithSpecialExceptions(String type) throws Exception
	{
		
		DBUtil db = new DBUtil();
		String id = db.getNextStringPrimaryKey("test");
		db.executeInsert("insert into test(id,name) values('"+id+"','testTXWithSpecialExceptions("+type+")')");	
		//����ع�
		if(type.equals("IMPLEMENTS"))
		{
			throw new RollbackInstanceofException("IMPLEMENTS RollbackInstanceofException");
		}
		
		//����ع�
		if(type.equals("INSTANCEOF"))
		{
			throw new SubRollbackInstanceofException("INSTANCEOF RollbackInstanceofException");
		}
		
		//����ع�
		if(type.equals("exception1"))
		{
			throw new Exception1("IMPLEMENTS exception1");
		}
		/**
		 * ���񲻻�ع���û�н�������
		 */
		if(type.equals("notxexception"))
		{
			throw new Exception3("notxexception Exception3");
		}
		
	}
	
	/**
	 * ֻҪ���ض�ʵ�����쳣�ͻ�ع�
	 * @param type
	 * @throws Exception
	 */
	public void testTXWithInstanceofExceptions(String type) throws Exception
	{
		
		DBUtil db = new DBUtil();
		String id = db.getNextStringPrimaryKey("test");
		db.executeInsert("insert into test(id,name) values('"+id+"','testTXWithInstanceofExceptions(" + type + ")')");
		
		//����ع�
		if(type.equals("IMPLEMENTS"))
		{
			throw new RollbackInstanceofException("IMPLEMENTS RollbackInstanceofException");
		}
		
		//����ع�
		if(type.equals("INSTANCEOF"))
		{
			throw new SubRollbackInstanceofException("INSTANCEOF RollbackInstanceofException");
		}
		/**
		 * ���񲻻�ع����ύ
		 */
		if(type.equals("notxexception"))
		{
			throw new Exception3("notxexception Exception3");
		}
	}
	
	/**
	 * ֻ���쳣�����ʵ���쳣�Ŵ�������Ļع�
	 * @param type
	 * @throws Exception
	 */
	public void testTXWithImplementsofExceptions(String type) throws Exception
	{
		
		DBUtil db = new DBUtil();
		String id = db.getNextStringPrimaryKey("test");
		db.executeInsert("insert into test(id,name) values('"+id+"','testTXWithImplementsofExceptions(" + type + ")')");
		//����ع�
		if(type.equals("IMPLEMENTS"))
		{
			throw new RollbackInstanceofException("IMPLEMENTS RollbackInstanceofException");
		}
		
		//���񲻻�ع����ύ
		if(type.equals("INSTANCEOF"))
		{
			throw new SubRollbackInstanceofException("INSTANCEOF RollbackInstanceofException");
		}
		/**
		 * ���񲻻�ع����ύ
		 */
		if(type.equals("notxexception"))
		{
			throw new Exception3("notxexception Exception3");
		}
	}
	
	public void testPatternTX1(String type) throws Exception
	{
		DBUtil db = new DBUtil();
		String id = db.getNextStringPrimaryKey("test");
		db.executeInsert("insert into test(id,name) values('"+id+"','testPatternTX1(" + type + ")')");
		//����ع�
		if(type.equals("IMPLEMENTS"))
		{
			throw new RollbackInstanceofException("IMPLEMENTS RollbackInstanceofException");
		}
		
		//���񲻻�ع����ύ
		if(type.equals("INSTANCEOF"))
		{
			throw new SubRollbackInstanceofException("INSTANCEOF RollbackInstanceofException");
		}
		/**
		 * ���񲻻�ع����ύ
		 */
		if(type.equals("notxexception"))
		{
			throw new Exception3("notxexception Exception3");
		}
	}
	
	public void testPatternTX2(String type) throws Exception
	{
		DBUtil db = new DBUtil();
		String id = db.getNextStringPrimaryKey("test");
		db.executeInsert("insert into test(id,name) values('"+id+"','testPatternTX2(" + type + ")')");
		//����ع�
		if(type.equals("IMPLEMENTS"))
		{
			throw new RollbackInstanceofException("IMPLEMENTS RollbackInstanceofException");
		}
		
		//���񲻻�ع����ύ
		if(type.equals("INSTANCEOF"))
		{
			throw new SubRollbackInstanceofException("INSTANCEOF RollbackInstanceofException");
		}
		/**
		 * ���񲻻�ع����ύ
		 */
		if(type.equals("notxexception"))
		{
			throw new Exception3("notxexception Exception3");
		}
	}
	
	public void testPatternTX3(String type) throws Exception
	{
		DBUtil db = new DBUtil();
		String id = db.getNextStringPrimaryKey("test");
		db.executeInsert("insert into test(id,name) values('"+id+"','testPatternTX3(" + type + ")')");
		//����ع�
		if(type.equals("IMPLEMENTS"))
		{
			throw new RollbackInstanceofException("IMPLEMENTS RollbackInstanceofException");
		}
		
		//���񲻻�ع����ύ
		if(type.equals("INSTANCEOF"))
		{
			throw new SubRollbackInstanceofException("INSTANCEOF RollbackInstanceofException");
		}
		/**
		 * ���񲻻�ع����ύ
		 */
		if(type.equals("notxexception"))
		{
			throw new Exception3("notxexception Exception3");
		}
	}
	public void testPatternTX4(String type) throws Exception
	{
		DBUtil db = new DBUtil();
		String id = db.getNextStringPrimaryKey("test");
		db.executeInsert("insert into test(id,name) values('"+id+"','testPatternTX4(" + type + ")')");
		//����ع�
		if(type.equals("IMPLEMENTS"))
		{
			throw new RollbackInstanceofException("IMPLEMENTS RollbackInstanceofException");
		}
		
		//���񲻻�ع����ύ
		if(type.equals("INSTANCEOF"))
		{
			throw new SubRollbackInstanceofException("INSTANCEOF RollbackInstanceofException");
		}
		/**
		 * ���񲻻�ع����ύ
		 */
		if(type.equals("notxexception"))
		{
			throw new Exception3("notxexception Exception3");
		}
	}
	
	/**
	 * ���ϵͳ������쳣�������Զ��ع�
	 * ����������������ع��쳣
	 * <method name="testSystemException">	
				<rollbackexceptions>
					<exception class="org.frameworkset.spi.transaction.RollbackInstanceofException" 
					type="IMPLEMENTS"/>
				</rollbackexceptions>
			</method>
			�������׳���ϵͳ����Ŀ�ָ���쳣������������ع�
	 */
	public void testSystemException() throws Exception
	{
		DBUtil db = new DBUtil();
		String id = db.getNextStringPrimaryKey("test");
		db.executeInsert("insert into test(id,name) values('"+id+"','testSystemException()')");
//		throw new java.lang.NullPointerException("��ָ���쳣������ع�");
		
	}

}
