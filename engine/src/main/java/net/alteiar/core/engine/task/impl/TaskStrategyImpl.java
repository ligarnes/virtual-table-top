package net.alteiar.core.engine.task.impl;

import net.alteiar.core.engine.PlatformContext;
import net.alteiar.core.engine.task.Task;
import net.alteiar.core.engine.task.TaskEngine;
import net.alteiar.core.engine.task.TaskStatus;
import net.alteiar.core.engine.task.TaskStrategy;
import net.alteiar.layouts.ThreadTaskLogger;

import org.slf4j.LoggerFactory;

public class TaskStrategyImpl implements TaskStrategy {

    @Override
    public void executeTask(Task task) {

        try {

            ThreadTaskLogger.setTaskId(task.getId());

            PlatformContext.getInstance().getTaskHistory().changeStatus(task, TaskStatus.STARTED);

            task.initializeTask();

            PlatformContext.getInstance().getTaskHistory().changeStatus(task, TaskStatus.INITIALIZED);

            task.execute();

            PlatformContext.getInstance().getTaskHistory().changeStatus(task, TaskStatus.SUCCEED);

        } catch (Throwable ex) {

            PlatformContext.getInstance().getTaskHistory().changeStatus(task, TaskStatus.FAILED);

            LoggerFactory.getLogger(TaskEngine.class).debug(
                    "An exception occur while executing the task {} with id {}", task.getClass(), task.getId(), ex);
        } finally {

            try {

                task.finalizeTask();
            } catch (Throwable ex) {

                LoggerFactory.getLogger(TaskStrategy.class).debug("Exception occur during finalization", ex);
            }
        }
    }
}
