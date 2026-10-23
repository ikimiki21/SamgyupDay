import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Label title = new Label("SamgyupDay POS");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        VBox root = new VBox(12, title,
                menuButton("Login", "LoginView.fxml", "SamgyupDay POS Login"),
                menuButton("Stock-In", "StockInView.fxml", "Inventory Stock-In"),
                menuButton("Table Check-In", "CheckInView.fxml", "Customer Table Check-In"),
                menuButton("Waste Log", "WasteLogView.fxml", "Meat Waste Log"));
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        stage.setTitle("SamgyupDay POS - Menu");
        stage.setScene(new Scene(root, 320, 300));
        stage.show();
    }

    private Button menuButton(String text, String fxml, String windowTitle) {
        Button button = new Button(text);
        button.setPrefWidth(220);
        button.setOnAction(e -> openView(fxml, windowTitle));
        return button;
    }

    private void openView(String fxml, String windowTitle) {
        try {
            Parent view = FXMLLoader.load(getClass().getResource("/" + fxml));
            Stage stage = new Stage();
            stage.setTitle(windowTitle);
            stage.setScene(new Scene(view));
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Could not open " + fxml + ": " + ex.getMessage()).showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
