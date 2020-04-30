package ${daoPackage};

import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import ${entityPackage}.${entityClassName};
import ${entityPackage}.Q${entityClassName};
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

import javax.annotation.Nullable;

/**
 * Description: ${entityDesc}-JPA接口
 *
 * @author Coder
 */
public interface ${daoClassName} extends CustomBaseRepository<${entityClassName}, String> ,QuerydslBinderCustomizer<Q${entityClassName}>{

    /**
     * 自定义查询条件
     *
     * @param bindings 定义查询路径
     * @param root q查询对象
     */
    @Override
    default void customize(QuerydslBindings bindings, @Nullable Q${entityClassName} root){
        bindings.bind(String.class).first(
            (SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase
        );
    }
}
