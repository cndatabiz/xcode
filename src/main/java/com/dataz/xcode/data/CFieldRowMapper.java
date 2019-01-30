package com.dataz.xcode.data;

import com.dataz.xcode.entity.CmField;
import com.google.common.base.Strings;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.dataz.xcode.utils.ConvertUtil.fieldToProperty;


/**
 * 功能描述:
 *
 * @author tommy create on 2018-04-26-11:51
 */

public class CFieldRowMapper implements RowMapper<CmField> {

    @Override
    public CmField mapRow(@Nullable ResultSet rs, int rowNum) throws SQLException {
        Assert.notNull(rs,"rs is not null.");

        CmField field = CmField.of(rs.getString("colName"));
        String fieldDesc = rs.getString("colDesc");
        if (Strings.isNullOrEmpty(fieldDesc)) {
            fieldDesc = fieldToProperty(field.getFieldName());
        }
        field.setFieldDesc(fieldDesc);

        field.setDataLength(rs.getInt("dataLength"));
        field.setDataPrecision(rs.getInt("dataPrecision"));
        field.setDataType(rs.getString("dataType"));
        field.setNullable(rs.getString("nullable"));

        return field;
    }
}
