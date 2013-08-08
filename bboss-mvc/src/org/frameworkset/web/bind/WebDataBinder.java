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
package org.frameworkset.web.bind;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.frameworkset.http.converter.HttpMessageConverter;
import org.frameworkset.spi.support.validate.BindingResult;
import org.frameworkset.util.ClassUtil;
import org.frameworkset.util.ClassUtil.ClassInfo;
import org.frameworkset.util.ClassUtil.PropertieDescription;
import org.frameworkset.util.annotations.MethodData;
import org.frameworkset.web.multipart.MultipartFile;
import org.frameworkset.web.multipart.MultipartHttpServletRequest;
import org.frameworkset.web.servlet.ModelMap;
import org.frameworkset.web.servlet.handler.HandlerUtils;

import com.frameworkset.util.EditorInf;
import com.frameworkset.util.ValueObjectUtil;

/**
 * <p>Title: WebDataBinder.java</p> 
 * <p>Description: </p>
 * <p>bboss workgroup</p>
 * <p>Copyright (c) 2008</p>
 * @Date 2010-10-12
 * @author biaoping.yin
 * @version 1.0
 */
public class WebDataBinder  {//extends DataBinder {
	private Object target;

	private  String objectName;
	private Class objectType;
	
	private BindingResult bindingResult;

	/**
	 * Default prefix that field marker parameters start with, followed by the field
	 * name: e.g. "_subscribeToNewsletter" for a field "subscribeToNewsletter".
	 * <p>Such a marker parameter indicates that the field was visible, that is,
	 * existed in the form that caused the submission. If no corresponding field
	 * value parameter was found, the field will be reset. The value of the field
	 * marker parameter does not matter in this case; an arbitrary value can be used.
	 * This is particularly useful for HTML checkboxes and select options.
	 * @see #setFieldMarkerPrefix
	 */
	public static final String DEFAULT_FIELD_MARKER_PREFIX = "_";


	private String fieldMarkerPrefix = DEFAULT_FIELD_MARKER_PREFIX;

	private boolean bindEmptyMultipartFiles = true;
	
	private Collection targetContainer;
	private Map targetMapContainer;
	private String mapKeyName;
	private Class mapKeyType;
	


	/**
	 * Create a new WebDataBinder instance, with default object name.
	 * @param target the target object to bind onto (or <code>null</code>
	 * if the binder is just used to convert a plain parameter value)
	 * @see #DEFAULT_OBJECT_NAME
	 */
	public WebDataBinder(Object target) {
//		super(target);
		this( target, null);
	}
	
	public BindingResult getBindingResult()
	{
		return bindingResult;
	}

	/**
	 * Create a new WebDataBinder instance.
	 * @param target the target object to bind onto (or <code>null</code>
	 * if the binder is just used to convert a plain parameter value)
	 * @param objectName the name of the target object
	 */
	public WebDataBinder(Object target, String objectName) {
//		super(target, objectName);
		this.target = target;
		this.objectName = objectName;
	}
	
	public WebDataBinder(Collection target, String objectName,Class objectType) {
//		super(target, objectName);
		this.targetContainer = target;
		this.objectName = objectName;
		this.objectType = objectType; 
	}
	public WebDataBinder(Map target, String mapKeyName,Class mapKeyType,String objectName,Class objectType) {
//		super(target, objectName);
		this.targetMapContainer = target;
		this.objectName = objectName;
		this.objectType = objectType; 
		this.mapKeyName = mapKeyName;
		this.mapKeyType = mapKeyType;
		
	}
	
	
	


