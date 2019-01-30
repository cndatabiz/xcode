package com.dataz.xcode.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 功能描述:
 *
 * @author tommy create on 2018-04-26-9:11
 */

@Setter
@Getter
public class CmEntity {
    private String entityDesc;
    private String entityName;

    private List<CmField> fields;

    public CmEntity() {
    }

    public CmEntity(String entityDesc, String entityName) {
        this.entityDesc = entityDesc;
        this.entityName = entityName;
    }

    public static CmEntity of(String entityDesc, String entityName){
        return new CmEntity(entityDesc, entityName);
    }

    @Override
    public String toString() {
        return "CmEntity{" +
            "entityDesc='" + entityDesc + '\'' +
            ", entityName='" + entityName + '\'' +
            '}';
    }
}
