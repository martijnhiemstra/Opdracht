package nl.humaninference.person.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import nl.humaninference.person.entity.Department;
import nl.humaninference.person.entity.Person;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PersonRepositoryTest {

	@Autowired
	private IPersonRepository personRepository;

//    @Test
//    public void test_save() {
//        Department department = new Department();
//        department.setName("IT");
//
//        Person person = new Person();
//        person.setName("John Doe");
//        person.setSalary(5000.00);
//        person.setStatus(PersonStatus.ACTIVE);
//        person.setStartDate(LocalDateTime.now());
//        person.setDepartment(department);
//
//        Person savedPerson = personRepository.save(person);
//
//        Optional<Person> foundPerson = personRepository.findById(savedPerson.getId());
//        assertThat(foundPerson).isPresent();
//        assertThat(foundPerson.get().getName()).isEqualTo("John Doe");
//    }

    @Test
    public void test_findAll() {
    	List<Person> persons = personRepository.findAll();

        assertThat(persons).isNotNull();
        assertThat(persons.size()).isEqualTo(10);
    }

	@Test
	public void test_findById() {
		Optional<Person> personOptional = personRepository.findById(1L);

		assertThat(personOptional.isPresent()).isEqualTo(true);
		assertThat(personOptional.get().getId()).isEqualTo(1);
		assertThat(personOptional.get().getName()).isEqualTo("John Doe");
	}

	@Test
	public void test_specification_findbyname() {
		Pageable pageable = PageRequest.of(0, 20);
		
		Page<Person> persons = personRepository.findAll(PersonSpecifications.search("John", null, null, null, null, null), pageable);
		
		assertThat(persons.getContent().size()).isEqualTo(2);
		assertThat(persons.getContent().get(0).getName()).isEqualTo("John Doe");
		assertThat(persons.getContent().get(1).getName()).isEqualTo("Alice Johnson");
	}

	@Test
	public void test_specification_pagination() {
		Pageable pageable = PageRequest.of(1, 3);
		
		Page<Person> persons = personRepository.findAll(PersonSpecifications.search(null, null, null, null, null, null), pageable);
		
		assertThat(persons.getContent().size()).isEqualTo(3);
		assertThat(persons.getContent().get(0).getId()).isEqualTo(4);
		assertThat(persons.getContent().get(1).getId()).isEqualTo(5);
		assertThat(persons.getContent().get(2).getId()).isEqualTo(6);
	}

	@Test
	public void test_specification_findbysalary() {
		Pageable pageable = PageRequest.of(0, 20);
		
		Page<Person> persons = personRepository.findAll(PersonSpecifications.search(null, null, 5000.0, 6000.0, null, null), pageable);
		
		assertThat(persons.getContent().size()).isEqualTo(4);
		assertThat(persons.getContent().get(0).getId()).isEqualTo(1);
		assertThat(persons.getContent().get(1).getId()).isEqualTo(4);
		assertThat(persons.getContent().get(2).getId()).isEqualTo(7);
		assertThat(persons.getContent().get(3).getId()).isEqualTo(10);
	}
	
	@Test
	public void test_specification_department() {
		Pageable pageable = PageRequest.of(0, 20);
		
		Department department = new Department();
		department.setId(2);
		
		Page<Person> persons = personRepository.findAll(PersonSpecifications.search(null, department, null, null, null, null), pageable);
		
		assertThat(persons.getContent().size()).isEqualTo(2);
		assertThat(persons.getContent().get(0).getId()).isEqualTo(2);
		assertThat(persons.getContent().get(1).getId()).isEqualTo(7);
	}

}
