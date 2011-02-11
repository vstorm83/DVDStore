/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.tag.support;

import net.homeip.dvdstore.web.ui.component.list.SimpleListUIBean;

/**
 *
 * @author VU VIET PHUONG
 */
public abstract class AbstractSimpleListTag extends AbstractSimpleUITag {
    protected String list;

    @Override
    protected void populateParams() {
        super.populateParams();

        SimpleListUIBean uiBean = ((SimpleListUIBean) component);
        uiBean.setList(list);
        uiBean.setCellTemplate(getJspBody());
    }

    public void setList(String list) {
        this.list = list;
    }
}