	/**
	 * Specify a prefix that can be used for parameters that mark potentially
	 * empty fields, having "prefix + field" as name. Such a marker parameter is
	 * checked by existence: You can send any value for it, for example "visible".
	 * This is particularly useful for HTML checkboxes and select options.
	 * <p>Default is "_", for "_FIELD" parameters (e.g. "_subscribeToNewsletter").
	 * Set this to null if you want to turn off the empty field check completely.
	 * <p>HTML checkboxes only send a value when they're checked, so it is not
	 * possible to detect that a formerly checked box has just been unchecked,
	 * at least not with standard HTML means.
	 * <p>One way to address this is to look for a checkbox parameter value if
	 * you know that the checkbox has been visible in the form, resetting the
	 * checkbox if no value found. In Bboss web MVC, this typically happens
	 * in a custom <code>onBind</code> implementation.
	 * <p>This auto-reset mechanism addresses this deficiency, provided
	 * that a marker parameter is sent for each checkbox field, like
	 * "_subscribeToNewsletter" for a "subscribeToNewsletter" field.
	 * As the marker parameter is sent in any case, the data binder can
	 * detect an empty field and automatically reset its value.
	 * @see #DEFAULT_FIELD_MARKER_PREFIX
	 * 
	 */
	public void setFieldMarkerPrefix(String fieldMarkerPrefix) {
		this.fieldMarkerPrefix = fieldMarkerPrefix;
	}

	/**
	 * Return the prefix for parameters that mark potentially empty fields.
	 */
	public String getFieldMarkerPrefix() {
		return this.fieldMarkerPrefix;
	}

	/**
	 * Set whether to bind empty MultipartFile parameters. Default is "true".
	 * <p>Turn this off if you want to keep an already bound MultipartFile
	 * when the user resubmits the form without choosing a different file.
	 * Else, the already bound MultipartFile will be replaced by an empty
	 * MultipartFile holder.
	 * 
	 */
	public void setBindEmptyMultipartFiles(boolean bindEmptyMultipartFiles) {
		this.bindEmptyMultipartFiles = bindEmptyMultipartFiles;
	}

	/**
	 * Return whether to bind empty MultipartFile parameters.
	 */
	public boolean isBindEmptyMultipartFiles() {
		return this.bindEmptyMultipartFiles;
	}

	private boolean isCollection()
	{
		return this.targetContainer != null || this.targetMapContainer != null;
	}
	/**
	 * This implementation performs a field marker check
	 * before delegating to the superclass binding process.
	 * @throws Exception 
	 * @see #checkFieldMarkers
	 */
	protected void doBind(HttpServletRequest request,HttpServletResponse response,PageContext pageContext,
			MethodData handlerMethod,ModelMap model,HttpMessageConverter[] messageConverters) throws Exception {
//		checkFieldMarkers(mpvs);
		if(!this.isCollection())
			createTransferObject( request, response, pageContext,
					 handlerMethod, model,this.getTarget(),messageConverters);
		else
		{
			Object target = this.getTarget();
			if(target != null) //����ָ���������Ͳ��ҷ�������Ϊpo����
			{
				createTransferObject( request, response, pageContext,
						 handlerMethod, model,target,messageConverters);
			}
			else //����ָ���������Ͳ��ҷ�������Ϊ�������󣬻��߼���û��ָ����������
			{
				
			}
//			BeanInfo beanInfo = null;
//			try {
//				beanInfo = Introspector.getBeanInfo(this.objectType);
//			} catch (Exception e) {
//				model.getErrors().reject("createTransferObject.getBeanInfo.error",objectType.getCanonicalName() + ":"+e.getMessage());
////				throw new PropertyAccessException(new PropertyChangeEvent(whichToVO, "",
////					     null, null),"��ȡbean ��Ϣʧ��",e);
//				return ;
//			}
			//��һ����ʱ�򣬼���target����
			//list��Ҫ���ִ�attribute�л�ȡlist��set������������������Ҫ�����Զ����ݰ�
			//������Ļ�������Ҫ���ݰ󶨵Ķ��󼯺�,���󼯺ϵ�����Ŀǰ����ֻ��request�л�ȡ
			//����
//			int bcount = createTransferObject( request, response, pageContext,
//					 handlerMethod, model,this.getTarget(),messageConverters);
			
		}
	}
	
	
	public static class CallHolder
	{
		/**�����������ݣ������ظ���ȡ*/
		private Map<String,Object> datas = new HashMap<String,Object>();
		
		
		private int parameterCounts = -1;
		private boolean isCollection;
		
		
		/**�͵�ǰ��¼��صļ����������忪ʼ*/
		private int position = 0;
//		private boolean required = false;
//		private EditorInf editor = null;
		private Map<String,Boolean> isarray = new HashMap<String,Boolean>();
		private Map<String,Boolean> isrequired = new HashMap<String,Boolean>();
		private Map<String,EditorInf> editors = new HashMap<String,EditorInf>();
		/**
		 * ��¼���Ե����ڸ�ʽ�����ָ�����У�û��ָ����Ϊnull��
		 */
		private Map<String,SimpleDateFormat> dateformats = new HashMap<String,SimpleDateFormat>();
		private Map<String,Object> defaultValues = new HashMap<String,Object>();
		
		
		public boolean needAddData()
		{
			return this.isCollection && this.parameterCounts > 1 && this.getPosition() == 0;
		}
		
		
//		/**�͵�ǰ��¼��صļ��������������*/
//		
//		public void initisarray()
//		{
//			this.isarray = new boolean[this.getCounts()] ;
//		}
		
