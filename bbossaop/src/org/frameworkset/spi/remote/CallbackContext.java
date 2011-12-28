package org.frameworkset.spi.remote;

import java.util.Map;

/**
 * 
 * <p>
 * Title: CallbackContext.java
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * bboss workgroup
 * </p>
 * <p>
 * Copyright (c) 2007
 * </p>
 * 
 * @Date 2009-6-1 ����08:58:51
 * @author biaoping.yin
 * @version 1.0
 */
public class CallbackContext implements java.io.Serializable {
    /**
     * ȷ���������ص��ĵ�ַ���ɵ��ÿͻ���ȷ��
     */
    private Target callbackTarget;
    /**
     * �ص������Ĳ������ɷ������������¼�������
     */
    private Map callbackParameters;

    public Target getCallbackTarget() {
        return callbackTarget;
    }

    public Map getCallbackParameters() {
        return callbackParameters;
    }

    public CallbackContext(Map callbackParameters, Target callbackTarget) {
        super();
        this.callbackParameters = callbackParameters;
        this.callbackTarget = callbackTarget;
    }

}
