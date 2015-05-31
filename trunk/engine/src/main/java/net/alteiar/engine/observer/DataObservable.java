package net.alteiar.engine.observer;

public interface DataObservable {

	void addDataListener(DataListener listener);

	void removeListener(DataListener listener);
}
