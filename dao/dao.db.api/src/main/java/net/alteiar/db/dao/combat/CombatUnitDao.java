package net.alteiar.db.dao.combat;

import net.alteiar.basictypes.CombatUnit;
import net.alteiar.db.dao.exception.DataException;
import net.alteiar.engine.observer.DataObservable;

public interface CombatUnitDao extends DataObservable {

	void insert(CombatUnit combatUnit) throws DataException;

	void insertOrUpdate(CombatUnit combatUnit) throws DataException;

	void update(CombatUnit combatUnit) throws DataException;

	CombatUnit find(long id) throws DataException;

	void delete(CombatUnit combatUnit) throws DataException;
}
