package net.alteiar.engine.task.impl;

import net.alteiar.engine.PlatformContext;
import net.alteiar.engine.task.Task;

import org.slf4j.LoggerFactory;

public class TaskExecutor implements Runnable {

	// private static final Logger LOGGER = LoggerFactory
	// .getLogger(TaskExecutor.class);

	private boolean isRunning;

	private TaskStrategy strategy;

	public void initialize(TaskStrategy taskStrategy) {

		isRunning = true;

		strategy = taskStrategy;
	}

	public void stop() {

		isRunning = false;
	}

	@Override
	public void run() {

		while (isRunning) {

			// LOGGER.debug("Executor {} is waiting for a task", Thread
			// .currentThread().getName());
			Task task = null;

			task = PlatformContext.getInstance().getTaskEngine().dequeue();

			if (task != null) {

				// LOGGER.debug("Executor {} execute {}", Thread.currentThread()
				// .getName(), task.getClass().getSimpleName());

				try {
					strategy.executeTask(task);
				} catch (Throwable e) {

					LoggerFactory
							.getLogger(TaskExecutor.class)
							.debug("An exception occur while executing the task {} with id {}",
									task.getClass(), task.getId(), e);
				}
			}
		}

		// LOGGER.debug("Executor {} is stopped",
		// Thread.currentThread().getName());
	}
}