		public int increamentPosition()
		{
			this.position ++;
			return position;
		}
		
		public boolean isArray(String name)
		{
			boolean isarray = this.isarray.get(name) != null && this.isarray.get(name) == true;
			return isarray;
		}
		public Object[] getParameterValues(String name)
		{
			return (Object[])this.datas.get(name);
		}
		public void setCounts(int count)
		{
			if( count > parameterCounts)
				this.parameterCounts = count;
		}
		public void addData(String name,Object value,EditorInf editor,boolean required)
		{
			this.datas.put(name, value);
			this.isarray.put(name, false);
			isrequired.put(name, required);
			editors.put(name, editor);
		}
		
		public void addData(String name,Object value,EditorInf editor,boolean required,Object defaultValue)
		{
			this.datas.put(name, value);
			this.isarray.put(name, false);
			isrequired.put(name, required);
			editors.put(name, editor);
			this.defaultValues.put(name, defaultValue);
		}
		
		public void addData(String name,Object value)
		{
			this.datas.put(name, value);
			this.isarray.put(name, false);
			isrequired.put(name, false);
			editors.put(name, null);
		}
		public void addData(String name,String[] value,boolean isarray,EditorInf editor,boolean required)
		{
			this.datas.put(name, value);
			this.isarray.put(name, isarray);
			isrequired.put(name, required);
			editors.put(name, editor);
		}
		
		
		public void addData(String name,MultipartFile[] value,boolean isarray,EditorInf editor,boolean required)
		{
			this.datas.put(name, value);
			this.isarray.put(name, isarray);
			isrequired.put(name, required);
			editors.put(name, editor);
		}
		
