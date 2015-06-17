package net.alteiar.ui.view.unit;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import net.alteiar.campaign.Campaign;
import net.alteiar.dao.api.DaoFactorySingleton;
import net.alteiar.db.dao.CampaignDao;
import net.alteiar.db.dao.exception.DataException;
import net.alteiar.engine.observer.DataModificationAdapter;
import net.alteiar.ui.exception.FXMLException;
import net.alteiar.ui.view.FxmlViewController;

public class UnitListView extends FxmlViewController implements Initializable {

	@FXML
	private FlowPane listUnits;

	@FXML
	private Button btnEdit;

	private final long campaignId;

	public UnitListView(Long campaignId) {

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

					final Parent parent = selection.loadView();

					parent.setOnDragDetected(evt -> {
						drag(evt, parent, unitId);
					});

					listUnits.getChildren().add(parent);
				} catch (FXMLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void drag(MouseEvent event, Parent source, Long unitId) {

		/* drag was detected, start a drag-and-drop gesture */
		/* allow any transfer mode */
		Dragboard db = source.startDragAndDrop(TransferMode.LINK);

		/* Put a string on a dragboard */
		ClipboardContent content = new ClipboardContent();
		content.putString(unitId.toString());
		db.setContent(content);

		event.consume();
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		btnEdit.setOnAction(event -> {
			editView();
		});

		this.revalidate();
	}

	private void editView() {

		getStage().replaceRoot(new UnitListViewEdit(campaignId));
	}
}
