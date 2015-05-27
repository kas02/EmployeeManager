import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

abstract public class Alert {

    static boolean answer = false;

    public static boolean exitConfirm(){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);

        window.setTitle("Do you want exit?");

        Button buttonYes = new Button("Yes");
        buttonYes.setMinWidth(100);

        Button buttonNo = new Button("No");
        buttonNo.setMinWidth(100);

        buttonYes.setOnAction(e -> {
            answer = true;
            window.close();
        });
        buttonNo.setOnAction(e -> {
            answer = false;
            window.close();
        });

        Label label = new Label();
        label.setText("Sure you want to exit the program?");

        HBox hBoxBottom = new HBox(10);
        hBoxBottom.setPadding(new Insets(10, 25, 20, 25));
        hBoxBottom.getChildren().addAll(buttonYes, buttonNo);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(label);
        borderPane.setBottom(hBoxBottom);

        Scene scene = new Scene(borderPane, 250, 100);
        scene.getStylesheets().add("MyStyle.css");
        window.setScene(scene);
        window.setResizable(false);
        window.showAndWait();
        return answer;

    }

}