		public void addData(String name,String[] value,boolean isarray,EditorInf editor,boolean required,Object defaultValue,String dateformat)
		{
			this.datas.put(name, value);
			this.isarray.put(name, isarray);
			isrequired.put(name, required);
			editors.put(name, editor);
			if(dateformat != null)
			{
				SimpleDateFormat df = new SimpleDateFormat(dateformat); 
				this.dateformats.put(name, df);
			}
			this.defaultValues.put(name, defaultValue);
		}
		
		
		public Object getData(String name)
		{
			Object value = null;
			
			if(this.isArray(name))
			{
				Object[] values = this.getParameterValues(name);
				try
				{
					if(values == null || this.getPosition() >= values.length)
						value = null;
					else
						value = this.getParameterValues(name)[this.getPosition()];
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
				Object defaultValue = this.defaultValues.get(name);
				if(value == null && defaultValue != null)
					value = defaultValue;
					
			}
			else
				value = this.datas.get(name);
			return value;
		}
		public int getCounts()
		{
			return this.parameterCounts;
		}
		
		public boolean isCollection()
		{
			return this.isCollection;
		}
		
		
		
		public boolean lasted()
		{
			return this.getPosition() >= this.getCounts();
		}
		public int getPosition() {
			return position;
		}

		/**
		 * @return the required
		 */
		public boolean isRequired(String name) {
			return this.isrequired.get(name) != null && this.isrequired.get(name) ;
		}

		/**
		 * @return the editor
		 */
		public EditorInf getEditor(String name) {
			return this.editors.get(name);
		}
		
		/**
		 * @return the dateformat
		 */
		public SimpleDateFormat getDateformat(String name) {
			return this.dateformats.get(name);
		}

		
		
	}
	
	
	/**
	 * added by biaoping.yin 2005.8.13
	 * ���϶�Ӧ�ķ����ǻ�����������/ö������/�������� ����û��ָ������
	 */
	public  void createTransferObject(
			HttpServletRequest request,HttpServletResponse response,PageContext pageContext,
			MethodData handlerMethod,ModelMap model,HttpMessageConverter[] messageConverters)
	{		
//		BeanInfo beanInfo = null;
//		try {
//			beanInfo = Introspector.getBeanInfo(whichToVO.getClass());
//			
//		} catch (Exception e) {
//			model.getErrors().reject("createTransferObject.getBeanInfo.error",whichToVO.getClass().getCanonicalName() + ":"+e.getMessage());
////			throw new PropertyAccessException(new PropertyChangeEvent(whichToVO, "",
////				     null, null),"��ȡbean ��Ϣʧ��",e);
//			return ;
//		} 
//		ClassInfo beanInfo = ClassUtil.getClassInfo(whichToVO.getClass());
		CallHolder holder = new CallHolder();
		holder.isCollection =  true;
		
		Object mapKey = null;
		if(holder.isCollection)//�������ͣ�List,Map��,���û�����ݼ�¼����ֱ�ӷ��أ��޸�û����������·���һ���ռ�¼������
		{
			boolean hasdata = false;
		
				
				String name = property.getName();
				
				Class type = property.getPropertyType();
				if(!HandlerUtils.isMultipartFile(type))
				{
					String[] values = request.getParameterValues(name);
					
					if(values != null && values.length > 0)
					{
						hasdata = true;
						break;
					}
				}
				else
				{
					if(!HandlerUtils.isIgnoreFieldNameMultipartFile(type))
					{
						if(request instanceof MultipartHttpServletRequest)
						{
							MultipartFile[] values = ((MultipartHttpServletRequest)request).getFiles(name);
							if(values != null && values.length > 0)
							{
								hasdata = true;
								break;
							}
						}
					}
					else
					{
						if(request instanceof MultipartHttpServletRequest)
						{
							MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
							MultipartFile[] values = multipartRequest.getFirstFieldFiles();
							if(values != null && values.length > 0)
							{
								hasdata = true;
								break;
							}
//							Iterator<String> filenames = multipartRequest.getFileNames();
//							if(filenames == null)
//								break;
//							while(filenames.hasNext())
//							{
//								MultipartFile[] values = multipartRequest.getFiles(filenames.next());
//								
//								if(values != null && values.length > 0)
//								{
//									hasdata = true;
//									break;
//								}
//							}
						}
					}
					
				}
				if(!hasdata)
					return;
				
			}
			
			
		
		do
		{
			if(holder.getPosition() > 0)
				try {
					whichToVO = HandlerUtils.newCommandObject(objectType);
				} catch (Exception e1) {
					e1.printStackTrace();
					break;
				}
			
			
			for(int in = 0; attributes != null && in < attributes.size(); in ++)
			{
				PropertieDescription property = attributes.get(in);
				if(!property.canwrite())
				{
					continue;
				}
//				Method writeMethod = property.getWriteMethod();
//				if(writeMethod == null)
//					continue;
				
				Object value = null;
				try {

					value = HandlerUtils.buildPropertyValue(property, request, response, pageContext, 
							handlerMethod, model, messageConverters, holder,whichToVO.getClass());
					if(this.mapKeyName != null && this.mapKeyName.equals(property.getName()))//�����map����󶨣�����Ҫ����map key��ֵ
						mapKey = ValueObjectUtil.typeCast(value, this.mapKeyType);
//					writeMethod.invoke(whichToVO, new Object[]{value});
					property.setValue(whichToVO, value);
					
				} catch (IllegalArgumentException e) {
					//rejectValue(String field, String errorCode, String[] rejectvalue,Class fieldtype,String defaultMessage);
//						model.getErrors().rejectValue(property.getName(), "buildPropertyValue.error",e.getMessage());
					model.getErrors().rejectValue(property.getName(), "buildPropertyValue.error", String.valueOf(value),property.getPropertyType(),null);
//						return ValueObjectUtil.getDefaultValue(property.getPropertyType());
//						throw new PropertyAccessException(new PropertyChangeEvent(whichToVO, property.getName(),
//							     null,null),"��������ʧ��",e);
				} catch (IllegalAccessException e) {
					
//						throw new PropertyAccessException(new PropertyChangeEvent(whichToVO, property.getName(),
//							     null, null),"��������ʧ��",e);
//						model.getErrors().rejectValue(property.getName(), "buildPropertyValue.error",e.getMessage());
					model.getErrors().rejectValue(property.getName(), "buildPropertyValue.error", String.valueOf(value),property.getPropertyType(),null);
				} catch (InvocationTargetException e) {
					
//						throw new PropertyAccessException(new PropertyChangeEvent(whichToVO, property.getName(),
//							     null, null),"��������ʧ��",e);
//						model.getErrors().rejectValue(property.getName(), "buildPropertyValue.error",e.getMessage());
					model.getErrors().rejectValue(property.getName(), "buildPropertyValue.error", String.valueOf(value),property.getPropertyType(),null);
				} catch (Exception e) {
					e.printStackTrace();
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//						model.getErrors().rejectValue(property.getName(), "buildPropertyValue.error",e.getMessage());
					model.getErrors().rejectValue(property.getName(), "buildPropertyValue.error", String.valueOf(value),property.getPropertyType(),null);
				}
			}
			if(holder.isCollection())
			{
				if(targetContainer != null )
					targetContainer.add(whichToVO);
				else if(mapKey != null)
					this.targetMapContainer.put(mapKey, whichToVO);
				holder.increamentPosition();
			}
		}while(this.isCollection() && !holder.lasted() );


		
		
	}
	/**
	 * added by biaoping.yin 2005.8.13
	 * ��map�а���������ֵ���Ƶ�������,��Ӧ���Ե����ƺ����ͱ���һ��
	 * @param completeVO ������ֵ��map����
	 * @param whichToVO �ն���
	 * @param validators 
	 * @return Object
	 */
	public  void createTransferObject(
			HttpServletRequest request,HttpServletResponse response,PageContext pageContext,
			MethodData handlerMethod,ModelMap model,
		    Object whichToVO,HttpMessageConverter[] messageConverters)
	{		
//		BeanInfo beanInfo = null;
//		try {
//			beanInfo = Introspector.getBeanInfo(whichToVO.getClass());
//			
//		} catch (Exception e) {
//			model.getErrors().reject("createTransferObject.getBeanInfo.error",whichToVO.getClass().getCanonicalName() + ":"+e.getMessage());
////			throw new PropertyAccessException(new PropertyChangeEvent(whichToVO, "",
////				     null, null),"��ȡbean ��Ϣʧ��",e);
//			return ;
//		} 
		ClassInfo beanInfo = ClassUtil.getClassInfo(whichToVO.getClass());
		CallHolder holder = new CallHolder();
		holder.isCollection =  this.isCollection();
		List<PropertieDescription> attributes = beanInfo.getPropertyDescriptors();		
		Object mapKey = null;
		if(holder.isCollection)//�������ͣ�List,Map��,���û�����ݼ�¼����ֱ�ӷ��أ��޸�û����������·���һ���ռ�¼������
		{
			boolean hasdata = false;
			PropertieDescription property = null;
			for(int in = 0; attributes != null && in < attributes.size(); in ++)
			{
				property = attributes.get(in);
				if(!property.canwrite())
				{
					continue;
				}
				
				String name = property.getName();
				
				Class type = property.getPropertyType();
				if(!HandlerUtils.isMultipartFile(type))
				{
					String[] values = request.getParameterValues(name);
					
					if(values != null && values.length > 0)
					{
						hasdata = true;
						break;
					}
				}
				else
				{
					if(!HandlerUtils.isIgnoreFieldNameMultipartFile(type))
					{
						if(request instanceof MultipartHttpServletRequest)
						{
							MultipartFile[] values = ((MultipartHttpServletRequest)request).getFiles(name);
							if(values != null && values.length > 0)
							{
								hasdata = true;
								break;
							}
						}
					}
					else
					{
						if(request instanceof MultipartHttpServletRequest)
						{
							MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
							MultipartFile[] values = multipartRequest.getFirstFieldFiles();
							if(values != null && values.length > 0)
							{
								hasdata = true;
								break;
							}
//							Iterator<String> filenames = multipartRequest.getFileNames();
//							if(filenames == null)
//								break;
//							while(filenames.hasNext())
//							{
//								MultipartFile[] values = multipartRequest.getFiles(filenames.next());
//								
//								if(values != null && values.length > 0)
//								{
//									hasdata = true;
//									break;
//								}
//							}
						}
					}
					
				}
				
			}
			if(!hasdata)
				return;
			
		}
		do
		{
			if(holder.getPosition() > 0)
				try {
					whichToVO = HandlerUtils.newCommandObject(objectType);
				} catch (Exception e1) {
					e1.printStackTrace();
					break;
				}
			
			
			for(int in = 0; attributes != null && in < attributes.size(); in ++)
			{
				PropertieDescription property = attributes.get(in);
				if(!property.canwrite())
				{
					continue;
				}
//				Method writeMethod = property.getWriteMethod();
//				if(writeMethod == null)
//					continue;
				
				Object value = null;
				try {

					value = HandlerUtils.buildPropertyValue(property, request, response, pageContext, 
							handlerMethod, model, messageConverters, holder,whichToVO.getClass());
					if(this.mapKeyName != null && this.mapKeyName.equals(property.getName()))//�����map����󶨣�����Ҫ����map key��ֵ
						mapKey = ValueObjectUtil.typeCast(value, this.mapKeyType);
//					writeMethod.invoke(whichToVO, new Object[]{value});
					property.setValue(whichToVO, value);
					
				} catch (IllegalArgumentException e) {
					//rejectValue(String field, String errorCode, String[] rejectvalue,Class fieldtype,String defaultMessage);
//						model.getErrors().rejectValue(property.getName(), "buildPropertyValue.error",e.getMessage());
					model.getErrors().rejectValue(property.getName(), "buildPropertyValue.error", String.valueOf(value),property.getPropertyType(),null);
//						return ValueObjectUtil.getDefaultValue(property.getPropertyType());
//						throw new PropertyAccessException(new PropertyChangeEvent(whichToVO, property.getName(),
//							     null,null),"��������ʧ��",e);
				} catch (IllegalAccessException e) {
					
//						throw new PropertyAccessException(new PropertyChangeEvent(whichToVO, property.getName(),
//							     null, null),"��������ʧ��",e);
//						model.getErrors().rejectValue(property.getName(), "buildPropertyValue.error",e.getMessage());
					model.getErrors().rejectValue(property.getName(), "buildPropertyValue.error", String.valueOf(value),property.getPropertyType(),null);
				} catch (InvocationTargetException e) {
					
//						throw new PropertyAccessException(new PropertyChangeEvent(whichToVO, property.getName(),
//							     null, null),"��������ʧ��",e);
//						model.getErrors().rejectValue(property.getName(), "buildPropertyValue.error",e.getMessage());
					model.getErrors().rejectValue(property.getName(), "buildPropertyValue.error", String.valueOf(value),property.getPropertyType(),null);
				} catch (Exception e) {
					e.printStackTrace();
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//						model.getErrors().rejectValue(property.getName(), "buildPropertyValue.error",e.getMessage());
					model.getErrors().rejectValue(property.getName(), "buildPropertyValue.error", String.valueOf(value),property.getPropertyType(),null);
				}
			}
			if(holder.isCollection())
			{
				if(targetContainer != null )
					targetContainer.add(whichToVO);
				else if(mapKey != null)
					this.targetMapContainer.put(mapKey, whichToVO);
				holder.increamentPosition();
			}
		}while(this.isCollection() && !holder.lasted() );


		
		
	}

