<%-- 
    Document   : PlaceOrderConfirm
    Created on : Feb 15, 2010, 4:58:00 PM
    Author     : VU VIET PHUONG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="dvdstore" uri="/dvdstore-tags" %>
<dvdstore:CenterWindow itemTitle="itemTitle.PlaceOrderConfirm" >
    <s:form theme="simple">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td height="5"></td>
            </tr>
            <tr>
                <td>
                    <dvdstore:ShoppingCartDeliveryInfo addressKey="deliveryInfo.address"
                                                       telKey="deliveryInfo.tel"
                                                       emailKey="deliveryInfo.email"
                                                       paymentMethodKey="paymentMethod"
                                                       theme="defaultTheme" confirmForm="true"/>
                </td>
            </tr>
            <tr>
                <td height="15"></td>
            </tr>
            <tr>
                <td>
                    <dvdstore:ShoppingCartItemTable list="items" theme="defaultTheme" confirmForm="true"/>
                </td>
            </tr>
            <tr>
                <td height="5"></td>
            </tr>
            <tr>
                <td>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td align="left">
                                <s:submit action="PlaceOrder_form" namespace="/movie"
                                          value="Quay về giỏ hàng" theme="simple"/>
                            </td>
                            <td align="right">
                                <s:submit action="PlaceOrder_doPayment" namespace="/movie"
                                          value="Đặt hàng" theme="simple"/>
                            </td>                            
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </s:form>
</dvdstore:CenterWindow>

