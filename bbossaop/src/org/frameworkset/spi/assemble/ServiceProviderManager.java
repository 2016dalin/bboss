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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.frameworkset.spi.BaseApplicationContext;
import org.frameworkset.spi.CallContext;
import org.frameworkset.spi.assemble.RefID.Index;
import org.frameworkset.spi.assemble.callback.AssembleCallback;

/**
 * 
 * 
 * <p>
 * Title: ServiceProviderManager.java
 * </p>
 * 
 * <p>
 * Description: �����ṩ�߹�����
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
 * @Date Aug 13, 2008 9:27:36 AM
 * @author biaoping.yin,����ƽ
 * @version 1.0
 */
public class ServiceProviderManager {
    private static Logger log = Logger.getLogger(ServiceProviderManager.class);
    /**
     * �������õķ�����������
     */
    
    public Map<String,Set> reverseAttrRefids = new HashMap<String,Set>();
    
    /**
     * �������õķ�����������
     */
    public Map<String,Set> reverseServiceRefids = new HashMap<String,Set>();
    /**
     * �������ñ�ʶ��ǰ׺
     */
    public static final String ATTRIBUTE_PREFIX = "attr:";
    /**
     * �������ñ�ʶ��ǰ׺��������ñ�ʶ�в���ǰ׺ʱĬ��Ϊ��������
     */
    public static final String SERVICE_PREFIX = "service:";

//    private static ServiceProviderManager serviceProviderManager;
    public static String defaultConfigFile = "manager-provider.xml";

    private Map<String,Pro> properties = new HashMap<String,Pro>();

    /**
     * ���Ѿ��������������ļ����м�¼�������ظ����������ļ�
     */
    private Map parsedList;
    private static final Object trace = new Object();

    /**
     * ������������ļ��������б�,�����ļ� List<LinkConfigFile>
     */
    private List traceFiles;
    
     
    /**
     * ����id������providerManager������ Map<managerid,ProviderManagerInfo>
     */
    private Map managers;

    private ProviderManagerInfo defaultProviderManagerInfo;

    /**
     * ��Ҫ����Ĺ�����������ļ��б���Щ�ļ���·����������� config-manager.xml�ļ��ĵ�ַ�����磺
     * ���config-manager.xml�ļ��Ĵ��Ŀ¼Ϊd:/cms/WEB-INF/classes/
     * managerimport��file��������Ϊhnds/hnds-dingshui.xml,��ô�õ����ļ�����ʵ��ַΪ
     * d:/cms/WEB-INF/classes/hnds/hnds-dingshui.xml Map<path,LinkConfigFile>
     */
    private Map managerimports;
    private BaseApplicationContext applicationContext;
//    static {
//    	ServiceProviderManager temp = new ServiceProviderManager();
//    	temp.init();
//    	serviceProviderManager = temp;
//    }
//
//    public static ServiceProviderManager getInstance() {
//
//        return serviceProviderManager;
//    }
    
//    public void init()
//    {
////    	 try {
////
////             load(defaultConfigFile, null);
////
////         }
////
////         catch (Exception e) {
////             e.printStackTrace();
////             log.error("Load [" + defaultConfigFile + "]ʧ��", e);
////         }
//    	this.init(defaultConfigFile);
//    }
    
    public void init(String configfile)
    {
    	init(AssembleCallback.classpathprex, "", configfile);
//    	 try {
//    		 List<ManagerImport> mis = AssembleUtil.getManagerImports(AssembleCallback.classpathprex, "", configfile);
//    		 for(int i = 0; i < mis.size(); i ++)
//    		 {
//    			 load(mis.get(i), null);
//    		 }
//    		 
//
//         }
//
//         catch (Exception e) {
//             e.printStackTrace();
//             log.error("Load [" + defaultConfigFile + "]ʧ��", e);
//         }
    }
    
    
    public void init(String docbaseType,String docbase,String configfile)
    {
    	init(docbaseType,docbase,configfile,true);
    }
    
    public void init(String docbaseType,String docbase,InputStream instream)
    {
    	init(docbaseType,docbase,instream,true);
    }
    
