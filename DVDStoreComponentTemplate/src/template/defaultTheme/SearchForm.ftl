<#include "/${parameters.templateDir}/${parameters.theme}/DSFormElement.ftl" />
<#include "/${parameters.templateDir}/${parameters.theme}/TableHeader.ftl" />
  <tr>
    <td height="2" colspan="3"></td>
  </tr>
  <tr>
    <td width="2"></td>
    <td>
        <@s.form action="${parameters.action!'MovieList_byOther'}" namespace="${parameters.namespace!'/movie'}" method="GET" >
            <@s.token />
            <@textfield key="${parameters.movieNameKey}" size="18" maxlength="50"
                    labelposition="top" textfieldType="sidebar"/>
            <@textfield key="${parameters.actorNameKey}" size="12" maxlength="50" 
                    labelposition="top" withSearchButton=true textfieldType="sidebar"/>
        </@s.form>
    </td>
    <td width="2"></td>
  </tr>
  <tr>
    <td height="2" colspan="3"></td>
  </tr>
</table>
<@s.if test="searchError!=null and searchError.equals('true')">
    <script type="text/javascript">
        alert("<@s.text name='searchForm.required' />");
    </script>
</@s.if>