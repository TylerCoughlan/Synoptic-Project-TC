package com.drisq.minimalGraphicsProject.fx;

import java.net.URL;

import com.drisq.util.fx.FxUtil;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class GuiLauncher extends Application {

	private static final String FXML_RSC = "rsc/RetailMainMenu.fxml";

	public static void main(String... args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		URL url = getClass().getResource(FXML_RSC);
		Region root = FXMLLoader.load(url);
		PrimaryWindowController ctrl = (PrimaryWindowController) FxUtil.getController(root);
		ctrl.quit();
		Scene scene = new Scene(root);
		stage.setTitle("Retail Main Menu");
		stage.setScene(scene);
		stage.show();
	}

}
