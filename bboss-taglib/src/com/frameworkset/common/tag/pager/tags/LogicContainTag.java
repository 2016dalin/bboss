package com.frameworkset.common.tag.pager.tags;

import com.frameworkset.util.RegexUtil;

/**
 * �߼�������ǩ��
 * �жϵ�ǰ��ֵ�Ƿ��pattern���Զ�Ӧ������ʽ�������������ִ�б�ǩ���еĴ���
 * ����ִ��
 * <p>Title: LogicContainTag</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: ��ɳ�ƴ������ϵͳ�������޹�˾</p>
 * @Date 2007-12-6 20:26:14
 * @author biaoping.yin
 * @version 1.0
 */
public class LogicContainTag extends MatchTag {
	
	protected boolean match() {
		
		if(actualValue == null)
			return false;
		if(RegexUtil.isContain(String.valueOf(actualValue),pattern))
			return true;
		else
			return false;
	}	

}
