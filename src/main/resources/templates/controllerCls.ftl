package ${webPackage};

import com.querydsl.core.types.Predicate;
import ${bizPackage}.${instanceName}Biz;
import ${reqPackage}.${instanceName}Req;
import ${entityPackage}.${entityClassName};
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.groups.Default;

/**
 * Description: ${entityDesc}-管理接口
 *
 * @author Coder
 */
@RestController
@RequestMapping(name = "${entityDesc}管理", path = "/admin/${instanceName?uncap_first}")
public class ${instanceName}Controller {

    private final ${instanceName}Biz ${instanceName?uncap_first}Biz;

    @Autowired
    public ${instanceName}Controller(${instanceName}Biz ${instanceName?uncap_first}Biz) {
        this.${instanceName?uncap_first}Biz = ${instanceName?uncap_first}Biz;
    }

    /**
     * 分页查询${entityDesc}数据
     */
    @GetMapping(name = "查询${entityDesc}列表", path = "/search")
    public PageListJsonResult<${instanceName}Vo> search(@QuerydslPredicate(root = ${entityClassName}.class) Predicate predicate,
                                                         @PageableDefault Pageable pageable) {
        Page<${instanceName}Vo> entities = ${instanceName?uncap_first}Biz.search(predicate, pageable);
        return new PageListJsonResult<>(entities);
    }

    /**
    * 查询${entityDesc}明细数据
    */
    @GetMapping(name = "查询${entityDesc}明细", path = "/detail")
    public DataJsonResult<${instanceName}Vo> detail(@RequestParam String id) {
        return DataJsonResult.succeed(${instanceName?uncap_first}Biz.detail(id));
    }

    /**
    * 新增${entityDesc}
    */
    @PostMapping(name = "新增${entityDesc}", path = "/create")
    public JsonResult create(@Validated({Insert.class,Default.class}) @RequestBody ${instanceName}Req req) {
        ${instanceName?uncap_first}Biz.add(req);
        return JsonResult.success();
    }

    /**
    * 修改${entityDesc}数据
    */
    @PostMapping(name = "修改${entityDesc}", path = "/modify")
    public JsonResult modify${instanceName}(@Validated({Modify.class,Default.class}) @RequestBody ${instanceName}Req req) {
        ${instanceName?uncap_first}Biz.modify(req);
        return JsonResult.success();
    }

    /**
    * 删除${entityDesc}
    */
    @PostMapping(name = "删除${entityDesc}", path = "/remove")
    public JsonResult remove(@RequestParam String id) {
        ${instanceName?uncap_first}Biz.remove(id);
        return JsonResult.success();
    }
}
