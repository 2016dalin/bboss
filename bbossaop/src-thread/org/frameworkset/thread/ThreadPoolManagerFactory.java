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
package org.frameworkset.thread;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.frameworkset.log.BaseLogger;
import org.frameworkset.spi.BaseSPIManager;
import org.frameworkset.spi.assemble.Pro;
import org.frameworkset.spi.assemble.ProMap;

/**
 * 
 * <p>
 * Title: ThreadPoolManagerFactory.java
 * </p>
 * 
 * <p>
 * Description: corePoolSize�� �̳߳�ά���̵߳��������� maximumPoolSize���̳߳�ά���̵߳��������
 * keepAliveTime�� �̳߳�ά���߳�������Ŀ���ʱ�� unit�� �̳߳�ά���߳�������Ŀ���ʱ��ĵ�λ workQueue��
 * �̳߳���ʹ�õĻ������ handler�� �̳߳ضԾܾ�����Ĵ������
 * 
 * 
 * </p>
 * 
 * <p>
 * Copyright (c) 2009
 * </p>
 * 
 * <p>
 * bboss workgroup
 * </p>
 * 
 * @Date May 17, 2009
 * @author biaoping.yin
 * @version 1.0
 */
public class ThreadPoolManagerFactory
{

    /**
     * <property name="transfer.threadpool"> <map> <property name="corePoolSize"
     * value="50"/> <property name="maximumPoolSize" value="50"/> <!--
     * TimeUnit.SECONDS TimeUnit.MICROSECONDS TimeUnit.MILLISECONDS
     * TimeUnit.NANOSECONDS ʱ�䵥λ���������²����� keepAliveTime waitTime
     * delayTime����delayTimeΪ����ʱ������ǰٷֱ�ʱ��Ч�� --> <property name="timeUnit"
     * value="TimeUnit.SECONDS"/> <property name="keepAliveTime" value="10"/>
     * <!--
     * 
     * 
     * LinkedBlockingQueue PriorityBlockingQueue ArrayBlockingQueue
     * SynchronousQueue
     * 
     * --> <property name="blockingQueueType" value="ArrayBlockingQueue"/>
     * <property name="blockingQueue" value="40"/>
     * 
     * <!-- RejectedExecutionHandler
     * ����ʵ��java.util.concurrent.RejectedExecutionHandler�ӿ� Ŀǰϵͳ�ṩ����ȱʡʵ�֣�
     * org.frameworkset.thread.WaitPolicy
     * ѭ���ȴ�event.threadpool.waitTimeָ����ʱ�䣬��λΪ��
     * java.util.concurrent.ThreadPoolExecutor$DiscardPolicy ֱ�Ӷ������񣬲��׳��쳣
     * java.util.concurrent.ThreadPoolExecutor$AbortPolicy
     * ֱ�Ӷ��������׳��쳣RejectedExecutionException
     * java.util.concurrent.ThreadPoolExecutor$CallerRunsPolicy ֱ������
     * java.util.concurrent.ThreadPoolExecutor$DiscardOldestPolicy ������У������ϵ�����ɾ��
     * org.frameworkset.thread.RunRejectPolicy ֱ�����У�����֪ͨ���������ӳ�����ִ������
     * org.frameworkset.thread.RejectRequeuePoliecy ���·��������ջִ������֪ͨ�ⲿ����Ż�����ַ�
     * --> <property name="rejectedExecutionHandler"
     * value="org.frameworkset.thread.RejectRequeuePoliecy"/> <!-- <property
     * name="rejectedExecutionHandler"
     * value="java.util.concurrent.ThreadPoolExecutor$CallerRunsPolicy"/>-->
     * 
     * <!-- ���²���ֻ�������õ�org.frameworkset.thread.WaitPolicy����ʱ����Ҫ���� --> <property
     * name="waitTime" value="1"/> <property name="delayTime" value="20%"/>
     * <property name="maxWaits" value="-1"/> <property name="maxdelayTime"
     * value="4"/> <property name="waitFailHandler" value=
     * "org.frameworkset.mq.transfer.send.SendBigData$WaiterFailedHandler"/>
     * </map> </property>
     */
    private static final Logger log = Logger.getLogger(ThreadPoolManagerFactory.class);

    private static Map<String, InnerThreadPoolExecutor> pools = new HashMap<String, InnerThreadPoolExecutor>();

