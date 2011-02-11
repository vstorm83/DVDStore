<@s.url value="${stack.findString(parameters.advertismentSource)!''}" var="source" encode="false"/>
<#assign source = stack.findString('source') />
<#if source?ends_with(".swf")>
    <#assign isFlash = true>
<#else>
    <#assign isFlash = false>
</#if>
<#include "/${parameters.templateDir}/${parameters.theme}/TableHeader.ftl" />
    <tr>
        <td valign="top" align="center">
            <#if isFlash>
            <object width="140px" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=7,0,19,0" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000">
              <param value="${source}" name="movie">
              <param value="high" name="quality">
              <embed width="140px" type="application/x-shockwave-flash"
pluginspage="http://www.macromedia.com/go/getflashplayer" quality="high" src="${source}">
            </object>
            <#else>
            <a target="_blank" href="${(stack.findString(parameters.advertismentLink)!'')?xhtml}">
                <img width="140px" border="0" src="${source}">
            </a>
            </#if>
        </td>
    </tr>
</table>