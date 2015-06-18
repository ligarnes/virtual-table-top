package net.alteiar.task.unit;

import net.alteiar.basictypes.Unit;
import net.alteiar.dao.api.DaoFactorySingleton;
import net.alteiar.db.dao.exception.DataException;
import net.alteiar.engine.task.impl.TaskBase;

public class ChangeUnitImage extends TaskBase {

	private final long unitId;

	private final String imagePath;

	public ChangeUnitImage(long unitId, String imagePath) {
		super();
		this.unitId = unitId;
		this.imagePath = imagePath;
	}

	@Override
	public void execute() throws DataException {

		Unit unit = DaoFactorySingleton.getInstance().getUnitDao().find(unitId);
		unit.setImage(imagePath);

		// apply the change
		DaoFactorySingleton.getInstance().getUnitDao().update(unit);
	}

}
