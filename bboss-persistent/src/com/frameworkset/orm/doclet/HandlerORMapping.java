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
package com.frameworkset.orm.doclet;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;

import xjavadoc.XClass;
import xjavadoc.XDoc;
import xjavadoc.XField;
import xjavadoc.XJavaDoc;
import xjavadoc.XTag;
import xjavadoc.filesystem.FileSourceSet;

import com.frameworkset.orm.ORMappingException;
import com.frameworkset.orm.ORMappingManager;
import com.frameworkset.orm.engine.model.Column;
import com.frameworkset.orm.engine.model.Database;
import com.frameworkset.orm.engine.model.Table;

/**
 * <p>Title: HandlerORMapping</p>
 *
 * <p>Description: ��ʼ������ϵͳ�����е�javabean�������ݿ��ϵ��֮����ڵĶ������Ժͱ��ֶε�ӳ���ϵ.
 *                 ����o/r mappingԪģ��,���Ҷ�Ԫģ�ͽ��г־û�
 *                 �������¹�����н�����
 *                 ��javabean����ע���ж������ݿ����ƺͱ����ƣ����ֶ����ǿ�ѡ��
 *                 ��javabean�����Զ����ע���ж����ֶζ�Ӧ�ı���ֶΣ����ֶ����Ǳ����
 * </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author biaoping.yin
 * @version 1.0
 */
public class HandlerORMapping {
    private static Logger log = Logger.getLogger(HandlerORMapping.class);

    private final XJavaDoc _xJavaDoc = new XJavaDoc();
    private File srcDir;
    private Collection allClasses = null;
    public static void main(String[] args) {
        HandlerORMapping handlerormapping = new HandlerORMapping();
//        handlerormapping.init("src");
//        handlerormapping.execute();
        String str = null;
        try {
            str = handlerormapping.restoreFromXml("d:/schemas").getAnonymityDatabase().
                  toString();
        } catch (ORMappingException ex) {
        }
        log.debug(str);

    }

    /**
     * ���ݴ����Դ�ļ�����Ը�Ŀ¼������Ŀ¼ͨ��������ϵͳ������basedir+relativeSrcdir���ã���
     * ��ʼ��xjavadoc�������е�java�ļ�ת����XClass����Ϊ����java���������������ݿ��֮��ù�ϵ��׼��
     * @param relativeSrcdir String
     */
    public void init(String relativeSrcdir)
    {
        long start = System.currentTimeMillis();
        srcDir = new File(System.getProperty("basedir"),relativeSrcdir);
        log.debug("Load java source in " + srcDir.getAbsolutePath());
        _xJavaDoc.reset(true);
        _xJavaDoc.addSourceSet(new FileSourceSet(srcDir));
        allClasses = _xJavaDoc.getSourceClasses();
        long end = System.currentTimeMillis();
        log.debug("Load java source complete! Total cost:" + (end - start)/1000 + " seconds");
    }

    /**
     * ���ݴ����Դ�ļ��ĸ�Ŀ¼��
     * ��ʼ��xjavadoc�������е�java�ļ�ת����XClass����Ϊ����java���������������ݿ��֮��ù�ϵ��׼��
     * @param srcdir String
     */
    public void initByCompletePath(String srcdir)
    {
        long start = System.currentTimeMillis();
        log.debug("Load java source in " + srcdir);
        srcDir = new File(srcdir);
        _xJavaDoc.reset(true);
        _xJavaDoc.addSourceSet(new FileSourceSet(srcDir));
        allClasses = _xJavaDoc.getSourceClasses();
        long end = System.currentTimeMillis();
        log.debug("Load java source complete! Total cost:" + (end - start)/1000 + " seconds");
    }

