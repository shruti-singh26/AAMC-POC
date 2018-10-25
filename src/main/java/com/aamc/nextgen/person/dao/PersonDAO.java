package com.aamc.nextgen.person.dao;

import java.util.List;

import com.aamc.nextgen.person.model.Person;

public interface PersonDAO {
	
	Person getPersonById(Long id);
	
	Person getPersonByEmail(String id);

	List<Person> getAllPersons();

	boolean deletePerson(Long id);

	Person updatePerson(Person person);

	Person createPerson(Person person);
}
