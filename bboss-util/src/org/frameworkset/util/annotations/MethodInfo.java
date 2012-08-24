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

package org.frameworkset.util.annotations;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.frameworkset.http.MediaType;
import org.frameworkset.util.ClassUtil;
import org.frameworkset.util.ClassUtils;
import org.frameworkset.util.MethodParameter;
import org.frameworkset.util.ParameterNameDiscoverer;
import org.frameworkset.util.beans.BeansException;

import com.frameworkset.util.BeanUtils;
import com.frameworkset.util.EditorInf;
import com.frameworkset.util.SimpleStringUtil;
import com.frameworkset.util.ValueObjectUtil;

/**
 * <p>Title: MethodInfo.java</p> 
 * <p>Description: </p>
 * <p>bboss workgroup</p>
 * <p>Copyright (c) 2007</p>
 * @Date 2010-10-16 ����07:26:01
 * @author biaoping.yin
 * @version 1.0
 */
public class MethodInfo {
	private Method method;
	
	/**
	 * �Ƿ��Ƿ�ҳ����
	 */
	private boolean pagerMethod = false;
	/**
	 * �Ƿ�ָ��ҳ���С����
	 */
	private boolean definePageSize = false;
	
	private int pageSize = 10;
	
	private MethodParameter[] paramNames;
	private HandlerMapping mapping = null;
	private HandlerMapping typeLevelMapping;
	private AssertDToken assertDToken;
	private boolean responsebody = false;
	private ResponseBody responsebodyAnno ;
	private MediaType responseMediaType;
	private HttpMethod[] requestMethods;
	private String[] paths;
	private String[] pathPattern;
	private String[] pathVariables;
	private Integer[] pathVariablePositions;
	private boolean[] databind;
	private String[] baseurls ;
	/**
	 * ������עmvc����������ǿ��Ҫ����ж�̬����У�飬����ͻ�������û�и������ƻ��������Ѿ����ϣ���ôֱ�Ӿܾ�
	 * ���� 
	 * 
	 * ��ֹ��վ�����������ز��� bboss��ֹ��վ����������Ļ������£� ���ö�̬���ƺ�session���ϵķ�ʽ�����ͻ������ƣ�һ���������һ��Ψһ���� 
				����ʶ����ÿͻ������ƺͷ����session��ʶ��ϵķ�ʽ�����б�����ͻ������ƺͷ����������ȷƥ�䣬��������ʣ�������Ϊ�û�Ϊ�Ƿ��û�����ֹ�û����ʲ���ת�� 
				redirectpath������Ӧ�ĵ�ַ��Ĭ��Ϊ/login.jsp�� ���ƴ洢����ͨ������tokenstoreָ�����������֣��ڴ�洢��session�洢��Ĭ��Ϊsession�洢��������ʧЧ��ƥ�������ʧЧ�����߳�ʱʧЧ����ϵͳ�Զ����ʧЧ�����ƣ�����session��ʽ 
				�洢����ʱ������ͻ���ҳ��û������session����ô���ƻ��ǻ�洢���ڴ��С� �����������ڣ��ͻ��˵������ڷ����������д����������ʧЧ��ƥ�������ʧЧ�����߳�ʱʧЧ����ϵͳ�Զ����ʧЧ�����ƣ� 
				���ͻ��˲�û����ȷ�ύ���󣬻ᵼ�·�������ƴ����Ϊ�������ƣ���Ҫ��ʱ�����Щ �������ƣ�������ƴ洢��session�У���ô���Ƶ��������ں�session���������ڱ���һ�£��������������ƣ� 
				������ƴ洢���ڴ��У���ô���Ƶ���������ƹ�������Լ���ʱɨ���������ʱɨ��ʱ����Ϊ��tokenscaninterval����ָ������λΪ���룬Ĭ��Ϊ30���ӣ��������ʱ����tokendualtime����ָ����Ĭ��Ϊ1��Сʱ 
				����ͨ��enableToken��������ָ���Ƿ��������Ƽ����ƣ�true��⣬false����⣬Ĭ��Ϊfalse����� enableToken�Ƿ��������Ƽ����ƣ�true 
				���ã�false �����ã�Ĭ��Ϊ������
	 */
	private boolean requiredDToken = false;
	
