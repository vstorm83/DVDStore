<#include "/${parameters.templateDir}/${parameters.theme}/DSFormElement.ftl" />
<#include "/${parameters.templateDir}/${parameters.theme}/TableHeader.ftl" />
  <tr>
    <td height="20" colspan="3"></td>
  </tr>
  <tr>
    <td colspan="3" align="center">
        <span class="deliveryInfoModifyForm">
            Xin hãy nhập thông tin chính xác<br/> để chúng tôi có thể chuyển hàng đến tận tay quý khách
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
        <@s.form action="${parameters.action!'DeliveryInfoModify_process'}" namespace="${parameters.namespace!'/user'}" >            
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