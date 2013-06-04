package com.frameworkset.common.poolman.handle;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.frameworkset.common.poolman.Record;
import com.frameworkset.common.poolman.util.SQLUtil;
import com.frameworkset.orm.engine.model.SchemaType;

/**
 * 
 * 
 * <p>Title: XMLRowHandler.java</p>
 *
 * <p>Description: xml�д�����</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *

 * @Date Oct 24, 2008 2:29:31 PM
 * @author biaoping.yin
 * @version 1.0
 */
public class XMLRowHandler extends BaseRowHandler<StringBuffer> {
       
        /**
         * rowValue����ΪStringBuffer
         */
	public void handleRow(StringBuffer rowValue,Record origine) 
	{
	    if(meta == null)
            {
                throw new RowHandlerException("Դ���ݶ���[meta]δ��ʼ��,�޷������д���.");
            }
	    StringBuffer record = (StringBuffer)rowValue;
	    record.append("    <record>\r\n");
	    
	    try
	    {
	        
    	        for (int i = 0; i < meta.getColumnCounts(); i++) {
                    String columnName = meta.getColumnLabelUpper(i + 1);  
                    int sqltype = meta.getColumnType(i + 1);
                    
                    String object = origine.getString(columnName);
                    SchemaType schemaType = SQLUtil.getSchemaType(dbname, sqltype);
                    record.append(buildNode("column",
                                      columnName,
                                      schemaType.getName(),
                                      schemaType.getJavaType(),
                                      object,
                                      "\r\n"));
//                    record.append("\r\n\t\t<column name=\"").append(
//                                    columnName).append(
//                                    "\" type=\"").append(schemaType.getName())
//                                    .append("\" javatype=\"").append(
//                                                    schemaType.getJavaType()).append("\"")
//                                    .append(">\r\n")
//                                    .append("\t\t\t<![CDATA[")
//                                    // .append(ResultMap.getStringFromObject(object))//��Ҫת����String����
//                                    .append(object)// ��Ҫת����String����
//                                    .append("]]>\r\n").append(
//                                                    "\t\t</column>");                                    
                    
                }
	    }
	    catch(Exception e)
	    {
	        throw new RowHandlerException(e);
	    }
            record.append("    </record>\r\n");
	}
	
	
	/**
	 * ����xml���ĸ��ڵ�����
	 * ȱʡΪrecords���û�������չ�������
	 * @return
	 */
	public String getRootName()
	{
	    return "records";
	}
	
	/**
         * ����xml�ı����ַ���
         * ȱʡΪgb2312���û�������չ�������
         * @return
         */
        public String getEncoding()
        {
            return "gb2312";
        }
        
        
        /**
         * ����xml�﷨�İ汾��
         * ȱʡΪ1.0���û�������չ�������
         * @return
         */
        public String getVersion()
        {
            return "1.0";
        }
        
        public static String buildNode(String columnNodeName,
                                      String columnName,
                                      String columnType,
                                      String columnJavaType,
                                      String value,
                                      String split)
        {
            Map attributes = new HashMap();
            attributes.put("name", columnName);
            attributes.put("type", columnType);
            attributes.put("javatype", columnJavaType);
            
            return buildNode(columnNodeName,
                              attributes,
                              value,
                              split);
//            StringBuffer record = new StringBuffer();
//            record.append("\t\t<").append(columnNodeName).append(" name=\"")
//                                                             .append(columnName)
//                                                             .append("\" type=\"").append(columnType)
//                                                            .append("\" javatype=\"")
//                                                            .append(columnJavaType)
//                                                            .append("\"")
//                                                            .append(">\r\n")
//                                                            .append("\t\t\t<![CDATA[")                                                            
//                                                            .append(value)// ��Ҫת����String����
//                                                            .append("]]>\r\n")
//                                                            .append("\t\t</")
//                                                            .append(columnNodeName)
//                                                            .append(">")
//                                                            .append(split);
//            return record.toString();
        }
        
        public static String buildNode(String columnNodeName,
                                Map attributes,
                                String value,
                                String split)
      {
          StringBuffer record = new StringBuffer();
          
          record.append("\t<").append(columnNodeName);
          if(attributes != null && attributes.size() > 0)
          {
              Set entrys = attributes.entrySet();
              Iterator it = entrys.iterator();
              while(it.hasNext())
              {
                  Map.Entry e = (Map.Entry)it.next();
                  String aname = e.getKey().toString();                 
                  record.append(" ").append(aname).append("=\"")
                      .append(e.getValue())
                      .append("\"");
//                      .append("\" type=\"").append(columnType)
                 
              }
          }
          
          record.append(">");
          if(value != null)
          {
              record.append("\r\n\t    <![CDATA[")                                                            
                      .append(value)// ��Ҫת����String����
                      .append("]]>\r\n")
                      .append("\t</")
                      .append(columnNodeName)
                      .append(">")
                      .append(split);
          }
          else
          {
              record.append("</")
              .append(columnNodeName)
              .append(">")
              .append(split);
          }
          return record.toString();
      }


		
}
