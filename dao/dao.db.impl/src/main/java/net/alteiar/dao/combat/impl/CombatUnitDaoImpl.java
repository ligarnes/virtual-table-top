package net.alteiar.dao.combat.impl;

import javax.xml.bind.JAXBException;

import net.alteiar.basictypes.CombatUnit;
import net.alteiar.basictypes.ObjectFactory;
import net.alteiar.dao.util.XmlDao;
import net.alteiar.db.dao.combat.CombatUnitDao;
import net.alteiar.db.dao.exception.DataException;

import org.slf4j.LoggerFactory;

public class CombatUnitDaoImpl extends XmlDao<CombatUnit> implements
		CombatUnitDao {

	public void initialize() {

		try {

			super.initialize(CombatUnit.class);
		} catch (JAXBException e) {

			LoggerFactory.getLogger(getClass()).error(
					"Fail to initialize the dao", e);
		}
	}

	@Override
	public void insert(CombatUnit combatUnit) throws DataException {

		combatUnit.setId(nextId());

		if (exist(combatUnit)) {

			String errorMsg = String
					.format("Fail to insert the combat unit {%s}, the combat unit already exist",
							combatUnit.getId());
			throw new DataException(errorMsg);
		}

		ObjectFactory factory = new ObjectFactory();

		saveFile(factory.createCombatUnit(combatUnit));

		fireDataAdded(combatUnit.getId());
	}

	@Override
	public void insertOrUpdate(CombatUnit combatUnit) throws DataException {

		boolean dataAdded = true;
		if (exist(combatUnit)) {

			dataAdded = false;
		}

		ObjectFactory factory = new ObjectFactory();
		saveFile(factory.createCombatUnit(combatUnit));

		if (dataAdded) {
			fireDataAdded(combatUnit.getId());
		} else {
			fireDataChanged(combatUnit.getId());
		}
	}

	@Override
	public void update(CombatUnit combatUnit) throws DataException {

		if (exist(combatUnit)) {

			ObjectFactory factory = new ObjectFactory();
			saveFile(factory.createCombatUnit(combatUnit));

			fireDataChanged(combatUnit.getId());
		} else {

			String errorMsg = String
					.format("The combat unit {%s} does not exist and cannot be updated",
							combatUnit.getId());
			throw new DataException(errorMsg);
		}
	}

	@Override
	public CombatUnit find(long id) throws DataException {

		CombatUnit found = null;

		if (exist(id)) {

			found = loadFile(id);
		}

		return found;
	}

	@Override
	public void delete(CombatUnit combatUnit) throws DataException {

		if (exist(combatUnit)) {

			super.deleteFile(combatUnit);

			fireDataRemoved(combatUnit.getId());
		} else {

			String errorMsg = String
					.format("Fail to delete the combat unit {%s}, the combat unit does not exist",
							combatUnit.getId());
			throw new DataException(errorMsg);
		}
	}

}
