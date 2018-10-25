package com.aamc.nextgen.person.service;

import java.util.List;

import com.aamc.nextgen.person.model.Person;

public interface PersonService {

	Person getPersonById(Long id);
	
	Person getPersonByEmail(String email);

	List<Person> getAllPersons();

	boolean deletePerson(Long id);

	Person updatePerson(Person person);

	Person createPerson(Person person);
}
