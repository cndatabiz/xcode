package com.dataz.xcode.data;

import com.dataz.xcode.contants.DataSourceEnum;
import com.dataz.xcode.contants.SQLContants;
import com.dataz.xcode.entity.CmEntity;
import com.dataz.xcode.entity.CmField;
import com.google.common.base.Strings;
import javafx.util.Pair;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 功能描述: postgreSql 数据库元信息实现
 *
 * @author tommy create on 2018-04-26-9:45
 */
@Log4j2
public class PostgreSqlMetaData implements IMetaData {
    private final JdbcTemplate jdbcTemplate;

    PostgreSqlMetaData(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<CmEntity> queryEntityMeta(String entityName) {
         List<Map<String,Object>> entityList = doQueryTable(entityName, false);
        if (entityList.size()>0){
            Map<String,Object> retMap = entityList.get(0);
            CmEntity cmEntity = buildEntity(entityName, retMap);

            return Optional.of(cmEntity);
        }

        return Optional.empty();
    }

    @Override
    public List<CmEntity> queryEntityList(String entityName) {
        List<Map<String, Object>> entityList = doQueryTable(entityName, true);

        return entityList.stream().map(map -> buildEntity(entityName, map)).collect(Collectors.toList());
    }

    @Override
    public List<CmField> queryFieldMeta(String entityName) {
        return jdbcTemplate.query(SQLContants.ORACLE_FIELD_SQL,new CFieldRowMapper(),entityName.toUpperCase());
    }

    private List<Map<String, Object>> doQueryTable(String entityName, boolean isLike) {
        String entitySql = SQLContants.POSTGRESQL_ENTITY_SQL;
        String condition ;
        Pair<String, String> pair = buildQueryEntitySql(entityName, entitySql, isLike);

        condition = pair.getKey();
        entitySql = pair.getValue();

        log.debug("query table info sql : {}", entitySql);
        return jdbcTemplate.queryForList(entitySql, condition);
    }

    @Override
    public Pair<String, String> buildQueryEntitySql(String entityName, String entitySql, boolean isLike) {
        String condition = entityName.toUpperCase();

        if (!Strings.isNullOrEmpty(entityName)) {
            if (isLike){
                entitySql += " AND A.relname like ? ";
                condition = "%" + condition + "%";
            }else {
                entitySql += " AND A.relname = ? ";
            }
        }

        return new Pair<>(condition, entitySql);
    }
}