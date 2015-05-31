package net.alteiar.engine.task.impl;

import net.alteiar.engine.PlatformContext;
import net.alteiar.engine.task.Task;
import net.alteiar.engine.task.TaskStatus;

import org.slf4j.LoggerFactory;

public class TaskStrategy {

	public void executeTask(Task task) throws Throwable {

		try {

			PlatformContext.getInstance().getTaskHistory()
					.changeStatus(task, TaskStatus.BEGIN);

			task.initializeTask();

			PlatformContext.getInstance().getTaskHistory()
					.changeStatus(task, TaskStatus.INITIALIZED);

			task.execute();

			PlatformContext.getInstance().getTaskHistory()
					.changeStatus(task, TaskStatus.SUCCESS);

		} catch (Throwable ex) {

			PlatformContext.getInstance().getTaskHistory()
					.changeStatus(task, TaskStatus.FAILED);

			throw ex;
		} finally {

			try {

				task.finalizeTask();
			} catch (Throwable ex) {

				LoggerFactory.getLogger(TaskStrategy.class).debug(
						"Exception occur during finalization", ex);
			}
		}
	}
}
