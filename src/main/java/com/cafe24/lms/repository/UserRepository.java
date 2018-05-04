package com.cafe24.lms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.cafe24.lms.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByEmail(String email);
	User findByEmailAndPassword(String email, String password);
	
	@Modifying
	@Query(value="UPDATE User u SET u.name=:#{#user.name}, u.gender=:#{#user.gender}, u.password=:#{#user.password} WHERE u.no=:#{#user.no}", nativeQuery=false)
	int update(@Param("user") User user);
	
	@Modifying
	@Query(value="UPDATE User u SET u.name=:#{#user.name}, u.gender=:#{#user.gender} WHERE u.no=:#{#user.no}", nativeQuery=false)
	int updateWithoutPass(@Param("user") User user);
}
