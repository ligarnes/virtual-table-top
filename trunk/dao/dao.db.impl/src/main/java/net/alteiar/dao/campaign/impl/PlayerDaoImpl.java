package net.alteiar.dao.campaign.impl;

import javax.xml.bind.JAXBException;

import net.alteiar.campaign.ObjectFactory;
import net.alteiar.campaign.Player;
import net.alteiar.dao.util.XmlDao;
import net.alteiar.db.dao.PlayerDao;
import net.alteiar.db.dao.exception.DataException;

import org.slf4j.LoggerFactory;

public class PlayerDaoImpl extends XmlDao<Player> implements PlayerDao {

	public void initialize() {

		try {

			super.initialize(Player.class);
		} catch (JAXBException e) {

			LoggerFactory.getLogger(getClass()).error("Fail to initialize the dao", e);
		}
	}

	@Override
	public void insert(Player player) throws DataException {

		player.setId(nextId());

		if (exist(player)) {

			String errorMsg = String.format("Fail to insert the player {%s}, the player already exist", player.getId());
			throw new DataException(errorMsg);
		}

		ObjectFactory factory = new ObjectFactory();

		saveFile(factory.createPlayer(player));

		fireDataAdded(player.getId());
	}

	@Override
	public void update(Player player) throws DataException {

		if (exist(player)) {

			ObjectFactory factory = new ObjectFactory();
			saveFile(factory.createPlayer(player));

			fireDataChanged(player.getId());
		} else {

			String errorMsg = String.format("The player {%s} does not exist and cannot be updated", player.getId());
			throw new DataException(errorMsg);
		}
	}

	@Override
	public Player find(long id) throws DataException {

		Player found = null;

		if (exist(id)) {

			found = loadFile(id);
		}

		return found;
	}

	@Override
	public void delete(Player player) throws DataException {

		if (exist(player)) {

			super.deleteFile(player);

			fireDataRemoved(player.getId());
		} else {

			String errorMsg = String
					.format("Fail to delete the player {%s}, the player does not exist", player.getId());
			throw new DataException(errorMsg);
		}
	}

}
