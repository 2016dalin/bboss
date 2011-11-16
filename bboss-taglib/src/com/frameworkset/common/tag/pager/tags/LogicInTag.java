package com.frameworkset.common.tag.pager.tags;
/**
 * 
 * <p>Title: LogicInTag</p>
 *
 * <p>Description: �жϼ������Ի���expression���Զ�Ӧ�ı��ʽ�õ�ֵ�Ƿ����
 * ��scope���Ի���ʽ��Ӧ��ֵ��Χ֮�ڣ�ֵ��Χ�Զ��ŷָ������������ִ��in��ǩ���Ӧ�Ĵ��룬����ִ��</p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: ��ɳ�ƴ������ϵͳ�������޹�˾</p>
 * @Date 2007-12-7 10:36:55
 * @author biaoping.yin
 * @version 1.0
 */
public class LogicInTag extends MatchTag {
	
	protected boolean match() {

		if(actualValue == null)
			return false;
		
		for(int i = 0; i < this.getScopes().length; i ++)
		{
			if(String.valueOf(this.actualValue).equals(scopes[i]))
				return true;
		}
		return false;
		
	}

}
