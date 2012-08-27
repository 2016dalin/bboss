package org.frameworkset.web.token;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.frameworkset.util.StringUtil;


/**
 * ��ֹ��վ���������
 * bboss��ֹ��վ����������Ļ������£�
 * ���ö�̬���ƺ�session���ϵķ�ʽ�����ͻ������ƣ�һ���������һ��Ψһ����
 * ����ʶ����ÿͻ������ƺͷ����session��ʶ��ϵķ�ʽ�����б�����ͻ������ƺͷ����������ȷƥ�䣬��������ʣ�������Ϊ�û�Ϊ�Ƿ��û�����ֹ�û����ʲ���ת��
 * redirectpath������Ӧ�ĵ�ַ��Ĭ��Ϊ/login.jsp��
 * 
 * ���ƴ洢����ͨ������tokenstoreָ�����������֣��ڴ�洢��session�洢��Ĭ��Ϊsession�洢��������ʧЧ��ƥ�������ʧЧ�����߳�ʱʧЧ����ϵͳ�Զ����ʧЧ�����ƣ�����session��ʽ
 * �洢����ʱ������ͻ���ҳ��û������session����ô���ƻ��ǻ�洢���ڴ��С�
 * 
 * �����������ڣ��ͻ��˵������ڷ����������д����������ʧЧ��ƥ�������ʧЧ�����߳�ʱʧЧ����ϵͳ�Զ����ʧЧ�����ƣ�
 * ���ͻ��˲�û����ȷ�ύ���󣬻ᵼ�·�������ƴ����Ϊ�������ƣ���Ҫ��ʱ�����Щ
 * �������ƣ�������ƴ洢��session�У���ô���Ƶ��������ں�session���������ڱ���һ�£��������������ƣ�
 * ������ƴ洢���ڴ��У���ô���Ƶ���������ƹ�������Լ���ʱɨ���������ʱɨ��ʱ����Ϊ��tokenscaninterval����ָ������λΪ���룬Ĭ��Ϊ30���ӣ��������ʱ����tokendualtime����ָ����Ĭ��Ϊ1��Сʱ
 * 
 * ����ͨ��enableToken��������ָ���Ƿ��������Ƽ����ƣ�true��⣬false����⣬Ĭ��Ϊfalse�����
 * 
 * @author biaoping.yin
 *
 */
public class TokenFilter implements Filter{
	protected MemTokenManager memTokenManager;
	private static Logger log = Logger.getLogger(TokenFilter.class);
	protected String tokenfailpath = null;
	protected String redirectpath = "/login.jsp";
	protected boolean enableToken = false;
	/**
	 * tokenstore
	 * ָ�����ƴ洢���ƣ�Ŀǰ�ṩ���ֻ��ƣ�
	 * mem��������ֱ�Ӵ洢���ڴ�ռ���
	 * session�������ƴ洢��session��
	 * Ĭ�ϴ洢��session��
	 */
	protected String tokenstore = "session";
//	protected String tokenstore = "mem";
	
