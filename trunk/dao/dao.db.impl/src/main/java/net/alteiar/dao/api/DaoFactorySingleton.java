package net.alteiar.dao.api;

import java.io.File;
import java.io.IOException;

import net.alteiar.dao.combat.impl.CombatTrackerDaoImpl;
import net.alteiar.dao.combat.impl.CombatUnitDaoImpl;
import net.alteiar.dao.unit.impl.UnitDaoImpl;
import net.alteiar.db.dao.CombatTrackerDao;
import net.alteiar.db.dao.CombatUnitDao;
import net.alteiar.db.dao.UnitDao;
import net.alteiar.db.dao.XmlDaoFactory;

import org.slf4j.LoggerFactory;

public class DaoFactorySingleton implements XmlDaoFactory {

	private static DaoFactorySingleton INSTANCE = new DaoFactorySingleton();

	public static XmlDaoFactory getInstance() {

		return INSTANCE;
	}

	private final UnitDaoImpl unitDao;

	private final CombatUnitDaoImpl combatUnitDao;

	private final CombatTrackerDaoImpl combatTrackerDao;

	public DaoFactorySingleton() {

		unitDao = new UnitDaoImpl();

		combatUnitDao = new CombatUnitDaoImpl();

		combatTrackerDao = new CombatTrackerDaoImpl();
	}

	@Override
	public void setRootDir(String rootDir) {

		File unitDaoFile = new File(rootDir + File.separator + "unit");

		if (!unitDaoFile.exists()) {
			unitDaoFile.mkdirs();
		}

		File combatTrackerDir = new File(rootDir + File.separator
				+ "combatTracker");

		if (!combatTrackerDir.exists()) {
			combatTrackerDir.mkdirs();
		}

		try {

			unitDao.setParentDir(unitDaoFile.getCanonicalPath());
			combatUnitDao.setParentDir(combatTrackerDir.getCanonicalPath());
			combatTrackerDao.setParentDir(combatTrackerDir.getCanonicalPath());
		} catch (IOException e) {

			LoggerFactory.getLogger(getClass()).error(
					"Fail to retrieve the canonical file", e);
		}
	}

	@Override
	public void initialize() {

		unitDao.initialize();
		combatTrackerDao.initialize();
		combatUnitDao.initialize();
	}

	@Override
	public UnitDao getUnitDao() {

		return unitDao;
	}

	@Override
	public CombatUnitDao getCombatUnitDao() {

		return combatUnitDao;
	}

	@Override
	public CombatTrackerDao getCombatTrackerDao() {

		return combatTrackerDao;
	}

}
