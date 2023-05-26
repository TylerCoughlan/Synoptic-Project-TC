package com.drisq.minimalGraphicsProject.fx;

import java.net.URL;

import com.drisq.util.fx.FxUtil;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

//This is the initial Method for the GUI. This method locates the first FXML file 
// and then launches the GUI with the first scene.

public class GuiLauncher extends Application {

	private static final String FXML_RSC = "rsc/RetailSearchMenu.fxml";

	public static void main(String... args) {
		launch(args);
	}

	// This is where the the first stage is loaded and then launched into the GUI as
	// well as using ctrl. to call my methods from the controller.
	@Override
	public void start(Stage stage) throws Exception {
		URL url = getClass().getResource(FXML_RSC);
		Region root = FXMLLoader.load(url);
		PrimaryWindowController ctrl = (PrimaryWindowController) FxUtil.getController(root);

		// The below methods that are commented out are needing work. I am having
		// trouble being able to switch back to a previous scene.
		
		// ctrl.mensToSearch(stage);
		ctrl.home(stage);
		// ctrl.backButton(stage);
		ctrl.searchToResults(stage);
		ctrl.colourCombo("");
		ctrl.genderCombo("");
		ctrl.productsCombo("");
		ctrl.sizeCombo("");

		Scene scene = new Scene(root);
		stage.setTitle("Retail Stock Management");
		stage.setScene(scene);
		stage.show();
	}

}
