<%-- 
    Document   : DeliveryInfoModify
    Created on : Feb 17, 2010, 10:57:44 PM
    Author     : VU VIET PHUONG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="dvdstore" uri="/dvdstore-tags" %>
<dvdstore:CenterWindow itemTitle="itemTitle.DeliveryInfoModify" >
    <dvdstore:DeliveryInfoModifyForm firstNameKey="firstName" lastNameKey="lastName"
                           emailKey="email" addressKey="address"
                           telKey="tel" />
</dvdstore:CenterWindow>
