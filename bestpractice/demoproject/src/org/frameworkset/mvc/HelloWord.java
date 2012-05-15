/**
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
package org.frameworkset.mvc;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.frameworkset.util.annotations.MapKey;
import org.frameworkset.util.annotations.RequestParam;
import org.frameworkset.util.annotations.ResponseBody;
import org.frameworkset.web.servlet.ModelMap;

/**
 * <p>
 * HelloWord.java
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * bboss workgroup
 * </p>
 * <p>
 * Copyright (c) 2009
 * </p>
 * 
 * @Date 2011-6-4
 * @author biaoping.yin
 * @version 1.0
 */
public class HelloWord
{

	public String sayHelloNumber(@RequestParam(name = "name") int ynum,
			ModelMap model) 
	{

		if (ynum != 0)
		{
			model.addAttribute("serverHelloNumber", "��������Ϊ[" + ynum + "]��");
			
		}
		else
			model.addAttribute("serverHelloNumber", "��������Ϊ[" + ynum
					+ "]���������е㲻��Ŷ��");

		return "path:sayHello";
	}

	public String sayHelloString(@RequestParam(name = "name") String yourname,
			ModelMap model)
	{

		if (yourname != null && !"".equals(yourname))
			model.addAttribute("serverHello", "����������[" + yourname + "]�ʺã�");
		else
			model.addAttribute("serverHello", "�������������֣�");
		return "path:sayHello";
	}

	public String sayHelloTime(
			@RequestParam(name = "d12", dateformat = "yyyy-MM-dd") java.util.Date d12,
			@RequestParam(name = "stringdate", dateformat = "yyyy-MM-dd") java.sql.Date stringdate,
			@RequestParam(name = "stringdatetimestamp", dateformat = "yyyy-MM-dd HH/mm/ss") java.sql.Timestamp stringdatetimestamp,
			@RequestParam(name = "stringdatetimestamp") String stringdatetimestamp_,
			ModelMap model)
	{

		model.put("utilDate", d12);
		model.put("sqlDate", stringdate);
		model.put("sqlTimestamp", stringdatetimestamp);
		return "path:sayHello";

	}
	
	public String sayHelloTimes(
			@RequestParam(name = "d12s", dateformat = "yyyy-MM-dd") java.util.Date[] d12,
			@RequestParam(name = "stringdates", dateformat = "yyyy-MM-dd") java.sql.Date[] stringdate,
			@RequestParam(name = "stringdatetimestamps", dateformat = "yyyy-MM-dd HH/mm/ss") java.sql.Timestamp[] stringdatetimestamp,
			@RequestParam(name = "stringdatetimestamps") String[] stringdatetimestamp_,
			ModelMap model)
	{
		if(d12 != null)
		{
			model.put("utilDates", d12[0]);
			model.put("sqlDates", stringdate[0]);
			model.put("sqlTimestamps", stringdatetimestamp[0]);
		}
		return "path:sayHello";

	}

	public String sayHelloBean(ExampleBean yourname, ModelMap model)
	{

		if (yourname.getName() != null && !"".equals(yourname.getName()))
			model.addAttribute("serverHelloBean", yourname);
		else
			;

		return "path:sayHello";
	}

	public String sayHelloBeanList(List<ExampleBean> yourname, ModelMap model)
	{

		model.addAttribute("serverHelloListBean", yourname);

		return "path:sayHello";
	}

	public String sayHelloBeanMap(@RequestParam(name = "name") String yourname,
			@MapKey("name") Map<String, ExampleBean> mapBeans, ModelMap model)
	{

		model.addAttribute("serverHelloMapBean", mapBeans.get(yourname));
		return "path:sayHello";
	}
	
	public String sayHelloArray(@RequestParam(name = "name") String[] yournames,ModelMap model)
	{
		model.addAttribute("serverHelloArray",yournames);
		return "path:sayHello";
	}
	
	/**
	 * ���Ե����ַ�����ö������ֵת��
	 * @param type
	 * @param response
	 * @throws IOException
	 */
	public   @ResponseBody(charset="GBK") 
	String sayHelloEnum(@RequestParam(name="sex") SexType type) throws IOException
	{
		if(type != null)
		{
			if(type == SexType.F)
			{
				return "Ů";
			}
			else if(type == SexType.M)
			{
				return "��";
			}
			else if(type == SexType.UN)
			{
				return "δ֪";
			}
				
		}
		
		return "δ֪";
	}
	/**
	 * ���Ե����ַ�����ö������ֵת��
	 * @param type
	 * @param response
	 * @throws IOException
	 */
	public  @ResponseBody(charset="GBK") 
	String  sayHelloEnums(@RequestParam(name="sex") SexType[] types) throws IOException
	{
		
		if(types != null)
		{
			if(types[0] == SexType.F)
			{
				
				return "Ů";
			}
			else if(types[0] == SexType.M)
			{
				
				return "��";
			}
			else if(types[0] == SexType.UN)
			{
				return "δ֪";
			}				
		}	
		return "δ֪";
	}
	
	public String index()
	{		
		return "path:sayHello";
	}
	
	public @ResponseBody String jsonp(String callback)
	{
		return callback + "({\"symbol\" : \"IBM\", \"price\" : \"91.42\"})";
	}
	public @ResponseBody String jsonpwithjquery(String callback)
	{
		return callback + "({\"symbol\" : \"IBM jquery jsonp\", \"price\" : \"91.42\"})";
	}
	
}
