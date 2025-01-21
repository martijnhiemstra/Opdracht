package nl.humaninference.person.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class PersonTest {

	@Test
	public void test_getters_setters() {
		Person person = new Person();
		person.setName("Person 1");
		person.setId(56L);
		person.setDepartment(null);

        // Assert
        assertThat(person.getName()).isEqualTo("Person 1");
        assertThat(person.getDepartment()).isNull();
        assertThat(person.getId()).isEqualTo(56L);
	}
	
}
