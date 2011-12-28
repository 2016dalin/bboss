package com.frameworkset.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.PatternMatcherInput;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.apache.oro.text.regex.Perl5Substitution;
import org.apache.oro.text.regex.Util;
/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author biaoping.yin
 * @version 1.0
 */
public class RegexUtil {
    private static Logger log = Logger.getLogger(RegexUtil.class);
    
    public static final int default_mask = Perl5Compiler.SINGLELINE_MASK | Perl5Compiler.DEFAULT_MASK; 
    public static String[] parser(String src, String regex) {
        String patternStr = regex;

        /**
         * ����������ʽpatternStr�����øñ��ʽ�봫���src�ַ�������ģʽƥ��,
         * ���ƥ����ȷ�����ƥ���������ȡ�����϶���õ�6���֣���ŵ������в�����
         * ������
         */

        PatternCompiler compiler = new Perl5Compiler();
        Pattern pattern = null;
        try {
            pattern = compiler.compile(patternStr,
            		default_mask);
        } catch (MalformedPatternException e) {
            e.printStackTrace();

            return null;
        }
        PatternMatcher matcher = new Perl5Matcher();
        MatchResult result = null;
        String[] tokens = null;
        boolean match = matcher.matches(src, pattern);

        if (match) {
            result = matcher.getMatch();
            int size = result.groups() - 1;
            tokens = new String[size];
            for (int i = 0; i < size; i++) {
                tokens[i] = result.group(i + 1);
            }
        }
        return tokens;
    }
    
    /**
     * ��ƥ��regex��src�ַ�������ȡ�������ַ���
     * @param src
     * @param regex
     * @return
     */
    public static String[] matchWithPatternMatcherInput(String src, String regex) {
        String patternStr = regex;

        /**
         * ����������ʽpatternStr�����øñ��ʽ�봫���src�ַ�������ģʽƥ��,
         * ���ƥ����ȷ�����ƥ���������ȡ�����϶���õ�6���֣���ŵ������в�����
         * ������
         */

        PatternCompiler compiler = new Perl5Compiler();
        Pattern pattern = null;
        try {
            pattern = compiler.compile(patternStr,
            		default_mask);
        } catch (MalformedPatternException e) {
            e.printStackTrace();

            return null;
        }
        PatternMatcher matcher = new Perl5Matcher();
        MatchResult result = null;
        String[] tokens = null;
        List sets = new ArrayList(); 
        PatternMatcherInput input = new PatternMatcherInput(src);
        

        while (matcher.matches(input, pattern)) {
            result = matcher.getMatch();
            int size = result.groups();
            
            for (int i = 1; i < size; i++) {
            	sets.add(result.group(i));
            }
        }
        tokens = new String[sets.size()];
        sets.toArray(tokens);
        return tokens;
    }
    
    public static String[] match(String src, String regex) {
        String patternStr = regex;

        /**
         * ����������ʽpatternStr�����øñ��ʽ�봫���src�ַ�������ģʽƥ��,
         * ���ƥ����ȷ�����ƥ���������ȡ�����϶���õ�6���֣���ŵ������в�����
         * ������
         */

        PatternCompiler compiler = new Perl5Compiler();
        Pattern pattern = null;
        try {
            pattern = compiler.compile(patternStr,
            		default_mask);
        } catch (MalformedPatternException e) {
            e.printStackTrace();

            return null;
        }
        PatternMatcher matcher = new Perl5Matcher();
        MatchResult result = null;
        String[] tokens = null;

        if(matcher.matches(src, pattern)) {
            result = matcher.getMatch();
            int size = result.groups() - 1;
            tokens = new String[size];
            for (int i = 0; i < size; i++) {
                tokens[i] = result.group(i + 1);
            }
        }
        return tokens;
    }

    /**
     * ��src����ȡ����ģʽregex�����ַ���
     * @param src
     * @param regex
     * @return
     */
    public static String[] containWithPatternMatcherInput(String src, String regex) {
         String patternStr = regex;

        /**
         * ����������ʽpatternStr�����øñ��ʽ�봫���src�ַ�������ģʽƥ��,
         * ���ƥ����ȷ�����ƥ���������ȡ�����϶���õ�6���֣���ŵ������в�����
         * ������
         */

        PatternCompiler compiler = new Perl5Compiler();
        Pattern pattern = null;
        try {
            pattern = compiler.compile(patternStr,
            		default_mask);
        } catch (MalformedPatternException e) {
            e.printStackTrace();

            return null;
        }
        PatternMatcher matcher = new Perl5Matcher();
        MatchResult result = null;
        String[] tokens = null;
        List<String> sets = new ArrayList<String>(); 
        PatternMatcherInput input = new PatternMatcherInput(src);
        

        while (matcher.contains(input, pattern)) {
            result = matcher.getMatch();
            
            
            int size = result.groups();
            
            for (int i = 1; i < size; i++) {
            	sets.add(result.group(i));
            }
        }
        tokens = new String[sets.size()];
        sets.toArray(tokens);
        return tokens;
    }
    
