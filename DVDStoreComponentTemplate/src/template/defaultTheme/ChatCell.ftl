<#assign cn = (stack.findString(parameters.chatNick)!"vstorm83")?xhtml />
<#include "/${parameters.templateDir}/${parameters.theme}/TableHeader.ftl" />
<tbody>
  <tr>
    <td valign="top" align="center">
        <a href="ymsgr:sendim?${cn}">
            <img border="0" src="http://opi.yahoo.com/online?u=${cn}&amp;m=g&amp;t=2=us">
        </a>
    </td>
  </tr>
</tbody></table>