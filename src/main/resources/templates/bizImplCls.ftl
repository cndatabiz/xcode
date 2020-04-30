package ${bizImplPackage};

import com.querydsl.core.types.Predicate;
import  ${bizPackage}.${instanceName}Biz;
import ${reqPackage}.${instanceName}Req;
import ${entityPackage}.${entityClassName};
import ${entityPackage}.Q${entityClassName};
import ${daoPackage}.${daoClassName};
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

<#if propNameList?seq_contains("createDate") || propNameList?seq_contains("updateDate")>
import java.time.LocalDateTime;
</#if>

/**
 * Description: ${instanceName}-业务接口实现类
 *
 * @author Coder
 */
@Slf4j
@Service
class ${instanceName}BizImpl extends CommonBizImpl<${instanceName}Repository, ${entityClassName}, String> implements ${instanceName}Biz {

    private final Q${entityClassName} q${instanceName} = Q${entityClassName}.${entityObjectName};
    private final DtoMapper mapper;

    public ${instanceName}BizImpl(${instanceName}Repository ${instanceName?uncap_first}Repository, DtoMapper mapper) {
        super(${instanceName?uncap_first}Repository);
        this.mapper = mapper;
    }

    @Override
    public Page<${instanceName}Vo> search(Predicate predicate, Pageable pageable){
        Page<${entityClassName}> page = queryByPage(predicate, pageable);
        return page.map(entity -> mapper.map(entity, ${instanceName}Vo.class));
    }

    @Override
    public ${instanceName}Vo detail(String id) {
        return mapper.map(fetchById(id), ${instanceName}Vo.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(${instanceName}Req req) {
        ${entityClassName} entity = mapper.map(req, ${entityClassName}.class);
        <#if propNameList?seq_contains("createDate")>
        entity.setCreateDate(LocalDateTime.now());
        </#if>
        saveEntity(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modify(${instanceName}Req req) {
        ${entityClassName} entity = fetchById(req.getId());
        mapper.map(req, entity);
        <#if propNameList?seq_contains("updateDate")>
        entity.setUpdateDate(LocalDateTime.now());
        </#if>
        saveEntity(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(String id){
        repository.deleteById(id);
    }
}
