package net.alteiar;

import net.alteiar.core.engine.PlatformContext;
import net.alteiar.core.engine.persistence.PersistenceEngineImpl;
import net.alteiar.core.engine.task.history.TaskHistoryImpl;
import net.alteiar.core.engine.task.impl.LocalTaskEngineImpl;

import org.h2.jdbcx.JdbcDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class PlatformContextTestImpl extends PlatformContext {

    public PlatformContextTestImpl() {

        JdbcDataSource ds = new JdbcDataSource();
        ds.setURL("jdbc:h2:./test");
        ds.setUser("sa");
        ds.setPassword("sa");

        setPersistenceEngine(new PersistenceEngineImpl(ds));

        setTaskEngine(new LocalTaskEngineImpl());
        setTaskHistory(new TaskHistoryImpl());
    }

    public void clearDatabase() {

        // drop all
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getPersistenceEngine().getDataSource());
        jdbcTemplate.execute("DROP ALL OBJECTS DELETE FILES");
    }
}
