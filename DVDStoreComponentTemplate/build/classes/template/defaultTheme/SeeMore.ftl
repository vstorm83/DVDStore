<@s.url action="${parameters.action!'BestMovieList'}"
        namespace="${parameters.namespace!'movie'}" var='seemoreUrl'/>
<#include "/${parameters.templateDir}/${parameters.theme}/TableHeader.ftl" />
	<tr>
    	<td height="${parameter.topGap!5}px"></td>
    </tr>
	<tr>
		<td valign="top" align="center">
          <a href="${stack.findString('seemoreUrl')}" class="seeMore">
            ${parameters.seemore!"Seemore"?html} &gt;&gt;
          </a>
	  </td>
    </tr>
    <tr>
    	<td height="${parameter.bottomGap!5}px"></td>
    </tr>
</table>