import java.sql.*;
import javax.sql.DataSource;
import java.util.Properties;

import org.apache.commons.dbcp2.BasicDataSource;

public class DBCPDemo {
    private static final String USERNAME = "JDBC";
    private static final String PASSWORD = "";
    private static final String CONNECTION_URL = "jdbc:mysql://127.0.0.1/Experiment";
    private static BasicDataSource basicDataSource = new BasicDataSource();
    private static Connection CPConnection;
    private static Connection DConnection;
    private static Properties properties = new Properties();
    private static final String selectAllQuery = "SELECT * FROM Employee";
    private static final int iterationThreshold = 2000;

    private static void setupDataSource() {
        basicDataSource.setUsername(USERNAME);
        basicDataSource.setPassword(PASSWORD);
        basicDataSource.setConnectionProperties("useSSL=false");
        basicDataSource.setUrl(CONNECTION_URL);
        System.out.println("***Connection Pool Established");
    }

    private static void setupConnection() throws SQLException {
        CPConnection = basicDataSource.getConnection();
        properties.put("user",USERNAME);
        properties.put("password",PASSWORD);
        properties.put("useSSL","false");
        DConnection = DriverManager.getConnection(CONNECTION_URL,properties);
        System.out.println("***Connections Established");
    }

    private static void selectAll() throws SQLException {
        Statement statement = CPConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectAllQuery);
        System.out.println("mysql> " + selectAllQuery);
        System.out.println(Employee.getSchema());
        while(resultSet.next()) {
            Employee employee = new Employee(resultSet);
            System.out.println(employee);
        }
        resultSet.close();
        statement.close();
    }

    private static void testQueryPerformance() throws SQLException {
        System.out.println("***Starting Query Performance Test");
        Statement statement;
        long start = 0, end = 0;

        statement = DConnection.createStatement();
        for (int i = 0; i < iterationThreshold; i++) {
            statement.executeQuery(selectAllQuery);
        }

        start = System.currentTimeMillis();
        for (int i = 0; i < iterationThreshold; i++) {
            statement.executeQuery(selectAllQuery);
        }
        end = System.currentTimeMillis();
        System.out.println("\tDirect Connection time elapse: " + (end - start) + " ms");

        statement = CPConnection.createStatement();
        start = System.currentTimeMillis();
        for (int i = 0; i < iterationThreshold; i++) {
            statement.executeQuery(selectAllQuery);
        }
        end = System.currentTimeMillis();
        System.out.println("\tConnection Pool Connection time elapse: " + (end - start) + " ms");
        
        System.out.println("***Query Performance Test End");
    }

    private static void testConnectionPerformance() throws SQLException {
        System.out.println("***Starting Connection Performance Test");
        long start = 0, end = 0;

        start = System.currentTimeMillis();
        for (int i = 0; i < iterationThreshold; i++) {
            DConnection.close();
            DConnection = DriverManager.getConnection(CONNECTION_URL,properties);
        }
        end = System.currentTimeMillis();
        System.out.println("\tDirect Connection time elapse: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        for (int i = 0; i < iterationThreshold; i++) {
            CPConnection.close();
            CPConnection = basicDataSource.getConnection();
        }
        end = System.currentTimeMillis();
        System.out.println("\tConnection Pool Connection time elapse: " + (end - start) + " ms");

        System.out.println("***Connection Performance Test End");
    }

    public static void main(String[] args) throws SQLException {
        setupDataSource();
        setupConnection();
        selectAll();
        testQueryPerformance();
        testConnectionPerformance();
        CPConnection.close();
        DConnection.close();
        System.out.println("***Connections Closed");
        basicDataSource.close();
        System.out.println("***Connection Pool Closed");
    }
}