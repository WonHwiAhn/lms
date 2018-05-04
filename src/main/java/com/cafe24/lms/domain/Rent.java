package com.cafe24.lms.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table
public class Rent {
	//@Access(AccessType.PROPERTY)
	@Id
	@Column(name="RENT_NO")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long no;
	
	@Column(name="rent_date")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date rentDate;
	
	@Column(name="return_date")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date returnDate;
	
	@ManyToOne
	@JoinColumn(name="USER_NO")
	private User user;
	
	
	@ManyToOne
	@JoinColumn(name="ITEM_NO")
	private Item item;
	
	//@Access(AccessType.PROPERTY)
	@OneToMany(/*fetch = FetchType.EAGER,*/ mappedBy="rent")
	private List<Reserve> reserves = new ArrayList<Reserve>(); 
	
	public void addReserve(Reserve reserve) {
		this.reserves.add(reserve);
		if(reserve.getRent() != this) {
			reserve.setRent(this);
		}
	}
	
	public List<Reserve> getReserves() {
		return reserves;
	}

	public void setReserves(List<Reserve> reserves) {
		this.reserves = reserves;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
		if(!item.getRents().contains(this)) {
			item.getRents().add(this);
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		if(!user.getRents().contains(this)) {
			user.getRents().add(this);
		}
	}

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	/*public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}*/

	public Date getRentDate() {
		return rentDate;
	}

	public void setRentDate(Date rentDate) {
		this.rentDate = rentDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	@Override
	public String toString() {
		return "Rent [no=" + no + ", rentDate=" + rentDate + ", returnDate=" + returnDate + "]";
	}
}
