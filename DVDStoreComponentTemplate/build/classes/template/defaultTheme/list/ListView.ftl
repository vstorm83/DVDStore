<#macro ListView topGap=5 middleGap=5 bottomGap=5>
<#include "/${parameters.templateDir}/${parameters.theme}/TableHeader.ftl" />
<tr>
    <td height="${topGap}"></td>
</tr>
<#if parameters.list??>
    <@s.iterator value="parameters.list">
        <#if parameters.listKey?? && parameters.listKey == "key">
            <@s.push value="${parameters.listValue}" >
                <@ListCell />
            </@s.push>
        <#else>
            <@ListCell />
        </#if>
            <tr>
                <td height="${middleGap}"></td>
            </tr>
    </@s.iterator>
</#if>
<tr>
    <td height="${bottomGap}"></td>
</tr>
</table>
</#macro>
<#macro ListCell>
    <tr>
      <td>
        <#if parameters.cellTemplate??>
            ${parameters.cellTemplate.invoke(null)}
        </#if>
      </td>
    </tr>
</#macro>