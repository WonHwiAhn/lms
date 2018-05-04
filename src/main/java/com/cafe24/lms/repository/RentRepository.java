package com.cafe24.lms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe24.lms.domain.Rent;

public interface RentRepository extends JpaRepository<Rent, Long>{
	Page<Rent> findAllByOrderByNoDesc(Pageable pageable);
}
