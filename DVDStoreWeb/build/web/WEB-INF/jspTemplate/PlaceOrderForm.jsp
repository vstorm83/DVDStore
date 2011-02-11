<%-- 
    Document   : PlaceOrderForm
    Created on : Feb 15, 2010, 1:36:50 PM
    Author     : VU VIET PHUONG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="dvdstore" uri="/dvdstore-tags" %>
<dvdstore:CenterWindow itemTitle="itemTitle.ShoppingCart" >
    <s:form theme="simple">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
            <tr>
                <td height="5"></td>
            </tr>
            <tr>
                <td align="left">Anh/Chị: <s:property value="deliveryInfo.firstName" />
                    <s:property value="deliveryInfo.lastName" />
                </td>
            </tr>
            <tr>
                <td height="10"></td>
            </tr>
            <tr>
                <td><s:actionerror /></td>
            </tr>
            <tr>
                <td>
                    <dvdstore:ShoppingCartItemTable list="items" theme="defaultTheme"/>
                </td>
            </tr>
            <tr>
                <td height="3"></td>
            </tr>
            <tr>
                <td align="right">
                    <s:submit theme="simple" action="PlaceOrder_update" namespace="/movie"
                    value="Cập nhật giỏ hàng" />
                </td>
            </tr>
            <tr>
                <td height="5"></td>
            </tr>
            <tr>
                <td>
                    <dvdstore:ShoppingCartDeliveryInfo addressKey="deliveryInfo.address"
                                                       telKey="deliveryInfo.tel"
                                                       emailKey="deliveryInfo.email"
                                                       paymentMethodKey="paymentMethod"
                                                       theme="defaultTheme" />
                </td>
            </tr>
            <tr>
                <td valign="middle" align="right">
                    <s:submit action="PlaceOrder_confirm" namespace="/movie"
                              value="Sang bước kế tiếp" theme="simple"/>
                </td>
            </tr>
        </table>
    </s:form>
</dvdstore:CenterWindow>
