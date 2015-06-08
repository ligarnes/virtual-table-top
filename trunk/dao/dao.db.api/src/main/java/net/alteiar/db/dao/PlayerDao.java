package net.alteiar.db.dao;

import net.alteiar.campaign.Player;
import net.alteiar.db.dao.exception.DataException;

public interface PlayerDao {

	void insert(Player player) throws DataException;

	void update(Player player) throws DataException;

	Player find(long id) throws DataException;

	void delete(Player player) throws DataException;
}
