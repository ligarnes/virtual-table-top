package net.alteiar.combat.task;

import net.alteiar.basictypes.Unit;
import net.alteiar.dao.api.DaoFactorySingleton;
import net.alteiar.db.dao.exception.DataException;
import net.alteiar.engine.task.impl.TaskBase;

public class ChangeImage extends TaskBase {

	private final long unitId;

	private final String imagePath;

	public ChangeImage(long unitId, String imagePath) {
		super();
		this.unitId = unitId;
		this.imagePath = imagePath;
	}

	@Override
	public void execute() {

		Unit unit;
		try {
			unit = DaoFactorySingleton.getInstance().getUnitDao().find(unitId);
			unit.setImage(imagePath);

			// apply the change
			DaoFactorySingleton.getInstance().getUnitDao().update(unit);
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
