package net.alteiar.dao.impl;

import net.alteiar.combattracker.CombatUnit;
import net.alteiar.combattracker.Faction;
import net.alteiar.dao.combat.impl.CombatUnitDaoImpl;
import net.alteiar.db.dao.exception.DataException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CombatUnitDaoImplTest {

	private static CombatUnitDaoImpl dao;

	@BeforeClass
	public static void beforeTest() {

		dao = new CombatUnitDaoImpl();

		dao.setParentDir(".");
		dao.initialize();
	}

	@Test
	public void testInsert() throws DataException {

		CombatUnit combatUnit = new CombatUnit();
		combatUnit.setFaction(Faction.ALLY);
		combatUnit.setInitiative(5);
		combatUnit.setUnitId(2);

		dao.insert(combatUnit);

		CombatUnit found = dao.find(combatUnit.getId());

		Assert.assertEquals(combatUnit.getId(), found.getId());
		Assert.assertEquals(combatUnit.getFaction(), found.getFaction());
		Assert.assertEquals(combatUnit.getInitiative(), found.getInitiative());
		Assert.assertEquals(combatUnit.getUnitId(), found.getUnitId());

		combatUnit.setFaction(Faction.NONE);
		combatUnit.setInitiative(14);
		combatUnit.setUnitId(6);

		dao.update(combatUnit);

		found = dao.find(combatUnit.getId());

		Assert.assertEquals(combatUnit.getId(), found.getId());
		Assert.assertEquals(combatUnit.getFaction(), found.getFaction());
		Assert.assertEquals(combatUnit.getInitiative(), found.getInitiative());
		Assert.assertEquals(combatUnit.getUnitId(), found.getUnitId());

		dao.delete(found);
	}

	@Test
	public void testFindInvalid() throws DataException {

		CombatUnit combatUnit = new CombatUnit();
		combatUnit.setFaction(Faction.ENNEMY);
		combatUnit.setInitiative(5);
		combatUnit.setUnitId(2);

		dao.insert(combatUnit);

		CombatUnit found = dao.find(combatUnit.getId() + 1);

		Assert.assertNull(found);

		dao.delete(combatUnit);
	}

	@Test
	public void testUpdateInvalid() throws DataException {

		CombatUnit combatUnit = new CombatUnit();
		combatUnit.setFaction(Faction.ENNEMY);
		combatUnit.setInitiative(5);
		combatUnit.setUnitId(2);

		dao.insert(combatUnit);

		combatUnit.setId(combatUnit.getId() + 1);
		combatUnit.setFaction(Faction.ALLY);

		try {
			dao.update(combatUnit);
			Assert.fail("The update must fail");
		} catch (DataException ex) {

		}

		combatUnit.setId(combatUnit.getId() - 1);

		CombatUnit found = dao.find(combatUnit.getId());

		Assert.assertEquals(Faction.ENNEMY, found.getFaction());
		Assert.assertEquals(combatUnit.getInitiative(), found.getInitiative());
		Assert.assertEquals(combatUnit.getUnitId(), found.getUnitId());

		dao.delete(combatUnit);
	}

	@Test
	public void testDeleteInvalid() throws DataException {

		CombatUnit combatUnit = new CombatUnit();
		combatUnit.setFaction(Faction.ENNEMY);
		combatUnit.setInitiative(5);
		combatUnit.setUnitId(2);

		dao.insert(combatUnit);

		combatUnit.setId(combatUnit.getId() + 1);

		try {
			dao.delete(combatUnit);
			Assert.fail("The delete must fail");
		} catch (DataException ex) {

		}

		combatUnit.setId(combatUnit.getId() - 1);

		CombatUnit found = dao.find(combatUnit.getId());

		Assert.assertEquals(Faction.ENNEMY, found.getFaction());
		Assert.assertEquals(combatUnit.getInitiative(), found.getInitiative());
		Assert.assertEquals(combatUnit.getUnitId(), found.getUnitId());

		dao.delete(combatUnit);
	}

	@Test
	public void testInsertOrUpdate() throws DataException {

		CombatUnit combatUnit = new CombatUnit();
		combatUnit.setFaction(Faction.ALLY);
		combatUnit.setInitiative(5);
		combatUnit.setUnitId(2);

		dao.insertOrUpdate(combatUnit);

		CombatUnit found = dao.find(combatUnit.getId());

		Assert.assertEquals(combatUnit.getId(), found.getId());
		Assert.assertEquals(combatUnit.getFaction(), found.getFaction());
		Assert.assertEquals(combatUnit.getInitiative(), found.getInitiative());
		Assert.assertEquals(combatUnit.getUnitId(), found.getUnitId());

		combatUnit.setFaction(Faction.NONE);
		combatUnit.setInitiative(14);
		combatUnit.setUnitId(6);

		dao.insertOrUpdate(combatUnit);

		found = dao.find(combatUnit.getId());

		Assert.assertEquals(combatUnit.getId(), found.getId());
		Assert.assertEquals(combatUnit.getFaction(), found.getFaction());
		Assert.assertEquals(combatUnit.getInitiative(), found.getInitiative());
		Assert.assertEquals(combatUnit.getUnitId(), found.getUnitId());

		dao.delete(found);
	}
}