    /**
     * ������ʼ
     */
    public void execute()
    {
        ORMappingManager.getInstance().lock();
        log.debug("Handler ORMapping start...");
        long start = System.currentTimeMillis();
        if(allClasses == null)
        {
            log.debug("No java source in dir:" + srcDir.getAbsoluteFile());
            return;
        }

        Iterator it = allClasses.iterator();
        XClass xclass = null;
        for(;it.hasNext();)
        {
            Table table = null;
            xclass = (XClass)it.next();
            //���xclass��Ӧ����û��ָ������ֶ���Ϣ������������һ��ѭ��
            if(!needHandler(xclass))
            {
                //log.debug("ignore parseing " + xclass.getQualifiedName() + ".");
                continue;
            }
            log.debug("Start parseing " + xclass.getQualifiedName() + ".....");
            Database dataBase = null;
            String dbName = null;
            String dbType = null;
            String tableName = null;

            String tableSchema = null;

            XDoc doc = xclass.getDoc();
            XTag databaseTag = doc.getTag(TagConst.TAG_DATABASE);
            XTag tableTag = doc.getTag(TagConst.TAG_TABLE);

            if(databaseTag == null && tableTag == null)
                dataBase = ORMappingManager.getInstance().creatAnonymityDataBase();
            else
            {
                if (databaseTag != null) {
                    dbName = databaseTag.getAttributeValue(TagConst.
                        TAG_DATABASE_NAME);
                    dbType = databaseTag.getAttributeValue(TagConst.
                        TAG_DATABASE_TYPE);
                    tableName = databaseTag.getAttributeValue(TagConst.
                        TAG_DATABASE_TABLE);
                }

                if(tableTag != null)
                {
                    tableName = tableTag.getAttributeValue(TagConst.
                        TAG_TABLE_NAME);

                    //��������
                    tableSchema = tableTag.getAttributeValue(TagConst.
                        TAG_TABLE_SCHEMA);
                }
                //�������ݿ�ʵ��

                //���û��ָ�����ݿ��ǩ�����Ǳ��ǩָ���ˣ�����ݱ��ǩ�����Դ������ݿ�ʵ��
                //����
                if (databaseTag == null && tableTag != null) {
                    if (tableSchema != null)
                        dataBase = ORMappingManager.getInstance().creatDataBase(tableSchema);
                    else
                        dataBase = ORMappingManager.getInstance().creatDefaultDataBase();
                } else
                    dataBase = ORMappingManager.getInstance().creatDataBase(dbName, dbType);
            }
            try {                //������ʵ��



                log.debug("Parseing table ....." + tableName);
                table = ORMappingManager.getInstance().creatTable(tableName);
                table.setDatabase(dataBase);

                table.setBaseClass(xclass.getSuperclass().getQualifiedName());
                table.setJavaName(xclass.getQualifiedName());
                table.setPackage(xclass.getContainingPackage().getName());
                Iterator xFields = xclass.getFields().iterator();
                for(;xFields.hasNext();)
                {
                    XField field = (XField)xFields.next();
                    XDoc fdoc = field.getDoc();
                    if(fdoc != null)
                    {
                        XTag columnTag = fdoc.getTag(TagConst.TAG_COLUMN);
                        if(columnTag == null)
                            continue;

                        String columnName = columnTag.getAttributeValue(TagConst.TAG_COLUMN_NAME);

                        String columnDescription = columnTag.getAttributeValue(TagConst.TAG_COLUMN_DESCRIPTION);
                        String columnPrimarykey = columnTag.getAttributeValue(TagConst.TAG_COLUMN_PRIMARYKEY);
                        String columnRequired = columnTag.getAttributeValue(TagConst.TAG_COLUMN_REQUIRED);
                        String columnType = columnTag.getAttributeValue(TagConst.TAG_COLUMN_TYPE);
                        Column column = ORMappingManager.getInstance().creatColumn(columnName);
                        column.setTable(table);
                        column.setDescription(columnDescription);
                        if(columnPrimarykey != null)
                            column.setPrimaryKey(new Boolean(columnPrimarykey).booleanValue());
                        if(columnRequired != null)
                            column.setNotNull(new Boolean(columnRequired).booleanValue());
                        column.setType(columnType);
                        column.setJavaName(field.getName());
                        log.debug("Parseing table column '" + columnName + "' with field '" + field.getName() + "'");
                        column.setJavaType(field.getType().getQualifiedName());

                        table.addColumn(column);

                    }
                }

                //table.setDatabase(dataBase);
                //����ʵ����ӵ����ݿ���
                dataBase.addTable(table);
                //System.out.println(dataBase);
                log.debug("Complete parseing " + xclass.getQualifiedName() + ".");
            } catch (ORMappingException ex) {
                ex.printStackTrace();
            }
        }
        ORMappingManager.getInstance().unlock();
        long end = System.currentTimeMillis();
        log.debug("Complete parseing all java sources! Total cost:" + (end - start) + " ms");
    }

    /**
     * �ж��Ƿ���Ҫ����xclass�е����ݿ������Ϣ
     * @param xclass XClass
     * @return boolean
     */
    private boolean needHandler(XClass xclass)
    {
        boolean need = false;
        if(xclass == null || xclass.getFields() == null || xclass.getFields().size() == 0)
            need = false;
        else
        {
            Iterator fields = xclass.getFields().iterator();
            for(;fields.hasNext();)
            {
                XField field = (XField)fields.next();
                if(field.getDoc().getTag(TagConst.TAG_COLUMN) != null)
                {
                    need = true;
                    break;
                }
            }

        }
        return need;
    }

    private String getPackage(XClass xclass)
    {
        String name = xclass.getModifiers();
        return null;
    }

    /**
     * cache
     */
    public boolean cache() {
        try {
            ORMappingManager.getInstance().cache();
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    /**
    * cache
    */
   public boolean cache(String cachePath) {
       try {
           ORMappingManager.getInstance().cache(cachePath);
           return true;
       } catch (IOException ex) {
           return false;
       }
   }


    /**
     * restore
     */
    public ORMappingManager restore() {
        try {
            return ORMappingManager.restore();
        } catch (ClassNotFoundException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        }
    }

    /**
     * restore from path
     */
    public ORMappingManager restore(String path) {
        try {
            return ORMappingManager.restore(path);
        } catch (ClassNotFoundException ex) {
            return null;
        } catch (IOException ex) {
            return null;
        }
    }

    /**
     * �����ݿ�����Ŀ¼xmlDir�е�schema�ļ�ת����
     * @param xmlDir String
     * @return ORMappingManager
     */
    public boolean cachetoXml(String xmlDir)
    {
        try {
             ORMappingManager.getInstance().cacheToXml(xmlDir);
             return true;
        } catch (ORMappingException ex) {
            return false;
        }
    }

    /**
     * �Ӵ����Ŀ¼xmlDir�е�schema�ļ�ת�������ݿ�
     * @param xmlDir String
     * @return ORMappingManager
     */
    public ORMappingManager restoreFromXml(String xmlDir)
    {
        return ORMappingManager.getInstance().restoreFromXml(xmlDir);
    }





}
