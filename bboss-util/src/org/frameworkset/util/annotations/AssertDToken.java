/*
 *  Copyright 2008 bbossgroups
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

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Title: DToken.java</p> 
 * <p>Description:������עmvc����������ǿ��Ҫ����ж�̬����У�飬����ͻ�������û�и������ƻ��������Ѿ����ϣ���ôֱ�Ӿܾ�
 * ���� 
 * 
 * ��ֹ��վ�����������ز��� bboss��ֹ��վ����������Ļ������£� ���ö�̬���ƺ�session���ϵķ�ʽ�����ͻ������ƣ�һ���������һ��Ψһ���� 
			����ʶ����ÿͻ������ƺͷ����session��ʶ��ϵķ�ʽ�����б�����ͻ������ƺͷ����������ȷƥ�䣬��������ʣ�������Ϊ�û�Ϊ�Ƿ��û�����ֹ�û����ʲ���ת�� 
			redirectpath������Ӧ�ĵ�ַ��Ĭ��Ϊ/login.jsp�� ���ƴ洢����ͨ������tokenstoreָ�����������֣��ڴ�洢��session�洢��Ĭ��Ϊsession�洢��������ʧЧ��ƥ�������ʧЧ�����߳�ʱʧЧ����ϵͳ�Զ����ʧЧ�����ƣ�����session��ʽ 
			�洢����ʱ������ͻ���ҳ��û������session����ô���ƻ��ǻ�洢���ڴ��С� �����������ڣ��ͻ��˵������ڷ����������д����������ʧЧ��ƥ�������ʧЧ�����߳�ʱʧЧ����ϵͳ�Զ����ʧЧ�����ƣ� 
			���ͻ��˲�û����ȷ�ύ���󣬻ᵼ�·�������ƴ����Ϊ�������ƣ���Ҫ��ʱ�����Щ �������ƣ�������ƴ洢��session�У���ô���Ƶ��������ں�session���������ڱ���һ�£��������������ƣ� 
			������ƴ洢���ڴ��У���ô���Ƶ���������ƹ�������Լ���ʱɨ���������ʱɨ��ʱ����Ϊ��tokenscaninterval����ָ������λΪ���룬Ĭ��Ϊ30���ӣ��������ʱ����tokendualtime����ָ����Ĭ��Ϊ1��Сʱ 
			����ͨ��enableToken��������ָ���Ƿ��������Ƽ����ƣ�true��⣬false����⣬Ĭ��Ϊfalse����� enableToken�Ƿ��������Ƽ����ƣ�true 
			���ã�false �����ã�Ĭ��Ϊ������</p>
 * <p>bboss workgroup</p>
 * <p>Copyright (c) 2008</p>
 * @Date 2012-8-24
 * @author biaoping.yin
 * @version 3.6
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AssertDToken {

}
