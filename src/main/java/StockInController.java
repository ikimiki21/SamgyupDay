import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.time.LocalDate;

public class StockInController {
    @FXML private TextField supplierField;
    @FXML private TextField invoiceField;
    @FXML private ComboBox<String> meatTypeCombo;
    @FXML private ComboBox<String> itemCutCombo;
    @FXML private TextField weightField;
    @FXML private DatePicker deliveryDatePicker;
    @FXML private DatePicker expirationDatePicker;
    @FXML private RadioButton passedRadio;
    @FXML private RadioButton conditionalRadio;
    @FXML private TextArea notesArea;
    @FXML private Label errorLabel;

    private ToggleGroup qualityGroup;

    @FXML
    public void initialize() {
        meatTypeCombo.getItems().addAll("Pork", "Beef");
        itemCutCombo.getItems().addAll("Samgyupsal", "Moksal", "Woo Samgyup", "Yangnyeom Beef");
        
        qualityGroup = new ToggleGroup();
        passedRadio.setToggleGroup(qualityGroup);
        conditionalRadio.setToggleGroup(qualityGroup);
        
        deliveryDatePicker.setValue(LocalDate.now());

        FormUtils.tabMovesFocus(notesArea);
    }

    @FXML
    private void handleSaveStockIn() {
        errorLabel.setText("");
        
        // Validation 1: Required Fields
        if (supplierField.getText().trim().isEmpty() || invoiceField.getText().trim().isEmpty()) {
            showError("Supplier Name and Invoice # are required.");
            return;
        }
        if (meatTypeCombo.getValue() == null || itemCutCombo.getValue() == null) {
            showError("Please select both Meat Type and Item Cut.");
            return;
        }
        
        // Validation 2: Weight Numeric Check
        double weight;
        try {
            weight = Double.parseDouble(weightField.getText().trim());
            if (weight <= 0.0 || weight > 500.0) {
                showError("Weight must be greater than 0.00 kg and less than 500.00 kg.");
                return;
            }
        } catch (NumberFormatException e) {
            showError("Please enter a valid numeric value for Weight (kg).");
            return;
        }
        
        // Validation 3: Date Validation
        if (expirationDatePicker.getValue() == null || expirationDatePicker.getValue().isBefore(LocalDate.now())) {
            showError("Expiration Date must be set to today or a future date.");
            return;
        }

        // Simulate UI Data Handling
        try {
            FormUtils.appendCsv("stockin.csv",
                    new String[] {"supplier", "invoice", "meat_type", "cut", "weight_kg",
                            "delivery_date", "expiration_date", "quality", "notes"},
                    supplierField.getText().trim(), invoiceField.getText().trim(),
                    meatTypeCombo.getValue(), itemCutCombo.getValue(), weight,
                    deliveryDatePicker.getValue(), expirationDatePicker.getValue(),
                    passedRadio.isSelected() ? "Passed" : "Conditional", notesArea.getText());
        } catch (IOException e) {
            showError("Could not save stock-in batch: " + e.getMessage());
            return;
        }
        errorLabel.setStyle("-fx-text-fill: green;");
        errorLabel.setText("Stock-in batch logged successfully!");
    }

    @FXML
    private void handleClear() {
        supplierField.clear();
        invoiceField.clear();
        weightField.clear();
        notesArea.clear();
        meatTypeCombo.setValue(null);
        itemCutCombo.setValue(null);
        expirationDatePicker.setValue(null);
        errorLabel.setText("");
    }

    private void showError(String msg) {
        errorLabel.setStyle("-fx-text-fill: red;");
        errorLabel.setText(msg);
    }
}