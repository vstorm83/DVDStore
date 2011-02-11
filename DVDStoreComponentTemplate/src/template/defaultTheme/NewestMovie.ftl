<#if stack.findValue(parameters.movie)??>
<@s.push value="${parameters.movie}" >
<@s.url action="${parameters.action!'MovieDetail'}?movId=${stack.findString(parameters.movieId)!''}"
        namespace="${parameters.namespace!'/movie'}" var='newestMovieDetailUrl'/>
<#assign newestMovieDesc = stack.findString(parameters.movieDescription)!"" />
<#assign newestMovieDesc = newestMovieDesc?replace("<img.*>","","r") />
<#assign newestMovieDesc = newestMovieDesc?replace("<p.*>","","r") />
<#assign newestMovieDesc = newestMovieDesc?replace("</p>","","r") />
<#assign newestMovieDesc = newestMovieDesc?replace("<table.*>","","r") />
<#assign newestMovieDesc = newestMovieDesc?replace("</table>","","r") />
<#assign newestMovieDesc = newestMovieDesc?replace("<tr.*>","","r") />
<#assign newestMovieDesc = newestMovieDesc?replace("</tr>","","r") />
<#assign newestMovieDesc = newestMovieDesc?replace("<td.*>","","r") />
<#assign newestMovieDesc = newestMovieDesc?replace("</td>","","r") />
<#assign newestMovieDesc = newestMovieDesc?replace("<tbody.*>","","r") />
<#assign newestMovieDesc = newestMovieDesc?replace("</tbody>","","r") />
<#assign newestMovieDesc = newestMovieDesc?replace("<div.*>","","r") />
<#assign newestMovieDesc = newestMovieDesc?replace("</div>","","r") />
<#if (newestMovieDesc?length > 1400)>
    <#assign newestMovieDesc = newestMovieDesc?substring(0,1400) />
    <#assign newestMovieDesc = newestMovieDesc?substring(0,newestMovieDesc?last_index_of(" ")) />
</#if>
<#include "/${parameters.templateDir}/${parameters.theme}/TableHeader.ftl" />
  <tr>
    <td height="5" colspan="3"></td>
  </tr>
  <tr>
    <td width="2"></td>
    <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="center" valign="middle">
            <table height="240px" width="160px" cellspacing="0" cellpadding="0" border="0" align="left">
            <tbody><tr>
              <td valign="middle" align="center">
                 <a href="${stack.findString('newestMovieDetailUrl')}">
                    <img height="230px" width="150px" border="0" <#t/>
                        src="<@s.url value='${stack.findString(parameters.moviePictureUrl)!""}' encode='false'/>">
                 </a>
              </td>
            </tr>
            </tbody></table>
            <div align="left">
                <a href="${stack.findString('newestMovieDetailUrl')}" class="newestMovieName"><#t/>
                    ${(stack.findString(parameters.movieName)!"")?xhtml}</a>
            <br> <br>
                <div class="newestMovieDescription">
                    ${newestMovieDesc}
                    <a href="${stack.findString('newestMovieDetailUrl')}" class="newestMovieContinue">...</a>
                </div>
            </div>
          </td>
        </tr>
        </table>
    </td>
    <td width="10"></td>
  </tr>
  <tr>
    <td height="3" colspan="3"></td>
  </tr>
</table>
</@s.push>
</#if>