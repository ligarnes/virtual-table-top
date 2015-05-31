package net.alteiar.engine.observer;

public abstract class DataModificationAdapter implements DataListener {

	private final Long unitId;

	public DataModificationAdapter(Long unitId) {

		this.unitId = unitId;
	}

	@Override
	public final void dataAdded(Long id) {

	}

	@Override
	public final void dataChanged(Long id) {

		if (id.equals(unitId)) {

			dataChanged();
		}
	}

	@Override
	public final void dataRemoved(Long id) {

		if (id.equals(unitId)) {

			dataDeleted();
		}
	}

	protected abstract void dataChanged();

	protected abstract void dataDeleted();

}
