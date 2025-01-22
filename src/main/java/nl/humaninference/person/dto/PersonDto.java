package nl.humaninference.person.dto;

import java.time.LocalDateTime;

import nl.humaninference.person.entity.PersonStatus;

public class PersonDto {

	private long id;
	
	private String name;
	
	private double salary;
	
	private PersonStatus status = PersonStatus.ACTIVE;
	
	private LocalDateTime startDate;
	
    private long departmentId;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public PersonStatus getStatus() {
		return status;
	}

	public void setStatus(PersonStatus status) {
		this.status = status;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(long departmentId) {
		this.departmentId = departmentId;
	}
    
}
