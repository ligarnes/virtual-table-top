package net.alteiar.ui.view.unit;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import net.alteiar.basictypes.Unit;
import net.alteiar.dao.api.DaoFactorySingleton;
import net.alteiar.db.dao.UnitDao;
import net.alteiar.db.dao.exception.DataException;
import net.alteiar.engine.observer.DataModificationAdapter;
import net.alteiar.ui.stage.InternalStageController;
import net.alteiar.ui.stage.StageManager;
import net.alteiar.ui.view.FxmlViewController;

public class UnitResumeView extends FxmlViewController implements Initializable {

	@FXML
	private HBox boxResume;

	@FXML
	private ImageView imgCharacter;

	@FXML
	private Label lblName;

	@FXML
	private Label lblClass;

	private final long characterId;

	public UnitResumeView(Long characterId) {

		this.characterId = characterId;

		UnitDao dao = DaoFactorySingleton.getInstance().getUnitDao();

		dao.addDataListener(new DataModificationAdapter(characterId) {

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

	public Unit getUnit() {

		UnitDao dao = DaoFactorySingleton.getInstance().getUnitDao();

		try {
			return dao.find(characterId);
		} catch (DataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		boxResume.addEventHandler(MouseEvent.MOUSE_CLICKED, evt -> {
			viewUnit();
		});
		revalidate();
	}

	private void viewUnit() {

		Stage stage = new Stage();
		stage.setTitle(getUnit().getName());

		String stageName = "unit.view." + characterId;

		if (!StageManager.getInstance().stageExist(stageName)) {

			StageManager.getInstance().addStage(stageName,
					new InternalStageController(stage, new UnitView(characterId)));
		}

		StageManager.getInstance().showStage(stageName);
	}

	private void revalidate() {

		final Unit character = getUnit();

		if (character != null) {

			if (character.getImage() != null) {
				imgCharacter.setImage(new Image(character.getImage()));
			}

			if (character.getName() != null && !character.getName().isEmpty()) {

				lblName.setText(character.getName());
			} else {

				lblName.setText("no name");
			}

			lblClass.setVisible(false);
		}
	}
}
