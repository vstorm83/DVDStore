/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.interceptor;

import java.util.List;
import java.util.Map;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

/**
 *
 * @author VU VIET PHUONG
 */
public class GetSessionIdInterceptor extends AbstractPhaseInterceptor<Message> {
    private static String sessionId;
    
    public GetSessionIdInterceptor() {
        super(Phase.INVOKE);
    }
    public void handleMessage(Message message) throws Fault {
        Map headers = (Map)message.get(Message.PROTOCOL_HEADERS);
        Object cookie = headers.get("Set-Cookie");
        if (cookie != null) {
            for (String value : (List<String>)cookie) {
                if (value.contains("JSESSIONID")) {
                    int startIdx = value.indexOf("=") + 1;
                    int endIdx = value.indexOf(" ", startIdx) - 1;
                    sessionId = value.substring(startIdx, endIdx);
                }
            }
        }
    }

    public static String getSessionId() {
        return sessionId;
    }

}
