import javafx.fxml.FXML;
import javafx.scene.control.*;

public class WasteLogController {
    @FXML private ComboBox<String> sourceCombo;
    @FXML private ComboBox<String> reasonCombo;
    @FXML private TextField wasteWeightField;
    @FXML private TextField staffIdField;
    @FXML private PasswordField supervisorPinField;
    @FXML private CheckBox fineCheckBox;
    @FXML private TextArea explanationArea;
    @FXML private Label errorLabel;

    @FXML
    public void initialize() {
        sourceCombo.getItems().addAll("Table 1", "Table 3", "Table 7", "Kitchen Preparation Area", "Cold Storage Spoilage");
        reasonCombo.getItems().addAll("Customer Table Leftover", "Kitchen Prep Trim", "Expired Batch Spoilage", "Dropped Meat");
    }

    @FXML
    private void handleLogWaste() {
        errorLabel.setText("");
        
        // Validation 1: Required Fields
        if (sourceCombo.getValue() == null || reasonCombo.getValue() == null) {
            showError("Please select both Source and Waste Reason.");
            return;
        }
        if (staffIdField.getText().trim().isEmpty()) {
            showError("Kitchen Staff ID is required.");
            return;
        }
        
        // Validation 2: Weight Validation
        try {
            int grams = Integer.parseInt(wasteWeightField.getText().trim());
            if (grams <= 0 || grams > 10000) {
                showError("Waste weight must be between 1g and 10,000g.");
                return;
            }
        } catch (NumberFormatException e) {
            showError("Please enter a valid numeric value for Weight in grams.");
            return;
        }
        
        // Validation 3: Supervisor PIN Auth
        if (supervisorPinField.getText().trim().length() < 4) {
            showError("Dual-confirmation requires a valid 4-digit Supervisor PIN.");
            return;
        }

        // Simulate UI Data Handling
        System.out.println("Waste record logged with dual confirmation PIN.");
        errorLabel.setStyle("-fx-text-fill: green;");
        errorLabel.setText("Waste record successfully saved!");
    }

    private void showError(String msg) {
        errorLabel.setStyle("-fx-text-fill: red;");
        errorLabel.setText(msg);
    }
}