	/**
	 * ��ſ��Ʒ��������ķ�����������
	 * �Ա���з����List<Object>���͵Ĳ��������ݰ�
	 * ParameterizedType
	 * ���������ֱ�Ӻͷ����Ĳ���λ��������Ӧ��0,1,...,n
	 * �����Ӧλ���ϵ���List<Object>���͵����ݣ���ô��ŵ�ֵ����Object�ľ�������
	 * ������null
	 */
//	private Class[] genericParameterTypes;
	
	
	
//	public MethodInfo(Method method, MethodParameter[] paramNames) {
//		super();
//		this.method = method;
////		this.editors = editors;
//		this.paramNames = paramNames;
//		mapping = method.getAnnotation(HandlerMapping.class);
//		this.requestMethods = mapping.method();
//	}
	
	public MethodInfo(Method method, HandlerMapping typeLevelMapping) {
		super();
		this.method = method;
		this.assertDToken = method.getAnnotation(AssertDToken.class);
		this.requiredDToken = assertDToken != null;
		mapping = method.getAnnotation(HandlerMapping.class);
		
		this.responsebodyAnno = method.getAnnotation(ResponseBody.class);
		if(responsebodyAnno != null)
		{
			responsebody = true;
			this.responseMediaType = convertMediaType();
		}
		if(mapping != null)
			this.requestMethods = mapping.method();
		this.typeLevelMapping = typeLevelMapping;
		this.baseurls = typeLevelMapping != null?typeLevelMapping.value():null;
		
		this.paths = mapping != null?mapping.value():null;
		this.pathPattern = buildPathPattern();
		this.parserVariables();
		this.parserInfo();
//		genericParameterTypes();
		
	}
	
	public MethodInfo(Method method, String[] baseurls) {
		super();
		this.method = method;
		mapping = method.getAnnotation(HandlerMapping.class);
		this.assertDToken = method.getAnnotation(AssertDToken.class);
		this.requiredDToken = assertDToken != null;
		this.responsebodyAnno = method.getAnnotation(ResponseBody.class);
		if(responsebodyAnno != null)
		{
			responsebody = true;
			this.responseMediaType = convertMediaType();
		}
		if(mapping != null)
			this.requestMethods = mapping.method();
		this.baseurls = baseurls;
		this.paths = mapping != null?mapping.value():null;
		this.pathPattern = buildPathPattern();
		this.parserVariables();
		this.parserInfo();
//		genericParameterTypes();
		
	}
	
	
	public boolean isResponseBody()
	{
		return this.responsebody;
	}
	
