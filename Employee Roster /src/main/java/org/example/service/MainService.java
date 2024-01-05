package org.example.service;

import java.util.List;
import org.example.entity.Employee;
import org.example.repository.EmployeeRepository;
import java.util.Optional;
import java.util.Scanner;
import static java.lang.System.out;

public class MainService {

  private final EmployeeRepository employeeRepository;

  // Constructor
  public MainService(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  // Start method
  public void start(Scanner scanner) {
    boolean continueRunning = true;
    while (continueRunning) {
      printMenu();
      int choice = readUserChoice(scanner);
      handleUserChoice(choice, scanner);
      continueRunning = askToContinue(scanner);
    }
  }

  // Method to handle user's choice
  private void handleUserChoice(int choice, Scanner scanner) {
    switch (choice) {
      case 1:
        searchEmployeeByField("firstName", scanner);
        break;
      case 2:
        searchEmployeeByField("lastName", scanner);
        break;
      case 3:
        searchEmployeeByField("age", scanner);
        promptForValidAge(scanner);
        break;
      case 4:
        searchEmployeeByField("title", scanner);
        break;
      case 5:
        searchEmployeeByField("manager", scanner);
        break;
      case 6:
        searchEmployeeByField("address", scanner);
        break;
      case 7:
        searchEmployeeByField("phoneNumber", scanner);
        break;
      case 8:
        searchEmployeesByTitleOrManager(scanner);
        break;
      case 9:
        promptAndAddEmployee(scanner);
        break;
      case 10:
        deleteEmployee(scanner);
        break;
      default:
        out.println("Invalid choice. Please try again.");
        break;
    }
  }

  private  boolean askToContinue(Scanner scanner) {
    out.print("Do you want to search again? (yes/no): "); // Ask if the user wants to stop searching
    while (true) {
      String searchAgain = scanner.nextLine().trim().toLowerCase(); // Read and trim the user's input

      if ("yes".equals(searchAgain)) {
        return true;
      } else if ("no".equals(searchAgain)) {
        return false;
      } else {
        out.print("Invalid input. Please enter 'yes' or 'no': "); // Invalid input
      }
    }
  }

  private void printMenu() {
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

  public  int readUserChoice(Scanner scanner) {
    try {
      return Integer.parseInt(scanner.nextLine()); // Read and parse the user's choice as an integer
    } catch (NumberFormatException e) {
      out.println("Invalid input."); // Display an error message for invalid input
      return -1;
    }
  }

  // Method to prompt user and add employee
  private void promptAndAddEmployee(Scanner scanner) {
    out.print("Enter First name: ");
    String firstName = scanner.nextLine();
    out.print("Enter Last name: ");
    String lastName = scanner.nextLine();

    int age = promptForValidAge(scanner);

    out.print("Enter Title/Position: ");
    String title = scanner.nextLine();
    out.print("Enter Manager: ");
    String manager = scanner.nextLine();
    out.print("Enter Address: ");
    String address = scanner.nextLine();
    out.print("Enter Phone number: ");
    String phone = scanner.nextLine();

    addEmployee(firstName, lastName, age, title, manager, address, phone);
  }

  private int promptForValidAge(Scanner scanner) {
    Integer age = null;
    do {
      out.print("Enter the age to search for: ");
      String input = scanner.nextLine();
      try {
        age = Integer.parseInt(input);
      } catch (NumberFormatException e) {
        out.println("Invalid input. Please enter a valid integer for age.");
      }
    } while (age == null);
    return age;
  }

  // AddEmployee method without scanner parameter
  private void addEmployee(String firstName, String lastName, int age, String title, String manager, String address, String phone) {
    Employee employee = new Employee();
    employee.setFirstName(firstName);
    employee.setLastName(lastName);
    employee.setAge(age);
    employee.setTitle(title);
    employee.setManager(manager);
    employee.setAddress(address);
    employee.setPhoneNumber(phone);

    try {
      Optional<Employee> savedEmployee = employeeRepository.save(employee);
      if (savedEmployee.isPresent()) {
        out.println("Employee added successfully!");
      } else {
        out.println("Failed to add the employee.");
      }
    } catch (Exception e) {
      out.println("Error occurred while adding the employee: " + e.getMessage());
    }
  }

  public void deleteEmployee(Scanner scanner) {
    out.print("Enter the ID of the employee to delete: ");
    Long id;
    try {
      id = Long.parseLong(scanner.nextLine());
    } catch (NumberFormatException e) {
      out.println("Invalid input. Please enter a valid employee ID.");
      return; // Exit the method if the input is invalid
    }

    Optional<Employee> employee = employeeRepository.findById(id);
    if (!employee.isPresent()) {
      out.println("No employee found with ID: " + id);
      return;
    }

    // Confirm deletion
    out.println("Are you sure you want to delete the following employee? (yes/no): ");
    out.println(employee.get());
    String input = scanner.nextLine().trim().toLowerCase();
    if (!input.equals("yes")) {
      out.println("Employee deletion cancelled.");
      return;
    }

    // Perform deletion
    try {
      employeeRepository.delete(employee.get());
      out.println("Employee deleted successfully.");
    } catch (Exception e) {
      out.println("Error occurred while deleting the employee: " + e.getMessage());
    }
  }

  private void searchEmployeeByField(String field, Scanner scanner) {
    List<Employee> employees;
    String value = null;  // Declare 'value' outside the if-else block

    if ("age".equals(field)) {
      int age = promptForValidAge(scanner);  // Get valid age input
      value = String.valueOf(age);  // Convert age to String
      employees = employeeRepository.findByField(field, value);
    } else {
      out.printf("Enter the %s to search for: ", field);
      value = scanner.nextLine().trim();
      employees = employeeRepository.findByField(field, value);
    }

    if (employees.isEmpty()) {
      out.println("No employees found with " + field + " like: " + value);
    } else {
      employees.forEach(this::printEmployeeDetails);
    }
  }


  private void searchEmployeesByTitleOrManager(Scanner scanner) {
    out.print("Enter the title or manager's name to search for: ");
    String value = scanner.nextLine().trim();
    List<Employee> employees = employeeRepository.findByTitleOrManager(value);
    if (employees.isEmpty()) {
      out.println("No employees found for the title or manager: " + value);
    } else {
      employees.forEach(this::printEmployeeDetails);
    }
  }

  private void printEmployeeDetails(Employee employee) {
    out.println("ID: " + employee.getId());
    out.println("First name: " + employee.getFirstName());
    out.println("Last name: " + employee.getLastName());
    out.println("Age: " + employee.getAge());
    out.println("Title: " + employee.getTitle());
    out.println("Manager: " + employee.getManager());
    out.println("Address: " + employee.getAddress());
    out.println("Phone: " + employee.getPhoneNumber());
    out.println("------------------------------");
  }
}
