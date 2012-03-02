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

package org.frameworkset.spi;

import org.frameworkset.spi.assemble.Pro;

/**
 * <p>Title: BeanInfoAware.java</p> 
 * <p>Description: �������ע�������ӦpropertyԪ������Ϣ���Ա�������л�ȡ��ص�������Ϣ
 * һ���org.frameworkset.spi.InitializingBean�ӿ����ʹ��
 * </p>
 * <p>bboss workgroup</p>
 * <p>Copyright (c) 2007</p>
 * @Date 2012-3-2 ����09:48:03
 * @author biaoping.yin
 * @version 1.0
 */
public abstract class BeanInfoAware {
	protected Pro beaninfo;

	public Pro getBeaninfo() {
		return beaninfo;
	}

	public void setBeaninfo(Pro beaninfo) {
		this.beaninfo = beaninfo;
	}

}
