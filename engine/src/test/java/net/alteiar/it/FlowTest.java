package net.alteiar.it;

import java.util.ArrayList;
import java.util.List;

import net.alteiar.DatabaseUtil;
import net.alteiar.PlatformContextTestImpl;
import net.alteiar.core.engine.PlatformContext;
import net.alteiar.core.engine.task.Task;
import net.alteiar.core.engine.task.impl.LocalTaskEngineImpl;
import net.alteiar.other.TaskExecutorManager;
import net.alteiar.utils.task.TaskEmpty;
import net.alteiar.utils.task.TaskWithChild;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class FlowTest {

    private TaskExecutorManager executors;

    @Before
    public void before() {

        PlatformContextTestImpl context = new PlatformContextTestImpl();

        context.clearDatabase();
        PlatformContext.setPlatformContext(context);

        PlatformContext.getInstance().initialize();
        PlatformContext.getInstance().start();

        executors = new TaskExecutorManager(200);
        executors.setTaskQueue(((LocalTaskEngineImpl) PlatformContext.getInstance().getTaskEngine()).getTaskQueue());

        executors.initialize();
        executors.start();
    }

    @After
    public void after() {

        executors.shutdown();
        PlatformContext.getInstance().shutdown();
    }

    @Test
    public void testEnqueueTask() {

        JdbcTemplate jdbcTemplate = DatabaseUtil.createJdbcTemplate();

        Integer count = jdbcTemplate.queryForObject("SELECT count(*) from task_history", Integer.class);

        Assert.assertEquals("Task history must be empty", 0, count.intValue());

        Task task = new TaskEmpty();

        PlatformContext.getInstance().getTaskEngine().enqueue(task);

        PlatformContext.getInstance().getTaskHistory().waitFor(task.getId());

        count = jdbcTemplate.queryForObject("SELECT count(*) from task_history", Integer.class);
        Assert.assertEquals("Task history must contain one entry", 1, count.intValue());
    }

    @Test
    public void testEnqueueTaskWithChild() {

        JdbcTemplate jdbcTemplate = DatabaseUtil.createJdbcTemplate();

        Integer count = jdbcTemplate.queryForObject("SELECT count(*) from task_history", Integer.class);

        Assert.assertEquals("Task history must be empty", 0, count.intValue());

        Task task = new TaskWithChild(10, true);

        PlatformContext.getInstance().getTaskEngine().enqueue(task);

        PlatformContext.getInstance().getTaskHistory().waitFor(task.getId());

        count = jdbcTemplate.queryForObject("SELECT count(*) from task_history", Integer.class);
        Assert.assertEquals("Task history must contain one entry", 11, count.intValue());
    }

    @Test
    public void testPerfo() {

        int total = 1000;

        JdbcTemplate jdbcTemplate = DatabaseUtil.createJdbcTemplate();

        Integer count = jdbcTemplate.queryForObject("SELECT count(*) from task_history", Integer.class);

        Assert.assertEquals("Task history must be empty", 0, count.intValue());

        long begin = System.currentTimeMillis();
        List<Long> ids = new ArrayList<Long>();
        for (int i = 0; i < total; ++i) {

            Task task = new TaskEmpty();

            PlatformContext.getInstance().getTaskEngine().enqueue(task);
            ids.add(task.getId());
        }

        for (Long id : ids) {

            PlatformContext.getInstance().getTaskHistory().waitFor(id);
        }
        long end = System.currentTimeMillis();

        long duration = end - begin;
        float average = duration / (float) total;
        System.out.println(String.format("Process %s task in %sms, average of %sms", total, duration, average));

        count = jdbcTemplate.queryForObject("SELECT count(*) from task_history", Integer.class);
        Assert.assertEquals("Task history must contain one entry", total, count.intValue());
    }
}
