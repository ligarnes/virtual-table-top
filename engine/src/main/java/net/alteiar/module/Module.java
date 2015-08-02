package net.alteiar.module;

public interface Module {

    String getName();

    void initialize();

    void start();

    void shutdown();
}
