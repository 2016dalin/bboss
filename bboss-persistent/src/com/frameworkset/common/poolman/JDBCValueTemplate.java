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
package com.frameworkset.common.poolman;

/**
 * 
 * 
 * <p>Title: JDBCValueTemplate.java</p>
 *
 * <p>Description: ����ִ��ģ���࣬ �ṩִ�����ݿ������ģ�巽�����÷����з���ֵ
 * ��ģ�巽���������е��������ݿ����������һ�����ݿ�������</p>
 * @see com.frameworkset.common.poolman.TemplateDBUtil�еķ���
 * public static Object executeTemplate(JDBCValueTemplate template) throws Throwable
 *
 * <p>
 * bboss workgroup
 * </p>
 * <p>
 * Copyright (c) 2007
 * </p>
 * 
 * @Date 2009-6-1 ����08:58:51
 * @author biaoping.yin
 * @version 1.0
 */
public interface JDBCValueTemplate<T> {
	/**
	 * ����ʵ����Ҫ���Ƶ����ݿ���������ݿ������ģ�巽���������û�ֱ���ڴ�����ʹ��
	 * ���ݿ����������롣
	 * �÷��������û�����һ���������͵ķ���ֵ
	 * @throws Exception
	 * @return Object
	 * 
	 */
	public T execute() throws Exception;

}
