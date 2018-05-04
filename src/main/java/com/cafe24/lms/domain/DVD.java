package com.cafe24.lms.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("D")
public class DVD extends Item{
	private String company;

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "DVD [company=" + company + ", getNo()=" + getNo() + ", getName()=" + getName() + ", getCategory()="
				+ getCategory() + "]";
	}
}
