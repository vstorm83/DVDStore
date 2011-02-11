/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.homeip.dvdstore.struts.action.movie;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import net.homeip.dvdstore.struts.action.DSActionSupport;
import net.homeip.dvdstore.pojo.web.vo.ShoppingCartVO;
import net.homeip.dvdstore.service.exception.InvalidInputException;

/**
 *
 * @author VU VIET PHUONG
 */
public class PlaceOrderAction extends DSActionSupport implements
        ModelDriven<ShoppingCartVO>, Preparable {

    private ShoppingCartVO shoppingCartVO;
    private long movId;

    public String add() {
        getPlaceOrderService().addItem(movId);
        updateModel(getPlaceOrderService().getShoppingCart());
        return INPUT;
    }

    public String form() {
        return INPUT;
    }

    public String update() {
        updateShoppingCart();
        return INPUT;
    }

    public String confirm() {
        updateShoppingCart();
        if (getShoppingCartItemCount() == 0) {
            addActionError(getText("shoppingCart.empty"));
            return INPUT;
        }
        return "confirm";
    }

    public String doPayment() {
        getPlaceOrderService().sendOrder();
        prepareShoppingCartSymbol();
        return SUCCESS;
    }

    private void updateModel(ShoppingCartVO vo) {
        getModel().setDeliveryInfo(vo.getDeliveryInfo());
        getModel().setItems(vo.getItems());

        //Do shoppingcartsymbol đã prepare trong DSActionSupport trc
        //nên phải update lại
        prepareShoppingCartSymbol();
    }

    private void updateShoppingCart() {
        try {
            getPlaceOrderService().updateShoppingCart(shoppingCartVO);
        } catch (InvalidInputException inEx) {
            addFieldError(inEx.getMessage(), getText(inEx.getMessage() + "." + "invalid"));
            return;
        }
        updateModel(getPlaceOrderService().getShoppingCart());
    }

    @Override
    public ShoppingCartVO getModel() {
        return shoppingCartVO;
    }

    @Override
    public void prepare() throws Exception {
        shoppingCartVO = getPlaceOrderService().getShoppingCart();
    }

    public void setMovId(long movId) {
        this.movId = movId;
    }
}
