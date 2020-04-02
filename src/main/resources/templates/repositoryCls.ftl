package ${daoPackage};

import ${entityPackage}.${entityClassName};
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.lang.NonNull;

/**
 * Repository of ${entityClassName}
 * @author Coder
 */
public interface ${daoClassName} extends CustomBaseRepository<${entityClassName}, String> ,QuerydslBinderCustomizer<Q${entityClassName}>{
    /**
    * 自定义查询条件
    * @param bindings 定义查询路径
    * @param root q查询对象
    */
    @Override
    default void customize(@NonNull QuerydslBindings bindings, @NonNull Q${entityClassName} root){
        bindings.bind(String.class).first(
        (StringPath path, String value) -> path.containsIgnoreCase(value));
    }
}
