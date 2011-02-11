<#macro GridView colNum=4 colGap=4 rowGap=18>
<#include "/${parameters.templateDir}/${parameters.theme}/TableHeader.ftl" />
<#assign counter = 0 />
<tbody>
    <tr>
        <td colspan="${colNum}" height="3"></td>
    </tr>
<#if parameters.list??>
    <@s.iterator value="parameters.list">
        <#if parameters.listKey?? && parameters.listKey == "key">
            <@s.push value="${parameters.listValue}" >
                <@GridRow index=counter />
            </@s.push>
        <#else>
           <@GridRow index=counter />
        </#if>
        <#assign counter = (counter + 1) % colNum />        
    </@s.iterator>
</#if>        
</tbody></table>
</#macro>

<#macro GridRow index colNum=4 colGap=4 rowGap=18>
    <#if index == 0>
     <tr>
    </#if>
       <td>
        <#if parameters.cellTemplate??>
            ${parameters.cellTemplate.invoke(null)}
        </#if>
       </td>
     <#if index &lt; (colNum - 1)>
        <td width="${colGap}px"></td>
     <#else>
      </tr>
      <tr>
    	<td colspan="2" height="${rowGap}px"></td>
      </tr>
     </#if>
</#macro>