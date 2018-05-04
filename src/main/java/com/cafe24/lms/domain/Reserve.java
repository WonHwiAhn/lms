package com.cafe24.lms.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table
public class Reserve {
	@Id
	@Column(name="RESERVE_NO")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long no;
	
	@Column(name="order_num")
	private int orderNum;
	
	@Column(name="plan_rent_date")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date planRentDate;
	
	@Column(name="plan_return_date")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date planReturnDate;

	// 회원
	@ManyToOne
	@JoinColumn(name="USER_NO")
	private User user;
	
	// 대여
	@ManyToOne
	@JoinColumn(name="RENT_NO")
	private Rent rent;
	
	public Rent getRent() {
		return rent;
	}

	public void setRent(Rent rent) {
		this.rent = rent;
		if(!rent.getReserves().contains(this)) {
			rent.getReserves().add(this);
		}
	}
	
	public Date getPlanRentDate() {
		return planRentDate;
	}

	public void setPlanRentDate(Date planRentDate) {
		this.planRentDate = planRentDate;
	}

	public Date getPlanReturnDate() {
		return planReturnDate;
	}

	public void setPlanReturnDate(Date planReturnDate) {
		this.planReturnDate = planReturnDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		if(!user.getReserves().contains(this)) {
			user.getReserves().add(this);
		}
	}

	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	@Override
	public String toString() {
		return "Reserve [no=" + no + ", orderNum=" + orderNum + "]";
	}
}
