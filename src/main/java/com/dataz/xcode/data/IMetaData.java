package com.dataz.xcode.data;

import com.dataz.xcode.entity.CmEntity;
import com.dataz.xcode.entity.CmField;
import com.google.common.base.Strings;
import javafx.util.Pair;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Tommy
 */
public interface IMetaData {
    /**
     * 查询实体元信息
     * @param entityName 实体名称
     * @return 实体信息
     */
    Optional<CmEntity> queryEntityMeta(String entityName);

    /**
     * 查询实体元信息
     * @param entityName 实体名称
     * @return 实体信息列表
     */
    List<CmEntity> queryEntityList(String entityName);

    /**
     * 查询实体字段信息
     * @param entityName 实体名称
     * @return 字段信息列表
     */
    List<CmField> queryFieldMeta(String entityName);

    /**
     * 构建实体信息
     * @param entityName 实体名称
     * @param retMap     实体名称及描述
     * @return 实体对象
     */
    default CmEntity buildEntity(String entityName, Map<String, Object> retMap) {
        String desc = retMap.get("CDESC") == null ? retMap.get("CNAME").toString() :  retMap.get("CDESC").toString();
        CmEntity cmEntity = CmEntity.of(desc, retMap.get("CNAME").toString());
        cmEntity.setFields(queryFieldMeta(entityName.toUpperCase()));

        return cmEntity;
    }

    /**
     * 生成查询实体SQL 以及其参数
     * @param entityName 实体名称
     * @param entitySql  查询实体SQL
     * @param isLike     是否模糊查询
     * @return           pair
     */
    default Pair<String, String> buildQueryEntitySql(String entityName, String entitySql, boolean isLike){
        String condition = entityName.toUpperCase();

        if (!Strings.isNullOrEmpty(entityName)) {
            if (isLike){
                entitySql += " AND A.TABLE_NAME like ? ";
                condition = "%" + condition + "%";
            }else {
                entitySql += " AND A.TABLE_NAME = ? ";
            }
        }

        return new Pair<>(condition, entitySql);
    }
}
