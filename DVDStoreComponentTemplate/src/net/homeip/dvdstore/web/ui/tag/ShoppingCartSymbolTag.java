/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.tag;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.homeip.dvdstore.web.ui.component.ShoppingCartSymbol;
import org.apache.struts2.components.Component;

/**
 *
 * @author VU VIET PHUONG
 */
public class ShoppingCartSymbolTag extends ActionUITag {
    private String shoppingCartKey;
    private String itemCount;
    private String unit;
    private String shoppingCartButtonKey;

    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new ShoppingCartSymbol(stack, req, res);
    }

    @Override
    protected void populateParams() {
        super.populateParams();

        ShoppingCartSymbol symbol = (ShoppingCartSymbol)getComponent();
        symbol.setShoppingCartKey(shoppingCartKey);
        symbol.setShoppingCartButtonKey(shoppingCartButtonKey);
        symbol.setItemCount(itemCount);
        symbol.setUnit(unit);
    }

    public String getItemCount() {
        return itemCount;
    }

    public void setItemCount(String itemCount) {
        this.itemCount = itemCount;
    }

    public String getShoppingCartButtonKey() {
        return shoppingCartButtonKey;
    }

    public void setShoppingCartButtonKey(String shoppingCartButtonKey) {
        this.shoppingCartButtonKey = shoppingCartButtonKey;
    }

    public String getShoppingCartKey() {
        return shoppingCartKey;
    }

    public void setShoppingCartKey(String shoppingCartKey) {
        this.shoppingCartKey = shoppingCartKey;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}