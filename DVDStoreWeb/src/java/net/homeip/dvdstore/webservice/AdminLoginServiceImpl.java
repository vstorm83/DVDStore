/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.webservice;

// <editor-fold defaultstate="collapsed" desc="import">
import javax.jms.Destination;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import net.homeip.dvdstore.webservice.exception.LoginFailException;
import org.apache.cxf.jaxws.context.WebServiceContextImpl;
import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.Authentication;
import org.springframework.security.concurrent.SessionRegistry;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.ProviderManager;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;// </editor-fold>

/**
 *
 * @author VU VIET PHUONG
 */
@WebService(endpointInterface = "net.homeip.dvdstore.webservice.AdminLoginService")
public class AdminLoginServiceImpl implements AdminLoginService {

    private WebServiceContext webServiceContext = new WebServiceContextImpl();
    private Logger logger = Logger.getLogger(AdminLoginServiceImpl.class);
    private ProviderManager authenticationManager;
    private SessionRegistry adminSessionRegistry;
    private JmsTemplate jmsTemplate;
    private Destination dvdstoreLogoutTopic;

    @Override
    public void login(String userName, String password) throws LoginFailException {

        logger.trace("userName=" + userName + " password=" + password);
        logger.trace("Authentication=" + SecurityContextHolder.getContext().getAuthentication());

        authenticate(userName, password);
        concurrentControl(SecurityContextHolder.getContext().getAuthentication());

        logger.trace("Authentication=" + SecurityContextHolder.getContext().getAuthentication());
    }

    public void setAuthenticationManager(ProviderManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    private void authenticate(String userName, String password) {
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(userName, password);

        try {
            Authentication authentication = authenticationManager.authenticate(authRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception ex) {
            throw new LoginFailException("Không đăng nhập được");
        }
    }

    private void concurrentControl(Authentication authentication) {
//        System.setProperty("javax.net.ssl.keyStore", ClassLoader.getSystemResource("client.ks").getPath());
//            System.setProperty("javax.net.ssl.keyStorePassword", "password");
//            System.setProperty("javax.net.ssl.trustStore", ClassLoader.getSystemResource("client.ts").getPath());
        Object[] principalList = adminSessionRegistry.getAllPrincipals();
        if (principalList != null && principalList.length > 0) {
            for (Object principal : principalList) {
                String oldSessionId = adminSessionRegistry.getAllSessions(principal, true)[0].getSessionId();
                sendKickMessage(oldSessionId);
                adminSessionRegistry.removeSessionInformation(oldSessionId);
                logger.trace("kick sessionid=" + oldSessionId);
            }
        }
        HttpServletRequest request = (HttpServletRequest) webServiceContext.getMessageContext().get(MessageContext.SERVLET_REQUEST);
        adminSessionRegistry.registerNewSession(request.getSession().getId(), authentication.getPrincipal());
        logger.trace("register new sessionId=" + request.getSession().getId());
    }

    private void sendKickMessage(final String oldSessionId) {
        jmsTemplate.convertAndSend(dvdstoreLogoutTopic, oldSessionId);
//        Thread jmsThread = new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                try {
//
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//        });
//        jmsThread.start();
    }

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void setAdminSessionRegistry(SessionRegistry adminSessionRegistry) {
        this.adminSessionRegistry = adminSessionRegistry;
    }

    public void setDvdstoreLogoutTopic(Destination dvdstoreLogoutTopic) {
        this.dvdstoreLogoutTopic = dvdstoreLogoutTopic;
    }
}
