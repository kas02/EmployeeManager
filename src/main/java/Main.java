import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

//The main class of the Application
public class Main extends Application {

    Button buttonAdd;
    Button buttonDel;
    Button buttonShowCurrent;
    ChoiceBox<String> choiceSort;
    static Stage mainWindow;

    //Current sort type of Employees list
    String currentSort;

    //Collection of Employee objects
    ArrayList<Employee> employeesList = new ArrayList<>();

    //ListView of Employees
    ListView<String> listView = new ListView<>();

    //Entrance to the program
    public static void main(String args[]){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Main window's description text
        Label label = new Label("You can add, delete or view employees data");
        label.setPadding(new Insets(10));

        //Renamed primaryStage for convenience
        mainWindow = primaryStage;
        mainWindow.setTitle("Employees manager");

        //Button which open the window for adding new Employee
        buttonAdd = new Button("Add");
        buttonAdd.setMinWidth(50);

        //Button which delete current selected Employee
        buttonDel = new Button("Delete");
        buttonDel.setMinWidth(50);

        //Button which open the window with information about
        //current selected Employee
        buttonShowCurrent = new Button("Show current");
        buttonShowCurrent.setMinWidth(50);

        //Open window for adding a new Employee
        buttonAdd.setOnAction(e -> {
            if(AddWindow.display())
                updateList();
                showList();
        });

        //Delete current selected Employee from the list
        buttonDel.setOnAction(e -> deleteEmployee());

        //Open window with information of current selected Employee
        buttonShowCurrent.setOnAction(e -> {
            //Trying to open a window with Employee information.
            //If it fail then change the description text
            if (!showEmployeeData())
                label.setText("Select an employee");
            else label.setText("You can add, delete or view employees data");
        });

        //Box with buttons
        HBox buttonsBox = new HBox(10);
        buttonsBox.setPadding(new Insets(10));
        buttonsBox.getChildren().addAll(buttonAdd, buttonDel, buttonShowCurrent);

        //View for selection type to sort
        choiceSort = new ChoiceBox<>();
        choiceSort.getItems().addAll("First name", "Second name", "ID", "Monthly salary");
        currentSort = "First name";
        choiceSort.setValue(currentSort);
        //Selection a type to sort and refreshing a list by invoking showList method
        choiceSort.getSelectionModel().selectedItemProperty().addListener((v, oldValue, newValue) -> {
            currentSort = newValue;
            showList();
        });

        updateList();
        showList();

        Label labelSort = new Label("Sort by:");

        VBox vBox = new VBox(5);
        vBox.setPadding(new Insets(0, 10, 10, 10));
        vBox.getChildren().addAll(labelSort, choiceSort);

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10));
        borderPane.setTop(label);
        borderPane.setBottom(buttonsBox);
        borderPane.setLeft(listView);
        borderPane.setRight(vBox);

        Scene navigationScene = new Scene(borderPane, 400, 400);
        mainWindow.setScene(navigationScene);
        mainWindow.show();

        mainWindow.setOnCloseRequest(e -> {
            e.consume();
            exitProgram();
        });

    }

    private void updateList() {
        DBHandler db = new DBHandler();
        employeesList = db.getList();
        try {
            db.getConnection().close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        if (employeesList.isEmpty()) {
            listView.getItems().add("No current employees");
            listView.getSelectionModel().select(-1);
            buttonShowCurrent.setDisable(true);
            choiceSort.setDisable(true);
        }
        else {
            buttonShowCurrent.setDisable(false);
            choiceSort.setDisable(false);
        }
    }

    public void showList() {
        listView.getItems().remove(0, listView.getItems().size());
        switch (currentSort){
            case "First name":
                Collections.sort(employeesList, Employee.FirstNameComparator);
                break;
            case "Second name":
                Collections.sort(employeesList, Employee.SecondNameComparator);
                break;
            case "ID":
                Collections.sort(employeesList, Employee.IdComparator);
                break;
            case "Monthly salary":
                Collections.sort(employeesList, Employee.PaymentComparator);
                break;
        }
        int i = 1;
        for (Employee anEmployeesList : employeesList) {
            listView.getItems().add(String.format("%s. %s", i++, anEmployeesList.toString()));
        }
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

    }

    private boolean showEmployeeData() {
        try {
            int index = listView.getSelectionModel().getSelectedIndex();
            Employee employee = employeesList.get(index);
            EmployeeDataWindow.display(employee);
            return true;
        } catch (ArrayIndexOutOfBoundsException e){
            return false;
        }
    }

    public static void exitProgram(){
        boolean answer = Alert.exitConfirm();
        if (answer){
            mainWindow.close();
        }
    }

    private void deleteEmployee() {
        try {
            int index = listView.getSelectionModel().getSelectedIndex();
            Employee employee = employeesList.get(index);
            int id = employee.getId();
            employeesList.remove(index);
            DBHandler db = new DBHandler();
            db.delEmployee(id);
            db.getConnection().close();
            updateList();
            showList();
        } catch (Exception e){
           showList();
        }
    }

}
