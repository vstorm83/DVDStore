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
    name="MovieDetailCellTag",
    tldTagClass="net.homeip.dvdstore.web.ui.tag.MovieDetailCellTag",
    description="Render an HTML cell of movie",
    allowDynamicAttributes=true)
public class MovieDetailCell extends MovieCell {
    /**
     * The name of the default template for the MovieDetailCellTag
     */
    private String placeOrderButtonKey;
    private String directorKey;
    private String actorKey;
    private String movieCatgoryKey;
    private String productPriceKey;
    private String saleOffKey;
    private String moneyUnit;
    private String productDescription;

    public MovieDetailCell(ValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    @Override
    protected String getDefaultTemplate() {
        return "MovieDetailCell";
    }

    @Override
    protected void evaluateExtraParams() {
        super.evaluateExtraParams();

        if (placeOrderButtonKey != null) {
            addParameter("placeOrderButtonLabel",
                    TextProviderHelper.getText(placeOrderButtonKey, "", stack));
        }
        if (actorKey != null) {
            addParameter("actorKey", findString(actorKey));
            addParameter("actorLabel", TextProviderHelper.getText(actorKey, "", stack));
        }
        if (directorKey != null) {
            addParameter("directorKey", findString(directorKey));
            addParameter("directorLabel", TextProviderHelper.getText(directorKey, "", stack));
        }
        if (movieCatgoryKey != null) {
            addParameter("movieCatgoryKey", findString(movieCatgoryKey));
            addParameter("movieCatgoryLabel", TextProviderHelper.getText(movieCatgoryKey, "", stack));
        }
        if (productPriceKey != null) {
            addParameter("productPriceKey", findString(productPriceKey));
            addParameter("productPriceLabel", TextProviderHelper.getText(productPriceKey, "", stack));
        }
        if (saleOffKey != null) {
            addParameter("saleOffKey", findString(saleOffKey));
            addParameter("saleOffLabel", TextProviderHelper.getText(saleOffKey, "", stack));
        }
        if (moneyUnit != null) {
            addParameter("moneyUnit", TextProviderHelper.getText(moneyUnit, "", stack));
        }
        if (productDescription != null) {
            addParameter("productDescription", findString(productDescription));
        }
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