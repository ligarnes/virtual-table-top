package net.alteiar.core.engine.task.history.dao;

import java.util.Date;

import net.alteiar.core.engine.task.Task;
import net.alteiar.core.engine.task.TaskStatus;
import net.alteiar.core.engine.task.history.dao.TaskHistoryEntry;
import net.alteiar.utils.task.TaskEmpty;

import org.junit.Assert;
import org.junit.Test;

public class TaskHistoryEntryTest {

    @Test
    public void testGetterSetter() {

        TaskHistoryEntry entry = new TaskHistoryEntry();

        long id = 10L;
        Long parentId = 8L;
        TaskStatus status = TaskStatus.STARTED;

        Task task = new TaskEmpty();
        task.setId(id);
        task.setParentId(parentId);
        task.setStatus(status);

        entry.setTask(task);

        Date creationTime = new Date();
        Date startTime = new Date(creationTime.getTime() + 1000L);
        Date endTime = new Date(creationTime.getTime() + 10000L);
        entry.setCreationTime(creationTime);
        entry.setStartTime(startTime);
        entry.setEndTime(endTime);

        Assert.assertEquals(id, entry.getId());
        Assert.assertEquals(parentId, entry.getParentId());
        Assert.assertEquals(status.getStatus(), entry.getStatus());
        Assert.assertEquals(TaskEmpty.class.getSimpleName(), entry.getTaskName());

        Assert.assertEquals(creationTime, entry.getCreationTime());
        Assert.assertEquals(startTime, entry.getStartTime());
        Assert.assertEquals(endTime, entry.getEndTime());
    }

    @Test
    public void testEquals() {

        long id = 10L;
        Long parentId = 8L;
        TaskStatus status = TaskStatus.STARTED;

        Task task = new TaskEmpty();
        task.setId(id);
        task.setParentId(parentId);
        task.setStatus(status);

        Task task2 = new TaskEmpty();
        task2.setId(id);
        task2.setParentId(parentId);
        task2.setStatus(status);

        Date creationTime = new Date();
        Date startTime = new Date(creationTime.getTime() + 1000L);
        Date endTime = new Date(creationTime.getTime() + 10000L);

        TaskHistoryEntry entry = new TaskHistoryEntry();
        entry.setTask(task);
        entry.setCreationTime(creationTime);
        entry.setStartTime(startTime);
        entry.setEndTime(endTime);

        TaskHistoryEntry entry2 = new TaskHistoryEntry();
        entry2.setTask(task2);
        entry2.setCreationTime(creationTime);
        entry2.setStartTime(startTime);
        entry2.setEndTime(endTime);

        Assert.assertFalse(entry.equals(null));
        Assert.assertFalse(entry.equals(""));
        Assert.assertTrue(entry.equals(entry2));
        Assert.assertTrue(entry.equals(entry));
        Assert.assertEquals(entry.hashCode(), entry2.hashCode());

        TaskHistoryEntry entry3 = new TaskHistoryEntry();
        entry3.setTask(task2);
        entry3.setCreationTime(creationTime);
        entry3.setStartTime(startTime);
        entry3.setEndTime(null);

        Assert.assertFalse(entry.equals(entry3));
        Assert.assertFalse(entry3.equals(entry));
        Assert.assertNotSame(entry.hashCode(), entry3.hashCode());

        entry3.setStartTime(null);
        entry3.setEndTime(endTime);
        Assert.assertFalse(entry.equals(entry3));
        Assert.assertFalse(entry3.equals(entry));
        Assert.assertNotSame(entry.hashCode(), entry3.hashCode());

        entry3.setCreationTime(null);
        entry3.setStartTime(startTime);
        Assert.assertFalse(entry.equals(entry3));
        Assert.assertFalse(entry3.equals(entry));
        Assert.assertNotSame(entry.hashCode(), entry3.hashCode());

        entry3.setCreationTime(creationTime);
        entry3.setTask(null);
        Assert.assertFalse(entry.equals(entry3));
        Assert.assertFalse(entry3.equals(entry));
        Assert.assertNotSame(entry.hashCode(), entry3.hashCode());

        Task task3 = new TaskEmpty();
        task3.setId(id + 5);
        task3.setParentId(parentId);
        task3.setStatus(status);

        entry3.setTask(task3);
        Assert.assertFalse(entry.equals(entry3));
        Assert.assertFalse(entry3.equals(entry));
        Assert.assertNotSame(entry.hashCode(), entry3.hashCode());

        task3.setId(id);
        task3.setParentId(null);
        Assert.assertTrue(entry.equals(entry3));

        task3.setStatus(TaskStatus.IN_QUEUE);
        Assert.assertTrue(entry.equals(entry3));
    }
}
