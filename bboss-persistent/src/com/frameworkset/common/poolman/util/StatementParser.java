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
package com.frameworkset.common.poolman.util;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;
import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;

import com.frameworkset.common.poolman.StatementInfo;
import com.frameworkset.common.poolman.sql.ParserException;
import com.frameworkset.common.poolman.sql.PrimaryKey;
import com.frameworkset.common.poolman.sql.PrimaryKeyCacheManager;
import com.frameworkset.common.poolman.sql.Sequence;
import com.frameworkset.common.poolman.sql.UpdateSQL;
import com.frameworkset.util.RegexUtil;
import com.frameworkset.util.SimpleStringUtil;

/**
 * ����sql������
 * 
 * @author biaoping.yin created on 2005-3-31 version 1.0
 */
public class StatementParser implements Serializable
{
	 private static Logger log = Logger.getLogger(StatementParser.class);

	/**
	 * insert������ʽ����insert��� ���insert�����������ʽƥ�䣬���������ݱ��ʽ�����ķ���
	 * ��insert�����Ϊ6����,���ҽ��⼸���ִ�ŵ��ַ������鷵�� �ֱ�Ϊ�� 1.insert�ؼ��� 2.into�ؼ��� 3.������ 4.���ֶ���
	 * 5.values�ؼ��� 6.�����ֶ�ֵ��
	 * 
	 * ����insert��䣺 Insert into oa_meetingpromptsound ( soundCode , soundName ,
	 * soundFileName ) values ( '��.����ƽ','bb','d()d' ) �����ֽ�Ϊ���²��֣� 1.Insert 2.into
	 * 3.oa_meetingpromptsound 4.( soundCode , soundName , soundFileName )
	 * 5.values 6.( '��.����ƽ','bb','d()d' )
	 */
	public static String[] parserInsert(String insert)
	{
		/**
		 * ����insert����������ʽ �ñ��ʽ��insert�����Ϊ6����,�ֱ�Ϊ�� 1.insert�ؼ��� 2.into�ؼ��� 3.������
		 * 4.���ֶ��� 5.values�ؼ��� 6.�����ֶ�ֵ�� ����insert��䣺 Insert into
		 * oa_meetingpromptsound ( soundCode , soundName , soundFileName )
		 * values ( '��.����ƽ','bb','d()d' ) �����ֽ�Ϊ���²��֣� 1.Insert 2.into
		 * 3.oa_meetingpromptsound 4.( soundCode , soundName , soundFileName )
		 * 5.values 6.( '��.����ƽ','bb','d()d' )
		 */
		String patternStr = "\\s*(insert)\\s+" + // ����insert�ؼ���
				"(into)\\s+" + // ����into�ؼ���
				"([^\\(^\\s]+)\\s*" + // ����������
				"(\\([^\\)]+\\))\\s*" + // �������ֶ�
				"(values)\\s*" + // ����value�ؼ���
				"(\\(.*(.*\n*)*.*)"; // �����ֶ�ֵ

		/**
		 * ����������ʽpatternStr�����øñ��ʽ�봫���sql������ģʽƥ��,
		 * ���ƥ����ȷ�����ƥ���������ȡ�����϶���õ�6���֣���ŵ������в����� ������
		 */

		PatternCompiler compiler = new Perl5Compiler();
		Pattern pattern = null;
		try
		{
			pattern = compiler.compile(patternStr,
					Perl5Compiler.CASE_INSENSITIVE_MASK);
		}
		catch (MalformedPatternException e)
		{
			e.printStackTrace();

			return null;
		}
		PatternMatcher matcher = new Perl5Matcher();
		MatchResult result = null;
		String[] tokens = null;
		boolean match = matcher.matches(insert, pattern);

		if (match)
		{
			result = matcher.getMatch();
			tokens = new String[6];
			for (int i = 0; i < 6; i++)
			{
				tokens[i] = result.group(i + 1).trim();
			}
		}

		return tokens;
	}

