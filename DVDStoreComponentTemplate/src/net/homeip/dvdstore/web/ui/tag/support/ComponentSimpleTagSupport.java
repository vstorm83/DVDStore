/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.tag.support;

import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.util.ValueStack;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import org.apache.struts2.components.Component;
import org.apache.struts2.dispatcher.Dispatcher;

/**
 *
 * @author VU VIET PHUONG
 */
public abstract class ComponentSimpleTagSupport extends StrutsSimpleTagSupport {
    protected Component component;

    public abstract Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res);

    @Override
    public void doTag() throws JspException, IOException {
        component = getBean(getStack(), (HttpServletRequest) pageContext.getRequest(),
                (HttpServletResponse) pageContext.getResponse());
        Container container = Dispatcher.getInstance().getContainer();
        container.inject(component);

        populateParams();
        component.start(pageContext.getOut());

        component.end(pageContext.getOut(), getBody());
        component = null;
    }
    
    protected void populateParams() {
    }

    public Component getComponent() {
        return component;
    }
}
