package net.alteiar.core.engine.task.impl;

import net.alteiar.core.engine.task.Task;
import net.alteiar.core.engine.task.TaskStatus;

public abstract class TaskBase implements Task {

    private Long id;

    private Long parentId;

    private TaskStatus status;

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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

    @Override
    public TaskStatus getStatus() {
        return status;
    }

    @Override
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
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
        TaskBase other = (TaskBase) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        return true;
    }
}