	public static String[] parseField(String fieldStr)
	{
        
        
        // ( soundCode , soundName , soundFileName )
		//fieldStr = fieldStr.trim();
		// String regx = "\\([\\s*([^\\,]+)\\,?]+\\)";
		String regx = "([^\\,^\\(^\\)]+)\\s*\\,?\\s*";
        return RegexUtil.containWithPatternMatcherInput(fieldStr,regx);
//		PatternCompiler compiler = new Perl5Compiler();
//		Pattern pattern = null;
//		try
//		{
//			pattern = compiler.compile(regx,
//					Perl5Compiler.CASE_INSENSITIVE_MASK);
//		}
//		catch (MalformedPatternException e)
//		{
//			e.printStackTrace();
//			return null;
//		}
//		PatternMatcher matcher = new Perl5Matcher();
//		MatchResult result = null;
//		String[] tokens = null;
//		if (matcher.matches(fieldStr, pattern))
//		{
//			result = matcher.getMatch();
//
//			tokens = new String[result.groups()];
//			for (int i = 0; i < result.groups(); i++)
//			{
//				tokens[i] = result.group(i + 1).trim();
//			}
//		}
//		return tokens;
	}

	public static String[] parserValues(String values)
	{
		
		String patternStr = "([^\\,]*)[\\,]?"; // �����ֶ�ֵ
		
		String patternStr1 = "('?[^\\,]*'?)[\\,]?"; // �����ֶ�ֵ

		/**
		 * ����������ʽpatternStr�����øñ��ʽ�봫���sql������ģʽƥ��,
		 * ���ƥ����ȷ�����ƥ���������ȡ�����϶���õ�6���֣���ŵ������в����� ������
		 */
		String[] ret = RegexUtil.containWithPatternMatcherInput(values,patternStr1);

		PatternCompiler compiler = new Perl5Compiler();
		Pattern pattern = null;
		try
		{
			pattern = compiler.compile(patternStr,
					Perl5Compiler.CASE_INSENSITIVE_MASK);
		}
		catch (MalformedPatternException e)
		{
			e.printStackTrace();

			return null;
		}
		PatternMatcher matcher = new Perl5Matcher();
		MatchResult result = null;
		String[] tokens = null;
		boolean match = matcher.matches(values.trim(), pattern);
		System.out.println(match);
		if (match)
		{
			result = matcher.getMatch();
			tokens = new String[6];
			for (int i = 0; i < 6; i++)
			{
				tokens[i] = result.group(i + 1).trim();
				System.out.println(tokens[i]);
			}
		}

		return tokens;
	}
	
