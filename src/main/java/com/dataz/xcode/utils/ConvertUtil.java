package com.dataz.xcode.utils;

import com.dataz.xcode.entity.CmField;
import com.google.common.base.CaseFormat;

/**
 * 功能描述:
 *
 * @author tommy create on 2018-04-26-18:33
 */

public class ConvertUtil {

    private final static String LOCAL_DATE = "LocalDate";
    private final static String LOCAL_DATE_TIME = "LocalDateTime";
    private static final String STRING = "String";
    private static final String BOOLEAN = "Boolean";

    public static String tableToClass(String dbName) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, dbName);
    }

    public static String tableToInstance(String dbName) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, dbName);
    }

    public static String tableToObject(String dbName) {
        return CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, dbName);
    }

    public static String fieldToProperty(String field) {
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, field);
    }

    public static String dbTypeToJavaType(CmField field) {
        String javaType = "";
        String dataType = field.getDataType().toUpperCase();
        switch (dataType) {
            case "VARCHAR2":
            case "VARCHAR":
            case "CHAR":
                javaType = "String";
                break;
            case "DECIMAL":
            case "NUMBER":
            case "NUMBERIC":
            case "INT":
            case "LONG":
            case "FLOAT":
            case "DOUBLE":
                javaType = getJavaNumberType(field);
                break;
            case "DATE":
            case "TIMESTAMP":
            case "DATETIME":
                javaType = convertJavaType(dataType);
                break;
            case "BOOLEAN":
                javaType = "Boolean";
            default:
        }

        return javaType;
    }

    private static String convertJavaType(String dataType){
        if ("DATE".equals(dataType)){
            return "LocalDate";
        }else{
            return "LocalDateTime";
        }
    }

    private static String getJavaNumberType(CmField field) {
        String numberType;

        if (field.getDataPrecision() == 0) {
            if (field.getDataLength() < 2) {
                numberType = "Boolean";
            }
            else if (field.getDataLength() < 3) {
                numberType = "Byte";
            }
            else if (field.getDataLength() < 5) {
                numberType = "Short";
            }
            else if (field.getDataLength() < 10) {
                numberType = "Integer";
            }
            else if (field.getDataLength() < 19) {
                numberType = "Long";
            }
            else {
                numberType = "BigDecimal";
            }
        } else {
            numberType = "BigDecimal";
        }

        return numberType;
    }

    public static String getAnnNotNull(CmField field, boolean isNull,
                                       String enableValidateGroup, String validateGroupName) {
        StringBuilder result = new StringBuilder();
        if (!isNull) {
            result.append("@NotNull(")
                    .append("message = \"").append(field.getFieldDesc()).append("不能为空.\"");
            if ("true".equalsIgnoreCase(enableValidateGroup)) {
                result.append(", group = ").append(validateGroupName).append(".class");
            }
            result.append(")");
        }

        return result.toString();
    }

    public static String getAnnColumn(CmField field, boolean isNull) {
        if ("id".equalsIgnoreCase(field.getFieldName())) {
            return "@Id";
        }

        StringBuilder result = new StringBuilder();
        result.append("@Column(")
                .append("name = \"").append(field.getFieldName()).append("\"");
        if (field.getDataType().contains("CHAR")) {
            result.append(", length = ").append(field.getDataLength());
        }
        if (!isNull) {
            result.append(", nullable = false");
        }
        result.append(" )");

        return result.toString();
    }

    /**
     * 根据字段类型返回请求格式化注解
     * @param field 字段信息
     * @return      输入格式化注解字符串
     */
    public static String getAnnInFormat(CmField field){
        String javaType = dbTypeToJavaType(field);
        switch (javaType) {
            case LOCAL_DATE:
                return "@DateTimeFormat(pattern = STD_DATE_FMT_PATTERN)";
            case LOCAL_DATE_TIME:
                return "@DateTimeFormat(pattern = STD_LONG_TIME_PATTERN)";
            default:
                return "";
        }
    }

    /**
     * 根据字段类型返回输出格式化注解
     * @param field  字段信息
     * @return       输出格式化注解字符串
     */
    public static String getAnnOutFormat(CmField field){
        String javaType = dbTypeToJavaType(field);
        switch (javaType) {
            case LOCAL_DATE:
                return "@JsonFormat(pattern = STD_DATE_FMT_PATTERN, shape = JsonFormat.Shape.STRING)";
            case LOCAL_DATE_TIME:
                return "@JsonFormat(pattern = STD_LONG_TIME_PATTERN, shape = JsonFormat.Shape.STRING)";
            default:
                return "";
        }
    }

    /**
     * 根据字段信息生成Q类属性字符串
     * public final StringPath itemName = createString("itemName");
     */
    public static String getQueryFieldStr(CmField field) {
        String javaType = dbTypeToJavaType(field);
        String property = fieldToProperty(field.getFieldName());

        StringBuilder result = new StringBuilder();
        result.append("public final ");

        // public final StringPath itemName = createString("itemName");
        if (STRING.equals(javaType)) {
            result.append("StringPath ")
                    .append(property)
                    .append(" = createString(\"")
                    .append(property)
                    .append("\")");
        }

        //public final DateTimePath<java.time.LocalDateTime> updateDate;
        if (LOCAL_DATE_TIME.equals(javaType)) {
            result.append(" DateTimePath<java.time.LocalDateTime> ")
                    .append(property)
                    .append(" = createDateTime(\"").append(property).append("\",")
                    .append("java.time.LocalDateTime.class)");
        }

        //public final  DateTimePath<java.time.LocalDate> startDate;
        if (LOCAL_DATE.equals(javaType)) {
            result.append(" DateTimePath<java.time.LocalDate> ")
                    .append(property)
                    .append(" = createDateTime(\"").append(property).append("\",")
                    .append("java.time.LocalDate.class)");
        }

        //public final NumberPath<Long> orderNo = createNumber("orderNo", Long.class);
        String javaNumTypeString = "Integer,Long,BigDecimal";
        if (javaNumTypeString.contains(javaType)) {
            result.append(" NumberPath<").append(javaType).append("> ")
                    .append(property)
                    .append(" = createNumber(\"")
                    .append(property)
                    .append("\", ")
                    .append(javaType).append(".class)");
        }

        //public final BooleanPath actNonExpired = createBoolean("actNonExpired");
        if (BOOLEAN.equals(javaType)) {
            result.append("BooleanPath ")
                    .append(property)
                    .append(" = createBoolean(\"")
                    .append(property)
                    .append("\")");
        }

        return result.toString();
    }

    public static <T> T cast(Object obj) {
        return (T) obj;
    }
}
