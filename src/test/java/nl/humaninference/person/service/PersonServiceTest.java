package nl.humaninference.person.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import nl.humaninference.person.entity.Department;
import nl.humaninference.person.entity.Person;
import nl.humaninference.person.entity.PersonStatus;
import nl.humaninference.person.exception.ValidationException;
import nl.humaninference.person.repository.IPersonRepository;

public class PersonServiceTest {

    @InjectMocks
    private PersonService personService;

    @Mock
    private IPersonRepository personRepository;

    // Needed to test if a validation exception is thrown in a test 
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    private Person person;

    @Before
    public void setUp() {
    	MockitoAnnotations.initMocks(this);

        Department department = new Department();
        department.setId(378L);
        department.setName("Finance");

        person = new Person();
        person.setId(123L);
        person.setName("Jane Doe");
        person.setSalary(6000.00);
        person.setStatus(PersonStatus.ACTIVE);
        person.setStartDate(LocalDateTime.now());
        person.setDepartment(department);
    }

    @SuppressWarnings("unchecked")
	@Test
    public void test_searchPersons() {
    	Pageable pageable = PageRequest.of(0, 20);
    	Page<Person> personsPage = new PageImpl(Arrays.asList(person, person), pageable, 0);

    	when(personRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(personsPage);

    	// Call the method
    	Page<Person> persons = personService.searchPersons("Test", null, 123.45, 234.56, null, null, pageable);

    	// Test that the page object that is returned by the repository contains the 2 people we expect
    	assertThat(persons.getContent().size()).isEqualTo(2);
    	assertThat(persons.getContent().get(0).getName()).isEqualTo("Jane Doe");
    	assertThat(persons.getContent().get(0).getId()).isEqualTo(123L);
    	assertThat(persons.getContent().get(0).getDepartment().getId()).isEqualTo(378L);

    	assertThat(persons.getContent().get(1).getName()).isEqualTo("Jane Doe");
    	assertThat(persons.getContent().get(1).getId()).isEqualTo(123L);

        // Test that the repository has been called
        verify(personRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    public void test_findAll() {
    	Pageable pageable = PageRequest.of(0, 20);
    	Page<Person> personsPage = new PageImpl(Arrays.asList(person), pageable, 0);

    	when(personRepository.findAll(any(Pageable.class))).thenReturn(personsPage);

        Page<Person> persons = personService.findAll(pageable);

        assertThat(persons.getContent().size()).isEqualTo(1);
        verify(personRepository, times(1)).findAll(pageable);
    }

    @Test
    public void test_create_withvaliddata() {
        when(personRepository.save(any(Person.class))).thenReturn(person);

        Person createdPerson = personService.create(person);

        assertThat(createdPerson.getName()).isEqualTo("Jane Doe");
        verify(personRepository, times(1)).save(person);
    }

    @Test
    public void test_create_with_nullname() {
    	Person newPerson = new Person();
    	newPerson.setName(null);

    	// Assume a validation exception is being thrown when calling create
        exceptionRule.expect(ValidationException.class);
        exceptionRule.expectMessage("Name may not be empty");

        personService.create(newPerson);

        verify(personRepository, times(0)).save(person);
    }

    @Test
    public void test_create_with_emptyname() {
    	Person newPerson = new Person();
    	newPerson.setName("");

    	// Assume a validation exception is being thrown when calling create
        exceptionRule.expect(ValidationException.class);
        exceptionRule.expectMessage("Name may not be empty");

        personService.create(newPerson);

        verify(personRepository, times(0)).save(person);
    }

    @Test
    public void test_update_with_validdata() {
        when(personRepository.findById(any(Long.class))).thenReturn(Optional.of(person));
        when(personRepository.save(any(Person.class))).thenReturn(person);

        Person createdPerson = personService.update(1L, person);

        assertThat(createdPerson.getName()).isEqualTo("Jane Doe");
        verify(personRepository, times(1)).save(person);
    }

    @Test
    public void test_update_with_nonexistingperson() {
        when(personRepository.findById(any(Long.class))).thenReturn(Optional.empty());

    	// Assume a validation exception is being thrown when calling create
        exceptionRule.expect(ValidationException.class);
        exceptionRule.expectMessage("Person not found with ID: 12");

        Person createdPerson = personService.update(12L, person);

        verify(personRepository, times(0)).save(person);
    }

    @Test
    public void test_delete_existingperson() {
        when(personRepository.existsById(anyLong())).thenReturn(true);

    	personService.delete(1L);

        verify(personRepository, times(1)).existsById(1L);
        verify(personRepository, times(1)).deleteById(1L);
    }

    @Test
    public void test_delete_nonexistingperson() {
        when(personRepository.existsById(anyLong())).thenReturn(false);

    	// Assume a validation exception is being thrown when calling delete
        exceptionRule.expect(ValidationException.class);
        exceptionRule.expectMessage("Person not found with ID: 3");

    	personService.delete(3L);

        verify(personRepository, times(1)).existsById(3L);
        verify(personRepository, times(0)).deleteById(3L);
    }

    @Test
    public void test_findById() {
        when(personRepository.findById(anyLong())).thenReturn(Optional.of(person));

        Optional<Person> foundPerson = personService.findById(1L);

        assertThat(foundPerson).isPresent();
        assertThat(foundPerson.get().getName()).isEqualTo("Jane Doe");
        verify(personRepository, times(1)).findById(1L);
    }
    
}