	class ParserValues
	{
		Stack operations = new Stack();
	}
	public static void main(String[] args)
	{
		
		String insert = "insert into td_cms_channel(NAME, DISPLAY_NAME, PARENT_ID,CHNL_PATH, CREATEUSER, CREATETIME, ORDER_NO,SITE_ID, STATUS, OUTLINE_TPL_ID, DETAIL_TPL_ID,CHNL_OUTLINE_DYNAMIC, DOC_DYNAMIC,CHNL_OUTLINE_PROTECT, DOC_PROTECT,WORKFLOW,PARENT_WORKFLOW) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//		String insert = "insert into TD_CMS_DOCUMENT(TITLE, SUBTITLE, AUTHOR,DOCKIND,DOCOUTPUTID,CONTENT,STATUS,  KEYWORDS,DOCABSTRACT,DOCTYPE,DOCLEVEL,DOCWTIME,TITLECOLOR, PUBLISHTIME,DOCFLAG,DOCPUBURL,DOCUMENTPRIOR,CREATEUSER,   CREATETIME,LASTVISITTIME,PARENTDOCUMENT_ID, DOCSOURCE_ID,DETAILTEMPLATE_ID,CHANNEL_ID,USER_ID,ISLOCK,LINKTARGET,LINKFILE)   values('eee','eee','eee', 'null','null','', 0,'eee','eee',0, 0,TO_DATE('29-12-2006 10:54:49', 'DD-MM-YYYY HH24:MI:SS'),'#000000',TO_DATE('29-12-2006 10:54:49', 'DD-MM-YYYY HH24:MI:SS'),0, 'null',100,0,TO_DATE('29-12-2006 10:54:49', 'DD-MM-YYYY HH24:MI:SS'), TO_DATE('29-12-2006 10:54:49', 'DD-MM-YYYY HH24:MI:SS'),0,2,1, 2,1,0,'null','null')";
		parserInsert(insert);
//		String update = "update table1 set name='aaa' where id='1'";
//		parserUpdateSQL(update);
//		String patternStr = "(insert)\\s+" + // ����insert�ؼ���
//				"(into)\\s+" + // ����into�ؼ���
//				"([^\\(]+)\\s*" + // ����������
//				"(\\([^\\)]+\\))\\s+" + // �������ֶ�
//				"(values)\\s*" + // ����value�ؼ���
//				"(\\(.+)"; // �����ֶ�ֵ
//
//		// parseField("( soundCode , soundName , soundFileName )");\
//		/**
//		 * 'XXXXX',
//		 * ',XXXXXXX,
//		 * XXXXXXXX,'
//		 * ','
//		 * ,
//		 * ',XXXXX'
//		 */
//		String sql = "'������ ͬ־�� 2005-05-12 ��ʾ��\r\n"
//				+ "��Ϥ����ɳ�����������������·�����Ƕ�������,У8��ʦ��������һ������¡��ñ������Ὠ�飬�볤ɳ�����������о������ƴ���\r\n"
//				+ "������ʡ����ʾ��',to_date('2005-04-23 11:04:44','yyyy-mm-dd hh24:mi:ss'),2,1558";
////		for (int i = 0; i < 1; i++)
////			parserValues(sql);
//        
//        String fields = "( name , id , desc )";
//        String ret[] = parseField(fields);
//        String sqls[] = parserInsert("insert into TD_CMS_DOCUMENT(TITLE, SUBTITLE, AUTHOR,DOCKIND,DOCOUTPUTID,CONTENT,STATUS,  KEYWORDS,DOCABSTRACT,DOCTYPE,DOCLEVEL,DOCWTIME,TITLECOLOR, PUBLISHTIME,DOCFLAG,DOCPUBURL,DOCUMENTPRIOR,CREATEUSER,   CREATETIME,LASTVISITTIME,PARENTDOCUMENT_ID, DOCSOURCE_ID,DETAILTEMPLATE_ID,CHANNEL_ID,USER_ID,ISLOCK,LINKTARGET,LINKFILE)   values('eee','eee','eee', 'null','null','', 0,'eee','eee',0, 0,TO_DATE('29-12-2006 10:54:49', 'DD-MM-YYYY HH24:MI:SS'),'#000000',TO_DATE('29-12-2006 10:54:49', 'DD-MM-YYYY HH24:MI:SS'),0, 'null',100,0,TO_DATE('29-12-2006 10:54:49', 'DD-MM-YYYY HH24:MI:SS'), TO_DATE('29-12-2006 10:54:49', 'DD-MM-YYYY HH24:MI:SS'),0,2,1, 2,1,0,'null','null')");
//        
//        System.out.println(ret.length);
//        System.out.println(sqls.length);
//		// System.out.println("dbName:" +
//		// SQLManager.getInstance().getDefaultDBName());
//		// Object[] temp =
//		// PoolManStatement.refactorInsertStatement(sql,SQLManager.getInstance().getDefaultDBName());
//		// System.out.println(temp[0]);
//		// System.out.println(temp[1]);
//		// System.out.println(temp[2]);
//		// System.out.println(temp[3]);
//		//        
//		// parserInsert(sql);
	}
	
//	/**
//	 * ����updatesql����а����ı������ؼ��֣��͸��²��֣�table name
//	 * ���磺update table1 set name='aaa' where id='1';
//	 * @param updateSQL
//	 * @return String[4] {update,table1,set,values and where clause}
//	 */
//	public static String[] parserUpdateSQL(String updateSQL)
//	{
//		String regex = "\\s*(update)\\s+([^\\s]+)\\s+(set)\\s+(.+)";
//		PatternCompiler compiler = new Perl5Compiler();
//		Pattern pattern = null;
//		try
//		{
//			pattern = compiler.compile(regex,
//					Perl5Compiler.CASE_INSENSITIVE_MASK);
//		}
//		catch (MalformedPatternException e)
//		{
//			e.printStackTrace();
//
//			return null;
//		}
//		PatternMatcher matcher = new Perl5Matcher();
//		MatchResult result = null;
//		String[] tokens = null;
//		boolean match = matcher.matches(updateSQL, pattern);
//
//		if (match)
//		{
//			result = matcher.getMatch();
//			tokens = new String[4];
//			for (int i = 0; i < 4; i++)
//			{
//				tokens[i] = result.group(i + 1).trim();
//			}
//		}
//
//		return tokens;
//	}
//	public static Object[] refactorUpdateStatement(StatementInfo stateinfo) throws ParserException
//	{
//		return refactorUpdateStatement(stateinfo.getCon(),stateinfo.getSql(),stateinfo.getDbname());
//	}
//	 /**
//     * ���update��䣬��ȡupdate�ı�Ͷ�Ӧ�������ֶ���Ϣ
//     * @param updateStmt
//     * @param dbname
//     * @return
//     * @throws ParserException 
//     */
//    private static Object[] refactorUpdateStatement(Connection con,String updateStmt,String dbname) throws ParserException
//    {
//    	//{update,table1,set,values and where clause}
//    	String[] updateInfos = StatementParser.parserUpdateSQL(updateStmt);
//    	if(updateInfos == null)
//    		throw new ParserException("�Ƿ��ĸ�����䣺" + updateStmt);
//    		
//    	String tableName = updateInfos[1];
//    	PrimaryKey primaryKey = null;
//        try
//		{
//
//	        primaryKey = PrimaryKeyCacheManager.getInstance()
//														  .getPrimaryKeyCache(dbname)
//														  .getIDTable(con,tableName.toLowerCase());
//	        if(primaryKey == null)
//			{
//				//System.out.println("��'" + tableName + "'û�ж������������˶�������");
//	        	primaryKey = new PrimaryKey(dbname,updateInfos[1],null,null,con);
//	        	primaryKey.setHasTableinfo(false);
//				log.debug("δ����["+ tableName + "]����������Ϣ,����tableinfo�����Ƿ���ڸñ�ļ�¼��" + updateStmt);
//			}
//	        return new Object[] {primaryKey,updateInfos};
//
//		}
//        catch(Exception e)
//		{
//        	log.info("[db:"
//        	        + dbname
//        	        + "] tableinfo not initialed or initial failed"
//        	        + " or check the table "
//        	        + "[tableinfo has been created] or table[" + tableName
//        	        + "'s information has been inserted into tableinfo"
//        	        + "]\r\n please check log to view detail.");
//        	//e.printStackTrace();
//        	throw new ParserException("δ����["+ tableName + "]����������Ϣ,����tableinfo�����Ƿ���ڸñ�ļ�¼��" + updateStmt);
//        	
//
//			
//		}
//		
//    	
//    }
    
