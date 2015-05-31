package net.alteiar.combat.task;

import net.alteiar.combattracker.CombatTracker;
import net.alteiar.dao.api.DaoFactorySingleton;
import net.alteiar.db.dao.exception.DataException;
import net.alteiar.engine.task.impl.TaskBase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateCombatTracker extends TaskBase {

	private static final Logger logger = LoggerFactory
			.getLogger(CreateCombatTracker.class);

	private final CombatTracker tracker;

	public CreateCombatTracker(CombatTracker tracker) {

		this.tracker = tracker;
	}

	@Override
	public void execute() {

		try {

			logger.debug("create combat tracker");
			DaoFactorySingleton.getInstance().getCombatTrackerDao()
					.insert(tracker);
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