    public void init(String docbaseType, String docbase, String configfile,
			URL file)
	{

		try {
	   		 
	   		 {
	   			 this.loadFromURL(file);
	   		 }
   		 

        }

        catch (Exception e) {
            e.printStackTrace();
            log.error("Load [" + defaultConfigFile + "]ʧ��", e);
        }
		
	}
    public void init(String docbaseType,String docbase,String configfile,boolean isfile)
    {
    	 try {
    		 if(this.applicationContext.isfile())
    		 {
	    		 List<ManagerImport> mis = AssembleUtil.getManagerImports(docbaseType, docbase, configfile);
	    		 for(int i = 0; i < mis.size(); i ++)
	    		 {
	    			 load(mis.get(i), null);
	    		 }
    		 }
    		 else
    		 {
    			 this.loadFromContent(configfile);
    		 }
    		 

         }

         catch (Exception e) {
             e.printStackTrace();
             log.error("Load [" + defaultConfigFile + "]ʧ��", e);
         }
    }
    
    public void init(String docbaseType,String docbase,InputStream instream,boolean isfile)
    {
    	 try {
//    		 if(this.applicationContext.isfile())
//    		 {
//	    		 List<ManagerImport> mis = AssembleUtil.getManagerImports(docbaseType, docbase, configfile);
//	    		 for(int i = 0; i < mis.size(); i ++)
//	    		 {
//	    			 load(mis.get(i), null);
//	    		 }
//    		 }
//    		 else
    		 {
    			 this.parseXML(instream);
    		 }
    		 

         }

         catch (Exception e) {
             e.printStackTrace();
             log.error("Load [" + defaultConfigFile + "]ʧ��", e);
         }
    }

    public ServiceProviderManager(BaseApplicationContext applicationContext) {
        parsedList = new HashMap();
        managers = new HashMap();
        managerimports = new HashMap();
        traceFiles = new ArrayList();
        this.applicationContext = applicationContext;
    }

    /**
     * ����������imports�����ļ��б��еİ����Ĺ������parentFile��ָ������Щ�����ļ��������ļ�������ϵͳ ���е��Ժ͸���
     * 
     * @param imports
     * @param parentFile
     */
    public void batchLoad(List imports, LinkConfigFile parentFile) {

        for (int i = 0; imports != null && i < imports.size(); i++) {
            ManagerImport mi = (ManagerImport)imports.get(i);
            List<ManagerImport> resolvermis = AssembleUtil.getManagerImports(mi);
            for(int j = 0; j < resolvermis.size(); j ++)
            {
	            try {
	            	ManagerImport resolvermi = (ManagerImport)resolvermis.get(j);
	                this.load(resolvermi, parentFile);
	            } catch (Exception e) {
	            	e.printStackTrace();

	            }
            }
        }
    }

    private boolean isParsered(String configfile) {
        return this.parsedList.containsKey(configfile);
    }

    public void load(ManagerImport managerImport, LinkConfigFile parentFile) {
        if (isParsered(managerImport.getFile()))
            return;
        parseXML( managerImport, parentFile);
        parsedList.put(managerImport.getFile(), trace);

    }
    
    public void loadFromContent(String content) {
        
        parseXML( content);
        

    }
 public void loadFromURL(URL content) {
        
        parseXML( content);
        

    }
    
