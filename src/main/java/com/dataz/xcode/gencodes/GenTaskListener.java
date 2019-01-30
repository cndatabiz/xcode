package com.dataz.xcode.gencodes;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能描述:
 *
 * @author tommy create on 2018-05-01-14:33
 */

@Component
public class GenTaskListener {

    private List<GenTask> taskList = new ArrayList<>();

    void registerGenTask(GenTask task){
        Assert.notNull(task, "task can't be null.");
        taskList.add(task);
    }

    public List<GenTask> list(){
        return taskList;
    }
}
