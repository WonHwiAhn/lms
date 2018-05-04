package com.cafe24.lms.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table
public class User {
	//@Access(AccessType.PROPERTY)
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="USER_NO")
	private Long no;
	
	@NotEmpty
	@Length(min=2, max=10)
	@Column(name="name", nullable=false, length=30)
	private String name;
	
	@Email
	//@Pattern(regexp="^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$")
	@Column(name="email", nullable=false, length=64)
	private String email;
	
	@NotEmpty
	@Pattern(regexp="^[0-9a-zA-Z]{4,12}$")
	@Column(name="password", nullable=false)
	private String password;
	
	@Pattern(regexp="^(female|male)$")
	@Column(name="gender")
	private String gender;
	
	@Column(name="reg_date")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date regDate;
	
	@Column(name="auth")
	private int auth;
	
	// 대여
	@OneToMany(/*fetch=FetchType.EAGER,*/ mappedBy="user")
	private List<Rent> rents = new ArrayList<Rent>();
	
	// 예약
	@OneToMany(/*fetch=FetchType.EAGER,*/ mappedBy="user")
	private List<Reserve> reserves = new ArrayList<Reserve>();
	
	public void addReserve(Reserve reserve) {
		this.reserves.add(reserve);
		if(reserve.getUser() != this) {
			reserve.setUser(this);
		}
	}
	
	public void addRent(Rent rent) {
		this.rents.add(rent);
		if(rent.getUser() != this) {
			rent.setUser(this);
		}
	}
	
	public List<Reserve> getReserves() {
		return reserves;
	}

	public void setReserves(List<Reserve> reserves) {
		this.reserves = reserves;
	}

	public List<Rent> getRents() {
		return rents;
	}

	public void setRents(List<Rent> rents) {
		this.rents = rents;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	
	public int getAuth() {
		return auth;
	}

	public void setAuth(int auth) {
		this.auth = auth;
	}

	@Override
	public String toString() {
		return "User [no=" + no + ", email=" + email + ", password=" + password + ", gender=" + gender + ", regDate="
				+ regDate + "]";
	}
}
