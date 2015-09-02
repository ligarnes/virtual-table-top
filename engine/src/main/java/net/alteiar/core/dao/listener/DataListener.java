package net.alteiar.core.dao.listener;

public interface DataListener {

	void dataAdded(Long id);

	void dataChanged(Long id);

	void dataRemoved(Long id);
}
