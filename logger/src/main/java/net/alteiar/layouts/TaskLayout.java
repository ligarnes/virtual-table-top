package net.alteiar.layouts;

import java.text.SimpleDateFormat;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.LayoutBase;

public class TaskLayout extends LayoutBase<ILoggingEvent> {

    private static final String LINE_SEP = System.getProperty("line.separator");

    @Override
    public String doLayout(ILoggingEvent event) {

        StringBuffer sbuf = new StringBuffer(128);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sbuf.append(dateFormat.format(event.getTimeStamp()));

        sbuf.append(" [");
        sbuf.append(event.getThreadName());
        sbuf.append("] ");

        if (ThreadTaskLogger.getTaskId() != -1) {
            sbuf.append("[TaskId ");
            sbuf.append(ThreadTaskLogger.getTaskId());
            sbuf.append("] ");
        }

        sbuf.append(event.getLevel().toString());

        sbuf.append(" ");

        sbuf.append(event.getLoggerName());

        sbuf.append(" - ");
        sbuf.append(event.getFormattedMessage());
        sbuf.append(LINE_SEP);

        return sbuf.toString();
    }
}
