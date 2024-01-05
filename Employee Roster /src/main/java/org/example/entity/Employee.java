package org.example.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "employees")
public class Employee implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name="employee_id")
  private Long id;

  @Column(name="first_name")
  private String firstName;

  @Column(name="last_name")
  private String lastName;

  @Column
  private Integer age;

  @Column(name="title")
  private String title;

  @Column(name="manager")
  private String manager;

  @Column(name="address")
  private String address;

  @Column(name="phone_number")
  private String phoneNumber;

  // Default constructor required by JPA
  public Employee() {
  }

  // Constructor with parameters
  public Employee(Long id, String firstName, String lastName, Integer age, String title, String manager, String address, String phoneNumber) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
    this.title = title;
    this.manager = manager;
    this.address = address;
    this.phoneNumber = phoneNumber;
  }

  // Getters and setters for all fields
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getManager() {
    return manager;
  }

  public void setManager(String manager) {
    this.manager = manager;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

}
