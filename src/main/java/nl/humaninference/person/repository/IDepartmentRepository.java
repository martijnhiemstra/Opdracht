package nl.humaninference.person.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import nl.humaninference.person.entity.Department;

public interface IDepartmentRepository extends JpaRepository<Department, Long>{

}
