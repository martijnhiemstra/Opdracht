package nl.humaninference.person.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.mockito.Mockito;

public class DepartmentTest {

	@Test
	public void test_getters_setters() {
		Department department = new Department();
        department.setName("Department 1");
        department.setId(12L);
        department.setPersons(Arrays.asList(Mockito.mock(Person.class), Mockito.mock(Person.class), Mockito.mock(Person.class)));

        // Assert
        assertThat(department.getName()).isEqualTo("Department 1");
        assertThat(department.getPersons().size()).isEqualTo(3);
        assertThat(department.getId()).isEqualTo(12L);
	}
	
}
