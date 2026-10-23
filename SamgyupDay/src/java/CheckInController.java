import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CheckInController {
    @FXML private ComboBox<String> tableCombo;
    @FXML private TextField headcountField;
    @FXML private RadioButton tier299;
    @FXML private RadioButton tier399;
    @FXML private RadioButton tier499;
    @FXML private CheckBox childCheckBox;
    @FXML private TextField childHeadcountField;
    @FXML private ToggleButton timerToggle;
    @FXML private TextArea tableNotesArea;
    @FXML private Label errorLabel;

    private ToggleGroup tierGroup;

    @FXML
    public void initialize() {
        for (int i = 1; i <= 17; i++) {
            tableCombo.getItems().add("Table " + i);
        }
        
        tierGroup = new ToggleGroup();
        tier299.setToggleGroup(tierGroup);
        tier399.setToggleGroup(tierGroup);
        tier499.setToggleGroup(tierGroup);
    }

    @FXML
    private void handleChildToggle() {
        childHeadcountField.setDisable(!childCheckBox.isSelected());
        if (!childCheckBox.isSelected()) {
            childHeadcountField.clear();
        }
    }

    @FXML
    private void handleCheckIn() {
        errorLabel.setText("");
        
        // Validation 1: Required Table Selection
        if (tableCombo.getValue() == null) {
            showError("Please select an available table.");
            return;
        }
        
        // Validation 2: Headcount Numeric Limits
        int totalHeadcount;
        try {
            totalHeadcount = Integer.parseInt(headcountField.getText().trim());
            if (totalHeadcount < 1 || totalHeadcount > 8) {
                showError("Table seating capacity is between 1 and 8 customers.");
                return;
            }
        } catch (NumberFormatException e) {
            showError("Total Headcount must be a valid whole number.");
            return;
        }
        
        // Validation 3: Child Headcount Checks
        if (childCheckBox.isSelected()) {
            try {
                int childHeadcount = Integer.parseInt(childHeadcountField.getText().trim());
                if (childHeadcount < 1 || childHeadcount >= totalHeadcount) {
                    showError("Child headcount must be at least 1 and less than total guests.");
                    return;
                }
            } catch (NumberFormatException e) {
                showError("Please enter a valid number for Child Headcount.");
                return;
            }
        }

        // Simulate UI Data Handling
        System.out.println(tableCombo.getValue() + " activated with " + totalHeadcount + " guests.");
        errorLabel.setStyle("-fx-text-fill: green;");
        errorLabel.setText("Check-in confirmed! Timer and table active.");
    }

    private void showError(String msg) {
        errorLabel.setStyle("-fx-text-fill: red;");
        errorLabel.setText(msg);
    }
}