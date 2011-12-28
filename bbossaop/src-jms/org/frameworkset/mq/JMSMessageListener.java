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

package org.frameworkset.mq;

import javax.jms.JMSException;
import javax.jms.MessageListener;


/**
 * <p>Title: JMSMessageListener.java</p> 
 * <p>Description: 
 * 	ϵͳ��չ����Ϣ������ ���ṩ���¹�������
 * 	o ���������
 *  commit��rollback
 *  
 *  o ��Ϣ�ظ�������
 *  ���е��������ͨ������
 *  
	protected  RequestDispatcher getRequestDispatcher()
	��ȡ������Ϣ��������з������������ͺͽ��շ���
	
 * </p>
 * <p>bboss workgroup</p>
 * <p>Copyright (c) 2007</p>
 * @Date 2009-11-21 ����05:36:28
 * @author biaoping.yin
 * @version 1.0
 */
public abstract class JMSMessageListener implements MessageListener
{
    private ReceiveDispatcher receivor;

    public ReceiveDispatcher getReceivor()
    {
        return receivor;
    }

    public void setReceivor(ReceiveDispatcher receivor)
    {
        this.receivor = receivor;
    }

    public JMSMessageListener()
    {
        
    }    
    protected void commit() throws JMSException
    {
        this.receivor.commit();
    }
    
    
    protected void rollback() throws JMSException
    {
        this.receivor.rollback();
    }
    
    protected boolean isClientAknownledge() throws JMSException
    {
        return this.receivor.isClientAcknowledge();
    }

}
