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

import java.util.ArrayList;
import java.util.List;


/**
 * <p>Title: VariableHandler.java</p> 
 * <p>Description: ������������ </p>
 * <p>bboss workgroup</p>
 * <p>Copyright (c) 2008</p>
 * @Date 2009-12-16 ����11:34:31
 * @author biaoping.yin
 * @version 1.0
 */
public class VariableHandler
{
    public static String default_regex = "\\$\\{(.+?)\\}";
    
    
    private static String buildVariableRegex(String pretoken,String endtoken)
    {
        StringBuffer ret = new StringBuffer();
//        ret.append(pretoken).append("([^").append(endtoken).append("]+)").append(endtoken);
        ret.append(pretoken).append("(.+?)").append(endtoken);
        return ret.toString();
        
    }
    /**
     * ���Ը���Ĭ�ϵ�����ʽdefault_regex = "\\$\\{([^\\}]+)\\}"��ȡ���봮�еı�������Ϊ���鷵��
     * @param inputString
     * @return
     */
    public static String[] variableParser(String inputString)
    {
        String[] vars = RegexUtil.containWithPatternMatcherInput(inputString, default_regex);
        return vars;
    }
    
    /**
     * ���Ը���ָ���ı�����ǰ�����ͺ󵼷���ȡ���봮�еı�������Ϊ���鷵��
     * @param inputString
     * @param pretoken
     * @param endtoken
     * @return
     */
    public static String[] variableParser(String inputString,String pretoken,String endtoken)
    {
//        String regex = buildVariableRegex(pretoken,endtoken);   
//        String[] vars = RegexUtil.containWithPatternMatcherInput(inputString, regex);
//        return vars;
    	return variableParser( inputString,pretoken,endtoken,RegexUtil.default_mask);
    }
    
    /**
     * ���Ը���ָ���ı�����ǰ�����ͺ󵼷���ȡ���봮�еı�������Ϊ���鷵��
     * @param inputString
     * @param pretoken
     * @param endtoken
     * @return
     */
    public static String[] variableParser(String inputString,String pretoken,String endtoken,int mask)
    {
        String regex = buildVariableRegex(pretoken,endtoken);   
        String[] vars = RegexUtil.containWithPatternMatcherInput(inputString, regex,mask);
        return vars;
    }
    
    /**
     * ���Ը���ָ��������ʽ��ȡ���봮�еı�������Ϊ���鷵��
     * @param inputString
     * @param regex
     * @return
     */
    public static String[] variableParser(String inputString,String regex)
    {
//        String regex = buildVariableRegex(pretoken,endtoken);   
        String[] vars = RegexUtil.containWithPatternMatcherInput(inputString, regex);
        return vars;
    }
    /**
     * �Ӵ�src����ȡƥ��regexģʽ�������ַ�����������substitution�滻ƥ����ģʽ���ӷ���
     * @param src
     * @param regex
     * @param substitution
     * @return String[][]��ά���飬��һά��ʾ�滻���src���ڶ�ά��ʾƥ��regex�����е��Ӵ�����
     */
    public static String[][] parser2ndSubstitution(String inputString,String regex,String substitution)
    {
        return RegexUtil.contain2ndReplaceWithPatternMatcherInput(inputString, regex, substitution);
    }
    
