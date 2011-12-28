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

package org.frameworkset.spi.serviceid;

import java.util.List;

import org.frameworkset.spi.remote.RPCAddress;
import org.frameworkset.spi.remote.ServiceID;
import org.frameworkset.spi.serviceidentity.ServiceIDImpl;
import org.frameworkset.spi.serviceidentity.TargetImpl;
import org.jgroups.blocks.GroupRequest;
import org.junit.Test;


/**
 * <p>Title: TestServiceID.java</p> 
 * <p>Description: </p>
 * <p>bboss workgroup</p>
 * <p>Copyright (c) 2007</p>
 * @Date 2009-11-7 ����11:24:33
 * @author biaoping.yin
 * @version 1.0
 */
public class TestServiceID
{
	public static void main(String[] args)
	{
//		testBuildWebServiceURL();
	    buildAllTargets();
	}
	
	@Test
	public void testRESTFulServiceID()
	{
	    String serviceid = "(a|b|c)/serviceid";
	    
	    ServiceID id = new ServiceIDImpl(serviceid, null, GroupRequest.GET_ALL, ServiceID.result_object, 0,
                ServiceID.PROVIDER_BEAN_SERVICE,null);
        System.out.println(id.getService());
        System.out.println(id.getTarget().getTargets());
	}
	
	/**
	 * 	<!-- 
			ָ��Զ��ͨѶ��Ĭ��Э�飺mina,
							   jgroup,
							   jms,
							   webservice
							   rmi
							   ejb
							   corba
			һ��������Զ��������ʵ�ַ��ʵ�����£�
			protocol::ip:port/serviceid
			���Ӧ�ó���û��ָ��protocolͷ�����磺
			ip:port/serviceid
			��ʹ��rpc.default.protocolָ����Э��
		 -->			      
		<property name="rpc.default.protocol" 
					      value="mina"/>
	 */
	public static void defaultServiceID()
	{
		//Ĭ�ϵ�Զ�̷�����ñ�ʶ,��Э���ɿ�����������õ�ȱʡ����Э�������
		
        String serviceid = "(192.168.0.17:1010;192.168.0.18:1020)/serviceid";        
        ServiceID id = new ServiceIDImpl(serviceid, null, GroupRequest.GET_ALL, ServiceID.result_object, 0,
                                     ServiceID.PROVIDER_BEAN_SERVICE,null);
                             System.out.println(id);
        
	}
	
	public static void webserviceServiceID()
	{
		 //webserviceԶ�̷�����ñ�ʶ
		String serviceid = "(webservice::http://192.168.0.17:1010/webroot/;http://192.168.0.17:1010/webroot/)/serviceid";
                ServiceID id = new ServiceIDImpl(serviceid, null, GroupRequest.GET_ALL, ServiceID.result_object, 0,
                                             ServiceID.PROVIDER_BEAN_SERVICE,null);
                System.out.println(id);
                             
	}
	
	public static void testBuildWebServiceURL()
	{
		 //webserviceԶ�̷�����ñ�ʶ
//		String serviceid = "(webservice::http://192.168.0.17:1010/webroot;http://192.168.0.17:1011/webroot/)/serviceid";
		String serviceid = "(webservice::http://192.168.0.17:1010;http://192.168.0.17:1011/)/serviceid";
        ServiceID id = new ServiceIDImpl(serviceid, null, GroupRequest.GET_ALL, ServiceID.result_object, 0,
                                     ServiceID.PROVIDER_BEAN_SERVICE,null);
        List<RPCAddress> rpcAddresses = id.getTarget().getTargets();
        for(RPCAddress address :rpcAddresses)
        {
        	System.out.println(TargetImpl.buildWebserviceURL(address));
        }
                             
	}
	
	public static void minaServiceID()
	{
		 //minaԶ�̷�����ñ�ʶ
		String serviceid = "(mina::192.168.0.17:1010;192.168.0.17:1011)/serviceid";
        ServiceID id = new ServiceIDImpl(serviceid, null, GroupRequest.GET_ALL, ServiceID.result_object, 0,
                                     ServiceID.PROVIDER_BEAN_SERVICE,null);
        System.out.println(id);
                             
	}
	
	
	public static void jgroupServiceID()
	{
		 //jgroupԶ�̷�����ñ�ʶ
		String serviceid = "(jgroup::192.168.0.17:1010;192.168.0.17:1011)/serviceid";
        ServiceID id = new ServiceIDImpl(serviceid, null, GroupRequest.GET_ALL, ServiceID.result_object, 0,
                                     ServiceID.PROVIDER_BEAN_SERVICE,null);
        System.out.println(id);
                             
	}
	
	public static void rmiServiceID()
	{
		 //rmiԶ�̷�����ñ�ʶ
		String serviceid = "(rmi::192.168.0.17:1010;192.168.0.17:1011)/serviceid";
        ServiceID id = new ServiceIDImpl(serviceid, null, GroupRequest.GET_ALL, ServiceID.result_object, 0,
                                     ServiceID.PROVIDER_BEAN_SERVICE,null);
        System.out.println(id);
                             
	}
	
	public static void ejbServiceID()
	{
		 //ejbԶ�̷�����ñ�ʶ
		String serviceid = "(ejb::192.168.0.17:1010;192.168.0.17:1011)/serviceid";
        ServiceID id = new ServiceIDImpl(serviceid, null, GroupRequest.GET_ALL, ServiceID.result_object, 0,
                                     ServiceID.PROVIDER_BEAN_SERVICE,null);
        System.out.println(id);
                             
	}
	
	public static void corbaServiceID()
	{
		 //corbaԶ�̷�����ñ�ʶ
		String serviceid = "(corba::192.168.0.17:1010;192.168.0.17:1011)/serviceid";
        ServiceID id = new ServiceIDImpl(serviceid, null, GroupRequest.GET_ALL, ServiceID.result_object, 0,
                                     ServiceID.PROVIDER_BEAN_SERVICE,null);
        System.out.println(id);
                             
	}
	
	public static void buildAllTargets()
	{
	    List<RPCAddress> dests = TargetImpl.buildAllTargets("127.0.0.1:12347;127.0.0.1:12346", "mina");
	    System.out.println("dests:" + dests);
	    
	    List<RPCAddress> wsdests = TargetImpl.buildAllTargets("http://127.0.0.1:12347/rpcws;http://127.0.0.1:12346/rpcws", "webservice");
            System.out.println("wsdests:" + wsdests);
	}
}
