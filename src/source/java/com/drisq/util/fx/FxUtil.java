package com.drisq.util.fx;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class FxUtil {

  private static final Logger LOGGER = Logger.getLogger("com.drisq.util.fx");

  private static final Insets EXCPET_PAD_FIRST = new Insets(0, 5, 3, 0);

  private static final Insets EXCPET_PAD_OTHER = new Insets(8, 5, 3, 0);

  /**
   * Gets the window that this node is directly contained in.
   *
   * @param node The node of interest
   * @return The window that this node is directly contained in.
   */
  public static Window getWindow(Node node) {
    return node.getScene().getWindow();
  }

  /**
   * Gets the stage window that this node is directly contained in.
   *
   * @param node the node of interest.
   * @return the stage window that this node is directly contained in.
   * @throws ClassCastException when the {@link Window} associated with the node
   *           is not a {@link Stage}.
   */
  public static Stage getStageWindow(Node node) {
    return (Stage) node.getScene().getWindow();
  }

  public static Object getController(Node fxNode) {
    Object controller = null;
    do {
      controller = fxNode.getProperties().get("controller");
      fxNode = fxNode.getParent();
    } while (controller == null && fxNode != null);
    return controller;
  }

  public static Region loadFxmlSlot(AnchorPane slot, String fxmlRscName, Class<?> baseClass)
    throws IOException {

    URL url = baseClass.getResource(fxmlRscName);
    Region root = FXMLLoader.load(url);

    bindNodeToAllEdgesOfAnchorPane(root);
    slot.getChildren().add(root);

    return root;
  }

  public static void bindNodeToAllEdgesOfAnchorPane(Node node) {
    AnchorPane.setLeftAnchor(node, 0d);
    AnchorPane.setRightAnchor(node, 0d);
    AnchorPane.setTopAnchor(node, 0d);
    AnchorPane.setBottomAnchor(node, 0d);
  }

  public static <T> void addChoiceBoxUpdateFn(ChoiceBox<T> cBox, BiConsumer<T, T> updateFn) {
    cBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<T>() {

      @Override
      public void changed(ObservableValue<? extends T> obsVal, T oldVal, T newVal) {
        updateFn.accept(oldVal, newVal);
      }
    });
  }

  public static void addTextInputUpdateFn(
    TextInputControl tiControl,
    Consumer<String> resetFn,
    Consumer<String> updateFn) {

    tiControl.setOnKeyPressed(new EventHandler<KeyEvent>() {

      @Override
      public void handle(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
          updateFn.accept(tiControl.getText());

        } else if (event.getCode() == KeyCode.ESCAPE) {
          resetFn.accept(tiControl.getText());

        }
      }
    });

    tiControl.focusedProperty().addListener(new ChangeListener<Boolean>() {

      @Override
      public void changed(ObservableValue<? extends Boolean> ov, Boolean oldVal, Boolean focused) {
        if (!focused) {
          updateFn.accept(tiControl.getText());
        }
      }
    });
  }

  public static void runLater(Consumer<Void> consumer) {
    Platform.runLater(new Runnable() {

      @Override
      public void run() {
        consumer.accept(null);
      }
    });
  }

  /**
   * Hides a given node
   *
   * @param node The node to be hidden
   */
  public static void hideNode(Node node) {
    setHidden(node, true);
  }

  /**
   * Shows a given node
   *
   * @param node The node to be unhidden
   */
  public static void unhideNode(Node node) {
    setHidden(node, false);
  }

  /**
   * Hides/shows a given node
   *
   * @param node The node to be hidden/unhidden
   * @param hide Boolean value to determine whether the node is hidden/unhidden
   */
  public static void setHidden(Node node, boolean hide) {
    node.setManaged(!hide);
    node.setVisible(!hide);
  }

  public static Stage newStage(Window owner, String fxmlRscPath, Class<?> baseClass, String title) {
    Stage stage = new Stage();
    stage.initOwner(owner);
    initStage(stage, fxmlRscPath, baseClass, title);
    return stage;
  }

  public static void initStage(Stage stage, String fxmlRscPath, Class<?> baseClass, String title) {
    Region root = loadFxmlRsc(fxmlRscPath, baseClass);
    Scene scene = new Scene(root);
    stage.setTitle(title);
    stage.setScene(scene);
    stage.setMinHeight(root.getMinHeight() + 12);
    stage.setMinWidth(root.getMinWidth() + 2);
  }

  public static Region loadFxmlRsc(String fxmlRscPath, Class<?> baseClass) throws InternalError {
    URL url = baseClass.getResource(fxmlRscPath);
    Region root;
    try {
      LOGGER.fine("Loading FXML rsc: " + url);
      root = FXMLLoader.load(url);
      LOGGER.fine("Loaded FXML rsc: " + url);
    } catch (IOException e) {
      LOGGER.log(Level.WARNING, "Cannot load FXML resource: " + fxmlRscPath, e);
      throw new InternalError("Cannot load FXML resource: " + fxmlRscPath, e);
    }
    return root;
  }

  public static void initialiseAlertLayout(Alert alert) {
    // Stackoverflow Workaround to overcome Alert resizing feature.
    alert.getDialogPane().expandedProperty().addListener((l) -> {
      Platform.runLater(() -> {
        alert.getDialogPane().requestLayout();
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.sizeToScene();
        alert.setResizable(true);
      });
    });

    // Stackoverflow Workaround for JavaFX bug on Linux OS.
    alert.getDialogPane().getChildren().stream().filter(node -> node instanceof Label)
      .forEach(node -> ((Label) node).setMinHeight(Region.USE_PREF_SIZE));

    Window window = alert.getDialogPane().getScene().getWindow();
    Stage stage = (Stage) window;
    stage.setMinWidth(300);
    stage.setMinHeight(150);
    stage.sizeToScene();
  }

  public static Optional<ButtonType> popupConfirmationDialogueBox(
    Window owner,
    String title,
    String headerText,
    String contentText) {

    return popupNoticeDialogueBox(owner, title, headerText, contentText, AlertType.CONFIRMATION);
  }

  public static Optional<ButtonType> popupInfoDialogueBox(
    Window owner,
    String title,
    String headerText,
    String contentText) {

    return popupNoticeDialogueBox(owner, title, headerText, contentText, AlertType.INFORMATION);

  }

  public static Optional<ButtonType> popupWarningDialogueBox(
    Window owner,
    String title,
    String headerText,
    String contentText) {

    return popupNoticeDialogueBox(owner, title, headerText, contentText, AlertType.WARNING);
  }

  public static Optional<ButtonType> popupErrorDialogueBox(
    Window owner,
    String title,
    String headerText,
    String contentText) {

    return popupNoticeDialogueBox(owner, title, headerText, contentText, AlertType.ERROR);
  }

  private static Optional<ButtonType> popupNoticeDialogueBox(
    Window owner,
    String title,
    String headerText,
    String contentText,
    AlertType alertType) {

    Alert alert = new Alert(alertType, contentText);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setResizable(true);
    alert.initModality(Modality.APPLICATION_MODAL);
    alert.initOwner(owner);

    Optional<ButtonType> result = alert.showAndWait();
    return result;
  }

  public static Optional<ButtonType> popupLogMessageDialogueBox(
    Window owner,
    String title,
    String headerText,
    String contentText) {

    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setResizable(true);
    alert.initModality(Modality.APPLICATION_MODAL);
    alert.initOwner(owner);

    TextArea textArea = createTextArea(contentText);

    GridPane.setVgrow(textArea, Priority.ALWAYS);
    GridPane.setHgrow(textArea, Priority.ALWAYS);

    GridPane expContent = new GridPane();
    expContent.setMaxWidth(Double.MAX_VALUE);

    alert.getDialogPane().setContent(textArea);

    Optional<ButtonType> result = alert.showAndWait();
    return result;
  }

  /**
   * Display a message in a fresh 'Yes/No' pop-up Dialogue box.
   *
   * @param owner the Window or Stage that the pop-up Dialogue box belongs to.
   * @param titleText the pop-up window's title.
   * @param headerText the pop-up window's summary message.
   * @param contentText the pop-up window's detailed message.
   * @return the displayed Alert window.
   */
  public static Optional<Boolean> popupYesNoQuestion(
    Window owner,
    String titleText,
    String headerText,
    String contentText) {

    ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
    ButtonType no = new ButtonType("No", ButtonBar.ButtonData.NO);
    ButtonType cancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

    Alert alert = new Alert(AlertType.CONFIRMATION, contentText, cancel, no, yes);
    alert.setTitle(titleText);
    alert.setHeaderText(headerText);
    alert.setResizable(true);
    alert.initModality(Modality.APPLICATION_MODAL);
    alert.initOwner(owner);

    initialiseAlertLayout(alert);

    Optional<ButtonType> answer = alert.showAndWait();
    Optional<Boolean> result;
    if (!answer.isPresent() || answer.get() == cancel) {
      result = Optional.empty();
    } else {
      result = Optional.of(answer.get() == yes);
    }
    return result;
  }

  /**
   * Display a message in a fresh 'Alert' pop-up Dialogue box.
   *
   * @param owner The Window or Stage that the pop-up Dialogue box belongs to.
   * @param titleText The pop-up window's title.
   * @param headerText The pop-up window's summary message.
   * @param message The pop-up window's content text
   * @param causeSummary
   * @param stackTrace
   * @return The displayed Alert window.
   */
  public static Alert popupExceptionDialogueBox(
    Window owner,
    String titleText,
    String headerText,
    String message,
    String causeSummary,
    String stackTrace) {

    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(titleText);
    alert.setHeaderText(headerText);
    alert.setContentText(message);
    alert.setResizable(true);
    alert.initOwner(owner);

    TextArea textArea1 = causeSummary == null ? null : createTextArea(causeSummary);
    TextArea textArea2 = createTextArea(stackTrace);
    textArea2.setMinHeight(70);

    VBox expContent = new VBox();
    expContent.setFillWidth(true);
    VBox.setVgrow(textArea2, Priority.ALWAYS);

    if (textArea1 != null) {
      textArea1.setMinHeight(70);
      VBox.setVgrow(textArea1, Priority.ALWAYS);
    }

    ObservableList<Node> children = expContent.getChildren();

    if (textArea1 != null) {
      double textheight = 15 * countLines(causeSummary);
      if (textheight > 150) {
        textArea1.setMaxHeight(textheight);
        textheight = 150;
      } else {
        textArea1.setMaxHeight(textheight < 70 ? 85 : textheight + 15);
      }
      textArea1.setPrefHeight(textheight);
      textArea2.setPrefHeight(100);

      children.add(createPaddedLabel("The cause(s) were:", EXCPET_PAD_FIRST));
      children.add(textArea1);
      children.add(createPaddedLabel("The exception stacktrace was:", EXCPET_PAD_OTHER));
      children.add(textArea2);
    } else {
      children.add(createPaddedLabel("The exception stacktrace was:", EXCPET_PAD_FIRST));
      children.add(textArea2);
    }

    alert.getDialogPane().setExpandableContent(expContent);

    initialiseAlertLayout(alert);

    alert.show();

    return alert;
  }

  private static Label createPaddedLabel(String text, Insets insets) {
    Label label = new Label(text);

    label.setPadding(insets);

    return label;
  }

  /**
   * Display a message in a fresh 'Alert' pop-up Dialogue box.
   *
   * @param owner The Window or Stage that the pop-up Dialogue box belongs to.
   * @param titleText The pop-up window's title.
   * @param headerText The pop-up window's summary message.
   * @param exception the exception being presented.
   * @return The displayed Alert window.
   */
  public static Alert popupExceptionDialogueBox(
    Window owner,
    String titleText,
    String headerText,
    Throwable exception) {

    String msg = exception.getMessage();
    String summary = getCauses(exception.getCause());
    String trace = getStackTrace(exception);

    return popupExceptionDialogueBox(owner, titleText, headerText, msg, summary, trace);
  }

  private static int countLines(String text) {
    return (int) text.chars().filter(ch -> ch == '\n').count();
  }

  public static String getStackTrace(Throwable exception) {
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);

    exception.printStackTrace(pw);
    String exceptionText = sw.toString();
    return exceptionText;
  }

  public static String getCauses(Throwable cause) {
    String result;
    if (cause == null) {
      result = null;
    } else {
      StringBuilder contentText = new StringBuilder();

      do {
        contentText.append(cause.getMessage());
        contentText.append("\n");
        cause = cause.getCause();
      } while (cause != null);

      result = contentText.toString();
    }
    return result;
  }

  /**
   * Creates a text area that isn't editable and wrapped text.
   *
   * @param exceptionText The text to go into the text area
   * @return A text area
   */
  public static TextArea createTextArea(String exceptionText) {
    TextArea textArea = new TextArea(exceptionText);
    textArea.setEditable(false);
    textArea.setWrapText(true);

    textArea.setMaxWidth(Double.MAX_VALUE);
    textArea.setMaxHeight(Double.MAX_VALUE);
    return textArea;
  }

  /**
   * Returns the value of the check box
   * <p>
   * If selected it returns true, if not selected it returns false and if it is
   * indeterminate it returns null
   * </p>
   *
   * @param cb The check box of interest
   * @return The boolean value of the check box
   */
  public static Boolean getCheckBoxValue(CheckBox cb) {
    return cb.isSelected() ? Boolean.TRUE : cb.isIndeterminate() ? null : false;
  }

  /**
   * Sets the value of the check box
   * <p>
   * Sets the value of the check box to selected if val is true, unselected if
   * false or indeterminate is null
   * </p>
   *
   * @param cb The check box of interest
   * @param val Boolean value to determine what to set the check box as
   */
  public static void setCheckBoxValue(CheckBox cb, Boolean val) {
    cb.setSelected(val == Boolean.TRUE);
    cb.setIndeterminate(val == null);
  }

  public static class CheckBoxPredicate<T> implements Predicate<T> {

    private final BiPredicate<T, Boolean> pred;

    private Boolean cachedCheckBoxValue;

    public CheckBoxPredicate(BiPredicate<T, Boolean> pred) {
      this.pred = pred;
    }

    @Override
    public boolean test(T t) {
      return cachedCheckBoxValue == null || pred.test(t, cachedCheckBoxValue);
    }

    public void setValue(Boolean checkBoxValue) {
      cachedCheckBoxValue = checkBoxValue;
    }

    public void setValue(CheckBox cb) {
      cachedCheckBoxValue = getCheckBoxValue(cb);
    }
  }
}
