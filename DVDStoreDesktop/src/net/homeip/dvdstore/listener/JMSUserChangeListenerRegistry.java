/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.listener;

import java.awt.EventQueue;
import java.util.LinkedList;
import java.util.List;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import net.homeip.dvdstore.ApplicationContextUtil;
import net.homeip.dvdstore.listener.event.UserChangeEvent;
import net.homeip.dvdstore.pojo.webservice.vo.UserChangeVO;
import org.springframework.jms.listener.SimpleMessageListenerContainer;

/**
 *
 * @author VU VIET PHUONG
 */
public class JMSUserChangeListenerRegistry implements MessageListener {

    private List<UserChangeListener> listeners = new LinkedList<UserChangeListener>();
    private SimpleMessageListenerContainer userChangeMessageListenerContainer;

    public void addListener(UserChangeListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("can't add null listener");
        }
        if (listeners.contains(listener)) {
            return;
        }
        listeners.add(listener);

        if (listeners.size() == 1) {
            startListenToJMS();
        }
    }

    public void removeListener(UserChangeListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("can't add null listener");
        }
        listeners.remove(listener);

        if (listeners.size() == 0) {
            stopListenToJMS();
        }
    }

    public List<UserChangeListener> getListeners() {
        return listeners;
    }

    private void startListenToJMS() {
        try {
            userChangeMessageListenerContainer =
                    (SimpleMessageListenerContainer) ApplicationContextUtil.getApplicationContext().getBean(
                    "userChangeMessageListenerContainer");
            userChangeMessageListenerContainer.setMessageListener(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void stopListenToJMS() {
        userChangeMessageListenerContainer.shutdown();
    }

    public void onMessage(Message msg) {
        try {
            UserChangeVO userChangeVO = (UserChangeVO) ((ObjectMessage) msg).getObject();
            final UserChangeEvent evt = new UserChangeEvent(this);
            evt.setNewUserVO(userChangeVO.getNewUserVO());
            evt.setOldUserVO(userChangeVO.getOldUserVO());
            EventQueue.invokeLater(new Runnable() {

                public void run() {
                    for (UserChangeListener listener : listeners) {
                        listener.onUserChange(evt);
                    }
                }
            });
        } catch (JMSException ex) {
            ex.printStackTrace();
        }
    }    
}
