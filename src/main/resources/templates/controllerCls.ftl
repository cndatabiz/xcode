package ${webPackage};

import com.xzkingdee.wcm.sys.biz.IAuthenticationFacade;
import com.xzkingdee.wcm.validation.groups.Insert;
import com.xzkingdee.wcm.validation.groups.Modify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.querydsl.core.types.Predicate;
import com.xzkingdee.wcm.biz.results.PageListJsonResult;

import  ${bizPackage}.${instanceName}Biz;
import  ${bizImplPackage}.${instanceName}BizImpl;
import ${entityPackage}.${entityClassName};


/**
 * ${entityDesc} Controller
 * @author Coder
 */
@RestController
@RequestMapping(name = "${entityDesc}管理", path = "/admin/${instanceName?uncap_first}")
public class ${instanceName}Controller {

    private final ${instanceName}Biz ${instanceName?uncap_first}Biz;

    private final IAuthenticationFacade authenticationFacade;

    @Autowired
    public ${instanceName}ManageController(${instanceName}Biz ${instanceName?uncap_first}Biz, IAuthenticationFacade authenticationFacade) {
        this.${instanceName?uncap_first}Biz = ${instanceName?uncap_first}Biz;
        this.authenticationFacade = authenticationFacade;
    }

    /**
     * 新增${entityDesc}
     */
    @PostMapping(name = "新增${entityDesc}", path = "/create")
    public ${instanceName}Vo create${instanceName}(@Validated({Insert.class,Default.class}) ${instanceName}Req req) {
        <#if propNameList?seq_contains("createBy")>
        req.setCreateBy(authenticationFacade.getUserId());
        </#if>
        <#if propNameList?seq_contains("createDept")>
        req.setCreateDept(authenticationFacade.getOrgId());
        </#if>
        ${entityClassName} ${instanceName?uncap_first} = ${instanceName?uncap_first}Biz.add${instanceName}(req);

        return ${instanceName}Vo.of(${instanceName?uncap_first});
    }

    /**
     * 分页查询${entityDesc}数据
     */
    @GetMapping(name = "查询${entityDesc}列表", path = "/search")
    public PageListJsonResult<${instanceName}Vo> search${instanceName}(@QuerydslPredicate(root = ${entityClassName}.class) Predicate predicate, Pageable pageable) {
        Page<${entityClassName}> entities = ${instanceName?uncap_first}Biz.query${instanceName}ByPage(predicate, pageable);

        return new PageListJsonResult<>(entities.map(${instanceName}Vo::of));
    }

    /**
    * 查询${entityDesc}明细数据
    */
    @GetMapping(name = "查询${entityDesc}明细", path = "/detail")
    public ${instanceName}Vo query${instanceName}Detail(@RequestParam String id) {
        ${entityClassName} ${instanceName?uncap_first} = ${instanceName?uncap_first}Biz.fetchById(id);

        return ${instanceName}Vo.of(${instanceName?uncap_first});
    }

    /**
    * 修改${entityDesc}数据
    */
    @PostMapping(name = "修改${entityDesc}", path = "/modify")
    public ${instanceName}Vo modify${instanceName}(@Validated({Modify.class,Default.class}) ${instanceName}Req req) {
        <#if propNameList?seq_contains("updateBy")>
        req.setUpdateBy(authenticationFacade.getUserId());
        </#if>
        ${entityClassName} ${instanceName?uncap_first} = ${instanceName?uncap_first}Biz.modify${instanceName}(req);

        return ${instanceName}Vo.of(${instanceName?uncap_first});
    }

    /**
    * 删除${entityDesc}
    */
    @PostMapping(name = "删除${entityDesc}", path = "/remove")
    public void remove${instanceName}(@RequestParam String id) {
        ${instanceName?uncap_first}Biz.remove${instanceName}ById(id);
    }

}