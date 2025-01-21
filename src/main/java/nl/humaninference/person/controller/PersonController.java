package nl.humaninference.person.controller;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nl.humaninference.person.entity.Department;
import nl.humaninference.person.entity.Person;
import nl.humaninference.person.entity.PersonStatus;
import nl.humaninference.person.exception.ValidationException;
import nl.humaninference.person.service.DepartmentService;
import nl.humaninference.person.service.PersonService;

@RestController
@RequestMapping("/api/person")
public class PersonController {
	
    private final PersonService personService;

    private final DepartmentService departmentService;

    public PersonController(PersonService personService, DepartmentService departmentService) {
		this.personService = personService;
		this.departmentService = departmentService;
	}

	// This endpoint allows us to search for persons. In this situation we use request parameters.
    // We could also use a dto if we want to. 
    @GetMapping("/search")
    public Page<Person> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Double minSalary,
            @RequestParam(required = false) Double maxSalary,
            @RequestParam(required = false) PersonStatus status,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) Long departmentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Department department = null;
        if (departmentId != null) {
            department = departmentService.findById(departmentId).orElse(null);
        }

        return personService.searchPersons(name, department, minSalary, maxSalary, status, startDate, pageable);
    }

    // This endpoint allows people to create a new person.
    @PostMapping
    public ResponseEntity<Person> create(@RequestBody Person person) {
    	// We have a choice to do the validation here in the controller or in the service layer.
    	// For this assignment I have chosen to do both just to show that its possible to do both. 
    	// Putting the validation in the controller would look like this:
    	if (person.getName() == null || person.getName().isEmpty()) {
    		throw new ValidationException("Name may not be empty");
    	}
    	
    	// We can add more validation if we want. For this assignment I'll only do the name validation since the other validations work in the same way

        Person createdPerson = personService.create(person);

        return ResponseEntity.ok(createdPerson);
    }

    // Allow getting all the persons and allow the option to supply a page and rows per page
    @GetMapping
    public ResponseEntity<Page<Person>> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Person> persons = personService.findAll(pageable);
        return ResponseEntity.ok(persons);
    }

    // Allow getting 1 person
    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable Long id) {
        Optional<Person> person = personService.findById(id);
        
        return person.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Allow updating an existing person
    @PutMapping("/{id}")
    public ResponseEntity<Person> update(@PathVariable Long id, @RequestBody Person updatedPerson) {
        Person person = personService.update(id, updatedPerson);

        return ResponseEntity.ok(person);
    }

    // Allow the deletion of a specific person
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        personService.delete(id);

        return ResponseEntity.noContent().build();
    }
    
}