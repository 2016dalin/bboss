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

package org.frameworkset.spi.assemble;

import org.frameworkset.netty.NettyRPCServer;
import org.frameworkset.spi.ApplicationContext;
import org.frameworkset.spi.assemble.callback.WebDocbaseAssembleCallback;
import org.frameworkset.spi.remote.RPCTestInf;
import org.junit.Test;

/**
 * <p>Title: TestApplicationContextLoader.java</p> 
 * <p>Description: </p>
 * <p>bboss workgroup</p>
 * <p>Copyright (c) 2007</p>
 * @Date 2010-10-4 ����06:42:54
 * @author biaoping.yin
 * @version 1.0
 */
public class TestApplicationContextLoader {
	@Test
	public void testWebApplicationcontext()
	{
		AssembleUtil.registAssembleCallback(new WebDocbaseAssembleCallback("D:/workspace/bbossgroup-2.0-RC2/bbossaop/"));
		ApplicationContext context = ApplicationContext.getApplicationContext();
	}
	@Test
	public void testWebApplicationClient()
	{
			//�������з����ʱһ��Ҫ�ڳ�ʼ��ApplicationContext֮ǰ����������䣬����ָ��webbase·��,����ϵͳ���޷��ҵ�ͨ��docbase��������
	    	AssembleUtil.registAssembleCallback(new WebDocbaseAssembleCallback("D:/workspace/bbossgroup-2.0-RC2/bbossaop/"));
	        NettyRPCServer.getNettyRPCServer().start();
	      //�������пͷ���ʱһ��Ҫ�ڳ�ʼ��ApplicationContext֮ǰ����������䣬����ָ��webbase·��,����ϵͳ���޷��ҵ�ͨ��docbase��������
//	        AssembleUtil.registAssembleCallback(new WebDocbaseAssembleCallback("D:/workspace/bbossgroup-2.0-RC2/bbossaop/")); 

	        ApplicationContext context = ApplicationContext.getApplicationContext();
			RPCTestInf testInf = (RPCTestInf)context.getBeanObject("(netty::172.16.7.108:12347)/rpc.test?user=admin&password=123456&server_uuid=���");
			
			for(int i = 0; i < 10; i ++)
			{
				Object ret = testInf.getParameter();
				
				
					System.out.println("ret_1186: = "+ret);
				
//				Object ret_1186 = context.getRPCResult(0, ret);
//		        Object ret_1187 = context.getRPCResult(1, ret);
//		        Object ret_1188 = context.getRPCResult(2, ret);
//		        Object ret_1189 = context.getRPCResult(3, ret,Target.BROADCAST_TYPE_JRGOUP);
				
//				System.out.println("ret_1187:" + i + " = "+ret_1187);
//				System.out.println("ret_1188:" + i + " = "+ret_1188);
//				System.out.println("ret_1189:" + i + " = "+ret_1189);
			}
	    
	}
	@Test
	public void testApplicationcontext()
	{
		ApplicationContext context = ApplicationContext.getApplicationContext();
		
	}

}
