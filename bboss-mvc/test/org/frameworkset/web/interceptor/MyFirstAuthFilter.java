package org.frameworkset.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.frameworkset.web.servlet.handler.HandlerMeta;

public class MyFirstAuthFilter extends AuthenticateFilter{

	@Override
	protected boolean check(HttpServletRequest request,
			HttpServletResponse response, HandlerMeta handlerMeta)
	{
		
		String name = request.getParameter("name");
		//���ҳ�����name���������Ҳ���ֵΪtest������Ϊ��һ���ǰ�ȫҳ�棬ϵͳ���Զ���ת��redirecturl���Զ�Ӧ��ҳ��
		if(name != null && name.equals("test"))
			return false;
		return true;
	}

	@Override
	protected boolean checkPermission(HttpServletRequest request,
			HttpServletResponse response, HandlerMeta handlerMeta, String uri) {
		//���ҳ���ַΪauthorfailed.htm������Ϊ��һ���ǰ�ȫҳ�棬ϵͳ���Զ���ת��authorfailedurl���Զ�Ӧ��ҳ��
		if(uri.equals("/authorfailed.htm"))
			return false;
		return true;
	}

}
