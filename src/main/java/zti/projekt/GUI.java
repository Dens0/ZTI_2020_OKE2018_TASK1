package zti.projekt;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class GUI {

    @FXML
    private TextArea inputTextArea, outputTextArea;

    @FXML
    private Button submitButton;

    @FXML
    private ProgressIndicator indicator;

    public void init() {
        indicator.setVisible(false);

        submitButton.setOnAction(event -> {
            indicator.setVisible(true);
            String sourceText = inputTextArea.getText();

            Identification identification = new Identification(sourceText);

            identification.setOnSucceeded(success -> {
                outputTextArea.setText(identification.getValue());
                indicator.setVisible(false);
            });
            identification.setOnFailed(failed -> {
                indicator.setVisible(false);
                Alert alert = new Alert(Alert.AlertType.NONE, "Wystąpił błąd. Spróbuj ponownie.", ButtonType.OK);
                alert.showAndWait();
            });
            identification.start();

        });
    }
}


