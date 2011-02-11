/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.util;

import java.util.regex.Pattern;

/**
 *
 * @author VU VIET PHUONG
 */
public class ValidatorUtil {
    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])+((\\.com)|(\\.net)|(\\.org)|(\\.info)|(\\.edu)|(\\.mil)|(\\.gov)|(\\.biz)|(\\.ws)|(\\.us)|(\\.tv)|(\\.cc)|(\\.aero)|(\\.arpa)|(\\.coop)|(\\.int)|(\\.jobs)|(\\.museum)|(\\.name)|(\\.pro)|(\\.travel)|(\\.nato)|(\\..{2,3})|(\\..{2,3}\\..{2,3}))$";
    public static boolean isEmpty(String text) {
        return isEmpty(text, true);
    }
    public static boolean isEmpty(String text, boolean isTrimed) {        
        return text == null || (isTrimed?text.trim():text).equals("");
    }
    public static boolean isInvalidLength(String text,int min, int max) {
        return isInvalidLength(text, min, max, true);
    }
    public static boolean isInvalidLength(String text,int min, int max, boolean isTrimed) {
        if (text == null) {
            return true;
        }
        String tx = isTrimed?TextUtil.normalize(text):text;
        return tx.length() < min || tx.length() > max;
    }
    public static boolean isInvalidEmail(String email) {
        return !Pattern.matches(EMAIL_PATTERN, email.trim());
    }
}