    /**
     * added by biaoping.yin on 2005.03.29
     * �ع�insert���,���������Ϣ�����Ȼ�ȡ����ֵ
     * @param insertStmt ���ݿ�������
     * @param dbname ���ݿ�����
     * @return ret
     * ret[0]:���insert���
     * ret[1]:����µ�����ֵ,������ǲ��������Ϊ��
     * ret[2]:���±�tableinfo�в�����Ӧ����ֵ���,������ǲ��������Ϊ��
     * ret[3]:PrimaryKey����,������ǲ��������Ϊ��
     * ret[4]:��ʶ�������Զ����������û�ָ��,0-��ʶ�Զ�
     * 		  1-��ʶ�û�ָ��
     *        ������ǲ��������Ϊ��
     * ret[6]:field�ֶ����飻
     * @throws SQLException 
     * 
     */
    public static Object[] refactorInsertStatement(Connection con,String insertStmt,String dbname) throws SQLException
    {
    	return refactorInsertStatement(con,insertStmt,dbname,false);
        
    }
    
    public static Object[] refactorInsertStatement(StatementInfo stmtInfo) throws SQLException
    {
    	return refactorInsertStatement(stmtInfo.getCon(),stmtInfo.getSql(),stmtInfo.getDbname(), stmtInfo.isPrepared());
    }

