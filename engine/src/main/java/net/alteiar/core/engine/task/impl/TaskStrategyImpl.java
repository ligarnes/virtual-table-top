package net.alteiar.core.engine.task.impl;

import net.alteiar.core.engine.PlatformContext;
import net.alteiar.core.engine.task.Task;
import net.alteiar.core.engine.task.TaskStatus;
import net.alteiar.core.engine.task.TaskStrategy;
import net.alteiar.layouts.ThreadTaskLogger;

import org.slf4j.LoggerFactory;

public class TaskStrategyImpl implements TaskStrategy {

    public void executeTask(Task task) throws Throwable {

        try {

            ThreadTaskLogger.setTaskId(task.getId());

            PlatformContext.getInstance().getTaskHistory().changeStatus(task, TaskStatus.START);

            task.initializeTask();

            PlatformContext.getInstance().getTaskHistory().changeStatus(task, TaskStatus.INITIALIZED);

            task.execute();

            PlatformContext.getInstance().getTaskHistory().changeStatus(task, TaskStatus.SUCCESS);

        } catch (Throwable ex) {

            PlatformContext.getInstance().getTaskHistory().changeStatus(task, TaskStatus.FAILED);

            throw ex;
        } finally {

            try {

                task.finalizeTask();
            } catch (Throwable ex) {

                LoggerFactory.getLogger(TaskStrategyImpl.class).debug("Exception occur during finalization", ex);
            }
        }
    }
}
