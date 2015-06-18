package net.alteiar.ui.view.unit;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import net.alteiar.basictypes.Unit;
import net.alteiar.campaign.Campaign;
import net.alteiar.dao.api.DaoFactorySingleton;
import net.alteiar.db.dao.CampaignDao;
import net.alteiar.db.dao.exception.DataException;
import net.alteiar.engine.PlatformContext;
import net.alteiar.engine.observer.DataModificationAdapter;
import net.alteiar.task.unit.CreateUnit;
import net.alteiar.ui.exception.FXMLException;
import net.alteiar.ui.view.FxmlViewController;

public class UnitListViewEdit extends FxmlViewController implements Initializable {

	@FXML
	private FlowPane listUnits;

	@FXML
	private Button btnAdd;
	@FXML
	private Button btnReadOnly;
	@FXML
	private Button btnImport;

	private final long campaignId;

	public UnitListViewEdit(Long campaignId) {

		this.campaignId = campaignId;

		CampaignDao dao = DaoFactorySingleton.getInstance().getCampaignDao();

		dao.addDataListener(new DataModificationAdapter(campaignId) {

			@Override
			protected void dataDeleted() {

				Platform.runLater(() -> revalidate());
			}

			@Override
			protected void dataChanged() {

				Platform.runLater(() -> revalidate());
			}
		});
	}

	public Campaign getCampaign() {

		CampaignDao dao = DaoFactorySingleton.getInstance().getCampaignDao();

		try {
			return dao.find(campaignId);
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private void revalidate() {

		listUnits.getChildren().clear();
		final Campaign campaign = getCampaign();

		if (campaign != null) {

			for (Long unitId : campaign.getUnitsId()) {

				UnitResumeView selection = new UnitResumeView(unitId);

				try {
					listUnits.getChildren().add(selection.loadView());
				} catch (FXMLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		btnReadOnly.setOnAction(event -> {
			readOnlyView();
		});

		btnAdd.setOnAction(event -> {
			addUnit();
		});

		btnImport.setOnAction(event -> {
			importUnit();
		});

		this.revalidate();
	}

	private void addUnit() {

		PlatformContext.getInstance().getTaskEngine().enqueue(new CreateUnit(campaignId, new Unit()));
	}

	private void importUnit() {

	}

	private void readOnlyView() {

		getStage().replaceRoot(new UnitListView(campaignId));
	}
}
