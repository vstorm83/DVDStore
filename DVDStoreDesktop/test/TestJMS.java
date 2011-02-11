
import java.io.IOException;
import java.util.Date;
import javax.jms.Destination;
import net.homeip.dvdstore.ApplicationContextUtil;
import net.homeip.dvdstore.pojo.web.vo.DeliveryInfo;
import net.homeip.dvdstore.pojo.webservice.vo.UserChangeVO;
import net.homeip.dvdstore.pojo.webservice.vo.UserVO;
import org.springframework.jms.core.JmsTemplate;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author VU VIET PHUONG
 */
public class TestJMS {

    public static void main(String[] args) throws IOException {
        JmsTemplate jmsTemplate =
                (JmsTemplate) ApplicationContextUtil.getApplicationContext().getBean("jmsTemplate");
        Destination userChangeTopic =
                (Destination) ApplicationContextUtil.getApplicationContext().getBean("userChangeTopic");
        Destination userRegisterTopic =
                (Destination) ApplicationContextUtil.getApplicationContext().getBean("userRegisterTopic");
        UserVO oldUserVO = new UserVO();
        oldUserVO.setUserId(24L);
        oldUserVO.setUserName("hummer83");
        UserVO newUserVO = new UserVO();
        newUserVO.setUserName("tét");
        UserChangeVO userChangeVO = new UserChangeVO();
        userChangeVO.setNewUserVO(newUserVO);
        userChangeVO.setOldUserVO(oldUserVO);

        int key;
        do {
            jmsTemplate.convertAndSend(userChangeTopic, userChangeVO);
            UserVO testNew = new UserVO();
            testNew.setUserId(1L);
            testNew.setUserName("Thêm mới");
            DeliveryInfo deliveryInfo = new DeliveryInfo();
            deliveryInfo.setAddress("");
            deliveryInfo.setEmail("");
            deliveryInfo.setFirstName("");
            deliveryInfo.setLastName("");
            deliveryInfo.setTel("");
            testNew.setDeliveryInfo(deliveryInfo);
            testNew.setDateCreated(new Date());
            jmsTemplate.convertAndSend(userRegisterTopic, testNew);
            key = System.in.read();            
        } while (key != 10);
        System.exit(0);
    }
}
