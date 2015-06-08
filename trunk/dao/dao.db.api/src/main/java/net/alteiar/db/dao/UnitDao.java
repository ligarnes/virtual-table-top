package net.alteiar.db.dao;

import java.util.List;

import net.alteiar.basictypes.Unit;
import net.alteiar.db.dao.exception.DataException;
import net.alteiar.engine.observer.DataObservable;

public interface UnitDao extends DataObservable {

	void insert(Unit unit) throws DataException;

	void update(Unit unit) throws DataException;

	Unit find(long id) throws DataException;

	List<Unit> findAll() throws DataException;

	void delete(Unit unit) throws DataException;
}