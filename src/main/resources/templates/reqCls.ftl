package ${reqPackage};

import lombok.Getter;
import lombok.Setter;

/**
 * ${instanceName}Req is a Request entity object for Adding Or Modification.
 * @author Coder
 */

@Setter
@Getter
public class ${instanceName}Req {
<#list propList as prop>

    /**
    * ${prop.propDesc}
    */
    <#if !prop.nullable>
    ${prop.annNotNull}
    </#if>
    <#if prop.annInFormat != "">
    ${prop.annInFormat}
    </#if>
    private ${prop.javaType} ${prop.propName};

</#list>
}