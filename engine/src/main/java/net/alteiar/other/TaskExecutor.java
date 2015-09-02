package net.alteiar.other;

import java.util.concurrent.BlockingQueue;

import net.alteiar.core.engine.PlatformContext;
import net.alteiar.core.engine.task.Task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskExecutor implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(TaskExecutor.class);

    private boolean isRunning;

    private final BlockingQueue<Task> taskQueue;

    public TaskExecutor(BlockingQueue<Task> taskQueue) {

        this.taskQueue = taskQueue;
    }

    public void initialize() {

        isRunning = true;
    }

    public void stop() {

        isRunning = false;
    }

    @Override
    public void run() {

        while (isRunning) {

            logger.trace("Executor {} is waiting for a task", Thread.currentThread().getName());
            Task task = null;

            try {

                task = taskQueue.take();
            } catch (InterruptedException e1) {

                logger.debug("Thread is interrupted while waiting for a task to execute", e1);
            }

            if (task != null) {

                logger.trace("Executor {} execute {}", Thread.currentThread().getName(), task.getClass()
                        .getSimpleName());

                try {
                    PlatformContext.getInstance().getTaskEngine().execute(task);
                } catch (Throwable e) {

                    logger.debug("An exception occur while executing the task {} with id {}", task.getClass(),
                            task.getId(), e);
                }
            }
        }

        logger.trace("Executor {} is stopped", Thread.currentThread().getName());
    }
}
