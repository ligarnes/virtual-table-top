package net.alteiar.core.engine;

import net.alteiar.core.engine.persistence.PersistenceEngine;
import net.alteiar.core.engine.task.TaskEngine;
import net.alteiar.core.engine.task.history.TaskHistory;
import net.alteiar.core.engine.task.history.TaskHistoryImpl;
import net.alteiar.core.engine.task.impl.LocalTaskEngineImpl;
import net.alteiar.core.engine.task.impl.TaskStrategyImpl;
import net.alteiar.db.installer.ModuleImpl;
import net.alteiar.db.installer.ModuleInstaller;
import net.alteiar.db.installer.exception.DbScriptException;
import net.alteiar.db.installer.exception.ParsingException;
import net.alteiar.db.installer.xml.SqlModuleWrapper;
import net.alteiar.db.installer.xml.SqlScriptWrapper;
import net.alteiar.module.Module;
import net.alteiar.sql.SqlModule;
import net.alteiar.sql.SqlScript;

public class PlatformContext implements Module {

    private static final String NOT_INITIALIZED_EXCEPTION = "The %s must be set before initializing the platform";

    private static PlatformContext INSTANCE;

    public static PlatformContext getInstance() {

        return INSTANCE;
    }

    public static synchronized void setPlatformContext(PlatformContext context) {

        INSTANCE = context;
    }

    private TaskEngine taskEngine;

    private TaskHistory taskHistory;

    private PersistenceEngine persistenceEngine;

    protected PlatformContext() {

        taskEngine = new LocalTaskEngineImpl();
        taskHistory = new TaskHistoryImpl();
    }

    @Override
    public String getName() {

        return getClass().getSimpleName();
    }

    @Override
    public void initialize() {

        if (taskEngine == null) {
            throw new IllegalStateException(String.format(NOT_INITIALIZED_EXCEPTION, "task engine"));
        }

        if (taskHistory == null) {
            throw new IllegalStateException(String.format(NOT_INITIALIZED_EXCEPTION, "task history"));
        }

        if (persistenceEngine == null) {
            throw new IllegalStateException(String.format(NOT_INITIALIZED_EXCEPTION, "persistence engine"));
        }

        SqlModuleWrapper wrapper = new SqlModuleWrapper();

        try {
            wrapper.load(getClass().getResourceAsStream("/installer/db/module.xml"));
        } catch (ParsingException e) {

            throw new IllegalStateException("Unable to parse the module.xml", e);
        }

        SqlModule fileModule = wrapper.getSqlModule();

        ModuleImpl<SqlModule> impl = new ModuleImpl<SqlModule>(fileModule) {

            @Override
            protected SqlScript loadScript(String name) throws Exception {

                SqlScriptWrapper wrapper = new SqlScriptWrapper();
                wrapper.load(getClass().getResourceAsStream("/installer/db/scripts/" + name + ".xml"));
                return wrapper.getSqlScript();
            }
        };

        ModuleInstaller installer = new ModuleInstaller(impl);
        installer.setDatasource(getPersistenceEngine().getDataSource());

        try {
            installer.execute();
        } catch (DbScriptException e) {

            throw new IllegalStateException("Failure to execute the database installer", e);
        }

        taskEngine.setStrategy(new TaskStrategyImpl());
    }

    @Override
    public void start() {

        taskHistory.start();
    }

    @Override
    public void shutdown() {

        taskHistory.shutdown();
    }

    public TaskEngine getTaskEngine() {

        return taskEngine;
    }

    public PersistenceEngine getPersistenceEngine() {

        return persistenceEngine;
    }

    public TaskHistory getTaskHistory() {
        return this.taskHistory;
    }

    protected final void setPersistenceEngine(PersistenceEngine persistenceEngine) {
        this.persistenceEngine = persistenceEngine;
    }

    protected final void setTaskEngine(TaskEngine taskEngine) {
        this.taskEngine = taskEngine;
    }

    protected final void setTaskHistory(TaskHistory taskHistory) {
        this.taskHistory = taskHistory;
    }

}
