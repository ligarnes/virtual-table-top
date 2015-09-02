package net.alteiar.core.engine.task;

import net.alteiar.PlatformContextTestMocked;
import net.alteiar.core.engine.PlatformContext;
import net.alteiar.core.engine.task.history.TaskHistory;
import net.alteiar.core.engine.task.impl.TaskStrategyImpl;
import net.alteiar.utils.task.TaskEmpty;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class TaskStrategyTest {

    @Before
    public void before() {

        PlatformContextTestMocked mock = new PlatformContextTestMocked();
        mock.setTaskHistoryImpl(Mockito.mock(TaskHistory.class));

        PlatformContext.setPlatformContext(mock);
    }

    @After
    public void after() {

        PlatformContext.setPlatformContext(null);
        Mockito.validateMockitoUsage();
    }

    @Test
    public void testExecuteTask() throws Throwable {

        TaskStrategy strategy = new TaskStrategyImpl();

        Task task = Mockito.spy(TaskEmpty.class);

        strategy.executeTask(task);

        Mockito.verify(task, Mockito.times(1)).initializeTask();
        Mockito.verify(task, Mockito.times(1)).execute();
        Mockito.verify(task, Mockito.times(1)).finalizeTask();

    }

    @Test
    public void testExecuteTaskWithException() throws Throwable {

        TaskStrategy strategy = new TaskStrategyImpl();

        Task task = Mockito.mock(Task.class);

        Mockito.doThrow(new IllegalArgumentException()).when(task).execute();

        strategy.executeTask(task);

        Mockito.verify(task, Mockito.times(1)).initializeTask();
        Mockito.verify(task, Mockito.times(1)).execute();
        Mockito.verify(task, Mockito.times(1)).finalizeTask();
    }

    @Test
    public void testInitializeTaskWithException() throws Throwable {

        TaskStrategy strategy = new TaskStrategyImpl();

        Task task = Mockito.mock(Task.class);

        Mockito.doThrow(new IllegalArgumentException()).when(task).initializeTask();

        strategy.executeTask(task);

        Mockito.verify(task, Mockito.times(1)).initializeTask();
        Mockito.verify(task, Mockito.times(0)).execute();
        Mockito.verify(task, Mockito.times(1)).finalizeTask();
    }

    @Test
    public void testFinalizeTaskWithException() throws Throwable {

        TaskStrategy strategy = new TaskStrategyImpl();

        Task task = Mockito.mock(Task.class);

        Mockito.doThrow(new IllegalArgumentException()).when(task).finalizeTask();

        strategy.executeTask(task);

        Mockito.verify(task, Mockito.times(1)).initializeTask();
        Mockito.verify(task, Mockito.times(1)).execute();
        Mockito.verify(task, Mockito.times(1)).finalizeTask();
    }

}
