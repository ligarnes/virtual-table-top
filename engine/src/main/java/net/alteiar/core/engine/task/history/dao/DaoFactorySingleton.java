package net.alteiar.core.engine.task.history.dao;

public class DaoFactorySingleton {

    private static volatile DaoFactory INSTANCE = null;

    public static DaoFactory getInstance() {

        if (INSTANCE == null) {

            synchronized (DaoFactorySingleton.class) {

                if (INSTANCE == null) {

                    INSTANCE = new DaoFactoryImpl();
                    INSTANCE.initialize();
                }
            }
        }

        return INSTANCE;
    }

    public static void setInstance(DaoFactory instance) {

        INSTANCE = instance;
    }
}
