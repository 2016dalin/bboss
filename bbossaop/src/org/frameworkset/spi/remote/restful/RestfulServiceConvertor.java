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

package org.frameworkset.spi.remote.restful;

/**
 * <p>Title: RestfulServiceConvertor.java</p> 
 * <p>Description: ��ַ��Ϣ����</p>
 * <p>bboss workgroup</p>
 * <p>Copyright (c) 2007</p>
 * @Date 2010-3-5 ����04:33:57
 * @author biaoping.yin
 * @version 1.0
 */
public interface RestfulServiceConvertor
{
    /**
     * ��restful��ʶ��ַת��Ϊʵ��Ŀ��ע���ַ
     * ת��������restfuluddiת����ʵ�ʵ�ַ��Ȼ����ʵ�ʵ�ַ���serviceid����ص���֤��Ϣ
     * ����ȡ��һ��������õ�ַ��
     * @param restfuluddi restful����ַ��ʶ����Ҫת��ΪĿ���ַ
     * @param serviceid Ҫ���õķ����ʶ
     * @return
     */
    public String convert(String restfuluddi,String serviceid);
    
}
