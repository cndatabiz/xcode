package com.dataz.xcode.gencodes;

import com.dataz.xcode.contants.ClsFileContants;
import com.dataz.xcode.contants.DataSourceEnum;
import com.dataz.xcode.data.MetaDataFactory;
import com.dataz.xcode.entity.CmEntity;
import com.dataz.xcode.entity.CmField;
import com.dataz.xcode.entity.CmProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.dataz.xcode.contants.ClsFileContants.*;
import static com.dataz.xcode.contants.FtlContants.*;
import static com.dataz.xcode.contants.SQLContants.CREATE_BY;
import static com.dataz.xcode.contants.SQLContants.UPDATE_BY;
import static com.dataz.xcode.utils.ConvertUtil.*;

/**
 * 功能描述: 构建模板数据
 *
 * @author tommy create on 2018-04-26-13:57
 */

@Component
public class TemplateData {
    private final MetaDataFactory factory;
    private final DataSourceEnum dataSourceEnum;

    @Autowired
    public TemplateData(MetaDataFactory factory, DataSourceEnum dataSourceEnum) {
        this.factory = factory;
        this.dataSourceEnum = dataSourceEnum;
    }

    public Map<String, Object> buildFtlData(Map<String, Object> context, String tableName) {

        Optional<CmEntity> optional = factory.getMetaDataInfo(dataSourceEnum, tableName, context);
        CmEntity cmEntity = optional.orElseThrow(() -> new IllegalArgumentException("Invalid table name( " + tableName + " )."));

        // 初始化类名称
        context.put(ENTITY_INSTANCE, tableToInstance(cmEntity.getEntityName()));
        context.put(ENTITY_CLASS, tableToClass(cmEntity.getEntityName()).concat(ENTITY_SUFFIX));
        context.put(ENTITY_OBJECT, tableToObject(cmEntity.getEntityName()).concat(ENTITY_SUFFIX));
        context.put(ENTITY_DESC, cmEntity.getEntityDesc());

        context.put(DAO_CLASS, tableToClass(cmEntity.getEntityName()).concat(DAO_SUFFIX));
        context.put(BIZ_CLASS, tableToClass(cmEntity.getEntityName()).concat(BIZ_SUFFIX));
        context.put(BIZIMPL_CLASS, tableToClass(cmEntity.getEntityName()).concat(BIZ_IMPL_SUFFIX));
        context.put(WEB_CLASS, tableToClass(cmEntity.getEntityName()).concat(Web_SUFFIX));

        // 通过用户配置信息初始化包变量
        Object obj = context.get("xcode");
        Map<String, String> xCodeCfg = null;
        if (obj instanceof Map) {
            xCodeCfg = cast(context.get("xcode"));
        }
        Assert.notNull(xCodeCfg,"config info not be loaded properly.");

        context.put(DB_TABLE, cmEntity.getEntityName());
        ClsFileContants.initPackageVariable(context, xCodeCfg.get("base.package.name"));

        List<CmField> fields = cmEntity.getFields();
        List<String> fieldNames = fields.stream()
                                        .map(field -> fieldToProperty(field.getFieldName()))
                                        .collect(Collectors.toList());
        context.put(PROP_NAME_LIST, fieldNames);

        boolean isExtends = context.get(IS_EXTENDS).toString().equalsIgnoreCase(Boolean.TRUE.toString());
        isExtends = isExtends && fieldNames.contains(CREATE_BY) && fieldNames.contains(UPDATE_BY);

        context.put(IS_EXTENDS, isExtends);
        String dbType = context.get("dbType") + "";

        // 根据是否需要继承标志，生成属性列表
        context.put(PROPERTY_LIST, CmProperty.convert(cmEntity.getFields(), isExtends, xCodeCfg, dbType));

        return context;
    }

}
