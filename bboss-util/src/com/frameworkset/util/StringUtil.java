/*****************************************************************************
 *                                                                           *
 *  This file is part of the tna framework distribution.                     *
 *  Documentation and updates may be get from  biaoping.yin the author of    *
 *  this framework							     *
 *                                                                           *
 *  Sun Public License Notice:                                               *
 *                                                                           *
 *  The contents of this file are subject to the Sun Public License Version  *
 *  1.0 (the "License"); you may not use this file except in compliance with *
 *  the License. A copy of the License is available at http://www.sun.com    *
 *                                                                             *
 *  The Original Code is tag. The Initial Developer of the Original          *
 *  Code is biaoping yin. Portions created by biaoping yin are Copyright     *
 *  (C) 2000.  All Rights Reserved.                                          *
 *                                                                           *
 *  GNU Public License Notice:                                               *
 *                                                                           *
 *  Alternatively, the contents of this file may be used under the terms of  *
 *  the GNU Lesser General Public License (the "LGPL"), in which case the    *
 *  provisions of LGPL are applicable instead of those above. If you wish to *
 *  allow use of your version of this file only under the  terms of the LGPL *
 *  and not to allow others to use your version of this file under the SPL,  *
 *  indicate your decision by deleting the provisions above and replace      *
 *  them with the notice and other provisions required by the LGPL.  If you  *
 *  do not delete the provisions above, a recipient may use your version of  *
 *  this file under either the SPL or the LGPL.                              *
 *                                                                           *
 *  biaoping.yin (yin-bp@163.com)                                            *
 *  Author of Learning Java 						     					 *
 *                                                                           *
 *****************************************************************************/
package com.frameworkset.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.Clob;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.frameworkset.util.io.Resource;

/**
 * To change for your class or interface DAO��VOObject String������PO��������ת��������.
 * 
 * @author biaoping.yin
 * @version 1.0
 */

public class StringUtil extends SimpleStringUtil {
	private static Logger log = Logger.getLogger(StringUtil.class);
	public static String getCookieValue(HttpServletRequest request,String name,String defaultvalue)
	{
		Cookie[] cookies = request.getCookies();
	
		if(cookies == null)
		{
			return defaultvalue;
		}
			String temp_ = null;
			for (Cookie temp : cookies) {
				if(name.equals(temp.getName()))
				{
					temp_ = temp.getValue();
					break;
				}
			}
			if(temp_==null){
				temp_ = defaultvalue;
			}
		return temp_;
	}
	
	public static String getCookieValue(HttpServletRequest request,String name)
	{
		return getCookieValue(request,name,null);
	}
	
	public static void  addCookieValue(HttpServletRequest request,HttpServletResponse response ,String name,String value,int maxage)
	{
		Cookie[] cookies = request.getCookies();
		
		Cookie loginPathCookie = null;
		if(cookies != null)
		{
			for(Cookie cookie:cookies)
			{
				if(name.equals(cookie.getName()))
				{
					loginPathCookie = cookie;
					break;
				}
			}
		}
		if(loginPathCookie == null)
		{
			 loginPathCookie = new Cookie(name, value);			 
			loginPathCookie.setMaxAge(maxage);
			loginPathCookie.setPath(request.getContextPath());			
			response.addCookie(loginPathCookie);
		}
		else
		{
			loginPathCookie.setMaxAge(maxage);
			loginPathCookie.setValue(value);
			loginPathCookie.setPath(request.getContextPath());	
			response.addCookie(loginPathCookie);
//			loginPathCookie.setPath(request.getContextPath());
		}
	}
	
	public static void  addCookieValue(HttpServletRequest request,HttpServletResponse response ,String name,String value)
	{
		addCookieValue( request, response , name, value,3600 * 24);
	}
	public static String getRealPath(HttpServletRequest request, String path) {
		String contextPath = request.getContextPath();

		if (contextPath == null) {
//			System.out.println("StringUtil.getRealPath() contextPath:"
//					+ contextPath);
			return path;
		}
		if (path == null) {
			return null;
		}
		if (path.startsWith("/") && !path.startsWith(contextPath + "/")) {
			if (!contextPath.equals("/"))
				return contextPath + path;
			else {
				return path;
			}

		} else {
			return path;
		}

	}
	
	

