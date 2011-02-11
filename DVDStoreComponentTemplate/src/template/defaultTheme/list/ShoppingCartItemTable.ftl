<#assign confirmForm = parameters.confirmForm!false />
<script type="text/javascript" src="<@s.url value='/struts/defaultTheme/NumberOnly.js' encode='false' />" ></script>
<#include "/${parameters.templateDir}/${parameters.theme}/TableHeader.ftl" />
  <tr>
    <td>Những phim đang chọn mua</td>
  </tr>
  <tr>
    <td height="5"></td>
  </tr>
  <tr>
    <td><table width="100%" border="1" cellspacing="1" cellpadding="1">
      <tr align="center">
        <td width="42%">Tên phim</td>
        <td width="15%">Giá</td>
        <td width="9%">Giảm </td>
        <td width="8%">SL</td>
        <td width="17%">Thành tiền</td>
        <#if !confirmForm>
        <td width="9%">Xóa</td>
        </#if>
      </tr>
<#assign total = 0 />
<#if parameters.list??>
    <@s.iterator value="parameters.list" status="stat">
        <tr align="center">
        <td>
            <@s.property value="movName" />
        </td>
        <td><@s.property value="movPrice" /></td>
        <td>
            <@s.property value="saleOff" />
        </td>
        <td align="center">
            <#if confirmForm>
                <@s.property value="%{quantity}" /> 
            <#else>
                <@s.textfield name="items[%{#stat.index}].quantity" value="%{quantity}" size="1"
maxlength="2" theme="simple" onkeyup="ChiNhapSo(this.id)" cssStyle="text-align:center"/>
            </#if>
        </td>
        <td>
            <#assign movPrice = stack.findValue("movPrice") />
            <#assign saleOff = stack.findValue("saleOff") />
            <#assign quantity = stack.findValue("quantity") />
            <#assign money = (movPrice - movPrice*saleOff/100)*quantity />
            <#assign total=total + money />
            <@s.label value="${money}" />
        </td>
        <#if !confirmForm>
        <td>
            <@s.checkbox name="items[%{#stat.index}].delete" value="delete" theme="simple"/>
        </td>
        </#if>
      </tr>
    </@s.iterator>
</#if>      
      <tr align="center">
        <td colspan="4">Tổng</td>
        <td>
            <@s.label value="${total}" />
        </td>
        <#if !confirmForm>
        <td>&nbsp;</td>
        </#if>
      </tr>
    </table></td>
  </tr>  
</table>