    private void parseXML(ManagerImport managerImport, LinkConfigFile parentFile) {

    	String configFile = managerImport.getRealPath();
        /* CHANGED TO USE JAXP */

        String url = configFile;
        try {
        	if(managerImport.isClasspathBase())
        	{
	            URL confURL = ServiceProviderManager.class.getClassLoader().getResource(configFile);
	            if (confURL == null)
	                confURL = ServiceProviderManager.class.getClassLoader().getResource("/" + configFile);
	
	            if (confURL == null)
	                confURL = getTCL().getResource(configFile);
	            if (confURL == null)
	                confURL = getTCL().getResource("/" + configFile);
	            if (confURL == null)
	                confURL = ClassLoader.getSystemResource(configFile);
	            if (confURL == null)
	                confURL = ClassLoader.getSystemResource("/" + configFile);
	
	            if (confURL == null) {
	                url = System.getProperty("user.dir");
	                url += "/" + configFile;
	            } else {
	                url = confURL.toString();
	            }
        	}
        	else
        	{
        		url = managerImport.getRealPath();
        	}
            LinkConfigFile linkconfigFile = new LinkConfigFile(url, managerImport.getFile(), parentFile);

            ProviderParser handler = new ProviderParser(this.getApplicationContext(),url, linkconfigFile);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(false);
            factory.setValidating(false);
            SAXParser parser = factory.newSAXParser();
            parser.parse(url, handler);

            this.addMangers(handler.getManagers());
            this.addProperties(handler.getProperties());
            if (parentFile == null)
                this.traceFiles.add(linkconfigFile);
            else
                parentFile.addLinkConfigFile(linkconfigFile);
            linkconfigFile.setMgrServices(handler.getManagers());
            linkconfigFile.setProperties(handler.getProperties());
            this.managerimports.put(linkconfigFile.getIdentity(), linkconfigFile);
            if(handler.getMangerimports() != null && handler.getMangerimports().size() > 0)
            	this.batchLoad(handler.getMangerimports(), linkconfigFile);

        } catch (Exception e) {
            if (parentFile == null) {
                log.error("���ļ�[" + url + "]װ�ع������ʧ�ܣ������ļ��Ƿ���ڣ������Ƿ���ȷ���塣",e);
            } else {
                log.error("���ļ�[" + parentFile + "@" + url + "]װ�ع������ʧ�ܣ������ļ��Ƿ���ڣ������Ƿ���ȷ���塣",e);
            }
            // e.printStackTrace();
        }

    }
    
    
    private void parseXML(String content) {
    	InputStream in = null;
    	ByteArrayInputStream sr = null;
    		try
    		{
            ProviderParser handler = new ProviderParser(this.getApplicationContext());
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(false);
            factory.setValidating(false);
            
            SAXParser parser = factory.newSAXParser();        
            sr = new ByteArrayInputStream(content.getBytes());
//            in = new java.io.BufferedInputStream(sr);
//            parser.parse(in, handler);    
            parser.parse(sr, handler);    
            
            this.addProperties(handler.getProperties());
            handler = null;
        } catch (Exception e) {
            log.error("�����������ݳ���[" + content + "]�����鱨���Ƿ���ȷ���塣",e);
        }
        finally
        {
        	if(sr != null) {
				try {
					sr.close();
				} catch (Exception e2) {
					
				}
			}
        	if(in != null) {
				try {
					in.close();
				} catch (Exception e2) {
					
				}
			}
        }

    }
    
    
    private void parseXML(InputStream in) {
//    	InputStream in = null;
//    	ByteArrayInputStream sr = null;
    		try
    		{
            ProviderParser handler = new ProviderParser(this.getApplicationContext());
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(false);
            factory.setValidating(false);
            
            SAXParser parser = factory.newSAXParser();        
//            sr = new ByteArrayInputStream(content.getBytes());
//            in = new java.io.BufferedInputStream(sr);
//            parser.parse(in, handler);    
            parser.parse(in, handler);    
            
            this.addProperties(handler.getProperties());
            handler = null;
        } catch (Exception e) {
            log.error("�����������ݳ���[" + in + "]�����鱨���Ƿ���ȷ���塣",e);
        }
        finally
        {
//        	if(sr != null) {
//				try {
//					sr.close();
//				} catch (Exception e2) {
//					
//				}
//			}
        	if(in != null) {
				try {
					in.close();
				} catch (Exception e2) {
					
				}
			}
        }

    }
    

    private void parseXML(URL contentFile) {
//    	InputStream in = null;
//    	ByteArrayInputStream sr = null;
    		try
    		{
            ProviderParser handler = new ProviderParser(this.getApplicationContext());
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(false);
            factory.setValidating(false);
            
            SAXParser parser = factory.newSAXParser();
        
//            in = new java.io.BufferedInputStream(new java.io.FileInputStream(contentFile));
            parser.parse(contentFile.toString(), handler);            
            
            this.addProperties(handler.getProperties());
            handler = null;
        } catch (Exception e) {
            log.error("�����������ݳ���[" + contentFile.toString() + "]�����鱨���Ƿ���ȷ���塣",e);
        }
        finally
        {
        	
        }

    }

    public void addTopLevel(LinkConfigFile linkconfigFile) {
        this.traceFiles.add(linkconfigFile);
        this.managerimports.put(linkconfigFile.getIdentity(), linkconfigFile);
    }

    private static ClassLoader getTCL() throws IllegalAccessException, InvocationTargetException {
        Method method = null;
        try {
            method = (java.lang.Thread.class).getMethod("getContextClassLoader", null);
        } catch (NoSuchMethodException e) {
            return null;
        }
        return (ClassLoader)method.invoke(Thread.currentThread(), null);
    }

