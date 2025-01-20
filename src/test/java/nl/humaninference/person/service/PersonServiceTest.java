package nl.humaninference.person.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import nl.humaninference.person.entity.Department;
import nl.humaninference.person.entity.Person;
import nl.humaninference.person.entity.PersonStatus;
import nl.humaninference.person.repository.IPersonRepository;

public class PersonServiceTest {

    @InjectMocks
    private PersonService personService;

    @Mock
    private IPersonRepository personRepository;

    private Person person;

    @Before
    public void setUp() {
        Department department = new Department();
        department.setName("Finance");

        person = new Person();
        person.setName("Jane Doe");
        person.setSalary(6000.00);
        person.setStatus(PersonStatus.ACTIVE);
        person.setStartDate(LocalDateTime.now());
        person.setDepartment(department);
    }

    @Test
    public void test_createPerson() {
        when(personRepository.save(any(Person.class))).thenReturn(person);

        Person createdPerson = personService.createPerson(person);

        assertThat(createdPerson.getName()).isEqualTo("Jane Doe");
        verify(personRepository, times(1)).save(person);
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