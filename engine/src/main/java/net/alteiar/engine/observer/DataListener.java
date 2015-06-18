package net.alteiar.engine.observer;

public interface DataListener {

	void dataAdded(Long id);

	void dataChanged(Long id);

	void dataRemoved(Long id);
}
