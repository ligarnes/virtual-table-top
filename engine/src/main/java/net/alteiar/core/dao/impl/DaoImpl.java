package net.alteiar.core.dao.impl;

import javax.sql.DataSource;

import net.alteiar.core.dao.Dao;

import org.springframework.jdbc.core.JdbcTemplate;

public class DaoImpl implements Dao {

    private JdbcTemplate jdbcTemplate;

    @Override
    public void setDatasource(DataSource ds) {

        jdbcTemplate = new JdbcTemplate(ds);
    }

    protected JdbcTemplate getJdbcTemplate() {

        return jdbcTemplate;
    }
}
