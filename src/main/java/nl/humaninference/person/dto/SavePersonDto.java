package nl.humaninference.person.dto;

import java.time.LocalDateTime;

import nl.humaninference.person.entity.PersonStatus;

// I like to work with DTO's to ensure the data being sent must first be translated into the entity.
// If we allow an entity as a requestbody then we loose control over which properties a frontend may 
// send
public class SavePersonDto {

	private String name;

	private long departmentId;

	private double salary;

	private PersonStatus status = PersonStatus.ACTIVE;

	private LocalDateTime startDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(long departmentId) {
		this.departmentId = departmentId;
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

}
