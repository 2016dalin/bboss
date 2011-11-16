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
package com.frameworkset.orm;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.frameworkset.orm.engine.EngineException;
import com.frameworkset.orm.engine.model.Column;
import com.frameworkset.orm.engine.model.Database;
import com.frameworkset.orm.engine.model.Table;
import com.frameworkset.orm.engine.transform.XmlToAppData;
import com.frameworkset.orm.platform.PlatformConst;

/**
 * <p>Title: ORMappingManager</p>
 *
 * <p>Description: �������ͱ��ϵ
 *                 ���ҿɶԶ���ͱ��ӳ���ϵ���г־û�,�ɴӳ־û��ļ��лָ��ö���
 *              </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author biaoping.yin
 * @version 1.0
 */
public class ORMappingManager implements java.io.Serializable{
    private static Logger log = Logger.getLogger(ORMappingManager.class);
    public static final String ANONYMITY_DATABASE_TYPE = PlatformConst.PLATFORM_DEFAULT;
    public static final String ANONYMITY_DATABASE_NAME = "anonymity_database";
    private static ORMappingManager instance = null;

    private final static Map dataBases = Collections.synchronizedMap(new HashMap(10));
    private boolean locked = false;
    private ORMappingManager()
    {
        super();
    }
    static
    {
        //��ʼ���������ݿ�
        if(!dataBases.containsKey(ANONYMITY_DATABASE_NAME))
        {
            Database anonymityDatabase = new Database(ANONYMITY_DATABASE_TYPE);
            anonymityDatabase.setName(ANONYMITY_DATABASE_NAME);
            dataBases.put(ANONYMITY_DATABASE_NAME,anonymityDatabase);
        }


    }

    public static void main(String[] args) {
        ORMappingManager ormappingmanager = new ORMappingManager();
    }

    /**
     * ������������ඨ���Ѿ������޸���ô����ɾ��ԭ���Ļ������Ȼ�����½��л���
     */
    private static void synchronizePersist()
    {
        File cachFile = new File(System.getProperty("basedir"),"ORMappingManager.Cache");
        cachFile.deleteOnExit();
    }
    /**
     * ������������ඨ���Ѿ������޸���ô����ɾ��ԭ���Ļ������Ȼ�����½��л���
     */
    private static void synchronizePersist(String path)
    {
        File cachFile = new File(path,"ORMappingManager.Cache");
        cachFile.deleteOnExit();
    }

    /**
     * ��ȡo/r mapping��������ʵ���������������洢�ļ�������·��Ϊȱʡ��classpath��·��
     * ��ȡʵ��ʱ���ȴӴ洢�ļ��лָ��ö�������������û���ڴ洢�ļ��У������´����ö���
     * @param path String �־û��������ʵ��������·��
     * @return ORMappingManager
     */

    public static ORMappingManager getInstance()
    {
        if(instance == null)
       {
           try
           {
               log.debug("Restore persist object from ORMappingManager.Cache");
               instance = ORMappingManager.restore();
           }
           catch(Exception ex)
           {
               log.debug("No persist ORMappingManager instance or ORMappingManager instance not synchronizable with the persist object:" + ex.getMessage());
               synchronizePersist();
               //ex.printStackTrace();
           }
           if(instance == null)
           {
               log.debug("Create instance of ORMappingManager.");
               instance = new ORMappingManager();
           }
       }
       return instance;
    }
    /**
     * ��ȡo/r mapping��������ʵ��������path����Ϊ���������������洢�ļ�������·��
     * ��ȡʵ��ʱ���ȴӴ洢�ļ��лָ��ö�������������û���ڴ洢�ļ��У������´����ö���
     * @param path String �־û��������ʵ��������·��
     * @return ORMappingManager
     */
    public static ORMappingManager getInstance(String path)
    {
        if(instance == null)
       {
           try
           {
               log.debug("Restore persist object from "+ path +  "/ORMappingManager.Cache");
               instance = ORMappingManager.restore(path);
           }
           catch(Exception ex)
           {
               log.debug("No persist ORMappingManager instance or ORMappingManager instance not synchronizable with the persist object:" + ex.getMessage());
               synchronizePersist(path);
               //ex.printStackTrace();
           }
           if(instance == null)
           {
               log.debug("Create instance of ORMappingManager.");
               instance = new ORMappingManager();
           }
       }
       return instance;
    }

