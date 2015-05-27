import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

abstract public class AddWindow {

    static boolean result = false;

    public static boolean display(){

        Stage window = new Stage();
        BorderPane borderPane = new BorderPane();
        GridPane gridPane = new GridPane();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add a new employee");

        Button buttonAdd = new Button("Add");
        buttonAdd.setMinWidth(70);

        Button buttonCancel = new Button("Cancel");
        buttonCancel.setMinWidth(70);

        Label labelName = new Label("First name:");
        Label labelName2 = new Label("Second name:");
        Label labelSelect = new Label("Select type of payment");
        Label labelPayment = new Label("Payment:");

        TextField textName = new TextField();
        TextField textName2 = new TextField();
        TextField textPayment = new TextField();


        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);

        HBox hBox = new HBox(5);
        hBox.getChildren().addAll(buttonAdd, buttonCancel);

        ChoiceBox<String> choicePayment = new ChoiceBox<>();
        choicePayment.getItems().addAll("Fixed payment", "Hourly wage");
        choicePayment.setValue("Fixed payment");

        GridPane.setConstraints(labelName, 0, 0);
        GridPane.setConstraints(labelName2, 0, 1);
        GridPane.setConstraints(labelSelect, 0, 2);
        GridPane.setConstraints(labelPayment, 0, 3);
        GridPane.setConstraints(textName, 1, 0);
        GridPane.setConstraints(textName2, 1, 1);
        GridPane.setConstraints(choicePayment, 1, 2);
        GridPane.setConstraints(textPayment, 1, 3);
        GridPane.setConstraints(hBox, 1, 4);

        gridPane.getChildren().addAll(
                labelName, labelName2, labelSelect,
                labelPayment, textName, textName2,
                choicePayment, textPayment, hBox);

        buttonAdd.setOnAction(e -> {
            boolean added = addNewEmployee(textName, textName2, choicePayment, textPayment);

            if (added) {
                result = true;
                window.close();
            }
            else {
                Label labelError = new Label("Fill in all the fields. The \"Payment\" field must be a number.");
                labelError.setPadding(new Insets(5));
                borderPane.setBottom(labelError);
            }
        });

        buttonCancel.setOnAction(e -> window.close());

        borderPane.setCenter(gridPane);
        Scene scene = new Scene(borderPane, 310, 300);
        scene.getStylesheets().add("MyStyle.css");
        window.setScene(scene);
        window.setResizable(false);
        window.showAndWait();
        return result;

    }

    private static boolean isFloat(TextField input){
        try {
            Float.parseFloat(input.getText());
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    private static boolean addNewEmployee(TextField name, TextField name2, ChoiceBox<String> employeeType, TextField payment){
        int type = employeeType.getValue().equals("Hourly wage") ? 1 : 0;
        if (name.getText().equals("") || name2.getText().equals("") || payment.getText().equals("")|| !isFloat(payment)){
            return false;
        }
        else {
            DBHandler db = new DBHandler();
            return db.addNew(type, name.getText(), name2.getText(), Float.parseFloat(payment.getText()));
        }
    }
}
