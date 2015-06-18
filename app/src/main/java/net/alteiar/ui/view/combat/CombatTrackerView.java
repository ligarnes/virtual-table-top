package net.alteiar.ui.view.combat;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import net.alteiar.basictypes.CombatTracker;
import net.alteiar.basictypes.Faction;
import net.alteiar.dao.api.DaoFactorySingleton;
import net.alteiar.db.dao.exception.DataException;
import net.alteiar.engine.PlatformContext;
import net.alteiar.engine.observer.DataModificationAdapter;
import net.alteiar.task.combat.AddUnitToCombat;
import net.alteiar.ui.exception.FXMLException;
import net.alteiar.ui.view.FxmlViewController;

public class CombatTrackerView extends FxmlViewController implements Initializable {

	@FXML
	private VBox unitsView;

	private final Long combatTrackerId;

	public CombatTrackerView(CombatTracker combatTracker) {
		this(combatTracker.getId());
	}

	public CombatTrackerView(Long combatTrackerId) {
		this.combatTrackerId = combatTrackerId;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		DaoFactorySingleton.getInstance().getCombatTrackerDao()
				.addDataListener(new DataModificationAdapter(combatTrackerId) {

					@Override
					protected void dataDeleted() {
					}

					@Override
					protected void dataChanged() {
						Platform.runLater(() -> revalidate());
					}
				});

		unitsView.setOnDragEntered(evt -> {

			System.out.println("drag entered");
		});

		unitsView.setOnDragOver(evt -> {

			/*
			 * accept it only if it is not dragged from the same node and if it
			 * has a string data
			 */
			if (evt.getDragboard().hasString()) {
				/* allow for both copying and moving, whatever user chooses */
				evt.acceptTransferModes(TransferMode.LINK);
			}

			evt.consume();
		});

		unitsView.setOnDragDropped(evt -> {

			dropUnit(evt);
		});

		revalidate();
	}

	private void dropUnit(DragEvent event) {

		/* data dropped */
		/* if there is a string data on dragboard, read it and use it */
		Dragboard db = event.getDragboard();
		boolean success = false;
		if (db.hasString()) {

			Long unitId = Long.valueOf(db.getString());

			AddUnitToCombat task = new AddUnitToCombat(combatTrackerId, unitId, null, Faction.ALLY);

			PlatformContext.getInstance().getTaskEngine().enqueue(task);

			success = true;
		}
		/*
		 * let the source know whether the string was successfully transferred
		 * and used
		 */
		event.setDropCompleted(success);

		event.consume();
	}

	private CombatTracker getCombatTracker() {

		CombatTracker combatTracker = null;
		try {
			combatTracker = DaoFactorySingleton.getInstance().getCombatTrackerDao().find(combatTrackerId);
		} catch (DataException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return combatTracker;
	}

	private void revalidate() {

		unitsView.getChildren().clear();

		CombatTracker combatTracker = getCombatTracker();

		if (combatTracker != null) {

			for (Long characterId : combatTracker.getCombatUnitId()) {

				CombatTrackerCharacterView view = new CombatTrackerCharacterView(characterId);

				try {

					Node node = view.loadView();
					unitsView.getChildren().add(node);
				} catch (FXMLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
	}

}
