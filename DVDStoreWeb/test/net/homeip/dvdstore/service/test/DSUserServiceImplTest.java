/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.service.test;

import junit.framework.Assert;
import net.homeip.dvdstore.dao.UserDAO;
import net.homeip.dvdstore.pojo.User;
import net.homeip.dvdstore.service.DSUserServiceImpl;
import net.homeip.dvdstore.service.exception.EmailDuplicateException;
import net.homeip.dvdstore.service.exception.InvalidInputException;
import net.homeip.dvdstore.service.exception.UserNameDuplicateException;
import net.homeip.dvdstore.pojo.web.vo.UserVO;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.providers.encoding.Md5PasswordEncoder;

/**
 *
 * @author VU VIET PHUONG
 */
@RunWith(JMock.class)
public class DSUserServiceImplTest {

    private Mockery context;
    private DSUserServiceImpl userService;
    private UserDAO userDAO;
    private User user;
    private Md5PasswordEncoder pwdEncoder;
    private UserVO userVO = new UserVO();

    @Before
    public void setUp() throws Exception {
        context = new JUnit4Mockery();
        context.setImposteriser(ClassImposteriser.INSTANCE);
        userDAO = context.mock(UserDAO.class);
        user = context.mock(User.class);
        pwdEncoder = new Md5PasswordEncoder();

        userService = new DSUserServiceImpl();
        userService.setUserDAO(userDAO);
    }

    @Test
    public void testRegister() {
        testRegister_good();
        testRegister_userNameDup();
        testRegister_emailDub();
        testRegister_invalidInput();
    }

    @Test
    public void testIsEmailExists() {
        final String email = "email";
        context.checking(new Expectations() {
            {
                oneOf(userDAO).getUserIdByEmail(email);
                will(returnValue(new Long(0)));
            }
        });
        Assert.assertTrue("not true", userService.isEmailExists(email));
        context.checking(new Expectations() {
            {
                oneOf(userDAO).getUserIdByEmail(email);
                will(returnValue(null));
            }
        });
        Assert.assertFalse("not false", userService.isEmailExists(email));
    }

    @Test
    public void testIsUserNameExists() {
        final String userName = "họ và tên";
        context.checking(new Expectations() {

            {
                oneOf(userDAO).getUserIdByUserName(userName);
                will(returnValue(new Long(0)));
            }
        });
        Assert.assertTrue("not true", userService.isUserNameExists(userName));
        context.checking(new Expectations() {
            {
                oneOf(userDAO).getUserIdByUserName(userName);
                will(returnValue(null));
            }
        });
        Assert.assertFalse("not false", userService.isUserNameExists(userName));
    }

    private void testRegister_good() {
        context.checking(new Expectations() {
            {
                oneOf(userDAO).createNewUser();
                will(returnValue(user));
                oneOf(user).register(userVO, userDAO, pwdEncoder);
                oneOf(userDAO).saveUser(user);
            }
        });
        userService.register(userVO);
    }

    private void testRegister_userNameDup() {
        final UserNameDuplicateException userNameEx = new UserNameDuplicateException();
        context.checking(new Expectations() {
            {
                oneOf(userDAO).createNewUser();
                will(returnValue(user));
                oneOf(user).register(userVO, userDAO, pwdEncoder);
                will(throwException(userNameEx));
            }
        });
        try {
            userService.register(userVO);
        } catch (UserNameDuplicateException ex) {
            Assert.assertSame(ex, userNameEx);
        }
    }

    private void testRegister_emailDub() {
        final EmailDuplicateException emailEx = new EmailDuplicateException();
        context.checking(new Expectations() {
            {
                oneOf(userDAO).createNewUser();
                will(returnValue(user));
                oneOf(user).register(userVO, userDAO, pwdEncoder);
                will(throwException(emailEx));
            }
        });
        try {
            userService.register(userVO);
        } catch (EmailDuplicateException ex) {
            Assert.assertSame(ex, emailEx);
        }
    }

    private void testRegister_invalidInput() {
        final InvalidInputException inputEx = new InvalidInputException();
        
        context.checking(new Expectations() {
            {
                oneOf(userDAO).createNewUser();
                will(returnValue(user));
                oneOf(user).register(userVO, userDAO, pwdEncoder);
                will(throwException(inputEx));
            }
        });
        try {
            userService.register(userVO);
        } catch (InvalidInputException ex) {
            Assert.assertSame(ex, inputEx);
        }
    }
}
