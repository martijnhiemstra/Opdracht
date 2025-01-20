package nl.humaninference.person.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import nl.humaninference.person.entity.Department;
import nl.humaninference.person.entity.Person;
import nl.humaninference.person.entity.PersonStatus;
import nl.humaninference.person.service.PersonService;

public class PersonControllerTest {

    @InjectMocks
    private PersonController personController;

    @Mock
    private PersonService personService;

    @Test
    public void test_findAll() {
        Department department = new Department();
        department.setName("HR");

        Person person = new Person();
        person.setName("John Doe");
        person.setSalary(4000.00);
        person.setStatus(PersonStatus.ACTIVE);
        person.setStartDate(LocalDateTime.now());
        person.setDepartment(department);

        when(personService.findAll(Mockito.any())).thenReturn((Page<Person>) PageRequest.of(1, 10));

        ResponseEntity<Page<Person>> response = personController.findAll(0, 3);

        assertThat(response.getBody()).isNotEmpty();
        // assertThat(response.getBody().get(0).getName()).isEqualTo("John Doe");
        verify(personService, times(1)).findAll(null);
    }

    @Test
    public void test_findById() {
        Department department = new Department();
        department.setName("IT");

        Person person = new Person();
        person.setName("Jane Doe");
        person.setSalary(5000.00);
        person.setStatus(PersonStatus.ACTIVE);
        person.setStartDate(LocalDateTime.now());
        person.setDepartment(department);

        when(personService.findById(anyLong())).thenReturn(Optional.of(person));

        ResponseEntity<Person> response = personController.findById(1L);

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Jane Doe");
        verify(personService, times(1)).findById(1L);
    }
}