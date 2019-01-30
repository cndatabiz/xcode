package com.dataz.xcode.gencodes;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.Map;

/**
 * @author Tommy
 */
public interface GenTask {
    /**
     * 根据模板生成文件
     * @param cfg 模板配置
     * @param map 上下文环境
     * @param targetDir 目标文件路径
     * @throws TemplateException e
     * @throws IOException       e
     */
    void genFile(Configuration cfg, Map<String, Object> map, Path targetDir) throws TemplateException, IOException;

    /**
     * 模板解析后写入文件
     * @param template  模板文件
     * @param map       上下文环境
     * @param file      目标文件
     * @throws IOException       e
     * @throws TemplateException e
     */
    default void writeToFile(Template template, Map<String, Object> map, Writer file) throws IOException, TemplateException {
        template.process(map, file);

        file.flush();
        file.close();
    }
}
