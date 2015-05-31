package net.alteiar.engine.task.impl;

import net.alteiar.engine.PlatformContext;
import net.alteiar.engine.task.Task;

public abstract class TaskBase implements Task {

	private final long id;

	private final Long parentId;

	public TaskBase() {

		id = PlatformContext.getInstance().nextTaskId();
		parentId = null;
	}

	public TaskBase(long parentId) {

		id = PlatformContext.getInstance().nextTaskId();
		this.parentId = parentId;
	}

	@Override
	public long getId() {
		return id;
	}

	@Override
	public Long getParentId() {
		return parentId;
	}

	@Override
	public void initializeTask() {
	}

	@Override
	public void finalizeTask() {
	}

}
