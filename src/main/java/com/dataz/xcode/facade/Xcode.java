package com.dataz.xcode.facade;

import com.dataz.xcode.gencodes.GenTask;
import com.dataz.xcode.gencodes.Generator;
import com.dataz.xcode.gencodes.TemplateData;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static com.dataz.xcode.contants.FtlContants.*;

/**
 * 功能描述:
 *
 * @author tommy create on 2018-04-26-15:56
 */
@Log4j2
@Configuration
public class Xcode {
    private final Generator generator;
    private final TemplateData templateData;
    private final ExecutorService executorService ;
    private final Environment env;

    @Value("${base.package.name}")
    private String basePackName;

    @Value("${xcode.tables}")
    private String tables;

    public Xcode(Generator generator, TemplateData templateData, ExecutorService executorService, Environment env) {
        this.generator = generator;
        this.templateData = templateData;
        this.executorService = executorService;
        this.env = env;
    }

    public void startTask(String todos) throws IOException {

        if (Strings.isNotEmpty(todos)){
            tables = todos;
        }

        // 初始化基本配置
        Map<String, String> baseCfg = Maps.newHashMap();
        String codeDir = env.getProperty("xCode.targetDir","genCode");
        baseCfg.put("xcode.target.dir", codeDir);
        baseCfg.put("base.package.name", basePackName);
        baseCfg.put("base.entity.isExtend", env.getProperty("baseClass.isExtend"));
        baseCfg.put("base.entity.class", env.getProperty("baseClass.name"));
        baseCfg.put("validate.group.enable", env.getProperty("validateGroup.enable"));
        baseCfg.put("validate.group.name", env.getProperty("validateGroup.name"));

        Assert.notNull(tables, "no table need to generate codes.");

        Map<String, Object> context = Maps.newHashMap();
        context.put("xcode", baseCfg);

        context.put(PAGECAGE_NAME, basePackName);

        context.put(IS_EXTENDS, env.getProperty("baseClass.isExtend"));
        context.put(BASE_CLASS, env.getProperty("baseClass.name"));


        Path targetDir = Paths.get(codeDir);
        if (Files.notExists(targetDir)){
            Files.createDirectories(targetDir);
        }

        List<String> tableNames = Lists.newArrayList(tables.split(","));
        List<Future<String>> futureList = exeTasks(tableNames, context, targetDir);

        if (Objects.isNull(futureList)) {
            log.error("exe generate code task failure.");
        }

        for (Future<String> future : futureList) {
            try {
                log.info(future.get());
            } catch (InterruptedException | ExecutionException e) {

                log.error("errorMessage:{}", e);
            }
        }
    }

    private List<Future<String>> exeTasks(List<String> tableNames, Map<String, Object> inMap, Path targetDir){
        List<Callable<String>> callableTasks = new ArrayList<>();
        tableNames.forEach(tableName -> callableTasks.add(() -> {
            Map<String, Object> map = Maps.newHashMap(inMap);
            Map<String, Object> outMap = templateData.buildFtlData(map, tableName);
            generator.doGenFileTask(outMap,targetDir);

            return tableName.concat(" generate finished.");
        }));

        List<Future<String>> futures = null;
        try {
            futures = executorService.invokeAll(callableTasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();

        return futures;
    }

    public void registerTask(Map<String, GenTask> taskMap){
        taskMap.forEach((key, value) -> {
            log.debug("add gen {} task to generator.", key);
            generator.addTask(value);
        });
    }
}
