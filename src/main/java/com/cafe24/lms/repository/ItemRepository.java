package com.cafe24.lms.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cafe24.lms.domain.Item;
import com.cafe24.lms.util.SearchCriteria;

public interface ItemRepository extends JpaRepository<Item, Long>{
	List<Item> findAllByOrderByNoDesc();
	
	Page<Item> findAll(Pageable pageable);
	Page<Item> findAllByOrderByNoDesc(Pageable pageable);
	
	@Query("SELECT i FROM Item i WHERE i.name LIKE CONCAT('%', :searchString,'%')")
	Page<Item> findAllSearch(@Param("searchString") String searchString, Pageable pageable);
	
	/*@Query(value="SELECT i FROM Item i WHERE i.no > 0 ORDER BY i.no desc", nativeQuery=false)
	public List<Item> listSearchCriteria(@Param("cri") SearchCriteria cri);*/
}