	/**
	 * Check the given property values for field markers,
	 * i.e. for fields that start with the field marker prefix.
	 * <p>The existence of a field marker indicates that the specified
	 * field existed in the form. If the property values do not contain
	 * a corresponding field value, the field will be considered as empty
	 * and will be reset appropriately.
	 * @param mpvs the property values to be bound (can be modified)
	 * @see #getFieldMarkerPrefix
	 * @see #getEmptyValue(String, Class)
	 */
//	protected void checkFieldMarkers(Map mpvs) {
//		if (getFieldMarkerPrefix() != null) {
//			String fieldMarkerPrefix = getFieldMarkerPrefix();
//			
//			if(mpvs == null || mpvs.size() <= 0)
//				return ;
//			Iterator<Map.Entry<String,Object>> its = mpvs.entrySet().iterator();
//			while(its.hasNext()){
//				Map.Entry<String,Object> pv = its.next();
//				if (pv.getKey().startsWith(fieldMarkerPrefix)) {
//					String field = pv.getKey().substring(fieldMarkerPrefix.length());
//					if (!mpvs.containsKey(field)) {
//						Class fieldType = getPropertyAccessor().getPropertyType(field);
//						mpvs.put(field, getEmptyValue(field, fieldType));
//					}
//					mpvs.remove(pv.getKey());
//				}
//			}
//		}
//	}

