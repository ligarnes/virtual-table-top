package net.alteiar.combat.task;

import net.alteiar.combattracker.CombatUnit;
import net.alteiar.dao.api.DaoFactorySingleton;
import net.alteiar.db.dao.exception.DataException;
import net.alteiar.engine.task.impl.TaskBase;

public class ChangeInitiative extends TaskBase {

	private CombatUnit combatUnit;

	private Integer initiative;

	@Override
	public void execute() {

		CombatUnit unit;
		try {
			unit = DaoFactorySingleton.getInstance().getCombatUnitDao()
					.find(combatUnit.getId());

			unit.setInitiative(initiative);

			// apply the change
			DaoFactorySingleton.getInstance().getCombatUnitDao().update(unit);
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
