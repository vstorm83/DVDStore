<table width="100%" cellspacing="0" cellpadding="0" border="0" <#t/>
                <#if parameters.disabled?default(false)>
                     disabled="disabled" <#t/>
                    </#if>
                <#if parameters.title??>
                    title="${parameters.title?html}" <#t/>
                </#if>
                <#if parameters.id??>
                    id="${parameters.id?html}" <#t/>
                </#if>
                <#include "/${parameters.templateDir}/simple/css.ftl" />
                <#include "/${parameters.templateDir}/simple/common-attributes.ftl" /> <#t/>
                <#include "/${parameters.templateDir}/simple/scripting-events.ftl" /> <#t/>
                <#include "/${parameters.templateDir}/simple/dynamic-attributes.ftl" /> <#t/>>