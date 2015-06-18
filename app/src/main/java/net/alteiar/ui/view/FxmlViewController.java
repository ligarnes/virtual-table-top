package net.alteiar.ui.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import net.alteiar.ui.exception.FXMLException;

public class FxmlViewController extends ViewController {

	public FxmlViewController() {
	}

	@Override
	public final Parent loadView() throws FXMLException {

		StringBuilder builder = new StringBuilder();
		builder.append("/");
		builder.append(getClass().getPackage().getName().replaceAll("\\.", "/"));
		builder.append("/");
		builder.append(getClass().getSimpleName());
		builder.append(".fxml");

		return loadView(builder.toString(), this);
	}

	private Parent loadView(String filename, Object controller) throws FXMLException {
		Parent node = null;

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
