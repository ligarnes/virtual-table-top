package net.alteiar.core.engine.persistence;

import javax.sql.DataSource;

public interface PersistenceEngine {

    DataSource getDataSource();
}
