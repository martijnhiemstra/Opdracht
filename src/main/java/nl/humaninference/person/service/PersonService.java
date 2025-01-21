package nl.humaninference.person.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nl.humaninference.person.entity.Department;
import nl.humaninference.person.entity.Person;
import nl.humaninference.person.entity.PersonStatus;
import nl.humaninference.person.exception.ValidationException;
import nl.humaninference.person.repository.IPersonRepository;
import nl.humaninference.person.repository.PersonSpecifications;

@Service
@Transactional(readOnly = true)
public class PersonService {

	private static final Logger log = LoggerFactory.getLogger(PersonService.class);

    private final IPersonRepository personRepository;

    public PersonService(IPersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Page<Person> searchPersons(String name, Department department, Double minSalary, Double maxSalary, PersonStatus status, LocalDateTime startDate, Pageable pageable) {
    	log.debug("Searching persons. Name[{}] Department [{}] MinSlaray [{}] MaxSalary [{}] Status [{}] StartDate [{}]", name, department, minSalary, maxSalary, status, startDate);
    	
        return personRepository.findAll(PersonSpecifications.search(name, department, minSalary, maxSalary, status, startDate), pageable);
    }

    public Optional<Person> findById(long id) {
    	log.debug("Find person by id [{}]", id);
    	
    	return this.personRepository.findById(id);
    }

    // Finds all the persons. The pageable allows us to search by page and rows per page
    public Page<Person> findAll(Pageable pageable) {
    	log.debug("Find all person with pageable [{}]", pageable);

    	return personRepository.findAll(pageable);
    }

    // Create a new person in the database.
    @Transactional(readOnly = false)
    public Person create(Person person) {
    	log.debug("Creating person [{}]", person);

    	// Officially the validation should take place in the service layer also known as the business logic layer.
    	// Some people like do to it in the controller (2 birds 1 stone solution). If we were to 
    	// do the validation in the service layer instead of the controller layer then we would use the following code:
    	if (person.getName() == null || person.getName().isEmpty()) {
    		throw new ValidationException("Name may not be empty");
    	}

    	return personRepository.save(person);
    }

    // Update an existing person in the database
    @Transactional(readOnly = false)
    public Person update(Long id, Person updatedPerson) {
    	log.debug("Updating person [{}] with ID [{}]", updatedPerson, id);

    	return personRepository.findById(id).map(existingPerson -> {
            existingPerson.setName(updatedPerson.getName());
            existingPerson.setSalary(updatedPerson.getSalary());
            existingPerson.setStatus(updatedPerson.getStatus());
            existingPerson.setStartDate(updatedPerson.getStartDate());
            existingPerson.setDepartment(updatedPerson.getDepartment());

            return personRepository.save(existingPerson);
        }).orElseThrow(() -> new ValidationException("Person not found with ID: " + id));
    }

    // Delete an existing person from the database
    @Transactional(readOnly = false)
    public void delete(Long id) {
    	log.debug("Deleting person with ID [{}]", id);

    	// Since deleteById doesnt indicate if the record exists, we need to do an existsById to
    	// allow us to indicate to the "frontend" that the person wasn't found
        if (!personRepository.existsById(id)) {
            throw new ValidationException("Person not found with ID: " + id);
        }

        personRepository.deleteById(id);
    }

}