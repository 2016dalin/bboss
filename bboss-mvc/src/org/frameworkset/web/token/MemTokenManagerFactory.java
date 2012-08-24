package org.frameworkset.web.token;

public class MemTokenManagerFactory {
	private static MemTokenManager memTokenManager;
	/**
	 * used by TokenFilter
	 * @param dualtime
	 * @return
	 */
	public static MemTokenManager getMemTokenManager(long dualtime,long tokenscaninterval,boolean enableToken,String tokenstore,TokenFilter tokenFilter)
	{
		if(memTokenManager == null)
		{
			synchronized(MemTokenManager.class)
			{
				if(memTokenManager == null)
					memTokenManager = new MemTokenManager(dualtime,tokenscaninterval,enableToken,tokenstore, tokenFilter);
			}
		}
		return memTokenManager;
	}
	public static MemTokenManager getMemTokenManager()
	{
		if(memTokenManager == null)
		{
			throw new RuntimeException("MemTokenManager not build corrected using getMemTokenManager(long dualtime)");
		}
		return memTokenManager;
	}
	/**
	 * ���û�п�����̬���Ƽ����ƣ�����null�������˾ͷ������ƹ���������ϵͳ���ÿ������Ƽ����ƣ���ö�����TokenFilter�н��г�ʼ�������򲻻��ʼ����
	 * @return
	 */
	public static MemTokenManager getMemTokenManagerNoexception()
	{
		return memTokenManager;
	}

}