    /**
     * �Ӵ�src����ȡƥ��regexģʽ�������ַ�����������substitution�滻ƥ����ģʽ���ӷ���
     * @param src
     * @param regex
     * @param substitution
     * @return String[][]��ά���飬��һά��ʾ�滻���src���ڶ�ά��ʾƥ��regex�����е��Ӵ�����
     */
    public static String[][] parser2ndSubstitution(String inputString,String substitution)
    {
        return RegexUtil.contain2ndReplaceWithPatternMatcherInput(inputString,default_regex , substitution);
    }
    
    
    /**
     * �Ӵ�src����ȡƥ��pretoken ��������ǰ׺��endtoken ���������׺ָ��ģʽ�����ַ�����������substitution�滻ƥ����ģʽ���ӷ���
     * @param inputString ����Ĵ� 
     * @param pretoken ��������ǰ׺
     * @param endtoken ���������׺
     * @param substitution
     * @return String[][]��ά���飬��һά��ʾ�滻���src���ڶ�ά��ʾƥ��regex�����е��Ӵ�����
     */
    public static String[][] parser2ndSubstitution(String inputString,String pretoken,String endtoken ,String substitution)
    {
        String regex = buildVariableRegex(pretoken,endtoken);  
        return RegexUtil.contain2ndReplaceWithPatternMatcherInput(inputString, regex, substitution,RegexUtil.default_mask);
    }
    
    /**
     * �Ӵ�src����ȡƥ��pretoken ��������ǰ׺��endtoken ���������׺ָ��ģʽ�����ַ�����������substitution�滻ƥ����ģʽ���ӷ���
     * @param inputString ����Ĵ� 
     * @param pretoken ��������ǰ׺
     * @param endtoken ���������׺
     * @param substitution
     * @return String[][]��ά���飬��һά��ʾ�滻���src���ڶ�ά��ʾƥ��regex�����е��Ӵ�����
     */
    public static String[][] parser2ndSubstitution(String inputString,String pretoken,String endtoken ,String substitution,int mask)
    {
        String regex = buildVariableRegex(pretoken,endtoken);  
        return RegexUtil.contain2ndReplaceWithPatternMatcherInput(inputString, regex, substitution,mask);
    }
    
    /**
     * �滻����Ϊ�ƶ���ֵ
     * @param inputString
     * @param substitution
     * @return
     */
    public static String substitution(String inputString,String substitution)
    {
    	return SimpleStringUtil.replaceAll(inputString, default_regex, substitution);
        
    }
    
    /**
     * �滻����Ϊ�ƶ���ֵ
     * @param inputString
     * @param substitution
     * @return
     */
    public static String substitution(String inputString,String regex,String substitution)
    {
    	return SimpleStringUtil.replaceAll(inputString, regex, substitution);
    }
    
    public static class URLStruction {
		protected List<String> tokens;
		protected List<Variable> variables;
		

		public List<String> getTokens() {
			return tokens;
		}

		public void setTokens(List<String> tokens) {
			this.tokens = tokens;
		}

		public List<Variable> getVariables() {
			return variables;
		}

		public void setVariables(List<Variable> variables) {
			this.variables = variables;
		}
		
		

	}
    
    public static class SQLStruction extends URLStruction{
    	private String sql;
    	private boolean hasVars = true;
    	public SQLStruction()
    	{
    		
    	}
    	public SQLStruction(String sql)
    	{
    		this.sql = sql;
    		hasVars = false;
    	}
    	
    	public boolean hasVars()
    	{
    		return this.hasVars;
    	}
    	public String buildSQL()
    	{
    		if(sql == null)
    		{
    			if(this.variables != null && variables.size() > 0)
    			{
    				
	    			StringBuffer sql_ = new StringBuffer();
	    			int tsize = this.tokens.size();
	    			int vsize = this.variables.size();
	    			if(tsize == vsize)
	    			{
		    			for(int i = 0; i < vsize; i ++)
		    			{
		    				sql_.append(tokens.get(i)).append("?");
		    			}
	    			}
	    			else //tsize = vsize + 1;
	    			{
	    				for(int i = 0; i < vsize; i ++)
		    			{	    					
		    				sql_.append(tokens.get(i)).append("?");
		    			}
	    				sql_.append(tokens.get(vsize));
	    			}
	    			this.sql = sql_.toString();
	    			
    			}
    			
    		}
    		return sql;
    	}
		public String getSql() {
			return sql;
		}
		public void setSql(String sql) {
			this.sql = sql;
		}
    	
	}

