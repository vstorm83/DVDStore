<#include "/${parameters.templateDir}/${parameters.theme}/DSFormElement.ftl" />
<#include "/${parameters.templateDir}/${parameters.theme}/TableHeader.ftl" />
  <tr>
    <td height="20" colspan="3"></td>
  </tr>
  <tr>
    <td colspan="3" align="center">
        <span class="registerHeader">${parameters.registerHeader?xhtml}</span><br/>
        <span class="smallRegisterHeader">${parameters.smallRegisterHeader}</span>
    </td>
  </tr>
  <tr>
    <td height="20" colspan="3"></td>
  </tr>
  <tr>
    <td width="90"></td>
    <td>
        <@s.form action="${parameters.action!'Register_process'}" namespace="${parameters.namespace!'/user'}" >
            <@s.token />
            <@textfield key="${parameters.userNameKey}" maxlength="20" 
                        note="${parameters.userNameNote!''}" required=true/>
            <@textfield key="${parameters.passwordKey}" maxlength="20" isPassword=true
                        note="${parameters.passwordNote!''}" required=true/>
            <@textfield key="${parameters.retypePasswordKey}" maxlength="20" isPassword=true
                        required=true />
            <@textfield key="${parameters.firstNameKey}" maxlength="20"
                        required=true />
            <@textfield key="${parameters.lastNameKey}" maxlength="20"
                        required=true />
            <@textfield key="${parameters.emailKey}" maxlength="50"
                        required=true />
            <@textfield key="${parameters.addressKey}" maxlength="100"
                        required=true />
            <@textfield key="${parameters.telKey}" maxlength="50"
                        required=true />
            <@textfield key="${parameters.securityCodeKey}" isPic=true />
            <@textfield key="${parameters.inputSecurityCodeKey}"
                        required=true />

            <tr>
                <td colspan="3" height="5"></td>
            </tr>
            <tr>
                <td colspan="3" align="center">
                    <@s.submit value="${parameters.submitButtonLabel}" theme="simple"/>
                    &nbsp; &nbsp; &nbsp; &nbsp;
                    <@s.reset value="${parameters.resetButtonLabel}" theme="simple"/>
                </td>
            </tr>
        </@s.form>
    </td>
    <td width="2"></td>
  </tr>
  <tr>
    <td height="2" colspan="3"></td>
  </tr>
</table>