package net.alteiar.other;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import net.alteiar.core.engine.task.Task;
import net.alteiar.module.Module;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskExecutorManager implements Module {
    private final Logger logger;

    private final List<Thread> threads;
    private final List<TaskExecutor> executors;

    private int count;
    private BlockingQueue<Task> taskQueue;

    public TaskExecutorManager() {

        this(10);
    }

    public TaskExecutorManager(int count) {

        logger = LoggerFactory.getLogger(getClass());

        executors = new ArrayList<TaskExecutor>();
        threads = new ArrayList<Thread>();

        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setTaskQueue(BlockingQueue<Task> taskQueue) {
        this.taskQueue = taskQueue;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public void initialize() {

        logger.debug("Initializing {} ...", TaskExecutorManager.class.getSimpleName());

        if (taskQueue == null) {

            throw new IllegalStateException("A task queue must be set");
        }

        for (int i = 0; i < count; i++) {

            TaskExecutor executor = new TaskExecutor(taskQueue);
            executor.initialize();

            executors.add(executor);
        }

        logger.debug("{} initialized", TaskExecutorManager.class.getSimpleName());
    }

    @Override
    public void start() {

        logger.debug("Starting {} ...", TaskExecutorManager.class.getSimpleName());

        for (int i = 0; i < executors.size(); ++i) {

            Thread tr = new Thread(executors.get(i), "Task-executor-" + i);
            tr.start();

            threads.add(tr);
        }

        logger.debug("{} started", TaskExecutorManager.class.getSimpleName());
    }

    @Override
    public void shutdown() {

        logger.debug("Shuting down {} ...", TaskExecutorManager.class.getSimpleName());

        for (TaskExecutor taskExecutor : executors) {
            taskExecutor.stop();
        }

        for (Thread tr : threads) {
            tr.interrupt();
        }

        for (Thread tr : threads) {

            try {
                tr.join();
            } catch (InterruptedException e) {

            }
        }
        logger.debug("{} is shutdown!", TaskExecutorManager.class.getSimpleName());
    }

}