    public ProviderManagerInfo getProviderManagerInfo(String providerManagerType) {
        return (ProviderManagerInfo)this.managers.get(providerManagerType);

    }

    public void addMangers(Map managers) {
        if (managers != null && managers.size() > 0)
            this.managers.putAll(managers);

    }

    public void addProperties(Map<String,Pro> properties) {
        if (properties != null && properties.size() > 0)
            this.properties.putAll(properties);

    }

    public ProviderManagerInfo getDefaultProviderManagerInfo() {
        return defaultProviderManagerInfo;
    }

    public void setDefaultProviderManagerInfo(ProviderManagerInfo defaultProviderManagerInfo) {
        this.defaultProviderManagerInfo = defaultProviderManagerInfo;
    }

    private Map mutuxMangers;

    public Map getManagers() {
        if (mutuxMangers == null)
            mutuxMangers = Collections.unmodifiableMap(managers);
        return mutuxMangers;
    }

    public void addProviderManagerInfo(ProviderManagerInfo provider) {
        this.managers.put(provider.getId(), provider);
    }

    /**
     * ����һ�������ļ��嵥
     * 
     * @return
     */
    public List getTraceFiles() {
        return traceFiles;
    }

    /**
     * ���ظ�����ʶ�Ĺ�����������ļ���Ϣ
     * 
     * @param id
     * @return
     */
    public LinkConfigFile getLinkConfigFile(String id) {
        return (LinkConfigFile)this.managerimports.get(id);
    }
    
    public Set<String> getPropertyKeys()
    {
    	return this.properties.keySet();
    }

    public String getProperty(String name) {
        Pro pro = this.properties.get(name);
        if(pro == null)
            return null;
        String value = (String)pro.getValue();
//        if(value == null)
//            throw new AssembleException("�����ļ�û��ָ������[" + name + "]��");
        return value;
    }
    
    public Object getObjectProperty(String name) {
        return getObjectProperty(name,null);
//        if(value == null)
//            throw new AssembleException("�����ļ�û��ָ������[" + name + "]��");
        
    }
    
    public Object getObjectProperty(String name,Object defaultValue) {
        Pro pro = this.properties.get(name);
        if(pro == null)
        {
            log.debug("�����ļ�["+applicationContext.getConfigfile() +"]��û��ָ������[" + name + "]������ȱʡֵ" + defaultValue);
            return defaultValue;
        }
        
        return pro.getObject(defaultValue);
            
    }
    
    public ProSet getSetProperty(String name) {
        Pro value = this.properties.get(name);
        if (value == null) {
            log.debug("�����ļ�["+applicationContext.getConfigfile() +"]��û��ָ������[" + name + "]��");
            return null;
        }
       return value.getSet(); 
//        if(value == null)
//            throw new AssembleException("�����ļ�û��ָ������[" + name + "]��");
        
    }
    
    public ProSet getSetProperty(String name,ProSet defaultValue) {
        Pro value = this.properties.get(name);
        if (value == null) {
            log.debug("�����ļ�["+applicationContext.getConfigfile() +"]��û��ָ������[" + name + "]��");
            return defaultValue;
        }
       return value.getSet(defaultValue);
//        if(value == null)
//            throw new AssembleException("�����ļ�û��ָ������[" + name + "]��");
        
    }
    
    public ProList getListProperty(String name) {
        Pro value = this.properties.get(name);
        if (value == null) {
            
            log.debug("�����ļ�["+applicationContext.getConfigfile() +"]��û��ָ������[" + name + "]��");
            return null;
        }
       return value.getList();
//        if(value == null)
//            throw new AssembleException("�����ļ�û��ָ������[" + name + "]��");
        
    }
    
    public ProList getListProperty(String name,ProList defaultValue) {
        Pro value = this.properties.get(name);
        if (value == null) {
            log.debug("�����ļ�["+applicationContext.getConfigfile() +"]��û��ָ������[" + name + "]��");
            return defaultValue;
        }
       return value.getList(defaultValue);
//        if(value == null)
//            throw new AssembleException("�����ļ�û��ָ������[" + name + "]��");
        
    }
    
    
    public ProMap getMapProperty(String name) {
        Pro value = this.properties.get(name);
        if (value == null) {
            log.debug("�����ļ�["+applicationContext.getConfigfile() +"]��û��ָ������[" + name + "]��");
            return null;
        }
       return value.getMap();        
//        if(value == null)
//            throw new AssembleException("�����ļ�û��ָ������[" + name + "]��");
        
    }
    