	public void init(FilterConfig arg0) throws ServletException
	{
		String tokendualtime = arg0.getInitParameter("tokendualtime");
		String redirectpath_ =  arg0.getInitParameter("redirecturl");
		String tokenfailpath_ =   arg0.getInitParameter("tokenfailpath");
		String tokenstore_ = arg0.getInitParameter("tokenstore");
		if(!StringUtil.isEmpty(tokenstore_))
		{
			if(tokenstore_.toLowerCase().equals("mem") || tokenstore_.toLowerCase().equals("session"))
			{
				tokenstore = tokenstore_.toLowerCase();
				log.debug("Set tokenstore["+tokenstore_+"] failed,tokens will be stored in session.");
			}
			else
			{
				log.debug("Set tokenstore["+tokenstore_+"] success,tokens will be stored in session.");
			}
		}
		
		String tokenscaninterval = arg0.getInitParameter("tokenscaninterval");
		if(!StringUtil.isEmpty(redirectpath_))
		{
			redirectpath = redirectpath_; 
		}
		
		if(!StringUtil.isEmpty(tokenfailpath_))
		{
			tokenfailpath = tokenfailpath_; 
		}
//		else
//		{
//			tokenfailpath = redirectpath; 
//		}
		
		String enableToken_ = arg0.getInitParameter("enableToken");
		if(!StringUtil.isEmpty(enableToken_))
		{
			try {
				enableToken = Boolean.parseBoolean(enableToken_);
			} catch (Exception e) {
				log.debug("Set enableToken failed,false will be used.",(e));
			} 
		}
//		String tmp = arg0.getServletContext().getServletContextName();
//		this.redirectpath = StringUtil.getRealPath(tmp, redirectpath);
		
		long dualtime =  3600000;
		long tokenscaninterval_ = 1800000;
		if(!StringUtil.isEmpty(tokendualtime))
		{
			try {
				dualtime = Long.parseLong(tokendualtime);
			} catch (NumberFormatException e) {
				log.debug("Set tokendualtime failed,-1 will be used.",(e));
			}
		}
		if(!StringUtil.isEmpty(tokenscaninterval))
		{
			try {
				tokenscaninterval_ = Long.parseLong(tokenscaninterval);
			} catch (NumberFormatException e) {
				log.debug("Set tokendualtime failed,-1 will be used.",(e));
			}
		}
		if(enableToken)
			memTokenManager = MemTokenManagerFactory.getMemTokenManager(dualtime,tokenscaninterval_,enableToken,this.tokenstore,this);
	}
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		try {
			if(!checkTokenExist((HttpServletRequest )arg0,(HttpServletResponse )arg1))//���Ƽ�飬�����ǰ�����Ѿ�ʧЧ��ֱ����ת����¼ҳ������������к�ȥ��ȫ��֤���
			{
				return ;
			}
			else
			{				
				arg2.doFilter(arg0, arg1);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	protected boolean checkTokenExist(HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		if(!this.enableToken)//���û���������ƻ��ƣ���ֱ���������ƴ���
			return true;
		if(!this.memTokenManager.firstRequest(request))
		{
//			if(!response.isCommitted())
//			{
//				StringBuffer targetUrl = new StringBuffer();
//				if ( this.tokenfailpath.startsWith("/")) {
//					targetUrl.append(request.getContextPath());
//				}
//				targetUrl.append(this.tokenfailpath);
//				sendRedirect(request, response,targetUrl.toString(),  true,false,false);
//			}
			sendRedirect(request,
					response);
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public void sendRedirect(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if(!response.isCommitted())
		{
			if(this.tokenfailpath != null)
			{
				StringBuffer targetUrl = new StringBuffer();
				if ( this.tokenfailpath.startsWith("/")) {
					targetUrl.append(request.getContextPath());
				}
				targetUrl.append(this.tokenfailpath);
				
				sendRedirect(request, response,targetUrl.toString(),  true,false,false);
			}
			else
			{
				sendRedirect403(request,response);
			}
		}
	}
	
	public void sendRedirect403(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if(!response.isCommitted())
		{
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
		}
	}
	protected String appendDTokenToTargetURL(HttpServletRequest request, String targetUrl)
	{
		if(this.memTokenManager != null)
		{
			return memTokenManager.appendDTokenToURL(request, targetUrl);
		}
		else
		{
			return targetUrl;
		}
	}
	
	protected boolean isloopredirect(HttpServletRequest request,
			String targetUrl)
	{
		String fromurl = request.getRequestURI();
		int idx = targetUrl.indexOf("?");
		String comp = targetUrl;
		if(idx > 0)
		{
			comp = targetUrl.substring(0,idx);
		}
		if(fromurl.equals(comp))
			return true;
		return false;
	}
	protected void sendRedirect(HttpServletRequest request,
			HttpServletResponse response, String targetUrl,
			boolean http10Compatible,boolean isforward,boolean isinclude) throws IOException {

		if(isloopredirect(request,
				targetUrl))
		{
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
			return;
		}
		if(!isforward)
		{
			
			targetUrl = appendDTokenToTargetURL(request, targetUrl);
			if(!isinclude)
			{
				if (http10Compatible) {
					// Always send status code 302.
					response.sendRedirect(response.encodeRedirectURL(targetUrl));
				} else {
					// Correct HTTP status code is 303, in particular for POST requests.
					response.setStatus(303);
					response.setHeader("Location", response
							.encodeRedirectURL(targetUrl));
				}
			}
			else
			{
				 try
					{
						request.getRequestDispatcher(targetUrl).include(request, response);
					}
					catch (ServletException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}
		else
		{
			 try
			{
				request.getRequestDispatcher(targetUrl).forward(request, response);
			}
			catch (ServletException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	
	
	public String getRedirecturl() {

		return this.redirectpath;
	}

	public void setRedirecturl(String redirecturl) {

		this.redirectpath = redirecturl;
	}
	

}
