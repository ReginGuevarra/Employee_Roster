package org.example;

import java.sql.*;

public class DatabaseConnection {

  public static void main(String[] args) {
    String jdbcUrl = "jdbc:mysql://localhost:3306/EmployeeDB";
    String username = "regin";  // Replace with your MySQL username
    String password = "Jollibee";  // Replace with your MySQL password

    try {
      Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
      System.out.println("Connected to the database!");

      // Create a statement
      Statement statement = connection.createStatement();

      // Execute a query
      String sql = "SELECT * FROM Employee";
      ResultSet resultSet = statement.executeQuery(sql);

      // Process the result set
      while (resultSet.next()) {
        // Assuming there's an ID, FirstName, and LastName column in your Employee table
        int id = resultSet.getInt("ID");
        String firstName = resultSet.getString("FirstName");
        String lastName = resultSet.getString("LastName");
        // Print or process the data
        System.out.println("ID: " + id + ", First Name: " + firstName + ", Last Name: " + lastName);
      }

      // Clean up environment
      resultSet.close();
      statement.close();
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("Connection failed!");
    }
  }
}
