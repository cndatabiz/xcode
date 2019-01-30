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

import static com.dataz.xcode.contants.ClsFileContants.REPOSITORY_TEMPLATE;
import static com.dataz.xcode.contants.ClsFileContants.getRepositoryClassName;
import static com.dataz.xcode.contants.FtlContants.ENTITY_INSTANCE;

/**
 * 功能描述: 生成Repository类
 *
 * @author tommy create on 2018-05-30-15:39
 */
@Component
public class GenRepositoryTask implements GenTask  {

    @Override
    public void genFile(Configuration cfg, Map<String, Object> map, Path targetDir) throws TemplateException, IOException {
        Template template = cfg.getTemplate(REPOSITORY_TEMPLATE);
        String instanceName = map.get(ENTITY_INSTANCE).toString();
        Writer file = new FileWriter(targetDir.resolve(getRepositoryClassName(instanceName)).toFile());
        writeToFile(template,map,file);
    }
}
