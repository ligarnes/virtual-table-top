package net.alteiar.dao.unit.impl;

import java.util.List;

import javax.xml.bind.JAXBException;

import net.alteiar.combattracker.ObjectFactory;
import net.alteiar.combattracker.Unit;
import net.alteiar.dao.util.XmlDao;
import net.alteiar.db.dao.UnitDao;
import net.alteiar.db.dao.exception.DataException;

import org.slf4j.LoggerFactory;

public class UnitDaoImpl extends XmlDao<Unit> implements UnitDao {

	public void initialize() {

		try {

			super.initialize(Unit.class);
		} catch (JAXBException e) {

			LoggerFactory.getLogger(getClass()).error(
					"Fail to initialize the dao", e);
		}
	}

	@Override
	public void insert(Unit unit) throws DataException {

		unit.setId(nextId());

		if (exist(unit)) {

			String errorMsg = String.format(
					"Fail to insert the unit {%s}, the unit already exist",
					unit.getId());
			throw new DataException(errorMsg);
		}

		ObjectFactory factory = new ObjectFactory();

		saveFile(factory.createUnit(unit));

		fireDataAdded(unit.getId());
	}

	@Override
	public void update(Unit unit) throws DataException {

		if (exist(unit)) {

			ObjectFactory factory = new ObjectFactory();
			saveFile(factory.createUnit(unit));

			fireDataChanged(unit.getId());
		} else {

			String errorMsg = String.format(
					"The  unit {%s} does not exist and cannot be updated",
					unit.getId());
			throw new DataException(errorMsg);
		}
	}

	@Override
	public Unit find(long id) throws DataException {

		Unit found = null;

		if (exist(Unit.class, id)) {

			found = loadFile(Unit.class, id);
		}

		return found;
	}

	@Override
	public List<Unit> findAll() throws DataException {
		return loadAll(Unit.class);
	}

	@Override
	public void delete(Unit unit) throws DataException {

		if (exist(unit)) {

			super.deleteFile(unit);

			fireDataRemoved(unit.getId());
		} else {

			String errorMsg = String.format(
					"Fail to delete the  unit {%s}, the unit does not exist",
					unit.getId());
			throw new DataException(errorMsg);
		}
	}

}
