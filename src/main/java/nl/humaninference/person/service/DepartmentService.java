package nl.humaninference.person.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import nl.humaninference.person.entity.Department;
import nl.humaninference.person.repository.IDepartmentRepository;

@Service
public class DepartmentService {

	private final IDepartmentRepository departmentRepository;

	public DepartmentService(IDepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
	}

	public Optional<Department> findById(long id) {
    	return this.departmentRepository.findById(id);
    }

}