    /**
     * �����������ݿ�
     * @return Database
     */
    public Database creatAnonymityDataBase()
    {
         log.debug("Get Anonymity database.");
         return (Database) dataBases.get(ANONYMITY_DATABASE_NAME);
    }

    /**
     * ������Ӧ���ƺ����͵����ݿ�
     * @param dbName String ���ݿ����ӳص�����
     * @param dbType String ���ݿ����ͣ������ֵ�ɲο�PlatformConst��������������ݿ�����
     * @return Database
     */
    public Database creatDataBase(String dbName, String dbType)
    {
		return this.creatAnonymityDataBase();
//        if(dbName == null)
//        {
//            return creatAnonymityDataBase();
//        }
//        if(dataBases.containsKey(dbName))
//        {
//            log.debug("Get database:dbName=" + dbName + ",dbType=" + dbType);
//            return (Database) dataBases.get(dbName);
//        }
//        log.debug("Creat database:dbName=" + dbName + ",dbType=" + dbType);
//        if(dbType == null)
//            dbType = ((JDBCPool)SQLManager.getInstance().getPool(dbName)).getDBType();
//        if(dbType == null)
//            dbType = PlatformConst.PLATFORM_DEFAULT;
//        Database dataBase = new Database(dbType);
//        dataBase.setName(dbName);
//        dataBases.put(dbName,dataBase);
//        return dataBase;
    }

    /**
     * �����������Ƶ����ݿ�
     * @param dbName String ��Ӧ���ݿ����ӳ��е�����
     * @return Database
     */
    public Database creatDataBase(String dbName)
    {
		return creatAnonymityDataBase();
//        if(dbName == null)
//        {
//            return creatAnonymityDataBase();
//        }
//
//        if(dataBases.containsKey(dbName))
//        {
//            log.debug("Get database:dbName=" + dbName );
//            return (Database) dataBases.get(dbName);
//        }
//        String dbType = ((JDBCPool)SQLManager.getInstance().getPool(dbName)).getDBType();
//        log.debug("Creat database:dbName=" + dbName + ",dbType=" + dbType);
//        Database dataBase = new Database(dbType);
//        dataBase.setName(dbName);
//        dataBases.put(dbName,dataBase);
//        return dataBase;
    }

    /**
     * ����ȱʡ���ݿ�ʵ��
     * @return Database
     */
    public Database creatDefaultDataBase()
    {
		return this.creatAnonymityDataBase();
//        String dbName = SQLManager.getInstance().getDefaultDBName();
//        if(dataBases.containsKey(dbName))
//        {
//            log.debug("Get default database:dbName=" + dbName );
//            return (Database) dataBases.get(dbName);
//        }
//        String dbType = ((JDBCPool)SQLManager.getInstance().getPool(dbName)).getDBType();
//        if(dbType == null)
//            dbType = PlatformConst.PLATFORM_DEFAULT;
//        log.debug("Creat default database:dbName=" + dbName + ",dbType=" + dbType);
//        Database dataBase = new Database(dbType);
//        dataBase.setName(dbName);
//        dataBases.put(dbName,dataBase);
//        return dataBase;
    }

    /**
     * ��ȡ�������Ƶ����ݿ�
     * @param dbName String ���ݿ����ӳ�����
     * @return Database
     * @throws ORMappingException
     */
    public Database getDatabase(String dbName)
        throws ORMappingException
    {
		return this.getAnonymityDatabase();
//        if(!dataBases.containsKey(dbName))
//            throw new ORMappingException("Can not get database:" + dbName );
//        return (Database) dataBases.get(dbName);
    }

    /**
     * ��ȡ���������ݿ�
     *
     * @return Database
     * @throws ORMappingException
     */
    public Database getAnonymityDatabase()
        throws ORMappingException
    {
        if(!dataBases.containsKey(this.ANONYMITY_DATABASE_NAME))
            throw new ORMappingException("Can not get ANONYMITY_DATABASE��");
        return (Database) dataBases.get(ANONYMITY_DATABASE_NAME);
    }

    /**
     * ��ȡȱʡ���ݿ�     *
     * @return Database
     * @throws ORMappingException
     */
    public Database getDefaultDatabase()
        throws ORMappingException
    {
		return this.getAnonymityDatabase();
//        String dbName = SQLManager.getInstance().getDefaultDBName();
//        if(!dataBases.containsKey(dbName))
//            throw new ORMappingException("Can not get default database:" + dbName);
//        return (Database) dataBases.get(dbName);
    }



