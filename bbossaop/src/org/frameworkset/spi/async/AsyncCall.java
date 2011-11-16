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

package org.frameworkset.spi.async;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;

import org.apache.log4j.Logger;
import org.frameworkset.spi.ApplicationContext;
import org.frameworkset.spi.assemble.ProMap;
import org.frameworkset.spi.async.annotation.Result;
import org.frameworkset.util.TimeUtil;

/**
 * <p>
 * Title: AsyncCall.java
 * </p>
 * <p>
 * Description: ִ�з�����첽����
 * </p>
 * <p>
 * bboss workgroup
 * </p>
 * <p>
 * Copyright (c) 2007
 * </p>
 * 
 * @Date 2011-4-21 ����11:06:42
 * @author biaoping.yin
 * @version 1.0
 */
public class AsyncCall {
	private static Logger log = Logger.getLogger(AsyncCall.class);
	// static java.util.concurrent.ThreadPoolExecutor executor = null;
	private java.util.concurrent.ThreadPoolExecutor callexecutor = null;
	private java.util.concurrent.ThreadPoolExecutor callbackexecutor = null;
	private Object lock = new Object();
	private boolean started = false;
	private CallHandler callHandler;
	private CallBackHandler callBackHandler;
	/**
	 * ������ö�ջ
	 */
	private LinkedBlockingQueue<CallService> callblockqueue = new java.util.concurrent.LinkedBlockingQueue<CallService>(
			ApplicationContext.getApplicationContext().getIntProperty(
					"component.asynccall.block.size", 200));
	/**
	 * ������Ӧ�ص������ջ
	 */
	private LinkedBlockingQueue<CallService> callbackblockqueue = new java.util.concurrent.LinkedBlockingQueue<CallService>(
			ApplicationContext.getApplicationContext().getIntProperty(
					"component.asynccallback.block.size", 200));

	public void start() {
		if (this.started)
			return;
		synchronized (lock) {
			if (this.started)
				return;
			initCallExecutor();
			initCallBackExecutor();
			callHandler = new CallHandler();
			new Thread(this.callHandler, "callHandler").start();
			callBackHandler = new CallBackHandler();
			new Thread(this.callBackHandler, "callBackHandler").start();
			this.started = true;
		}
	}

	public void stop() {
		synchronized (lock) {
			if (!this.started)
				return;
			stopCallExecutor();
			stopCallBackExecutor();
			started = false;
		}
	}

	private void stopCallExecutor() {
		if (this.callexecutor != null)
			this.callexecutor.shutdown();

	}

	private void stopCallBackExecutor() {
		if (this.callbackexecutor != null)
			this.callbackexecutor.shutdown();
	}

	private void initCallExecutor() {
		ProMap proMap = ApplicationContext.getApplicationContext()
				.getMapProperty("component.asynccall.threadpool");
		if (proMap == null || proMap.size() == 0) {
			/**
			 * public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
			 * long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable>
			 * workQueue, RejectedExecutionHandler handler)
			 */
			callexecutor = new java.util.concurrent.ThreadPoolExecutor(5, 20,
					40, TimeUnit.SECONDS,
					new java.util.concurrent.LinkedBlockingQueue<Runnable>(
							ApplicationContext.getApplicationContext()
									.getIntProperty(
											"component.asynccall.block.size",
											200) / 2), new CallerRunsPolicy());
		} else {
			String timeUnit = proMap.getString("timeUnit", "TimeUnit.SECONDS");
			TimeUnit timeUnit_ = TimeUtil.getTimeUnitByName(timeUnit, TimeUnit.SECONDS);

			callexecutor = new java.util.concurrent.ThreadPoolExecutor(proMap
					.getInt("corePoolSize", 5), proMap.getInt(
					"maximumPoolSize", 20), proMap.getInt("keepAliveTime", 40),
					timeUnit_,
					new java.util.concurrent.LinkedBlockingQueue<Runnable>(
							ApplicationContext.getApplicationContext()
									.getIntProperty(
											"component.asynccall.block.size",
											200) / 2), new CallerRunsPolicy());
		}
	}

	private void initCallBackExecutor() {
		ProMap proMap = ApplicationContext.getApplicationContext()
				.getMapProperty("component.asynccallback.threadpool");
		if (proMap == null || proMap.size() == 0) {
			/**
			 * public ThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
			 * long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable>
			 * workQueue, RejectedExecutionHandler handler)
			 */
			callbackexecutor = new java.util.concurrent.ThreadPoolExecutor(
					5,
					20,
					40,
					TimeUnit.SECONDS,
					new java.util.concurrent.LinkedBlockingQueue<Runnable>(
							ApplicationContext
									.getApplicationContext()
									.getIntProperty(
											"component.asynccallback.block.size",
											200) / 2), new CallerRunsPolicy());
		} else {
			String timeUnit = proMap.getString("timeUnit", "TimeUnit.SECONDS");
			TimeUnit timeUnit_ = TimeUtil.getTimeUnitByName(timeUnit, TimeUnit.SECONDS);

			callbackexecutor = new java.util.concurrent.ThreadPoolExecutor(
					proMap.getInt("corePoolSize", 5),
					proMap.getInt("maximumPoolSize", 20),
					proMap.getInt("keepAliveTime", 40),
					timeUnit_,
					new java.util.concurrent.LinkedBlockingQueue<Runnable>(
							ApplicationContext
									.getApplicationContext()
									.getIntProperty(
											"component.asynccallback.block.size",
											200) / 2), new CallerRunsPolicy());
		}
	}

