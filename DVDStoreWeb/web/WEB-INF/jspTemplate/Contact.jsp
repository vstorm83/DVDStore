<%-- 
    Document   : Contact
    Created on : Feb 13, 2010, 9:07:00 AM
    Author     : VU VIET PHUONG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  prefix="s" uri="/struts-tags" %>
<%@taglib  prefix="dvdstore" uri="/dvdstore-tags" %>
<dvdstore:CenterWindow itemTitle="itemTitle.Contact" >
    <dvdstore:Contact companyNameKey="comInfo.comName" addressKey="comInfo.comAddress"
                      telKey="comInfo.comTel" emailKey="comInfo.comEmail" />
</dvdstore:CenterWindow>