    private static ProMap<String, Pro> defaultPoolparams = new ProMap<String, Pro>();
    static
    {
        Pro pro = new Pro();
        pro.setName("corePoolSize");pro.setValue("5");
        defaultPoolparams.put("corePoolSize", pro);
        pro = new Pro();
        pro.setName("maximumPoolSize");pro.setValue("10");
        defaultPoolparams.put("maximumPoolSize", pro);
        pro = new Pro();
        pro.setName("keepAliveTime");pro.setValue("30");
        defaultPoolparams.put("keepAliveTime", pro);
        pro = new Pro();
        pro.setName("timeUnit");pro.setValue("TimeUnit.SECONDS");
        defaultPoolparams.put("timeUnit", pro);
        pro = new Pro();
        pro.setName("blockingQueue");pro.setValue("10");
        defaultPoolparams.put("blockingQueue", pro);
        /**
         * DelayQueue LinkedBlockingQueue PriorityBlockingQueue
         * ArrayBlockingQueue SynchronousQueue
         */
        pro = new Pro();
        pro.setName("blockingQueueType");pro.setValue("ArrayBlockingQueue");
        defaultPoolparams.put("blockingQueueType", pro);
        pro = new Pro();
        pro.setName("rejectedExecutionHandler");pro.setValue("org.frameworkset.thread.RejectRequeuePoliecy");
        defaultPoolparams.put("rejectedExecutionHandler", pro);
        pro = new Pro();
        pro.setName("waitTime");pro.setValue("1");
        defaultPoolparams.put("waitTime", pro);
        pro = new Pro();
        pro.setName("delayTime");pro.setValue("20%");
        defaultPoolparams.put("delayTime", pro);
        pro = new Pro();
        pro.setName("maxWaits");pro.setValue("-1");
        defaultPoolparams.put("maxWaits", pro);
        pro = new Pro();
        pro.setName("maxdelayTime");pro.setValue("4");
        defaultPoolparams.put("maxdelayTime", pro);
        BaseSPIManager.addShutdownHook(new ShutdownThreadPools());

    }

    private static class ShutdownThreadPools implements java.lang.Runnable
    {

