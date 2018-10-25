package com.aamc.nextgen.person.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import com.aamc.nextgen.person.dao.PersonDAO;
import com.aamc.nextgen.person.model.Person;

@Service
@CacheConfig(cacheNames={"persons"})
public class PersonServiceImpl implements PersonService{

	@Autowired
    private PersonDAO personDao;
	
	@Override
	@Cacheable
	public Person getPersonById(Long id) {
		return personDao.getPersonById(id);
	}
	
	@Override
	public Person getPersonByEmail(String email) {
		return personDao.getPersonByEmail(email);
	}

	@Override
	@Cacheable
	public List<Person> getAllPersons() {
		return personDao.getAllPersons();
	}

	@Override
	@CacheEvict(allEntries=true)
	public Person createPerson(Person person) {
		return personDao.createPerson(person);
	}
	
	@Override
	@CacheEvict(allEntries=true)
	public Person updatePerson(Person person) {
		return personDao.updatePerson(person);
	}
	
	@Override
	@CacheEvict(allEntries=true)
	public boolean deletePerson(Long id) {
		return personDao.deletePerson(id);
	}

}
