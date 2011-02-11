<#if stack.findValue('pgNum') &gt; 0>
<@s.if test="movCatId != null">
    <#assign action = "MovieList_byCatId" />
</@s.if>
<@s.if test="movName != null or actName != null">
    <#assign action = "MovieList_byOther" />
</@s.if>
<@s.if test="firstLetter != null">
    <#assign action = "MovieList_byFirstLetter" />
</@s.if>
<#if parameters.action??>
    <#assign action = parameters.action />
</#if>
<#include "/${parameters.templateDir}/${parameters.theme}/TableHeader.ftl" />
  <tr>
    <td height="2" colspan="3"></td>
  </tr>
  <tr>
    <td width="2"></td>
    <td>
        <table>
        <tbody>
        <tr>
            <#list 1..stack.findValue('pgNum') as idx>
                <#if stack.findValue("pgIdx") == idx>
            <td class="pagingCurrent">${idx}</td>
                <#else>
                <@s.url action="${action}"
                        namespace="${parameters.namespace!'/movie'}" var='movieListUrl' includeParams="get">                    
                    <@s.param name="pgIdx">${idx}</@s.param>
                    <#include "/${parameters.templateDir}/${parameters.theme}/TokenParam.ftl" />
                </@s.url>
            <td>
                <a href="${stack.findString('movieListUrl')}" class="pagingRemain">
                  ${idx}
                </a>
            </td>
                </#if>
            </#list>
        </tr>
        </tbody></table>
    </td>
    <td width="2"></td>
  </tr>
  <tr>
    <td height="2" colspan="3"></td>
  </tr>
</table>
</#if>