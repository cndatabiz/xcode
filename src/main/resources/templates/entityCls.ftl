package ${entityPackage};

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * ${entityDesc} Controller
 * @author Coder
 */

@Setter
@Getter
@Entity
@Table(name = "${tableName}")
public class ${entityClassName} <#if isExtends>extends ${baseClass}</#if> {

<#list propList as prop>
    /**
    * ${prop.propDesc}
    */
    ${prop.annColumn}
    <#if prop.propName == "id">
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    </#if>
    private ${prop.javaType} ${prop.propName};

</#list>
}