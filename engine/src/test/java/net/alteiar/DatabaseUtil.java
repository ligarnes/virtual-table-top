package net.alteiar;

import net.alteiar.core.engine.PlatformContext;

import org.springframework.jdbc.core.JdbcTemplate;

public class DatabaseUtil {

    public static JdbcTemplate createJdbcTemplate() {

        return new JdbcTemplate(PlatformContext.getInstance().getPersistenceEngine().getDataSource());
    }
}
