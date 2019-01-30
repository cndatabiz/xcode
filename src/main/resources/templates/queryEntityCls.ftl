package ${entityPackage};

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;

/**
 * Q${entityClassName} is a Querydsl query type for ${entityClassName}
 * @author Coder
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class Q${entityClassName} extends EntityPathBase<${entityClassName}> {
    private static final long serialVersionUID = 1L;
    private static final PathInits INITS = PathInits.DIRECT2;

    public static final Q${entityClassName} ${entityObjectName} = new Q${entityClassName}("${entityObjectName}");

<#if isExtends>
    public final com.xzkingdee.wcm.dao.common.entities.QAbstractBaseEntity _super;

    //inherited
    public final StringPath id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate;

    // inherited
    public final QSysUserEntity createUser;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updateDate;

    // inherited
    public final QSysUserEntity updateUser;
</#if>

<#list propList as prop>
    /**
    * ${prop.propDesc}
    */
    ${prop.queryField};

</#list>

    public Q${entityClassName}(String variable) {
        super(${entityClassName}.class, forVariable(variable));
    }

    public Q${entityClassName}(Path<? extends ${entityClassName}> path) {
        super(path.getType(), path.getMetadata());
    }

    public Q${entityClassName}(PathMetadata metadata) {
        super(${entityClassName}.class, metadata);
    }

<#if isExtends>
    public Q${entityClassName}(PathMetadata metadata, PathInits inits) {
        this(${entityClassName}.class, metadata, inits);
    }

    public Q${entityClassName}(Class<? extends ${entityClassName}> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new com.xzkingdee.wcm.dao.common.entities.QAbstractBaseEntity(type, metadata, inits);
        this.createDate = _super.createDate;
        this.createUser = _super.createUser;
        this.id = _super.id;

        this.updateDate = _super.updateDate;
        this.updateUser = _super.updateUser;

        // add entity association object
        // ex: this.sysOrg = inits.isInitialized("sysOrg") ? new QSysOrgEntity(forProperty("sysOrg"), inits.get("sysOrg")) : null;
    }
</#if>
}