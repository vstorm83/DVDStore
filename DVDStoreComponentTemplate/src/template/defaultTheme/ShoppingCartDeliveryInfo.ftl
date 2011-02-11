<#if parameters.confirmForm!false>
    <#assign rq=false />
<#else>
    <#assign rq=true />
</#if>
<#include "/${parameters.templateDir}/${parameters.theme}/DSFormElement.ftl" />
<#include "/${parameters.templateDir}/${parameters.theme}/TableHeader.ftl" />
    <tbody><tr>
      <td height="25" class="deliveryInfoLabel">
        ĐỊA ĐIỂM GIAO PHIM
      </td>
    </tr>
        <td align="left">
            <table width="400" border="0" cellspacing="0" cellpadding="0">
                <@textfield key="${parameters.addressKey}" maxlength="100"
                                    required=rq size="30" confirmForm=parameters.confirmForm!false/>
                <@textfield key="${parameters.telKey}" maxlength="50"
                                    required=rq size="30" confirmForm=parameters.confirmForm!false/>
                <@textfield key="${parameters.emailKey}" maxlength="50"
                                        required=rq size="30" confirmForm=parameters.confirmForm!false/>
            </table>
       </td>
</tbody></table>