package seedu.watson.ui;

import java.io.IOException;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.watson.auth.AuthHandler;
import seedu.watson.commons.core.GuiSettings;
import seedu.watson.logic.Logic;

/**
 * Class that handles the login window.
 */
public class LoginWindow extends UiPart<Stage> {

    private static final String FXML = "Login.fxml";

    private final Stage primaryStage;
    private final Logic logic;

    private final LoginErrorWindow loginErrorWindow;

    /**
     * Creates a {@code LoginWindow} with the given {@code Stage} and {@code Logic}.
     */
    public LoginWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);
        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;
        this.loginErrorWindow = new LoginErrorWindow();


        //Welcome Header
        Text welcomeHeader = new Text("Welcome to Watson!");
        welcomeHeader.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));

        //Description Header
        Text descriptionHeader = new Text("Your friendly teaching assistant");
        descriptionHeader.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));

        //prompt text
        Text promptLogin = new Text("For testing purposes, use username:admin, password:admin");
        promptLogin.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        promptLogin.setFill(Color.RED);
        // Logo
        Image logo = new Image("images/teachings.png");
        ImageView imageView = new ImageView(logo);
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);

        // Holder for text fields
        VBox vb = new VBox();
        HBox nameHolder = new HBox();
        HBox passwordHolder = new HBox();

        // Name field
        Label nameLabel = new Label("Username:");
        nameLabel.setMinWidth(70);
        final TextField nameField = new TextField();
        nameHolder.getChildren().addAll(nameLabel, nameField);
        nameHolder.setAlignment(Pos.CENTER);

        // Password field
        Label passwordLabel = new Label("Password:");
        passwordLabel.setMinWidth(70);
        final TextField passwordField = new TextField();
        passwordHolder.getChildren().addAll(passwordLabel, passwordField);
        passwordHolder.setAlignment(Pos.CENTER);

        // Submit button
        Button submit = new Button("Login");

        // Button event handler
        submit.setOnAction(
            e -> {
                if ((nameField.getText() != null && !nameField.getText().isEmpty())
                    && (passwordField.getText() != null && !passwordField.getText()
                    .isEmpty())) {
                    String nameText = nameField.getText();
                    String passwordText = passwordField.getText();
                    handleLogin(nameText, passwordText);
                } else {
                    throw new IllegalArgumentException("Username/Password cannot be empty!");
                }
            }
        );

        // TextField event handlers
        passwordField.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                if ((nameField.getText() != null && !nameField.getText().isEmpty())
                    && (passwordField.getText() != null && !passwordField.getText()
                    .isEmpty())) {
                    String nameText = nameField.getText();
                    String passwordText = passwordField.getText();
                    handleLogin(nameText, passwordText);
                } else {
                    throw new IllegalArgumentException("Username/Password cannot be empty!");
                }
            }
        });

        // Combining all components
        vb.getChildren().addAll(welcomeHeader, descriptionHeader, imageView,
                nameHolder, passwordHolder, submit, promptLogin);
        vb.setSpacing(10);
        vb.setAlignment(Pos.CENTER);
        Scene scene1 = new Scene(vb, 400, 250);
        primaryStage.setScene(scene1);

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());
    }

    private void handleLogin(String username, String password) {
        if (AuthHandler.checkCredentials(username, password)) {
            // If the credentials are correct, show the main window
            primaryStage.hide();
            MainWindow mainWindow = new MainWindow(primaryStage, logic);
            mainWindow.show(); //This should be called before creating other UI parts
            mainWindow.fillInnerParts();
        } else {
            // If the credentials are wrong, show an error message
            if (!loginErrorWindow.isShowing()) {
                loginErrorWindow.show();
            } else {
                loginErrorWindow.focus();
            }
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }
}
