package com.drisq.minimalGraphicsProject.fx;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PrimaryWindowController {

	// FXML code for each object within the Retail main menu GUI

	@FXML
	private VBox _retailMainVbox;

	@FXML
	private HBox _retailMainLogoHbox;

	@FXML
	private ImageView _retailMainLogo;

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

	// FXML for all objects within the Search scene in GUI

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
	private ComboBox<genderSearch> _genderCombo;

	@FXML
	private ComboBox<product> _brandProductCombo;

	@FXML
	private ComboBox<size> _sizeCombo;

	@FXML
	private ComboBox<colour> _colourCombo;

	@FXML
	private Button _searchBtn;

	@FXML
	private HBox _searchHomeHbox;

	@FXML
	private Button _homeBtn;

	// FXML code for all objects within the Results scene in the GUI

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

	// This method sets the scene of the GUI to show the retail search menu when the
	// Mens button is selected.
	public void mensToSearch(Stage stage) throws Exception {

		Button mensButton = _mensBtn;
		mensButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				try {
					Parent root = FXMLLoader.load(getClass().getResource("rsc/RetailSearchMenu.fxml"));
					Scene scene = new Scene(root);
					stage.setTitle("Retail Search Menu");
					stage.setScene(scene);
					stage.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	public static ResultSet createTable() {

		String tableQuery = "SELECT ProductDesc, Brand, Qauntity  FROM OurProducts";
		return null;

//		try {
//			Statement tableQueryPS = () getConnection()).createStatement();
//			ResultSet tableNames = tableQueryPS.executeQuery(tableQuery);
//			System.out.println("Table query successful.");
//			return tableNames;
//
//		} catch (Exception e) {

	
	}

	// This Method sets the scene of the GUI to show the inventory of the store once
	// filters have been selected and the search button pressed
	public void searchToResults(Stage stage) throws Exception {

		Button searchButton = _searchBtn;
		searchButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				try {
					Parent root = FXMLLoader.load(getClass().getResource("rsc/RetailResults.fxml"));
					Scene scene = new Scene(root);
					stage.setTitle("Retail Stock");
					stage.setScene(scene);
					stage.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	// When the home button is pressed then this method works by sending the user
	// back to the main menu to be able to start the search again
	public void home(Stage stage) throws Exception {

		Button homeButton = _homeBtn;
		homeButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				try {
					Parent root = FXMLLoader.load(getClass().getResource("rsc/RetailMainMenu.fxml"));
					Scene scene = new Scene(root);
					stage.setTitle("Retail Main Menu");
					stage.setScene(scene);
					stage.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	public void backButton(Stage stage) throws Exception {

		Button backButton = _backBtnResults;
		backButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				try {
					Parent root = FXMLLoader.load(getClass().getResource("rsc/RetailSearchMenu.fxml"));
					Scene scene = new Scene(root);
					stage.setTitle("Retail Search Menu");
					stage.setScene(scene);
					stage.show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	// This method gathers the enumeration values from genderSearch and populates
	// the comboBox to select gender
	public void genderCombo(String text) {

		ComboBox<genderSearch> genderCombo = _genderCombo;
		genderCombo.getItems().addAll(genderSearch.values());

	}

	// This method gathers the enumeration values from colour and populates the
	// comboBox to select colour
	public void colourCombo(String text) {

		ComboBox<colour> colourCombo = _colourCombo;
		colourCombo.getItems().addAll(colour.values());

	}

	// This method gathers the enumeration values from size and populates the
	// comboBox to select size
	public void sizeCombo(String text) {

		ComboBox<size> sizeCombo = _sizeCombo;
		sizeCombo.getItems().addAll(size.values());
	}

	// This method gathers the enumeration values from product and populates the
	// comboBox to select the product tyoe
	public void productsCombo(String text) {

		ComboBox<product> productCombo = _brandProductCombo;
		productCombo.getItems().addAll(product.values());
	}

	// This method provides the enumeration values to populate the combo box to
	// filter by gender
	// on the search menu.

	public static enum genderSearch {
		MENS("Mens"), LADIES("Ladies"), BOYS("Boys"), GIRLS("Girls");

		private String text;

		private genderSearch(String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return text;
		}
	}

	// This method provides the enumeration values to populate the combo box to
	// filter by colour
	// on the search menu.
	public static enum colour {
		BEIGE("Beige"), BLACK("Black"), BLUE("Blue"), GREEN("Green"), GREY("Grey"), MULTI("Multi"), ORANGE("Orange"),
		PINK("Pink"), PURPLE("Purple"), RED("Red"), SILVER("Silver"), WHITE("White"), YELLOW("Yellow"), GOLD("Gold");

		private String text;

		private colour(String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return text;
		}
	}

	// This method provides the enumeration values to populate the combo box to
	// filter by size
	// on the search menu.
	public static enum size {
		SMALL("Small"), MEDIUM("Medium"), LARGE("Large"), XL("XL"), XXL("XXL");

		private String text;

		private size(String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return text;
		}
	}

	// This method provides the enumeration values to populate the combo box to
	// filter by shoe size
	// on the search menu if the product type selected is shoes
	public static enum shoeSize {
		SIX("6"), SIXHALF("6.5"), SEVEN("7"), SEVENHALF("7.5"), EIGHT("8"), EIGHTHALF("8.5"), NINE("9"),
		NINEHALF("9.5"), TEN("10"), TENHALF("10.5"), ELEVEN("11"), TWELVE("12"), THIRTEEN("13");

		private String text;

		private shoeSize(String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return text;
		}
	}

	// This method provides the enumeration values to populate the combo box to
	// filter by product type
	// on the search menu.
	public static enum product {
		FLEECES("Fleeces"), HOODIES("Hoodies"), JACKETS_AND_COATS("Jackets and Coats"), JEANS("Jeans"),
		POLOS("Polo Shirts"), SHIRTS("Shirts"), SHOES("Shoes"), SHORTS("Shorts"), SWEATSHIRTS("Sweatshirts"),
		TRACKSUIT_BOTTOMS("Tracksuit Bottoms"), TRACKSUITS("Tracksuits"), TROUSERS("Trousers"), TSHIRTS("T-Shirts"),
		DRESSES_SKIRTS("Dresses and Skirts"), TIGHTS_LEGGINGS("Tights and Leggings");

		private String text;

		private product(String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return text;
		}
	}

}
