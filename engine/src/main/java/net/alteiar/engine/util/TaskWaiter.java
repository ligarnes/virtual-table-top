package net.alteiar.engine.util;

import net.alteiar.engine.PlatformContext;
import net.alteiar.engine.task.Task;
import net.alteiar.engine.task.TaskStatus;

import org.slf4j.LoggerFactory;

public class TaskWaiter {

	private final long waitSlotInMs;

	public TaskWaiter() {

		this(100);
	}

	public TaskWaiter(long waitSlotInMs) {

		this.waitSlotInMs = waitSlotInMs;
	}

	public TaskStatus waitFor(Task task, long timeout) {

		TaskStatus status = PlatformContext.getInstance().getTaskHistory().getStatus(task);

		long begin = System.currentTimeMillis();

		while (!status.isFinal() && (System.currentTimeMillis() - begin < timeout)) {

			try {

				this.wait(waitSlotInMs);
			} catch (InterruptedException e) {
				LoggerFactory.getLogger(getClass()).debug("Interrupt before end waiting time", e);
			}

			status = PlatformContext.getInstance().getTaskHistory().getStatus(task);
		}

		return status;
	}

}
