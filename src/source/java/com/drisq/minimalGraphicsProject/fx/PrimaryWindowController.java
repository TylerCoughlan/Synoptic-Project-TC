package com.drisq.minimalGraphicsProject.fx;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PrimaryWindowController {
	
	//FXML code for each object within the Retail main menu GUI
	
	@FXML
	private VBox _retailMainVbox;
	
	@FXML
	private HBox _retailMainLogoHbox;
	
	@FXML
	private ImageView  _retailMainLogo;
	
	@FXML
	private HBox _retailGenderHbox;
	
	@FXML
	private Button _mensBtn;
	
	@FXML
	private Button _ladiesBtn;
	
	@FXML
	private Button _boysBtn;
	
	@FXML
	private Button _girlsBtn;
	
	@FXML
	private HBox _productHbox;
	
	@FXML
	private Button _brandBtn;
	
	@FXML
	private Button _productBtn;
	
	//FXML for all objects within the Search scene in GUI
	
	@FXML
	private VBox _searchMainVbox;
	
	@FXML
	private HBox _searchLogoHbox;
	
	@FXML
	private ImageView _searchMainLogo;
	
	@FXML
	private Label _brandProductLabel;
	
	@FXML
	private VBox _searchComboVbox;
	
	@FXML
	private ComboBox _genderCombo;
	
	@FXML
	private ComboBox _brandProductCombo;
	
	@FXML
	private ComboBox _sizeCombo;
	
	@FXML
	private ComboBox _colourCombo;
	
	@FXML
	private Button _searchBtn;
	
	@FXML
	private HBox _searchHomeHbox;
	
	@FXML
	private Button _homeBtn;
	
	//FXML code for all objects within the Results scene in the GUI
	
	@FXML
	private VBox _resultsVboxMain;
	
	@FXML
	private HBox _resultsLogoHbox;
	
	@FXML
	private VBox _resultsTableVbox;
	
	@FXML
	private TableView _retailResultsTable;
	
	@FXML
	private TableColumn _productDescCol, _quantityCol, _availableCol;
	
	@FXML
	private Button _backBtnResults;
	
	@FXML
	private Button _homeBtnResults;
	
	@FXML
	private void initialize() {
	}
	
	public void quit() {
		
		Button quitButton = _mensBtn;
		quitButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				Platform.exit();
			}
		});
	}

}
