package nl.humaninference.person.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.humaninference.person.entity.Department;
import nl.humaninference.person.repository.IDepartmentRepository;

@Service
@Transactional(readOnly = true)
public class DepartmentService {

	private static final Logger log = LoggerFactory.getLogger(PersonService.class);

	private final IDepartmentRepository departmentRepository;

	public DepartmentService(IDepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
	}

	public Optional<Department> findById(long id) {
		log.debug("Finding department with ID: " + id);
		
    	return this.departmentRepository.findById(id);
    }

}
