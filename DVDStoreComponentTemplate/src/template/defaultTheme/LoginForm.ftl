<#include "/${parameters.templateDir}/${parameters.theme}/DSFormElement.ftl" />
<#include "/${parameters.templateDir}/${parameters.theme}/TableHeader.ftl" />
  <tr>
    <td height="20" colspan="3"></td>
  </tr>
  <tr>
    <td colspan="3" align="center">
        <span class="centerLoginFormHeader">
            Nếu chưa có mật khẩu, vào mục đăng ký <br />
            Chú ý, chỉ áp dụng với khách hàng ở Hà Nội
        </span>
    </td>
  </tr>
  <tr>
    <td height="20" colspan="3"></td>
  </tr>
  <tr>
    <td colspan="3">
        <@s.actionerror />
        <@s.actionmessage/>
    </td>
  </tr>
  <tr>
    <td height="10" colspan="3"></td>
  </tr>
  <tr>
    <td width="90"></td>
    <td>
        <@s.form action="${parameters.action!'/j_spring_security_check'}" namespace="${parameters.namespace!'/'}" >
            <@textfield key="j_username" size="20" maxlength="50" />
            <@textfield key="j_password" size="20" maxlength="50" isPassword=true/>
            <tr>
                <td colspan="3" height="5"></td>
            </tr>
            <tr>
                <td colspan="3" align="center">
                    <@s.submit value="Đăng nhập" theme="simple"/>
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