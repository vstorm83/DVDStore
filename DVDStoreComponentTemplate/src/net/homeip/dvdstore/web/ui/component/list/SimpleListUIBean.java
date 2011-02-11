/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.component.list;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.tagext.JspFragment;
import org.apache.struts2.components.ListUIBean;

/**
 *
 * @author VU VIET PHUONG
 */
public abstract class SimpleListUIBean extends ListUIBean {
    private JspFragment cellTemplate;

    public SimpleListUIBean(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    @Override
    public void evaluateExtraParams() {
        super.evaluateExtraParams();

        if (cellTemplate != null) {
            addParameter("cellTemplate", cellTemplate);
        }
    }

    public JspFragment getCellTemplate() {
        return cellTemplate;
    }

    public void setCellTemplate(JspFragment cellTemplate) {
        this.cellTemplate = cellTemplate;
    }
}