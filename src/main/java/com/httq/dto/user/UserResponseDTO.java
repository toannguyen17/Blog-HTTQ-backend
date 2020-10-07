package com.httq.dto.user;

import java.util.List;

import com.httq.model.Role;
import io.swagger.annotations.ApiModelProperty;

public class UserResponseDTO {

	@ApiModelProperty(position = 0)
	private Long id;

	@ApiModelProperty(position = 1)
	private String  email;

	@ApiModelProperty(position = 2)
	private String lastName;

	@ApiModelProperty(position = 3)
	private String firstName;

	@ApiModelProperty(position = 4)
	private String phone;

	@ApiModelProperty(position = 5)
	private String address;

	@ApiModelProperty(position = 6)
	List<Role> roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
