package nl.humaninference.person.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import nl.humaninference.person.entity.Department;
import nl.humaninference.person.repository.IDepartmentRepository;

public class DepartmentServiceTest {

    @InjectMocks
    private DepartmentService departmentService;

    @Mock
    private IDepartmentRepository departmentRepository;

    private Department department;

    @Before
    public void setUp() {
    	MockitoAnnotations.initMocks(this);

        department = new Department();
        department.setId(378L);
        department.setName("Finance");
    }

    @Test
    public void test_findById() {
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.of(department));

        Optional<Department> foundDepartment = departmentService.findById(1L);

        assertThat(foundDepartment).isPresent();
        assertThat(foundDepartment.get().getName()).isEqualTo("Finance");
        verify(departmentRepository, times(1)).findById(1L);
    }
    
}