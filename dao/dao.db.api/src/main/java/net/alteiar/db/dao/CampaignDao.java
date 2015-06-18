package net.alteiar.db.dao;

import net.alteiar.campaign.Campaign;
import net.alteiar.db.dao.exception.DataException;
import net.alteiar.engine.observer.DataObservable;

public interface CampaignDao extends DataObservable {

	void insert(Campaign campaign) throws DataException;

	void update(Campaign campaign) throws DataException;

	Campaign find(long id) throws DataException;

	void delete(Campaign campaign) throws DataException;
}