	private MediaType convertMediaType()
	{
		MediaType temp = null;
		if(this.responsebodyAnno != null)
		{
			String type = responsebodyAnno.datatype();
			String charset = this.responsebodyAnno.charset();
			if(type.equals(ValueConstants.DEFAULT_NONE))
			{
				if(!charset.equals(ValueConstants.DEFAULT_NONE))
				{
					temp = new MediaType("text","html",Charset.forName(charset));
				}
			}
			else if(type.equals("json"))
			{
				if(!charset.equals(ValueConstants.DEFAULT_NONE))
				{
					temp = new MediaType("application","json",Charset.forName(charset));
				}
				else
					temp = new MediaType("application","json",Charset.forName("UTF-8"));
			}
			else if(type.equals("jsonp"))
			{
				if(!charset.equals(ValueConstants.DEFAULT_NONE))
				{
					temp = new MediaType("application","jsonp",Charset.forName(charset));
				}
				else
					temp = new MediaType("application","jsonp",Charset.forName("UTF-8"));
			}
			
			else if(type.equals("xml"))
			{
				if(!charset.equals(ValueConstants.DEFAULT_NONE))
				{
					temp = new MediaType("application","xml",Charset.forName(charset));
				}
				else
					temp = MediaType.APPLICATION_XML;
			}
			else if(type.equals("javascript"))
			{
				if(!charset.equals(ValueConstants.DEFAULT_NONE))
				{
					temp = new MediaType("application","javascript",Charset.forName(charset));
				}
				else
					temp = MediaType.JAVASCRIPT_HTML;
			}
			//javascript
			
			
			
			
		}
		return temp;
	}
	public static String getRealPath(String contextPath, String path) {
		if (contextPath == null || contextPath.equals("")) {
//			System.out.println("SimpleStringUtil.getRealPath() contextPath:"
//					+ contextPath);
			return path == null?"":path;
		}
		if (path == null || path.equals("")) {
			
			return contextPath;
		}
		
		contextPath = contextPath.replace('\\', '/');
		path = path.replace('\\', '/');
		if (path.startsWith("/") ) {
			
			if (!contextPath.endsWith("/"))
				return contextPath + path;
			else {
				return contextPath.substring(0,contextPath.length() - 1) + path;
			}

		} else {
			if (!contextPath.endsWith("/"))
				return contextPath + "/" + path;
			else {
				return contextPath + path;
			}
		}

	}
	private void parserVariables()
	{
		if( paths == null || paths.length == 0)
			return ;
		String baseurl = this.baseurls != null && this.baseurls.length > 0?baseurls[0]:"";
		String path = getRealPath(baseurl, paths[0]);
		int len = path.length();
		int index = path.indexOf('/');
		List<Integer> poses = new ArrayList<Integer>();
		List<String> variables = new ArrayList<String>();
		int count = -1;
		while(index != -1)
		{		
			if(index == len - 1)
				break;
			count ++;
			if(path.charAt(index+1) == '{')
			{
				poses.add(count);
				int endps = path.indexOf("}",index + 1);
				variables.add(path.substring(index + 1 + 1, endps));
			}
			index = path.indexOf("/", index + 1);			
		}
		
		if(poses.size() > 0)
		{
			this.pathVariables = SimpleStringUtil.toStringArray(variables);
			this.pathVariablePositions = SimpleStringUtil.toIntArray(poses);
		}
		
			
		
		
	}
	private String[] buildPathPattern()
	{
		if(paths == null || paths.length == 0)
			return null;
		String[] pathPatterns = null;
		if(this.baseurls == null || this.baseurls.length == 0)
		{
			pathPatterns = new String[paths.length];
			int k = 0;
			for(String mappedPath:paths)
			{
				StringBuffer pathUrl = new StringBuffer();
				String[] tmp = mappedPath.split("/");
//				pathUrl.append(tmp[0]);
				for(int i = 1; i < tmp.length; i ++ )
				{
					if(tmp[i].startsWith("{"))
						pathUrl.append("/*");
					else
						pathUrl.append("/").append(tmp[i]);
				}
				pathPatterns[k] = pathUrl.toString();
				k ++;
			}
		}
		else
		{
			pathPatterns = new String[baseurls.length * paths.length];
			int k = 0;
			for(String baseurl:baseurls)
			{				
				for(String mappedPath:paths)
				{
					StringBuffer pathUrl = new StringBuffer();
					pathUrl.append(baseurl);
					String[] tmp = mappedPath.split("/");
//					pathUrl.append(tmp[0]);
					for(int i = 1; i < tmp.length; i ++ )
					{
						if(tmp[i].startsWith("{"))
							pathUrl.append("/*");
						else
							pathUrl.append("/").append(tmp[i]);
					}
					pathPatterns[k] = pathUrl.toString();
					k ++;
				}
			}
		}
		return pathPatterns;
			
//		String baseurl;
//		String mappedPath = paths[0];
//		StringBuffer pathUrl = new StringBuffer();
//		pathUrl.append(baseurl);
//		for(int i = 0; i < mappedPath.length(); i ++ )
//		{
//			if(mappedPath.charAt(i) == '/')
//				pathUrl.append("/*");
//			
//		}
//		
//		return new String[] {pathUrl.toString() };
	}
	
//	public boolean restful()
//	{
//		return this.typeLevelMapping.restful();
//	}
	
	
	public MethodInfo(Method method) {
		super();
		this.method = method;
		this.assertDToken = method.getAnnotation(AssertDToken.class);
		this.requiredDToken = assertDToken != null;
		this.responsebodyAnno = method.getAnnotation(ResponseBody.class);
		if(responsebodyAnno != null)
		{
			responsebody = true;
			this.responseMediaType = convertMediaType();
		}
		this.parserInfo();
//		genericParameterTypes();
	}
	
