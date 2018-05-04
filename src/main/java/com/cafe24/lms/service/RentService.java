package com.cafe24.lms.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cafe24.lms.domain.Item;
import com.cafe24.lms.domain.Rent;
import com.cafe24.lms.domain.User;
import com.cafe24.lms.repository.RentRepository;

@Service
@Transactional
public class RentService{
	@Autowired
	private RentRepository rentRepository;
	
	public Rent findOne(Long no) {
		return rentRepository.findOne(no);
	}
	
	public void insert(Rent rent, Item item, User user) {
		
		item.setStatus(1);
		rent.setItem(item);
		rent.setUser(user);
		// 반납일 생성
		Calendar cal = new GregorianCalendar(Locale.KOREA);
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_YEAR, 7); // 오늘로부터 7일 증가
		
		rent.setRentDate(new Date());
		rent.setReturnDate(cal.getTime());
		
		rentRepository.save(rent);
		
		System.out.println("user ====> " + user);
		System.out.println("rent ====> " + rent);
	}
	
	// 키워드 없을 때
	public /*Page<Item>*/ HashMap<String, Object> testPage(int nowPage){
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		Page<Rent> list = rentRepository.findAllByOrderByNoDesc(new PageRequest(nowPage-1, 10));
		
		map.put("list", list.getContent());
		map.put("totalCounts", list.getTotalElements());
		map.put("totalPages", list.getTotalPages());
		map.put("nextPage", list.hasNext());
		map.put("previous", list.hasPrevious());
		map.put("nowPage", nowPage);
		
		// 페이지 수는 최대 5개만 보이게 설정.
		int divPage = (int)(nowPage / 5.1);
		int startPage = (int)(5*divPage + 1);
		
		map.put("startPage", startPage);
		
		return map;
	}
}
