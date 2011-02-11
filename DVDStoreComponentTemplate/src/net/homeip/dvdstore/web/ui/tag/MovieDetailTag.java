/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.homeip.dvdstore.web.ui.tag;

import com.opensymphony.xwork2.util.ValueStack;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.homeip.dvdstore.web.ui.component.MovieDetailCell;
import org.apache.struts2.components.Component;

/**
 *
 * @author VU VIET PHUONG
 */
public class MovieDetailTag extends MovieCellTag {
    private String placeOrderButtonKey;
    private String directorKey;
    private String actorKey;
    private String movieCatgoryKey;
    private String productPriceKey;
    private String saleOffKey;
    private String moneyUnit;
    private String productDescription;

    @Override
    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new MovieDetailCell(stack, req, res);
    }

    @Override
    protected void populateParams() {
        super.populateParams();

        MovieDetailCell cell = (MovieDetailCell)getComponent();
        cell.setPlaceOrderButtonKey(placeOrderButtonKey);
        cell.setDirectorKey(directorKey);
        cell.setActorKey(actorKey);
        cell.setMovieCatgoryKey(movieCatgoryKey);
        cell.setProductPriceKey(productPriceKey);
        cell.setSaleOffKey(saleOffKey);
        cell.setMoneyUnit(moneyUnit);
        cell.setProductDescription(productDescription);
    }

    public String getActorKey() {
        return actorKey;
    }

    public void setActorKey(String actorKey) {
        this.actorKey = actorKey;
    }

    public String getDirectorKey() {
        return directorKey;
    }

    public void setDirectorKey(String directorKey) {
        this.directorKey = directorKey;
    }

    public String getMoneyUnit() {
        return moneyUnit;
    }

    public void setMoneyUnit(String moneyUnit) {
        this.moneyUnit = moneyUnit;
    }

    public String getMovieCatgoryKey() {
        return movieCatgoryKey;
    }

    public void setMovieCatgoryKey(String movieCatgoryKey) {
        this.movieCatgoryKey = movieCatgoryKey;
    }

    public String getPlaceOrderButtonKey() {
        return placeOrderButtonKey;
    }

    public void setPlaceOrderButtonKey(String placeOrderButtonKey) {
        this.placeOrderButtonKey = placeOrderButtonKey;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductPriceKey() {
        return productPriceKey;
    }

    public void setProductPriceKey(String productPriceKey) {
        this.productPriceKey = productPriceKey;
    }

    public String getSaleOffKey() {
        return saleOffKey;
    }

    public void setSaleOffKey(String saleOffKey) {
        this.saleOffKey = saleOffKey;
    }

}