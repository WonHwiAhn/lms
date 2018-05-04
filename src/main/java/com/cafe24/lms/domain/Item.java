package com.cafe24.lms.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="DTYPE")
public abstract class Item{
	//@Access(AccessType.PROPERTY)
	@Id
	@Column(name="ITEM_NO")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long no;
	
	@Column(name="name")
	private String name;
	
	@Column(name="status")
	private int status;
	
	@ManyToOne
	@JoinColumn(name="CATEGORY_NO")
	private Category category;
	
	@OneToMany(fetch=FetchType.EAGER, mappedBy="item")
	private List<Rent> rents = new ArrayList<Rent>();
	
	public void addRent(Rent rent) {
		this.rents.add(rent);
		if(rent.getItem() != this) {
			rent.setItem(this);
		}
	}
	
	public List<Rent> getRents() {
		return rents;
	}

	public void setRents(List<Rent> rents) {
		this.rents = rents;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
		if(!category.getItems().contains(this)) {
			category.getItems().add(this);
		}
	}
}
