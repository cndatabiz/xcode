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

import static com.dataz.xcode.contants.ClsFileContants.BIZ_IMPL_TEMPLATE;
import static com.dataz.xcode.contants.ClsFileContants.getBizImplClassName;
import static com.dataz.xcode.contants.FtlContants.ENTITY_INSTANCE;

/**
 * 功能描述:
 *
 * @author tommy create on 2018-05-30-18:40
 */
@Component
public class GenBizImplTask implements GenTask {

    @Override
    public void genFile(Configuration cfg, Map<String, Object> map, Path targetDir) throws TemplateException, IOException {
        Template template = cfg.getTemplate(BIZ_IMPL_TEMPLATE);
        String instanceName = map.get(ENTITY_INSTANCE).toString();
        Writer file = new FileWriter(targetDir.resolve(getBizImplClassName(instanceName)).toFile());

        writeToFile(template,map,file);
    }
}
