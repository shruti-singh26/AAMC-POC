package com.aamc.nextgen.person.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.aamc.nextgen.person.model.Person;

@Repository
public class PersonDAOImpl implements PersonDAO {

	JdbcTemplate jdbcTemplate;

	private final String SQL_GET_PERSON_ID = "select * from person where id = ?";
	private final String SQL_GET_PERSON_EMAIL = "select * from person where email = ?";
	private final String SQL_GET_ALL = "select * from person";
	private final String SQL_INSERT_PERSON = "insert into person(name, email, gender,age,address) values(?,?,?,?,?)";
	private final String SQL_UPDATE_PERSON = "update person set name = ?, email = ?, gender  = ?, age  = ?, address  = ? where id = ?";
	private final String SQL_DELETE_PERSON = "delete from person where id = ?";
	
	@Autowired
	public PersonDAOImpl(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public Person getPersonById(Long id) {
		return jdbcTemplate.queryForObject(SQL_GET_PERSON_ID, new Object[] { id }, new PersonMapper());
	}
	
	public Person getPersonByEmail(String email) {
		return jdbcTemplate.queryForObject(SQL_GET_PERSON_EMAIL, new Object[] { email }, new PersonMapper());
	}

	public List<Person> getAllPersons() {
		return jdbcTemplate.query(SQL_GET_ALL, new PersonMapper());
	}
	
	public Person createPerson(final Person person) {
		
		KeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(SQL_INSERT_PERSON, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, person.getName());
				ps.setString(2, person.getEmail());
				ps.setString(3, person.getGender());
				ps.setInt(4, person.getAge());
				ps.setString(5, person.getAddress());
				return ps;
			}
		}, holder);

		Long newPersonId;
	    if (holder.getKeys().size() > 1) {
	    	newPersonId = (Long)holder.getKeys().get("id");
	    } else {
	    	newPersonId= holder.getKey().longValue();
	    }
		//long newPersonId = holder.getKey().longValue();
		person.setId(newPersonId);
		return person;
	}
	
	public Person updatePerson(Person person) {
		boolean updated = jdbcTemplate.update(SQL_UPDATE_PERSON, person.getName(), person.getEmail(),
				person.getGender(),person.getAge(),person.getAddress(),
				person.getId()) > 0;
		if(updated) {
			return getPersonById(person.getId());
		}else {
			return null;
		}		
	}
	
	public boolean deletePerson(Long personId) {
		return jdbcTemplate.update(SQL_DELETE_PERSON, personId) > 0;
	}

	private static final class PersonMapper implements RowMapper<Person> {

		public Person mapRow(ResultSet resultSet, int i) throws SQLException {

			Person person = new Person();
			person.setId(resultSet.getLong("id"));
			person.setName(resultSet.getString("name"));
			person.setEmail(resultSet.getString("email"));
			person.setGender(resultSet.getString("gender"));
			person.setAge(resultSet.getInt("age"));
			person.setAddress(resultSet.getString("address"));
			return person;
		}
	}

	
}
