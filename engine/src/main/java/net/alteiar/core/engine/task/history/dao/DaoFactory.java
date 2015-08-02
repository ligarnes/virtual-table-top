package net.alteiar.core.engine.task.history.dao;

public interface DaoFactory {

    void initialize();

    TaskHistoryDao getTaskHistoryDao();
}