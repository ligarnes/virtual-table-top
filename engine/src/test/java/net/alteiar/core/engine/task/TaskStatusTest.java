package net.alteiar.core.engine.task;

import org.junit.Assert;
import org.junit.Test;

public class TaskStatusTest {

    @Test
    public void testValueOf() {

        Assert.assertEquals(null, TaskStatus.valueOf(null));
        Assert.assertEquals(null, TaskStatus.valueOf(""));

        Assert.assertEquals(TaskStatus.IN_QUEUE, TaskStatus.valueOf("in_queue"));
        Assert.assertEquals(TaskStatus.NEW, TaskStatus.valueOf("new"));
        Assert.assertEquals(TaskStatus.INITIALIZED, TaskStatus.valueOf("initialiZed"));
        Assert.assertEquals(TaskStatus.STARTED, TaskStatus.valueOf("Started"));
        Assert.assertEquals(TaskStatus.SUCCEED, TaskStatus.valueOf("suCCeed"));
        Assert.assertEquals(TaskStatus.FAILED, TaskStatus.valueOf("Failed"));
    }

    @Test
    public void testEquals() {

        Assert.assertFalse(TaskStatus.IN_QUEUE.equals(null));
        Assert.assertFalse(TaskStatus.IN_QUEUE.equals(""));

        Assert.assertFalse(TaskStatus.IN_QUEUE.equals(TaskStatus.NEW));

        // test with null status
        Assert.assertFalse(TaskStatus.IN_QUEUE.equals(TaskStatus.NEW));
    }

    @Test
    public void testHashCode() {

        Assert.assertEquals(TaskStatus.IN_QUEUE.hashCode(), TaskStatus.IN_QUEUE.hashCode());
        Assert.assertNotSame(TaskStatus.NEW.hashCode(), TaskStatus.IN_QUEUE.hashCode());
    }

    @Test
    public void testIsFinal() {

        Assert.assertFalse(TaskStatus.IN_QUEUE.isFinal());
        Assert.assertFalse(TaskStatus.NEW.isFinal());
        Assert.assertFalse(TaskStatus.INITIALIZED.isFinal());
        Assert.assertFalse(TaskStatus.STARTED.isFinal());
        Assert.assertTrue(TaskStatus.FAILED.isFinal());
        Assert.assertTrue(TaskStatus.SUCCEED.isFinal());
    }
}
