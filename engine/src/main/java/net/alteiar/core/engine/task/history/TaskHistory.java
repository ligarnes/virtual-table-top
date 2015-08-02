package net.alteiar.core.engine.task.history;

import net.alteiar.core.engine.task.Task;
import net.alteiar.core.engine.task.TaskStatus;

public interface TaskHistory {

    void start();

    void shutdown();

    Long createTask(Task task);

    void changeStatus(Task task, TaskStatus status);

    TaskStatus getStatus(Long taskId);

    TaskStatus waitFor(Long taskId);

    TaskStatus waitFor(Long taskId, Long timeInMs);
}