	public static String getParameter(HttpServletRequest request, String name,
			String defaultValue) {
		String value = request.getParameter(name);
		return value != null ? value : defaultValue;
	}

	public static void main(String args[]) {
//		String str = "����,'bb,cc,'dd";
//		try {
//			str = new String(str.getBytes(), "utf-8");
//		} catch (UnsupportedEncodingException ex) {
//		}
//		System.out.println(str.getBytes()[0]);
//		System.out.println(str.getBytes()[1]);
//		System.out.println(str.getBytes()[2]);
//		System.out.println(str.getBytes()[3]);
//
//		System.out.println("?".getBytes()[0]);
		int maxlength = 16;
		String replace  ="...";
		String outStr = "2010��02��04��12ʱ���ν�����Ů��1987��06��18���������֤��430981198706184686������ʡ�佭���佭���ϴ���������ʮ��������24�ţ��������侭Ӫ�������е��Ŷ���������װ�걻���ˡ��Ӿ������������������ֳ��˽�ϵ����������12ʱ��ν�����ĸ�׻�־Ԫ�ڵ��������⣬�������ӽ�����ڣ�����һ�����԰���ڵ����¾���ȥ����ע��������һ���Ӿͽ��е��ԣ���ȡ�����������̨������700Ԫ�����";
		
		System.out.println(StringUtil.getHandleString(maxlength,replace,false,false,outStr));
		
outStr = "2010��02��07��11ʱ������ӱ��������2·�������ϱ����ԣ���ץ��һ�������ˡ��񾯳����󣬾����飬����ӱ�ڵ���10ʱ40������2·�������ϣ�;�б��������Ӱ����ֽ�3100Ԫ��һ��������ץ����һ�������ߡ� ";
		
		System.out.println(StringUtil.getHandleString(maxlength,replace,false,false,outStr));
	}

	
//	private static String handleCNName(String name,HttpServletRequest request) throws UnsupportedEncodingException
//	{
//		 if(isIE(request))
//         {
//	            name = java.net.URLEncoder.encode(name.replaceAll(" ", "-"),"UTF-8");
////	            response.setHeader("Content-Disposition", "attachment; filename=" + name);
//         }
//         else
//         {
//         	name = new String(name.getBytes(),"ISO-8859-1").replaceAll(" ", "-");
////	            response.setHeader("Content-Disposition", "attachment; filename=" + name);
//         }
//		 return name;
//	}
	public static void sendFile(HttpServletRequest request, HttpServletResponse response, File file) throws Exception {
        OutputStream out = null;
        RandomAccessFile raf = null;
        try {
        	raf = new RandomAccessFile(file, "r");
        	out =  response.getOutputStream();
            long fileSize = raf.length();
            long rangeStart = 0;
            long rangeFinish = fileSize - 1;

            // accept attempts to resume download (if any)
            String range = request.getHeader("Range");
            if (range != null && range.startsWith("bytes=")) {
                String pureRange = range.replaceAll("bytes=", "");
                int rangeSep = pureRange.indexOf("-");

                try {
                    rangeStart = Long.parseLong(pureRange.substring(0, rangeSep));
                    if (rangeStart > fileSize || rangeStart < 0) rangeStart = 0;
                } catch (NumberFormatException e) {
                    // ignore the exception, keep rangeStart unchanged
                }

                if (rangeSep < pureRange.length() - 1) {
                    try {
                        rangeFinish = Long.parseLong(pureRange.substring(rangeSep + 1));
                        if (rangeFinish < 0 || rangeFinish >= fileSize) rangeFinish = fileSize - 1;
                    } catch (NumberFormatException e) {
                        // ignore the exception
                    }
                }
            }

            // set some headers
            String name = handleCNName(file.getName(),request);
            response.setHeader("Content-Disposition", "attachment; filename=" + name);
//            response.setHeader("Content-Disposition", "attachment; filename=" + new String(file.getName().getBytes(),"ISO-8859-1").replaceAll(" ", "-"));
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Content-Length", Long.toString(rangeFinish - rangeStart + 1));
            response.setHeader("Content-Range", "bytes " + rangeStart + "-" + rangeFinish + "/" + fileSize);

            // seek to the requested offset
            raf.seek(rangeStart);

            // send the file
            byte buffer[] = new byte[1024];

            long len;
            int totalRead = 0;
            boolean nomore = false;
            while (true) {
                len = raf.read(buffer);
                if (len > 0 && totalRead + len > rangeFinish - rangeStart + 1) {
                    // read more then required?
                    // adjust the length
                    len = rangeFinish - rangeStart + 1 - totalRead;
                    nomore = true;
                }

                if (len > 0) {
                    out.write(buffer, 0, (int) len);
                    totalRead += len;
                    if (nomore) break;
                } else {
                    break;
                }
            }
            out.flush();
        } 
        catch(Exception e)
        {
        	throw e;
        }
        finally {
            try
			{
				raf.close();
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
            try
			{
				out.close();
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
 
 public static void sendFile(HttpServletRequest request, HttpServletResponse response, String filename,Blob blob) throws Exception {
	 if(blob == null)
		 return ;
	 sendFile_( request,  response,  filename,blob.getBinaryStream(),blob.length());
 }
 
 public static void sendFile(HttpServletRequest request, HttpServletResponse response, String filename,Clob clob) throws Exception {
	 if(clob == null)
		 return ;
	 sendFile_( request,  response,  filename,clob.getAsciiStream(),clob.length());
 }
 public static boolean isIE(HttpServletRequest request)
 {
	 String agent = request.getHeader("User-Agent");
//	 log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>isie:" + agent);
     boolean isie = agent.contains("MSIE ");
//     log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>isie:" + isie);
     return isie;
 }
 public static boolean isIE6(HttpServletRequest request)
 {
	 String clientInfo = request.getHeader("User-Agent");
	  if(clientInfo.indexOf("MSIE 6") > 0 || clientInfo.indexOf("MSIE 5") > 0){//IE6����GBK���˴�ʵ���ɾ�����
	        return true;
	  }
	  else
		  return false;
 }
 
 public static String handleCNName(String name,HttpServletRequest request) throws UnsupportedEncodingException
 {
	 
	 String agent = request.getHeader("User-Agent");
//	 log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>isie:" + agent);
     boolean isie = agent.contains("MSIE ");
     if(isie )
     {
//    	 if( (agent.indexOf("MSIE 6") > 0 || agent.indexOf("MSIE 5") > 0))
//    	 {
//    		 name = new String(name.getBytes(),"ISO-8859-1").replaceAll(" ", "-"); 
//    	 }
//    	 else
    	 {
    		 name = java.net.URLEncoder.encode(name.replaceAll(" ", "-"),"UTF-8");
    	 }
     }
     else
     {
    	 name = new String(name.getBytes(),"ISO-8859-1").replaceAll(" ", "-"); 
     }
     return name;
     
	 
 }
 

 public static void sendFile_(HttpServletRequest request, HttpServletResponse response, Resource in) throws Exception {
//	 if(in == null || in.exists())
//		 throw new IOException("��Դ������,����ʧ��");
	
	 sendFile_(request, response, in.getFilename(),in.getInputStream());
 }
 public static void sendFile_(HttpServletRequest request, HttpServletResponse response, String filename,InputStream in) throws Exception {
     OutputStream out = null;
//     InputStream in = null;
     try {
     	if(in == null)
     		return;
     	out = response.getOutputStream();
     	

         String name = StringUtil.handleCNName(filename,request);
         response.setContentType("Application");
         response.setHeader("Content-Disposition", "attachment; filename=" + name);
//         response.setHeader("Content-Disposition", "attachment; filename=" + new String(filename.getBytes(),"ISO-8859-1").replaceAll(" ", "-"));
//         response.setHeader("Accept-Ranges", "bytes");
//         response.setHeader("Content-Length", Long.toString(rangeFinish - rangeStart + 1));
//         response.setHeader("Content-Range", "bytes " + rangeStart + "-" + rangeFinish + "/" + fileSize);

         // seek to the requested offset
         

         // send the file
         byte buffer[] = new byte[1024];
//         in.skip(rangeStart);
         long len;
         int totalRead = 0;
//         boolean nomore = false;
         while (true) {
             len = in.read(buffer);
             if(len > 0)
             {
	                out.write(buffer, 0, (int) len);
	                totalRead += len;
             }
             else
             {
             	break;
             }
                 
         }
         out.flush();
     }
     catch(Exception e)
     {
     	e.printStackTrace();
     	throw e;
     }
     finally {
     	try
			{
     		if(in != null)
     			in.close();		
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         try
			{
         	if(out != null)
         		out.close();
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     }
 }
 public static void sendFile_(HttpServletRequest request, HttpServletResponse response, String filename,InputStream in,long fileSize) throws Exception {
        OutputStream out = null;
//        InputStream in = null;
        try {
        	if(in == null)
        		return;
        	out = response.getOutputStream();
        	
//        	if(blob == null)
//        		return ;
//        	in = blob.getBinaryStream();
//            long fileSize = blob.length();
            long rangeStart = 0;
            long rangeFinish = fileSize - 1;

            // accept attempts to resume download (if any)
            String range = request.getHeader("Range");
            if (range != null && range.startsWith("bytes=")) {
                String pureRange = range.replaceAll("bytes=", "");
                int rangeSep = pureRange.indexOf("-");

                try {
                    rangeStart = Long.parseLong(pureRange.substring(0, rangeSep));
                    if (rangeStart > fileSize || rangeStart < 0) rangeStart = 0;
                } catch (NumberFormatException e) {
                    // ignore the exception, keep rangeStart unchanged
                }

                if (rangeSep < pureRange.length() - 1) {
                    try {
                        rangeFinish = Long.parseLong(pureRange.substring(rangeSep + 1));
                        if (rangeFinish < 0 || rangeFinish >= fileSize) rangeFinish = fileSize - 1;
                    } catch (NumberFormatException e) {
                        // ignore the exception
                    }
                }
            }

            // set some headers
            
//            if(isIE(request))
//            {
//	            String name = java.net.URLEncoder.encode(filename.replaceAll(" ", "-"));
//	            response.setHeader("Content-Disposition", "attachment; filename=" + name);
//            }
//            else
//            {
//            	 response.setHeader("Content-Disposition", "attachment; filename=" + new String(filename.getBytes(),"ISO-8859-1").replaceAll(" ", "-"));
//            }
            String name = handleCNName(filename,request);
            response.setHeader("Content-Disposition", "attachment; filename=" + name);
//            response.setHeader("Content-Disposition", "attachment; filename=" + new String(filename.getBytes(),"ISO-8859-1").replaceAll(" ", "-"));
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Content-Length", Long.toString(rangeFinish - rangeStart + 1));
            response.setHeader("Content-Range", "bytes " + rangeStart + "-" + rangeFinish + "/" + fileSize);

            // seek to the requested offset
            

            // send the file
            byte buffer[] = new byte[1024];
            in.skip(rangeStart);
            long len;
            int totalRead = 0;
            boolean nomore = false;
            while (true) {
                len = in.read(buffer);
                if (len > 0 && totalRead + len > rangeFinish - rangeStart + 1) {
                    // read more then required?
                    // adjust the length
                    len = rangeFinish - rangeStart + 1 - totalRead;
                    nomore = true;
                }

                if (len > 0) {
                    out.write(buffer, 0, (int) len);
                    totalRead += len;
                    if (nomore) break;
                } else {
                    break;
                }
            }
            out.flush();
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	throw e;
        }
        finally {
        	try
			{
        		if(in != null)
        			in.close();		
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            try
			{
            	if(out != null)
            		out.close();
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
 
 	
}
