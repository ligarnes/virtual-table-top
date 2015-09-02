package net.alteiar.utils.task;

import java.util.ArrayList;
import java.util.List;

import net.alteiar.core.engine.PlatformContext;
import net.alteiar.core.engine.task.Task;
import net.alteiar.core.engine.task.impl.TaskBase;

public class TaskWithChild extends TaskBase {

    private final int subTaskCount;

    private final boolean waitFor;

    public TaskWithChild(int subTaskCount) {

        this(subTaskCount, false);
    }

    public TaskWithChild(int subTaskCount, boolean waitFor) {

        this.subTaskCount = subTaskCount;
        this.waitFor = waitFor;
    }

    @Override
    public void execute() throws Exception {

        List<Long> ids = new ArrayList<Long>();

        for (int i = 0; i < subTaskCount; i++) {

            Task t = new TaskEmpty();
            t.setParentId(getId());
            ids.add(PlatformContext.getInstance().getTaskEngine().enqueue(t));
        }

        if (waitFor) {

            for (Long id : ids) {

                PlatformContext.getInstance().getTaskHistory().waitFor(id);
            }
        }
    }
}
