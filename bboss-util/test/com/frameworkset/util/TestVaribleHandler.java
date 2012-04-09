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

package com.frameworkset.util;

import java.util.List;

import org.apache.oro.text.regex.Perl5Compiler;

import com.frameworkset.util.VariableHandler.Index;
import com.frameworkset.util.VariableHandler.SQLStruction;
import com.frameworkset.util.VariableHandler.URLStruction;
import com.frameworkset.util.VariableHandler.Variable;

/**
 * <p>Title: TestVaribleHandler.java</p> 
 * <p>Description: </p>
 * <p>bboss workgroup</p>
 * <p>Copyright (c) 2008</p>
 * @Date 2010-3-10 ����02:31:03
 * @author biaoping.yin
 * @version 1.0
 */
public class TestVaribleHandler
{
    @org.junit.Test
    public void testStringReplace()
    {
        String pretoken = "#\\[";
        String endtoken = "\\]";
        String url = "#[context]/#[context0]/#[context0]creatorepp";
        String[][] vars = VariableHandler.parser2ndSubstitution(url, pretoken, endtoken, "?");
        System.out.println(vars[0][0]);
        
    }
    
    @org.junit.Test
    public void testVariableParser()
    {
        String pretoken = "#\\[";
        String endtoken = "\\]";
        String url = "#[context]/#[context0]/#[context1]creatorepp";
        String[] vars = VariableHandler.variableParser(url, pretoken, endtoken);
        
        System.out.println(vars[0]);
        System.out.println(vars[1]);
        System.out.println(vars[2]);
        
    }
    
    
    @org.junit.Test
    public void testMutiVariableParser()
    {
        String pretoken = "<clob>";
        String endtoken = "</clob>";
        String url = "<clob>a\nbc</clob></clob>b<clob>abc</clob>";
        String[][] vars = VariableHandler.parser2ndSubstitution(url, pretoken, endtoken,"?",Perl5Compiler.SINGLELINE_MASK | Perl5Compiler.DEFAULT_MASK);
        
        System.out.println(vars[0][0]);
        System.out.println(vars[1][0]);
        System.out.println(vars[1][1]);
        
    }
    
