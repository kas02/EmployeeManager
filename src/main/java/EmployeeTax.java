public class EmployeeTax extends Employee {

    public EmployeeTax(int id, String firstName, String secondName, float payment) {
        super(id, firstName, secondName, payment);
    }

    @Override
    public double getSalary() {
        return this.getPayment()*20.8*8;
    }

}