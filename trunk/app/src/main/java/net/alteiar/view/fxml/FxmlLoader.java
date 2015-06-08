package net.alteiar.view.fxml;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import net.alteiar.view.exception.FXMLException;

public class FxmlLoader {

	private static final FxmlLoader INSTANCE = new FxmlLoader();

	public static FxmlLoader getInstance() {

		return INSTANCE;
	}

	public Node loadView(String filename, Object controller) throws FXMLException {
		Node node = null;

		try {

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(filename));
			fxmlLoader.setController(controller);

			node = fxmlLoader.load();
		} catch (IOException ex) {

			throw new FXMLException("Fail to load the FMXL view named {%s}", ex);
		}

		return node;
	}
}
