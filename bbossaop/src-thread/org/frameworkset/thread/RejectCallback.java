package org.frameworkset.thread;
/** 
 * <p>��˵��:</p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: bboss group</p>
 * @author  gao.tang ,biaoping.yin
 * @version V1.0  ����ʱ�䣺Sep 11, 2009 6:02:25 PM 
 */
public abstract class RejectCallback {
    /**�����̵߳���ʱ��ʱ����*/
    int rejecttimes = 0;
	/**
     * ��ȡ���ܾ��Ĵ���
     */
    public int getRejectTimes()
    {
        /**�����̵߳���ʱ��ʱ����*/
        return rejecttimes;
    }
    public int increamentRejecttimes()
    {
        rejecttimes ++ ;
        return rejecttimes;
    }

}
