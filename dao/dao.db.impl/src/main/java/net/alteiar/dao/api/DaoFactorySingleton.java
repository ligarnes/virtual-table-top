package net.alteiar.dao.api;

import java.io.File;
import java.io.IOException;

import net.alteiar.dao.campaign.impl.CampaignDaoImpl;
import net.alteiar.dao.campaign.impl.PlayerDaoImpl;
import net.alteiar.dao.combat.impl.CombatTrackerDaoImpl;
import net.alteiar.dao.combat.impl.CombatUnitDaoImpl;
import net.alteiar.dao.unit.impl.UnitDaoImpl;
import net.alteiar.db.dao.CampaignDao;
import net.alteiar.db.dao.UnitDao;
import net.alteiar.db.dao.XmlDaoFactory;
import net.alteiar.db.dao.combat.CombatTrackerDao;
import net.alteiar.db.dao.combat.CombatUnitDao;

import org.slf4j.LoggerFactory;

public class DaoFactorySingleton implements XmlDaoFactory {

	private static DaoFactorySingleton INSTANCE = new DaoFactorySingleton();

	public static XmlDaoFactory getInstance() {

		return INSTANCE;
	}

	private final UnitDaoImpl unitDao;

	private final CombatUnitDaoImpl combatUnitDao;

	private final CombatTrackerDaoImpl combatTrackerDao;

	private final CampaignDaoImpl campaignDao;

	private final PlayerDaoImpl playerDao;

	public DaoFactorySingleton() {

		unitDao = new UnitDaoImpl();

		combatUnitDao = new CombatUnitDaoImpl();

		combatTrackerDao = new CombatTrackerDaoImpl();

		campaignDao = new CampaignDaoImpl();

		playerDao = new PlayerDaoImpl();
	}

	private File getFile(String root, String child) {

		File dir = new File(root + File.separator + child);

		if (!dir.exists()) {
			dir.mkdirs();
		}

		return dir;
	}

	@Override
	public void setRootDir(String rootDir) {

		File campaignDir = getFile(rootDir, "campaign");

		File unitDaoDir = getFile(rootDir, "unit");

		File combatTrackerDir = getFile(rootDir, "combatTracker");

		File playerDir = getFile(rootDir, "player");

		try {

			unitDao.setParentDir(unitDaoDir.getCanonicalPath());
			combatUnitDao.setParentDir(combatTrackerDir.getCanonicalPath());
			combatTrackerDao.setParentDir(combatTrackerDir.getCanonicalPath());
			campaignDao.setParentDir(campaignDir.getCanonicalPath());
			playerDao.setParentDir(playerDir.getCanonicalPath());
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
		campaignDao.initialize();
		playerDao.initialize();
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

	@Override
	public CampaignDao getCampaignDao() {

		return campaignDao;
	}

}
