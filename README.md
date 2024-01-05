# Employee_Roster

### Summary
This console application, developed in Java using IntelliJ and MySQL Workbench, manages Employee details like name, title, manager, etc. It includes features such as adding or deleting employees, and searching by name/last name, and finding employees by title or manager.

### Reference
1. **Java Object-Oriented Programming:**
   - [Writing Reusable Algorithms with Runtime Polymorphism](https://www.linkedin.com/learning/java-object-oriented-programming-2/writing-reusable-algorithms-with-runtime-polymorphism?autoSkip=true&resume=false&u=0)
2. **Java 8 Essential Training:**
   - [Java 8 Essential Training Course](https://www.linkedin.com/learning/java-8-essential-training/welcome?u=104)
3. **MySQL Essential Training:**
   - [Harness the Power of MySQL](https://www.linkedin.com/learning/mysql-essential-training-2/harness-the-power-of-mysql?u=104)
4. **Java Persistence with JPA:**
   - [Map Java Objects to Databases](https://www.linkedin.com/learning/java-persistence-with-jpa/map-java-objects-to-databases?u=104)

### Details
- **Test Class Implementation:**
  - Utilizes a JSON file to manage and process test data.
- **MainBackupOld Class:**
  - Also employs a JSON file for data handling, serving as an alternative or backup implementation.
- **DatabaseConnection Class:**
  - Specifically designed for testing the connection with MySQL Workbench. This class ensures seamless integration and interaction with the MySQL database.

### Checklist
1. **Read and Display Local File:**
   - Create an application that reads from a local file and displays the contents on the console.
2. **Search Employee by Name:**
   - Implement functionality to search for an employee by name in the file and display the result on the console.
3. **Operation Selection:**
   - Develop a user interface where the application prompts:
     - "What operation would you like to perform? You have the following options: Search, Add Employee, Delete Employee. Please input your selection."
4. **Search Operation:**
   - Upon selecting the search operation, the application should further prompt:
     - "You have selected the search operation. What field do you want to search by? Options: First Name, Last Name, Title, Manager."
5. **Search by Title or Manager:**
   - Include a feature to search all employees by either their title or manager, displaying the results on the console.
6. **Add/Delete Employee:**
   - Enable the addition and deletion of employee details in the file through the application interface.

### Testing Done
1. **Database Connectivity:**
   - Successfully established a connection between Java and database management systems, specifically MySQL Workbench and pgAdmin 4.
2. **JSON File Handling:**
   - Utilized JSON format for storing local data and for reading from files, ensuring data integrity and accessibility.
3. **EmployeeDB Testing:**
   - Conducted thorough testing on the EmployeeDB in SQL, verifying database structure, integrity, and response.
4. **Functionalities Verification:**
   - Rigorously tested all key functionalities of the application, including:
     - Adding new employees to the database.
     - Deleting existing employees from the database.
     - Searching for employees based on various criteria.
