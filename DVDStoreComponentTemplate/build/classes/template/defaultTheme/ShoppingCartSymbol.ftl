<#include "/${parameters.templateDir}/${parameters.theme}/TableHeader.ftl" />
    <tr>
      <td class="shoppingCartIcon"></td>
    </tr>
    <tr>
      <td height="38px" align="center" valign="top" class="shoppingCartText">
            ${(parameters.shoppingCartLabel!"")?xhtml}: <#t/>
            <span class="shoppingCartNum">${stack.findString(parameters.itemCount)!""}</span> <#t/>
            ${(parameters.unit!"")?xhtml}
       </td>
    </tr>
    <tr>
      <td height="48px" align="center" valign="middle">
        <@s.form action="${parameters.action!'PlaceOrder_form'}" namespace="${parameters.namespace!'/movie'}"
                 theme="simple" >
            <@s.submit class="shoppingCartButton"
                    value="${(parameters.shoppingCartButtonLabel!'')?xhtml}"/>
        </@s.form>
       </td>
    </tr>
</table>