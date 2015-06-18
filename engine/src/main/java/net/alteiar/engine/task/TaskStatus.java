package net.alteiar.engine.task;

public class TaskStatus {

	public static final TaskStatus IN_QUEUE = new TaskStatus("IN_QUEUE", false);
	public static final TaskStatus BEGIN = new TaskStatus("BEGIN", false);
	public static final TaskStatus INITIALIZED = new TaskStatus("INITIALIZED", false);
	public static final TaskStatus SUCCESS = new TaskStatus("SUCCESS", true);
	public static final TaskStatus FAILED = new TaskStatus("FAILED", true);

	private final String status;

	private final boolean isFinal;

	private TaskStatus(String status, boolean isFinal) {

		this.status = status;
		this.isFinal = isFinal;
	}

	public String getStatus() {
		return status;
	}

	public boolean isFinal() {
		return isFinal;
	}

	@Override
	public String toString() {

		return status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}

		TaskStatus other = (TaskStatus) obj;
		if (status == null) {
			if (other.status != null) {
				return false;
			}
		} else if (!status.equals(other.status)) {
			return false;
		}
		return true;
	}

}
