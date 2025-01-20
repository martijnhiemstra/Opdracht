package nl.humaninference.person.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import nl.humaninference.person.entity.Person;

public interface IPersonRepository extends JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {

	
	
}
