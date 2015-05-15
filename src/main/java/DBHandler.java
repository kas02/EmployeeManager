import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.*;
import java.util.ArrayList;

public class DBHandler {

    public static final String URL = "jdbc:mysql://localhost:3306/employees";
    public static final String USER = "root";
    public static final String PASSWORD = "root";
    private Connection connection;

    public DBHandler(){
        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            if (connection.isClosed())
                throw new SQLException();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public ArrayList<Employee> getList(){
        final String SELECT_ALL = "SELECT * FROM employees";
        Employee employee;
        ArrayList<Employee> employeesList = new ArrayList<>();

        try{
            Statement statement = this.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                int type = resultSet.getInt("type");
                String firstName = resultSet.getString("first_name");
                String secondName = resultSet.getString("second_name");
                float payment = resultSet.getFloat("payment");
                if (type == 0) {
                    employee = new Employee(id, firstName, secondName, payment);
                }
                else {
                    employee = new EmployeeTax(id, firstName, secondName, payment);
                }
                employeesList.add(employee);
            }
            return employeesList;
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public Employee getById(int id){
        final String GET = "SELECT * FROM employees WHERE id=?";
        Employee employee = null;

        try {
            PreparedStatement statement = this.getConnection().prepareStatement(GET);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int type = resultSet.getInt("type");
                String firstName = resultSet.getString("first_name");
                String secondName = resultSet.getString("second_name");
                float payment = resultSet.getFloat("payment");
                if (type == 0) {
                    employee = new Employee(id, firstName, secondName, payment);
                }
                else {
                    employee = new EmployeeTax(id, firstName, secondName, payment);
                }
            }
            return employee;
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean addNew(int type, String firstName, String secondName, float payment){
        final String INSERT = "INSERT INTO employees (type, first_name, second_name, payment) VALUES (?,?,?,?)";
        try {
            PreparedStatement statement = this.getConnection().prepareStatement(INSERT);
            statement.setInt(1, type);
            statement.setString(2, firstName);
            statement.setString(3, secondName);
            statement.setFloat(4, payment);
            statement.execute();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean delEmployee(int id){
        final String DELETE = "DELETE FROM employees WHERE id=?";
        Connection connection;
        try {
            connection = this.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setInt(1, id);
            statement.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

}
