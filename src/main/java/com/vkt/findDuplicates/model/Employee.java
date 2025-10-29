package com.vkt.findDuplicates.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "excel_employee")
public class Employee {
	
	
	@Override
	public String toString() {
		return "Employee [dbId=" + dbId + ", id=" + id + ", age=" + age + ", name=" + name + ", email=" + email
				+ ", phone=" + phone + "]";
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "db_id")
	private long dbId;
	
	@Column(name = "id",nullable = false)
	private int id;
	
	@Column(name = "age",nullable = false)
    private int age;
	
	@Column(name = "name",nullable = false)
    private String name;
	
	@Column(name = "email",nullable = false)
    private String email;
	
	@Column(name = "phone",nullable = false)
    private long phone;
    
    
	public long getDbId() {
		return dbId;
	}
	public void setDbId(long dbId) {
		this.dbId = dbId;
	}
	public Employee(int id, int age, String name, String email, long phone_number) {
		this.id = id;
        this.age = age;
        this.name = name;
        this.email = email;
        this.phone = phone_number;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public long getPhone() {
		return phone;
	}
	public void setPhone(long phone) {
		this.phone = phone;
	}
}