	public String[] getPaths()
	{
		return mapping == null?null:mapping.value();
	}
	private MethodParameter buildMutilMethodParamAnnotations(Annotation[] annotations,int parampostion,String methodparamname,Class paramType)
	{
		MethodParameter ret = new MethodParameter(method,parampostion);
		List<MethodParameter> mutilMethodParamAnnotations = new ArrayList<MethodParameter>();
		MethodParameter paramAnno = null;
		Annotation annotation = null;
		boolean ismapkey = false;
		for(int k = 0; k < annotations.length; k ++)
		{
			 annotation = annotations[k];
			 if(annotation instanceof RequestBody)
			{
				 paramAnno = new MethodParameter(method,parampostion);
				RequestBody param = (RequestBody)annotation;
//					if(param.editor() != null && !param.editor().equals(""))
//						paramNames[i].setEditor((EditorInf)BeanUtils.instantiateClass(param.editor()));
//					paramNames[i].setParameterName(param.value());
				paramAnno.setDataBindScope(Scope.REQUEST_BODY);
				mutilMethodParamAnnotations.add(paramAnno);
				continue;
				
			}
			else if(annotation instanceof DataBind)
			{
				 paramAnno  = new MethodParameter(method,parampostion);
				 paramAnno.setDataBeanBind(true);
				 mutilMethodParamAnnotations.add(paramAnno);
					continue;
			}
			else if(annotation instanceof RequestParam)
			{
				paramAnno  = new MethodParameter(method,parampostion);
				RequestParam param = (RequestParam)annotation;
				paramAnno.setRequestParam(param);
				if(param.editor() != null && !param.editor().equals(""))
					paramAnno.setEditor((EditorInf)BeanUtils.instantiateClass(param.editor()));
				if(!param.name().equals(""))
					paramAnno.setParameterName(param.name());
				else
					paramAnno.setParameterName(methodparamname);
				paramAnno.setDataBindScope(Scope.REQUEST_PARAM);
				paramAnno.setRequired(param.required());
				String aa = param.defaultvalue();
				if(!aa.equals(ValueConstants.DEFAULT_NONE))
					paramAnno.setDefaultValue(aa);
				mutilMethodParamAnnotations.add(paramAnno);
				continue;
				
			}
			else if(annotation instanceof MapKey)
			{
				ret  = new MethodParameter(method,parampostion);
				MapKey param = (MapKey)annotation;
				ret.setMapKey(param);
				ret.setDataBindScope(Scope.MAP_PARAM);	
				ismapkey = true;
//				mutilMethodParamAnnotations.add(paramAnno);
				break;
			}
			else if(annotation instanceof PagerParam)//��ҳ������Ϣ
			{
				this.setPagerMethod(true);
				paramAnno  = new MethodParameter(method,parampostion);
				PagerParam param = (PagerParam)annotation;
				if(param.editor() != null && !param.editor().equals(""))
					paramAnno.setEditor((EditorInf)BeanUtils.instantiateClass(param.editor()));
				if(param.name().equals(PagerParam.PAGE_SIZE))
				{
					this.setDefinePageSize(true);
					String id = param.id();
					if(id.equals(ValueConstants.DEFAULT_NONE))
						id = null;	
					paramAnno.setParamNamePrefix(id);
					paramAnno.setParameterName(param.name());
					
				}
				else
				{
					String id = param.id();
					if(param.id().equals(ValueConstants.DEFAULT_NONE))
						id = PagerParam.DEFAULT_ID;	
					if(param.name().startsWith(id))
						paramAnno.setParameterName(param.name());
					else
					{
						paramAnno.setParameterName(id + "." + param.name());
					}					
				}
				paramAnno.setDataBindScope(Scope.PAGER_PARAM);
				paramAnno.setRequired(param.required());
				String aa = param.defaultvalue();
				if(!aa.equals(ValueConstants.DEFAULT_NONE))
					paramAnno.setDefaultValue(aa);
				mutilMethodParamAnnotations.add(paramAnno);
				continue;
				
			} 
			else if(annotation instanceof PathVariable)
			{
				paramAnno  = new MethodParameter(method,parampostion);
				PathVariable param = (PathVariable)annotation;
				paramAnno.setPathVariable(param);
				if(param.editor() != null && !param.editor().equals(""))
					paramAnno.setEditor((EditorInf)BeanUtils.instantiateClass(param.editor()));
//				paramAnno.setParameterName(param.value());
				if(!param.value().equals(""))
					paramAnno.setParameterName(param.value());
				else
					paramAnno.setParameterName(methodparamname);
				paramAnno.setDataBindScope(Scope.PATHVARIABLE);
				String aa = param.defaultvalue();
				if(!aa.equals(ValueConstants.DEFAULT_NONE))
					paramAnno.setDefaultValue(aa);
				mutilMethodParamAnnotations.add(paramAnno);
				continue;
				
			}
			else if(annotation instanceof CookieValue)
			{
				paramAnno  = new MethodParameter(method,parampostion);
				CookieValue param = (CookieValue)annotation;
				paramAnno.setCookieValue(param);
				if(param.editor() != null && !param.editor().equals(""))
					paramAnno.setEditor((EditorInf)BeanUtils.instantiateClass(param.editor()));
				if(!param.name().equals(""))
					paramAnno.setParameterName(param.name());
				else
					paramAnno.setParameterName(methodparamname);
//				paramAnno.setParameterName(param.name());
				paramAnno.setDataBindScope(Scope.COOKIE);
				
				String aa = param.defaultvalue();
				if(!aa.equals(ValueConstants.DEFAULT_NONE))
					paramAnno.setDefaultValue(aa);
				mutilMethodParamAnnotations.add(paramAnno);
				continue;
			}
			else if(annotation instanceof RequestHeader)
			{
				paramAnno  = new MethodParameter(method,parampostion);
				RequestHeader param = (RequestHeader)annotation;
				paramAnno.setRequestHeader(param);
				if(param.editor() != null && !param.editor().equals(""))
					paramAnno.setEditor((EditorInf)BeanUtils.instantiateClass(param.editor()));
				if(!param.name().equals(""))
					paramAnno.setParameterName(param.name());
				else
					paramAnno.setParameterName(methodparamname);
//				paramAnno.setParameterName(param.name());
				paramAnno.setDataBindScope(Scope.REQUEST_HEADER);
				String aa = param.defaultvalue();
				if(!aa.equals(ValueConstants.DEFAULT_NONE))
					paramAnno.setDefaultValue(aa);
				mutilMethodParamAnnotations.add(paramAnno);
				continue;
				
			}
			
			else if(annotation instanceof Attribute)
			{
				paramAnno  = new MethodParameter(method,parampostion);
				Attribute param = (Attribute)annotation;
				paramAnno.setAttribute(param);
				paramAnno.setRequired(param.required());
				if(param.editor() != null && !param.editor().equals(""))
					paramAnno.setEditor((EditorInf)BeanUtils.instantiateClass(param.editor()));
				if(!param.name().equals(""))
					paramAnno.setParameterName(param.name());
				else
					paramAnno.setParameterName(methodparamname);
//				paramAnno.setParameterName(param.name());
				if(param.scope() == AttributeScope.PAGECONTEXT_APPLICATION_SCOPE)
					paramAnno.setDataBindScope(Scope.PAGECONTEXT_APPLICATION_SCOPE);
				else if(param.scope() == AttributeScope.PAGECONTEXT_PAGE_SCOPE)
					paramAnno.setDataBindScope(Scope.PAGECONTEXT_PAGE_SCOPE);
				else if(param.scope() == AttributeScope.PAGECONTEXT_REQUEST_SCOPE)
					paramAnno.setDataBindScope(Scope.PAGECONTEXT_REQUEST_SCOPE);
				else if(param.scope() == AttributeScope.PAGECONTEXT_SESSION_SCOPE)
					paramAnno.setDataBindScope(Scope.PAGECONTEXT_SESSION_SCOPE);
				else if(param.scope() == AttributeScope.REQUEST_ATTRIBUTE)
					paramAnno.setDataBindScope(Scope.REQUEST_ATTRIBUTE);
				else if(param.scope() == AttributeScope.SESSION_ATTRIBUTE)
					paramAnno.setDataBindScope(Scope.SESSION_ATTRIBUTE);
				else if(param.scope() == AttributeScope.MODEL_ATTRIBUTE)
					paramAnno.setDataBindScope(Scope.MODEL_ATTRIBUTE);
				String aa = param.defaultvalue();
				if(!aa.equals(ValueConstants.DEFAULT_NONE))
					paramAnno.setDefaultValue(aa);
				mutilMethodParamAnnotations.add(paramAnno);
				continue;
			}
			
		}
		
		if(mutilMethodParamAnnotations.size() == 0)
		{
			if(!ismapkey)
			{
				boolean isprimary = ValueObjectUtil.isPrimaryType(paramType);
				if(isprimary )
				{
					MethodParameter temp = new MethodParameter(method,parampostion);
					temp.setParameterName(methodparamname);
					temp.setPrimaryType(isprimary);
					return temp;
				}
				else
				{
					return null;
				}
				
			}
			return ret;
		}
		ret.setMultiAnnotationParams(mutilMethodParamAnnotations);
		return ret;
	}
//	private void genericParameterTypes()
//	{
////		Type[] types = method.getGenericParameterTypes();
////		
////		if(types == null || types.length == 0)
////		{
////			return;
////		}
////		genericParameterTypes = new Class[types.length];
////		for(int i = 0;  i < types.length; i ++)
////		{
////			Type type = types[i]; 
////			if(type instanceof ParameterizedType)
////			{
////				Class listClass = (Class)type;
////				if(List.class.isAssignableFrom(listClass) || Set.class.isAssignableFrom(listClass))
////				{
////					Type zzz = ((ParameterizedType)type).getActualTypeArguments()[0];
////					this.genericParameterTypes[i] = (Class)zzz;
////				}
////			}
////		}
//		genericParameterTypes = ClassUtils.genericParameterTypes(method);
//		
//		
//		
//	}
	