    /**
     * �������ݿ�����
     * @return Table
     */
    public Table creatTable()
    {
        return new Table();
    }

    /**
     * �����������ݿ�����ı�
     * @param tableName String
     * @return Table
     * @throws ORMappingException
     */
    public Table creatTable(String tableName)
        throws ORMappingException
    {
//        if(tableName == null || tableName.trim().equals(""))
//            throw new ORMappingException("can not creat table:tableName is null or empty string" );
        return new Table(tableName);
    }

    /**
     * �������ݿ���ж���
     * @return Column
     */
    public Column creatColumn()
    {
        log.debug("Creat column.");
        return new Column();
    }

    /**
     * �����������ݿ���������ж���
     * @param name String
     * @return Column
     * @throws ORMappingException
     */
    public Column creatColumn(String name) throws ORMappingException {
        if(name == null || name.trim().equals(""))
            throw new ORMappingException("Can not creat culumn:column name is null or empty string" );
        log.debug("Creat column:name=" + name);
        return new Column(name);
    }

    /**
     * �־û����ݿ��������ļ�ORMappingManager.Cache��
     * @throws IOException
     */
    public void cache() throws IOException {
        ObjectOutputStream out =
            new ObjectOutputStream(
                new FileOutputStream("ORMappingManager.Cache"));
        out.writeObject(instance);
        out.close(); // Also flushes output
    }

    /**
     * �־û����ݿ�������·��path�µ��ļ�ORMappingManager.Cache��
     * @param cachePath String
     * @throws IOException
     */
    public void cache(String cachePath) throws IOException {
        if(cachePath == null && cachePath.trim().length() == 0)
        {
            cache();
            return;
        }
        if(!cachePath.endsWith("/") || cachePath.endsWith("\\"))
            cachePath.concat("/");
        ObjectOutputStream out =
            new ObjectOutputStream(
                new FileOutputStream(cachePath + "ORMappingManager.Cache"));
        out.writeObject(instance);
        out.close(); // Also flushes output
    }


    /**
     * �������ļ�ORMappingManager.Cache�лָ��������
     * @return ORMappingManager
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static ORMappingManager restore() throws IOException,
        ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream("ORMappingManager.Cache"));
                    ORMappingManager w2 = (ORMappingManager)in.readObject();


        return w2;
    }

    /**
     * ��·��path�µ��ļ�ORMappingManager.Cache�лָ��������
     * @param path String
     * @return ORMappingManager
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static ORMappingManager restore(String path) throws IOException,
        ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(
                    new FileInputStream("ORMappingManager.Cache"));
                    ORMappingManager w2 = (ORMappingManager)in.readObject();
        return w2;
    }

    /**
     * �������ݿ���Ϣ
     */
    public void reset()
    {
        dataBases.clear();
        //��ʼ���������ݿ�
        //if(!dataBases.containsKey(ANONYMITY_DATABASE_NAME))
        {
            Database anonymityDatabase = new Database(ANONYMITY_DATABASE_TYPE);
            anonymityDatabase.setName(ANONYMITY_DATABASE_NAME);
            dataBases.put(ANONYMITY_DATABASE_NAME,anonymityDatabase);
        }


    }
    /**
     * ��xml�ļ��м������ݿ�ʵ���������ݿ������ļ�ת��Ϊ���ݿ�
     * @param xmlPath String
     * @return ORMappingManager
     */
    public  ORMappingManager restoreFromXml(String xmlPath)
    {
        //reset();
        File xmls = new File(xmlPath);
        if(!xmls.exists())
            xmls = new File(System.getProperty("basedir"),xmlPath);
        if(!xmls.exists() || !xmls.isDirectory())
        {
            log.debug("Database cache path must be directory!");
            return ORMappingManager.getInstance();
        }

        File[] schemas = xmls.listFiles(new SchemaFileFilter());
        if(schemas == null || schemas.length == 0)
        {
            log.debug("No database is found in directory " +
                      xmls.getAbsolutePath() + "!");
            return ORMappingManager.getInstance();
        }

        for(int i = 0; i < schemas.length; i ++)
        {
            File schema = schemas[i];
            String filePath = "";
            filePath = schema.getAbsolutePath();
            if(schema.isDirectory())
            {
                restoreFromXml(filePath);
            }
            else
            {
                XmlToAppData xmlToAppData = new XmlToAppData(getDBType(schema.getName()));
                try {
                    log.debug("Generate database from schema:" + filePath);
                    Database db = xmlToAppData.parseFile(filePath);
                    if(dataBases.containsKey(db.getName()))
                    {
                        Database odb = (Database)dataBases.get(db.getName());
                        odb.addDataBase(db);
                    }
                    else
                    {
                        getInstance().dataBases.put(db.getName(), db);
                    }
                } catch (EngineException ex) {
                    log.error("Parse schema " + filePath + " error:" + ex.getMessage());
                }



            }



        }

        return ORMappingManager.getInstance();
    }

