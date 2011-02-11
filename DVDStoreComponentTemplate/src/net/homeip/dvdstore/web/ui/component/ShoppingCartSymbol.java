/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.component;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.util.TextProviderHelper;
import org.apache.struts2.views.annotations.StrutsTag;

/**
 *
 * @author VU VIET PHUONG
 */
@StrutsTag(
    name="ShoppingCartSymbolTag",
    tldTagClass="net.homeip.dvdstore.web.ui.tag.ShoppingCartSymbolTag",
    description="Render an HTML ShoppingCartSymbol",
    allowDynamicAttributes=true)
public class ShoppingCartSymbol extends ActionUIBean {
    /**
     * The name of the default template for the ShoppingCartSymbolTag
     */
    private String shoppingCartKey;
    private String itemCount;
    private String unit;
    private String shoppingCartButtonKey;

    public ShoppingCartSymbol(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    @Override
    protected String getDefaultTemplate() {
        return "ShoppingCartSymbol";
    }

    @Override
    protected void evaluateExtraParams() {
        super.evaluateExtraParams();

        if (shoppingCartKey != null) {
            addParameter("shoppingCartLabel", TextProviderHelper.getText(shoppingCartKey, "", stack));
        }
        if (itemCount != null) {
            addParameter("itemCount", findString(itemCount));
        }
        if (unit != null) {
            addParameter("unit", TextProviderHelper.getText(unit, "", stack));
        }
        if (shoppingCartButtonKey != null) {
            addParameter("shoppingCartButtonLabel",
                        TextProviderHelper.getText(shoppingCartButtonKey, "", stack));
        }
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
