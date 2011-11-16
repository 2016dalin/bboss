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
 * <p>Title: AOPMethods.java</p> 
 * <p>Description: </p>
 * <p>bboss workgroup</p>
 * <p>Copyright (c) 2007</p>
 * @Date 2011-4-20 ����03:21:57
 * @author biaoping.yin
 * @version 1.0
 */
public class AOPMethods  implements java.io.Serializable,UNmodify
{
    private static Logger log = Logger.getLogger(Transactions.class);

    /**
     * ���񷽷���Ϣ List<SynchronizedMethod>
     */
    private List<SynchronizedMethod> aopMethods;

    /**
     * ��ʶ�����Ƿ�������ʽ���񷽷� Map<methoduuid,SynchronizedMethod>
     */
    private Map aopMethodInfoIDX;

    public static final SynchronizedMethod NULL = new SynchronizedMethod();

    public AOPMethods()
    {
    	aopMethods = new ArrayList();

    	aopMethodInfoIDX = new HashMap();
    }

    /**
     * �жϷ����Ƿ�����Ҫ�����������,������򷵻ذ���������Ʋ�����Ϣ�ķ������� ���Ǿͷ���null
     * 
     * @param method
     * @return SynchronizedMethod ����������Ʋ�����Ϣ
     */
    public SynchronizedMethod isTransactionMethod(Method method)
    {
        return containMethod(method);
    }
    
    /**
     * �жϸ����ķ����Ƿ����첽���÷�������������첽������Ϣ���ظ����ó���
     * @param method
     * @return
     */
    public SynchronizedMethod isAsyncMethod(Method method)
    {
        return containMethod(method);
    }
    
    private SynchronizedMethod containMethod(Method method)
    {
        try
        {

            String uuid = SynchronizedMethod.buildMethodUUID(method);
            SynchronizedMethod match = (SynchronizedMethod) aopMethodInfoIDX.get(uuid);
            if (match == null)
            {
                synchronized (this)
                {
                    match = (SynchronizedMethod) aopMethodInfoIDX.get(uuid);
                    if (match != null)
                    {
                        if (match == NULL)
                        {
                            return null;
                        }
                        else
                        {
                            return match;
                        }
                    }
                    for (int i = 0; i < aopMethods.size(); i++)
                    {
                        SynchronizedMethod m = (SynchronizedMethod) aopMethods.get(i);
                        if (m.match(method, uuid))
                        {
                            this.aopMethodInfoIDX.put(uuid, m);
                            return m;
                        }
                    }
                    aopMethodInfoIDX.put(uuid, NULL);
                    return null;
                }
            }
            else
            {
                if (match == NULL)
                {
                    return null;
                }
                else
                {
                    return match;
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public int size()
    {
        return this.aopMethods.size();
    }

    public void addTransactionMethod(SynchronizedMethod method)
    {
        if(this.containTransactionMethod(method)) 
            return;
        this.aopMethods.add(method);
    }
    
    

    private boolean containTransactionMethod(SynchronizedMethod method)
    {
       if(aopMethods == null || aopMethods.size() == 0)
           return false;
       for(SynchronizedMethod method_: this.aopMethods)
       {
           if(method_.getUUID().equals(method.getUUID()))
               return true;
       }
       return false;
        
    }

    public List<SynchronizedMethod> getTransactionMethods()
    {
        return aopMethods;
    }

    public void unmodify()
    {
        aopMethods = java.util.Collections.unmodifiableList(aopMethods);
//        this.aopMethodInfoIDX = java.util.Collections.unmodifiableMap(this.aopMethodInfoIDX);
        
    }

}
