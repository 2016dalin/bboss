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
package org.frameworkset.web.enumtest;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.frameworkset.util.annotations.RequestParam;
import org.frameworkset.util.annotations.ResponseBody;

/**
 * <p>Title: EnumConvertController.java</p> 
 * <p>Description: </p>
 * <p>bboss workgroup</p>
 * <p>Copyright (c) 2008</p>
 * @Date 2011-4-5
 * @author biaoping.yin
 * @version 1.0
 */

public class EnumConvertController {
	/**
	 * ���Ե����ַ�����ö������ֵת��
	 * @param type
	 * @param response
	 * @throws IOException
	 */
	public  @ResponseBody(charset="GBK") String querySex(@RequestParam(name="sex") SexType type) 
	{
		if(type != null)
		{
//			if(type == SexType.F)
//			{
//				response.setContentType("text/html; charset=GBK");
//				response.getWriter().print("Ů");
//			}
//			else if(type == SexType.M)
//			{
//				response.setContentType("text/html; charset=GBK");
//				response.getWriter().print("��");
//			}
//			else if(type == SexType.UN)
//			{
//				response.setContentType("text/html; charset=GBK");
//				response.getWriter().print("δ֪");
//			}
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
	public  @ResponseBody(charset="GBK") String queryMutiSex(@RequestParam(name="sex") SexType[] types)
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
	
	public String selectSex()
	{
		return "path:enumpage";
	}
}
