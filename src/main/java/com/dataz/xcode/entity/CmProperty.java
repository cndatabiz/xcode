package com.dataz.xcode.entity;

import com.dataz.xcode.utils.ConvertUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.dataz.xcode.contants.SQLContants.SYS_FILEDS_LIST;
import static com.dataz.xcode.utils.ConvertUtil.dbTypeToJavaType;
import static com.dataz.xcode.utils.ConvertUtil.fieldToProperty;

/**
 * 功能描述:
 *
 * @author tommy create on 2018-04-26-18:22
 */

@Setter
@Getter
public class CmProperty {

    private String propName;
    private String propDesc;
    private String javaType;
    private int length;
    private int precision;
    private boolean nullable;

    private String annColumn;
    private String annNotNull;
    /**
     * 请求数据格式化
     */
    private String annInFormat;

    /**
     * 输出数据格式化
     */
    private String annOutFormat;

    private String queryField;

    private CmProperty(String propDesc, String propName, String javaType, boolean nullable) {
        this.propName = propName;
        this.propDesc = propDesc;
        this.javaType = javaType;
        this.nullable = nullable;
    }

    public static CmProperty of(String propName, String propDesc, String javaType, boolean nullable) {
        return new CmProperty(propDesc, propName, javaType, nullable);
    }

    public static List<CmProperty> convert(List<CmField> fields, boolean isExtend, Map<String, String> xCodeCfg,
                                           String dbType) {
        List<CmProperty> cProperties = new ArrayList<>();

        if (isExtend) {
            fields = fields.stream()
                .filter(cField -> !SYS_FILEDS_LIST.contains(cField.getFieldName()))
                .collect(Collectors.toList());
        }

        String enableValidateGroup = xCodeCfg.get("validate.group.enable");
        String validateGroupName = xCodeCfg.get("validate.group.name");

        fields.forEach(field -> {

            String proName = fieldToProperty(field.getFieldName());
            String javaType = dbTypeToJavaType(field, dbType);
            // oracle:Y,mysql:YES
            boolean isNull = field.getNullable().toUpperCase().contains("Y");

            CmProperty property = CmProperty.of(proName, field.getFieldDesc(), javaType, isNull);
            property.setLength(field.getDataLength());
            property.setPrecision(field.getDataPrecision());
            property.setAnnNotNull(ConvertUtil.getAnnNotNull(field, isNull, enableValidateGroup, validateGroupName));
            property.setAnnColumn(ConvertUtil.getAnnColumn(field, isNull));

            property.setAnnInFormat(ConvertUtil.getAnnInFormat(field));
            property.setAnnOutFormat(ConvertUtil.getAnnOutFormat(field));

            property.setQueryField(ConvertUtil.getQueryFieldStr(field));

            cProperties.add(property);
        });

        return cProperties;
    }

    @Override
    public String toString(){
        return this.getPropName();
    }
}
