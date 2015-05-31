package net.alteiar.engine.task.impl;

import java.util.ArrayList;
import java.util.List;

public class TaskExecutorManager {

	private final List<Thread> threads;
	private final List<TaskExecutor> executors;

	public TaskExecutorManager() {

		executors = new ArrayList<TaskExecutor>();
		threads = new ArrayList<Thread>();
	}

	public void initialize(int count, TaskStrategy strategy) {

		for (int i = 0; i < count; i++) {

			TaskExecutor executor = new TaskExecutor();
			executor.initialize(strategy);

			executors.add(executor);
		}
	}

	public void start() {

		for (int i = 0; i < executors.size(); ++i) {

			Thread tr = new Thread(executors.get(i), "Task-executor-" + i);
			tr.start();

			threads.add(tr);
		}
	}

	public void stop() {

		for (TaskExecutor taskExecutor : executors) {
			taskExecutor.stop();
		}

		for (Thread tr : threads) {
			tr.interrupt();
		}
	}
}
