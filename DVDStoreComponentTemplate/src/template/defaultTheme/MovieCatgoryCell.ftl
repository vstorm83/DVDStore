<@s.url action="${parameters.action!'MovieList_byCatId'}"
        namespace="${parameters.namespace!'/movie'}" var='movieListByCatIdUrl'>
    <@s.param name="movCatId">${stack.findString(parameters.movieCatgoryId)!''}</@s.param>
    <#include "/${parameters.templateDir}/${parameters.theme}/TokenParam.ftl" />
</@s.url>
<#include "/${parameters.templateDir}/${parameters.theme}/TableHeader.ftl" />
<tr>
    <td width="13"></td>
    <td width="10" class="movieCatgoryItemIcon"></td>
    <td width="8"></td>
    <td width="119" align="left">               
        <a href="${movieListByCatIdUrl}" id="movieCatgoryLink" <#t/>
          onmouseover="this.id='movieCatgoryLinkMouseOver'" onmouseout="this.id='movieCatgoryLink'" <#t/>>
            ${(stack.findString(parameters.movieCatgoryName)!"")?xhtml}
        </a>
    </td>
</tr>
</table>