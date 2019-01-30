package ${bizPackage};

import com.xzkingdee.wcm.biz.BaseEntityBiz;
import ${entityPackage}.${entityClassName};

/**
 * ${instanceName} Biz
 * @author Coder
 */
public interface ${instanceName}Biz extends CommonBiz<${entityClassName},String> {

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
}