package net.alteiar.db.dao;

import net.alteiar.engine.observer.DataListener;
import net.alteiar.engine.observer.DataObservable;
import net.alteiar.engine.observer.DataObservableImpl;

public class Dao implements DataObservable {

	private final DataObservableImpl observable;

	public Dao() {

		this.observable = new DataObservableImpl();
	}

	@Override
	public void addDataListener(DataListener listener) {

		observable.addDataListener(listener);
	}

	@Override
	public void removeListener(DataListener listener) {

		observable.removeListener(listener);
	}

	public void fireDataAdded(Long id) {

		observable.fireDataAdded(id);
	}

	public void fireDataChanged(Long id) {

		observable.fireDataChanged(id);
	}

	public void fireDataRemoved(Long id) {

		observable.fireDataRemoved(id);
	}
}
