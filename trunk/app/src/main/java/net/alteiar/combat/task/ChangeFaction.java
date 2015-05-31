package net.alteiar.combat.task;

import net.alteiar.combattracker.CombatUnit;
import net.alteiar.combattracker.Faction;
import net.alteiar.dao.api.DaoFactorySingleton;
import net.alteiar.db.dao.exception.DataException;
import net.alteiar.engine.task.impl.TaskBase;

public class ChangeFaction extends TaskBase {

	private final Long combatUnitId;

	private final Faction faction;

	public ChangeFaction(CombatUnit combatUnit, Faction faction) {

		this(combatUnit.getId(), faction);
	}

	public ChangeFaction(Long combatUnitId, Faction faction) {

		this.combatUnitId = combatUnitId;
		this.faction = faction;
	}

	@Override
	public void execute() {

		CombatUnit unit;
		try {

			unit = DaoFactorySingleton.getInstance().getCombatUnitDao()
					.find(combatUnitId);

			unit.setFaction(faction);

			// apply the change
			DaoFactorySingleton.getInstance().getCombatUnitDao().update(unit);

		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
