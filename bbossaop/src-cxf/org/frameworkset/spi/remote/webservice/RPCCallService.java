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
package org.frameworkset.spi.remote.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import org.frameworkset.spi.remote.RPCMessage;

/**
 * 
 * <p>Title: RPCCallService.java</p> 
 * <p>Description: </p>
 * <p>bboss workgroup</p>
 * <p>Copyright (c) 2008</p>
 * @Date 2009-11-4 ����05:04:55
 * @author biaoping.yin
 * @version 1.0
 */
@WebService(name="RPCCallService", targetNamespace="http://webservice.remote.spi.frameworkset.org/")
public interface RPCCallService {

	@WebMethod(operationName="sendRPCMessage", action="urn:SendRPCMessage")
	@RequestWrapper(className="org.frameworkset.spi.remote.webservice.SendRPCMessage", localName="sendRPCMessage", targetNamespace="http://webservice.remote.spi.frameworkset.org/")
	@ResponseWrapper(className="org.frameworkset.spi.remote.webservice.SendRPCMessageResponse", localName="sendRPCMessageResponse", targetNamespace="http://webservice.remote.spi.frameworkset.org/")
	public RPCMessage sendRPCMessage(@WebParam(name="message") RPCMessage message)  throws Exception;

}