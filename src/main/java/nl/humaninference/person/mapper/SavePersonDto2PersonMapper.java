package nl.humaninference.person.mapper;

import nl.humaninference.person.dto.PersonDto;
import nl.humaninference.person.dto.SavePersonDto;
import nl.humaninference.person.entity.Person;

public class SavePersonDto2PersonMapper implements IDtoMapper<SavePersonDto, PersonDto, Person>{

	@Override
	public Person convertToEntity(SavePersonDto dto, Person current) {
		current.setName(dto.getName());
		current.setSalary(dto.getSalary());
		current.setStartDate(dto.getStartDate());
		current.setStatus(dto.getStatus());

		return current;
	}

	@Override
	public PersonDto convertToDto(Person e) {
		PersonDto personDto = new PersonDto();

		personDto.setId(e.getId());
		personDto.setName(e.getName());
		personDto.setSalary(e.getSalary());
		personDto.setStartDate(e.getStartDate());
		personDto.setStatus(e.getStatus());
		personDto.setDepartmentId(e.getDepartment().getId());

		return personDto;
	}

}
