package net.alteiar.core.engine.task.history.dao;

import javax.sql.DataSource;

import net.alteiar.core.engine.PlatformContext;

public class DaoFactoryImpl implements DaoFactory {

    private TaskHistoryDao executionLogDao;

    /*
     * (non-Javadoc)
     * 
     * @see net.alteiar.core.engine.task.history.dao.DaoFactory#initialize()
     */
    @Override
    public void initialize() {

        DataSource ds = PlatformContext.getInstance().getPersistenceEngine().getDataSource();

        executionLogDao = new TaskHistoryDaoImpl();
        executionLogDao.setDatasource(ds);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * net.alteiar.core.engine.task.history.dao.DaoFactory#getTaskHistoryDao()
     */
    @Override
    public TaskHistoryDao getTaskHistoryDao() {
        return executionLogDao;
    }

}
