package net.alteiar.core.engine.task.history.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.alteiar.core.dao.impl.DaoObservableImpl;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class TaskHistoryDaoImpl extends DaoObservableImpl implements TaskHistoryDao {

    private static final String UPDATE_TASK_HISTORY = "UPDATE task_history SET";

    private static final String INSERT = "INSERT INTO task_history (creation_time, modification_time) VALUES (?, ?)";

    private static final String FIND_STATUS_BY_ID = "SELECT status from task_history WHERE task_id=?";

    /*
     * (non-Javadoc)
     *
     * @see net.alteiar.engine.task.dao.TaskHistoryDao#createId()
     */
    @Override
    public Long createId() {

        KeyHolder keyHolder = new GeneratedKeyHolder();

        PreparedStatementCreator creator = conn -> {

            PreparedStatement preparedStatement = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setTimestamp(1, new Timestamp(new Date().getTime()));
            preparedStatement.setTimestamp(2, new Timestamp(new Date().getTime()));

            return preparedStatement;
        };

        getJdbcTemplate().update(creator, keyHolder);

        return (Long) keyHolder.getKey();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * net.alteiar.engine.task.dao.TaskHistoryDao#update(net.alteiar.engine.
     * task.dao.TaskHistoryEntry)
     */
    @Override
    public void update(TaskHistoryEntry historyEntry) {

        StringBuilder queryBuilder = new StringBuilder(UPDATE_TASK_HISTORY);

        List<Object> parameters = new ArrayList<Object>();

        queryBuilder.append(" modification_time=? ");
        parameters.add(new Date());

        if (historyEntry.getParentId() != null) {

            queryBuilder.append(" parent_id=? ");
            parameters.add(historyEntry.getParentId());
        }

        if (historyEntry.getCreationTime() != null) {

            queryBuilder.append(", creation_time=? ");
            parameters.add(historyEntry.getCreationTime());
        }

        if (historyEntry.getStartTime() != null) {

            queryBuilder.append(", start_time=? ");
            parameters.add(historyEntry.getStartTime());
        }

        if (historyEntry.getEndTime() != null) {

            queryBuilder.append(", end_time=? ");
            parameters.add(historyEntry.getEndTime());
        }

        if (historyEntry.getStatus() != null) {

            queryBuilder.append(", status=? ");
            parameters.add(historyEntry.getStatus());
        }

        if (historyEntry.getTaskName() != null) {

            queryBuilder.append(", task_name=? ");
            parameters.add(historyEntry.getTaskName());
        }

        queryBuilder.append(" WHERE task_id=?");
        parameters.add(historyEntry.getId());

        getJdbcTemplate().update(queryBuilder.toString(), parameters.toArray());
    }

    /*
     * (non-Javadoc)
     *
     * @see net.alteiar.engine.task.dao.TaskHistoryDao#getStatus(long)
     */
    @Override
    public String getStatus(long taskId) {

        return getJdbcTemplate().queryForObject(FIND_STATUS_BY_ID, String.class, taskId);
    }
}
