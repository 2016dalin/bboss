package org.frameworkset.rpc;

import org.frameworkset.event.ExampleListener;
import org.frameworkset.spi.remote.RunAop;

public class Run {

    public static void main(String[] args)
    {
        ExampleListener listener = new ExampleListener();
        //����init����ע������������������յ��¼��������������¼�
        listener.init();
        RunAop.main(null);
    }
}
