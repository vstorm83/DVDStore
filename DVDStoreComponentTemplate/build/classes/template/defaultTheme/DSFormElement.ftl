<#macro textfield key labelposition="" size="18" maxlength="50" required=false isPassword=false
        withSearchButton=false withLoginButton=false textfieldType="" note="" isPic=false confirmForm=false>
${tag.addParameter("name", key)}<#t/>
<#if textfieldType!="sidebar" && key != parameters.inputSecurityCodeKey!"">
${tag.addParameter("nameValue", stack.findString(key))}<#t/>
</#if>
<@s.text name=key var="label" />
${tag.addParameter("label", stack.findString("label"))}
${tag.addParameter("labelposition", labelposition)}<#t/>
${tag.addParameter("size", size)}<#t/>
${tag.addParameter("maxlength", maxlength)}<#t/>
<#if textfieldType == "sidebar">
    <#assign labelClass = "sidebarLabel" />
    <#assign errorLabelClass = "sidebarErrorLabel" />
<#else>
    <#assign labelClass = "label" />
    <#assign errorLabelClass = "errorLabel" />
</#if>
${tag.addParameter("required", required)}
<#--Control header-->
<#assign hasFieldErrors = parameters.name?? && fieldErrors?? && fieldErrors[parameters.name]??/>
<#if hasFieldErrors>
<#list fieldErrors[parameters.name] as error>
<tr errorFor="${parameters.id}">
<#if parameters.labelposition?default("") == 'top'>
    <td align="left" valign="top" colspan="3"><#rt/>
<#else>
    <td align="center" valign="top" colspan="3"><#rt/>
</#if>
        <span class="errorMessage">${error?html}</span><#t/>
    </td><#lt/>
</tr>
</#list>
</#if>
<#--
	if the label position is top,
	then give the label it's own row in the table
-->
<tr>
<#if parameters.labelposition?default("") == 'top'>
    <td align="left" valign="top" colspan="3"><#rt/>
<#else>
    <td class="tdLabel" align="right" ><#rt/>
</#if>
<#if parameters.label??>
    <label <#t/>
<#if parameters.id??>
        for="${parameters.id?html}" <#t/>
</#if>
<#if hasFieldErrors>
        class="${errorLabelClass}"<#t/>
<#else>
        class="${labelClass}"<#t/>
</#if>
    ><#t/>
<#if parameters.required?default(false) && parameters.requiredposition?default("right") != 'right'>
        <span class="required">*</span><#t/>
</#if>
${parameters.label?html}<#t/>
<#if parameters.required?default(false) && parameters.requiredposition?default("right") == 'right'>
 <span class="required">*</span><#t/>
</#if>
${parameters.labelseparator?default(":")?html}<#t/>
<#include "/${parameters.templateDir}/xhtml/tooltip.ftl" /> 
</label><#t/>
</#if>
    </td><#lt/>
<#-- add the extra row -->
<#if parameters.labelposition?default("") == 'top'>
</tr>
<tr>
<#else>
<td width="3"></td>
</#if>
    <td valign="middle"><#t/>
<#--Control body-->
<#if isPic>
    <img width="200" height="50"
                    src="<@s.url action='CaptchaGen' namespace="/" />" >
<#elseif isPassword>
    <#include "/${parameters.templateDir}/simple/password.ftl" />
<#elseif confirmForm>
    <#include "/${parameters.templateDir}/simple/label.ftl" />
<#else>
    <#include "/${parameters.templateDir}/simple/text.ftl" />
</#if>
<#--Control footer-->
    <#if withSearchButton>
        <@s.url value="/struts/${parameters.theme}/images/b_search.png" var="b_search.png" encode="false"/>
        &nbsp;<@s.submit type="image" src="${stack.findString('b_search.png')}" theme="simple"/>
            </td><#lt/>
        </tr>
    <#elseif withLoginButton>
        <@s.url value="/struts/${parameters.theme}/images/b_login.png" var="b_login.png" encode="false"/>
        &nbsp;<@s.submit type="image" src="${stack.findString('b_login.png')}" theme="simple"/>
            </td><#lt/>
        </tr>
    <#elseif note != "">
        <br/><span class="note">${note?xhtml}</span>
            </td><#lt/>
        </tr>
    <#else>
        <#include "/${parameters.templateDir}/xhtml/controlfooter.ftl" />
    </#if>
</#macro>