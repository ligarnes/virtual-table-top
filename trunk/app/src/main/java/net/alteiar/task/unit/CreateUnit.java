package net.alteiar.task.unit;

import net.alteiar.basictypes.Unit;
import net.alteiar.campaign.Campaign;
import net.alteiar.dao.api.DaoFactorySingleton;
import net.alteiar.db.dao.exception.DataException;
import net.alteiar.engine.task.impl.TaskBase;

public class CreateUnit extends TaskBase {

	private long campaignId;
	private final Unit unit;

	public CreateUnit(long campaignId, Unit unit) {
		this.unit = unit;
	}

	@Override
	public void execute() throws DataException {

		// apply the change
		DaoFactorySingleton.getInstance().getUnitDao().insert(unit);

		Campaign campaign = DaoFactorySingleton.getInstance().getCampaignDao().find(campaignId);

		campaign.getUnitsId().add(unit.getId());

		DaoFactorySingleton.getInstance().getCampaignDao().update(campaign);
	}
}
