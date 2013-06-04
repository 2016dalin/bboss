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
 *  distributed under the License is distributed on an "AS IS" bboss persistent,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.  
 */
package com.frameworkset.common.poolman.management;

import java.io.Serializable;

/**
 * <p>Title: PoolManBootstrapMBean</p>
 *
 * <p>Description: ͨ��PoolManBootstrapMBeanʵ�ֶ�pool���ӳصĶ�̬������綯̬������
 * ��̬װ�أ���̬ע���ȵ�</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author biaoping.yin
 * @version 1.0
 */
public interface PoolManBootstrapMBean extends Serializable{

    /**��������poolman*/
    public void reStart(String configFile) throws Exception;
    /**����poolman*/
    public void start(String configFile) throws Exception;
    /**ֹͣpoolman*/
    public void stop() throws Exception;

}