    @org.junit.Test
    public void testMutidefaltVariableParser()
    {
        
        String url = "${abc}${abcd}";
        String[][] vars = VariableHandler.parser2ndSubstitution(url,"?");
        
        System.out.println(vars[0][0]);
        System.out.println(vars[1][0]);
        System.out.println(vars[1][1]);
        
    }
    @org.junit.Test
    public void testUrlParser()
    {
    	String url = "http://localhost:80/detail.html?user=#[account[0][0]]&password=#[password->aaa[0]->bb->cc[0]]love";
        URLStruction a = VariableHandler.parserURLStruction(url);
		 url =
		 "http://localhost:80/detail.html?user=#[account&password=password]&love=#[account[key]]";
		 a = VariableHandler.parserURLStruction(url);
		 url =
			 "http://localhost:80/detail.html?user=#[account&password=password]&love=#[account";
//		 
		 a = VariableHandler.parserURLStruction(url);
		 url =
			 "http://localhost:80/detail.html?user=#[account&password=#[password&love=#[account";
		 a = VariableHandler.parserURLStruction(url);
//		 url =
//			 "http://localhost:80/detail.html";
//		 
//		 url =
//			 "http://localhost:80/#[]detail.html";
//		 url =
//			 "#[account";
		 System.out.println("url:"+url);
		// Item item = new Item();
		
		// Map<String,String> map = new HashMap<String, String>();
		// map.put("account", "aaa");
		// map.put("password", "123");
		// item.combinationItemUrlStruction(a, map);

		if(a != null){
			
		
		List<String> tokens = a.getTokens();
		for (int k = 0; k < tokens.size(); k++) {
			System.out.println("tokens[" + k + "]:" + tokens.get(k));
		}
		List<Variable> variables = a.getVariables();

		for (int k = 0; k < variables.size(); k++) {

			Variable as = variables.get(k);

			System.out.println("�������ƣ�" + as.getVariableName());
			System.out.println("������Ӧλ�ã�" + as.getPosition());

		}
		}
    }
    @org.junit.Test
    public void testSQLParser()
    {
    	 String url = "http://localhost:80/detail.html?user=#[account[0][0]]&password=#[password->aaa[0]->bb->cc[0]]love";
         URLStruction a = VariableHandler.parserSQLStruction(url);
 		 url =
 		 "http://localhost:80/detail.html?user=#[account]&password=#[password]&love=#[account[key]]";
 		 a = VariableHandler.parserSQLStruction(url);
 		url =
 	 		 "http://localhost:80/detail.html?user=#[account]&password=#[password]&love=#[account[0";
 	 		 a = VariableHandler.parserSQLStruction(url);
 	 		 
 	 		url =
 	 	 		 "http://localhost:80/detail.html?user=account&password=password&love=account";
 	 	 		 a = VariableHandler.parserSQLStruction(url);
 	 	 		url =
 	 	 	 		 "http://localhost:80/detail.html?user=account&password=password]&love=account]";
 	 	 	 		 a = VariableHandler.parserSQLStruction(url);
 		 url =
 			 "http://localhost:80/detail.html,user=#[account],password=#[password],account=#[account]";
 		 a = VariableHandler.parserSQLStruction(url);
 		 
 		 url =
 			 "http://localhost:80/#[detail.html,user=#[account],password=#[password],account=#[account]";
 		 a = VariableHandler.parserSQLStruction(url);
// 		 url =
// 			 "http://localhost:80/detail.html";
// 		 
// 		 url =
// 			 "http://localhost:80/#[]detail.html";
// 		 url =
// 			 "#[account";
 		 System.out.println("url:"+url);
 		// Item item = new Item();
 		
 		// Map<String,String> map = new HashMap<String, String>();
 		// map.put("account", "aaa");
 		// map.put("password", "123");
 		// item.combinationItemUrlStruction(a, map);

 		if(a != null){
 			
 		
	 		List<String> tokens = a.getTokens();
	 		for (int k = 0; k < tokens.size(); k++) {
	 			System.out.println("tokens[" + k + "]:" + tokens.get(k));
	 		}
	 		List<Variable> variables = a.getVariables();
	
	 		for (int k = 0; k < variables.size(); k++) {
	
	 			Variable as = variables.get(k);
	
	 			System.out.println("�������ƣ�" + as.getVariableName());
	 			System.out.println("������Ӧλ�ã�" + as.getPosition());
	 			//��������Ƕ�Ӧ���������list��set��map��Ԫ�ص�Ӧ�ã��������Ӧ��Ԫ�������±���Ϣ
	 			List<Index> idxs = as.getIndexs();
	 			if(idxs != null)
	 			{
	 				for(int h = 0; h < idxs.size(); h ++)
	 				{
	 					Index idx = idxs.get(h);
	 					if(idx.getInt_idx() > 0)
	 					{
	 						System.out.println("Ԫ�������±꣺"+idx.getInt_idx());
	 					}
	 					else
	 					{
	 						System.out.println("map key��"+idx.getString_idx());
	 					}
	 				}
	 			}
	
	 		}
 		}
    }
    /**
     * �Ա�������ʽ����������VariableHandler����sql���������ܲ�����
     * ������ʽ����������VariableHandler����sql�����Ĺ�������
     * VariableHandler֧���������ã��㼶���ޣ���֧�����顢list��map��setԪ�ص�����,�ݴ��Ա�������ʽ��ʽҪ��
     * �Ӳ��Ե�����������������������ʽ��VariableHandler parserSQLStruction���������ܻ������
     */
    @org.junit.Test
    public void regexUtilvsVarialparserUtil()
    {
    	String listRepositorySql = "select *  from CIM_ETL_REPOSITORY  where 1=1 " +
		"#if($HOST_ID && !$HOST_ID.equals(\"\")) " +
		"	and HOST_ID = #[HOST_ID->bb[0]]" +
		"#end  " +
		" and PLUGIN_ID = #[PLUGIN_ID] " +
		" and CATEGORY_ID = #[CATEGORY_ID] and APP = #[APP] ";
    	
    
    	StringBuilder b = new StringBuilder();
    	b.append(listRepositorySql).append(listRepositorySql)
    	.append(listRepositorySql)
    	.append(listRepositorySql)
    	.append( listRepositorySql)
    	.append(listRepositorySql)
    	.append(listRepositorySql)
    	.append(listRepositorySql)
    	.append(listRepositorySql)
    	.append(listRepositorySql)
    	.append(listRepositorySql)
    	.append(listRepositorySql)
    	.append(listRepositorySql)
    	.append(listRepositorySql)
    	.append(listRepositorySql)
    	.append(listRepositorySql)
    	.append(listRepositorySql)
    	.append(listRepositorySql)
    	.append(listRepositorySql)
    	.append(listRepositorySql)
    	.append(listRepositorySql)
    	.append(listRepositorySql)
    	.append(listRepositorySql)
    	.append(listRepositorySql)
    	.append(listRepositorySql)
    	.append(listRepositorySql)
    	.append(listRepositorySql)
    	.append(listRepositorySql)
    	.append(listRepositorySql);
    	listRepositorySql = b.toString();
    	
    	SQLStruction a = VariableHandler.parserSQLStruction(listRepositorySql);
    	 long start = System.currentTimeMillis();
    	 String[][] sqls = VariableHandler.parser2ndSubstitution(listRepositorySql, "#\\[","\\]","?");
    	 long end = System.currentTimeMillis();
    	 System.out.println(sqls[0][0]);
    	 System.out.println("--------------------------");
    	 System.out.println(a.getSql());
    	 System.out.println(sqls[0][0].equals(a.getSql()));
    	 
    	 
    	 start = System.currentTimeMillis();
    	  sqls = VariableHandler.parser2ndSubstitution(listRepositorySql, "#\\[","\\]","?");
    	 end = System.currentTimeMillis();
    	 System.out.println("������ʽ��ʱ��" + (end - start));
    	 start = System.currentTimeMillis();
    	 a = VariableHandler.parserSQLStruction(listRepositorySql);
	   	 end = System.currentTimeMillis();
	   	System.out.println("����������ʱ��" + (end - start));
    	
    	
    	
    	 
    }
    
    
    /**
     * ������ʽֻ�ܽ����򵥵ı������޷��������ӵı�����ʽ
     * #[HOST_ID]���ָ�ʽ������ʽ�ܹ�����
     * #[HOST_ID->bb[0]]���ִ����õĸ�ʽ��������Ͳ��ܽ�����
     * VariableHandler.parserSQLStruction�������Խ����������ָ�ʽ�ı����������ܹ������ӵı�������Ϣ
     * ��Variable�б�ķ�ʽ�洢���Թ��־ò��ܶ���Щ������ֵ
     */
    @org.junit.Test
    public void varialparserUtil()
    {
    	String listRepositorySql = "select *  from CIM_ETL_REPOSITORY  where 1=1 " +
		"#if($HOST_ID && !$HOST_ID.equals(\"\")) " +
		"	and HOST_ID = #[host_id->bb[2]->bb[aa]]" +
		"#end  " +
		" and PLUGIN_ID in (#[PLUGIN_ID[0]], #[PLUGIN_ID[1]])" +
		" and CATEGORY_ID = #[CATEGORY_ID] and APP = #[APP] ";
    	String deleteAllsql = "delete from LISTBEAN where FIELDNAME in (#[bean->fss],#[bean->ftestttt],#[bean->fsdds]," +
		"#[bean->finsertOpreation],#[bean->fss556])";
    	SQLStruction b = VariableHandler.parserSQLStruction(deleteAllsql);
    	SQLStruction a = VariableHandler.parserSQLStruction(listRepositorySql);
    	Variable hostid = a.getVariables().get(0);
    	Object value = VariableHandler.evaluateVariableValue(hostid, new Host());
    	System.out.println(value);
    	 long start = System.currentTimeMillis();
    	 String[][] sqls = VariableHandler.parser2ndSubstitution(listRepositorySql, "#\\[","\\]","?");
    	 long end = System.currentTimeMillis();
    	 System.out.println("������ʽ�����Ĵ���sql:" + sqls[0][0]);
    	 System.out.println("--------------------------");
    	 System.out.println("bboss�����������ķ���������ȷsql:" + a.getSql());
    	 System.out.println("bboss����:" + a.getVariables().get(0).toString());
    	 System.out.println(sqls[0][0].equals(a.getSql()));
    	 
    	 
    	 start = System.currentTimeMillis();
    	  sqls = VariableHandler.parser2ndSubstitution(listRepositorySql, "#\\[","\\]","?");
    	 end = System.currentTimeMillis();
    	 System.out.println("������ʽ��ʱ��" + (end - start));
    	 start = System.currentTimeMillis();
    	 a = VariableHandler.parserSQLStruction(listRepositorySql);
	   	 end = System.currentTimeMillis();
	   	System.out.println("����������ʱ��" + (end - start));
    	
    	
    	
    	 
    }
    
}
