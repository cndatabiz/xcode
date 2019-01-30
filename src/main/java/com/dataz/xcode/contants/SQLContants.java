package com.dataz.xcode.contants;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 功能描述:
 *
 * @author tommy create on 2018-04-26-11:30
 */

public class SQLContants {

    public static String ORACLE_ENTITY_SQL =
        "SELECT A.TABLE_NAME CNAME , B.COMMENTS CDESC " +
            " FROM USER_TABLES A, USER_TAB_COMMENTS B " +
            " WHERE A.TABLE_NAME = B.TABLE_NAME ";

    public static String ORACLE_FIELD_SQL =
        "SELECT A.COLUMN_NAME colName,B.COMMENTS colDesc,A.DATA_TYPE dataType," +
              " A.DATA_LENGTH dataLength,A.DATA_PRECISION dataPrecision, A.NULLABLE nullable " +
        " FROM USER_TAB_COLUMNS A " +
              " LEFT JOIN user_col_comments B ON A.TABLE_NAME   = B.TABLE_NAME " +
                                             " AND A.COLUMN_NAME = B.COLUMN_NAME " +
        " WHERE A.TABLE_NAME = ? " +
        " ORDER BY A.COLUMN_ID " ;

    public static String MYSQL_ENTITY_SQL =
        "SELECT A.table_name CNAME,A.table_comment CDESC " +
        " FROM information_schema.TABLES A " ;

    public static String MYSQL_FIELD_SQL =
        "SELECT b.COLUMN_NAME colName,b.column_comment colDesc,b.DATA_TYPE dataType,IFNULL(b.CHARACTER_MAXIMUM_LENGTH,b.NUMERIC_PRECISION) dataLength, " +
        "b.NUMERIC_SCALE dataPrecision,b.IS_NULLABLE nullable " +
        " FROM information_schema.TABLES a " +
        " LEFT JOIN information_schema.COLUMNS b ON a.table_name = b.TABLE_NAME and a.TABLE_SCHEMA = b.TABLE_SCHEMA" +
        " WHERE a.table_name = ? and a.TABLE_SCHEMA = ? " +
        " ORDER BY b.ORDINAL_POSITION ";

    public static List<String> SYS_FILEDS_LIST
        = Lists.newArrayList("ID", "CREATE_DATE", "UPDATE_DATE", "CREATE_BY", "UPDATE_BY");

    public static String CREATE_BY = "createBy";
    public static String UPDATE_BY = "updateBy";

}
