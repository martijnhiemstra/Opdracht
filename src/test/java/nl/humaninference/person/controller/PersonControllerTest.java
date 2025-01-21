package nl.humaninference.person.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import nl.humaninference.person.entity.Department;
import nl.humaninference.person.entity.Person;
import nl.humaninference.person.entity.PersonStatus;
import nl.humaninference.person.exception.ValidationException;
import nl.humaninference.person.service.DepartmentService;
import nl.humaninference.person.service.PersonService;

public class PersonControllerTest {

    @InjectMocks
    private PersonController personController;

    @Mock
    private PersonService personService;

    @Mock
    private DepartmentService departmentService;

    // Needed to test if a validation exception is thrown in a test 
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
    
    private Person person;

    @Before
    public void setUp() {
    	MockitoAnnotations.initMocks(this);
    	
    	Department department = new Department();
        department.setId(1L);
        department.setName("IT");

        person = new Person();
        person.setName("Jane Doe");
        person.setSalary(5489.45);
        person.setStatus(PersonStatus.ACTIVE);
        person.setStartDate(LocalDateTime.now());
        person.setDepartment(department);
    }

    @Test
    public void test_findAll() {
    	Pageable pageable = PageRequest.of(0, 20);
    	Page<Person> personsPage = new PageImpl(Arrays.asList(person), pageable, 0);

        when(personService.findAll(Mockito.any())).thenReturn(personsPage);

        ResponseEntity<Page<Person>> response = personController.findAll(0, 3);

        assertThat(response.getBody()).isNotEmpty();
        assertThat(response.getBody().getContent().get(0).getName()).isEqualTo("Jane Doe");
        assertThat(response.getBody().getContent().get(0).getSalary()).isEqualTo(5489.45);
        verify(personService, times(1)).findAll(any());
    }

    @Test
    public void test_findById() {
        when(personService.findById(anyLong())).thenReturn(Optional.of(person));

        ResponseEntity<Person> response = personController.findById(1L);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Jane Doe");
        verify(personService, times(1)).findById(1L);
    }

    @Test
    public void test_create_with_validdata() {
        when(personService.create(any())).thenReturn(person);

        ResponseEntity<Person> response = personController.create(person);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Jane Doe");
        assertThat(response.getBody().getSalary()).isEqualTo(5489.45);

        verify(personService, times(1)).create(person);
    }

    @Test
    public void test_create_with_invaliddata() {
    	// Create a person with a null name
        Person person = new Person();

    	// Assume a validation exception is being thrown when calling create
        exceptionRule.expect(ValidationException.class);
        exceptionRule.expectMessage("Name may not be empty");

        ResponseEntity<Person> response = personController.create(person);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        // Make sure create isnt called due to the validation exception
        verify(personService, times(0)).create(person);
    }

    @Test
    public void test_update_with_validdata() {
        when(personService.update(1L, person)).thenReturn(person);

        ResponseEntity<Person> response = personController.update(1L, person);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Jane Doe");
        assertThat(response.getBody().getSalary()).isEqualTo(5489.45);

        verify(personService, times(1)).update(1L, person);
    }

    @Test
    public void test_update_with_invaliddata() {
    	// Important. We are testing the components directly. This means the global exception handler isn't invoked.
    	// So the response status should be 200 (OK)
    	Person updatePerson = new Person();

        when(personService.update(1L, updatePerson)).thenReturn(updatePerson);

        ResponseEntity<Person> response = personController.update(1L, updatePerson);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        verify(personService, times(1)).update(1L, updatePerson);
    }

    @Test
    public void test_delete() {
        ResponseEntity<Void> response = personController.delete(123L);

        assertThat(response.getBody()).isNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        verify(personService, times(1)).delete(123L);
    }
    
    @Test
    public void test_search() {
        Page<Person> mockPage = new PageImpl<>(Arrays.asList(person));
        Pageable pageable = PageRequest.of(0, 10);
        
        LocalDateTime localDateTime = LocalDateTime.parse("2023-01-01T00:00:00");

        // Mock dependencies
        when(departmentService.findById(1L)).thenReturn(Optional.of(person.getDepartment()));
        when(personService.searchPersons("Jane Doe", person.getDepartment(), 4000.0, 6000.0, PersonStatus.ACTIVE, 
        		localDateTime, pageable))
                .thenReturn(mockPage);

        // Call the method directly
        Page<Person> result = personController.search(
                "Jane Doe",                  // name
                4000.0,                  // minSalary
                6000.0,                  // maxSalary
                PersonStatus.ACTIVE,     // status
                localDateTime, // startDate
                1L,                      // departmentId
                0,                       // page
                10                       // size
        );

        // Assertions
        assertThat(result.getTotalElements()).isEqualTo(1L);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Jane Doe");
        assertThat(result.getContent().get(0).getSalary()).isEqualTo(5489.45);
        assertThat(result.getContent().get(0).getStatus()).isEqualTo(PersonStatus.ACTIVE);
        
        verify(personService, times(1)).searchPersons(any(), any(), any(), any(), any(), any(), any());
    }

}