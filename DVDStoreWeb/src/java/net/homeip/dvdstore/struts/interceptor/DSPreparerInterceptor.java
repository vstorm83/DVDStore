/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.struts.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import net.homeip.dvdstore.struts.action.DSPreparer;

/**
 *
 * @author VU VIET PHUONG
 */
public class DSPreparerInterceptor implements Interceptor {

    @Override
    public void destroy() {
    }

    @Override
    public void init() {
    }

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        Object action = invocation.getAction();
        if (action instanceof DSPreparer) {
            ((DSPreparer)action).prepareDS();
        }
        return invocation.invoke();
    }

}
