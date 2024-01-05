package org.example.repository;

import org.example.entity.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class EmployeeRepositoryImpl implements EmployeeRepository {

  private final EntityManager entityManager;

  public EmployeeRepositoryImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  public Optional<Employee> save(Employee employee) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      if (employee.getId() == null) {
        entityManager.persist(employee);
      } else {
        employee = entityManager.merge(employee);
      }
      transaction.commit();
      return Optional.of(employee);
    } catch (Exception e) {
      if (transaction.isActive()) {
        transaction.rollback();
      }
      e.printStackTrace();
      return Optional.empty();
    }
  }

  @Override
  public Optional<Employee> findById(Long id) {
    Employee employee = entityManager.find(Employee.class, id);
    return Optional.ofNullable(employee);
  }

  @Override
  public Optional<Employee> findByFirstNameAndLastName(String firstname, String lastname) {
    try {
      Employee employee = entityManager.createQuery(
              "SELECT e FROM Employee e WHERE e.firstName = :firstName AND e.lastName = :lastName", Employee.class)
          .setParameter("firstName", firstname)
          .setParameter("lastName", lastname)
          .getSingleResult();
      return Optional.of(employee);
    } catch (Exception e) {
      e.printStackTrace();
      return Optional.empty();
    }
  }

  @Override
  public void delete(Employee employee) {
    EntityTransaction transaction = entityManager.getTransaction();
    try {
      transaction.begin();
      Employee managedEmployee = entityManager.contains(employee) ? employee : entityManager.merge(employee);
      entityManager.remove(managedEmployee);
      transaction.commit();
    } catch (Exception e) {
      if (transaction.isActive()) {
        transaction.rollback();
      }
      e.printStackTrace();
    }
  }
  @Override
  public List<Employee> findByField(String fieldName, String value) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
    Root<Employee> employee = cq.from(Employee.class);

    Predicate condition;
    switch (fieldName) {
      case "firstName":
        condition = cb.like(employee.get("firstName"), "%" + value + "%");
        break;
      case "lastName":
        condition = cb.like(employee.get("lastName"), "%" + value + "%");
        break;
      case "title":
        condition = cb.like(employee.get("title"), "%" + value + "%");
        break;
      case "manager":
        condition = cb.like(employee.get("manager"), "%" + value + "%");
        break;
      case "address":
        condition = cb.like(employee.get("address"), "%" + value + "%");
        break;
      case "phone":
        condition = cb.like(employee.get("phone"), "%" + value + "%");
        break;
      case "age":
        Integer ageValue;
        try {
          ageValue = Integer.parseInt(value);
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException("Invalid value for age. It must be an integer.");
        }
        condition = cb.equal(employee.get("age"), ageValue);
        break;
      default:
        throw new IllegalArgumentException("Unknown field: " + fieldName);
    }

    cq.where(condition);
    return entityManager.createQuery(cq).getResultList();
  }


  @Override
  public List<Employee> findByTitleOrManager(String value) {
    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
    CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
    Root<Employee> employee = cq.from(Employee.class);

    Predicate titlePredicate = cb.like(employee.get("title"), "%" + value + "%");
    Predicate managerPredicate = cb.like(employee.get("manager"), "%" + value + "%");
    cq.where(cb.or(titlePredicate, managerPredicate));

    return entityManager.createQuery(cq).getResultList();
  }
}
