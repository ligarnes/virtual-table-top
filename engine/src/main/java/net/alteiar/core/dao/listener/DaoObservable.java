package net.alteiar.core.dao.listener;

public interface DaoObservable {

	void addDataListener(DataListener listener);

	void removeListener(DataListener listener);
}
