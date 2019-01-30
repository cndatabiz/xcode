package ${bizImplPackage};

import com.xzkingdee.wcm.biz.impl.BaseEntityBizImpl;
import  ${bizPackage}.${instanceName}Biz;

import ${entityPackage}.${entityClassName};
import ${entityPackage}.Q${entityClassName};

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * ${instanceName} Service
 * @author Coder
 */
@Service
class ${instanceName}BizImpl extends CommonBizImpl<${instanceName}Repository, ${entityClassName}, String> implements ${instanceName}Biz {
    private static final Logger LOGGER = LogManager.getLogger(${instanceName}BizImpl.class);

    private final Q${entityClassName} q${instanceName} = Q${entityClassName}.${entityObjectName};
    private final DtoMapper mapper;

    public ${instanceName}BizImpl(${instanceName}Repository ${instanceName?uncap_first}Repository, DtoMapper mapper) {
        super(${instanceName?uncap_first}Repository);
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public ${entityClassName} add(${instanceName}Req req) {
        ${entityClassName} entity = mapper.map(req, ${entityClassName}.class);
        <#if propNameList?seq_contains("createDate")>
        entity.setCreateDate(LocalDateTime.now());
        </#if>


        saveEntity(entity);
        return entity;
    }

    @Override
    @Transactional
    public ${entityClassName} modify(${instanceName}Req req) {
        ${entityClassName} entity = fetchById(req.getId());
        mapper.map(req, entity);
        <#if propNameList?seq_contains("updateDate")>
        entity.setUpdateDate(LocalDateTime.now());
        </#if>
        saveEntity(entity);
        return entity;
    }
}