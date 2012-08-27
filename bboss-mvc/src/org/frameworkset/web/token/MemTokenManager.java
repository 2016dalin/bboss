package org.frameworkset.web.token;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.frameworkset.spi.BaseApplicationContext;
import org.frameworkset.util.HashUtil;

import com.frameworkset.util.StringUtil;

/**
 * @author biaoping.yin
 * 
 *
 */
public class MemTokenManager {
	private final Map<MemToken,Object> temptokens = new HashMap<MemToken,Object>();
	private TokenFilter tokenFilter;
	/**
	 * bboss��վ����token�Ĳ������ƣ�ÿ���ͻ���ҳ��ͨ��������ƽ�token���ط���˽���
	 * У��
	 */
	public static final String temptoken_param_name = "_dt_token_";
	private static final String temptoken_request_attribute = "org.frameworkset.web.token.bboss_csrf_Token"; 
	public static final String temptoken_request_validateresult_key = "temptoken_request_validateresult_key";
	/**
	 * ����У��ɹ�
	 */
	public static final Integer temptoken_request_validateresult_ok = new Integer(1);
	/**
	 * ����У��ʧ��
	 */
	public static final Integer temptoken_request_validateresult_fail = new Integer(0);
	/**
	 * ������״̬�����״̬��Ͽ�����������AssertDTokenע���jspҳ���ϵ�AssertDTokenTagһ��ʹ�ã��������������AssertDTokenע�����jspҳ��������AssertDTokenTag��ǩ����Ҫ�����ʹ������
	 * ����ͻ���û�д������ƣ���ܾ�����
	 * AssertDToken��AssertDTokenTag��Ҫ������ֹ�ͻ��˰�����ȥ������ƭ���������з���
	 */
	public static final Integer temptoken_request_validateresult_nodtoken = new Integer(2);
	private final Object c = new Object();
	private final Object checkLock = new Object();
	private boolean enableToken = false;
	private TokenMonitor tokenMonitor;
	public static final int tokenstore_in_session = 1;
	public static final int tokenstore_in_mem = 0;
	
	
	/**
	 * tokenstore
	 * ָ�����ƴ洢���ƣ�Ŀǰ�ṩ���ֻ��ƣ�
	 * mem��������ֱ�Ӵ洢���ڴ�ռ���
	 * session�������ƴ洢��session��
	 * Ĭ�ϴ洢��session��
	 */
	protected String tokenstore = "session";
	protected int tokenstore_i = tokenstore_in_session;
	/**
	 * ���Ƴ���ʱ��,Ĭ��Ϊ1��Сʱ
	 */
	private long tokendualtime = 3600000;
	/**
	 * ���Ƴ�ʱ���ʱ������Ĭ��Ϊ-1�������
	 * �����Ҫ��⣬��ôֻҪ���Ƴ���ʱ�䳬��tokendualtime
	 * ��Ӧ��ʱ�佫�ᱻ���
	 */
	private long tokenscaninterval = 1800000;
	
	MemTokenManager(long tokendualtime,long tokenscaninterval,boolean enableToken,String tokenstore,TokenFilter tokenFilter)
	{
		this.tokendualtime = tokendualtime;
		this.tokenscaninterval = tokenscaninterval;
		this.enableToken = enableToken;
		this.tokenstore = tokenstore; 
		this.tokenFilter = tokenFilter;
		if(tokenstore.equals("mem"))
			tokenstore_i = tokenstore_in_mem;
		else
			tokenstore_i = tokenstore_in_session;
		if(enableToken && tokenscaninterval > 0 && tokendualtime > 0)
		{
			tokenMonitor = new TokenMonitor();
			tokenMonitor.start();
			BaseApplicationContext.addShutdownHook(new Runnable(){

				@Override
				public void run() {
					tokenMonitor.killdown();
					
				}
				
			});
		}
	}
	
