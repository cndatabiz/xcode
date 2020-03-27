package com.dataz.xcode.data;

import com.dataz.xcode.contants.SqlConstant;
import com.dataz.xcode.entity.CmEntity;
import com.dataz.xcode.entity.CmField;
import javafx.util.Pair;
import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 功能描述: 查询mysql 元数据
 *
 * @author tommy create on 2018-05-18-16:08
 */
@Log4j2
public class MysqlMetaData implements IMetaData {
    private final JdbcTemplate jdbcTemplate;
    private String catalog;

    MysqlMetaData(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        try {
            catalog = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection().getCatalog();
        } catch (SQLException e) {
            log.error(e);
            catalog = "";
        }
    }

    @Override
    public Optional<CmEntity> queryEntityMeta(String entityName) {
        List<Map<String, Object>> entityList = doQueryTable(entityName, false);
        if (entityList.size() > 0) {
            Map<String, Object> retMap = entityList.get(0);
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

    private List<Map<String, Object>> doQueryTable(String entityName, boolean isLike) {
        String entitySql = SqlConstant.MYSQL_ENTITY_SQL;
        entitySql += " WHERE A.TABLE_SCHEMA = ? ";

        String condition ;
        Pair<String, String> pair = buildQueryEntitySql(entityName, entitySql, isLike);

        condition = pair.getKey();
        entitySql = pair.getValue();

        log.debug("catalog:{}", catalog);
        log.debug("query table info sql : {}", entitySql);
        return jdbcTemplate.queryForList(entitySql, catalog, condition);
    }

    @Override
    public List<CmField> queryFieldMeta(String entityName) {
        log.debug("query field info sql : {}", SqlConstant.MYSQL_FIELD_SQL);

        return jdbcTemplate
            .query(SqlConstant.MYSQL_FIELD_SQL, new CFieldRowMapper(), entityName.toUpperCase(), catalog);
    }
}
