package org.frameworkset.thread;


/**
 * 
 * <p>Title: WaitFailHandler.java</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright (c) 2009</p>
 *
 * <p>bboss workgroup</p>
 * @Date 2009-7-22
 * @author biaoping.yin
 * @version 1.0
 */
public interface WaitFailHandler<T>
{
    /**
     * �ȴ�����ʧ�ܺ󣬶Ա�����������ִ�����⴦����
     * @param r
     */
    public void failhandler(T r);
    
}
