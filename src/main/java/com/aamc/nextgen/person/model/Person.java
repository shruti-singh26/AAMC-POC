package com.aamc.nextgen.person.model;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Person implements Serializable{
	
	
	static final long serialVersionUID = 1L;
			
	public Person() {
		
	}
    public Person(Long id, @NotBlank String name, @NotBlank String email, String gender, int age, String address) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.gender = gender;
		this.age = age;
		this.address = address;
	}

	private Long id;
	
	@NotBlank
	private String name;
    
    @NotBlank
    private String email;

    //@NotBlank
    private String gender;
    
    //@NotBlank
    private int age;
    
    private String address;

   public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "{\"id\":" + id  + ",\"name\":\"" + name + "\",\"email\":\"" + email
				+ "\",\"gender\":\"" + gender + "\",\"age\":" + age + ",\"address\":\"" + address + "\"}";
	}    

}
