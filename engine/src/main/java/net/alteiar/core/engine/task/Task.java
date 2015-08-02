package net.alteiar.core.engine.task;

public interface Task {

    void setId(long id);

    void setParentId(Long parentId);

    /**
     * retrieve task id
     *
     * @return
     */
    Long getId();

    /**
     * retrieve the parent id
     *
     * @return null if no parent
     */
    Long getParentId();

    /**
     * initialize the task
     */
    void initializeTask();

    /**
     * execute the task
     */
    void execute() throws Exception;

    /**
     * finalize the task
     * <p/>
     * always execute after the task, even if the task fail
     */
    void finalizeTask();

    /**
     * get the status of the task
     *
     * @return the status of the task
     */
    TaskStatus getStatus();

    /**
     * change the status of the task
     *
     * @param status
     *            the new task status
     */
    void setStatus(TaskStatus status);
}
