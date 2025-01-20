package nl.humaninference.person.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import nl.humaninference.person.entity.Department;
import nl.humaninference.person.entity.Person;
import nl.humaninference.person.entity.PersonStatus;
import nl.humaninference.person.repository.IPersonRepository;
import nl.humaninference.person.repository.PersonSpecifications;

@Service
public class PersonService {
	
	private static final Logger log = LoggerFactory.getLogger(PersonService.class);

    private final IPersonRepository personRepository;

    public PersonService(IPersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Page<Person> searchPersons(String name, Department department, Double minSalary, Double maxSalary, PersonStatus status, LocalDateTime startDate, Pageable pageable) {
        return personRepository.findAll(PersonSpecifications.search(name, department, minSalary, maxSalary, status, startDate), pageable);
    }
    
    public Optional<Person> findById(long id) {
    	return this.personRepository.findById(id);
    }
    
    // Finds all the persons. The pageable allows us to search by page and rows per page
    public Page<Person> findAll(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    // Create a new person in the database
    public Person createPerson(Person person) {
        return personRepository.save(person);
    }
    
    // Update an existing person in the database
    public Person updatePerson(Long id, Person updatedPerson) {
        return personRepository.findById(id).map(existingPerson -> {
            existingPerson.setName(updatedPerson.getName());
            existingPerson.setSalary(updatedPerson.getSalary());
            existingPerson.setStatus(updatedPerson.getStatus());
            existingPerson.setStartDate(updatedPerson.getStartDate());
            existingPerson.setDepartment(updatedPerson.getDepartment());
            return personRepository.save(existingPerson);
        }).orElseThrow(() -> new RuntimeException("Person not found with ID: " + id));
    }
    
    // Delete an existing person from the database
    public void deletePerson(Long id) {
        if (!personRepository.existsById(id)) {
            throw new RuntimeException("Person not found with ID: " + id);
        }
        personRepository.deleteById(id);
    }

}