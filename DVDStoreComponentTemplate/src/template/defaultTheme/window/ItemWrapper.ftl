<#macro ItemWrapper titleType="small" underlineTitle=true backgroundType="gradien">
<#if parameters.label?? && parameters.label != "">
    <#assign itemTitle = parameters.label />
<#else>
    <#assign itemTitle = parameters.itemTitle!"" />
</#if>
<#include "/${parameters.templateDir}/${parameters.theme}/TableHeader.ftl" />
  <tr>
    <td <#t/>
        <#if titleType == "small">
            class="itemList_td_small_title"
        <#else>
            class="itemList_td_title"
        </#if>>
        ${itemTitle?xhtml}
    </td>
  </tr>
<#if underlineTitle!true>
  <tr>
    <td class="itemList_td_titleUnderline"></td>
  </tr>
</#if>
  <tr>
<#if backgroundType == "mono">
    <td class="itemList_td_monoBackground">
<#elseif backgroundType == "gradien">
    <td class="itemList_td_gradienBackground">
<#else>
    <td>
</#if>
</#macro>