	private Map<Integer,Object> genericParameterTypes = new HashMap<Integer,Object>();
	public Class getGenericParameterType(int i)
	{
//		if(this.genericParameterTypes != null )
//		{
//			return this.genericParameterTypes[i];
//		}
//		else
//			return null;
		return ClassUtils.genericParameterType(method, i);
	}
	
	public Class[] getGenericParameterTypes(int i)
	{
//		if(this.genericParameterTypes != null )
//		{
//			return this.genericParameterTypes[i];
//		}
//		else
//			return null;
		return ClassUtils.genericParameterTypes(method, i);
	}
	
	private void parserInfo()
	{
		Annotation[][] annotations = method.getParameterAnnotations();
		Class[] paramTypes = method.getParameterTypes();
		ParameterNameDiscoverer parameterNameDiscoverer = ClassUtil.getParameterNameDiscoverer();
		String[] temp_paramNames = parameterNameDiscoverer.getParameterNames(getMethod());
		/**
		 * �������û��ָ���κ�ע�⣬����û��ͨ��asm��ȡ��������������
		 * ��ֱ�ӷ���
		 */
		if((temp_paramNames == null || temp_paramNames.length == 0 ) 
				&& (annotations == null || annotations.length ==0))
			return;
//		editors = new EditorInf[annotations.length];
		paramNames = new MethodParameter[annotations.length];
		databind = new boolean[annotations.length];
		for(int i = 0; i < annotations.length; i ++)
		{
			String methodparamName = temp_paramNames == null || temp_paramNames.length == 0?"": temp_paramNames[i];
			if(annotations[i].length == 0)
			{
//				editors[i] = null;
				boolean isprimary = ValueObjectUtil.isPrimaryType(paramTypes[i]);
				if(isprimary && !methodparamName.equals(""))
				{
					MethodParameter temp = new MethodParameter(method,i);
					temp.setParameterName(methodparamName);
					temp.setPrimaryType(isprimary);
					paramNames[i] = temp;
				}
				else
				{
					paramNames[i] = null;
				}
			}
			else
			{
				paramNames[i] = buildMutilMethodParamAnnotations(annotations[i],i,methodparamName,paramTypes[i]);
			}
		}
		
	}

