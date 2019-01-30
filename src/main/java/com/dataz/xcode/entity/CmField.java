package com.dataz.xcode.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * 功能描述:
 *
 * @author tommy create on 2018-04-26-9:15
 */
@Setter
@Getter
public class CmField {

    private String fieldDesc;
    private String fieldName;
    private String dataType;
    private int    dataLength;
    private int    dataPrecision;
    private String nullable;

    @Override
    public String toString() {
        return "CmField{" +
            "fieldDesc='" + fieldDesc + '\'' +
            ", fieldName='" + fieldName + '\'' +
            '}';
    }

    public static CmField of(String fieldName){
        CmField field = new CmField();
        field.setFieldName(fieldName);
        return field;
    }
}
