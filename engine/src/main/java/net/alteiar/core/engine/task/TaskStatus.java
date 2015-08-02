package net.alteiar.core.engine.task;

public class TaskStatus {

    public static final TaskStatus IN_QUEUE = new TaskStatus("IN_QUEUE", false);
    public static final TaskStatus NEW = new TaskStatus("NEW", false);
    public static final TaskStatus START = new TaskStatus("START", false);
    public static final TaskStatus INITIALIZED = new TaskStatus("INITIALIZED", false);
    public static final TaskStatus SUCCESS = new TaskStatus("SUCCEED", true);
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

    public static TaskStatus valueOf(String status) {

        TaskStatus valueOf = null;

        if (IN_QUEUE.getStatus().equalsIgnoreCase(status)) {

            valueOf = IN_QUEUE;
        } else if (NEW.getStatus().equalsIgnoreCase(status)) {

            valueOf = NEW;
        } else if (START.getStatus().equalsIgnoreCase(status)) {

            valueOf = START;
        } else if (INITIALIZED.getStatus().equalsIgnoreCase(status)) {

            valueOf = INITIALIZED;
        } else if (SUCCESS.getStatus().equalsIgnoreCase(status)) {

            valueOf = SUCCESS;
        } else if (FAILED.getStatus().equalsIgnoreCase(status)) {

            valueOf = FAILED;
        }

        return valueOf;
    }
}
