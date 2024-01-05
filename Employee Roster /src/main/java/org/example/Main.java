package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.Scanner;
import org.example.service.MainService;
import org.example.repository.EmployeeRepository;
import org.example.repository.EmployeeRepositoryImpl;

public class Main {

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("EmployeeDB");
    EntityManager entityManager = emf.createEntityManager();

    EmployeeRepository employeeRepository = new EmployeeRepositoryImpl(entityManager);
    MainService mainService = new MainService(employeeRepository);

    try {
      mainService.start(scanner);
    } finally {
      entityManager.close();
      emf.close();
      scanner.close();
    }
  }
}
