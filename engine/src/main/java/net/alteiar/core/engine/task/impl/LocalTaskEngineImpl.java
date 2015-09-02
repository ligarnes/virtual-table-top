package net.alteiar.core.engine.task.impl;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import net.alteiar.core.engine.PlatformContext;
import net.alteiar.core.engine.task.Task;
import net.alteiar.core.engine.task.TaskEngine;
import net.alteiar.core.engine.task.TaskStatus;
import net.alteiar.core.engine.task.TaskStrategy;

public class LocalTaskEngineImpl implements TaskEngine {

    private final BlockingQueue<Task> tasks;

    private TaskStrategy strategy;

    public LocalTaskEngineImpl() {

        tasks = new LinkedBlockingQueue<Task>();
    }

    @Override
    public void setStrategy(TaskStrategy strategy) {

        this.strategy = strategy;
    }

    @Override
    public long enqueue(Task task) {

        if (task.getId() == null) {

            long id = PlatformContext.getInstance().getTaskHistory().createTask(task);
            task.setId(id);
        }

        PlatformContext.getInstance().getTaskHistory().changeStatus(task, TaskStatus.IN_QUEUE);
        tasks.offer(task);

        return task.getId();
    }

    public BlockingQueue<Task> getTaskQueue() {

        return tasks;
    }

    @Override
    public void execute(Task task) {

        if (task.getId() == null) {

            long id = PlatformContext.getInstance().getTaskHistory().createTask(task);
            task.setId(id);
        }

        strategy.executeTask(task);
    }

}