    /**
     * added by biaoping.yin on 2005.03.29
     * �ع�insert���,���������Ϣ�����Ȼ�ȡ����ֵ
     * @param insertStmt ���ݿ�������
     * @param dbname ���ݿ�����
     * @return ret
     * ret[0]:���insert���
     * ret[1]:����µ�����ֵ,������ǲ��������Ϊ��
     * ret[2]:���±�tableinfo�в�����Ӧ����ֵ���,������ǲ��������Ϊ��
     * ret[3]:PrimaryKey����,������ǲ��������Ϊ��
     * ret[4]:��ʶ�������Զ����������û�ָ��,0-��ʶ�Զ�
     * 		  1-��ʶ�û�ָ��
     *        ������ǲ��������Ϊ��
     * ret[6]:field�ֶ����飻
     * @throws SQLException 
     * 
     */
    public static Object[] refactorInsertStatement(Connection con ,String insertStmt,String dbname,boolean prepared) throws SQLException
    {
        Object[] ret = new Object[6];

        //String tableName = this.getInsertTableName(insertStmt);
        //this.getInsertTableName(insertStmt);
        /**
         *  1.Insert
		 * 	2.into
		 * 	3.oa_meetingpromptsound
		 * 	4.( soundCode , soundName , soundFileName )
		 * 	5.values
		 * 	6.( '��.����ƽ','bb','d()d' )
         */
        String tableInfos[] = StatementParser.parserInsert(insertStmt);
        if(tableInfos == null)
        {
            ret[0] = insertStmt;
            return ret;
        }
        String insert = tableInfos[0];
        String into   = tableInfos[1];
        String tableName = tableInfos[2];
        String fields = tableInfos[3];

        String values_key = tableInfos[4];
        String values = tableInfos[5];
        PrimaryKey primaryKey = null;
        try
		{

	        primaryKey = PrimaryKeyCacheManager.getInstance()
														  .getPrimaryKeyCache(dbname)
														  .getIDTable(con,tableName.toLowerCase());

		}
        catch(Exception e)//����׳�sql�쳣�����ԣ���Ӱ����������Ĵ���
		{
        	log.info("[db:"
        	        + dbname
        	        + "] tableinfo not initialed or initial failed"
        	        + " or check the table "
        	        + "[tableinfo has been created] or table[" + tableName
        	        + "'s information has been inserted into tableinfo"
        	        + "]\r\n please check log to view detail.");
        	//e.printStackTrace();

        	ret[0] = insertStmt;
        	
			return ret;
		}
		if(primaryKey == null)
		{
			//System.out.println("��'" + tableName + "'û�ж������������˶�������");
			ret[0] = insertStmt;
			PrimaryKey t_p = new PrimaryKey(dbname,tableName,null,null,con);
	        ret[3] = t_p;
			return ret;
		}


        //�������������Ʊ���
        String idName = primaryKey.getPrimaryKeyName();
        boolean contain = containKey(fields,idName);
        ret[4] = new Integer(0);
        ret[5] = StatementParser.parseField(fields);

        if(contain)
        {
        	String s_temp = values.trim();
        	s_temp = s_temp.substring(1,s_temp.length() -1);
        	String keyValue = getKeyValue(s_temp);
            ret[0] = insertStmt;
            ret[1] = keyValue;
            PrimaryKey t_p = new PrimaryKey(primaryKey.getDbname(),
            								primaryKey.getTableName(),
            								primaryKey.getPrimaryKeyName(),
            								keyValue,con);
            ret[3] = t_p;
            ret[4] = new Integer(1);            
            return ret;
        }
        //����������ֶ����Բ��뵽insert�����
        StringBuffer temp = new StringBuffer(fields);
        temp.insert(1,idName + ",");
        fields = temp.toString();

        
        temp = new StringBuffer(values);
//      //��������ֵ����
        Sequence idValue = primaryKey.generateObjectKey(con);
        temp.insert(1,PrimaryKey.changeID(idValue.getPrimaryKey(),dbname,primaryKey.getType()) + ",");
        values = temp.toString();
        temp = new StringBuffer(insert);
        temp.append(" ")
			.append(into)
			.append(" ")
			.append(tableName)
			.append(fields)
			.append(" ")
			.append(values_key)
			.append(values);
        //started here
        //����insert���
        ret[0] = temp.toString();
        //�����µ�����ֵ
        ret[1] = idValue.getPrimaryKey();
        //���ø���tableinfo�����
        List datas = new ArrayList();
        datas.add(new Long(idValue.getSequence()));
        datas.add(tableName.toLowerCase());
        datas.add(new Long(idValue.getSequence()));
        
        UpdateSQL preparedUpdate = new UpdateSQL(dbname,tableName,UpdateSQL.TABLE_INFO_UPDATE,  datas);
//        ret[2] = "update tableinfo set table_id_value=" + idValue.getSequence() +" where table_name='"+ tableName.toLowerCase() + "' and table_id_value <" + idValue.getSequence()  ;
        ret[2] = preparedUpdate  ;

        //���ñ�������Ϣ��װ��
        ret[3] = primaryKey;
//        ret[4] = new Integer(0);

        return ret;
    }
    
    private static String getKeyValue(String values)
	{
		String regx = "";
		return "";
	}
    
    /**
     * �ж��Ƿ���������
     * @param fields
     * @param idName
     * @return boolean
     */
    private static boolean containKey(String fields,String idName)
    {
        String temp = fields.trim();
        temp = temp.substring(1,temp.length() - 1);
        //System.out.println(temp);
        String field[] = SimpleStringUtil.split(temp,",");
        for(int i = 0; field != null && i < field.length; i ++)
        {
            if(field[i].trim().equalsIgnoreCase(idName.trim()))
                return true;
        }
        return false;
    }
    
     
}
