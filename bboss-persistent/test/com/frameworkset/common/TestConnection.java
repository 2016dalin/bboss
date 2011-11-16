package com.frameworkset.common;

import java.sql.Connection;
import java.sql.SQLException;

import com.frameworkset.common.poolman.DBUtil;

public class TestConnection {
	static long max = 0;
	public static void getConntectionTest()
	{
		try {
			long s = System.currentTimeMillis();
			Connection con = DBUtil.getConection();
			long end = System.currentTimeMillis();
			max = max < (end - s)?(end -s):max;
			System.out.println("��ȡconnection��ʱ��" + (end - s)  + "����.");
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public static void main(String[] args)
	{
		try {
			long s = System.currentTimeMillis();
			System.out.println("��ȡconnectionǰ���У�" +DBUtil.getNumIdle()  + "��.");
			Connection con = DBUtil.getConection();
			System.out.println("��ȡconnection��ʱ���У�" +DBUtil.getNumIdle()  + "��.");
			Connection con1 = DBUtil.getConection();
			long end = System.currentTimeMillis();
			System.out.println("��ȡconnection1��ʱ���У�" +DBUtil.getNumIdle()  + "��.");
			con.close();
			con1.close();
			System.out.println("��ȡconnection1����У�" +DBUtil.getNumIdle()  + "��.");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		try {
//			Class r = Runtime.getRuntime().getClass();
//			java.lang.reflect.Method m = r.getDeclaredMethod(
//					"addShutdownHook", new Class[] { Thread.class });
//			m.invoke(Runtime.getRuntime(), new Object[] { new Thread(
//					new ShutDown()) });
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		for(int i = 0; i < 10; i ++)
//		{
//			T t = new T();
//			t.start();
//		}
	}
	
	
	
	static class T extends Thread
	{
		public void run()
		{
			int i = 0;
			while(i < 100)
			{
				i ++ ;
				getConntectionTest();
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	static class ShutDown implements Runnable
	{

		public void run() {
			System.out.println("��ȡ���ӵ��ʱ�䣺" + max + "����.");
			
		}
		
	}

}
