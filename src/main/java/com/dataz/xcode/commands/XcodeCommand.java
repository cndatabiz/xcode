package com.dataz.xcode.commands;

import com.dataz.xcode.data.IMetaData;
import com.dataz.xcode.data.MetaDataFactory;
import com.dataz.xcode.entity.CmEntity;
import com.dataz.xcode.facade.Xcode;
import com.dataz.xcode.gencodes.GenTask;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 功能描述:
 *
 * @author tommy create on 2019-01-29-13:51
 */
@ShellComponent
@Log4j2
public class XcodeCommand {
    private final ApplicationContext context;
    private final Xcode xcode;
    private final MetaDataFactory factory;

    public XcodeCommand(ApplicationContext context, Xcode xcode, MetaDataFactory factory) {
        this.context = context;
        this.xcode = xcode;
        this.factory = factory;
    }

    @ShellMethod("generate spring mvc & jpa code by table.")
    public void xcode(@ShellOption(defaultValue="",value = {"-t","-tables"}) String tables) throws IOException {
        log.debug("xcode's generate file tasks start.");

        Map<String, GenTask> genTaskMap = context.getBeansOfType(GenTask.class);
        xcode.registerTask(genTaskMap);

        xcode.startTask(tables);
        log.debug("xcode's generate file tasks execute finished.");
    }

    @ShellMethod("list table by query result.")
    public String ls(@ShellOption(value = {"-c","-n"}) String tableName){
        IMetaData metaData = factory.getMetaData();
        List<CmEntity> entityList = metaData.queryEntityList(tableName);
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        entityList.forEach(entity ->
                           sb.append(entity.getEntityDesc()).append("\t\t")
                             .append(entity.getEntityName()).append("\n"));
        sb.append("============= total: ").append(entityList.size()).append(" =============");

        return sb.toString();
    }
}
