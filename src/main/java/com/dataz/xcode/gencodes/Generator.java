package com.dataz.xcode.gencodes;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * 功能描述:
 *
 * @author tommy create on 2018-04-26-14:26
 */
@Component
@Log4j2
public class Generator {
    private Configuration cfg;
    private final GenTaskListener taskListener;
    private final static String FTL_DIR = "/templates";

    public Generator(GenTaskListener taskListener){
        this.taskListener = taskListener;

        cfg = new Configuration(Configuration.VERSION_2_3_28);
        cfg.setClassForTemplateLoading(this.getClass(), FTL_DIR);
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
    }

    public void doGenFileTask(Map<String, Object> map, Path targetDir) throws IOException, TemplateException {

        List<GenTask> tasks = taskListener.list();
        for (GenTask task : tasks) {
            task.genFile(cfg, map, targetDir);
        }
    }

    public void addTask(GenTask task) {
        Assert.isInstanceOf(GenTask.class, task, "task must be GenTask class's instance.");
        log.debug("add task is {}",task.getClass().toString());
        taskListener.registerGenTask(task);
    }
}
