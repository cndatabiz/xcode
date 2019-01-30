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

import static com.dataz.xcode.contants.ClsFileContants.REQ_TEMPLATE;
import static com.dataz.xcode.contants.ClsFileContants.getReqClassName;
import static com.dataz.xcode.contants.FtlContants.ENTITY_INSTANCE;

/**
 * 功能描述:
 *
 * @author tommy create on 2018-11-27-10:11
 */
@Component
public class GenReqTask implements GenTask {

    @Override
    public void genFile(Configuration cfg, Map<String, Object> map, Path targetDir)
                                                                        throws TemplateException, IOException {
        Template template = cfg.getTemplate(REQ_TEMPLATE);
        String className = map.get(ENTITY_INSTANCE).toString();
        Writer file = new FileWriter(targetDir.resolve(getReqClassName(className)).toFile());

        writeToFile(template, map, file);
    }
}
