package net.alteiar.view.unit;

import java.io.IOException;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import net.alteiar.campaign.Campaign;
import net.alteiar.dao.api.DaoFactorySingleton;
import net.alteiar.db.dao.CampaignDao;
import net.alteiar.db.dao.exception.DataException;
import net.alteiar.engine.observer.DataModificationAdapter;
import net.alteiar.view.exception.FXMLException;

public class UnitSelectionView {

	private FlowPane flow;

	private final long campaignId;

	public UnitSelectionView(Long campaignId) {

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

	public Node loadView() throws IOException {

		flow = new FlowPane();
		flow.setVgap(10);
		flow.setHgap(10);

		HBox hbox = new HBox();
		hbox.getChildren().add(flow);
		hbox.setMinHeight(150);
		hbox.setMinWidth(300);

		HBox.setMargin(flow, new Insets(10, 10, 10, 10));
		HBox.setHgrow(flow, Priority.ALWAYS);

		this.revalidate();
		return hbox;
	}

	private void revalidate() {

		flow.getChildren().clear();
		final Campaign campaign = getCampaign();

		if (campaign != null) {

			for (Long unitId : campaign.getUnitsId()) {

				UnitResumeView selection = new UnitResumeView(unitId);

				try {
					flow.getChildren().add(selection.loadView());
				} catch (FXMLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
