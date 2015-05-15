import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

abstract public class EmployeeDataWindow {

    public static void display(Employee employee){
        Stage window = new Stage();
        window.setTitle("Employee information");
        Label labelId = new Label("ID: " + employee.getId());
        Label labelName1 = new Label("First name: " + employee.getFirstName());
        Label labelName2 = new Label("Second name: " + employee.getSecondName());
        //Label labelPayment = new Label("Payment: "+ payment + " UAH");
        Label labelSalary = new Label("Monthly salary: "+ employee.getSalary() + " UAH");


        VBox infVbox = new VBox(10);
        infVbox.getChildren().addAll(labelId, labelName1, labelName2, labelSalary);
        infVbox.setPadding(new Insets(10));

        Scene infScene = new Scene(infVbox, 300, 300);
        window.setScene(infScene);
        window.setResizable(false);
        window.show();

    }

}
