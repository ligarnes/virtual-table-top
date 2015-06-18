package net.alteiar.engine.task;

public interface TaskEngine {

	// void initialize(TaskStrategy strategy);

	void enqueue(Task task);

	// void executeSync(Task task);

	Task dequeue();

}
