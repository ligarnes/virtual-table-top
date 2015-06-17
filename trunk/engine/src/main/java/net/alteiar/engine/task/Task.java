package net.alteiar.engine.task;

public interface Task {

	/**
	 * retrieve task id
	 * 
	 * @return
	 */
	long getId();

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

}
