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

package org.frameworkset.spi.remote.mina.server;



import java.io.IOException;
import java.net.InetSocketAddress;
import java.security.GeneralSecurityException;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.ssl.SslFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.frameworkset.mq.SSLHelper;
import org.frameworkset.spi.ApplicationContext;
import org.frameworkset.spi.BaseSPIManager;
import org.frameworkset.spi.assemble.ProMap;
import org.frameworkset.spi.remote.RPCAddress;
import org.frameworkset.spi.remote.Target;
import org.frameworkset.spi.remote.mina.MinaUtil;
import org.frameworkset.spi.remote.mina.client.ClinentTransport;


/**
 * <p>Title: MinaRPCServer.java</p> 
 * <p>Description: </p>
 * <p>bboss workgroup</p>
 * <p>Copyright (c) 2007</p>
 * @Date 2009-10-7 ����05:06:40
 * @author biaoping.yin
 * @version 1.0
 */
public class MinaRPCServer 
{
//	public static final int PORT = 33789;
    private static Logger log = Logger.getLogger(MinaRPCServer.class);
	public ProMap conparams = null;
//	public static boolean defaultlongconnection ;
	
	private long CONNECT_TIMEOUT = 30;
        
   
	public long getCONNECT_TIMEOUT()
    {
        return CONNECT_TIMEOUT;
    }
    private boolean started = false;
	private RPCAddress localAddress;
	private String ip = "127.0.0.1";
    
    int PORT = -1;
	public boolean started()
	{
		return this.started;
	}
	public boolean validateAddress(RPCAddress address)
	{
	    //�����жϵ�ַ�Ƿ��ڵ�ַ��Χ��
	    
	    return ClinentTransport.validateAddress(address);
	}
	private static MinaRPCServer server;
	
	public static MinaRPCServer getMinaRPCServer()
	{
		if(server != null)
			return server;
		synchronized(MinaRPCServer.class)
		{
			if(server != null)
				return server;
			server = (MinaRPCServer)BaseSPIManager.getBeanObject("rpc.mina.server");
			
		}
		
		return server;
	}
	
	private static class ShutDownMinaServer implements Runnable
	{
	    MinaRPCServer server;
	    ShutDownMinaServer(MinaRPCServer server)
	    {
	        this.server = server;
	    }
        public void run()
        {
            server.stop();
            
        }
	    
	    
	}
	
	public RPCAddress getLocalAddress()
	{
		return this.localAddress;
	}
	
