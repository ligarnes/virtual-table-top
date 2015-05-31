package net.alteiar.db.dao;

public interface DaoFactory {

	void initialize();

	UnitDao getUnitDao();

	CombatUnitDao getCombatUnitDao();

	CombatTrackerDao getCombatTrackerDao();
}