    /**
     * ��src����ȡ����ģʽregex�����ַ���
     * @param src
     * @param regex
     * @return
     */
    public static String[] containWithPatternMatcherInput(String src, String regex,int mask) {
         String patternStr = regex;

        /**
         * ����������ʽpatternStr�����øñ��ʽ�봫���src�ַ�������ģʽƥ��,
         * ���ƥ����ȷ�����ƥ���������ȡ�����϶���õ�6���֣���ŵ������в�����
         * ������
         */

        PatternCompiler compiler = new Perl5Compiler();
        Pattern pattern = null;
        try {
            pattern = compiler.compile(patternStr,
            		mask);
        } catch (MalformedPatternException e) {
            e.printStackTrace();

            return null;
        }
        PatternMatcher matcher = new Perl5Matcher();
        MatchResult result = null;
        String[] tokens = null;
        List<String> sets = new ArrayList<String>(); 
        PatternMatcherInput input = new PatternMatcherInput(src);
        

        while (matcher.contains(input, pattern)) {
            result = matcher.getMatch();
            
            
            int size = result.groups();
            
            for (int i = 1; i < size; i++) {
            	sets.add(result.group(i));
            }
        }
        tokens = new String[sets.size()];
        sets.toArray(tokens);
        return tokens;
    }
    
    /**
     * �Ӵ�src����ȡƥ��regexģʽ�������ַ�����������substitution�滻ƥ����ģʽ���ӷ���
     * @param src
     * @param regex
     * @param substitution
     * @return String[][]��ά���飬��һά��ʾ�滻���src���ڶ�ά��ʾƥ��regex�����е��Ӵ�����
     */
    public static String[][] contain2ndReplaceWithPatternMatcherInput(String src, String regex,String substitution) {
        String patternStr = regex;

       /**
        * ����������ʽpatternStr�����øñ��ʽ�봫���src�ַ�������ģʽƥ��,
        * ���ƥ����ȷ�����ƥ���������ȡ�����϶���õ�6���֣���ŵ������в�����
        * ������
        */

       PatternCompiler compiler = new Perl5Compiler();
       Pattern pattern = null;
       try {
           pattern = compiler.compile(patternStr,
        		   default_mask);
       } catch (MalformedPatternException e) {
           e.printStackTrace();

           return null;
       }
       PatternMatcher matcher = new Perl5Matcher();
       MatchResult result = null;
       String[] tokens = null;
       List<String> sets = new ArrayList<String>(); 
       PatternMatcherInput input = new PatternMatcherInput(src);
       

       while (matcher.contains(input, pattern)) {
           result = matcher.getMatch();
           
           
           int size = result.groups();
           
           for (int i = 1; i < size; i++) {
               sets.add(result.group(i));
           }
       }
       String[][] retvalue = null;
       String newsrc = src;
       if(sets.size() > 0)
       {
           newsrc = Util.substitute(matcher, pattern, new Perl5Substitution(substitution),
                   src, Util.SUBSTITUTE_ALL);
       }
       
       tokens = new String[sets.size()];       
       sets.toArray(tokens);
       retvalue = new String[][] {{newsrc},tokens};
       return retvalue;
   }
    
