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

import static com.dataz.xcode.contants.ClsFileContants.Q_ENTITY_TEMPLATE;
import static com.dataz.xcode.contants.ClsFileContants.getQueryEntityClassName;
import static com.dataz.xcode.contants.FtlContants.ENTITY_CLASS;

/**
 * 功能描述: 生成Query 查询类
 *
 * @author tommy create on 2018-05-30-10:39
 */
@Component
public class GenQEntityTask implements GenTask {

    @Override
    public void genFile(Configuration cfg, Map<String, Object> map, Path targetDir) throws TemplateException, IOException {
        Template template = cfg.getTemplate(Q_ENTITY_TEMPLATE);
        String className = map.get(ENTITY_CLASS).toString();

        Writer file = new FileWriter(targetDir.resolve(getQueryEntityClassName(className)).toFile());
        writeToFile(template,map,file);
    }
}