	public Method getMethod() {
		return method;
	}

	public MethodParameter[] getParamNames() {
		return paramNames;
	}
	
	public EditorInf getEditor(int index) {
		if(paramNames != null)
		{
			 if(this.paramNames.length < index + 1 )
				 throw new BeansException("�Ƿ���param index ["+index+"]��paramNames length is " + paramNames.length);
			return paramNames[index].getEditor();
		}
		return null;
	}
	public String getParamName(int index) {
		if(paramNames != null )
		{
			if(this.paramNames.length < index + 1 )
				 throw new BeansException("�Ƿ���paramNames index ["+index+"]��paramNames length is " + paramNames.length);
			return paramNames[index].getRequestParameterName();
		}
		return null;
		
	}
	
	public MethodParameter getMethodParameter(int index)
	{
		return paramNames[index];
	}
	public boolean isDataBind(int index)
	{
		if(databind != null)
		{
			 if(this.databind.length < index + 1 )
				 throw new BeansException("�Ƿ���databind index ["+index+"]��databind length is " + databind.length);
		}
		return this.databind[index];
	}
	
	
	public HttpMethod[] getRequestMethods() {
		return requestMethods;
	}

	public HandlerMapping getMethodMapping() {
		return mapping;
	}

	public HandlerMapping getTypeLevelMapping() {
		return typeLevelMapping;
	}

