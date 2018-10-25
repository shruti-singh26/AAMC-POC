package com.aamc.nextgen.person.controller;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.aamc.nextgen.person.AbstractTest;
import com.aamc.nextgen.person.model.Person;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PersonControllerTests extends AbstractTest{
	
	@LocalServerPort
    private int port;
	
	final String URL = "http://localhost:";
	
	final String domain = "/persons";
	
	final Person person = new Person(1L, "test1", "test1@gmail.com","Female",18,"TestAddress1");

	@Test
	public void test1_getPersonsListEmpty() throws Exception {
		
		ResponseEntity<List> ResponsePersonList = restTemplate.getForEntity(URL + port + domain, List.class);
		assertEquals(204, ResponsePersonList.getStatusCodeValue());
	}
	
	@Test
	public void test2_createPerson() throws Exception {
		
		
		Person person1 = new Person(1L, "test1", "test1@gmail.com","Female",-1,"TestAddress1");
		Person person2 = new Person(2L, "test2", "test2@gmail.com","Male",20,"TestAddress2");

		ResponseEntity<Person> ResponsePerson = restTemplate.postForEntity(URL + port + domain, person, Person.class);
		
		assertEquals(200, ResponsePerson.getStatusCodeValue());
		assertNotNull(ResponsePerson.getBody());
		assertEquals(person.toString(), ResponsePerson.getBody().toString());
		
		ResponsePerson = restTemplate.postForEntity(URL + port + domain, person, Person.class);
		assertEquals(409, ResponsePerson.getStatusCodeValue());
		
		ResponsePerson = restTemplate.postForEntity(URL + port + domain, person1, Person.class);
		assertEquals(400, ResponsePerson.getStatusCodeValue());
		
		ResponsePerson = restTemplate.postForEntity(URL + port + domain, person2, Person.class);
		
		assertEquals(200, ResponsePerson.getStatusCodeValue());
		assertNotNull(ResponsePerson.getBody());
		assertEquals(person2.toString(), ResponsePerson.getBody().toString());
		
	}
	
	@Test
	public void test3_getPersonsListNonEmpty() throws Exception {
		
		ResponseEntity<List> ResponsePersonList = restTemplate.getForEntity(URL + port + domain, List.class);
		
		assertEquals(200, ResponsePersonList.getStatusCodeValue());
		assertNotNull(ResponsePersonList.getBody());		
		assertTrue(ResponsePersonList.getBody().size() > 0);
	}
	
	@Test
	public void test4_getPerson() throws Exception {
		
		ResponseEntity<Person> ResponsePerson = restTemplate.getForEntity(URL + port + domain + "/1", Person.class);
		
		assertEquals(200, ResponsePerson.getStatusCodeValue());
		assertNotNull(ResponsePerson.getBody());		
		assertEquals(person.toString(), ResponsePerson.getBody().toString());
		
		ResponsePerson = restTemplate.getForEntity(URL + port + domain + "/-1", Person.class);
		assertEquals(400, ResponsePerson.getStatusCodeValue());
		
		ResponsePerson = restTemplate.getForEntity(URL + port + domain + "/10", Person.class);
		assertEquals(404, ResponsePerson.getStatusCodeValue());

	}

	@Test 
	public void test5_updatePerson() {
		
		Person person1 = new Person(1L, "test1", "test1@gmail.com","Male",18,"TestAddress1");
		Person person2 = new Person(2L, "test2", "test2@gmail.com","Male",-1,"TestAddress2");
		Person person3 = new Person(1L, "test1", "test2@gmail.com","Male",18,"TestAddress1");
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON); 
	    HttpEntity<Person> entity1 = new HttpEntity<Person>(person1, headers); 
	    HttpEntity<Person> entity2 = new HttpEntity<Person>(person2, headers); 
	    HttpEntity<Person> entity3 = new HttpEntity<Person>(person3, headers); 
	    
	    ResponseEntity<Person> ResponsePerson = restTemplate.exchange(URL + port + domain + "/1", HttpMethod.PUT, entity3, Person.class);
		assertEquals(409, ResponsePerson.getStatusCodeValue());
		
		ResponsePerson = restTemplate.exchange(URL + port + domain + "/2", HttpMethod.PUT, entity2, Person.class);
		assertEquals(400, ResponsePerson.getStatusCodeValue());
		
		ResponsePerson = restTemplate.exchange(URL + port + domain + "/10", HttpMethod.PUT, entity3, Person.class);
		assertEquals(404, ResponsePerson.getStatusCodeValue());
		
	    ResponsePerson = restTemplate.exchange(URL + port + domain + "/1", HttpMethod.PUT, entity1, Person.class);
		assertEquals(200, ResponsePerson.getStatusCodeValue());
		assertNotNull(ResponsePerson.getBody());
		assertEquals(person1.toString(), ResponsePerson.getBody().toString());
		
		
	}

	@Test
	public void test6_deletePerson() {
		
		ResponseEntity<?> ResponsePerson = restTemplate.exchange(URL + port + domain + "/-1", HttpMethod.DELETE, null, Person.class);
		assertEquals(400, ResponsePerson.getStatusCodeValue());
		
		ResponsePerson = restTemplate.exchange(URL + port + domain + "/10", HttpMethod.DELETE, null, Person.class);
		assertEquals(404, ResponsePerson.getStatusCodeValue());
		
	    ResponsePerson = restTemplate.exchange(URL + port + domain + "/1", HttpMethod.DELETE, null, Person.class);
	    
	    assertEquals(200, ResponsePerson.getStatusCodeValue());

	}
}
