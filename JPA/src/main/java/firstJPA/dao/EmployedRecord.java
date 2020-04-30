package firstJPA.dao;

public class EmployedRecord {
    private String employee;
    private String department;

    public EmployedRecord(String employee, String department) {
        this.employee = employee;
        this.department = department;
    }

    @Override
    public String toString() {
        return "EmployedRecord{" +
                "employee='" + employee + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
