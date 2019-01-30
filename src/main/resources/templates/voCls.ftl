package ${voPackage};

import lombok.Getter;
import lombok.Setter;

/**
 * View Object for {@link ${entityClassName}}
 * @author Coder
 */
@Getter
@Setter
public class ${instanceName}Vo {

<#list propList as prop>
    /**
    * ${prop.propDesc}
    */
    <#if prop.annOutFormat != "">
    ${prop.annOutFormat}
    </#if>
    private ${prop.javaType} ${prop.propName};

</#list>

    public static ${instanceName}Vo of(${entityClassName} entity) {
        ${instanceName}Vo vo = new ${instanceName}Vo();

        <#list propList as prop>
        vo.${prop.propName} = entity.get${prop.propName?cap_first}();
        </#list>

        return vo;
    }
}