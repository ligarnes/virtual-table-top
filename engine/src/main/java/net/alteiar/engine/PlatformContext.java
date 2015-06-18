package net.alteiar.engine;

import net.alteiar.engine.task.TaskEngine;
import net.alteiar.engine.task.TaskHistory;
import net.alteiar.engine.task.impl.TaskEngineImpl;
import net.alteiar.engine.task.impl.TaskExecutorManager;
import net.alteiar.engine.task.impl.TaskHistoryImpl;
import net.alteiar.engine.task.impl.TaskStrategy;

public class PlatformContext {

	private static final PlatformContext INSTANCE = new PlatformContext();

	public static PlatformContext getInstance() {

		return INSTANCE;
	}

	private final TaskEngine taskEngine;

	private final TaskHistory taskHistory;

	private TaskExecutorManager executors;

	private long uniqueId;

	public PlatformContext() {

		taskEngine = new TaskEngineImpl();
		taskHistory = new TaskHistoryImpl();

		uniqueId = 0;
	}

	public void initialize() {

		TaskStrategy strategy = new TaskStrategy();

		executors = new TaskExecutorManager();
		executors.initialize(5, strategy);
		executors.start();
	}

	public void shutdown() {

		executors.stop();
	}

	public TaskEngine getTaskEngine() {

		return taskEngine;
	}

	public TaskHistory getTaskHistory() {

		return taskHistory;
	}

	public long nextTaskId() {

		uniqueId++;

		return uniqueId;
	}
}
