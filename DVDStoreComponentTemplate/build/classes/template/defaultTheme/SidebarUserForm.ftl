<#include "/${parameters.templateDir}/${parameters.theme}/DSFormElement.ftl" />
<#include "/${parameters.templateDir}/${parameters.theme}/TableHeader.ftl" />
  <tr>
    <td height="2" colspan="3"></td>
  </tr>
  <tr>
    <td width="5"></td>
    <td>
        <table width="100%" cellspacing="0" cellpadding="0" border="0">
          <tr>
            <td>
<#if parameters.loggedIn!false>
<table width="100%" cellspacing="0" cellpadding="0" border="0">
  <tr>
    <td width="15" height="55">&nbsp;</td>
    <td valign="middle" align="left">
        <a href="<@s.url action='DeliveryInfoModify_form' namespace='/user' />" <#t/>
onmouseout="this.id='modifyUserLink'" onmouseover="this.id='modifyUserLinkMouseOver'" id="modifyUserLink">
        Thay đổi thông tin <br>cá nhân
        </a>
    </td>
  </tr>
  <tr>
    <td width="15" height="35">&nbsp;</td>
    <td valign="middle" align="left">
        <a href="<@s.url action='CredentialsModify_form' namespace='/user' />" <#t/>
onmouseout="this.id='modifyUserLink'" onmouseover="this.id='modifyUserLinkMouseOver'" id="modifyUserLink">
        Sửa mật khẩu
        </a>
    </td>
  </tr>
  <tr>
    <td width="15" height="35">&nbsp;</td>
    <td valign="middle" align="left">
        <a href="<@s.url value='/j_spring_security_logout' />" <#t/>
onmouseout="this.id='modifyUserLink'" onmouseover="this.id='modifyUserLinkMouseOver'" id="modifyUserLink">
        Đăng thoát
        </a>
    </td>
  </tr>
</table>
<#else>
            <@s.form action="${parameters.action!'/j_spring_security_check'}" namespace="${parameters.namespace!'/'}" >
                <@textfield key="${parameters.userNameKey}" size="16" maxlength="50"
                        labelposition="top" textfieldType="sidebar"/>
                <@textfield key="${parameters.passwordKey}" size="12" maxlength="50"
                        labelposition="top" withLoginButton=true textfieldType="sidebar" isPassword=true/>
            </@s.form>
</#if>
            </td>
          </tr>
          <tr>
            <td height="15"></td>
          </tr>
          <tr>
            <td align="center">
                <img width="125" height="195"
                    src="<@s.url value='/struts/${parameters.theme}/images/phim.gif' encode='false'/>" >
            </td>
          </tr>
          <tr>
            <td height="10"></td>
          </tr>
          <tr>
            <td align="center">
                <a href="<@s.url action='${parameters.registerAction!"Register_form"}'
                            namespace='${parameters.registerNamespace!"/user"}' />">
                <img width="100" height="37" border="0" 
                    src="<@s.url value='/struts/${parameters.theme}/images/reg.gif' encode='false' />"></a>
            </td>
          </tr>
        </table>
    </td>
    <td width="2"></td>
  </tr>
  <tr>
    <td height="9" colspan="3"></td>
  </tr>
</table>