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
package com.frameworkset.orm.ant;

import org.apache.log4j.Logger;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import com.frameworkset.orm.ORMappingException;
import com.frameworkset.orm.doclet.HandlerORMapping;

/**
 * <p>Title: ORMappingTask</p>
 *
 * <p>Description: ִ�н���o/r mapping��ant �������
 * ��org.apache.tools.ant.Task�༯�ɣ���˿���ant�ű�����ƽ���������
 * ���ҽ����������ı����ӳ���ϵ���嵽�����ļ��У������ɶ���/���ݿ�ӳ��schema�ļ�
 * </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author biaoping.yin
 * @version 1.0
 */
public class ORMappingTask extends Task{
    private static Logger log = Logger.getLogger(ORMappingTask.class);
    /**
     * Դ�ļ�·��
     */
    private String src = null;

    /**
     * scheme�ļ����·��
     */
    private String schemaDir = null;

    /**
     * database�����ļ����·��
     */
    private String cacheDir = null;

    public void execute() throws BuildException
    {
        HandlerORMapping handler = new HandlerORMapping();
        if(src == null)
        {
            log.debug("Build O/R Mapping error occur:" + src + "=null.");
            throw new BuildException("Build O/R Mapping error occur:" + src + "=null.");
        }
        //��ʼ��handler��Դ�ļ�·��
        handler.init(src);
        //��ʼִ��ӳ���ϵ
        handler.execute();
        //����ӳ���ϵ
        if(cacheDir != null)
            handler.cache(cacheDir);
        if(schemaDir != null)
            handler.cachetoXml(schemaDir);

        if(schemaDir == null && cacheDir == null)
        {
            handler.cache();
        }
    }

    public String getSrc() {
        return src;
    }

    public String getSchemaDir() {
        return schemaDir;
    }

    public String getCacheDir() {
        return cacheDir;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public void setSchemaDir(String schemaDir) {
        this.schemaDir = schemaDir;
    }

    public void setCacheDir(String cacheDir) {
        this.cacheDir = cacheDir;
    }

    public static void  main(String[] args)  {
//        ORMappingTask task = new ORMappingTask();
//        task.setSchemaDir("d:/schemas");
//        task.setSrc("src");
//        task.execute();
        HandlerORMapping handler = new HandlerORMapping();
        String str = null;
        try {
            str = handler.restoreFromXml("d:/schemas").getAnonymityDatabase().
                  toString();
        } catch (ORMappingException ex) {
        }
        log.debug(str);
    }
}
