<#if parameters.tokenName??>
    <@s.param name="${parameters.tokenNameField}">${parameters.tokenName?html}</@s.param>
    <@s.param name="${parameters.tokenName}">${parameters.token?html}</@s.param>
</#if>