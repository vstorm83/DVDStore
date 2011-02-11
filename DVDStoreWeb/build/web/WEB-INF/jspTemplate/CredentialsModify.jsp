<%-- 
    Document   : PasswordModify
    Created on : Feb 18, 2010, 7:44:46 AM
    Author     : VU VIET PHUONG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="dvdstore" uri="/dvdstore-tags" %>
<dvdstore:CenterWindow itemTitle="itemTitle.CredentialsModify" >
    <dvdstore:CredentialsModifyForm userNameKey="userName" userNameNoteKey="userNameNote"
                           passwordKey="password" passwordNoteKey="passwordNote"
                           newPasswordKey="newPassword" retypeNewPasswordKey="retypeNewPassword" />
</dvdstore:CenterWindow>
