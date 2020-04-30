package ${bizPackage};

import ${entityPackage}.${entityClassName};
import ${reqPackage}.${instanceName}Req;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Description: ${instanceName} 业务层接口
 *
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
    Page<${instanceName}Vo> search(Predicate predicate, Pageable pageable);

    /**
     * 根据ID查询${entityDesc}
     *
     * @param id ${entityDesc}ID
     * @return ${entityDesc}
     */
    ${instanceName}Vo detail(String id);

    /**
     * 新增${entityDesc}
     *
     * @param req 新增信息
     */
    void add(${instanceName}Req req);

    /**
     * 修改${entityDesc}
     *
     * @param req 修改内容
     */
    void modify(${instanceName}Req req);

    /**
     * 删除${entityDesc}
     *
     * @param id ${entityDesc}ID
     */
    void remove(String id);
}
