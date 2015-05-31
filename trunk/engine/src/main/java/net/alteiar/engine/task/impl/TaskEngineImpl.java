package net.alteiar.engine.task.impl;

import java.util.concurrent.LinkedBlockingQueue;

import net.alteiar.engine.PlatformContext;
import net.alteiar.engine.task.Task;
import net.alteiar.engine.task.TaskEngine;
import net.alteiar.engine.task.TaskStatus;

public class TaskEngineImpl implements TaskEngine {

	private final LinkedBlockingQueue<Task> tasks;

	public TaskEngineImpl() {

		tasks = new LinkedBlockingQueue<Task>();

	}

	@Override
	public void enqueue(Task task) {

		PlatformContext.getInstance().getTaskHistory()
				.changeStatus(task, TaskStatus.IN_QUEUE);
		tasks.offer(task);
	}

	@Override
	public Task dequeue() {

		Task next = null;
		try {
			next = tasks.take();
		} catch (InterruptedException e) {

			// TODO log
		}

		return next;
	}

}
