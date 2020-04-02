package ${bizPackage};

import com.xzkingdee.wcm.biz.BaseEntityBiz;
import ${entityPackage}.${entityClassName};

/**
 * ${instanceName} Biz
 * @author Coder
 */
public interface ${instanceName}Biz extends CommonBiz<${entityClassName},String> {

    /**
    * 分页查询${entityDesc}
    *
    * @param predicate   查询参数
    * @param pageable    分页参数
    * @return ${entityDesc}列表
    */
    Page<${entityClassName}> queryByPage(Predicate predicate, Pageable pageable);

    /**
    * 根据${entityDesc}ID查询数量
    *
    * @param id ${entityDesc}ID
    * @return 数量
    */
    long countById(String id);

    /**
    * 新增${entityDesc}
    * @return     返回保存后实体对象
    */
    ${entityClassName} add(${instanceName}Req req);

    /**
    * 修改${entityDesc}
    * @return     返回保存后实体对象
    */
    ${entityClassName} modify(${instanceName}Req req);

    /**
    * 删除${entityDesc}
    *
    * @param id ${entityDesc}ID
    */
    void remove(String id);
}