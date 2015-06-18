package net.alteiar.dao.campaign.impl;

import javax.xml.bind.JAXBException;

import net.alteiar.campaign.Campaign;
import net.alteiar.campaign.ObjectFactory;
import net.alteiar.dao.util.XmlDao;
import net.alteiar.db.dao.CampaignDao;
import net.alteiar.db.dao.exception.DataException;

import org.slf4j.LoggerFactory;

public class CampaignDaoImpl extends XmlDao<Campaign> implements CampaignDao {

	public void initialize() {

		try {

			super.initialize(Campaign.class);
		} catch (JAXBException e) {

			LoggerFactory.getLogger(getClass()).error("Fail to initialize the dao", e);
		}
	}

	@Override
	public void insert(Campaign campaign) throws DataException {

		campaign.setId(nextId());

		if (exist(campaign)) {

			String errorMsg = String.format("Fail to insert the campaign {%s}, the campaign already exist",
					campaign.getId());
			throw new DataException(errorMsg);
		}

		ObjectFactory factory = new ObjectFactory();

		saveFile(factory.createCampaign(campaign));

		fireDataAdded(campaign.getId());
	}

	@Override
	public void update(Campaign campaign) throws DataException {

		if (exist(campaign)) {

			ObjectFactory factory = new ObjectFactory();
			saveFile(factory.createCampaign(campaign));

			fireDataChanged(campaign.getId());
		} else {

			String errorMsg = String.format("The campaign {%s} does not exist and cannot be updated", campaign.getId());
			throw new DataException(errorMsg);
		}
	}

	@Override
	public Campaign find(long id) throws DataException {

		Campaign found = null;

		if (exist(id)) {

			found = loadFile(id);
		}

		return found;
	}

	@Override
	public void delete(Campaign campaign) throws DataException {

		if (exist(campaign)) {

			super.deleteFile(campaign);

			fireDataRemoved(campaign.getId());
		} else {

			String errorMsg = String.format("Fail to delete the campaign {%s}, the campaign does not exist",
					campaign.getId());
			throw new DataException(errorMsg);
		}
	}

}
