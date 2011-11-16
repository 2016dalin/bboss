package com.frameworkset.common.tag.pager.tags;

import com.frameworkset.util.RegexUtil;

/**
 * �߼�ƥ���ǩ��
 * �жϵ�ǰ��ֵ�Ƿ��pattern���Զ�Ӧ������ʽ��ƥ�䣬ƥ����ִ�б�ǩ���еĴ���
 * ����ִ��
 * <p>Title: LogicRexMatchTag</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: ��ɳ�ƴ������ϵͳ�������޹�˾</p>
 * @Date 2007-12-6 20:18:52
 * @author biaoping.yin
 * @version 1.0
 */
public class LogicRexMatchTag extends MatchTag {
	
	protected boolean match() {
		
		if(actualValue == null)
			return false;
		if(RegexUtil.isMatch(String.valueOf(actualValue),pattern))
			return true;
		else
			return false;
	}

}
