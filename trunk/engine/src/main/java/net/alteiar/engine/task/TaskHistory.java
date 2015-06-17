package net.alteiar.engine.task;


public interface TaskHistory {

	void changeStatus(Task task, TaskStatus status);

	TaskStatus getStatus(Task task);
}
