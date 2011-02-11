<#include "/${parameters.templateDir}/${parameters.theme}/TableHeader.ftl" />
  <tr>
    <td height="2" colspan="3"></td>
  </tr>
  <tr>
    <td width="2"></td>
    <td class="searchBar" align="center" valign="middle">
      <table width="400px" cellspacing="0" cellpadding="0" border="0">
        <tbody><tr>
<#list 0..9 as num>
          <@searchCell key=num_index />
</#list>
<#list ["A","B","C","D","E","F","G","H","I","J","K","L","M","N","0","P","Q","R","S","T","U","V","X","Y","Z"] as num>
          <@searchCell key=num />
</#list>
        </tr></tbody>
      </table>
    </td>
    <td width="2"></td>
  </tr>
  <tr>
    <td height="2" colspan="3"></td>
  </tr>
</table>

<#macro searchCell key>
<@s.url action="${parameters.action!'MovieList_byFirstLetter'}"
        namespace="${parameters.namespace!'/movie'}" var="searchByFirstLetterUrl">
    <@s.param name="firstLetter">${(key!"")?xhtml}</@s.param>
    <#include "/${parameters.templateDir}/${parameters.theme}/TokenParam.ftl" />
</@s.url>
          <td><#rt/>
            <a href="${stack.findString('searchByFirstLetterUrl')}" class="searchBarLink"><#t/>
               ${(key!"")?xhtml}<#t/>
            </a><#t/>
          </td>
</#macro>