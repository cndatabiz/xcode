package ${entityPackage};

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import <#if !isExtends>java.io.Serializable;</#if>

/**
 * Description: ${entityDesc}实体类
 *
 * @author Coder
 */
@Setter
@Getter
@Entity
@Table(name = "${tableName}")
public class ${entityClassName} <#if isExtends>extends ${baseClass}<#else>implements Serializable</#if> {

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
