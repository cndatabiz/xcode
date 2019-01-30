package com.dataz.xcode.contants;

/**
 * @author Tommy
 */

public enum PackageEnum {
    /**
     * 实体包
     */
    entity(ClsFileContants.ENTITY_PACKAGE),
    /**
     * dao 包
     */
    dao(ClsFileContants.DAO_PACKAGE),
    /**
     * 业务接口 包
     */
    biz(ClsFileContants.BIZ_PACKAGE),
    /**
     * 业务接口实现
     */
    bizImpl(ClsFileContants.BIZ_IMPL_PACKAGE),
    web(ClsFileContants.WEB_PACKAGE),
    vo(ClsFileContants.VO_PACKAGE),
    req(ClsFileContants.REQ_PACKAGE);


    private String packageType;

    PackageEnum(String packageType) {
        this.packageType = packageType;
    }

    public String getPackageType() {
        return packageType;
    }
}