	public void put(String token)
	{
		if(this.enableToken)
		{
			synchronized(checkLock)
			{
				temptokens.put(new MemToken(token,System.currentTimeMillis()), c);
			}
		}
	}
	
	public Integer sessionmemhash(String token,HttpSession session)
	{
//		String sessionid = session.getId();
//		String token = request.getParameter(temptoken_param_name);
		if(token == null)
			return MemTokenManager.temptoken_request_validateresult_nodtoken;
		
//		String hash = String.valueOf(HashUtil.mixHash(new StringBuffer().append(sessionid).append("_").append(token).toString()));
//		if(session.getAttribute(hash) != null)
//		{
//			session.removeAttribute(hash);
//			return true;
//		}
//		else
//			return false;
		if(this.tokenstore_i == tokenstore_in_session)
		{
			
			if(session.getAttribute(token) != null)
			{
				session.removeAttribute(token);
				return MemTokenManager.temptoken_request_validateresult_ok;
			}
			else
				return MemTokenManager.temptoken_request_validateresult_fail;
		}
		else//in memory
		{
			String sessionid = session.getId();
			token = token + "_" + sessionid;
			return _mem(token);
		}
		
		
	}
	
	/**
	 * �����̬����У��ɹ���������û�����÷���true
	 * @param result
	 * @return
	 */
	private boolean assertDToken(Integer result)
	{
		return result == temptoken_request_validateresult_ok || result == temptoken_request_validateresult_nodtoken;
	}
	/**
	 * �ж������Ƿ���Ч��һ������ֻ�ж�һ�Σ��������ж�
	 * ͬʱ��¼�жϽ�����Ա�������������ȡ������������Ӧ�Ĵ���
	 * @param request
	 * @return
	 */
	protected boolean firstRequest(ServletRequest request) 
	{
		Integer result = (Integer)request.getAttribute(MemTokenManager.temptoken_request_validateresult_key);//
		if(result != null)
		{
			return assertDToken(result);
		}
		
		String token = request.getParameter(MemTokenManager.temptoken_param_name);
		if(request instanceof HttpServletRequest)
		{
			
			HttpSession session = ((HttpServletRequest)request).getSession(false);
			if(session == null)
			{
				result = mem(token);
			}
			else
			{
				result = sessionmemhash(token,session);
			}
		}
		else
		{
			result = mem(token);
		}
		request.setAttribute(MemTokenManager.temptoken_request_validateresult_key,result);
		return 	assertDToken(result);
	}
	public static final String temptoken_param_name_word = temptoken_param_name + "=";
	/**
	 * Ϊurl׷�Ӷ�̬���Ʋ���
	 * @param url
	 * @return
	 */
	public String appendDTokenToURL(HttpServletRequest request,String url)
	{
		if(url == null)
			return url;
		if(url.indexOf(temptoken_param_name_word) > 0)
			return url;
		StringBuffer ret = new StringBuffer();
		String token = this.buildDToken(request);
		int idx = url.indexOf("?");
		if(idx > 0)
		{
			ret.append(url).append("&").append(temptoken_param_name).append("=").append(token);
		}
		else
		{
			ret.append(url).append("?").append(temptoken_param_name).append("=").append(token);
		}
		return ret.toString();
			
		
	}
	
	/**
	 * �ж������Ƿ����ò���У��ɹ�
	 * @param result
	 * @return
	 */
	private boolean assertDTokenSetted(Integer result)
	{
//		return !(result == MemTokenManager.temptoken_request_validateresult_nodtoken 
//				|| result == MemTokenManager.temptoken_request_validateresult_fail);		
		return result == MemTokenManager.temptoken_request_validateresult_ok;
	}
	
	/**
	 * �ж������Ƿ����ò���У��ɹ�
	 * @param result
	 * @return
	 */
	public boolean assertDTokenSetted(ServletRequest request)
	{

		Integer result = (Integer)request.getAttribute(temptoken_request_validateresult_key);
		return assertDTokenSetted(result);
		
	}
	
