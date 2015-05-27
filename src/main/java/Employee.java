import java.util.Comparator;

public class Employee {

    //Employee attributes
    private int id;
    private String firstName;
    private String secondName;
    private float payment;

    //Constructs a new Employee instance with parameters
    public Employee(int id, String firstName, String secondName, float payment) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.payment = payment;
    }
    //Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPayment() {
        return payment;
    }

    public void setPayment(float payment) {
        this.payment = payment;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public double getSalary() {
        return this.getPayment();
    }


    //Interfaces for sorting a collection
    public static Comparator<Employee> IdComparator = (o1, o2) -> {
        int id1 = o1.getId();
        int id2 = o2.getId();
        return id1 - id2;
    };

    public static Comparator<Employee> FirstNameComparator = (o1, o2) -> {
        String firstName1 = o1.getFirstName().toUpperCase();
        String firstName2 = o2.getFirstName().toUpperCase();
        return firstName1.compareTo(firstName2);
    };

    public static Comparator<Employee> SecondNameComparator = (o1, o2) -> {
        String secondName1 = o1.getSecondName().toUpperCase();
        String secondName2 = o2.getSecondName().toUpperCase();
        return secondName1.compareTo(secondName2);
    };

    public static Comparator<Employee> PaymentComparator = (o1, o2) -> {
        int salary1 = (int) (o1.getSalary() * 100);
        int salary2 = (int) (o2.getSalary() * 100);
        return salary1 - salary2;
    };

    //Making a string from object's of attributes values
    public String toString(){

        return String.format(
                "%s %s;  ID = %s;  %.2f UAH",
                this.getFirstName(),
                this.getSecondName(),
                this.getId(),
                this.getSalary());
    }
}