    /**
     * ����schema�ļ������ƻ�ȡ���ݿ����ͣ������С�Oracle_bsf.xml���������ļ���
     * ��ô��ȡ�����ݿ����;���Oracle
     * ���û�а������ݿ�������Ϣ��ȱʡ�����ݿ����ͽ��ᱻ���أ�PlatformConst.PLATFORM_DEFAULT����
     * @param schema String
     * @return String
     */
    private String getDBType(String schema)
    {
        int ch = schema.indexOf("_");
        if(ch == -1)
            return PlatformConst.PLATFORM_DEFAULT;
        else
            return schema.substring(0,ch);
    }


    /**
     * �ж�Ŀ¼���Ƿ������Ҫ��������ݿ������ļ�
     * @param dir File
     * @return boolean
     */
    private boolean containDataBase(File dir)
    {
        File[] files = dir.listFiles(new SchemaFileFilter());
        if(files == null || files.length == 0)
            return false;

        return true;
    }

    /**
     * �����ݿ�ʵ�������ݿ���Լ�����л��嵽xml�ļ���
     * @param xmlPath String
     * @return ORMappingManager
     */
    public  ORMappingManager cacheToXml(String xmlPath)
        throws ORMappingException
    {
        File xmls = new File(xmlPath);
        if(!xmls.exists())
            xmls = new File(System.getProperty("basedir"),xmlPath);
        if(!xmls.exists() || !xmls.isDirectory())
        {
            log.debug("Creat directory '" + xmlPath +"'");
            xmls.mkdirs();
        }
        Collection databases = ORMappingManager.dataBases.values();

        if(databases != null)
        {
            Iterator itr = databases.iterator();
            for(;itr.hasNext();)
            {
                Database db = (Database)itr.next();

                File file = getSchemaFile(xmls,db.getDatabaseType() ,db.getName() );
                //file.deleteOnExit();
                try {
                    //file.createNewFile();
                    log.debug("Save db metadata to schema file:" + file.getAbsolutePath() + "!");
                    BufferedOutputStream out =
                    new java.io.BufferedOutputStream(new FileOutputStream(file));
                    out.write(db.toString().getBytes());
                    out.flush();
                } catch (IOException ex) {
                    log.debug("Save db metadata to schema file " + file.getAbsolutePath() + " failed!");
                }
            }
        }

            //throws new ORMappingException("Save database error:" + xmlPath);
        return  ORMappingManager.instance;
    }

    private class SchemaFileFilter implements java.io.FileFilter
   {
        public boolean accept(File pathname) {
                if(pathname.isDirectory())
                    return true;
                if(pathname.getName().endsWith(".xml"))
                    return true;
                return false;
            }
   }

   public synchronized File getSchemaFile(File dir,String databaseType ,String dbName)
   {
       String schemafileName = databaseType + "_" + dbName + ".xml";
       File schemafile = new File(dir,schemafileName);
       if(schemafile.exists())
       {
           int i = 1;
           while(true)
           {
               schemafileName = databaseType + "_" + dbName + "." + i + ".xml";
               schemafile = new File(dir,schemafileName);
               if(schemafile.exists())
               {
                   i ++;
                   continue;
               }
               else
                   break;
           }


//            try {
//                schemafile.createNewFile();
//            } catch (IOException ex) {
//            }
       }
       return schemafile;
   }



    public void lock() {
        locked = true;
    }

    public boolean locked() {
        return locked ;
    }


    /**
     * unlock
     */
    public void unlock() {
        locked = false;
    }


}
