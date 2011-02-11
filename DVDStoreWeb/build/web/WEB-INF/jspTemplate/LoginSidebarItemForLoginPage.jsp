<%-- 
    Document   : LoginSidebarItemForLoginPage
    Created on : Mar 13, 2010, 9:44:09 AM
    Author     : VU VIET PHUONG
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<table width="100%" cellspacing="0" cellpadding="0" border="0" id="">
    <tbody><tr>
            <td class="itemList_td_small_title">
                Đăng nhập
            </td>
        </tr>
        <tr>
            <td class="itemList_td_titleUnderline"></td>
        </tr>
        <tr>
            <td class="itemList_td_gradienBackground">

                <table width="100%" cellspacing="0" cellpadding="0" border="0" id="">  <tbody><tr>
                            <td height="2" colspan="3"></td>
                        </tr>
                        <tr>
                            <td width="5"></td>
                            <td>
                                <table width="100%" cellspacing="0" cellpadding="0" border="0">
                                    <tbody><tr>
                                            <td>
                                                <form method="post" action="<s:url value="/j_spring_security_check" />"
                                                      name="j_spring_security_check" id="j_spring_security_check">
                                                    <table class="wwFormTable">

                                                        <tbody><tr>
                                                                <td valign="top" align="left" colspan="3"><label class="sidebarLabel" for="">Tên đăng nhập:</label></td>
                                                            </tr>
                                                            <tr>
                                                                <td valign="middle"><input type="text" id="" maxlength="50" size="16" name="j_username"></td>
                                                            </tr>


                                                            <tr>
                                                                <td valign="top" align="left" colspan="3"><label class="sidebarLabel" for="">Mật khẩu:</label></td>
                                                            </tr>
                                                            <tr>
                                                                <td valign="middle"><input type="password" id="" maxlength="50" size="12" name="j_password">        &nbsp;
                                                                    <input type="image" value="Submit" id="j_spring_security_check_0" src="<s:url value="/struts/defaultTheme/images/b_login.png" encode="false"/>" alt="Submit">

                                                                </td>
                                                            </tr>
                                                        </tbody></table></form>



                                            </td>
                                        </tr>
                                        <tr>
                                            <td height="15"></td>
                                        </tr>
                                        <tr>
                                            <td align="center">
                                                <img width="125" height="195" src="<s:url value="/struts/defaultTheme/images/phim.gif" encode="false"/>">
                                            </td>
                                        </tr>
                                        <tr>
                                            <td height="10"></td>
                                        </tr>
                                        <tr>
                                            <td align="center">
                                                <a href="/DVDStoreWeb/user/Register_form.htm">
                                                    <img width="100" height="37" border="0" src="<s:url value="/struts/defaultTheme/images/reg.gif" encode="false"/>"></a>
                                            </td>
                                        </tr>                                        
                                    </tbody></table>
                            </td>
                            <td width="2"></td>
                        </tr>
                        <tr>
                            <td height="9" colspan="3"></td>
                        </tr>
                    </tbody></table>
            </td>
        </tr>
    </tbody></table>

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