	public MinaRPCServer(ProMap conparams)
	{
		this.conparams = conparams;
		CONNECT_TIMEOUT = conparams.getInt("connection.timeout",30) * 1000;
		String ip = conparams.getString("connection.bind.ip");
		if(ip != null)
		    this.ip = ip;
		PORT = conparams.getInt("connection.bind.port");
		this.localAddress = new RPCAddress(this.ip,PORT,null,Target.BROADCAST_TYPE_MINA);
	}
	private NioSocketAcceptor acceptor ;
	private java.util.concurrent.locks.ReentrantLock lock = new ReentrantLock();
	public void start() throws MinaRunException
	{
		if(started)
			return;
		else
		{
			lock.lock();
			try
			{
				if(started)
					return;
				// Create a class that handles sessions, incoming and outgoing data
				System.out.println("Start Mina server.....");	
		        RPCServerIoHandler handler = MinaUtil.getRPCServerIoHandler();
		        
		        // This socket acceptor will handle incoming connections
		        acceptor = new NioSocketAcceptor();
		        /**
		         * ����ssl�ļ���֧��
		         */
		        ProMap commons = ApplicationContext.getApplicationContext().getMapProperty("rpc.protocol.mina.params");
		        boolean enablessl = ApplicationContext.getApplicationContext().getMapProperty("rpc.protocol.mina.params").getBoolean("enablessl",false);
		        if(enablessl)
		        {
		            try
		            {
		                ProMap ssls =  ApplicationContext.getApplicationContext().getMapProperty("rpc.protocol.mina.ssl.server");
		                if(ssls == null)
		                {
		                    throw new MinaRunException("������sslģʽ�� ����û��ָ��rpc.protocol.mina.ssl.server �����������ļ�org/frameworkset/spi/manager-rpc-mina.xml�Ƿ���ȷ�����˸ò�����");
		                }
		                String keyStore = ssls.getString("keyStore");
		                String keyStorePassword = ssls.getString("keyStorePassword");
		                String trustStore = ssls.getString("trustStore");
		                String trustStorePassword = ssls.getString("trustStorePassword");
		                SslFilter sslFilter = new SslFilter(SSLHelper.createSSLContext(keyStore, keyStorePassword, trustStore, trustStorePassword));
		                String[] enabledCipherSuites = (String[])commons.getObject("enabledCipherSuites",SSLHelper.enabledCipherSuites);
		                sslFilter.setEnabledCipherSuites(enabledCipherSuites);
		                String[] protocols = (String[])commons.getObject("enabledProtocols");
		                if(protocols != null)
		                    sslFilter.setEnabledProtocols(protocols);
		                boolean needClientAuth = commons.getBoolean("needClientAuth",false);
		                boolean wantClientAuth = commons.getBoolean("wantClientAuth",false);
		                sslFilter.setNeedClientAuth(needClientAuth);
		                sslFilter.setWantClientAuth(wantClientAuth);
		                
		                
		                
		                /** ���ü��ܹ����� **/
		                acceptor.getFilterChain().addLast("SSL", sslFilter);
		            }
		            catch (GeneralSecurityException e)
		            {
		                throw new MinaRunException("������sslģʽ�� �����ļ�org/frameworkset/spi/manager-rpc-mina.xml�Ƿ���ȷ�����˿ͷ��˵�ssl����rpc.protocol.mina.ssl.server��",e);
		            }
		            catch (IOException e)
		            {
		                throw new MinaRunException("������sslģʽ�� �����ļ�org/frameworkset/spi/manager-rpc-mina.xml�Ƿ���ȷ�����˿ͷ��˵�ssl����rpc.protocol.mina.ssl.server��",e);
		            }
		            catch(MinaRunException e)
		            {
		                throw e;
		            }
		            catch (Exception e)
		            {
		                throw new MinaRunException("������sslģʽ�� �����ļ�org/frameworkset/spi/manager-rpc-mina.xml�Ƿ���ȷ�����˿ͷ��˵�ssl����rpc.protocol.mina.ssl.server��",e);
		            }
		        }
		        // add an IoFilter .  This class is responsible for converting the incoming and 
		        // outgoing raw data to ImageRequest and ImageResponse objects
		        acceptor.getFilterChain().addLast("protocol", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
		        
		        // get a reference to the filter chain from the acceptor
		        DefaultIoFilterChainBuilder filterChainBuilder = acceptor.getFilterChain();
		        
		        // add an ExecutorFilter to the filter chain.  The preferred order is to put the executor filter
		        // after any protocol filters due to the fact that protocol codecs are generally CPU-bound
		        // which is the same as I/O filters.
		        filterChainBuilder.addLast("threadPool", new ExecutorFilter(Executors.newCachedThreadPool()));
		        
		        // set this NioSocketAcceptor's handler to the ImageServerHandler
		        acceptor.setHandler(handler);
		        
		        // Bind to the specified address.  This kicks off the listening for 
		        // incoming connections
		        
		        acceptor.bind(new InetSocketAddress(PORT));
		        
		        System.out.println("Mina server is listenig at port " + PORT);
		        System.out.println("Mina server started.");
		        BaseSPIManager.addShutdownHook(new ShutDownMinaServer(this));
		        this.started = true;
			}
			catch(IOException e)
			{
				throw new MinaRunException(e);
			}
			finally
			{
				lock.unlock();
			}
		}
		
	}

    public static void main(String[] args) throws Exception {
    	MinaRPCServer.getMinaRPCServer().start();
//    	Class.forName("String");
    }
    
    public void stop()
    {
        if(!this.started)
            return;
        log.debug("Stop mina server [" + acceptor.getLocalAddress() + "] begin.");
        if(acceptor == null)
            return;
        acceptor.unbind();
        this.started = false;
        log.debug("Stop mina server [" + acceptor.getLocalAddress() + "] end.");
    }
   
}
