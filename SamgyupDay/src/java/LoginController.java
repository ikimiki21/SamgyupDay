import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private ComboBox<String> roleComboBox;
    @FXML private CheckBox rememberMeCheckBox;
    @FXML private Label errorLabel;

    @FXML
    public void initialize() {
        roleComboBox.getItems().addAll("Cashier", "Kitchen Head", "Team Leader");
    }

    @FXML
    private void handleLogin() {
        errorLabel.setText("");
        
        // Validation 1: Required fields
        if (usernameField.getText().trim().isEmpty()) {
            showError("Username/Staff ID is required.");
            return;
        }
        if (passwordField.getText().trim().isEmpty()) {
            showError("Password field cannot be empty.");
            return;
        }
        if (roleComboBox.getValue() == null) {
            showError("Please select an active system role.");
            return;
        }

        // Simulate UI Data Handling
        String user = usernameField.getText().trim();
        String role = roleComboBox.getValue();
        
        System.out.println("User authenticated: " + user + " as " + role);
        errorLabel.setStyle("-fx-text-fill: green;");
        errorLabel.setText("Login successful! Redirecting to dashboard...");
    }

    @FXML
    private void handleForgotPassword() {
        errorLabel.setStyle("-fx-text-fill: orange;");
        errorLabel.setText("Please contact your Team Leader to reset credentials.");
    }

    private void showError(String message) {
        errorLabel.setStyle("-fx-text-fill: red;");
        errorLabel.setText(message);
    }
}