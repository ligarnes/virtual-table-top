package net.alteiar.combat.task;

import net.alteiar.basictypes.CombatTracker;
import net.alteiar.basictypes.CombatUnit;
import net.alteiar.basictypes.Faction;
import net.alteiar.basictypes.Unit;
import net.alteiar.dao.api.DaoFactorySingleton;
import net.alteiar.db.dao.exception.DataException;
import net.alteiar.engine.task.impl.TaskBase;

public class AddUnitToCombat extends TaskBase {

	private final CombatTracker tracker;

	private final Unit unit;

	private final Integer initiative;

	private final Faction faction;

	public AddUnitToCombat(CombatTracker traker, Unit unit, Integer initiative, Faction faction) {
		super();
		this.tracker = traker;
		this.unit = unit;
		this.initiative = initiative;
		this.faction = faction;
	}

	@Override
	public void initializeTask() {

		if (tracker == null) {

			throw new IllegalArgumentException("The tracker must not be null");
		}
	}

	@Override
	public void execute() {

		CombatUnit combatUnit = new CombatUnit();
		combatUnit.setUnitId(unit.getId());
		combatUnit.setFaction(faction);
		combatUnit.setInitiative(initiative);

		try {

			DaoFactorySingleton.getInstance().getCombatUnitDao().insert(combatUnit);

			tracker.getCombatUnitId().add(combatUnit.getId());

			DaoFactorySingleton.getInstance().getCombatTrackerDao().update(tracker);
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