	public static class Variable {
		private String variableName;
		private int position;
		private List<Index> indexs;
		
		private Variable parent;
		private Variable next;
		public String getVariableName() {
			return variableName;
		}

		public void setVariableName(String variableName) {
			this.variableName = variableName;
		}

		public int getPosition() {
			return position;
		}

		public void setPosition(int position) {
			this.position = position;
		}

		public List<Index> getIndexs() {
			return indexs;
		}

		public void setIndexs(List<Index> indexs) {
			this.indexs = indexs;
		}

		public Variable getParent() {
			return parent;
		}

		public void setParent(Variable parent) {
			this.parent = parent;
		}

		public Variable getNext() {
			return next;
		}

		public void setNext(Variable next) {
			this.next = next;
		}
		
		public String toString()
		{
			StringBuffer ret = new StringBuffer();
			ret.append(this.variableName);
			if(this.indexs != null && this.indexs.size() >0)
			{
				for(Index idx :indexs)
				{
					ret.append("[").append(idx.toString()).append("]");
				}
			}
			if(next != null)
			{
				ret.append("->").append(next.toString());
			}
			if(this.parent == null)
				ret.append(",position=").append(this.position);
			return ret.toString();
		}
		

		
	}
	
	public static class Index
	{
//		private Object index;
		private int int_idx = -1;
		private String string_idx;
		public Index(int index) {
			super();
			this.int_idx = index;
			
		}
		public Index(String index) {
			super();
			this.string_idx = index;
			
		}
		
		public int getInt_idx() {
			return int_idx;
		}
		public String getString_idx() {
			return string_idx;
		}
		public String toString()
		{
			if(int_idx != -1)
				return int_idx + "";
			else
				return string_idx;
		}
	}
	
	
    
