package com.dataz.xcode.contants;

import java.util.Arrays;
import java.util.Map;

/**
 * 功能描述:
 *
 * @author tommy create on 2018-04-26-14:39
 */

public class ClsFileContants {

    public static String JAVA_EXT = ".java";
    public static String ENTITY_SUFFIX = "Entity";
    public static String DAO_SUFFIX = "Repository";
    public static String BIZ_SUFFIX = "Biz";
    public static String BIZ_IMPL_SUFFIX = "BizImpl";
    public static String REQ_SUFFIX = "Req";
    public static String VO_SUFFIX = "Vo";

    public static String Web_SUFFIX = "Controller";

    /**
     * package variable constant
     */
    public static String ENTITY_PACKAGE = "entityPackage";
    public static String DAO_PACKAGE = "daoPackage";
    public static String BIZ_PACKAGE = "bizPackage";
    public static String BIZ_IMPL_PACKAGE = "bizImplPackage";
    public static String WEB_PACKAGE = "webPackage";
    public static String VO_PACKAGE = "voPackage";
    public static String REQ_PACKAGE = "reqPackage";

    /**
     * template file constant
     */
    public static String ENTITY_TEMPLATE = "entityCls.ftl";
    public static String Q_ENTITY_TEMPLATE = "queryEntityCls.ftl";
    public static String REPOSITORY_TEMPLATE = "repositoryCls.ftl";
    public static String BIZ_TEMPLATE = "bizCls.ftl";
    public static String BIZ_IMPL_TEMPLATE = "bizImplCls.ftl";
    public static String REQ_TEMPLATE = "reqCls.ftl";
    public static String VO_TEMPLATE = "voCls.ftl";
    public static String CONTROLLER_TEMPLATE = "controllerCls.ftl";


    public static String getEntityClassName(String className) {
        return className.concat(JAVA_EXT);
    }

    public static String getQueryEntityClassName(String className) {
        return "Q" + className.concat(JAVA_EXT);
    }

    public static String getRepositoryClassName(String className) {
        return className.concat(DAO_SUFFIX).concat(JAVA_EXT);
    }

    public static String getBizClassName(String className) {
        return className.concat(BIZ_SUFFIX).concat(JAVA_EXT);
    }

    public static String getBizImplClassName(String className) {
        return className.concat(BIZ_IMPL_SUFFIX).concat(JAVA_EXT);
    }

    public static String getReqClassName(String className) {
        return className.concat(REQ_SUFFIX).concat(JAVA_EXT);
    }

    public static String getVoClassName(String className) {
        return className.concat(VO_SUFFIX).concat(JAVA_EXT);
    }

    public static String getControllerClassName(String className) {
        return className.concat(Web_SUFFIX).concat(JAVA_EXT);
    }

    private static String getFullPackage(String basePackage, PackageEnum packageType) {
        String fullPackage = "";
        switch (packageType) {
            case entity:
                fullPackage = basePackage.concat(".entity");
                break;
            case dao:
                fullPackage = basePackage.concat(".repository");
                break;
            case biz:
                fullPackage = basePackage.concat(".biz");
                break;
            case bizImpl:
                fullPackage = basePackage.concat(".biz.impl");
                break;
            case web:
                fullPackage = basePackage.concat(".controller");
                break;
            case vo:
                fullPackage = basePackage.concat(".vo");
                break;
            case req:
                fullPackage = basePackage.concat(".req");
                break;
            default:
        }
        return fullPackage;
    }

    public static void initPackageVariable(Map<String, Object> map, String basePageName) {
        Arrays.stream(PackageEnum.values()).forEach(
                packageEnum -> map.put(packageEnum.getPackageType(), getFullPackage(basePageName, packageEnum)
                ));
    }
}
