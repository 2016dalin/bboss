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

package org.frameworkset.spi.remote.jms;

import javax.jms.ObjectMessage;

import org.frameworkset.mq.JMSTemplate;
import org.frameworkset.spi.remote.BaseFutureCall;
import org.frameworkset.spi.remote.RPCAddress;
import org.frameworkset.spi.remote.RPCIOHandler;
import org.frameworkset.spi.remote.RPCMessage;
import org.frameworkset.spi.remote.RemoteException;


/**
 * <p>Title: RPCJMSFuture.java</p> 
 * <p>Description: </p>
 * <p>bboss workgroup</p>
 * <p>Copyright (c) 2007</p>
 * @Date 2009-11-11 ����10:45:10
 * @author biaoping.yin
 * @version 1.0
 */
public class RPCJMSFuture extends BaseFutureCall
{
	protected JMSTemplate request ;
	/**
	 * �ͷ��˱�ʶid����Ϊ���������ظ����ͷ��˵���Ӧ��Ϣ�������ʶ
	 */
	protected RPCAddress src_address;
	public RPCJMSFuture(RPCMessage srcmsg, RPCAddress address,
			RPCIOHandler handler,JMSTemplate request ,RPCAddress src_address)
	{

	    super(srcmsg, address, handler);
	    this.request = request;
	    this.src_address = src_address;
	}

	@Override
	protected RPCMessage _call() throws Exception
	{		
		
		ObjectMessage message = this.request.createObjectMessage(); 
		this.srcmsg.setSrc(src_address);
		message.setObject(this.srcmsg);
		message.setJMSCorrelationID(this.address.getServer_uuid());		
		try
		{
		    this.request.send(message);
		}
    	 
        catch(Exception e)
        {
            throw new RemoteException(srcmsg,e); 
        }
		return null;
	}

}