    /**
     * ������������url·�������ɳ����ַ����б�ͱ������������б�
     * �����ķֽ��Ϊ#[��],���url��û�а���������ô����nullֵ
     * @param url
     * @return
     */
    public static URLStruction parserURLStruction(String url) {
		if(url == null || url.trim().length() == 0)
			return null;
		int len = url.length();
		int i = 0;
		StringBuffer token = new StringBuffer();
		StringBuffer var = new StringBuffer();
		boolean varstart = false;
		int varstartposition = -1;

		List<Variable> variables = new ArrayList<Variable>();
		int varcount = 0;
		List<String> tokens = new ArrayList<String>();
		while (i < len) {
			if (url.charAt(i) == '#') {
				if(i + 1 < len)
				{
					if( url.charAt(i + 1) == '[')
					{
				
						if (varstart) {
							token.append("#[").append(var);
							var.setLength(0);
						}
		
						varstart = true;
		
						i = i + 2;
		
						varstartposition = i;
						var.setLength(0);
						continue;
					}
					
				}
				
			}

			if (varstart) {
				if (url.charAt(i) == '&') {
					varstart = false;
					i++;
					token.append("#[").append(var);
					var.setLength(0);
					continue;
				} else if (url.charAt(i) == ']') {
					if (i == varstartposition) {
						varstart = false;
						i++;
						token.append("#[]");
						continue;
					} else {
						Variable variable = new Variable();
						variable.setPosition(varcount);
						variable.setVariableName(var.toString());
						variables.add(variable);
						tokens.add(token.toString());
						token.setLength(0);
						var.setLength(0);
						varcount++;
						varstart = false;
						i++;
					}
				} else {
					var.append(url.charAt(i));
					i ++;
				}

			} else {
				token.append(url.charAt(i));
				i ++;
			}
		}
		if (token.length() > 0) {
			if (var.length() > 0) {
				token.append("#[").append(var);
			}
			tokens.add(token.toString());
		} else {
			if (var.length() > 0) {
//				token.append("#[").append(var);
//				tokens.add(token.toString());
				token.append("#[").append(var);
				int idx = tokens.size() - 1;
				tokens.set(idx,tokens.get(idx)+token.toString());
			}

		}

		if (variables.size() == 0)
			return null;
		else {
			URLStruction itemUrlStruction = new URLStruction();
			itemUrlStruction.setTokens(tokens);
			itemUrlStruction.setVariables(variables);
			return itemUrlStruction;
		}

	}
    
    
    /**
     * ������������sql�������ɳ����ַ����б�ͱ������������б�
     * �����ķֽ��Ϊ#[��],���url��û�а���������ô����nullֵ
     * �������顢list��map��Ԫ��ȡֵ����[]��������±��key����
     * �������ò���->���ӷ�
     * @param url
     * @return
     */
    public static SQLStruction parserSQLStruction(String sql) {
		if(sql == null || sql.trim().length() == 0)
			return null;
		int len = sql.length();
		int i = 0;
		StringBuffer token = new StringBuffer();
		StringBuffer var = new StringBuffer();
//		StringBuffer index = new StringBuffer();
		
		boolean varstart = false;
		int varstartposition = -1;//��¼�����Ŀ�ʼλ��
		//����������ʼ
		boolean index_start = false;
		Variable header = null;
		Variable hh = null;
		Variable variable = null;
		List<Index> indexs = null;
		/**
		 * ������������λ�ÿ�ʼ
		 */
		boolean ref_start = false;

		List<Variable> variables = new ArrayList<Variable>();
		int varcount = 0;
		List<String> tokens = new ArrayList<String>();
		while (i < len) {
			if (sql.charAt(i) == '#') {
				if(i + 1 < len)
				{
					if( sql.charAt(i + 1) == '[')
					{
				
						if (varstart) {//fixed me
							String partvar = sql.substring(varstartposition,i);
//							token.append("#[").append(var);							
							token.append("#[").append(partvar);
							var.setLength(0);
						}
						index_start = false;
						varstart = true;
						variable = null;
						header = null;
						header = null;
						hh = null;
						indexs = null;
						/**
						 * ������������λ�ÿ�ʼ
						 */
						ref_start = false;
						i = i + 2;
		
						varstartposition = i;
						var.setLength(0);
						continue;
					}
					
				}
				
			}

			if (varstart) {
				if (sql.charAt(i) == '[') {
					
					if(!ref_start)
					{				
						if(!index_start)
						{
							header = new Variable();
							header.setPosition(varcount);
							header.setVariableName(var.toString());
//							variables.add(header);
							var.setLength(0);
							tokens.add(token.toString());
							token.setLength(0);
							varcount++;
							index_start = true;
							indexs = new ArrayList<Index>();
							header.setIndexs(indexs);
							hh = header;
						}
						else
						{
							//]
						}
						
					}
					else
					{
						if(!index_start)
						{
							variable = new Variable();
							//variable.setPosition(varcount);
							variable.setVariableName(var.toString());
							var.setLength(0);
							header.setNext(variable);
							variable.setParent(header);
							header = variable;
							index_start = true;
							indexs = new ArrayList<Index>();
							header.setIndexs(indexs);
						}
					}
					i++;
//					token.append("#[").append(var);
					var.setLength(0);
					continue;
				} else if (sql.charAt(i) == ']') {
					if (i == varstartposition) {
						varstart = false;
						i++;
						token.append("#[]");
						continue;
					} else {
						if(index_start)
						{
						
							String t = var.toString();
							try{
								int idx = Integer.parseInt(t);
								indexs.add(new Index(idx));
							}
							catch(Exception e)
							{
								indexs.add(new Index(t));
							}
							var.setLength(0);
//							index_start = false;
							if(i + 1 < len)
							{
								if(sql.charAt(i + 1) == ']')
								{
									index_start = false;
									indexs = null;
//									i ++;
								}
							}
							i++;
							
						}
						else if(ref_start)//���ý����������������a->b[0]
						{
							ref_start = false;
//							if(sql.charAt(i + 1) == '-' && sql.charAt(i + 2) == '>')
//							{
//								
//							}
//							else
							{
								varstart = false;
							}
//							tokens.add(token.toString());
//							token.setLength(0);
							varcount++;
							if(variable == null)
							{
								variable = new Variable();
								//variable.setPosition(varcount);
								variable.setVariableName(var.toString());
								var.setLength(0);
								header.setNext(variable);
								variable.setParent(header);
								header = variable;
							}
							variables.add(hh);	
							hh = null;
							i++;
						}
						else if(varstart)
						{
							if(header == null)
							{
								header = new Variable();
								header.setPosition(varcount);
								header.setVariableName(var.toString());
//								variables.add(header);								
								var.setLength(0);
								tokens.add(token.toString());
								token.setLength(0);
								varcount++;
								hh = header;
							}							
							varstart = false;
							variables.add(hh);	
							hh = null;
							i++;
						}
					}
				}
				else if (sql.charAt(i) == '-')
				{
					if(i + 1 < len )
					{
						if(sql.charAt(i+1) == '>')
						{
							if(varstart)
							{
								if(!ref_start)
								{
									ref_start = true;
									header = new Variable();
									header.setPosition(varcount);
									header.setVariableName(var.toString());
//									variables.add(header);
									var.setLength(0);
									//fixed
									tokens.add(token.toString());
									token.setLength(0);
									varcount++;
									hh = header;
								}
								else
								{
									if(variable == null)//û����Ϊ�����±굼�����ö����Ѿ���������ʼ��������
									{
										variable = new Variable();
										//variable.setPosition(varcount);
										variable.setVariableName(var.toString());
										var.setLength(0);
										indexs = null;
										header.setNext(variable);
										variable.setParent(header);
										header = variable;
									}
									else
									{
										if(var.length() > 0)
										{
											variable = new Variable();
											//variable.setPosition(varcount);
											variable.setVariableName(var.toString());
											var.setLength(0);
											indexs = null;
											header.setNext(variable);
											variable.setParent(header);
											header = variable;
										}
									}
								}
								index_start = false;
								indexs = null;
							}
							else
							{
								token.append("->");
							}
							i++;
							i++;
							continue;
						}
						else
						{
							var.append(sql.charAt(i)); 
							i ++;
						}
					}
				}
				else {
					var.append(sql.charAt(i)); 
					i ++;
				}

			} else {
				token.append(sql.charAt(i));
				i ++;
			}
		}
		/**
		 * �ݴ���
		 * ���1.����û����ȫ����(��Ҫ��תheader����)
		 * ���2.������ַ���û�б���
		 * a.��ȫû�б�������عؼ���
		 * b.�в��ֱ������壬���ǲ�ȫ
		 * 
		 */
		if (token.length() > 0) {//���2.������ַ���û�б���
			if (var.length() > 0) {// b.�в��ֱ������壬���ǲ�ȫ���ӱ�����ʼ��λ�ûָ�token
				String partvar = sql.substring(varstartposition);
				token.append("#[").append(partvar);
//				token.append("#[").append(var);
			}
			tokens.add(token.toString());
		} 
		
		else {
			if (var.length() > 0) {//���1.����û����ȫ�������ӱ�����ʼ��λ�ûָ�token
//				token.append("#[").append(var);
				String partvar = sql.substring(varstartposition);
				token.append("#[").append(partvar);
				int idx = tokens.size() - 1;
				tokens.set(idx, tokens.get(idx) + token.toString());
//				tokens.add(token.toString());
			}

		}

		if (variables.size() == 0)
		{
			return new SQLStruction(sql);
		}
		else {
			SQLStruction itemUrlStruction = new SQLStruction();
			itemUrlStruction.setTokens(tokens);
			itemUrlStruction.setVariables(variables);
			itemUrlStruction.buildSQL();
			return itemUrlStruction;
		}

	}
    
    
    
    
}