	public String[] getPathPattern() {
		return pathPattern;
	}

	public String[] getPathVariables() {
		return pathVariables;
	}

	public Integer[] getPathVariablePositions() {
		return pathVariablePositions;
	}

	/**
	 * @return the pagerMethod
	 */
	public boolean isPagerMethod() {
		return pagerMethod;
	}

	/**
	 * @param pagerMethod the pagerMethod to set
	 */
	public void setPagerMethod(boolean pagerMethod) {
		this.pagerMethod = pagerMethod;
	}

	/**
	 * @return the definePageSize
	 */
	public boolean isDefinePageSize() {
		return definePageSize;
	}

	/**
	 * @param definePageSize the definePageSize to set
	 */
	public void setDefinePageSize(boolean definePageSize) {
		this.definePageSize = definePageSize;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	
	public ResponseBody getResponsebodyAnno()
	{
	
		return responsebodyAnno;
	}

	
	public void setResponsebodyAnno(ResponseBody responsebodyAnno)
	{
	
		this.responsebodyAnno = responsebodyAnno;
	}

	
	public MediaType getResponseMediaType()
	{
	
		return responseMediaType;
	}

	
	public void setResponseMediaType(MediaType responseMediaType)
	{
	
		this.responseMediaType = responseMediaType;
	}

	public boolean isRequiredDToken() {
		return requiredDToken;
	}

	public AssertDToken getAssertDToken() {
		return assertDToken;
	}

}
