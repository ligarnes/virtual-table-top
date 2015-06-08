package net.alteiar.db.dao.combat;

import net.alteiar.basictypes.CombatTracker;
import net.alteiar.db.dao.exception.DataException;
import net.alteiar.engine.observer.DataObservable;

public interface CombatTrackerDao extends DataObservable {

	void insert(CombatTracker combatTracker) throws DataException;

	void update(CombatTracker combatTracker) throws DataException;

	CombatTracker find(long id) throws DataException;

	void delete(CombatTracker combatTracker) throws DataException;
}
