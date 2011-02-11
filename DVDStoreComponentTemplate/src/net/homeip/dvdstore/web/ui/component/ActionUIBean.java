/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.web.ui.component;

import com.opensymphony.xwork2.util.ValueStack;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.components.UIBean;
import org.apache.struts2.util.TokenHelper;
import org.apache.struts2.views.annotations.StrutsTagAttribute;

/**
 *
 * @author VU VIET PHUONG
 */
public abstract class ActionUIBean extends UIBean {
    private String action;
    private String namespace;

    public ActionUIBean(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    @Override
    protected void evaluateExtraParams() {
        super.evaluateExtraParams();

        if (action != null) {
            addParameter("action", findString(action));
        }
        if (namespace != null) {
            addParameter("namespace", findString(namespace));
        }

        String tokenName = TokenHelper.DEFAULT_TOKEN_NAME;

        addParameter("tokenName", tokenName);
        String token = buildToken(tokenName);
        addParameter("token", token);
        addParameter("tokenNameField", TokenHelper.TOKEN_NAME_FIELD);
    }
    public String getAction() {
        return action;
    }

    @StrutsTagAttribute(description="action attribute", type="String",
                required=false, rtexprvalue=false, name="action")
    public void setAction(String action) {
        this.action = action;
    }
    public String getNamespace() {
        return namespace;
    }

    @StrutsTagAttribute(description="Set namespace", type="String",
            required=false, rtexprvalue=false, name="namespace")
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    private String buildToken(String name) {
        Map context = stack.getContext();
        Object myToken = context.get(name);

        if (myToken == null) {
            myToken = TokenHelper.setToken(name);
            context.put(name, myToken);
        }

        return myToken.toString();
    }
}
