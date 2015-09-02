package net.alteiar;

import net.alteiar.core.engine.PlatformContext;
import net.alteiar.core.engine.persistence.PersistenceEngine;
import net.alteiar.core.engine.persistence.PersistenceEngineImpl;
import net.alteiar.core.engine.task.TaskEngine;
import net.alteiar.core.engine.task.history.TaskHistory;
import net.alteiar.core.engine.task.history.TaskHistoryImpl;
import net.alteiar.core.engine.task.impl.LocalTaskEngineImpl;

import org.h2.jdbcx.JdbcDataSource;

public class PlatformContextTestMocked extends PlatformContext {

    public PlatformContextTestMocked() {

        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:./test");
        ds.setUser("sa");
        ds.setPassword("sa");

        setPersistenceEngine(new PersistenceEngineImpl(ds));

        setTaskEngine(new LocalTaskEngineImpl());
        setTaskHistory(new TaskHistoryImpl());
    }

    public void setTaskEngineImpl(TaskEngine taskEngine) {

        setTaskEngine(taskEngine);
    }

    public void setPersistenceEngineImpl(PersistenceEngine persistenceEngine) {

        setPersistenceEngine(persistenceEngine);
    }

    public void setTaskHistoryImpl(TaskHistory taskHistory) {

        setTaskHistory(taskHistory);
    }
}
