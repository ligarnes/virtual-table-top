package net.alteiar.core.engine.persistence;

import javax.sql.DataSource;

public class PersistenceEngineImpl implements PersistenceEngine {

    private final DataSource ds;

    public PersistenceEngineImpl(DataSource ds) {

        this.ds = ds;
    }

    @Override
    public DataSource getDataSource() {
        return ds;
    }
}