	public Integer mem(String token)
	{
//		String token = request.getParameter(temptoken_param_name);
		return _mem(token);
	}
	
	private Integer _mem(String token)
	{
		
		if(token != null)
		{
			synchronized(checkLock)
			{
				Object tt =temptokens.remove(new MemToken(token,-1));
				if(tt != null)//is first request,and clear temp token to against Cross Site Request Forgery
				{
//					temptokens.remove(token);				
					return MemTokenManager.temptoken_request_validateresult_ok;
				}
				else
				{
					return MemTokenManager.temptoken_request_validateresult_fail;
				}
			}
		}
		else 
		{
			return MemTokenManager.temptoken_request_validateresult_nodtoken;
		}
	}
	
	public String genToken(ServletRequest request,String fid,boolean cache)
	{
		String tmp = null;
		String k = null;
		if(fid != null)
		{
			k = temptoken_request_attribute+ "_" + fid;
			tmp = (String)request.getAttribute(k);
			if(tmp != null)//����Ѿ�����token����ֱ�ӷ���������toke�������ظ�����token
				return tmp;
		}
		
		if(request instanceof HttpServletRequest)
		{
			HttpSession session = ((HttpServletRequest)request).getSession(false);
			if(session == null)
			{
				tmp = memToken( cache);
			}
			else
			{
				tmp = sessionmemhashToken(request,session,cache);
			}
		}
		else
		{
			tmp = memToken( cache);
		}
		if(fid != null)
		{
			request.setAttribute(k, tmp);//��������token����request������һ����������������ͬ��token
		}
		return tmp;
	}
	
	private String sessionmemhashToken(ServletRequest request,HttpSession session,boolean cache)
	{
		String sessionid = session.getId();
		String token = UUID.randomUUID().toString();
		String hash = String.valueOf(HashUtil.mixHash(new StringBuffer().append(sessionid).append("_").append(token).toString()));
		if(this.enableToken)
		{
			if(cache)
			{
				if(this.tokenstore_i == tokenstore_in_session)
				{
					session.setAttribute(hash, c);
				}
				else
				{
					put(hash + "_" + sessionid);
				}
			}
		}
		return hash;
	}
	private String memToken(boolean cache)
	{
		String token = UUID.randomUUID().toString();
		if(cache)
			put(token);
		return token;
	}
	
	class TokenMonitor extends Thread
	{
		public TokenMonitor()
		{
			super("DTokens Scan Thread.");
		}
		private boolean killdown = false;
		public void start()
		{
			super.start();
		}
		public void killdown() {
			killdown = true;
			synchronized(this)
			{
				this.notifyAll();
			}
			
		}
		@Override
		public void run() {
			while(!killdown)
			{
				synchronized(this)
				{
					try {
						
						this.wait(tokenscaninterval);
					} catch (InterruptedException e) {
						break;
					}
				}
				if(killdown)
					break;
				check();				
			}
		}
		public boolean isKilldown() {
			return killdown;
		}
		
	}
	
	private void check()
	{
		List<MemToken> olds = new ArrayList<MemToken>();
		synchronized(this.checkLock)
		{
			Set<MemToken> keySet = this.temptokens.keySet();
			Iterator<MemToken> itr = keySet.iterator();
			
			while(itr.hasNext())
			{
				
				MemToken token = itr.next();
				if(isold(token))
				{
					olds.add(token);
//					temptokens.remove(token);
				}
			}
		}
		MemToken token = null;
		for(int i = 0; i < olds.size(); i ++)
		{
			if(tokenMonitor.isKilldown())
				break;
			token = olds.get(i);
			temptokens.remove(token);
		}
		olds = null;
		
	}
	
	private boolean isold(MemToken token)
	{
		long currentTime = System.currentTimeMillis();
		long age = currentTime - token.getCreateTime();		
		return age > this.tokendualtime;
		
	}
	
