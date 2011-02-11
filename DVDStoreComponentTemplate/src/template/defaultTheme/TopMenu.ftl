<@s.url action="${parameters.mainAction!'Home'}"
        namespace="${parameters.mainNamespace!'/'}" var='mainUrl'/>
<@s.url action="${parameters.contactAction!'Contact'}"
        namespace="${parameters.contactNamespace!'/'}" var='contactUrl'/>
<@s.url action="${parameters.guideAction!'Guide'}"
        namespace="${parameters.guideNamespace!'/'}" var='guideUrl'/>
<@s.url action="${parameters.registerAction!'Register_form'}"
        namespace="${parameters.registerNamespace!'/user'}" var='registerUrl'/>

<#include "/${parameters.templateDir}/${parameters.theme}/TableHeader.ftl" />
  <tr class="topMenuBackground">
    <td height="26" width="14" class="topMenuLeftBackground"></td>
    <td width="363"></td>
    <td width="83" align="center" valign="middle">
       <a href="${stack.findString('mainUrl')}" id="topMenu" onmouseover="this.id='topMenuMouseOver'" onmouseout="this.id='topMenu'">
            ${(parameters.main!"")?xhtml}</a>
    </td>
    <td width="26" class="topMenuSeperator"></td>
    <td width="76" align="center" valign="middle">
       <a href="${stack.findString('contactUrl')}" id="topMenu" onmouseover="this.id='topMenuMouseOver'" onmouseout="this.id='topMenu'">
            ${(parameters.contact!"")?xhtml}</a>
    </td>
    <td width="26" class="topMenuSeperator"></td>
    <td width="91" align="center" valign="middle">
       <a href="${stack.findString('guideUrl')}" id="topMenu" onmouseover="this.id='topMenuMouseOver'" onmouseout="this.id='topMenu'">
            ${(parameters.guide!"")?xhtml}</a>
    </td>
    <td width="26" class="topMenuSeperator"></td>
    <td width="69" align="center" valign="middle">
       <a href="${stack.findString('registerUrl')}" id="topMenu" onmouseover="this.id='topMenuMouseOver'" onmouseout="this.id='topMenu'">
            ${(parameters.register!"")?xhtml}</a>
    </td>
    <td width="12" class="topMenuRightBackground"></td>
  </tr>
</table>