package com.cafe24.lms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cafe24.lms.domain.Rent;
import com.cafe24.lms.domain.Reserve;
import com.cafe24.lms.domain.User;

public interface ReserveRepository extends JpaRepository<Reserve, Long>{
	@Query(value="SELECT coalesce(MAX(a.orderNum),0) FROM Reserve a WHERE rent_no=:#{#rent.no}", nativeQuery=false)
	int findMaxOrderNum(@Param("rent") Rent rent);
	
	@Query(value="SELECT r FROM Reserve r WHERE rent_no=:#{#rent.no} AND user_no=:#{#user.no}", nativeQuery=false)
	Reserve boolAlreadyExist(@Param("rent") Rent rent, @Param("user") User user);
	
	Page<Reserve> findAllByOrderByNoDesc(Pageable pageable);
}