    /**
     * �Ӵ�src����ȡƥ��regexģʽ�������ַ�����������substitution�滻ƥ����ģʽ���ӷ���
     * @param src
     * @param regex
     * @param substitution
     * @return String[][]��ά���飬��һά��ʾ�滻���src���ڶ�ά��ʾƥ��regex�����е��Ӵ�����
     */
    public static String[][] contain2ndReplaceWithPatternMatcherInput(String src, String regex,String substitution,int MASK) {
        String patternStr = regex;

       /**
        * ����������ʽpatternStr�����øñ��ʽ�봫���src�ַ�������ģʽƥ��,
        * ���ƥ����ȷ�����ƥ���������ȡ�����϶���õ�6���֣���ŵ������в�����
        * ������
        */

       PatternCompiler compiler = new Perl5Compiler();
       Pattern pattern = null;
       try {
           pattern = compiler.compile(patternStr,
        		   MASK);
       } catch (MalformedPatternException e) {
           e.printStackTrace();

           return null;
       }
       PatternMatcher matcher = new Perl5Matcher();
       MatchResult result = null;
       String[] tokens = null;
       List<String> sets = new ArrayList<String>(); 
       PatternMatcherInput input = new PatternMatcherInput(src);
       

       while (matcher.contains(input, pattern)) {
           result = matcher.getMatch();
           
           
           int size = result.groups();
           
           for (int i = 1; i < size; i++) {
               sets.add(result.group(i));
           }
       }
       String[][] retvalue = null;
       String newsrc = src;
       if(sets.size() > 0)
       {
           newsrc = Util.substitute(matcher, pattern, new Perl5Substitution(substitution),
                   src, Util.SUBSTITUTE_ALL);
       }
       
       tokens = new String[sets.size()];       
       sets.toArray(tokens);
       retvalue = new String[][] {{newsrc},tokens};
       return retvalue;
   }
    
    /**
     * �Ӱ���
     * @param src
     * @param regex
     * @return
     */
    public static String[] contain(String src, String regex) {
        String patternStr = regex;

        /**
         * ����������ʽpatternStr�����øñ��ʽ�봫���src�ַ�������ģʽƥ��,
         * ���ƥ����ȷ�����ƥ���������ȡ�����϶���õ�6���֣���ŵ������в�����
         * ������
         */

        PatternCompiler compiler = new Perl5Compiler();
        Pattern pattern = null;
        try {
            pattern = compiler.compile(patternStr,
            		default_mask);
        } catch (MalformedPatternException e) {
            e.printStackTrace();

            return null;
        }
        PatternMatcher matcher = new Perl5Matcher();
        MatchResult result = null;
        String[] tokens = null;
        
        

        if (matcher.contains(src, pattern)) {
            result = matcher.getMatch();
            int size = result.groups() - 1;
            tokens = new String[size];
            for (int i = 0; i < size; i++) {
                tokens[i] = result.group(i + 1);
            }
        }
        return tokens;
    }
    
    
    public static boolean isContain(String src, String regex) {
    	if(src == null )
    		return false;
        String patternStr = regex;

        /**
         * ����������ʽpatternStr�����øñ��ʽ�봫���src�ַ�������ģʽƥ��,
         * ���ƥ����ȷ�����ƥ���������ȡ�����϶���õ�6���֣���ŵ������в�����
         * ������
         */

        PatternCompiler compiler = new Perl5Compiler();
        Pattern pattern = null;
        try {
            pattern = compiler.compile(patternStr,
            		default_mask);
        } catch (MalformedPatternException e) {
            e.printStackTrace();

            return false;
        }
        PatternMatcher matcher = new Perl5Matcher();
        return matcher.contains(src, pattern);
       
    }
    
    

    public RegexUtil() {
    }

    /**
     * ���src��������ʽregexƥ����
     * @param src String
     * @param regex String
     * @return boolean
     */
    public static boolean isMatch(String src, String regex) {
    	if(src == null  )
    		return false;
        boolean flag = false;
        String patternStr = regex;

        /**
         * ����������ʽpatternStr�����øñ��ʽ�봫���src�ַ�������ģʽƥ��,
         * ���ƥ����ȷ�����ƥ���������ȡ�����϶���õ�6���֣���ŵ������в�����
         * ������
         */

        PatternCompiler compiler = new Perl5Compiler();
        Pattern pattern = null;
        try {
            pattern = compiler.compile(patternStr,
            		default_mask);
        } catch (MalformedPatternException e) {
            e.printStackTrace();
            return false;
        }
        PatternMatcher matcher = new Perl5Matcher();
//        log.debug("src:" + src);;
//        log.debug("pattern:" + pattern);
//        log.debug("regex:" + regex);
        flag = matcher.matches(src, pattern);
        return flag;
    }
    
    /**
     * ��content�з���ģʽpattern���ַ����滻Ϊnewstr�ַ���
     * ��δʵ��
     * @param content
     * @param pattern
     * @param newstr
     */
    public static void replace(String content,String pattern,String newstr)
    {
    	
    }
}
