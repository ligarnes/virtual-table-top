package net.alteiar.core.engine.task.history.dao;

import net.alteiar.core.dao.Dao;

public interface TaskHistoryDao extends Dao {

    Long createId();

    void update(TaskHistoryEntry historyEntry);

    String getStatus(long taskId);

}