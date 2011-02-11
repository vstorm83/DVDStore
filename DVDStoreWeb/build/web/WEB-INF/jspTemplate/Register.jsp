<%-- 
    Document   : Register
    Created on : Feb 13, 2010, 6:32:19 PM
    Author     : VU VIET PHUONG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="dvdstore" uri="/dvdstore-tags" %>
<dvdstore:CenterWindow itemTitle="itemTitle.Register" >
    <dvdstore:RegisterForm userNameKey="userName" userNameNoteKey="userNameNote"
                           passwordKey="password" passwordNoteKey="passwordNote"
                           retypePasswordKey="retypePassword"
                           firstNameKey="deliveryInfo.firstName" lastNameKey="deliveryInfo.lastName"
                           emailKey="deliveryInfo.email" addressKey="deliveryInfo.address"
                           telKey="deliveryInfo.tel" securityCodeKey="securityCode"
                           inputSecurityCodeKey="inputSecurityCode"
                           submitButtonLabelKey="registerForm.submitButtonLabel"
                           resetButtonLabelKey="registerForm.resetButtonLabel"
                           registerHeaderKey="registerHeader"
                           smallRegisterHeaderKey="smallRegisterHeader" />
</dvdstore:CenterWindow>
