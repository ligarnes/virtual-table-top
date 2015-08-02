package net.alteiar.core.dao.listener;

import java.util.HashSet;
import java.util.Set;

public class DataObservableImpl implements DaoObservable {

	private final Set<DataListener> listeners;

	public DataObservableImpl() {

		listeners = new HashSet<DataListener>();
	}

	@Override
	public void addDataListener(DataListener listener) {

		synchronized (listeners) {

			this.listeners.add(listener);
		}
	}

	@Override
	public void removeListener(DataListener listener) {

		synchronized (listeners) {

			this.listeners.remove(listener);
		}
	}

	private Set<DataListener> getCopy() {

		Set<DataListener> copy = new HashSet<DataListener>();
		synchronized (listeners) {

			copy.addAll(listeners);
		}

		return copy;
	}

	public void fireDataAdded(Long id) {

		Set<DataListener> copy = getCopy();

		for (DataListener dataListener : copy) {

			dataListener.dataAdded(id);
		}
	}

	public void fireDataChanged(Long id) {

		Set<DataListener> copy = getCopy();

		for (DataListener dataListener : copy) {

			dataListener.dataChanged(id);
		}
	}

	public void fireDataRemoved(Long id) {

		Set<DataListener> copy = getCopy();

		for (DataListener dataListener : copy) {

			dataListener.dataRemoved(id);
		}
	}
}
