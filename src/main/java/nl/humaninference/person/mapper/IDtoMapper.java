package nl.humaninference.person.mapper;

public interface IDtoMapper<SD, D, E> {

	E convertToEntity(SD dto, E current);

	D convertToDto(E e);

}
