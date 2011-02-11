/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.interceptor;

import java.util.ArrayList;
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
public class PutSessionIdInterceptor extends AbstractPhaseInterceptor<Message> {

    public PutSessionIdInterceptor() {
        super(Phase.SEND);
    }

    public void handleMessage(Message message) throws Fault {
        Map headers = (Map)message.get(Message.PROTOCOL_HEADERS);
        String sessionID = GetSessionIdInterceptor.getSessionId();
        if (sessionID != null) {            
            List<String> cookie = new ArrayList<String>();
            cookie.add("$Version='1'");
            cookie.add("JSESSIONID=" + sessionID);
            cookie.add("$Path=/DVDStoreWeb");
            headers.put("Cookie", cookie);
        }
    }

}
