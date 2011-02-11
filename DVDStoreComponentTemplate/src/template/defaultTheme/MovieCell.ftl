<@s.url action="${parameters.action!'MovieDetail'}?movId=${stack.findString(parameters.movieId)!''}"
        namespace="${parameters.namespace!'/movie'}" var='movieDetailUrl'/>
<#include "/${parameters.templateDir}/${parameters.theme}/TableHeader.ftl" />
    <tr>
      <td valign="top" align="center">
            <a href="${stack.findString('movieDetailUrl')}">
                <img height="143px" width="100px" border="0" <#t/>
                    src="<@s.url value='${stack.findString(parameters.moviePictureUrl)!""}' encode='false'/>" > <#lt/>
            </a>
      </td>
    </tr>
    <tr>
        <td height="2px"></td>
    </tr>
    <tr>
      <td valign="top" align="center">
        <a href="${stack.findString('movieDetailUrl')}" id="movieLink" onMouseOut="this.id='movieLink'" onMouseOver="this.id='movieLinkMouseOver'">
            ${(stack.findString(parameters.movieName)!"")?xhtml}
        </a>
      </td>
    </tr>
</table>