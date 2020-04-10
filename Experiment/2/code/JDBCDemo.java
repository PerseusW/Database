import java.sql.*;
import java.util.Properties;

public class JDBCDemo
{
    private static final String CONNECTION_URL = "jdbc:mysql://127.0.0.1/Experiment";
    private static final String USERNAME = "JDBC";
    private static final String PASSWORD = "";
    private static int salaryThreshold = 0;
    private static Connection connection;
    private static String selectAllQuery = "SELECT * FROM Employee";
    private static String selectHighSalaryQuery = "SELECT * FROM Employee WHERE salary > ?";
    private static String insertStatement = "INSERT INTO Employee VALUE ('EmployeeJ',171860606,19,20000,1000)";
    private static String deleteStatement = "DELETE FROM Employee WHERE ID = 171860606";
    private static String updateStatment = "UPDATE Employee SET salary = salary + 1000 WHERE ID = 171860606";

    private static void establishDirectConnection() throws SQLException {
        Properties properties = new Properties();
        properties.put("user",USERNAME);
        properties.put("password",PASSWORD);
        properties.put("useSSL","false");
        connection = DriverManager.getConnection(CONNECTION_URL,properties);
        System.out.println("***Direct Connection established");
    }

    private static void selectAllEmployees() throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectAllQuery);
        System.out.println("mysql> " + selectAllQuery);
        System.out.println(Employee.getSchema());
        while (resultSet.next()) {
            Employee employee = new Employee(resultSet);
            System.out.println(employee);
        }
        statement.close();
    }

    private static void selectHighSalaryEmployees() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(selectHighSalaryQuery);
        preparedStatement.setInt(1,salaryThreshold);
        ResultSet resultSet = preparedStatement.executeQuery();
        System.out.println("mysql> " + preparedStatement);
        System.out.println(Employee.getSchema());
        while (resultSet.next()) {
            Employee employee = new Employee(resultSet);
            System.out.println(employee);
        }
        preparedStatement.close();
    }

    private static void insertJDBCEmployee() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(insertStatement);
        System.out.println("mysql> " + insertStatement);
        selectAllEmployees();
    }

    private static void deleteJDBCEmployee() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(deleteStatement);
        System.out.println("mysql> " + deleteStatement);
        selectAllEmployees();
    }

    private static void updateJDBCEmployee() throws SQLException {
        Statement statement = connection.createStatement();
        System.out.println("mysql> " + updateStatment);
        statement.executeUpdate(updateStatment);
        selectAllEmployees();
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        salaryThreshold = Integer.parseInt(args[0]);
        establishDirectConnection();
        selectAllEmployees();
        selectHighSalaryEmployees();
        deleteJDBCEmployee();
        insertJDBCEmployee();
        updateJDBCEmployee();
        connection.close();
        System.out.println("***Direct Connection closed");
    }
}