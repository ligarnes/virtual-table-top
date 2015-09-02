package net.alteiar.core.engine.task.history;

import net.alteiar.core.engine.task.Task;
import net.alteiar.core.engine.task.TaskStatus;
import net.alteiar.core.engine.task.history.dao.DaoFactory;
import net.alteiar.core.engine.task.history.dao.DaoFactorySingleton;
import net.alteiar.core.engine.task.history.dao.TaskHistoryDao;
import net.alteiar.core.engine.task.history.dao.TaskHistoryEntry;
import net.alteiar.utils.task.TaskEmpty;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class TaskHistoryTest {

    private DaoFactory factory;

    private TaskHistoryDao dao;

    @Before
    public void before() {

        factory = Mockito.mock(DaoFactory.class);

        dao = Mockito.mock(TaskHistoryDao.class);

        Mockito.when(factory.getTaskHistoryDao()).thenReturn(dao);

        DaoFactorySingleton.setInstance(factory);
    }

    @After
    public void after() {

        DaoFactorySingleton.setInstance(null);

        Mockito.validateMockitoUsage();
    }

    @Test
    public void testCreateId() {

        TaskHistoryImpl taskHistory = new TaskHistoryImpl();

        taskHistory.createTask(new TaskEmpty());

        Mockito.verify(dao, Mockito.times(1)).createId();
    }

    @Test
    public void testChangeStatus() {

        TaskHistoryImpl taskHistory = new TaskHistoryImpl();

        Task t = new TaskEmpty();
        t.setId(1l);

        taskHistory.changeStatus(t, TaskStatus.INITIALIZED);
        Assert.assertEquals(TaskStatus.INITIALIZED, taskHistory.getStatus(1l));

        taskHistory.changeStatus(t, TaskStatus.STARTED);
        Assert.assertEquals(TaskStatus.STARTED, taskHistory.getStatus(1l));

        taskHistory.changeStatus(t, TaskStatus.SUCCEED);
        Assert.assertEquals(TaskStatus.SUCCEED, taskHistory.getStatus(1l));

        taskHistory.changeStatus(t, TaskStatus.FAILED);
        Assert.assertEquals(TaskStatus.FAILED, taskHistory.getStatus(1l));

        taskHistory.changeStatus(t, TaskStatus.NEW);
        Assert.assertEquals(TaskStatus.NEW, taskHistory.getStatus(1l));

        taskHistory.changeStatus(t, TaskStatus.IN_QUEUE);
        Assert.assertEquals(TaskStatus.IN_QUEUE, taskHistory.getStatus(1l));
    }

    @Test
    public void testGetStatus() {

        TaskHistoryImpl taskHistory = new TaskHistoryImpl();

        Assert.assertEquals(TaskStatus.FAILED, taskHistory.getStatus(2L));

        Task t = new TaskEmpty();
        t.setId(1l);

        taskHistory.changeStatus(t, TaskStatus.INITIALIZED);
        Assert.assertEquals(TaskStatus.INITIALIZED, taskHistory.getStatus(1L));

        Mockito.when(dao.getStatus(2L)).thenReturn(TaskStatus.SUCCEED.toString());
        Assert.assertEquals(TaskStatus.SUCCEED, taskHistory.getStatus(2L));
    }

    @Test
    public void testWaitForEver() {

        final TaskHistoryImpl taskHistory = new TaskHistoryImpl();

        final Task t = new TaskEmpty();
        t.setId(2L);

        taskHistory.changeStatus(t, TaskStatus.STARTED);

        Thread tr = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Assert.fail(e.getMessage());
            }

            taskHistory.changeStatus(t, TaskStatus.SUCCEED);
        });

        tr.start();
        TaskStatus tStatus = taskHistory.waitFor(2L);

        Assert.assertEquals(TaskStatus.SUCCEED, tStatus);
    }

    @Test
    public void testWaitForTimeOut() {

        final TaskHistoryImpl taskHistory = new TaskHistoryImpl();

        final Task t = new TaskEmpty();
        t.setId(2L);

        taskHistory.changeStatus(t, TaskStatus.STARTED);

        Thread tr = new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Assert.fail(e.getMessage());
            }

            taskHistory.changeStatus(t, TaskStatus.SUCCEED);
        });

        tr.start();
        TaskStatus tStatus = taskHistory.waitFor(2L, 200L);

        Assert.assertEquals(TaskStatus.STARTED, tStatus);
    }

    @Test
    public void testWaitFor() {

        final TaskHistoryImpl taskHistory = new TaskHistoryImpl();

        final Task t = new TaskEmpty();
        t.setId(2L);

        taskHistory.changeStatus(t, TaskStatus.STARTED);

        Thread tr = new Thread(() -> {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                Assert.fail(e.getMessage());
            }

            taskHistory.changeStatus(t, TaskStatus.SUCCEED);
        });

        tr.start();
        TaskStatus tStatus = taskHistory.waitFor(2L, 2000L);

        Assert.assertEquals(TaskStatus.SUCCEED, tStatus);
    }

    @Test
    public void testDatabaseSaver() throws InterruptedException {

        final TaskHistoryImpl taskHistory = new TaskHistoryImpl();

        taskHistory.start();

        final Task t = new TaskEmpty();
        t.setId(2L);

        taskHistory.changeStatus(t, TaskStatus.STARTED);
        taskHistory.changeStatus(t, TaskStatus.FAILED);
        taskHistory.changeStatus(new TaskEmpty(), TaskStatus.IN_QUEUE);
        taskHistory.changeStatus(new TaskEmpty(), TaskStatus.SUCCEED);
        taskHistory.changeStatus(new TaskEmpty(), TaskStatus.IN_QUEUE);
        taskHistory.changeStatus(new TaskEmpty(), TaskStatus.NEW);
        taskHistory.changeStatus(new TaskEmpty(), TaskStatus.IN_QUEUE);
        taskHistory.changeStatus(new TaskEmpty(), TaskStatus.STARTED);

        taskHistory.shutdown();

        Mockito.verify(dao, Mockito.times(8)).update(Mockito.any(TaskHistoryEntry.class));
    }
}
