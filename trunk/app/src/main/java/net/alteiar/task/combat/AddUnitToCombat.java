package net.alteiar.task.combat;

import net.alteiar.basictypes.CombatTracker;
import net.alteiar.basictypes.CombatUnit;
import net.alteiar.basictypes.Faction;
import net.alteiar.basictypes.Unit;
import net.alteiar.dao.api.DaoFactorySingleton;
import net.alteiar.db.dao.exception.DataException;
import net.alteiar.engine.task.impl.TaskBase;

public class AddUnitToCombat extends TaskBase {

	private final Long trackerId;

	private final Long unitId;

	private final Integer initiative;

	private final Faction faction;

	public AddUnitToCombat(CombatTracker tracker, Unit unit, Integer initiative, Faction faction) {
		this(tracker.getId(), unit.getId(), initiative, faction);
	}

	public AddUnitToCombat(Long traker, Long unitId, Integer initiative, Faction faction) {
		super();
		this.trackerId = traker;
		this.unitId = unitId;
		this.initiative = initiative;
		this.faction = faction;
	}

	@Override
	public void initializeTask() {

		if (trackerId == null) {

			throw new IllegalArgumentException("The tracker must not be null");
		}

		CombatTracker tracker;
		try {
			tracker = DaoFactorySingleton.getInstance().getCombatTrackerDao().find(trackerId);

			for (Long combatId : tracker.getCombatUnitId()) {

				CombatUnit combat;
				combat = DaoFactorySingleton.getInstance().getCombatUnitDao().find(combatId);

				if (combat.getUnitId() == unitId) {
					throw new IllegalArgumentException(String.format("The unit {%s} is already in the combat tracker",
							unitId));
				}
			}
		} catch (DataException e1) {

			throw new IllegalStateException("Fail to access database", e1);
		}
	}

	@Override
	public void execute() {

		CombatUnit combatUnit = new CombatUnit();
		combatUnit.setUnitId(unitId);
		combatUnit.setFaction(faction);
		combatUnit.setInitiative(initiative);

		try {

			DaoFactorySingleton.getInstance().getCombatUnitDao().insert(combatUnit);

			CombatTracker tracker = DaoFactorySingleton.getInstance().getCombatTrackerDao().find(trackerId);
			tracker.getCombatUnitId().add(combatUnit.getId());

			DaoFactorySingleton.getInstance().getCombatTrackerDao().update(tracker);
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
