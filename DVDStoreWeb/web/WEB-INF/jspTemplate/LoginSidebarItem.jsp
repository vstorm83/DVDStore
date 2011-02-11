<%-- 
    Document   : LoginSidebarItem
    Created on : Feb 5, 2010, 2:35:52 AM
    Author     : VU VIET PHUONG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="dvdstore" uri="/dvdstore-tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<security:authorize ifAllGranted="PERMIT_Customer">
    <security:authentication property="principal.username" scope="request" var="username"/>
    <dvdstore:LoginWindow itemTitle="%{#request.username}">
        <dvdstore:SidebarUserForm loggedIn="true" />
    </dvdstore:LoginWindow>
</security:authorize>
<security:authorize ifNotGranted="PERMIT_Customer">
    <dvdstore:LoginWindow itemTitle="itemTitle.Login">
        <dvdstore:SidebarUserForm userNameKey="j_username" passwordKey="j_password" />
    </dvdstore:LoginWindow>
</security:authorize>
<table width="100%" border="0" cellpadding="0" cellspacing="0" >
    <tr>
        <td height="15"></td>
    </tr>
    <tr>
        <td align="center">
            <img width="125" height="195" src="<s:url value="/struts/defaultTheme/images/07.jpg" encode="false"/>">
        </td>
    </tr>
    <tr>
        <td height="15"></td>
    </tr>
    <tr>
        <td align="center">
            <img width="125" height="195" src="<s:url value="/struts/defaultTheme/images/08.jpg" encode="false"/>">
        </td>
    </tr>
</table>
