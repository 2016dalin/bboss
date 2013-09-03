package org.frameworkset.web.socket;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.logging.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WsOutbound;

/**
 * Tomcat7 WebSocket�ĺ��Α���, WebSocket��servlet�ӿڼ�����webSocketServlet,ʵ��ΪSerlet
 * <p>��������,�ò��ֱ��������ľ�Ž�β��<p>
 *
 * ��������  2013-7-21<br>
 * @author  longgangbai <br>
 * @version $Revision$ $Date$
 * @since   3.0.0
 */
public class WebSocketServlet extends 
        org.apache.catalina.websocket.WebSocketServlet { 
    //
    private Logger logger=Logger.getLogger(WebSocketServlet.class.getSimpleName());
    
    private static final long serialVersionUID = 1L;

    
    @Override
    protected StreamInbound createWebSocketInbound(String subProtocol, HttpServletRequest arg1) {
        logger.info(";request ws servelt");
        
        return  new MessageInbound(){
            @Override
            protected void onClose(int status){
                logger.info(";web socket closed :"+status);
            }
            @Override
            protected void onOpen(WsOutbound outbound){
                logger.info(";web socket onOpen !");
            }
            @Override
            protected void onBinaryMessage(ByteBuffer buff) throws IOException {
                // TODO Auto-generated method stub
                logger.info(";web socket Received : !"+buff.remaining());
            }

            @Override
            protected void onTextMessage(CharBuffer buff) throws IOException {
            	
//                logger.info(";web socket Received : !"+buff.remaining());
            	String msg = buff.toString();
                logger.info("buff.toString();"+msg);
                //getWsOutbound���Է��ص�ǰ��WsOutbound,ͨ������ͻ��˷�������,�������nio��CharBuffer
//                for (int j = 0; j < 50; j++) 
//                {
//                    try {
//                        Thread.sleep(2000);
//                        this.getWsOutbound().writeTextMessage(CharBuffer.wrap("���"));
//                    } catch (InterruptedException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }
                this.getWsOutbound().writeTextMessage(CharBuffer.wrap("���:"+msg));
                
            }
            
        }; 
    } 

} 
