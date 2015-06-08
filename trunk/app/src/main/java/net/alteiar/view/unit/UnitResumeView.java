package net.alteiar.view.unit;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import net.alteiar.view.exception.FXMLException;
import net.alteiar.view.fxml.FxmlLoader;

public class UnitResumeView implements Initializable {

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

	public Node loadView() throws FXMLException {

		return FxmlLoader.getInstance().loadView("/view/unit/unitResumeView.fxml", this);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		boxResume.addEventHandler(MouseEvent.MOUSE_CLICKED, evt -> {
			viewUnit();
		});
		revalidate();
	}

	private void viewUnit() {

		Platform.runLater(() -> {
			Stage stage = new Stage();
			stage.setTitle("Character sheet");

			UnitView unitView = new UnitView(characterId);

			try {
				stage.setScene(new Scene((Parent) unitView.loadView()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Fill stage with content
			stage.show();
		});

	}

	private void revalidate() {

		final Unit character = getUnit();

		if (character != null) {

			imgCharacter.setImage(new Image(character.getImage()));

			lblName.setText(character.getName());

			lblClass.setVisible(false);
		}
	}
}
