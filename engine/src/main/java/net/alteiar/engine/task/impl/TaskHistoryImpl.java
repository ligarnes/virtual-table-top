package net.alteiar.engine.task.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.alteiar.engine.task.Task;
import net.alteiar.engine.task.TaskHistory;
import net.alteiar.engine.task.TaskStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TaskHistoryImpl implements TaskHistory {

	private final Logger logger = LoggerFactory.getLogger("net.ateiar.TaskHistory");

	private final SimpleDateFormat dateFormat;

	private final Map<Long, TaskStatus> taskStatus;

	public TaskHistoryImpl() {

		dateFormat = new SimpleDateFormat("hh:mm:ss");
		taskStatus = new HashMap<Long, TaskStatus>();
	}

	@Override
	public void changeStatus(Task task, TaskStatus status) {

		taskStatus.put(task.getId(), status);
		statusToString(create(task, status.getStatus()));
	}

	public TaskStatus getStatus(Task task) {

		return taskStatus.get(task);
	}

	private synchronized void statusToString(TaskHistoryInfo taskHistoryInfo) {

		StringBuilder line = new StringBuilder();

		line.append(dateFormat.format(taskHistoryInfo.getTime()));
		line.append(";");
		line.append(taskHistoryInfo.getTaskId());
		line.append(";");
		line.append(taskHistoryInfo.getParentId());
		line.append(";");
		line.append(taskHistoryInfo.getTaskClass());
		line.append(";");
		line.append(taskHistoryInfo.getStatus());
		line.append(";");

		logger.info(line.toString());
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
