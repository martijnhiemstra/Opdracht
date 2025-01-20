package nl.humaninference.person.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import nl.humaninference.person.entity.Department;
import nl.humaninference.person.repository.IDepartmentRepository;

@Service
public class DepartmentService {

	private static final Logger log = LoggerFactory.getLogger(DepartmentService.class);

	private final IDepartmentRepository departmentRepository;

	public DepartmentService(IDepartmentRepository departmentRepository) {
		this.departmentRepository = departmentRepository;
	}

	public Optional<Department> findById(long id) {
		if (log.isDebugEnabled())
			log.debug("Looking for department with id " + id);
		
    	return this.departmentRepository.findById(id);
    }

}
