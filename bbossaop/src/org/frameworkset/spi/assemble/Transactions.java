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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.frameworkset.spi.UNmodify;

/**
 * 
 * 
 * <p>
 * Title: Transactions.java
 * </p>
 * 
 * <p>
 * Description: ��װҵ���������������,ҵ��������������� �����������ݣ� Ҫ����������Ƶķ����� ���������б� ��������ع����쳣�嵥
 * ����������������
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * 
 * <p>
 * bboss workgroup
 * </p>
 * 
 * @Date Jul 30, 2008 9:19:00 AM
 * @author biaoping.yin,����ƽ
 * @version 1.0
 */
public class Transactions implements java.io.Serializable,UNmodify
{
    private static Logger log = Logger.getLogger(Transactions.class);

    AOPMethods transationMethods;

    public Transactions()
    {
    	transationMethods = new AOPMethods();
    }

    /**
     * �жϷ����Ƿ�����Ҫ�����������,������򷵻ذ���������Ʋ�����Ϣ�ķ������� ���Ǿͷ���null
     * 
     * @param method
     * @return SynchronizedMethod ����������Ʋ�����Ϣ
     */
    public SynchronizedMethod isTransactionMethod(Method method)
    {
        return transationMethods.isTransactionMethod(method);
    }

    public int size()
    {
        return this.transationMethods.size();
    }

    public void addTransactionMethod(SynchronizedMethod method)
    {
        this.transationMethods.addTransactionMethod(method);
    }
    
    

//    private boolean containTransactionMethod(SynchronizedMethod method)
//    {
//       return this.transationMethods.containTransactionMethod(method);
//        
//    }

    public List<SynchronizedMethod> getTransactionMethods()
    {
        return this.transationMethods.getTransactionMethods();
    }

    public void unmodify()
    {
        this.transationMethods.unmodify();
//        this.fullTXMethodInfoIDX = java.util.Collections.unmodifiableMap(this.fullTXMethodInfoIDX);
        
    }

}
