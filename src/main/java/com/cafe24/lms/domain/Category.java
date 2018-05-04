package com.cafe24.lms.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Category {
	//@Access(AccessType.PROPERTY)
	@Id
	@Column(name="CATEGORY_NO")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long no;
	
	@Column(name="name")
	private String name;
	
	@OneToMany(/*fetch=FetchType.EAGER,*/ mappedBy="category", cascade=CascadeType.ALL)
	private List<Item> items = new ArrayList<Item>();
	
	public void addItem(Item item) {
		this.items.add(item);
		if(item.getCategory() != this) {
			item.setCategory(this);
		}
	}
	
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
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

	@Override
	public String toString() {
		return "Category [no=" + no + ", name=" + name + "]";
	}
}