    public ProMap getMapProperty(String name,ProMap defaultValue) {
        Pro value = this.properties.get(name);
        if (value == null) {
            log.debug("�����ļ�["+applicationContext.getConfigfile() +"]��û��ָ������[" + name + "]��");
            return defaultValue;
        }
       return value.getMap();
    }
    
    
    public ProArray getArrayProperty(String name) {
        Pro value = this.properties.get(name);
        if (value == null) {
            log.debug("�����ļ�["+applicationContext.getConfigfile() +"]��û��ָ������[" + name + "]��");
            return null;
        }
       return value.getArray();        
//        if(value == null)
//            throw new AssembleException("�����ļ�û��ָ������[" + name + "]��");
        
    }
    
    public ProArray getArrayProperty(String name,ProArray defaultValue) {
        Pro value = this.properties.get(name);
        if (value == null) {
            log.debug("�����ļ�["+applicationContext.getConfigfile() +"]��û��ָ������[" + name + "]��");
            return defaultValue;
        }
       return value.getArray(defaultValue);
    }

    public int getIntProperty(String name) {
        Pro value = this.properties.get(name);
        if (value == null) {
            throw new AssembleException("�����ļ�["+applicationContext.getConfigfile() +"]��û��ָ������[" + name + "]��");
        }
       return value.getInt();
        
        
            
        
    }
    
    public long getLongProperty(String name) {
        Pro value = this.properties.get(name);
        if (value == null) {
            throw new AssembleException("�����ļ�["+applicationContext.getConfigfile() +"]��û��ָ������[" + name + "]��");
        }
       return value.getLong();
        
        
            
        
    }
    
    public long getLongProperty(String name,long defaultvalue) {
        Pro value = this.properties.get(name);
        if (value == null) {
            return defaultvalue;
        }
       return value.getLong(defaultvalue);
        
        
            
        
    }

    public boolean getBooleanProperty(String name) {
        Pro value = this.properties.get(name);
        if (value == null) {
            throw new AssembleException("�����ļ�["+applicationContext.getConfigfile() +"]��û��ָ������[" + name + "]��");
        }
       return value.getBoolean();
    }
    
    public Pro getPropertyBean(String name)
    {
        Pro pro = this.properties.get(name);
        if(pro == null){
            log.debug("�����ļ�["+applicationContext.getConfigfile() +"]��û��ָ������[" + name + "]��");
            return null;
        }
        return pro;
    }
    
    /**
     * �������õ�ά�Ȼ�ȡ���Ӧ��Pro����
     * @param refid
     * @return
     */
    public Pro getInnerPropertyBean(RefID refid,String strrefid)
    {	
//    	String name = refid.getName();
    	Pro pro = null;
//    	if(strrefid != null && strrefid.equals("test1->testarray[0]->test2"))
//    	{
//    		System.out.println();
//    	}
    	boolean firsted = true;
    	Pro temp = null;
    	do
    	{
    		if(firsted)
    		{
    			pro = this.properties.get(refid.getName());
    			firsted = false;
    		}
    		else
    		{
    			if(pro.isRefereced())//�����Ȼ��һ�����ù�ϵ���������ҵ�ʵ�ʵĶ���pro��Ȼ���ڼ�����һ�����ڵ����ù�ϵ����
    			{
    				pro = getInnerPropertyBean(pro.getRefidLink(),pro.getRefid());
    			}
    			List<Pro> refs = pro.getReferences();
    			pro = null;
    			for(int i = 0; i < refs.size(); i ++)
    			{
    				temp = refs.get(i);
    				if(temp.getName().equals(refid.getName()))
					{
    					pro = temp;
    					break;
					}
    			}
    			if(pro == null)
    			{
    				 log.debug("�����ļ�["+applicationContext.getConfigfile() +"]��û��ָ������[" + strrefid + "]��");
    		         return null;
    			}
    		}
    		
	        List<Index> indexs = refid.getIndexs();
	        if(indexs != null && indexs.size() > 0)//�ڲ����󣺹�������list/array/set
	        {
	        	for(int i = 0;i < indexs.size(); i ++)
	        	{
	        		Index index = indexs.get(i);
	        		if(!index.isInconstruction())
	        		{
	        			if(index.getInt_idx() >= 0)
	        			{
	        				if(pro.isList())
	        					pro = pro.getList().getPro(index.getInt_idx());
	        				else if(pro.isArray())
	        					pro = pro.getArray().getPro(index.getInt_idx());
	        				
	        				else 
	        					pro = pro.getSet().getPro(index.getInt_idx());	
	        			}
	        			else
	        			{
	        				pro = pro.getMap().getPro(index.getString_idx());	
	        			}
	        		}
	        		else
	        		{
	        			pro = (Pro)pro.getConstructorParams().get(index.getInt_idx());
	        		}
	        		
        			if(pro.isRefereced() && indexs.size() > 1)
        			{
        				pro = getInnerPropertyBean(pro.getRefidLink(),pro.getRefid());
        			}
	        		
	        	}
	        }
	        else//��������
	        {
	        	
	        }
	       
	        refid = refid.getNext();
    	}while(refid != null);
	        
        
        if(pro == null){
            log.debug("�����ļ�["+applicationContext.getConfigfile() +"]��û��ָ������[" + strrefid + "]��");
            return null;
        }
        return pro;
    }

