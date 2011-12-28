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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * <p>Title: LinkConfigFile.java</p>
 *
 * <p>Description: �����ļ�������Ϣ
 * ���������ļ�·��������������ļ���������Ϣ 
 * �Լ��������ļ��еĹ����������
 * 
 * ʵ�ֶ����й������ļ�غ͹���
 * </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>bboss workgroup</p>
 * @Date Aug 20, 2008 10:36:22 AM
 * @author biaoping.yin,����ƽ
 * @version 1.0
 */
public class LinkConfigFile {
	public LinkConfigFile(String fullPath,String configFile,LinkConfigFile parent)
	{
		this.configFile = configFile;
		this.parent = parent;
		linkConfigFiles = new ArrayList();
		this.fullPath = fullPath;
		this.mgrServices  = new HashMap();
	}
	
	/**
	 * �����ļ�����·��
	 */
	private String fullPath = null;
	/**
	 * �����ļ����·��
	 */
	private String configFile ;
	private Map properties = new HashMap();
	
	/**
	 * �����ļ����·����ַ
	 */
	private String relativePath ;
	/**
	 * ���������ļ�configFile�������ļ�������Ϣ
	 */
	private LinkConfigFile parent;
	
	/**
	 * �����ļ���Ӧ�����й������
	 * Map<mgrid,ProviderManagerInfo>
	 */
	private Map mgrServices = null;
	/**
	 * �����ļ�����Ĺ��������ļ�
	 * List<LinkConfigFile>
	 */
	private List linkConfigFiles = null;
	 
	public String getConfigFile() {
		return configFile;
	}
	
	public LinkConfigFile getParent() {
		return parent;
	}
	
	public ProviderManagerInfo getProviderManagerInfo(String mgrid){
		
		return (ProviderManagerInfo)mgrServices.get(mgrid);
	}
	
	
	
	
	public String toString()
	{
		StringBuffer ret = new StringBuffer();
		if(parent != null)
		{
			ret.append(this.parent.toString())
			.append("@").append(this.configFile);
		}
		else
		{
			ret.append(this.configFile);
		}
		return ret.toString();
	}
	
	public String toString(String subfile)
    {
        StringBuffer ret = new StringBuffer();
        if(parent != null)
        {
            ret.append(this.parent.toString())
            .append("@").append(this.configFile).append("@").append(subfile);
        }
        else
        {
            ret.append(this.configFile).append("@").append(subfile);
        }
        return ret.toString();
    }

	public Map getMgrServices() {
		return mgrServices;
	}

	public void setMgrServices(Map mgrServices) {
		if(mgrServices != null && mgrServices.size() > 0)
			this.mgrServices.putAll( mgrServices);
	}
	
	public Map getProperties() {
            return properties;
        }
    
        public void setProperties(Map properties) {
                if(properties != null && properties.size() > 0)
                        this.properties.putAll( properties);
        }
	
	
	

	public void addLinkConfigFile(LinkConfigFile linkConfigFile)
	{
		this.linkConfigFiles.add(linkConfigFile);
	}

	public List getLinkConfigFiles() {
		return linkConfigFiles;
	}
	
	/**
	 * �����ļ��ı�ʶ
	 * @return
	 */
	public String getIdentity()
	{
		return this.configFile;
	}
	
	public boolean hasSubLinks()
	{
		return this.linkConfigFiles != null && this.linkConfigFiles.size() > 0;
	}

	public String getFullPath() {
		return fullPath;
	}
	
	
	public boolean hasMGRService()
	{
		return this.mgrServices != null && this.mgrServices.size() > 0;
	}

	public boolean hasPros()
	{

		
		return this.properties != null && this.properties.size() > 0;
	}
	
	
}
