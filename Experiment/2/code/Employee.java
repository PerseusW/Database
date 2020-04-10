import java.sql.*;

public class Employee
{
    private String name;
    private int ID;
    private int age;
    private int salary;
    private int departmentID;

    public Employee(ResultSet resultSet) throws SQLException {
        name = resultSet.getString("name");
        ID = resultSet.getInt("ID");
        age = resultSet.getInt("age");
        salary = resultSet.getInt("salary");
        departmentID = resultSet.getInt("departmentID");
    }

    public static String getSchema() {
        return "name\t\tID\t\tage\tsalary\tdepartmentID";
    }

    public String toString() {
        return name + "\t" + String.valueOf(ID) + "\t" + String.valueOf(age) + "\t" + String.valueOf(salary) + "\t" + String.valueOf(departmentID);
    }
}