    public String getProperty(String name, String defaultValue) {
        String value = getProperty(name);
        if (value == null) {
            log.debug("�����ļ�["+applicationContext.getConfigfile() +"]��û��ָ������[" + name + "]������ȱʡֵ" + defaultValue);
            return defaultValue;
        }
        return value;
    }

    public int getIntProperty(String name, int defaultValue) {
        Pro value = this.properties.get(name);
        if (value == null) {
            log.debug("�����ļ�["+applicationContext.getConfigfile() +"]��û��ָ������[" + name + "]��");
            return defaultValue;
        }
       return value.getInt(defaultValue);
        
    }

    public boolean getBooleanProperty(String name, boolean defaultValue) {
        Pro value = this.properties.get(name);
        if (value == null) {
            log.debug("�����ļ�["+applicationContext.getConfigfile() +"]��û��ָ������[" + name + "]��");
            return defaultValue;
        }
       return value.getBoolean();
    }
//    getBeanObject
    public Object getBeanObject(CallContext context, String name, Object defaultValue)
    {
        
        Pro pro = this.properties.get(name);
        if(pro == null)
        {
            log.debug("�����ļ�["+applicationContext.getConfigfile() +"]��û��ָ������[" + name + "]������ȱʡֵ" + defaultValue);
            return defaultValue;
        }
        
        return pro.getBeanObject(context,defaultValue);
    }

    public Object getBeanObject(CallContext context, Pro providerManagerInfo)
    {
        // TODO Auto-generated method stub
         return getBeanObject(context, providerManagerInfo,null);
    }
    
    
    public Object getBeanObject(CallContext context, Pro providerManagerInfo,Object defaultValue)
    {
        // TODO Auto-generated method stub
         return providerManagerInfo.getBeanObject(context,defaultValue);
    }

    public BaseApplicationContext getApplicationContext()
    {
        return applicationContext;
    }
    private boolean started = true;
	public synchronized void destroy() {
		if(!started)
			return;
		started = false;
		if(managerimports != null)
			this.managerimports.clear();
		
		if(managerimports != null)
			this.managers.clear();
		if(mutuxMangers != null)
			this.mutuxMangers.clear();
		if(reverseAttrRefids != null)
			this.reverseAttrRefids.clear();
		if(properties != null)
			this.properties.clear();
		if(reverseServiceRefids != null)
			this.reverseServiceRefids.clear();
		if(traceFiles != null)
			this.traceFiles.clear();
		
	}
	
	public boolean containsBean(String themeSourceBeanName) {
		
		return this.properties.containsKey(themeSourceBeanName);
	}

	public String[] getStringArray(String key) {
		
		 Pro value = this.properties.get(key);
	        if (value == null) {
	            throw new AssembleException("�����ļ�["+applicationContext.getConfigfile() +"]��û��ָ������[" + key + "]��");
	        }
	       return value.getStringArray();
	}

	public String[] getStringArray(String key, String[] defaultValues) {
		 Pro value = this.properties.get(key);
	        if (value == null) {
	            return defaultValues;
	        }
	       return value.getStringArray(defaultValues);
	}
	
    
 

}
