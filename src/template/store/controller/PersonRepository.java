package template.store.controller;

import org.springframework.data.repository.CrudRepository;

import template.store.models.Person;

public interface PersonRepository extends CrudRepository<Person, Long>{

	public Person findByName(String name);
	
	public boolean deleteByName(String name);
}
