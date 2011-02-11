<#include "/${parameters.templateDir}/${parameters.theme}/DSFormElement.ftl" />
<#include "/${parameters.templateDir}/${parameters.theme}/TableHeader.ftl" />
  <tr>
    <td height="20" colspan="3"></td>
  </tr>
  <tr>
    <td colspan="3" align="center">
        <span class="deliveryInfoModifyForm">
            Để sửa mật khẩu, xin hãy nhập chính xác mật khẩu cũ
        </span>
    </td>
  </tr>
  <tr>
    <td height="15" colspan="3"></td>
  </tr>
  <tr>
    <td colspan="3">
        <@s.actionmessage />
    </td>
  </tr>
  <tr>
    <td height="10" colspan="3"></td>
  </tr>
  <tr>
    <td width="90"></td>
    <td>
        <@s.form action="${parameters.action!'CredentialsModify_process'}" namespace="${parameters.namespace!'/user'}" >
            <@textfield key="${parameters.userNameKey}" maxlength="20"
                        note="${parameters.userNameNote!''}" required=true/>
            <@textfield key="${parameters.passwordKey}" maxlength="20" isPassword=true
                        required=true/>
            <@textfield key="${parameters.newPasswordKey}" maxlength="20" isPassword=true
                        note="${parameters.passwordNote!''}" required=true/>
            <@textfield key="${parameters.retypeNewPasswordKey}" maxlength="20" isPassword=true
                        required=true />
            <tr>
                <td colspan="3" height="5"></td>
            </tr>
            <tr>
                <td colspan="3" align="center">
                    <@s.submit value="Đồng ý sửa" theme="simple"/>
                    &nbsp; &nbsp; &nbsp; &nbsp;
                    <@s.reset value="Nhập lại" theme="simple"/>
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