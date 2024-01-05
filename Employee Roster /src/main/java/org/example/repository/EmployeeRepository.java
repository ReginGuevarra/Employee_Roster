package org.example.repository;

import org.example.entity.Employee;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {
  Optional<Employee> save(Employee employee);
  Optional<Employee> findById(Long id);
  Optional<Employee> findByFirstNameAndLastName(String firstname, String lastname);
  List<Employee> findByField(String fieldName, String value);
  List<Employee> findByTitleOrManager(String value);
  void delete(Employee employee);
}
