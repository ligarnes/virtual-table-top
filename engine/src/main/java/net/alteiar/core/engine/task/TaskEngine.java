package net.alteiar.core.engine.task;

public interface TaskEngine {

    /**
     * Set the execution strategy of the tasks
     *
     * @param strategy
     */
    void setStrategy(TaskStrategy strategy);

    /**
     * Enqueue a new task
     *
     * @param task
     * @return task id
     */
    long enqueue(Task task);

    /**
     * Execute a new request
     *
     * @param task
     */
    void execute(Task task);
}
