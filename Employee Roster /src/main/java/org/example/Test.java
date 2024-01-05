package org.example;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.out; // Static import for System.out.println

public class Test {
  private static URL url = Test.class.getResource("/demo.json");

  public static void main(String[] args) throws IOException, URISyntaxException {
    String data = new String(Files.readAllBytes(Paths.get("/Users/rguevarr/IdeaProjects/json reader/src/main/resources/demo.json"))); // Read file contents into a string
    JSONArray jsonArray = new JSONArray(data); // Create a JSON array from the string data
    Scanner scanner = new Scanner(System.in); // Initialize a scanner for user input
    boolean continueSearching = true; // A flag to control the loop

    while (continueSearching) { // Start a loop for user interactions
      boolean validChoice = false;
      String searchField = null;

      int choice = 0;
      while (!validChoice) {
        printMenu(); // Display the search menu
        choice = readUserChoice(scanner);

        switch (choice) { // Handle user's choice
          // Cases 1 to 8 correspond to different search fields
          case 1:
          case 2:
          case 3:
          case 4:
          case 5:
          case 6:
          case 7:
            searchField = getFieldByChoice(choice); // Get the search field based on user's choice
            validChoice = true;
            break;
          case 8:
            searchField = "title_or_manager"; // Special case for searching by title or manager
            validChoice = true;
            break;
          case 9:
            addEmployee(jsonArray, scanner); // Call the method to add an employee
            break;
          case 10:
            deleteEmployee(jsonArray, scanner); // Call the method to delete an employee
            break;
          default:
            out.println("Please choose a number from 1 to 10."); // Display an error message for invalid choices
            out.println("------------------------------");
        }
      }

      if (validChoice && choice != 9 && choice != 10) {
        out.print("Enter the value to search for: "); // Prompt the user for the search value
        String searchValue = scanner.nextLine(); // Read the search value from the user

        if ("title_or_manager".equals(searchField)) { // Check if searching by title or manager
          searchEmployeesByTitleOrManager(jsonArray, searchValue); // Perform the corresponding search
        } else {
          searchEmployeeByField(jsonArray, searchField, searchValue); // Perform the search by a specific field
        }
      }

      out.print("Do you want to search again? (yes/no): "); // Ask if the user wants to stop searching
      String searchAgain;

      boolean validInput = false;
      do {
        searchAgain = scanner.nextLine().toLowerCase(); // Read and convert the user's input to lowercase

        if (searchAgain.equals("yes") || searchAgain.equals("no")) { // Validate the input
          validInput = true;
        } else {
          out.print("Invalid input. Please enter 'yes' or 'no': "); // Display an error message for invalid input
        }
      } while (!validInput);

      if (searchAgain.equals("no")) { // Check if the user wants to stop searching
        continueSearching = false; // Set the flag to exit the loop
      }
    }

    scanner.close(); // Close the scanner when done
  }

  public static void printMenu() {
    out.println("Choose a search field:");
    out.println("Press 1 for First name");
    out.println("Press 2 for Last name");
    out.println("Press 3 for Age");
    out.println("Press 4 for Title/Position");
    out.println("Press 5 for Manager");
    out.println("Press 6 for Address");
    out.println("Press 7 for Phone number");
    out.println("Press 8 for All employees under the same Title or Manager");
    out.println("Press 9 to Add an employee");
    out.println("Press 10 to Delete an employee");
    out.println("------------------------------");
    out.print("Enter the corresponding number: ");
  }

  public static int readUserChoice(Scanner scanner) {
    try {
      return Integer.parseInt(scanner.nextLine()); // Read and parse the user's choice as an integer
    } catch (NumberFormatException e) {
      out.println("Invalid input."); // Display an error message for invalid input
      return -1;
    }
  }

  public static String getFieldByChoice(int choice) {
    String[] fieldNames = {"firstname", "lastname", "age", "title", "manager", "address", "phone"};
    return fieldNames[choice - 1]; // Map the user's choice to a field name
  }

  public static void searchEmployeeByField(JSONArray jsonArray, String fieldName, String value) {
    boolean found = false;

    for (int i = 0; i < jsonArray.length(); i++) {
      JSONObject object = jsonArray.getJSONObject(i);
      String fieldValue = object.getString(fieldName);

      if (fieldValue.equalsIgnoreCase(value)) {
        out.println("Employee found:");
        out.println("------------------------------");
        printEmployeeDetails(object);
        found = true;
      }
    }

    if (!found) {
      out.println("No employees found for the specified criteria.");
      out.println("------------------------------");
    }
  }

