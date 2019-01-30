package com.dataz.xcode.data;

import com.dataz.xcode.contants.DataSourceEnum;
import com.dataz.xcode.entity.CmEntity;
import lombok.Getter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * 功能描述: 根据数据库连接返回元信息工厂类
 *
 * @author tommy create on 2018-04-26-9:36
 */
@Component
public class MetaDataFactory{
    @Getter
    private IMetaData metaData;

    public MetaDataFactory(JdbcTemplate jdbcTemplate, DataSourceEnum ds) {
        switch (ds) {
            case ORACLE:
                metaData = new OracleMetaData(jdbcTemplate);
                break;
            case MYSQL:
                metaData = new MysqlMetaData(jdbcTemplate);
                break;
            default:
        }
    }

    public Optional<CmEntity> getMetaDataInfo(DataSourceEnum ds, String entityName, Map<String, Object> context) {
        switch (ds) {
            case ORACLE:
                context.put("dbType", DataSourceEnum.ORACLE.toString());
                break;
            case MYSQL:
                context.put("dbType", DataSourceEnum.MYSQL.toString());
                break;
            default:
        }

        return metaData.queryEntityMeta(entityName);
    }
}