	public String buildDToken(String elementType,HttpServletRequest request)
	{
		return buildDToken(elementType,"'",request,null);
	}
	public String buildDToken(String elementType,String jsonsplit,HttpServletRequest request,String fid)
	{
		return buildDToken(elementType,jsonsplit,request,fid,true);
	}
	/**
	 * ��������������,���ֵΪ��
	 * <input type="hidden" name="_dt_token_" value="-1518435257">
	 * @param request
	 * @return
	 */
	public String buildHiddenDToken(HttpServletRequest request)
	{
		return buildDToken("input",null,request,null,true);
	}
	/**
	 * ����json������
	 * ���jsonsplitΪ'�������ֵΪ��
	 * _dt_token_:'1518435257'
	 * ������jsonsplitΪ",�����ֵΪ��
	 * _dt_token_:"1518435257"
	 * @param jsonsplit
	 * @param request
	 * @return
	 */
	public String buildJsonDToken(String jsonsplit,HttpServletRequest request)
	{
		return buildDToken("json","'",request,null,true);
	}
	/**
	 * ����url����������
	 * ���ֵΪ��
	 * _dt_token_=1518435257
	 * @param request
	 * @return
	 */
	public String buildParameterDToken(HttpServletRequest request)
	{
		return buildDToken("param",null,request,null,true);
	}
	/**
	 * ֻ�������ƣ��������ַ�ʽ���ͻ��˱��뽫��token�Բ�����_dt_token_���ط���ˣ�����������
	 * ���ֵΪ��
	 * 1518435257
	 * @param request
	 * @return
	 */
	public String buildDToken(HttpServletRequest request)
	{
		return buildDToken("token",null,request,null,true);
	}
	
	public String buildDToken(String elementType,String jsonsplit,HttpServletRequest request,String fid,boolean cache)
	{
//		if(!this.enableToken)
//			return "";
		StringBuffer buffer = new StringBuffer();
		if(StringUtil.isEmpty(elementType) || elementType.equals("input"))
		{
			buffer.append("<input type=\"hidden\" name=\"").append(temptoken_param_name).append("\" value=\"").append(this.genToken(request,fid, cache)).append("\">");
		}
		else if(elementType.equals("json"))//json
		{
			buffer.append(temptoken_param_name).append(":").append(jsonsplit).append(this.genToken(request,fid,cache)).append(jsonsplit);
		}
		else if(elementType.equals("param"))//����
		{
			buffer.append(temptoken_param_name).append("=").append(this.genToken(request,fid,cache));
		}
		else if(elementType.equals("token"))//ֻ���token
		{
			buffer.append(this.genToken(request,fid,cache));
		}
		else
		{
			buffer.append("<input type=\"hidden\" name=\"").append(temptoken_param_name).append("\" value=\"").append(this.genToken(request,fid, cache)).append("\">");
		}
		return buffer.toString();
	}

	public boolean isEnableToken() {
		return enableToken;
	}
	
	public static void main(String[] args)
	{
		Map h = new HashMap();
		h.put("1", "1");
		h.put("2", "2");
		h.put("3", "3");
		Iterator it = h.keySet().iterator();
		List olds = new ArrayList();
		while(it.hasNext())
		{
			olds.add(it.next());			
		}
		
		for(int i = 0; i < olds.size(); i ++)
		{
			Object token = olds.get(i);
			h.remove(token);
		}
	}
	public void sendRedirect(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		this.tokenFilter.sendRedirect(request, response);
	}
	
	public void doDTokencheck(ServletRequest request,ServletResponse response) throws IOException, DTokenValidateFailedException
	{
		if(!assertDTokenSetted(request))
		{
			if(request instanceof HttpServletRequest)
			{
				sendRedirect((HttpServletRequest) request,(HttpServletResponse) response);
			}
			else
			{
				throw new DTokenValidateFailedException();
			}
		}
	}
	

}