  public static void searchEmployeesByTitleOrManager(JSONArray jsonArray, String value) {
    List<JSONObject> matchingEmployees = new ArrayList<>();

    for (int i = 0; i < jsonArray.length(); i++) {
      JSONObject object = jsonArray.getJSONObject(i);
      String title = object.getString("title");
      String manager = object.getString("manager");

      if (title.equalsIgnoreCase(value) || manager.equalsIgnoreCase(value)) {
        matchingEmployees.add(object);
      }
    }

    if (!matchingEmployees.isEmpty()) {
      out.println("Employees found:");
      out.println("------------------------------");
      for (JSONObject employee : matchingEmployees) {
        printEmployeeDetails(employee);
      }
    } else {
      out.println("No employees found for the specified criteria.");
      out.println("------------------------------");
    }
  }

  public static void printEmployeeDetails(JSONObject employee) {
    String foundName = employee.getString("firstname");
    String foundLastname = employee.getString("lastname");
    int foundAge = employee.getInt("age");
    String foundTitle = employee.getString("title");
    String foundManager = employee.getString("manager");
    String foundAddress = employee.getString("address");
    String foundPhone = employee.getString("phone");

    out.println("First name: " + foundName);
    out.println("Last name: " + foundLastname);
    out.println("Age: " + foundAge);
    out.println("Title/Position: " + foundTitle);
    out.println("Manager: " + foundManager);
    out.println("Address: " + foundAddress);
    out.println("Phone: " + foundPhone);
    out.println("------------------------------");
  }
  // New codes below to add & delete employees
  public static void addEmployee(JSONArray jsonArray, Scanner scanner) {
    // Prompt the user for employee details
    out.print("Enter First name: ");
    String firstName = scanner.nextLine();
    out.print("Enter Last name: ");
    String lastName = scanner.nextLine();
    out.print("Enter Age: ");
    int age = Integer.parseInt(scanner.nextLine());
    out.print("Enter Title/Position: ");
    String title = scanner.nextLine();
    out.print("Enter Manager: ");
    String manager = scanner.nextLine();
    out.print("Enter Address: ");
    String address = scanner.nextLine();
    out.print("Enter Phone number: ");
    String phone = scanner.nextLine();

    // Create a new employee JSON object
    JSONObject newEmployee = new JSONObject();
    newEmployee.put("firstname", firstName);
    newEmployee.put("lastname", lastName);
    newEmployee.put("age", age);
    newEmployee.put("title", title);
    newEmployee.put("manager", manager);
    newEmployee.put("address", address);
    newEmployee.put("phone", phone);

    // Add the new employee to the JSONArray in memory
    jsonArray.put(newEmployee);

    // Save the updated data to the file
    saveDataToFile(jsonArray, url);

    out.println("Employee added successfully!");
  }

  public static void deleteEmployee(JSONArray jsonArray, Scanner scanner) {
    out.print("Enter the first and last name: ");
    String firstAndLastName = scanner.nextLine();

    // Split the input into first name and last name
    String[] nameParts = firstAndLastName.split(" ");

    // Check if the input contains both first and last names
    if (nameParts.length != 2) {
      out.println("Please enter both the first name and last name separated by a space.");
      return;
    }

    String firstName = nameParts[0];
    String lastName = nameParts[1];

    boolean found = false;

    for (int i = 0; i < jsonArray.length(); i++) {
      JSONObject object = jsonArray.getJSONObject(i);
      try {
        // Extract the first name and last name of each employee from JSON
        String employeeFirstName = object.getString("firstname");
        String employeeLastName = object.getString("lastname");

        // Compare the entered names with employee names
        if (employeeFirstName.equalsIgnoreCase(firstName) && employeeLastName.equalsIgnoreCase(lastName)) {
          // Remove the matched employee from the JSON array
          jsonArray.remove(i);
          found = true;
          break; // Assuming there's only one matching employee to delete
        }
      } catch (JSONException e) {
        // Handle any potential JSON exceptions
        out.println("Error while processing data: " + e.getMessage());
      }
    }

    // Save the updated data to the file
    saveDataToFile(jsonArray, url);

    if (found) {
      out.println("Employee deleted successfully!");
    } else {
      out.println("No employees found for the specified criteria.");
      out.println("------------------------------");
    }
  }

  public static void saveDataToFile(JSONArray jsonArray, URL url) {
    try {
      // Write the updated JSONArray to the demo.json file
      Files.write(Paths.get(url.toURI()), jsonArray.toString(2).getBytes());
    } catch (IOException | URISyntaxException e) {
      e.printStackTrace();
    }
  }
}