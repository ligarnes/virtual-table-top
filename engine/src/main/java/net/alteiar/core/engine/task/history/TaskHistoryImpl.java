package net.alteiar.core.engine.task.history;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import net.alteiar.core.engine.task.Task;
import net.alteiar.core.engine.task.TaskStatus;
import net.alteiar.core.engine.task.history.dao.DaoFactorySingleton;
import net.alteiar.core.engine.task.history.dao.TaskHistoryDao;
import net.alteiar.core.engine.task.history.dao.TaskHistoryEntry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskHistoryImpl implements TaskHistory {

    private final Logger logger = LoggerFactory.getLogger(TaskHistory.class);

    private final Map<Long, TaskStatus> cache;

    private final Object notifier;
    private final DatabaseSaver dbSaver;

    public TaskHistoryImpl() {

        notifier = new Object();

        cache = new HashMap<Long, TaskStatus>();

        dbSaver = new DatabaseSaver();
    }

    @Override
    public void start() {

        logger.debug("Starting {} ...", TaskHistoryImpl.class.getSimpleName());
        dbSaver.start();
        logger.debug("{} started", TaskHistoryImpl.class.getSimpleName());
    }

    @Override
    public void shutdown() {
        logger.debug("Shutting down {} ...", TaskHistoryImpl.class.getSimpleName());
        dbSaver.shutdown();
        logger.debug("{} shutdown", TaskHistoryImpl.class.getSimpleName());
    }

    @Override
    public Long createTask(Task task) {

        TaskHistoryDao dao = DaoFactorySingleton.getInstance().getTaskHistoryDao();

        task.setStatus(TaskStatus.NEW);

        Long id = dao.createId();

        logger.debug("Create new Task with id {}", id);

        return id;
    }

    @Override
    public void changeStatus(Task task, TaskStatus status) {

        logger.debug("Change status of task {} from {} to {}", task.getId(), task.getStatus(), status);

        task.setStatus(status);

        cache.put(task.getId(), status);

        TaskHistoryEntry entry = new TaskHistoryEntry();
        entry.setTask(task);

        if (TaskStatus.START.equals(status)) {

            entry.setStartTime(new Date());
        }

        if (status.isFinal()) {

            entry.setEndTime(new Date());
        }

        dbSaver.enqueue(entry);

        synchronized (notifier) {

            notifier.notifyAll();
        }
    }

    @Override
    public TaskStatus getStatus(Long taskId) {

        TaskStatus tStatus = cache.get(taskId);

        if (tStatus == null) {

            TaskHistoryDao dao = DaoFactorySingleton.getInstance().getTaskHistoryDao();
            String status = dao.getStatus(taskId);

            if (status != null) {
                tStatus = TaskStatus.valueOf(status);
            } else {
                tStatus = TaskStatus.FAILED;
            }
        }

        return tStatus;
    }

    @Override
    public TaskStatus waitFor(Long taskId) {

        return waitFor(taskId, Long.MAX_VALUE);
    }

    @Override
    public TaskStatus waitFor(Long taskId, Long timeInMs) {

        TaskStatus tStatus = getStatus(taskId);

        Long before = System.currentTimeMillis();

        Long duration = System.currentTimeMillis() - before;

        while (!tStatus.isFinal() && duration < timeInMs) {

            try {

                synchronized (notifier) {

                    tStatus = getStatus(taskId);

                    if (!tStatus.isFinal()) {

                        notifier.wait(timeInMs);
                    }
                }
            } catch (InterruptedException e) {

                logger.debug("Interrupt before end waiting time", e);
            }

            tStatus = getStatus(taskId);

            duration = System.currentTimeMillis() - before;
        }

        return tStatus;
    }

    private static class DatabaseSaver implements Runnable {

        private boolean terminate;
        private final LinkedBlockingQueue<TaskHistoryEntry> entries;

        private final Thread tr;

        public DatabaseSaver() {

            entries = new LinkedBlockingQueue<TaskHistoryEntry>();
            tr = new Thread(this, "TaskHistory datasaver");
        }

        public void enqueue(TaskHistoryEntry entry) {

            entries.add(entry);
        }

        public void start() {

            terminate = false;
            tr.start();
        }

        public void shutdown() {

            terminate = true;
            tr.interrupt();

            try {
                tr.join(10000L);
            } catch (InterruptedException e) {
                LoggerFactory.getLogger(TaskHistory.class).debug(
                        "Interrupt while terminating task history database saver", e);
            }
        }

        protected TaskHistoryEntry dequeue() throws InterruptedException {

            return entries.take();
        }

        @Override
        public void run() {

            while (!terminate || !entries.isEmpty()) {

                TaskHistoryEntry entry;
                try {
                    entry = dequeue();

                    if (entry != null) {

                        TaskHistoryDao dao = DaoFactorySingleton.getInstance().getTaskHistoryDao();
                        dao.update(entry);
                    }
                } catch (InterruptedException e) {
                    LoggerFactory.getLogger(TaskHistory.class).debug("Interrupt while waiting for task history", e);
                }
            }
        }
    }
}
