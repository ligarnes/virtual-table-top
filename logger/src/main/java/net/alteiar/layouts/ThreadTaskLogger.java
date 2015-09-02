package net.alteiar.layouts;

public class ThreadTaskLogger {

    private static ThreadLocal<Long> taskId = new ThreadLocal<Long>() {
        @Override
        protected synchronized Long initialValue() {
            return -1L;
        }
    };

    public static void setTaskId(Long id) {

        taskId.set(id);
    }

    public static Long getTaskId() {
        return taskId.get();
    }
}