	public boolean started() {
		return this.started;
	}

	public void putCallService(CallService callService)
			throws InterruptedException {
		callblockqueue.put(callService);
	}

	public void putCallBackService(CallService callService)
			throws InterruptedException {
		this.callbackblockqueue.put(callService);
	}

	public Object runCallService(CallService task) throws Exception {
		if (!this.started())
			throw new AsyncCallException("�첽���÷����Ѿ���ֹͣ���ܾ������µ��첽�������");
		if (task != null) {
			try {
				/**
				 * �����Ҫ���ؽ������ô����Ƿ�ص���ʽ��������ǻص���ʽ����ô�����������߳�ģʽ������ǲ����첽���÷�ʽ
				 * �������Ҫ���ؽ������ôֱ�Ӳ����첽���÷�ʽ
				 */
				if (task.getAsyncMethod().getAsyncResultMode() == Result.YES) {
					if (task.getAsyncMethod().getAsyncCallback() == null) {
						if (task.getAsyncMethod().getAsyncTimeout() > 0) {
							FutureTask f = new FutureTask(task);
							callexecutor.execute(f);
							return f.get(task.getAsyncMethod()
									.getAsyncTimeout(), TimeUnit.MICROSECONDS);
						} else // ���ֲ����ó�ʱֱ�������ķ�ʽû��ʵ�����壬��ͬ������һ��
						{
							FutureTask f = new FutureTask(task);
							callexecutor.execute(f);
							return f.get();
						}
					} else {//�лص��������첽����,�������������첽����
						this.putCallBackService(task);
					}
				} else {//����Ҫ����ֵ���첽�����������������첽������ֹ�󲢷�������Դ���ľ�
					this.putCallService(task);
				}
			} catch (Exception e) {
				throw e;
			}

			return null;
		}
		else 
			throw new AsyncCallException("�첽�������ʧ�ܣ�����Ϊnull");
	}

	
	/**
	 * ����Ҫ���ؽ����ģʽ��Ҳû�лص�����������ָ����ʱʱ�䣬���û��ָ����run������ͬ������
	 * �����첽���ã���ʱʱ���쳣��¼��־(���ֳ�ʱû���ر�����壬ֻ�Ǹ���ϵͳ˵������֮�ǳ�������ʱ����)
	 * <p>Title: AsyncCall.java</p> 
	 * <p>Description: </p>
	 * <p>bboss workgroup</p>
	 * <p>Copyright (c) 2007</p>
	 * @Date 2011-4-21 ����03:58:56
	 * @author biaoping.yin
	 * @version 1.0
	 */
	public static class AsynRunnable implements Runnable
	{
		
		CallService task; 
		AsynRunnable(CallService task)
		{
			this.task = task;
		}

		public void run() {
			try {	
				if(task.getAsyncMethod().getAsyncTimeout() <= 0)
				{
					task.call();
				}
				else
				{
					FutureTask t = new FutureTask(task);
					new Thread(t).start();
					t.get(task.getAsyncMethod().getAsyncTimeout(), TimeUnit.MICROSECONDS);
				}
			} catch (Exception e) {				
				log.error(e);
			}
		}
	}
	
	public static class AsynCallbackRunnable implements Runnable
	{
		CallService task; 
		AsynCallbackRunnable(CallService task)
		{
			this.task = task;
		}

		public void run() {
			try {
				
				if(task.getAsyncMethod().getAsyncTimeout() <= 0)
				{
					final Object ret = task.call();
					/**
					 * �Ե��ý�������첽��ʽ���лص�����
					 */
						
						new Thread(new Runnable(){
							public void run()
							{
								task.getCallBackService().getCallBack().handleResult(ret);
							}
						}).start();
					
				}
				else
				{
					FutureTask t = new FutureTask(task);
					new Thread(t).start();
					final Object ret = t.get(task.getAsyncMethod().getAsyncTimeout(), TimeUnit.MICROSECONDS);
					/**
					 * �Ե��ý�������첽��ʽ���лص�����
					 */
					new Thread(new Runnable(){
						public void run()
						{
							task.getCallBackService().getCallBack().handleResult(ret);
						}
					}).start();
				}
			} 
			catch (InvocationTargetException e) {			
				
				final Throwable throwable = e.getTargetException();
				new Thread(new Runnable(){
					public void run()
					{
						task.getCallBackService().getCallBack().handleError(throwable);
					}
				}).start();
			}
			catch (Exception e) {			
				
				final Throwable throwable = e;
				new Thread(new Runnable(){
					public void run()
					{
						task.getCallBackService().getCallBack().handleError(throwable);
					}
				}).start();
			}
			
		}
	}
	class CallHandler implements Runnable {

		public void run() {
			CallService callService = null;
			while (true) {

//				if (!AsyncCall.this.started())
//					break;
				try {
					
						callService = callblockqueue.take();
						callexecutor.execute(new AsynRunnable(callService));
					
					
				} catch (Exception e) {
					log.error(e);
					continue;
				}

			}

		}

	}

	class CallBackHandler implements Runnable {

		public void run() {
			CallService callService = null;
			while (true) {
//				if (!AsyncCall.this.started())
//					break;
				try {
					callService = callbackblockqueue.take();
					callbackexecutor.execute(new AsynCallbackRunnable(callService));
					
				} catch (Exception e) {
					log.error(e);
					continue;
				}

			}
		}

	}

}