	/**
	 * Determine an empty value for the specified field.
	 * <p>Default implementation returns <code>Boolean.FALSE</code>
	 * for boolean fields and an empty array of array types.
	 * Else, <code>null</code> is used as default.
	 * @param field the name of the field
	 * @param fieldType the type of the field
	 * @return the empty value (for most fields: null)
	 */
	protected Object getEmptyValue(String field, Class fieldType) {
		if (fieldType != null && boolean.class.equals(fieldType) || Boolean.class.equals(fieldType)) {
			// Special handling of boolean property.
			return Boolean.FALSE;
		}
		else if (fieldType != null && fieldType.isArray()) {
			// Special handling of array property.
			return Array.newInstance(fieldType.getComponentType(), 0);
		}
		else {
			// Default value: try null.
			return null;
		}
	}


	/**
	 * Bind the multipart files contained in the given request, if any
	 * (in case of a multipart request).
	 * <p>Multipart files will only be added to the property values if they
	 * are not empty or if we're configured to bind empty multipart files too.
	 * @param multipartFiles Map of field name String to MultipartFile object
	 * @param mpvs the property values to be bound (can be modified)
	 * 
	 * @see #setBindEmptyMultipartFiles
	 */
	protected void bindMultipartFiles(Map multipartFiles, Map mpvs) {
		for (Iterator it = multipartFiles.entrySet().iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			String key = (String) entry.getKey();
			MultipartFile value = (MultipartFile) entry.getValue();
			if (isBindEmptyMultipartFiles() || !value.isEmpty()) {
				mpvs.put(key, value);
			}
		}
	}

	private Object getTarget() throws Exception {
		if(this.targetContainer == null && this.targetMapContainer == null)
			return target;
		else if(objectType != null )//Ӧ��û������
		{
			if(!ValueObjectUtil.isBasePrimaryType(objectType) 
					&& !HandlerUtils.isMultipartFile(objectType))
				return HandlerUtils.newCommandObject(objectType);
			else //���϶�Ӧ�ķ����ǻ�����������/ö������/��������
			{
				return null;
			}
		}
		else //�Ǽ��ϵ��Ǽ���û��ָ������
		{
			return null;
		}
	}

	public void setBindingResult(BindingResult bindingResult) {
		this.bindingResult = bindingResult;
	}

}
