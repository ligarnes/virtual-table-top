package net.alteiar.task.unit;

import net.alteiar.basictypes.Unit;
import net.alteiar.dao.api.DaoFactorySingleton;
import net.alteiar.db.dao.exception.DataException;
import net.alteiar.engine.task.impl.TaskBase;

public class ChangeUnit extends TaskBase {

	private final Unit unit;

	public ChangeUnit(Unit unit) {
		this.unit = unit;
	}

	@Override
	public void execute() throws DataException {

		// apply the change
		DaoFactorySingleton.getInstance().getUnitDao().update(unit);
	}

}
