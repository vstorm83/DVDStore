
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import net.homeip.dvdstore.pojo.User;
import net.homeip.dvdstore.pojo.web.vo.DeliveryInfo;
import net.homeip.dvdstore.util.TextUtil;
import net.sf.jasperreports.engine.JRException;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author VU VIET PHUONG
 */
public class TestQBE {

//    public static void main(String[] args) throws JRException {
//        String userNameSearch = "hummer83";
//        GregorianCalendar startDateSearch = new GregorianCalendar(2000, 1, 18);
//        GregorianCalendar endDateSearch = new GregorianCalendar(2010, 1, 18);
//        List<User> users = findUser(userNameSearch, null, "", "", "", "", startDateSearch, endDateSearch);
//        System.out.println(users.size());
//        DateFormat dateFormat = DateFormat.getDateInstance();
//        System.out.println(dateFormat.format(users.get(0).getDateCreated()));
//    }

//    public static List<User> findUser(String userNameSearch, String firstNameSearch,
//            String lastNameSearch, String addressSearch, String telSearch,
//            String emailSearch, final GregorianCalendar startDateSearch, final GregorianCalendar endDateSearch) {
//        HibernateTemplate hibernateTemplate =
//                (HibernateTemplate) ApplicationContextUtil.getApplicationContext().getBean(
//                "hibernateTemplate");
//        User user = new User();
//        user.setUserName(TextUtil.normalize(userNameSearch));
//        user.setDeliveryInfo(new DeliveryInfo(TextUtil.normalize(firstNameSearch),
//                TextUtil.normalize(lastNameSearch), TextUtil.normalize(addressSearch),
//                TextUtil.normalize(telSearch), TextUtil.normalize(emailSearch)));
//
//        final Example example = Example.create(user).excludeZeroes().excludeProperty(
//                "password").enableLike(MatchMode.ANYWHERE).ignoreCase();
//        makeSearchDate(startDateSearch);
//        makeSearchDate(endDateSearch);
//        endDateSearch.add(Calendar.DATE, 1);
//
//        List<User> users = hibernateTemplate.executeFind(new HibernateCallback() {
//
//            @Override
//            public Object doInHibernate(Session session) throws HibernateException, SQLException {
//
//                return session.createCriteria(User.class).add(example)
//                        .add(Restrictions.and(
//                        Restrictions.ge("dateCreated", startDateSearch.getTime()),
//                        Restrictions.lt("dateCreated", endDateSearch.getTime())))
//                        .list();
//            }
//        });
//        return users;
//    }

    private static void makeSearchDate(GregorianCalendar dt) {
        dt.set(Calendar.HOUR, 0);
        dt.set(Calendar.MINUTE, 0);
        dt.set(Calendar.SECOND, 0);
        dt.set(Calendar.MILLISECOND, 0);
    }
}
