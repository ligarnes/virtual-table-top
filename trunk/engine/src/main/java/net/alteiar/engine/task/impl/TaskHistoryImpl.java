package net.alteiar.engine.task.impl;

import java.util.ArrayList;
import java.util.Date;

import net.alteiar.engine.task.Task;
import net.alteiar.engine.task.TaskHistory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskHistoryImpl implements TaskHistory {

	private final Logger logger = LoggerFactory
			.getLogger(TaskHistoryImpl.class);

	private final ArrayList<TaskHistoryInfo> history;

	public TaskHistoryImpl() {

		history = new ArrayList<TaskHistoryImpl.TaskHistoryInfo>();
	}

	@Override
	public void changeStatus(Task task, String status) {

		history.add(create(task, status));

		statusToString(create(task, status));

	}

	private void statusToString(TaskHistoryInfo taskHistoryInfo) {

		StringBuilder line = new StringBuilder();

		line.append(taskHistoryInfo.getTaskId());
		line.append(";");
		line.append(taskHistoryInfo.getParentId());
		line.append(";");
		line.append(taskHistoryInfo.getTaskClass());
		line.append(";");
		line.append(taskHistoryInfo.getStatus());
		line.append(";");
		line.append(taskHistoryInfo.getTaskClass());
		line.append(";");

		logger.debug(line.toString());
	}

	public void saveHistory() {

		StringBuilder line = new StringBuilder();

		line.append("Task Id;Parent Id;Task Class;Status;Time");

		System.out.println(line);

		for (TaskHistoryInfo taskHistoryInfo : history) {

			line = new StringBuilder();

			line.append(taskHistoryInfo.getTaskId());
			line.append(";");
			line.append(taskHistoryInfo.getParentId());
			line.append(";");
			line.append(taskHistoryInfo.getTaskClass());
			line.append(";");
			line.append(taskHistoryInfo.getStatus());
			line.append(";");
			line.append(taskHistoryInfo.getTaskClass());
			line.append(";");
			line.append(taskHistoryInfo.getTime());

			System.out.println(line);
		}
	}

	private TaskHistoryInfo create(Task task, String status) {

		TaskHistoryInfo info = new TaskHistoryInfo();

		info.setTaskId(task.getId());
		info.setParentId(task.getParentId());
		info.setStatus(status);
		info.setTaskClass(task.getClass().getSimpleName());
		info.setTime(new Date());

		return info;
	}

	private class TaskHistoryInfo {

		private Long taskId;
		private Long parentId;
		private String taskClass;
		private String status;
		private Date time;

		public Long getTaskId() {
			return taskId;
		}

		public void setTaskId(Long taskId) {
			this.taskId = taskId;
		}

		public Long getParentId() {
			return parentId;
		}

		public void setParentId(Long parentId) {
			this.parentId = parentId;
		}

		public String getTaskClass() {
			return taskClass;
		}

		public void setTaskClass(String taskClass) {
			this.taskClass = taskClass;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public Date getTime() {
			return time;
		}

		public void setTime(Date time) {
			this.time = time;
		}
	}
}
