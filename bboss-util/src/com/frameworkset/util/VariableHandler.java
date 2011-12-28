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
		private List<String> tokens;
		private List<Variable> variables;

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

	public static class Variable {
		private String variableName;

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

		private int position;
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
				token.append("#[").append(var);
				tokens.add(token.toString());
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
    
    public static void main(String[] args)
    {
//        String pretoken = "\\{";
//        String endtoken = "\\}";
//        String url = "${context}/${context0}/${context0}creatorepp";
//        String regex = buildVariableRegex(pretoken,endtoken);
//        System.out.print(regex);
//        String[] vars = variableParser(url,pretoken,endtoken);
//        System.out.println(vars);
//        vars = variableParser(url,regex);
//        
//        System.out.println(vars);
        
        
        String url = "http://localhost:80/detail.html?user=#[account]&password=#[password]love";
		
		 url =
		 "http://localhost:80/detail.html?user=#[account&password=password]&love=#[account]";
		 url =
			 "http://localhost:80/detail.html?user=#[account&password=password]&love=#[account";
//		 
		 url =
			 "http://localhost:80/detail.html?user=#[account&password=#[password&love=#[account";
//		 url =
//			 "http://localhost:80/detail.html";
//		 
//		 url =
//			 "http://localhost:80/#[]detail.html";
//		 url =
//			 "#[account";
		 System.out.println("url:"+url);
		// Item item = new Item();
		 URLStruction a = parserURLStruction(url);
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
    
    
}
