package net.alteiar.dao.combat.impl;

import javax.xml.bind.JAXBException;

import net.alteiar.basictypes.CombatTracker;
import net.alteiar.basictypes.ObjectFactory;
import net.alteiar.dao.util.XmlDao;
import net.alteiar.db.dao.combat.CombatTrackerDao;
import net.alteiar.db.dao.exception.DataException;

import org.slf4j.LoggerFactory;

public class CombatTrackerDaoImpl extends XmlDao<CombatTracker> implements CombatTrackerDao {

	public void initialize() {

		try {

			super.initialize(CombatTracker.class);
		} catch (JAXBException e) {

			LoggerFactory.getLogger(getClass()).error("Fail to initialize the dao", e);
		}
	}

	@Override
	public void insert(CombatTracker combatTracker) throws DataException {

		combatTracker.setId(nextId());

		if (exist(combatTracker)) {

			String errorMsg = String.format("Fail to insert the combat tracker {%s}, the combat tracker already exist",
					combatTracker.getId());
			throw new DataException(errorMsg);
		}

		ObjectFactory factory = new ObjectFactory();

		saveFile(factory.createCombatTracker(combatTracker));

		fireDataAdded(combatTracker.getId());
	}

	@Override
	public void update(CombatTracker combatTracker) throws DataException {

		if (exist(combatTracker)) {

			ObjectFactory factory = new ObjectFactory();
			saveFile(factory.createCombatTracker(combatTracker));

			fireDataChanged(combatTracker.getId());
		} else {

			String errorMsg = String.format("The combat tracker {%s} does not exist and cannot be updated",
					combatTracker.getId());
			throw new DataException(errorMsg);
		}
	}

	@Override
	public CombatTracker find(long id) throws DataException {

		CombatTracker found = null;

		if (exist(id)) {

			found = loadFile(id);
		}

		return found;
	}

	@Override
	public void delete(CombatTracker combatUnit) throws DataException {

		if (exist(combatUnit)) {

			super.deleteFile(combatUnit);

			fireDataRemoved(combatUnit.getId());
		} else {

			String errorMsg = String.format(
					"Fail to delete the combat tracker {%s}, the combat tracker does not exist", combatUnit.getId());
			throw new DataException(errorMsg);
		}
	}

}
