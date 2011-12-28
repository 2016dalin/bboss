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

package org.frameworkset.spi.async.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Title: Async.java</p> 
 * <p>Description: ����֧�ַ�������������첽���û���
 * 1.�����첽���ã����ǽ��ͨ���ص��ķ�ʽ���ظ����ö�
   2������Ҫ�ȴ�����ĵ��첽����
   3����Ҫ�ȴ����������ָ���ȴ���ʱʱ�䣬һ��timeout�ͱ���ʱ�쳣
 * </p>
 * <p>bboss workgroup</p>
 * <p>Copyright (c) 2007</p>
 * @Date 2011-4-20 ����02:21:50
 * @author biaoping.yin
 * @version 1.0
 */
@Target({ ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Async {
	/**
	 * ָ���첽���õĳ�ʱʱ�䣬Ĭ��Ϊ-1�����õȴ���ֱ�����÷�����
	 * ����0ʱ���ȴ����������ָ����ʱ����׳���ʱ�쳣
	 * @return
	 */
	public long timeout() default -1;
	/**
	 * �����첽���ã����ǽ��ͨ���ص��ķ�ʽ���ظ����ö�
	 * @return
	 */
	public String callback() default Constants.NULLCALLBACK;
	/**
	 * �Ƿ���Ҫ���ص��ý����Ĭ�ϲ����أ����̼߳�����ǰ��
	 * �����Ҫ�����򣬸���timeout��callback��������������
	 * ���ؽ���ĵȴ�����ģʽ��
	 * 
	 * ��timeout > 0 ��ȴ��ض���ʱ��������ȡ���������ָ����ʱ�����׳�ʱ�쳣���ȴ���ʱ��ģʽ�ַ�Ϊ���������
	 * ���ָ���˻ص������������������򣬽���������ص�����������
	 * ���û��ָ���ص����������������򣬽��������������������
	 * 
	 * ��timeout <= 0 ʱ�������õȴ������ֱ��������أ�����ģʽҲ�����������
	 * ���ָ���˻ص����� ������������
	 * ���û��ָ���ص�������������������ֱ�����������
	 * @return
	 */
	public Result result() default Result.NO;	
}
