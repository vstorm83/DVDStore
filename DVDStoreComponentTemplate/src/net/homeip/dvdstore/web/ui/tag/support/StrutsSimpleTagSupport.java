/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.tag.support;

import com.opensymphony.xwork2.util.TextParseUtil;
import com.opensymphony.xwork2.util.ValueStack;
import java.io.PrintWriter;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import org.apache.struts2.components.Component;
import org.apache.struts2.util.FastByteArrayOutputStream;
import org.apache.struts2.views.jsp.TagUtils;

/**
 *
 * @author VU VIET PHUONG
 */
public class StrutsSimpleTagSupport extends SimpleTagSupport {
    protected PageContext pageContext;

    protected ValueStack getStack() {
        return TagUtils.getStack(pageContext);
    }

    protected String findString(String expr) {
        return (String) findValue(expr, String.class);
    }

    protected Object findValue(String expr) {
    	expr = Component.stripExpressionIfAltSyntax(getStack(), expr);

        return getStack().findValue(expr);
    }

    protected Object findValue(String expr, Class toType) {
        if (Component.altSyntax(getStack()) && toType == String.class) {
        	return TextParseUtil.translateVariables('%', expr, getStack());
            //return translateVariables(expr, getStack());
        } else {
        	expr = Component.stripExpressionIfAltSyntax(getStack(), expr);

            return getStack().findValue(expr, toType);
        }
    }

    protected String toString(Throwable t) {
        FastByteArrayOutputStream bout = new FastByteArrayOutputStream();
        PrintWriter wrt = new PrintWriter(bout);
        t.printStackTrace(wrt);
        wrt.close();

        return bout.toString();
    }

    protected String getBody() {
        return "";
    }

    @Override
    public void setJspContext(JspContext pc) {
        super.setJspContext(pc);
        pageContext = (PageContext)getJspContext();
    }
}
