package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.Hyperlink;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.scene.control.PasswordField;

public class LoginController {

	@FXML
	private TextField si_username;

	@FXML
	private TextField si_password;

	@FXML
	private TextField usernameField2;

	@FXML
	private Button si_loginBtn;

	@FXML
	private PasswordField passwordField2;

	@FXML
	private TextField firstnameField;

	@FXML
	private TextField lastnameField;

	@FXML
	private TextField phonenumberField;

	@FXML
	private Button resetButton;

	@FXML
	private TextField addressField;

	@FXML
	private TextField emailField;

	@FXML
	private Button confirmButton;

	@FXML
	private AnchorPane scenePane;

	@FXML
	private Pane scenePane2;

	@FXML
	private Hyperlink signInLink;

	@FXML
	private Hyperlink signUpLink;

	private Connection connect;
	private PreparedStatement prepare;
	private ResultSet result;
	private Alert alert;

	

// phần login

	public void loginBtn() {

		if (si_username.getText().isEmpty() || si_password.getText().isEmpty()) {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Message");
			alert.setHeaderText(null);
			alert.setContentText("Incorrect Username/Password");
			alert.showAndWait();
		} else {

			String selectData = "SELECT username, password FROM employee WHERE username = ? and password = ?";
			connect = database.connectDB();
			try {
				prepare = connect.prepareStatement(selectData);
				prepare.setString(1, si_username.getText());
				prepare.setString(2, si_password.getText());
				result = prepare.executeQuery();
				if (result.next()) {
					
					data.username = si_username.getText();
					
					Parent root = FXMLLoader.load(getClass().getResource("/application/fxml/Mainpage.fxml"));
					
					Stage stage = new Stage();
					Scene scene = new Scene(root);
					stage.setTitle("Phone Store Management System");
					stage.setScene(scene);
					stage.show();
					si_loginBtn.getScene().getWindow().hide();
				} else {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Message");
					alert.setHeaderText(null);
					alert.setContentText("Incorrect Username/Password");
					alert.showAndWait();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	// hyperlink đăng ký
	@FXML
	public void handleSignUp() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/fxml/signup.fxml"));
			Parent signUpRoot = fxmlLoader.load();
			Scene signUpScene = new Scene(signUpRoot);
			Stage currentStage = (Stage) signUpLink.getScene().getWindow();
			currentStage.setScene(signUpScene);
			currentStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// hyperlink login
	@FXML
	public void handleSignIn() {
		try {

			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/fxml/login.fxml"));
			Parent signInRoot = fxmlLoader.load();
			Scene signInScene = new Scene(signInRoot);
			Stage currentStage = (Stage) signInLink.getScene().getWindow();
			currentStage.setScene(signInScene);
			currentStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// lưu tài khoản vừa đky
	public void regBtn() {
		if (usernameField2.getText().isEmpty() || passwordField2.getText().isEmpty()
				|| phonenumberField.getText().isEmpty() || emailField.getText().isEmpty()
				|| addressField.getText().isEmpty()) {
			alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Message");
			alert.setHeaderText(null);
			alert.setContentText("Please fill all blank fields");
			alert.showAndWait();

		} else {

			String regData = "INSERT INTO employee (username, password, email, phonenumber, address)"
					+ "VALUES(?,?,?,?,?)";
			connect = database.connectDB();

			try {
				// kiểm tra tài khoản đã tồn tại chưa
				String checkUsername = "SELECT username FROM employee WHERE username = '" + usernameField2.getText()
						+ "'";

				prepare = connect.prepareStatement(checkUsername);
				result = prepare.executeQuery();

				if (result.next()) {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Message");
					alert.setHeaderText(null);
					alert.setContentText(usernameField2.getText() + " is already taken ");
					alert.showAndWait();
				} else

				if (passwordField2.getText().length() < 8) {
					alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Message");
					alert.setHeaderText(null);
					alert.setContentText("Password >8 required");
					alert.showAndWait();
				} else {
					prepare = connect.prepareStatement(regData);
					prepare.setString(1, usernameField2.getText());
					prepare.setString(2, passwordField2.getText());
					prepare.setString(3, emailField.getText());
					prepare.setString(4, phonenumberField.getText());
					prepare.setString(5, addressField.getText());
					prepare.executeUpdate();

					usernameField2.setText("");
					passwordField2.setText("");
					emailField.setText("");
					phonenumberField.setText("");		
					addressField.setText("");

					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/fxml/login.fxml"));
					Parent signInRoot = fxmlLoader.load();
					Scene signInScene = new Scene(signInRoot);
					Stage currentStage = (Stage) confirmButton.getScene().getWindow();
					currentStage.setScene(signInScene);
					currentStage.show();

				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

}
