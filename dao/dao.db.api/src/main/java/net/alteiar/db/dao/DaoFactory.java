package net.alteiar.db.dao;

import net.alteiar.db.dao.combat.CombatTrackerDao;
import net.alteiar.db.dao.combat.CombatUnitDao;

public interface DaoFactory {

	void initialize();

	CampaignDao getCampaignDao();

	UnitDao getUnitDao();

	CombatUnitDao getCombatUnitDao();

	CombatTrackerDao getCombatTrackerDao();
}
