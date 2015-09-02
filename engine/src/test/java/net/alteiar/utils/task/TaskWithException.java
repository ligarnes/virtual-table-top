package net.alteiar.utils.task;

import net.alteiar.core.engine.task.impl.TaskBase;

public class TaskWithException extends TaskBase {

    private final Exception ex;

    public TaskWithException() {

        this(new IllegalAccessException());
    }

    public TaskWithException(Exception ex) {

        this.ex = ex;
    }

    @Override
    public void execute() throws Exception {

        throw ex;
    }
}
