package net.alteiar.core.dao.impl;

import javax.sql.DataSource;

import net.alteiar.core.dao.Dao;
import net.alteiar.core.dao.listener.DaoObservable;
import net.alteiar.core.dao.listener.DataObservableImpl;

import org.springframework.jdbc.core.JdbcTemplate;

public class DaoObservableImpl extends DataObservableImpl implements Dao, DaoObservable {

    private JdbcTemplate jdbcTemplate;

    @Override
    public void setDatasource(DataSource ds) {

        jdbcTemplate = new JdbcTemplate(ds);
    }

    protected JdbcTemplate getJdbcTemplate() {

        return jdbcTemplate;
    }
}
