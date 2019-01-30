package com.dataz.xcode.gencodes;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Path;
import java.util.Map;

import static com.dataz.xcode.contants.ClsFileContants.ENTITY_TEMPLATE;
import static com.dataz.xcode.contants.ClsFileContants.getEntityClassName;
import static com.dataz.xcode.contants.FtlContants.ENTITY_CLASS;

/**
 * 功能描述: 生成实体类
 *
 * @author tommy create on 2018-05-01-14:36
 */

@Component
public class GenEntityTask implements GenTask {

    @Override
    public void genFile(Configuration cfg, Map<String, Object> map, Path targetDir) throws TemplateException, IOException {
        Template template = cfg.getTemplate(ENTITY_TEMPLATE);
        String className = map.get(ENTITY_CLASS).toString();
        Writer file = new FileWriter(targetDir.resolve(getEntityClassName(className)).toFile());

        writeToFile(template,map,file);
    }
}
