package com.aamc.nextgen.person.controller;

import java.util.List;
import java.util.StringJoiner;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.aamc.nextgen.person.exception.BusinessObjectNotFoundException;
import com.aamc.nextgen.person.exception.ConflictException;
import com.aamc.nextgen.person.exception.NoContentException;
import com.aamc.nextgen.person.exception.ValidationException;
import com.aamc.nextgen.person.model.Person;
import com.aamc.nextgen.person.service.PersonService;

/**
 * @author 
 *
 */
@RestController
public class PersonController {
	
	@Autowired
    private PersonService personService;
	
	Logger logger = LoggerFactory.getLogger(PersonController.class);

    /**
     * @param pageable
     * @return
     * @throws Exception 
     */
    @GetMapping("/persons")
    public List<Person> getPersons() {

    	 List<Person> lPersons = personService.getAllPersons();
    	
    	if(lPersons != null && lPersons.size() > 0) {
    		logger.info("getPersons::List of persons are retrived successfully");
    		return lPersons;
    	}else {
    		logger.warn("getPersons::There are no persons in database.");
    		throw new NoContentException("getPersons::There are no persons in database.");
    	}
       
    }

    /**
     * @param personId
     * @return
     */
    @GetMapping("/persons/{personId}")
    public Person getPerson(@PathVariable Long personId) {
    	
    	if(personId < 0) {
    		logger.warn("getPerson::Enter a valid id for person: " + personId);
    		throw new ValidationException("getPerson::Please enter valid Id for person.");
    	}
    	try {
    		Person lPerson = personService.getPersonById(personId);
    		logger.info("getPerson::Person is retrived successfully");
    		return lPerson;
        	
        } catch (EmptyResultDataAccessException ex) {
        	logger.error("getPerson::Person is not available for Id:" + personId);
        	throw new BusinessObjectNotFoundException("getPerson::Person is not available for Id:" + personId);
        }
    }

    /**
     * @param person
     * @return
     */
    @PostMapping("/persons")
    public Person createPerson(@Valid @RequestBody Person person) {
    	
    	boolean badRequest = false;
    	StringJoiner lMessages = new StringJoiner(",");    	
    	
    	if(person.getEmail().isEmpty()) {
    		logger.warn("createPerson::Email should not be empty.");
    		badRequest = true;
    		lMessages.add("Email should not be empty.");
    	}
    	if(person.getName().isEmpty()) {
    		logger.warn("createPerson::Name should not be empty.");
    		badRequest = true;
    		lMessages.add("Name should not be empty.");
    	}
    	if((person.getAge() < 0 || person.getAge() > 150)) {
    		logger.warn("createPerson::Age entered is out of range.");
    		badRequest = true;
    		lMessages.add("Age entered is out of range. Please enter value between 0 and 150.");
    	}
    	if(badRequest) {
    		throw new ValidationException(lMessages.toString());
    	}
    	
    	try {
    		List<Person> lPersons = personService.getAllPersons();
    		
    		if(lPersons!= null && lPersons.size() > 0) {
	    		Person lPerson1 = personService.getPersonByEmail(person.getEmail());
	        	if(lPerson1 != null) {
	        		logger.warn("createPerson::Person with this email id already exists.:" + person.getEmail());
	        		throw new ConflictException("createPerson::Person with this email id already exists.:" + person.getEmail());
	        	}
    		}
        	
        } catch (EmptyResultDataAccessException ex) {
        	logger.error(ex.getMessage());
        	logger.info("createPerson::Person is not available for email:" + person.getEmail());
        }
    	
    	
		Person lPerson = personService.createPerson(person);
    	logger.info("createPerson::Person is created Successfully");
    	return lPerson;    	
    }

    /**
     * @param personId
     * @param personRequest
     * @return
     */
    @PutMapping("/persons/{personId}")
    public Person updatePerson(@PathVariable Long personId,
                                   @Valid @RequestBody Person person) {

    	boolean badRequest = false;
    	StringJoiner lMessages = new StringJoiner(",");
    	if(personId < 0) {
    		badRequest = true;
    		logger.warn("updatePerson::Enter a valid id for person: " + personId);
    		lMessages.add("Enter a valid id for person");
    	}
    	if(person.getEmail().isEmpty()) {
    		badRequest = true;
    		logger.warn("updatePerson::Email should not be empty.");
    		lMessages.add("Email should not be empty.");
    	}
    	if(person.getName().isEmpty()) {
    		badRequest = true;
    		logger.warn("updatePerson::Name should not be empty.");
    		lMessages.add("Name should not be empty.");
    	}
    	if((person.getAge() < 0 || person.getAge() > 150)) {
    		badRequest = true;
    		logger.warn("updatePerson::Age entered is out of range.");
    		lMessages.add("Age entered is out of range. Please enter value between 0 and 150.");
    	}
    	
    	if(badRequest) {
    		throw new ValidationException(lMessages.toString());
    	}
    	
    	try {
    		Person lPerson1 = personService.getPersonById(personId);
        	
        } catch (EmptyResultDataAccessException ex) {
        	logger.error("updatePerson::Person is not available for Id:" + personId);
        	throw new BusinessObjectNotFoundException("updatePerson::Person is not available for Id:" + personId);
        }
    	
    	try {
    		List<Person> lPersons = personService.getAllPersons();
    		
    		if(lPersons!= null && lPersons.size() > 1) {
	    		Person lPerson2 = personService.getPersonByEmail(person.getEmail());
	        	if(lPerson2 != null && personId!= lPerson2.getId()) {
	        		logger.warn("updatePerson::Person with this email id already exists.:" + person.getEmail());
	        		throw new ConflictException("updatePerson::Person with this email id already exists.:" + person.getEmail());
	        	}
    		}
        	
        } catch (EmptyResultDataAccessException ex) {
        	logger.info("updatePerson::Person is not available for email:" + person.getEmail());
        }
    	person.setId(personId);
    	Person lPerson = personService.updatePerson(person);
        logger.info("updatePerson::Person is updated Successfully");
        return lPerson;
    }


    /**
     * @param personId
     * @return
     */
    @DeleteMapping("/persons/{personId}")
    public void deletePerson(@PathVariable Long personId) {
    	
    	if(personId < 0) {
    		logger.warn("deletePerson::Enter a valid id for person: " + personId);
    		throw new ValidationException("deletePerson::Enter a valid id for person: " + personId);
    	}
    	
    	try {
    		Person lPerson = personService.getPersonById(personId);
        	
        } catch (EmptyResultDataAccessException ex) {
        	logger.info("deletePerson::Person is not available for Id:" + personId);
        	throw new BusinessObjectNotFoundException("deletePerson::Person is not available for Id:\" + personId");
        }
    	personService.deletePerson(personId);
        logger.info("deletePerson::Person is deleted Successfully");
    }

}