        public void run()
        {
            Set<Map.Entry<String, InnerThreadPoolExecutor>> sets = pools.entrySet();
            if(sets.size() > 0)
            {
                for(Iterator<Map.Entry<String, InnerThreadPoolExecutor>> it = sets.iterator();it.hasNext();)
                {
                    Map.Entry<String, InnerThreadPoolExecutor> e = it.next();
                    log.debug("Shutdown thread pool["+ e.getKey() +"] begin.");
                    InnerThreadPoolExecutor p = e.getValue();                    
                    p.shutdown();                    
                    log.debug("Shutdown thread pool["+ e.getKey() +"] end.");                    
                }
                
            }
            
        }
        
    }
    public static ThreadPoolExecutor getThreadPoolExecutor(String threadpoolname)
    {
        ThreadPoolExecutor poolExecutor = pools.get(threadpoolname);
        if (poolExecutor != null)
            return poolExecutor;

        synchronized (ThreadPoolManagerFactory.class)
        {
            poolExecutor = pools.get(threadpoolname);
            if (poolExecutor != null)
                return poolExecutor;
            ProMap poolparams = (ProMap) BaseSPIManager.getMapProperty(threadpoolname);
            if (poolparams == null)
            {
                log.debug("Config for [" + threadpoolname + "] not found. defaultPoolparams will be used as follow:\n"
                        + defaultPoolparams.toString());
                poolparams = defaultPoolparams;
            }

            // int corePoolSize = poolparams.get("corePoolSize");
            int corePoolSize = poolparams.getInt("corePoolSize", 5);
            int maximumPoolSize = poolparams.getInt("maximumPoolSize", 10);
            long keepAliveTime = poolparams.getInt("keepAliveTime", 30);
            TimeUnit unit = null;

            String unit_ = poolparams.getString("timeUnit", "TimeUnit.SECONDS");
            if (unit_.equals("TimeUnit.SECONDS"))
                unit = TimeUnit.SECONDS;
            else if (unit_.equals("TimeUnit.MICROSECONDS"))
                unit = TimeUnit.MICROSECONDS;
            else if (unit_.equals("TimeUnit.MILLISECONDS"))
                unit = TimeUnit.MILLISECONDS;
            else if (unit_.equals("TimeUnit.NANOSECONDS"))
                unit = TimeUnit.NANOSECONDS;
            else
            {
                unit = TimeUnit.SECONDS;
            }
            /**
             * 
             * LinkedBlockingQueue PriorityBlockingQueue ArrayBlockingQueue
             * SynchronousQueue
             */
            String blockingQueueType = poolparams.getString("blockingQueueType");

            int blockingQueue = poolparams.getInt("blockingQueue", 10);

            BlockingQueue<Runnable> workQueue = null;
            if (blockingQueueType != null)
            {
                if (blockingQueueType.equals("ArrayBlockingQueue"))
                {
                    workQueue = new java.util.concurrent.ArrayBlockingQueue<Runnable>(blockingQueue);
                }
                else if (blockingQueueType.equals("LinkedBlockingQueue"))
                {
                    workQueue = new java.util.concurrent.LinkedBlockingQueue<Runnable>(blockingQueue);
                }
                else if (blockingQueueType.equals("PriorityBlockingQueue"))
                {
                    workQueue = new java.util.concurrent.PriorityBlockingQueue<Runnable>(blockingQueue);
                }
                else if (blockingQueueType.equals("SynchronousQueue"))
                {
                    workQueue = new java.util.concurrent.SynchronousQueue<Runnable>();
                }
                else workQueue = new java.util.concurrent.ArrayBlockingQueue<Runnable>(blockingQueue);
                // else if(blockingQueueType.equals("DelayQueue"))
                // {
                // workQueue = new java.util.concurrent.DelayQueue<Delayed>();
                // }
            }
            else
            {
                workQueue = new java.util.concurrent.ArrayBlockingQueue<Runnable>(blockingQueue);
            }

            // WaitPolicy WaitPolicy = new WaitPolicy();
            // RejectedExecutionHandler handler = new
            // ThreadPoolExecutor.DiscardOldestPolicy();

            String rejectedExecutionHandler = poolparams.getString("rejectedExecutionHandler",
                    "org.frameworkset.thread.WaitPolicy");
            RejectedExecutionHandler handler = null;
            try
            {

                handler = (RejectedExecutionHandler) Class.forName(rejectedExecutionHandler).newInstance();
            }
            catch (InstantiationException e)
            {
                throw new ThreadException(e);
            }
            catch (IllegalAccessException e)
            {
                throw new ThreadException(e);
            }
            catch (ClassNotFoundException e)
            {
                throw new ThreadException(e);
            }

            poolExecutor = new InnerThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue,
                    handler,threadpoolname);
            ((InnerThreadPoolExecutor) poolExecutor).setPoolparams(poolparams, unit);
            
            pools.put(threadpoolname, (InnerThreadPoolExecutor) poolExecutor);

        }
        return poolExecutor;

    }

    public static class WaitParam
    {
        volatile long initdelayTime = 1 * 1000;

        String delayTime_s;

        int maxWaits = -1;

        boolean isPercent = false;

        long delayTime = 10;

        // int current = 0;
        long maxdelayTime = 20;

        boolean needdelay;

        String waitFailHandlerClass;

        WaitFailHandler waitFailHandler;

        public long getInitdelayTime()
        {
            return initdelayTime;
        }

        public String getDelayTime_s()
        {
            return delayTime_s;
        }

        public int getMaxWaits()
        {
            return maxWaits;
        }

        public boolean isPercent()
        {
            return isPercent;
        }

        public long getDelayTime()
        {
            return delayTime;
        }

        public long getMaxdelayTime()
        {
            return maxdelayTime;
        }

        public boolean isNeeddelay()
        {
            return needdelay;
        }

        public String getWaitFailHandlerClass()
        {
            return waitFailHandlerClass;
        }

        public WaitFailHandler getWaitFailHandler()
        {
            return waitFailHandler;
        }
    }

    public static class InnerThreadPoolExecutor extends ThreadPoolExecutor
    {
        WaitParam waitParam;
        String name;

        boolean inited = false;

        @Override
        public void execute(Runnable command)
        {
            if (command instanceof DelayThread)
            {
                DelayThread t_ = (DelayThread) command;
                t_.init(waitParam);

            }
            super.execute(command);
        }

        private ProMap poolparams = null;

        private TimeUnit timeunit;

        public ProMap getPoolparams()
        {
            return poolparams;
        }

        public long getWaittime(int rejecttimes) throws ThreadException
        {
            // int rejecttimes = this.getRejectTimes();
            if (waitParam.maxWaits > 0)
            {

                if (rejecttimes >= waitParam.maxWaits)
                    throw new ThreadException("Exceed maxtimes[maxTimes=" + waitParam.maxWaits + "]");
            }
            // else
            // {
            // return -1;
            // }
            if (rejecttimes > 0 && waitParam.initdelayTime < waitParam.maxdelayTime
                    && waitParam.initdelayTime < waitParam.maxdelayTime)
            {
                if (waitParam.needdelay)
                {
                    if (waitParam.isPercent)
                    {

                        waitParam.initdelayTime += java.lang.Math.round(waitParam.initdelayTime * waitParam.delayTime
                                * 0.01);
                    }
                    else
                    {
                        waitParam.initdelayTime += waitParam.delayTime;
                    }
                }

            }
            if (waitParam.initdelayTime < waitParam.maxdelayTime)
                return waitParam.initdelayTime;
            else return waitParam.maxdelayTime;

        }

        public boolean busy(RejectCallback rejectcallback, BaseLogger log)
        {
            boolean isbusy = false;
            while (true)
            {
                isbusy = this.uncompledtasks >= this.holder;
                if (isbusy)
                {
                    int rejecttimes = rejectcallback.increamentRejecttimes();
                    long waittime = this.getWaittime(rejecttimes);
                    log
                            .logBasic(rejectcallback.getClass().getName(), "Executer is busy ,Wait for " + waittime
                                    + " ms.");
                    synchronized (rejectcallback)
                    {
                        try
                        {
                            rejectcallback.wait(waittime);
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
                else
                {
                    break;
                }
            }
            return isbusy;

        }

        public void setPoolparams(ProMap poolparams, TimeUnit timeunit)
        {
            this.poolparams = poolparams;

            this.timeunit = timeunit;

            if (this.inited)
                return;
            waitParam = new WaitParam();
            // needdelay = poolparams.getBoolean("needdelay", false);
            if (timeunit == null)
                this.timeunit = TimeUnit.SECONDS;
            waitParam.delayTime_s = poolparams.getString("delayTime");
            waitParam.needdelay = waitParam.delayTime_s != null && !waitParam.delayTime_s.equals("");
            if (waitParam.needdelay)
            {

                try
                {
                    if (waitParam.delayTime_s.endsWith("%"))
                    {
                        waitParam.isPercent = true;
                        waitParam.delayTime = Integer.parseInt(waitParam.delayTime_s.substring(0, waitParam.delayTime_s
                                .length() - 1));
                    }
                    else
                    {
                        waitParam.delayTime = Integer.parseInt(waitParam.delayTime_s);
                        waitParam.delayTime = TimeUnit.MILLISECONDS.convert(waitParam.delayTime, timeunit);
                    }
                }
                catch (Exception e)
                {
                    throw new ThreadException("build delay thread failed: delayTime=" + waitParam.delayTime, e);
                }
            }
            waitParam.initdelayTime = poolparams.getInt("waitTime", 1);
            waitParam.initdelayTime = TimeUnit.MILLISECONDS.convert(waitParam.initdelayTime, timeunit);
            waitParam.maxdelayTime = poolparams.getInt("maxdelayTime", 4);
            waitParam.maxdelayTime = TimeUnit.MILLISECONDS.convert(waitParam.maxdelayTime, timeunit);

            waitParam.maxWaits = poolparams.getInt("maxWaits", -1);
            if (waitParam.maxWaits > 0)
            {
                waitParam.waitFailHandlerClass = poolparams.getString("waitFailHandler");
                if (waitParam.waitFailHandlerClass != null)
                    try
                    {
                        waitParam.waitFailHandler = (WaitFailHandler) Class.forName(waitParam.waitFailHandlerClass)
                                .newInstance();
                    }
                    catch (InstantiationException e)
                    {
                        log.error(e);
                        // e.printStackTrace();
                    }
                    catch (IllegalAccessException e)
                    {
                        log.error(e);
                    }
                    catch (ClassNotFoundException e)
                    {
                        log.error(e);
                    }
            }
            log.info(name + ".initdelayTime:" + waitParam.initdelayTime);
            log.info(name + ".delayTime:" + waitParam.delayTime_s);
            // System.out.println("current:" +current);
            log.info(name + ".maxdelayTime:" + waitParam.maxdelayTime);
            log.info(name + ".maxWaits:" + waitParam.maxWaits);
            this.inited = true;

        }

        public InnerThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler,String name)
        {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
            this.name = name;
            

        }

        public InnerThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                BlockingQueue<Runnable> workQueue, RejectedExecutionHandler handler,String name)
        {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
            this.name = name;

        }

        public InnerThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory,String name)
        {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
            this.name = name;

        }

        public InnerThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                BlockingQueue<Runnable> workQueue,String name)
        {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
            this.name = name;

        }

    }

}
