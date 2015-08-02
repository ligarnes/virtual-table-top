package net.alteiar.core.engine.task.history.dao;

import java.util.Date;

import net.alteiar.core.engine.task.Task;

public class TaskHistoryEntry {

    private Task task;

    private Date creationTime;
    private Date startTime;
    private Date endTime;

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public long getId() {
        return task.getId();
    }

    public Long getParentId() {

        return task.getParentId();
    }

    public String getTaskName() {
        return task.getClass().getSimpleName();
    }

    public String getStatus() {
        return task.getStatus().toString();
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((creationTime == null) ? 0 : creationTime.hashCode());
        result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
        result = prime * result + ((startTime == null) ? 0 : startTime.hashCode());
        result = prime * result + ((task == null) ? 0 : task.hashCode());
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
        TaskHistoryEntry other = (TaskHistoryEntry) obj;
        if (creationTime == null) {
            if (other.creationTime != null) {
                return false;
            }
        } else if (!creationTime.equals(other.creationTime)) {
            return false;
        }
        if (endTime == null) {
            if (other.endTime != null) {
                return false;
            }
        } else if (!endTime.equals(other.endTime)) {
            return false;
        }
        if (startTime == null) {
            if (other.startTime != null) {
                return false;
            }
        } else if (!startTime.equals(other.startTime)) {
            return false;
        }
        if (task == null) {
            if (other.task != null) {
                return false;
            }
        } else if (!task.equals(other.task)) {
            return false;
        }
        return true;
    }

}
