/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.tag;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.homeip.dvdstore.web.ui.component.TopMenu;
import org.apache.struts2.components.Component;
import org.apache.struts2.views.jsp.ui.AbstractUITag;

/**
 *
 * @author VU VIET PHUONG
 */
public class TopMenuTag extends AbstractUITag {
    private String mainAction;
    private String mainNamespace;
    private String contactAction;
    private String contactNamespace;
    private String guideAction;
    private String guideNamespace;
    private String registerAction;
    private String registerNamespace;
    private String main;
    private String contact;
    private String guide;
    private String register;

    @Override
    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new TopMenu(stack, req, res);
    }

    @Override
    protected void populateParams() {
        super.populateParams();

        TopMenu menu = (TopMenu)getComponent();
        menu.setMain(main);
        menu.setMainAction(mainAction);
        menu.setMainNamespace(mainNamespace);
        menu.setContact(contact);
        menu.setContactAction(contactAction);
        menu.setContactNamespace(contactNamespace);
        menu.setGuide(guide);
        menu.setGuideAction(guideAction);
        menu.setGuideNamespace(guideNamespace);
        menu.setRegister(register);
        menu.setRegisterAction(registerAction);
        menu.setRegisterNamespace(registerNamespace);
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactAction() {
        return contactAction;
    }

    public void setContactAction(String contactAction) {
        this.contactAction = contactAction;
    }

    public String getContactNamespace() {
        return contactNamespace;
    }

    public void setContactNamespace(String contactNamespace) {
        this.contactNamespace = contactNamespace;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public String getGuideAction() {
        return guideAction;
    }

    public void setGuideAction(String guideAction) {
        this.guideAction = guideAction;
    }

    public String getGuideNamespace() {
        return guideNamespace;
    }

    public void setGuideNamespace(String guideNamespace) {
        this.guideNamespace = guideNamespace;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getMainAction() {
        return mainAction;
    }

    public void setMainAction(String mainAction) {
        this.mainAction = mainAction;
    }

    public String getMainNamespace() {
        return mainNamespace;
    }

    public void setMainNamespace(String mainNamespace) {
        this.mainNamespace = mainNamespace;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public String getRegisterAction() {
        return registerAction;
    }

    public void setRegisterAction(String registerAction) {
        this.registerAction = registerAction;
    }

    public String getRegisterNamespace() {
        return registerNamespace;
    }

    public void setRegisterNamespace(String registerNamespace) {
        this.registerNamespace = registerNamespace;
    }
}